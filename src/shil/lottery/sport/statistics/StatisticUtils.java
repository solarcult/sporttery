package shil.lottery.sport.statistics;

import java.util.List;

import shil.lottery.sport.entity.ScoreCounter;

public class StatisticUtils {
	
	/**
	 * 算数平均值
	 * @param ds
	 * @return double
	 * @since 2014-07-25 23:56
	 */
	public static double arithmeticMean(double[] ds)
	{
		double t = 0;
		for(int i=0;i<ds.length;i++) t+=ds[i];
		return t/ds.length;
	}
	
	/**
	 * 计算标准差
	 * @param ds
	 * @return double
	 * @since 2014-07-26 09:12
	 */
	public static double standardDeviation(double[] ds)
	{
		double arithmeticMean = arithmeticMean(ds);
		double fenmu = 0;
		for(int i=0;i<ds.length;i++)
		{
			fenmu += Math.pow(ds[i] - arithmeticMean , 2);
		}
		
		return Math.sqrt(fenmu/(ds.length-1));
	}
	
	public static double weightAverage(List<ScoreCounter> scs)
	{
		double total = 0d;
		for(ScoreCounter sc :scs)
		{
			total += sc.getCounter();
		}
		
		double result = 0d;
		for(ScoreCounter sc :scs)
		{
			result += (double)((double)sc.getCounter()/total) * sc.getScore();
		}
		
		return result;
	}
	
	/**
	 * 勾股定理
	 * @return double
	 * @since 20140808 0:33
	 */
	public static double gougu(double x ,double y)
	{
		return Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
	}
	
	public static void main(String[] args)
	{
		System.out.println(standardDeviation(new double[]{38,73,86,90,111,124}));
		System.out.println(standardDeviation(new double[]{71,84,85,89,90,103}));
		System.out.println(gougu(3,4));
	}

}
