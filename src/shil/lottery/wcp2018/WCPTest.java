package shil.lottery.wcp2018;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
/**
 * 
 * @author vanis
 * http://info.sporttery.cn/football/hhad_list.php
 */
public class WCPTest {

	public static double[] russiaGoal = {5,3,0};
	public static double[] russiaLose = {0,1,3};
	
	public static double[] keluodiyaGoal = {2,3,2};
	public static double[] keluodiyaLose = {0,0,1};
	
	public static double[] danmaiGoal = {1,1,0};
	public static double[] danmaiLose = {0,1,0};
	
	public static void kd7202() {
		DescriptiveStatistics kldyG = new DescriptiveStatistics(keluodiyaGoal);
		DescriptiveStatistics kldyL = new DescriptiveStatistics(keluodiyaLose);
		
		DescriptiveStatistics dmG = new DescriptiveStatistics(danmaiGoal);
		DescriptiveStatistics dmL = new DescriptiveStatistics(danmaiLose);
		
		System.out.println(kldyG);
		System.out.println(kldyL);
		
		System.out.println("-----win-------");
		
		System.out.println(dmG);
		System.out.println(dmL);
	}
	
	public static double[] baxiGoal = {1,2,0};
	public static double[] baxiLose = {1,0,2};
	
	public static double[] moxigeGoal = {1,2,0};
	public static double[] moxigeLose = {0,1,3};
	
	
	public static void bm7222() {
		DescriptiveStatistics bxG = new DescriptiveStatistics(baxiGoal);
		DescriptiveStatistics bxL = new DescriptiveStatistics(baxiLose);
		
		DescriptiveStatistics mxgG = new DescriptiveStatistics(moxigeGoal);
		DescriptiveStatistics mxgL = new DescriptiveStatistics(moxigeLose);
		
		System.out.println(bxG);
		System.out.println(bxL);
		
		System.out.println("------win------");
		
		System.out.println(mxgG);
		System.out.println(mxgL);
	}
	
	public static double[] bilishiGoal = {3,5,1};
	public static double[] bilishiLose = {0,2,0};
	
	public static double[] ribenGoal = {2,2,0};
	public static double[] ribenLose = {1,2,1};
	
	
	public static void br7302() {
		DescriptiveStatistics blsG = new DescriptiveStatistics(bilishiGoal);
		DescriptiveStatistics blsL = new DescriptiveStatistics(bilishiLose);
		
		DescriptiveStatistics rbG = new DescriptiveStatistics(ribenGoal);
		DescriptiveStatistics rbL = new DescriptiveStatistics(ribenLose);
		
		System.out.println(blsG);
		System.out.println(blsL);
		
		System.out.println("-----win--2,3--2:1---");
		
		System.out.println(rbG);
		System.out.println(rbL);
	}
	
	public static double[] ruidianGoal = {1,1,3};
	public static double[] ruidianLose = {0,2,0};
	
	public static double[] ruishiGoal = {1,2,2};
	public static double[] ruishiLose = {1,1,2};
	
	public static void rr7322() {
		DescriptiveStatistics rdG = new DescriptiveStatistics(ruidianGoal);
		DescriptiveStatistics rdL = new DescriptiveStatistics(ruidianLose);
		
		DescriptiveStatistics rsG = new DescriptiveStatistics(ruishiGoal);
		DescriptiveStatistics rsL = new DescriptiveStatistics(ruishiLose);
		
		System.out.println(rdG);
		System.out.println(rdL);
		
		System.out.println("-----draw--1:2--2,3---");
		
		System.out.println(rsG);
		System.out.println(rsL);
	}
	
	public static double[] gelunbiyaGoal = {1,3,1};
	public static double[] gelunbiyaLose = {2,0,0};
	
	public static double[] yinggelanGoal = {2,6,0};
	public static double[] yinggelanLose = {1,1,1};
	
	public static void main(String[] args){

//		kd7202();
//		bm7222();
		br7302();
		rr7322();
		
	}
	
}
