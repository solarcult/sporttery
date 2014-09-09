package shil.lottery.sport.chain;

import java.util.List;
import java.util.Map;
import java.util.Set;

import shil.lottery.sport.entity.MatchResult;
import shil.lottery.sport.entity.StatusCounter;
import shil.lottery.sport.entity.VSTeam;
import shil.lottery.sport.guess.Guess4TeamMatchResult3;
import shil.lottery.sport.guess.Guess4TeamScores1;
                                                                                                                                                
/**
 * 大体的想法是根据历史的两队的比赛信息,赢的总赢,输的总输,但操作下来看,历史数据太少,而且准确率不高
 * 下一步是根据比赛过的球队A->B,B->C来递推A->C的结果.这里遇到一个怎么定性A->B,B->C的结果的问题,预计GuessTen来解决
 * 有空重新审视一下预测球数的代码,关于2x2的那个部分everylist,感觉总体预测值偏大一些.need check check.
 * @author LiangJingJing
 * @since 20140909-12:19
 */
public class GuessNine implements Guess4TeamMatchResult3, Guess4TeamScores1 {

	@Override
	public Set<Integer> guess4teamScores(List<VSTeam> vsTeams,
			VSTeam predictMatch, boolean debug) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int guess4teamMatchResult(List<VSTeam> vsTeams, VSTeam predictMatch) {
		
		Map<String,List<OneTeamMatch>> teamsMatchsMap = ChainUtils.sortVSTeams2TeamsMap(vsTeams);
		
		List<OneTeamMatch> specialVSmatchs = ChainUtils.findHostvsGuestListDirect(teamsMatchsMap, predictMatch.getVs()[0], predictMatch.getVs()[1]);
		
		GuessNiceOneTeamMatchsDigest guessNiceOneTeamMatchsDigest = ChainUtils.analyzeOneTeamMatchs(specialVSmatchs);
		
		if(guessNiceOneTeamMatchsDigest.getT_m_nums()<1) return -3;
		
		MatchResult matchResult = new MatchResult(0);
		matchResult.setWinStatusCounter(new StatusCounter(OneTeamMatch.Result_win,(double)guessNiceOneTeamMatchsDigest.getC_win()/guessNiceOneTeamMatchsDigest.getT_m_nums()));
		matchResult.setDrawStatusCounter(new StatusCounter(OneTeamMatch.Result_draw,(double)guessNiceOneTeamMatchsDigest.getC_draw()/guessNiceOneTeamMatchsDigest.getT_m_nums()));
		matchResult.setLoseStatusCounter(new StatusCounter(OneTeamMatch.Result_lose,(double)guessNiceOneTeamMatchsDigest.getC_lose()/guessNiceOneTeamMatchsDigest.getT_m_nums()));
		
		int answer = ChainUtils.convertNormal2OneTeamMatchResult(matchResult.getMatch_Result());
//		if(answer>=0)
//		{
//			System.out.println(specialVSmatchs);
//			System.out.println(guessNiceOneTeamMatchsDigest);
//		}
		return answer;
	}

	
	public static void main(String[] args)
	{
		System.out.println((double)(4)/23);
	}
}
