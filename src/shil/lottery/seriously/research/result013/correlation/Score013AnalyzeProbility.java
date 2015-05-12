package shil.lottery.seriously.research.result013.correlation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.Frequency;

import shil.lottery.seriously.utils.AnalyzeUtil;
import shil.lottery.seriously.utils.AnalyzeUtil.AVG_TP;

/**
 * 分析比赛结果所在象限所占的比例
 * @author LiangJingJing
 * @date May 11, 2015 12:28:15 AM
 */
public class Score013AnalyzeProbility {

	public static final String Quadrant1 = "1第一象限";
	public static final String Quadrant2 = "2第二象限";
	public static final String Quadrant3 = "3第三象限";
	public static final String Quadrant4 = "4第四象限";
	public static final String QuadrantONing = "5第十象限";
	
	public static String getQuadrant(double xvalue, double yvalue){
		if(xvalue > 0 && yvalue > 0){
			return Quadrant1;
		}else if(xvalue > 0 && yvalue < 0){
			return Quadrant2;
		}else if(xvalue < 0 && yvalue <0){
			return Quadrant3;
		}else if(xvalue < 0 && yvalue > 0){
			return Quadrant4;
		}else{
			return QuadrantONing;
		}
	}
	
	public static List<String> descriptors = new ArrayList<String>();
	public static List<String> names = new ArrayList<String>();
	static{
		descriptors.add("A进球数-B进球数(正数代表A比B的攻击力强)");
		descriptors.add("A失球数-B失球数(正数代表A比B的防御力弱)");
		descriptors.add("A进球数-B失球数(正数代表A攻击力比B防御力强)");
		descriptors.add("A失球数-B进球数(正数代表A防御力比B攻击力弱)");
		for(int i=0;i<descriptors.size();i++)
			for(int j=i+1;j<descriptors.size();j++)
				names.add("xaix:" +descriptors.get(i)+" {vs} yaix:" + descriptors.get(j));
	}
	//所采用的均值类型
	private AVG_TP avg_type;
	//这些List里面存放着不同X,Y轴组合的胜负平结果,详细信息放在descriptor里面.
	private List<Frequency> winFrequencys;
	private List<Frequency> drawFrequencys;
	private List<Frequency> loseFrequencys;
	
	//
	private List<FiveQuadrant> descriptorFiveQuadrants;
	
	private Score013AnalyzeProbility(AVG_TP avg_tp){
		avg_type = avg_tp;
		winFrequencys = new ArrayList<Frequency>(names.size());
		drawFrequencys = new ArrayList<Frequency>(names.size());
		loseFrequencys = new ArrayList<Frequency>(names.size());
		descriptorFiveQuadrants = new ArrayList<FiveQuadrant>(names.size());
	}
	
	public List<Frequency> getWinFrequencys() {
		return winFrequencys;
	}

	public List<Frequency> getDrawFrequencys() {
		return drawFrequencys;
	}

	public List<Frequency> getLoseFrequencys() {
		return loseFrequencys;
	}
	
	public AVG_TP getAvg_type() {
		return avg_type;
	}

	public List<FiveQuadrant> getDescriptorFiveQuadrants() {
		return descriptorFiveQuadrants;
	}
	
	@Override
	public String toString() {
		return "Score013AnalyzeProbility [avg_type=" + avg_type
				+ ", winFrequencys=" + winFrequencys + ", drawFrequencys="
				+ drawFrequencys + ", loseFrequencys=" + loseFrequencys
				+ ", descriptorFiveQuadrants=" + descriptorFiveQuadrants + "]";
	}

	public static Score013AnalyzeProbility analyzeRecordsList(AVG_TP avt_tp,List<List<Double>> wins,List<List<Double>> draws,List<List<Double>> loses){
		Score013AnalyzeProbility score013AnalyzeResult = new Score013AnalyzeProbility(avt_tp);
		analyzeOneReccordsList(wins, score013AnalyzeResult.winFrequencys,AnalyzeUtil.winS);
		analyzeOneReccordsList(draws, score013AnalyzeResult.drawFrequencys,AnalyzeUtil.drawS);
		analyzeOneReccordsList(loses, score013AnalyzeResult.loseFrequencys,AnalyzeUtil.loseS);
		descriptorCombine(score013AnalyzeResult);
		return score013AnalyzeResult;
	}
	
