package shil.lottery.sport.graphic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

import shil.lottery.sport.analyze.NumberUtils;
import shil.lottery.sport.cards.AnaylzeVSTeam2CardsbyScore;
import shil.lottery.sport.cards.VSTeamCard;
import shil.lottery.sport.db.SportMetaDaoImpl;
import shil.lottery.sport.domain.VSTeam;

public class CardsGraphicTest {
	
	public static void main(String[] args)
	{
//		wingraphic();
//		losegraphic();
		deltagraphic();
//		deltagraphicTest();
//		linegraphic();
//		linegraphicTest();
	}
	
	public static void wingraphic()
	{
		JFreeChart jFreeChart = ChartFactory.createScatterPlot("Cards", "attack", "defense", getWinCards());
		ChartFrame chartFrame = new ChartFrame("CardsFrame", jFreeChart);
		chartFrame.pack();
		chartFrame.setVisible(true);
	}
	
	public static void losegraphic()
	{
		JFreeChart jFreeChart = ChartFactory.createScatterPlot("Cards", "attack", "defense", getLoseCards());
		ChartFrame chartFrame = new ChartFrame("CardsFrame", jFreeChart);
		chartFrame.pack();
		chartFrame.setVisible(true);
	}
	
	public static void deltagraphic()
	{
		JFreeChart jFreeChart = ChartFactory.createScatterPlot("Cards", "winCard.getAttack() - loseCard.getDefense()", "loseCard.getAttack() - winCard.getDefense()", getDeltaCards());
		ChartFrame chartFrame = new ChartFrame("CardsFrame", jFreeChart);
		chartFrame.pack();
		chartFrame.setVisible(true);
	}
	
	public static void deltagraphicTest()
	{
		JFreeChart jFreeChart = ChartFactory.createScatterPlot("Cards", "winCard.getAttack() - loseCard.getAttack()", "winCard.getDefense() - loseCard.getDefense()", getDeltaCardsTest());
		ChartFrame chartFrame = new ChartFrame("CardsFrame", jFreeChart);
		chartFrame.pack();
		chartFrame.setVisible(true);
	}
	
	public static void linegraphic()
	{
		JFreeChart jFreeChart = ChartFactory.createScatterPlot("Cards", "times", "minusDelta", getLineCards());
		ChartFrame chartFrame = new ChartFrame("CardsFrame", jFreeChart);
		chartFrame.pack();
		chartFrame.setVisible(true);
	}
	
	public static void linegraphicTest()
	{
		JFreeChart jFreeChart = ChartFactory.createScatterPlot("Cards", "times", "minusDelta", getLineCardsTest());
		ChartFrame chartFrame = new ChartFrame("CardsFrame", jFreeChart);
		chartFrame.pack();
		chartFrame.setVisible(true);
	}
	
	public static XYDataset getWinCards()
	{
		DefaultXYDataset dataset = new DefaultXYDataset();
		
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		Map<String,VSTeamCard> cards = AnaylzeVSTeam2CardsbyScore.anaylzeLeagueCards(vsTeams);
		List<Double> winAttacks = new ArrayList<Double>();
		List<Double> winDefense = new ArrayList<Double>();
		
		for(VSTeam vs : vsTeams)
		{
			VSTeamCard winCard = null;
			
			if(vs.getMatch_Result() == 3)
			{
				winCard = cards.get(AnaylzeVSTeam2CardsbyScore.makeCardKey(vs.getLeague(), vs.getVs()[0]));
			}
			else if(vs.getMatch_Result() == 0)
			{
				winCard = cards.get(AnaylzeVSTeam2CardsbyScore.makeCardKey(vs.getLeague(), vs.getVs()[1]));
			}
			else
			{
				continue;
			}
			
			if(winCard != null)
			{
				winAttacks.add(winCard.getAttack());
				winDefense.add(winCard.getDefense());
			}
		}
//		System.out.println(vsTeams.size());
		System.out.println(winAttacks.size());
		
		double[][] wins = new double[][]{NumberUtils.convertListDs2doubles(winAttacks),NumberUtils.convertListDs2doubles(winDefense)};
		dataset.addSeries("wins", wins);
		
		return dataset;
	}
	
