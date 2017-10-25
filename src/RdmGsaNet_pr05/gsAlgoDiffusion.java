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

		
		public static double gsComputeDiffusion( reactionType type ) {
		
			double diffusion;
			
			switch (type) {
			
				case ai2: {
					diffusion = fick (  ) ;
					break;
				}
			
				case linear : {
					diffusion = perimeter (  ) ;
					break;
				}
			
				default: {
					diffusion = 0;
					System.out.println("diffusion Type not defined");
				}
			}
			
			return diffusion;
		}


		@Override
		public void gsInit(boolean x) {
			}


		@Override
		public double gsCompute(double x, double y) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		// methods to define reaction type
		
		// classical reaction from Gray Scott model
		private static double fick (  ) {
			return  0;
		}
		
		// reaction not defined
		private static double perimeter (  ) {
			return 0 ;
		}

		




	}
