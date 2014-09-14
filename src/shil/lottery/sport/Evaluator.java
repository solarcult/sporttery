package shil.lottery.sport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import shil.lottery.sport.analyze.AnalyzeVSTeamsCorrectPercent;
import shil.lottery.sport.cards.AnalyzeCardPoint;
import shil.lottery.sport.chain.ChainUtils;
import shil.lottery.sport.chain.GuessNine;
import shil.lottery.sport.db.SportMetaDaoImpl;
import shil.lottery.sport.entity.VSTeam;
import shil.lottery.sport.guess.Guess4TeamMatchResult2;
import shil.lottery.sport.guess.Guess4TeamMatchResult3;
import shil.lottery.sport.guess.Guess4TeamScores1;
import shil.lottery.sport.guess.GuessCardsCircleMatchResult;
import shil.lottery.sport.guess.GuessEight;
import shil.lottery.sport.guess.GuessFiveOne;
import shil.lottery.sport.guess.GuessFiveTwo;
import shil.lottery.sport.guess.GuessFour;
import shil.lottery.sport.guess.GuessScoreOne;
import shil.lottery.sport.guess.GuessSeven;
import shil.lottery.sport.guess.GuessSix;
import shil.lottery.sport.guess.GuessThree;
import shil.lottery.sport.guess.GuessTwo;
import shil.lottery.sport.guess.GuessUtils;
import shil.lottery.sport.legacy.Guess4TeamMatchResult1;
import shil.lottery.sport.legacy.GuessOne;
import shil.lottery.sport.legacy.GuessRefineScoreVSTeamProbability;
import shil.lottery.sport.score.GuessScoreCombine7Weight;
import shil.lottery.sport.score.GuessScoreCombineAdvantage;
import shil.lottery.sport.score.GuessScoreLeagueProbability;
import shil.lottery.sport.score.GuessScoreVSTeamProbability;
import shil.lottery.sport.score.GuessScoreVSTeamWeight;
import shil.lottery.sport.score.GuessScoreVSTeamWeight7League;
import shil.lottery.sport.score.GuessScoreVSTeamWeight7League7VSTeam;
import shil.lottery.sport.score.GuessScoreVSTeamWeight7VSTeam;
import shil.lottery.sport.score.GuessScoreVSTeamWeightNoFirstdoor;
import shil.lottery.sport.strategy.StrategyUtils;

public class Evaluator {
	
	public static double predataP = 0.7d; 
	
	public static void main(String[] args)
	{
//		findbest4();
//		evaluatorVsTeamScores();	//71.4%	85/119 	23.9%
//		evalutorLeagueScores();	//66.75%  538/806	22.5%
//		evaluatorGuessTwo();
//		evaluatorGuessThree();
//		evaluatorGuessTwoThree();
//		evaluatorGuessFour();
//		findbestGuessFour();
//		findbestVSTeamScores();
//		findbestGuessSix();
//		evaluatorGuessSix();
//		evaluatorGuessSeven();
//		evaluatorGuessEight();
//		evaluatorGuessCardsCircleMatchResult();
//		findbestGuessCardsCircleMatchResult();
//		evaluatorGuessNiceMatchResult();
//		evaluatorVsTeamScoresWeight();
//		findbestVSTeamScoresWeight();
//		evaluatorVsTeamScoresWeight7VSTeam(); 	//67.8% 59/87			25.5%
//		evaluatorVsTeamScoresWeight7League();	//66.7%  76/114			23.9%
//		evaluatorVsTeamScoresWeight7League7VSTeam();	//68.9% 42/61	25.5%
//		evaluatorVsTeamScoresWeightNoFirstdoor();	//	65.8%  807/1227	21.9%
//		evaluatorScoresCombine();	//59%	136/230						23.5%
//		evaluatorScoresCombine7Weight(); //60.8% 104/171				23.4%
	}
	
	public static void evaluatorScoresCombine7Weight()
	{
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		
		Guess4TeamScores1 guess = new GuessScoreCombine7Weight();
		
		System.out.println(vsTeams.size());
		int size = (int) (vsTeams.size() * predataP);
		
		double bingo = 0;
		double guessnumber = 0;
		double totalguessnumber = 0;
		for(int i=size;i<vsTeams.size();i++)
		{
			List<VSTeam> temp = vsTeams.subList(0, i);
			Set<Integer> s = guess.guess4teamScores(temp, vsTeams.get(i), false);
//			System.out.println(vsTeams.get(i).getVs()[0]+ " : "+s);
			if(!GuessUtils.isGuessScoreLegal(s))
			{
				continue;
			}
			guessnumber++;
			totalguessnumber+=s.size();
			if(GuessUtils.isGuessScoreCorrect(s, vsTeams.get(i).getTeama_goals()+vsTeams.get(i).getTeamb_goals()))
			{
				bingo++;
			}
		}
		System.out.println("总共猜了球数: "+totalguessnumber+" 效率: "+ bingo / totalguessnumber);
		System.out.println("中: "+bingo);
		System.out.println("猜了: "+guessnumber+" 平均每场猜了: "+totalguessnumber/guessnumber);
		System.out.println("一共: "+ (vsTeams.size() - size));
		System.out.println("evaluatorVsTeamScores: " + (bingo/guessnumber) + " <-  correct%");
	}
	
	public static void evaluatorScoresCombine()
	{
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		
		Guess4TeamScores1 guess = new GuessScoreCombineAdvantage();
		
		System.out.println(vsTeams.size());
		int size = (int) (vsTeams.size() * predataP);
		
		double bingo = 0;
		double guessnumber = 0;
		double totalguessnumber = 0;
		for(int i=size;i<vsTeams.size();i++)
		{
			List<VSTeam> temp = vsTeams.subList(0, i);
			Set<Integer> s = guess.guess4teamScores(temp, vsTeams.get(i), false);
//			System.out.println(vsTeams.get(i).getVs()[0]+ " : "+s);
			if(!GuessUtils.isGuessScoreLegal(s))
			{
				continue;
			}
			guessnumber++;
			totalguessnumber+=s.size();
			if(GuessUtils.isGuessScoreCorrect(s, vsTeams.get(i).getTeama_goals()+vsTeams.get(i).getTeamb_goals()))
			{
				bingo++;
			}
		}
		System.out.println("总共猜了球数: "+totalguessnumber+" 效率: "+ bingo / totalguessnumber);
		System.out.println("中: "+bingo);
		System.out.println("猜了: "+guessnumber+" 平均每场猜了: "+totalguessnumber/guessnumber);
		System.out.println("一共: "+ (vsTeams.size() - size));
		System.out.println("evaluatorVsTeamScores: " + (bingo/guessnumber) + " <-  correct%");
	}
	
