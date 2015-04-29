package shil.lottery.seriously.research.result013.correlation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.math3.stat.Frequency;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYZDataset;

import shil.lottery.seriously.utils.AbstractBubbleChart;
import shil.lottery.seriously.utils.AnalyzeUtil;
import shil.lottery.seriously.vo.WholeMatches;
import shil.lottery.sport.entity.VSTeam;
import shil.lottery.sport.statistics.NumberUtils;

/**
 * 这次试验站在主客的赔率上来看是否有规律,希望是偏向两个角的聚集多一些,
 * 整体来看,两边的还算是靠谱,偶尔有逆转,平局算正常.
 * 所有数据都对主队有利,所以左上角会有一切平局或者逆袭的情况.右下角如果比较稳定,则说明比较踏实的分析.
 * 首先要分析区块,右下角那些区域是比较踏实的,左上角哪些是比较稳定的.其次是最不可捉摸的中间地带,它的界限在哪里,不同联赛和队伍有不同的界限.
 * 最大的问题是怎么分离中间的数据,中间焦灼的数据,很难判断,需要其他方案.
 * 29日,增加了Z纬度来判断
 * @author LiangJingJing
 * @date Apr 21, 2015 10:11:13 PM
 */
public class WLRate2ResultTest {

	public static void main(String[] args) {
		WholeMatches wholeMatches = AnalyzeUtil.analyzeWholeRecords();
		Set<String> leaguenames = wholeMatches.getLeaguesNames();
		
		for(String leaguename : leaguenames){
			List<VSTeam> vsTeams = wholeMatches.getLeaguesVSTeamsMap().get(leaguename);
			if(vsTeams.size()<AnalyzeUtil.leagalMinMatches) continue;
			final List<Double> winx = new ArrayList<Double>();
			final List<Double> winy = new ArrayList<Double>();
			final List<Double> drawx = new ArrayList<Double>();
			final List<Double> drawy = new ArrayList<Double>();
			final List<Double> losex = new ArrayList<Double>();
			final List<Double> losey = new ArrayList<Double>();
			final Frequency winz = new Frequency();
			final Frequency drawz = new Frequency();
			final Frequency losez = new Frequency();
			for(VSTeam vsTeam : vsTeams){
				if(vsTeam.getMatch_Result() == AnalyzeUtil.win){
					winx.add(vsTeam.getBetCalcRate_web()[0]*AnalyzeUtil.MultRate100);
					winy.add(vsTeam.getBetCalcRate_web()[2]*AnalyzeUtil.MultRate100);
					winz.addValue(vsTeam.getBetCalcRate_web()[0]*AnalyzeUtil.MultRate100+AnalyzeUtil.Connect+vsTeam.getBetCalcRate_web()[2]*AnalyzeUtil.MultRate100);
				}else if(vsTeam.getMatch_Result() == AnalyzeUtil.draw){
					drawx.add(vsTeam.getBetCalcRate_web()[0]*AnalyzeUtil.MultRate100);
					drawy.add(vsTeam.getBetCalcRate_web()[2]*AnalyzeUtil.MultRate100);
					drawz.addValue(vsTeam.getBetCalcRate_web()[0]*AnalyzeUtil.MultRate100+AnalyzeUtil.Connect+vsTeam.getBetCalcRate_web()[2]*AnalyzeUtil.MultRate100);
				}else{
					losex.add(vsTeam.getBetCalcRate_web()[0]*AnalyzeUtil.MultRate100);
					losey.add(vsTeam.getBetCalcRate_web()[2]*AnalyzeUtil.MultRate100);
					losez.addValue(vsTeam.getBetCalcRate_web()[0]*AnalyzeUtil.MultRate100+AnalyzeUtil.Connect+vsTeam.getBetCalcRate_web()[2]*AnalyzeUtil.MultRate100);
				}
			}
			
			new AbstractBubbleChart(leaguename+"_win","主队的胜率","客队的胜率") {
				
				@Override
				public XYZDataset getXYZDataset() {
					DefaultXYZDataset dataset = new DefaultXYZDataset();
					dataset.addSeries("win", new double[][]{NumberUtils.convertListDs2doubles(winx),NumberUtils.convertListDs2doubles(winy),AnalyzeUtil.getFrequencyZvaluebyXYDoublesCount(NumberUtils.convertListDs2doubles(winx),NumberUtils.convertListDs2doubles(winy), winz)});
					return dataset;
				}
			};
			
			new AbstractBubbleChart(leaguename+"_draw","主队的胜率","客队的胜率") {
				
				@Override
				public XYZDataset getXYZDataset() {
					DefaultXYZDataset dataset = new DefaultXYZDataset();
					dataset.addSeries("win", new double[][]{{0},{0},{0}});
					dataset.addSeries("draw", new double[][]{NumberUtils.convertListDs2doubles(drawx),NumberUtils.convertListDs2doubles(drawy),AnalyzeUtil.getFrequencyZvaluebyXYDoublesCount(NumberUtils.convertListDs2doubles(drawx),NumberUtils.convertListDs2doubles(drawy), drawz)});
					return dataset;
				}
			};
			
			new AbstractBubbleChart(leaguename+"_lose","主队的胜率","客队的胜率") {
				
				@Override
				public XYZDataset getXYZDataset() {
					DefaultXYZDataset dataset = new DefaultXYZDataset();
					dataset.addSeries("win", new double[][]{{0},{0},{0}});
					dataset.addSeries("draw", new double[][]{{0},{0},{0}});
					dataset.addSeries("lose", new double[][]{NumberUtils.convertListDs2doubles(losex),NumberUtils.convertListDs2doubles(losey),AnalyzeUtil.getFrequencyZvaluebyXYDoublesCount(NumberUtils.convertListDs2doubles(losex),NumberUtils.convertListDs2doubles(losey), losez)});
					return dataset;
				}
			};
			
			new AbstractBubbleChart(leaguename,"主队的胜率","客队的胜率") {
				
				@Override
				public XYZDataset getXYZDataset() {
					DefaultXYZDataset dataset = new DefaultXYZDataset();
					dataset.addSeries("win", new double[][]{NumberUtils.convertListDs2doubles(winx),NumberUtils.convertListDs2doubles(winy),AnalyzeUtil.getFrequencyZvaluebyXYDoublesCount(NumberUtils.convertListDs2doubles(winx),NumberUtils.convertListDs2doubles(winy), winz)});
					dataset.addSeries("draw", new double[][]{NumberUtils.convertListDs2doubles(drawx),NumberUtils.convertListDs2doubles(drawy),AnalyzeUtil.getFrequencyZvaluebyXYDoublesCount(NumberUtils.convertListDs2doubles(drawx),NumberUtils.convertListDs2doubles(drawy), drawz)});
					dataset.addSeries("lose", new double[][]{NumberUtils.convertListDs2doubles(losex),NumberUtils.convertListDs2doubles(losey),AnalyzeUtil.getFrequencyZvaluebyXYDoublesCount(NumberUtils.convertListDs2doubles(losex),NumberUtils.convertListDs2doubles(losey), losez)});
					return dataset;
				}
			};
		}
	}
}
