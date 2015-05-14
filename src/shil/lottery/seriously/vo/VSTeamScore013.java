package shil.lottery.seriously.vo;

import java.util.List;

import shil.lottery.seriously.utils.AnalyzeUtil;
import shil.lottery.seriously.utils.AnalyzeUtil.AVG_TP;
import shil.lottery.sport.entity.VSTeam;

/**
 * 记录某场比赛分析后的4个关键结果,先判断是否可用.
 * @author LiangJingJing
 * @date May 14, 2015 11:37:36 PM
 */
public class VSTeamScore013 {
	
	private boolean isAvaliable;
	private AVG_TP avgTp;
	private double gsd;
	private double lsd;
	private double agbl;
	private double albg;
	
	private VSTeamScore013(AVG_TP avg_tp){
		this.avgTp = avg_tp;
	}

	public boolean isAvaliable() {
		return isAvaliable;
	}

	public AVG_TP getAvgTp() {
		return avgTp;
	}

	public double getGsd() {
		return gsd;
	}

	public double getLsd() {
		return lsd;
	}

	public double getAgbl() {
		return agbl;
	}

	public double getAlbg() {
		return albg;
	}
	
	public static VSTeamScore013 calculateVSTeamScore013(List<VSTeam> vsTeams,VSTeam vsTeam){
		return calculateVSTeamScore013(vsTeams,vsTeam,AVG_TP.PopulationVariance);
	}

	public static VSTeamScore013 calculateVSTeamScore013(List<VSTeam> vsTeams,VSTeam vsTeam,AVG_TP avg_tp){
		VSTeamScore013 vsTeamScore013 = new VSTeamScore013(avg_tp);
		
		WholeMatches wholeMatches = WholeMatches.analyzeWholeMatches(vsTeams);

		String teamA = vsTeam.getVs()[0];
		String teamB = vsTeam.getVs()[1];
		
		List<VSTeam> teamAs = (wholeMatches.getTeamDigest().get(teamA)!=null)? wholeMatches.getTeamDigest().get(teamA).get(vsTeam.getLeague()) : null;
		List<VSTeam> teamBs = wholeMatches.getTeamDigest().get(teamB)!=null ? wholeMatches.getTeamDigest().get(teamB).get(vsTeam.getLeague()) : null;
		if(teamAs == null || teamBs == null || (teamAs.size() < AnalyzeUtil.leagalMinMatches)||(teamBs.size()<AnalyzeUtil.leagalMinMatches)){
			//确保比赛取值场次在5次到9次之间
			vsTeamScore013.isAvaliable = false;
		}else{
			vsTeamScore013.isAvaliable = true;
			//得到最近的几个场次比赛数据
			int al = (teamAs.size() > AnalyzeUtil.leagalMaxMatches)? AnalyzeUtil.leagalMaxMatches : teamAs.size();
			int bl = (teamBs.size() > AnalyzeUtil.leagalMaxMatches)? AnalyzeUtil.leagalMaxMatches : teamBs.size();
			ScoreStatistics as = ScoreStatistics.analyzeVSTeams2scoreStatistics(vsTeam.getLeague(), teamA, teamAs.subList(teamAs.size()-al, teamAs.size()));
			ScoreStatistics bs = ScoreStatistics.analyzeVSTeams2scoreStatistics(vsTeam.getLeague(), teamB, teamBs.subList(teamBs.size()-bl, teamBs.size()));
			
			//分析,应该根据不同的数值,SD,Mean,GMean,等,写函数,传值取不同的结果,传入Statistics
			double gsd  = AnalyzeUtil.getAVG(as.getGoalStatistics(), avg_tp) - AnalyzeUtil.getAVG(bs.getGoalStatistics(),avg_tp);
			double lsd = AnalyzeUtil.getAVG(as.getLostStatistics(), avg_tp) - AnalyzeUtil.getAVG(bs.getLostStatistics(),avg_tp);
			double agbl = AnalyzeUtil.getAVG(as.getGoalStatistics(), avg_tp) - AnalyzeUtil.getAVG(bs.getLostStatistics(),avg_tp); 
			double albg = AnalyzeUtil.getAVG(as.getLostStatistics(), avg_tp) - AnalyzeUtil.getAVG(bs.getGoalStatistics(),avg_tp);
			vsTeamScore013.gsd = gsd;
			vsTeamScore013.lsd = lsd;
			vsTeamScore013.agbl = agbl;
			vsTeamScore013.albg = albg;
		}
		return vsTeamScore013;
	}
}