	public static void evaluatorVsTeamScoresWeightNoFirstdoor()
	{
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		
		Guess4TeamScores1 guess = new GuessScoreVSTeamWeightNoFirstdoor();
		
		System.out.println(vsTeams.size());
		int size = (int) (vsTeams.size() * predataP);
		
		double bingo = 0;
		double guessnumber = 0;
		double totalguessnumber = 0;
		for(int i=size;i<vsTeams.size();i++)
		{
			List<VSTeam> temp = vsTeams.subList(0, i);
			Set<Integer> s = guess.guess4teamScores(temp, vsTeams.get(i), false);
//			System.out.println(vsTeams.get(i).getVs()[0]+ " : "+s);
			if(!GuessUtils.isGuessScoreLegal(s))
			{
				continue;
			}
			guessnumber++;
			totalguessnumber+=s.size();
			if(GuessUtils.isGuessScoreCorrect(s, vsTeams.get(i).getTeama_goals()+vsTeams.get(i).getTeamb_goals()))
			{
				bingo++;
			}
		}
		System.out.println("总共猜了球数: "+totalguessnumber+" 效率: "+ bingo / totalguessnumber);
		System.out.println("中: "+bingo);
		System.out.println("猜了: "+guessnumber+" 平均每场猜了: "+totalguessnumber/guessnumber);
		System.out.println("一共: "+ (vsTeams.size() - size));
		System.out.println("evaluatorVsTeamScores: " + (bingo/guessnumber) + " <-  correct%");
	}
	
	public static void evaluatorVsTeamScoresWeight7League7VSTeam()
	{
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		
		Guess4TeamScores1 guess = new GuessScoreVSTeamWeight7League7VSTeam();
		
		System.out.println(vsTeams.size());
		int size = (int) (vsTeams.size() * predataP);
		
		double bingo = 0;
		double guessnumber = 0;
		double totalguessnumber = 0;
		for(int i=size;i<vsTeams.size();i++)
		{
			List<VSTeam> temp = vsTeams.subList(0, i);
			Set<Integer> s = guess.guess4teamScores(temp, vsTeams.get(i), false);
//			System.out.println(vsTeams.get(i).getVs()[0]+ " : "+s);
			if(!GuessUtils.isGuessScoreLegal(s))
			{
				continue;
			}
			guessnumber++;
			totalguessnumber+=s.size();
			if(GuessUtils.isGuessScoreCorrect(s, vsTeams.get(i).getTeama_goals()+vsTeams.get(i).getTeamb_goals()))
			{
				bingo++;
			}
		}
		System.out.println("总共猜了球数: "+totalguessnumber+" 效率: "+ bingo / totalguessnumber);
		System.out.println("中: "+bingo);
		System.out.println("猜了: "+guessnumber+" 平均每场猜了: "+totalguessnumber/guessnumber);
		System.out.println("一共: "+ (vsTeams.size() - size));
		System.out.println("evaluatorVsTeamScores: " + (bingo/guessnumber) + " <-  correct%");
	}
	
	public static void evaluatorVsTeamScoresWeight7VSTeam()
	{
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		
		Guess4TeamScores1 guess = new GuessScoreVSTeamWeight7VSTeam();
		
		System.out.println(vsTeams.size());
		int size = (int) (vsTeams.size() * predataP);
		
		double bingo = 0;
		double guessnumber = 0;
		double totalguessnumber = 0;
		for(int i=size;i<vsTeams.size();i++)
		{
			List<VSTeam> temp = vsTeams.subList(0, i);
			Set<Integer> s = guess.guess4teamScores(temp, vsTeams.get(i), false);
//			System.out.println(vsTeams.get(i).getVs()[0]+ " : "+s);
			if(!GuessUtils.isGuessScoreLegal(s))
			{
				continue;
			}
			guessnumber++;
			totalguessnumber+=s.size();
			if(GuessUtils.isGuessScoreCorrect(s, vsTeams.get(i).getTeama_goals()+vsTeams.get(i).getTeamb_goals()))
			{
				bingo++;
			}
		}
		System.out.println("总共猜了球数: "+totalguessnumber+" 效率: "+ bingo / totalguessnumber);
		System.out.println("中: "+bingo);
		System.out.println("猜了: "+guessnumber+" 平均每场猜了: "+totalguessnumber/guessnumber);
		System.out.println("一共: "+ (vsTeams.size() - size));
		System.out.println("evaluatorVsTeamScores: " + (bingo/guessnumber) + " <-  correct%");
	}
	
	public static void evaluatorVsTeamScoresWeight7League()
	{
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		
		Guess4TeamScores1 guess = new GuessScoreVSTeamWeight7League();
		
		System.out.println(vsTeams.size());
		int size = (int) (vsTeams.size() * predataP);
		
		double bingo = 0;
		double guessnumber = 0;
		double totalguessnumber = 0;
		for(int i=size;i<vsTeams.size();i++)
		{
			List<VSTeam> temp = vsTeams.subList(0, i);
			Set<Integer> s = guess.guess4teamScores(temp, vsTeams.get(i), false);
//			System.out.println(vsTeams.get(i).getVs()[0]+ " : "+s);
			if(!GuessUtils.isGuessScoreLegal(s))
			{
				continue;
			}
			guessnumber++;
			totalguessnumber+=s.size();
			if(GuessUtils.isGuessScoreCorrect(s, vsTeams.get(i).getTeama_goals()+vsTeams.get(i).getTeamb_goals()))
			{
				bingo++;
			}
		}
		System.out.println("总共猜了球数: "+totalguessnumber+" 效率: "+ bingo / totalguessnumber);
		System.out.println("中: "+bingo);
		System.out.println("猜了: "+guessnumber+" 平均每场猜了: "+totalguessnumber/guessnumber);
		System.out.println("一共: "+ (vsTeams.size() - size));
		System.out.println("evaluatorVsTeamScores: " + (bingo/guessnumber) + " <-  correct%");
	}
	
