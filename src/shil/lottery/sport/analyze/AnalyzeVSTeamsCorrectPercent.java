package shil.lottery.sport.analyze;

import java.util.List;

import shil.lottery.sport.entity.VSTeam;

public class AnalyzeVSTeamsCorrectPercent {
	
	public static double analyzeVSTeamsHostCorrectPercent(List<VSTeam> vsTeams)
	{
		double bingo = 0;
		
		for(VSTeam vs : vsTeams)
		{
			if(vs.getMatch_Result() == 3)
			{
				//当胜的赔率最小则代表庄家预测主队会赢,并且<=包含了当胜平,胜负赔率相等时,算做胜赢.
				if((vs.getPeilv()[0] <= vs.getPeilv()[1]) && (vs.getPeilv()[0] <= vs.getPeilv()[2]))
				{
					bingo++;
				}
			}
			else if(vs.getMatch_Result() == 1)
			{
				if((vs.getPeilv()[1] <= vs.getPeilv()[0]) && (vs.getPeilv()[1] <= vs.getPeilv()[2]))
				{
					bingo++;
				}
			}
			else if(vs.getMatch_Result() == 0)
			{
				if((vs.getPeilv()[2] <= vs.getPeilv()[0]) && (vs.getPeilv()[2] <= vs.getPeilv()[1]))
				{
					bingo++;
				}
			}
		}
		
//		System.out.println(bingo/vsTeams.size());
//		System.out.println(bingo);
		return bingo/vsTeams.size();
	}

	public static double analyzeVSTeamsPeopleVoteCorrectPercent(List<VSTeam> vsTeams)
	{
		double bingo = 0;
		
		for(VSTeam vs : vsTeams)
		{
			if(vs.getMatch_Result() == 3)
			{
				//群众投票率高的算赢,并且>=包含了当胜平,胜负赔率相等时,算做胜赢.
				if((vs.getPeopleVote_num()[0] >= vs.getPeopleVote_num()[1]) && (vs.getPeopleVote_num()[0] >= vs.getPeopleVote_num()[2]))
				{
					bingo++;
				}
			}
			else if(vs.getMatch_Result() == 1)
			{
				if((vs.getPeopleVote_num()[1] >= vs.getPeopleVote_num()[0]) && (vs.getPeopleVote_num()[1] >= vs.getPeopleVote_num()[2]))
				{
					bingo++;
				}
			}
			else if(vs.getMatch_Result() == 0)
			{
				if((vs.getPeopleVote_num()[2] >= vs.getPeopleVote_num()[0]) && (vs.getPeopleVote_num()[2] >= vs.getPeopleVote_num()[1]))
				{
					bingo++;
				}
			}
		}
		
//		System.out.println(bingo/vsTeams.size());
//		System.out.println(bingo);
		return bingo/vsTeams.size();
	}
	
}
