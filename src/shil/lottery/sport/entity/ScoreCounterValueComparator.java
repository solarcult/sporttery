package shil.lottery.sport.entity;

import java.util.Comparator;

public class ScoreCounterValueComparator implements Comparator<ScoreCounter> {

	@Override
	public int compare(ScoreCounter arg0, ScoreCounter arg1) {

		if (arg0.getValue() > arg1.getValue()) 
		{
			return -1;
		} 
		else if (arg0.getValue() < arg1.getValue()) 
		{
			return 1;
		}
		return 0;
	}

}
