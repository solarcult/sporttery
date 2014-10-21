package shil.lottery.sport.analyze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import shil.lottery.sport.db.SportMetaDaoImpl;
import shil.lottery.sport.entity.ScoreCounter;
import shil.lottery.sport.entity.ScoreStuff;
import shil.lottery.sport.entity.VSTeam;
import shil.lottery.sport.score.diff.AdvancedMilkyWay;
import shil.lottery.sport.statistics.StatisticUtils;

public class AnalyzeScore {
	
	/**
	 * 计算某队进球数量
	 * @param vsTeams
	 * @return Map<队名,进球数>
	 * @since 2014-07-26 18:33
	 */
	public static Map<String,ScoreStuff> analyzeTeamWinScore(List<VSTeam> vsTeams)
	{
		Map<String,ScoreStuff> vsWinScoreMap = new HashMap<String, ScoreStuff>();
		
		for(VSTeam vs : vsTeams)
		{
			String teama = vs.getVs()[0];
			ScoreStuff ascore = vsWinScoreMap.get(teama); 
			if(ascore==null)
			{
				ascore = new ScoreStuff();
				vsWinScoreMap.put(teama, ascore);
			}
			ascore.addScores(vs.getTeama_goals());
			
			String teamb = vs.getVs()[1];
			ScoreStuff bscore = vsWinScoreMap.get(teamb); 
			if(bscore==null)
			{
				bscore = new ScoreStuff();
				vsWinScoreMap.put(teamb, bscore);
			}
			bscore.addScores(vs.getTeamb_goals());
			
//			if(vs.getVs()[0].equals("利雅得希拉尔") || vs.getVs()[1].equals("利雅得希拉尔")) System.out.println(vs);
		}
		
		return vsWinScoreMap;
	}
	
	/**
	 * 计算某队失球数量
	 * @param vsTeams
	 * @return Map<队名,进球数>
	 * @since 2014-07-26 19:33
	 */
	public static Map<String,ScoreStuff> analyzeTeamLoseScore(List<VSTeam> vsTeams)
	{
		Map<String,ScoreStuff> vsLoseScoreMap = new HashMap<String, ScoreStuff>();
		
		for(VSTeam vs : vsTeams)
		{
			String teama = vs.getVs()[0];
			ScoreStuff ascore = vsLoseScoreMap.get(teama); 
			if(ascore==null)
			{
				ascore = new ScoreStuff();
				vsLoseScoreMap.put(teama, ascore);
			}
			ascore.addScores(vs.getTeamb_goals());
			
			String teamb = vs.getVs()[1];
			ScoreStuff bscore = vsLoseScoreMap.get(teamb); 
			if(bscore==null)
			{
				bscore = new ScoreStuff();
				vsLoseScoreMap.put(teamb, bscore);
			}
			bscore.addScores(vs.getTeama_goals());
		}
		
		return vsLoseScoreMap;
	}
	
	/**
	 * 计算整个联赛的进球数量
	 * @param vsTeams
	 * @return Map<联赛名称,进球数>
	 * @since 2014-07-26 19:33
	 */
	public static Map<String,ScoreStuff> analyzeLeagueTotalScore(List<VSTeam> vsTeams)
	{
		Map<String,ScoreStuff> leagueScoreMap = new HashMap<String,ScoreStuff>();
		
		for(VSTeam vs : vsTeams)
		{
			String league = vs.getLeague();
			ScoreStuff score = leagueScoreMap.get(league); 
			if(score==null)
			{
				score = new ScoreStuff(Integer.MAX_VALUE);
				leagueScoreMap.put(league, score);
			}
			score.addScores(vs.getTeama_goals() + vs.getTeamb_goals());
		}
		
		return leagueScoreMap;
	}
	
	public static Map<String,Set<String>> getLeagueTeamsName(List<VSTeam> vsTeams)
	{
		Map<String,Set<String>> lnmap = new HashMap<String, Set<String>>();
		
		for(VSTeam vs : vsTeams)
		{
			String league = vs.getLeague();
			Set<String> lnames = lnmap.get(league); 
			if(lnames==null)
			{
				lnames = new HashSet<String>();
				lnmap.put(league, lnames);
			}
			lnames.add(vs.getVs()[0]);
			lnames.add(vs.getVs()[1]);
		}
		
		return lnmap;
	}
	
	/**
	 * 分析球队比赛的结果,并统计排降序
	 * @param his_ScoreCounter
	 * @return List<ScoreCounter>
	 * @since 2017-08-02 07:48
	 */
	public static List<ScoreCounter> sortScore2List(ScoreStuff his_ScoreCounter) {
		Map<Integer,ScoreCounter> bBingoTimesMap = new HashMap<Integer, ScoreCounter>();
		for(int i : his_ScoreCounter.getScores())
		{
			ScoreCounter scoreCounter = bBingoTimesMap.get(i);
			if(scoreCounter==null)
			{
				scoreCounter = new ScoreCounter(i);
				bBingoTimesMap.put(i, scoreCounter);
			}
			scoreCounter.increaseBingo();
		}
		
		List<ScoreCounter> bwlist = new ArrayList<ScoreCounter>();
		for(ScoreCounter sc : bBingoTimesMap.values())
		{
			bwlist.add(sc);
		}
		Collections.sort(bwlist);
		return bwlist;
	}
	
	@Test
	public void testScore()
	{
		ScoreStuff s = new ScoreStuff();
		s.addScores(38);
		s.addScores(73);
		s.addScores(86);
		s.addScores(90);
		s.addScores(111);
		s.addScores(124);
		
		double[] d = new double[]{38,73,86,90,111,124};
		Assert.assertEquals("this is it",s.getArithmeticAverage(), StatisticUtils.arithmeticMean(d),0);
		Assert.assertEquals("this is it2",s.getStandardDeviation(), StatisticUtils.standardDeviation(d),0);
	}
	
	public static void main(String[] args)
	{
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		
		AdvancedMilkyWay.showLeagueScoreDiffStatus(vsTeams, "欧洲冠军联赛");
		
		Map<String, ScoreStuff> wins = AnalyzeScore.analyzeTeamWinScore(vsTeams);
		Map<String, ScoreStuff> loses = AnalyzeScore.analyzeTeamLoseScore(vsTeams);
		
		ScoreStuff his_a_wins = wins.get("利雅得希拉尔");
		ScoreStuff his_a_loses = loses.get("利雅得希拉尔");
		System.out.println(his_a_wins);
		System.out.println(his_a_loses);
	}

}
