package shil.lottery.sport.score;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import shil.lottery.sport.analyze.AnalyzeScore;
import shil.lottery.sport.entity.ScoreCounter;
import shil.lottery.sport.entity.ScoreStuff;
import shil.lottery.sport.entity.VSTeam;
import shil.lottery.sport.guess.Guess4TeamScores1;
import shil.lottery.sport.strategy.StrategyUtils;

/**
 * 68.8%
 * @author LiangJingJing
 *
 */
public class GuessScoreVSTeamWeight7League7VSTeam implements Guess4TeamScores1 
{
	public static double firstdoor = 0.86;

	@Override
	public Set<Integer> guess4teamScores(List<VSTeam> vsTeams,VSTeam predictMatch, boolean debug) 
	{
		Set<Integer> tscores = new HashSet<Integer>();

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
			tscores.add(-1);
			return tscores;
		}

		List<ScoreCounter> awlist = AnalyzeScore.sortScore2List(his_a_wins);
		if (debug) 
		{
			System.out.println("a wins:");
			StrategyUtils.printFirst24Item(awlist);
		}
		
		Set<ScoreCounter> awScores = new HashSet<ScoreCounter>();
		if ((double) (awlist.get(0).getCounter() + awlist.get(1).getCounter())/ his_a_wins.getScores().size() > firstdoor) 
		{
			awScores.add(awlist.get(0));
			awScores.add(awlist.get(1));
		} 
		else if ((double) (awlist.get(0).getCounter()+ awlist.get(1).getCounter() + awlist.get(2).getCounter())/ his_a_wins.getScores().size() > firstdoor) 
		{
			awScores.add(awlist.get(0));
			awScores.add(awlist.get(1));
			awScores.add(awlist.get(2));
		} else 
		{
			tscores.add(-2);
			return tscores;
		}
		
		
		List<ScoreCounter> bwlist = AnalyzeScore.sortScore2List(his_b_wins);
		if (debug) 
		{
			System.out.println("b wins:");
			StrategyUtils.printFirst24Item(bwlist);
		}
		
		Set<ScoreCounter> bwScores = new HashSet<ScoreCounter>();
		if ((double) (bwlist.get(0).getCounter() + bwlist.get(1).getCounter())/ his_b_wins.getScores().size() > firstdoor) 
		{
			bwScores.add(bwlist.get(0));
			bwScores.add(bwlist.get(1));
		} 
		else if ((double) (bwlist.get(0).getCounter()+ bwlist.get(1).getCounter() + bwlist.get(2).getCounter())/ his_b_wins.getScores().size() > firstdoor) 
		{
			bwScores.add(bwlist.get(0));
			bwScores.add(bwlist.get(1));
			bwScores.add(bwlist.get(2));
		} else
		{
			tscores.add(-3);
			return tscores;
		}
		
		List<ScoreCounter> allist = AnalyzeScore.sortScore2List(his_a_loses);
		if (debug) 
		{
			System.out.println("a loses:");
			StrategyUtils.printFirst24Item(allist);
		}
		
		Set<ScoreCounter> alScores = new HashSet<ScoreCounter>();
		if ((double) (allist.get(0).getCounter() + allist.get(1).getCounter())/ his_a_loses.getScores().size() > firstdoor) 
		{
			alScores.add(allist.get(0));
			alScores.add(allist.get(1));
		} else if ((double) (allist.get(0).getCounter()+ allist.get(1).getCounter() + allist.get(2).getCounter())/ his_a_loses.getScores().size() > firstdoor) 
		{
			alScores.add(allist.get(0));
			alScores.add(allist.get(1));
			alScores.add(allist.get(2));
		} else {
			tscores.add(-4);
			return tscores;
		}
		
		List<ScoreCounter> bllist = AnalyzeScore.sortScore2List(his_b_loses);
		if (debug) 
		{
			System.out.println("b loses:");
			StrategyUtils.printFirst24Item(bllist);
		}
		
		Set<ScoreCounter> blScores = new HashSet<ScoreCounter>();
		if ((double) (bllist.get(0).getCounter() + bllist.get(1).getCounter())/ his_b_loses.getScores().size() > firstdoor) 
		{
			blScores.add(bllist.get(0));
			blScores.add(bllist.get(1));
		} 
		else if ((double) (bllist.get(0).getCounter()+ bllist.get(1).getCounter() + bllist.get(2).getCounter())/ his_b_loses.getScores().size() > firstdoor) 
		{
			blScores.add(bllist.get(0));
			blScores.add(bllist.get(1));
			blScores.add(bllist.get(2));
		} else 
		{
			tscores.add(-5);
			return tscores;
		}
		
		Map<Integer, ScoreCounter> everyScoresMap = new HashMap<Integer, ScoreCounter>();
		double awsum = 0d;
		for(ScoreCounter i:awScores) awsum += i.getCounter();
		double bwsum = 0d;
		for(ScoreCounter i : bwScores) bwsum += i.getCounter();
		double alsum = 0d;
		for(ScoreCounter i:alScores) alsum += i.getCounter();
		double blsum = 0d;
		for(ScoreCounter i: blScores) blsum += i.getCounter();

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
				scoreCounter.increaseWeight(((double)i.getCounter()/awsum) * ((double)j.getCounter()/blsum));
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
				scoreCounter.increaseWeight((double)((double)i.getCounter()/alsum) * ((double)j.getCounter()/bwsum));
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
				scoreCounter.increaseWeight(((double)i.getCounter()/awsum) * ((double)j.getCounter()/bwsum));
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
				scoreCounter.increaseWeight(((double)i.getCounter()/alsum) * ((double)j.getCounter()/blsum));
			}
		}
		
		double totaleverynum = 0;
		List<ScoreCounter> everylist = new ArrayList<ScoreCounter>();
		for (ScoreCounter sc : everyScoresMap.values()) 
		{
			everylist.add(sc);
			totaleverynum += sc.getCounter();
		}

		Collections.sort(everylist);

		if (debug) 
		{
			System.out.println("everylist :");
			StrategyUtils.printFirst24Item(everylist);
		}

		Set<Integer> xscores = new HashSet<Integer>();
		xscores.add(everylist.get(0).getScore());
		xscores.add(everylist.get(1).getScore());
		xscores.add(everylist.get(2).getScore());

		Set<Integer> fscores = new HashSet<Integer>();
		Guess4TeamScores1 leagues = new GuessScoreLeagueProbability();
		Set<Integer> lresult = leagues.guess4teamScores(vsTeams,predictMatch, debug);
		
		Guess4TeamScores1 vscore = new GuessScoreVSTeamProbability();
		Set<Integer> vsresult = vscore.guess4teamScores(vsTeams,predictMatch, debug);
		
		for(int i: xscores)
		{
			if(lresult.contains(i)
					&& vsresult.contains(i)) 
				fscores.add(i);
		}
		
		double contain = 0d;
		for (int f : fscores) 
		{
			contain += everyScoresMap.get(f).getCounter();
		}

		if (debug)
			System.out.println("最终结果所占比例%:" + contain / totaleverynum);

		return fscores;
		
	}

}