	public static XYDataset getLoseCards()
	{
		DefaultXYDataset dataset = new DefaultXYDataset();
		
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		Map<String,VSTeamCard> cards = AnaylzeVSTeam2CardsbyScore.anaylzeLeagueCards(vsTeams);
		List<Double> loseAttacks = new ArrayList<Double>();
		List<Double> loseDefense = new ArrayList<Double>();
		
		for(VSTeam vs : vsTeams)
		{
			VSTeamCard loseCard = null;
			
			if(vs.getMatch_Result() == 3)
			{
				loseCard = cards.get(AnaylzeVSTeam2CardsbyScore.makeCardKey(vs.getLeague(), vs.getVs()[1]));
			}
			else if(vs.getMatch_Result() == 0)
			{
				loseCard = cards.get(AnaylzeVSTeam2CardsbyScore.makeCardKey(vs.getLeague(), vs.getVs()[0]));
			}
			else
			{
				continue;
			}
			
			if(loseCard != null)
			{
				loseAttacks.add(loseCard.getAttack());
				loseDefense.add(loseCard.getDefense());
			}
		}
//		System.out.println(vsTeams.size());
		System.out.println(loseAttacks.size());
		
		double[][] wins = new double[][]{{0},{0}};
		dataset.addSeries("wins", wins);
		
		double[][] loses = new double[][]{NumberUtils.convertListDs2doubles(loseAttacks),NumberUtils.convertListDs2doubles(loseDefense)};
		dataset.addSeries("lose", loses);
		
		return dataset;
	}
	
	public static XYDataset getDeltaCards()
	{
		DefaultXYDataset dataset = new DefaultXYDataset();
		
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		Map<String,VSTeamCard> cards = AnaylzeVSTeam2CardsbyScore.anaylzeLeagueCards(vsTeams);
		List<Double> aget = new ArrayList<Double>();
		List<Double> bget = new ArrayList<Double>();
		List<Double> cget = new ArrayList<Double>();
		List<Double> dget = new ArrayList<Double>();
		
		List<Double> eget = new ArrayList<Double>();
		List<Double> fget = new ArrayList<Double>();
		
		List<Double> gget = new ArrayList<Double>();
		List<Double> hget = new ArrayList<Double>();
		
		double apbp = 0d;
		double apbn = 0d;
		double anbn = 0d;
		double anbp = 0d;
		
		double cpdp = 0d;
		double cpdn = 0d;
		double cndn = 0d;
		double cndp = 0d;
		
		double nice =0d;
		
		double epfp = 0;
		double epfn = 0;
		double enfn = 0;
		double enfp = 0;
		
		double gphp = 0;
		double gphn = 0;
		double gnhn = 0;
		double gnhp = 0;
		
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
			
			if(winCard!=null && loseCard!=null)
			{
				if(winCard.getAttack() == loseCard.getAttack()) System.out.println("attacksame:" + vs +winCard +loseCard);
				if(winCard.getDefense() == loseCard.getDefense()) System.out.println("defensesame"+ vs +winCard +loseCard);
				
				double a = winCard.getAttack() - loseCard.getDefense();
				aget.add(a);
				double b = loseCard.getAttack() - winCard.getDefense();
				bget.add(b);
				if(a>=0&&b>0) apbp++;
				if(a>=0&&b<=0) apbn++;
				if(a<0&&b<0) anbn++;
				if(a<0&&b>=0) anbp++;
				
				double c = winCard.getAttack() - loseCard.getAttack();
				cget.add(c);
				double d = winCard.getDefense() - loseCard.getDefense();
				dget.add(d);
				if(c>=0&&d>=0) cpdp++;
				if(c>=0&&d<0) cpdn++;
				if(c<0&&d<0) cndn++;
				if(c<0&&d>=0) cndp++;
				
				if(a>=0&&b<=0 && c>=0&&d>=0) nice++;
			}
			
			if(daCard!=null && dbCard != null)
			{
				double e = daCard.getAttack() - dbCard.getDefense();
				eget.add(e);
				double f = dbCard.getAttack() - daCard.getDefense();
				fget.add(f);
				
				if(e>=0&&f>0) epfp++;
				if(e>=0&&f<=0) epfn++;
				if(e<0&&f<=0) enfn++;
				if(e<0&&f>0) enfp++;
				
				double g =daCard.getAttack() - dbCard.getAttack();
				gget.add( g);
				double h = daCard.getDefense() - dbCard.getDefense();
				hget.add( h);
				
				if(g>=0&&h>0) gphp++;
				if(g>=0&&h<=0) gphn++;
				if(g<0&&h<=0) gnhn++;
				if(g<0&&h>0) gnhp++;
			}
		}
//		System.out.println(vsTeams.size());
		System.out.println(aget.size() == apbp+apbn+anbn+anbp);
		System.out.println("apbp: "+apbp+apbp/aget.size());
		System.out.println("apbn: "+apbn+apbn/aget.size());
		System.out.println("anbn: "+anbn+anbn/aget.size());
		System.out.println("anbp: "+anbp+anbp/aget.size());
		
		System.out.println("cpdp: "+cpdp+cpdp/aget.size());
		System.out.println("cpdn: "+cpdn+cpdn/aget.size());
		System.out.println("cndn: "+cndn+cndn/aget.size());
		System.out.println("cndp: "+cndp+cndp/aget.size());
		