	private static void findbestVSTeamScoresWeight() {
		
		List<Eva> ds = new ArrayList<Eva>();
		
//		GuessScoreLeagueProbability.firstdoor = 0d;
		
//		while(GuessScoreLeagueProbability.firstdoor < 1)
		{
			GuessScoreVSTeamWeight.firstdoor = 0.6d;
//			System.out.println("GuessScoreLeagueProbability.firstdoor="+GuessScoreLeagueProbability.firstdoor+"GuessScoreVSTeamWeight.firstdoor="+GuessScoreVSTeamWeight.firstdoor);
			while(GuessScoreVSTeamWeight.firstdoor < 1)
			{
				System.out.println("GuessScoreVSTeamWeight.firstdoor="+GuessScoreVSTeamWeight.firstdoor);
				evaluatorVsTeamScoresWeight(ds);
				GuessScoreVSTeamWeight.firstdoor += 0.001;
			}
//			GuessScoreLeagueProbability.firstdoor += 0.01;
			Collections.sort(ds);
			ds = StrategyUtils.subList(ds, 0, 108);
		}
		
		Collections.sort(ds);
		StrategyUtils.printFirstAnyItem(ds,256);
	}
	
	public static void evaluatorVsTeamScoresWeight(List<Eva> ds)
	{
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		
		Guess4TeamScores1 guess = new GuessScoreVSTeamWeight();
		
		int size = (int) (vsTeams.size() * predataP);
		
		double bingo = 0;
		double guessnumber = 0;
		for(int i=size;i<vsTeams.size();i++)
		{
			List<VSTeam> temp = vsTeams.subList(0, i);
			Set<Integer> s = guess.guess4teamScores(temp, vsTeams.get(i), false);
			if(!GuessUtils.isGuessScoreLegal(s))
			{
				continue;
			}
			
			guessnumber++;
			if(GuessUtils.isGuessScoreCorrect(s, vsTeams.get(i).getTeama_goals()+vsTeams.get(i).getTeamb_goals()))
			{
				bingo++;
			}
		}
		if((guessnumber /  (double)(vsTeams.size()-size))>0.08)
			ds.add(new Eva((bingo/guessnumber), "GuessScoreVSTeamWeight.firstdoor="+GuessScoreVSTeamWeight.firstdoor +" | bingo " + bingo +" / guessnumber " + guessnumber + " eva: " + (vsTeams.size()-size)));
	}
	
	public static void evaluatorVsTeamScoresWeight()
	{
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		
		Guess4TeamScores1 guess = new GuessScoreVSTeamWeight();
		
		System.out.println(vsTeams.size());
		int size = (int) (vsTeams.size() * predataP);
		
		double bingo = 0;
		double guessnumber = 0;
		double totalguessnumber = 0;
		for(int i=size;i<vsTeams.size();i++)
		{
			List<VSTeam> temp = vsTeams.subList(0, i);
			Set<Integer> s = guess.guess4teamScores(temp, vsTeams.get(i), false);
//			System.out.println(vsTeams.get(i).getVs()[0]+ " : "+s);
			if(!GuessUtils.isGuessScoreLegal(s))
			{
				continue;
			}
			guessnumber++;
			totalguessnumber+=s.size();
			if(GuessUtils.isGuessScoreCorrect(s, vsTeams.get(i).getTeama_goals()+vsTeams.get(i).getTeamb_goals()))
			{
				bingo++;
			}
		}
		System.out.println("总共猜了球数: "+totalguessnumber+" 效率: "+ bingo / totalguessnumber);
		System.out.println("中: "+bingo);
		System.out.println("猜了: "+guessnumber+" 平均每场猜了: "+totalguessnumber/guessnumber);
		System.out.println("一共: "+ (vsTeams.size() - size));
		System.out.println("evaluatorVsTeamScores: " + (bingo/guessnumber) + " <-  correct%");
	}
	
	public static double evaluatorGuessNiceMatchResult()
	{
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		
		Guess4TeamMatchResult3 guessc = new GuessNine();
		
		int size = (int) (vsTeams.size() * predataP);
		
		double bingo = 0;
		double guessnumber = 0;
		for(int i=size;i<vsTeams.size();i++)
		{
			int gc = guessc.guess4teamMatchResult(vsTeams.subList(0,i),vsTeams.get(i));

			Set<Integer> h = new HashSet<Integer>();h.add(-1);h.add(-2);h.add(-3);h.add(-4);h.add(-5);
			if(h.contains(gc))
			{
				continue;
			}
			if(vsTeams.get(i).getMatch_Result()==1) continue;
			if(gc==5) continue;
			guessnumber++;
			if(ChainUtils.isGuessCorrect(gc, vsTeams.get(i).getMatch_Result()))
			{
				bingo++;
			}
		}
		
		System.out.println(bingo +" / " + guessnumber + " eva: " + (vsTeams.size()-size));
		System.out.println("guess nine : " + (bingo/guessnumber) + " <-  correct%");

		return (bingo/guessnumber);
	}
	
