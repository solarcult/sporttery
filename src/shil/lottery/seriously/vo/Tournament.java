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
 * @author yuanshun.sl
 * @since 2015-April-17 11:10
 */
public class Tournament {

	private String leaguename;
	private String teamname;
	private double matchesN;
	private double win;
	private double draw;
	private double lose;
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
	}

	public String getLeaguename() {
		return leaguename;
	}

	public String getTeamname() {
		return teamname;
	}

	public double getMatchesN() {
		return matchesN;
	}

	public double getWin() {
		return win;
	}

	public double getDraw() {
		return draw;
	}

	public double getLose() {
		return lose;
	}

	public static Tournament analyzeVSTeams2Tournament(String leaguename, String teamname, List<VSTeam> vsTeams) {
		Tournament tournament = new Tournament();
		tournament.leaguename = leaguename;
		tournament.teamname = teamname;
		
		//过滤不相关的比赛
		List<VSTeam> refinedVsTeams = AnalyzeUtil.filterVSTeamMatchs(leaguename, teamname, vsTeams);
		
		tournament.matchesN = refinedVsTeams.size();
		
		for (VSTeam vsTeam : refinedVsTeams) {
			int pos = AnalyzeUtil.pos(teamname, vsTeam.getVs());
			int oppos = AnalyzeUtil.oppos(pos);
			tournament.goalStatistics.addValue(vsTeam.getGoals()[pos]);
			tournament.lostStatistics.addValue(vsTeam.getGoals()[oppos]);
			tournament.goalFrequency.addValue(vsTeam.getGoals()[pos]);
			tournament.lostFrequency.addValue(vsTeam.getGoals()[oppos]);
			
			if(pos==0){
				//host
				tournament.hostTournament.goalStatistics.addValue(vsTeam.getGoals()[pos]);
				tournament.hostTournament.lostStatistics.addValue(vsTeam.getGoals()[oppos]);
				tournament.hostTournament.goalFrequency.addValue(vsTeam.getGoals()[pos]);
				tournament.hostTournament.lostFrequency.addValue(vsTeam.getGoals()[oppos]);
			}else if(pos==1){
				tournament.guestTournament.goalStatistics.addValue(vsTeam.getGoals()[pos]);
				tournament.guestTournament.lostStatistics.addValue(vsTeam.getGoals()[oppos]);
				tournament.guestTournament.goalFrequency.addValue(vsTeam.getGoals()[pos]);
				tournament.guestTournament.lostFrequency.addValue(vsTeam.getGoals()[oppos]);
			}else{
				throw new RuntimeException("should not happend here.");
			}
		}
		
		return tournament;
	}

}
