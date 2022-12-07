package shil.lottery.wcp2022;

import java.util.Objects;

public class VSTeamsBetDetail {

    private String code;

    private String name;

    private double betRate;

    private double prob;

    private double multi;

    public VSTeamsBetDetail(String code, String name, double betRate, double prob) {
        this.code = code;
        this.name = name;
        this.betRate = betRate;
        this.prob = prob;
        multi = 1;
    }

    public VSTeamsBetDetail(String code, String name, double betRate, double prob,double multi) {
        this.code = code;
        this.name = name;
        this.betRate = betRate;
        this.prob = prob;
        this.multi = multi;
    }

    public double getProbBetRate(){
        return betRate * prob;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VSTeamsBetDetail that = (VSTeamsBetDetail) o;
        return Objects.equals(code, that.code) && Double.compare(that.betRate, betRate) == 0 && Double.compare(that.prob, prob) == 0 && Double.compare(that.multi, multi) == 0 && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, betRate, prob, multi);
    }

    @Override
    public String toString() {
        return "VSTeamsBetDetail{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", betRate=" + betRate +
                ", prob=" + prob +
                ", multi=" + multi +
                '}';
    }
}
