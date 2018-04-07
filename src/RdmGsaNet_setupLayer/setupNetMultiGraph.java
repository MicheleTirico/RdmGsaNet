package RdmGsaNet_setupLayer;

import java.util.ArrayList;

import org.graphstream.algorithm.Kruskal;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNetAlgo.graphGenerator;
import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNetAlgo.graphGenerator.spanningTreeAlgo;
import RdmGsaNet_mainSim.layerGs;
import RdmGsaNet_mainSim.layerNet;
import RdmGsaNet_mainSim.layerNet.meanPointPlace;

public class setupNetMultiGraph implements setupNet_Inter {

	// COSTANTS
		// get graphs
		private static Graph 	gsGraph = layerGs.getGraph() ,
								netGraph = layerNet.getGraph() ;
		
		double radiusStartPoint , raidiusSmallGraph  ;
		int randomSeed ;
		
		private int numberStartPoint ;
		private int sizeGraph ;
		private boolean isSpanningTree ;		
		
		public setupNetMultiGraph(int numberStartPoint , double radiusStartPoint , int sizeGraph , double raidiusSmallGraph , boolean isSpanningTree , int randomSeed ) {
			this.numberStartPoint = numberStartPoint ;
			this.sizeGraph = sizeGraph ;
			this.radiusStartPoint = radiusStartPoint ;
			this.isSpanningTree  = isSpanningTree  ;
			this.raidiusSmallGraph = raidiusSmallGraph ;
			this.randomSeed = randomSeed ;
		}
	
	
	@Override
	public void createLayerNet() {
		
		String idNodeCenter = setupNet_Inter.getMeanPointStr( gsGraph ) ;		//	System.out.println(idMeanPoint);
		
		netGraph.addNode("0");
		 
		Node nodeCenter = netGraph.getNode("0") ;
		// set coordinate
		Node nFrom = gsGraph.getNode(idNodeCenter); 
		gsAlgoToolkit.setNodeCoordinateFromNode(gsGraph, netGraph, nFrom, nodeCenter);		
	
		// get coordinate
		double[] meanPointCoord = GraphPosLengthUtils.nodePosition(nodeCenter) ;		//	System.out.println(idMeanPoint);
		
		netGraph.removeNode(nodeCenter ) ;
		
		ArrayList<Node> listNodes = graphGenerator.createListNodeInSquare(netGraph, numberStartPoint, meanPointCoord, radiusStartPoint , randomSeed ) ;	//		System.out.println(listNodes);		System.out.println(netGraph.getNodeCount() );
		
		for ( Node nodeCenterSmallGraph : listNodes) {
			double[] nCoord  = GraphPosLengthUtils.nodePosition(nodeCenterSmallGraph) ;		
			ArrayList<Node> listNodeMultiGraph = graphGenerator.createListNodeInSquare(netGraph, sizeGraph, nCoord, raidiusSmallGraph , randomSeed );
			randomSeed++ ;

			if ( isSpanningTree ) {
				
				listNodeMultiGraph.add(nodeCenterSmallGraph) ;
				// create CompleteGraph
				graphGenerator.createCompleteGraphFromListNode(netGraph, listNodeMultiGraph );
				
				// create spanning tree
				graphGenerator.createSpaningTree(netGraph, spanningTreeAlgo.krustal);
				
				/*
				// add attribute dist of Edge
				for ( Edge e : netGraph.getEachEdge() ) {
			
					Node 	n0 = e.getNode0() ,
							n1 = e.getNode1() ;
					
					double dist = gsAlgoToolkit.getDistGeom(n0, n1);
					e.addAttribute("weight", dist );
				}
					
				// compute Krustal algoritm
				Kruskal kruskal = new Kruskal( "tree" , true , false ) ;
				kruskal.init(netGraph) ;
				kruskal.compute();
				
				// create list of edge to remove
				ArrayList<Edge> listEdgeToRemove = new ArrayList<Edge> () ;
				for ( Edge e : netGraph.getEachEdge()) {
				
					boolean tree = e.getAttribute("tree");	
					if ( tree == false )
						listEdgeToRemove.add(e);	
				}
			
				// remove edge
				for ( Edge e : listEdgeToRemove) 
					netGraph.removeEdge(e) ;
				*/
//				netGraph.removeNode(nodeCenterSmallGraph);
			}
		}
	
	}
	
		

	@Override
	public void setMeanPoint(meanPointPlace point) {
		setupNet_Inter.setMeanPointInter(gsGraph, point);	
	}

}
