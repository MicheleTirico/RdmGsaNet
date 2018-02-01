package RdmGsaNet_pr09;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNet_pr09.layerNet.meanPointPlace;

import java.util.ArrayList;

public class setupNetSeed implements setupNet_Inter {
	
	// get graphs
	private static Graph gsGraph = layerGs.getGraph() ,
							netGraph = layerNet.getGraph() ;
	 
	// create layer Net 
	public void createLayerNet() {	//		System.out.println("hello seed");	

		// list id gs nodes  ( con == 1 )
		ArrayList<String> listIdGsCon = new ArrayList<String> () ;
		
		// create list of id with con = 1 => that means we create a list of meanPoint
		for ( Node nGs : gsGraph.getEachNode()) {
			int con = nGs.getAttribute("con") ;
			if (  con == 1 ) 
				listIdGsCon.add(nGs.getId());	
		}																							//		System.out.println(listIdGsCon);
		
		// create seed node in netGraph and set coordinate
		for ( String id : listIdGsCon ) {
			
			// get node in gsGraph with con == 1
			Node nGs = gsGraph.getNode(id);
			
			// get array of coordinate of node gs with con == 1
			double [] nGsCoordinate = GraphPosLengthUtils.nodePosition(nGs) ;						//	System.out.println(nGsCoordinate[0]);
			
			// create node in netGraph
			netGraph.addNode(id);
			
			// set coordinate of node in netGraph
			Node nNet = netGraph.getNode(id); 														//	System.out.println(nNet.getId());
			nNet.setAttribute( "xyz", nGsCoordinate[0] , nGsCoordinate[1] , nGsCoordinate[2] );		//	double [] nNetCoordinate = GraphPosLengthUtils.nodePosition(nNet) ;			System.out.println(nNetCoordinate[0]);
		}
	}

	@Override
	public void setMeanPoint(meanPointPlace point) {
		setupNet_Inter.setMeanPointInter(gsGraph, point);
	}


}
	
//-----------------------------------------------------------------------------------------------------	
