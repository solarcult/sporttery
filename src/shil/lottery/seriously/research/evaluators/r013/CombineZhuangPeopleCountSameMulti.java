package shil.lottery.seriously.research.evaluators.r013;

import java.util.List;

import shil.lottery.seriously.research.Guess013;
import shil.lottery.seriously.research.score013.PredictByScore013XYCombineFrequencyCountSame;
import shil.lottery.seriously.research.score013.PredictByScore013XYCombineFrequencyMulti;
import shil.lottery.seriously.utils.EvaluatorRecorder;
import shil.lottery.sport.entity.VSTeam;

/**
 * 
	all done,this is result:
	PredictResultAnalyze [bingoFrequency=
	Value 	 Freq. 	 Pct. 	 Cum Pct. 
	预测正确	1049	54%	54%
	预测错误	889	46%	100%
	, result013Frequency=
	Value 	 Freq. 	 Pct. 	 Cum Pct. 
	预测正确:3	1049	54%	54%
	预测错误:3->0	388	20%	74%
	预测错误:3->1	501	26%	100%
 * @author LiangJingJing
 * @date May 24, 2015 11:52:07 AM
 */
public class CombineZhuangPeopleCountSameMulti extends Abstract013Evaluator {

	@Override
	public int guess013(List<VSTeam> vsTeams, VSTeam vsTeam) {
		ZhuangPredictEvalutor zhuangPredictEvalutor = new ZhuangPredictEvalutor();
		PeoplePredictEvalutor peoplePredictEvalutor = new PeoplePredictEvalutor();
		PredictByScore013XYCombineFrequencyCountSame predictByScore013XYCombineFrequencyCountSame = new PredictByScore013XYCombineFrequencyCountSame();
		PredictByScore013XYCombineFrequencyMulti predictByScore013XYCombineFrequencyMulti = new PredictByScore013XYCombineFrequencyMulti();
		int result = Guess013.NotAvaliable;
		StringBuilder stringBuilder = new StringBuilder();
		int zhuresult = zhuangPredictEvalutor.guess013(vsTeams, vsTeam);
		int peoresult = peoplePredictEvalutor.guess013(vsTeams, vsTeam);
		stringBuilder.append(zhuresult + " vs " +peoresult);
		if(zhuresult == peoresult){
			int multi = predictByScore013XYCombineFrequencyMulti.guess013(vsTeams, vsTeam);
			stringBuilder.append(" vs "+multi);
			if(zhuresult == multi){
				int countsame = predictByScore013XYCombineFrequencyCountSame.guess013(vsTeams, vsTeam);
				stringBuilder.append(" vs "+countsame);
				if(zhuresult == countsame){
					result = zhuresult;
				}
			}
		}
		EvaluatorRecorder.getEvaluatorRecorder().putContent(vsTeam, stringBuilder.toString());
		return result;
	}
	
	public static void main(String[] args){
		new CombineZhuangPeopleCountSameMulti().startEvaluator();
	}

}
