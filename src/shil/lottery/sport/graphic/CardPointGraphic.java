package shil.lottery.sport.graphic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

import shil.lottery.sport.analyze.NumberUtils;
import shil.lottery.sport.cards.AnD;
import shil.lottery.sport.cards.AnalyzeCardPoint;
import shil.lottery.sport.cards.CardPoint;
import shil.lottery.sport.cards.MatchResultEnum;
import shil.lottery.sport.db.SportMetaDaoImpl;
import shil.lottery.sport.domain.VSTeam;

public class CardPointGraphic {

	public static void main(String[] args)
	{
		wattack_ldefense_win();
		wattack_lattck_win();
		wattack_ldefense_draw1();
		wattack_ldefense_draw2();
		wattack_lattack_draw1();
		wattack_lattack_draw2();
		whole_pictures();
	}
	
	public static void wattack_ldefense_win()
	{
		new AbstractScatterPlotGraphic("wattack_ldefense_win", "x", "y") {
			@Override
			public XYDataset getDeltaCards() {
				
				List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
				Map<String,List<CardPoint>> cds = AnalyzeCardPoint.analyzeEveryPointSituation(vsTeams);
				DefaultXYDataset dataset = new DefaultXYDataset();
				double[][] dot = new double[][]{{0},{0}};
				dataset.addSeries("dot", dot);
				
				double[][] xy = wattack_ldefense_win(cds);
				dataset.addSeries("wattack_ldefense_win", xy);
				
				return dataset;
			}

			
		};
	}
	
	public static double[][] wattack_ldefense_win(
			Map<String, List<CardPoint>> cds) {
		List<CardPoint> cdww = cds.get(AnD.wattack_ldefense.name()+MatchResultEnum.win.name());
		List<Double> xs = new ArrayList<Double>();
		List<Double> ys = new ArrayList<Double>();
		for(CardPoint cp : cdww)
		{
			xs.add(cp.getX());
			ys.add(cp.getY());
		}
		
		double[][] xy = new double[][]{NumberUtils.convertListDs2doubles(xs),NumberUtils.convertListDs2doubles(ys)};
		return xy;
	}
	
	public static void wattack_lattck_win()
	{
		new AbstractScatterPlotGraphic("wattack_lattck_win", "x", "y") {
			@Override
			public XYDataset getDeltaCards() {
				
				List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
				Map<String,List<CardPoint>> cds = AnalyzeCardPoint.analyzeEveryPointSituation(vsTeams);
				DefaultXYDataset dataset = new DefaultXYDataset();
				double[][] dot = new double[][]{{0},{0}};
				dataset.addSeries("dot", dot);
				
				double[][] xy = wattack_lattck_win(cds);
				dataset.addSeries("wattack_lattck_win", xy);
				
				return dataset;
			}
		};
	}
	
	public static double[][] wattack_lattck_win(
			Map<String, List<CardPoint>> cds) {
		List<CardPoint> cdww = cds.get(AnD.wattack_lattck.name()+MatchResultEnum.win.name());
		List<Double> xs = new ArrayList<Double>();
		List<Double> ys = new ArrayList<Double>();
		for(CardPoint cp : cdww)
		{
			xs.add(cp.getX());
			ys.add(cp.getY());
		}
		
		double[][] xy = new double[][]{NumberUtils.convertListDs2doubles(xs),NumberUtils.convertListDs2doubles(ys)};
		return xy;
	}
	
	public static void wattack_ldefense_draw1()
	{
		new AbstractScatterPlotGraphic("wattack_ldefense_draw1", "x", "y") {
			@Override
			public XYDataset getDeltaCards() {
				
				List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
				Map<String,List<CardPoint>> cds = AnalyzeCardPoint.analyzeEveryPointSituation(vsTeams);
				DefaultXYDataset dataset = new DefaultXYDataset();
				double[][] dot = new double[][]{{0},{0}};
				dataset.addSeries("dot", dot);
				
				double[][] xy = wattack_ldefense_draw1(cds);
				dataset.addSeries("wattack_ldefense_draw1", xy);
				
				return dataset;
			}
		};
	}
	
	public static double[][] wattack_ldefense_draw1(
			Map<String, List<CardPoint>> cds) {
		List<CardPoint> cdww = cds.get(AnD.wattack_ldefense.name()+MatchResultEnum.draw1.name());
		List<Double> xs = new ArrayList<Double>();
		List<Double> ys = new ArrayList<Double>();
		for(CardPoint cp : cdww)
		{
			xs.add(cp.getX());
			ys.add(cp.getY());
		}
		
		double[][] xy = new double[][]{NumberUtils.convertListDs2doubles(xs),NumberUtils.convertListDs2doubles(ys)};
		return xy;
	}
	
