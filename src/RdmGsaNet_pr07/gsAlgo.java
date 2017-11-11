package RdmGsaNet_pr07;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

	public class gsAlgo   {
		
		// declare enum type
		public enum morphogen {activator, inhibitor }
		public enum interaction {reaction , diffusion, ext }
		
		public enum reactionType {ai2, linear }
		public enum diffusionType {fick, perimeter, weigth }
		public enum extType {gsModel, test}
		
		public enum DisMorpType {homo, random }
		
		 // declare new maps / map (key, double, double) = id node, act, inh
		static Map<String, ArrayList> mapMorp0 = new HashMap<String, ArrayList>();
		static Map<String, ArrayList> mapMorp1 = new HashMap<String, ArrayList>();
		
		// call setup variables
	//	int stopSim = setupGs.getStopSim();		
		static double Da  ;
		static double Di  ;
		static double feed ;
		static double kill ;
		
		static Graph graph = layerGs.getGraph(layerGs.gsGraph);
		
		public gsAlgo( double Da, double Di, double kill, double feed ) {
			this.Da = Da ;
			this.Di = Di ;
			this.kill = kill ;
			this.feed = feed ;
		}
		
		public static void setupAlgoValues (double Da, double Di, double kill, double feed) {	
			// setup values	
		}
				
		public static void gsAlgoMain (int stopSim, reactionType r, diffusionType d, extType e) {
					
			// simulation
			for (int step = 1 ; step <= stopSim; step++) {	
//					System.out.println(step);
				
				// start parameters from setup
				if ( step == 1) {
					
					for ( Node n : graph.getEachNode() ) {
						ArrayList<Double> ArList0 = new ArrayList<Double>() ;
					
						ArList0.add( (double) n.getAttribute( "GsAct" ) );
						ArList0.add( (double) n.getAttribute( "GsInh" ) );
					
						mapMorp0.put(n.getId(), ArList0 );	
					}
				}	
				
				// set parameters from previous step 
				else {
					for ( Node n : graph.getEachNode() ) {
						ArrayList<Double> ArList0 = mapMorp1.get(n.getId());
						
						mapMorp0.put(n.getId(), ArList0 );
					}
				}
				
				// Iterator simulation
				//declare iterator to go over each node
				Iterator<Node> iterNode = graph.iterator();
				
				while (iterNode.hasNext()) {
					
					Node n = iterNode.next();
					String id = n.getId();
//					System.out.println("node id " + id);
					
					ArrayList ArList0 = mapMorp0.get(n.getId()) ; 
					
					double act0 = (double) ArList0.get(0);
					double inh0 = (double) ArList0.get(1);
					
					double reaction = gsAlgoReaction.gsComputeReaction(reactionType.ai2, act0, inh0);
					
					double diffusionAct = gsAlgoDiffusion.gsComputeDiffusion(diffusionType.fick, Da , graph, "GsAct", id) ;
					double diffusionInh = gsAlgoDiffusion.gsComputeDiffusion(diffusionType.fick, Di , graph, "GsInh", id) ;
					
					double extFeed = gsAlgoExt.gsComputeExt(extType.gsModel,morphogen.activator , feed, kill , act0 , inh0 ) ;
					double extKill = gsAlgoExt.gsComputeExt(extType.gsModel, morphogen.inhibitor , feed, kill , act0 , inh0 ) ;
					
//					System.out.println("Da " + Da);
//					System.out.println("Di " + Di);
//					System.out.println("feed " + feed);
//					System.out.println("kill " + kill);
					
//					System.out.println();
//					System.out.println("reaction " + reaction);
//					System.out.println("diffusionAct " + diffusionAct);
//					System.out.println("diffusionInh " + diffusionInh);
//					System.out.println("extfeed " + extFeed);
//					System.out.println("extkill " + extKill);
					
					double act1 = act0 + diffusionAct - reaction + extFeed ;
					double inh1 = inh0 + diffusionInh + reaction - extKill ;
					
					// set act and inh in map1
					ArrayList<Double> ArList1 = new ArrayList<Double>() ;
				
					ArList1.add( act1 );
					ArList1.add( inh1 );
				
					mapMorp1.put(n.getId(), ArList1 );		
				}		
								
				// set map1 to graph	
				for ( Node n : graph.getEachNode() ) {
					
					ArrayList ArList1 = mapMorp1.get(n.getId()) ; 
					
					double act1 = (double) ArList1.get(0);
					double inh1 = (double) ArList1.get(1);
					
					n.setAttribute( "GsAct", act1);
					n.setAttribute( "GsInh", inh1);	
										
				}
				System.out.println("mapMorp0 " + mapMorp0);
				System.out.println("mapMorp1 " + mapMorp1);
			}
		}
	}
	
		
		/*
		 * // loop
				for ( Node n : graph.getEachNode() ) {
					
					ArrayList ArList0 = mapMorp0.get(n.getId()) ; 
				
					double act0 = (double) ArList0.get(0);
					double inh0 = (double) ArList0.get(1);
					
					double reaction = gsAlgoReaction.gsComputeReaction(reactionType.ai2, act0, inh0);
					
					double diffusionAct = gsAlgoDiffusion.gsComputeDiffusion(diffusionType.fick, Da, graph, "GsAct");
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
		
		

		*/
	
	
	

