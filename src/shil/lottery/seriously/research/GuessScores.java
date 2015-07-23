package shil.lottery.seriously.research;

import java.util.List;

import shil.lottery.sport.entity.VSTeam;

public interface GuessScores {
	List<Integer> guessScores(List<VSTeam> vsTeams,VSTeam vsTeam);
}
 