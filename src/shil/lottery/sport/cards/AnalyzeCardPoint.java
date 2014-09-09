package shil.lottery.sport.cards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shil.lottery.sport.entity.VSTeam;
import shil.lottery.sport.statistics.StatisticUtils;

/**
 * 分析卡片位置的类
 * @author ljj
 * @since 2014-08-08 0:26
 */
public class AnalyzeCardPoint {
	
	public static String ALLINONE = "allinone";
	public static double radius = 0.85d;

	public static Map<String,List<CardPoint>> analyzeEveryPointSituation(List<VSTeam> vsTeams)
	{
		Map<String,List<CardPoint>> cardPointsMap = new HashMap<String, List<CardPoint>>();
		List<CardPoint> waldw = new ArrayList<CardPoint>();
		cardPointsMap.put(AnD.wattack_ldefense.name()+MatchResultEnum.win.name(), waldw);
		List<CardPoint> walaw = new ArrayList<CardPoint>();
		cardPointsMap.put(AnD.wattack_lattck.name()+ MatchResultEnum.win.name(), walaw);
		List<CardPoint> aaldd1 = new ArrayList<CardPoint>();
		cardPointsMap.put(AnD.wattack_ldefense.name()+MatchResultEnum.draw1.name(), aaldd1);
		List<CardPoint> aaldd2 = new ArrayList<CardPoint>();
		cardPointsMap.put(AnD.wattack_ldefense.name()+MatchResultEnum.draw2.name(), aaldd2);
		List<CardPoint> aalad1 = new ArrayList<CardPoint>();
		cardPointsMap.put(AnD.wattack_lattck.name()+MatchResultEnum.draw1.name(), aalad1);
		List<CardPoint> aalad2 = new ArrayList<CardPoint>();
		cardPointsMap.put(AnD.wattack_lattck.name()+MatchResultEnum.draw2.name(), aalad2);
		List<CardPoint> lawdl = new ArrayList<CardPoint>();
		cardPointsMap.put(AnD.wattack_ldefense.name()+MatchResultEnum.lose.name(), lawdl);
		List<CardPoint> lawal = new ArrayList<CardPoint>();
		cardPointsMap.put(AnD.wattack_lattck.name()+ MatchResultEnum.lose.name(), lawal);
		
		List<CardPoint> wholelist = new ArrayList<CardPoint>();
		cardPointsMap.put(ALLINONE, wholelist);
		
		Map<String,VSTeamCard> cards = AnaylzeVSTeam2CardsbyScore.anaylzeLeagueCards(vsTeams);
		for(VSTeam vs : vsTeams)
		{
			VSTeamCard winCard = null;
			VSTeamCard loseCard = null;
			VSTeamCard daCard = null;
			VSTeamCard dbCard = null;
			
			if(vs.getMatch_Result() == 3)
			{
				winCard = cards.get(AnaylzeVSTeam2CardsbyScore.makeCardKey(vs.getLeague(), vs.getVs()[0]));
				loseCard = cards.get(AnaylzeVSTeam2CardsbyScore.makeCardKey(vs.getLeague(), vs.getVs()[1]));
			}
			else if(vs.getMatch_Result() == 0)
			{
				winCard = cards.get(AnaylzeVSTeam2CardsbyScore.makeCardKey(vs.getLeague(), vs.getVs()[1]));
				loseCard = cards.get(AnaylzeVSTeam2CardsbyScore.makeCardKey(vs.getLeague(), vs.getVs()[0]));
			}
			else
			{
				daCard = cards.get(AnaylzeVSTeam2CardsbyScore.makeCardKey(vs.getLeague(), vs.getVs()[0]));
				dbCard = cards.get(AnaylzeVSTeam2CardsbyScore.makeCardKey(vs.getLeague(), vs.getVs()[1])); 
			}
			
			if(winCard != null && loseCard != null)
			{
				double a = winCard.getAttack() - loseCard.getDefense();
				double b = loseCard.getAttack() - winCard.getDefense();
				CardPoint ab = new CardPoint(a, b, AnD.wattack_ldefense, MatchResultEnum.win);
				waldw.add(ab);
				wholelist.add(ab);
				double c = winCard.getAttack() - loseCard.getAttack();
				double d = winCard.getDefense() - loseCard.getDefense();
				CardPoint cd = new CardPoint(c, d, AnD.wattack_lattck, MatchResultEnum.win);
				walaw.add(cd);
				wholelist.add(cd);
				
				CardPoint ba = new CardPoint(b, a, AnD.wattack_ldefense, MatchResultEnum.lose);
				lawdl.add(ba);
				wholelist.add(ba);
				CardPoint dc = new CardPoint(b, a, AnD.wattack_ldefense, MatchResultEnum.lose);
				lawal.add(dc);
				wholelist.add(dc);
			}
			
			if(daCard != null && dbCard != null)
			{
				double e = daCard.getAttack() - dbCard.getDefense();
				double f = dbCard.getAttack() - daCard.getDefense();
				CardPoint ef = new CardPoint(e, f, AnD.wattack_ldefense, MatchResultEnum.draw1);
				aaldd1.add(ef);
				wholelist.add(ef);
				
				double i = daCard.getAttack() - dbCard.getAttack();
				double j = dbCard.getDefense() - daCard.getDefense();
				CardPoint ij = new CardPoint(i, j, AnD.wattack_lattck, MatchResultEnum.draw1);
				aalad1.add(ij);
				wholelist.add(ij);
				
				
				double g = dbCard.getAttack() - daCard.getDefense();
				double h = daCard.getAttack() - dbCard.getDefense();
				CardPoint gh = new CardPoint(g, h, AnD.wattack_ldefense, MatchResultEnum.draw2);
				aaldd2.add(gh);
				wholelist.add(gh);
				
				double k = dbCard.getAttack() - daCard.getAttack();
				double l = daCard.getDefense() - dbCard.getDefense();
				CardPoint kl = new CardPoint(k, l, AnD.wattack_lattck, MatchResultEnum.draw2);
				aalad2.add(kl);
				wholelist.add(kl);
			}
		}
		
		return cardPointsMap;
	}
	
	public static CardPoint getwattack_ldefense(VSTeamCard vs1,VSTeamCard vs2)
	{
		double a = vs1.getAttack() - vs2.getDefense();
		double b = vs2.getAttack() - vs1.getDefense();
		return new CardPoint(a, b, AnD.wattack_ldefense, MatchResultEnum.win);
	}
	
	public static CardPoint getwattack_lattack(VSTeamCard vs1,VSTeamCard vs2)
	{
		double a = vs1.getAttack() - vs2.getAttack();
		double b = vs1.getDefense() - vs2.getDefense();
		return new CardPoint(a, b, AnD.wattack_lattck, MatchResultEnum.win);
	}
	
	public static boolean isInsideCircle(CardPoint point,CardPoint detectp)
	{
		if(radius > StatisticUtils.gougu(point.getX()-detectp.getX(), point.getY()-detectp.getY()))
		{
			//在半径范围内,则说明在圆内
			return true;
		}
		
		return false;
	}
}
