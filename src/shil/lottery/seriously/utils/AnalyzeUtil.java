package shil.lottery.seriously.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.Frequency;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import shil.lottery.seriously.vo.WholeMatches;
import shil.lottery.sport.db.SportMetaDaoImpl;
import shil.lottery.sport.entity.VSTeam;

/**
 * 将所有List<VSTeam>数据分析为
 * 
 * 联赛名称set<名称>
 * 
 * 联赛Map<联赛名称,List<VSTeam>按从新到旧的顺序,即最前面是最新的>
 * 
 * 队名set<名称>
 * 
 * 队名Map<联赛名称,List<VSTeam>自己在这个联赛中的比赛记录,按从新到旧的顺序,即最前面是最新的>
 * 
 * @author yuanshun.sl
 * @since 2015-04-16 14:50
 *
 */
public class AnalyzeUtil {

	public static int leagalMinMatches = 5;
	public static int leagalMaxMatches = 9;
	
	public static int lose = 0;
	public static int draw = 1;
	public static int win = 3;
	
	public static String winS = "赢";
	public static String drawS = "平";
	public static String loseS = "负";
	
	public static char Connect = ':';
	public static int MultRate10 = 10;
	public static int MultRate100 = 100;
	
	public enum AVG_TP{
		
		//算数平均数，	几何平均数，		标准差，				均方根，			总体方差
		ArithmeticMean("ArithmeticMean"), GeometricMean("GeometricMean"), StandardDeviation("StandardDeviation"), RootMeanSquare("RootMeanSquare"), PopulationVariance("PopulationVariance");
		
		private String name;
		
		AVG_TP(String name){
			this.name = name;
		}
		
		public String toString(){
			return name;
		}
	}
	
	/**
	 * 分析X月的数据
	 * @param x
	 * @return
	 */
	public static WholeMatches analyzeXmonth(int x){
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadBeforeMonthVSTeamRecords(x);
		return WholeMatches.analyzeWholeMatches(vsTeams);
	}
	
	/**
	 * 分析所有的数据
	 * @return
	 */
	public static WholeMatches analyzeWholeRecords(){
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		return WholeMatches.analyzeWholeMatches(vsTeams);
	}
	
	/**
	 * 判断是否为主场,主场返回0,客场返回1. 不包含这个球队返回-1.
	 * @param teamname
	 * @param vsnames
	 * @return
	 */
	public static int pos(String teamname, String[] vsnames){

		for(int i=0;i<vsnames.length;i++){
			if(teamname.equals(vsnames[i])) return i;
		}
		
		return -1;
	}
	
	/**
	 * 得到对手所在位置,配合pos()
	 * @param i
	 * @return
	 */
	public static int oppos(int i){
		if(i!=0 && i!=1) throw new RuntimeException(i+" postion is illeagal!");
		return i==1?0:1;
	}
	
	/**
	 * 分析比赛结果
	 * @param pos
	 * @param goals
	 * @return
	 */
	public static int match013(int pos,double goals[]){
		int result = AnalyzeUtil.draw;
		int oppos = AnalyzeUtil.oppos(pos);
		
		if(goals[pos] > goals[oppos]){
			result = AnalyzeUtil.win;
		}else if(goals[pos] < goals[oppos]){
			result = AnalyzeUtil.lose;
		}
		
		return result;
	}
	
	/**
	 * 将Vsteams过滤为只有某联赛和某组队的数据
	 * @param leaguename
	 * @param teamname
	 * @param vsTeams
	 * @return
	 */
	public static List<VSTeam> filterVSTeamMatchs(String leaguename, String teamname, List<VSTeam> vsTeams) {
		List<VSTeam> refinedVsTeams = new ArrayList<VSTeam>();
		for(VSTeam vsTeam : vsTeams){
			if(vsTeam.getLeague().equals(leaguename)){
				//此处是为了过滤相同联赛和相同队名的比赛
				for(String tname : vsTeam.getVs()){
					if(tname.equals(teamname)){
						refinedVsTeams.add(vsTeam);
						break;
					}
				}
			}
		}
		return refinedVsTeams;
	}

	public static double[] providerDoubleArrayValues(int size,int value){
		double[] ds = new double[size];
		for(int i=0; i < size; i++)
			ds[i] = value;
		return ds;
	}
	
	public static double[] getFrequencyZvaluebyXDoublesPct(double[] xs, Frequency frequency){
		double[] z = new double[xs.length];
		for(int i=0;i<xs.length;i++){
			z[i] = frequency.getPct((int)xs[i]);
		}
		return z;
	}
	
	public static double[] getFrequencyZvaluebyXDoublesCount(double[] xs, Frequency frequency){
		double[] z = new double[xs.length];
		for(int i=0;i<xs.length;i++){
			z[i] = frequency.getCount((int)xs[i]);
		}
		return z;
	}
	
	public static double[] getFrequencyZvaluebyXYDoublesCount(double[] xs, double[] ys, Frequency frequency){
		double[] z = new double[xs.length];
		for(int i=0;i<xs.length;i++){
			z[i] = frequency.getCount(xs[i]+Connect+ys[i]);
		}
		return z;
	}
	
	public static double[] getFrequencyZvaluebyXYDoublesPct(double[] xs, double[] ys, Frequency frequency, double mult){
		double[] z = new double[xs.length];
		for(int i=0;i<xs.length;i++){
			z[i] = frequency.getPct(xs[i]+Connect+ys[i])*mult;
		}
		return z;
	}
	
	public static double getAVG(DescriptiveStatistics descriptiveStatistics,AVG_TP avg_tp){
		switch(avg_tp){
			case ArithmeticMean: return descriptiveStatistics.getMean();
			case GeometricMean:	return descriptiveStatistics.getGeometricMean();
			case StandardDeviation:	return descriptiveStatistics.getStandardDeviation();
			case RootMeanSquare:	return descriptiveStatistics.getQuadraticMean();
			case PopulationVariance:	return descriptiveStatistics.getPopulationVariance();
			default : throw new RuntimeException("yo ~ this should not happend."); 
		}
	}
}
