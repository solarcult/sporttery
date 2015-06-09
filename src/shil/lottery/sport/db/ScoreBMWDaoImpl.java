package shil.lottery.sport.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import shil.lottery.seriously.research.score013.Score013BMW;

public class ScoreBMWDaoImpl {

	public static void insertScoreBMS(){
		Connection connection = DataBaseManager.getConnection();
		PreparedStatement preparedStatement = null;
		try{
			preparedStatement = connection.prepareStatement(
				"insert into score_bmw("
				+"id, odd_rate_bm, odd_rate_ms, odd_rate_bms,"
				+"gsd_lsd_sub, gsd_lsd_ratio, gsd_agbl_sub, gsd_agbl_ratio,"
				+"gsd_albg_sub, gsd_albg_ratio, lsd_agbl_sub, lsd_agbl_ratio,"
				+"lsd_albg_sub, lsd_albg_ratio, agbl_albg_sub, agbl_albg_ratio,"
				+"gsd_lsd_quat_bm, gsd_lsd_quat_ms, gsd_lsd_quat_bms,"
				+"gsd_agbl_quat_bm, gsd_agbl_quat_ms, gsd_agbl_quat_bms,"
				+"gsd_albg_quat_bm, gsd_albg_quat_ms, gsd_albg_quat_bms,"
				+"lsd_agbl_quat_bm, lsd_agbl_quat_ms, lsd_agbl_quat_bms,"
				+"lsd_albg_quat_bm, lsd_albg_quat_ms, lsd_albg_quat_bms,"
				+"agbl_albg_quat_bm, agbl_albg_quat_ms, agbl_albg_quat_bms,"
				+"bingo, result"
				+"values("
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?)"
			);
			
			
			
			preparedStatement.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(preparedStatement!=null)
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	
	public static void insertScoreBMSOnlyOdd(Score013BMW score013bmw){
		Connection connection = DataBaseManager.getConnection();
		PreparedStatement preparedStatement = null;
		try{
			preparedStatement = connection.prepareStatement(
				"insert into score_bmw("
				+"id,name, odd_rate_bm, odd_rate_ms, odd_rate_bms,"
				+"odd_rate_bingo, result)"
				+" values(?,?,?,?,?,?,?)"
			);
			
			preparedStatement.setLong(1, score013bmw.getId());
			preparedStatement.setString(2,score013bmw.getName());
			preparedStatement.setDouble(3, score013bmw.getOdd_rate_bm());
			preparedStatement.setDouble(4, score013bmw.getOdd_rate_ms());
			preparedStatement.setDouble(5, score013bmw.getOdd_rate_bms());
			preparedStatement.setString(6, score013bmw.getOdd_rate_bingo());
			preparedStatement.setString(7, score013bmw.getResult());
			
			preparedStatement.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(preparedStatement!=null)
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
}
