package shil.lottery.sport.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

import shil.lottery.sport.entity.VSTeam;
import shil.lottery.sport.strategy.StrategyUtils;

/**
 * 数据库存取类
 * @author LiangJingJing
 * @since before 2014-07-20
 */
public class SportMetaDaoImpl {
	
	//之前7个月的数据
	public static int minusMonth = -7;
	
	public static void insertVSTeams(List<VSTeam> vsTeams)
	{
		Connection connection = DataBaseManager.getConnection();
		PreparedStatement preStatement = null;
		try
		{
			preStatement = connection.prepareStatement(
					"insert into sport_meta_data("
					+ "year,month,day,changci,week,league,teama,teamb,"
					+ "win_odds,draw_odds,lose_odds,"
					+ "people_bet_win_count,people_bet_draw_count,people_bet_lose_count,"
					+ "win_odds_percent,draw_odds_percent,lose_odds_percent,"
					+ "people_bet_win_percent,people_bet_draw_percent,people_bet_lose_percent,"
					+ "win_rate_mismatch,draw_rate_mismatch,lose_rate_mismatch,"
					+ "teama_goals,teamb_goals,match_result,host_rate_1125,match_date)"
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
					);

		
		for(VSTeam vsTeam : vsTeams)
		{
			try
			{
				preStatement.setInt(1, vsTeam.getYear());
				preStatement.setInt(2, vsTeam.getMonth());
				preStatement.setInt(3, vsTeam.getDay());
				preStatement.setInt(4, vsTeam.getChangci());
				preStatement.setString(5, vsTeam.getWeek());
				preStatement.setString(6, vsTeam.getLeague());
				preStatement.setString(7, vsTeam.getVs()[0]);
				preStatement.setString(8, vsTeam.getVs()[1]);
				preStatement.setDouble(9, vsTeam.getPeilv()[0]);
				preStatement.setDouble(10, vsTeam.getPeilv()[1]);
				preStatement.setDouble(11, vsTeam.getPeilv()[2]);
				preStatement.setDouble(12, vsTeam.getPeopleVote_num()[0]);
				preStatement.setDouble(13, vsTeam.getPeopleVote_num()[1]);
				preStatement.setDouble(14, vsTeam.getPeopleVote_num()[2]);
				preStatement.setDouble(15, vsTeam.getBetCalcRate_web()[0]);
				preStatement.setDouble(16, vsTeam.getBetCalcRate_web()[1]);
				preStatement.setDouble(17, vsTeam.getBetCalcRate_web()[2]);
				preStatement.setDouble(18, vsTeam.getPeopleVote_rate()[0]);
				preStatement.setDouble(19, vsTeam.getPeopleVote_rate()[1]);
				preStatement.setDouble(20, vsTeam.getPeopleVote_rate()[2]);
				preStatement.setDouble(21, vsTeam.getBetCalcRate_web()[0] - vsTeam.getPeopleVote_rate()[0]);
				preStatement.setDouble(22, vsTeam.getBetCalcRate_web()[1] - vsTeam.getPeopleVote_rate()[1]);
				preStatement.setDouble(23, vsTeam.getBetCalcRate_web()[2] - vsTeam.getPeopleVote_rate()[2]);
				preStatement.setInt(24, vsTeam.getTeama_goals());
				preStatement.setInt(25, vsTeam.getTeamb_goals());
				preStatement.setInt(26, vsTeam.getMatch_Result());
				preStatement.setDouble(27, vsTeam.getHost_rate_1125());
				
				Calendar date = Calendar.getInstance();
				date.set(vsTeam.getYear(), vsTeam.getMonth() - 1, vsTeam.getDay());
				
				preStatement.setDate(28, new java.sql.Date(date.getTimeInMillis()));
				
				preStatement.executeUpdate();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}finally
		{
			try {
				if(preStatement!=null) preStatement.close();
				if(connection!=null) connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public static List<VSTeam> loadEveryVSTeamRecords()
	{
		return loadBeforeMonthVSTeamRecords(999);
	}
	
	public static List<VSTeam> loadBeforeMonthVSTeamRecords(int beforeMonth)
	{
		Calendar c =Calendar.getInstance();
		c.add(Calendar.MONTH, -beforeMonth);
		
		List<VSTeam> vsTeams = new ArrayList<VSTeam>();
		Connection connection = DataBaseManager.getConnection();
		PreparedStatement preStatement = null;
		try
		{
			preStatement = connection.prepareStatement(
					"select "
					+ "year,month,day,changci,week,league,teama,teamb,"
					+ "win_odds,draw_odds,lose_odds,"
					+ "people_bet_win_count,people_bet_draw_count,people_bet_lose_count,"
					+ "teama_goals,teamb_goals,id "
					+ "from sport_meta_data "
					+ "where match_date > ? order by match_date asc"
					);
			
			preStatement.setDate(1, new java.sql.Date(c.getTimeInMillis()));
			
			ResultSet resultSet = preStatement.executeQuery();
			while(resultSet.next())
			{
		        int year = resultSet.getInt(1);
		        int month = resultSet.getInt(2);
		        int day = resultSet.getInt(3);
		        int changci = resultSet.getInt(4);
		        String week = resultSet.getString(5);
		        String league = resultSet.getString(6);
		        
		        String[] vs = new String[2];
		        vs[0] = resultSet.getString(7);
		        vs[1] = resultSet.getString(8);
		        
		        double[] peilv = new double[3];
		        peilv[0] = resultSet.getDouble(9);
		        peilv[1] = resultSet.getDouble(10);
		        peilv[2] = resultSet.getDouble(11);
		        
		        double[] peopleVote_num = new double[3];
		        peopleVote_num[0] = resultSet.getDouble(12);
		        peopleVote_num[1] = resultSet.getDouble(13);
		        peopleVote_num[2] = resultSet.getDouble(14);
		        
		        int teama_goals = resultSet.getInt(15);
		        int teamb_goals = resultSet.getInt(16);
		        
		        int id = resultSet.getInt(17);
				
				vsTeams.add(VSTeam.builderVSTeam(vs, peilv, peopleVote_num, year, month, day, changci, week, league, teama_goals, teamb_goals, id));
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				if(preStatement!=null) preStatement.close();
				if(connection!=null) connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return vsTeams;
	}
	
	@Test
	public void testInsert()
	{
		List<VSTeam> vsTeams = new ArrayList<VSTeam>();
		
		vsTeams.add(VSTeam.builderVSTeam(
				new String[]{"北雪平","佐加顿斯"},
				new double[]{2.30d,3.15d,2.70d},
				new double[]{16058,19976,12606},
				2014,1,15,051,"周一","瑞典超级联赛",3,5
				)
			);
		
		insertVSTeams(vsTeams);
	}
	
	@Test
	public void testLoad()
	{
		StrategyUtils.printFirst24Item(loadEveryVSTeamRecords());
	}
	
	@Test
	public void testDate()
	{
		Calendar c =Calendar.getInstance();
		System.out.println(DateFormat.getDateInstance().format(c.getTime()));
		c.add(Calendar.MONTH, -1);
		System.out.println(DateFormat.getDateInstance().format(c.getTime()));
		
		Calendar d = Calendar.getInstance();
		d.set(2014, 01, 25);
		System.out.println(DateFormat.getDateInstance().format(d.getTime()));
	}
	
}
