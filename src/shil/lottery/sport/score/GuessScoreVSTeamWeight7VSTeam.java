package shil.lottery.sport.score;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import shil.lottery.sport.entity.VSTeam;
import shil.lottery.sport.guess.Guess4TeamScores1;

/**
 * 67.8胜率
 * @author LiangJingJing
 *
 */
public class GuessScoreVSTeamWeight7VSTeam implements Guess4TeamScores1 
{

	@Override
	public Set<Integer> guess4teamScores(List<VSTeam> vsTeams,VSTeam predictMatch, boolean debug) 
	{
		Guess4TeamScores1 weight = new GuessScoreVSTeamWeight();
		Set<Integer> xscores = weight.guess4teamScores(vsTeams, predictMatch, debug);
		
		Set<Integer> fscores = new HashSet<Integer>();
		Guess4TeamScores1 vscore = new GuessScoreVSTeamProbability();
		Set<Integer> vsresult = vscore.guess4teamScores(vsTeams,predictMatch, debug);
		
		for(int i: xscores)
		{
			if(vsresult.contains(i)) fscores.add(i);
		}
		
		return fscores;
	}

}
