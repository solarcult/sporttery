package shil.lottery.sport.guess;

import java.util.List;

import shil.lottery.sport.cards.AnD;
import shil.lottery.sport.cards.AnalyzeCardPoint;
import shil.lottery.sport.cards.AnaylzeVSTeam2CardsbyScore;
import shil.lottery.sport.cards.CardPoint;
import shil.lottery.sport.cards.MatchResultEnum;
import shil.lottery.sport.cards.VSTeamCard;
import shil.lottery.sport.entity.VSTeam;

/**
 * 根据密度一个标准差来算概率
 * @author ljj
 * @since 2014-08-07 22:25
 */
public class GuessCardsCircleMatchResult implements Guess4TeamMatchResult3{

	public static int guessCardsCircleMatchResult(List<VSTeam> vsTeams, VSTeam predictMatch)
	{	
		List<CardPoint> cardPoints = AnalyzeCardPoint.analyzeEveryPointSituation(vsTeams).get(AnalyzeCardPoint.ALLINONE);
		
		VSTeamCard[] vs = AnaylzeVSTeam2CardsbyScore.convertVSTeam2Card(vsTeams, predictMatch);
		
		if(vs[0] == null || vs[1] == null)
		{
			return -5;
		}
		
		//VS0胜利的分析
		CardPoint vs0_win_wa_ld = AnalyzeCardPoint.getwattack_ldefense(vs[0],vs[1]);
		CardPoint vs0_win_wa_la = AnalyzeCardPoint.getwattack_lattack(vs[0],vs[1]);		
		
		double vs0_win_wa_ld_win_counter = 0;
		double vs0_win_wa_ld_draw_counter = 0;
		double vs0_win_wa_la_win_counter = 0;
		double vs0_win_wa_la_draw_counter = 0;
		for(CardPoint cp : cardPoints)
		{
			if(AnalyzeCardPoint.isInsideCircle(vs0_win_wa_ld, cp))
			{
				//分析进入到半径中的已知数据的类型,进行统计
				if(cp.getAnd()== AnD.wattack_ldefense)
				{
					if(cp.getMr() == MatchResultEnum.win)	vs0_win_wa_ld_win_counter++;
					else if(cp.getMr() == MatchResultEnum.draw1)	vs0_win_wa_ld_draw_counter++;
				}
			}
			
			if(AnalyzeCardPoint.isInsideCircle(vs0_win_wa_la, cp))
			{
				//分析进入到半径中的已知数据的类型,进行统计
				if(cp.getAnd()== AnD.wattack_lattck)
				{
					if(cp.getMr() == MatchResultEnum.win)	vs0_win_wa_la_win_counter++;
					else if(cp.getMr() == MatchResultEnum.draw1)	vs0_win_wa_la_draw_counter++;
				}
			}
		}
		
		double vs0_win_wa_ld_win_p = vs0_win_wa_ld_win_counter/(vs0_win_wa_ld_win_counter+vs0_win_wa_ld_draw_counter);
		double vs0_win_wa_ld_draw_p = vs0_win_wa_ld_draw_counter/(vs0_win_wa_ld_win_counter+vs0_win_wa_ld_draw_counter);
//		System.out.println("vs0_win_wa_ld_win_p: "+vs0_win_wa_ld_win_p);
//		System.out.println("vs0_win_wa_ld_draw_p: "+vs0_win_wa_ld_draw_p);
		
		double vs0_win_wa_la_win_p = vs0_win_wa_la_win_counter/(vs0_win_wa_la_win_counter+vs0_win_wa_la_draw_counter);
		double vs0_win_wa_la_draw_p = vs0_win_wa_la_draw_counter/(vs0_win_wa_la_win_counter+vs0_win_wa_la_draw_counter);
//		System.out.println("vs0_win_wa_la_win_p: "+vs0_win_wa_la_win_p);
//		System.out.println("vs0_win_wa_la_draw_p: "+vs0_win_wa_la_draw_p);
		
		int vs0_r = -1; 
		if(vs0_win_wa_ld_win_p > vs0_win_wa_ld_draw_p)
		{
			vs0_r = 3;
		}
		else if (vs0_win_wa_ld_win_p < vs0_win_wa_ld_draw_p)
		{
			vs0_r = 1;
		}
		else
		{
			vs0_r = 4;
		}
		
		//VS1胜利的分析
		CardPoint vs1_win_wa_ld = AnalyzeCardPoint.getwattack_ldefense(vs[1],vs[0]);
		CardPoint vs1_win_wa_la = AnalyzeCardPoint.getwattack_lattack(vs[1],vs[0]);

		double vs1_win_wa_ld_win_counter = 0;
		double vs1_win_wa_ld_draw_counter = 0;
		double vs1_win_wa_la_win_counter = 0;
		double vs1_win_wa_la_draw_counter = 0;
		for(CardPoint cp : cardPoints)
		{
			if(AnalyzeCardPoint.isInsideCircle(vs1_win_wa_ld, cp))
			{
				//分析进入到半径中的已知数据的类型,进行统计
				if(cp.getAnd()== AnD.wattack_ldefense)
				{
					if(cp.getMr() == MatchResultEnum.win)	vs1_win_wa_ld_win_counter++;
					else if(cp.getMr() == MatchResultEnum.draw1)	vs1_win_wa_ld_draw_counter++;
				}
			}
			
			if(AnalyzeCardPoint.isInsideCircle(vs1_win_wa_la, cp))
			{
				//分析进入到半径中的已知数据的类型,进行统计
				if(cp.getAnd()== AnD.wattack_lattck)
				{
					if(cp.getMr() == MatchResultEnum.win)	vs1_win_wa_la_win_counter++;
					else if(cp.getMr() == MatchResultEnum.draw1)	vs1_win_wa_la_draw_counter++;
				}
			}
		}
		
		double vs1_win_wa_ld_win_p = vs1_win_wa_ld_win_counter/(vs1_win_wa_ld_win_counter+vs1_win_wa_ld_draw_counter);
		double vs1_win_wa_ld_draw_p = vs1_win_wa_ld_draw_counter/(vs1_win_wa_ld_win_counter+vs1_win_wa_ld_draw_counter);
//		System.out.println("vs1_win_wa_ld_win_p: "+vs1_win_wa_ld_win_p);
//		System.out.println("vs1_win_wa_ld_draw_p: "+vs1_win_wa_ld_draw_p);
		
		double vs1_win_wa_la_win_p = vs1_win_wa_la_win_counter/(vs1_win_wa_la_win_counter+vs1_win_wa_la_draw_counter);
		double vs1_win_wa_la_draw_p = vs1_win_wa_la_draw_counter/(vs1_win_wa_la_win_counter+vs1_win_wa_la_draw_counter);
//		System.out.println("vs1_win_wa_la_win_p: "+vs1_win_wa_la_win_p);
//		System.out.println("vs1_win_wa_la_draw_p: "+vs1_win_wa_la_draw_p);
		
		int vs1_r = -1; 
		if(vs1_win_wa_ld_win_p > vs1_win_wa_ld_draw_p)
		{
			vs1_r = 3;
		}
		else if (vs1_win_wa_ld_win_p < vs1_win_wa_ld_draw_p)
		{
			vs1_r = 1;
		}
		else
		{
			vs1_r = 4;
		}
		
		int f = -5;
		if(vs0_r ==4 && vs1_r == 4) 
		{
			f = vs0_win_wa_ld_win_p >= vs1_win_wa_ld_win_p ? 4:6;
		}
		else if(vs0_r ==4 && vs1_r == 3)
		{
			f = vs0_win_wa_ld_win_p >= vs1_win_wa_ld_win_p ? 4:0;
		}
		else if(vs0_r == 4 && vs1_r ==1)
		{
			f = vs0_win_wa_ld_win_p >= vs1_win_wa_ld_draw_p ? 4:1;
		}
		else if(vs0_r ==3 && vs1_r == 4) 
		{
			f = vs0_win_wa_ld_win_p >= vs1_win_wa_ld_win_p ? 3:6;
		}
		else if(vs0_r ==3 && vs1_r == 3)
		{
			f = vs0_win_wa_ld_win_p >= vs1_win_wa_ld_win_p ? 3:0;
		}
		else if(vs0_r == 3 && vs1_r ==1)
		{
			f = vs0_win_wa_ld_win_p >= vs1_win_wa_ld_draw_p ? 3:1;
		}
		else if(vs0_r ==1 && vs1_r == 4) 
		{
			f = vs0_win_wa_ld_win_p >= vs1_win_wa_ld_win_p ? 1:6;
		}
		else if(vs0_r ==1 && vs1_r == 3)
		{
			f = vs0_win_wa_ld_win_p >= vs1_win_wa_ld_win_p ? 1:0;
		}
		else if(vs0_r == 1 && vs1_r ==1)
		{
			f = 1;
		}
		else
		{
			throw new RuntimeException("wtf");
		}
//		System.out.println(f);
		
 		return f;
	}

	@Override
	public int guess4teamMatchResult(List<VSTeam> vsTeams, VSTeam predictMatch) {
		
		return GuessCardsCircleMatchResult.guessCardsCircleMatchResult(vsTeams, predictMatch);
		
	}
}
