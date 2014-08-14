package shil.lottery.sport.analyze;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import shil.lottery.sport.domain.VSTeam;
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
			ascore.getScores().add(vs.getTeama_goals());
			
			String teamb = vs.getVs()[1];
			ScoreStuff bscore = vsWinScoreMap.get(teamb); 
			if(bscore==null)
			{
				bscore = new ScoreStuff();
				vsWinScoreMap.put(teamb, bscore);
			}
			bscore.getScores().add(vs.getTeamb_goals());
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
			ascore.getScores().add(vs.getTeamb_goals());
			
			String teamb = vs.getVs()[1];
			ScoreStuff bscore = vsLoseScoreMap.get(teamb); 
			if(bscore==null)
			{
				bscore = new ScoreStuff();
				vsLoseScoreMap.put(teamb, bscore);
			}
			bscore.getScores().add(vs.getTeama_goals());
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
				score = new ScoreStuff();
				leagueScoreMap.put(league, score);
			}
			score.getScores().add(vs.getTeama_goals() + vs.getTeamb_goals());
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
	
	@Test
	public void testScore()
	{
		ScoreStuff s = new ScoreStuff();
		s.getScores().add(38);
		s.getScores().add(73);
		s.getScores().add(86);
		s.getScores().add(90);
		s.getScores().add(111);
		s.getScores().add(124);
		
		double[] d = new double[]{38,73,86,90,111,124};
		Assert.assertEquals("this is it",s.getArithmeticAverage(), StatisticUtils.arithmeticMean(d),0);
		Assert.assertEquals("this is it2",s.getStandardDeviation(), StatisticUtils.standardDeviation(d),0);
	}
	
	public static void main(String[] args)
	{
		System.out.println(Math.ceil(1.12345));
		System.out.println(Math.rint(2.1));
	}

}
