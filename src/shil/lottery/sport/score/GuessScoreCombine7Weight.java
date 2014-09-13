package shil.lottery.sport.score;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import shil.lottery.sport.entity.VSTeam;
import shil.lottery.sport.guess.Guess4TeamScores1;

public class GuessScoreCombine7Weight implements Guess4TeamScores1 {

	@Override
	public Set<Integer> guess4teamScores(List<VSTeam> vsTeams,VSTeam predictMatch, boolean debug) {
		
		Set<Integer> result = new HashSet<Integer>();
		
		Guess4TeamScores1 weight = new GuessScoreVSTeamWeight();
		Set<Integer> w = weight.guess4teamScores(vsTeams, predictMatch, debug);
		Guess4TeamScores1 combine = new GuessScoreCombineAdvantage();
		Set<Integer> c = combine.guess4teamScores(vsTeams, predictMatch, debug);
		
		for(int i : w)
		{
			if(c.contains(i)) result.add(i);
		}
		
		return result;
	}

}
