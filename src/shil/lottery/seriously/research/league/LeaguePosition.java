package shil.lottery.seriously.research.league;

import java.util.List;
import java.util.Map;

import shil.lottery.seriously.vo.TeamValuePosition;
import shil.lottery.seriously.vo.WholeMatches;

/**
 * 联赛分析位置结果类
 * @author LiangJingJing
 * @date Apr 25, 2015 1:54:36 PM
 */
public class LeaguePosition {
	
	public static int perfect = 5;
	public static int good = 4;
	public static int normal = 3;
	public static int notgood = 2;
	public static int bad = 1;
	
	public static double BelievableRate = 0.89;
	
	private String leaguename;
	//<队伍名称,分析结果对象>方便定位具体某个队伍 
	private Map<String,TeamValuePosition> teamValuePositionMap;
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
		return teamValuePositionMap;
	}
	
	public TeamValuePosition getTeamValuePosition(String teamname){
		return this.teamValuePositionMap.get(teamname);
	}
	
	public void setTeamValuePositionMap(Map<String, TeamValuePosition> teamValuePositionMap) {
		this.teamValuePositionMap = teamValuePositionMap;
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
				+ ", TeamValuePositionMap=" + teamValuePositionMap
				+ "]";
	}
	
	public boolean isBelievable(){
		return this.percentOfTeam() >= LeaguePosition.BelievableRate;
	}
	
	public static LeaguePosition analyzeOneLeague(String leaguename,WholeMatches wholeMatches){
		
		return LeagueUtil.analyzeOneLeague(leaguename, wholeMatches);
	}
}
