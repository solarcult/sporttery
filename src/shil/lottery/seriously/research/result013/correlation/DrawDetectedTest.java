package shil.lottery.seriously.research.result013.correlation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.math3.stat.Frequency;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYZDataset;

import shil.lottery.seriously.utils.AbstractBubbleChart;
import shil.lottery.seriously.utils.AnalyzeUtil;
import shil.lottery.seriously.vo.WholeMatches;
import shil.lottery.sport.entity.VSTeam;
import shil.lottery.sport.graphic.AbstractScatterPlotGraphic;
import shil.lottery.sport.statistics.NumberUtils;

/**
 * 根据|主胜率-客胜率|/平率 试图找到不平局的规律,貌似比较难.
 * 不对,比值超过1以后的趋势应该平局更少,代表两队差距越大.下面应该计算具体概率或者密度.
 * @author LiangJingJing
 * @date Apr 21, 2015 11:07:25 PM
 */
public class DrawDetectedTest {

	public static void main(String[] args) {
		WholeMatches wholeMatches = AnalyzeUtil.analyzeWholeRecords();
		Set<String> leaguenames = wholeMatches.getLeaguesNames();
		
		for(String leaguename : leaguenames){
			List<VSTeam> vsTeams = wholeMatches.getLeaguesVSTeamsMap().get(leaguename);
			if(vsTeams.size()<AnalyzeUtil.leagalMinMatches) continue;
			final List<Double> drawx = new ArrayList<Double>();
			final List<Double> drawy = new ArrayList<Double>();
			final Frequency drawz = new Frequency();
			final List<Double> notdrawy = new ArrayList<Double>();
			final List<Double> notdrawx = new ArrayList<Double>();
			final Frequency notdrawz = new Frequency();

			for(VSTeam vsTeam : vsTeams){
				if(vsTeam.getMatch_Result() == AnalyzeUtil.draw){
					drawx.add((vsTeam.getBetCalcRate_web()[0]-vsTeam.getBetCalcRate_web()[2])/vsTeam.getBetCalcRate_web()[1]);
					drawy.add(vsTeam.getBetCalcRate_web()[1]);
					drawz.addValue((vsTeam.getBetCalcRate_web()[0]-vsTeam.getBetCalcRate_web()[2])/vsTeam.getBetCalcRate_web()[1]+AnalyzeUtil.Connect+vsTeam.getBetCalcRate_web()[1]);
				}else{
					notdrawx.add((vsTeam.getBetCalcRate_web()[0]-vsTeam.getBetCalcRate_web()[2])/vsTeam.getBetCalcRate_web()[1]);
					notdrawy.add(vsTeam.getBetCalcRate_web()[1]);
					notdrawz.addValue((vsTeam.getBetCalcRate_web()[0]-vsTeam.getBetCalcRate_web()[2])/vsTeam.getBetCalcRate_web()[1]+AnalyzeUtil.Connect+vsTeam.getBetCalcRate_web()[1]);
				}
			}
			
			new AbstractScatterPlotGraphic(leaguename+"_draw","相减绝对值比","draw_value"){
				@Override
				public XYDataset getDeltaCards() {
					DefaultXYDataset dataset = new DefaultXYDataset();
					dataset.addSeries("draw", new double[][]{NumberUtils.convertListDs2doubles(drawx),NumberUtils.convertListDs2doubles(drawy)});
					dataset.addSeries("point", new double[][]{{0},{0}});
					return dataset;
				}
			};
			
			new AbstractBubbleChart(leaguename+"_draw","(主胜率-客胜率)/平率 ","draw_value") {
				@Override
				public XYZDataset getXYZDataset() {
					DefaultXYZDataset dataset = new DefaultXYZDataset();
					dataset.addSeries("draw", new double[][]{NumberUtils.convertListDs2doubles(drawx),NumberUtils.convertListDs2doubles(drawy),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(drawx),NumberUtils.convertListDs2doubles(drawy), drawz)});
					dataset.addSeries("point", new double[][]{{0},{0},{0}});
					return dataset;
				}
			};
			
			new AbstractScatterPlotGraphic(leaguename+"_notdraw","相减绝对值比","draw_value"){
				@Override
				public XYDataset getDeltaCards() {
					DefaultXYDataset dataset = new DefaultXYDataset();
					dataset.addSeries("point", new double[][]{{0},{0}});
					dataset.addSeries("notdraw", new double[][]{NumberUtils.convertListDs2doubles(notdrawx),NumberUtils.convertListDs2doubles(notdrawy)});
					return dataset;
				}
			};
			
			new AbstractBubbleChart(leaguename+"_notdraw","(主胜率-客胜率)/平率 ","draw_value") {
				@Override
				public XYZDataset getXYZDataset() {
					DefaultXYZDataset dataset = new DefaultXYZDataset();
					dataset.addSeries("point", new double[][]{{0},{0},{0}});
					dataset.addSeries("notdraw", new double[][]{NumberUtils.convertListDs2doubles(notdrawx),NumberUtils.convertListDs2doubles(notdrawy),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(notdrawx),NumberUtils.convertListDs2doubles(notdrawy), notdrawz)});
					return dataset;
				}
			};
			
			new AbstractScatterPlotGraphic(leaguename,"相减绝对值比","draw_value"){
				@Override
				public XYDataset getDeltaCards() {
					DefaultXYDataset dataset = new DefaultXYDataset();
					dataset.addSeries("draw", new double[][]{NumberUtils.convertListDs2doubles(drawx),NumberUtils.convertListDs2doubles(drawy)});
					dataset.addSeries("notdraw", new double[][]{NumberUtils.convertListDs2doubles(notdrawx),NumberUtils.convertListDs2doubles(notdrawy)});
					dataset.addSeries("point", new double[][]{{0},{0}});
					return dataset;
				}
			};
			
			new AbstractBubbleChart(leaguename,"(主胜率-客胜率)/平率 ","draw_value") {
				@Override
				public XYZDataset getXYZDataset() {
					DefaultXYZDataset dataset = new DefaultXYZDataset();
					dataset.addSeries("draw", new double[][]{NumberUtils.convertListDs2doubles(drawx),NumberUtils.convertListDs2doubles(drawy),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(drawx),NumberUtils.convertListDs2doubles(drawy), drawz)});
					dataset.addSeries("notdraw", new double[][]{NumberUtils.convertListDs2doubles(notdrawx),NumberUtils.convertListDs2doubles(notdrawy),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(notdrawx),NumberUtils.convertListDs2doubles(notdrawy), notdrawz)});
					dataset.addSeries("point", new double[][]{{0},{0},{0}});
					return dataset;
				}
			};
		}
	}
	
}
