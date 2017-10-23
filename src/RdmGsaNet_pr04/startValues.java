package RdmGsaNet_pr04;

public class startValues {
	
	// started objects
	
	public static void setVar() {
		
	// DECLARE VARIABLES
		
		// declare variables Gs layout
			setupGs kill = new setupGs();
			setupGs feed = new setupGs();
			setupGs Di = new setupGs();
			setupGs Da = new setupGs();
			
			setupGs SeedAct = new setupGs();
			setupGs SeedInh = new setupGs();
			
			setupGs typelayout = new setupGs();
			setupGs disMorph = new setupGs();
			
			
			
		// declare variables only Gs layout GRID
			setupGs GsGridSize = new setupGs();
			

//----------------------------------------------------------------------------------------------------------------
	// SET VARIABLES 
			
		// set variables Gs layout
			kill.setKill(0.5);
			feed.setFeed(0.8);
			Di.setDi(0.1);
			Da.setDa(0.4);
			
			SeedAct.setRandomSeedAct(3);
			SeedInh.setRandomSeedInh(3);
			
			
			typelayout.setTypeLayout( "grid" );
			disMorph.setDisMorp("random");
			
			
		// set variables only Gs layout GRID
			GsGridSize.setGsGridSize(20);
		
		
		
			
	}

}
