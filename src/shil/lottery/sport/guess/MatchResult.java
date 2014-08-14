package shil.lottery.sport.guess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 比赛结果对象
 * @author LiangJingJing
 * @since 2014-07-28 0:03
 */
public class MatchResult {
	
	public MatchResult(double door)
	{
		this.door = door;
	}
	
	private StatusCounter winStatusCounter;
	private StatusCounter drawStatusCounter;
	private StatusCounter loseStatusCounter;
	private double door;
	
	public StatusCounter getWinStatusCounter() {
		return winStatusCounter;
	}

	public void setWinStatusCounter(StatusCounter winStatusCounter) {
		this.winStatusCounter = winStatusCounter;
	}

	public StatusCounter getDrawStatusCounter() {
		return drawStatusCounter;
	}

	public void setDrawStatusCounter(StatusCounter drawStatusCounter) {
		this.drawStatusCounter = drawStatusCounter;
	}

	public StatusCounter getLoseStatusCounter() {
		return loseStatusCounter;
	}

	public void setLoseStatusCounter(StatusCounter loseStatusCounter) {
		this.loseStatusCounter = loseStatusCounter;
	}
	
	public int getMatch_Result(){
		
		List<StatusCounter> statusCounters  = new ArrayList<StatusCounter>();
		
		statusCounters.add(winStatusCounter);
		statusCounters.add(drawStatusCounter);
		statusCounters.add(loseStatusCounter);
		
		Collections.sort(statusCounters);
		
		if(statusCounters.get(0).getProb() - statusCounters.get(1).getProb() > door)
		{
			return statusCounters.get(0).getResult(); 
		}
		
		return -2;
	}

	@Override
	public String toString() {
		return "MatchResult [winStatusCounter=" + winStatusCounter
				+ ", drawStatusCounter=" + drawStatusCounter
				+ ", loseStatusCounter=" + loseStatusCounter
				+ ", getMatch_Result()=" + getMatch_Result() + "]";
	}



	class StatusCounter implements Comparable<StatusCounter>
	{
		private int result;
		private double prob;
		public StatusCounter(int result,double prob)
		{
			this.result = result;
			this.prob = prob;
		}
		
		@Override
		public int compareTo(StatusCounter o) {
			if(this.prob > o.prob) return -1;
			else if(this.prob < o.prob) return 1;
			else return 0;
		}

		public int getResult() {
			return result;
		}

		public double getProb() {
			return prob;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			long temp;
			temp = Double.doubleToLongBits(prob);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			result = prime * result + this.result;
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
			StatusCounter other = (StatusCounter) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (Double.doubleToLongBits(prob) != Double
					.doubleToLongBits(other.prob))
				return false;
			if (result != other.result)
				return false;
			return true;
		}

		private MatchResult getOuterType() {
			return MatchResult.this;
		}

		@Override
		public String toString() {
			return "StatusCounter [result=" + result + ", prob=" + prob + "]";
		}
	}

}
