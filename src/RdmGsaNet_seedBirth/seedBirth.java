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
	
	private boolean runSeedBirth ;
	
	protected static double probSplit ; 
	protected double percBirth , dist ; 
	
	
	// type split
	public enum seedSplitType { splitProb, onlySetSeed }
	protected seedSplitType seedSplitType ; 
	
	
	// interface object
	private seedBirt_inter sb_inter ; 
	
	// constructor
	public seedBirth( boolean runSeedBirth , seedSplitType seedSplitType ) {
		
		this.runSeedBirth = runSeedBirth ; 
		this.seedSplitType = seedSplitType ;

		switch (seedSplitType) {
			case splitProb:
				sb_inter = new seedBirth_splitProb(  ) ;
	 			break;
	 		
			case onlySetSeed :
				sb_inter = new seedBirth_onlySetSeed(  ) ;
				break;
		}
	}
	
	// set parameters 
	public void setParameters ( double probSplit , double percBirth , double dist  ) {
		this.probSplit = probSplit ;
		this.percBirth = percBirth ;
		this.dist = dist ; 
		
	}
	


	public void compute ( ) {
		
		if ( ! runSeedBirth )
			return ;
		
		ArrayList<String> listIdToSplit = sb_inter.getListIdToSplit (  probSplit  , percBirth  ) ;
		// System.out.println(listIdToSplit);

		Map<Node, Node> mapNewSeed = sb_inter.createNewSeed(listIdToSplit, dist );
	
		sb_inter.connectNewSeed(mapNewSeed );
		
	
		
		
		
		
		
		
		
	}

	// test
	public void computeTest (  ) {
		
		if ( ! runSeedBirth )		//	System.out.println(runSeedBirth);
			return ; 		
	
		sb_inter.test();
	}
	
	
// get and set methods ------------------------------------------------------------------------------------------------------------------------------


}
