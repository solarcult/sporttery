package shil.lottery.sport.chain;

/**
 * 
 * @author LiangJingJing
 * @since 20140909-09:42
 */
public class OneTeamMatch {

	public static int Result_win = 8;
	public static int Result_draw = 5;
	public static int Result_lose = 2;
	
//	public static int Result_win = 3;
//	public static int Result_draw = 1;
//	public static int Result_lose = 0;
	
	private int year;
	private int month;
	private int day;
	private String league;
	private String name;
	private String rival;
	private int win_goals;
	private int lose_goals;
	//win:8draw:5lose:2
	private int result;

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public String getLeague() {
		return league;
	}

	public void setLeague(String league) {
		this.league = league;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWin_goals() {
		return win_goals;
	}

	public void setWin_goals(int win_goals) {
		this.win_goals = win_goals;
	}

	public int getLose_goals() {
		return lose_goals;
	}

	public void setLose_goals(int lose_goals) {
		this.lose_goals = lose_goals;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getRival() {
		return rival;
	}

	public void setRival(String rival) {
		this.rival = rival;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + day;
		result = prime * result + ((league == null) ? 0 : league.hashCode());
		result = prime * result + lose_goals;
		result = prime * result + month;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + this.result;
		result = prime * result + ((rival == null) ? 0 : rival.hashCode());
		result = prime * result + win_goals;
		result = prime * result + year;
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
		OneTeamMatch other = (OneTeamMatch) obj;
		if (day != other.day)
			return false;
		if (league == null) {
			if (other.league != null)
				return false;
		} else if (!league.equals(other.league))
			return false;
		if (lose_goals != other.lose_goals)
			return false;
		if (month != other.month)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (result != other.result)
			return false;
		if (rival == null) {
			if (other.rival != null)
				return false;
		} else if (!rival.equals(other.rival))
			return false;
		if (win_goals != other.win_goals)
			return false;
		if (year != other.year)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OneTeamMatch [year=" + year + ", month=" + month + ", day="
				+ day + ", league=" + league + ", name=" + name + ", rival="
				+ rival + ", win_goals=" + win_goals + ", lose_goals="
				+ lose_goals + ", result=" + result + "]";
	}

}
