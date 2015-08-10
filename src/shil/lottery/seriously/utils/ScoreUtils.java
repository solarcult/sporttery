package shil.lottery.seriously.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.math3.stat.Frequency;

import shil.lottery.sport.entity.ScoreCounter;
import shil.lottery.sport.entity.ScoreCounterCountComparator;

public class ScoreUtils {

	public static boolean isPassPctLimit(Frequency frequency,int topN,double pctLimit){
		
		Iterator<Entry<Comparable<?>, Long>> freItr = frequency.entrySetIterator();
		
		List<ScoreCounter> scs = new ArrayList<ScoreCounter>();
		
		while(freItr.hasNext()){
			Entry<Comparable<?>, Long> age = freItr.next();
			Double aged = (Double) age.getKey();
			ScoreCounter sc = new ScoreCounter(aged.intValue());
			sc.increaseCounter(age.getValue());
			scs.add(sc);
		}
		
		Collections.sort(scs,new ScoreCounterCountComparator());
		
		double total = 0;
		
		int n = (scs.size() > topN) ? topN : scs.size();
		
		for(int i=0; i < n; i++){
			total += frequency.getPct((double)scs.get(i).getScore());
		}
		
		return total >= pctLimit;
	}
}