	private static void analyzeOneReccordsList(List<List<Double>> records,List<Frequency> frequencys,String status013){
//		System.out.println(status013);
//		int nameid = 0;
		for(int i=0;i<records.size();i++){
			for(int j=i+1;j<records.size();j++){
				List<Double> xaix = records.get(i);
				List<Double> yaix = records.get(j);
				Frequency frequency = new Frequency();
				for(int k = 0 ; k<xaix.size(); k++){
					double xvalue = xaix.get(k);
					double yvalue = yaix.get(k);
					frequency.addValue(getQuadrant(xvalue,yvalue));
				}
				
//				System.out.println(names.get(nameid));
//				System.out.println(frequency);
//				System.out.println(frequency.getSumFreq()+"\n");
				frequencys.add(frequency);
//				nameid++;
			}
		}
	}
	
	private static void descriptorCombine(Score013AnalyzeProbility score013AnalyzeProbility){
		
		for(int i=0;i<Score013AnalyzeProbility.names.size();i++){
			//name
			FiveQuadrant fiveQuadrant = new FiveQuadrant(Score013AnalyzeProbility.names.get(i));
			
			//取出每一种x,y类型组合的胜负平结果,放入同一个空间+数轴中,看一下每一个象限的胜负平比例.
			Frequency win = score013AnalyzeProbility.getWinFrequencys().get(i);
			Frequency draw = score013AnalyzeProbility.getDrawFrequencys().get(i);
			Frequency lose = score013AnalyzeProbility.getLoseFrequencys().get(i);
			
			Frequency quadrant1 = new Frequency();
			quadrant1.incrementValue(Quadrant1+AnalyzeUtil.Connect+AnalyzeUtil.winS, win.getCount(Quadrant1));
			quadrant1.incrementValue(Quadrant1+AnalyzeUtil.Connect+AnalyzeUtil.drawS, draw.getCount(Quadrant1));
			quadrant1.incrementValue(Quadrant1+AnalyzeUtil.Connect+AnalyzeUtil.loseS, lose.getCount(Quadrant1));
			fiveQuadrant.setQuadrant1(quadrant1);
			
			Frequency quadrant2 = new Frequency();
			quadrant2.incrementValue(Quadrant2+AnalyzeUtil.Connect+AnalyzeUtil.winS, win.getCount(Quadrant2));
			quadrant2.incrementValue(Quadrant2+AnalyzeUtil.Connect+AnalyzeUtil.drawS, draw.getCount(Quadrant2));
			quadrant2.incrementValue(Quadrant2+AnalyzeUtil.Connect+AnalyzeUtil.loseS, lose.getCount(Quadrant2));
			fiveQuadrant.setQuadrant2(quadrant2);
			
			Frequency quadrant3 = new Frequency();
			quadrant3.incrementValue(Quadrant3+AnalyzeUtil.Connect+AnalyzeUtil.winS, win.getCount(Quadrant3));
			quadrant3.incrementValue(Quadrant3+AnalyzeUtil.Connect+AnalyzeUtil.drawS, draw.getCount(Quadrant3));
			quadrant3.incrementValue(Quadrant3+AnalyzeUtil.Connect+AnalyzeUtil.loseS, lose.getCount(Quadrant3));
			fiveQuadrant.setQuadrant3(quadrant3);
			
			Frequency quadrant4 = new Frequency();
			quadrant4.incrementValue(Quadrant4+AnalyzeUtil.Connect+AnalyzeUtil.winS, win.getCount(Quadrant4));
			quadrant4.incrementValue(Quadrant4+AnalyzeUtil.Connect+AnalyzeUtil.drawS, draw.getCount(Quadrant4));
			quadrant4.incrementValue(Quadrant4+AnalyzeUtil.Connect+AnalyzeUtil.loseS, lose.getCount(Quadrant4));
			fiveQuadrant.setQuadrant4(quadrant4);
			
			Frequency quadrantONing = new Frequency();
			quadrantONing.incrementValue(QuadrantONing+AnalyzeUtil.Connect+AnalyzeUtil.winS, win.getCount(QuadrantONing));
			quadrantONing.incrementValue(QuadrantONing+AnalyzeUtil.Connect+AnalyzeUtil.drawS, draw.getCount(QuadrantONing));
			quadrantONing.incrementValue(QuadrantONing+AnalyzeUtil.Connect+AnalyzeUtil.loseS, lose.getCount(QuadrantONing));
			fiveQuadrant.setQuadrantONing(quadrantONing);
			
			score013AnalyzeProbility.descriptorFiveQuadrants.add(fiveQuadrant);
		}
	}
	
}


