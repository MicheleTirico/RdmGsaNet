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
	
	private static boolean runSeedBirth  ;
	
	protected static double probSplit ; 
	protected static double percBirth , 
							angleTest  , angleTestDecimal ;

	protected double dist; 
	
	// type set Seed
	public enum setSeedType { throwSeed, onlySetSeed }
	protected setSeedType setSeedType ; 
	
	// type set seed
	public enum generateSeedType { probability , percentGraph , percentGradient }
	protected static generateSeedType generateSeedType ;
	
	public enum choiceNodeType { maxInten , ortoAngleVector  }
	protected static choiceNodeType choiceNodeType ;
	
	// interface object
	private seedBirt_inter sb_inter ; 
	
	// constructor
	public seedBirth ( boolean runSeedBirth , setSeedType setSeedType , generateSeedType generateSeedType  ) {
		
		this.runSeedBirth = runSeedBirth ; 
		this.setSeedType = setSeedType ;
		this.generateSeedType =  generateSeedType ;
		

		switch (setSeedType) {
			case throwSeed: 				
				sb_inter = new seedBirth_throwSeed(  ) ;
				break;
	 		
			case onlySetSeed : {
				if ( generateSeedType.equals(generateSeedType.percentGraph) ) 
					sb_inter = new seedBirth_onlySetSeed( ) ;
					
				else if ( generateSeedType.equals(generateSeedType.percentGradient) )
					sb_inter = new seedBirth_percentGradient( ) ;
				
				break;
		
			}
		}
	}
	
	// set parameters 
	public void setParameters_throwSeed ( double probSplit , double dist  ) {
		this.probSplit = probSplit ;
		this.dist = dist ; 
	}
	
	public void setParameters_onlySetSeed ( double percBirth  , choiceNodeType choiceNodeType , double angleTestDecimal ) {
		this.percBirth = percBirth ;
		this.choiceNodeType = choiceNodeType ;
		this.angleTestDecimal = angleTestDecimal ;
		
		angleTest = angleTestDecimal * Math.PI / 180 ;
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

	public static choiceNodeType getChoiceNodeType ( ) {
		return choiceNodeType ;
	}
	
	public static double getPercBirth ( ) {
		return percBirth ;
	}
	
	public static boolean  getRunSeedBirth ( ) {
		return runSeedBirth ;
	}
	
	public static generateSeedType getGenerateSeedType ( ) {
		return generateSeedType ;
	}
	
	public static double getAngleTest() {
		return angleTest ;
	}

}
