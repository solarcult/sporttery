package shil.lottery.sport.guess;

import java.util.List;
import java.util.Map;

import shil.lottery.sport.analyze.AnalyzeScore;
import shil.lottery.sport.entity.ScoreCounter;
import shil.lottery.sport.entity.ScoreStuff;
import shil.lottery.sport.entity.VSTeam;
import shil.lottery.sport.strategy.StrategyUtils;

/**
 * 根据进球数预测成败
 * @author LiangJingJing
 * @since 2014-08-02 07:28
 */
public abstract class AbstractGuessFive implements Guess4TeamMatchResult3 {
	
	public static double scoredoor = 1.2d;
	
	@Override
	public int guess4teamMatchResult(List<VSTeam> vsTeams, VSTeam predictMatch) {
		
		boolean debug = false;
		
		Map<String,ScoreStuff> wins = AnalyzeScore.analyzeTeamWinScore(vsTeams);
		Map<String,ScoreStuff> loses = AnalyzeScore.analyzeTeamLoseScore(vsTeams);
		
		ScoreStuff his_a_wins = wins.get(predictMatch.getVs()[0]);
		ScoreStuff his_a_loses = loses.get(predictMatch.getVs()[0]);
		
		ScoreStuff his_b_wins = wins.get(predictMatch.getVs()[1]);
		ScoreStuff his_b_loses = loses.get(predictMatch.getVs()[1]);
		
		if(his_a_wins == null || his_a_wins.getScores().size() < 9
				|| his_b_wins == null || his_b_wins.getScores().size() < 9)
		{
			return -1;
		}
		
		//计算出阿的进球数
		List<ScoreCounter> awlist = AnalyzeScore.sortScore2List(his_a_wins);
		if(debug)
		{
			System.out.println("a wins:");
			StrategyUtils.printFirst24Item(awlist);
		}
		
		//计算出b的进球数
		List<ScoreCounter> bwlist = AnalyzeScore.sortScore2List(his_b_wins);
		if(debug)
		{
			System.out.println("b wins:");
			StrategyUtils.printFirst24Item(bwlist);
		}
		
		List<ScoreCounter> allist = AnalyzeScore.sortScore2List(his_a_loses);
		if(debug)
		{
			System.out.println("a loses:");
			StrategyUtils.printFirst24Item(allist);
		}
		
		List<ScoreCounter> bllist = AnalyzeScore.sortScore2List(his_b_loses);
		if(debug)
		{
			System.out.println("b loses:");
			StrategyUtils.printFirst24Item(bllist);
		}
		
		return guessResult(awlist,allist,bwlist,bllist);
	}
	
	public abstract int guessResult(List<ScoreCounter> aws,List<ScoreCounter> als,List<ScoreCounter> bws,List<ScoreCounter> bls);

}
