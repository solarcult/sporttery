package shil.lottery.seriously.research.score013;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import shil.lottery.seriously.research.Guess013;
import shil.lottery.seriously.research.evaluators.r013.Abstract013Evaluator;
import shil.lottery.seriously.utils.AnalyzeUtil;
import shil.lottery.sport.db.ScoreBMWDaoImpl;
import shil.lottery.sport.entity.StatusCounter;
import shil.lottery.sport.entity.VSTeam;

public class PrepareScoreBMWData extends Abstract013Evaluator {

	@Override
	public int guess013(List<VSTeam> vsTeams, VSTeam vsTeam) {
		/*
		Score013AnalyzeProbility score013AnalyzeProbility = Score013AnalyzeProbility.analyzeScore013AnalyzeProbility(vsTeams);
		if(!score013AnalyzeProbility.isSampleAvailable()) return Guess013.NotAvaliable;
		VSTeamScore013 vsTeamScore013 = VSTeamScore013.calculateVSTeamScore013(vsTeams, vsTeam);
		if(!vsTeamScore013.isAvaliable()) return Guess013.NotAvaliable;
		Score013XYCombineFrequency score013xyCombineFrequency = Score013XYCombineFrequency.buildScore013XYCombineFrequency(score013AnalyzeProbility, vsTeamScore013);
		if(!score013xyCombineFrequency.isAvaliable()) return Guess013.NotAvaliable;
		
		Score013DaoImpl.insertScore013("Score013XYCombineFrequency", vsTeam, vsTeamScore013, score013xyCombineFrequency);
		*/
		
		List<StatusCounter> scs = new ArrayList<StatusCounter>();
		
		scs.add(new StatusCounter(Guess013.win, vsTeam.getBetCalcRate_web()[0]));
		scs.add(new StatusCounter(Guess013.draw, vsTeam.getBetCalcRate_web()[1]));
		scs.add(new StatusCounter(Guess013.lose, vsTeam.getBetCalcRate_web()[2]));
		
		Collections.sort(scs);
		
		StatusCounter big = scs.get(0);
		StatusCounter medium = scs.get(1);
		StatusCounter small = scs.get(2);
		
		double odd_rate_bm = big.getProb() - medium.getProb();
		double odd_rate_ms = medium.getProb() - small.getProb();
		double odd_rate_bms = odd_rate_bm / odd_rate_ms;
		
		if(odd_rate_bm==0 || odd_rate_ms==0)
			return Guess013.NotAvaliable;
		String odd_rate_bingo = (big.getResult() == vsTeam.getMatch_Result())?"BINGO":"NOTBINGO";
		
		Score013BMW score013bmw = new Score013BMW();
		score013bmw.setId(vsTeam.getId());
		score013bmw.setName("only odd");
		score013bmw.setOdd_rate_bm(odd_rate_bm);
		score013bmw.setOdd_rate_ms(odd_rate_ms);
		score013bmw.setOdd_rate_bms(odd_rate_bms);
		score013bmw.setOdd_rate_bingo(odd_rate_bingo);
		score013bmw.setResult(String.valueOf(vsTeam.getMatch_Result()));
		
		ScoreBMWDaoImpl.insertScoreBMSOnlyOdd(score013bmw);
		
		return Guess013.NotAvaliable;
	}
	
	public static void main(String[] args){
		new PrepareScoreBMWData().startEvaluator();
	}

}
