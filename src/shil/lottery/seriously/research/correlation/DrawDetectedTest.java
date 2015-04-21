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
 * 根据|主胜率-客胜率|/平率 试图找到不平局的规律,貌似比较难.
 * @author LiangJingJing
 * @date Apr 21, 2015 11:07:25 PM
 */
public class DrawDetectedTest {

	public static void main(String[] args) {
		WholeMatches wholeMatches = AnalyzeUtil.analyzeWholeRecords();
		Set<String> leaguenames = wholeMatches.getLeaguesNames();
		
		for(String leaguename : leaguenames){
			List<VSTeam> vsTeams = wholeMatches.getLeaguesMap().get(leaguename);
			if(vsTeams.size()<AnalyzeUtil.leagalMinMatches) continue;
			final List<Double> notdraw = new ArrayList<Double>();
			final List<Double> draw = new ArrayList<Double>();

			for(VSTeam vsTeam : vsTeams){
				if(vsTeam.getMatch_Result() == 1){
					draw.add(Math.abs(vsTeam.getBetCalcRate_web()[0]-vsTeam.getBetCalcRate_web()[2])/vsTeam.getBetCalcRate_web()[1]);
				}else{
					notdraw.add(Math.abs(vsTeam.getBetCalcRate_web()[0]-vsTeam.getBetCalcRate_web()[2])/vsTeam.getBetCalcRate_web()[1]);
				}
			}
			
			new AbstractScatterPlotGraphic(leaguename,"绝对值比","平或不平"){
				@Override
				public XYDataset getDeltaCards() {
					DefaultXYDataset dataset = new DefaultXYDataset();
					dataset.addSeries("notdraw", new double[][]{NumberUtils.convertListDs2doubles(notdraw),BasicWinLoseRateTest.providerValues(notdraw.size(), 1)});
					dataset.addSeries("draw", new double[][]{NumberUtils.convertListDs2doubles(draw),BasicWinLoseRateTest.providerValues(draw.size(), -1)});
					return dataset;
				}
			};
		}
	}

}
