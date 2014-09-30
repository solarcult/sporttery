package shil.lottery.sport.score.diff;

public class VSMatchResult 
{
	private String match_date;
	private String team_name;
	private String league;
	private int win_goals;
	private int lose_goals;
	private int match_result;
	
	public VSMatchResult(String match_date,String team_name,String league,int win_goals,int lose_goals)
	{
		this.match_date = match_date;
		this.team_name = team_name;
		this.league = league;
		this.win_goals = win_goals;
		this.lose_goals = lose_goals;
		if(win_goals > lose_goals) this.match_result = 3; 
		else if(win_goals == lose_goals) this.match_result = 1;
		else this.match_result = 0;
	}
	
	public String getMatch_date() {
		return match_date;
	}
	public String getTeam_name() {
		return team_name;
	}
	public int getWin_goals() {
		return win_goals;
	}
	public int getLose_goals() {
		return lose_goals;
	}
	public int getMatch_result() {
		return match_result;
	}
	public String getLeague() {
		return league;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((league == null) ? 0 : league.hashCode());
		result = prime * result + lose_goals;
		result = prime * result
				+ ((match_date == null) ? 0 : match_date.hashCode());
		result = prime * result + match_result;
		result = prime * result
				+ ((team_name == null) ? 0 : team_name.hashCode());
		result = prime * result + win_goals;
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
		VSMatchResult other = (VSMatchResult) obj;
		if (league == null) {
			if (other.league != null)
				return false;
		} else if (!league.equals(other.league))
			return false;
		if (lose_goals != other.lose_goals)
			return false;
		if (match_date == null) {
			if (other.match_date != null)
				return false;
		} else if (!match_date.equals(other.match_date))
			return false;
		if (match_result != other.match_result)
			return false;
		if (team_name == null) {
			if (other.team_name != null)
				return false;
		} else if (!team_name.equals(other.team_name))
			return false;
		if (win_goals != other.win_goals)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VSMatchResult [match_date=" + match_date + ", team_name="
				+ team_name + ", league=" + league + ", win_goals=" + win_goals
				+ ", lose_goals=" + lose_goals + ", match_result="
				+ match_result + "]";
	}
	
}
