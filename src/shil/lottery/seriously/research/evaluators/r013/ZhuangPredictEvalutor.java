package shil.lottery.seriously.research.evaluators.r013;

import java.util.List;

import shil.lottery.seriously.utils.AnalyzeUtil;
import shil.lottery.seriously.utils.EvaluatorRecorder;
import shil.lottery.sport.entity.VSTeam;
/**
 * 
	all done,this is result:
	PredictResultAnalyze [bingoFrequency=
	Value 	 Freq. 	 Pct. 	 Cum Pct. 
	预测正确	3105	51%	51%
	预测错误	3021	49%	100%
	, result013Frequency=
	Value 	 Freq. 	 Pct. 	 Cum Pct. 
	预测正确:0	798	13%	13%
	预测正确:3	2307	38%	51%
	预测错误:0->1	456	7%	58%
	预测错误:0->3	430	7%	65%
	预测错误:3->0	990	16%	81%
	预测错误:3->1	1145	19%	100%
 * 只根据庄家的预测胜率判断
 * @author LiangJingJing
 * @date May 21, 2015 10:20:21 PM
 */
public class ZhuangPredictEvalutor extends Abstract013Evaluator {

	@Override
	public int guess013(List<VSTeam> vsTeams, VSTeam vsTeam) {
		EvaluatorRecorder.getEvaluatorRecorder().putContent(vsTeam, vsTeam.getBetCalcRate_web()[0] + " v " +vsTeam.getBetCalcRate_web()[1]+" v "+ vsTeam.getBetCalcRate_web()[2]);
		return AnalyzeUtil.get013WDLresult(vsTeam.getBetCalcRate_web()[0], vsTeam.getBetCalcRate_web()[1], vsTeam.getBetCalcRate_web()[2]);
	}
	
	public static void main(String[] args){
		new ZhuangPredictEvalutor().startEvaluator();
	}

}
