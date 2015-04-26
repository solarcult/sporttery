package shil.lottery.seriously.research.sumpos;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.Frequency;

import shil.lottery.seriously.utils.AnalyzeUtil;
import shil.lottery.seriously.vo.LeaguePosition;
import shil.lottery.seriously.vo.TeamValuePosition;
import shil.lottery.seriously.vo.WholeMatches;
import shil.lottery.sport.db.SportMetaDaoImpl;
import shil.lottery.sport.entity.VSTeam;

/**
 * 主要进行察看主队与客队联赛积分差值所反映的胜负平结果.
 * @author yuanshun.sl
 * @date 2015-April-26 21:43
 */
public class LeagueSumTest {
	public static void main(String[] args){
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		
		final List<Double> xs = new ArrayList<Double>();
		final List<Double> ys = new ArrayList<Double>();
		Frequency win = new Frequency();
		Frequency draw = new Frequency();
		Frequency lose = new Frequency();
		Frequency wincool = new Frequency();
		Frequency drawcool = new Frequency();
		Frequency losecool = new Frequency();
		
		for(int i=0;i<vsTeams.size();i++){
			VSTeam vsTeam = vsTeams.get(i);
			WholeMatches wholeMatches = WholeMatches.analyzeWholeMatches(vsTeams.subList(0, i));
			LeaguePosition leaguePosition = LeaguePosition.analyzeOneLeague(vsTeam.getLeague(), wholeMatches);
			if(leaguePosition==null) continue;
			//联赛中排序的队伍比例必须超过这个值,才算有效
			if(leaguePosition.percentOfTeam() < 0.925) continue;
			
			TeamValuePosition teamValuePositionA = leaguePosition.getTeamValuePosition(vsTeam.getVs()[0]);
			TeamValuePosition teamValuePositionB = leaguePosition.getTeamValuePosition(vsTeam.getVs()[1]);
			if(teamValuePositionA == null || teamValuePositionB == null) continue;
			xs.add((double) (teamValuePositionA.getValue()-teamValuePositionB.getValue()));
			ys.add((double) vsTeam.getMatch_Result());
			if(vsTeam.getMatch_Result()==AnalyzeUtil.win){
				win.addValue(teamValuePositionA.getValue()-teamValuePositionB.getValue());
				wincool.addValue(teamValuePositionA.getCool()-teamValuePositionB.getCool());
			}else if(vsTeam.getMatch_Result() == AnalyzeUtil.draw){
				draw.addValue(teamValuePositionA.getValue()-teamValuePositionB.getValue());
				drawcool.addValue(teamValuePositionA.getCool()-teamValuePositionB.getCool());
			}else{
				lose.addValue(teamValuePositionA.getValue()-teamValuePositionB.getValue());
				losecool.addValue(teamValuePositionA.getCool()-teamValuePositionB.getCool());
			}
		}
		
		 AnalyzeUtil.frequencyDescriptor(win);
		 AnalyzeUtil.frequencyDescriptor(draw);
		 AnalyzeUtil.frequencyDescriptor(lose);
		 AnalyzeUtil.frequencyDescriptor(wincool);
		 AnalyzeUtil.frequencyDescriptor(drawcool);
		 AnalyzeUtil.frequencyDescriptor(losecool);
		 
		/*
		new AbstractScatterPlotGraphic("比赛差值测试","差值","胜负平"){
			@Override
			public XYDataset getDeltaCards() {
				DefaultXYDataset dataset = new DefaultXYDataset();
				double[][] dot = new double[][]{{0},{0}};
				dataset.addSeries("0,0", dot);
				dataset.addSeries("see", new double[][]{NumberUtils.convertListDs2doubles(xs),NumberUtils.convertListDs2doubles(ys)});
//				dataset.addSeries("draw", new double[][]{NumberUtils.convertListDs2doubles(drawValues),AnalyzeUtil.providerDoubleArrayValues(drawValues.size(),1)});
//				dataset.addSeries("lose", new double[][]{NumberUtils.convertListDs2doubles(loseValues),AnalyzeUtil.providerDoubleArrayValues(loseValues.size(),-1)});
				return dataset;
			}
		};
		*/
	}
}
