package shil.lottery.seriously.utils;

import java.util.List;

import shil.lottery.sport.db.SportMetaDaoImpl;
import shil.lottery.sport.entity.VSTeam;

/**
 * 将所有List<VSTeam>数据分析为
 * 
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
public class AnalyzeWholeMatchesUtil {

	public static WholeMatches analyzeXmonth(int x){
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadBeforeMonthVSTeamRecords(x);
		return analyze(vsTeams);
	}
	
	public static WholeMatches analyze(List<VSTeam> vsTeams){
		
		WholeMatches wholeMatches = new WholeMatches();
		
		for(VSTeam vsTeam : vsTeams){
			
			String leaguename = vsTeam.getLeague();
			wholeMatches.addLeaguesName(leaguename);
			wholeMatches.addLeaguesMap(leaguename, vsTeam);
			
			for(String teamname : vsTeam.getVs()){
				wholeMatches.addTeamname(teamname);
				wholeMatches.addTeamDigest(teamname, leaguename, vsTeam);
			}
		}
		
		return wholeMatches;
	}
}
