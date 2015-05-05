package shil.lottery.seriously.research.result013.correlation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.Frequency;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYZDataset;

import shil.lottery.seriously.utils.AbstractBubbleChart;
import shil.lottery.seriously.utils.AnalyzeUtil;
import shil.lottery.seriously.vo.ScoreStatistics;
import shil.lottery.seriously.vo.WholeMatches;
import shil.lottery.sport.db.SportMetaDaoImpl;
import shil.lottery.sport.entity.VSTeam;
import shil.lottery.sport.statistics.NumberUtils;

/**
 * 本篇代码记录对进球数的分析来判断是否能找到胜负平的规律,或者概率
 * 经查,貌似有一些规律,下面应该分析所在象限胜负平的比率.
 * @author LiangJingJing
 * @date May 5, 2015 11:04:45 PM
 */
public class Scores013Test {
	
	public static void main(String[] args){
		
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		
		//描述 最后wdl代表胜负平
		//由于要区分不同的情况的数值,所以分出来一大堆做记录的对象.
		final List<Double> gsdsw = new ArrayList<Double>();
		final List<Double> lsdsw = new ArrayList<Double>();
		final List<Double> agblsw = new ArrayList<Double>();
		final List<Double> albgsw = new ArrayList<Double>();
		final List<Double> gsdsd = new ArrayList<Double>();
		final List<Double> lsdsd = new ArrayList<Double>();
		final List<Double> agblsd = new ArrayList<Double>();
		final List<Double> albgsd = new ArrayList<Double>();
		final List<Double> gsdsl = new ArrayList<Double>();
		final List<Double> lsdsl = new ArrayList<Double>();
		final List<Double> agblsl = new ArrayList<Double>();
		final List<Double> albgsl = new ArrayList<Double>();
		List<Double> mrs = new ArrayList<Double>();
		final Frequency glfw = new Frequency();
		final Frequency gagblfw = new Frequency();
		final Frequency galbgfw = new Frequency();
		final Frequency lagblfw = new Frequency();
		final Frequency lalbgfw = new Frequency();
		final Frequency agblalbgfw = new Frequency();
		final Frequency glfd = new Frequency();
		final Frequency gagblfd = new Frequency();
		final Frequency galbgfd = new Frequency();
		final Frequency lagblfd = new Frequency();
		final Frequency lalbgfd = new Frequency();
		final Frequency agblalbgfd = new Frequency();
		final Frequency glfl = new Frequency();
		final Frequency gagblfl = new Frequency();
		final Frequency galbgfl = new Frequency();
		final Frequency lagblfl = new Frequency();
		final Frequency lalbgfl = new Frequency();
		final Frequency agblalbgfl = new Frequency();
		
		//遍历历史记录,寻找规律
		for(int i=0;i<vsTeams.size();i++){
			VSTeam vsTeam = vsTeams.get(i);
			WholeMatches wholeMatches = WholeMatches.analyzeWholeMatches(vsTeams.subList(0, i));

			String teamA = vsTeam.getVs()[0];
			String teamB = vsTeam.getVs()[1];
			
			List<VSTeam> teamAs = (wholeMatches.getTeamDigest().get(teamA)!=null)? wholeMatches.getTeamDigest().get(teamA).get(vsTeam.getLeague()) : null;
			List<VSTeam> teamBs = wholeMatches.getTeamDigest().get(teamB)!=null ? wholeMatches.getTeamDigest().get(teamB).get(vsTeam.getLeague()) : null;
			if(teamAs == null || teamBs == null || (teamAs.size() < AnalyzeUtil.leagalMinMatches)||(teamBs.size()<AnalyzeUtil.leagalMinMatches)){
				//确保比赛取值场次在5次到9次之间
				continue;
			}
			
			//得到最近的几个场次比赛数据
			int al = (teamAs.size() > AnalyzeUtil.leagalMaxMatches)? AnalyzeUtil.leagalMaxMatches : teamAs.size();
			int bl = (teamBs.size() > AnalyzeUtil.leagalMaxMatches)? AnalyzeUtil.leagalMaxMatches : teamBs.size();
			ScoreStatistics as = ScoreStatistics.analyzeVSTeams2scoreStatistics(vsTeam.getLeague(), teamA, teamAs.subList(teamAs.size()-al, teamAs.size()));
			ScoreStatistics bs = ScoreStatistics.analyzeVSTeams2scoreStatistics(vsTeam.getLeague(), teamB, teamBs.subList(teamBs.size()-bl, teamBs.size()));
			
			//TODO 分析,应该根据不同的数值,SD,Mean,GMean,等,写函数,传值取不同的结果,传入Statistics
			double gsd  = as.getGoalStatistics().getStandardDeviation() - bs.getGoalStatistics().getStandardDeviation();
			double lsd = as.getLostStatistics().getStandardDeviation() - bs.getLostStatistics().getStandardDeviation();
			double agbl = as.getGoalStatistics().getStandardDeviation() - bs.getLostStatistics().getStandardDeviation();
			double albg = as.getLostStatistics().getStandardDeviation() - bs.getGoalStatistics().getStandardDeviation();

			mrs.add((double) vsTeam.getMatch_Result());

			//根据胜负平分类,这里的记录形式不太好,最后应该用[]List来记录
			//TODO 记录各象限的概率
			if(vsTeam.getMatch_Result()==AnalyzeUtil.win){
				gsdsw.add(gsd);
				lsdsw.add(lsd);
				agblsw.add(agbl);
				albgsw.add(albg);
				glfw.addValue(gsd+AnalyzeUtil.Connect+lsd);
				gagblfw.addValue(gsd+AnalyzeUtil.Connect+agbl);
				galbgfw.addValue(gsd+AnalyzeUtil.Connect+albg);
				lagblfw.addValue(lsd+AnalyzeUtil.Connect+agbl);
				lalbgfw.addValue(lsd+AnalyzeUtil.Connect+albg);
				agblalbgfw.addValue(agbl+AnalyzeUtil.Connect+albg);
			}else if(vsTeam.getMatch_Result() == AnalyzeUtil.draw){
				gsdsd.add(gsd);
				lsdsd.add(lsd);
				agblsd.add(agbl);
				albgsd.add(albg);
				glfd.addValue(gsd+AnalyzeUtil.Connect+lsd);
				gagblfd.addValue(gsd+AnalyzeUtil.Connect+agbl);
				galbgfd.addValue(gsd+AnalyzeUtil.Connect+albg);
				lagblfd.addValue(lsd+AnalyzeUtil.Connect+agbl);
				lalbgfd.addValue(lsd+AnalyzeUtil.Connect+albg);
				agblalbgfd.addValue(agbl+AnalyzeUtil.Connect+albg);
			}else{
				gsdsl.add(gsd);
				lsdsl.add(lsd);
				agblsl.add(agbl);
				albgsl.add(albg);
				glfl.addValue(gsd+AnalyzeUtil.Connect+lsd);
				gagblfl.addValue(gsd+AnalyzeUtil.Connect+agbl);
				galbgfl.addValue(gsd+AnalyzeUtil.Connect+albg);
				lagblfl.addValue(lsd+AnalyzeUtil.Connect+agbl);
				lalbgfl.addValue(lsd+AnalyzeUtil.Connect+albg);
				agblalbgfl.addValue(agbl+AnalyzeUtil.Connect+albg);
			}
		}
		
		
		//这里的硬拼数据展示太白痴了,应该用for[]来自动3*2的循环组装展示数据,将title也放到[]里面来拼接,下次再有这种事可千万不要这么做了.累坏了00:43
		new AbstractBubbleChart("SD detect result - 赢","A进球数-B进球数(正数代表A的攻击力比B强)","A失球数-B失球数(正数代表A比B的防御力弱)") {
			
			@Override
			public XYZDataset getXYZDataset() {
				DefaultXYZDataset dataset = new DefaultXYZDataset();
				dataset.addSeries("赢",new double[][]{NumberUtils.convertListDs2doubles(gsdsw),NumberUtils.convertListDs2doubles(lsdsw),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(gsdsw),NumberUtils.convertListDs2doubles(lsdsw), glfw, 25)});
				return dataset;
			}
		};
		
