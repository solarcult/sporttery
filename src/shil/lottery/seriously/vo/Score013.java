package shil.lottery.seriously.vo;

public class Score013 {

	private static long Multi = 100000l;
	
	private long id;
	private String name;
	private double win_odds_percent;
	private double draw_odds_percent;
	private double lose_odds_percent;
	private double people_bet_win_precent;
	private double people_bet_draw_precent;
	private double people_bet_lose_precent;
	private int match_result;
	private double gsd;
	private double lsd;
	private double agbl;
	private double albg;
	private double gsd_lsd_current_quadrant_win_precent;
	private double gsd_lsd_current_quadrant_draw_precent;
	private double gsd_lsd_current_quadrant_lose_precent;
	private double gsd_agbl_current_quadrant_win_precent;
	private double gsd_agbl_current_quadrant_draw_precent;
	private double gsd_agbl_current_quadrant_lose_precent;
	private double gsd_albg_current_quadrant_win_precent;
	private double gsd_albg_current_quadrant_draw_precent;
	private double gsd_albg_current_quadrant_lose_precent;
	private double lsd_agbl_current_quadrant_win_precent;
	private double lsd_agbl_current_quadrant_draw_precent;
	private double lsd_agbl_current_quadrant_lose_precent;
	private double lsd_albg_current_quadrant_win_precent;
	private double lsd_albg_current_quadrant_draw_precent;
	private double lsd_albg_current_quadrant_lose_precent;
	private double agbl_albg_current_quadrant_win_precent;;
	private double agbl_albg_current_quadrant_draw_precent;
	private double agbl_albg_current_quadrant_lose_precent;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getWin_odds_percent() {
		return win_odds_percent;
	}
	public void setWin_odds_percent(double win_odds_percent) {
		this.win_odds_percent = win_odds_percent;
	}
	public double getDraw_odds_percent() {
		return draw_odds_percent;
	}
	public void setDraw_odds_percent(double draw_odds_percent) {
		this.draw_odds_percent = draw_odds_percent;
	}
	public double getLose_odds_percent() {
		return lose_odds_percent;
	}
	public void setLose_odds_percent(double lose_odds_percent) {
		this.lose_odds_percent = lose_odds_percent;
	}
	public double getPeople_bet_win_precent() {
		return people_bet_win_precent;
	}
	public void setPeople_bet_win_precent(double people_bet_win_precent) {
		this.people_bet_win_precent = people_bet_win_precent;
	}
	public double getPeople_bet_draw_precent() {
		return people_bet_draw_precent;
	}
	public void setPeople_bet_draw_precent(double people_bet_draw_precent) {
		this.people_bet_draw_precent = people_bet_draw_precent;
	}
	public double getPeople_bet_lose_precent() {
		return people_bet_lose_precent;
	}
	public void setPeople_bet_lose_precent(double people_bet_lose_precent) {
		this.people_bet_lose_precent = people_bet_lose_precent;
	}
	public int getMatch_result() {
		return match_result;
	}
	public void setMatch_result(int match_result) {
		this.match_result = match_result;
	}
	public double getGsd() {
		return gsd;
	}
	public void setGsd(double gsd) {
		this.gsd = gsd;
	}
	public double getLsd() {
		return lsd;
	}
	public void setLsd(double lsd) {
		this.lsd = lsd;
	}
	public double getAgbl() {
		return agbl;
	}
	public void setAgbl(double agbl) {
		this.agbl = agbl;
	}
	public double getAlbg() {
		return albg;
	}
	public void setAlbg(double albg) {
		this.albg = albg;
	}
	public long getGsd_lsd_current_quadrant_win_precentL() {
		return (long) (gsd_lsd_current_quadrant_win_precent * Multi);
	}
	public void setGsd_lsd_current_quadrant_win_precent(
			double gsd_lsd_current_quadrant_win_precent) {
		this.gsd_lsd_current_quadrant_win_precent = gsd_lsd_current_quadrant_win_precent;
	}
	public long getGsd_lsd_current_quadrant_draw_precentL() {
		return (long) (gsd_lsd_current_quadrant_draw_precent * Multi);
	}
	public void setGsd_lsd_current_quadrant_draw_precent(
			double gsd_lsd_current_quadrant_draw_precent) {
		this.gsd_lsd_current_quadrant_draw_precent = gsd_lsd_current_quadrant_draw_precent;
	}
	public long getGsd_lsd_current_quadrant_lose_precentL() {
		return (long) (gsd_lsd_current_quadrant_lose_precent * Multi);
	}
	public void setGsd_lsd_current_quadrant_lose_precent(
			double gsd_lsd_current_quadrant_lose_precent) {
		this.gsd_lsd_current_quadrant_lose_precent = gsd_lsd_current_quadrant_lose_precent;
	}
	public long getGsd_agbl_current_quadrant_win_precentL() {
		return (long) (gsd_agbl_current_quadrant_win_precent * Multi);
	}
	public void setGsd_agbl_current_quadrant_win_precent(
			double gsd_agbl_current_quadrant_win_precent) {
		this.gsd_agbl_current_quadrant_win_precent = gsd_agbl_current_quadrant_win_precent;
	}
	public long getGsd_agbl_current_quadrant_draw_precentL() {
		return (long) (gsd_agbl_current_quadrant_draw_precent * Multi);
	}
	public void setGsd_agbl_current_quadrant_draw_precent(
			double gsd_agbl_current_quadrant_draw_precent) {
		this.gsd_agbl_current_quadrant_draw_precent = gsd_agbl_current_quadrant_draw_precent;
	}
	public long getGsd_agbl_current_quadrant_lose_precentL() {
		return (long) (gsd_agbl_current_quadrant_lose_precent * Multi);
	}
	public void setGsd_agbl_current_quadrant_lose_precent(
			double gsd_agbl_current_quadrant_lose_precent) {
		this.gsd_agbl_current_quadrant_lose_precent = gsd_agbl_current_quadrant_lose_precent;
	}
	public long getGsd_albg_current_quadrant_win_precentL() {
		return (long) (gsd_albg_current_quadrant_win_precent * Multi);
	}
	public void setGsd_albg_current_quadrant_win_precent(
			double gsd_albg_current_quadrant_win_precent) {
		this.gsd_albg_current_quadrant_win_precent = gsd_albg_current_quadrant_win_precent;
	}
	public long getGsd_albg_current_quadrant_draw_precentL() {
		return (long) (gsd_albg_current_quadrant_draw_precent * Multi);
	}
	public void setGsd_albg_current_quadrant_draw_precent(
			double gsd_albg_current_quadrant_draw_precent) {
		this.gsd_albg_current_quadrant_draw_precent = gsd_albg_current_quadrant_draw_precent;
	}
	public long getGsd_albg_current_quadrant_lose_precentL() {
		return (long) (gsd_albg_current_quadrant_lose_precent * Multi);
	}
	public void setGsd_albg_current_quadrant_lose_precent(
			double gsd_albg_current_quadrant_lose_precent) {
		this.gsd_albg_current_quadrant_lose_precent = gsd_albg_current_quadrant_lose_precent;
	}
	public long getLsd_agbl_current_quadrant_win_precentL() {
		return (long) (lsd_agbl_current_quadrant_win_precent * Multi);
	}
	public void setLsd_agbl_current_quadrant_win_precent(
			double lsd_agbl_current_quadrant_win_precent) {
		this.lsd_agbl_current_quadrant_win_precent = lsd_agbl_current_quadrant_win_precent;
	}
	public long getLsd_agbl_current_quadrant_draw_precentL() {
		return (long) (lsd_agbl_current_quadrant_draw_precent * Multi);
	}
	public void setLsd_agbl_current_quadrant_draw_precent(
			double lsd_agbl_current_quadrant_draw_precent) {
		this.lsd_agbl_current_quadrant_draw_precent = lsd_agbl_current_quadrant_draw_precent;
	}
	public long getLsd_agbl_current_quadrant_lose_precentL() {
		return (long) (lsd_agbl_current_quadrant_lose_precent * Multi);
	}
	public void setLsd_agbl_current_quadrant_lose_precent(
			double lsd_agbl_current_quadrant_lose_precent) {
		this.lsd_agbl_current_quadrant_lose_precent = lsd_agbl_current_quadrant_lose_precent;
	}
	public long getLsd_albg_current_quadrant_win_precentL() {
		return (long) (lsd_albg_current_quadrant_win_precent * Multi);
	}
	public void setLsd_albg_current_quadrant_win_precent(
			double lsd_albg_current_quadrant_win_precent) {
		this.lsd_albg_current_quadrant_win_precent = lsd_albg_current_quadrant_win_precent;
	}
	public long getLsd_albg_current_quadrant_draw_precentL() {
		return (long) (lsd_albg_current_quadrant_draw_precent * Multi);
	}
	public void setLsd_albg_current_quadrant_draw_precent(
			double lsd_albg_current_quadrant_draw_precent) {
		this.lsd_albg_current_quadrant_draw_precent = lsd_albg_current_quadrant_draw_precent;
	}
	public long getLsd_albg_current_quadrant_lose_precentL() {
		return (long) (lsd_albg_current_quadrant_lose_precent * Multi);
	}
	public void setLsd_albg_current_quadrant_lose_precent(
			double lsd_albg_current_quadrant_lose_precent) {
		this.lsd_albg_current_quadrant_lose_precent = lsd_albg_current_quadrant_lose_precent;
	}
	public long getAgbl_albg_current_quadrant_win_precentL() {
		return (long) (agbl_albg_current_quadrant_win_precent * Multi);
	}
	public void setAgbl_albg_current_quadrant_win_precent(
			double agbl_albg_current_quadrant_win_precent) {
		this.agbl_albg_current_quadrant_win_precent = agbl_albg_current_quadrant_win_precent;
	}
	public long getAgbl_albg_current_quadrant_draw_precentL() {
		return (long) (agbl_albg_current_quadrant_draw_precent * Multi);
	}
	public void setAgbl_albg_current_quadrant_draw_precent(
			double agbl_albg_current_quadrant_draw_precent) {
		this.agbl_albg_current_quadrant_draw_precent = agbl_albg_current_quadrant_draw_precent;
	}
	public long getAgbl_albg_current_quadrant_lose_precentL() {
		return (long) (agbl_albg_current_quadrant_lose_precent * Multi);
	}
	public void setAgbl_albg_current_quadrant_lose_precent(
			double agbl_albg_current_quadrant_lose_precent) {
		this.agbl_albg_current_quadrant_lose_precent = agbl_albg_current_quadrant_lose_precent;
	}
	public double getGsd_lsd_current_quadrant_win_precent() {
		return gsd_lsd_current_quadrant_win_precent;
	}
	public double getGsd_lsd_current_quadrant_draw_precent() {
		return gsd_lsd_current_quadrant_draw_precent;
	}
	public double getGsd_lsd_current_quadrant_lose_precent() {
		return gsd_lsd_current_quadrant_lose_precent;
	}
	public double getGsd_agbl_current_quadrant_win_precent() {
		return gsd_agbl_current_quadrant_win_precent;
	}
	public double getGsd_agbl_current_quadrant_draw_precent() {
		return gsd_agbl_current_quadrant_draw_precent;
	}
	public double getGsd_agbl_current_quadrant_lose_precent() {
		return gsd_agbl_current_quadrant_lose_precent;
	}
	public double getGsd_albg_current_quadrant_win_precent() {
		return gsd_albg_current_quadrant_win_precent;
	}
	public double getGsd_albg_current_quadrant_draw_precent() {
		return gsd_albg_current_quadrant_draw_precent;
	}
	public double getGsd_albg_current_quadrant_lose_precent() {
		return gsd_albg_current_quadrant_lose_precent;
	}
	public double getLsd_agbl_current_quadrant_win_precent() {
		return lsd_agbl_current_quadrant_win_precent;
	}
	public double getLsd_agbl_current_quadrant_draw_precent() {
		return lsd_agbl_current_quadrant_draw_precent;
	}
	public double getLsd_agbl_current_quadrant_lose_precent() {
		return lsd_agbl_current_quadrant_lose_precent;
	}
	public double getLsd_albg_current_quadrant_win_precent() {
		return lsd_albg_current_quadrant_win_precent;
	}
	public double getLsd_albg_current_quadrant_draw_precent() {
		return lsd_albg_current_quadrant_draw_precent;
	}
	public double getLsd_albg_current_quadrant_lose_precent() {
		return lsd_albg_current_quadrant_lose_precent;
	}
	public double getAgbl_albg_current_quadrant_win_precent() {
		return agbl_albg_current_quadrant_win_precent;
	}
	public double getAgbl_albg_current_quadrant_draw_precent() {
		return agbl_albg_current_quadrant_draw_precent;
	}
	public double getAgbl_albg_current_quadrant_lose_precent() {
		return agbl_albg_current_quadrant_lose_precent;
	}
}
