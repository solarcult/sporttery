package shil.lottery.sport.cards;

public class Quadrants {
	
	public Quadrants(double q1,double q2,double q3,double q4,double wholeSample)
	{
		this.quad1 = q1;
		this.quad2 = q2;
		this.quad3 = q3;
		this.quad4 = q4;
		this.totalGuess = q1+q2+q3+q4;
		this.wholeSample = wholeSample;
	}
	
	public double quad1;
	public double quad2;
	public double quad3;
	public double quad4;
	public double totalGuess;
	public double wholeSample;
	
	public double getQuad1p()
	{
		return quad1/totalGuess;
	}
	
	public double getQuad2p()
	{
		return quad2/totalGuess;
	}
	
	public double getQuad3p()
	{
		return quad3/totalGuess;
	}
	
	public double getQuad4p()
	{
		return quad4/totalGuess;
	}
	
	public double getGuessp()
	{
		return totalGuess/wholeSample;
	}

	public double getQuad1() {
		return quad1;
	}

	public double getQuad2() {
		return quad2;
	}

	public double getQuad3() {
		return quad3;
	}

	public double getQuad4() {
		return quad4;
	}

	public double getTotalGuess() {
		return totalGuess;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(quad1);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(quad2);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(quad3);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(quad4);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(totalGuess);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(wholeSample);
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
		Quadrants other = (Quadrants) obj;
		if (Double.doubleToLongBits(quad1) != Double
				.doubleToLongBits(other.quad1))
			return false;
		if (Double.doubleToLongBits(quad2) != Double
				.doubleToLongBits(other.quad2))
			return false;
		if (Double.doubleToLongBits(quad3) != Double
				.doubleToLongBits(other.quad3))
			return false;
		if (Double.doubleToLongBits(quad4) != Double
				.doubleToLongBits(other.quad4))
			return false;
		if (Double.doubleToLongBits(totalGuess) != Double
				.doubleToLongBits(other.totalGuess))
			return false;
		if (Double.doubleToLongBits(wholeSample) != Double
				.doubleToLongBits(other.wholeSample))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Quadrants [quad1=" + quad1 + ", quad2=" + quad2 + ", quad3="
				+ quad3 + ", quad4=" + quad4 + ", totalGuess=" + totalGuess
				+ ", wholeSample=" + wholeSample + ", getQuad1p()="
				+ getQuad1p() + ", getQuad2p()=" + getQuad2p()
				+ ", getQuad3p()=" + getQuad3p() + ", getQuad4p()="
				+ getQuad4p() + ", getGuessp()=" + getGuessp() + "]";
	}
}
