package shil.lottery.seriously.research.score013;

import java.util.List;

import org.apache.commons.math3.stat.Frequency;

import shil.lottery.seriously.utils.AnalyzeUtil;
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
	
	public int predictMatchResult(){
		int result = -1;
		if(isAvaliable){
			double winV = getResultTypeValue(AnalyzeUtil.winS);
			double drawV = getResultTypeValue(AnalyzeUtil.drawS);
			double loseV = getResultTypeValue(AnalyzeUtil.loseS);
			double biggest = (winV > drawV) ? winV : drawV;
			biggest = (biggest > loseV) ? biggest : loseV;
			if(biggest==winV) result = AnalyzeUtil.win;
			else if(biggest==drawV) result = AnalyzeUtil.draw;
			else if(biggest==loseV) result = AnalyzeUtil.lose;
			else result = -2;
		}
		return result;
	}
	
	/**
	 * 将所有组合的概率胜负平率都相乘计算,有点简单粗暴
	 * @param type
	 * @return
	 */
	private double getResultTypeValue(String type){
		//TODO see comment
		return gsd_lsd.getPct(type)*gsd_agbl.getPct(type)*gsd_albg.getPct(type)*lsd_agbl.getPct(type)*lsd_albg.getPct(type)*agbl_albg.getPct(type);
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
	
	public static Score013XYCombineFrequency buildScore013XYCombineFrequencyWithParentProb(Score013AnalyzeProbility score013AnalyzeProbility, VSTeamScore013 vsTeamScore013){
		Score013XYCombineFrequency score013xyCombineFrequency = new Score013XYCombineFrequency();
		
		if(!score013AnalyzeProbility.isSampleAvailable() || !vsTeamScore013.isAvaliable()) return score013xyCombineFrequency; 
		//这提取了所属象限中胜负平的概率,落在本象限所在的概率,为落在本象限*本象限胜负平的概率
		//gsd vs lsd
		score013xyCombineFrequency.gsd_lsd = getAdvCombine2Frequency(score013AnalyzeProbility,0,vsTeamScore013.getGsd(), vsTeamScore013.getLsd());
		//gsd vs agbl
		score013xyCombineFrequency.gsd_agbl = getAdvCombine2Frequency(score013AnalyzeProbility,1,vsTeamScore013.getGsd(), vsTeamScore013.getAgbl());
		//gsd vs albg
		score013xyCombineFrequency.gsd_albg = getAdvCombine2Frequency(score013AnalyzeProbility,2,vsTeamScore013.getGsd(), vsTeamScore013.getAlbg());
		//lsd vs agbl
		score013xyCombineFrequency.lsd_agbl = getAdvCombine2Frequency(score013AnalyzeProbility,3,vsTeamScore013.getLsd(), vsTeamScore013.getAgbl());
		//lsd vs albg
		score013xyCombineFrequency.lsd_albg = getAdvCombine2Frequency(score013AnalyzeProbility,4,vsTeamScore013.getLsd(), vsTeamScore013.getAlbg());
		//agbl vs albg
		score013xyCombineFrequency.agbl_albg = getAdvCombine2Frequency(score013AnalyzeProbility,5,vsTeamScore013.getAgbl(),vsTeamScore013.getAlbg());
		
		if(score013xyCombineFrequency.gsd_lsd!=null && score013xyCombineFrequency.gsd_agbl!=null &&  score013xyCombineFrequency.gsd_albg!=null
				&& score013xyCombineFrequency.lsd_agbl!=null && score013xyCombineFrequency.lsd_albg!=null && score013xyCombineFrequency.agbl_albg!=null)
			score013xyCombineFrequency.isAvaliable = true;
		
		return score013xyCombineFrequency;
	}
	
	private static Frequency getAdvCombine2Frequency(Score013AnalyzeProbility score013AnalyzeProbility, int xytype,double xvalue,double yvalue){
		
		Frequency result = new Frequency();
		long mult = 1000000000000000000l;
		//取出已知xy组合类型,比赛A队赢率所在四个象限的概率.
		Frequency parentW = score013AnalyzeProbility.getWinFrequencys().get(xytype);
		Frequency parentD = score013AnalyzeProbility.getDrawFrequencys().get(xytype);
		Frequency parentL = score013AnalyzeProbility.getLoseFrequencys().get(xytype);
		//取出已知xy组合类型,和x,y值,某x,y所落到特定象限的胜负平概率.
		List<FiveQuadrant> fiveQuadrants = score013AnalyzeProbility.getDescriptorFiveQuadrants();
		Frequency fiveQ = fiveQuadrants.get(xytype).getQuadrantFrequency(xvalue,yvalue);

		String quadrant = FiveQuadrant.getQuadrant(xvalue,yvalue);
		if(FiveQuadrant.QuadrantONing.equals(quadrant)) return null;
		
		double wp = parentW.getPct(quadrant);
		double qw = fiveQ.getPct(AnalyzeUtil.winS);
		double dp = parentD.getPct(quadrant);
		double qd = fiveQ.getPct(AnalyzeUtil.drawS);
		double lp = parentL.getPct(quadrant);
		double ql = fiveQ.getPct(AnalyzeUtil.loseS);
		
		result.incrementValue(AnalyzeUtil.winS,(long) (wp*qw*mult));
		result.incrementValue(AnalyzeUtil.drawS, (long) (dp*qd*mult));
		result.incrementValue(AnalyzeUtil.loseS, (long) (lp*ql*mult));
		
		return result;
	}
}
