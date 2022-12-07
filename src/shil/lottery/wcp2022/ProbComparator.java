package shil.lottery.wcp2022;

import java.util.Comparator;

public class ProbComparator implements Comparator<CombineMatches> {
    @Override
    public int compare(CombineMatches o1, CombineMatches o2) {
        if(o1.getProb() > o2.getProb()){
            return -1;
        }
        if(o1.getProb() < o2.getProb()){
            return 1;
        }
        return 0;
    }
}
