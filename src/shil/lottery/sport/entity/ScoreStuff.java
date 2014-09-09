package shil.lottery.sport.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shil.lottery.sport.statistics.NumberUtils;
import shil.lottery.sport.statistics.StatisticUtils;

/**
 * 记录进球数类
 * refine的概念是去掉一个最高值一个最低值之类的想法,现在是百分比
 * @author LiangJingJing
 * @since 2014-07-26 19:51
 */
public class ScoreStuff {
	
	private List<Integer> scores;
	private Map<Integer,ScoreCounter> hitCounterMap;
	
	private double refineRate = 0.05;
	
	public ScoreStuff() {
		this.scores = new ArrayList<Integer>();
		this.hitCounterMap  = new HashMap<Integer, ScoreCounter>();
	}
	
	public List<Integer> getScores() {
		
		return Collections.unmodifiableList(scores);
	}
	
	public void addScores(int score)
	{
		this.scores.add(score);
		
		ScoreCounter scoreCounter = hitCounterMap.get(score);
		if(scoreCounter==null)
		{
			scoreCounter = new ScoreCounter(score);
			hitCounterMap.put(score, scoreCounter);
		}
		scoreCounter.increaseBingo();
	}
	
	public double[] getScoresDouble()
	{
		return NumberUtils.convertListsI2doubles(getScores());
	}
	
	@Deprecated
	public double[] getRefineScoresDouble()
	{
		int remove = (int) Math.ceil(((double)scores.size() * refineRate));
		//如果长度不够refine,则返回原始数据,一般出现在只有一条数据的情况.
		if(2 * remove > scores.size())
		{
			return getScoresDouble();
		}
		
		List<Integer> copy = new ArrayList<Integer>(scores);
		Collections.sort(copy);
		List<Integer> refineScores = copy.subList(remove, copy.size()-remove);
		double[] r = new double[refineScores.size()];
		for(int i=0;i<refineScores.size();i++)
		{
			r[i] = (double) refineScores.get(i);
		}
		
		return r;
	}
	
	@Deprecated
	public double getRefineArithmeticAverage()
	{
		return StatisticUtils.arithmeticMean(getRefineScoresDouble());
	}
	
	@Deprecated
	public double getRefineStandardDeviation()
	{
		return StatisticUtils.standardDeviation(getRefineScoresDouble());
	}
	
	public double getArithmeticAverage()
	{
		return StatisticUtils.arithmeticMean(getScoresDouble());
	}
	
	public double getStandardDeviation()
	{
		return StatisticUtils.standardDeviation(getScoresDouble());
	}
	
	public double getWeightAverage()
	{
		double weightAverage = 0d;
		
		for(ScoreCounter s : hitCounterMap.values())
		{
			double weight =(double)((double)s.getCounter() / (double)this.getScores().size());
			weightAverage += (double) ((double)s.getScore() * weight);
		}
		
		return weightAverage;
	}
	
	public Map<Integer, ScoreCounter> getScoreCounterMap() {
		
		return hitCounterMap;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getScores() == null) ? 0 : getScores().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScoreStuff other = (ScoreStuff) obj;
		if (getScores() == null) {
			if (other.getScores() != null)
				return false;
		} else if (!getScores().equals(other.getScores()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ScoreStuff ["
				+ "scores=" + getScores()
				+ ", size="	+ getScores().size()
//				+ ", refineRate=" + refineRate
//				+ ", getScoresDouble()=" + Arrays.toString(getScoresDouble())
				+ ", getArithmeticAverage()=" + getArithmeticAverage()
				+ ", getWeightAverage()=" + getWeightAverage()
				+ ", getStandardDeviation()=" + getStandardDeviation()
//				+ ", getRefineScoresDouble()=" + Arrays.toString(getRefineScoresDouble())
//				+ ", getRefineArithmeticAverage()=" + getRefineArithmeticAverage()
//				+ ", getRefineStandardDeviation()=" + getRefineStandardDeviation() 
				+ "]";
	}
	
}
