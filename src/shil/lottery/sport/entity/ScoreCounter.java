package shil.lottery.sport.entity;

public class ScoreCounter implements Comparable<ScoreCounter> {

	private int score;
	private int counter;

	public ScoreCounter(int score) {
		this.score = score;
		this.counter = 0;
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

	@Override
	public int compareTo(ScoreCounter o) {
		if (this.counter > o.counter) {
			return -1;
		} else if (this.counter < o.counter) {
			return 1;
		}
		return 0;
	}

	@Override
	public String toString() {
		return "ScoreCounter [score=" + score + ", counter=" + counter + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + counter;
		result = prime * result + score;
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
		return true;
	}
}
