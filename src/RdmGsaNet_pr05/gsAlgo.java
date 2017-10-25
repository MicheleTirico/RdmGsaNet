package RdmGsaNet_pr05;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

public interface gsAlgo   {
	
	public enum morphogen {activator, inhibitor }
	public enum interaction {reaction , diffusion, ext }
	
	public enum reactionType {ai2, linear }
	public enum diffusionType {fick, perimeter, weigth }
	public enum extType {gsModel, test}
	
	public static Graph GsGraphAlgo = new SingleGraph("GsGraphAlgo");
	
	
	
	// methods

	public void gsInit(boolean x) ;
	/*methods that is used before static method. In gsInit we identify the stream of information 
	and started parameters
	*/
	public double gsCompute(
			double x, 
			double y) ;
	
	public static void gsAlgoMain (reactionType r, diffusionType d, extType e) {
		
		double stopSim = setupGs.getStopSim();
		int step;
		
		// define equations
		double act = 0, inh = 0;
		
		double reaction = gsAlgoReaction.gsComputeReaction(reactionType.ai2, act, inh);
		
		
		
		
		
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

	
	public static void gsAlgoInit (boolean x) {
		
		if (x = true ) {
			// true = keep the initial parameters of RdmGsaNet
			System.out.println("GsNet initial parameters");

			Graph graph =setupGs.getGraph(setupGs.GsGraph);
		
		}
		
		else {
			
		}

		
	}
	
}
