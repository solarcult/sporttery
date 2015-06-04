package shil.lottery.sport.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
