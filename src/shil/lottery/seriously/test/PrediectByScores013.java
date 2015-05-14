package shil.lottery.seriously.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.Frequency;

import shil.lottery.seriously.vo.Score013AnalyzeProbility;
import shil.lottery.seriously.vo.VSTeamScore013;
import shil.lottery.sport.db.SportMetaDaoImpl;
import shil.lottery.sport.entity.VSTeam;

public class PrediectByScores013 {

	public static void main(String[] args){
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		Frequency resultRecords = new Frequency();
		for(int i=0;i<vsTeams.size();i++){
			VSTeam vsTeam = vsTeams.get(i);
			Score013AnalyzeProbility sap = Score013AnalyzeProbility.analyzeScore013AnalyzeProbility(vsTeams.subList(0, i));
			if(!sap.isSampleAvailable()) continue;
			VSTeamScore013 currentTeamCalc = VSTeamScore013.calculateVSTeamScore013(vsTeams.subList(0,i), vsTeam);
			if(!currentTeamCalc.isAvaliable()) continue;
			
			//预测与记录
		}
	}
	
}
