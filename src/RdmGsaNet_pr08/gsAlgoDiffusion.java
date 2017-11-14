package RdmGsaNet_pr08;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import com.sun.org.apache.xml.internal.security.utils.SignerOutputStream;

public class gsAlgoDiffusion  {
			
		// variables
		gsAlgo.diffusionType type;
		gsAlgo.morphogen morp;
		
		// constructor ( define type of diffusion and morphogen )
		public gsAlgoDiffusion (gsAlgo.diffusionType type,  gsAlgo.morphogen morp ) {
			this.type = type;
			this.morp = morp;
		}
		
		// this method return the value of diffusion. 
		public static double gsComputeDiffusion( gsAlgo.diffusionType type , double speed , Graph graph, String morp, String id, Map mapMorp0 ) {
			double diffusion;			
			
			switch (type) {
				case fick: {
					diffusion = speed * fick ( graph, morp, id, mapMorp0 ) ;
					break;
				}
				// diffusion not yet developed 
				case perimeter : { diffusion = perimeter ( ) ; break; }
				case weigth : { diffusion = weigth ( ) ; break; }
			
				default: { System.out.println("diffusion type not defined") ; diffusion = 0; }
			}	
			return diffusion;
		}

//------------------------------------------------------------------------------------------------------		
		// methods to define diffusion type
		
		// classical diffusion, equilibrium of mass
		private static double fick (  Graph graph, String morp , String id , Map mapMorp0) {
			
			// local variables
			double diffusion = 0;
			double d0 = 0 ;
			double d0Neig = 0 ;
			double sumNeig = 0 ;
			int degree = 0 ;
			
			// iterator
			Node n = graph.getNode(id);
			Iterator<Node> iter = n.getNeighborNodeIterator() ;		//	System.out.println("id " + n);
			
			// list mapMorp0
			ArrayList d0list0 = (ArrayList) mapMorp0.get(id) ;		//	System.out.println(list0);
			
			// define morhogen 
			int morpInt;
			if (morp == "gsAct")	{	morpInt = 0 ;	}
			else 					{	morpInt = 1 ;	}			//	System.out.println(morp);
			
			// degree
			degree = n.getInDegree(); 	//	System.out.println(id + " degree " + degree);
			
			// count d0
			d0 = (double) d0list0.get(morpInt);		//	System.out.println(mapMorp0);		System.out.println("d0 " + d0);
			
			// count d0Neig
			while (iter.hasNext()) {
				 
				Node neig = iter.next() ;
				ArrayList<Double> neigList0 = (ArrayList) mapMorp0.get(neig.getId()) ;
				
				d0Neig =  neigList0.get(morpInt);	//	System.out.println("d0Neig " + d0Neig);
				sumNeig = sumNeig + d0Neig;			//	System.out.println(neig.getId() + "  " +neigList0);	String a = neig.getId() ;		System.out.println(a);
			}										//	System.out.println("sumNeig " + sumNeig);
			
			// compute fick's diffusion
			diffusion = degree * d0 - sumNeig ;		//	System.out.println(diffusion);
			return diffusion ;
		}
			
	
//----------------------------------------------------------------------------------------------------------------------------------
		
		// diffusion not defined
		private static double perimeter (  ) {
			double diffusion = 0;
			return diffusion ;
		}

		// diffusion not defined
		private static double weigth (  ) {
			double diffusion = 0;
			return diffusion ;
		}
	}
