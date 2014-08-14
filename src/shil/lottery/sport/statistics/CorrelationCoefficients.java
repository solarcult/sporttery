package shil.lottery.sport.statistics;

/**
 * 
 * @author LiangJingJing
 * @since 2014-07-20 15:36
 */
public class CorrelationCoefficients {
	
	/**
	 * 计算两组数值型变量的关联度,数值个数相对应
	 * @param numericals_numericals
	 * @return -1~1
	 * @since 2014-07-25 23:35 start 57done
	 */
	public static double calcNumerical8NumericalCC(double[] numericals1,double[] numericals2)
	{
		if(numericals1.length != numericals2.length)
		{
			throw new RuntimeException("two numerical matrix length not match");
		}
		
		double avg1 = StatisticUtils.arithmeticMean(numericals1);
		double avg2 = StatisticUtils.arithmeticMean(numericals2);
		
		double[] dis_numericals1 = new double[numericals1.length];
		for(int i=0;i<numericals1.length;i++)
		{
			dis_numericals1[i] = numericals1[i] - avg1;
		}
		double[] dis_numericals2 = new double[numericals2.length];
		for(int i=0;i<numericals2.length;i++)
		{
			dis_numericals2[i] = numericals2[i] - avg2;
		}
		double Sxx = 0;
		for(int i=0;i<dis_numericals1.length;i++)
		{
			Sxx+= Math.pow(dis_numericals1[i], 2);
		}
		double Syy = 0;
		for(int i=0;i<dis_numericals2.length;i++)
		{
			Syy+= Math.pow(dis_numericals2[i], 2);
		}
		double Sxy = 0;
		for(int i=0;i<dis_numericals1.length;i++)
		{
			Sxy+= dis_numericals1[i] * dis_numericals2[i];
		}
		
		return Sxy/Math.sqrt(Sxx*Syy);
	}
	
	/**
	 * 计算类型的具体数据的相关度,具体类别所传数量不要求相等
	 * @param categorys_numericals
	 * @return 0~1
	 * @since 2014-07-25 23:33 done
	 */
	public static double calcCategory8NumericalCC(double[] ... categorys_numericals)
	{
		//计算类型所传入数据的平均数
		double[] n_average = new double[categorys_numericals.length];
		double t_sum = 0;
		double t_number = 0;
		for(int i=0;i<categorys_numericals.length;i++)
		{
			double t_total = 0;
			for(int j=0;j<categorys_numericals[i].length;j++)
			{
				t_total += categorys_numericals[i][j];
				t_number++;
				t_sum += categorys_numericals[i][j];
			}
			n_average[i] = t_total / categorys_numericals[i].length;
		}
		
		//整体的平均数
		double total_average = t_sum/t_number;
		
		double S_group_inside_variable = 0;
		for(int i=0;i<categorys_numericals.length;i++)
		{
			for(int j=0;j<categorys_numericals[i].length;j++)
			{
				S_group_inside_variable += Math.pow((categorys_numericals[i][j] - n_average[i]),2);
			}
		}
		
		double S_group_between_variable = 0;
		for(int i=0;i<categorys_numericals.length;i++)
		{
			S_group_between_variable += categorys_numericals[i].length * Math.pow((n_average[i]-total_average), 2);
		}
		
		return S_group_between_variable / (S_group_between_variable+S_group_inside_variable);
	}
	
	public static void main(String[] args)
	{
		System.out.println(calcCategory8NumericalCC(new double[]{23,26,27,28},new double[]{25,26,29,32,33},new double[]{15,16,18,22,26,29}));
		System.out.println(calcNumerical8NumericalCC(new double[]{3000,5000,12000,2000,7000,15000,5000,6000,8000,10000}, new double[]{7000,8000,25000,5000,12000,30000,10000,15000,20000,18000}));
	}

}
