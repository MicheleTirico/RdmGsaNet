
package RdmGsaNet_pr04;
/*
 * the main class, where on the top we define all parameters and characteristics of model.
 * after that, we call methods to setup layout, run simulation, exported results and visualization.
 */

public class Run  {	

	public static void main(String[] args) {
		
		// SETUP VARIABLES
		startValues.setVar();
		
		// CREATE LAYER GS
		setupGsGrid GsLayer = new setupGsGrid();
		GsLayer.setGrid();
		GsLayer.setupDisMorp();	
		
		// 	LAYERS' VISUALIZATION 
	//	GsLayer.GsViz();
		GsLayer.GsMorpViz();
	
		// CREATE LAYER NET
		
		// GS ALGO
		startValues.setGsAlgo();
	
		
		
	
		
		
		
		
		//......

		
		
		
	}
}

