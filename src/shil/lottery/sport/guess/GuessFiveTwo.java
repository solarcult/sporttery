package shil.lottery.sport.guess;

import java.util.List;

import shil.lottery.sport.statistics.StatisticUtils;

public class GuessFiveTwo extends AbstractGuessFive {

	@Override
	public int guessResult(List<ScoreCounter> aws, List<ScoreCounter> als,List<ScoreCounter> bws, List<ScoreCounter> bls) {
		
		double ac = StatisticUtils.weightAverage(aws);
		double bc = StatisticUtils.weightAverage(bws);
		int result = -1;
		if((ac-bc) > scoredoor) result = 3;
		else if(bc - ac >scoredoor) result = 0;
		return result;
	}

}
