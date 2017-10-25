package RdmGsaNet_pr05;

public interface gsAlgo   {
	
	public enum morp {activator, inhibitor }
	public enum interaction {reaction , diffusion, ext }
	
	public enum reactionType {ai2, linear }
	public enum diffusionType {D1, D2}
	public enum extType {E1, E2}
	
	
	
	// methods

	public void gsInit(boolean x) ;
	/*methods that is used before static method. In gsInit we identify the stream of information 
	and started parameters
	*/
	public double gsCompute(
			//interaction i, 
			double x, 
			double y) ;
	
	public static void gsAlgoMain (reactionType r, diffusionType d, extType e) {
		
		double stopSim = setupGs.getStopSim();
	//	double reaction ;
		int step;
		
		// define equations
		double act = 0, inh = 0;
		
		double reaction = gsAlgoReaction.gsComputeReaction(gsAlgoReaction.reactionType.ai2, act, inh);
		
		
		
		
		
		// loop
		
		for (step = 0 ; step >= stopSim ; step++ ) {
			
			
			
		}
		
		
		/* for each nodes, compute act and ihn
		 * define equation
		 	act = act(-1) + gsAlgoComp(diffusion) + sAlgoComp(reaction) + sAlgoComp(ext)
			ihn = ihn(-1) + gsAlgoComp(diffusion) + sAlgoComp(reaction) + sAlgoComp(ext) 
		 
	
		for step <= stopSim
			get each node
			act = act(-1) + equation
			ihn = ihn(-1) + equation
		*/
		
		
	}

	
	
}
