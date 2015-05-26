package shil.lottery.sport.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import shil.lottery.seriously.research.Guess013;
import shil.lottery.seriously.research.score013.Score013XYCombineFrequency;
import shil.lottery.seriously.vo.Score013;
import shil.lottery.seriously.vo.VSTeamScore013;
import shil.lottery.sport.entity.VSTeam;

public class Score013DaoImpl {

	public static void insertScore013(String name, VSTeam vsTeam, VSTeamScore013 vsTeamScore013, Score013XYCombineFrequency score013xyCombineFrequency){
		Connection connection = DataBaseManager.getConnection();
		PreparedStatement preparedStatement = null;
		try{
			preparedStatement = connection.prepareStatement(
				"insert into score013_data("
				+"id, win_odds_percent, draw_odds_percent, lose_odds_percent,"
				+"people_bet_win_precent, people_bet_draw_precent, people_bet_lose_precent,"
				+"match_result, gsd, lsd, agbl, albg,"
				+"gsd_lsd_current_quadrant_win_precent, gsd_lsd_current_quadrant_draw_precent, gsd_lsd_current_quadrant_lose_precent,"
				+"gsd_agbl_current_quadrant_win_precent, gsd_agbl_current_quadrant_draw_precent, gsd_agbl_current_quadrant_lose_precent,"
				+"gsd_albg_current_quadrant_win_precent, gsd_albg_current_quadrant_draw_precent, gsd_albg_current_quadrant_lose_precent,"
				+"lsd_agbl_current_quadrant_win_precent, lsd_agbl_current_quadrant_draw_precent, lsd_agbl_current_quadrant_lose_precent,"
				+"lsd_albg_current_quadrant_win_precent, lsd_albg_current_quadrant_draw_precent, lsd_albg_current_quadrant_lose_precent,"
				+"agbl_albg_current_quadrant_win_precent, agbl_albg_current_quadrant_draw_precent, agbl_albg_current_quadrant_lose_precent,"
				+"name)"
				+"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
			);
			
			preparedStatement.setLong(1, vsTeam.getId());
			preparedStatement.setDouble(2, vsTeam.getBetCalcRate_web()[0]);
			preparedStatement.setDouble(3, vsTeam.getBetCalcRate_web()[1]);
			preparedStatement.setDouble(4, vsTeam.getBetCalcRate_web()[2]);
			preparedStatement.setDouble(5, vsTeam.getPeopleVote_rate()[0]);
			preparedStatement.setDouble(6, vsTeam.getPeopleVote_rate()[1]);
			preparedStatement.setDouble(7, vsTeam.getPeopleVote_rate()[2]);
			preparedStatement.setInt(8, vsTeam.getMatch_Result());
			preparedStatement.setDouble(9, vsTeamScore013.getGsd());
			preparedStatement.setDouble(10, vsTeamScore013.getLsd());
			preparedStatement.setDouble(11, vsTeamScore013.getAgbl());
			preparedStatement.setDouble(12, vsTeamScore013.getAlbg());
			preparedStatement.setDouble(13, score013xyCombineFrequency.getGsd_lsd().getPct(Guess013.winS));
			preparedStatement.setDouble(14, score013xyCombineFrequency.getGsd_lsd().getPct(Guess013.drawS));
			preparedStatement.setDouble(15, score013xyCombineFrequency.getGsd_lsd().getPct(Guess013.loseS));
			
			preparedStatement.setDouble(16, score013xyCombineFrequency.getGsd_agbl().getPct(Guess013.winS));
			preparedStatement.setDouble(17, score013xyCombineFrequency.getGsd_agbl().getPct(Guess013.drawS));
			preparedStatement.setDouble(18, score013xyCombineFrequency.getGsd_agbl().getPct(Guess013.loseS));
			
			preparedStatement.setDouble(19, score013xyCombineFrequency.getGsd_albg().getPct(Guess013.winS));
			preparedStatement.setDouble(20, score013xyCombineFrequency.getGsd_albg().getPct(Guess013.drawS));
			preparedStatement.setDouble(21, score013xyCombineFrequency.getGsd_albg().getPct(Guess013.loseS));
			
			preparedStatement.setDouble(22, score013xyCombineFrequency.getLsd_agbl().getPct(Guess013.winS));
			preparedStatement.setDouble(23, score013xyCombineFrequency.getLsd_agbl().getPct(Guess013.drawS));
			preparedStatement.setDouble(24, score013xyCombineFrequency.getLsd_agbl().getPct(Guess013.loseS));
			
			preparedStatement.setDouble(25, score013xyCombineFrequency.getLsd_albg().getPct(Guess013.winS));
			preparedStatement.setDouble(26, score013xyCombineFrequency.getLsd_albg().getPct(Guess013.drawS));
			preparedStatement.setDouble(27, score013xyCombineFrequency.getLsd_albg().getPct(Guess013.loseS));
			
			preparedStatement.setDouble(28, score013xyCombineFrequency.getAgbl_albg().getPct(Guess013.winS));
			preparedStatement.setDouble(29, score013xyCombineFrequency.getAgbl_albg().getPct(Guess013.drawS));
			preparedStatement.setDouble(30, score013xyCombineFrequency.getAgbl_albg().getPct(Guess013.loseS));
			
			preparedStatement.setString(31, name);
			
			preparedStatement.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			try {
				if(preparedStatement!=null) preparedStatement.close();
//				if(connection!=null) connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public static Score013 queryScore013ByVsTeamId(long id){
		Score013 score013 = null;

		Connection connection = DataBaseManager.getConnection();
		PreparedStatement preparedStatement = null;
		try{
			preparedStatement = connection.prepareStatement(
				"select "
				+"name, win_odds_percent, draw_odds_percent, lose_odds_percent,"
				+"people_bet_win_precent, people_bet_draw_precent, people_bet_lose_precent,"
				+"match_result, gsd, lsd, agbl, albg,"
				+"gsd_lsd_current_quadrant_win_precent, gsd_lsd_current_quadrant_draw_precent, gsd_lsd_current_quadrant_lose_precent,"
				+"gsd_agbl_current_quadrant_win_precent, gsd_agbl_current_quadrant_draw_precent, gsd_agbl_current_quadrant_lose_precent,"
				+"gsd_albg_current_quadrant_win_precent, gsd_albg_current_quadrant_draw_precent, gsd_albg_current_quadrant_lose_precent,"
				+"lsd_agbl_current_quadrant_win_precent, lsd_agbl_current_quadrant_draw_precent, lsd_agbl_current_quadrant_lose_precent,"
				+"lsd_albg_current_quadrant_win_precent, lsd_albg_current_quadrant_draw_precent, lsd_albg_current_quadrant_lose_precent,"
				+"agbl_albg_current_quadrant_win_precent, agbl_albg_current_quadrant_draw_precent, agbl_albg_current_quadrant_lose_precent"
				+" from score013_data "
				+"where id = ?"
			);
			
			preparedStatement.setLong(1, id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				score013 = new Score013();
				score013.setId(id);
				score013.setName(resultSet.getString(1));
				score013.setWin_odds_percent(resultSet.getDouble(2));
				score013.setDraw_odds_percent(resultSet.getDouble(3));
				score013.setLose_odds_percent(resultSet.getDouble(4));
				score013.setPeople_bet_win_precent(resultSet.getDouble(5));
				score013.setPeople_bet_draw_precent(resultSet.getDouble(6));
				score013.setPeople_bet_lose_precent(resultSet.getDouble(7));
				score013.setMatch_result(resultSet.getInt(8));
				score013.setGsd(resultSet.getDouble(9));
				score013.setLsd(resultSet.getDouble(10));
				score013.setAgbl(resultSet.getDouble(11));
				score013.setAlbg(resultSet.getDouble(12));
				
				score013.setGsd_lsd_current_quadrant_win_precent(resultSet.getDouble(13));
				score013.setGsd_lsd_current_quadrant_draw_precent(resultSet.getDouble(14));
				score013.setGsd_lsd_current_quadrant_lose_precent(resultSet.getDouble(15));
				
				score013.setGsd_agbl_current_quadrant_win_precent(resultSet.getDouble(16));
				score013.setGsd_agbl_current_quadrant_draw_precent(resultSet.getDouble(17));
				score013.setGsd_agbl_current_quadrant_lose_precent(resultSet.getDouble(18));
				
				score013.setGsd_albg_current_quadrant_win_precent(resultSet.getDouble(19));
				score013.setGsd_albg_current_quadrant_draw_precent(resultSet.getDouble(20));
				score013.setGsd_albg_current_quadrant_lose_precent(resultSet.getDouble(21));
				
				score013.setLsd_agbl_current_quadrant_win_precent(resultSet.getDouble(22));
				score013.setLsd_agbl_current_quadrant_draw_precent(resultSet.getDouble(23));
				score013.setLsd_agbl_current_quadrant_lose_precent(resultSet.getDouble(24));
				
				score013.setLsd_albg_current_quadrant_win_precent(resultSet.getDouble(25));
				score013.setLsd_albg_current_quadrant_draw_precent(resultSet.getDouble(26));
				score013.setLsd_albg_current_quadrant_lose_precent(resultSet.getDouble(27));
				
				score013.setAgbl_albg_current_quadrant_win_precent(resultSet.getDouble(28));
				score013.setAgbl_albg_current_quadrant_draw_precent(resultSet.getDouble(29));
				score013.setAgbl_albg_current_quadrant_lose_precent(resultSet.getDouble(30));
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				if(preparedStatement!=null) preparedStatement.close();
//				if(connection!=null) connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return score013;
	}
}
