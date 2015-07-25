package shil.lottery.seriously.research;

import java.util.List;
import java.util.Set;

import shil.lottery.sport.entity.VSTeam;

public interface GuessScores {
	Set<Integer> guessScores(List<VSTeam> vsTeams,VSTeam vsTeam);
}
 