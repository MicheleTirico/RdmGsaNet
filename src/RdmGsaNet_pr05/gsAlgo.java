package RdmGsaNet_pr05;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import scala.collection.parallel.ParIterableLike.Foreach;


public interface gsAlgo   {
	
	public enum morphogen {activator, inhibitor }
	public enum interaction {reaction , diffusion, ext }
	
	public enum reactionType {ai2, linear }
	public enum diffusionType {fick, perimeter, weigth }
	public enum extType {gsModel, test}
	
	public static <S> void gsAlgoMain (reactionType r, diffusionType d, extType e) {
		
		int stopSim = setupGs.getStopSim();
		int step ;
		double Da = setupGs.getDa() ;
		double Di = setupGs.getDi() ;

//-----------------------------------------------------------------------------------------------------------		
//	DEFINE EQUATIONS
	
	//	double feed = setupGs.getFeed();
	//	double kill = setupGs.getKill();
		
		
	//	double reaction = gsAlgoReaction.gsComputeReaction(reactionType.ai2, act, inh);
	//	double diffusion = gsAlgoDiffusion.gsComputeDiffusion(diffusionType.fick);
	//	double extFeed = gsAlgoExt.gsComputeExt(extType.gsModel, morphogen.activator,feed, kill, act, inh ) ;
	//	double extKill = gsAlgoExt.gsComputeExt(extType.gsModel, morphogen.inhibitor,feed, kill, act, inh ) ;

//-----------------------------------------------------------------------------------------------------------		
//	LOOP SIMULATION	
		
		Graph graph = setupGs.getGraph( setupGs.GsGraph );
		for ( step = 1 ; step <= stopSim ;  step++  ) {
			
			System.out.println(step);
			
			
			// map0 (key, double, double) = id node, act, inh
			
			// CREATE MAP0 AND MAP1
			
			// mapMor0
			Map<String, ArrayList> mapMorp0 = new HashMap<String, ArrayList>();
			
			// mapMorp1
			Map<String, ArrayList> mapMorp1 = new HashMap<String, ArrayList>();
			
			// SET MORP IN MAP0
			for ( Node n : graph.getEachNode() ) {
				

				// set act and inh in map0
				ArrayList<Double> ArListMorp0 = new ArrayList<Double>() ;
			
				ArListMorp0.add( (double) 0 );
				ArListMorp0.add( (double) 0 );
			
				ArListMorp0.set(0, (double) n.getAttribute( "GsAct" )) ;
				ArListMorp0.set(1, (double) n.getAttribute( "GsInh" )) ;
				
				
				mapMorp0.put(n.getId(), ArListMorp0 );
				
			}
			
			// LOOP SIMULATION
			
			
			for ( Node n : graph.getEachNode() ) {
		
				ArrayList ListMorp = mapMorp0.get(n.getId()) ; 
				
				double act0 = (double) ListMorp.get(0);
				double inh0 = (double) ListMorp.get(1);
				
				double reaction = gsAlgoReaction.gsComputeReaction(reactionType.ai2, act0, inh0);
				
//				double diffusionAct = gsAlgoDiffusion.gsComputeDiffusion(diffusionType.fick, Da);
//				double diffusionInh = gsAlgoDiffusion.gsComputeDiffusion(diffusionType.fick, Di);
//				double extFeed = gsAlgoExt.gsComputeExt(extType.gsModel, morphogen.activator,feed, kill, act, inh ) ;
//				double extKill = gsAlgoExt.gsComputeExt(extType.gsModel, morphogen.inhibitor,feed, kill, act, inh ) ;
				
				double act1 = act0  + reaction;
				double inh1 = inh0  - reaction;
				
//				double act1 = act0 + diffusionAct + reaction;
//				double inh1 = inh0 + diffusionInh - reaction;
				
				// set act and inh in map1
				ArrayList<Double> ArListMorp1 = new ArrayList<Double>() ;
			
				ArListMorp1.add( act1);
				ArListMorp1.add( inh1);
			
				mapMorp1.put(n.getId(), ArListMorp1 );				
				
			}
			// per ogni key di map0, sostituire il value (list) di map1
			for (Entry<String, ArrayList> entry : mapMorp0.entrySet()) {
				
				ArrayList<Double> list0 = new ArrayList<Double>() ;
				ArrayList<Double> list1 = new ArrayList<Double>() ;
				
				double act = 1 ; 
				double inh = 0 ; 
				list0.add(act);
				list0.add(inh);
				
				mapMorp0.put(entry.getKey(), list0);
				
				
				
			}
			
			
		
			
			System.out.println(mapMorp1);
			
			// create map0  and map1
			
			// set morp in map0
			
			// loop execute gsalgo (for each node )
				
				// act-inh = act-inh(in map) + d + r + e 
			
				// set act-inh in map1
			
			// loop set act-inh in map1 (for each node )
			
		}
	}
	
}
			/*
			for(  Node n : graph.getEachNode () ) {
				
				double OldAct, OldInh;
				
				double act = n.getAttribute( "GsAct" ) ; 
				double inh = n.getAttribute( "GsInh") ; 
				
				OldAct = act; 
				OldInh = inh;
				
				double reaction = gsAlgoReaction.gsComputeReaction(reactionType.ai2, OldAct, OldInh);
				
				double diffusionAct = gsAlgoDiffusion.gsComputeDiffusion(diffusionType.fick, Da);
				double diffusionInh = gsAlgoDiffusion.gsComputeDiffusion(diffusionType.fick, Di);
				double extFeed = gsAlgoExt.gsComputeExt(extType.gsModel, morphogen.activator,feed, kill, act, inh ) ;
				double extKill = gsAlgoExt.gsComputeExt(extType.gsModel, morphogen.inhibitor,feed, kill, act, inh ) ;
				
			
				
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
	
	
	

