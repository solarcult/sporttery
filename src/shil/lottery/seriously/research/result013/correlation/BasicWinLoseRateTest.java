package shil.lottery.seriously.research.result013.correlation;

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
 * 简单分析了联盟比赛中,不同队伍胜负平所占的比率造成的结果图
 * 发现没有太强的关联.but it's fun
 * @author LiangJingJing
 * @since 2015-04-21 20:37
 */
public class BasicWinLoseRateTest {

	public static void main(String[] args){
		WholeMatches wholeMatches = AnalyzeUtil.analyzeWholeRecords();
		
		Set<String> leaguenames = wholeMatches.getLeaguesNames();
		
		for(String leaguename : leaguenames){
			List<VSTeam> vsTeams = wholeMatches.getLeaguesVSTeamsMap().get(leaguename);
			if(vsTeams.size()<AnalyzeUtil.leagalMinMatches) continue;
			final List<Double> winValues = new ArrayList<Double>();
			final List<Double> drawValues = new ArrayList<Double>();
			final List<Double> loseValues = new ArrayList<Double>();
			for(VSTeam vsTeam : vsTeams){
				if(vsTeam.getMatch_Result() == AnalyzeUtil.win){
					winValues.add(vsTeam.getBetCalcRate_web()[0]);
					loseValues.add(vsTeam.getBetCalcRate_web()[2]);
				}else if(vsTeam.getMatch_Result() == AnalyzeUtil.draw){
					drawValues.add(vsTeam.getBetCalcRate_web()[1]);
				}else{
					winValues.add(vsTeam.getBetCalcRate_web()[2]);
					loseValues.add(vsTeam.getBetCalcRate_web()[0]);
				}
			}
			
			new AbstractScatterPlotGraphic(leaguename,"百分比","胜负平"){
				@Override
				public XYDataset getDeltaCards() {
					DefaultXYDataset dataset = new DefaultXYDataset();
					double[][] dot = new double[][]{{0},{0}};
					dataset.addSeries("0,0", dot);
					dataset.addSeries("win", new double[][]{NumberUtils.convertListDs2doubles(winValues),AnalyzeUtil.providerDoubleArrayValues(winValues.size(),3)});
					dataset.addSeries("draw", new double[][]{NumberUtils.convertListDs2doubles(drawValues),AnalyzeUtil.providerDoubleArrayValues(drawValues.size(),1)});
					dataset.addSeries("lose", new double[][]{NumberUtils.convertListDs2doubles(loseValues),AnalyzeUtil.providerDoubleArrayValues(loseValues.size(),-1)});
					return dataset;
				}
				
			};
		}
	}
}
