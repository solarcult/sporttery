package shil.lottery.seriously.research.score013;

import java.util.List;

import shil.lottery.seriously.research.Guess013;
import shil.lottery.seriously.research.evaluators.Abstract013Evaluators;
import shil.lottery.seriously.vo.VSTeamScore013;
import shil.lottery.sport.db.Score013DaoImpl;
import shil.lottery.sport.entity.VSTeam;

public class PrepareScore013Data extends Abstract013Evaluators{

	@Override
	public int guess013(List<VSTeam> vsTeams, VSTeam vsTeam) {
		
		Score013AnalyzeProbility score013AnalyzeProbility = Score013AnalyzeProbility.analyzeScore013AnalyzeProbility(vsTeams);
		if(!score013AnalyzeProbility.isSampleAvailable()) return Guess013.NotAvaliable;
		VSTeamScore013 vsTeamScore013 = VSTeamScore013.calculateVSTeamScore013(vsTeams, vsTeam);
		if(!vsTeamScore013.isAvaliable()) return Guess013.NotAvaliable;
		Score013XYCombineFrequency score013xyCombineFrequency = Score013XYCombineFrequency.buildScore013XYCombineFrequency(score013AnalyzeProbility, vsTeamScore013);
		if(!score013xyCombineFrequency.isAvaliable()) return Guess013.NotAvaliable;
		
		Score013DaoImpl.insertScore013("Score013XYCombineFrequency", vsTeam, vsTeamScore013, score013xyCombineFrequency);
		
		return Guess013.NotAvaliable;
	}
	
	public static void main(String[] args) {
		new PrepareScore013Data().startEvaluator(false);
	}
}
