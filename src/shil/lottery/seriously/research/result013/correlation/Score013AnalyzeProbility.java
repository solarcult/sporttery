package shil.lottery.seriously.research.result013.correlation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.Frequency;

/**
 * 分析比赛结果所在象限所占的比例
 * @author LiangJingJing
 * @date May 11, 2015 12:28:15 AM
 */
public class Score013AnalyzeProbility {
	
	public static final String QuadrantONing = "5第十象限";
	public static final String Quadrant4 = "4第四象限";
	public static final String Quadrant3 = "3第三象限";
	public static final String Quadrant2 = "2第二象限";
	public static final String Quadrant1 = "1第一象限";
	
	public static List<String> descriptors = new ArrayList<String>();
	public static List<String> names = new ArrayList<String>();
	static{
		descriptors.add("A进球数-B进球数(正数代表A比B的攻击力强)");
		descriptors.add("A失球数-B失球数(正数代表A比B的防御力弱)");
		descriptors.add("A进球数-B失球数(正数代表A攻击力比B防御力强)");
		descriptors.add("A失球数-B进球数(正数代表A防御力比B攻击力弱)");
		for(int i=0;i<descriptors.size();i++)
			for(int j=i+1;j<descriptors.size();j++)
				names.add("xaix:" +descriptors.get(i)+" - yaix:" + descriptors.get(j));
	}
	private List<Frequency> winFrequencys;
	private List<Frequency> drawFrequencys;
	private List<Frequency> loseFrequencys;
	
	private Score013AnalyzeProbility(){
		winFrequencys = new ArrayList<Frequency>();
		drawFrequencys = new ArrayList<Frequency>();
		loseFrequencys = new ArrayList<Frequency>();
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
	
	public static Score013AnalyzeProbility analyzeRecordsList(List<List<Double>> wins,List<List<Double>> draws,List<List<Double>> loses){
		Score013AnalyzeProbility score013AnalyzeResult = new Score013AnalyzeProbility();
		analyzeOneReccordsList(wins, score013AnalyzeResult.winFrequencys,"胜");
		analyzeOneReccordsList(draws, score013AnalyzeResult.drawFrequencys,"平");
		analyzeOneReccordsList(loses, score013AnalyzeResult.loseFrequencys,"负");
		return score013AnalyzeResult;
	}
	
	private static void analyzeOneReccordsList(List<List<Double>> records,List<Frequency> frequencys,String status013){
		System.out.println(status013);
		int nameid = 0;
		for(int i=0;i<records.size();i++){
			for(int j=i+1;j<records.size();j++){
				List<Double> xaix = records.get(i);
				List<Double> yaix = records.get(j);
				Frequency frequency = new Frequency();
				for(int k = 0 ; k<xaix.size(); k++){
					double xvalue = xaix.get(k);
					double yvalue = yaix.get(k);
					if(xvalue > 0 && yvalue > 0){
						frequency.addValue(Quadrant1);
					}else if(xvalue > 0 && yvalue < 0){
						frequency.addValue(Quadrant2);
					}else if(xvalue < 0 && yvalue <0){
						frequency.addValue(Quadrant3);
					}else if(xvalue < 0 && yvalue > 0){
						frequency.addValue(Quadrant4);
					}else{
						frequency.addValue(QuadrantONing);
					}
				}
				
				System.out.println(names.get(nameid));
				System.out.println(frequency);
				System.out.println(frequency.getSumFreq()+"\n");
				frequencys.add(frequency);
				nameid++;
			}
		}
	}

	@Override
	public String toString() {
		return "Score013AnalyzeResult [winFrequencys=" + winFrequencys
				+ ", drawFrequencys=" + drawFrequencys + ", loseFrequencys="
				+ loseFrequencys + "]";
	}

}
