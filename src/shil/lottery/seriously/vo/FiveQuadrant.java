package shil.lottery.seriously.vo;

import org.apache.commons.math3.stat.Frequency;

public class FiveQuadrant {
	
	public static final String Quadrant1 = "1第一象限";
	public static final String Quadrant2 = "2第二象限";
	public static final String Quadrant3 = "3第三象限";
	public static final String Quadrant4 = "4第四象限";
	public static final String QuadrantONing = "5第十象限";
	
	private String name;
	
	private Frequency quadrant1;
	private Frequency quadrant2;
	private Frequency quadrant3;
	private Frequency quadrant4;
	private Frequency quadrantONing;
	
	public FiveQuadrant(String name){
		this.name = name;
	}

	public Frequency getQuadrant1() {
		return quadrant1;
	}

	public void setQuadrant1(Frequency quadrant1) {
		this.quadrant1 = quadrant1;
	}

	public Frequency getQuadrant2() {
		return quadrant2;
	}

	public void setQuadrant2(Frequency quadrant2) {
		this.quadrant2 = quadrant2;
	}

	public Frequency getQuadrant3() {
		return quadrant3;
	}

	public void setQuadrant3(Frequency quadrant3) {
		this.quadrant3 = quadrant3;
	}

	public Frequency getQuadrant4() {
		return quadrant4;
	}

	public void setQuadrant4(Frequency quadrant4) {
		this.quadrant4 = quadrant4;
	}

	public Frequency getQuadrantONing() {
		return quadrantONing;
	}

	public void setQuadrantONing(Frequency quadrantONing) {
		this.quadrantONing = quadrantONing;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "FiveQuadrant [name=" + name + ", quadrant1=" + quadrant1
				+ ", quadrant2=" + quadrant2 + ", quadrant3=" + quadrant3
				+ ", quadrant4=" + quadrant4 + ", quadrantONing="
				+ quadrantONing + "]";
	}
	
}
