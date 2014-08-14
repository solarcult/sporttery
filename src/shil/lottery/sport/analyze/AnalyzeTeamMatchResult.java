package shil.lottery.sport.analyze;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shil.lottery.sport.domain.VSTeam;
import shil.lottery.sport.guess.GuessUtils;

public class AnalyzeTeamMatchResult {

	public static Map<String, TeamMatchResult> analyzeTeamMatchResult(List<VSTeam> vsTeams)
	{
		Map<String, TeamMatchResult> tmrs = new HashMap<String, TeamMatchResult>();
		
		for(VSTeam v : vsTeams)
		{
			String teama = v.getVs()[0];
			TeamMatchResult tmra = tmrs.get(teama);
			if(tmra == null)
			{
				tmra = new TeamMatchResult(teama);
				tmrs.put(tmra.getTeam_Name(), tmra);
			}
			tmra.increaseMatchResult(v.getMatch_Result());
			
			String teamb = v.getVs()[1];
			TeamMatchResult tmrb = tmrs.get(teamb);
			if(tmrb == null)
			{
				tmrb = new TeamMatchResult(teamb);
				tmrs.put(tmrb.getTeam_Name(), tmrb);
			}
			tmrb.increaseMatchResult(GuessUtils.reverseMatch_Result(v.getMatch_Result()));
		}
		
		return tmrs;
	}
}
