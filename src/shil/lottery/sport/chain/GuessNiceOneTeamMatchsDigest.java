package shil.lottery.sport.chain;

import shil.lottery.sport.entity.ScoreStuff;

public class GuessNiceOneTeamMatchsDigest {
	
	private String name;
	private String rival;
	private int c_win;
	private int c_draw;
	private int c_lose;
	private int t_m_nums;
	private ScoreStuff scoreStuff;
	
	public GuessNiceOneTeamMatchsDigest()
	{
		this.scoreStuff = new ScoreStuff();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRival() {
		return rival;
	}

	public void setRival(String rival) {
		this.rival = rival;
	}

	public int getC_win() {
		return c_win;
	}

	public void increaseC_win() {
		this.c_win++;
		this.t_m_nums++;
	}

	public int getC_draw() {
		return c_draw;
	}

	public void increaseC_draw() {
		this.c_draw++;
		this.t_m_nums++;
	}

	public int getC_lose() {
		return c_lose;
	}

	public void increaseC_lose() {
		this.c_lose++;
		this.t_m_nums++;
	}

	public ScoreStuff getScoreStuff() {
		return scoreStuff;
	}
	
	public int getT_m_nums() {
		
		if(t_m_nums!=this.scoreStuff.getScores().size())
			throw new RuntimeException("size doesn not match, why?");
		
		return t_m_nums;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + c_draw;
		result = prime * result + c_lose;
		result = prime * result + c_win;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((rival == null) ? 0 : rival.hashCode());
		result = prime * result
				+ ((scoreStuff == null) ? 0 : scoreStuff.hashCode());
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
		GuessNiceOneTeamMatchsDigest other = (GuessNiceOneTeamMatchsDigest) obj;
		if (c_draw != other.c_draw)
			return false;
		if (c_lose != other.c_lose)
			return false;
		if (c_win != other.c_win)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (rival == null) {
			if (other.rival != null)
				return false;
		} else if (!rival.equals(other.rival))
			return false;
		if (scoreStuff == null) {
			if (other.scoreStuff != null)
				return false;
		} else if (!scoreStuff.equals(other.scoreStuff))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GuessNiceOneTeamMatchsDigest [name=" + name + ", rival="
				+ rival + ", c_win=" + c_win + ", c_draw=" + c_draw
				+ ", c_lose=" + c_lose + ", scoreStuff=" + scoreStuff + "]";
	}

}