	public static double evaluatorGuessCardsCircleMatchResult()
	{
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		
		Guess4TeamMatchResult3 guessc = new GuessCardsCircleMatchResult();
		
		int size = (int) (vsTeams.size() * predataP);
		
		double bingo = 0;
		double guessnumber = 0;
		for(int i=size;i<vsTeams.size();i++)
		{
			int gc = guessc.guess4teamMatchResult(vsTeams.subList(0,i),vsTeams.get(i));

			Set<Integer> h = new HashSet<Integer>();h.add(-1);h.add(-2);h.add(-3);h.add(-4);h.add(-5);
			if(h.contains(gc)) 
			{
				continue;
			}
			if(vsTeams.get(i).getMatch_Result()==1) continue;
			if(gc==1) continue;
			
			guessnumber++;
			if(GuessUtils.isGuessCorrect(gc, vsTeams.get(i).getMatch_Result()))
			{
				bingo++;
			}
		}
		
		System.out.println(bingo +" / " + guessnumber + " eva: " + (vsTeams.size()-size));
		System.out.println("guess eight : " + (bingo/guessnumber) + " <-  correct%");

		return (bingo/guessnumber);
	}
	
	private static void findbestGuessCardsCircleMatchResult() 
	{
		
		List<Eva> ds = new ArrayList<Eva>();
		
		AnalyzeCardPoint.radius = 0;
		
		while(AnalyzeCardPoint.radius < 2)
		{
			System.out.println("AnalyzeCardPoint.radius="+AnalyzeCardPoint.radius);
			
			evaluatorGuessCardsCircleMatchResult(ds);
			
			AnalyzeCardPoint.radius += 0.01;
			Collections.sort(ds);
			ds = StrategyUtils.subList(ds, 0, 108);
		}
		
		Collections.sort(ds);
		StrategyUtils.printFirstAnyItem(ds,256);
	}
	
	public static double evaluatorGuessCardsCircleMatchResult(List<Eva> ds)
	{
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		
		Guess4TeamMatchResult3 guessc = new GuessCardsCircleMatchResult();
		
		int size = (int) (vsTeams.size() * predataP);
		
		double bingo = 0;
		double guessnumber = 0;
		for(int i=size;i<vsTeams.size();i++)
		{
			int gc = guessc.guess4teamMatchResult(vsTeams.subList(0,i),vsTeams.get(i));

			Set<Integer> h = new HashSet<Integer>();h.add(-1);h.add(-2);h.add(-3);h.add(-4);h.add(-5);
			if(h.contains(gc)) 
			{
				continue;
			}
			if(vsTeams.get(i).getMatch_Result()==1) continue;
			if(gc==1) continue;
			
			guessnumber++;
			if(GuessUtils.isGuessCorrect(gc, vsTeams.get(i).getMatch_Result()))
			{
				bingo++;
			}
		}
		
		if((guessnumber /  (double)(vsTeams.size()-size))>0.03)
			ds.add(new Eva((bingo/guessnumber), "AnalyzeCardPoint.radius="+AnalyzeCardPoint.radius+" | bingo " + bingo +" / guessnumber " + guessnumber + " eva: " + (vsTeams.size()-size)));
		
		System.out.println(bingo +" / " + guessnumber + " eva: " + (vsTeams.size()-size));
		System.out.println("GuessCardsCircleMatchResult : " + (bingo/guessnumber) + " <-  correct%");

		return (bingo/guessnumber);
	}
	
	public static double evaluatorGuessEight()
	{
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		
		Guess4TeamMatchResult3 guess8 = new GuessEight();
		
		int size = (int) (vsTeams.size() * predataP);
		
		double bingo = 0;
		double guessnumber = 0;
		for(int i=size;i<vsTeams.size();i++)
		{
			int g8 = guess8.guess4teamMatchResult(vsTeams.subList(0,i),vsTeams.get(i));

			Set<Integer> h = new HashSet<Integer>();h.add(-1);h.add(-2);h.add(-3);h.add(-4);h.add(-5);
			if(h.contains(g8)) 
			{
				continue;
			}
			guessnumber++;
			if(GuessUtils.isGuessCorrect(g8, vsTeams.get(i).getMatch_Result()))
			{
				bingo++;
			}
		}
		System.out.println(bingo +" / " + guessnumber + " eva: " + (vsTeams.size()-size));
		System.out.println("guess eight : " + (bingo/guessnumber) + " <-  correct%");

		return (bingo/guessnumber);
	}
	
	public static double evaluatorGuessSeven()
	{
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		
		Guess4TeamMatchResult3 guess7 = new GuessSeven();
		
		int size = (int) (vsTeams.size() * predataP);
		
		double bingo = 0;
		double guessnumber = 0;
		for(int i=size;i<vsTeams.size();i++)
		{
			int g7 = guess7.guess4teamMatchResult(vsTeams.subList(0,i),vsTeams.get(i));

			Set<Integer> h = new HashSet<Integer>();h.add(-1);h.add(-2);h.add(-3);h.add(-4);h.add(-5);
			if(h.contains(g7)) 
			{
				continue;
			}
			guessnumber++;
			if(GuessUtils.isGuessCorrect(g7, vsTeams.get(i).getMatch_Result()))
			{
				bingo++;
			}
		}
		System.out.println(bingo +" / " + guessnumber + " eva: " + (vsTeams.size()-size));
		System.out.println("guess seven : " + (bingo/guessnumber) + " <-  correct%");

		return (bingo/guessnumber);
	}
	
	public static double evaluatorGuessSix()
	{
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		
		Guess4TeamMatchResult3 guess6 = new GuessSix();
		
		int size = (int) (vsTeams.size() * predataP);
		
		double bingo = 0;
		double guessnumber = 0;
		for(int i=size;i<vsTeams.size();i++)
		{
			int g6 = guess6.guess4teamMatchResult(vsTeams.subList(0,i),vsTeams.get(i));

			Set<Integer> h = new HashSet<Integer>();h.add(-1);h.add(-2);h.add(-3);h.add(-4);h.add(-5);
			if(h.contains(g6)) 
			{
				continue;
			}
			guessnumber++;
			if(GuessUtils.isGuessCorrect(g6, vsTeams.get(i).getMatch_Result()))
			{
				bingo++;
			}
		}
		System.out.println(bingo +" / " + guessnumber + " eva: " + (vsTeams.size()-size));
		System.out.println("guess six : " + (bingo/guessnumber) + " <-  correct%");
		
		return (bingo/guessnumber);
	}
	
