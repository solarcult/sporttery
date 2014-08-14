package shil.lottery.sport.analyze;

import java.util.List;

public class NumberUtils {

	public static double[] convertListDs2doubles(List<Double> lds)
	{
		double[] ds = new double[lds.size()];
		int i = 0;
		for(double d : lds)
		{
			ds[i] = d;
			i++;
		}
		
		return ds;
	}
	
	public static double[] convertListsI2doubles(List<Integer> is) {
		double[] r = new double[is.size()];
		for(int i=0;i<is.size();i++)
		{
			r[i] = (double) is.get(i);
		}
		
		return r;
	}
	
}
