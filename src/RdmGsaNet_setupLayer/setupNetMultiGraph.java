package RdmGsaNet_setupLayer;

import java.util.ArrayList;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNetAlgo.graphGenerator;
import RdmGsaNetAlgo.gsAlgoToolkit;
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
		private boolean createEdge ;		
		
		public setupNetMultiGraph(int numberStartPoint , double radiusStartPoint , int sizeGraph , double raidiusSmallGraph , boolean createEdge , int randomSeed ) {
			this.numberStartPoint = numberStartPoint ;
			this.sizeGraph = sizeGraph ;
			this.radiusStartPoint = radiusStartPoint ;
			this.createEdge  = createEdge  ;
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
		
		ArrayList<Node> listNodes = graphGenerator.createListNodeInSquare(netGraph, numberStartPoint, meanPointCoord, radiusStartPoint , randomSeed ) ;	//	
		System.out.println(listNodes);		System.out.println(netGraph.getNodeCount() );
		
		for ( Node nodeCenterSmallGraph : listNodes) {
			double[] nCoord  = GraphPosLengthUtils.nodePosition(nodeCenterSmallGraph) ;		
			ArrayList<Node> listNodeMultiGraph = graphGenerator.createListNodeInSquare(netGraph, sizeGraph, nCoord, raidiusSmallGraph , randomSeed );
	//		System.out.println(listNodeMultiGraph);
		}
//		System.out.println(listNodes);		System.out.println(netGraph.getNodeCount() );
		
	}
		

	@Override
	public void setMeanPoint(meanPointPlace point) {
		setupNet_Inter.setMeanPointInter(gsGraph, point);	
	}

}