		new AbstractBubbleChart("SD detect result - 平","A进球数-B进球数(正数代表A的攻击力比B强)","A失球数-B失球数(正数代表A比B的防御力弱)") {
			
			@Override
			public XYZDataset getXYZDataset() {
				DefaultXYZDataset dataset = new DefaultXYZDataset();
				double[][] dot = new double[][]{{0},{0},{0}};
				dataset.addSeries("win", dot);
				dataset.addSeries("平",new double[][]{NumberUtils.convertListDs2doubles(gsdsd),NumberUtils.convertListDs2doubles(lsdsd),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(gsdsd),NumberUtils.convertListDs2doubles(lsdsd), glfd, 25)});
				return dataset;
			}
		};
		
		new AbstractBubbleChart("SD detect result - 输","A进球数-B进球数(正数代表A的攻击力比B强)","A失球数-B失球数(正数代表A比B的防御力弱)") {
			
			@Override
			public XYZDataset getXYZDataset() {
				DefaultXYZDataset dataset = new DefaultXYZDataset();
				double[][] dot = new double[][]{{0},{0},{0}};
				dataset.addSeries("win", dot);
				dataset.addSeries("draw", dot);
				dataset.addSeries("输",new double[][]{NumberUtils.convertListDs2doubles(gsdsl),NumberUtils.convertListDs2doubles(lsdsl),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(gsdsl),NumberUtils.convertListDs2doubles(lsdsl), glfl, 25)});
				return dataset;
			}
		};
		
		new AbstractBubbleChart("SD detect result - 全","A进球数-B进球数(正数代表A的攻击力比B强)","A失球数-B失球数(正数代表A比B的防御力弱)") {
			
			@Override
			public XYZDataset getXYZDataset() {
				DefaultXYZDataset dataset = new DefaultXYZDataset();
				dataset.addSeries("赢",new double[][]{NumberUtils.convertListDs2doubles(gsdsw),NumberUtils.convertListDs2doubles(lsdsw),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(gsdsw),NumberUtils.convertListDs2doubles(lsdsw), glfw, 25)});
				dataset.addSeries("平",new double[][]{NumberUtils.convertListDs2doubles(gsdsd),NumberUtils.convertListDs2doubles(lsdsd),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(gsdsd),NumberUtils.convertListDs2doubles(lsdsd), glfd, 25)});
				dataset.addSeries("输",new double[][]{NumberUtils.convertListDs2doubles(gsdsl),NumberUtils.convertListDs2doubles(lsdsl),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(gsdsl),NumberUtils.convertListDs2doubles(lsdsl), glfl, 25)});
				return dataset;
			}
		};
		
		//-----
		
		
		new AbstractBubbleChart("SD detect result - 赢","A进球数-B进球数(正数代表A的攻击力比B强)","A进球数-B失球数(正数代表A攻击力比B防御力强)") {
			
			@Override
			public XYZDataset getXYZDataset() {
				DefaultXYZDataset dataset = new DefaultXYZDataset();
				dataset.addSeries("赢",new double[][]{NumberUtils.convertListDs2doubles(gsdsw),NumberUtils.convertListDs2doubles(agblsw),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(gsdsw),NumberUtils.convertListDs2doubles(agblsw), gagblfw, 25)});
				return dataset;
			}
		};
		
		new AbstractBubbleChart("SD detect result - 平","A进球数-B进球数(正数代表A的攻击力比B强)","A进球数-B失球数(正数代表A攻击力比B防御力强)") {
			
			@Override
			public XYZDataset getXYZDataset() {
				DefaultXYZDataset dataset = new DefaultXYZDataset();
				dataset.addSeries("赢",new double[][]{{0},{0},{0}});
				dataset.addSeries("平",new double[][]{NumberUtils.convertListDs2doubles(gsdsd),NumberUtils.convertListDs2doubles(agblsd),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(gsdsd),NumberUtils.convertListDs2doubles(agblsd), gagblfd, 25)});
				return dataset;
			}
		};
		
		new AbstractBubbleChart("SD detect result - 输","A进球数-B进球数(正数代表A的攻击力比B强)","A进球数-B失球数(正数代表A攻击力比B防御力强)") {
			
			@Override
			public XYZDataset getXYZDataset() {
				DefaultXYZDataset dataset = new DefaultXYZDataset();
				dataset.addSeries("赢",new double[][]{{0},{0},{0}});
				dataset.addSeries("平",new double[][]{{0},{0},{0}});
				dataset.addSeries("输",new double[][]{NumberUtils.convertListDs2doubles(gsdsl),NumberUtils.convertListDs2doubles(agblsl),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(gsdsl),NumberUtils.convertListDs2doubles(agblsl), gagblfl, 25)});
				return dataset;
			}
		};
		
		new AbstractBubbleChart("SD detect result - 全","A进球数-B进球数(正数代表A的攻击力比B强)","A进球数-B失球数(正数代表A攻击力比B防御力强)") {
			
			@Override
			public XYZDataset getXYZDataset() {
				DefaultXYZDataset dataset = new DefaultXYZDataset();
				dataset.addSeries("赢",new double[][]{NumberUtils.convertListDs2doubles(gsdsw),NumberUtils.convertListDs2doubles(agblsw),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(gsdsw),NumberUtils.convertListDs2doubles(agblsw), gagblfw, 25)});
				dataset.addSeries("平",new double[][]{NumberUtils.convertListDs2doubles(gsdsd),NumberUtils.convertListDs2doubles(agblsd),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(gsdsd),NumberUtils.convertListDs2doubles(agblsd), gagblfd, 25)});
				dataset.addSeries("输",new double[][]{NumberUtils.convertListDs2doubles(gsdsl),NumberUtils.convertListDs2doubles(agblsl),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(gsdsl),NumberUtils.convertListDs2doubles(agblsl), gagblfl, 25)});
				return dataset;
			}
		};
		
		//------
		
		new AbstractBubbleChart("SD detect result - 赢","A进球数-B进球数(正数代表A的攻击力比B强)","A失球数-B进球数(正数代表A防御力比B攻击力弱)") {
			
			@Override
			public XYZDataset getXYZDataset() {
				DefaultXYZDataset dataset = new DefaultXYZDataset();
				dataset.addSeries("赢",new double[][]{NumberUtils.convertListDs2doubles(gsdsw),NumberUtils.convertListDs2doubles(albgsw),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(gsdsw),NumberUtils.convertListDs2doubles(albgsw), galbgfw, 25)});
				return dataset;
			}
		};
		
		new AbstractBubbleChart("SD detect result - 平","A进球数-B进球数(正数代表A的攻击力比B强)","A失球数-B进球数(正数代表A防御力比B攻击力弱)") {
			
			@Override
			public XYZDataset getXYZDataset() {
				DefaultXYZDataset dataset = new DefaultXYZDataset();
				dataset.addSeries("赢",new double[][]{{0},{0},{0}});
				dataset.addSeries("平",new double[][]{NumberUtils.convertListDs2doubles(gsdsd),NumberUtils.convertListDs2doubles(albgsd),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(gsdsd),NumberUtils.convertListDs2doubles(albgsd), galbgfd, 25)});
				return dataset;
			}
		};
		
		new AbstractBubbleChart("SD detect result - 输","A进球数-B进球数(正数代表A的攻击力比B强)","A失球数-B进球数(正数代表A防御力比B攻击力弱)") {
			
			@Override
			public XYZDataset getXYZDataset() {
				DefaultXYZDataset dataset = new DefaultXYZDataset();
				dataset.addSeries("赢",new double[][]{{0},{0},{0}});
				dataset.addSeries("平",new double[][]{{0},{0},{0}});
				dataset.addSeries("输",new double[][]{NumberUtils.convertListDs2doubles(gsdsl),NumberUtils.convertListDs2doubles(albgsl),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(gsdsl),NumberUtils.convertListDs2doubles(albgsl), galbgfl, 25)});
				return dataset;
			}
		};
		
		new AbstractBubbleChart("SD detect result - 全","A进球数-B进球数(正数代表A的攻击力比B强)","A失球数-B进球数(正数代表A防御力比B攻击力弱)") {
			
			@Override
			public XYZDataset getXYZDataset() {
				DefaultXYZDataset dataset = new DefaultXYZDataset();
				dataset.addSeries("赢",new double[][]{NumberUtils.convertListDs2doubles(gsdsw),NumberUtils.convertListDs2doubles(albgsw),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(gsdsw),NumberUtils.convertListDs2doubles(albgsw), galbgfw, 25)});
				dataset.addSeries("平",new double[][]{NumberUtils.convertListDs2doubles(gsdsd),NumberUtils.convertListDs2doubles(albgsd),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(gsdsd),NumberUtils.convertListDs2doubles(albgsd), galbgfd, 25)});
				dataset.addSeries("输",new double[][]{NumberUtils.convertListDs2doubles(gsdsl),NumberUtils.convertListDs2doubles(albgsl),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(gsdsl),NumberUtils.convertListDs2doubles(albgsl), galbgfl, 25)});
				return dataset;
			}
		};
		
		//----------------
		new AbstractBubbleChart("SD detect result - 赢","A失球数-B失球数(正数代表A比B的防御力弱)","A进球数-B失球数(正数代表A攻击力比B防御力强)") {
			
			@Override
			public XYZDataset getXYZDataset() {
				DefaultXYZDataset dataset = new DefaultXYZDataset();
				dataset.addSeries("赢",new double[][]{NumberUtils.convertListDs2doubles(lsdsw),NumberUtils.convertListDs2doubles(agblsw),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(lsdsw),NumberUtils.convertListDs2doubles(agblsw), lagblfw, 25)});
				return dataset;
			}
		};
		
		new AbstractBubbleChart("SD detect result - 平","A失球数-B失球数(正数代表A比B的防御力弱)","A进球数-B失球数(正数代表A攻击力比B防御力强)") {
			
			@Override
			public XYZDataset getXYZDataset() {
				DefaultXYZDataset dataset = new DefaultXYZDataset();
				dataset.addSeries("赢",new double[][]{{0},{0},{0}});
				dataset.addSeries("平",new double[][]{NumberUtils.convertListDs2doubles(lsdsd),NumberUtils.convertListDs2doubles(agblsd),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(lsdsd),NumberUtils.convertListDs2doubles(agblsd), lagblfd, 25)});
				return dataset;
			}
		};
		new AbstractBubbleChart("SD detect result - 输","A失球数-B失球数(正数代表A比B的防御力弱)","A进球数-B失球数(正数代表A攻击力比B防御力强)") {
			
			@Override
			public XYZDataset getXYZDataset() {
				DefaultXYZDataset dataset = new DefaultXYZDataset();
				dataset.addSeries("赢",new double[][]{{0},{0},{0}});
				dataset.addSeries("平",new double[][]{{0},{0},{0}});
				dataset.addSeries("输",new double[][]{NumberUtils.convertListDs2doubles(lsdsl),NumberUtils.convertListDs2doubles(agblsl),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(lsdsl),NumberUtils.convertListDs2doubles(agblsl), lagblfl, 25)});
				return dataset;
			}
		};
		new AbstractBubbleChart("SD detect result - 全","A失球数-B失球数(正数代表A比B的防御力弱)","A进球数-B失球数(正数代表A攻击力比B防御力强)") {
			
			@Override
			public XYZDataset getXYZDataset() {
				DefaultXYZDataset dataset = new DefaultXYZDataset();
				dataset.addSeries("赢",new double[][]{NumberUtils.convertListDs2doubles(lsdsw),NumberUtils.convertListDs2doubles(agblsw),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(lsdsw),NumberUtils.convertListDs2doubles(agblsw), lagblfw, 25)});
				dataset.addSeries("平",new double[][]{NumberUtils.convertListDs2doubles(lsdsd),NumberUtils.convertListDs2doubles(agblsd),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(lsdsd),NumberUtils.convertListDs2doubles(agblsd), lagblfd, 25)});
				dataset.addSeries("输",new double[][]{NumberUtils.convertListDs2doubles(lsdsl),NumberUtils.convertListDs2doubles(agblsl),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(lsdsl),NumberUtils.convertListDs2doubles(agblsl), lagblfl, 25)});
				return dataset;
			}
		};
		
		//-------------
		new AbstractBubbleChart("SD detect result - 赢","A失球数-B失球数(正数代表A比B的防御力弱)","A失球数-B进球数(正数代表A防御力比B攻击力弱)") {
			
			@Override
			public XYZDataset getXYZDataset() {
				DefaultXYZDataset dataset = new DefaultXYZDataset();
				dataset.addSeries("赢",new double[][]{NumberUtils.convertListDs2doubles(lsdsw),NumberUtils.convertListDs2doubles(albgsw),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(lsdsw),NumberUtils.convertListDs2doubles(albgsw), lalbgfw, 25)});
				return dataset;
			}
		};
		
		new AbstractBubbleChart("SD detect result - 平","A失球数-B失球数(正数代表A比B的防御力弱)","A失球数-B进球数(正数代表A防御力比B攻击力弱)") {
			
			@Override
			public XYZDataset getXYZDataset() {
				DefaultXYZDataset dataset = new DefaultXYZDataset();
				dataset.addSeries("赢",new double[][]{{0},{0},{0}});
				dataset.addSeries("平",new double[][]{NumberUtils.convertListDs2doubles(lsdsd),NumberUtils.convertListDs2doubles(albgsd),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(lsdsd),NumberUtils.convertListDs2doubles(albgsd), lalbgfd, 25)});
				return dataset;
			}
		};
		new AbstractBubbleChart("SD detect result - 输","A失球数-B失球数(正数代表A比B的防御力弱)","A失球数-B进球数(正数代表A防御力比B攻击力弱)") {
			
			@Override
			public XYZDataset getXYZDataset() {
				DefaultXYZDataset dataset = new DefaultXYZDataset();
				dataset.addSeries("赢",new double[][]{{0},{0},{0}});
				dataset.addSeries("平",new double[][]{{0},{0},{0}});
				dataset.addSeries("输",new double[][]{NumberUtils.convertListDs2doubles(lsdsl),NumberUtils.convertListDs2doubles(albgsl),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(lsdsl),NumberUtils.convertListDs2doubles(albgsl), lalbgfl, 25)});
				return dataset;
			}
		};
		new AbstractBubbleChart("SD detect result - 全","A失球数-B失球数(正数代表A比B的防御力弱)","A失球数-B进球数(正数代表A防御力比B攻击力弱)") {
			
			@Override
			public XYZDataset getXYZDataset() {
				DefaultXYZDataset dataset = new DefaultXYZDataset();
				dataset.addSeries("赢",new double[][]{NumberUtils.convertListDs2doubles(lsdsw),NumberUtils.convertListDs2doubles(albgsw),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(lsdsw),NumberUtils.convertListDs2doubles(albgsw), lalbgfw, 25)});
				dataset.addSeries("平",new double[][]{NumberUtils.convertListDs2doubles(lsdsd),NumberUtils.convertListDs2doubles(albgsd),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(lsdsd),NumberUtils.convertListDs2doubles(albgsd), lalbgfd, 25)});
				dataset.addSeries("输",new double[][]{NumberUtils.convertListDs2doubles(lsdsl),NumberUtils.convertListDs2doubles(albgsl),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(lsdsl),NumberUtils.convertListDs2doubles(albgsl), lalbgfl, 25)});
				return dataset;
			}
		};
		
		//--------------
		new AbstractBubbleChart("SD detect result - 赢","A进球数-B失球数(正数代表A攻击力比B防御力强)","A失球数-B进球数(正数代表A防御力比B攻击力弱)") {
			
			@Override
			public XYZDataset getXYZDataset() {
				DefaultXYZDataset dataset = new DefaultXYZDataset();
				dataset.addSeries("赢",new double[][]{NumberUtils.convertListDs2doubles(agblsw),NumberUtils.convertListDs2doubles(albgsw),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(agblsw),NumberUtils.convertListDs2doubles(albgsw), agblalbgfw, 25)});
				return dataset;
			}
		};
		new AbstractBubbleChart("SD detect result - 平","A进球数-B失球数(正数代表A攻击力比B防御力强)","A失球数-B进球数(正数代表A防御力比B攻击力弱)") {
			
			@Override
			public XYZDataset getXYZDataset() {
				DefaultXYZDataset dataset = new DefaultXYZDataset();
				dataset.addSeries("赢",new double[][]{{0},{0},{0}});
				dataset.addSeries("平",new double[][]{NumberUtils.convertListDs2doubles(agblsd),NumberUtils.convertListDs2doubles(albgsd),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(agblsd),NumberUtils.convertListDs2doubles(albgsd), agblalbgfd, 25)});
				return dataset;
			}
		};
		new AbstractBubbleChart("SD detect result - 输","A进球数-B失球数(正数代表A攻击力比B防御力强)","A失球数-B进球数(正数代表A防御力比B攻击力弱)") {
			
			@Override
			public XYZDataset getXYZDataset() {
				DefaultXYZDataset dataset = new DefaultXYZDataset();
				dataset.addSeries("赢",new double[][]{{0},{0},{0}});
				dataset.addSeries("平",new double[][]{{0},{0},{0}});
				dataset.addSeries("输",new double[][]{NumberUtils.convertListDs2doubles(agblsl),NumberUtils.convertListDs2doubles(albgsl),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(agblsl),NumberUtils.convertListDs2doubles(albgsl), agblalbgfl, 25)});
				return dataset;
			}
		};
		new AbstractBubbleChart("SD detect result - 全","A进球数-B失球数(正数代表A攻击力比B防御力强)","A失球数-B进球数(正数代表A防御力比B攻击力弱)") {
			
			@Override
			public XYZDataset getXYZDataset() {
				DefaultXYZDataset dataset = new DefaultXYZDataset();
				dataset.addSeries("赢",new double[][]{NumberUtils.convertListDs2doubles(agblsw),NumberUtils.convertListDs2doubles(albgsw),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(agblsw),NumberUtils.convertListDs2doubles(albgsw), agblalbgfw, 25)});
				dataset.addSeries("平",new double[][]{NumberUtils.convertListDs2doubles(agblsd),NumberUtils.convertListDs2doubles(albgsd),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(agblsd),NumberUtils.convertListDs2doubles(albgsd), agblalbgfd, 25)});
				dataset.addSeries("输",new double[][]{NumberUtils.convertListDs2doubles(agblsl),NumberUtils.convertListDs2doubles(albgsl),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(agblsl),NumberUtils.convertListDs2doubles(albgsl), agblalbgfl, 25)});
				return dataset;
			}
		};
		
	}

}
