package shil.lottery.seriously.research.evaluators;

import java.util.List;

import shil.lottery.seriously.utils.AnalyzeUtil;
import shil.lottery.sport.entity.VSTeam;

public class ZhuangPredictEvalutor extends Abstract013Evaluators {

	@Override
	public int guess013(List<VSTeam> vsTeams, VSTeam vsTeam) {
		
		return AnalyzeUtil.get013WDLresult(vsTeam.getBetCalcRate_web()[0], vsTeam.getBetCalcRate_web()[1], vsTeam.getBetCalcRate_web()[2]);
	}
	
	public static void main(String[] args){
		new ZhuangPredictEvalutor().startEvaluator();
	}

}
