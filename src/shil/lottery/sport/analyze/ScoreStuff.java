package shil.lottery.sport.analyze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shil.lottery.sport.guess.ScoreCounter;
import shil.lottery.sport.statistics.StatisticUtils;
import shil.lottery.sport.strategy.StrategyUtils;

/**
 * 记录进球数类
 * @author LiangJingJing
 * @since 2014-07-26 19:51
 */
public class ScoreStuff {
	
	private List<Integer> scores;
	
	private double refineRate = 0.05;
	
	private static int lastNmatch = 100;
	
	public ScoreStuff() {
		this.scores = new ArrayList<Integer>();
	}
	
	public List<Integer> getScores() {
		//TODO 此处把数据缩减到最近10个 20140807
		if(this.scores.size() > lastNmatch)
		{
			this.scores = StrategyUtils.subList(this.scores, this.scores.size()-lastNmatch , this.scores.size());
		}
		return scores;
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
		if(this.getScores().size() < 5) return -5;
		
		double weightAverage = 0d;
		
		Map<Integer,ScoreCounter> hitCounterMap = getScoreCounterMap();
		for(ScoreCounter s : hitCounterMap.values())
		{
			double weight =(double)((double)s.getCounter() / (double)this.getScores().size());
			weightAverage += (double) ((double)s.getScore() * weight);
		}
		
		return weightAverage;
	}
	
	public Map<Integer, ScoreCounter> getScoreCounterMap() {
		
		Map<Integer,ScoreCounter> hitCounterMap = new HashMap<Integer, ScoreCounter>();
		
		for(int i : this.getScores())
		{
			ScoreCounter scoreCounter = hitCounterMap.get(i);
			if(scoreCounter==null)
			{
				scoreCounter = new ScoreCounter(i);
				hitCounterMap.put(i, scoreCounter);
			}
			scoreCounter.increaseBingo();
		}
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
//				+ ", refineRate=" + refineRate
//				+ ", getScoresDouble()=" + Arrays.toString(getScoresDouble())
				+ ", getArithmeticAverage()=" + getArithmeticAverage()
				+ ", getStandardDeviation()=" + getStandardDeviation()
//				+ ", getRefineScoresDouble()=" + Arrays.toString(getRefineScoresDouble())
//				+ ", getRefineArithmeticAverage()=" + getRefineArithmeticAverage()
//				+ ", getRefineStandardDeviation()=" + getRefineStandardDeviation() 
				+ "]";
	}
	
}
