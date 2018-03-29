package test;

import java.io.File;

public class testParent {

	protected static String folder  = "C:\\Users\\frenz\\ownCloud\\RdmGsaNet_exp\\vf_seedProb_multiRDmPoint_01\\solitions\\maxStep_2000_generateNetNodeVectorFieldSplitSeedProb_02_generateNetEdgeInRadiusFather_02_prob_0.07_00\\" ;
	
	public static void main(String[] args) {

		System.out.println(folder );
		
		File file = new File ( folder) ; 
		
		String test = file.getParent();
		System.out.println(test);
	}

}
