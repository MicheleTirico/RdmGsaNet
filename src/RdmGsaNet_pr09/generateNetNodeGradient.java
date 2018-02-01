package RdmGsaNet_pr09;

public class generateNetNodeGradient extends main implements generateNetNode_Inter {

	// COSTANTS
	protected enum splitSeed { onlyOneRandom , splitMax , splitMaxThreshold , splitProbability }
	protected  splitSeed type ;
	
	// probability costants 
	static double  prob = 0 ;
	
	// constant for onlyOneRandom
	
	//COSTRUCTOR
	public generateNetNodeGradient ( splitSeed type ) {
		this.type = type ;
	}
	
	public static void setParametersOnlyOneRandom ( ) {
		
	}
	
	@Override
	public void generateNodeRule(int step) {
		
		
		switch (type ) {
		case onlyOneRandom :
			generateNetNodeGradientOnlyOneRandom.test();
			
			break;
		
		
		}
		
	}

	@Override
	public void removeNodeRule(int step) {
		// TODO Auto-generated method stub
		
	}

// COMMON METHODS FOR GRADIENT ---------------------------------------------------------------------------------------------------------------------
	private static void getOnlyOneRandomMethod () {
		
	}
	
// GET METHODS ----------------------------------------------------------------------------------------------------------------------------
	public static double getProb() 		{ return prob; }

	
}
