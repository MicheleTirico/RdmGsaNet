package RdmGsaNet_pr03;


import RdmGsaNet_pr03.layoutGs.typeLayout;
import RdmGsaNet_pr03.layoutGsGrid.type;

/*
 * the main class, where on the top we define all parameters and characteristics of model.
 * after that, we call methods to setup layout, run simulation, exported results and visualization.
 */

final class Run {

	public static void main(String[] args) {
		
		// started objects
		startValuesGs kill = new startValuesGs();
		startValuesGs feed = new startValuesGs();
		startValuesGs Di = new startValuesGs();
		startValuesGs Da = new startValuesGs();
		
		startValuesGs GsGridSize = new startValuesGs();
		startValuesGs SeedAct = new startValuesGs();
		startValuesGs SeedInh = new startValuesGs();
		
		// Gs Setup
		kill.setKill(0.5);
		feed.setFeed(0.8);
		Di.setDi(0.1);
		Da.setDa(0.4);
		
		GsGridSize.setGsGridSize(20);
		SeedAct.setRandomSeedAct(3);
		SeedInh.setRandomSeedInh(3);
		
		
		
		// setup Grid parameters
		
//		layoutGs.setGsGridLayout(layoutGs.typeLayout.grid);
//		layoutGs.setDisMorp(disMorp.homogeneus);
		
		
		// setup GsLayout => create GsLayer
		layoutGsGrid GsLayer; 		// create layer 0 for reaction diffusion
		
		layoutGs.setupTypelayout(typeLayout.grid);
		
		
		
		

		
		
//		GsLayer = new layoutGsGrid(type.grid4, GsGridSize, disMorp.random );
		
//		viz.vizGsGraph(GsLayer);
		

		
		
		
		
	}
}

