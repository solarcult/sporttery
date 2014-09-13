package shil.lottery.sport.score;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import shil.lottery.sport.entity.VSTeam;
import shil.lottery.sport.guess.Guess4TeamScores1;

/**
 * 先统计出来每个算法哪个位置的数据比较优,再进行合并优势.
 * weight的第1位
 * nodoor的第2位
 * orignal的第3位
 * @author LiangJingJing
 * @since 20140913 10:40
 */
public class GuessScoreCombineAdvantage implements Guess4TeamScores1 {

	@Override
	public Set<Integer> guess4teamScores(List<VSTeam> vsTeams,VSTeam predictMatch, boolean debug) 
	{
		Set<Integer> result = new HashSet<Integer>();
		
		SpeicalPostionScore weight = new GuessScoreVSTeamWeight();
		int w = weight.getSpeicalPostionScore(vsTeams, predictMatch, debug, 0);
		
		SpeicalPostionScore nodoor = new GuessScoreVSTeamWeightNoFirstdoor();
		int n = nodoor.getSpeicalPostionScore(vsTeams, predictMatch, debug, 1);
		
		SpeicalPostionScore orignal = new GuessScoreVSTeamProbability();
		int o = orignal.getSpeicalPostionScore(vsTeams, predictMatch, debug, 2);
		
		int agree = 0;
		if(n!=-1) 
		{
			agree++;
			result.add(n);
		}
		if(w!=-1) 
		{
			agree++;
			result.add(w);
		}
		if(o!=-1) 
		{
			agree++;
			result.add(o);
		}
		
		if(agree<2) return Collections.emptySet();
		
		return result;
	}

}
