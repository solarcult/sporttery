package shil.lottery.sport.strategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import shil.lottery.sport.entity.VSTeam;

/**
 * 打杂工具类
 * @author LiangJingJing
 * @since before 2014-07-20
 */
public class StrategyUtils {
	
	public static String[] buildScoreStatus(String[] prefinish, double[] nextline,String nextteam)
	{
		String[] result = new String[prefinish.length * nextline.length];
		
		for(int i =0;i<prefinish.length;i++)
		{
			for(int j=0;j<nextline.length;j++)
			{
				String mark;
				if(j==0) mark = " - "+ nextteam + ".胜";
				else if(j==1) mark = " - "+ nextteam + ".平";
				else if(j==2) mark = " - "+ nextteam + ".负";
				else mark = " : wtf,there is something wrong,u should never see me,dude.";
				
				result[i*nextline.length+j] = prefinish[i] + mark;
			}
		}
		
		return result;
	}
	
	public static double[] mutliMatrix(double[] prefinish, double[] nextline)
	{
		double[] result = new double[prefinish.length * nextline.length];
		
		for(int i =0;i<prefinish.length;i++)
		{
			for(int j=0;j<nextline.length;j++)
			{
				result[i*nextline.length+j] = prefinish[i] * nextline[j];
			}
		}
		
		return result;
	}
	
	public static String[][] convert2vs(List<VSTeam> vsTeams)
	{
		String[][] vs = new String[vsTeams.size()][2];
		
		int count = 0;
		for(VSTeam vsTeam : vsTeams)
		{
			vs[count] = vsTeam.getVs();
			count++;
		}
		
		return vs;
	}
	
	public static double[][] convert2peilv(List<VSTeam> vsTeams)
	{
		double[][] peilv = new double[vsTeams.size()][];
		
		int count = 0;
		for(VSTeam vsTeam : vsTeams)
		{
			peilv[count] = vsTeam.getPeilv();
			count++;
		}
		
		return peilv;
	}
	
	public static double[][] convert2peopleVote_rate(List<VSTeam> vsTeams)
	{
		double[][] peopleVote_rate = new double[vsTeams.size()][];
		
		int count = 0;
		for(VSTeam vsTeam : vsTeams)
		{
			peopleVote_rate[count] = vsTeam.getPeopleVote_rate();
			count++;
		}
		
		return peopleVote_rate;
	}
	
	public static double[][] convert2betCalcRate_web(List<VSTeam> vsTeams)
	{
		double[][] betCalcRate_web = new double[vsTeams.size()][];
		
		int count = 0;
		for(VSTeam vsTeam : vsTeams)
		{
			betCalcRate_web[count] = vsTeam.getBetCalcRate_web();
			count++;
		}
		
		return betCalcRate_web;
	}
	
	public static String[] convert2leagues(List<VSTeam> vsTeams)
	{
		String[] leagues = new String[vsTeams.size()];
		
		int count = 0;
		for(VSTeam vsTeam : vsTeams)
		{
			leagues[count] = vsTeam.getLeague();
			count++;
		}
		
		return leagues;
	}
	
	public static void printFirst24Item(List<?> lists)
	{
//		System.out.println("all in one total length : " +lists.size());
		
		for(int g=0; g<((lists.size()<24)?lists.size():24); g++)
		{
			System.out.println(lists.get(g));
		}
	}
	
	public static void printFirst24Item(Collection<?> collections)
	{
		
		for(Object c : collections)
		{
			System.out.println(c);
		}
	}
	
	public static void printFirstAnyItem(List<?> lists,int any)
	{
		System.out.println("all in one total length : " +lists.size());
		
		for(int g=0; g<((lists.size()<any)?lists.size():any); g++)
		{
			System.out.println(lists.get(g));
		}
	}
	
	public static <E> List<E> subList(List<E> lists,int fromIndex, int toIndex)
	{
		List<E> list = new ArrayList<E>();
		for(int i = fromIndex;i<(lists.size()<toIndex?lists.size():toIndex);i++)
		{
			list.add(lists.get(i));
		}
		
		return list;
	}
} 
