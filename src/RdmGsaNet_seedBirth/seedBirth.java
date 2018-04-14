package RdmGsaNet_seedBirth;

import java.util.ArrayList;
import java.util.Map;

import org.graphstream.graph.*;


import RdmGsaNet_mainSim.layerNet;
import RdmGsaNet_mainSim.main;

public class seedBirth {

	// parameters
	protected static Graph 	netGraph = layerNet.getGraph() ,
							seedGraph = main.getSeedGraph();
	
	private static boolean runSeedBirth ;
	
	protected static double probSplit ; 
	protected static double percBirth ;

	protected double dist; 
	
	
	// type set Seed
	public enum setSeedType { throwSeed, onlySetSeed }
	protected setSeedType setSeedType ; 
	
	// type set seed
	public enum generateSeedType { probability , percentGraph }
	protected generateSeedType generateSeedType ;
	
	
	
	// interface object
	private seedBirt_inter sb_inter ; 
	
	// constructor
	public seedBirth( boolean runSeedBirth , setSeedType setSeedType , generateSeedType generateSeedType ) {
		
		this.runSeedBirth = runSeedBirth ; 
		this.setSeedType = setSeedType ;
		this.generateSeedType =  generateSeedType ;

		switch (setSeedType) {
			case throwSeed:
				sb_inter = new seedBirth_throwSeed(  ) ;
	 			break;
	 		
			case onlySetSeed :
				sb_inter = new seedBirth_onlySetSeed(  ) ;
				break;
		}
	}
	
	// set parameters 
	public void setParameters_throwSeed ( double probSplit , double dist  ) {
		this.probSplit = probSplit ;
		this.dist = dist ; 
	}
	
	public void setParameters_onlySetSeed( double percBirth ) {
		this.percBirth = percBirth ;
	}
	
	


	public void compute ( ) {
		
		if ( ! runSeedBirth )
			return ;
		
		ArrayList<String> listIdToSplit = sb_inter.getListIdToSplit (  probSplit  , percBirth  ) ;

		Map<Node, Node> mapNewSeed = sb_inter.createNewSeed(listIdToSplit, dist );
	
		sb_inter.connectNewSeed( mapNewSeed );
		
	
		
		
		
		
		
		
		
	}

	// test
	public void computeTest (  ) {
		
		if ( ! runSeedBirth )		//	System.out.println(runSeedBirth);
			return ; 		
	
		sb_inter.test();
	}
	
	
// get and set methods ------------------------------------------------------------------------------------------------------------------------------

	
	public static double getPercBirth ( ) {
		return percBirth ;
	}
	
	public static boolean  getRunSeedBirth ( ) {
		return runSeedBirth ;
	}
}
