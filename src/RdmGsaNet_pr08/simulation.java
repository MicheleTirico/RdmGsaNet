package RdmGsaNet_pr08;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import RdmGsaNetViz.graphViz;

public class simulation {	
	
	private static Graph gsGraph = layerGs.getGraph() ;
	private static Graph netGraph = layerNet.getGraph() ; 
	
// MAP OF GRAPH
	
	private static Map<Double , Graph > mapStepNetGraph = new HashMap<Double, Graph> ();
	
	
// LIST FOR ID
	// list of gsId
	private static ArrayList<String> listIdGs = new ArrayList<String>() ;
	
	// list of netId / careful : this list evolving in increasing time
	private static ArrayList<String> listIdNet = new ArrayList<String>() ; 
	
// MAP FOR MORPHOGEN
	// map / before each step / key = (string) idGs , values, (list of double) [ activator, inhibiter ] 
	private static Map< String, ArrayList<Double >> mapMorp0	= new HashMap< String, ArrayList<Double >>();
 		
 	// map / after each step / key = (string) idGs , values, (list of double) [ activator, inhibiter ] 
	private static Map< String, ArrayList<Double >> mapMorp1 	= new HashMap< String, ArrayList<Double >>();
	
// MAP FOR CONNECTION
//	 map / key = (string) idGs , value = (string) idNet
//	private static Map< String, String > mapIdGsNet 	= new HashMap< String, String >();
	 
// TEMPORAL MAP
	// map / key = (double) step , values = ( list of string)  idGs connected to net
	private static Map < Double , ArrayList<String> > mapStepIdNet = new HashMap <Double , ArrayList<String> > ()  ;
	
	// map / key = (double) step , values = (list of string ) id of new nodes in net
	private static Map < Double , ArrayList<String> > mapStepNewNodeId = new HashMap <Double , ArrayList<String> > ()  ;
	
	// map / key = (double) step , values = (list of string ) idNet of new nodes 
//	private static Map < Double , ArrayList<String> > mapStepNewNodeIdNet = new HashMap <Double , ArrayList<String> > ()  ;
	
	// map / key = (double) step , values = (list of string ) idNet 	
		//	CARREFUL : mapStepIdNet = mapStepIdGsCon
//	private static Map < Double , ArrayList<String> > mapStepIdNet = new HashMap <Double , ArrayList<String> > ()  ;
	
	public void  runSim (int stopSim, boolean printMorp , boolean genNode, boolean genEdge ) {
		
		
		generateNetEdge genNetEd = main.generateNetEdge ;
		generateNetNode genNetNo = main.generateNetNode ;
		
		// start simulation, we define the last step in class run
		for ( int step = 1 ; step <= stopSim ; step++ ) {	
			
			System.out.println("step " + step);

			// method to handle first step
			firstStep (step );	// System.out.println(mapMorp0);
			
			// run gs algo to all nodes
			gsAlgo.gsAlgoMain( false );
			
			// define rules to growth network
			if ( genNode == true) { genNetNo.generateNode( step ); }
			
			if ( genEdge == true) {genNetEd.generateEdge( step ); }

			// create list and map
			listIdNet = createListId ( netGraph );
//			mapStepIdNet = updateMapStepId( step , netGraph , mapStepIdNet) ;	//					

			
			// update map graph
			updateMapGraph( mapStepNetGraph , step, netGraph);
//			System.out.println(mapStepNetGraph);

			if ( printMorp == true) { System.out.println(mapMorp1); }
			
		}
	}
		
// PRIVATE METHODS --------------------------------------------------------------------------------------------------------------
	/* define first step of simulation 
	 	* if we have first step, we keep values from Gs graph
	 	* else we keep values from mapMorp0  		*/
	private static void firstStep (int step ) {
		if ( step == 1) {
			
			// create list of gsId
			listIdGs = createListId(gsGraph) ;
			
			// set parameters from gsGraph
			for ( Node n : gsGraph.getEachNode() ) {
				ArrayList<Double> ArList0 = new ArrayList<Double>() ;
		
				ArList0.add( (double) n.getAttribute( "gsAct" ) );
				ArList0.add( (double) n.getAttribute( "gsInh" ) ); 
				
				mapMorp0.put(n.getId(), ArList0 );	
			}
		}	
		// set parameters from previous step 
		else {
			for ( Node n : gsGraph.getEachNode() ) {
				ArrayList<Double> ArList0 = (ArrayList<Double>) mapMorp1.get(n.getId());
				
				mapMorp0.put(n.getId(), ArList0 );
			}
		} 
	}
	
	// create map
	private static Map < Double , ArrayList<String >>  updateMapStepId ( double step, Graph graph, Map < Double , ArrayList<String >>  map   ) {
		map.put(step, createListId(graph)) ;
		return map;	
	}
	
	// method to a list of id of nodes 
	protected static ArrayList<String> createListId (Graph graph ) {
		
		ArrayList<String> list = new ArrayList<String>() ; 
		for ( Node n : graph.getEachNode()) {list.add(n.getId()) ; }	//	System.out.println(list);			
		return list;	
	}
	// MAP GRAPH -----------------------------------------------------------------------------------------------------------------	

	private static void updateMapGraph ( Map<Double , Graph > map, double step , Graph graph ) {
		map.put(step, graph);	
	}
	
// GET METHODS -----------------------------------------------------------------------------------------------------------------
	// get methods
	public static Map<String, ArrayList<Double>> getmapMorp0 ()				{ return mapMorp0 ; }  
	public static Map<String, ArrayList<Double>> getmapMorp1 ()				{ return mapMorp1 ; } 
	public static Map<Double, ArrayList<String>> getMapStepIdNet () 		{ return mapStepIdNet ; }  
	public static Map<Double, ArrayList<String>> getMapStepNewNodeId  () 	{ return mapStepNewNodeId  ; }  
	public static Map<Double, Graph>			 getMapStepNetGraph	() 		{ return mapStepNetGraph  ; }  
}