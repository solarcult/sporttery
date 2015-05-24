package shil.lottery.seriously.research.evaluators;

import java.util.List;

import shil.lottery.seriously.utils.AnalyzeUtil;
import shil.lottery.sport.entity.VSTeam;

/**
 * 
	all done,this is result:
	PredictResultAnalyze [bingoFrequency=
	Value 	 Freq. 	 Pct. 	 Cum Pct. 
	预测正确	3024	49%	49%
	预测错误	3097	51%	100%
	, result013Frequency=
	Value 	 Freq. 	 Pct. 	 Cum Pct. 
	预测正确:0	758	12%	12%
	预测正确:1	219	4%	16%
	预测正确:3	2047	33%	49%
	预测错误:0->1	441	7%	57%
	预测错误:0->3	432	7%	64%
	预测错误:1->0	212	3%	67%
	预测错误:1->3	254	4%	71%
	预测错误:3->0	817	13%	85%
	预测错误:3->1	941	15%	100%
 * @author LiangJingJing
 * @date May 24, 2015 8:29:39 PM
 */
public class PeoplePredictEvalutor extends Abstract013Evaluators {

	@Override
	public int guess013(List<VSTeam> vsTeams, VSTeam vsTeam) {
		
		return AnalyzeUtil.get013WDLresult(vsTeam.getPeopleVote_rate()[0], vsTeam.getPeopleVote_rate()[1], vsTeam.getPeopleVote_rate()[2]);
	}
	
	public static void main(String[] args){
		new PeoplePredictEvalutor().startEvaluator();
	}

}
