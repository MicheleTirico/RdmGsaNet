package RdmGsaNet_pr08;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class simulation {	
	
	private static Graph gsGraph = layerGs.getGraph() ;
	private static Graph netGraph = layerNet.getGraph() ;
	
	// LIST
	// list of net nodes id
	
	
	/* 	MAP FOR GS
 		Map to update values of morphogens ( id node ( String ) , morphogens ( list ) )
		mapMorp0 = values of morp before each step
		mapMorp1 = values of morp after each step
	 */
	private static Map< String, ArrayList<Double >> mapMorp0	= new HashMap< String, ArrayList<Double >>();
	private static Map< String, ArrayList<Double >> mapMorp1 	= new HashMap< String, ArrayList<Double >>();
	
	/*	MAP FOR NET
	 * mapNetIdGs = map to define the id of gsLayer 
	 * mapNetId
	 */
	private static Map<String, ArrayList<Double>> mapNetIdGs 	= new HashMap< String, ArrayList<Double >>();
	private static Map<String, ArrayList<Double>> mapNetId		= new HashMap< String, ArrayList<Double >>();
	
	/* MAP FOR CONNECTION
	 * mapIdGsNet
	 */
	private static Map< String, String > mapIdGsNet 	= new HashMap< String, String >();
	
	// map of step , list of idGs connected
	private static Map < Double , ArrayList<String> > mapStepIdGsCon = new HashMap <Double , ArrayList<String> > ()  ;
	private static Map < Double , ArrayList<String> > mapStepNewNodeIdGs = new HashMap <Double , ArrayList<String> > ()  ;
	
	public void  runSim (int stopSim ) {				
		
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
//			System.out.println("generateNode");
			genNetNo.generateNode( step );
			
//			System.out.println("generateEdge");
			genNetEd.generateEdge( step );
			
//			for ( Node n : gsGraph.getEachNode() ) {		int a = n.getAttribute("con") ;		System.out.println(a); }
		}
	}
		
// PRIVATE METHODS --------------------------------------------------------------------------------------------------------------
	/* define first step of simulation 
	 	* if we have first step, we keep values from Gs graph
	 	* else we keep values from mapMorp0  		*/
	private static void firstStep (int step ) {
		if ( step == 1) {
			
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
	
	// get methods
	public static Map getmapMorp0 () { return mapMorp0 ; }  
	public static Map getmapMorp1 () { return mapMorp1 ; } 
	public static Map getMapStepIdGsCon () { return mapStepIdGsCon ; }  
	public static Map getMapStepNewNodeIdGs  () { return mapStepNewNodeIdGs  ; }  
}
