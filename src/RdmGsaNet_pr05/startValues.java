package RdmGsaNet_pr05;

public interface startValues {
	
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
			setupGs GsGridType = new setupGs();	
			
//----------------------------------------------------------------------------------------------------------------
	// SET VARIABLES 
			
		// set variables Gs layout
			kill.setKill(0.5);
			feed.setFeed(0.8);
			Di.setDi(0.1);
			Da.setDa(0.4);
			
			SeedAct.setRandomSeedAct(123);
			SeedInh.setRandomSeedInh(2222);
			
		// set common parameters	
			typelayout.setTypeLayout( "grid" );		// choose among : grid , random , gis
			disMorph.setDisMorp("random");			// choose among : random , homogeneus	
			
		// set variables only Gs layout GRID
			GsGridSize.setGsGridSize(1);
			GsGridType.setGsGridType("grid4");		// choose among : grid4 , grid8			
	}

//----------------------------------------------------------------------------------------------------------------
	// SET GSALGO	
	public static void setGsAlgo() {
		
		// declare GsAlgo type
	//		setupGs reactionType = new setupGs();
//			setupGs diffusionType = new setupGs();
		
		// set type reaction and diffusion
//			reactionType.setReactionType("ai");			// choose among : ai2, linear
//			diffusionType.setDiffusionType("fick");		// choose among : fick, perimeter, weight
			
		// parameters of simulation 
			setupGs stopSim = new setupGs();
			
			stopSim.setStopSim( 1 );
	
	}
	
		
		
	
	
			
}
