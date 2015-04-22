package shil.lottery.seriously.research.correlation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

import shil.lottery.seriously.utils.AnalyzeUtil;
import shil.lottery.seriously.vo.WholeMatches;
import shil.lottery.sport.entity.VSTeam;
import shil.lottery.sport.graphic.AbstractScatterPlotGraphic;
import shil.lottery.sport.statistics.NumberUtils;

/**
 * 这次试验站在主客的赔率上来看是否有规律,希望是偏向两个角的聚集多一些,
 * 整体来看,两边的还算是靠谱,偶尔有逆转,平局算正常.
 * 所有数据都对主队有利,所以左上角会有一切平局或者逆袭的情况.右下角如果比较稳定,则说明比较踏实的分析.
 * 首先要分析区块,右下角那些区域是比较踏实的,左上角哪些是比较稳定的.其次是最不可捉摸的中间地带,它的界限在哪里,不同联赛和队伍有不同的界限.
 * 最大的问题是怎么分离中间的数据,中间焦灼的数据,很难判断,需要其他方案.
 * @author LiangJingJing
 * @date Apr 21, 2015 10:11:13 PM
 */
public class WLRate2ResultTest {

	public static void main(String[] args) {
		WholeMatches wholeMatches = AnalyzeUtil.analyzeWholeRecords();
		Set<String> leaguenames = wholeMatches.getLeaguesNames();
		
		for(String leaguename : leaguenames){
			List<VSTeam> vsTeams = wholeMatches.getLeaguesMap().get(leaguename);
			if(vsTeams.size()<AnalyzeUtil.leagalMinMatches) continue;
			final List<Double> winx = new ArrayList<Double>();
			final List<Double> winy = new ArrayList<Double>();
			final List<Double> drawx = new ArrayList<Double>();
			final List<Double> drawy = new ArrayList<Double>();
			final List<Double> losex = new ArrayList<Double>();
			final List<Double> losey = new ArrayList<Double>();
			for(VSTeam vsTeam : vsTeams){
				if(vsTeam.getMatch_Result() == AnalyzeUtil.win){
					winx.add(vsTeam.getBetCalcRate_web()[0]);
					winy.add(vsTeam.getBetCalcRate_web()[2]);
				}else if(vsTeam.getMatch_Result() == AnalyzeUtil.draw){
					drawx.add(vsTeam.getBetCalcRate_web()[0]);
					drawy.add(vsTeam.getBetCalcRate_web()[2]);
				}else{
					losex.add(vsTeam.getBetCalcRate_web()[0]);
					losey.add(vsTeam.getBetCalcRate_web()[2]);
				}
			}
			
			new AbstractScatterPlotGraphic(leaguename,"主队的胜率","客队的胜率"){
				@Override
				public XYDataset getDeltaCards() {
					DefaultXYDataset dataset = new DefaultXYDataset();
					dataset.addSeries("win", new double[][]{NumberUtils.convertListDs2doubles(winx),NumberUtils.convertListDs2doubles(winy)});
					dataset.addSeries("draw", new double[][]{NumberUtils.convertListDs2doubles(drawx),NumberUtils.convertListDs2doubles(drawy)});
					dataset.addSeries("lose", new double[][]{NumberUtils.convertListDs2doubles(losex),NumberUtils.convertListDs2doubles(losey)});
					return dataset;
				}
			};
		}
	}
}
