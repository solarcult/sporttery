package shil.lottery.sport.guess;

import java.util.List;

import shil.lottery.sport.entity.VSTeam;

public class GuessFour implements Guess4TeamMatchResult3 {

	@Override
	public int guess4teamMatchResult(List<VSTeam> vsTeams, VSTeam predictMatch) {
		
		Guess4TeamMatchResult2 guess2 = new GuessTwo();
		Guess4TeamMatchResult3 guess3 = new GuessThree();
//		GuessTwo.door = 0.4d;
//		GuessThree.door = 0.1d;
		
		int g2 = guess2.guess4teamMatchResult(predictMatch).getMatch_Result();
		int g3 = guess3.guess4teamMatchResult(vsTeams,predictMatch);
		
		if(g2==g3) return g2;
		
		return -5;
	}

}
