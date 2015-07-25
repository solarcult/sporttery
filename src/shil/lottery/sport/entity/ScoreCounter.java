package shil.lottery.sport.entity;

public class ScoreCounter{

	private int score;
	private double counter;
	private double weight;
	
	public ScoreCounter(int score) {
		this.score = score;
		this.counter = 0;
		this.weight = 0;
	}
	
	public ScoreCounter(int score, double counter, double weight){
		this.score = score;
		this.counter = counter;
		this.weight = weight;
	}

	public void increaseBingo() {
		this.counter++;
	}
	
	public void increaseCounter(double counter)
	{
		this.counter += counter;
	}
	
	public void increaseWeight(double weight)
	{
		this.weight += weight;
	}

	public int getScore() {
		return this.score;
	}

	public double getCounter() {
		return this.counter;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public double getValue()
	{
		if(weight==0) return counter;
		else return weight;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(counter);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + score;
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
		if (Double.doubleToLongBits(counter) != Double
				.doubleToLongBits(other.counter))
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