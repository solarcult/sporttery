package shil.lottery.seriously.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;


import shil.lottery.sport.entity.VSTeam;

/**
 * 联赛名称set<名称>
 * 
 * 联赛Map<联赛名称,List<VSTeam>按从新到旧的顺序,即最前面是最新的>
 * 
 * 队名set<名称>
 * 
 * 队名Map<联赛名称,List<VSTeam>自己在这个联赛中的比赛记录,按从新到旧的顺序,即最前面是最新的>
 * 
 * @author yuanshun.sl
 * @since 2015-04-16 14:50
 *
 */
public class WholeMatches {
	
	//联赛名称set<名称>
	private Set<String> leaguesNames;
	//联赛Map<联赛名称,List<VSTeam>按从新到旧的顺序,即最前面是最新的>
	private Map<String,LinkedList<VSTeam>> leaguesMap;
	//队伍set<名称>
	private Set<String> teamnames;
	//队伍name,Map<联赛名称,List<VSTeam>自己在这个联赛中的比赛记录,按从新到旧的顺序,即最前面是最新的>
	Map<String,Map<String,LinkedList<VSTeam>>> teamDigest;
	
	public WholeMatches(){
		
		this.leaguesNames = new HashSet<String>();
		this.leaguesMap = new HashMap<String, LinkedList<VSTeam>>();
		this.teamnames = new HashSet<String>();
		this.teamDigest = new HashMap<String, Map<String,LinkedList<VSTeam>>>();
	}
	
	public boolean addLeaguesName(String leaguename){
		return this.leaguesNames.add(leaguename);
	}
	
	public void addLeaguesMap(String leaguename, VSTeam vsTeam){
		LinkedList<VSTeam> vsTeams = this.leaguesMap.get(leaguename);
		if(vsTeams == null){
			vsTeams = new LinkedList<VSTeam>();
			this.leaguesMap.put(leaguename, vsTeams);
		}
		
		vsTeams.addFirst(vsTeam);
	}
	
	public boolean addTeamname(String teamname){
		return this.teamnames.add(teamname);
	}
	
	public void addTeamDigest(String teamname,String leaguename,VSTeam vsTeam){
		Map<String,LinkedList<VSTeam>> teamleagueMap = this.teamDigest.get(teamname);
		if(teamleagueMap == null){
			teamleagueMap = new HashMap<String,LinkedList<VSTeam>>();
			this.teamDigest.put(teamname, teamleagueMap);
		}
		
		LinkedList<VSTeam> vsTeams = teamleagueMap.get(leaguename);
		if(vsTeams == null){
			vsTeams = new LinkedList<VSTeam>();
			teamleagueMap.put(leaguename, vsTeams);
		}
		
		vsTeams.addFirst(vsTeam);
	}

	public Set<String> getLeaguesNames() {
		return leaguesNames;
	}

	public Map<String, LinkedList<VSTeam>> getLeaguesMap() {
		return leaguesMap;
	}

	public Set<String> getTeamnames() {
		return teamnames;
	}

	public Map<String, Map<String, LinkedList<VSTeam>>> getTeamDigest() {
		return teamDigest;
	}
	
}
