package shil.lottery.sport.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import shil.lottery.sport.entity.VSTeam;
import shil.lottery.sport.entity.WCPBean;

/**
 * 第三版接口实现
 * @author LiangJingJing
 * @since before 2014-07-20
 */
public class StrategyThree implements PursuitOfLottery3 {
	
	@Override
	public List<WCPBean> shooter(List<VSTeam> vsTeams) {
		
		String[][] vs = StrategyUtils.convert2vs(vsTeams);
		double[][] peilv = StrategyUtils.convert2peilv(vsTeams);
		double[][] peopleVote_rate = StrategyUtils.convert2peopleVote_rate(vsTeams);
		double[][] betCalcRate_web = StrategyUtils.convert2betCalcRate_web(vsTeams);
		/*业务逻辑*/
		//初始化两对胜平负描述
		String[] score_status = new String[]{"胜","平","负"};
		for(int s = 0;s<score_status.length;s++)
		{
			score_status[s] = vs[0][0] +"."+ score_status[s];
		}
		
		double[] n_shouyi = peilv[0];
		double[] n_win = betCalcRate_web[0];
		double[] n_conf = peopleVote_rate[0];
		for(int a=0;a<peilv.length-1;a++)
		{
			//基础收益
			n_shouyi = StrategyUtils.mutliMatrix(n_shouyi, peilv[a+1]);
			//获胜概率
			n_win = StrategyUtils.mutliMatrix(n_win, betCalcRate_web[a+1]);
			//群众信心
			n_conf = StrategyUtils.mutliMatrix(n_conf, peopleVote_rate[a+1]);
			//比赛两对描述
			score_status = StrategyUtils.buildScoreStatus(score_status, peilv[a+1],vs[a+1][0]);
		}
		
		List<WCPBean> allinOne = new ArrayList<WCPBean>();
		double[] lirun = new double[n_shouyi.length];
		
		for(int u=0;u<lirun.length;u++)
		{
			//核心利润公式
			lirun[u] = n_shouyi[u] * n_win[u] * n_conf[u] ;
			allinOne.add(new WCPBean(score_status[u], lirun[u], " shouyi: " + n_shouyi[u] + " ; win: " + n_win[u] + " ; conf: " +n_conf[u]));
			Collections.sort(allinOne);
			allinOne = StrategyUtils.subList(allinOne, 0, (allinOne.size()<128)?allinOne.size():128);
		}
		
		Collections.sort(allinOne);
		return StrategyUtils.subList(allinOne, 0, (allinOne.size()<26)?allinOne.size():26);
	}
	
	@Override
	public List<WCPBean> groupAanalyze2Teams(List<VSTeam> vsTeams) {
		
		List<WCPBean> finallyResult = new LinkedList<WCPBean>();
		
			for(int i=0;i<vsTeams.size();i++)
			{
				for(int j=i+1;j<vsTeams.size();j++)
				{
					List<VSTeam> teams = new ArrayList<VSTeam>();
					teams.add(vsTeams.get(i));
					teams.add(vsTeams.get(j));
	
					finallyResult.addAll(shooter(teams));
				}
			}
			
			Collections.sort(finallyResult);
			return finallyResult;
	}
	
	@Override
	public List<WCPBean> groupAanalyze3Teams(List<VSTeam> vsTeams) {
		
		List<WCPBean> finallyResult = new ArrayList<WCPBean>();

		for(int i=0;i<vsTeams.size();i++)
		{
			for(int j=i+1;j<vsTeams.size();j++)
			{
				for(int k=j+1;k<vsTeams.size();k++)
				{
					List<VSTeam> teams = new ArrayList<VSTeam>();
					teams.add(vsTeams.get(i));
					teams.add(vsTeams.get(j));
					teams.add(vsTeams.get(k));
					
					finallyResult.addAll(shooter(teams));
				}
			}
		}
		
		Collections.sort(finallyResult);
		return finallyResult;
	}

	@Override
	public List<WCPBean> groupAanalyze4Teams(List<VSTeam> vsTeams) {
		List<WCPBean> finallyResult = new ArrayList<WCPBean>();
		
		for(int i=0;i<vsTeams.size();i++)
		{
			for(int j=i+1;j<vsTeams.size();j++)
			{
				for(int k=j+1;k<vsTeams.size();k++)
				{
					for(int l=k+1;l<vsTeams.size();l++)
					{
						List<VSTeam> teams = new ArrayList<VSTeam>();
						teams.add(vsTeams.get(i));
						teams.add(vsTeams.get(j));
						teams.add(vsTeams.get(k));
						teams.add(vsTeams.get(l));
						
						finallyResult.addAll(shooter(teams));
					}
				}
			}
		}
		
		Collections.sort(finallyResult);
		return finallyResult;
	}

	@Override
	public List<WCPBean> groupAanalyze5Teams(List<VSTeam> vsTeams) {
		List<WCPBean> finallyResult = new ArrayList<WCPBean>();

		for(int i=0;i<vsTeams.size();i++)
		{
			for(int j=i+1;j<vsTeams.size();j++)
			{
				for(int k=j+1;k<vsTeams.size();k++)
				{
					for(int l=k+1;l<vsTeams.size();l++)
					{
						for(int m=l+1; m<vsTeams.size();m++)
						{
							List<VSTeam> teams = new ArrayList<VSTeam>();
							teams.add(vsTeams.get(i));
							teams.add(vsTeams.get(j));
							teams.add(vsTeams.get(k));
							teams.add(vsTeams.get(l));
							teams.add(vsTeams.get(m));
							
							finallyResult.addAll(shooter(teams));
						}
					}
				}
			}
		}
		Collections.sort(finallyResult);
		return finallyResult;
	}
}
