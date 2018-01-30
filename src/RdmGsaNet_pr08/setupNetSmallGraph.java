package RdmGsaNet_pr08;

import java.util.ArrayList;
import java.util.Iterator;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNet_pr08.layerNet.meanPointPlace;
import scala.util.control.BreakControl;

//class not yet developed. 
public class setupNetSmallGraph implements setupNetInter {

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
		}																//	System.out.println(listIdGsCon);
			
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
		
			} catch (org.graphstream.graph.IdAlreadyInUseException e) {
				continue;
			}
		}
	}

	@Override
	public void setMeanPoint(meanPointPlace point) {
		setupNetInter.setMeanPointInter(gsGraph, point);	
	}
	
// PRIVATE METHODS ----------------------------------------------------------------------------------------------------------------------------------
	
	// method to create edge
	private static void createEdge (Graph graph,  Node n1 , Node n2 ) {
	
		String idEdge = n1.getId() + "-" + n2.getId() ;
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
	
	/*
	private static Graph gsGraph = layerGs.getGraph() ;
	private static Graph netGraph = layerNet.getGraph() ;
	
	public enum typeRadius { topo, geom , weight }
	public static typeRadius radiusType;
	private static double radius ;

	public setupNetSmallGraph ( double radius , typeRadius type ) {
		this.radius = radius ;
		this.radiusType = type ;
	}

	public void createLayerNet() {
		System.out.println("create small graph");
		
		// list id gs nodes  ( con == 1 )
		ArrayList<String> listIdGsCon = new ArrayList<String> () ;
				
		// create list of id with con = 1 => that means we create a list of meanPoint
		for ( Node nGs : gsGraph.getEachNode()) {
			int con = nGs.getAttribute("con") ;
				if (  con == 1 ) 
					listIdGsCon.add(nGs.getId());	
			}		System.out.println(listIdGsCon);
				
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

	public void setMeanPoint( layerNet.meanPointPlace point)  {
		setupNetInter.setMeanPointInter(gsGraph, point);
		ArrayList<String> pointInRadiusStr =  new ArrayList<String>();
		
		
		String meanPointId = null ;
		for ( Node nGs : gsGraph.getEachNode()) {
			int isCon = nGs.getAttribute("con");
			if ( isCon == 1  ) { meanPointId = nGs.getId() ; }
		}
		
		Node meanPointNodeGs = gsGraph.getNode(meanPointId);										//		System.out.println("meanPointId " + meanPointId);
		
		switch (radiusType) {
		case topo: 		{ pointInRadiusStr = gsAlgoToolkit.getIdInRadiusTopo(gsGraph, meanPointNodeGs, radius); 
						  break;	}	
		case weight : 	{ pointInRadiusStr = gsAlgoToolkit.getIdInRadiusWeight(gsGraph, meanPointNodeGs, radius) ;
						  break;	}
		case geom :		{ pointInRadiusStr = gsAlgoToolkit.getIdInRadiusGeom(meanPointNodeGs, radius) ;
						  break;	}
		}									//		System.out.println("pointInRadiusStr "+ pointInRadiusStr);
		
		
		for (String idConGs : pointInRadiusStr) {
			Node nGs = gsGraph.getNode(idConGs);
			nGs.addAttribute("con", 1);
		}
  	}
  	*/
