package shil.lottery.seriously.research.score013;

import java.util.List;

import shil.lottery.seriously.research.Guess013;
import shil.lottery.seriously.research.evaluators.Abstract013Evaluators;
import shil.lottery.seriously.utils.AnalyzeUtil;
import shil.lottery.seriously.utils.EvaluatorRecorder;
import shil.lottery.seriously.vo.VSTeamScore013;
import shil.lottery.sport.entity.VSTeam;

/**
 * 
	all done,this is result:
	PredictResultAnalyze [bingoFrequency=
	Value 	 Freq. 	 Pct. 	 Cum Pct. 
	预测正确	1458	45%	45%
	预测错误	1784	55%	100%
	, result013Frequency=
	Value 	 Freq. 	 Pct. 	 Cum Pct. 
	预测正确:0	8	0%	0%
	预测正确:1	12	0%	1%
	预测正确:3	1438	44%	45%
	预测错误:0->1	4	0%	45%
	预测错误:0->3	6	0%	45%
	预测错误:1->0	12	0%	46%
	预测错误:1->3	16	0%	46%
	预测错误:3->0	892	28%	74%
	预测错误:3->1	854	26%	100%
	
	----------------
	
	使用用细分host和guest的进球数据的预测
	
	all done,this is result:
	PredictResultAnalyze [bingoFrequency=
	Value 	 Freq. 	 Pct. 	 Cum Pct. 
	预测正确	1262	44%	44%
	预测错误	1574	56%	100%
	, result013Frequency=
	Value 	 Freq. 	 Pct. 	 Cum Pct. 
	预测正确:0	8	0%	0%
	预测正确:1	1	0%	0%
	预测正确:3	1253	44%	44%
	预测错误:0->1	7	0%	45%
	预测错误:0->3	13	0%	45%
	预测错误:1->0	9	0%	46%
	预测错误:1->3	12	0%	46%
	预测错误:3->0	795	28%	74%
	预测错误:3->1	738	26%	100%
	
	
	
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

		double winV = getResultTypeValue(score013xyCombineFrequency,Guess013.winS) * 10000l;
		double drawV = getResultTypeValue(score013xyCombineFrequency,Guess013.drawS) * 10000l;
		double loseV = getResultTypeValue(score013xyCombineFrequency,Guess013.loseS) * 10000l;
		
		EvaluatorRecorder.getEvaluatorRecorder().putContent(vsTeam, winV +" vs " + drawV +" vs "+ loseV);
		
		return AnalyzeUtil.get013WDLresult(winV, drawV, loseV);
	}
	
	/**
	 * 将所有组合的概率胜负平率都相乘计算,有点简单粗暴
	 * @param type
	 * @return
	 */
	private double getResultTypeValue(Score013XYCombineFrequency score013xyCombineFrequency,String type){

		return score013xyCombineFrequency.getGsd_lsd().getPct(type)*score013xyCombineFrequency.getGsd_agbl().getPct(type)*score013xyCombineFrequency.getGsd_albg().getPct(type)*score013xyCombineFrequency.getLsd_agbl().getPct(type)*score013xyCombineFrequency.getLsd_albg().getPct(type)*score013xyCombineFrequency.getAgbl_albg().getPct(type);
	}
	
	public static void main(String[] args){
		new PredictByScore013XYCombineFrequencyMulti().startEvaluator();
	}
}
