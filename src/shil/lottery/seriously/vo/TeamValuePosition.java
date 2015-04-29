package shil.lottery.seriously.vo;

/**
 * 记录联赛中队伍的总分值value和所占的位置cool,位置分4等,优良中差,记得,可以相减.4321.
 * @author LiangJingJing
 * @date Apr 25, 2015 1:04:26 PM
 */
public class TeamValuePosition implements Comparable<TeamValuePosition>{
	
	private String league;
	private String teamname;
	private int value;
	private int level;
	
	public TeamValuePosition(String league, String teamname){
		this.league = league;
		this.teamname = teamname;
	}
	
	public void addValue(int value){
		this.value += value;
	}

	public String getTeamname() {
		return teamname;
	}

	public int getValue() {
		return value;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public String getLeague() {
		return league;
	}

	@Override
	public int compareTo(TeamValuePosition o) {
		//-1代表数值大,应该排在前面
		if(this.value > o.value) return -1;
		else if(this.value < o.value) return 1;
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + level;
		result = prime * result + ((league == null) ? 0 : league.hashCode());
		result = prime * result
				+ ((teamname == null) ? 0 : teamname.hashCode());
		result = prime * result + value;
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
		TeamValuePosition other = (TeamValuePosition) obj;
		if (level != other.level)
			return false;
		if (league == null) {
			if (other.league != null)
				return false;
		} else if (!league.equals(other.league))
			return false;
		if (teamname == null) {
			if (other.teamname != null)
				return false;
		} else if (!teamname.equals(other.teamname))
			return false;
		if (value != other.value)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TeamValuePosition [league=" + league + ", teamname=" + teamname
				+ ", value=" + value + ", cool=" + level + "]";
	}
	
}