	private static void findbestGuessSix() {
		
		List<Eva> ds = new ArrayList<Eva>();
		
		GuessSix.windoor =0d;
		GuessSix.losedoor =0d;
		GuessSix.drawdoor =0d;
		
		while(GuessSix.drawdoor < 3)
		{
			System.out.println("GuessSix.drawdoor="+GuessSix.drawdoor);
			
			evaluatorGuessSix(ds);
			
			GuessSix.drawdoor += 0.01;
			Collections.sort(ds);
			ds = StrategyUtils.subList(ds, 0, 108);
		}
		
		Collections.sort(ds);
		StrategyUtils.printFirstAnyItem(ds,256);
	}
	
	public static double evaluatorGuessSix(List<Eva> ds)
	{
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		
		Guess4TeamMatchResult3 guess6 = new GuessSix();
		
		int size = (int) (vsTeams.size() * predataP);
		
		double bingo = 0;
		double guessnumber = 0;
		for(int i=size;i<vsTeams.size();i++)
		{
			int g6 = guess6.guess4teamMatchResult(vsTeams.subList(0,i),vsTeams.get(i));

			Set<Integer> h = new HashSet<Integer>();h.add(-1);h.add(-2);h.add(-3);h.add(-4);h.add(-5);
			if(h.contains(g6)) 
			{
				continue;
			}
			guessnumber++;
			if(GuessUtils.isGuessCorrect(g6, vsTeams.get(i).getMatch_Result()))
			{
				bingo++;
			}
		}
		System.out.println(bingo +" / " + guessnumber + " eva: " + (vsTeams.size()-size));
		System.out.println("guess four : " + (bingo/guessnumber) + " <-  correct%");
		
		if((guessnumber /  (double)(vsTeams.size()-size))>0.03)
			ds.add(new Eva((bingo/guessnumber), "GuessSix.drawdoor="+GuessSix.drawdoor+" | bingo " + bingo +" / guessnumber " + guessnumber + " eva: " + (vsTeams.size()-size)));
		
		return (bingo/guessnumber);
	}
	
	private static void findbestVSTeamScores() {
		
		List<Eva> ds = new ArrayList<Eva>();
		
		GuessScoreLeagueProbability.firstdoor = 0d;
		
		while(GuessScoreLeagueProbability.firstdoor < 1)
		{
			GuessScoreVSTeamProbability.firstdoor = 0d;
			System.out.println("GuessScoreLeagueProbability.firstdoor="+GuessScoreLeagueProbability.firstdoor+"GuessScoreVSTeamProbability.firstdoor="+GuessScoreVSTeamProbability.firstdoor);
			while(GuessScoreVSTeamProbability.firstdoor < 1)
			{
				evaluatorVsTeamScores(ds);
				GuessScoreVSTeamProbability.firstdoor += 0.01;
			}
			GuessScoreLeagueProbability.firstdoor += 0.01;
			Collections.sort(ds);
			ds = StrategyUtils.subList(ds, 0, 108);
		}
		
		Collections.sort(ds);
		StrategyUtils.printFirstAnyItem(ds,256);
	}

	public static void findbestGuessFour()
	{
		
		List<Eva> ds = new ArrayList<Eva>();
		
		GuessThree.door = 0;
		//0.3d
		while(GuessThree.door < 5)
		{
			GuessTwo.door = 0;
			System.out.println("GuessTwo="+GuessTwo.door+"GuessThree="+GuessThree.door);
			while(GuessTwo.door < 5)
			{
				evaluatorGuessFour(ds);
				GuessTwo.door += 0.1;
			}
			GuessThree.door += 0.1;
			Collections.sort(ds);
			ds = StrategyUtils.subList(ds, 0, 108);
		}
		
		Collections.sort(ds);
		StrategyUtils.printFirstAnyItem(ds,256);
	}
	
	private static void findbestGuessFiveOne() {
			
			List<Eva> ds = new ArrayList<Eva>();
			
			GuessFiveOne.scoredoor = 0d;
			
			while(GuessFiveOne.scoredoor < 5)
			{
				System.out.println("GuessFiveOne.scoredoor="+GuessFiveOne.scoredoor);
				evaluatorGuessFiveOne(ds);
				GuessFiveOne.scoredoor += 0.01;
				
				Collections.sort(ds);
				ds = StrategyUtils.subList(ds, 0, 108);
			}
			
			Collections.sort(ds);
			StrategyUtils.printFirstAnyItem(ds,256);
		}
	
	public static double evaluatorGuessFiveOne(List<Eva> ds)
	{
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		
		Guess4TeamMatchResult3 guess51 = new GuessFiveOne();
		
		int size = (int) (vsTeams.size() * predataP);
		
		double bingo = 0;
		double guessnumber = 0;
		for(int i=size;i<vsTeams.size();i++)
		{
			int g51 = guess51.guess4teamMatchResult(vsTeams.subList(0,size),vsTeams.get(i));
	
			Set<Integer> h = new HashSet<Integer>();h.add(-1);h.add(-2);h.add(-3);h.add(-4);h.add(-5);
			if(h.contains(g51)) 
			{
				continue;
			}
			guessnumber++;
			if(GuessUtils.isGuessCorrect(g51, vsTeams.get(i).getMatch_Result()))
			{
				bingo++;
			}
		}
	//	System.out.println(bingo +" / " + guessnumber + " eva: " + (vsTeams.size()-size));
	//	System.out.println("guess three : " + (bingo/guessnumber) + " <-  correct%");
		if((bingo/guessnumber)!=Double.NaN && (guessnumber /  (double)(vsTeams.size()-size))>0.08)
			ds.add(new Eva((bingo/guessnumber), "GuessFiveOne.scoredoor= "+ GuessFiveOne.scoredoor +" | bingo " + bingo +" / guessnumber " + guessnumber + " eva: " + (vsTeams.size()-size)));
		
		return (bingo/guessnumber);
	}
		
