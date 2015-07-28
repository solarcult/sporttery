package shil.lottery.seriously.research.evaluators.scores;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import shil.lottery.seriously.research.league.LeagueUtil;
import shil.lottery.seriously.vo.ScoreStatistics;
import shil.lottery.seriously.vo.WholeMatches;
import shil.lottery.sport.entity.VSTeam;

public class SimpleTop2Evaluator extends AbstractScoreEvaluator {

	@Override
	public Set<Integer> guessScores(List<VSTeam> vsTeams, VSTeam vsTeam) {
		Set<Integer> predictScores = new HashSet<Integer>();
		WholeMatches wholeMatches = WholeMatches.analyzeWholeMatches(vsTeams);
		Map<String,List<VSTeam>> refineMatches = LeagueUtil.refineLeagueTeamMatches(vsTeam.getLeague(), wholeMatches);
		
		List<VSTeam> as = refineMatches.get(vsTeam.getVs()[0]);
		List<VSTeam> bs = refineMatches.get(vsTeam.getVs()[1]);
		if(as==null || bs==null) return predictScores;
		ScoreStatistics teamAss = ScoreStatistics.analyzeVSTeams2scoreStatistics(vsTeam.getLeague(), vsTeam.getVs()[0], refineMatches.get(vsTeam.getVs()[0]));
		ScoreStatistics teamBss = ScoreStatistics.analyzeVSTeams2scoreStatistics(vsTeam.getLeague(), vsTeam.getVs()[1], refineMatches.get(vsTeam.getVs()[1]));

		//分主客场比赛进球数
//		int gg = (int) Math.round(teamAss.getHostScoreStatistics().getGoalStatistics().getMean() + teamBss.getGuestScoreStatistics().getGoalStatistics().getMean());
//		int gl = (int) Math.round(teamAss.getHostScoreStatistics().getGoalStatistics().getMean() + teamBss.getGuestScoreStatistics().getLostStatistics().getMean());
//		int lg = (int) Math.round(teamAss.getHostScoreStatistics().getLostStatistics().getMean() + teamBss.getGuestScoreStatistics().getGoalStatistics().getMean());
//		int ll = (int) Math.round(teamAss.getHostScoreStatistics().getLostStatistics().getMean() + teamBss.getGuestScoreStatistics().getLostStatistics().getMean());
		
		//不分主客场进球数
		int gg = (int) Math.round(teamAss.getGoalStatistics().getMean() + teamBss.getGoalStatistics().getMean());
		int gl = (int) Math.round(teamAss.getGoalStatistics().getMean() + teamBss.getLostStatistics().getMean());
		int lg = (int) Math.round(teamAss.getLostStatistics().getMean() + teamBss.getGoalStatistics().getMean());
		int ll = (int) Math.round(teamAss.getLostStatistics().getMean() + teamBss.getLostStatistics().getMean());
		
		predictScores.add(gg);
		predictScores.add(gl);
		predictScores.add(lg);
		predictScores.add(ll);
		
		if(predictScores.size() > 2) predictScores.clear();
		
		return predictScores;
	}

	public static void main(String[] args){
		new SimpleTop2Evaluator().startEvaluator();
	}
}
