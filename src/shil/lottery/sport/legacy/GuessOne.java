package shil.lottery.sport.legacy;

import shil.lottery.sport.domain.VSTeam;
import shil.lottery.sport.guess.GuessUtils;

/**
 * 根据庄家的胜率和群众的胜率预测
 * @author LiangJingJing
 * @since 2014-07-23 23:56
 */
public class GuessOne implements Guess4TeamMatchResult1 {

	@Override
	public int guess4teamMatchResult(VSTeam predictMatch) {
		
		double winchance = predictMatch.getBetCalcRate_web()[0] * predictMatch.getPeopleVote_rate()[0];
		double drawchance = predictMatch.getBetCalcRate_web()[1] * predictMatch.getPeopleVote_rate()[1];
		double losechance = predictMatch.getBetCalcRate_web()[2] * predictMatch.getPeopleVote_rate()[2];
		
		System.out.println("winchance" + winchance);
		System.out.println("drawchance" + drawchance);
		System.out.println("losechance" + losechance);
		
		return GuessUtils.findFinallyResult(winchance, drawchance, losechance);
	}

}