	private static void findbestGuessFiveTwo() {
			
			List<Eva> ds = new ArrayList<Eva>();
			
			GuessFiveOne.scoredoor = 0d;
			
			while(GuessFiveOne.scoredoor < 5)
			{
				System.out.println("GuessFiveTwo.scoredoor="+GuessFiveOne.scoredoor);
				evaluatorGuessFiveTwo(ds);
				GuessFiveOne.scoredoor += 0.01;
				
				Collections.sort(ds);
				ds = StrategyUtils.subList(ds, 0, 108);
			}
			
			Collections.sort(ds);
			StrategyUtils.printFirstAnyItem(ds,256);
		}
	
	public static double evaluatorGuessFiveTwo(List<Eva> ds)
	{
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		
		Guess4TeamMatchResult3 guess52 = new GuessFiveTwo();
		
		int size = (int) (vsTeams.size() * predataP);
		
		double bingo = 0;
		double guessnumber = 0;
		for(int i=size;i<vsTeams.size();i++)
		{
			int g52 = guess52.guess4teamMatchResult(vsTeams.subList(0,size),vsTeams.get(i));
	
			Set<Integer> h = new HashSet<Integer>();h.add(-1);h.add(-2);h.add(-3);h.add(-4);h.add(-5);
			if(h.contains(g52)) 
			{
				continue;
			}
			guessnumber++;
			if(GuessUtils.isGuessCorrect(g52, vsTeams.get(i).getMatch_Result()))
			{
				bingo++;
			}
		}
	//	System.out.println(bingo +" / " + guessnumber + " eva: " + (vsTeams.size()-size));
	//	System.out.println("guess three : " + (bingo/guessnumber) + " <-  correct%");
		if((bingo/guessnumber)!=Double.NaN && (guessnumber /  (double)(vsTeams.size()-size))>0.08)
			ds.add(new Eva((bingo/guessnumber), "GuessFiveTwo.scoredoor= "+ GuessFiveOne.scoredoor +" | bingo " + bingo +" / guessnumber " + guessnumber + " eva: " + (vsTeams.size()-size)));
		
		return (bingo/guessnumber);
	}
	
