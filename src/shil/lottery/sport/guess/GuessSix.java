package shil.lottery.sport.guess;

import java.util.List;
import java.util.Map;

import shil.lottery.sport.cards.AnaylzeVSTeam2CardsbyScore;
import shil.lottery.sport.cards.VSTeamCard;
import shil.lottery.sport.entity.VSTeam;

public class GuessSix implements Guess4TeamMatchResult3 {

	public static double windoor = 2.65d;
	public static double losedoor = 2.9d;
	public static double drawdoor = 0.32d;
	
	@Override
	public int guess4teamMatchResult(List<VSTeam> vsTeams, VSTeam predictMatch) {
		
		Map<String,VSTeamCard> cards = AnaylzeVSTeam2CardsbyScore.anaylzeLeagueCards(vsTeams);
		
		VSTeamCard teama = cards.get(AnaylzeVSTeam2CardsbyScore.makeCardKey(predictMatch.getLeague(), predictMatch.getVs()[0]));
		VSTeamCard teamb = cards.get(AnaylzeVSTeam2CardsbyScore.makeCardKey(predictMatch.getLeague(), predictMatch.getVs()[1]));
		
		if(teama==null || teamb==null) return -5;
		
		double acomein = teama.getAttack() - teamb.getDefense();
		double bcomein = teamb.getAttack() - teama.getDefense();
		
//		System.out.println(acomein);
//		System.out.println(bcomein);
//		System.out.println("-: "+ (acomein-bcomein));
		
		if(acomein > bcomein) 
		{
			if(acomein-bcomein > windoor) return 3;
		}
		else if(bcomein > acomein) 
		{
			if(bcomein - acomein > losedoor) return 0;
		}
		else if(Math.abs(acomein - bcomein)<GuessSix.drawdoor) 
		{
			return 1;
		}
		
		return -1;
	}

}
