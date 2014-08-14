package shil.lottery.sport.analyze;

public class TeamMatchResult {
	
	private String team_name;

	private double win_3;
	private double draw_1;
	private double lose_0;
	
	public TeamMatchResult(String teamname)
	{
		this.team_name = teamname;
	}
	
	public double getWin_3() {
		return win_3;
	}
	public double getDraw_1() {
		return draw_1;
	}
	public double getLose_0() {
		return lose_0;
	}
	
	public void increaseMatchResult(int r)
	{
		if(r==3) this.win_3++;
		else if(r==1) this.draw_1++;
		else if(r==0) this.lose_0++;
		else throw new RuntimeException("what can i do for u?");
		
	}
	
	public double getW3P()
	{
		double w = win_3/(win_3+draw_1+lose_0);
		if(w==0) w = 0.01;
		return w;
	}
	
	public double getD1P()
	{
		double d = draw_1/(win_3+draw_1+lose_0);
		if(d==0) d= 0.01;
		return d;
	}
	
	public double getL0P()
	{
		double l = lose_0/(win_3+draw_1+lose_0);
		if(l==0) l = 0.01;
		return l;
	}
	
	public String getTeam_Name()
	{
		return this.team_name;
	}
	
	public double getTotal()
	{
		return win_3+draw_1+lose_0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(draw_1);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(lose_0);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((team_name == null) ? 0 : team_name.hashCode());
		temp = Double.doubleToLongBits(win_3);
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
		TeamMatchResult other = (TeamMatchResult) obj;
		if (Double.doubleToLongBits(draw_1) != Double
				.doubleToLongBits(other.draw_1))
			return false;
		if (Double.doubleToLongBits(lose_0) != Double
				.doubleToLongBits(other.lose_0))
			return false;
		if (team_name == null) {
			if (other.team_name != null)
				return false;
		} else if (!team_name.equals(other.team_name))
			return false;
		if (Double.doubleToLongBits(win_3) != Double
				.doubleToLongBits(other.win_3))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TeamMatchResult [team_name=" + team_name + ", win_3=" + win_3
				+ ", draw_1=" + draw_1 + ", lose_0=" + lose_0 + ", getW3P()="
				+ getW3P() + ", getD1P()=" + getD1P() + ", getL0P()="
				+ getL0P() + ", getTotal()=" + getTotal() + "]";
	}

}
