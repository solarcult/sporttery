package shil.lottery.sport.cards;

/**
 * 团队卡片,攻击力 ,防御力
 * @author ljj
 * @since 2014-08-02 23:25
 */
public class VSTeamCard {
	
	private String league;
	private String name;
	private double attack;
	private double defense;
	
	public VSTeamCard(String league,String name,double attack,double defense)
	{
		this.league = league;
		this.name = name;
		this.attack = attack;
		this.defense = defense;
	}
	
	public String getLeague() {
		return league;
	}

	public String getName() {
		return name;
	}
	
	public double getAttack() {
		return attack;
	}
	
	public double getDefense() {
		return defense;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(attack);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(defense);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((league == null) ? 0 : league.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		VSTeamCard other = (VSTeamCard) obj;
		if (Double.doubleToLongBits(attack) != Double
				.doubleToLongBits(other.attack))
			return false;
		if (Double.doubleToLongBits(defense) != Double
				.doubleToLongBits(other.defense))
			return false;
		if (league == null) {
			if (other.league != null)
				return false;
		} else if (!league.equals(other.league))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VSTeamCard [league=" + league + ", name=" + name + ", attack="
				+ attack + ", defense=" + defense + "]";
	}
}
