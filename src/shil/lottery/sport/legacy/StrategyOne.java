package shil.lottery.sport.legacy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import shil.lottery.sport.entity.WCPBean;
import shil.lottery.sport.strategy.StrategyUtils;

public class StrategyOne implements PursuitOfLottery{

	public static void main(String[] args)
	{
		PursuitOfLottery pursuitOfLottery = new StrategyOne();
		pursuitOfLottery.shooter();
	}
	
	@Override
	public void shooter() 
	{
		
		
		//巴西 , 德国
		String[][] vs = new String[][] 
				{
					{"巴西","荷兰"},
					{"德国","阿根廷"}
				};
		
		double[][] peilv = 
			{
				{2d,3.6d,2.9d},
				{2d,3d,3.45d}
			};
		double[][] peopleBelieveRate_bet = 
			{
				{32d,24d,43d},
				{46d,31d,24d}
			};
		

		/*
		//球队 vs 球队
		String[][] vs = new String[][] 
				{
					{"利勒斯特罗姆","萨尔普斯堡"},		//1
					{"桑内斯","斯达"},	//2
					{"博德闪耀","奥德"},	//3
					{"布兰","斯特罗姆"},	//4
//					{},	//5
//					{"大阪钢巴","金泽塞维根"},	//6
				};
		
		double[][] peilv = 
			{
				{1.5d,4d,4.85d},	//1
				{2.65d,3.4d,2.21d},		//2
				{2.5d,3.35d,2.35d},		//3
				{2.5d,3.55d,2.26d},		//4
//				{1.63d,3.75d,4.1d},		//5
//				{1.12d,6.6d,12.5d},		//6
			};
		
		double[][] peopleBelieveRate_bet = 
			{
				{74d,19d,8d},		//1
				{26d,30d,45d},		//2
				{29d,32d,39d},		//3
				{28d,29d,43d},		//4
//				{77d,17d,6d},		//5
//				{79d,12d,9d}		//6
			};
		*/
		
		
		/*业务逻辑*/
		
		//初始化两对胜平负描述
		String[] score_status = new String[]{"胜","平","负"};
		for(int s = 0;s<score_status.length;s++)
		{
			score_status[s] = vs[0][0] +"."+ score_status[s];
		}
		
		//初始化赔率转换出来的胜率值
		double[][] betCalcRate_web = new double[peilv.length][peilv[0].length];
		for(int b=0;b<peilv.length;b++)
		{
			double total = 0;
			double[] convertRate = new double[peilv[b].length];
			for(int e=0;e<peilv[b].length;e++)
			{
				convertRate[e] = 1 / peilv[b][e];
				total += convertRate[e];
			}
			for(int e = 0; e<convertRate.length;e++)
			{
				betCalcRate_web[b][e] = convertRate[e] / total;
			}
			
			if(total < 1.125) System.out.println("good match: " + vs[b][0] + " vs " +vs[b][1] + " ~ " + total);
			else if(total > 1.125) System.out.println("bad match : " + vs[b][0] + " vs " +vs[b][1] + " ~ " + total);
			else System.out.println("1.125 match : " + vs[b][0] + " vs " +vs[b][1] + " ~ " + total);
		}
		
		double[] n_shouyi = peilv[0];
		double[] n_win = betCalcRate_web[0];
		double[] n_conf = peopleBelieveRate_bet[0];
		for(int a=0;a<peilv.length-1;a++)
		{
			//基础收益
			n_shouyi = StrategyUtils.mutliMatrix(n_shouyi, peilv[a+1]);
			//获胜概率
			n_win = StrategyUtils.mutliMatrix(n_win, betCalcRate_web[a+1]);
			//群众信心
			n_conf = StrategyUtils.mutliMatrix(n_conf, peopleBelieveRate_bet[a+1]);
			//比赛两对描述
			score_status = StrategyUtils.buildScoreStatus(score_status, peilv[a+1],vs[a+1][0]);
		}
		
		List<WCPBean> allinOne = new ArrayList<WCPBean>();
		double[] lirun = new double[n_shouyi.length];
		
		for(int u=0;u<lirun.length;u++)
		{
			lirun[u] = n_shouyi[u] * n_win[u] * n_conf[u] ;
			allinOne.add(new WCPBean(score_status[u], lirun[u], " shouyi: " + n_shouyi[u] + " ; win: " + n_win[u] + " ; conf: " +n_conf[u]));
		}
		
		Collections.sort(allinOne);
		
		System.out.println("all in one total length : " +allinOne.size());
		
		for(int g=0; g<((25>allinOne.size())?allinOne.size():25); g++)
		{
			System.out.println(allinOne.get(g));
		}
	}

}
