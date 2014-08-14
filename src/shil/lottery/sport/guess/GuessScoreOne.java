package shil.lottery.sport.guess;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import shil.lottery.sport.analyze.AnalyzeScore;
import shil.lottery.sport.analyze.ScoreStuff;
import shil.lottery.sport.domain.VSTeam;

/**
 * 根据联盟进球数的值猜测进球数
 * @author LiangJingJing
 * @since 2014-07-26 20:52
 */
public class GuessScoreOne implements Guess4TeamScores1 {

	@Override
	public Set<Integer> guess4teamScores(List<VSTeam> vsTeams, VSTeam predictMatch, boolean debug) {
		Set<Integer> scores = new HashSet<Integer>();
		
		Map<String,ScoreStuff> totals = AnalyzeScore.analyzeLeagueTotalScore(vsTeams);
		Map<String,ScoreStuff> teamWins = AnalyzeScore.analyzeTeamWinScore(vsTeams);
		Map<String,ScoreStuff> teamLoses = AnalyzeScore.analyzeTeamLoseScore(vsTeams);
		
		if(teamWins.get(predictMatch.getVs()[0])==null
				|| teamWins.get(predictMatch.getVs()[1])==null
				|| teamLoses.get(predictMatch.getVs()[0])==null
				|| teamLoses.get(predictMatch.getVs()[1])==null)
		{
			scores.add(-1);
			return scores;
		}
		
//		if(teamWins.get(predictMatch.getVs()[0]).getRefineScoresDouble().length < 5
//				|| teamWins.get(predictMatch.getVs()[1]).getRefineScoresDouble().length < 5)
//				|| teamWins.get(predictMatch.getVs()[0]).getRefineStandardDeviation() >=1 
//				|| teamWins.get(predictMatch.getVs()[1]).getRefineStandardDeviation() >=1)
//		{
//			scores.add(-2);
//			return scores;
//		}
		
		if(totals.get(predictMatch.getLeague())==null || totals.get(predictMatch.getLeague()).getScores().size() < 10)
		{
			scores.add(-3);
			return scores;	
		}
		
//		scores.add((int) Math.rint(totals.get(predictMatch.getLeague()).getRefineArithmeticAverage()));
//		scores.add((int) Math.rint(totals.get(predictMatch.getLeague()).getRefineArithmeticAverage()+totals.get(predictMatch.getLeague()).getStandardDeviation()));
//		scores.add((int) Math.rint(totals.get(predictMatch.getLeague()).getRefineArithmeticAverage()-totals.get(predictMatch.getLeague()).getStandardDeviation()));
		
		scores.add((int) Math.ceil(totals.get(predictMatch.getLeague()).getRefineArithmeticAverage()));
		scores.add((int) Math.floor(totals.get(predictMatch.getLeague()).getRefineArithmeticAverage()));
		
//		double refineAvg = totals.get(predictMatch.getLeague()).getRefineArithmeticAverage();
//		double refineSD = totals.get(predictMatch.getLeague()).getRefineStandardDeviation();
//		int smallfrom = (int) Math.rint(refineAvg - refineSD);
//		int bigto = (int) Math.rint(refineAvg + refineSD);

		double avg = totals.get(predictMatch.getLeague()).getArithmeticAverage();
		double sd = totals.get(predictMatch.getLeague()).getStandardDeviation();
		int smallfrom = (int) Math.rint(avg - sd);
		int bigto = (int) Math.rint(avg + sd);
		
		for(int i=smallfrom;i<=bigto;i++)
		{
			scores.add(i);
		}
		
		System.out.println(predictMatch.getVs()[0] + " 得: " + teamWins.get(predictMatch.getVs()[0]));
		System.out.println(predictMatch.getVs()[1] + " 得: " + teamWins.get(predictMatch.getVs()[1]));
		System.out.println(predictMatch.getVs()[0] + " 失: " + teamLoses.get(predictMatch.getVs()[0]));
		System.out.println(predictMatch.getVs()[1] + " 失: " + teamLoses.get(predictMatch.getVs()[1]));
		System.out.println();
		System.out.println(predictMatch.getLeague()+" 总体: " + totals.get(predictMatch.getLeague()));
		
		/*
		double winchance = predictMatch.getBetCalcRate_web()[0] * predictMatch.getPeopleVote_rate()[0];
		double drawchance = predictMatch.getBetCalcRate_web()[1] * predictMatch.getPeopleVote_rate()[1];
		double losechance = predictMatch.getBetCalcRate_web()[2] * predictMatch.getPeopleVote_rate()[2];
		
		int result = GuessUtils.findBiggerChance(winchance, drawchance, losechance);
		if(result == 3)
		{
			int w = (int) Math.rint((teamWins.get(predictMatch.getVs()[0]).getRefineArithmeticAverage() + teamLoses.get(predictMatch.getVs()[1]).getRefineArithmeticAverage()));
			scores.add(w);
		}
		else if(result == 0)
		{
			int w = (int) Math.rint((teamWins.get(predictMatch.getVs()[1]).getRefineArithmeticAverage() + teamLoses.get(predictMatch.getVs()[0]).getRefineArithmeticAverage()));
			scores.add(w);
		}
		else if(result == 1)
		{
			scores.add((int) Math.rint(totals.get(predictMatch.getLeague()).getRefineArithmeticAverage()));
		}
		else
		{
			System.out.println(result);
			throw new RuntimeException("wtf");
		}
		*/
		
		/*
		System.out.println("相似度分析:");
		int min = (teamWins.get(predictMatch.getVs()[0]).getScores().size() > teamWins.get(predictMatch.getVs()[1]).getScores().size())? teamWins.get(predictMatch.getVs()[1]).getScores().size(): teamWins.get(predictMatch.getVs()[0]).getScores().size();
		double data1[] = new double[min];
		for(int i=0;i<min;i++)
		{
			data1[i] = teamWins.get(predictMatch.getVs()[0]).getScoresDouble()[i] + teamLoses.get(predictMatch.getVs()[1]).getScoresDouble()[i];
		}
		double data2[] = new double[min];
		for(int i=0;i<min;i++)
		{
			data2[i] = teamWins.get(predictMatch.getVs()[1]).getScoresDouble()[i] + teamLoses.get(predictMatch.getVs()[0]).getScoresDouble()[i];
		}
		double data3[] = new double[min];
		for(int i=0;i<min;i++)
		{
			data3[i] = totals.get(predictMatch.getLeague()).getScoresDouble()[i];
		}
		System.out.println("1+3: "+CorrelationCoefficients.calcNumerical8NumericalCC(data1,data3));
		System.out.println("2+3: "+CorrelationCoefficients.calcNumerical8NumericalCC(data2,data3));
		System.out.println("1+2: "+CorrelationCoefficients.calcNumerical8NumericalCC(data1,data2));
		*/
		return scores;
	}

}
