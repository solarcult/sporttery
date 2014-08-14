package shil.lottery.sport.guess;

import java.io.File;
import java.util.List;
import java.util.Map;

import shil.lottery.sport.cards.AnalyzeQuadrantStatus;
import shil.lottery.sport.cards.AnaylzeVSTeam2CardsbyScore;
import shil.lottery.sport.cards.Quadrants;
import shil.lottery.sport.cards.VSTeamCard;
import shil.lottery.sport.db.SportMetaDaoImpl;
import shil.lottery.sport.domain.VSTeam;
import shil.lottery.sport.excel.LoadExcelData2VSTeams;

/**
 * 根据卡片的攻击防御里进行分析
 * @author ljj
 * @since 2014-08-06
 */
public class GuessEight implements Guess4TeamMatchResult3 {

	@Override
	public int guess4teamMatchResult(List<VSTeam> vsTeams, VSTeam predictMatch) {
		
		Quadrants winAmL = AnalyzeQuadrantStatus.analyzeWinAttackMLoseDefense(vsTeams);
		Quadrants drawAmL = AnalyzeQuadrantStatus.analyzeDrawAttackMLoseDefense(vsTeams);
		
		Map<String,VSTeamCard> cards = AnaylzeVSTeam2CardsbyScore.anaylzeLeagueCards(vsTeams);
		
		VSTeamCard t1 = cards.get(AnaylzeVSTeam2CardsbyScore.makeCardKey(predictMatch.getLeague(), predictMatch.getVs()[0]));
		VSTeamCard t2 = cards.get(AnaylzeVSTeam2CardsbyScore.makeCardKey(predictMatch.getLeague(), predictMatch.getVs()[1]));
		System.out.println("t1: "+t1);
		System.out.println("t2: "+t2);
		
		if(t1==null || t2==null) 
			return -2;
		
		boolean ap = false;
		boolean bp = false;
		if(t1.getAttack() - t2.getDefense() > 0) ap = true;
		if(t2.getAttack() - t1.getDefense() > 0) bp = true;
		
		boolean cp = false;
		boolean dp = false;
		if(t2.getAttack() - t1.getDefense() > 0) cp = true;
		if(t1.getAttack() - t2.getDefense() > 0) dp = true;
		
		double t1p = -1000;
		double t1n = -1000;
		double d1p = -1000;
		double d1n = -1000;
		if(ap && bp) { t1p = winAmL.getQuad1p(); d1p = drawAmL.getQuad1p(); t1n = winAmL.getQuad1();d1n = drawAmL.getQuad1();}
		else if(ap && !bp){ t1p = winAmL.getQuad2p(); d1p = drawAmL.getQuad2p(); t1n = winAmL.getQuad2();d1n = drawAmL.getQuad2();}
		else if(!ap && !bp){ t1p = winAmL.getQuad3p(); d1p = drawAmL.getQuad3p();t1n = winAmL.getQuad3();d1n = drawAmL.getQuad3();}
		else if(!ap && bp){ t1p = winAmL.getQuad4p(); d1p = drawAmL.getQuad4p();t1n = winAmL.getQuad4();d1n = drawAmL.getQuad4();}
		System.out.println("t1p: "+t1p +" d1p= "+d1p);
		
		double t2p = -10;
		double t2n = -10;
		double d2p = -10;
		double d2n = -10;
		if(cp && dp) { t2p = winAmL.getQuad1p(); d2p = drawAmL.getQuad1p(); t2n = winAmL.getQuad1(); d2n = drawAmL.getQuad1();}
		else if(cp && !dp) { t2p = winAmL.getQuad2p(); d2p = drawAmL.getQuad2p();t2n = winAmL.getQuad2(); d2n = drawAmL.getQuad2();}
		else if(!cp && !dp){ t2p = winAmL.getQuad3p(); d2p = drawAmL.getQuad3p();t2n = winAmL.getQuad3(); d2n = drawAmL.getQuad3();}
		else if(!cp && dp) { t2p = winAmL.getQuad4p(); d2p = drawAmL.getQuad4p();t2n = winAmL.getQuad4(); d2n = drawAmL.getQuad4();}
		System.out.println("t2p: "+t2p +" d2p= "+ d2p);
		
		if(t1p >= t2p) 
		{
			double w = t1p * (t1n/(t1n+d1n));
			double d = d1p * (d1n/(t1n+d1n));
			System.out.println("w1: "+w);
			System.out.println("d1: "+d);
			if(w>d) return 3;
			else if(w<=d)return 1;
			else return -5;
		}
		else if(t1p < t2p) 
		{
			double w = t2p * (t2n/(t2n+d2n));
			double d = d2p * (d2n/(t2n+d2n));
			System.out.println("w2: "+w);
			System.out.println("d2: "+d);
			
			if(w>d) return 0;
			else if(w<=d)return 1;
			else return -5;
		}
		
		return -4;
	}

	public static void main(String[] args)
	{
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		List<VSTeam> guessTeams = LoadExcelData2VSTeams.justDoIt(new File("e:\\abc.xls"));
		Guess4TeamMatchResult3 g8 = new GuessEight();
		
		for(VSTeam vs : guessTeams)
		{
			System.out.println("\n* * *");
			System.out.println(vs.getLeague());
			System.out.println(vs.getVs()[0] +" vs " +vs.getVs()[1]);
			System.out.println(g8.guess4teamMatchResult(vsTeams, vs));
		}
		
	}
}
