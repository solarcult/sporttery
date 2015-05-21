package shil.lottery.seriously.research.evaluators;

import java.util.List;

import shil.lottery.seriously.research.Guess013;
import shil.lottery.seriously.research.score013.PredictByScore013XYCombineFrequencyCountSame;
import shil.lottery.sport.entity.VSTeam;
/**
 * 
	all done,this is result:
	PredictResultAnalyze [bingoFrequency=
	Value 	 Freq. 	 Pct. 	 Cum Pct. 
	预测正确	1215	52%	52%
	预测错误	1112	48%	100%
	, result013Frequency=
	Value 	 Freq. 	 Pct. 	 Cum Pct. 
	预测正确:3	1215	52%	52%
	预测错误:3->0	493	21%	73%
	预测错误:3->1	619	27%	100%
 * @author LiangJingJing
 * @date May 20, 2015 11:53:06 PM
 */
public class CombineZhuangScore013CountSameEvaluator extends Abstract013Evaluators {

	@Override
	public int guess013(List<VSTeam> vsTeams, VSTeam vsTeam) {
		ZhuangPredictEvalutor zhuangPredictEvalutor = new ZhuangPredictEvalutor();
		PredictByScore013XYCombineFrequencyCountSame predictByScore013XYCombineFrequencyCountSame = new PredictByScore013XYCombineFrequencyCountSame();
		int result = Guess013.NotAvaliable;
		int zhuangResult = zhuangPredictEvalutor.guess013(vsTeams, vsTeam);
		int countSameResult = predictByScore013XYCombineFrequencyCountSame.guess013(vsTeams, vsTeam);
		if(zhuangResult==countSameResult)
			result = zhuangResult;
		return result;
	}

	public static void main(String[] args) {
		new CombineZhuangScore013CountSameEvaluator().startEvaluator();
	}

}
