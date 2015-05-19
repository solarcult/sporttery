package shil.lottery.seriously.research.score013;

import java.util.List;

import shil.lottery.seriously.research.Guess013;
import shil.lottery.seriously.research.evaluators.AbstractEvaluators;
import shil.lottery.seriously.utils.AnalyzeUtil;
import shil.lottery.seriously.vo.VSTeamScore013;
import shil.lottery.sport.entity.VSTeam;

public class PredictByScore013XYCombineFrequency extends AbstractEvaluators{

	@Override
	public int guess013(List<VSTeam> vsTeams, VSTeam vsTeam) {
		
		Score013AnalyzeProbility score013AnalyzeProbility = Score013AnalyzeProbility.analyzeScore013AnalyzeProbility(vsTeams);
		if(!score013AnalyzeProbility.isSampleAvailable()) return Guess013.NotAvaliable;
		VSTeamScore013 vsTeamScore013 = VSTeamScore013.calculateVSTeamScore013(vsTeams, vsTeam);
		if(!vsTeamScore013.isAvaliable()) return Guess013.NotAvaliable;
		Score013XYCombineFrequency score013xyCombineFrequency = Score013XYCombineFrequency.buildScore013XYCombineFrequency(score013AnalyzeProbility, vsTeamScore013);
		if(!score013xyCombineFrequency.isAvaliable()) return Guess013.NotAvaliable;
		int result = Guess013.NotAvaliable;

		double winV = getResultTypeValue(score013xyCombineFrequency,AnalyzeUtil.winS);
		double drawV = getResultTypeValue(score013xyCombineFrequency,AnalyzeUtil.drawS);
		double loseV = getResultTypeValue(score013xyCombineFrequency,AnalyzeUtil.loseS);
		double biggest = (winV > drawV) ? winV : drawV;
		biggest = (biggest > loseV) ? biggest : loseV;
		if (biggest == winV) result = AnalyzeUtil.win;
		else if (biggest == drawV) result = AnalyzeUtil.draw;
		else if (biggest == loseV) result = AnalyzeUtil.lose;
		else result = -2;
		
		return result;
	}
	
	/**
	 * 将所有组合的概率胜负平率都相乘计算,有点简单粗暴
	 * @param type
	 * @return
	 */
	private double getResultTypeValue(Score013XYCombineFrequency score013xyCombineFrequency,String type){
		//TODO see comment
		return score013xyCombineFrequency.getGsd_lsd().getPct(type)*score013xyCombineFrequency.getGsd_agbl().getPct(type)*score013xyCombineFrequency.getGsd_albg().getPct(type)*score013xyCombineFrequency.getLsd_agbl().getPct(type)*score013xyCombineFrequency.getLsd_albg().getPct(type)*score013xyCombineFrequency.getAgbl_albg().getPct(type);
	}
	
	public static void main(String[] args){
		new PredictByScore013XYCombineFrequency().startEvaluator();
	}
}
