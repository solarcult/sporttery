package shil.lottery.seriously.league;

import java.util.ArrayList;

import weka.clusterers.SimpleKMeans;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

/**
 * 
 * @author LiangJingJing
 * @date Apr 22, 2015 10:52:59 PM
 */
public class WelcomeWeka {

	public static void main(String[] args) {
		Attribute numbers = new Attribute("numbers");
		ArrayList<Attribute> as = new ArrayList<Attribute>();
		as.add(numbers);
		Instances dataset = new Instances("classifer",as,0);
		double[] dd = new double[]{2,3,3,4,8,9,11,12,16,17,18,19,22,23,24,25};
		for(int i=0;i<dd.length;i++){
		
		double[] nums = new double[dataset.numAttributes()];
		
		nums[0]=dd[i];
		Instance inst = new DenseInstance(1,nums);
		System.out.print(" "+nums[0]);
		dataset.add(inst);
		}
		String[] options = new String[6];
		options[0] = "-N";
		options[1] = "4";
		options[2] = "-I";
		options[3] = "100";
		options[4] = "-init";
		options[5] = "1";
		
		SimpleKMeans sk = new SimpleKMeans();
		
		try {
			sk.setOptions(options);
			sk.buildClusterer(dataset);
			System.out.println(sk);
			System.out.println(sk.getCapabilities());
			for(int j =0; j<dataset.size();j++)
			System.out.println(dataset.get(j) +" : "+ sk.clusterInstance(dataset.get(j)));
			
//			Iterator i = sk.getCapabilities().capabilities();
//			while(i.hasNext()){
//				Capability c = (Capability) i.next();
//				System.out.println(c);
//			}
//			System.out.println(sk);
//			System.out.println(sk);
//			System.out.println(sk);
//			System.out.println(sk);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
