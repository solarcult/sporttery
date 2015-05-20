package shil.lottery.seriously.research.score013;

import java.util.List;

import org.apache.commons.math3.stat.Frequency;

import shil.lottery.seriously.research.Guess013;
import shil.lottery.seriously.research.evaluators.AbstractEvaluators;
import shil.lottery.seriously.utils.AnalyzeUtil;
import shil.lottery.seriously.vo.VSTeamScore013;
import shil.lottery.sport.entity.VSTeam;

public class PredictByScore013XYCombineFrequencyCountSame extends AbstractEvaluators{

	@Override
	public int guess013(List<VSTeam> vsTeams, VSTeam vsTeam) {
		
		Score013AnalyzeProbility score013AnalyzeProbility = Score013AnalyzeProbility.analyzeScore013AnalyzeProbility(vsTeams);
		if(!score013AnalyzeProbility.isSampleAvailable()) return Guess013.NotAvaliable;
		VSTeamScore013 vsTeamScore013 = VSTeamScore013.calculateVSTeamScore013(vsTeams, vsTeam);
		if(!vsTeamScore013.isAvaliable()) return Guess013.NotAvaliable;
		Score013XYCombineFrequency score013xyCombineFrequency = Score013XYCombineFrequency.buildScore013XYCombineFrequency(score013AnalyzeProbility, vsTeamScore013);
		if(!score013xyCombineFrequency.isAvaliable()) return Guess013.NotAvaliable;

		//6种类型投票
		Frequency resultF = new Frequency();
		resultF.addValue(getResultS(score013xyCombineFrequency.getGsd_lsd()));
		resultF.addValue(getResultS(score013xyCombineFrequency.getGsd_agbl()));
		resultF.addValue(getResultS(score013xyCombineFrequency.getGsd_albg()));
		resultF.addValue(getResultS(score013xyCombineFrequency.getLsd_agbl()));
		resultF.addValue(getResultS(score013xyCombineFrequency.getLsd_albg()));
		resultF.addValue(getResultS(score013xyCombineFrequency.getAgbl_albg()));

		double winV = resultF.getPct(Guess013.winS);
		double drawV = resultF.getPct(Guess013.drawS);
		double loseV = resultF.getPct(Guess013.loseS);
		
		//取投票最高数
		return AnalyzeUtil.get013WDLresult(winV, drawV, loseV);
	}
	
	private static String getResultS(Frequency frequency){
		double w = frequency.getCount(Guess013.winS);
		double d = frequency.getCount(Guess013.drawS);
		double l = frequency.getCount(Guess013.loseS);
		
		return AnalyzeUtil.get013WDLresultS(w, d, l);
	}
	
	public static void main(String[] args){
		new PredictByScore013XYCombineFrequencyCountSame().startEvaluator();
	}
}
