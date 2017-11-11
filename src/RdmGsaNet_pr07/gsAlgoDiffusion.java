package RdmGsaNet_pr07;


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
		
		// call map from gsAlgo
		static Map mapMorp0 = gsAlgo.mapMorp0; 
		static Map mapMorp1 = gsAlgo.mapMorp1; 
				
		// costructor
		public gsAlgoDiffusion (gsAlgo.diffusionType type,  gsAlgo.morphogen morp ) {
			this.type = type;
			this.morp = morp;
		}
		
		public static double gsComputeDiffusion( gsAlgo.diffusionType type , double speed , Graph graph, String morp, String id) {
			double diffusion;			
			
			switch (type) {
				case fick: {
					diffusion = speed * fick ( graph, morp, id) ;
					break;
				}
			
				case perimeter : { diffusion = perimeter ( ) ; break; }
				case weigth : { diffusion = weigth ( ) ; break; }
			
				default: { System.out.println("diffusion type not defined") ; diffusion = 0; }
			}
			
			return diffusion;
		}

//------------------------------------------------------------------------------------------------------		
		// methods to define reaction type
		
		// classical reaction from Gray Scott model
		private static double fick (  Graph graph, String morp , String id ) {
			
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
			if (morp == "GsAct")	{	morpInt = 0 ;	}
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
				sumNeig = sumNeig + d0Neig;			//	System.out.println(neig.getId() + "  " +neigList0);				String a = neig.getId() ;		System.out.println(a);
			}										//			System.out.println("sumNeig " + sumNeig);
			
			diffusion = degree * d0 - sumNeig ;		//	System.out.println(diffusion);
			return diffusion ;
		}
			
			/*
			Node n = graph.getNode(id);
			Iterator<Node> iterNeig = n.getNeighborNodeIterator();
			
			int morpInt;
			if (morp == "GsAct")	{	morpInt = 0 ;	}
			else 					{	morpInt = 1 ;	}

			double sumNeig = 0;
			int degree = n.getDegree();
			
			ArrayList<Double> listMap0 = (ArrayList<Double>) mapMorp0.get(n.getId());			
			double nodeMorp0 = listMap0.get(morpInt);

			while ( iterNeig.hasNext() ) {
				Node nodeNeig = iterNeig.next();
				
				ArrayList<Double> listMorpO = (ArrayList<Double>) mapMorp0.get(id);
			
//				System.out.println(listMorpO);
					
				double morp0 = listMorpO.get(morpInt);

//				System.out.println(morp0);

//				double x = 0; 
						
				sumNeig = sumNeig + morp0;
			}
//			System.out.println("degree " + degree);
//			System.out.println("nodeMorp0 " + nodeMorp0);
//			System.out.println("sumneig " + sumNeig);

			

			diffusion = degree * nodeMorp0 - sumNeig;

			return diffusion;
		}
		
			/*
		 
			// variables
			Node n = graph.getNode(id);
			ArrayList<Double> listNeig = new ArrayList<>();
			Iterator<Node> iterNeig = n.getNeighborNodeIterator();
			
			double diffNeig = 0;
			double sumNeig = 0 ;
			
			int morpInt;
			
			if (morp == "GsAct")	{	morpInt = 0 ;	}
			else 					{	morpInt = 1 ;	}

			ArrayList<Double> listMap0 = (ArrayList<Double>) mapMorp0.get(n.getId());
//			System.out.println(listMap0);
			listMap0.get(1);
			
			double nodeMorp0 = listMap0.get(morpInt);
//			double nodeMorp0 = n.getAttribute(morp);
			int degree = n.getDegree();
			while (iterNeig.hasNext () ) {
				
				Node nodeNeig = iterNeig.next();
				diffNeig = diffNeig + (double) nodeNeig.getAttribute(morp);
				listNeig.add(diffNeig);
			}
			
//			System.out.println(listNeig);
			
			sumNeig = listNeig.get(listNeig.size() - 1 ) ;
			
			// sum the values of neighbors
			for (double v : listNeig) { sumNeig += v; }
			
//				System.out.println(nodeMorp0);
//				System.out.println(sumNeig);
			double diffusion = degree * nodeMorp0 - sumNeig;
			
//				System.out.println(diffusion);
//				System.out.println(nodeNeig);
//				System.out.println(nodeConputeDiff.getId() +  listNeig);	
//				System.out.println(nodeConputeDiff);
			return  diffusion;
			*/
		
		
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
