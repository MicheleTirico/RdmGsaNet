
package RdmGsaNet_pr05;

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
		
		// GS ALGO
		startValues.setGsAlgo();
	

//		gsAlgo.gsAlgoInit(true);
		
		
		
		gsAlgo.gsAlgoMain(gsAlgo.reactionType.ai2, gsAlgo.diffusionType.fick, gsAlgo.extType.gsModel);
		
		
	
		
		
		
		
		//......

		
		
		
	}
}

