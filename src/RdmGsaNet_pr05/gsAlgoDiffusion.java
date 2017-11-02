package RdmGsaNet_pr05;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;


public class gsAlgoDiffusion implements gsAlgo {
			
		// variables
		diffusionType type;
		morphogen morp;
		
		// call map from gsAlgo
		static Map mapMorp0 = gsAlgo.mapMorp0; 
		static Map mapMorp1 = gsAlgo.mapMorp1; 
		
		
		// costructor
		public gsAlgoDiffusion (diffusionType type,  morphogen morp ) {
			this.type = type;
			this.morp = morp;
		}
		
		public static double gsComputeDiffusion( diffusionType type , double speed , Graph graph, String morp, String id) {
			double diffusion;
			switch (type) {
				case fick: {
					diffusion = speed * fick ( graph, morp, id) ;
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
		private static double fick (  Graph graph, String morp , String id) {
		/* PASSAGGI DA FARE
		 * 1. lavorare con un iterator
		 * per ogni nodo , prendi il vicino, prendi il valore di morp da map0, calcola la difusione , carica in map1 */
		
			// variables
			Node n = graph.getNode(id);
			ArrayList<Double> listNeig = new ArrayList<>();
			Iterator<Node> iterNeig = n.getNeighborNodeIterator();
			
			double diffNeig = 0;
			double sumNeig = 0 ;
			
			while (iterNeig.hasNext () ) {
				
				Node nodeNeig = iterNeig.next();
				diffNeig = diffNeig + (double) nodeNeig.getAttribute(morp);
				listNeig.add(diffNeig);
			}
			
//			System.out.println(listNeig);
			
			// sum the values of neighbors
			for (double v : listNeig) { sumNeig += v; }
			// listNeig.forEach( v -> v += v );
			
			double nodeMorp0 = n.getAttribute(morp);
//			System.out.println(nodeMorp0);
//			System.out.println(sumNeig);
			double diffusion = nodeMorp0 - sumNeig;
			
//				System.out.println(diffusion);
//				System.out.println(nodeNeig);
//				System.out.println(nodeConputeDiff.getId() +  listNeig);	
//				System.out.println(nodeConputeDiff);
			return  diffusion;
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
/*
 * 	
			Iterator<Node> iterNodeDiff = graph.iterator();
			
			while ( iterNodeDiff.hasNext() ) {
				
				Node nodeConputeDiff = iterNodeDiff.next();	
				ArrayList<Double> listNeig = new ArrayList<>();
				Iterator<Node> iterNeig = nodeConputeDiff.getNeighborNodeIterator();
				
				double diffNeig = 0;
				double sumNeig = 0 ;
				
				while (iterNeig.hasNext () ) {
					
					Node nodeNeig = iterNeig.next();
					diffNeig = diffNeig + (double) nodeNeig.getAttribute(morp);
					listNeig.add(diffNeig);
				}
				
//				System.out.println(listNeig);
				
				// sum the values of neighbors
				for (double v : listNeig) { sumNeig += v; }
				// listNeig.forEach( v -> v += v );
				
				double nodeMorp0 = nodeConputeDiff.getAttribute(morp);
				
				*/
	