		System.out.println("nice: " + nice/aget.size());
		
		System.out.println(eget.size() == epfp+epfn+enfn+enfp);
		System.out.println("epfp: "+epfp+epfp/eget.size());
		System.out.println("epfn: "+epfn+epfn/eget.size());
		System.out.println("enfn: "+enfn+enfn/eget.size());
		System.out.println("enfp: "+enfp+enfp/eget.size());
		
		System.out.println("gphp: "+gphp+gphp/eget.size());
		System.out.println("gphn: "+gphn+gphn/eget.size());
		System.out.println("gnhn: "+gnhn+gnhn/eget.size());
		System.out.println("gnhp: "+gnhp+gnhp/eget.size());
		
		double[][] wins = new double[][]{{0},{0}};
		dataset.addSeries("wins", wins);
		
		double[][] loses = new double[][]{{0},{0}};
//		dataset.addSeries("lose", loses);
		
		double[][] get = new double[][]{NumberUtils.convertListDs2doubles(aget),NumberUtils.convertListDs2doubles(bget)};
		dataset.addSeries("ready", get);
		
		double[][] revget = new double[][]{NumberUtils.convertListDs2doubles(cget),NumberUtils.convertListDs2doubles(dget)};
//		dataset.addSeries("rev-ready", revget);
		
		double[][] draw = new double[][]{NumberUtils.convertListDs2doubles(eget),NumberUtils.convertListDs2doubles(fget)};
//		dataset.addSeries("draw", draw);
		
		double[][] revdraw = new double[][]{NumberUtils.convertListDs2doubles(gget),NumberUtils.convertListDs2doubles(hget)};
//		dataset.addSeries("rev-draw", revdraw);
		
		return dataset;
	}
	
	public static XYDataset getDeltaCardsTest()
	{
		DefaultXYDataset dataset = new DefaultXYDataset();
		
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		Map<String,VSTeamCard> cards = AnaylzeVSTeam2CardsbyScore.anaylzeLeagueCards(vsTeams);
		List<Double> aget = new ArrayList<Double>();
		List<Double> bget = new ArrayList<Double>();
		
		for(VSTeam vs : vsTeams)
		{
			VSTeamCard winCard = null;
			VSTeamCard loseCard = null;
			
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
				continue;
			}
			
			if(winCard!=null && loseCard!=null)
			{
				if(winCard.getAttack() == loseCard.getAttack()) System.out.println("attacksame:" + vs +winCard +loseCard);
				if(winCard.getDefense() == loseCard.getDefense()) System.out.println("defensesame"+ vs +winCard +loseCard);
				aget.add( winCard.getAttack() - loseCard.getAttack());
				bget.add( winCard.getDefense() - loseCard.getDefense());
			}
		}
//		System.out.println(vsTeams.size());
		System.out.println(aget.size());
		
		double[][] wins = new double[][]{{0},{0}};
		dataset.addSeries("wins", wins);
		
		double[][] loses = new double[][]{{0},{0}};
		dataset.addSeries("lose", loses);
		
		double[][] get = new double[][]{NumberUtils.convertListDs2doubles(aget),NumberUtils.convertListDs2doubles(bget)};
		dataset.addSeries("ready", get);
		
		return dataset;
	}
	
	public static XYDataset getLineCards()
	{
		DefaultXYDataset dataset = new DefaultXYDataset();
		
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		Map<String,VSTeamCard> cards = AnaylzeVSTeam2CardsbyScore.anaylzeLeagueCards(vsTeams);
		List<Double> aget = new ArrayList<Double>();
		List<Double> bget = new ArrayList<Double>();
		List<Double> num = new ArrayList<Double>();
		List<Double> line = new ArrayList<Double>();
		double i=0;
		for(VSTeam vs : vsTeams)
		{
			VSTeamCard winCard = null;
			VSTeamCard loseCard = null;
			
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
				continue;
			}
			
			if(winCard!=null && loseCard!=null)
			{
				if(winCard.getAttack() == loseCard.getAttack()) System.out.println("attacksame:" + vs +winCard +loseCard);
				if(winCard.getDefense() == loseCard.getDefense()) System.out.println("defensesame"+ vs +winCard +loseCard);
				aget.add( winCard.getAttack() - loseCard.getDefense());
				bget.add( loseCard.getAttack() - winCard.getDefense());
				num.add(i++);
				line.add((winCard.getAttack() - loseCard.getDefense())-(loseCard.getAttack() - winCard.getDefense()));
			}
		}
