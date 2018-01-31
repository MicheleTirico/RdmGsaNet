package RdmGsaNet_pr09;

public class gsAlgoReaction {
	
	// variables
	private gsAlgo.reactionType type;

	// constructor ( define type of reaction )
	public gsAlgoReaction ( gsAlgo.reactionType type) {
		this.type = type;
	}

	// method to compute raction
	public static double gsComputeReaction( gsAlgo.reactionType type, double act, double inh ) {
		double reaction;
		
		switch (type) {	
			case ai2: {
				reaction = ai2 ( act , inh ) ;
				break;
			}
		
			case linear : {
				reaction = linear ( act , inh ) ;
				break;
			}
		
			default: {	reaction = 0;	System.out.println("reaction Type not defined");	}
		}	
		return reaction;
	}

//-----------------------------------------------------------------------------------------------------------------------------------
	// methods to define reaction type
	
	// classical reaction from Gray Scott model
	private static double ai2 ( double act , double inh ) {
		return  act * ( inh * inh ) ;
	}
	
	// reaction not defined
	private static double linear ( double act , double inh ) {
		return act + inh ;
	}
}
