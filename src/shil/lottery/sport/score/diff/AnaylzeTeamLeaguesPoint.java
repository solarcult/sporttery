package shil.lottery.sport.score.diff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import shil.lottery.sport.cards.AnaylzeVSTeam2CardsbyScore;
import shil.lottery.sport.db.SportMetaDaoImpl;
import shil.lottery.sport.entity.VSTeam;

/**
 * 分析每个队伍的积分
 * @author LiangJingJing
 * @since 2014-09-28 20:05
 */
public class AnaylzeTeamLeaguesPoint {
	
	public static int diff_Sample_Size_Max = 6;
	public static int diff_Sample_Size_Min = 4;
	
	/**
	 * 返回的Map<Key,List<VSMatchResult>>
	 * Key分两种,一种是:team_name,另一种是:league+team_name. 
	 * 估计最常用的都会拿league+team_name,这样可以反映一个联赛中,每个队的表现情况,是等质的.
	 * @param vsTeams
	 * @return Map<String,List<VSMatchResult>>
	 */
	public static Map<String,List<VSMatchResult>> anaylzeLeagueTeamVSMatchResult(List<VSTeam> vsTeams)
	{
		Map<String,List<VSMatchResult>> teamMap = new HashMap<String, List<VSMatchResult>>();
		
		for(VSTeam vsTeam : vsTeams)
		{
			String league = vsTeam.getLeague();
			
			//处理teama的记录
			String teama = vsTeam.getVs()[0];
			VSMatchResult teamaresult= new VSMatchResult(vsTeam.getYear()+"-"+vsTeam.getMonth()+"-"+vsTeam.getDay(), teama, league, vsTeam.getTeama_goals(), vsTeam.getTeamb_goals());
			List<VSMatchResult> tas = teamMap.get(teama);
			if(tas==null)
			{
				tas = new ArrayList<VSMatchResult>();
				teamMap.put(teama, tas);
			}
			tas.add(teamaresult);
			
			String leagueTeama = AnaylzeVSTeam2CardsbyScore.makeCardKey(league, teama);
			List<VSMatchResult> leagueteamas = teamMap.get(leagueTeama);
			if(leagueteamas==null)
			{
				leagueteamas = new ArrayList<VSMatchResult>();
				teamMap.put(leagueTeama, leagueteamas);
			}
			leagueteamas.add(teamaresult);
			
			//处理teamb
			String teamb = vsTeam.getVs()[1];
			VSMatchResult teambresult= new VSMatchResult(vsTeam.getYear()+"-"+vsTeam.getMonth()+"-"+vsTeam.getDay(), teamb, league, vsTeam.getTeamb_goals(), vsTeam.getTeama_goals());
			List<VSMatchResult> tbs = teamMap.get(teamb);
			if(tbs==null)
			{
				tbs = new ArrayList<VSMatchResult>();
				teamMap.put(teamb, tbs);
			}
			tbs.add(teambresult);
			
			String leagueTeamb = AnaylzeVSTeam2CardsbyScore.makeCardKey(league, teamb);
			List<VSMatchResult> leagueteambs = teamMap.get(leagueTeamb);
			if(leagueteambs==null)
			{
				leagueteambs = new ArrayList<VSMatchResult>();
				teamMap.put(leagueTeamb, leagueteambs);
			}
			leagueteambs.add(teambresult);
		}
		
		return teamMap;
	}
	
