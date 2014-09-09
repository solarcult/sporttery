package shil.lottery.sport.guess;

import java.util.List;
import java.util.Map;

import shil.lottery.sport.analyze.AnalyzeTeamMatchResult;
import shil.lottery.sport.analyze.TeamMatchResult;
import shil.lottery.sport.entity.MatchResult;
import shil.lottery.sport.entity.StatusCounter;
import shil.lottery.sport.entity.VSTeam;

/**
 * 根据历史胜负平的概率猜结果,貌似不好用,door和输赢的系数不好取,而且door的算法是否科学还有疑问
 * @author LiangJingJing
 * @since 2014-07-30 22:36
 */
public class GuessThree implements Guess4TeamMatchResult3 {

	public static double minMatchs = 5;
	public static double door = 1d;
			//1.5d;
	
	@Override
	public int guess4teamMatchResult(List<VSTeam> vsTeams,VSTeam predictMatch) {
		
		Map<String, TeamMatchResult> tmrs = AnalyzeTeamMatchResult.analyzeTeamMatchResult(vsTeams);
		
		TeamMatchResult a = tmrs.get(predictMatch.getVs()[0]);
		TeamMatchResult b = tmrs.get(predictMatch.getVs()[1]);
		
		if(a==null || b==null) return -1;
		if(a.getTotal() < minMatchs || b.getTotal() < minMatchs) return -3;
//		if(a.getWin_3()==0 || a.getDraw_1() ==0 || a.getLose_0() == 0
//				|| b.getWin_3()==0 || b.getDraw_1() == 0 || b.getLose_0() == 0)
//			return -4;
		
		double winchance = Math.pow(a.getW3P() * b.getL0P(),2) *10 *10
				* predictMatch.getBetCalcRate_web()[0]
				* predictMatch.getPeopleVote_rate()[0];
		double drawchance = Math.pow(a.getD1P() * b.getD1P(),2) *10 *10
				*predictMatch.getBetCalcRate_web()[1] 
				* predictMatch.getPeopleVote_rate()[1];
		double losechance = Math.pow(a.getL0P() * b.getW3P(),2) *10 *10
				*predictMatch.getBetCalcRate_web()[2] 
				* predictMatch.getPeopleVote_rate()[2];
		
		MatchResult matchResult = new MatchResult(door);
		matchResult.setWinStatusCounter(new StatusCounter(3, winchance));
		matchResult.setDrawStatusCounter(new StatusCounter(1, drawchance));
		matchResult.setLoseStatusCounter(new StatusCounter(0, losechance));
		
//		System.out.println(matchResult);
		
		return matchResult.getMatch_Result();
	}

}
