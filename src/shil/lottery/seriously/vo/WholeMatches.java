package shil.lottery.seriously.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;





import shil.lottery.sport.entity.VSTeam;

/**
 * 联赛名称set<名称>
 * 
 * 联赛Map<联赛名称,List<VSTeam>按从旧到新的顺序>
 * 
 * 队名set<名称>
 * 
 * 队名Map<联赛名称,List<VSTeam>自己在这个联赛中的比赛记录,按从旧到新的顺序>
 * 
 * @author yuanshun.sl
 * @since 2015-04-16 14:50
 *
 */
public class WholeMatches {
	
	//联赛名称set<名称>
	private Set<String> leaguesNames;
	//联赛Map<联赛名称,List<VSTeam>按从旧到新>
	private Map<String,List<VSTeam>> leaguesVSTeamsMap;
	//联赛队伍名称Map<联赛名称,Set<队伍名称>>
	private Map<String,Set<String>> leaguesTeamnamesMap;
	//队伍set<名称>
	private Set<String> teamnames;
	//队伍name,Map<联赛名称,List<VSTeam>自己在这个联赛中的比赛记录,按从旧到新>
	Map<String,Map<String,List<VSTeam>>> teamDigest;
	
	public WholeMatches(){
		this.leaguesNames = new HashSet<String>();
		this.leaguesVSTeamsMap = new HashMap<String, List<VSTeam>>();
		this.leaguesTeamnamesMap = new HashMap<String,Set<String>>();
		this.teamnames = new HashSet<String>();
		this.teamDigest = new HashMap<String, Map<String,List<VSTeam>>>();
	}
	
	public boolean addLeaguesName(String leaguename){
		return this.leaguesNames.add(leaguename);
	}
	
	public void addLeaguesVSTeamsMap(String leaguename, VSTeam vsTeam){
		List<VSTeam> vsTeams = this.leaguesVSTeamsMap.get(leaguename);
		if(vsTeams == null){
			vsTeams = new ArrayList<VSTeam>();
			this.leaguesVSTeamsMap.put(leaguename, vsTeams);
		}
		vsTeams.add(vsTeam);
	}
	
	public void addLeaguesTeamnamesMap(String leaguename, String teamname){
		Set<String> teamnames = this.leaguesTeamnamesMap.get(leaguename);
		if(teamnames == null){
			teamnames = new HashSet<String>();
			this.leaguesTeamnamesMap.put(leaguename, teamnames);
		}
		teamnames.add(teamname);
	}
	
	public boolean addTeamname(String teamname){
		return this.teamnames.add(teamname);
	}
	
	public void addTeamDigest(String teamname,String leaguename,VSTeam vsTeam){
		Map<String,List<VSTeam>> teamleagueMap = this.teamDigest.get(teamname);
		if(teamleagueMap == null){
			teamleagueMap = new HashMap<String,List<VSTeam>>();
			this.teamDigest.put(teamname, teamleagueMap);
		}
		
		List<VSTeam> vsTeams = teamleagueMap.get(leaguename);
		if(vsTeams == null){
			vsTeams = new ArrayList<VSTeam>();
			teamleagueMap.put(leaguename, vsTeams);
		}
		
		//protected:去重,防止相同比赛重复记录
		if(!vsTeams.contains(vsTeam)){
			vsTeams.add(vsTeam);
		}
	}

	public Set<String> getLeaguesNames() {
		return leaguesNames;
	}

	public Map<String, List<VSTeam>> getLeaguesVSTeamsMap() {
		return leaguesVSTeamsMap;
	}

	public Set<String> getTeamnames() {
		return teamnames;
	}

	public Map<String, Map<String, List<VSTeam>>> getTeamDigest() {
		return teamDigest;
	}
	
	public Map<String, Set<String>> getLeaguesTeamnamesMap() {
		return leaguesTeamnamesMap;
	}

	/**
	 * 将VSTeams分析出WholeMatches对象
	 * @param vsTeams
	 * @return
	 */
	public static WholeMatches analyzeWholeMatches(List<VSTeam> vsTeams){
		
		WholeMatches wholeMatches = new WholeMatches();
		
		for(VSTeam vsTeam : vsTeams){
			
			String leaguename = vsTeam.getLeague();
			wholeMatches.addLeaguesName(leaguename);
			wholeMatches.addLeaguesVSTeamsMap(leaguename, vsTeam);
			
			for(String teamname : vsTeam.getVs()){
				wholeMatches.addTeamname(teamname);
				wholeMatches.addTeamDigest(teamname, leaguename, vsTeam);
				wholeMatches.addLeaguesTeamnamesMap(leaguename, teamname);
			}
		}
		
		return wholeMatches;
	}
	
}
