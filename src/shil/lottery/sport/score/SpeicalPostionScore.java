package shil.lottery.sport.score;

import java.util.List;

import shil.lottery.sport.entity.VSTeam;

public interface SpeicalPostionScore {

	public int getSpeicalPostionScore(List<VSTeam> vsTeams,VSTeam predictMatch, boolean debug,int postion);
}
