package shil.lottery.seriously.research.evaluators;

import java.util.List;

import shil.lottery.seriously.utils.AnalyzeUtil;
import shil.lottery.sport.entity.VSTeam;

public class PeoplePredictEvalutor extends Abstract013Evaluators {

	@Override
	public int guess013(List<VSTeam> vsTeams, VSTeam vsTeam) {
		
		return AnalyzeUtil.get013WDLresult(vsTeam.getPeopleVote_rate()[0], vsTeam.getPeopleVote_rate()[1], vsTeam.getPeopleVote_rate()[2]);
	}
	
	public static void main(String[] args){
		new PeoplePredictEvalutor().startEvaluator();
	}

}
