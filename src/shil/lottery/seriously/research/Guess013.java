package shil.lottery.seriously.research;

import java.util.List;

import shil.lottery.sport.entity.VSTeam;

public interface Guess013 {
	public static int win = 3;
	public static int draw = 1;
	public static int lose = 0;
	public static int NotAvaliable = -1;
	public static int ErrorWarning = -2;
	public static String winS = "赢";
	public static String drawS = "平";
	public static String loseS = "负";
	public static String ErrorWarningS = "Call 911";
	public int guess013(List<VSTeam> vsTeams,VSTeam vsTeam);
}
