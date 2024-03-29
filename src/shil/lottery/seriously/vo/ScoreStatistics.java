package shil.lottery.seriously.vo;

import java.util.List;

import org.apache.commons.math3.stat.Frequency;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import shil.lottery.seriously.utils.AnalyzeUtil;
import shil.lottery.sport.entity.VSTeam;

/**
 * 联赛名称 球队名称 总场次 总进球 总失球 进球平均/标准差 失球平均/标准差 进n球纪录<Map<score,count>>
 * 失n球纪录<Map<score,count>>
 * 
 * 这个类只能记录某一个球队的数据,是一个大体的统计,没有精细到某一场比赛,也没有关联赔率相关信息. needs to improve in other codes
 * 
 * @author yuanshun.sl
 * @since 2015-April-17 11:10
 */
public class ScoreStatistics {
	
	private String leaguename;
	private String teamname;
	//胜负平统计
	private Frequency match013;
	//记录进球的平均数量和基本数据分析
	private DescriptiveStatistics goalStatistics;
	private DescriptiveStatistics lostStatistics;
	//记录进球的数量的频率统计信息
	private Frequency goalFrequency;
	private Frequency lostFrequency;

	// 主场数据
	private ScoreStatistics hostScoreStatistics;
	// 客场数据
	private ScoreStatistics guestScoreStatistics;

	/**
	 * 构造函数
	 * @param isMain 这里判断是否为主对象,从对象如主客场比赛信息里面没有嵌套自己,这个作为标示
	 */
	private ScoreStatistics(boolean isMain) {
		this.goalStatistics = new DescriptiveStatistics();
		this.lostStatistics = new DescriptiveStatistics();
		this.goalFrequency =  new Frequency();
		this.lostFrequency = new Frequency();
		this.match013 = new Frequency();
		//判断是否为主对象
		if(isMain){
			this.hostScoreStatistics = new ScoreStatistics(false);
			this.guestScoreStatistics = new ScoreStatistics(false);
		}
		
	}

	public String getLeaguename() {
		return leaguename;
	}

	public String getTeamname() {
		return teamname;
	}

	/**
	 * 分析联赛和某队的进球数
	 * @param leaguename 联赛名称
	 * @param teamname 球队名称
	 * @param vsTeams 历史记录
	 * @return ScoreStatistics
	 */
	public static ScoreStatistics analyzeVSTeams2scoreStatistics(String leaguename, String teamname, List<VSTeam> vsTeams) {
		ScoreStatistics scoreStatistics = new ScoreStatistics(true);
		scoreStatistics.leaguename = leaguename;
		scoreStatistics.teamname = teamname;

		for (VSTeam vsTeam : vsTeams) {
			int pos = AnalyzeUtil.pos(teamname, vsTeam.getVs());
			if(pos==-1) throw new RuntimeException("wtf of this , should not be happend, continue;");
			feedScoreStatistics(scoreStatistics, pos, vsTeam.getGoals());
			
			//host?
			if(pos==0){
				feedScoreStatistics(scoreStatistics.hostScoreStatistics, pos, vsTeam.getGoals());
			}else if(pos==1){
				feedScoreStatistics(scoreStatistics.guestScoreStatistics, pos, vsTeam.getGoals());
			}
		}
		
		return scoreStatistics;
	}
	
	/**
	 * 具体将进球数/失球数放入统计对象中
	 * @param scoreStatistics
	 * @param position
	 * @param goals
	 */
	public static void feedScoreStatistics(ScoreStatistics scoreStatistics , int position , double[] goals){
		int oppos = AnalyzeUtil.oppos(position);
		scoreStatistics.goalStatistics.addValue(goals[position]);
		scoreStatistics.lostStatistics.addValue(goals[oppos]);
		scoreStatistics.goalFrequency.addValue(goals[position]);
		scoreStatistics.lostFrequency.addValue(goals[oppos]);
		scoreStatistics.match013.addValue(AnalyzeUtil.match013(position,goals));
	}

	public Frequency getMatch013() {
		return match013;
	}

	public DescriptiveStatistics getGoalStatistics() {
		return goalStatistics;
	}

	public DescriptiveStatistics getLostStatistics() {
		return lostStatistics;
	}

	public Frequency getGoalFrequency() {
		return goalFrequency;
	}

	public Frequency getLostFrequency() {
		return lostFrequency;
	}

	public ScoreStatistics getHostScoreStatistics() {
		return hostScoreStatistics;
	}

	public ScoreStatistics getGuestScoreStatistics() {
		return guestScoreStatistics;
	}

}
