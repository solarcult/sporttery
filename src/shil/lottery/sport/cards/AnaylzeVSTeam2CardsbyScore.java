package shil.lottery.sport.cards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import shil.lottery.sport.analyze.AnalyzeScore;
import shil.lottery.sport.db.SportMetaDaoImpl;
import shil.lottery.sport.entity.ScoreStuff;
import shil.lottery.sport.entity.VSTeam;
import shil.lottery.sport.statistics.NumberUtils;
import shil.lottery.sport.statistics.StatisticUtils;

/**
 * 把每个联赛的队伍转换成卡片游戏中的卡片,进行对战
 * @author ljj
 * @since 2014-08-02 22:08
 */
public class AnaylzeVSTeam2CardsbyScore {
	
	public static Map<String,VSTeamCard> anaylzeLeagueCards(List<VSTeam> vsTeams)
	{
		Map<String,VSTeamCard> cards = new HashMap<String, VSTeamCard>();
		//得到所有联队名称
		Map<String,Set<String>> lnmap = AnalyzeScore.getLeagueTeamsName(vsTeams);
		//根据联队,得到每个队的进球数,最近10场
		Map<String,ScoreStuff> wmap = AnalyzeScore.analyzeTeamWinScore(vsTeams);
		//同上,得到每个队的失球数
		Map<String,ScoreStuff> lmap = AnalyzeScore.analyzeTeamLoseScore(vsTeams);
		
		for(Entry<String,Set<String>> e : lnmap.entrySet())
		{
			List<Double> leagueTeamWinScoreWightAverages = new ArrayList<Double>();
			List<Double> leagueTeamLoseScoreWightAverages = new ArrayList<Double>();
			
			for(String name : e.getValue())
			{
				//得到输赢的队伍进球信息
				ScoreStuff ws = wmap.get(name);
				if(ws==null) continue;
				ScoreStuff ls = lmap.get(name);
				double wag = ws.getWeightAverage();
				//达不到5个则忽略
				if(wag == -5)
				{
//					System.out.println("team: "+name+" ,not enough data: "+ ws.getScores().size());
					continue;
				}
				leagueTeamWinScoreWightAverages.add(wag);
				leagueTeamLoseScoreWightAverages.add(ls.getWeightAverage());
			}
			
			double[] wswg = NumberUtils.convertListDs2doubles(leagueTeamWinScoreWightAverages);
			double[] lswg = NumberUtils.convertListDs2doubles(leagueTeamLoseScoreWightAverages);
			
			double wavg = StatisticUtils.arithmeticMean(wswg);
			double lavg = StatisticUtils.arithmeticMean(lswg);
			double wsd = StatisticUtils.standardDeviation(wswg);
			double lsd = StatisticUtils.standardDeviation(lswg);
			
			for(String name : e.getValue())
			{
				ScoreStuff ws = wmap.get(name);
				if(ws==null) continue;
				ScoreStuff ls = lmap.get(name);
				double wag = ws.getWeightAverage();
				if(wag == -5) 
				{
//					System.out.println("can't make team card: "+name+" ,not enough data: "+ ws.getScores().size());
					continue;
				}
				
				double attack = (ws.getWeightAverage() - wavg)/wsd;
				double defense = (ls.getWeightAverage() - lavg)/lsd;
				VSTeamCard n = new VSTeamCard(e.getKey(),name,attack,defense);
				VSTeamCard t = cards.put(makeCardKey(e.getKey(),name), n);
				if(t!=null)
				{
					throw new RuntimeException("``````````````````````why u r in ? "+ t);
				}
				
//				System.out.println(n);
			}
		}
		
		return cards;
	}
	
	public static VSTeamCard[] convertVSTeam2Card(List<VSTeam> vsTeams,VSTeam predictMatch)
	{
		VSTeamCard[] cards = new VSTeamCard[2]; 
		Map<String,VSTeamCard> maps = anaylzeLeagueCards(vsTeams);
		cards[0] = maps.get(makeCardKey(predictMatch.getLeague(), predictMatch.getVs()[0]));
		cards[1] = maps.get(makeCardKey(predictMatch.getLeague(), predictMatch.getVs()[1]));
		return cards;
	}
	
	public static String makeCardKey(String league,String teamname)
	{
		return league+":"+teamname;
	}
	
	public static void main(String[] args)
	{
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		AnaylzeVSTeam2CardsbyScore.anaylzeLeagueCards(vsTeams);
	}
	
}
