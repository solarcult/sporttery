package shil.lottery.sport.score;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import shil.lottery.sport.entity.VSTeam;
import shil.lottery.sport.guess.Guess4TeamScores1;

public class GuessScoreWeight7NoDoor implements Guess4TeamScores1 {

	@Override
	public Set<Integer> guess4teamScores(List<VSTeam> vsTeams,VSTeam predictMatch, boolean debug) {
		
		Guess4TeamScores1 weight = new GuessScoreVSTeamWeight();
		Guess4TeamScores1 nodoor = new GuessScoreVSTeamWeightNoFirstdoor();
		
		Set<Integer> w = weight.guess4teamScores(vsTeams, predictMatch, debug);
		Set<Integer> n = nodoor.guess4teamScores(vsTeams, predictMatch, debug);
		
		/*
		 * 第一个肯能为空,第二个一定不为空,这种方式没有进步
		if(!w.isEmpty() && !n.isEmpty())
			w.addAll(n);
		
		return w;
		*/
		
		//只有两个预测一致才算数,说明还是按weight的来,胜率没有提高多少.
		if(w.containsAll(n))
			return w;
		else
			return Collections.emptySet();
	}

}
