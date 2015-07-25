package shil.lottery.sport.score.diff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import shil.lottery.sport.analyze.AnalyzeScore;
import shil.lottery.sport.entity.ScoreCounter;
import shil.lottery.sport.entity.ScoreCounterValueComparator;
import shil.lottery.sport.entity.ScoreStuff;
import shil.lottery.sport.entity.VSTeam;
import shil.lottery.sport.guess.Guess4TeamScores1;
import shil.lottery.sport.score.GuessScoreVSTeamProbability;
import shil.lottery.sport.strategy.StrategyUtils;

/**
 * 根据nodoor weight来预测进球数
 * 并且考虑了两个队积分的差值情况
 * 首先计算了之前两队积分差值分布的进球数
 * 结合前预测的进球数一起分析预测
 * @author LiangJingJing
 * @since 2014-09-29 15:23
 */
public class GuessScoreAdvancedMilkyWay implements Guess4TeamScores1
{

	@Override
	public Set<Integer> guess4teamScores(List<VSTeam> vsTeams,VSTeam predictMatch, boolean debug) 
	{
		
		AdvancedMilkyWay.showPredictScoreDiffStatus(vsTeams, predictMatch);
		
		List<ScoreCounter> everylist = getPredictScoreList(vsTeams,predictMatch, debug);

		if(everylist.isEmpty()) return Collections.emptySet();
		
		if(predictMatch.getVs()[0].equals("布鲁马波卡纳"))
		{
			System.out.println();
		}
		
		Set<Integer> xscores = new HashSet<Integer>();
		xscores.add(everylist.get(0).getScore());	//23.5%
		xscores.add(everylist.get(1).getScore());	//24.2%
//		xscores.add(everylist.get(2).getScore());	//18%
//		xscores.add(everylist.get(3).getScore());	//13.5%
		
		Set<Integer> fscores = xscores; 
		
		double contain = 0d;
		for(int f=0;f< fscores.size();f++)
		{
			contain += everylist.get(f).getCounter();
		}
		
		double totaleverynum = 0;
		for(ScoreCounter sc : everylist)
		{
			totaleverynum += sc.getCounter();
		}
		
		if(debug)
			System.out.println("最终结果所占比例%:"+ contain / totaleverynum);
		
		return fscores;
		
	}
	
