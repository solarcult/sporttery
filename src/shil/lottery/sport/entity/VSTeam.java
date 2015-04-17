package shil.lottery.sport.entity;

import java.util.Arrays;

/**
 * 比赛元信息
 * @author LiangJingJing
 * @since before 2014-07-20
 */
public class VSTeam implements Comparable<VSTeam>{
	
	//对战双方
	private String[] vs;
	//官方给的赔率
	private double[] peilv;
	//用户购买胜负平的数量
	private double[] peopleVote_num;
	//根据赔率计算出来的概率,由程序推算出来
	private double[] betCalcRate_web;
	//根据用户投注计算出来的概率,由程序推算出来
	private double[] peopleVote_rate;
	//庄家返还比例,约小返还越多,对彩票购买者越好.
	private double host_rate_1125;
	
	private int year;
	private int month;
	private int day;
	private int changci;
	private String week;
	private String league;
	private int teama_goals;
	private int teamb_goals;
	
	private double[] goals;
	
	private VSTeam(){
		this.goals = new double[2];
	};
	
	public static VSTeam builderVSTeam(String[] vs,double[] peilv,double[] peopleVote_num)
	{
		VSTeam vsTeam = new VSTeam();
		vsTeam.setVs(vs);
		vsTeam.setPeilv(peilv);
		vsTeam.setPeopleVote_num(peopleVote_num);
		
		return vsTeam;
	}
	
	public static VSTeam builderVSTeam(String[] vs,double[] peilv,double[] peopleVote_num,
			int year,int month, int day,int changci,String week,String league,int teama_goals,int teamb_goals)
	{
		VSTeam vsTeam = new VSTeam();
		vsTeam.setVs(vs);
		vsTeam.setPeilv(peilv);
		vsTeam.setPeopleVote_num(peopleVote_num);
		vsTeam.setYear(year);
		vsTeam.setMonth(month);
		vsTeam.setDay(day);
		vsTeam.setChangci(changci);
		vsTeam.setWeek(week);
		vsTeam.setLeague(league);
		vsTeam.setTeama_goals(teama_goals);
		vsTeam.setTeamb_goals(teamb_goals);
		
		return vsTeam;
	}

	public String[] getVs() {
		return vs;
	}

	public double[] getPeilv() {
		return peilv;
	}

	public double[] getPeopleVote_num() {
		return peopleVote_num;
	}

	private void setVs(String[] vs) {
		this.vs = vs;
	}

	private void setPeilv(double[] peilv) {
		this.peilv = peilv;		
		this.betCalcRate_web = new double[peilv.length];
		
		//以下根据赔率计算胜负平所占的概率
		double total = 0;
		double[] convertRate = new double[peilv.length];
		for(int e=0;e<peilv.length;e++)
		{
			convertRate[e] = 1 / peilv[e];
			total += convertRate[e];
		}
		for(int e = 0; e<convertRate.length;e++)
		{
			betCalcRate_web[e] = convertRate[e] / total;
		}
		
		
		this.host_rate_1125 = total; 
	}

	private void setPeopleVote_num(double[] peopleVote_num) {
		this.peopleVote_num = peopleVote_num;
		this.peopleVote_rate = new double[peopleVote_num.length];
		
		//根据真实投注的数量计算胜负平所占的概率
		double total = 0;
		for(int i=0;i<this.peopleVote_num.length;i++)
		{
			total += this.peopleVote_num[i];
		}
		
		for(int j = 0;j<this.peopleVote_num.length;j++)
		{
			this.peopleVote_rate[j] = this.peopleVote_num[j]/total;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(peilv);
		result = prime * result + Arrays.hashCode(peopleVote_num);
		result = prime * result + Arrays.hashCode(vs);
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
		VSTeam other = (VSTeam) obj;
		if (!Arrays.equals(peilv, other.peilv))
			return false;
		if (!Arrays.equals(peopleVote_num, other.peopleVote_num))
			return false;
		if (!Arrays.equals(vs, other.vs))
			return false;
		return true;
	}

	public double[] getBetCalcRate_web() {
		return betCalcRate_web;
	}

	public double getHost_rate_1125() {
		return host_rate_1125;
	}
	
	public double[] getPeopleVote_rate() {
		return peopleVote_rate;
	}
	
	@Override
	public String toString() {
		return "VSTeam [vs=" + Arrays.toString(vs) + ", peilv="
				+ Arrays.toString(peilv) + ", peopleVote_num="
				+ Arrays.toString(peopleVote_num) + ", betCalcRate_web="
				+ Arrays.toString(betCalcRate_web) + ", peopleVote_rate="
				+ Arrays.toString(peopleVote_rate) + ", host_rate_1125="
				+ host_rate_1125 + ", year=" + year + ", month=" + month
				+ ", day=" + day + ", changci=" + changci + ", week=" + week
				+ ", league=" + league + ", teama_goals=" + teama_goals
				+ ", teamb_goals=" + teamb_goals + "]";
	}

	@Override
	public int compareTo(VSTeam o) {
		if(this.host_rate_1125 < o.getHost_rate_1125()) return -1;
		else if(this.host_rate_1125 > o.getHost_rate_1125()) return 1;
		else return 0;
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public int getChangci() {
		return changci;
	}

	public String getWeek() {
		return week;
	}

	public String getLeague() {
		return league;
	}

	public int getTeama_goals() {
		return teama_goals;
	}

	public int getTeamb_goals() {
		return teamb_goals;
	}
	
	public int getMatch_Result()
	{
		if(teama_goals > teamb_goals) return 3;	//win 
		else if(teama_goals == teamb_goals) return 1; //draw
		else return 0;	//lose
	}

	private void setYear(int year) {
		this.year = year;
	}

	private void setMonth(int month) {
		this.month = month;
	}

	private void setDay(int day) {
		this.day = day;
	}

	private void setChangci(int changci) {
		this.changci = changci;
	}

	private void setWeek(String week) {
		this.week = week;
	}

	private void setLeague(String league) {
		this.league = league;
	}

	private void setTeama_goals(int teama_goals) {
		this.teama_goals = teama_goals;
		this.goals[0] = teama_goals;
	}

	private void setTeamb_goals(int teamb_goals) {
		this.teamb_goals = teamb_goals;
		this.goals[1] = teama_goals;
	}
	
	public double[] getGoals() {
		return goals;
	}

	public static void main(String[] args)
	{
//		double[] peilv = {1.79,3.20,4.00}; //1.121159217877095
		double[] peilv = {2.20,3.05,2.95}; //1.1213973578519285
		
		//以下根据赔率计算胜负平所占的概率
		double total = 0;
		double[] convertRate = new double[peilv.length];
		for(int e=0;e<peilv.length;e++)
		{
			convertRate[e] = 1 / peilv[e];
			total += convertRate[e];
		}
		double[] betCalcRate_web = new double[3];
		for(int e = 0; e<convertRate.length;e++)
		{
			betCalcRate_web[e] = convertRate[e] / total;
			System.out.println(betCalcRate_web[e]);
		}
		
		System.out.println(total);
	}
}
