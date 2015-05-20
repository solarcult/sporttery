package shil.lottery.seriously.research.score013;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.Frequency;

import shil.lottery.seriously.research.Guess013;
import shil.lottery.seriously.utils.AnalyzeUtil.AVG_TP;
import shil.lottery.seriously.vo.VSTeamScore013;
import shil.lottery.sport.entity.VSTeam;

/**
 * 分析比赛结果所在象限所占的比例
 * @author LiangJingJing
 * @date May 11, 2015 12:28:15 AM
 */
public class Score013AnalyzeProbility {
	
	public static int SampleAvailable = 64;
	
	private int sample;

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
	
	//记录每一种X,Y轴组合类型,五象限的胜负平情况
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
	
	public boolean isSampleAvailable(){
		return sample > SampleAvailable;
	}
	
	public int getSample() {
		return sample;
	}
	
	@Override
	public String toString() {
		return "Score013AnalyzeProbility [sample=" + sample + ", avg_type="
				+ avg_type + ", winFrequencys=" + winFrequencys
				+ ", drawFrequencys=" + drawFrequencys + ", loseFrequencys="
				+ loseFrequencys + ", descriptorFiveQuadrants="
				+ descriptorFiveQuadrants + "]";
	}

	public static Score013AnalyzeProbility analyzeScore013AnalyzeProbility(List<VSTeam> vsTeams){
		return Score013AnalyzeProbility.analyzeScore013AnalyzeProbility(vsTeams,AVG_TP.PopulationVariance);
	}
	
	public static Score013AnalyzeProbility analyzeScore013AnalyzeProbility(List<VSTeam> vsTeams,AVG_TP avg_tp){
		
		final List<Double> gsdsw = new ArrayList<Double>();
		final List<Double> lsdsw = new ArrayList<Double>();
		final List<Double> agblsw = new ArrayList<Double>();
		final List<Double> albgsw = new ArrayList<Double>();
		final List<List<Double>> wins = new ArrayList<List<Double>>();
		wins.add(gsdsw);
		wins.add(lsdsw);
		wins.add(agblsw);
		wins.add(albgsw);
		
		final List<Double> gsdsd = new ArrayList<Double>();
		final List<Double> lsdsd = new ArrayList<Double>();
		final List<Double> agblsd = new ArrayList<Double>();
		final List<Double> albgsd = new ArrayList<Double>();
		final List<List<Double>> draws = new ArrayList<List<Double>>();
		draws.add(gsdsd);
		draws.add(lsdsd);
		draws.add(agblsd);
		draws.add(albgsd);
		
		final List<Double> gsdsl = new ArrayList<Double>();
		final List<Double> lsdsl = new ArrayList<Double>();
		final List<Double> agblsl = new ArrayList<Double>();
		final List<Double> albgsl = new ArrayList<Double>();
		final List<List<Double>> loses = new ArrayList<List<Double>>();
		loses.add(gsdsl);
		loses.add(lsdsl);
		loses.add(agblsl);
		loses.add(albgsl);
		
		int sample = 0;
		
		//遍历历史记录,寻找规律
		for(int i=0;i<vsTeams.size();i++){
			VSTeam vsTeam = vsTeams.get(i);
			
			VSTeamScore013 vsTeamScore013 = VSTeamScore013.calculateVSTeamScore013(vsTeams.subList(0,i), vsTeam, avg_tp);
			
			if(!vsTeamScore013.isAvaliable()) continue;
			
			double gsd = vsTeamScore013.getGsd();
			double lsd = vsTeamScore013.getLsd();
			double agbl = vsTeamScore013.getAgbl();
			double albg = vsTeamScore013.getAlbg();
			
			//根据胜负平分类,这里的记录形式不太好,最后应该用[]List来记录
			if(vsTeam.getMatch_Result()==Guess013.win){
				gsdsw.add(gsd);
				lsdsw.add(lsd);
				agblsw.add(agbl);
				albgsw.add(albg);
			}else if(vsTeam.getMatch_Result() == Guess013.draw){
				gsdsd.add(gsd);
				lsdsd.add(lsd);
				agblsd.add(agbl);
				albgsd.add(albg);
			}else{
				gsdsl.add(gsd);
				lsdsl.add(lsd);
				agblsl.add(agbl);
				albgsl.add(albg);
			}
			
			sample++;
		}
		
		Score013AnalyzeProbility score013AnalyzeProbility = Score013AnalyzeProbility.analyzeRecordsList(avg_tp, wins, draws, loses);
		score013AnalyzeProbility.sample = sample;
		
		return score013AnalyzeProbility;
	}

