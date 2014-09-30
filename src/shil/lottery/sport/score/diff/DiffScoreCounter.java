package shil.lottery.sport.score.diff;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import shil.lottery.sport.entity.ScoreCounter;

/**
 * 
 * @author LiangJingJing
 * 2014-09-29 23:09
 */
public class DiffScoreCounter {
	
	private String team_name;
	//diff,score,scorecounter
	private Map<Integer,Map<Integer,ScoreCounter>> diffScoreMap;
	
	public DiffScoreCounter(String team_name)
	{
		this.team_name = team_name;
		this.diffScoreMap = new TreeMap<Integer, Map<Integer,ScoreCounter>>();
	}
	
	public Map<Integer,ScoreCounter> getDiffMap(int diff)
	{
		Map<Integer,ScoreCounter> scoreMap = this.diffScoreMap.get(diff);
		if(scoreMap==null)
		{
			scoreMap = new TreeMap<Integer,ScoreCounter>();
			this.diffScoreMap.put(diff, scoreMap);
		}
		
		return scoreMap;
	}
	
	/**
	 * 返回Set中所有的diff值的分配情况
	 * @param is Set<Integer>
	 * @return List<ScoreCounter>
	 * @since 2014-09-30 16:42
	 */
	public List<ScoreCounter> combineDiffMap(Set<Integer> is)
	{
		Map<Integer,ScoreCounter> combineMap = new TreeMap<Integer, ScoreCounter>();
		List<ScoreCounter> scs = new ArrayList<ScoreCounter>();
		//计算所有score的总值,为了计算weight用
		double t_t_score = 0;
		for(int i:is)
		{
			t_t_score += getDiff_totalScoreCount(i);
		}
		
		//分析每一个diff的进球情况,统一在一起.
		for(int i: is)
		{
			Map<Integer,ScoreCounter> scoremap = getDiffMap(i);
			for(Entry<Integer,ScoreCounter> e : scoremap.entrySet())
			{
				ScoreCounter sc = combineMap.get(e.getKey());
				if(sc==null)
				{
					sc = new ScoreCounter(e.getKey());
					combineMap.put(e.getKey(), sc);
				}
				sc.increaseCounter(e.getValue().getCounter());
			}
		}
		
		//汇总分析
		for(Entry<Integer,ScoreCounter> e : combineMap.entrySet())
		{
			ScoreCounter sc = e.getValue();
			sc.increaseWeight(sc.getCounter()/t_t_score);
			scs.add(sc);
		}
		
		return scs;
	}
	
	/**
	 * 返回此diff值所包含的所有进球数
	 * @param diff
	 * @return double
	 */
	public double getDiff_totalScoreCount(int diff)
	{
		double diff_total_score_count = 0;
		Map<Integer,ScoreCounter> scoreMap = this.diffScoreMap.get(diff);
		for(ScoreCounter sc : scoreMap.values())
		{
			diff_total_score_count += sc.getCounter();
		}
		
		return diff_total_score_count;
	}
	
	/**
	 * 增加一个diff中的进球数的统计值
	 * @param diff
	 * @param score
	 */
	public void increaseDiffScore(int diff, int score)
	{
		Map<Integer,ScoreCounter> scoremap = getDiffMap(diff);  
		ScoreCounter sc = scoremap.get(score);
		if(sc==null)
		{
			sc = new ScoreCounter(score);
			scoremap.put(score, sc);
		}
		sc.increaseBingo();
	}
	
	/**
	 * 增加diff的进球数的counter和权重
	 * @param diff
	 * @param score
	 * @param counter
	 * @param weight
	 */
	public void increaseDiffScoreWeight(int diff, int score,double counter,double weight)
	{
		Map<Integer,ScoreCounter> scoremap = getDiffMap(diff);  
		ScoreCounter sc = scoremap.get(score);
		if(sc==null)
		{
			sc = new ScoreCounter(score);
			scoremap.put(score, sc);
		}
		sc.increaseCounter(counter);
		sc.increaseWeight(weight);
	}

	public String getTeam_name() 
	{
		return team_name;
	}

	public Map<Integer, Map<Integer, ScoreCounter>> getDiffScoreMap() 
	{
		return diffScoreMap;
	}

	@Override
	public String toString() {
		return "DiffScoreCounter [team_name=" + team_name + ", diffScoreMap="
				+ diffScoreMap + "]";
	}
	
}
