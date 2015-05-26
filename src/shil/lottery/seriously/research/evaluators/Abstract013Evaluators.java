package shil.lottery.seriously.research.evaluators;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import shil.lottery.seriously.utils.AnalyzeUtil;
import shil.lottery.seriously.utils.EvaluatorRecorder;
import shil.lottery.seriously.utils.WriteFileUtil;
import shil.lottery.sport.db.SportMetaDaoImpl;
import shil.lottery.sport.entity.VSTeam;

/**
 * 用来估算准确率的类,外带画图功能,是我昨晚加上去的,看起来不错.
 * @author LiangJingJing
 * @date May 21, 2015 12:26:39 AM
 */
public abstract class Abstract013Evaluators implements Guess013{
	
	public static boolean output2file = true;
	
	public static String Bingo = "预测正确";
	public static String NotBingo = "预测错误";
	
	class PredictResultAnalyze{
		private Frequency bingoFrequency;
		private Frequency result013Frequency;
		
		public PredictResultAnalyze(){
			this.bingoFrequency = new Frequency();
			this.result013Frequency = new Frequency();
		}
		
		public void merge(PredictResultAnalyze predictResultAnalyze){
			this.bingoFrequency.merge(predictResultAnalyze.bingoFrequency);
			this.result013Frequency.merge(predictResultAnalyze.result013Frequency);
		}

		public Frequency getBingoFrequency() {
			return bingoFrequency;
		}

		public Frequency getResult013Frequency() {
			return result013Frequency;
		}

		@Override
		public String toString() {
			return "PredictResultAnalyze [bingoFrequency=\n" + bingoFrequency
					+ ", result013Frequency=\n" + result013Frequency + "]";
		}
	}
	
	class PartOfEvaluatorTask implements Callable<PredictResultAnalyze>{
		private List<VSTeam> vsTeams;
		private VSTeam vsTeam;
		
		public PartOfEvaluatorTask(List<VSTeam> vsTeams,VSTeam vsTeam) {
			this.vsTeams = vsTeams;
			this.vsTeam = vsTeam;
		}

		@Override
		public PredictResultAnalyze call() throws Exception {
			int result = guess013(vsTeams, vsTeam);
			if(output2file) EvaluatorRecorder.getEvaluatorRecorder().putResult(vsTeam, result+"->"+vsTeam.getMatch_Result());
			PredictResultAnalyze predictResultAnalyze = new PredictResultAnalyze();
			if(result>=0){
				if(vsTeam.getMatch_Result() == result){
					predictResultAnalyze.getBingoFrequency().addValue(Abstract013Evaluators.Bingo);
					predictResultAnalyze.getResult013Frequency().addValue(Abstract013Evaluators.Bingo+AnalyzeUtil.Connect+result);
				}else{
					predictResultAnalyze.getBingoFrequency().addValue(Abstract013Evaluators.NotBingo);
					predictResultAnalyze.getResult013Frequency().addValue(Abstract013Evaluators.NotBingo+AnalyzeUtil.Connect+result+"->"+vsTeam.getMatch_Result());
				}
			}else if(result == -1){
				//System.out.println("not enough informaiton.");
			}else if(result == -2){
				throw new RuntimeException("result has trouble , please check.");
			}
			return predictResultAnalyze;
		}
	}
	
	public void startEvaluator(boolean output){
		output2file = output;
		startEvaluator();
	}
	
	public void startEvaluator(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMMMM-dd_HH.mm.ss");
		if(output2file) EvaluatorRecorder.getEvaluatorRecorder().setName(this.getClass().getSimpleName()+"@"+sdf.format(Calendar.getInstance().getTime()));
		List<VSTeam> vsTeams = SportMetaDaoImpl.loadEveryVSTeamRecords();
		PredictResultAnalyze resultRecords = new PredictResultAnalyze();
		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		CompletionService<PredictResultAnalyze> completionService = new ExecutorCompletionService<PredictResultAnalyze>(executorService);
//		int totals = 1100; 
		int totals = vsTeams.size();
		//分发任务
		for(int i=1;i<totals;i++){
			VSTeam vsTeam = vsTeams.get(i);
			System.out.println("makes:"+i);
			Callable<PredictResultAnalyze> partOfTask = new PartOfEvaluatorTask(vsTeams.subList(0, i-1), vsTeam);
			completionService.submit(partOfTask);
		}
		
		//收集结果合并,一般存放Bingo,NotBingo
		final List<Long> xaix = new ArrayList<Long>();
		final List<Double> yaix = new ArrayList<Double>();
		for(int i=1;i<totals;i++){
			PredictResultAnalyze predictResultAnalyze = new PredictResultAnalyze();
			try {
				predictResultAnalyze = completionService.take().get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			resultRecords.merge(predictResultAnalyze);
			if(predictResultAnalyze.getBingoFrequency().getSumFreq()>0){
				xaix.add(resultRecords.getBingoFrequency().getSumFreq());
				yaix.add(resultRecords.getBingoFrequency().getPct(Abstract013Evaluators.Bingo) * 100);
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
		
		System.out.println("\nall done,this is result:");
		System.out.print(resultRecords);
		
		if(output2file) WriteFileUtil.writeEvaluatorRecorder2File(EvaluatorRecorder.getEvaluatorRecorder());
	}
}
