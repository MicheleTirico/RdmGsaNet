package RdmGsaNet_pr09;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import com.sun.org.apache.bcel.internal.generic.NEWARRAY;

import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNet_pr09.layerNet.meanPointPlace;
import RdmGsaNet_pr09.setupNetSmallGraph.smallGraphType;
import javafx.collections.transformation.SortedList;


public class setupNetFistfulNodes  implements setupNet_Inter {

	// COSTANTS
		private int fistfulOfNodes ;
		
		protected enum typeRadius { circle , square }
		private typeRadius typeRadius ;
		
		private static double 	minX , minY , maxX , maxY , 
								radius ;
		
		// get graphs
		private static Graph 	gsGraph = layerGs.getGraph() ,
								netGraph = layerNet.getGraph() ;
			
		// COSTRUCTOR
		public setupNetFistfulNodes  ( int fistfulOfNodes , typeRadius typeRadius ,  double radius ) {
			this.fistfulOfNodes = fistfulOfNodes ;
			this.typeRadius = typeRadius  ;
			this.radius = radius ;
		}
		
		public void createLayerNet() {
			
			String idMeanPoint = setupNet_Inter.getMeanPointStr( gsGraph ) ;		//	System.out.println(idMeanPoint);
			
			netGraph.addNode("0");
			
			// set coordinate
			Node nFrom = gsGraph.getNode(idMeanPoint); 
			Node netMeanPoint = netGraph.getNode("0") ;
			gsAlgoToolkit.setNodeCoordinateFromNode(gsGraph, netGraph, nFrom, netMeanPoint);		
		
			// get coordinate
			double[] meanPointCoord = GraphPosLengthUtils.nodePosition(netMeanPoint) ;
			System.out.println(idMeanPoint);
			
			
			switch (typeRadius ) {
			case square :
				for ( int x = 1 ; x < fistfulOfNodes ; x++ )
					createNodesInSquare( x, netGraph , meanPointCoord );
				break;

			case circle : {
				createNodeInCircle () ;
			}
			break ;
			}
			
			for ( Node nNet : netGraph.getEachNode() ) {
				Map<String , Double > mapDist = gsAlgoToolkit.getMapIdDist(netGraph, nNet) ;		//	System.out.println(mapDist);
				ArrayList<Double> listDist = new ArrayList<Double>(mapDist.values());
				Collections.sort(listDist);															//	System.out.println(listDist);
				
				for ( int x = 0 ; x < netGraph.getNodeCount() ; x++ ) {
				
					double distMin = listDist.get(x) ;
					Set<String> setIdNear =  gsAlgoToolkit.getKeysByValue(mapDist, distMin) ;
					String idNear = setIdNear.stream().findFirst().get() ;
					Node nodeNear = netGraph.getNode(idNear) ;
					try {
						createEdge(netGraph, nNet, nodeNear);
						break ; 
					} catch ( org.graphstream.graph.EdgeRejectedException e) {
						// TODO: handle exception
					}	
				}
			}
			
			System.out.println(netGraph.getNodeSet());
			netGraph.display(false ) ;
			
		}

		@Override
		public void setMeanPoint(meanPointPlace point) {
			setupNet_Inter.setMeanPointInter(gsGraph, point);	
		}
		
	// PRIVATE METHODS ----------------------------------------------------------------------------------------------------------------------------------
		
		private static void createNodeInCircle () {			}
		
		private static Node createNodesInSquare ( int nextId , Graph graph , double[] meanPointCoord  ) {
			
			minX = meanPointCoord[0] - radius;
			maxX = meanPointCoord[0] + radius;
			minY = meanPointCoord[1] - radius;
			maxY = meanPointCoord[1] + radius;
			
			double sizeSquare = maxX - minX ;
			
			Random rnd = new Random();
			
			double rndX =  minX + rnd.nextDouble() * sizeSquare ;
			double rndY =  minY + rnd.nextDouble() * sizeSquare ;
			
			String idNewNode = Integer.toString( nextId ) ;  
			graph.addNode( idNewNode ) ;
			
			Node newNode = graph.getNode(idNewNode);
			newNode.setAttribute("xyz", rndX , rndY , 0 );
			
			return newNode ;
			
			
			
			
			
			
			
			
			
		}
		
		// method to create edge
		private static void createEdge ( Graph graph,  Node n1 , Node n2 ) {
		
			String idEdge = n1.getId() + "-" + n2.getId() ;
			if ( n1.getId() != n2.getId() )
				graph.addEdge(idEdge, n1, n2 );
		}
		

}
