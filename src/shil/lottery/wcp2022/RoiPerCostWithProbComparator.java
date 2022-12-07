package shil.lottery.wcp2022;

import java.util.Comparator;

public class RoiPerCostWithProbComparator implements Comparator<CombineMatches> {

    @Override
    public int compare(CombineMatches o1, CombineMatches o2) {
        if(o1.roiPerCostWithProb() > o2.roiPerCostWithProb()){
            return -1;
        }
        if(o1.roiPerCostWithProb() < o2.roiPerCostWithProb()){
            return 1;
        }
        return 0;
    }
}
