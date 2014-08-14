package shil.lottery.sport.cards;

public class CardPoint {

	
	public double x;
	public double y;
	public AnD and;
	public MatchResultEnum mr;
	
	public CardPoint(double x,double y, AnD and,MatchResultEnum mr)
	{
		this.x = x;
		this.y = y;
		this.and = and;
		this.mr = mr;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public AnD getAnd() {
		return and;
	}

	public MatchResultEnum getMr() {
		return mr;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((and == null) ? 0 : and.hashCode());
		result = prime * result + ((mr == null) ? 0 : mr.hashCode());
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
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
		CardPoint other = (CardPoint) obj;
		if (and != other.and)
			return false;
		if (mr != other.mr)
			return false;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CardsPoint [x=" + x + ", y=" + y + ", and=" + and + ", mr="
				+ mr + "]";
	}
}
