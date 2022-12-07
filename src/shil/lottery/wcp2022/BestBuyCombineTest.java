package shil.lottery.wcp2022;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BestBuyCombineTest {

    static List<VSTeamsBetDetail> a = new ArrayList<>();
    static List<VSTeamsBetDetail> b = new ArrayList<>();
    static List<VSTeamsBetDetail> c = new ArrayList<>();
    static List<VSTeamsBetDetail> d = new ArrayList<>();
    static List<VSTeamsBetDetail> e = new ArrayList<>();
    static List<VSTeamsBetDetail> f = new ArrayList<>();



    static {

//        b.add(new VSTeamsBetDetail("044","+2平",4.1,0.6));
//        b.add(new VSTeamsBetDetail("044","总进4球",4,0.6));
//        b.add(new VSTeamsBetDetail("044","0:2",6.5,0.65));
//        b.add(new VSTeamsBetDetail("044","0:3",6,0.65));
//        b.add(new VSTeamsBetDetail("044","平负",4,0.25));
//        b.add(new VSTeamsBetDetail("044","负负",1.28,0.7));
//        b.add(new VSTeamsBetDetail("044","平负|负负",1.85,0.85,2));

//        c.add(new VSTeamsBetDetail("041","单负",1.84,0.85));
//        c.add(new VSTeamsBetDetail("041","总进3球",3.65,0.6));
//        c.add(new VSTeamsBetDetail("041","0:2",6.5,0.6));
//        c.add(new VSTeamsBetDetail("041","1:2",6,0.6));
//        c.add(new VSTeamsBetDetail("041","平负",4.6,0.35));
//        c.add(new VSTeamsBetDetail("041","负负",3.15,0.35));
//        c.add(new VSTeamsBetDetail("041","平负|负负",3.87,0.6,2));

        d.add(new VSTeamsBetDetail("045","单胜",4.7,0.5));
        d.add(new VSTeamsBetDetail("045","+1胜",2.02,0.6));
//        d.add(new VSTeamsBetDetail("045","总进3球",3.6,0.6));
//        d.add(new VSTeamsBetDetail("045","0:2",6.25,0.6));
//        d.add(new VSTeamsBetDetail("045","平负",3.9,0.3));
//        d.add(new VSTeamsBetDetail("045","负负",1.86,0.6));
//        d.add(new VSTeamsBetDetail("045","平负|负负",2.88,0.8,2));

//        e.add(new VSTeamsBetDetail("046","单负",1.43,0.8));
        e.add(new VSTeamsBetDetail("046","+1胜",2.05,0.5));
//        e.add(new VSTeamsBetDetail("046","总进2球",3.65,0.55));
//        e.add(new VSTeamsBetDetail("046","0:2",6.25,0.6));
        e.add(new VSTeamsBetDetail("046","平负",4.25,0.55));
        e.add(new VSTeamsBetDetail("046","负负",2.27,0.4));
        e.add(new VSTeamsBetDetail("046","平负|负负",3,0.6,2));

        a.add(new VSTeamsBetDetail("048","单胜",8.25,0.2));
//        a.add(new VSTeamsBetDetail("048","+1平",3.5,0.55));
//        a.add(new VSTeamsBetDetail("048","总进3球",3.45,0.6));
//        a.add(new VSTeamsBetDetail("048","0:2",6.25,0.6));
        a.add(new VSTeamsBetDetail("048","平负",3.8,0.35));
        a.add(new VSTeamsBetDetail("048","负负",1.72,0.6));
        a.add(new VSTeamsBetDetail("048","平负|负负",2.2,0.7,2));

        f.add(new VSTeamsBetDetail("049","单胜",1.61,0.8));
        f.add(new VSTeamsBetDetail("049","单平",3.2,0.6));
        f.add(new VSTeamsBetDetail("049","-1平",3.2,0.55));
        f.add(new VSTeamsBetDetail("049","-1败",1.97,0.55));
        f.add(new VSTeamsBetDetail("049","总进2球",3.1,0.6));
        f.add(new VSTeamsBetDetail("049","1:0",5.3,0.6));
        f.add(new VSTeamsBetDetail("049","2:0",6.5,0.55));
        f.add(new VSTeamsBetDetail("049","平胜",4,0.55));
        f.add(new VSTeamsBetDetail("049","胜胜",2.5,0.55));
        f.add(new VSTeamsBetDetail("049","平胜|胜胜",3.12,0.6,2));
    }


    public static void main(String[] args){

//        self(a);

        sortX(Utils.combine2match(e,a));

//        sortX(Utils.combine3match(a,d,e));


    }


    static void sortX(List<CombineMatches> cms){

        int cutSize = cms.size() > 50 ? cms.size()/2 : cms.size();

        //按概率排序
        Collections.sort(cms,new RoiPerCostWithProbComparator());
        System.out.println("total : " + cms.size());

        List<CombineMatches> cutProbs = new ArrayList<>(cutSize);

        for(int i=0 ;i<cms.size()
                && i<cutSize
                ;i++){
//            System.out.println(cms.get(i));
            cutProbs.add(cms.get(i));
        }

        System.out.println();

        Collections.sort(cms,new RoiPerCostComparator());

        List<CombineMatches> last = new ArrayList<>(cutSize);

        for(int i=0 ;i<cms.size()
                && i<cutSize
                ;i++){
//            System.out.println(cms.get(i));
            if(cutProbs.contains(cms.get(i))){
                last.add(cms.get(i));
            }
        }

        System.out.println("\n ROI Finally: \n");

        Collections.sort(last,new RoiPerCostWithProbComparator());

        System.out.println(last.size());
        for(CombineMatches l : last){
            System.out.println(l);
        }

        Collections.sort(cms,new ProbComparator());

        List<CombineMatches> probs = new ArrayList<>(cutSize);

        for(int i=0 ;i<cms.size()
                && i<cutSize
                ;i++){
//            System.out.println(cms.get(i));
            if(last.contains(cms.get(i))){
                probs.add(cms.get(i));
            }
        }

        System.out.println("\n Prob Finally: \n");

        Collections.sort(probs,new ProbComparator());

        System.out.println(probs.size());
        for(CombineMatches p : probs){
            System.out.println(p);
        }
    }

    static void self(List<VSTeamsBetDetail> self){
        List<CombineMatches> last = Utils.self(self);
        Collections.sort(last,new RoiPerCostWithProbComparator());
        for(CombineMatches c : last)
            System.out.println(c);
    }

}