	public static void wattack_ldefense_draw2()
	{
		new AbstractScatterPlotGraphic("wattack_ldefense_draw2", "x", "y") {
			@Override
			public XYDataset getDeltaCards() {
				
				List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
				Map<String,List<CardPoint>> cds = AnalyzeCardPoint.analyzeEveryPointSituation(vsTeams);
				DefaultXYDataset dataset = new DefaultXYDataset();
				double[][] dot = new double[][]{{0},{0}};
				dataset.addSeries("dot", dot);
				
				double[][] xy = wattack_ldefense_draw2(cds);
				dataset.addSeries("wattack_ldefense_draw2", xy);
				
				return dataset;
			}
		};
	}
	
	public static double[][] wattack_ldefense_draw2(
			Map<String, List<CardPoint>> cds) {
		List<CardPoint> cdww = cds.get(AnD.wattack_ldefense.name()+MatchResultEnum.draw2.name());
		List<Double> xs = new ArrayList<Double>();
		List<Double> ys = new ArrayList<Double>();
		for(CardPoint cp : cdww)
		{
			xs.add(cp.getX());
			ys.add(cp.getY());
		}
		
		double[][] xy = new double[][]{NumberUtils.convertListDs2doubles(xs),NumberUtils.convertListDs2doubles(ys)};
		return xy;
	}
	
	public static void wattack_lattack_draw1()
	{
		new AbstractScatterPlotGraphic("wattack_lattack_draw1", "x", "y") {
			@Override
			public XYDataset getDeltaCards() {
				
				List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
				Map<String,List<CardPoint>> cds = AnalyzeCardPoint.analyzeEveryPointSituation(vsTeams);
				DefaultXYDataset dataset = new DefaultXYDataset();
				double[][] dot = new double[][]{{0},{0}};
				dataset.addSeries("dot", dot);
				
				double[][] xy = wattack_lattack_draw1(cds);
				dataset.addSeries("wattack_lattck_draw1", xy);
				
				return dataset;
			}
		};
	}
	
	public static double[][] wattack_lattack_draw1(
			Map<String, List<CardPoint>> cds) {
		List<CardPoint> cdww = cds.get(AnD.wattack_lattck.name()+MatchResultEnum.draw1.name());
		List<Double> xs = new ArrayList<Double>();
		List<Double> ys = new ArrayList<Double>();
		for(CardPoint cp : cdww)
		{
			xs.add(cp.getX());
			ys.add(cp.getY());
		}
		
		double[][] xy = new double[][]{NumberUtils.convertListDs2doubles(xs),NumberUtils.convertListDs2doubles(ys)};
		return xy;
	}
	
	public static void wattack_lattack_draw2()
	{
		new AbstractScatterPlotGraphic("wattack_lattack_draw2", "x", "y") {
			@Override
			public XYDataset getDeltaCards() {
				
				List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
				Map<String,List<CardPoint>> cds = AnalyzeCardPoint.analyzeEveryPointSituation(vsTeams);
				DefaultXYDataset dataset = new DefaultXYDataset();
				double[][] dot = new double[][]{{0},{0}};
				dataset.addSeries("dot", dot);
				
				double[][] xy = wattack_lattack_draw2_data(cds);
				dataset.addSeries("wattack_lattck_draw2", xy);
				
				return dataset;
			}
		};
	}
	
	public static double[][] wattack_lattack_draw2_data(
			Map<String, List<CardPoint>> cds) {
		List<CardPoint> cdww = cds.get(AnD.wattack_lattck.name()+MatchResultEnum.draw2.name());
		List<Double> xs = new ArrayList<Double>();
		List<Double> ys = new ArrayList<Double>();
		for(CardPoint cp : cdww)
		{
			xs.add(cp.getX());
			ys.add(cp.getY());
		}
		
		double[][] xy = new double[][]{NumberUtils.convertListDs2doubles(xs),NumberUtils.convertListDs2doubles(ys)};
		return xy;
	}
	
	public static void whole_pictures()
	{
		new AbstractScatterPlotGraphic("whole_pictures", "x", "y") {
			@Override
			public XYDataset getDeltaCards() {
				
				List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
				Map<String,List<CardPoint>> cds = AnalyzeCardPoint.analyzeEveryPointSituation(vsTeams);
				DefaultXYDataset dataset = new DefaultXYDataset();
				double[][] dot = new double[][]{{0},{0}};
				dataset.addSeries("dot", dot);
				
				dataset.addSeries("wattack_ldefense_win", wattack_ldefense_win(cds));
				dataset.addSeries("wattack_lattck_win", wattack_lattck_win(cds));
				dataset.addSeries("wattack_ldefense_draw1", wattack_ldefense_draw1(cds));
				dataset.addSeries("wattack_ldefense_draw2", wattack_ldefense_draw2(cds));
				dataset.addSeries("wattack_lattck_draw1", wattack_lattack_draw1(cds));
				dataset.addSeries("wattack_lattck_draw2", wattack_lattack_draw2_data(cds));
				
				return dataset;
			}
		};
	}
}
