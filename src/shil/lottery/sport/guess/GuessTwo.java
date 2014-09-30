package shil.lottery.sport.guess;

import shil.lottery.sport.entity.MatchResult;
import shil.lottery.sport.entity.StatusCounter;
import shil.lottery.sport.entity.VSTeam;

/**
 * 按照胜率的差值来预测
 * @author LiangJingJing
 * @since 2014-07-27 23:24
 */
public class GuessTwo implements Guess4TeamMatchResult2 {
	
	public static double door = 0.44d; 
			//0.389d; 
			//0.3;
	
	@Override
	public MatchResult guess4teamMatchResult(VSTeam predictMatch) {
		
		double winchance = predictMatch.getBetCalcRate_web()[0] * predictMatch.getPeopleVote_rate()[0];
		double drawchance = predictMatch.getBetCalcRate_web()[1] * predictMatch.getPeopleVote_rate()[1];
		double losechance = predictMatch.getBetCalcRate_web()[2] * predictMatch.getPeopleVote_rate()[2];
		
		MatchResult matchResult = new MatchResult(door);
		matchResult.setWinStatusCounter(new StatusCounter(3, winchance));
		matchResult.setDrawStatusCounter(new StatusCounter(1, drawchance));
		matchResult.setLoseStatusCounter(new StatusCounter(0, losechance));
		
		return matchResult;
	}
}
