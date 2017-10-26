package RdmGsaNet_pr05;

import org.graphstream.graph.Graph;

public class gsAlgoReaction implements gsAlgo {
	
	// variables
	//public enum reactionType {ai2, linear }
	reactionType type;

	// costructor
	public gsAlgoReaction (reactionType type) {
		this.type = type;
	}

	// methods
	public static double gsComputeReaction( reactionType type, double act, double inh ) {
	
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
		
			default: {
				reaction = 0;
				System.out.println("reaction Type not defined");
			}
		}
		
		return reaction;
	}


	public void gsInit(boolean x) {	
		}



	@Override
	public double gsCompute(double x, double y) {
		// TODO Auto-generated method stub
		return 0;
	}
	
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
