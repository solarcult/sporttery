package shil.lottery.sport;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import shil.lottery.sport.analyze.AnalyzeDiffLeagueCorrectPercent;
import shil.lottery.sport.domain.VSTeam;
import shil.lottery.sport.excel.LoadExcelData2VSTeams;
import shil.lottery.sport.strategy.PursuitOfLottery3;
import shil.lottery.sport.strategy.StrategySix;
import shil.lottery.sport.strategy.StrategyThree;
import shil.lottery.sport.strategy.StrategyUtils;

/**
 * World Champion Project 预测类
 * @author LiangJingJing
 * @since before 2014-07-20
 */
public class WCP {
	
	public static void main(String[] args)
	{
		
		List<VSTeam> vsTeams = LoadExcelData2VSTeams.justDoIt(new File("d:\\abc.xls"));
		
//		Collections.sort(vsTeams);
//		StrategyUtils.printFirst25Item(vsTeams);
		
		PursuitOfLottery3 three = new StrategyThree();
		System.out.println("v3 This is 2 teams shooter:");
		StrategyUtils.printFirst24Item(three.groupAanalyze2Teams(vsTeams));
		System.out.println("v3 This is 3 teams shooter:");
		StrategyUtils.printFirst24Item(three.groupAanalyze3Teams(vsTeams));
		System.out.println("v3 This is 4 teams shooter:");
		StrategyUtils.printFirst24Item(three.groupAanalyze4Teams(vsTeams));
		System.out.println("v3 This is 5 teams shooter:");
		StrategyUtils.printFirst24Item(three.groupAanalyze5Teams(vsTeams));
		System.out.println("v3 This is all teams shooter.");
		StrategyUtils.printFirst24Item(three.shooter(vsTeams));
		
		PursuitOfLottery3 six = new StrategySix();
		System.out.println("v6 This is 2 teams shooter:");
		StrategyUtils.printFirst24Item(six.groupAanalyze2Teams(vsTeams));
		System.out.println("v6 This is 3 teams shooter:");
		StrategyUtils.printFirst24Item(six.groupAanalyze3Teams(vsTeams));
		System.out.println("v6 This is 4 teams shooter:");
		StrategyUtils.printFirst24Item(six.groupAanalyze4Teams(vsTeams));
		System.out.println("v6 This is 5 teams shooter:");
		StrategyUtils.printFirst24Item(six.groupAanalyze5Teams(vsTeams));
		System.out.println("v6 This is all teams shooter:");
		StrategyUtils.printFirst24Item(six.shooter(vsTeams));
		
		
		Set<String> sets = new HashSet<String>();
		for(VSTeam v: vsTeams)
		{
			sets.add(v.getLeague());
			System.out.println(v);
		}
		
		for(String s: sets)
		{
			System.out.println(s+ " host: " + AnalyzeDiffLeagueCorrectPercent.getAllRecordsLeagueHostGuessRateMap().get(s));
			System.out.println(s+ " people: " +AnalyzeDiffLeagueCorrectPercent.getAllRecordsLeaguePeopleGuessRateMap().get(s));
		}
		
		System.out.println("all host: " + AnalyzeDiffLeagueCorrectPercent.getAllRecordsLeagueHostGuessRateMap().get(AnalyzeDiffLeagueCorrectPercent.ALL_VSTEAMS_HostGuessHistoryRate));
		System.out.println("all people: " +AnalyzeDiffLeagueCorrectPercent.getAllRecordsLeaguePeopleGuessRateMap().get(AnalyzeDiffLeagueCorrectPercent.ALL_VSTEAMS_PeopleGuessHistoryRate));
	}

}
