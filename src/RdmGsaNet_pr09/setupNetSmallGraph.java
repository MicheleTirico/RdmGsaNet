package RdmGsaNet_pr09;

import java.util.ArrayList;
import java.util.Iterator;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNet_pr09.layerNet.meanPointPlace;

//class not yet developed. 
public class setupNetSmallGraph implements setupNet_Inter {

	// COSTANTS
	// type graph
	public enum smallGraphType { star4diagonal, star4Edge , star8 }
	protected smallGraphType typeLayout ;
	
	// get graphs
	private static Graph gsGraph = layerGs.getGraph() ,
							netGraph = layerNet.getGraph() ;
		
	// COSTRUCTOR
	public setupNetSmallGraph  ( smallGraphType typeLayout ) {
		this.typeLayout = typeLayout;
	}
	
	public void createLayerNet() {
		
		// list id gs nodes  ( con == 1 )
		ArrayList<String> listIdGsCon = new ArrayList<String> () ;
				
		// create list of id with con = 1 => that means we create a list of meanPoint
		for ( Node nGs : gsGraph.getEachNode()) {
			int con = nGs.getAttribute("con") ;
			if (  con == 1 ) 
				listIdGsCon.add(nGs.getId());				
		}						 										//	System.out.println(listIdGsCon);
			
		//get start node 
		String startNodeStr = listIdGsCon.get(0);
		Node startNode = gsGraph.getNode(startNodeStr);
				
		// update list of new nodes to create 
		switch ( typeLayout ) {
			case star8 : 			setLayoutStar8(listIdGsCon , startNode); 			break ;
			case star4diagonal : 	setLayout4diagonal(listIdGsCon , startNode);		break ; 
			case star4Edge : 		setLayout4Edge(listIdGsCon , startNode); 			break ;
		}																//	System.out.println(listIdGsCon);
				
		// create seed node in netGraph and set coordinate
		for ( String id : listIdGsCon ) {
					
			// get node in gsGraph with con == 1
			Node nGs = gsGraph.getNode(id);
					
			// get array of coordinate of node gs with con == 1
			double [] nGsCoordinate = GraphPosLengthUtils.nodePosition(nGs) ;						//	System.out.println(nGsCoordinate[0]);
				
			try {
			// create node in netGraph
			netGraph.addNode(id);
					
			// set coordinate of node in netGraph
			Node nNet = netGraph.getNode(id); 														//	System.out.println(nNet.getId());
			nNet.setAttribute( "xyz", nGsCoordinate[0] , nGsCoordinate[1] , nGsCoordinate[2] );		//	double [] nNetCoordinate = GraphPosLengthUtils.nodePosition(nNet) ;			System.out.println(nNetCoordinate[0]);
					
			createEdge(netGraph, startNode, nNet);
//			gsAlgoToolkit.setWeigth(netGraph);
		
			} catch (org.graphstream.graph.IdAlreadyInUseException e) { continue; 	}
		}
	}

	@Override
	public void setMeanPoint(meanPointPlace point) {
		setupNet_Inter.setMeanPointInter(gsGraph, point);	
	}
	
// PRIVATE METHODS ----------------------------------------------------------------------------------------------------------------------------------
	
	// method to create edge
	private static void createEdge ( Graph graph,  Node n1 , Node n2 ) {
	
		String idEdge = n1.getId() + "-" + n2.getId() ;
		if ( n1.getId() != n2.getId() )
			graph.addEdge(idEdge, n1, n2 );
	}
	
	// set layout star 8
	private static void setLayoutStar8 ( ArrayList<String> listIdGsCon , Node startNode ) {
		
		ArrayList<Node> listNeig = gsAlgoToolkit.getListNeighbor(gsGraph, startNode );
		for ( Node n : listNeig )
			listIdGsCon.add(n.getId());		
	}
	
	// set layout star 4 diagonal
	private static void setLayout4diagonal ( ArrayList<String> listIdGsCon , Node startNode  ) {
	
		ArrayList<Node> listNeig = gsAlgoToolkit.getListNeighbor(gsGraph, startNode );
		ArrayList<String> listNodeEdge = gsAlgoToolkit.getIdInRadiusGeom(startNode, 1.05);
		for ( Node n : listNeig ) {
			if ( !listNodeEdge.contains(n.getId()))
				listIdGsCon.add(n.getId());		
		}
	}
	
	// set layout star 4 edge
	private static void setLayout4Edge (ArrayList<String> listIdGsCon , Node startNode  ) {
		
		ArrayList<Node> listNeig = gsAlgoToolkit.getListNeighbor(gsGraph, startNode );
		ArrayList<String> listNodeToAddList = gsAlgoToolkit.getIdInRadiusGeom(startNode, 1.05);
		listNodeToAddList.remove(startNode.getId() ) ;
	
		for ( String n : listNodeToAddList ) 
			listIdGsCon.add(n);
	}
}	