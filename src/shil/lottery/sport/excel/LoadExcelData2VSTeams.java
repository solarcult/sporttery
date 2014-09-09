package shil.lottery.sport.excel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import shil.lottery.sport.entity.VSTeam;

/**
 * 将数据Excel数据转换为VSTeam对象
 * @author LiangJingJing
 * @since before 2014-07-20
 */
public class LoadExcelData2VSTeams {
	
	public static List<VSTeam> justDoIt(File file)
	{
		List<VSTeam> vsTeams = new ArrayList<VSTeam>();
		
	    String[][] result = ExcelUtils.getData(file);
	    int rowLength = result.length;
	    for(int i=0;i<rowLength;i+=5) {
	        String title = result[i][0];
	        StringTokenizer st = new StringTokenizer(title, " ");
	        String weekchangci = st.nextToken();
	        String week = weekchangci.substring(0, 2);
	        int changci = Integer.parseInt(weekchangci.substring(2));
	        String league = st.nextToken();
	        String date = st.nextToken();
	        String[] date_detail = date.split("-");
	        int year = Integer.parseInt(date_detail[0]);
	        int month = Integer.parseInt(date_detail[1]);
	        int day = Integer.parseInt(date_detail[2]);
	        
	        String[] vs = new String[2];
	        vs[0] = st.nextToken();
	        st.nextToken();
	        vs[1] = st.nextToken();
	        
	        double[] peopleVote_num = new double[3];
	        peopleVote_num[0] = Double.parseDouble(result[i+2][1]);
	        peopleVote_num[1] = Double.parseDouble(result[i+3][1]);
	        peopleVote_num[2] = Double.parseDouble(result[i+4][1]);
	        
	        double[] peilv = new double[3];
	        peilv[0] = Double.parseDouble(result[i+2][2]);
	        peilv[1] = Double.parseDouble(result[i+3][2]);
	        peilv[2] = Double.parseDouble(result[i+4][2]);
	        
	        int teama_goals = -1;
	        int teamb_goals = -1;
	        String resultScore = result[i+2][6];
//	        if(resultScore.indexOf(":")!=-1) throw new RuntimeException("should not have :");
	        String[] scores = resultScore.split(",");
	        if(scores.length >= 2)
	        {
	        	teama_goals = Integer.parseInt(scores[0]);
	        	teamb_goals = Integer.parseInt(scores[1]);
	        }
	        
	        vsTeams.add(VSTeam.builderVSTeam(vs, peilv, peopleVote_num, year, month, day, changci, week, league, teama_goals, teamb_goals));
	    }
		
		return vsTeams;
	}

	public static void main(String[] args)
	{
		justDoIt(new File("d:\\test.xls"));
	}
}
