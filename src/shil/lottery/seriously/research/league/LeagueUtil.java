package shil.lottery.seriously.research.league;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import shil.lottery.seriously.utils.AnalyzeUtil;
import shil.lottery.seriously.vo.TeamValuePosition;
import shil.lottery.seriously.vo.WholeMatches;
import shil.lottery.sport.entity.VSTeam;
import weka.clusterers.SimpleKMeans;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

/**
 * 分析每一个联赛队伍排名情况.
 * @author LiangJingJing
 * @date Apr 25, 2015 10:43:26 AM
 */
public class LeagueUtil {
	
	/**
	 * 如果找不到,返回Integer.MAX_VALUE.
	 * 最大值不超过 AnalyzeUtil.leagalMaxMatches,最小值不低于AnalyzeUtil.leagalMinMatches
	 * @param leaguename
	 * @param wholeMatches
	 * @return
	 */
	public static int findMinMatchNumber(String leaguename,WholeMatches wholeMatches){
		
		int minmatch = Integer.MAX_VALUE;
		
		//寻找所有队中,比赛次数最小的数字,为了均衡计算比赛成绩,相同的比赛次数.
		Set<String> teamnames = wholeMatches.getLeaguesTeamnamesMap().get(leaguename);
		if(teamnames==null) return minmatch;
		for(String teamname : teamnames){
			List<VSTeam> history = wholeMatches.getTeamDigest().get(teamname).get(leaguename);
			if(history.size() < AnalyzeUtil.leagalMinMatches){
//				System.out.println(teamname+" DO NOT have enough matches: "+history.size());
				continue;
			}else{
				if(history.size() < minmatch ){
					minmatch = history.size();
				}
			}
		}
		
		if(minmatch!=Integer.MAX_VALUE){
			//不能超过最大取的比赛数量
			minmatch = (minmatch > AnalyzeUtil.leagalMaxMatches)? AnalyzeUtil.leagalMaxMatches : minmatch;
		}
		
		return minmatch;
	}
	
	public static Map<String,List<VSTeam>> refineLeagueTeamMatches(String leaguename,WholeMatches wholeMatches){
		Map<String,List<VSTeam>> cutMatches = new HashMap<String, List<VSTeam>>();
		int minmatch = findMinMatchNumber(leaguename, wholeMatches);
		if(minmatch == Integer.MAX_VALUE) return cutMatches;
		
		Set<String> teamnames = wholeMatches.getLeaguesTeamnamesMap().get(leaguename);
		//获取最终拿来分析的比赛信息
		
		for(String teamname : teamnames){
			List<VSTeam> history = wholeMatches.getTeamDigest().get(teamname).get(leaguename);
			if(history.size() >= minmatch){
				//最后面的比赛最新
				cutMatches.put(teamname, history.subList(history.size()-minmatch, history.size()));
			}
		}
		
		return cutMatches;
	}
	
	public static Map<String,List<VSTeam>> getAllLeagueTeamMatches(String leaguename,WholeMatches wholeMatches){
		Map<String,List<VSTeam>> allMatches = new HashMap<String, List<VSTeam>>();
		
		Set<String> teamnames = wholeMatches.getLeaguesTeamnamesMap().get(leaguename);
		//获取最终拿来分析的比赛信息
		
		for(String teamname : teamnames){
			List<VSTeam> history = wholeMatches.getTeamDigest().get(teamname).get(leaguename);
				allMatches.put(teamname, history);
		}
		
		return allMatches;
	}
	
	public static LeaguePosition analyzeOneLeague(String leaguename,WholeMatches wholeMatches){
		
		LeaguePosition leaguePosition = new LeaguePosition(leaguename);

		int minmatch = findMinMatchNumber(leaguename,wholeMatches);
		if(minmatch==Integer.MAX_VALUE) return null;
		
		leaguePosition.setMinMatches(minmatch);
		leaguePosition.setTotalTeamNum(wholeMatches.getLeaguesTeamnamesMap().get(leaguename).size());
		
		//获取最终拿来分析的比赛信息
		Map<String,List<VSTeam>> cutMatches = refineLeagueTeamMatches(leaguename, wholeMatches);
		if(cutMatches.isEmpty()) return null;
		
		leaguePosition.setContainTeamNum(cutMatches.size());
		
		if(!leaguePosition.isBelievable()) return null;
		
		//计算总分
		//索引map
		Map<String,TeamValuePosition> teamValuePositionMap = new HashMap<String, TeamValuePosition>();
		//计算List
		List<TeamValuePosition> teamValuePositions = new ArrayList<TeamValuePosition>();
		for(Entry<String,List<VSTeam>> entry : cutMatches.entrySet()){
			List<VSTeam> vsTeams = entry.getValue();
			TeamValuePosition teamValuePosition = new TeamValuePosition(leaguename,entry.getKey());
			//统计队伍得分数 
			for(VSTeam vsTeam : vsTeams){
				teamValuePosition.addValue(vsTeam.getMatch_Result());
			}
			teamValuePositionMap.put(entry.getKey(), teamValuePosition);
			teamValuePositions.add(teamValuePosition);
		}
		Collections.sort(teamValuePositions);
		
		if(teamValuePositions.size()<LeaguePosition.perfect) return null;
		
		//构造WEKA cluster
		//数据类型
		Attribute numeric = new Attribute("teamValue");
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();
		attributes.add(numeric);
		//数据集
		Instances dataset = new Instances("dateset",attributes,teamValuePositions.size());
		//填充数据
		for(TeamValuePosition teamValuePosition : teamValuePositions){
			double[] value = new double[dataset.numAttributes()];
			value[0] = teamValuePosition.getValue();
			//一行数据
			Instance inst = new DenseInstance(1,value);
			dataset.add(inst);
		}
		//运行参数
		String[] options = new String[6];
		options[0] = "-N";
		options[1] = String.valueOf(LeaguePosition.perfect); //分5段
		options[2] = "-I";
		options[3] = "100";
		options[4] = "-init";
		options[5] = "1";
		//简单聚合算法
		SimpleKMeans simpleKMeans = new SimpleKMeans();
		try{
			//训练
			simpleKMeans.setOptions(options);
			simpleKMeans.buildClusterer(dataset);
			//分析
			int current = -1;
			int currentCool = LeaguePosition.perfect;
			for(int i = 0; i < dataset.size(); i++){
				int level = simpleKMeans.clusterInstance(dataset.get(i));
				if(current == -1){
					//初始化状态值,由于排序时分数最高的在前面,所以开始的球队最棒
					current = level;
					teamValuePositions.get(i).setLevel(currentCool);
				}else if(level==current){
					//在同一梯队
					teamValuePositions.get(i).setLevel(currentCool);
				}else{
					//梯队发生变化 x!=current
					current = level;
					currentCool--;
					teamValuePositions.get(i).setLevel(currentCool);
				}
			}
		}catch(Exception e){
			System.out.println("hey , nice shot~ ");
			e.printStackTrace();
			return null;
		}
	
		leaguePosition.setTeamValuePositions(teamValuePositions);
		leaguePosition.setTeamValuePositionMap(teamValuePositionMap);
		
		return leaguePosition;
	}
	
	public static void main(String[] args){
		
		WholeMatches wholeMatches = AnalyzeUtil.analyzeWholeRecords();
		Set<String> leaguenames = wholeMatches.getLeaguesNames();
		
		for(String leaguename : leaguenames){
			System.out.println(LeagueUtil.analyzeOneLeague(leaguename,wholeMatches));
			
			
			System.out.println("\n--------------\n");
		}
	}

}
