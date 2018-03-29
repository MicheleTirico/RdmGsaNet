package RdmGsaNetAlgo;

import java.util.ArrayList;
import java.util.Iterator;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

public class graphToolkit {
	
	public enum elementTypeToReturn { element , string , integer }
	public elementTypeToReturn elementType ;
	
	public enum element { node , edge }
	public element element ;
	
	// get list of nodes 
	public static ArrayList getListElement ( Graph graph , element element , elementTypeToReturn elementTypeToReturn  ) {
	
		ArrayList list = new ArrayList () ;
		ArrayList<String> listStr = new ArrayList<String> () ;
		
		switch (element) {
			case edge: {
				if ( elementTypeToReturn.equals(elementTypeToReturn.element) )
					for ( Edge e : graph.getEachEdge() )
						list.add(e);
				
				else if ( elementTypeToReturn.equals(elementTypeToReturn.string) )
					for ( Edge e : graph.getEachEdge() )
						list.add(e.getId());
				
				else if ( elementTypeToReturn.equals(elementTypeToReturn.integer ) )
					for ( Edge e : graph.getEachEdge() ) 
						listStr.add(e.getId());	
					listStr.stream().forEach( s -> list.add(Integer.parseInt(s)));
			}	break;
			
			case node : {
				if ( elementTypeToReturn.equals(elementTypeToReturn.element) )
					for ( Node n : graph.getEachNode() )
						list.add(n);
			
				else if ( elementTypeToReturn.equals(elementTypeToReturn.string) )
					for ( Node n : graph.getEachNode() )
						list.add(n.getId());
				
				else if ( elementTypeToReturn.equals(elementTypeToReturn.integer ) )
					for( Node n : graph.getEachNode() )
						listStr.add(n.getId());
					listStr.stream().forEach( s -> list.add(Integer.parseInt(s)));
			}	break;
		}
		return list ;
	}
	
	// get list of elements whit value of attribute like answer	
	public static ArrayList getListElementAttribute (  Graph graph , element element , elementTypeToReturn elementTypeToReturn , String attribute , int valAtr ) {
		
		ArrayList list = new ArrayList () ;
		
		switch (element) {
			case edge: {
				if ( elementTypeToReturn.equals(elementTypeToReturn.element) ) {
					for ( Edge e : graph.getEachEdge() ) {
						int attrTest = e.getAttribute(attribute);
						if ( attrTest == valAtr ) 	
							list.add(e);	
					}
				}
				else if ( elementTypeToReturn.equals(elementTypeToReturn.string) ) {
					for ( Edge e : graph.getEachEdge() ) {
						int attrTest = e.getAttribute(attribute);
						if ( attrTest == valAtr ) 	
							list.add(e.getId());
					}
				}
			}	break;
			
			case node : {
				if ( elementTypeToReturn.equals(elementTypeToReturn.element) ) {
					for ( Node n : graph.getEachNode() ) {
						int attrTest = n.getAttribute(attribute);
						if ( attrTest == valAtr ) 	
							list.add(n);
				}
			}
				 else if ( elementTypeToReturn.equals(elementTypeToReturn.string) ) { 
					for ( Node n : graph.getEachNode() ) {
						int attrTest = n.getAttribute(attribute);
						if ( attrTest == valAtr ) 
							list.add(n.getId());
					}
				}
			}	break;
		}
		return list ;
	}
	
	// get list of nodes at vertex 
	public static ArrayList getListVertexRoundCoord ( elementTypeToReturn elementTypeToReturn , Graph graphSeed , Graph graphVertex ,  String idSeed ) {

		ArrayList listVertex = new ArrayList();
		if ( elementTypeToReturn. equals(elementTypeToReturn.string)) {
			 listVertex = new ArrayList<String>(4);
		}
		else if  ( elementTypeToReturn. equals(elementTypeToReturn.element)) {
			listVertex = new ArrayList<Node>(4);
		}
		
		Node nodeSeed = graphSeed.getNode(idSeed), 
			 nodeMinVertex ;
	
		double [] nodeSeedCoordinate = GraphPosLengthUtils.nodePosition(nodeSeed) ;
		
		double 	xSeed = nodeSeedCoordinate[0], 
				ySeed = nodeSeedCoordinate[1];		
	
		int xMin = (int) xSeed,
			yMin = (int) ySeed;
		
		String 	idNodeMinVertex = xMin + "_" + yMin ,
				nodeXStr = null , nodeYStr = null , nodeXYStr = null;
		
		nodeMinVertex = graphVertex.getNode(idNodeMinVertex); 
		
		String [] idList = null ;
		
		idList[0] = nodeXStr  = ( xMin )  +"_" + ( yMin + 1 ) ;
		idList[1] = nodeYStr  = ( xMin + 1 )  +"_" + ( yMin  ) ; 
		idList[2] = nodeXYStr = ( xMin + 1 )  +"_" + ( yMin + 1 ) ;	
		idList[3] = idNodeMinVertex ;
		
		if ( elementTypeToReturn. equals(elementTypeToReturn.string)) {
	
			listVertex.add(nodeXStr) ;
			listVertex.add(nodeYStr) ;
			listVertex.add(nodeXYStr) ;
			listVertex.add( idNodeMinVertex ) ;
		}
		
		else if  ( elementTypeToReturn. equals(elementTypeToReturn.element)) {
			listVertex.add(graphVertex.getNode(nodeXStr) );
			listVertex.add(graphVertex.getNode(nodeYStr) );
			listVertex.add(graphVertex.getNode(nodeXYStr) );
			listVertex.add( graphVertex.getNode(idNodeMinVertex ) );
		}
		return listVertex ;
	}
	
