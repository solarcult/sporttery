package shil.lottery.sport.legacy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import shil.lottery.sport.domain.VSTeam;
import shil.lottery.sport.domain.WCPBean;
import shil.lottery.sport.strategy.StrategyUtils;

public class StrategyTwo implements PursuitOfLottery2{

	public static void main(String[] args)
	{
		List<VSTeam> vsTeams = new ArrayList<VSTeam>();
		
		vsTeams.add(VSTeam.builderVSTeam(
				new String[]{"纽约红牛","哥伦布机"},
				new double[]{1.61d,3.55d,4.55d},
				new double[]{60d,29d,12d}));
		
		vsTeams.add(VSTeam.builderVSTeam(
				new String[]{"费城联合","科罗拉多"},
				new double[]{2.03d,3.35d,3d},
				new double[]{39d,30d,31d}));
		
		vsTeams.add(VSTeam.builderVSTeam(
				new String[]{"多伦多FC","休斯顿迪纳摩"},
				new double[]{1.58d,3.60d,4.70d},
				new double[]{64d,26d,11d}));
		
		vsTeams.add(VSTeam.builderVSTeam(
				new String[]{"蒙特利尔冲击","堪萨斯城竞技"},
				new double[]{2.98d,3.05d,2.18d},
				new double[]{18d,30d,52d}));
		
		vsTeams.add(VSTeam.builderVSTeam(
				new String[]{"新英格兰革命","芝加哥火焰"},
				new double[]{1.70d,3.55d,3.95d},
				new double[]{66d,24d,10d}));
		
		vsTeams.add(VSTeam.builderVSTeam(
				new String[]{"温哥华白帽","美国芝华士"},
				new double[]{1.47d,3.85d,5.45d},
				new double[]{62d,29d,9d}));
		
		vsTeams.add(VSTeam.builderVSTeam(
				new String[]{"洛杉矶银河","皇家盐湖城"},
				new double[]{1.58d,3.60d,4.70d},
				new double[]{64d,26d,11d}));
		
		PursuitOfLottery2 pursuitOfLottery2 = new StrategyTwo();
		pursuitOfLottery2.shooter(vsTeams);
	}
	
	@Override
	public void shooter(List<VSTeam> vsTeams)
	{
		String[][] vs = StrategyUtils.convert2vs(vsTeams);
		double[][] peilv = StrategyUtils.convert2peilv(vsTeams);
		double[][] peopleBelieveRate_bet = StrategyUtils.convert2peopleVote_rate(vsTeams);
		
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
		
		StrategyUtils.printFirst24Item(allinOne);
	}
}
