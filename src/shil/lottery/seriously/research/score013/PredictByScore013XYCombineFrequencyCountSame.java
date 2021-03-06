package shil.lottery.seriously.research.score013;

import java.util.List;

import org.apache.commons.math3.stat.Frequency;

import shil.lottery.seriously.research.Guess013;
import shil.lottery.seriously.research.evaluators.r013.Abstract013Evaluator;
import shil.lottery.seriously.utils.AnalyzeUtil;
import shil.lottery.seriously.utils.EvaluatorRecorder;
import shil.lottery.seriously.vo.Score013;
import shil.lottery.seriously.vo.VSTeamScore013;
import shil.lottery.sport.db.Score013DaoImpl;
import shil.lottery.sport.entity.VSTeam;

/**
 * 
	all done,this is result:
	PredictResultAnalyze [bingoFrequency=
	Value 	 Freq. 	 Pct. 	 Cum Pct. 
	预测正确	1454	45%	45%
	预测错误	1788	55%	100%
	, result013Frequency=
	Value 	 Freq. 	 Pct. 	 Cum Pct. 
	预测正确:0	2	0%	0%
	预测正确:1	10	0%	0%
	预测正确:3	1442	44%	45%
	预测错误:0->1	3	0%	45%
	预测错误:0->3	4	0%	45%
	预测错误:1->0	10	0%	45%
	预测错误:1->3	14	0%	46%
	预测错误:3->0	900	28%	74%
	预测错误:3->1	857	26%	100%
	
	----------------------
	
	加入host和guest的计算
	
	all done,this is result:
	PredictResultAnalyze [bingoFrequency=
	Value 	 Freq. 	 Pct. 	 Cum Pct. 
	预测正确	1271	45%	45%
	预测错误	1565	55%	100%
	, result013Frequency=
	Value 	 Freq. 	 Pct. 	 Cum Pct. 
	预测正确:0	5	0%	0%
	预测正确:1	1	0%	0%
	预测正确:3	1265	45%	45%
	预测错误:0->1	5	0%	45%
	预测错误:0->3	7	0%	45%
	预测错误:1->0	6	0%	45%
	预测错误:1->3	6	0%	46%
	预测错误:3->0	801	28%	74%
	预测错误:3->1	740	26%	100%

 * 将不同xy组合的结果,投票选出最多的一个,做为预测结果. 45%左右胜率
 * @author LiangJingJing
 * @date May 20, 2015 9:43:12 PM
 */
public class PredictByScore013XYCombineFrequencyCountSame extends Abstract013Evaluator{

	@Override
	public int guess013(List<VSTeam> vsTeams, VSTeam vsTeam) {
		
		/*
		Score013AnalyzeProbility score013AnalyzeProbility = Score013AnalyzeProbility.analyzeScore013AnalyzeProbility(vsTeams);
		if(!score013AnalyzeProbility.isSampleAvailable()) return Guess013.NotAvaliable;
		VSTeamScore013 vsTeamScore013 = VSTeamScore013.calculateVSTeamScore013(vsTeams, vsTeam);
		if(!vsTeamScore013.isAvaliable()) return Guess013.NotAvaliable;
		Score013XYCombineFrequency score013xyCombineFrequency = Score013XYCombineFrequency.buildScore013XYCombineFrequency(score013AnalyzeProbility, vsTeamScore013);
		if(!score013xyCombineFrequency.isAvaliable()) return Guess013.NotAvaliable;
		 */
		Score013 score013 = Score013DaoImpl.queryScore013ByVsTeamId(vsTeam.getId());
		if(score013 == null) return Guess013.NotAvaliable;
		Score013XYCombineFrequency score013xyCombineFrequency = Score013XYCombineFrequency.buildScore013XYCombineFrequency(score013);
		
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
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("getGsd_lsd:"+score013xyCombineFrequency.getGsd_lsd());
		stringBuilder.append("getGsd_agbl:"+score013xyCombineFrequency.getGsd_agbl());
		stringBuilder.append("getGsd_albg:"+score013xyCombineFrequency.getGsd_albg());
		stringBuilder.append("getLsd_agbl:"+score013xyCombineFrequency.getLsd_agbl());
		stringBuilder.append("getLsd_albg:"+score013xyCombineFrequency.getLsd_albg());
		stringBuilder.append("getAgbl_albg:"+score013xyCombineFrequency.getAgbl_albg());
		stringBuilder.append("finally result: "+resultF);

		if(output2file) EvaluatorRecorder.getEvaluatorRecorder().putContent(vsTeam, stringBuilder.toString());
		
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
