
package RdmGsaNet_pr05;

import RdmGsaNet_pr05.gsAlgo.diffusionType;
import RdmGsaNet_pr05.gsAlgo.extType;
import RdmGsaNet_pr05.gsAlgo.reactionType;

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
	//	gsLayer.GsViz();
		gsLayer.GsMorpViz();
	
		// CREATE LAYER NET
		
		// GS ALGO
		startValues.setGsAlgo();
		gsAlgoReaction reaction = new gsAlgoReaction();
		reaction.gsInit(false);
	gsAlgo.gsAlgoMain(reactionType.linear, diffusionType.D1, extType.E1);
	
	
		
		
	
		
		
		
		
		//......

		
		
		
	}
}