	/**
	 * Key: 1.league+team_name, 2.team_name 3.league_name
	 * Value: List<VSTeam>
	 * @param vsTeams
	 * @return Map<String,List<VSTeam>>
	 */
	public static Map<String,List<VSTeam>> anaylzeLeagueVSTeam(List<VSTeam> vsTeams)
	{
		Map<String,List<VSTeam>> teamMap = new HashMap<String, List<VSTeam>>();
		
		for(VSTeam vsTeam : vsTeams)
		{
			//记录联赛
			String league = vsTeam.getLeague();
			List<VSTeam> leagues = teamMap.get(league);
			if(leagues==null)
			{
				leagues = new ArrayList<VSTeam>();
				teamMap.put(league, leagues);
			}
			leagues.add(vsTeam);
			
			//处理teama的记录
			String teama = vsTeam.getVs()[0];
			List<VSTeam> tas = teamMap.get(teama);
			if(tas==null)
			{
				tas = new ArrayList<VSTeam>();
				teamMap.put(teama, tas);
			}
			tas.add(vsTeam);
			
			String leagueTeama = AnaylzeVSTeam2CardsbyScore.makeCardKey(league, teama);
			List<VSTeam> leagueteamas = teamMap.get(leagueTeama);
			if(leagueteamas==null)
			{
				leagueteamas = new ArrayList<VSTeam>();
				teamMap.put(leagueTeama, leagueteamas);
			}
			leagueteamas.add(vsTeam);
			
			//处理teamb
			String teamb = vsTeam.getVs()[1];
			List<VSTeam> tbs = teamMap.get(teamb);
			if(tbs==null)
			{
				tbs = new ArrayList<VSTeam>();
				teamMap.put(teamb, tbs);
			}
			tbs.add(vsTeam);
			
			String leagueTeamb = AnaylzeVSTeam2CardsbyScore.makeCardKey(league, teamb);
			List<VSTeam> leagueteambs = teamMap.get(leagueTeamb);
			if(leagueteambs==null)
			{
				leagueteambs = new ArrayList<VSTeam>();
				teamMap.put(leagueTeamb, leagueteambs);
			}
			leagueteambs.add(vsTeam);
		}
		
		return teamMap;
	}
	
	/**
	 * 计算两个队当前的排名分值差,去最近6次比赛的结果计算
	 * @param as
	 * @param bs
	 * @return
	 */
	public static int calcDiff(List<VSMatchResult> as,List<VSMatchResult> bs)
	{
		if(as==null || bs == null) return 777;
		int sampleSize = Math.min(as.size(), bs.size());
		if(sampleSize < diff_Sample_Size_Min) return 777;
		if(sampleSize > diff_Sample_Size_Max) sampleSize = diff_Sample_Size_Max;
		int ar  = 0;
		for(int i=as.size()-1;i>=as.size()-sampleSize;i--)
		{
			VSMatchResult a = as.get(i);
			ar+=a.getMatch_result();
		}
		int br  = 0;
		for(int i=bs.size()-1;i>=bs.size()-sampleSize;i--)
		{
			VSMatchResult b = bs.get(i);
			br+=b.getMatch_result();
		}
		return ar - br;
	}
	
	public void analyzeDiffScore(List<VSTeam> vsTeams)
	{
		
	}

	public static void main(String[] args)
	{
		scoreResearch();
		resultResearch();
	}
	
	private static void resultResearch()
	{
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		//diff数量:结果 , counter
		Map<Integer,List<Core>> all = new TreeMap<Integer, List<Core>>();
		
		for(int i=(int)(vsTeams.size()*0.75);i<vsTeams.size();i++)
		{
			List<VSTeam> temp = vsTeams.subList(0, i);
			VSTeam vs = vsTeams.get(i);
			Map<String,List<VSMatchResult>> t = anaylzeLeagueTeamVSMatchResult(temp);
			List<VSMatchResult> as = t.get(AnaylzeVSTeam2CardsbyScore.makeCardKey(vs.getLeague(),vs.getVs()[0]));
			List<VSMatchResult> bs = t.get(AnaylzeVSTeam2CardsbyScore.makeCardKey(vs.getLeague(),vs.getVs()[1]));
			if(as==null || bs==null) continue;
			if(calcDiff(as,bs)==777) continue;
			int dif = calcDiff(as,bs);
			List<Core> cs = all.get(dif);
			if(cs==null)
			{
				cs = new ArrayList<Core>();
				all.put(dif, cs);
			}
			cs.add(new Core(dif,vs.getTeama_goals()+vs.getTeamb_goals(),vs.getMatch_Result()));
		}
		
		for(Entry<Integer,List<Core>> e : all.entrySet())
		{
			System.out.println(e.getKey() + " ~ ");
			
			JFreeChart jFreeChart = ChartFactory.createBarChart("diff:"+e.getKey(), "x", "y", anaylzeCoresMatchResult(e.getValue()));
			ChartFrame chartFrame = new ChartFrame("CardsFrame", jFreeChart);
			chartFrame.pack();
			chartFrame.setVisible(true);
		}
	}

