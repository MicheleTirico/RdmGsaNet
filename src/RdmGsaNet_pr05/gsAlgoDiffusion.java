package RdmGsaNet_pr05;


import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;


public class gsAlgoDiffusion implements gsAlgo {
			
		// variables
		//public enum reactionType {ai2, linear }
		diffusionType type;
//		morphogen morp;
		
		
		// costructor
		public gsAlgoDiffusion (diffusionType type
//				, morphogen morp
				) {
			this.type = type;
//			this.morp = morp;
		}

		
		public static double gsComputeDiffusion( diffusionType type , double speed ) {
			double diffusion;
			switch (type) {
				case fick: {
					diffusion = speed * fick (  ) ;
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
		private static double fick (  ) {
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
