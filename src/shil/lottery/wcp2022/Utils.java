package shil.lottery.wcp2022;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static String makeStringBetter(int maxLength, String x){
        StringBuilder sb = new StringBuilder(x);
        int left = maxLength - x.length();
        for(int i=0;i<left;i++){
            sb.append(" ");
        }
        return sb.toString();
    }

    public static List<CombineMatches> combine2match(List<VSTeamsBetDetail> ... ts){

        List<CombineMatches> combines = new ArrayList();
        //得到第一个链表的第1个元素
        for(int k = 0 ; k < ts[0].size(); k++)
            //得到第二个链表的第1个元素
            for(int l = 0; l <ts[1].size(); l++) {
                CombineMatches combineMatches = new CombineMatches();
                combineMatches.add(ts[0].get(k));
                combineMatches.add(ts[1].get(l));
                combines.add(combineMatches);
            }
        return combines;
    }

    public static List<CombineMatches> combine3match(List<VSTeamsBetDetail> ... ts){

        List<CombineMatches> combines = new ArrayList();
            //得到第一个链表的第1个元素
            for(int k = 0 ; k < ts[0].size(); k++)
                //得到第二个链表的第1个元素
                for(int l = 0; l <ts[1].size(); l++)
                    //得到第三个链表的第1个元素
                    for(int y = 0; y <ts[2].size(); y++){
                        CombineMatches combineMatches = new CombineMatches();
                        combineMatches.add(ts[0].get(k));
                        combineMatches.add(ts[1].get(l));
                        combineMatches.add(ts[2].get(y));
                        combines.add(combineMatches);
                    }
        return combines;
    }

    public static List<CombineMatches> self(List<VSTeamsBetDetail> self){
        List<CombineMatches> combines = new ArrayList();
        for(VSTeamsBetDetail v : self){
            CombineMatches combineMatches = new CombineMatches();
            combineMatches.add(v);
            combines.add(combineMatches);
        }
        return combines;
    }
}
