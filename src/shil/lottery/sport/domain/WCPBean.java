package shil.lottery.sport.domain;

/**
 * 根据利润排序的WCP计算过数据
 * @author LiangJingJing
 * @since before 2014-07-20
 */
public class WCPBean implements Comparable<WCPBean> {

	private String name;
	private double lirun;
	private String others;
	
	public WCPBean(String name,double lirun,String others)
	{
		this.name = name;
		this.lirun = lirun;
		this.others = others;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLirun() {
		return lirun;
	}

	public void setLirun(double lirun) {
		this.lirun = lirun;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(lirun);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((others == null) ? 0 : others.hashCode());
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
		WCPBean other = (WCPBean) obj;
		if (Double.doubleToLongBits(lirun) != Double
				.doubleToLongBits(other.lirun))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (others == null) {
			if (other.others != null)
				return false;
		} else if (!others.equals(other.others))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "WCPBean [name=" + name + ", lirun=" + lirun + ", others="
				+ others + "]";
	}

	@Override
	public int compareTo(WCPBean o) {
		if(this.lirun > o.lirun) return -1;
		else if(this.lirun < o.lirun) return 1;
		else return 0;
	}

}
