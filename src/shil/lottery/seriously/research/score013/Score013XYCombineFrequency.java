package shil.lottery.seriously.research.score013;

import java.util.List;

import org.apache.commons.math3.stat.Frequency;

import shil.lottery.seriously.research.Guess013;
import shil.lottery.seriously.vo.Score013;
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
	
	public static Score013XYCombineFrequency buildScore013XYCombineFrequency(Score013 score013){
		Score013XYCombineFrequency score013xyCombineFrequency = new Score013XYCombineFrequency();
		
		Frequency gsd_lsd = new Frequency();
		gsd_lsd.incrementValue(Guess013.winS, score013.getGsd_lsd_current_quadrant_win_precentL());
		gsd_lsd.incrementValue(Guess013.drawS, score013.getGsd_lsd_current_quadrant_draw_precentL());
		gsd_lsd.incrementValue(Guess013.loseS, score013.getGsd_lsd_current_quadrant_lose_precentL());
		score013xyCombineFrequency.gsd_lsd = gsd_lsd;
		
		Frequency gsd_agbl = new Frequency();
		gsd_agbl.incrementValue(Guess013.winS, score013.getGsd_agbl_current_quadrant_win_precentL());
		gsd_agbl.incrementValue(Guess013.drawS, score013.getGsd_agbl_current_quadrant_draw_precentL());
		gsd_agbl.incrementValue(Guess013.loseS, score013.getGsd_agbl_current_quadrant_lose_precentL());
		score013xyCombineFrequency.gsd_agbl = gsd_agbl;
		
		Frequency gsd_albg = new Frequency();
		gsd_albg.incrementValue(Guess013.winS, score013.getGsd_albg_current_quadrant_win_precentL());
		gsd_albg.incrementValue(Guess013.drawS, score013.getGsd_albg_current_quadrant_draw_precentL());
		gsd_albg.incrementValue(Guess013.loseS, score013.getGsd_albg_current_quadrant_lose_precentL());
		score013xyCombineFrequency.gsd_albg = gsd_albg;
		
		Frequency lsd_agbl = new Frequency();
		lsd_agbl.incrementValue(Guess013.winS, score013.getLsd_agbl_current_quadrant_win_precentL());
		lsd_agbl.incrementValue(Guess013.drawS, score013.getLsd_agbl_current_quadrant_draw_precentL());
		lsd_agbl.incrementValue(Guess013.loseS, score013.getLsd_agbl_current_quadrant_lose_precentL());
		score013xyCombineFrequency.lsd_agbl = lsd_agbl;
		
		Frequency lsd_albg = new Frequency();
		lsd_albg.incrementValue(Guess013.winS, score013.getLsd_albg_current_quadrant_win_precentL());
		lsd_albg.incrementValue(Guess013.drawS, score013.getLsd_albg_current_quadrant_draw_precentL());
		lsd_albg.incrementValue(Guess013.loseS, score013.getLsd_albg_current_quadrant_lose_precentL());
		score013xyCombineFrequency.lsd_albg = lsd_albg;
		
		Frequency agbl_albg = new Frequency();
		agbl_albg.incrementValue(Guess013.winS, score013.getAgbl_albg_current_quadrant_win_precentL());
		agbl_albg.incrementValue(Guess013.drawS, score013.getAgbl_albg_current_quadrant_draw_precentL());
		agbl_albg.incrementValue(Guess013.loseS, score013.getAgbl_albg_current_quadrant_lose_precentL());
		score013xyCombineFrequency.agbl_albg = agbl_albg;
		
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((agbl_albg == null) ? 0 : agbl_albg.hashCode());
		result = prime * result
				+ ((gsd_agbl == null) ? 0 : gsd_agbl.hashCode());
		result = prime * result
				+ ((gsd_albg == null) ? 0 : gsd_albg.hashCode());
		result = prime * result + ((gsd_lsd == null) ? 0 : gsd_lsd.hashCode());
		result = prime * result + (isAvaliable ? 1231 : 1237);
		result = prime * result
				+ ((lsd_agbl == null) ? 0 : lsd_agbl.hashCode());
		result = prime * result
				+ ((lsd_albg == null) ? 0 : lsd_albg.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Score013XYCombineFrequency other = (Score013XYCombineFrequency) obj;
		if (agbl_albg == null) {
			if (other.agbl_albg != null)
				return false;
		} else if (!isPctEqual(agbl_albg,other.agbl_albg))
			return false;
		if (gsd_agbl == null) {
			if (other.gsd_agbl != null)
				return false;
		} else if (!isPctEqual(gsd_agbl,other.gsd_agbl))
			return false;
		if (gsd_albg == null) {
			if (other.gsd_albg != null)
				return false;
		} else if (!isPctEqual(gsd_albg,other.gsd_albg))
			return false;
		if (gsd_lsd == null) {
			if (other.gsd_lsd != null)
				return false;
		} else if (!isPctEqual(gsd_lsd,other.gsd_lsd))
			return false;
		if (isAvaliable != other.isAvaliable)
			return false;
		if (lsd_agbl == null) {
			if (other.lsd_agbl != null)
				return false;
		} else if (!isPctEqual(lsd_agbl,other.lsd_agbl))
			return false;
		if (lsd_albg == null) {
			if (other.lsd_albg != null)
				return false;
		} else if (!isPctEqual(lsd_albg,other.lsd_albg))
			return false;
		return true;
	}
	
	public boolean isPctEqual(Frequency one, Frequency two){
		int oneW = (int) (one.getPct(Guess013.winS)*100);
		int twoW = (int) (two.getPct(Guess013.winS)*100);
		if(oneW != twoW) return false;
		int oneD = (int) (one.getPct(Guess013.drawS)*100);
		int twoD = (int) (two.getPct(Guess013.drawS)*100);
		if(oneD != twoD) return false;
		int oneL = (int) (one.getPct(Guess013.loseS)*100);
		int twoL = (int) (two.getPct(Guess013.loseS)*100);
		if(oneL != twoL) return false;
		return true;
	}
}
