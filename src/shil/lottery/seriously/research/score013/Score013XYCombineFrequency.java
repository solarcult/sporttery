package shil.lottery.seriously.research.score013;

import java.util.List;

import org.apache.commons.math3.stat.Frequency;

import shil.lottery.seriously.vo.VSTeamScore013;

/**
 * 将所有类型组合放在一起的类,记录几种x,y组合最终结果的类.
 * 每一个类型的Freqnecy都存有胜负平的概率情况
 * @author LiangJingJing
 * @date May 18, 2015 12:51:21 AM
 */
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
		
		//这里只提取了所属象限中胜负平的概率,没有考虑落在本象限所在的概率,应该为落在本象限*本象限胜负平的概率
		List<FiveQuadrant> fiveQuadrants = score013AnalyzeProbility.getDescriptorFiveQuadrants();
		//gsd vs lsd
		score013xyCombineFrequency.gsd_lsd = fiveQuadrants.get(0).getQuadrantFrequency(vsTeamScore013.getGsd(), vsTeamScore013.getLsd());
		//gsd vs agbl
		score013xyCombineFrequency.gsd_agbl = fiveQuadrants.get(1).getQuadrantFrequency(vsTeamScore013.getGsd(), vsTeamScore013.getAgbl());
		//gsd vs albg
		score013xyCombineFrequency.gsd_albg = fiveQuadrants.get(2).getQuadrantFrequency(vsTeamScore013.getGsd(), vsTeamScore013.getAlbg());
		//lsd vs agbl
		score013xyCombineFrequency.lsd_agbl = fiveQuadrants.get(3).getQuadrantFrequency(vsTeamScore013.getLsd(), vsTeamScore013.getAgbl());
		//lsd vs albg
		score013xyCombineFrequency.lsd_albg = fiveQuadrants.get(4).getQuadrantFrequency(vsTeamScore013.getLsd(), vsTeamScore013.getAlbg());
		//agbl vs albg
		score013xyCombineFrequency.agbl_albg = fiveQuadrants.get(5).getQuadrantFrequency(vsTeamScore013.getAgbl(),vsTeamScore013.getAlbg());
		
		if(score013xyCombineFrequency.gsd_lsd!=null && score013xyCombineFrequency.gsd_agbl!=null &&  score013xyCombineFrequency.gsd_albg!=null
				&& score013xyCombineFrequency.lsd_agbl!=null && score013xyCombineFrequency.lsd_albg!=null && score013xyCombineFrequency.agbl_albg!=null)
			score013xyCombineFrequency.isAvaliable = true;
		
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

	@Override
	public String toString() {
		return "Score013XYCombineFrequency [gsd_lsd=" + gsd_lsd + ", gsd_agbl="
				+ gsd_agbl + ", gsd_albg=" + gsd_albg + ", lsd_agbl="
				+ lsd_agbl + ", lsd_albg=" + lsd_albg + ", agbl_albg="
				+ agbl_albg + ", isAvaliable=" + isAvaliable + "]";
	}
}
