package shil.lottery.sport.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class ScoreCounterMap {

	//<进球数,记录器>
	Map<Integer,ScoreCounter> everyScoresMap;
	
	public ScoreCounterMap(){
		everyScoresMap = new HashMap<Integer, ScoreCounter>();
	}
	
	public ScoreCounter getScoreCounter(int score){
		ScoreCounter scoreCounter = everyScoresMap.get(score);
		if(scoreCounter == null){
			scoreCounter = new ScoreCounter(score);
			everyScoresMap.put(score, scoreCounter);
		}
		
		return scoreCounter;
	}
	
	public Set<Entry<Integer,ScoreCounter>> entrySet(){
		return everyScoresMap.entrySet();
	}
	
	public void increaseBingo(int score){
		getScoreCounter(score).increaseBingo();
	}
	
	public void increaseOneHit(int score, double weight){
		getScoreCounter(score).increaseBingo();
		getScoreCounter(score).increaseWeight(weight);
	}
	
	public List<ScoreCounter> getScoreCounterEveryList(){
		List<ScoreCounter> everylist = new ArrayList<ScoreCounter>();
		for(Entry<Integer,ScoreCounter> entry : everyScoresMap.entrySet()){
			everylist.add(entry.getValue());
		}
		
		return everylist;
	}
}
