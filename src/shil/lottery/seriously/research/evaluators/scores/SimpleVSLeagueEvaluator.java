package shil.lottery.seriously.research.evaluators.scores;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import shil.lottery.sport.entity.VSTeam;

public class SimpleVSLeagueEvaluator extends AbstractScoreEvaluator {

	@Override
	public Set<Integer> guessScores(List<VSTeam> vsTeams, VSTeam vsTeam) {
		Set<Integer> ss = new SimpleTop2Evaluator().guessScores(vsTeams, vsTeam);
		Set<Integer> ls = new TestLeagueTop2Evaluator().guessScores(vsTeams, vsTeam);
		
		Set<Integer> result = new HashSet<Integer>();
		for(int one : ls){
			if(ss.contains(one)) result.add(one);
		}
		
		if(result.size()<2) result.clear();
		
		return result;
	}

	public static void main(String[] args){
		new SimpleVSLeagueEvaluator().startEvaluator();
	}
}
