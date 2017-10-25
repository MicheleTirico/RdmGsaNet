package RdmGsaNet_pr04;

public interface gsAlgo  {
	
	// 	VARIABLES
	final String reactionType = setupGs.getReactionType();
//	final String diffusionType = setupGs.getDiffusionType();
	
	final double kill = setupGs.getKill();
	final double feed = setupGs.getFeed();
	final double Da = setupGs.getDa();
	final double Di = setupGs.getDi();
	
	public enum morp {activator, inhibitor }
	public enum interaction {reaction , diffusion, ext }
	
	final int stopSim = setupGs.getStopSim();
	
	
	
	//STATIC METHODS
	public static void gsAlgoMain () {
		
		/* for each nodes, compute act and ihn
		 * define equation
		 	act = act(-1) + gsAlgoComp(diffusion) + sAlgoComp(reaction) + sAlgoComp(ext)
			ihn = ihn(-1) + gsAlgoComp(diffusion) + sAlgoComp(reaction) + sAlgoComp(ext) 
		 
	
		for step <= stopSim
			get each node
			act = act(-1) + equation
			ihn = ihn(-1) + equation
		*/
		
		
		double act;
		double actOld = 1;
		double R = 1;
		double D = 1;
		double E = 1;
		
		act = actOld + R + D + E ;
		
		
		
		for (int step = 0 ; step < stopSim ; step++ ) {
			
			
			
			step = step + 1 ;
		}
	}
	
	
	// COMMON METHODS
	
	// to initializate started values for each interaction
	public void gsAlgoInit( morp x , interaction y );
	
	// to compute the value of interaction
	public void gsAlgoComp( interaction x );
	
	// to return the value of morphogen
	public void getValueMorp( morp x );
	
	// to return the value of interaction
	public void getValueInter( interaction x );



	
	
	
	
}	
	/*
	
	// METHODS
	public static void gsAlgoMain () {
		
		
		
		gsAlgoReaction act = new gsAlgoReaction();
	//	act.gsAlgoCalcMorp( x );
		
		
	}
	
	
	public double gsAlgoCalcMorp( morp x) ;
	
	public void gsAlgoInter(interaction x);
	
	public void gsAlgoExpMorp() ;
	
	public void getValue(morp x);



}
*/