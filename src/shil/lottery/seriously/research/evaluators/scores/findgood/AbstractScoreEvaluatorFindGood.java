package shil.lottery.seriously.research.evaluators.scores.findgood;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.math3.stat.Frequency;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import shil.lottery.seriously.research.evaluators.r013.Abstract013Evaluator;
import shil.lottery.seriously.utils.AbstractLineChart;
import shil.lottery.seriously.utils.EvaluatorRecorder;
import shil.lottery.seriously.utils.WriteFileUtil;
import shil.lottery.sport.db.SportMetaDaoImpl;
import shil.lottery.sport.entity.VSTeam;

public abstract class AbstractScoreEvaluatorFindGood{

	public static boolean output2file = true;
	
	public class PredictResultAnalyze{
		private Frequency bingoFrequency;
		
		public PredictResultAnalyze(){
			this.bingoFrequency = new Frequency();
		}
		
		public void merge(PredictResultAnalyze predictResultAnalyze){
			this.bingoFrequency.merge(predictResultAnalyze.bingoFrequency);
		}

		public Frequency getBingoFrequency() {
			return bingoFrequency;
		}

		@Override
		public String toString() {
			return "PredictResultAnalyze [bingoFrequency=\n" + bingoFrequency
					+ "]";
		}
	}
	
	class PartOfEvaluatorTask implements Callable<PredictResultAnalyze>{
		private List<VSTeam> vsTeams;
		private VSTeam vsTeam;
		private double pctLimit;
		
		public PartOfEvaluatorTask(List<VSTeam> vsTeams,VSTeam vsTeam,double pctLimit) {
			this.vsTeams = vsTeams;
			this.vsTeam = vsTeam;
			this.pctLimit = pctLimit;
		}

		@Override
		public PredictResultAnalyze call() throws Exception {
			PredictResultAnalyze predictResultAnalyze = new PredictResultAnalyze();
			Set<Integer> result = guessScores(vsTeams, vsTeam, pctLimit);
			if(!result.isEmpty()){
				//have result
				int totalScores = vsTeam.getTeama_goals() + vsTeam.getTeamb_goals();
				if(result.contains(totalScores)){
					predictResultAnalyze.getBingoFrequency().addValue(Abstract013Evaluator.Bingo);
				}else{
					predictResultAnalyze.getBingoFrequency().addValue(Abstract013Evaluator.NotBingo);
				}
				if(output2file) EvaluatorRecorder.getEvaluatorRecorder().putResult(vsTeam, result+" -> "+totalScores);
			}else{
				//no result
			}
			
			return predictResultAnalyze;
		}
	}
	
	public PredictResultAnalyze startEvaluator(double pctLimit){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMMMM-dd_HH.mm.ss");
		if(output2file) EvaluatorRecorder.getEvaluatorRecorder().setName("scores." + this.getClass().getSimpleName()+"@"+sdf.format(Calendar.getInstance().getTime()));
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		PredictResultAnalyze resultRecords = new PredictResultAnalyze();
		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		CompletionService<PredictResultAnalyze> completionService = new ExecutorCompletionService<PredictResultAnalyze>(executorService);
		
		int totals = vsTeams.size();
		//分发任务
		for(int i=1;i<totals;i++){
			VSTeam vsTeam = vsTeams.get(i);
//			System.out.println("makes:"+i);
			Callable<PredictResultAnalyze> partOfTask = new PartOfEvaluatorTask(vsTeams.subList(0, i-1), vsTeam,pctLimit);
			completionService.submit(partOfTask);
		}
		
		// 收集结果合并,一般存放Bingo,NotBingo
		final List<Long> xaix = new ArrayList<Long>();
		final List<Double> yaix = new ArrayList<Double>();
		for (int i = 1; i < totals; i++) {
			PredictResultAnalyze predictResultAnalyze = new PredictResultAnalyze();
			try {
				predictResultAnalyze = completionService.take().get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			resultRecords.merge(predictResultAnalyze);
			if (predictResultAnalyze.getBingoFrequency().getSumFreq() > 0) {
				xaix.add(resultRecords.getBingoFrequency().getSumFreq());
				yaix.add(resultRecords.getBingoFrequency().getPct(Abstract013Evaluator.Bingo) * 100);
			}
//			System.out.println(i + " is done");
//			System.out.println(resultRecords);

			executorService.shutdown();
		}

		/*
		new AbstractLineChart(this.getClass().getName(), "總體預測次數", "預測正確%") {
			@Override
			public CategoryDataset getXYDataset() {
				DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();
				for (int i = 0; i < xaix.size(); i++)
					defaultCategoryDataset.addValue(yaix.get(i), "Tea Time",
							xaix.get(i));
				return defaultCategoryDataset;
			}
		};
		*/
		
//		System.out.println("\nall done,this is result:");
//		System.out.print(resultRecords);
		
		if(output2file) WriteFileUtil.writeEvaluatorRecorder2File("history/score",EvaluatorRecorder.getEvaluatorRecorder());
		
		System.out.println("done is " +pctLimit + resultRecords);
		
		return resultRecords;
	}
	
	
	public abstract Set<Integer> guessScores(List<VSTeam> vsTeams,VSTeam vsTeam,double pctLimit);
}
