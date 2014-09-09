package shil.lottery.sport.chain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shil.lottery.sport.entity.VSTeam;

/**
 * 
 * @author LiangJingJing
 * @since 20140909-09:47
 */
public class ChainUtils {

	/**
	 * 将原始数据按比赛队伍名称进行分割,每个队伍的比赛结果一个List
	 * @param vsTeams
	 * @return
	 */
	public static Map<String,List<OneTeamMatch>> sortVSTeams2TeamsMap(List<VSTeam> vsTeams)
	{
		Map<String,List<OneTeamMatch>> teamsMap = new HashMap<String, List<OneTeamMatch>>();
		
		for(VSTeam vsTeam : vsTeams)
		{
			String teama = vsTeam.getVs()[0];
			List<OneTeamMatch> oneTeamMatchsHost = teamsMap.get(teama);
			if(oneTeamMatchsHost == null)
			{
				oneTeamMatchsHost = new ArrayList<OneTeamMatch>();
				teamsMap.put(teama, oneTeamMatchsHost);
			}
			oneTeamMatchsHost.add(convertVSTeam2OneTeamMatch(vsTeam, true));
			
			String teamb = vsTeam.getVs()[1];
			List<OneTeamMatch> oneTeamMatchsGuest = teamsMap.get(teamb);
			if(oneTeamMatchsGuest == null)
			{
				oneTeamMatchsGuest = new ArrayList<OneTeamMatch>();
				teamsMap.put(teamb, oneTeamMatchsGuest);
			}
			oneTeamMatchsGuest.add(convertVSTeam2OneTeamMatch(vsTeam, false));
		}
		
		return teamsMap;
	}
	
	/**
	 * 转换两个实体对象
	 * @param vsTeam
	 * @param ishost
	 * @return
	 */
	protected static OneTeamMatch convertVSTeam2OneTeamMatch(VSTeam vsTeam, boolean ishost)
	{
		OneTeamMatch oneTeamMatch = new OneTeamMatch();
		oneTeamMatch.setYear(vsTeam.getYear());
		oneTeamMatch.setMonth(vsTeam.getMonth());
		oneTeamMatch.setDay(vsTeam.getDay());
		oneTeamMatch.setLeague(vsTeam.getLeague());
		oneTeamMatch.setResult(convertResult(vsTeam, ishost));
		setTeamGoals(vsTeam, oneTeamMatch, ishost);
		
		if(ishost)
		{
			oneTeamMatch.setName(vsTeam.getVs()[0]);
			oneTeamMatch.setRival(vsTeam.getVs()[1]);
		}
		else
		{
			oneTeamMatch.setName(vsTeam.getVs()[1]);
			oneTeamMatch.setRival(vsTeam.getVs()[0]);
		}
		
		return oneTeamMatch;
	}
	
	/**
	 * 转换比赛结果
	 * @param vsTeam
	 * @param ishost
	 * @return
	 */
	protected static int convertResult(VSTeam vsTeam,boolean ishost)
	{
		int result = vsTeam.getMatch_Result();
		if(result == 1) return OneTeamMatch.Result_draw;
		if(ishost)
		{
			if(result == 3) return OneTeamMatch.Result_win;
			if(result == 0) return OneTeamMatch.Result_lose;
		}
		else
		{
			if(result == 3) return OneTeamMatch.Result_lose;
			if(result == 0) return OneTeamMatch.Result_win;
		}
		
		assert false:"should not went to here.";
		return -1;
	}
	
	/**
	 * 设置进球数
	 * @param vsTeam
	 * @param oneTeamMatch
	 * @param ishost
	 */
	protected static void setTeamGoals(VSTeam vsTeam,OneTeamMatch oneTeamMatch,boolean ishost)
	{
		if(ishost)
		{
			oneTeamMatch.setWin_goals(vsTeam.getTeama_goals());
			oneTeamMatch.setLose_goals(vsTeam.getTeamb_goals());
		}
		else
		{
			oneTeamMatch.setWin_goals(vsTeam.getTeamb_goals());
			oneTeamMatch.setLose_goals(vsTeam.getTeama_goals());
		}
		
	}
	
