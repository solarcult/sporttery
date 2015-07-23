package shil.lottery.seriously.research.evaluators.scores;

import java.util.ArrayList;
import java.util.List;

import shil.lottery.seriously.vo.ScoreStatistics;
import shil.lottery.sport.entity.VSTeam;

public class SimpleTop2Evaluator extends AbstractScoreEvaluator {

	@Override
	public List<Integer> guessScores(List<VSTeam> vsTeams, VSTeam vsTeam) {
		List<Integer> scores = new ArrayList<Integer>();
		
//		ScoreStatistics ss = ScoreStatistics.analyzeVSTeams2scoreStatistics(leaguename, teamname, vsTeams);
		
		return scores;
	}

}
