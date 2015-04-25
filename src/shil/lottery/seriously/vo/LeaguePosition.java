package shil.lottery.seriously.vo;

import java.util.List;
import java.util.Map;

/**
 * 联赛分析位置结果类
 * @author LiangJingJing
 * @date Apr 25, 2015 1:54:36 PM
 */
public class LeaguePosition {
	
	public static int perfect = 4;
	public static int good = 3;
	public static int normal = 2;
	public static int bad = 1;
	
	private String leaguename;
	//<队伍名称,分析结果对象>方便定位具体某个队伍 
	private Map<String,TeamValuePosition> TeamValuePositionMap;
	//所有可遍历的结果
	private List<TeamValuePosition> teamValuePositions;
	private int totalTeamNum;
	private int containTeamNum;
	private int minMatches;
	
	public LeaguePosition(String leaguename){
		this.leaguename = leaguename;
	}
	
	public String getLeaguename() {
		return leaguename;
	}
	
	public Map<String, TeamValuePosition> getTeamValuePositionMap() {
		return TeamValuePositionMap;
	}
	
	public void setTeamValuePositionMap(
			Map<String, TeamValuePosition> teamValuePositionMap) {
		TeamValuePositionMap = teamValuePositionMap;
	}
	
	public List<TeamValuePosition> getTeamValuePositions() {
		return teamValuePositions;
	}
	
	public void setTeamValuePositions(List<TeamValuePosition> teamValuePositions) {
		this.teamValuePositions = teamValuePositions;
	}
	
	public int getTotalTeamNum() {
		return totalTeamNum;
	}
	
	public void setTotalTeamNum(int totalTeamNum) {
		this.totalTeamNum = totalTeamNum;
	}
	
	public int getContainTeamNum() {
		return containTeamNum;
	}
	
	public void setContainTeamNum(int containTeamNum) {
		this.containTeamNum = containTeamNum;
	}

	public int getMinMatches() {
		return minMatches;
	}

	public void setMinMatches(int minMatches) {
		this.minMatches = minMatches;
	}
	
	public double percentOfTeam(){
		return (double)containTeamNum/(double)totalTeamNum;
	}

	@Override
	public String toString() {
		return "LeaguePosition [leaguename=" + leaguename
				+ ", totalTeamNum=" + totalTeamNum + ", containTeamNum="
				+ containTeamNum + ", minMatches=" + minMatches
				+ ", percentOfTeam()=" + percentOfTeam() 
				+ ", teamValuePositions=" + teamValuePositions
				+ ", TeamValuePositionMap=" + TeamValuePositionMap
				+ "]";
	}
	
}