	/**
	 * 找到特定两个球队的进球历史
	 * @param teamsMap
	 * @param host
	 * @param guest
	 * @return
	 */
	public static List<OneTeamMatch> findHostvsGuestListDirect(Map<String,List<OneTeamMatch>> teamsMap, String host, String guest)
	{
		List<OneTeamMatch> hvsgs = new ArrayList<OneTeamMatch>();
		List<OneTeamMatch> hosts = teamsMap.get(host);
		if(hosts!=null)
		{
			for(OneTeamMatch one : hosts)
			{
				assert one.getName().equals(host);
				if(one.getRival().equals(guest)) hvsgs.add(one);
			}
		}
		return hvsgs;
	}
	
	/**
	 * 分析历史结果,得到结果摘要
	 * @param oneTeamMatchs
	 * @return
	 */
	public static GuessNiceOneTeamMatchsDigest analyzeOneTeamMatchs(List<OneTeamMatch> oneTeamMatchs)
	{
		GuessNiceOneTeamMatchsDigest guessNiceOneTeamMatchsDigest = new GuessNiceOneTeamMatchsDigest();
		
		if(!oneTeamMatchs.isEmpty())
		{
			guessNiceOneTeamMatchsDigest.setName(oneTeamMatchs.get(0).getName());
			guessNiceOneTeamMatchsDigest.setRival(oneTeamMatchs.get(0).getRival());
		}
		
		for(OneTeamMatch one : oneTeamMatchs)
		{
			if(!one.getName().equals(guessNiceOneTeamMatchsDigest.getName()) || !one.getRival().equals(guessNiceOneTeamMatchsDigest.getRival()))
				throw new RuntimeException("OneTeamMatchs list not pure, contain at least 2 vs team match, as below: "+ " 1. "+guessNiceOneTeamMatchsDigest.getName()+" vs "+guessNiceOneTeamMatchsDigest.getRival() +" ; 2. "+ one.getName()+" vs "+one.getRival());
			
			if(one.getResult()==OneTeamMatch.Result_win) guessNiceOneTeamMatchsDigest.increaseC_win();
			else if(one.getResult()==OneTeamMatch.Result_draw) guessNiceOneTeamMatchsDigest.increaseC_draw();
			else if(one.getResult()==OneTeamMatch.Result_lose) guessNiceOneTeamMatchsDigest.increaseC_lose();
			
			guessNiceOneTeamMatchsDigest.getScoreStuff().addScores(one.getWin_goals()+one.getLose_goals());
		}
		
		return guessNiceOneTeamMatchsDigest;
	}
	
	public static boolean isGuessCorrect(int guess,int answer)
	{
		int c_answer = convertNormal2OneTeamMatchResult(answer);
		
		if(guess == c_answer)
		{
			return true;
		}
		else if(guess == OneTeamMatch.Result_win+OneTeamMatch.Result_draw)
		{
			if(c_answer == OneTeamMatch.Result_win 
					|| answer == OneTeamMatch.Result_draw)
				return true;
		}
		
		else if(guess == OneTeamMatch.Result_win+OneTeamMatch.Result_lose)
		{
			if(c_answer == OneTeamMatch.Result_win 
					|| answer == OneTeamMatch.Result_lose)
				return true;
		}
		
		else if(guess == OneTeamMatch.Result_lose+OneTeamMatch.Result_draw)
		{
			if(c_answer == OneTeamMatch.Result_lose 
					|| answer == OneTeamMatch.Result_draw)
				return true;
		}
		
		return false;
	}
	
	protected static int convertNormal2OneTeamMatchResult(int answer)
	{
		if(answer == 3) return OneTeamMatch.Result_win;
		if(answer == 1) return OneTeamMatch.Result_draw;
		if(answer == 0) return OneTeamMatch.Result_lose;
		
		return answer;
	}
}
