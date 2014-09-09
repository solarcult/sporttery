package shil.lottery.sport.analyze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import shil.lottery.sport.db.SportMetaDaoImpl;
import shil.lottery.sport.entity.VSTeam;

/**
 * 分析不同联赛庄家猜测正确率类
 * @author LiangJingJing
 * @since 2014-07-20 16:12
 */
public class AnalyzeDiffLeagueCorrectPercent {
	
	private static Map<String,Double> hostGuessHistoryMap;
	private static Map<String,Double> peopleGuessHistoryMap;
	
	public static String ALL_VSTEAMS_HostGuessHistoryRate = "all_vsTeams_hostGuessHistoryRate";
	public static String ALL_VSTEAMS_PeopleGuessHistoryRate = "all_vsTeams_peopleGuessHistoryRate";
	
	public static int availableTimes = 10;
	
	public static Map<String,Double> analyzeDiffLeagueHostCorrectPrecent(List<VSTeam> vsTeams)
	{
		Map<String,List<VSTeam>> leagueMap = new HashMap<String, List<VSTeam>>();
		
		for(VSTeam vsTeam : vsTeams)
		{
			String leagueName = vsTeam.getLeague();
			List<VSTeam> leagueList = leagueMap.get(leagueName);
			if(leagueList == null)
			{
				leagueList = new ArrayList<VSTeam>();
				leagueMap.put(leagueName, leagueList);
			}
			
			leagueList.add(vsTeam);
		}
		
		Map<String,Double> hostGuessHistoryMap = new HashMap<String, Double>();
		
		for(Entry<String, List<VSTeam>> entry : leagueMap.entrySet())
		{
//			System.out.println("\n * " + entry.getKey() + " host sample: " + entry.getValue().size());
			if(entry.getValue().size() >= availableTimes)
			{
				//10次以上记录可以当作数据可靠
				hostGuessHistoryMap.put(entry.getKey(),AnalyzeVSTeamsCorrectPercent.analyzeVSTeamsHostCorrectPercent(entry.getValue()));
			}
			else
			{
//				System.out.println("host 10次以下记录,just for display: ");
				AnalyzeVSTeamsCorrectPercent.analyzeVSTeamsHostCorrectPercent(entry.getValue());
			}
		}
		
		//所有记录的情况
		hostGuessHistoryMap.put(ALL_VSTEAMS_HostGuessHistoryRate,AnalyzeVSTeamsCorrectPercent.analyzeVSTeamsHostCorrectPercent(vsTeams));
		
		return hostGuessHistoryMap;
	}
	
	public static Map<String,Double> getAllRecordsLeagueHostGuessRateMap()
	{
		if(hostGuessHistoryMap == null)
		{
			hostGuessHistoryMap = AnalyzeDiffLeagueCorrectPercent.analyzeDiffLeagueHostCorrectPrecent(SportMetaDaoImpl.loadEveryVSTeamRecords());
		}
		return hostGuessHistoryMap;
	}
	
	public static Map<String,Double> analyzeDiffLeaguePeopleCorrectPrecent(List<VSTeam> vsTeams)
	{
		Map<String,List<VSTeam>> leagueMap = new HashMap<String, List<VSTeam>>();
		
		for(VSTeam vsTeam : vsTeams)
		{
			String leagueName = vsTeam.getLeague();
			List<VSTeam> leagueList = leagueMap.get(leagueName);
			if(leagueList == null)
			{
				leagueList = new ArrayList<VSTeam>();
				leagueMap.put(leagueName, leagueList);
			}
			
			leagueList.add(vsTeam);
		}
		
		Map<String,Double> peopleGuessHistoryMap = new HashMap<String, Double>();
		
		for(Entry<String, List<VSTeam>> entry : leagueMap.entrySet())
		{
//			System.out.println("\n * " + entry.getKey() + " people sample: " + entry.getValue().size());
			if(entry.getValue().size() >= availableTimes)
			{
				//10次以上记录可以当作数据可靠
				peopleGuessHistoryMap.put(entry.getKey(),AnalyzeVSTeamsCorrectPercent.analyzeVSTeamsPeopleVoteCorrectPercent(entry.getValue()));
			}
			else
			{
//				System.out.println("people 10次以下记录,just for display: ");
				AnalyzeVSTeamsCorrectPercent.analyzeVSTeamsPeopleVoteCorrectPercent(entry.getValue());
			}
		}
		
		//所有记录的情况
		peopleGuessHistoryMap.put(ALL_VSTEAMS_PeopleGuessHistoryRate,AnalyzeVSTeamsCorrectPercent.analyzeVSTeamsPeopleVoteCorrectPercent(vsTeams));
		
		return peopleGuessHistoryMap;
	}
	
	public static Map<String,Double> getAllRecordsLeaguePeopleGuessRateMap()
	{
		if(peopleGuessHistoryMap == null)
		{
			peopleGuessHistoryMap = AnalyzeDiffLeagueCorrectPercent.analyzeDiffLeaguePeopleCorrectPrecent(SportMetaDaoImpl.loadEveryVSTeamRecords());
		}
		return peopleGuessHistoryMap;
	}
	
	public static void main(String[] args)
	{
		analyzeDiffLeagueHostCorrectPrecent(SportMetaDaoImpl.loadEveryVSTeamRecords());
	}

}
