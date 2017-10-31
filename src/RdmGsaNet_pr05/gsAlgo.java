package RdmGsaNet_pr05;

import java.awt.List;
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
		
		public static void gsAlgoMain (reactionType r, diffusionType d, extType e) {
			
			int stopSim = setupGs.getStopSim();
			int step ;
			double Da = setupGs.getDa() ;
			double Di = setupGs.getDi() ;
			
			// call graph Gs 
			Graph graph = setupGs.getGraph( setupGs.GsGraph );
			
			// declare new maps / map (key, double, double) = id node, act, inh
			Map<String, ArrayList> mapMorp0 = new HashMap<String, ArrayList>();
			Map<String, ArrayList> mapMorp1 = new HashMap<String, ArrayList>();

			// simulation
			for (step = 1 ; step <= stopSim; step++) {
				
				// start parameters from setup
				if ( step == 1) {
					
					for ( Node n : graph.getEachNode() ) {
						ArrayList<Double> ArList0 = new ArrayList<Double>() ;
					
						ArList0.add( (double) n.getAttribute( "GsAct" ) );
						ArList0.add( (double) n.getAttribute( "GsInh" ) );
					
						mapMorp0.put(n.getId(), ArList0 );	
					}
				}	
				
				// set parameters from previus step 
				else {
					for ( Node n : graph.getEachNode() ) {
						ArrayList<Double> ArList0 = mapMorp1.get(n.getId());
						
						mapMorp0.put(n.getId(), ArList0 );
					}
				}
				
				for ( Node n : graph.getEachNode() ) {
					
					ArrayList ArList0 = mapMorp0.get(n.getId()) ; 
				
					double act0 = (double) ArList0.get(0);
					double inh0 = (double) ArList0.get(1);
					
					double reaction = gsAlgoReaction.gsComputeReaction(reactionType.ai2, act0, inh0);
					
					double diffusionAct = gsAlgoDiffusion.gsComputeDiffusion(diffusionType.fick, Da, graph);
//					double diffusionInh = gsAlgoDiffusion.gsComputeDiffusion(diffusionType.fick, Di);
//					double extFeed = gsAlgoExt.gsComputeExt(extType.gsModel, morphogen.activator,feed, kill, act, inh ) ;
//					double extKill = gsAlgoExt.gsComputeExt(extType.gsModel, morphogen.inhibitor,feed, kill, act, inh ) ;
					
					double act1 = act0  + reaction;
					double inh1 = inh0  - reaction;
					
//					double act1 = act0 + diffusionAct + reaction;
//					double inh1 = inh0 + diffusionInh - reaction;
					
					// set act and inh in map1
					ArrayList<Double> ArList1 = new ArrayList<Double>() ;
				
					ArList1.add( act1 );
					ArList1.add( inh1 );
				
					mapMorp1.put(n.getId(), ArList1 );	
				}

//				System.out.println(mapMorp0);
//				System.out.println(mapMorp1);
			}
		}	
	}
		
		/*
		
		public enum morphogen {activator, inhibitor }
		public enum interaction {reaction , diffusion, ext }
		
		public enum reactionType {ai2, linear }
		public enum diffusionType {fick, perimeter, weigth }
		public enum extType {gsModel, test}
		
		public static void gsAlgoMain (reactionType r, diffusionType d, extType e) {
			
			int stopSim = setupGs.getStopSim();
			int step ;
			double Da = setupGs.getDa() ;
			double Di = setupGs.getDi() ;	 
			
//			mapMorp0 , mapMorp1
//			ArList0 , ArList1
			
			
			for ( step = 1 ; step <= stopSim ; step++ ) {		
				
				// mapMor0 mapMorp1
				Map<String, ArrayList> mapMorp0 = new HashMap<String, ArrayList>();
				Map<String, ArrayList> mapMorp1 = new HashMap<String, ArrayList>();
				
				Graph graph = setupGs.getGraph( setupGs.GsGraph );
				
			//	double act0 = 0 , inh0 = 0;
		
				System.out.println(step);
				
				if (step == 1) {
					
					for ( Node n : graph.getEachNode() ) {
						
						// set act and inh in map0
						ArrayList<Double> ArList0 = new ArrayList<Double>() ;
				
						ArList0.add( (double) n.getAttribute( "GsAct" ) );
						ArList0.add( (double) n.getAttribute( "GsInh" ) );
						
						mapMorp0.put(n.getId(), ArList0 );	
					}
				}
		
				
				else {
					for (Map.Entry<String, ArrayList> entry : mapMorp0.entrySet()) {
						
						ArrayList<Double> ArList1= new ArrayList<Double>();
						ArList1.add((double) 22);
						ArList1.add((double) 21);
//						ArrayList<Double> list1 = mapMorp1.get(entry.getKey());
				//		System.out.println(list1) ;
						
						ArrayList ArList0 = entry.setValue(ArList1);
						
						
						
					}
				}
			
				
				// loop
				for ( Node n : graph.getEachNode() ) {
					
					ArrayList ArList0 = mapMorp0.get(n.getId()) ; 
				
					
					
					double act0 = (double) ArList0.get(0);
//					double inh0 = (double) ListMorp.get(1);
					
//					double reaction = gsAlgoReaction.gsComputeReaction(reactionType.ai2, act0, inh0);
					
//					double diffusionAct = gsAlgoDiffusion.gsComputeDiffusion(diffusionType.fick, Da);
//					double diffusionInh = gsAlgoDiffusion.gsComputeDiffusion(diffusionType.fick, Di);
//					double extFeed = gsAlgoExt.gsComputeExt(extType.gsModel, morphogen.activator,feed, kill, act, inh ) ;
//					double extKill = gsAlgoExt.gsComputeExt(extType.gsModel, morphogen.inhibitor,feed, kill, act, inh ) ;
					
//					double act1 = act0  + reaction;
//					double inh1 = inh0  - reaction;
					
//					double act1 = act0 + diffusionAct + reaction;
//					double inh1 = inh0 + diffusionInh - reaction;
					
					// set act and inh in map1
//					ArrayList<Double> ArListMorp1 = new ArrayList<Double>() ;
				
//					ArListMorp1.add( act1);
//					ArListMorp1.add( inh1);
				
//					mapMorp1.put(n.getId(), ArListMorp1 );	
				}
				
				for (Map.Entry<String, ArrayList> entry : mapMorp0.entrySet()) {

					ArrayList<Double> ArList1= new ArrayList<Double>();
					ArList1.add((double) 22);
					ArList1.add((double) 21);
//					ArrayList<Double> list1 = mapMorp1.get(entry.getKey());
			//		System.out.println(list1) ;
					
					ArrayList ArList0 = entry.setValue(ArList1);
					
					
					
				}
			}
			
		}
	}

	
	/*
	
	public enum morphogen {activator, inhibitor }
	public enum interaction {reaction , diffusion, ext }
	
	public enum reactionType {ai2, linear }
	public enum diffusionType {fick, perimeter, weigth }
	public enum extType {gsModel, test}
	
	public static void gsAlgoMain (reactionType r, diffusionType d, extType e) {
		
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
				
			if (step == 1 ) {
				ArListMorp0.add( (double) n.getAttribute( "GsAct" ) );
				ArListMorp0.add( (double) n.getAttribute( "GsInh" ) );
			}
			else {
				ArListMorp0 = mapMorp1.get(n.getId());
				System.out.println(ArListMorp0);
		
			}
				
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
				
			//	System.out.println( mapMorp1.get(n.getId() ) );
			}
			
			// per ogni key di map0, sostituire il value (list) di map1
			for (Map.Entry<String, ArrayList> entry : mapMorp0.entrySet()) {
				

				ArrayList<Double> list1 = mapMorp1.get(entry.getKey());
				System.out.println(list1) ;
				
				ArrayList list0 = entry.setValue(list1);
	
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
		
		

		*/
	
	
	

