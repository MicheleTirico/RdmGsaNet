
package RdmGsaNet_pr06;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

/*
 * the main class, where on the top we define all parameters and characteristics of model.
 * after that, we call methods to setup layout, run simulation, exported results and visualization.
 */

public class Run  {	

	public static void main(String[] args) {
		
		// SETUP VARIABLES
		startValues.setVar();
		
		// CREATE LAYER GS
		setupGsGrid gsLayer = new setupGsGrid();
		gsLayer.setGrid();
		gsLayer.setupDisMorp();	
		
		// 	LAYERS' VISUALIZATION 
//		gsLayer.GsViz();
//		gsLayer.GsMorpViz();
	
		// CREATE LAYER NET
		layerNet netlayer = new layerNet( new setupNetSeed ()) ;
		
//		netlayer.changeLayer(new setupNetRandom ());
		netlayer.createLayer();
		
		netlayer.setupGsNetLink( setupNetInter.meanPointPlace.border);
		
		Graph gs = setupGs.getGraph(setupGs.GsGraph);
		
		for (Node n : gs.getEachNode()) {
			Object x = n.getAttribute("con" ) ;
			System.out.println(x);
		}
		
		for (Edge e : gs.getEachEdge()) {
			
		}
		
		
		// GS ALGO
//		startValues.setGsAlgo();
	

//		gsAlgo.gsAlgoInit(true);
		
//		gsAlgo.DisMorp (homo o random);
		
		gsAlgo.gsAlgoMain(2, gsAlgo.reactionType.ai2, gsAlgo.diffusionType.fick, gsAlgo.extType.gsModel);
		
		
	
		
		
		
		
		//......

		
		
		
	}
}

