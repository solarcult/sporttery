package shil.lottery.wcp2022;


import java.util.ArrayList;
import java.util.List;

public class CombineMatches{

    private List<VSTeamsBetDetail> matches;

    private StringBuilder names;
    private double betRate;
    private double prob;
    private double multi;


    public CombineMatches() {
        matches = new ArrayList();
        names = new StringBuilder();
        prob = 1;
        multi = 1;
        betRate = 1;
    }

    public void add(VSTeamsBetDetail vsTeamsBetDetail){
        if(matches.contains(vsTeamsBetDetail)){
            System.out.println("Warning!!! already has: "+ vsTeamsBetDetail + matches.size());
        }else {
            matches.add(vsTeamsBetDetail);
            names.append(vsTeamsBetDetail.getCode()).append(" ").append(vsTeamsBetDetail.getName()).append(" + ");
            prob *= vsTeamsBetDetail.getProb();
            multi *= vsTeamsBetDetail.getMulti();
            betRate *= vsTeamsBetDetail.getBetRate();
        }
    }

    public List getMatches(){
        return matches;
    }

    public String getNames() {
        return names.toString();
    }

    public double getBetRate() {
        return betRate;
    }

    public double getProb() {
        return prob;
    }

    public double getMulti() {
        return multi;
    }

    public double roiPerCost(){
        return betRate/multi;
    }

    public double roiPerCostWithProb(){
        return betRate*prob/multi;
    }

    @Override
    public String toString() {
        return "CombineMatches{" +
                "names=:" + Utils.makeStringBetter(25,names.toString()) +
                ", betRate=" + String.format("%.2f",betRate) +
                ", prob=" + String.format("%.2f",prob) +
                ", multi=" + multi +
                ", roiPerCost=" + String.format("%.2f",roiPerCost()) +
                ", roiPerCostWithProb=" + String.format("%.2f",roiPerCostWithProb()) +
                (multi > betRate ? " ! 亏钱的 !" : "" ) +
                '}';
    }
}