	public static String evaluatorGuessFour()
	{
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		
		Guess4TeamMatchResult3 guess4 = new GuessFour();
		
		int size = (int) (vsTeams.size() * predataP);
		
		double bingo = 0;
		double guessnumber = 0;
		for(int i=size;i<vsTeams.size();i++)
		{
			int g4 = guess4.guess4teamMatchResult(vsTeams.subList(0,i),vsTeams.get(i));

			Set<Integer> h = new HashSet<Integer>();h.add(-1);h.add(-2);h.add(-3);h.add(-4);h.add(-5);
			if(h.contains(g4)) 
			{
				continue;
			}
			guessnumber++;
			if(GuessUtils.isGuessCorrect(g4, vsTeams.get(i).getMatch_Result()))
			{
				bingo++;
			}
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(bingo +" / " + guessnumber + " eva: " + (vsTeams.size()-size)+"\n"+"guess two : " + (bingo/guessnumber) + " <-  correct%");
		System.out.println(sb);
		return sb.toString();
	}
	
	public static double evaluatorGuessFour(List<Eva> ds)
	{
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		
		Guess4TeamMatchResult3 guess4 = new GuessFour();
		
		int size = (int) (vsTeams.size() * predataP);
		
		double bingo = 0;
		double guessnumber = 0;
		for(int i=size;i<vsTeams.size();i++)
		{
			int g4 = guess4.guess4teamMatchResult(vsTeams.subList(0,i),vsTeams.get(i));

			Set<Integer> h = new HashSet<Integer>();h.add(-1);h.add(-2);h.add(-3);h.add(-4);h.add(-5);
			if(h.contains(g4)) 
			{
				continue;
			}
			guessnumber++;
			if(GuessUtils.isGuessCorrect(g4, vsTeams.get(i).getMatch_Result()))
			{
				bingo++;
			}
		}
		
		if((bingo/guessnumber)!=Double.NaN && (guessnumber /  (double)(vsTeams.size()-size))>0.08)
			ds.add(new Eva((bingo/guessnumber), "GuessTwo= "+GuessTwo.door+" GuessThree= "+GuessThree.door +" | bingo " + bingo +" / guessnumber " + guessnumber + " eva: " + (vsTeams.size()-size)));
		
		return (bingo/guessnumber);
	}
	
	public static double evaluatorGuessTwoThree()
	{
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		
		Guess4TeamMatchResult2 guess2 = new GuessTwo();
		Guess4TeamMatchResult3 guess3 = new GuessThree();
		
		int size = (int) (vsTeams.size() * predataP);
		
		double bingo = 0;
		double guessnumber = 0;
		for(int i=size;i<vsTeams.size();i++)
		{
			int g2 = guess2.guess4teamMatchResult(vsTeams.get(i)).getMatch_Result();
			int g3 = guess3.guess4teamMatchResult(vsTeams.subList(0,i),vsTeams.get(i));
			if(g2==g3)
			{
				Set<Integer> h = new HashSet<Integer>();h.add(-1);h.add(-2);h.add(-3);h.add(-4);
				if(h.contains(g2)) 
				{
					continue;
				}
				guessnumber++;
				if(GuessUtils.isGuessCorrect(g2, vsTeams.get(i).getMatch_Result()))
				{
					bingo++;
				}
			}
			else{
//				System.out.println(g2+" : "+g3);
			}
		}
		System.out.println(bingo +" / " + guessnumber + " eva: " + (vsTeams.size()-size));
		System.out.println("guess three : " + (bingo/guessnumber) + " <-  correct%");
		
		return (bingo/guessnumber);
	}
	
	public static String evaluatorGuessThree()
	{
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		
		Guess4TeamMatchResult3 guess = new GuessThree();
		
//		System.out.println(vsTeams.size());
		int size = (int) (vsTeams.size() * predataP);
		
		double bingo = 0;
		double guessnumber = 0;
		for(int i=size;i<vsTeams.size();i++)
		{
			int s = guess.guess4teamMatchResult(vsTeams.subList(0,i),vsTeams.get(i));
//			System.out.println(vsTeams.get(i).getVs()[0]+ " : "+s);
			Set<Integer> h = new HashSet<Integer>();h.add(-1);h.add(-2);h.add(-3);h.add(-4);
			if(h.contains(s)) 
			{
				continue;
			}
			guessnumber++;
			if(GuessUtils.isGuessCorrect(s, vsTeams.get(i).getMatch_Result()))
			{
				bingo++;
			}
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(bingo +" / " + guessnumber + " eva: " + (vsTeams.size()-size)+"\n"+"guess two : " + (bingo/guessnumber) + " <-  correct%");
		System.out.println(sb);
		return sb.toString();
	}
	
	@Deprecated
	public static void findbest4()
	{
		
		List<Double> ds = new ArrayList<Double>();
		GuessThree.door = 2;
		//0.3d
		while(GuessThree.door < 3)
		{
			System.out.println(GuessThree.door);
//			ds.add(evaluatorGuessThree());
			GuessThree.door += 0.001;
		}
		
		Collections.sort(ds);
		StrategyUtils.printFirst24Item(ds);
	}
	
	
	public static void evaluatorVsTeamRefineScores()
	{
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		
		Guess4TeamScores1 guess = new GuessRefineScoreVSTeamProbability();
		
		System.out.println(vsTeams.size());
		int size = (int) (vsTeams.size() * predataP);
		
		double bingo = 0;
		double guessnumber = 0;
		for(int i=size;i<vsTeams.size();i++)
		{
			List<VSTeam> temp = vsTeams.subList(0, i);
			Set<Integer> s = guess.guess4teamScores(temp, vsTeams.get(i), false);
			System.out.println(vsTeams.get(i).getVs()[0]+ " : "+s);
			if(!GuessUtils.isGuessScoreLegal(s))
			{
				continue;
			}
			
			guessnumber++;
			if(GuessUtils.isGuessScoreCorrect(s, vsTeams.get(i).getTeama_goals()+vsTeams.get(i).getTeamb_goals()))
			{
				bingo++;
			}
		}
		System.out.println("中: "+bingo);
		System.out.println("猜了: "+guessnumber);
		System.out.println("一共: "+ (vsTeams.size() - size));
		System.out.println("evaluatorVsTeamScores: " + (bingo/guessnumber) + " <-  correct%");
	}
	
	public static void evaluatorVsTeamScores()
	{
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		
		Guess4TeamScores1 guess = new GuessScoreVSTeamProbability();
		
		System.out.println(vsTeams.size());
		int size = (int) (vsTeams.size() * predataP);
		
		double bingo = 0;
		double guessnumber = 0;
		double totalguessnumber = 0;
		for(int i=size;i<vsTeams.size();i++)
		{
			List<VSTeam> temp = vsTeams.subList(0, i);
			Set<Integer> s = guess.guess4teamScores(temp, vsTeams.get(i), false);
//			System.out.println(vsTeams.get(i).getVs()[0]+ " : "+s);
			if(!GuessUtils.isGuessScoreLegal(s))
			{
				continue;
			}
			
			guessnumber++;
			totalguessnumber+=s.size();
			if(GuessUtils.isGuessScoreCorrect(s, vsTeams.get(i).getTeama_goals()+vsTeams.get(i).getTeamb_goals()))
			{
				bingo++;
			}
		}
		System.out.println("总共猜了球数: "+totalguessnumber+" 效率: "+ bingo / totalguessnumber);
		System.out.println("中: "+bingo);
		System.out.println("猜了: "+guessnumber+" 平均每场猜了: "+totalguessnumber/guessnumber);
		System.out.println("一共: "+ (vsTeams.size() - size));
		System.out.println("evaluatorVsTeamScores: " + (bingo/guessnumber) + " <-  correct%");
	}
	
	public static void evaluatorVsTeamScores(List<Eva> ds)
	{
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		
		Guess4TeamScores1 guess = new GuessScoreVSTeamProbability();
		
//		System.out.println(vsTeams.size());
		int size = (int) (vsTeams.size() * predataP);
		
		double bingo = 0;
		double guessnumber = 0;
		for(int i=size;i<vsTeams.size();i++)
		{
			List<VSTeam> temp = vsTeams.subList(0, i);
			Set<Integer> s = guess.guess4teamScores(temp, vsTeams.get(i), false);
//			System.out.println(vsTeams.get(i).getVs()[0]+ " : "+s);
			if(!GuessUtils.isGuessScoreLegal(s))
			{
				continue;
			}
			
			guessnumber++;
			if(GuessUtils.isGuessScoreCorrect(s, vsTeams.get(i).getTeama_goals()+vsTeams.get(i).getTeamb_goals()))
			{
				bingo++;
			}
		}
		
		if((guessnumber /  (double)(vsTeams.size()-size))>0.08)
			ds.add(new Eva((bingo/guessnumber), "GuessScoreLeagueProbability.firstdoor="+GuessScoreLeagueProbability.firstdoor+"GuessScoreVSTeamProbability.firstdoor="+GuessScoreVSTeamProbability.firstdoor +" | bingo " + bingo +" / guessnumber " + guessnumber + " eva: " + (vsTeams.size()-size)));
	}
	
	public static String evaluatorGuessTwo()
	{
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		
		Guess4TeamMatchResult2 guess = new GuessTwo();
		
		System.out.println(vsTeams.size());
		int size = (int) (vsTeams.size() * predataP);
		
		double bingo = 0;
		double guessnumber = 0;
		for(int i=size;i<vsTeams.size();i++)
		{
			int s = guess.guess4teamMatchResult(vsTeams.get(i)).getMatch_Result();
//			System.out.println(vsTeams.get(i).getVs()[0]+ " : "+s);
			Set<Integer> h = new HashSet<Integer>();h.add(-1);h.add(-2);h.add(-3);
			if(h.contains(s)) 
			{
				continue;
			}
			guessnumber++;
			if(GuessUtils.isGuessCorrect(s, vsTeams.get(i).getMatch_Result()))
			{
				bingo++;
			}
		}
		StringBuilder sb = new StringBuilder();
		sb.append(bingo +" / " + guessnumber + " eva: " + (vsTeams.size()-size)+"\n"+"guess two : " + (bingo/guessnumber) + " <-  correct%");
		System.out.println(sb);
		return sb.toString();
	}
	
	public static void evalutorLeagueScores()
	{
	List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		
//		Guess4TeamResult1 guess = new GuessOne();
//		Guess4TeamScores1 guess = new GuessScoreOne();
		Guess4TeamScores1 guess = new GuessScoreLeagueProbability();
		
		System.out.println(vsTeams.size());
		int size = (int) (vsTeams.size() * predataP);
		
		double bingo = 0;
		double guessnumber = 0;
		double totalguessnumber = 0;
		for(int i=size;i<vsTeams.size();i++)
		{
			List<VSTeam> temp = vsTeams.subList(0, i);
			Set<Integer> s = guess.guess4teamScores(temp, vsTeams.get(i), false);
//			System.out.println(vsTeams.get(i).getVs()[0]+ " : "+s);
			if(!GuessUtils.isGuessScoreLegal(s))
			{
				continue;
			}

			guessnumber++;
			totalguessnumber+=s.size();
			if(GuessUtils.isGuessScoreCorrect(s, vsTeams.get(i).getTeama_goals()+vsTeams.get(i).getTeamb_goals()))
			{
				bingo++;
			}
		}
		System.out.println("总共猜了球数: "+totalguessnumber+" 效率: "+ bingo / totalguessnumber);
		System.out.println("中: "+bingo);
		System.out.println("猜了: "+guessnumber+" 平均每场猜了: "+totalguessnumber/guessnumber);
		System.out.println((bingo/guessnumber) + " <-  correct%");
//		System.out.println(AnalyzeVSTeamsCorrectPercent.analyzeVSTeamsHostCorrectPercent(vsTeams.subList(size, vsTeams.size())) + " times: " + vsTeams.subList(size, vsTeams.size()).size());
//		System.out.println(AnalyzeVSTeamsCorrectPercent.analyzeVSTeamsPeopleVoteCorrectPercent(vsTeams.subList(size, vsTeams.size())) + " times: " + vsTeams.subList(size, vsTeams.size()).size());
	}
	
	public static void evalutor2()
	{
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		
//		Guess4TeamResult1 guess = new GuessOne();
		Guess4TeamScores1 guess = new GuessScoreOne();
		
		System.out.println(vsTeams.size());
		int size = (int) (vsTeams.size() * predataP);
		
		double bingo = 0;
		double guessnumber = 0;
		for(int i=size;i<vsTeams.size();i++)
		{
			List<VSTeam> temp = vsTeams.subList(0, i);
			Set<Integer> s = guess.guess4teamScores(temp, vsTeams.get(i), false);
			System.out.println(vsTeams.get(i).getVs()[0]+ " : "+s);
			Set<Integer> h = new HashSet<Integer>();h.add(-1);h.add(-2);h.add(-3);
			if(h.containsAll(s)) 
			{
				continue;
			}
			guessnumber++;
			if(GuessUtils.isGuessScoreCorrect(s, vsTeams.get(i).getTeama_goals()+vsTeams.get(i).getTeamb_goals()))
			{
				bingo++;
			}
			
		}
		System.out.println(bingo);
		System.out.println((bingo/guessnumber) + " <-  correct%");
//		System.out.println(AnalyzeVSTeamsCorrectPercent.analyzeVSTeamsHostCorrectPercent(vsTeams.subList(size, vsTeams.size())) + " times: " + vsTeams.subList(size, vsTeams.size()).size());
//		System.out.println(AnalyzeVSTeamsCorrectPercent.analyzeVSTeamsPeopleVoteCorrectPercent(vsTeams.subList(size, vsTeams.size())) + " times: " + vsTeams.subList(size, vsTeams.size()).size());
	}
	
	public static void evaluator1()
	{
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		
		Guess4TeamMatchResult1 guess = new GuessOne();
		
		System.out.println(vsTeams.size());
		int size = (int) (vsTeams.size() * predataP);
		
		double bingo = 0;
		
		for(int i=size;i<vsTeams.size();i++)
		{
			if(GuessUtils.isGuessCorrect(guess.guess4teamMatchResult(vsTeams.get(i)),vsTeams.get(i).getMatch_Result())) bingo++;
		}
		System.out.println(bingo);
		System.out.println(bingo/(double)(vsTeams.size()-size) + " times: " + (vsTeams.size()-size));
		System.out.println(AnalyzeVSTeamsCorrectPercent.analyzeVSTeamsHostCorrectPercent(vsTeams.subList(size, vsTeams.size())) + " times: " + vsTeams.subList(size, vsTeams.size()).size());
		System.out.println(AnalyzeVSTeamsCorrectPercent.analyzeVSTeamsPeopleVoteCorrectPercent(vsTeams.subList(size, vsTeams.size())) + " times: " + vsTeams.subList(size, vsTeams.size()).size());
	}
}

class Eva implements Comparable<Eva>
{
	protected double value;
	protected String others;
	
	public Eva(double value, String others)
	{
		this.value = value;
		this.others = others;
	}

	@Override
	public int compareTo(Eva o) {
		if(this.value > o.value) return -1;
		else if(this.value < o.value) return 1;
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((others == null) ? 0 : others.hashCode());
		long temp;
		temp = Double.doubleToLongBits(value);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Eva other = (Eva) obj;
		if (others == null) {
			if (other.others != null)
				return false;
		} else if (!others.equals(other.others))
			return false;
		if (Double.doubleToLongBits(value) != Double
				.doubleToLongBits(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Eva [value=" + value + ", others=" + others + "]";
	}
	
}