	public static Score013AnalyzeProbility analyzeRecordsList(AVG_TP avt_tp,List<List<Double>> wins,List<List<Double>> draws,List<List<Double>> loses){
		Score013AnalyzeProbility score013AnalyzeResult = new Score013AnalyzeProbility(avt_tp);
		analyzeOneReccordsList(wins, score013AnalyzeResult.winFrequencys,Guess013.winS);
		analyzeOneReccordsList(draws, score013AnalyzeResult.drawFrequencys,Guess013.drawS);
		analyzeOneReccordsList(loses, score013AnalyzeResult.loseFrequencys,Guess013.loseS);
		descriptorCombine(score013AnalyzeResult);
		return score013AnalyzeResult;
	}
	
	private static void analyzeOneReccordsList(List<List<Double>> records,List<Frequency> frequencys,String status013){
		for(int i=0;i<records.size();i++){
			for(int j=i+1;j<records.size();j++){
				List<Double> xaix = records.get(i);
				List<Double> yaix = records.get(j);
				Frequency frequency = new Frequency();
				for(int k = 0 ; k<xaix.size(); k++){
					double xvalue = xaix.get(k);
					double yvalue = yaix.get(k);
					frequency.addValue(FiveQuadrant.getQuadrant(xvalue,yvalue));
				}
				
				frequencys.add(frequency);
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
			quadrant1.incrementValue(Guess013.winS, win.getCount(FiveQuadrant.Quadrant1));
			quadrant1.incrementValue(Guess013.drawS, draw.getCount(FiveQuadrant.Quadrant1));
			quadrant1.incrementValue(Guess013.loseS, lose.getCount(FiveQuadrant.Quadrant1));
			fiveQuadrant.setQuadrant1(quadrant1);
			
			Frequency quadrant2 = new Frequency();
			quadrant2.incrementValue(Guess013.winS, win.getCount(FiveQuadrant.Quadrant2));
			quadrant2.incrementValue(Guess013.drawS, draw.getCount(FiveQuadrant.Quadrant2));
			quadrant2.incrementValue(Guess013.loseS, lose.getCount(FiveQuadrant.Quadrant2));
			fiveQuadrant.setQuadrant2(quadrant2);
			
			Frequency quadrant3 = new Frequency();
			quadrant3.incrementValue(Guess013.winS, win.getCount(FiveQuadrant.Quadrant3));
			quadrant3.incrementValue(Guess013.drawS, draw.getCount(FiveQuadrant.Quadrant3));
			quadrant3.incrementValue(Guess013.loseS, lose.getCount(FiveQuadrant.Quadrant3));
			fiveQuadrant.setQuadrant3(quadrant3);
			
			Frequency quadrant4 = new Frequency();
			quadrant4.incrementValue(Guess013.winS, win.getCount(FiveQuadrant.Quadrant4));
			quadrant4.incrementValue(Guess013.drawS, draw.getCount(FiveQuadrant.Quadrant4));
			quadrant4.incrementValue(Guess013.loseS, lose.getCount(FiveQuadrant.Quadrant4));
			fiveQuadrant.setQuadrant4(quadrant4);
			
			Frequency quadrantONing = new Frequency();
			quadrantONing.incrementValue(Guess013.winS, win.getCount(FiveQuadrant.QuadrantONing));
			quadrantONing.incrementValue(Guess013.drawS, draw.getCount(FiveQuadrant.QuadrantONing));
			quadrantONing.incrementValue(Guess013.loseS, lose.getCount(FiveQuadrant.QuadrantONing));
			fiveQuadrant.setQuadrantONing(quadrantONing);
			
			score013AnalyzeProbility.descriptorFiveQuadrants.add(fiveQuadrant);
		}
	}
}


