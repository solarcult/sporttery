package shil.lottery.seriously.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//	private double goals;
//	private double losts;
//	private double mean_goals;
//	private double mean_losts;
//	private double sd_goals;
//	private double sd_losts;
	private Map<Double, Double> goaldetailmap;
	private Map<Double, Double> lostdetailmap;
	private double win;
	private double draw;
	private double lose;
	private DescriptiveStatistics goals;
	private DescriptiveStatistics losts;
	private Frequency winscore;
	private Frequency losescore;

	// 主场数据

	// 客场数据

	private Tournament() {
		goaldetailmap = new HashMap<Double, Double>();
		lostdetailmap = new HashMap<Double, Double>();
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

//	public double getGoals() {
//		return goals;
//	}
//
//	public double getLosts() {
//		return losts;
//	}
//
//	public double getMean_goals() {
//		return mean_goals;
//	}
//
//	public double getMean_losts() {
//		return mean_losts;
//	}
//
//	public double getSd_goals() {
//		return sd_goals;
//	}
//
//	public double getSd_losts() {
//		return sd_losts;
//	}

	public Map<Double, Double> getGoaldetailmap() {
		return goaldetailmap;
	}

	public Map<Double, Double> getLostdetailmap() {
		return lostdetailmap;
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

	public static Tournament analyzeVSTeams2Tournament(String leaguename,
			String teamname, List<VSTeam> vsTeams) {
		Tournament tournament = new Tournament();
		tournament.leaguename = leaguename;
		tournament.teamname = teamname;
		tournament.matchesN = vsTeams.size();
		
		for (VSTeam vsTeam : vsTeams) {
			int pos = AnalyzeUtil.pos(teamname, vsTeam.getVs());
			int oppos = AnalyzeUtil.oppos(pos);
			tournament.goals.addValue(vsTeam.getGoals()[pos]);
			tournament.losts.addValue(vsTeam.getGoals()[oppos]);
		}
		
		return tournament;
	}

}
