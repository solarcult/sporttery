package shil.lottery.seriously.research.score013;

import java.util.List;

import shil.lottery.seriously.research.Guess013;
import shil.lottery.seriously.research.evaluators.Abstract013Evaluators;
import shil.lottery.seriously.utils.AnalyzeUtil;
import shil.lottery.seriously.vo.VSTeamScore013;
import shil.lottery.sport.entity.VSTeam;

/**
 * 将xy组合的所有结果相乘,有一些武断,44%左右胜率.
 * @author LiangJingJing
 * @date May 20, 2015 9:44:01 PM
 */
public class PredictByScore013XYCombineFrequencyMulti extends Abstract013Evaluators{

	@Override
	public int guess013(List<VSTeam> vsTeams, VSTeam vsTeam) {
		
		Score013AnalyzeProbility score013AnalyzeProbility = Score013AnalyzeProbility.analyzeScore013AnalyzeProbility(vsTeams);
		if(!score013AnalyzeProbility.isSampleAvailable()) return Guess013.NotAvaliable;
		VSTeamScore013 vsTeamScore013 = VSTeamScore013.calculateVSTeamScore013(vsTeams, vsTeam);
		if(!vsTeamScore013.isAvaliable()) return Guess013.NotAvaliable;
		Score013XYCombineFrequency score013xyCombineFrequency = Score013XYCombineFrequency.buildScore013XYCombineFrequency(score013AnalyzeProbility, vsTeamScore013);
		if(!score013xyCombineFrequency.isAvaliable()) return Guess013.NotAvaliable;

		double winV = getResultTypeValue(score013xyCombineFrequency,Guess013.winS);
		double drawV = getResultTypeValue(score013xyCombineFrequency,Guess013.drawS);
		double loseV = getResultTypeValue(score013xyCombineFrequency,Guess013.loseS);
		
		return AnalyzeUtil.get013WDLresult(winV, drawV, loseV);
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
		new PredictByScore013XYCombineFrequencyMulti().startEvaluator();
	}
}
