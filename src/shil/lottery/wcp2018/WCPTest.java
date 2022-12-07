package shil.lottery.wcp2018;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
/**
 * 
 * @author vanis
 * http://info.sporttery.cn/football/hhad_list.php
 *
 * skewness +在左边 -在右边
 *
 * 过了晋级赛,所有比重降低到0.6,同组晋级的队伍0.8
 */
public class WCPTest {

	public static double xiaozuFailRate = 0.6;
	public static double xiaozuTop2Rate = 0.8;
	public static double one8Rate = 1;
	public static double one4Rate = 1;
	public static double one2Rate = 1;


	public static double[] helanGoal = {2*xiaozuFailRate,1*xiaozuTop2Rate,2*xiaozuFailRate,3*one8Rate};
	public static double[] helanLose = {0,1*xiaozuTop2Rate,0,1*one8Rate};
	
	public static double[] meiguoGoal = {1,0,1};
	public static double[] meiguoLose = {1,0,0};
	
	public static void r49() {
		DescriptiveStatistics aG = new DescriptiveStatistics(helanGoal);
		DescriptiveStatistics aL = new DescriptiveStatistics(helanLose);
		
		DescriptiveStatistics bG = new DescriptiveStatistics(meiguoGoal);
		DescriptiveStatistics bL = new DescriptiveStatistics(meiguoLose);

		System.out.println("helan goal");
		System.out.println(aG);
		System.out.println("helan lose");
		System.out.println(aL);
		
		System.out.println("\n-----vs-----\n");

		System.out.println("meiguo goal");
		System.out.println(bG);
		System.out.println("meiguo lose");
		System.out.println(bL);
	}


	public static double[] agentingGoal = {1*xiaozuFailRate,2*xiaozuFailRate,2*xiaozuTop2Rate,2*one8Rate};
	public static double[] agentingLose = {2*xiaozuFailRate,0,0,1*one8Rate};

	public static double[] aodaliyaGoal = {1,1,1};
	public static double[] aodaliyaLose = {4,0,0};

	public static void r50() {
		DescriptiveStatistics aG = new DescriptiveStatistics(agentingGoal);
		DescriptiveStatistics aL = new DescriptiveStatistics(agentingLose);

		DescriptiveStatistics bG = new DescriptiveStatistics(aodaliyaGoal);
		DescriptiveStatistics bL = new DescriptiveStatistics(aodaliyaLose);

		System.out.println("agentingGoal");
		System.out.println(aG);
		System.out.println("agentingLose");
		System.out.println(aL);

		System.out.println("\n-----vs-----\n");

		System.out.println("aodaliyaGoal");
		System.out.println(bG);
		System.out.println("aodaliyaLose");
		System.out.println(bL);
	}

	public static double[] faguoGoal = {4*xiaozuTop2Rate,2*xiaozuFailRate,0,3*one8Rate};
	public static double[] faguoLose = {1*xiaozuTop2Rate,1*xiaozuFailRate,1*xiaozuFailRate,1*one8Rate};

	public static double[] bolanGoal = {0,2,0};
	public static double[] bolanLose = {0,0,2};

	public static void r51() {
		DescriptiveStatistics aG = new DescriptiveStatistics(faguoGoal);
		DescriptiveStatistics aL = new DescriptiveStatistics(faguoLose);

		DescriptiveStatistics bG = new DescriptiveStatistics(bolanGoal);
		DescriptiveStatistics bL = new DescriptiveStatistics(bolanLose);

		System.out.println("faguo goal");
		System.out.println(aG);
		System.out.println("faguo lose");
		System.out.println(aL);

		System.out.println("\n-----vs-----\n");

		System.out.println("bolan goal");
		System.out.println(bG);
		System.out.println("bolan lose");
		System.out.println(bL);
	}

	public static double[] yinggelanGoal = {6*xiaozuFailRate,0,3*xiaozuFailRate,3*one8Rate};
	public static double[] yinggelanLose = {2*xiaozuFailRate,0,0,0};

	public static double[] saineijiaerGoal = {0,3,2};
	public static double[] saineijiaerLose = {2,1,1};

