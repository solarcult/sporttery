package shil.lottery.sport.score.diff;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import shil.lottery.sport.cards.AnaylzeVSTeam2CardsbyScore;
import shil.lottery.sport.entity.ScoreCounter;
import shil.lottery.sport.entity.VSTeam;
import shil.lottery.sport.strategy.StrategyUtils;

public class AdvancedMilkyWay {
	
	/**
	 * 展示predict两队的排名分数差值,并选取周围的3个diff来展示进球数分布
	 * @param vsTeams
	 * @param predictMatch
	 * @return
	 */
	public static void showPredictScoreDiffStatus(List<VSTeam> vsTeams,VSTeam predictMatch)
	{
		//比赛信息
		String league = predictMatch.getLeague();
		String teama = predictMatch.getVs()[0];
		String teamb = predictMatch.getVs()[1];
	
		System.out.println("取得 联赛和具体队名 的所有历史  leagues + name:");
		anaylze2ScoreDiffStatus(vsTeams,predictMatch,AnaylzeType.league2teamname,AnaylzeVSTeam2CardsbyScore.makeCardKey(league, teama),AnaylzeVSTeam2CardsbyScore.makeCardKey(league, teamb),true);
		System.out.println();
		System.out.println("只取得 队名 的历史diff状态  team_name:");
		anaylze2ScoreDiffStatus(vsTeams,predictMatch,AnaylzeType.teamname,teama,teamb,true);
	}
	
//	public static DiffScoreCounter predict
	
	/**
	 * 分析两个传入队伍的历史,返回分析后的DiffScoreCounter对象
	 * @param vsTeams
	 * @param oneKey
	 * @param anotherKey
	 * @param debug 是否打印信息
	 * @return
	 */
	public static DiffScoreCounter anaylze2ScoreDiffStatus(List<VSTeam> vsTeams,VSTeam predictMatch,AnaylzeType type,String oneKey,String anotherKey,boolean debug)
	{
		/*
		DiffScoreCounter emptyDiffScoreCounter = new DiffScoreCounter("emptyDiffScoreCounter",DiffScoreCounter.ILLEAGAL);

		Map<String,List<VSMatchResult>> mrmap = AnaylzeTeamLeaguesPoint.anaylzeLeagueTeamVSMatchResult(vsTeams);
		List<VSMatchResult> as = mrmap.get(oneKey);
		List<VSMatchResult> bs = mrmap.get(anotherKey);

		//计算本次的diff
		int diff = AnaylzeTeamLeaguesPoint.calcDiff(as,bs);
		if(diff==DiffScoreCounter.ILLEAGAL) return emptyDiffScoreCounter;
		emptyDiffScoreCounter.setDiff(diff);
		
		*/
		
		DiffScoreCounter one = calcPredictMatchDiff(vsTeams, predictMatch, type);
		if(one.isEmpty()) return one;
		
		int diff = one.getDiff();
		
		if(debug)
		{
			System.out.println("diff is "+diff);
			if(diff >= 5) System.out.println("^^^ watch my match detail, this will be easy to win or lose ^^^\n");
		}
		
		//分析历史比赛状况
		Map<String,List<VSTeam>> vsmap = AnaylzeTeamLeaguesPoint.anaylzeLeagueVSTeam(vsTeams);
		List<VSTeam> alist = vsmap.get(oneKey);
		List<VSTeam> blist = vsmap.get(anotherKey);
		if(alist == null || blist == null) return one;
		
		//计算历史a,b两队diff进球统计信息
		DiffScoreCounter adiff = diffHistoryScoreRate(oneKey, alist, vsTeams,type);
		DiffScoreCounter bdiff = diffHistoryScoreRate(anotherKey, blist, vsTeams,type);
		
		//将两队历史信息整合
		DiffScoreCounter combinedsc = combine2Diff(adiff, bdiff);
		
		if(debug) print2ScoreDiffCounterList(combinedsc,oneKey,anotherKey,diff);
		
		return combinedsc;
	}
	
	/**
	 * 打印出diff附近3个的进球信息
	 * @param combinedsc
	 * @param oneKey
	 * @param anotherKey
	 * @param diff
	 * @return
	 */
	public static List<ScoreCounter> print2ScoreDiffCounterList(DiffScoreCounter combinedsc,String oneKey,String anotherKey,int diff)
	{	
		//取出中间三个
		Set<Integer> is = new HashSet<Integer>();
		is.add(Math.abs(diff-1));
		is.add(Math.abs(diff));
		is.add(Math.abs(diff+1));
		
		System.out.println("team diff time " + oneKey + " vs " + anotherKey);
		System.out.println(diff-1);
		StrategyUtils.printFirst24Item(combinedsc.getDiffMap(Math.abs(diff-1)).values());
		System.out.println("-->"+diff+"<--");
		StrategyUtils.printFirst24Item(combinedsc.getDiffMap(Math.abs(diff)).values());
		System.out.println(diff+1);
		StrategyUtils.printFirst24Item(combinedsc.getDiffMap(Math.abs(diff+1)).values());
		System.out.println("combine all");
		StrategyUtils.printFirst24Item(combinedsc.combineDiffMap(is));
		
		return combinedsc.combineDiffMap(is);
	}
	
