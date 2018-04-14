package RdmGsaNet_setupLayer;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNet_mainSim.layerGs;
import RdmGsaNet_mainSim.layerNet;
import RdmGsaNet_mainSim.layerNet.meanPointPlace;

public class setupNetCircle implements setupNet_Inter {

	// get graphs
	private static Graph 	gsGraph = layerGs.getGraph() ,
							netGraph = layerNet.getGraph() ;
				
	private int numNodes ;
	private double centreX , centreY , radius ;
	
	
	public setupNetCircle (int numNodes  ,double  radius  ) {
		this.numNodes = numNodes ;
		
		this.radius  = radius ;
	}
		
			
	@Override
	public void createLayerNet() {
		String idNodeCenter = setupNet_Inter.getMeanPointStr( gsGraph ) ;		//	System.out.println(idMeanPoint);
	
		double[] meanPointCoord = GraphPosLengthUtils.nodePosition(gsGraph.getNode(idNodeCenter ) ) ;
		
		double centreX = meanPointCoord[0] , centreY = meanPointCoord[1];
		
		// netGraph.addNode("0");
		 
		// Node nodeCenter = netGraph.getNode("0") ;
		// nodeCenter.setAttribute("xyz", centreX , centreY , 0 );
		
		double angle = 2 * Math.PI / numNodes ;
		
		for ( int n = 0 ; n < numNodes ; n++ ) {
			
			double 	coordX = radius * Math.cos( n * angle ) ,
					coordY = radius * Math.sin( n * angle );
		
			String idNode = Integer.toString(n) ;
			netGraph .addNode(idNode);
			
			Node node = netGraph.getNode(idNode) ;
			node.setAttribute("xyz", centreX + coordX ,  centreY + coordY , 0 );
		
		}
		
		for ( int n = 0 ; n < numNodes ; n++ ) {
			String idEdge = Integer.toString(n);
			try {
				
				netGraph.addEdge(idEdge,Integer.toString(n) , Integer.toString(n+1) ) ;
		
			}
			catch (org.graphstream.graph.ElementNotFoundException e) {
				netGraph.addEdge(idEdge,Integer.toString(n) , Integer.toString(0) ) ;
				break ; 
			}
		}
	}

	@Override
	public void setMeanPoint(meanPointPlace point) {
		setupNet_Inter.setMeanPointInter(gsGraph, point);	
		
		
	}

}
