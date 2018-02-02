package RdmGsaNet_pr09;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.stream.file.FileSinkDGS;

import RdmGsaNetExport.handleNameFile;
import RdmGsaNetViz.setupViz;

public class simulation {	
	
	private static Graph gsGraph = layerGs.getGraph() ;
	private static Graph netGraph = layerNet.getGraph() ; 
	private static int stopSim ;
	private static int finalStep;
	private static int step;

	private static handleNameFile handle = main.getHandle();
	
// STORING GRAPH EVENTS
	 private static FileSinkDGS fsdGs = new FileSinkDGS();
	 private static FileSinkDGS fsdNet = new FileSinkDGS();

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
		 
// TEMPORAL MAP
	// map / key = (double) step , values = ( list of string)  idGs connected to net
	private static Map < Double , ArrayList<String> > mapStepIdNet = new HashMap <Double , ArrayList<String> > ()  ;
	
	// map / key = (double) step , values = (list of string ) id of new nodes in net
	private static Map < Double , ArrayList<String> > mapStepNewNodeId = new HashMap <Double , ArrayList<String> > ()  ;
		
	public static void  runSim ( boolean runSim ,
								 int stopSim, boolean printMorp , 
								 boolean genNode, boolean genEdge , 
								 boolean storedDgsGsStep , String pathStepGs ,
								 boolean storedDgsNetStep , String pathStepNet
//								boolean vizMorp 
								) 
								throws IOException, InterruptedException {
		
		if ( runSim == false ) 
			return ;
		
		generateNetEdge genNetEd = main.generateNetEdge ;
		generateNetNode genNetNo = main.generateNetNode ;
		 
		pathStepGs = handle.getPathStepGs();
		
		if ( storedDgsGsStep == true) { gsGraph.addSink(fsdGs); fsdGs.begin(pathStepGs);	}

		if ( storedDgsNetStep == true) { netGraph.addSink(fsdNet); fsdNet.begin(pathStepNet);	}
		
		// add step 0 
		addStep0( netGraph , mapStepIdNet );
	
		// start simulation, we define the last step in class run
		for ( step = 1 ; step <= stopSim ; step++ ) {	
			
			if ( storedDgsGsStep == true) { 	gsGraph.stepBegins(step); }			
			if ( storedDgsNetStep == true) { 	netGraph.stepBegins(step); }	
			
			// print each step
			System.out.println("------------step " + step + "----------------------------");

			// method to handle first step
			firstStep (step );	// System.out.println(mapMorp0);
			
			/* run gs algo to all nodes
				boolean print : if true, print mapMorp */
			gsAlgo.gsAlgoMain( false );
			
			// define rules to growth network
			if ( genNode == true) { genNetNo.generateNode( step ); }
			
			// update net 
			updateMapStepId(step , netGraph, mapStepIdNet);
			updateMapStepNewNodes ( step , netGraph , mapStepNewNodeId );
			
			if ( genEdge == true) {genNetEd.generateEdge( step ); }

			// create list and map
			listIdNet = createListId ( netGraph );	
			
			// update map graph
			updateMapGraph( mapStepNetGraph , step, netGraph);
			
			// print values in run
			if ( printMorp == true) { System.out.println(mapMorp1); }			//	System.out.println("node set " + mapStepIdNet);	
		}
		
		
		// stored graph in dgs format
		if ( storedDgsGsStep == true) { 	fsdGs.end();	}
		if ( storedDgsNetStep == true) { 	fsdNet.end();	}
		
		finalStep = step - 1 ;	
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
	
	// update new nodes map
	private static void updateMapStepNewNodes ( int step , Graph graph , Map mapNewNodes ) {

		ArrayList<String> nodeStep    	= mapStepIdNet.get( (double) step );
		ArrayList<String> nodeOldStep 	= mapStepIdNet.get( (double) step - 1.0 );
		
		ArrayList<String> newNodes 		= new ArrayList<String>() ;								//		System.out.println( "step " + nodeStep);//		System.out.println( "old step " + nodeOldStep);
		
		try {
			for ( String s : nodeStep ) {
				if ( !nodeOldStep.contains(s) ) {
					newNodes.add(s);
				}
			}
		} catch (java.lang.NullPointerException e ) {  }										//		System.out.println(newNodes);	
		
		mapNewNodes.put(step, newNodes);	
	}
	
	// create map
	private static Map < Double , ArrayList<String >>  updateMapStepId ( double step, Graph graph, Map < Double , ArrayList<String >>  map   ) {
		map.put(step, createListId(graph)) ;
		return map;	
	}
	
	// method to create a list of id of nodes 
	protected static ArrayList<String> createListId (Graph graph ) {
		
		ArrayList<String> list = new ArrayList<String>() ; 
		for ( Node n : graph.getEachNode()) {list.add(n.getId()) ; }	//	System.out.println(list);			
		return list;	
	}
	
	// add step 0 
	private static void addStep0 ( Graph graph  , Map mapStepId ) {
		
		ArrayList<String> idStep0 = new ArrayList<>();
		for ( Node n : graph.getNodeSet() )
			idStep0.add(n.getId());
		mapStepId.put(0.0 , idStep0 ) ;
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
	
	public static int 							getStopSim() 				{ return stopSim ; } 
	public static ArrayList<String> 			getListIdGs ()				{ return listIdGs ; }
	public static int 							getStep () 					{ return step ; }
}