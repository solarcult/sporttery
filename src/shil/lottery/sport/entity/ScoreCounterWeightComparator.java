package shil.lottery.sport.entity;

import java.util.Comparator;

public class ScoreCounterWeightComparator implements Comparator<ScoreCounter> {

	@Override
	public int compare(ScoreCounter arg0, ScoreCounter arg1) {

		if (arg0.getWeight() > arg1.getWeight()) 
		{
			return -1;
		} 
		else if (arg0.getWeight() < arg1.getWeight()) 
		{
			return 1;
		}
		return 0;
	}
}
