package shil.lottery.seriously.utils;

import java.util.ArrayList;
import java.util.List;

import shil.lottery.seriously.DO.WholeMatches;
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
public class AnalyzeUtil {

	public static WholeMatches analyzeXmonth(int x){
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadBeforeMonthVSTeamRecords(x);
		return analyzeWholeMatches(vsTeams);
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
			wholeMatches.addLeaguesMap(leaguename, vsTeam);
			
			for(String teamname : vsTeam.getVs()){
				wholeMatches.addTeamname(teamname);
				wholeMatches.addTeamDigest(teamname, leaguename, vsTeam);
			}
		}
		
		return wholeMatches;
	}
	
	/**
	 * 将Vsteams过滤为只有某联赛和某组队的数据
	 * @param leaguename
	 * @param teamname
	 * @param vsTeams
	 * @return
	 */
	public static List<VSTeam> filterVSTeamMatchs(String leaguename, String teamname, List<VSTeam> vsTeams) {
		List<VSTeam> refinedVsTeams = new ArrayList<VSTeam>();
		for(VSTeam vsTeam : vsTeams){
			if(vsTeam.getLeague().equals(leaguename)){
				//此处是为了过滤相同联赛和相同队名的比赛
				for(String tname : vsTeam.getVs()){
					if(tname.equals(teamname)){
						refinedVsTeams.add(vsTeam);
						break;
					}
				}
			}
		}
		return refinedVsTeams;
	}
	
	/**
	 * 判断是否为主场,主场返回0,客场返回1. 不包含这个球队返回-1.
	 * @param teamname
	 * @param vsnames
	 * @return
	 */
	public static int pos(String teamname, String[] vsnames){
		
		for(int i=0;i<vsnames.length;i++){
			if(teamname.equals(vsnames[i])) return i;
		}
		
		return -1;
	}
	
	/**
	 * 得到对手所在位置,配合pos()
	 * @param i
	 * @return
	 */
	public static int oppos(int i){
		return i==1?0:1;
	}
	
}
