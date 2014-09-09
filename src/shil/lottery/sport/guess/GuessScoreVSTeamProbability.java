package shil.lottery.sport.guess;

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
import shil.lottery.sport.strategy.StrategyUtils;

/**
 * 预测两只球队的进球数
 * @author LiangJingJing
 * @since 2014-07-29 21:50
 */
public class GuessScoreVSTeamProbability implements Guess4TeamScores1 {
	
	public static double firstdoor = 0.849d; 
			//0.845d; 
//			0.799d; //best for test

	@Override
	public Set<Integer> guess4teamScores(List<VSTeam> vsTeams,VSTeam predictMatch, boolean debug) {
		Set<Integer> tscores = new HashSet<Integer>();
		
		Map<String,ScoreStuff> wins = AnalyzeScore.analyzeTeamWinScore(vsTeams);
		Map<String,ScoreStuff> loses = AnalyzeScore.analyzeTeamLoseScore(vsTeams);
		
		ScoreStuff his_a_wins = wins.get(predictMatch.getVs()[0]);
		ScoreStuff his_a_loses = loses.get(predictMatch.getVs()[0]);
		
		ScoreStuff his_b_wins = wins.get(predictMatch.getVs()[1]);
		ScoreStuff his_b_loses = loses.get(predictMatch.getVs()[1]);
		
		if(his_a_wins == null || his_a_wins.getScores().size() < 9
				|| his_b_wins == null || his_b_wins.getScores().size() < 9)
		{
			tscores.add(-1);
			return tscores;	
		}
		
		Set<Integer> awScores = new HashSet<Integer>();
		List<ScoreCounter> awlist = AnalyzeScore.sortScore2List(his_a_wins);
		if(debug)
		{
			System.out.println("a wins:");
			StrategyUtils.printFirst24Item(awlist);
		}
		
		if((double)(awlist.get(0).getCounter()+awlist.get(1).getCounter())/his_a_wins.getScores().size() > firstdoor)
		{
			awScores.add(awlist.get(0).getScore());
			awScores.add(awlist.get(1).getScore());
		}
		else if((double)(awlist.get(0).getCounter()+awlist.get(1).getCounter()+awlist.get(2).getCounter())/his_a_wins.getScores().size() > firstdoor)
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
		
		Set<Integer> bwScores = new HashSet<Integer>();
		//计算出b的进球数
		List<ScoreCounter> bwlist = AnalyzeScore.sortScore2List(his_b_wins);
		if(debug)
		{
			System.out.println("b wins:");
			StrategyUtils.printFirst24Item(bwlist);
		}
		
		if((double)(bwlist.get(0).getCounter()+bwlist.get(1).getCounter())/his_b_wins.getScores().size() > firstdoor)
		{
			bwScores.add(bwlist.get(0).getScore());
			bwScores.add(bwlist.get(1).getScore());
		}
		else if((double)(bwlist.get(0).getCounter()+bwlist.get(1).getCounter()+bwlist.get(2).getCounter())/his_b_wins.getScores().size() > firstdoor)
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
		
		Set<Integer> alScores = new HashSet<Integer>();
		List<ScoreCounter> allist = AnalyzeScore.sortScore2List(his_a_loses);
		if(debug)
		{
			System.out.println("a loses:");
			StrategyUtils.printFirst24Item(allist);
		}
		
		if((double)(allist.get(0).getCounter()+allist.get(1).getCounter())/his_a_loses.getScores().size() > firstdoor)
		{
			alScores.add(allist.get(0).getScore());
			alScores.add(allist.get(1).getScore());
		}
		else if((double)(allist.get(0).getCounter()+allist.get(1).getCounter()+allist.get(2).getCounter())/his_a_loses.getScores().size() > firstdoor)
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
		
		Set<Integer> blScores = new HashSet<Integer>();
		List<ScoreCounter> bllist = AnalyzeScore.sortScore2List(his_b_loses);
		if(debug)
		{
			System.out.println("b loses:");
			StrategyUtils.printFirst24Item(bllist);
		}
		
		if((double)(bllist.get(0).getCounter()+bllist.get(1).getCounter())/his_b_loses.getScores().size() > firstdoor)
		{
			blScores.add(bllist.get(0).getScore());
			blScores.add(bllist.get(1).getScore());
		}
		else if((double)(bllist.get(0).getCounter()+bllist.get(1).getCounter()+bllist.get(2).getCounter())/his_b_loses.getScores().size() > firstdoor)
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

		return fscores;
	}
}
