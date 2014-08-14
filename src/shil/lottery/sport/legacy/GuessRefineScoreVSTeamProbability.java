package shil.lottery.sport.legacy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import shil.lottery.sport.analyze.AnalyzeScore;
import shil.lottery.sport.analyze.ScoreStuff;
import shil.lottery.sport.domain.VSTeam;
import shil.lottery.sport.guess.Guess4TeamScores1;
import shil.lottery.sport.guess.GuessScoreLeagueProbability;
import shil.lottery.sport.guess.ScoreCounter;
import shil.lottery.sport.strategy.StrategyUtils;

/**
 * 预测两只球队的进球数
 * @author LiangJingJing
 * @since 2014-07-29 21:50
 */
public class GuessRefineScoreVSTeamProbability implements Guess4TeamScores1 {
	
	public static double firstdoor = 0.7d; //best for test

	@Override
	public Set<Integer> guess4teamScores(List<VSTeam> vsTeams,VSTeam predictMatch, boolean debug) {
		Set<Integer> tscores = new HashSet<Integer>();
		
		Map<String,ScoreStuff> wins = AnalyzeScore.analyzeTeamWinScore(vsTeams);
		Map<String,ScoreStuff> loses = AnalyzeScore.analyzeTeamLoseScore(vsTeams);
		
		ScoreStuff his_a_wins = wins.get(predictMatch.getVs()[0]);
		ScoreStuff his_a_loses = loses.get(predictMatch.getVs()[0]);
		
		ScoreStuff his_b_wins = wins.get(predictMatch.getVs()[1]);
		ScoreStuff his_b_loses = loses.get(predictMatch.getVs()[1]);
		
		if(his_a_wins == null || his_a_wins.getRefineScoresDouble().length < 10
				|| his_b_wins == null || his_b_wins.getRefineScoresDouble().length < 10)
		{
			tscores.add(-1);
			return tscores;	
		}
		
		//计算出a的进球数
		Map<Integer,ScoreCounter> aBingoTimesMap = new HashMap<Integer, ScoreCounter>();
		Set<Integer> awScores = new HashSet<Integer>();
		for(double k : his_a_wins.getRefineScoresDouble())
		{
			int i = (int) k;
			ScoreCounter scoreCounter = aBingoTimesMap.get(i);
			if(scoreCounter==null)
			{
				scoreCounter = new ScoreCounter(i);
				aBingoTimesMap.put(i, scoreCounter);
			}
			scoreCounter.increaseBingo();
		}
		
		List<ScoreCounter> awlist = new ArrayList<ScoreCounter>();
		for(ScoreCounter sc : aBingoTimesMap.values())
		{
			awlist.add(sc);
		}
		Collections.sort(awlist);
//		System.out.println("a wins:");
//		StrategyUtils.printFirst24Item(awlist);
		
		if((double)(awlist.get(0).getCounter()+awlist.get(1).getCounter())/his_a_wins.getRefineScoresDouble().length > firstdoor)
		{
			awScores.add(awlist.get(0).getScore());
			awScores.add(awlist.get(1).getScore());
		}
		else if((double)(awlist.get(0).getCounter()+awlist.get(1).getCounter()+awlist.get(2).getCounter())/his_a_wins.getRefineScoresDouble().length > firstdoor)
		{
			awScores.add(awlist.get(0).getScore());
			awScores.add(awlist.get(1).getScore());
			awScores.add(awlist.get(2).getScore());
		}
		else
		{
			awScores.add(-2);
			return awScores;	
		}
		
		//计算出b的进球数
		Map<Integer,ScoreCounter> bBingoTimesMap = new HashMap<Integer, ScoreCounter>();
		Set<Integer> bwScores = new HashSet<Integer>();
		for(double k : his_b_wins.getRefineScoresDouble())
		{
			int i = (int) k;
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
//		System.out.println("b wins:");
//		StrategyUtils.printFirst24Item(bwlist);
		
		if((double)(bwlist.get(0).getCounter()+bwlist.get(1).getCounter())/his_b_wins.getRefineScoresDouble().length > firstdoor)
		{
			bwScores.add(bwlist.get(0).getScore());
			bwScores.add(bwlist.get(1).getScore());
		}
		else if((double)(bwlist.get(0).getCounter()+bwlist.get(1).getCounter()+bwlist.get(2).getCounter())/his_b_wins.getRefineScoresDouble().length > firstdoor)
		{
			bwScores.add(bwlist.get(0).getScore());
			bwScores.add(bwlist.get(1).getScore());
			bwScores.add(bwlist.get(2).getScore());
		}
		else
		{
			bwScores.add(-3);
			return bwScores;	
		}
		
		//计算a的失球数
		Map<Integer,ScoreCounter> aLoseTimesMap = new HashMap<Integer, ScoreCounter>();
		Set<Integer> alScores = new HashSet<Integer>();
		for(double k : his_a_loses.getRefineScoresDouble())
		{
			int i = (int) k;
			ScoreCounter scoreCounter = aLoseTimesMap.get(i);
			if(scoreCounter==null)
			{
				scoreCounter = new ScoreCounter(i);
				aLoseTimesMap.put(i, scoreCounter);
			}
			scoreCounter.increaseBingo();
		}
		
		List<ScoreCounter> allist = new ArrayList<ScoreCounter>();
		for(ScoreCounter sc : aLoseTimesMap.values())
		{
			allist.add(sc);
		}
		Collections.sort(allist);
//		System.out.println("a loses:");
//		StrategyUtils.printFirst24Item(allist);
		
		if((double)(allist.get(0).getCounter()+allist.get(1).getCounter())/his_a_loses.getRefineScoresDouble().length > firstdoor)
		{
			alScores.add(allist.get(0).getScore());
			alScores.add(allist.get(1).getScore());
		}
		else if((double)(allist.get(0).getCounter()+allist.get(1).getCounter()+allist.get(2).getCounter())/his_a_loses.getRefineScoresDouble().length > firstdoor)
		{
			alScores.add(allist.get(0).getScore());
			alScores.add(allist.get(1).getScore());
			alScores.add(allist.get(2).getScore());
		}
		else
		{
			alScores.add(-4);
			return alScores;	
		}
		
		//计算出b的失球数
		Map<Integer,ScoreCounter> bLoseTimesMap = new HashMap<Integer, ScoreCounter>();
		Set<Integer> blScores = new HashSet<Integer>();
		for(double k : his_b_loses.getRefineScoresDouble())
		{
			int i = (int) k;
			ScoreCounter scoreCounter = bLoseTimesMap.get(i);
			if(scoreCounter==null)
			{
				scoreCounter = new ScoreCounter(i);
				bLoseTimesMap.put(i, scoreCounter);
			}
			scoreCounter.increaseBingo();
		}
		
		List<ScoreCounter> bllist = new ArrayList<ScoreCounter>();
		for(ScoreCounter sc : bLoseTimesMap.values())
		{
			bllist.add(sc);
		}
		Collections.sort(bllist);
//		System.out.println("b loses:");
//		StrategyUtils.printFirst24Item(bllist);
		
		if((double)(bllist.get(0).getCounter()+bllist.get(1).getCounter())/his_b_loses.getRefineScoresDouble().length > firstdoor)
		{
			blScores.add(bllist.get(0).getScore());
			blScores.add(bllist.get(1).getScore());
		}
		else if((double)(bllist.get(0).getCounter()+bllist.get(1).getCounter()+bllist.get(2).getCounter())/his_b_loses.getRefineScoresDouble().length > firstdoor)
		{
			blScores.add(bllist.get(0).getScore());
			blScores.add(bllist.get(1).getScore());
			blScores.add(bllist.get(2).getScore());
		}
		else
		{
			blScores.add(-5);
			return blScores;	
		}
		
		Map<Integer,ScoreCounter> everyScoresMap = new HashMap<Integer, ScoreCounter>();
		
		for(int i: awScores)
		{
			for(int j: blScores)
			{
				ScoreCounter scoreCounter = everyScoresMap.get(i+j);
				if(scoreCounter==null)
				{
					scoreCounter = new ScoreCounter(i+j);
					everyScoresMap.put(i+j, scoreCounter);
				}
				scoreCounter.increaseBingo();
				tscores.add(i+j);
				
			}
		}
		
		for(int i: alScores)
		{
			for(int j: bwScores)
			{
				ScoreCounter scoreCounter = everyScoresMap.get(i+j);
				if(scoreCounter==null)
				{
					scoreCounter = new ScoreCounter(i+j);
					everyScoresMap.put(i+j, scoreCounter);
				}
				scoreCounter.increaseBingo();
				tscores.add(i+j);
			}
		}
		
		for(int i: awScores)
		{
			for(int j: bwScores)
			{
				ScoreCounter scoreCounter = everyScoresMap.get(i+j);
				if(scoreCounter==null)
				{
					scoreCounter = new ScoreCounter(i+j);
					everyScoresMap.put(i+j, scoreCounter);
				}
				scoreCounter.increaseBingo();
				tscores.add(i+j);
			}
		}
		
		for(int i: alScores)
		{
			for(int j: blScores)
			{
				ScoreCounter scoreCounter = everyScoresMap.get(i+j);
				if(scoreCounter==null)
				{
					scoreCounter = new ScoreCounter(i+j);
					everyScoresMap.put(i+j, scoreCounter);
				}
				scoreCounter.increaseBingo();
				tscores.add(i+j);
			}
		}
		
		double totaleverynum = 0;
		List<ScoreCounter> everylist = new ArrayList<ScoreCounter>();
		for(ScoreCounter sc : everyScoresMap.values())
		{
			everylist.add(sc);
			totaleverynum += sc.getCounter();
		}

		if(debug)
		{
			Collections.sort(everylist);
			System.out.println("everylist :");
			StrategyUtils.printFirst24Item(everylist);
		}
		
		Guess4TeamScores1 leagues =  new GuessScoreLeagueProbability();
		Set<Integer> leascores = leagues.guess4teamScores(vsTeams, predictMatch, true);
		
		Set<Integer> fscores = new HashSet<Integer>();
		for(int t : tscores)
		{
			if(leascores.contains(t))
			{
				fscores.add(t);
			}
		}
		
		double contain = 0d;
		for(int f : fscores)
		{
			contain += everyScoresMap.get(f).getCounter();
		}
		
		System.out.println("最终结果所占比例%:"+ contain / totaleverynum);

/*		20140730暂时不用,加上以后减少了猜测次数但胜率没有提高.
		if((contain / totaleverynum) < firstdoor)
		{
			Set<Integer> r = new HashSet<Integer>();
			r.add(-6);
			return r;
		}
*/
		return fscores;
	}
}
