package shil.lottery.sport.entity;

/**
 * 概率 和 它所代表的胜负平 结果、
 * 支持排序,排序后,概率大的放在前面.
 * @author LiangJingJing
 */
public class StatusCounter implements Comparable<StatusCounter> {

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
		if (Double.doubleToLongBits(prob) != Double
				.doubleToLongBits(other.prob))
			return false;
		if (result != other.result)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StatusCounter [result=" + result + ", prob=" + prob + "]";
	}

}
