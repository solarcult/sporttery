package shil.lottery.seriously.vo;

import java.util.List;

import org.apache.commons.math3.stat.Frequency;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import shil.lottery.seriously.utils.AnalyzeUtil;
import shil.lottery.sport.entity.VSTeam;

/**
 * 联赛名称 球队名称 总场次 总进球 总失球 进球平均/标准差 失球平均/标准差 进n球纪录<Map<score,count>>
 * 失n球纪录<Map<score,count>>
 * 
 * 这个类只能记录某一个球队的数据,是一个大体的统计,没有精细到某一场比赛,也没有关联赔率相关信息. needs to improve in other codes
 * 
 * @author yuanshun.sl
 * @since 2015-April-17 11:10
 */
public class Tournament {
	
	public static int lose = 0;
	public static int draw = 1;
	public static int win = 3;

	private String leaguename;
	private String teamname;
	//胜负平统计
	private Frequency match013;
	//记录进球的平均数量和基本数据分析
	private DescriptiveStatistics goalStatistics;
	private DescriptiveStatistics lostStatistics;
	//记录进球的数量的频率统计信息
	private Frequency goalFrequency;
	private Frequency lostFrequency;

	// 主场数据
	private Tournament hostTournament;
	// 客场数据
	private Tournament guestTournament;

	private Tournament() {
		this.goalStatistics = new DescriptiveStatistics();
		this.lostStatistics = new DescriptiveStatistics();
		this.goalFrequency =  new Frequency();
		this.lostFrequency = new Frequency();
		this.hostTournament = new Tournament();
		this.guestTournament = new Tournament(); 
		this.match013 = new Frequency();
	}

	public String getLeaguename() {
		return leaguename;
	}

	public String getTeamname() {
		return teamname;
	}

	public static Tournament analyzeVSTeams2Tournament(String leaguename, String teamname, List<VSTeam> vsTeams) {
		Tournament tournament = new Tournament();
		tournament.leaguename = leaguename;
		tournament.teamname = teamname;
		
		//过滤不相关的比赛, 去掉这个限制,因为这样可以得到这支球队所有的比赛结果,不用被联赛限制,为了更好的通用性.过滤应在外层调用
		//List<VSTeam> refinedVsTeams = AnalyzeUtil.filterVSTeamMatchs(leaguename, teamname, vsTeams);
		
		for (VSTeam vsTeam : vsTeams) {
			int pos = AnalyzeUtil.pos(teamname, vsTeam.getVs());
			if(pos==-1) continue;
			feedTournament(tournament, pos, vsTeam.getGoals());
			
			//host?
			if(pos==0){
				feedTournament(tournament.hostTournament, pos, vsTeam.getGoals());
			}else if(pos==1){
				feedTournament(tournament.guestTournament, pos, vsTeam.getGoals());
			}
		}
		
		return tournament;
	}
	
	public static void feedTournament(Tournament tournament , int postion , double[] goals){
		int oppos = AnalyzeUtil.oppos(postion);
		tournament.goalStatistics.addValue(goals[postion]);
		tournament.lostStatistics.addValue(goals[oppos]);
		tournament.goalFrequency.addValue(goals[postion]);
		tournament.lostFrequency.addValue(goals[oppos]);
		tournament.match013.addValue(AnalyzeUtil.match013(postion,goals));
	}

}
