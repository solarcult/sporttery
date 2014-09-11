package shil.lottery.sport.score;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import shil.lottery.sport.analyze.AnalyzeScore;
import shil.lottery.sport.entity.ScoreCounter;
import shil.lottery.sport.entity.ScoreStuff;
import shil.lottery.sport.entity.VSTeam;
import shil.lottery.sport.guess.Guess4TeamScores1;
import shil.lottery.sport.strategy.StrategyUtils;

public class GuessScoreLeagueWeight implements Guess4TeamScores1 {

	public static double firstdoor = 0.669d;

	@Override
	public Set<Integer> guess4teamScores(List<VSTeam> vsTeams,VSTeam predictMatch, boolean debug) {

		Set<Integer> scores = new HashSet<Integer>();

		Map<String, ScoreStuff> totals = AnalyzeScore.analyzeLeagueTotalScore(vsTeams);
		ScoreStuff t_scoreStuff = totals.get(predictMatch.getLeague());

		if (t_scoreStuff == null || t_scoreStuff.getScores().size() < GuessScoreVSTeamProbability.minmatch) {
			scores.add(-1);
			return scores;
		}

		Map<Integer, ScoreCounter> bingoTimesMap = new HashMap<Integer, ScoreCounter>();
		for (int i : t_scoreStuff.getScores()) 
		{
			ScoreCounter scoreCounter = bingoTimesMap.get(i);
			if (scoreCounter == null) {
				scoreCounter = new ScoreCounter(i);
				bingoTimesMap.put(i, scoreCounter);
			}
			scoreCounter.increaseBingo();
		}

		List<ScoreCounter> sclist = new ArrayList<ScoreCounter>();
		for (ScoreCounter sc : bingoTimesMap.values()) 
		{
			sclist.add(sc);
		}
		Collections.sort(sclist);

		if (debug) {
			System.out.println("leagues :");
			StrategyUtils.printFirst24Item(sclist);
		}

		if ((double) (sclist.get(0).getCounter() + sclist.get(1).getCounter()) / t_scoreStuff.getScores().size() > firstdoor) 
		{
			scores.add(sclist.get(0).getScore());
			scores.add(sclist.get(1).getScore());
			if (debug)
			{
				System.out.println("leagues所占比例%:"+ (double) (sclist.get(0).getCounter() + sclist.get(1).getCounter())/ t_scoreStuff.getScores().size());
			}
		} else if ((double) (sclist.get(0).getCounter()+ sclist.get(1).getCounter() + sclist.get(2).getCounter())/ t_scoreStuff.getScores().size() > firstdoor) 
		{
			scores.add(sclist.get(0).getScore());
			scores.add(sclist.get(1).getScore());
			scores.add(sclist.get(2).getScore());
			if (debug) 
			{
				System.out.println("leagues所占比例%:"+ (double) (sclist.get(0).getCounter()+ sclist.get(1).getCounter() + sclist.get(2).getCounter())/ t_scoreStuff.getScores().size());
			}
		}

		return scores;
	}
}