	public static void r52() {
		DescriptiveStatistics aG = new DescriptiveStatistics(yinggelanGoal);
		DescriptiveStatistics aL = new DescriptiveStatistics(yinggelanLose);

		DescriptiveStatistics bG = new DescriptiveStatistics(saineijiaerGoal);
		DescriptiveStatistics bL = new DescriptiveStatistics(saineijiaerLose);

		System.out.println("yinggelan goal");
		System.out.println(aG);
		System.out.println("yinggelan lose");
		System.out.println(aL);

		System.out.println("\n-----vs-----\n");

		System.out.println("saineijiaer goal");
		System.out.println(bG);
		System.out.println("saineijiaer lose");
		System.out.println(bL);
	}

	public static double[] ribenGoal = {2*xiaozuFailRate,0,2*xiaozuTop2Rate};
	public static double[] ribenLose = {1*xiaozuFailRate,1*xiaozuFailRate,1*xiaozuTop2Rate};

	public static double[] keluodiyaGoal = {0,4*xiaozuFailRate,0,1*one8Rate};
	public static double[] keluodiyaLose = {0,1*xiaozuFailRate,0,1*one8Rate};

	public static void r53() {
		DescriptiveStatistics aG = new DescriptiveStatistics(ribenGoal);
		DescriptiveStatistics aL = new DescriptiveStatistics(ribenLose);

		DescriptiveStatistics bG = new DescriptiveStatistics(keluodiyaGoal);
		DescriptiveStatistics bL = new DescriptiveStatistics(keluodiyaLose);

		System.out.println("riben goal");
		System.out.println(aG);
		System.out.println("riben lose");
		System.out.println(aL);

		System.out.println("\n-----vs-----\n");

		System.out.println("keluodiya goal");
		System.out.println(bG);
		System.out.println("keluodiya lose");
		System.out.println(bL);
	}

	public static double[] baxiGoal = {2*xiaozuFailRate,1*xiaozuTop2Rate,0,4*one8Rate};
	public static double[] baxiLose = {0,0,1*xiaozuFailRate,1*one8Rate};

	public static double[] hanguoGoal = {0,2*xiaozuFailRate,2*xiaozuTop2Rate};
	public static double[] hanguoLose = {0,3*xiaozuFailRate,1*xiaozuTop2Rate};

	public static void r54() {
		DescriptiveStatistics aG = new DescriptiveStatistics(baxiGoal);
		DescriptiveStatistics aL = new DescriptiveStatistics(baxiLose);

		DescriptiveStatistics bG = new DescriptiveStatistics(hanguoGoal);
		DescriptiveStatistics bL = new DescriptiveStatistics(hanguoLose);

		System.out.println("baxi goal");
		System.out.println(aG);
		System.out.println("baxi lose");
		System.out.println(aL);

		System.out.println("\n-----vs-----\n");

		System.out.println("hanguo goal");
		System.out.println(bG);
		System.out.println("hanguo lose");
		System.out.println(bL);
	}

	public static double[] moluogeGoal = {0,2*xiaozuFailRate,2*xiaozuFailRate,0};
	public static double[] moluogeLose = {0,0,1*xiaozuFailRate,0};

	public static double[] xibanyaGoal = {7*xiaozuFailRate,1*xiaozuFailRate,1*xiaozuTop2Rate};
	public static double[] xibanyaLose = {0,1*xiaozuFailRate,2*xiaozuTop2Rate};

	public static void r55() {
		DescriptiveStatistics aG = new DescriptiveStatistics(moluogeGoal);
		DescriptiveStatistics aL = new DescriptiveStatistics(moluogeLose);

		DescriptiveStatistics bG = new DescriptiveStatistics(xibanyaGoal);
		DescriptiveStatistics bL = new DescriptiveStatistics(xibanyaLose);

		System.out.println("moluoge goal");
		System.out.println(aG);
		System.out.println("moluoge lose");
		System.out.println(aL);

		System.out.println("\n-----vs-----\n");

		System.out.println("xibanya goal");
		System.out.println(bG);
		System.out.println("xibanya lose");
		System.out.println(bL);
	}

