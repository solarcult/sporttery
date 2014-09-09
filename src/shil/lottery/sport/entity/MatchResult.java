package shil.lottery.sport.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 比赛结果对象
 * @author LiangJingJing
 * @since 2014-07-28 0:03
 */
public class MatchResult {
	
	public MatchResult(double door)
	{
		this.door = door;
	}
	
	private StatusCounter winStatusCounter;
	private StatusCounter drawStatusCounter;
	private StatusCounter loseStatusCounter;
	private double door;
	
	public StatusCounter getWinStatusCounter() {
		return winStatusCounter;
	}

	public void setWinStatusCounter(StatusCounter winStatusCounter) {
		this.winStatusCounter = winStatusCounter;
	}

	public StatusCounter getDrawStatusCounter() {
		return drawStatusCounter;
	}

	public void setDrawStatusCounter(StatusCounter drawStatusCounter) {
		this.drawStatusCounter = drawStatusCounter;
	}

	public StatusCounter getLoseStatusCounter() {
		return loseStatusCounter;
	}

	public void setLoseStatusCounter(StatusCounter loseStatusCounter) {
		this.loseStatusCounter = loseStatusCounter;
	}
	
	public int getMatch_Result(){
		
		List<StatusCounter> statusCounters  = new ArrayList<StatusCounter>();
		
		statusCounters.add(winStatusCounter);
		statusCounters.add(drawStatusCounter);
		statusCounters.add(loseStatusCounter);
		
		Collections.sort(statusCounters);
		
//		return statusCounters.get(0).getResult() + statusCounters.get(1).getResult();
		
		if(statusCounters.get(0).getProb() - statusCounters.get(1).getProb() > door)
		{
			return statusCounters.get(0).getResult(); 
		}
		
		return -2;
	}

	@Override
	public String toString() {
		return "MatchResult [winStatusCounter=" + winStatusCounter
				+ ", drawStatusCounter=" + drawStatusCounter
				+ ", loseStatusCounter=" + loseStatusCounter
				+ ", getMatch_Result()=" + getMatch_Result() + "]";
	}

}
