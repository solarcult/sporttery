package shil.lottery.sport.strategy;

import java.util.List;

import shil.lottery.sport.entity.VSTeam;
import shil.lottery.sport.entity.WCPBean;

/**
 * 第三版接口
 * @author LiangJingJing
 * @since before 2014-07-20
 */
public interface PursuitOfLottery3 {
	public List<WCPBean> shooter(List<VSTeam> vsTeams);
	public List<WCPBean> groupAanalyze2Teams(List<VSTeam> vsTeams);
	public List<WCPBean> groupAanalyze3Teams(List<VSTeam> vsTeams);
	public List<WCPBean> groupAanalyze4Teams(List<VSTeam> vsTeams);
	public List<WCPBean> groupAanalyze5Teams(List<VSTeam> vsTeams);
}
