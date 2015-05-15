package shil.lottery.seriously.vo;

import java.util.List;

import org.apache.commons.math3.stat.Frequency;

public class Score013XYCombineFrequency {

	private Frequency gsd_lsd;
	private Frequency gsd_agbl;
	private Frequency gsd_albg;
	private Frequency lsd_agbl;
	private Frequency lsd_albg;
	private Frequency agbl_albg;
	
	private boolean isAvaliable;
	
	private Score013XYCombineFrequency(){
		this.isAvaliable = false;
	}
	
	public static Score013XYCombineFrequency buildScore013XYCombineFrequency(Score013AnalyzeProbility score013AnalyzeProbility, VSTeamScore013 vsTeamScore013){
		Score013XYCombineFrequency score013xyCombineFrequency = new Score013XYCombineFrequency();
		
		if(!score013AnalyzeProbility.isSampleAvailable() || !vsTeamScore013.isAvaliable()) return score013xyCombineFrequency; 
		
		score013xyCombineFrequency.isAvaliable = true;
		
		List<FiveQuadrant> fiveQuadrants = score013AnalyzeProbility.getDescriptorFiveQuadrants();
		//gsd vs lsd
		score013xyCombineFrequency.gsd_lsd = fiveQuadrants.get(1).getQuadrantFrequency(vsTeamScore013.getGsd(), vsTeamScore013.getLsd());
		//gsd vs agbl
		score013xyCombineFrequency.gsd_agbl = fiveQuadrants.get(2).getQuadrantFrequency(vsTeamScore013.getGsd(), vsTeamScore013.getAgbl());
		//gsd vs albg
		score013xyCombineFrequency.gsd_albg = fiveQuadrants.get(3).getQuadrantFrequency(vsTeamScore013.getGsd(), vsTeamScore013.getAlbg());
		//lsd vs agbl
		score013xyCombineFrequency.lsd_agbl = fiveQuadrants.get(4).getQuadrantFrequency(vsTeamScore013.getLsd(), vsTeamScore013.getAgbl());
		//lsd vs albg
		score013xyCombineFrequency.lsd_albg = fiveQuadrants.get(5).getQuadrantFrequency(vsTeamScore013.getLsd(), vsTeamScore013.getAlbg());
		//agbl vs albg
		score013xyCombineFrequency.agbl_albg = fiveQuadrants.get(6).getQuadrantFrequency(vsTeamScore013.getAgbl(),vsTeamScore013.getAlbg());
		
		return score013xyCombineFrequency;
	}

	public Frequency getGsd_lsd() {
		return gsd_lsd;
	}

	public Frequency getGsd_agbl() {
		return gsd_agbl;
	}

	public Frequency getGsd_albg() {
		return gsd_albg;
	}

	public Frequency getLsd_agbl() {
		return lsd_agbl;
	}

	public Frequency getLsd_albg() {
		return lsd_albg;
	}

	public Frequency getAgbl_albg() {
		return agbl_albg;
	}

	public boolean isAvaliable() {
		return isAvaliable;
	}
	
}
