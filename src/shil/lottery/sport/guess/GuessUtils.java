package shil.lottery.sport.guess;

import java.util.HashSet;
import java.util.Set;

/**
 * 猜测工具类
 * @author LiangJingJing
 * @since 2014-07-23 23:57
 */
public class GuessUtils {
	
	public static int getMinPeilvPostion(double[] peilv)
	{
		if((peilv[0] <= peilv[1]) && (peilv[0] <= peilv[2]))
		{
			return 0;
		}
		else if((peilv[1] <= peilv[0]) && (peilv[1] <= peilv[2]))
		{
			return 1;
		}
		else if((peilv[2] <= peilv[0]) && (peilv[2] <= peilv[1]))
		{
			return 2;
		}
		else
		{
			throw new RuntimeException("please come and check this.");
		}
	}
	
	public static int findFinallyResult(double winchance,double drawchance,double losechance)
	{
		
		if((winchance > drawchance) && (winchance > losechance))
		{
			return 3;
		}
		else if((drawchance > winchance) && (drawchance > losechance))
		{
			return 1;
		}
		else if((losechance > winchance) && (losechance > drawchance))
		{
			return 0;
		}
		else if((winchance>losechance) && winchance  == drawchance)
		{
			return 4;
		}
		else if((winchance>drawchance) && winchance  == losechance)
		{
			return 5;
		}
		else if((drawchance > winchance) &&  drawchance == losechance)
		{
			return 6;
		}
		else
		{
			throw new RuntimeException("please come and check this.");
		}
	}
	
	public static boolean isGuessCorrect(int guess,int answer)
	{
//		System.out.println("guess: "+ guess +" , answer: "+answer);
		
		if(guess==answer)
		{
			return true;
		}
		else if((guess == 4)&& ((answer==3)||(answer == 1)))
		{
			return true;
		}
		else if((guess == 5) && ((answer==3)||(answer == 0)))
		{
			return true;
		}
		else if((guess == 6) && ((answer==1)||(answer == 0)))
		{
			return true;
		}
		
		return false;
	}
	
	public static boolean isGuessScoreCorrect(Set<Integer> s , int answer)
	{
		return s.contains(answer);
	}
	
	public static boolean isGuessScoreLegal(Set<Integer> result)
	{
		if(result.size()==0) return false;
		
		Set<Integer> bad = new HashSet<Integer>();
		bad.add(-1);bad.add(-2);bad.add(-3);bad.add(-4);bad.add(-5);bad.add(-6);bad.add(-7);
		
		for(int i : result)
		{
			if(bad.contains(i)) return false;
		}
		
		return true;
	}
	
	public static int reverseMatch_Result(int result)
	{
		if(result == 3) return 0;
		else if(result == 1) return 1;
		else if(result == 0) return 3;
		else throw new RuntimeException("what do your input?");
	}

}
