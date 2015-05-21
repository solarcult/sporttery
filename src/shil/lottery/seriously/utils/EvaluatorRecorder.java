package shil.lottery.seriously.utils;

import java.util.concurrent.ConcurrentHashMap;

import shil.lottery.sport.entity.VSTeam;

public class EvaluatorRecorder {
	private static EvaluatorRecorder evaluatorRecorder = new EvaluatorRecorder();
	
	private String name;
	private ConcurrentHashMap<VSTeam, ContentResult> recorder;
	
	private EvaluatorRecorder(){
		this.recorder = new ConcurrentHashMap<VSTeam, ContentResult>();
	}
	
	public static EvaluatorRecorder getEvaluatorRecorder(){
		return evaluatorRecorder;
	}
	
	public void setName(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public ConcurrentHashMap<VSTeam, ContentResult> getRecorder() {
		return recorder;
	}
	
	public void putContent(VSTeam vsTeam,String content){
		ContentResult contentResult = evaluatorRecorder.recorder.get(vsTeam);
		if(contentResult==null){
			contentResult = new ContentResult();
			contentResult.setContent(content);
			evaluatorRecorder.recorder.put(vsTeam, contentResult);
		}else{
			contentResult.setContent(content);
		}
	}
	
	public void putResult(VSTeam vsTeam,String result){
		ContentResult contentResult = evaluatorRecorder.recorder.get(vsTeam);
		if(contentResult==null){
			contentResult = new ContentResult();
			contentResult.setResult(result);
			evaluatorRecorder.recorder.put(vsTeam, contentResult);
		}else{
			contentResult.setResult(result);
		}
	}
}
class ContentResult{
	private String content;
	private String result;
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "ContentResult [result=" + result + ", content=" + content + "]";
	}
}
