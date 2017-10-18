package hp_RdmGsaNet_pr03;

import hp_RdmGsaNet_pr02.layoutGs.disMorp;
import hp_RdmGsaNet_pr03.layoutGsGrid.type;

final class Run  {

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
		
		// run GsLayout
		layoutGsGrid GsLayer = new layoutGsGrid(type.grid4, GsGridSize, disMorp.random )

		
		
		
		
	}
}