	public static ArrayList getListVertexRoundPoint ( elementTypeToReturn elementTypeToReturn ,  Graph graphVertex ,  double[] nodeCoord ) {

		ArrayList listVertex = new ArrayList();
		
		if ( elementTypeToReturn. equals(elementTypeToReturn.string)) 
			 listVertex = new ArrayList<String>(4);
		
		else if  ( elementTypeToReturn. equals(elementTypeToReturn.element)) 
			listVertex = new ArrayList<Node>(4);
		
		
		double 	sizeGrid = Math.pow(graphVertex.getNodeCount(), 0.5 ) - 1 ,
				xSeed = nodeCoord[0], 
				ySeed = nodeCoord[1];		
	
		int xMin = (int) xSeed,
			yMin = (int) ySeed;
		
		String 	idNodeMinVertex = xMin + "_" + yMin ,
				nodeXStr = null , nodeYStr = null , nodeXYStr = null;

		nodeXStr  = ( xMin )  +"_" + ( yMin + 1 ) ;
		nodeYStr  = ( xMin + 1 )  +"_" + ( yMin  ) ; 
		nodeXYStr = ( xMin + 1 )  +"_" + ( yMin + 1 ) ;	
	
		if ( elementTypeToReturn. equals(elementTypeToReturn.string)) {
			
			if ( yMin < sizeGrid ) 	
				listVertex.add(nodeXStr) ;
			if ( xMin < sizeGrid  ) 
				listVertex.add(nodeYStr) ;	
			if ( xMin < sizeGrid &  yMin < sizeGrid ) 
				listVertex.add(nodeXYStr) ;	
			listVertex.add( idNodeMinVertex ) ;
			}
		
		else if  ( elementTypeToReturn. equals(elementTypeToReturn.element)) {
			
			if ( yMin < sizeGrid ) 	
				listVertex.add(graphVertex.getNode(nodeXStr) );
			if ( xMin < sizeGrid  ) 
				listVertex.add(graphVertex.getNode(nodeYStr) );
			if ( xMin < sizeGrid &&  yMin < sizeGrid ) 
				listVertex.add(graphVertex.getNode(nodeXYStr) );
			listVertex.add( graphVertex.getNode(idNodeMinVertex ) );
		}
		return listVertex ;
	}
		
	// get distance between node and edge of two other nodes 
	public static double getDistNodeEdge (Node nStart , Node nEnd , Node nPoint , boolean isRel) {
		
		double dist = 0 ;
		double [] 	nStartCoord = GraphPosLengthUtils.nodePosition(nStart) ,
					nEndCoord = GraphPosLengthUtils.nodePosition(nEnd) ,
					nPointCoord = GraphPosLengthUtils.nodePosition(nPoint) ;
		
		double 	x1 = nStartCoord[0] , 
				y1 = nStartCoord[1] , 
				
				x2 = nEndCoord[0] , 
				y2 = nEndCoord[2] , 
			
				xp = nPointCoord[0] ,
				yp = nPointCoord[1] ;
									
		double num = Math.abs(( xp - x1 ) * ( y2 - y1 ) - ( yp - y1 ) * ( x2 - x1 ) ) ;
		double distNode = Math.pow ( Math.pow(( x2 - x1 ), 2) + Math.pow(( y2 - y1 ), 2) , 0.5 ) ;
		
		dist = num / distNode ; 
				
		if ( isRel ) 
	
			dist = dist / distNode ;

		return dist;
		
	}
	
	// get coordinate of node between
	public static double[] getCoordNodeMean ( Node n1 , Node n2) {
		
		double[] 	coord = new double [2],
					n1Coord = GraphPosLengthUtils.nodePosition(n1) ,
					n2Coord = GraphPosLengthUtils.nodePosition(n2) ;
		
	//	System.out.println(n1 + " " + n1Coord[0] + " " + n1Coord[1]);
	//	System.out.println(n2 + " " + n2Coord[0] + " " + n2Coord[1]);
		
		double 	x = Math.min(n1Coord[0] , n2Coord[0]) + Math.abs( (n1Coord[0] - n2Coord[0]) ) * 0.5 ,
				y = Math.min(n1Coord[1] , n2Coord[1]) + Math.abs( (n1Coord[1] - n2Coord[1]) ) * 0.5 ;
		
		coord[0] = x ;
		coord[1] = y ;
 		
		return coord ;
	}
	// get list of neighbor String
	public static ArrayList getListNeighbor ( Graph graph , String idNode , elementTypeToReturn elementTypeToReturn ) {
			
		Node node = graph.getNode(idNode) ;			
		ArrayList listNeig = new ArrayList();
			
		Iterator<Node> iter = node.getNeighborNodeIterator() ;	
		while (iter.hasNext()) {		 
			Node neig = iter.next() ;		//		System.out.println(neig.getId() + neig.getAttributeKeySet());
			if ( !listNeig.contains(neig.getId()))
				if ( elementTypeToReturn. equals(elementTypeToReturn.string)) 
					listNeig.add(neig.getId());
				else if  ( elementTypeToReturn. equals(elementTypeToReturn.element)) 
					listNeig.add(neig);
		}
		return listNeig ;
	}

	public static int getMaxIdIntElement ( Graph graph , element element ) {
		
		int idInt = 0  ;
		ArrayList <Integer> listElementInt = getListElement(graph, element, elementTypeToReturn.integer) ;
		while ( listElementInt.contains(idInt))
			idInt++ ;
		
		return idInt ;
	}
}