	private List<ScoreCounter> getPredictScoreList(List<VSTeam> vsTeams,VSTeam predictMatch, boolean debug) 
	{
		Map<String, ScoreStuff> wins = AnalyzeScore.analyzeTeamWinScore(vsTeams);
		Map<String, ScoreStuff> loses = AnalyzeScore.analyzeTeamLoseScore(vsTeams);

		ScoreStuff his_a_wins = wins.get(predictMatch.getVs()[0]);
		ScoreStuff his_a_loses = loses.get(predictMatch.getVs()[0]);

		ScoreStuff his_b_wins = wins.get(predictMatch.getVs()[1]);
		ScoreStuff his_b_loses = loses.get(predictMatch.getVs()[1]);

		if (his_a_wins == null || his_a_wins.getScores().size() < GuessScoreVSTeamProbability.minmatch
				|| his_b_wins == null
				|| his_b_wins.getScores().size() < GuessScoreVSTeamProbability.minmatch) 
		{
			return Collections.emptyList();
		}

		List<ScoreCounter> awlist = AnalyzeScore.sortScore2List(his_a_wins);
		if (debug) 
		{
			System.out.println("a wins:");
			StrategyUtils.printFirst24Item(awlist);
		}
		
		Set<ScoreCounter> awScores = new HashSet<ScoreCounter>();
		awScores.addAll(awlist);
		
		List<ScoreCounter> bwlist = AnalyzeScore.sortScore2List(his_b_wins);
		if (debug) 
		{
			System.out.println("b wins:");
			StrategyUtils.printFirst24Item(bwlist);
		}
		
		Set<ScoreCounter> bwScores = new HashSet<ScoreCounter>();
		bwScores.addAll(bwlist);

		List<ScoreCounter> allist = AnalyzeScore.sortScore2List(his_a_loses);
		if (debug) 
		{
			System.out.println("a loses:");
			StrategyUtils.printFirst24Item(allist);
		}
		
		Set<ScoreCounter> alScores = new HashSet<ScoreCounter>();
		alScores.addAll(allist);

		List<ScoreCounter> bllist = AnalyzeScore.sortScore2List(his_b_loses);
		if (debug) 
		{
			System.out.println("b loses:");
			StrategyUtils.printFirst24Item(bllist);
		}
		
		Set<ScoreCounter> blScores = new HashSet<ScoreCounter>();
		blScores.addAll(bllist);

		double awsum = 0d;
		for(ScoreCounter i : awScores) awsum += i.getCounter();
		double bwsum = 0d;
		for(ScoreCounter i : bwScores) bwsum += i.getCounter();
		double alsum = 0d;
		for(ScoreCounter i : alScores) alsum += i.getCounter();
		double blsum = 0d;
		for(ScoreCounter i : blScores) blsum += i.getCounter();

		Map<Integer, ScoreCounter> everyScoresMap = new HashMap<Integer, ScoreCounter>();
		for (ScoreCounter i : awScores) 
		{
			for (ScoreCounter j : blScores) 
			{
				ScoreCounter scoreCounter = everyScoresMap.get(i.getScore() + j.getScore());
				if (scoreCounter == null) 
				{
					scoreCounter = new ScoreCounter(i.getScore() + j.getScore());
					everyScoresMap.put(i.getScore() + j.getScore(), scoreCounter);
				}
				scoreCounter.increaseBingo();
				scoreCounter.increaseWeight(Math.sqrt(((double)i.getCounter()/awsum) * ((double)j.getCounter()/blsum)));
			}
		}

		for (ScoreCounter i : alScores) 
		{
			for (ScoreCounter j : bwScores) 
			{
				ScoreCounter scoreCounter = everyScoresMap.get(i.getScore() + j.getScore());
				if (scoreCounter == null) 
				{
					scoreCounter = new ScoreCounter(i.getScore() + j.getScore());
					everyScoresMap.put(i.getScore() + j.getScore(), scoreCounter);
				}
				scoreCounter.increaseBingo();
				scoreCounter.increaseWeight(Math.sqrt((double)((double)i.getCounter()/alsum) * ((double)j.getCounter()/bwsum)));
			}
		}

		for (ScoreCounter i : awScores) 
		{
			for (ScoreCounter j : bwScores) 
			{
				ScoreCounter scoreCounter = everyScoresMap.get(i.getScore() + j.getScore());
				if (scoreCounter == null) 
				{
					scoreCounter = new ScoreCounter(i.getScore() + j.getScore());
					everyScoresMap.put(i.getScore() + j.getScore(), scoreCounter);
				}
				scoreCounter.increaseBingo();
				scoreCounter.increaseWeight(Math.sqrt(((double)i.getCounter()/awsum) * ((double)j.getCounter()/bwsum)));
			}
		}

		for (ScoreCounter i : alScores) 
		{
			for (ScoreCounter j : blScores) 
			{
				ScoreCounter scoreCounter = everyScoresMap.get(i.getScore() + j.getScore());
				if (scoreCounter == null) 
				{
					scoreCounter = new ScoreCounter(i.getScore() + j.getScore());
					everyScoresMap.put(i.getScore() + j.getScore(), scoreCounter);
				}
				scoreCounter.increaseBingo();
				scoreCounter.increaseWeight(Math.sqrt(((double)i.getCounter()/alsum) * ((double)j.getCounter()/blsum)));
			}
		}
		
		List<ScoreCounter> everylist = new ArrayList<ScoreCounter>();
		for (ScoreCounter sc : everyScoresMap.values()) 
		{
			everylist.add(sc);
		}
		
		Collections.sort(everylist,new ScoreCounterValueComparator());
		
		if (debug) 
		{
			System.out.println("everylist :");
			StrategyUtils.printFirst24Item(everylist);
		}
		
		return everylist;
	}
}
