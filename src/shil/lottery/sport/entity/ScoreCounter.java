package shil.lottery.sport.entity;

public class ScoreCounter implements Comparable<ScoreCounter> {

	private int score;
	private int counter;
	private double weight;
	
	public ScoreCounter(int score) {
		this.score = score;
		this.counter = 0;
		this.weight = 1;
	}

	public ScoreCounter(int score,double weight) {
		this.score = score;
		this.counter = 0;
		this.weight = weight;
	}

	public void increaseBingo() {
		this.counter++;
	}

	public int getScore() {
		return this.score;
	}

	public int getCounter() {
		return this.counter;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public double getValue()
	{
		return this.counter * this.weight;
	}

	@Override
	public int compareTo(ScoreCounter o) {
		if (this.getValue() > o.getValue()) {
			return -1;
		} else if (this.getValue() < o.getValue()) {
			return 1;
		}
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + counter;
		result = prime * result + score;
		long temp;
		temp = Double.doubleToLongBits(weight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		ScoreCounter other = (ScoreCounter) obj;
		if (counter != other.counter)
			return false;
		if (score != other.score)
			return false;
		if (Double.doubleToLongBits(weight) != Double
				.doubleToLongBits(other.weight))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ScoreCounter [score=" + score + ", counter=" + counter
				+ ", weight=" + weight + ", getValue()=" + getValue() + "]";
	}

}