	private static void scoreResearch() 
	{
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
//		Map<String,List<VSMatchResult>> maps = anaylzeLeagueTeamVSMatchResult(vsTeams);
		//diff数量:结果 , counter
		Map<Integer,List<Core>> all = new TreeMap<Integer, List<Core>>();
		
		for(int i=(int)(vsTeams.size()*0.75);i<vsTeams.size();i++)
		{
			List<VSTeam> temp = vsTeams.subList(0, i);
			VSTeam vs = vsTeams.get(i);
			Map<String,List<VSMatchResult>> t = anaylzeLeagueTeamVSMatchResult(temp);
			List<VSMatchResult> as = t.get(AnaylzeVSTeam2CardsbyScore.makeCardKey(vs.getLeague(),vs.getVs()[0]));
			List<VSMatchResult> bs = t.get(AnaylzeVSTeam2CardsbyScore.makeCardKey(vs.getLeague(),vs.getVs()[1]));
			if(as==null || bs==null) continue;
			if(calcDiff(as,bs)==777) continue;
			int dif = Math.abs(calcDiff(as,bs));
//			String key = calcDiff(as,bs)+":"+vs.getMatch_Result()+":"+(vs.getTeama_goals()+vs.getTeamb_goals());
			List<Core> cs = all.get(dif);
			if(cs==null)
			{
				cs = new ArrayList<Core>();
				all.put(dif, cs);
			}
			cs.add(new Core(dif,vs.getTeama_goals()+vs.getTeamb_goals(),vs.getMatch_Result()));

		}
		
		for(Entry<Integer,List<Core>> e : all.entrySet())
		{
			System.out.println(e.getKey() + " ~ ");
			
			JFreeChart jFreeChart = ChartFactory.createBarChart("score:"+e.getKey(), "x", "y", anaylzeCoresScore(e.getValue()));
			ChartFrame chartFrame = new ChartFrame("CardsFrame", jFreeChart);
			chartFrame.pack();
			chartFrame.setVisible(true);
		}
		
		/*
		StrategyUtils.printFirst24Item(maps.get("巴伊亚"));
		System.out.println();
		StrategyUtils.printFirst24Item(maps.get("弗拉门戈"));
		System.out.println();
		StrategyUtils.printFirst24Item(maps.get(AnaylzeVSTeam2CardsbyScore.makeCardKey("巴西甲级联赛","巴伊亚")));
		System.out.println();
		StrategyUtils.printFirst24Item(maps.get(AnaylzeVSTeam2CardsbyScore.makeCardKey("巴西甲级联赛","弗拉门戈")));
		System.out.println();
		*/
	}
	
	private static DefaultCategoryDataset anaylzeCoresMatchResult(List<Core> cores)
	{
		DefaultCategoryDataset dcd = new DefaultCategoryDataset();
		Map<Integer,Integer> m = new HashMap<Integer, Integer>();
		double[] ds = new double[cores.size()];
		for(int i=0;i<cores.size();i++)
		{
			ds[i] = cores.get(i).match_result;
			Integer x = m.get(cores.get(i).match_result);
			if(x == null)
			{
				x = 0;
			}
			x++;
			m.put(cores.get(i).match_result,x);
		}
		
		for(Entry<Integer, Integer> e: m.entrySet())
		{
			dcd.setValue(e.getValue(), e.getKey(), "matchs");;
		}
		
		DescriptiveStatistics s = new DescriptiveStatistics(ds);
		System.out.println(s);
		
		return dcd;
	}
	
	private static DefaultCategoryDataset anaylzeCoresScore(List<Core> cores)
	{
		DefaultCategoryDataset dcd = new DefaultCategoryDataset();
		Map<Integer,Integer> m = new HashMap<Integer, Integer>();
		double[] ds = new double[cores.size()];
		for(int i=0;i<cores.size();i++)
		{
			ds[i] = cores.get(i).goals;
			Integer x = m.get(cores.get(i).goals);
			if(x == null)
			{
				x = 0;
			}
			x++;
			m.put(cores.get(i).goals,x);
		}
		
		for(Entry<Integer, Integer> e: m.entrySet())
		{
			dcd.setValue(e.getValue(), e.getKey(), "numbers");;
		}
		
		DescriptiveStatistics s = new DescriptiveStatistics(ds);
		System.out.println(s);
		
		return dcd;
	}
}

class Core
{
	public int diff;
	public int goals;
	public int match_result;
	
	public Core(int diff, int goals,int match_result)
	{
		this.diff = diff;
		this.goals = goals;
		this.match_result = match_result;
	}

	@Override
	public String toString() {
		return "Core [diff=" + diff + ", goals=" + goals + ", match_result="
				+ match_result + "]";
	}
}
