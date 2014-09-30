package shil.lottery.sport.score.diff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import shil.lottery.sport.analyze.AnalyzeScore;
import shil.lottery.sport.cards.AnaylzeVSTeam2CardsbyScore;
import shil.lottery.sport.entity.ScoreCounter;
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
		

		anaylzeScoreChange(vsTeams,predictMatch);
		
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
	
	/**
	 * 分析predict两队的排名分数差值,并选取周围的3个diff来展示进球数分布
	 * @param vsTeams
	 * @param predictMatch
	 * @return
	 */
	private List<ScoreCounter> anaylzeScoreChange(List<VSTeam> vsTeams,VSTeam predictMatch)
	{
		//比赛信息
		String league = predictMatch.getLeague();
		String teama = predictMatch.getVs()[0];
		String teamb = predictMatch.getVs()[1];
	
		//分析比赛状况
		Map<String,List<VSTeam>> vsmap = AnaylzeTeamLeaguesPoint.anaylzeLeagueVSTeam(vsTeams);
		Map<String,List<VSMatchResult>> mrmap = AnaylzeTeamLeaguesPoint.anaylzeLeagueTeamVSMatchResult(vsTeams);
		List<VSMatchResult> as = mrmap.get(AnaylzeVSTeam2CardsbyScore.makeCardKey(league, teama));
		List<VSMatchResult> bs = mrmap.get(AnaylzeVSTeam2CardsbyScore.makeCardKey(league, teamb));
		List<VSTeam> alist = vsmap.get(AnaylzeVSTeam2CardsbyScore.makeCardKey(league, teama));
		List<VSTeam> blist = vsmap.get(AnaylzeVSTeam2CardsbyScore.makeCardKey(league, teamb));
		if(as==null || bs==null || alist == null || blist == null) return Collections.emptyList();
		
		//计算本次的diff
		int diff = AnaylzeTeamLeaguesPoint.calcDiff(as,bs);
		if(diff==777) return Collections.emptyList();
		
		//计算历史a,b两队diff进球统计信息
		DiffScoreCounter adiff = diffScoreRate(teama, alist, vsTeams);
		DiffScoreCounter bdiff = diffScoreRate(teamb, blist, vsTeams);
		
		//将两队历史信息整合
		DiffScoreCounter combinedsc = combine2Diff(adiff, bdiff);
		
		//取出中间三个
		Set<Integer> is = new HashSet<Integer>();
		is.add(Math.abs(diff-1));
		is.add(Math.abs(diff));
		is.add(Math.abs(diff+1));
		
		System.out.println();
		System.out.println("diff time:");
		System.out.println(diff-1);
		StrategyUtils.printFirst24Item(combinedsc.getDiffMap(Math.abs(diff-1)).values());
		System.out.println(diff);
		StrategyUtils.printFirst24Item(combinedsc.getDiffMap(Math.abs(diff)).values());
		System.out.println(diff+1);
		StrategyUtils.printFirst24Item(combinedsc.getDiffMap(Math.abs(diff+1)).values());
		System.out.println("$ combine $");
		StrategyUtils.printFirst24Item(combinedsc.combineDiffMap(is));
		
		return combinedsc.combineDiffMap(is);
	}
	
	/**
	 * 将两个DiffScoreCounter的相同diff和Score相加
	 * @param adiff
	 * @param bdiff
	 * @return
	 */
	private DiffScoreCounter combine2Diff(DiffScoreCounter adiff,DiffScoreCounter bdiff)
	{
		DiffScoreCounter combine = new DiffScoreCounter(adiff.getTeam_name()+":"+bdiff.getTeam_name());
		Set<Integer> tdiff = new HashSet<Integer>();
		tdiff.addAll(adiff.getDiffScoreMap().keySet());
		tdiff.addAll(bdiff.getDiffScoreMap().keySet());
		for(int i : tdiff)
		{
			Map<Integer,ScoreCounter> as = adiff.getDiffMap(i);
			Map<Integer,ScoreCounter> bs = bdiff.getDiffMap(i);
			double a_t_score = adiff.getDiff_totalScoreCount(i);
			double b_t_score = bdiff.getDiff_totalScoreCount(i);
			double t_t_score = a_t_score + b_t_score;
			Set<Integer> ss = new HashSet<Integer>();
			ss.addAll(as.keySet());
			ss.addAll(bs.keySet());
			for(int score : ss)
			{
				double ac =0,bc = 0;
				if(as.get(score)!=null)ac = as.get(score).getCounter();
				if(bs.get(score)!=null)bc = bs.get(score).getCounter();
				
				double abs = ac + bc;
				double weight =  abs / t_t_score;
				combine.increaseDiffScoreWeight(i, score, abs , weight);
			}
		}
		
		return combine;
	}
	
	/**
	 * 分析team_name的历史记录,并算出此场比赛在当时diff下的总进球数
	 * @param team_name
	 * @param oneteamleaguelist
	 * @param mrmap
	 * @return DiffScoreCounter
	 */
	private DiffScoreCounter diffScoreRate(String team_name,List<VSTeam> oneteamleaguelist,List<VSTeam> vsTeams)
	{
		DiffScoreCounter diffScoreCounter = new DiffScoreCounter(team_name);
		List<VSTeam> copy_vsTeams = new ArrayList<VSTeam>(vsTeams);
		
		for(int i = oneteamleaguelist.size()-1; i > 0; i--)
		{
			VSTeam vsteam = oneteamleaguelist.get(i);
			//移出掉要运算的对象
			copy_vsTeams.remove(vsteam);
			if(copy_vsTeams.size()==vsTeams.size()) throw new RuntimeException("copy failed");
			
			Map<String,List<VSMatchResult>> mrmap = AnaylzeTeamLeaguesPoint.anaylzeLeagueTeamVSMatchResult(copy_vsTeams);
			
			
			//找到vs[0]的历史记录
			List<VSMatchResult> as = mrmap.get(AnaylzeVSTeam2CardsbyScore.makeCardKey(vsteam.getLeague(), vsteam.getVs()[0]));
			//找到vs[1]的历史记录
			List<VSMatchResult> bs = mrmap.get(AnaylzeVSTeam2CardsbyScore.makeCardKey(vsteam.getLeague(), vsteam.getVs()[1]));
			
			if(as==null || bs == null) continue;
			
			//计算当次的diff结果 
			int diff = AnaylzeTeamLeaguesPoint.calcDiff(as, bs);
			if(diff == 777) continue;
			diff = Math.abs(diff);
			diffScoreCounter.increaseDiffScore(diff, vsteam.getTeama_goals()+vsteam.getTeamb_goals());
		}
		
		return diffScoreCounter;
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
		
		Collections.sort(everylist);
		
		if (debug) 
		{
			System.out.println("everylist :");
			StrategyUtils.printFirst24Item(everylist);
		}
		
		return everylist;
	}
}