//		System.out.println(vsTeams.size());
		System.out.println(aget.size());
		
		double[][] wins = new double[][]{{0},{0}};
		dataset.addSeries("wins", wins);
		
		double[][] loses = new double[][]{{0},{0}};
		dataset.addSeries("lose", loses);
		
		double[][] get = new double[][]{{0},{0}};
		dataset.addSeries("ready", get);
		
		double[][] lines = new double[][]{NumberUtils.convertListDs2doubles(num),NumberUtils.convertListDs2doubles(line)};
		dataset.addSeries("line", lines);
		
		return dataset;
	}
	
	public static XYDataset getLineCardsTest()
	{
		DefaultXYDataset dataset = new DefaultXYDataset();
		
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		Map<String,VSTeamCard> cards = AnaylzeVSTeam2CardsbyScore.anaylzeLeagueCards(vsTeams);
		List<Double> aget = new ArrayList<Double>();
		List<Double> bget = new ArrayList<Double>();
		List<Double> num = new ArrayList<Double>();
		List<Double> line = new ArrayList<Double>();
		double i=0;
		for(VSTeam vs : vsTeams)
		{
			VSTeamCard winCard = null;
			VSTeamCard loseCard = null;
			
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
				continue;
			}
			
			if(winCard!=null && loseCard!=null)
			{
				if(winCard.getAttack() == loseCard.getAttack()) System.out.println("attacksame:" + vs +winCard +loseCard);
				if(winCard.getDefense() == loseCard.getDefense()) System.out.println("defensesame"+ vs +winCard +loseCard);
				aget.add( winCard.getAttack() - loseCard.getAttack());
				bget.add( winCard.getDefense() - loseCard.getDefense());
				num.add(i++);
				line.add((winCard.getAttack() - loseCard.getAttack())-(winCard.getDefense() - loseCard.getDefense()));
			}
		}
//		System.out.println(vsTeams.size());
		System.out.println(aget.size());
		
		double[][] wins = new double[][]{{0},{0}};
		dataset.addSeries("wins", wins);
		
		double[][] loses = new double[][]{{0},{0}};
		dataset.addSeries("lose", loses);
		
		double[][] get = new double[][]{{0},{0}};
		dataset.addSeries("ready", get);
		
		double[][] lines = new double[][]{NumberUtils.convertListDs2doubles(num),NumberUtils.convertListDs2doubles(line)};
		dataset.addSeries("line", lines);
		
		return dataset;
	}
	
	public static XYDataset getCards()
	{
		DefaultXYDataset dataset = new DefaultXYDataset();
		
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		Map<String,VSTeamCard> cards = AnaylzeVSTeam2CardsbyScore.anaylzeLeagueCards(vsTeams);
		List<Double> winAttacks = new ArrayList<Double>();
		List<Double> winDefense = new ArrayList<Double>();
		List<Double> loseAttacks = new ArrayList<Double>();
		List<Double> loseDefense = new ArrayList<Double>();
		List<Double> aget = new ArrayList<Double>();
		List<Double> bget = new ArrayList<Double>();
		
		for(VSTeam vs : vsTeams.subList(0, 1000))
		{
			VSTeamCard winCard = null;
			VSTeamCard loseCard = null;
			
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
				continue;
			}
			
			if(winCard != null)
			{
				winAttacks.add(winCard.getAttack());
				winDefense.add(winCard.getDefense());
			}
			if(loseCard != null)
			{
				loseAttacks.add(loseCard.getAttack());
				loseDefense.add(loseCard.getDefense());
			}
			if(winCard!=null && loseCard!=null)
			{
				if(winCard.getAttack() == loseCard.getAttack()) System.out.println("attacksame:" + vs +winCard +loseCard);
				if(winCard.getDefense() == loseCard.getDefense()) System.out.println("defensesame"+ vs +winCard +loseCard);
				aget.add( winCard.getAttack() - loseCard.getDefense());
				bget.add( loseCard.getAttack() - winCard.getDefense());
			}
		}
		System.out.println(vsTeams.size());
		System.out.println(winAttacks.size());
		System.out.println(loseAttacks.size());
		System.out.println(aget.size());
		
		double[][] wins = new double[][]{NumberUtils.convertListDs2doubles(winAttacks),NumberUtils.convertListDs2doubles(winDefense)};
//		double[][] wins = new double[][]{{0},{0}};
		dataset.addSeries("wins", wins);
		
		double[][] loses = new double[][]{NumberUtils.convertListDs2doubles(loseAttacks),NumberUtils.convertListDs2doubles(loseDefense)};
		dataset.addSeries("lose", loses);
		
		double[][] get = new double[][]{NumberUtils.convertListDs2doubles(aget),NumberUtils.convertListDs2doubles(bget)};
//		dataset.addSeries("ready", get);
		
		return dataset;
	}
}
