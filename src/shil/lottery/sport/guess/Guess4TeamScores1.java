package shil.lottery.sport.guess;

import java.util.List;
import java.util.Set;

import shil.lottery.sport.entity.VSTeam;

public interface Guess4TeamScores1 {
	
	public Set<Integer> guess4teamScores(List<VSTeam> vsTeams,VSTeam predictMatch, boolean debug);

}
