package RdmGsaNet_generateGraph;

import java.util.ArrayList;

import java.util.Map;

import org.graphstream.graph.Graph;

import RdmGsaNet_mainSim.layerGs;
import RdmGsaNet_mainSim.layerNet;
import RdmGsaNet_mainSim.main;
import RdmGsaNet_mainSim.simulation;

public class generateNetNode extends main  {
	
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
		this.type = type ;
	}
	
	public void generateNode ( int step )  {
		type.generateNodeRule ( step ) ;
	} 

// PRIVATE METHODS ------------------------------------------------------------------------------------------------
	
// GET METHODS --------------------------------------------------------------------------------------------------------
	public static generateNetNode 	getGenerateNode () { return growth ; }
	public static String 			getGenerateType () { return type.getClass().getSimpleName(); }
}
	
	

