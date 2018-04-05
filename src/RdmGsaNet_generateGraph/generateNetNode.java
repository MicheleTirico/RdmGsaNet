package RdmGsaNet_generateGraph;

import java.io.IOException;
import java.util.ArrayList;

import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNet_generateGraph.generateNetNode.layoutSeed;
import RdmGsaNet_generateGraph.generateNetNodeBreakGrid.interpolation;
import RdmGsaNet_mainSim.layerGs;
import RdmGsaNet_mainSim.layerNet;
import RdmGsaNet_mainSim.main;
import RdmGsaNet_mainSim.simulation;

public class generateNetNode extends main  {
	
//	protected  enum splitSeed { onlyOneRandom , splitMax , splitMaxThreshold , splitProbability }
//	protected splitSeed splitSeedtype ;
	
	protected boolean stillAlive;
	
	public enum layoutSeed { center , random , allNode }
	protected layoutSeed setLayoutSeed; 
	
	public enum rule { random , maxValue , minValue }
	protected rule ruleType ;
	
	public enum interpolation { averageEdge , averageDist, sumVectors  } 
	public interpolation typeInterpolation ;
	
	protected int numberMaxSeed ; 
	protected String morp;
	
	// probability costants 
	public static  double  prob = 0 ;
	
	// VARIABLES 
	// map
	private static Map < Double , ArrayList<String> > mapStepIdNet = simulation.getMapStepIdNet() ;
	private static Map < Double , ArrayList<String> > mapStepNewNodeId = simulation.getMapStepNewNodeId() ;
	
	// graph
	private static Graph gsGraph = layerGs.getGraph();
	private static Graph netGraph = layerNet.getGraph();

	// variables for constructor
	private static generateNetNode_Inter type ;
	private static generateNetNode growth ;

	// constructor
	public generateNetNode (generateNetNode_Inter type ) {
		prob = generateNetNodeVectorFieldSplitSeedProb_02.getProb();
		
		this.type = type ;
	}

	public void generateNode ( int step ) throws IOException  {
		type.generateNodeRule ( step ) ;
	} 

// PRIVATE METHODS ----------------------------------------------------------------------------------------------------------------------------------
	
// SET LAYOUT SEED NODES ----------------------------------------------------------------------------------------------------------------------------	
	protected void setSeedNodes ( int step , int numberMaxSeed , layoutSeed setLayoutSeed ) {

		if ( step != 1 )
			return ;
		
		int nodeCount = netGraph.getNodeCount();
		
		if ( numberMaxSeed > nodeCount )
			numberMaxSeed = nodeCount ;
		
		switch (setLayoutSeed) {
		case allNode:
			setLayoutSeedAllNode();					break;
			
		case center :
			setLayoutSeedCenter();					break;

		case random :
			setLayoutSeedRandom(numberMaxSeed);		break;
		}
	}

		
	// set layout seed all node
	private void setLayoutSeedAllNode ( ) {
		for ( Node n : netGraph.getEachNode() ) 
			n.addAttribute("seedGrad", 1);	
	}
		
	// set layout seed only center
	private void setLayoutSeedCenter ( ) {
		String idNodeCenter = gsAlgoToolkit.getCenterGrid(gsGraph);
		Node idNode = netGraph.getNode(idNodeCenter);
		idNode.addAttribute("seedGrad", 1 );
	}
		
	// set layout seed random ( aggiustare perche non é proprio random )
	private void setLayoutSeedRandom ( int numberMaxSeed ) {	
		int nodeCount = netGraph.getNodeCount();
		int numberNewSeed = 0 ;
			
		for ( Node n : netGraph.getEachNode() ) {
			int isSeed =  n.getAttribute("seedGrad") ;
			if ( isSeed != 1 ) {
			n.addAttribute("seedGrad", 1);
				numberNewSeed++;	
			}
			if ( numberNewSeed >= numberMaxSeed )
				return ;
		}	
	}

	// handle create new node	
	protected void handleNewNodeCreation ( Graph graph , String idCouldAdded , Node nodeSeed , double xNewNode , double yNewNode , boolean addFather  ) {
			
		Node nodeCouldAdded = null ;
		// there isn't node
		try {															//	System.out.println(idCouldAdded);
			netGraph.addNode(idCouldAdded) ;
			seedGraph.addNode(idCouldAdded) ;
			Node nNet = netGraph.getNode(idCouldAdded);
			Node nSeed = seedGraph.getNode(idCouldAdded);				//	System.out.println(nodeSeed.getId());
			nSeed.addAttribute("father", nodeSeed.getId());	
			nNet.addAttribute("father", nodeSeed.getId());				//	System.out.println(n.getAttributeKeySet());
			
			nodeCouldAdded = netGraph.getNode(idCouldAdded); 			//	System.out.println(idCouldAdded);
			nodeCouldAdded.addAttribute("seedGrad", 1);
			
			nodeSeed.setAttribute("seedGrad", 0 );
			nodeCouldAdded.addAttribute("father", nodeSeed.getId() );
			
			// set coordinate
			nNet.setAttribute( "xyz", xNewNode , yNewNode, 0 );	
			nSeed.setAttribute( "xyz", xNewNode , yNewNode, 0 );	
			
			seedGraph.removeNode(nodeSeed);
			}
			
		// if node already exist 
		catch (org.graphstream.graph.IdAlreadyInUseException e) { 		//
			System.out.println(e.getMessage());
			nodeCouldAdded = netGraph.getNode(idCouldAdded); 			//	System.out.println(idCouldAdded);
			nodeCouldAdded.addAttribute("seedGrad", 0 );
			nodeSeed.setAttribute("seedGrad", 1);
		}
	}	
	

	
	
	// Check coordinate in grid
	protected double ceckCoordInGrid ( Graph graphGrid , double newNodeCoord  ) {
		
		double sizeGridEdge = Math.pow( graphGrid.getNodeCount() , 0.5 ) - 1 ;
		
		if ( newNodeCoord > sizeGridEdge )
			newNodeCoord = sizeGridEdge ;
		
		if ( newNodeCoord < 0 )
			newNodeCoord = 0 ;
	
		return newNodeCoord ;	
	}
	
		
	
// GET METHODS --------------------------------------------------------------------------------------------------------
	public static generateNetNode 	getGenerateNode () 	{ return growth ; }
	public static String 			getGenerateType () 	{ return type.getClass().getSimpleName(); }
	public static double 			getProb() 			{ return prob; }



}
	
	

