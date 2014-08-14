package shil.lottery.sport.strategy;

/**
 * 第六代算法,这回使用庄家的胜率n_win和人们投注的胜率n_conf之积排序
 * @author LiangJingJing
 * @since 2014-07-21 21:42
 */
public class StrategySix extends AbstractPursuitOfLottery3 {

	@Override
	public double getLiRunU(int u, double[]... dmatrix) {
		//n_shouyi[u] * n_win[u] * n_conf[u] ;
		return dmatrix[1][u] *dmatrix[2][u];
	}
	

}
