package shil.lottery.seriously.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.Frequency;

import shil.lottery.seriously.vo.FiveQuadrant;
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
	
	public static void getUwant(Score013AnalyzeProbility score013AnalyzeProbility, VSTeamScore013 vsTeamScore013){
		List<FiveQuadrant> fiveQuadrants = score013AnalyzeProbility.getDescriptorFiveQuadrants();
		//gsd vs lsd
		Frequency gsd_lsd = fiveQuadrants.get(1).getQuadrantFrequency(vsTeamScore013.getGsd(), vsTeamScore013.getLsd());
		//gsd vs agbl
		Frequency gsd_agbl = fiveQuadrants.get(2).getQuadrantFrequency(vsTeamScore013.getGsd(), vsTeamScore013.getAgbl());
		//gsd vs albg
		Frequency gsd_albg = fiveQuadrants.get(3).getQuadrantFrequency(vsTeamScore013.getGsd(), vsTeamScore013.getAlbg());
		//lsd vs agbl
		Frequency lsd_agbl = fiveQuadrants.get(4).getQuadrantFrequency(vsTeamScore013.getLsd(), vsTeamScore013.getAgbl());
		//lsd vs albg
		Frequency lsd_albg = fiveQuadrants.get(5).getQuadrantFrequency(vsTeamScore013.getLsd(), vsTeamScore013.getAlbg());
		//agbl vs albg
		Frequency agbl_albg = fiveQuadrants.get(6).getQuadrantFrequency(vsTeamScore013.getAgbl(),vsTeamScore013.getAlbg());
	}
	
	
}
