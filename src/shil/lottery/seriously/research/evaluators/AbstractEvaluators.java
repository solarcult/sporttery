package shil.lottery.seriously.research.evaluators;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.math3.stat.Frequency;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import shil.lottery.seriously.research.Guess013;
import shil.lottery.seriously.utils.AbstractLineChart;
import shil.lottery.sport.db.SportMetaDaoImpl;
import shil.lottery.sport.entity.VSTeam;

public abstract class AbstractEvaluators implements Guess013{
	
	public static String Bingo = "预测正确";
	public static String NotBingo = "预测错误";
	
	class PartOfEvaluatorTask implements Callable<Frequency>{
		private List<VSTeam> vsTeams;
		private VSTeam vsTeam;
		
		public PartOfEvaluatorTask(List<VSTeam> vsTeams,VSTeam vsTeam) {
			this.vsTeams = vsTeams;
			this.vsTeam = vsTeam;
		}

		@Override
		public Frequency call() throws Exception {
			int result = guess013(vsTeams, vsTeam);
			Frequency frequency = new Frequency();
			if(result>=0){
				if(vsTeam.getMatch_Result() == result){
					frequency.addValue(AbstractEvaluators.Bingo);
				}else{
					frequency.addValue(AbstractEvaluators.NotBingo);
				}
			}else if(result == -1){
//				System.out.println("not enough informaiton.");
			}else if(result == -2){
				throw new RuntimeException("result has trouble , please check.");
			}
			return frequency;
		}
	}
	
	public void startEvaluator(){
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		Frequency resultRecords = new Frequency();
		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		CompletionService<Frequency> completionService = new ExecutorCompletionService<Frequency>(executorService);
		int totals = 1100; 
//		int totals = vsTeams.size();
		//分发任务
		for(int i=0;i<totals;i++){
			VSTeam vsTeam = vsTeams.get(i);
			System.out.println("makes:"+i);
			Callable<Frequency> partOfTask = new PartOfEvaluatorTask(vsTeams.subList(0, i), vsTeam);
			completionService.submit(partOfTask);
		}
		
		//收集结果合并,一般存放Bingo,NotBingo
		final List<Long> xaix = new ArrayList<Long>();
		final List<Double> yaix = new ArrayList<Double>();
		for(int i=0;i<totals;i++){
			Frequency frequency = null;
			try {
				frequency = completionService.take().get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			resultRecords.merge(frequency);
			if(frequency.getSumFreq()>0){
				xaix.add(resultRecords.getSumFreq());
				yaix.add(resultRecords.getPct(AbstractEvaluators.Bingo) * 100);
			}
			System.out.println(i + " is done");
			System.out.println(resultRecords);
			
			executorService.shutdown();
		}
		
		new AbstractLineChart(this.getClass().getName(),"總體預測次數","預測正確%") {
			
			@Override
			public CategoryDataset getXYDataset() {
				DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();
				for(int i=0;i<xaix.size();i++)
					defaultCategoryDataset.addValue(yaix.get(i), "Tea Time", xaix.get(i));
				
				return defaultCategoryDataset;
			}
		};
		
		System.out.println("all done,this is result:");
		System.out.print(resultRecords);
	}
}
