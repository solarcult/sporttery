package shil.lottery.seriously.research.evaluators.scores;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.math3.stat.Frequency;

import shil.lottery.seriously.research.league.LeagueUtil;
import shil.lottery.seriously.utils.ScoreUtils;
import shil.lottery.seriously.vo.ScoreStatistics;
import shil.lottery.seriously.vo.WholeMatches;
import shil.lottery.sport.entity.ScoreCounter;
import shil.lottery.sport.entity.ScoreCounterMap;
import shil.lottery.sport.entity.ScoreCounterWeightComparator;
import shil.lottery.sport.entity.VSTeam;

/**
 * 核心想法:
 * 1.找到联盟之前的比赛信息,找到最小的比赛场次,将数据取出来
 * 2.根据数据来计算进球数和失球数
 * 3.两两相交
 * 4.取前n个
 * @author LiangJingJing
 * @date Jul 28, 2015 10:28:49 PM
 */
public class TestLeagueTop2Evaluator extends AbstractScoreEvaluator {

	public static long Mutli10000 = 10000;
	
	public static int topN = 3;
	
	public static double pctLimit = 0.758d;
	
	@Override
	public Set<Integer> guessScores(List<VSTeam> vsTeams, VSTeam vsTeam) {
		Set<Integer> predictScores = new HashSet<Integer>();
		WholeMatches wholeMatches = WholeMatches.analyzeWholeMatches(vsTeams);
		Map<String,List<VSTeam>> refineMatches = LeagueUtil.refineLeagueTeamMatches(vsTeam.getLeague(), wholeMatches);
		
		/*
		//取所有的比赛结果,所有结果对预测没有益处
		Map<String,List<VSTeam>> refineMatches = LeagueUtil.getAllLeagueTeamMatches(vsTeam.getLeague(), wholeMatches);
		*/
		List<VSTeam> as = refineMatches.get(vsTeam.getVs()[0]);
		List<VSTeam> bs = refineMatches.get(vsTeam.getVs()[1]);
		if(as==null || bs==null) return predictScores;
		ScoreStatistics teamAss = ScoreStatistics.analyzeVSTeams2scoreStatistics(vsTeam.getLeague(), vsTeam.getVs()[0], refineMatches.get(vsTeam.getVs()[0]));
		ScoreStatistics teamBss = ScoreStatistics.analyzeVSTeams2scoreStatistics(vsTeam.getLeague(), vsTeam.getVs()[1], refineMatches.get(vsTeam.getVs()[1]));

		Frequency ag = teamAss.getGoalFrequency();
		Frequency al = teamAss.getLostFrequency();
		Frequency bg = teamBss.getGoalFrequency();
		Frequency bl = teamBss.getLostFrequency();
		
		/*
		//取主客场比赛结果对结果没有益处
		Frequency ag = teamAss.getHostScoreStatistics().getGoalFrequency();
		Frequency al = teamAss.getHostScoreStatistics().getLostFrequency();
		Frequency bg = teamBss.getGuestScoreStatistics().getGoalFrequency();
		Frequency bl = teamBss.getGuestScoreStatistics().getLostFrequency();
		*/
		if(!ScoreUtils.isPassPctLimit(ag, topN, pctLimit)
				|| !ScoreUtils.isPassPctLimit(al, topN, pctLimit)
				|| !ScoreUtils.isPassPctLimit(bg, topN, pctLimit)
				|| !ScoreUtils.isPassPctLimit(bl, topN, pctLimit)) 
			return predictScores;
		
		Iterator<Entry<Comparable<?>, Long>> agi = ag.entrySetIterator();
		Iterator<Entry<Comparable<?>, Long>> ali = al.entrySetIterator();
		Iterator<Entry<Comparable<?>, Long>> bgi = bg.entrySetIterator();
		Iterator<Entry<Comparable<?>, Long>> bli = bl.entrySetIterator();
		
		ScoreCounterMap scoreCounterMap = new ScoreCounterMap();
		
		// ag * bg
		while(agi.hasNext()){
			Entry age = agi.next();
			Double aged = (Double) age.getKey();
			bgi = bg.entrySetIterator();
			while(bgi.hasNext()){
				Entry bge = bgi.next();
				Double bged = (Double) bge.getKey(); 
				scoreCounterMap.increaseOneHit(aged.intValue()+bged.intValue(), Math.sqrt(ag.getPct(aged)*bg.getPct(bged))*Mutli10000);
			}
		}
		agi = ag.entrySetIterator();
		bgi = bg.entrySetIterator();
		
		// ag * bl
		while(agi.hasNext()){
			Entry age = agi.next();
			Double aged = (Double) age.getKey();
			bli = bl.entrySetIterator();
			while(bli.hasNext()){
				Entry ble = bli.next();
				Double bled = (Double) ble.getKey(); 
				scoreCounterMap.increaseOneHit(aged.intValue()+bled.intValue(), Math.sqrt(ag.getPct(aged)*bl.getPct(bled))*Mutli10000);
			}
		}
		agi = ag.entrySetIterator();
		bli = bl.entrySetIterator();
		
		//al * bg
		while(ali.hasNext()){
			Entry ale = ali.next();
			Double aled = (Double) ale.getKey();
			bgi = bg.entrySetIterator();
			while(bgi.hasNext()){
				Entry bge = bgi.next();
				Double bged = (Double) bge.getKey(); 
				scoreCounterMap.increaseOneHit(aled.intValue()+bged.intValue(),Math.sqrt(al.getPct(aled)*bg.getPct(bged))*Mutli10000);
			}
		}
		ali = al.entrySetIterator();
		bgi = bg.entrySetIterator();
		
		//al * bl
		while(ali.hasNext()){
			Entry ale = ali.next();
			Double aled = (Double) ale.getKey();
			bli = bl.entrySetIterator();
			while(bli.hasNext()){
				Entry ble = bli.next();
				Double bled = (Double) ble.getKey(); 
				scoreCounterMap.increaseOneHit(aled.intValue()+bled.intValue(), Math.sqrt(al.getPct(aled)*bl.getPct(bled))*Mutli10000);
			}
		}
		
		List<ScoreCounter> everylist = scoreCounterMap.getScoreCounterEveryList();
		
		Collections.sort(everylist,new ScoreCounterWeightComparator());
		
		System.out.println(everylist);
		
		predictScores.add(everylist.get(0).getScore());
		predictScores.add(everylist.get(1).getScore());
		predictScores.add(everylist.get(2).getScore());
		
		return predictScores;
	}

	public static void main(String[] args){
		new TestLeagueTop2Evaluator().startEvaluator();
	}
	
}