	/**
	 * 分析并打印出联赛的Diff分布信息
	 * @param vsTeams
	 * @param league_name
	 */
	public static void showLeagueScoreDiffStatus(List<VSTeam> vsTeams,String league_name)
	{
		//分析比赛状况
		Map<String,List<VSTeam>> vsmap = AnaylzeTeamLeaguesPoint.anaylzeLeagueVSTeam(vsTeams);
		List<VSTeam> league_list = vsmap.get(league_name);
		if(league_list==null) return ;
		
		DiffScoreCounter combinedsc = new DiffScoreCounter("emptyDiffScoreCounter",DiffScoreCounter.COMBINE);
		
		for(VSTeam vsTeam : league_list)
		{
			combinedsc = combine2Diff(combinedsc, calcThisFinishMatchDiff(vsTeams,vsTeam, AnaylzeType.league2teamname));
		}
		
		StrategyUtils.printFirst24Item(combinedsc.getAllScoreMap());
	}
	
	public static DiffScoreCounter calcPredictMatchDiff(List<VSTeam> vsTeams,VSTeam predictMatch,AnaylzeType type)
	{
		String league = predictMatch.getLeague();
		String teama = predictMatch.getVs()[0];
		String teamb = predictMatch.getVs()[1];
		
		String oneKey;
		String anotherKey;
		switch (type)
		{
			case league2teamname:
				oneKey = AnaylzeVSTeam2CardsbyScore.makeCardKey(league, teama);
				anotherKey = AnaylzeVSTeam2CardsbyScore.makeCardKey(league, teamb);
				break;
			case teamname:
				oneKey = teama;
				anotherKey = teamb;
				break;
			default:
				throw new RuntimeException("wtf of this anaylzeType"+type);
		}
		
		DiffScoreCounter onetime = new DiffScoreCounter(oneKey+" vs "+anotherKey,DiffScoreCounter.ILLEAGAL);
		
		//分析比赛状况
		Map<String,List<VSMatchResult>> mrmap = AnaylzeTeamLeaguesPoint.anaylzeLeagueTeamVSMatchResult(vsTeams);
		List<VSMatchResult> as = mrmap.get(oneKey);
		List<VSMatchResult> bs = mrmap.get(anotherKey);
		if(as==null || bs==null ) return onetime;
		//计算本次的diff
		int diff = AnaylzeTeamLeaguesPoint.calcDiff(as,bs);
		onetime.setDiff(diff);
		
		diff = Math.abs(diff);
		
		return onetime;
	}
	
	/**
	 * 分析出predictMatch当场的DiffScoreCounter情况
	 * @param vsTeams
	 * @param finishedMatch
	 * @param oneKey
	 * @param anotherKey
	 * @return
	 */
	public static DiffScoreCounter calcThisFinishMatchDiff(List<VSTeam> vsTeams,VSTeam finishedMatch,AnaylzeType type)//,String oneKey,String anotherKey)
	{
//		DiffScoreCounter onetime = new DiffScoreCounter("onetime:"+oneKey+":"+anotherKey,DiffScoreCounter.ILLEAGAL);
//		
//		//分析比赛状况
//		Map<String,List<VSMatchResult>> mrmap = AnaylzeTeamLeaguesPoint.anaylzeLeagueTeamVSMatchResult(vsTeams);
//		List<VSMatchResult> as = mrmap.get(oneKey);
//		List<VSMatchResult> bs = mrmap.get(anotherKey);
//		if(as==null || bs==null ) return onetime;
//		
//		//计算本次的diff
//		int diff = AnaylzeTeamLeaguesPoint.calcDiff(as,bs);
//		if(diff==DiffScoreCounter.ILLEAGAL) return onetime;
//		onetime.setDiff(diff);
//		diff = Math.abs(diff);
		
		
		DiffScoreCounter onetime = calcPredictMatchDiff(vsTeams, finishedMatch, type);
		onetime.setDiff(Math.abs(onetime.getDiff()));
		onetime.increaseDiffScore(onetime.getDiff(), finishedMatch.getTeama_goals()+finishedMatch.getTeamb_goals());
	
		return onetime;
	}
	
	/**
	 * 将两个DiffScoreCounter的相同diff和Score相加
	 * @param adiff
	 * @param bdiff
	 * @return
	 */
	private static DiffScoreCounter combine2Diff(DiffScoreCounter adiff,DiffScoreCounter bdiff)
	{
		DiffScoreCounter combine = new DiffScoreCounter(adiff.getTeam_name()+":"+bdiff.getTeam_name(),DiffScoreCounter.COMBINE);
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
	private static DiffScoreCounter diffHistoryScoreRate(String team_name,List<VSTeam> oneteamleaguelist,List<VSTeam> vsTeams,AnaylzeType type)
	{
		DiffScoreCounter diffScoreCounter = new DiffScoreCounter(team_name,DiffScoreCounter.COMBINE);
		List<VSTeam> copy_vsTeams = new ArrayList<VSTeam>(vsTeams);
		
		for(int i = oneteamleaguelist.size()-1; i > 0; i--)
		{
			VSTeam vsteam = oneteamleaguelist.get(i);
			//移出掉要运算的对象
			copy_vsTeams.remove(vsteam);
			
			diffScoreCounter = combine2Diff(diffScoreCounter, calcThisFinishMatchDiff(copy_vsTeams,vsteam,type));
		}
		
		return diffScoreCounter;
	}
	
	public static void main(String[] args)
	{
		DiffScoreCounter a = new DiffScoreCounter("a",2);
		a.increaseDiffScore(2, 4);
		a.increaseDiffScore(1, 3);
		
		DiffScoreCounter b = new DiffScoreCounter("b",6);
		b.increaseDiffScore(0, 2);
		b.increaseDiffScore(1, 3);
		
		a = combine2Diff(a, b);
		StrategyUtils.printFirst24Item(a.getAllScoreMap());
	}

}

enum AnaylzeType 
{
	league2teamname,teamname
}
