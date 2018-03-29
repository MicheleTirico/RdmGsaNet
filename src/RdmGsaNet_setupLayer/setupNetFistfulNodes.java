package RdmGsaNet_setupLayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNet_mainSim.layerGs;
import RdmGsaNet_mainSim.layerNet;
import RdmGsaNet_mainSim.layerNet.meanPointPlace;
import RdmGsaNet_mainSim.main;

public class setupNetFistfulNodes extends main implements setupNet_Inter {

	// COSTANTS
		private int fistfulOfNodes ;
		
		public enum typeRadius { circle , square }
		private typeRadius typeRadius ;
		
		private static double 	minX , minY , maxX , maxY , 
								radius ;
		
		private static boolean createEdge ;
		
		// get graphs
		private static Graph 	gsGraph = layerGs.getGraph() ,
								netGraph = layerNet.getGraph() ;
			
		// COSTRUCTOR
		public setupNetFistfulNodes  ( int fistfulOfNodes , typeRadius typeRadius ,  double radius , boolean createEdge) {
			this.fistfulOfNodes = fistfulOfNodes ;
			this.typeRadius = typeRadius  ;
			this.radius = radius ;
			this.createEdge = createEdge ;
		}
		
		public void createLayerNet() {
			
			String idMeanPoint = setupNet_Inter.getMeanPointStr( gsGraph ) ;		//	System.out.println(idMeanPoint);
			
			netGraph.addNode("0");
			
			// set coordinate
			Node nFrom = gsGraph.getNode(idMeanPoint); 
			Node netMeanPoint = netGraph.getNode("0") ;
			gsAlgoToolkit.setNodeCoordinateFromNode(gsGraph, netGraph, nFrom, netMeanPoint);		
		
			// get coordinate
			double[] meanPointCoord = GraphPosLengthUtils.nodePosition(netMeanPoint) ;		//	System.out.println(idMeanPoint);
			
			switch (typeRadius ) {
			case square :
				for ( int x = 1 ; x < fistfulOfNodes ; x++ )
					layerNet.createNodesInSquare( x , netGraph , meanPointCoord , radius );
				break;

			case circle : {
				createNodeInCircle () ;
			}
			break ;
			}
			
			int idEdgeInt = 0 ;
			for ( Node nNet : netGraph.getEachNode() ) {
				Map<String , Double > mapDist = gsAlgoToolkit.getMapIdDist(netGraph, nNet) ;		//	System.out.println(mapDist);
				ArrayList<Double> listDist = new ArrayList<Double>(mapDist.values());
				Collections.sort(listDist);															//	System.out.println(listDist);
				
				for ( int x = 0 ; x < netGraph.getNodeCount() ; x++ ) {
				
					double distMin = listDist.get(x) ;
					Set<String> setIdNear =  gsAlgoToolkit.getKeysByValue(mapDist, distMin) ;
					String idNear = setIdNear.stream().findFirst().get() ;
					Node nodeNear = netGraph.getNode(idNear) ;
	
					if ( createEdge ) {
						String idEdge = Integer.toBinaryString(idEdgeInt) ;
						try {
							netGraph.addEdge(idEdge, nNet, nodeNear) ;
							idEdgeInt++ ;
							
							break ; 
						} catch ( org.graphstream.graph.EdgeRejectedException e) {
							// TODO: handle exception
						}	
					}
					break ;
				}
			}
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
}
