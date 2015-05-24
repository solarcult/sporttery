package shil.lottery.seriously.research.evaluators;

import java.util.List;

import shil.lottery.seriously.research.Guess013;
import shil.lottery.seriously.research.score013.PredictByScore013XYCombineFrequencyCountSame;
import shil.lottery.seriously.research.score013.PredictByScore013XYCombineFrequencyMulti;
import shil.lottery.sport.entity.VSTeam;

/**
	all done,this is result:
	PredictResultAnalyze [bingoFrequency=
	Value 	 Freq. 	 Pct. 	 Cum Pct. 
	预测正确	1449	45%	45%
	预测错误	1773	55%	100%
	, result013Frequency=
	Value 	 Freq. 	 Pct. 	 Cum Pct. 
	预测正确:0	2	0%	0%
	预测正确:1	9	0%	0%
	预测正确:3	1438	45%	45%
	预测错误:0->1	3	0%	45%
	预测错误:0->3	4	0%	45%
	预测错误:1->0	8	0%	45%
	预测错误:1->3	12	0%	46%
	预测错误:3->0	892	28%	73%
	预测错误:3->1	854	27%	100%
 * @author LiangJingJing
 * @date May 23, 2015 11:30:52 PM
 */
public class CombileScore013MultiCountSameEvaluator extends Abstract013Evaluators {

	@Override
	public int guess013(List<VSTeam> vsTeams, VSTeam vsTeam) {
		PredictByScore013XYCombineFrequencyCountSame predictByScore013XYCombineFrequencyCountSame = new PredictByScore013XYCombineFrequencyCountSame();
		PredictByScore013XYCombineFrequencyMulti predictByScore013XYCombineFrequencyMulti = new PredictByScore013XYCombineFrequencyMulti();
		int result = Guess013.NotAvaliable;
		int multi = predictByScore013XYCombineFrequencyMulti.guess013(vsTeams, vsTeam);
		int countsame = predictByScore013XYCombineFrequencyCountSame.guess013(vsTeams, vsTeam);
		if(multi==countsame){
			result = multi;
		}
		return result;
	}

	public static void main(String[] args) {
		new CombileScore013MultiCountSameEvaluator().startEvaluator();
	}
}
