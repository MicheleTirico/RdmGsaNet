package RdmGsaNet_pr05;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public interface gsAlgo   {
	
	public enum morphogen {activator, inhibitor }
	public enum interaction {reaction , diffusion, ext }
	
	public enum reactionType {ai2, linear }
	public enum diffusionType {fick, perimeter, weigth }
	public enum extType {gsModel, test}
	
	//public static Graph GsGraphAlgo = new SingleGraph("GsGraphAlgo");
	
	
	
	// methods

	
	/*methods that is used before static method. In gsInit we identify the stream of information 
	and started parameters
	*/
	
	
	
	
	public static void gsAlgoMain (reactionType r, diffusionType d, extType e) {
		
		int stopSim = setupGs.getStopSim();
		int step ;
		double Da = setupGs.getDa() ;
		double Di = setupGs.getDi() ;
		
	 
		
		// define equations
	//	double act = 0, inh = 0;
	//	double feed = setupGs.getFeed();
	//	double kill = setupGs.getKill();
		
		
	//	double reaction = gsAlgoReaction.gsComputeReaction(reactionType.ai2, act, inh);
	//	double diffusion = gsAlgoDiffusion.gsComputeDiffusion(diffusionType.fick);
	//	double extFeed = gsAlgoExt.gsComputeExt(extType.gsModel, morphogen.activator,feed, kill, act, inh ) ;
	//	double extKill = gsAlgoExt.gsComputeExt(extType.gsModel, morphogen.inhibitor,feed, kill, act, inh ) ;

		// loop
		
		Graph graph = setupGs.getGraph( setupGs.GsGraph );
		for ( step = 1 ; step <= stopSim ;  step++  ) {

			System.out.println(step);
			
			for(  Node n : graph.getEachNode () ) {
				
				double OldAct, OldInh;
				
				double act = n.getAttribute( "GsAct" ) ; 
				double inh = n.getAttribute( "GsInh") ; 
				
				OldAct = act; 
				OldInh = inh;
				
				double reaction = gsAlgoReaction.gsComputeReaction(reactionType.ai2, OldAct, OldInh);
				
				double diffusionAct = gsAlgoDiffusion.gsComputeDiffusion(diffusionType.fick, Da);
				double diffusionInh = gsAlgoDiffusion.gsComputeDiffusion(diffusionType.fick, Di);
				
				
				
				act = act + diffusionAct + reaction;
				inh = inh + diffusionInh - reaction;
				
				n.setAttribute( "GsAct" , act ) ;
				n.setAttribute( "GsInh" , inh ) ;
				
				System.out.println( act ) ;
				System.out.println( inh );
			
			
			
			
			
			
			}}}}
		
		/*
		for ( step = 1 ; step <= stopSim ;  step++  ) {

			System.out.println(step);
			
			for(  Node n : graph.getEachNode () ) {
				if ( step == 1 ) {

					double act = n.getAttribute("GsAct");
					double inh = n.getAttribute("GsInh");
					
				}
				else {
				
					
			
			 System.out.println(act);
			
			double actOld = act;
			double inhOld = inh;
			
			double reaction = gsAlgoReaction.gsComputeReaction(reactionType.ai2, act, inh);
			
			act = reaction;
			System.out.println(act);
				}
			}
		}
	}
}

		
		
		
		
		/*	
		do { 
			step = 1 ;
			
			for(  Node n : graph.getEachNode () ) {
				double act  , inh;

				
				act = n.getAttribute("GsAct");
				inh = n.getAttribute("GsInh");
				
				System.out.println(act);
			 
			}
			step = 2 ;
		}

		while ( step <= stopSim) ; {
			
			double actOld;
			
			actOld = act;
//			double inhOld = inh;
			
//			double reaction = gsAlgoReaction.gsComputeReaction(reactionType.ai2, act, inh);
			
//			act = reaction;
//			System.out.println(act);		
			
		}
		System.out.println(act);		
	
	}
}
		
		/*
		
		
		for ( step = 1 ; step <= stopSim ;  step++  ) {

			System.out.println(step);
			
			for(  Node n : graph.getEachNode () ) {
			
			double act = n.getAttribute("GsAct");
			double inh = n.getAttribute("GsInh");
			
			 System.out.println(act);
			
			double actOld = act;
			double inhOld = inh;
			
			double reaction = gsAlgoReaction.gsComputeReaction(reactionType.ai2, act, inh);
			
			act = reaction;
			System.out.println(act);
			
		
			
		}
		}
	}
}
		
		
		/*
		
		do { 
			step = 0;
			
			
		
			step = 1; 
		}
		
		
		while ( step <= stopSim );  {
			
			System.out.println(step);
			Graph graph = setupGs.getGraph( setupGs.GsGraph );
			for(  Node n : graph.getEachNode () ) {
				
		
				
				double act = n.getAttribute("GsAct");
				double inh = n.getAttribute("GsInh");
		
				
				System.out.println(act);
				
				double actOld = act;
				double inhOld = inh;
				
				double reaction = gsAlgoReaction.gsComputeReaction(reactionType.ai2, act, inh);
				
				act = reaction;
				System.out.println(act);
			
		
				
				System.out.println(n.getId());
				}
			
			
			step++;
		}
		
	
		/*
		
		for (step = 0 ; step >= stopSim ; step++ ) {
			System.out.println(step);
			Graph graph = setupGs.getGraph( setupGs.GsGraph );
		//	Node.getDegree();
			for ( Node n : graph.getEachNode () ) { 
				
				double act = graph.getAttribute("GsAct");
				System.out.println(act);
				System.out.println(step);
			//	graph.display();
			
			}
			for(Node n:graph) {
				System.out.println(n.getId());
			}
			
			/*
			
			double act = 0 ; // valore da getatribute node
			double inh = 0 ; // valore da getatribute node
			
			
			double actOld = act;
			double inhOld = inh;
			
			double reaction = gsAlgoReaction.gsComputeReaction(reactionType.ai2, act, inh);
			double diffusion = gsAlgoDiffusion.gsComputeDiffusion(diffusionType.fick);
			double extFeed = gsAlgoExt.gsComputeExt(extType.gsModel, morphogen.activator,feed, kill, act, inh ) ;
			double extKill = gsAlgoExt.gsComputeExt(extType.gsModel, morphogen.inhibitor,feed, kill, act, inh ) ;
			
			act = reaction ;
			
			*/
			
	
			
		


		
		
		
		
//		for (step = 0 ; step >= stopSim ; step++ ) {	}
		
		
		
	
	
		/* for each nodes, compute act and ihn
		 * define equation
		 	act = act(-1) + gsAlgoComp(diffusion) + sAlgoComp(reaction) + sAlgoComp(ext)
			ihn = ihn(-1) + gsAlgoComp(diffusion) + sAlgoComp(reaction) + sAlgoComp(ext) 
		 
	
		for step <= stopSim
			get each node
			act = act(-1) + equation
			ihn = ihn(-1) + equation
		*/
		
	
	

/*
	public static void gsAlgoInit (boolean x) { }
		
		
		if (x = true ) {
			// true = keep the initial parameters of RdmGsaNet
			System.out.println("GsNet initial parameters");

			Graph graph =setupGs.getGraph(setupGs.GsGraph);
		
		}
	

		*/
	
	
	

