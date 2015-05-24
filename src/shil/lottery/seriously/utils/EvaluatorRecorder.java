package shil.lottery.seriously.utils;

import java.util.ArrayList;
import java.util.List;
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
			contentResult.addContent(content);
			evaluatorRecorder.recorder.put(vsTeam, contentResult);
		}else{
			contentResult.addContent(content);
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
	private List<String> content;
	private String result;
	
	public ContentResult(){
		this.content = new ArrayList<String>();
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void addContent(String content){
		this.content.add(content);
	}

	public List<String> getContent() {
		return content;
	}

	@Override
	public String toString() {
		return "ContentResult [result=" + result + ", \ncontent=" + content + "]";
	}
	
	
}
