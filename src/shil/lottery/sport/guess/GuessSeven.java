package shil.lottery.sport.guess;

import java.util.List;
import java.util.Map;

import shil.lottery.sport.cards.AnaylzeVSTeam2CardsbyScore;
import shil.lottery.sport.cards.VSTeamCard;
import shil.lottery.sport.entity.VSTeam;

/**
 * 双向交叉验证
 * @author ljj
 * @since 2014-08-05 0:03
 */
public class GuessSeven implements Guess4TeamMatchResult3 {

	@Override
	public int guess4teamMatchResult(List<VSTeam> vsTeams, VSTeam predictMatch) {
		Map<String,VSTeamCard> cards = AnaylzeVSTeam2CardsbyScore.anaylzeLeagueCards(vsTeams);
		
		VSTeamCard teama = cards.get(AnaylzeVSTeam2CardsbyScore.makeCardKey(predictMatch.getLeague(), predictMatch.getVs()[0]));
		VSTeamCard teamb = cards.get(AnaylzeVSTeam2CardsbyScore.makeCardKey(predictMatch.getLeague(), predictMatch.getVs()[1]));
		
		if(teama==null || teamb==null) return -5;
		
		double a1 = teama.getAttack() - teamb.getDefense();
		double b1 = teamb.getAttack() - teama.getDefense();
		double c1 = teama.getAttack() - teamb.getAttack();
		double d1 = teama.getDefense() - teamb.getDefense();
		
		double a2 = teamb.getAttack() - teama.getDefense();
		double b2 = teama.getAttack() - teamb.getDefense();
		double c2 = teamb.getAttack() - teama.getAttack();
		double d2 = teamb.getDefense() - teama.getDefense();
				
		if(a1>=0&&b1<=0 && c1>=0&&d1>=0) return 3;
		
		if(a2>=0&&b2<=0 && c2>=0&&d2>=0) return 0;
		
		return -1;
	}

}
