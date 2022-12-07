package shil.lottery.wcp2022;

import java.util.Comparator;

/**
 * 不考虑概率的投入汇报比
 */
public class RoiPerCostComparator implements Comparator<CombineMatches> {
    @Override
    public int compare(CombineMatches o1, CombineMatches o2) {
        if(o1.roiPerCost() > o2.roiPerCost()){
            return -1;
        }
        if(o1.roiPerCost() < o2.roiPerCost()){
            return 1;
        }
        return 0;
    }
}
