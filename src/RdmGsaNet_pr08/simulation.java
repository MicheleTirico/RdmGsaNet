package RdmGsaNet_pr08;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class simulation {	
	
	public void  runSim (int stopSim ) {		
		
		Graph graph = layerGs.getGraph(layerGs.gsGraph) ;
		growthNet growthNet = run.growthNet ;

//		for ( Node n : graph.getEachNode() ) {	double x = n.getAttribute( "GsAct" ) ;	System.out.println(x);	}
		
		Map<String, ArrayList<Double>> mapMorp0 = new HashMap<String, ArrayList<Double>>();
		Map<String, ArrayList<Double>> mapMorp1 = new HashMap<String, ArrayList<Double>>();
		
		for (int step = 1 ; step <= stopSim; step++) {	//	System.out.println(step);

			firstStep (step, mapMorp0 , mapMorp1, graph);	//	System.out.println(mapMorp0);

			gsAlgo.gsAlgoMain( mapMorp0, mapMorp1 );
		
			growthNet.growth(step);
		}
	}
		
// PRIVATE METHODS --------------------------------------------------------------------------------------------------------------
	/* define first step of simulation 
	 	* if we have first step, we keep values from Gs graph
	 	* else we keep values from mapMorp0  		*/
	private static void firstStep (int step , Map mapMorp0 , Map mapMorp1, Graph graph ) {
		if ( step == 1) {
			
			for ( Node n : graph.getEachNode() ) {
				ArrayList<Double> ArList0 = new ArrayList<Double>() ;
		
				ArList0.add( (double) n.getAttribute( "GsAct" ) );
				ArList0.add( (double) n.getAttribute( "GsInh" ) );
				
				mapMorp0.put(n.getId(), ArList0 );	
			}
		}	
		// set parameters from previous step 
		else {
			for ( Node n : graph.getEachNode() ) {
				ArrayList<Double> ArList0 = (ArrayList<Double>) mapMorp1.get(n.getId());
				
				mapMorp0.put(n.getId(), ArList0 );
			}
		}
	}
	
}
