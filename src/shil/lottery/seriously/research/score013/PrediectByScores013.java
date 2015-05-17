package shil.lottery.seriously.research.score013;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.math3.stat.Frequency;

import shil.lottery.seriously.utils.AnalyzeUtil;
import shil.lottery.seriously.vo.VSTeamScore013;
import shil.lottery.sport.db.SportMetaDaoImpl;
import shil.lottery.sport.entity.VSTeam;

/**
 * 进行预测类,根据xy所在象限和象限的输赢情况进行预测.
 * @author LiangJingJing
 * @date May 18, 2015 12:49:12 AM
 */
public class PrediectByScores013 {
	
	class PartOfPrediectByScores013Task implements Callable<Frequency>{
		private List<VSTeam> vsTeams;
		private VSTeam vsTeam;
		
		public PartOfPrediectByScores013Task(List<VSTeam> vsTeams,VSTeam vsTeam) {
			this.vsTeams = vsTeams;
			this.vsTeam = vsTeam;
		}

		@Override
		public Frequency call() throws Exception {
			System.out.println("start: "+vsTeams.size());
			Frequency frequency = new Frequency();
			Score013AnalyzeProbility score013AnalyzeProbility = Score013AnalyzeProbility.analyzeScore013AnalyzeProbility(vsTeams);
			if(!score013AnalyzeProbility.isSampleAvailable()) return frequency;
			VSTeamScore013 vsTeamScore013 = VSTeamScore013.calculateVSTeamScore013(vsTeams, vsTeam);
			if(!vsTeamScore013.isAvaliable()) return frequency;
			
			Score013XYCombineFrequency score013xyCombineFrequency = Score013XYCombineFrequency.buildScore013XYCombineFrequency(score013AnalyzeProbility, vsTeamScore013);
//			Score013XYCombineFrequency score013xyCombineFrequency = Score013XYCombineFrequency.buildScore013XYCombineFrequencyWithParentProb(score013AnalyzeProbility, vsTeamScore013);
			
			if(!score013xyCombineFrequency.isAvaliable()) return frequency;
			
			//预测与记录
			int result = score013xyCombineFrequency.predictMatchResult();
			if(result<0)
				throw new RuntimeException("result has trouble");
			
			if(vsTeam.getMatch_Result() == result){
				frequency.addValue(AnalyzeUtil.Bingo);
			}else{
				frequency.addValue(AnalyzeUtil.NotBingo);
			}
			return frequency;
		}
		
	}
	
	public void predict(){
		
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		Frequency resultRecords = new Frequency();
		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		CompletionService<Frequency> completionService = new ExecutorCompletionService<Frequency>(executorService);
		for(int i=0;i<vsTeams.size();i++){
			VSTeam vsTeam = vsTeams.get(i);
			System.out.println("make:"+i);
			PartOfPrediectByScores013Task partOfTask = new PartOfPrediectByScores013Task(vsTeams.subList(0, i), vsTeam);
			completionService.submit(partOfTask);
		}
		for(int i=0;i<vsTeams.size();i++){
			
			Frequency frequency = null;
			try {
				frequency = completionService.take().get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			 
			 resultRecords.merge(frequency);
			 System.out.println(i + " is done");
			 System.out.println(resultRecords);
		}
		
		System.out.println("all done,this is result:");
		System.out.print(resultRecords);
	}

	public static void main(String[] args){
		new PrediectByScores013().predict();
	}
	
}
