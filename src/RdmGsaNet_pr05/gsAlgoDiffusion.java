package RdmGsaNet_pr05;


import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;


public class gsAlgoDiffusion implements gsAlgo {
			
		// variables
		diffusionType type;
//		morphogen morp;
		
		
		// costructor
		public gsAlgoDiffusion (diffusionType type
//				, morphogen morp
				) {
			this.type = type;
//			this.morp = morp;
		}

		
		public static double gsComputeDiffusion( diffusionType type , double speed , Graph graph, String morp) {
			double diffusion;
			switch (type) {
				case fick: {
					diffusion = speed * fick ( graph, morp ) ;
					break;
				}
			
				case perimeter : { diffusion = perimeter ( ) ; break; }
				
				case weigth : { diffusion = weigth ( ) ; break; }
			
				default: {
					diffusion = 0;
					System.out.println("diffusion type not defined");
				}
			}
			
			return diffusion;
		}


//------------------------------------------------------------------------------------------------------		
		// methods to define reaction type
		
		// classical reaction from Gray Scott model
		private static double fick (  Graph graph, String morp ) {
			for ( Node n : graph.getEachNode() ) {
				
				graph.getAttribute(morp);
				
				
				
			}
			
				
			
			
			
			
			
			return  0;
		}
		
		// diffusion not defined
		private static double perimeter (  ) {
			return 0 ;
		}

		// diffusion not defined
		private static double weigth (  ) {
			return 0 ;
		}




	}
