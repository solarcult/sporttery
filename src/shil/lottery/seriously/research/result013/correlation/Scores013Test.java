package shil.lottery.seriously.research.result013.correlation;

import java.util.List;

import shil.lottery.seriously.utils.AnalyzeUtil;
import shil.lottery.seriously.vo.LeaguePosition;
import shil.lottery.seriously.vo.ScoreStatistics;
import shil.lottery.seriously.vo.TeamValuePosition;
import shil.lottery.seriously.vo.WholeMatches;
import shil.lottery.sport.db.SportMetaDaoImpl;
import shil.lottery.sport.entity.VSTeam;

public class Scores013Test {
	
	public static void main(String[] args){
		
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		
		for(int i=0;i<vsTeams.size();i++){
			VSTeam vsTeam = vsTeams.get(i);
			WholeMatches wholeMatches = WholeMatches.analyzeWholeMatches(vsTeams.subList(0, i));

			String teamA = vsTeam.getVs()[0];
			String teamB = vsTeam.getVs()[1];
			
			List<VSTeam> teamAs = wholeMatches.getTeamDigest().get(teamA).get(vsTeam.getLeague());
			List<VSTeam> teamBs = wholeMatches.getTeamDigest().get(teamB).get(vsTeam.getLeague());
			if((teamAs.size() < AnalyzeUtil.leagalMinMatches)||(teamBs.size()<AnalyzeUtil.leagalMinMatches)){
				continue;
			}
			
			int al = (teamAs.size() > AnalyzeUtil.leagalMaxMatches)? AnalyzeUtil.leagalMaxMatches : teamAs.size();
			int bl = (teamBs.size() > AnalyzeUtil.leagalMaxMatches)? AnalyzeUtil.leagalMaxMatches : teamBs.size();
			ScoreStatistics as = ScoreStatistics.analyzeVSTeams2scoreStatistics(vsTeam.getLeague(), teamA, teamAs.subList(teamAs.size()-al, teamAs.size()));
			ScoreStatistics bs = ScoreStatistics.analyzeVSTeams2scoreStatistics(vsTeam.getLeague(), teamB, teamBs.subList(teamAs.size()-bl, teamAs.size()));
			
			double gsd  = as.getGoalStatistics().getStandardDeviation() - bs.getGoalStatistics().getStandardDeviation();
			double lsd = as.getLostStatistics().getStandardDeviation() - bs.getLostStatistics().getStandardDeviation();
			
			double agbl = as.getGoalStatistics().getStandardDeviation() - bs.getLostStatistics().getStandardDeviation();
			double albg = as.getLostStatistics().getStandardDeviation() - bs.getGoalStatistics().getStandardDeviation();
			
			
			/*
			LeaguePosition leaguePosition = LeaguePosition.analyzeOneLeague(vsTeam.getLeague(), wholeMatches);
			if(leaguePosition==null) continue;
			//联赛中排序的队伍比例必须超过这个值,才算有效
			if(!leaguePosition.isBelievable()) continue;
			
			TeamValuePosition teamValuePositionA = leaguePosition.getTeamValuePosition(vsTeam.getVs()[0]);
			TeamValuePosition teamValuePositionB = leaguePosition.getTeamValuePosition(vsTeam.getVs()[1]);
			if(teamValuePositionA == null || teamValuePositionB == null) continue;
			if(vsTeam.getMatch_Result()==AnalyzeUtil.win){
			}else if(vsTeam.getMatch_Result() == AnalyzeUtil.draw){
			}else{
			}
			*/
		}
	}

}
