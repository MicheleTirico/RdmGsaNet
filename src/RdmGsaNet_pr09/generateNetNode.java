package RdmGsaNet_pr09;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

public class generateNetNode {
	
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
	
	

