package shil.lottery.seriously.research.result013.correlation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.Frequency;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYZDataset;

import shil.lottery.seriously.utils.AbstractBubbleChart;
import shil.lottery.seriously.utils.AnalyzeUtil;
import shil.lottery.seriously.utils.AnalyzeUtil.AVG_TP;
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
		
		for(AVG_TP tp : AVG_TP.values()){
			System.out.println("下面进行平均数为:"+tp.name()+" 的分析");
			final double mult = 1;
			//描述 最后wdl代表胜负平
			//由于要区分不同的情况的数值,所以分出来一大堆做记录的对象.
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
				
				AVG_TP avg_tp = tp;
				//分析,应该根据不同的数值,SD,Mean,GMean,等,写函数,传值取不同的结果,传入Statistics
				double gsd  = AnalyzeUtil.getAVG(as.getGoalStatistics(), avg_tp) - AnalyzeUtil.getAVG(bs.getGoalStatistics(),avg_tp);
				double lsd = AnalyzeUtil.getAVG(as.getLostStatistics(), avg_tp) - AnalyzeUtil.getAVG(bs.getLostStatistics(),avg_tp);
				double agbl = AnalyzeUtil.getAVG(as.getGoalStatistics(), avg_tp) - AnalyzeUtil.getAVG(bs.getLostStatistics(),avg_tp); 
				double albg = AnalyzeUtil.getAVG(as.getLostStatistics(), avg_tp) - AnalyzeUtil.getAVG(bs.getGoalStatistics(),avg_tp); 
	
				mrs.add((double) vsTeam.getMatch_Result());
	
				//根据胜负平分类,这里的记录形式不太好,最后应该用[]List来记录
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
			
			Score013AnalyzeProbility score013AnalyzeResult = Score013AnalyzeProbility.analyzeRecordsList(tp, wins, draws, loses);
			System.out.println(score013AnalyzeResult);
			
			/*
			//这里的硬拼数据展示太白痴了,应该用for[]来自动3*2的循环组装展示数据,将title也放到[]里面来拼接,下次再有这种事可千万不要这么做了.累坏了00:43
			new AbstractBubbleChart("gsd vs lsd - 赢 "+ tp,"A进球数-B进球数(正数代表A的攻击力比B强)","A失球数-B失球数(正数代表A比B的防御力弱)") {
				
				@Override
				public XYZDataset getXYZDataset() {
					DefaultXYZDataset dataset = new DefaultXYZDataset();
					dataset.addSeries(AnalyzeUtil.winS,new double[][]{NumberUtils.convertListDs2doubles(gsdsw),NumberUtils.convertListDs2doubles(lsdsw),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(gsdsw),NumberUtils.convertListDs2doubles(lsdsw), glfw, mult)});
					return dataset;
				}
			};
			
			new AbstractBubbleChart("gsd vs lsd - 平"+ tp,"A进球数-B进球数(正数代表A的攻击力比B强)","A失球数-B失球数(正数代表A比B的防御力弱)") {
				
				@Override
				public XYZDataset getXYZDataset() {
					DefaultXYZDataset dataset = new DefaultXYZDataset();
					double[][] dot = new double[][]{{0},{0},{0}};
					dataset.addSeries(AnalyzeUtil.winS, dot);
					dataset.addSeries(AnalyzeUtil.drawS,new double[][]{NumberUtils.convertListDs2doubles(gsdsd),NumberUtils.convertListDs2doubles(lsdsd),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(gsdsd),NumberUtils.convertListDs2doubles(lsdsd), glfd, mult)});
					return dataset;
				}
			};
			
			new AbstractBubbleChart("gsd vs lsd - 输"+ tp,"A进球数-B进球数(正数代表A的攻击力比B强)","A失球数-B失球数(正数代表A比B的防御力弱)") {
				
				@Override
				public XYZDataset getXYZDataset() {
					DefaultXYZDataset dataset = new DefaultXYZDataset();
					double[][] dot = new double[][]{{0},{0},{0}};
					dataset.addSeries(AnalyzeUtil.winS, dot);
					dataset.addSeries(AnalyzeUtil.drawS, dot);
					dataset.addSeries(AnalyzeUtil.loseS,new double[][]{NumberUtils.convertListDs2doubles(gsdsl),NumberUtils.convertListDs2doubles(lsdsl),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(gsdsl),NumberUtils.convertListDs2doubles(lsdsl), glfl, mult)});
					return dataset;
				}
			};
			
			new AbstractBubbleChart("gsd vs lsd - 全"+ tp,"A进球数-B进球数(正数代表A的攻击力比B强)","A失球数-B失球数(正数代表A比B的防御力弱)") {
				
				@Override
				public XYZDataset getXYZDataset() {
					DefaultXYZDataset dataset = new DefaultXYZDataset();
					dataset.addSeries(AnalyzeUtil.winS,new double[][]{NumberUtils.convertListDs2doubles(gsdsw),NumberUtils.convertListDs2doubles(lsdsw),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(gsdsw),NumberUtils.convertListDs2doubles(lsdsw), glfw, mult)});
					dataset.addSeries(AnalyzeUtil.drawS,new double[][]{NumberUtils.convertListDs2doubles(gsdsd),NumberUtils.convertListDs2doubles(lsdsd),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(gsdsd),NumberUtils.convertListDs2doubles(lsdsd), glfd, mult)});
					dataset.addSeries(AnalyzeUtil.loseS,new double[][]{NumberUtils.convertListDs2doubles(gsdsl),NumberUtils.convertListDs2doubles(lsdsl),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(gsdsl),NumberUtils.convertListDs2doubles(lsdsl), glfl, mult)});
					return dataset;
				}
			};
			
			//-----
			
			
			new AbstractBubbleChart("gsd vs agbl - 赢"+ tp,"A进球数-B进球数(正数代表A的攻击力比B强)","A进球数-B失球数(正数代表A攻击力比B防御力强)") {
				
				@Override
				public XYZDataset getXYZDataset() {
					DefaultXYZDataset dataset = new DefaultXYZDataset();
					dataset.addSeries(AnalyzeUtil.winS,new double[][]{NumberUtils.convertListDs2doubles(gsdsw),NumberUtils.convertListDs2doubles(agblsw),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(gsdsw),NumberUtils.convertListDs2doubles(agblsw), gagblfw, mult)});
					return dataset;
				}
			};
			
			new AbstractBubbleChart("gsd vs agbl - 平"+ tp,"A进球数-B进球数(正数代表A的攻击力比B强)","A进球数-B失球数(正数代表A攻击力比B防御力强)") {
				
				@Override
				public XYZDataset getXYZDataset() {
					DefaultXYZDataset dataset = new DefaultXYZDataset();
					dataset.addSeries(AnalyzeUtil.winS,new double[][]{{0},{0},{0}});
					dataset.addSeries(AnalyzeUtil.drawS,new double[][]{NumberUtils.convertListDs2doubles(gsdsd),NumberUtils.convertListDs2doubles(agblsd),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(gsdsd),NumberUtils.convertListDs2doubles(agblsd), gagblfd, mult)});
					return dataset;
				}
			};
			
			new AbstractBubbleChart("gsd vs agbl - 输"+ tp,"A进球数-B进球数(正数代表A的攻击力比B强)","A进球数-B失球数(正数代表A攻击力比B防御力强)") {
				
				@Override
				public XYZDataset getXYZDataset() {
					DefaultXYZDataset dataset = new DefaultXYZDataset();
					dataset.addSeries(AnalyzeUtil.winS,new double[][]{{0},{0},{0}});
					dataset.addSeries(AnalyzeUtil.drawS,new double[][]{{0},{0},{0}});
					dataset.addSeries(AnalyzeUtil.loseS,new double[][]{NumberUtils.convertListDs2doubles(gsdsl),NumberUtils.convertListDs2doubles(agblsl),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(gsdsl),NumberUtils.convertListDs2doubles(agblsl), gagblfl, mult)});
					return dataset;
				}
			};
			
			new AbstractBubbleChart("gsd vs agbl - 全"+ tp,"A进球数-B进球数(正数代表A的攻击力比B强)","A进球数-B失球数(正数代表A攻击力比B防御力强)") {
				
				@Override
				public XYZDataset getXYZDataset() {
					DefaultXYZDataset dataset = new DefaultXYZDataset();
					dataset.addSeries(AnalyzeUtil.winS,new double[][]{NumberUtils.convertListDs2doubles(gsdsw),NumberUtils.convertListDs2doubles(agblsw),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(gsdsw),NumberUtils.convertListDs2doubles(agblsw), gagblfw, mult)});
					dataset.addSeries(AnalyzeUtil.drawS,new double[][]{NumberUtils.convertListDs2doubles(gsdsd),NumberUtils.convertListDs2doubles(agblsd),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(gsdsd),NumberUtils.convertListDs2doubles(agblsd), gagblfd, mult)});
					dataset.addSeries(AnalyzeUtil.loseS,new double[][]{NumberUtils.convertListDs2doubles(gsdsl),NumberUtils.convertListDs2doubles(agblsl),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(gsdsl),NumberUtils.convertListDs2doubles(agblsl), gagblfl, mult)});
					return dataset;
				}
			};
			
			//------
			
			new AbstractBubbleChart("gsd vs albg - 赢"+ tp,"A进球数-B进球数(正数代表A的攻击力比B强)","A失球数-B进球数(正数代表A防御力比B攻击力弱)") {
				
				@Override
				public XYZDataset getXYZDataset() {
					DefaultXYZDataset dataset = new DefaultXYZDataset();
					dataset.addSeries(AnalyzeUtil.winS,new double[][]{NumberUtils.convertListDs2doubles(gsdsw),NumberUtils.convertListDs2doubles(albgsw),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(gsdsw),NumberUtils.convertListDs2doubles(albgsw), galbgfw, mult)});
					return dataset;
				}
			};
			
			new AbstractBubbleChart("gsd vs albg - 平"+ tp,"A进球数-B进球数(正数代表A的攻击力比B强)","A失球数-B进球数(正数代表A防御力比B攻击力弱)") {
				
				@Override
				public XYZDataset getXYZDataset() {
					DefaultXYZDataset dataset = new DefaultXYZDataset();
					dataset.addSeries(AnalyzeUtil.winS,new double[][]{{0},{0},{0}});
					dataset.addSeries(AnalyzeUtil.drawS,new double[][]{NumberUtils.convertListDs2doubles(gsdsd),NumberUtils.convertListDs2doubles(albgsd),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(gsdsd),NumberUtils.convertListDs2doubles(albgsd), galbgfd, mult)});
					return dataset;
				}
			};
			
			new AbstractBubbleChart("gsd vs albg - 输"+ tp,"A进球数-B进球数(正数代表A的攻击力比B强)","A失球数-B进球数(正数代表A防御力比B攻击力弱)") {
				
				@Override
				public XYZDataset getXYZDataset() {
					DefaultXYZDataset dataset = new DefaultXYZDataset();
					dataset.addSeries(AnalyzeUtil.winS,new double[][]{{0},{0},{0}});
					dataset.addSeries(AnalyzeUtil.drawS,new double[][]{{0},{0},{0}});
					dataset.addSeries(AnalyzeUtil.loseS,new double[][]{NumberUtils.convertListDs2doubles(gsdsl),NumberUtils.convertListDs2doubles(albgsl),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(gsdsl),NumberUtils.convertListDs2doubles(albgsl), galbgfl, mult)});
					return dataset;
				}
			};
			
			new AbstractBubbleChart("gsd vs albg - 全"+ tp,"A进球数-B进球数(正数代表A的攻击力比B强)","A失球数-B进球数(正数代表A防御力比B攻击力弱)") {
				
				@Override
				public XYZDataset getXYZDataset() {
					DefaultXYZDataset dataset = new DefaultXYZDataset();
					dataset.addSeries(AnalyzeUtil.winS,new double[][]{NumberUtils.convertListDs2doubles(gsdsw),NumberUtils.convertListDs2doubles(albgsw),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(gsdsw),NumberUtils.convertListDs2doubles(albgsw), galbgfw, mult)});
					dataset.addSeries(AnalyzeUtil.drawS,new double[][]{NumberUtils.convertListDs2doubles(gsdsd),NumberUtils.convertListDs2doubles(albgsd),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(gsdsd),NumberUtils.convertListDs2doubles(albgsd), galbgfd, mult)});
					dataset.addSeries(AnalyzeUtil.loseS,new double[][]{NumberUtils.convertListDs2doubles(gsdsl),NumberUtils.convertListDs2doubles(albgsl),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(gsdsl),NumberUtils.convertListDs2doubles(albgsl), galbgfl, mult)});
					return dataset;
				}
			};
			
			//----------------
			new AbstractBubbleChart("lsd vs agbl - 赢"+ tp,"A失球数-B失球数(正数代表A比B的防御力弱)","A进球数-B失球数(正数代表A攻击力比B防御力强)") {
				
				@Override
				public XYZDataset getXYZDataset() {
					DefaultXYZDataset dataset = new DefaultXYZDataset();
					dataset.addSeries(AnalyzeUtil.winS,new double[][]{NumberUtils.convertListDs2doubles(lsdsw),NumberUtils.convertListDs2doubles(agblsw),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(lsdsw),NumberUtils.convertListDs2doubles(agblsw), lagblfw, mult)});
					return dataset;
				}
			};
			
			new AbstractBubbleChart("lsd vs agbl - 平"+ tp,"A失球数-B失球数(正数代表A比B的防御力弱)","A进球数-B失球数(正数代表A攻击力比B防御力强)") {
				
				@Override
				public XYZDataset getXYZDataset() {
					DefaultXYZDataset dataset = new DefaultXYZDataset();
					dataset.addSeries(AnalyzeUtil.winS,new double[][]{{0},{0},{0}});
					dataset.addSeries(AnalyzeUtil.drawS,new double[][]{NumberUtils.convertListDs2doubles(lsdsd),NumberUtils.convertListDs2doubles(agblsd),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(lsdsd),NumberUtils.convertListDs2doubles(agblsd), lagblfd, mult)});
					return dataset;
				}
			};
			new AbstractBubbleChart("lsd vs agbl - 输"+ tp,"A失球数-B失球数(正数代表A比B的防御力弱)","A进球数-B失球数(正数代表A攻击力比B防御力强)") {
				
				@Override
				public XYZDataset getXYZDataset() {
					DefaultXYZDataset dataset = new DefaultXYZDataset();
					dataset.addSeries(AnalyzeUtil.winS,new double[][]{{0},{0},{0}});
					dataset.addSeries(AnalyzeUtil.drawS,new double[][]{{0},{0},{0}});
					dataset.addSeries(AnalyzeUtil.loseS,new double[][]{NumberUtils.convertListDs2doubles(lsdsl),NumberUtils.convertListDs2doubles(agblsl),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(lsdsl),NumberUtils.convertListDs2doubles(agblsl), lagblfl, mult)});
					return dataset;
				}
			};
			new AbstractBubbleChart("lsd vs agbl - 全"+ tp,"A失球数-B失球数(正数代表A比B的防御力弱)","A进球数-B失球数(正数代表A攻击力比B防御力强)") {
				
				@Override
				public XYZDataset getXYZDataset() {
					DefaultXYZDataset dataset = new DefaultXYZDataset();
					dataset.addSeries(AnalyzeUtil.winS,new double[][]{NumberUtils.convertListDs2doubles(lsdsw),NumberUtils.convertListDs2doubles(agblsw),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(lsdsw),NumberUtils.convertListDs2doubles(agblsw), lagblfw, mult)});
					dataset.addSeries(AnalyzeUtil.drawS,new double[][]{NumberUtils.convertListDs2doubles(lsdsd),NumberUtils.convertListDs2doubles(agblsd),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(lsdsd),NumberUtils.convertListDs2doubles(agblsd), lagblfd, mult)});
					dataset.addSeries(AnalyzeUtil.loseS,new double[][]{NumberUtils.convertListDs2doubles(lsdsl),NumberUtils.convertListDs2doubles(agblsl),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(lsdsl),NumberUtils.convertListDs2doubles(agblsl), lagblfl, mult)});
					return dataset;
				}
			};
			
			//-------------
			new AbstractBubbleChart("lsd vs albg - 赢"+ tp,"A失球数-B失球数(正数代表A比B的防御力弱)","A失球数-B进球数(正数代表A防御力比B攻击力弱)") {
				
				@Override
				public XYZDataset getXYZDataset() {
					DefaultXYZDataset dataset = new DefaultXYZDataset();
					dataset.addSeries(AnalyzeUtil.winS,new double[][]{NumberUtils.convertListDs2doubles(lsdsw),NumberUtils.convertListDs2doubles(albgsw),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(lsdsw),NumberUtils.convertListDs2doubles(albgsw), lalbgfw, mult)});
					return dataset;
				}
			};
			
			new AbstractBubbleChart("lsd vs albg - 平"+ tp,"A失球数-B失球数(正数代表A比B的防御力弱)","A失球数-B进球数(正数代表A防御力比B攻击力弱)") {
				
				@Override
				public XYZDataset getXYZDataset() {
					DefaultXYZDataset dataset = new DefaultXYZDataset();
					dataset.addSeries(AnalyzeUtil.winS,new double[][]{{0},{0},{0}});
					dataset.addSeries(AnalyzeUtil.drawS,new double[][]{NumberUtils.convertListDs2doubles(lsdsd),NumberUtils.convertListDs2doubles(albgsd),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(lsdsd),NumberUtils.convertListDs2doubles(albgsd), lalbgfd, mult)});
					return dataset;
				}
			};
			new AbstractBubbleChart("lsd vs albg - 输"+ tp,"A失球数-B失球数(正数代表A比B的防御力弱)","A失球数-B进球数(正数代表A防御力比B攻击力弱)") {
				
				@Override
				public XYZDataset getXYZDataset() {
					DefaultXYZDataset dataset = new DefaultXYZDataset();
					dataset.addSeries(AnalyzeUtil.winS,new double[][]{{0},{0},{0}});
					dataset.addSeries(AnalyzeUtil.drawS,new double[][]{{0},{0},{0}});
					dataset.addSeries(AnalyzeUtil.loseS,new double[][]{NumberUtils.convertListDs2doubles(lsdsl),NumberUtils.convertListDs2doubles(albgsl),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(lsdsl),NumberUtils.convertListDs2doubles(albgsl), lalbgfl, mult)});
					return dataset;
				}
			};
			new AbstractBubbleChart("lsd vs albg - 全"+ tp,"A失球数-B失球数(正数代表A比B的防御力弱)","A失球数-B进球数(正数代表A防御力比B攻击力弱)") {
				
				@Override
				public XYZDataset getXYZDataset() {
					DefaultXYZDataset dataset = new DefaultXYZDataset();
					dataset.addSeries(AnalyzeUtil.winS,new double[][]{NumberUtils.convertListDs2doubles(lsdsw),NumberUtils.convertListDs2doubles(albgsw),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(lsdsw),NumberUtils.convertListDs2doubles(albgsw), lalbgfw, mult)});
					dataset.addSeries(AnalyzeUtil.drawS,new double[][]{NumberUtils.convertListDs2doubles(lsdsd),NumberUtils.convertListDs2doubles(albgsd),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(lsdsd),NumberUtils.convertListDs2doubles(albgsd), lalbgfd, mult)});
					dataset.addSeries(AnalyzeUtil.loseS,new double[][]{NumberUtils.convertListDs2doubles(lsdsl),NumberUtils.convertListDs2doubles(albgsl),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(lsdsl),NumberUtils.convertListDs2doubles(albgsl), lalbgfl, mult)});
					return dataset;
				}
			};
			
			//--------------
			new AbstractBubbleChart("agbl vs albg - 赢"+ tp,"A进球数-B失球数(正数代表A攻击力比B防御力强)","A失球数-B进球数(正数代表A防御力比B攻击力弱)") {
				
				@Override
				public XYZDataset getXYZDataset() {
					DefaultXYZDataset dataset = new DefaultXYZDataset();
					dataset.addSeries(AnalyzeUtil.winS,new double[][]{NumberUtils.convertListDs2doubles(agblsw),NumberUtils.convertListDs2doubles(albgsw),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(agblsw),NumberUtils.convertListDs2doubles(albgsw), agblalbgfw, mult)});
					return dataset;
				}
			};
			new AbstractBubbleChart("agbl vs albg - 平"+ tp,"A进球数-B失球数(正数代表A攻击力比B防御力强)","A失球数-B进球数(正数代表A防御力比B攻击力弱)") {
				
				@Override
				public XYZDataset getXYZDataset() {
					DefaultXYZDataset dataset = new DefaultXYZDataset();
					dataset.addSeries(AnalyzeUtil.winS,new double[][]{{0},{0},{0}});
					dataset.addSeries(AnalyzeUtil.drawS,new double[][]{NumberUtils.convertListDs2doubles(agblsd),NumberUtils.convertListDs2doubles(albgsd),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(agblsd),NumberUtils.convertListDs2doubles(albgsd), agblalbgfd, mult)});
					return dataset;
				}
			};
			new AbstractBubbleChart("agbl vs albg - 输"+ tp,"A进球数-B失球数(正数代表A攻击力比B防御力强)","A失球数-B进球数(正数代表A防御力比B攻击力弱)") {
				
				@Override
				public XYZDataset getXYZDataset() {
					DefaultXYZDataset dataset = new DefaultXYZDataset();
					dataset.addSeries(AnalyzeUtil.winS,new double[][]{{0},{0},{0}});
					dataset.addSeries(AnalyzeUtil.drawS,new double[][]{{0},{0},{0}});
					dataset.addSeries(AnalyzeUtil.loseS,new double[][]{NumberUtils.convertListDs2doubles(agblsl),NumberUtils.convertListDs2doubles(albgsl),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(agblsl),NumberUtils.convertListDs2doubles(albgsl), agblalbgfl, mult)});
					return dataset;
				}
			};
			new AbstractBubbleChart("agbl vs albg - 全"+ tp,"A进球数-B失球数(正数代表A攻击力比B防御力强)","A失球数-B进球数(正数代表A防御力比B攻击力弱)") {
				
				@Override
				public XYZDataset getXYZDataset() {
					DefaultXYZDataset dataset = new DefaultXYZDataset();
					dataset.addSeries(AnalyzeUtil.winS,new double[][]{NumberUtils.convertListDs2doubles(agblsw),NumberUtils.convertListDs2doubles(albgsw),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(agblsw),NumberUtils.convertListDs2doubles(albgsw), agblalbgfw, mult)});
					dataset.addSeries(AnalyzeUtil.drawS,new double[][]{NumberUtils.convertListDs2doubles(agblsd),NumberUtils.convertListDs2doubles(albgsd),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(agblsd),NumberUtils.convertListDs2doubles(albgsd), agblalbgfd, mult)});
					dataset.addSeries(AnalyzeUtil.loseS,new double[][]{NumberUtils.convertListDs2doubles(agblsl),NumberUtils.convertListDs2doubles(albgsl),AnalyzeUtil.getFrequencyZvaluebyXYDoublesPct(NumberUtils.convertListDs2doubles(agblsl),NumberUtils.convertListDs2doubles(albgsl), agblalbgfl, mult)});
					return dataset;
				}
			};
			*/
		}
	}
}