	public static double[] putaoyaGoal = {3*xiaozuFailRate,2*xiaozuFailRate,1*xiaozuTop2Rate,6*one8Rate};
	public static double[] putaoyaLose = {2*xiaozuFailRate,0,2*xiaozuTop2Rate,1*one8Rate};

	public static double[] ruishiGoal = {1*xiaozuFailRate,0,3*xiaozuFailRate};
	public static double[] ruishiLose = {0,1*xiaozuTop2Rate,2*xiaozuFailRate};

	public static void r56() {
		DescriptiveStatistics aG = new DescriptiveStatistics(putaoyaGoal);
		DescriptiveStatistics aL = new DescriptiveStatistics(putaoyaLose);

		DescriptiveStatistics bG = new DescriptiveStatistics(ruishiGoal);
		DescriptiveStatistics bL = new DescriptiveStatistics(ruishiLose);

		System.out.println("putaoya goal");
		System.out.println(aG);
		System.out.println("putaoya lose");
		System.out.println(aL);

		System.out.println("\n-----vs-----\n");

		System.out.println("ruishi goal");
		System.out.println(bG);
		System.out.println("ruishi lose");
		System.out.println(bL);
	}

	public static void r57() {
		DescriptiveStatistics aG = new DescriptiveStatistics(baxiGoal);
		DescriptiveStatistics aL = new DescriptiveStatistics(baxiLose);

		DescriptiveStatistics bG = new DescriptiveStatistics(keluodiyaGoal);
		DescriptiveStatistics bL = new DescriptiveStatistics(keluodiyaLose);

		System.out.println("baxi goal");
		System.out.println(aG);
		System.out.println("baxi lose");
		System.out.println(aL);

		System.out.println("\n-----vs-----\n");

		System.out.println("keluodiya goal");
		System.out.println(bG);
		System.out.println("keluodiya lose");
		System.out.println(bL);
	}

	public static void r58() {
		DescriptiveStatistics aG = new DescriptiveStatistics(helanGoal);
		DescriptiveStatistics aL = new DescriptiveStatistics(helanLose);

		DescriptiveStatistics bG = new DescriptiveStatistics(agentingGoal);
		DescriptiveStatistics bL = new DescriptiveStatistics(agentingLose);

		System.out.println("helan goal");
		System.out.println(aG);
		System.out.println("helan lose");
		System.out.println(aL);

		System.out.println("\n-----vs-----\n");

		System.out.println("agenting goal");
		System.out.println(bG);
		System.out.println("agenting lose");
		System.out.println(bL);
	}

	public static void r59() {
		DescriptiveStatistics aG = new DescriptiveStatistics(putaoyaGoal);
		DescriptiveStatistics aL = new DescriptiveStatistics(putaoyaLose);

		DescriptiveStatistics bG = new DescriptiveStatistics(moluogeGoal);
		DescriptiveStatistics bL = new DescriptiveStatistics(moluogeLose);


		System.out.println("moluoge goal");
		System.out.println(bG);
		System.out.println("moluoge lose");
		System.out.println(bL);

		System.out.println("\n-----vs-----\n");

		System.out.println("putaoya goal");
		System.out.println(aG);
		System.out.println("putaoya lose");
		System.out.println(aL);

	}

	public static void r60() {
		DescriptiveStatistics aG = new DescriptiveStatistics(yinggelanGoal);
		DescriptiveStatistics aL = new DescriptiveStatistics(yinggelanLose);

		DescriptiveStatistics bG = new DescriptiveStatistics(faguoGoal);
		DescriptiveStatistics bL = new DescriptiveStatistics(faguoLose);

		System.out.println("yinggelan goal");
		System.out.println(aG);
		System.out.println("yinggelan lose");
		System.out.println(aL);

		System.out.println("\n-----vs-----\n");

		System.out.println("faguo goal");
		System.out.println(bG);
		System.out.println("faguo lose");
		System.out.println(bL);
	}

	public static void main(String[] args){

//		r49();
//		r50();
//		r51();
//		r52();
//		r53();
//		r54();
//		r55();
//		r56();
//		r57();
//		r58();
//		r59();
		r60();
	}
	
}
