package shil.lottery.sport.graphic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

import shil.lottery.sport.analyze.NumberUtils;
import shil.lottery.sport.cards.AnaylzeVSTeam2CardsbyScore;
import shil.lottery.sport.cards.VSTeamCard;
import shil.lottery.sport.db.SportMetaDaoImpl;
import shil.lottery.sport.domain.VSTeam;

public class LoseCardsAttackminusDefense {

	public static void main(String[] args) {
		new AbstractScatterPlotGraphic("loseCards","Lose.attack-Win.denfese","Win.attack-Lose.defense") {
			
			@Override
			public XYDataset getDeltaCards() {
				
				DefaultXYDataset dataset = new DefaultXYDataset();
				
				List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
				Map<String,VSTeamCard> cards = AnaylzeVSTeam2CardsbyScore.anaylzeLeagueCards(vsTeams);
				List<Double> aget = new ArrayList<Double>();
				List<Double> bget = new ArrayList<Double>();
				List<Double> eget = new ArrayList<Double>();
				List<Double> fget = new ArrayList<Double>();
				double apbp = 0d;
				double apbn = 0d;
				double anbn = 0d;
				double anbp = 0d;
				double epfp = 0;
				double epfn = 0;
				double enfn = 0;
				double enfp = 0;
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
						daCard = cards.get(AnaylzeVSTeam2CardsbyScore.makeCardKey(vs.getLeague(), vs.getVs()[1]));
						dbCard = cards.get(AnaylzeVSTeam2CardsbyScore.makeCardKey(vs.getLeague(), vs.getVs()[0])); 
					}
					
					if(winCard!=null && loseCard!=null)
					{
						if(winCard.getAttack() == loseCard.getAttack()) System.out.println("attacksame:" + vs +winCard +loseCard);
						if(winCard.getDefense() == loseCard.getDefense()) System.out.println("defensesame"+ vs +winCard +loseCard);
						
						double a = loseCard.getAttack() - winCard.getDefense();
						aget.add(a);
						double b = winCard.getAttack() - loseCard.getDefense();
						bget.add(b);
						if(a>=0&&b>0) apbp++;
						if(a>=0&&b<=0) apbn++;
						if(a<0&&b<0) anbn++;
						if(a<0&&b>=0) anbp++;
					}
					
					if(daCard!=null && dbCard != null)
					{
						double e = daCard.getAttack() - dbCard.getDefense();
						eget.add(e);
						double f = dbCard.getAttack() - daCard.getDefense();
						fget.add(f);
						
						if(e>=0&&f>0) epfp++;
						if(e>=0&&f<=0) epfn++;
						if(e<0&&f<0) enfn++;
						if(e<0&&f>=0) enfp++;
					}
				}
				System.out.println(vsTeams.size());
				System.out.println(aget.size() == apbp+apbn+anbn+anbp);
				System.out.println("apbp: "+apbp + " % -> " + apbp/aget.size());
				System.out.println("apbn: "+apbn + " % -> " + apbn/aget.size());
				System.out.println("anbn: "+anbn + " % -> " + anbn/aget.size());
				System.out.println("anbp: "+anbp + " % -> " + anbp/aget.size());
				
				System.out.println("epfp: "+epfp+ " % -> " +epfp/eget.size());
				System.out.println("epfn: "+epfn+ " % -> " +epfn/eget.size());
				System.out.println("enfn: "+enfn+ " % -> " +enfn/eget.size());
				System.out.println("enfp: "+enfp+ " % -> " +enfp/eget.size());
				
				double[][] dot = new double[][]{{0},{0}};
				dataset.addSeries("0,0", dot);
				
				double[][] amd = new double[][]{NumberUtils.convertListDs2doubles(aget),NumberUtils.convertListDs2doubles(bget)};
				dataset.addSeries("lose", amd);
				
				double[][] draw = new double[][]{NumberUtils.convertListDs2doubles(eget),NumberUtils.convertListDs2doubles(fget)};
				dataset.addSeries("draw", draw);
				
				return dataset;
			}
		};

	}

}
