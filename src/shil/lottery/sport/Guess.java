package shil.lottery.sport;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import shil.lottery.sport.analyze.AnalyzeDiffLeagueCorrectPercent;
import shil.lottery.sport.analyze.AnalyzeTeamMatchResult;
import shil.lottery.sport.analyze.TeamMatchResult;
import shil.lottery.sport.db.SportMetaDaoImpl;
import shil.lottery.sport.entity.VSTeam;
import shil.lottery.sport.excel.LoadExcelData2VSTeams;
import shil.lottery.sport.guess.Guess4TeamMatchResult2;
import shil.lottery.sport.guess.Guess4TeamMatchResult3;
import shil.lottery.sport.guess.Guess4TeamScores1;
import shil.lottery.sport.guess.GuessFour;
import shil.lottery.sport.guess.GuessThree;
import shil.lottery.sport.guess.GuessTwo;
import shil.lottery.sport.guess.GuessUtils;
import shil.lottery.sport.score.GuessScoreLeagueProbability;
import shil.lottery.sport.score.GuessScoreVSTeamWeight;
import shil.lottery.sport.score.GuessScoreVSTeamWeightNoFirstdoor;
import shil.lottery.sport.score.GuessScoreWeight7NoDoor;
import shil.lottery.sport.score.diff.AdvancedMilkyWay;


public class Guess {

	public static void main(String[] args) {
		
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		
		List<VSTeam> guessTeams = LoadExcelData2VSTeams.justDoIt(new File("d:\\abc.xls"),false);
		
		Guess4TeamMatchResult2 result = new GuessTwo();
		Guess4TeamMatchResult3 result3 = new GuessThree();
		Guess4TeamMatchResult3 result4 = new GuessFour();
		Guess4TeamScores1 score = new GuessScoreLeagueProbability();
		Guess4TeamScores1 weightscore = new GuessScoreVSTeamWeight();
		Guess4TeamScores1 nodoorscore = new GuessScoreVSTeamWeightNoFirstdoor();
		Guess4TeamScores1 w7n = new GuessScoreWeight7NoDoor();
		
		String guesstworate =  Evaluator.evaluatorGuessTwo();
		String guessthreerate = Evaluator.evaluatorGuessThree();
		String guessfourrate = Evaluator.evaluatorGuessFour();
		
		Map<String, TeamMatchResult> tmrs = AnalyzeTeamMatchResult.analyzeTeamMatchResult(vsTeams);
		for(VSTeam vs : guessTeams)
		{
//			if(vs.getWeek().equals("周四")
//					&&vs.getChangci()==28
//					)
//			{
//				System.out.println("xx");
//			}
//			else
//				continue;
			
			System.out.println("\n* * *");
			System.out.println(vs.getLeague() +"  "+vs.getWeek()+" : "+ vs.getChangci());
			System.out.println(vs.getVs()[0] +" vs " +vs.getVs()[1]);
			
			int rr = result.guess4teamMatchResult(vs).getMatch_Result();
			if(rr>=0)
			{
				System.out.println("~2~~result is :  " + rr);
				System.out.println(guesstworate);
				System.out.println("~3~~result is :  " + result3.guess4teamMatchResult(vsTeams, vs));
				System.out.println(guessthreerate);
				System.out.println("~4~~result is :  " + result4.guess4teamMatchResult(vsTeams, vs));
				System.out.println(guessfourrate);
			}
			System.out.println("host success: "+AnalyzeDiffLeagueCorrectPercent.getAllRecordsLeagueHostGuessRateMap().get(vs.getLeague()));
			System.out.println("people success: "+AnalyzeDiffLeagueCorrectPercent.getAllRecordsLeaguePeopleGuessRateMap().get(vs.getLeague()));
			System.out.println("a: "+tmrs.get(vs.getVs()[0]));
			System.out.println("b: "+tmrs.get(vs.getVs()[1]));
			System.out.println();
			Set<Integer> scores = score.guess4teamScores(vsTeams, vs, false);
			boolean isleague = GuessUtils.isGuessScoreLegal(scores);
			if(isleague)
			{
				score.guess4teamScores(vsTeams, vs, true);
				System.out.println("league avg score is :,  " +scores);
			}
			
			Set<Integer> wscores = weightscore.guess4teamScores(vsTeams, vs, false);
			boolean isweight = GuessUtils.isGuessScoreLegal(wscores);
			if(isweight)
			{
				System.out.println("weight team scores: :)");
				System.out.println("weight finally : "+ weightscore.guess4teamScores(vsTeams, vs, true));
			}
			
			{
				System.out.println("nodoor team scores: ;)");
				System.out.println("finally nodoor : "+ nodoorscore.guess4teamScores(vsTeams, vs, true));
			}
			
			Set<Integer> w7ns = w7n.guess4teamScores(vsTeams, vs, false);
			boolean isw7ns = GuessUtils.isGuessScoreLegal(w7ns);
			if(isw7ns)
			{
				System.out.println();
				System.out.println("~7777777777777777777777~");
				System.out.println("7weight 7 nodoor team scores: :)");
				System.out.println("7weight 7 nodoor7 finally 7weight 7 nodoor7: "+ w7ns);
				System.out.println("~7777777777777777777777~");
			}
			
			System.out.println();
			System.out.println("Milky Way is :] ");
			AdvancedMilkyWay.showPredictScoreDiffStatus(vsTeams, vs);
			System.out.println();
			System.out.println("联赛的所有 diff status: ");
			AdvancedMilkyWay.showLeagueScoreDiffStatus(vsTeams, vs.getLeague());
			
		}
	}

}
