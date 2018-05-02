package RdmGsaNetAlgo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNetAlgo.graphToolkit.element;
import RdmGsaNetAlgo.graphToolkit.elementTypeToReturn;
import RdmGsaNet_staticBuckets.bucketSet;

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
	
	public static Map<double[] ,Node > getMapCoordVertexRoundPoint (  elementTypeToReturn elementTypeToReturn ,  Graph graphVertex ,  double[] nodeCoord  ) {
		
		Map<double[] , Node > map = new HashMap<double[] , Node > ( ) ;
		ArrayList<Node> listVertex =  getListVertexRoundPoint(elementTypeToReturn.element, graphVertex, nodeCoord ) ;
		
		for ( Node n : listVertex ) {
			
			double[] coord = GraphPosLengthUtils.nodePosition(n) ;		
			map.put(coord, n);
		}				
		return map ;
	}
	
	
	// list of edges near in radius 
	public static ArrayList<Edge> getListEdgeInRadius ( Graph graph , String  idNode , double radius , boolean doCeckIntersectionInLine ) {
	
		ArrayList<Edge> listEdges = new ArrayList<Edge> ( );
		Node nPoint = graph.getNode(idNode) ;
		double[] nPointCoord = GraphPosLengthUtils.nodePosition(nPoint);
		
		for ( Edge e : graph.getEachEdge() ) {
			
			Node nStart = e.getNode0();
			double[] nStartCoord = GraphPosLengthUtils.nodePosition(nStart);
			
			Node nEnd = e.getNode1();
			double[] nEndCoord = GraphPosLengthUtils.nodePosition(nEnd);
			
			double distNodeEdge = getDistNodeEdge(nStart, nEnd, nPoint, false ) ;
			
//			if ( Double.isNaN(distNodeEdge))	System.out.println(distNodeEdge);
			
			if ( distNodeEdge == 0 ) {
				listEdges.add(e);
				continue; 
			}
			
			double [] coordInter = getCoordIntesectionLinesOrto(nStartCoord[0],  nEndCoord[0], nStartCoord[1],  nEndCoord[1], nPointCoord[0], nPointCoord[1] ) ;
				
			if ( doCeckIntersectionInLine ) {
				
				double 	minXEdge = Math.min(nStartCoord[0], nEndCoord[0] ) , 
						maxXEdge = Math.max(nStartCoord[0], nEndCoord[0] ) ;
				
				if ( distNodeEdge < radius && coordInter[0] <= maxXEdge && coordInter[0] >= minXEdge  )
					listEdges.add(e);
			} else {
			
				if ( distNodeEdge <= radius )
					listEdges.add(e);
			}			
		}		
		return listEdges ;
	}
	
	// list of edges near in radius 
	public static ArrayList<Edge> getListEdgeInRadiusInBucketSet ( Graph graph , String  idNode , double radius , boolean doCeckIntersectionInLine , bucketSet bucketSet ) {
	
		ArrayList<Edge> listEdges = new ArrayList<Edge> ( );
		Node nPoint = graph.getNode(idNode) ;
		double[] nPointCoord = GraphPosLengthUtils.nodePosition(nPoint);
	
		System.out.println("list "  + bucketSet.getListEdgeInBucket(nPoint) );
		System.out.println("nodes "  + bucketSet.getListNodesInBucket(nPoint));
		
		for ( Edge e : bucketSet.getListEdgeInListBuckets(nPoint) ) {
//		for ( Edge e : bucketSet.getListEdgeInBucket(nPoint) ) {
	
	//	for ( Edge e : graph.getEachEdge() ) {	
			Node nStart = e.getNode0();
			double[] nStartCoord = GraphPosLengthUtils.nodePosition(nStart);
			
			Node nEnd = e.getNode1();
			double[] nEndCoord = GraphPosLengthUtils.nodePosition(nEnd);
			
			double distNodeEdge = getDistNodeEdge(nStart, nEnd, nPoint, false ) ;
			
//				if ( Double.isNaN(distNodeEdge))	System.out.println(distNodeEdge);
			
			if ( distNodeEdge == 0 ) {
				listEdges.add(e);
				continue; 
			}
			
			double [] coordInter = getCoordIntesectionLinesOrto(nStartCoord[0],  nEndCoord[0], nStartCoord[1],  nEndCoord[1], nPointCoord[0], nPointCoord[1] ) ;
				
			if ( doCeckIntersectionInLine ) {
				
				double 	minXEdge = Math.min(nStartCoord[0], nEndCoord[0] ) , 
						maxXEdge = Math.max(nStartCoord[0], nEndCoord[0] ) ;
				
				if ( distNodeEdge < radius && coordInter[0] <= maxXEdge && coordInter[0] >= minXEdge  )
					listEdges.add(e);
			} else {
			
				if ( distNodeEdge <= radius )
					listEdges.add(e);
			}			
		}		
		return listEdges ;
	}
	
	public static Edge getEdgeXInList ( Edge edge , ArrayList<Edge> listEdgeInRadius ) {
		
		boolean isEdge = false ;
		Edge edgeToReturn ;
		Node n0ceck = edge.getNode0();
		Node n1ceck = edge.getNode1();
		
		double [] 	n0ceckCoord = GraphPosLengthUtils.nodePosition(n0ceck) , 
					n1ceckCoord = GraphPosLengthUtils.nodePosition(n1ceck) ;  
		
		for ( Edge e : listEdgeInRadius ) {
			
			Node n0 = e.getNode0();
			Node n1 = e.getNode1();
			
			double [] 	n0Coord = GraphPosLengthUtils.nodePosition(n0) , 
						n1Coord = GraphPosLengthUtils.nodePosition(n1) ,
						
						intersectionCoord = getCoordIntersectionLine(n0ceckCoord[0], n0ceckCoord[1], n1ceckCoord [0], n1ceckCoord [1], n0Coord[0], n0Coord[1], n1Coord[0], n1Coord[1]) ;
						
			 double minX = Math.min(n0Coord[0], n1Coord[0] ) ,
					maxX = Math.max(n0Coord[0], n1Coord[0] ) ; 
			
			if ( intersectionCoord[0] >= minX && intersectionCoord[0] <= maxX ) {
				return e ; 
			}			
		}
		return null ;
	}
	
	public static Edge getNearEdgeXInList ( Edge edge , ArrayList<Edge> listEdgeInRadius ) {
		
		ArrayList<Edge> list = new ArrayList<Edge> () ;
		
		Edge edgeToReturn = null ;
		Node n0ceck = edge.getNode0();
		Node n1ceck = edge.getNode1();
		
		double [] 	n0ceckCoord = GraphPosLengthUtils.nodePosition(n0ceck) , 
					n1ceckCoord = GraphPosLengthUtils.nodePosition(n1ceck) ;  
		
		for ( Edge e : listEdgeInRadius ) {
			
		
			
			Node n0 = e.getNode0();
			Node n1 = e.getNode1();
		
			
			
			double [] 	n0Coord = GraphPosLengthUtils.nodePosition(n0) , 
						n1Coord = GraphPosLengthUtils.nodePosition(n1) ;
					
			
			double [] intersectionCoord = getCoordIntersectionLine(n0ceckCoord[0], n0ceckCoord[1], n1ceckCoord [0], n1ceckCoord [1], n0Coord[0], n0Coord[1], n1Coord[0], n1Coord[1]) ;
						
			
			 double minX = Math.min(n0Coord[0], n1Coord[0] ) ,
					maxX = Math.max(n0Coord[0], n1Coord[0] ) ; 
			 
			 double minY = Math.min(n0Coord[1], n1Coord[1] ) ,
					maxY = Math.max(n0Coord[1], n1Coord[1] ) ; 
			
			if ( intersectionCoord[0] >= minX && intersectionCoord[0] <= maxX && intersectionCoord[1] >= minY && intersectionCoord[1] <= maxY ) {
				list.add(e) ; 
				
			}			
		}
		return edgeToReturn ;
	}
	
	public static ArrayList<Edge> getListEdgeXInList ( Edge edge , ArrayList<Edge> listEdgeInRadius ) {
		
		ArrayList<Edge> list = new ArrayList<Edge> () ;
		
		Node n0ceck = edge.getNode0();
		Node n1ceck = edge.getNode1();
		
		double [] 	n0ceckCoord = GraphPosLengthUtils.nodePosition(n0ceck) , 
					n1ceckCoord = GraphPosLengthUtils.nodePosition(n1ceck) ;  
		
		for ( Edge e : listEdgeInRadius ) {
			
			Node n0 = e.getNode0();
			Node n1 = e.getNode1();
			
			double [] 	n0Coord = GraphPosLengthUtils.nodePosition(n0) , 
						n1Coord = GraphPosLengthUtils.nodePosition(n1) ,
						
						intersectionCoord = getCoordIntersectionLine(n0ceckCoord[0], n0ceckCoord[1], n1ceckCoord [0], n1ceckCoord [1], n0Coord[0], n0Coord[1], n1Coord[0], n1Coord[1]) ;
						
			
			 double minX = Math.min(n0Coord[0], n1Coord[0] ) ,
					maxX = Math.max(n0Coord[0], n1Coord[0] ) ; 
			
			 double minY = Math.min(n0Coord[1], n1Coord[1] ) ,
						maxY = Math.max(n0Coord[1], n1Coord[1] ) ; 
				
			if ( intersectionCoord[0] >= minX && intersectionCoord[0] <= maxX && intersectionCoord[1] >= minY && intersectionCoord[1] <= maxY ) {
				list.add(e) ; 
			}			
		}
		return list ;
	}
	
	// return true if edge is in list 
	public static boolean ceckEdgeInList ( Edge edge , ArrayList<Edge> listEdgeInRadius ) {
		
		boolean isEdge = false ;
		
		Node n0ceck = edge.getNode0();
		Node n1ceck = edge.getNode1();
		
		double [] 	n0ceckCoord = GraphPosLengthUtils.nodePosition(n0ceck) , 
					n1ceckCoord = GraphPosLengthUtils.nodePosition(n1ceck) ;  
		
		for ( Edge e : listEdgeInRadius ) {
			
			Node n0 = e.getNode0();
			Node n1 = e.getNode1();
			
			double [] 	n0Coord = GraphPosLengthUtils.nodePosition(n0) , 
						n1Coord = GraphPosLengthUtils.nodePosition(n1) ,
						
						intersectionCoord = getCoordIntersectionLine(n0ceckCoord[0], n0ceckCoord[1], n1ceckCoord [0], n1ceckCoord [1], n0Coord[0], n0Coord[1], n1Coord[0], n1Coord[1]) ;
						
			 double minX = Math.min(n0Coord[0], n1Coord[0] ) ,
					maxX = Math.max(n0Coord[0], n1Coord[0] ) ; 
			
			if ( intersectionCoord[0] >= minX && intersectionCoord[0] <= maxX ) {
				isEdge = true ;
				return isEdge ; 
			}			
		}
		return isEdge ;
	}
	
	public static double[] getCoordIntersectionLine ( double x1 , double y1 , double x2 , double y2 , double x3 , double y3 , double x4 , double y4  ) {
		double 	xi = 0 , yi =  0 ,
				m1 , m2 , q1 , q2 ;
		double[] coordInter = new double[2] ;
		
		m1 = ( y1 - y2 ) / ( x1 - x2 ) ;
		m2 = ( y3 - y4 ) / ( x3 - x4 ) ;
		
		q1 = ( x2 * y1 - x1 * y2 ) / ( x2 - x1 ) ;
		q2 = ( x4 * y3 - x3 * y4 ) / ( x4 - x3 ) ;
		
		xi = ( q2 - q1 ) / ( m1 - m2 ) ;
		yi = m1 * xi  + q1 ;
			
		coordInter[0] = xi ; coordInter [1] = yi ;
		return coordInter ;
	}
	
	public static boolean ceckLineIntesected ( double x1 , double y1 , double x2 , double y2 ) {
		
		
		return true ;
	}
	
	public static Map<String , Node > getMapIdVertexRpoundPoint (  elementTypeToReturn elementTypeToReturn ,  Graph graphVertex ,  double[] nodeCoord  ) {
		
		Map< String , Node > map = new HashMap< String , Node > ( ) ;
		ArrayList<Node> listVertex = new ArrayList<Node>( getListVertexRoundPoint(elementTypeToReturn.element, graphVertex, nodeCoord ) ) ;
			
		double minX = 1000000000 , minY = 1000000000 , maxX = -1  , maxY = -1  ; 
		
		for ( Node n : listVertex ) {
			
			double[] coord = GraphPosLengthUtils.nodePosition(n) ;
		
			if ( coord[0] <= minX && coord[1] <= minY  ) {
				map.put("00", n);
				minX = coord[0] ;
				minY = coord[1] ;
			}
			
			else if ( coord[0] >= maxX && coord[1] >= maxY ) {	
				map.put("11", n);
				maxX = coord[0] ;
				maxY = coord[1] ;
			}
		}
		
		ArrayList<Node> listNodeExtr = new ArrayList<Node> (map.values() ) ; 
		
		for ( Node n : listVertex ) {
			double[] coord = GraphPosLengthUtils.nodePosition(n) ;
			if ( ! listNodeExtr.contains(n)) {

				if ( coord[0] == minX )
					map.put("01", n);
				else
					map.put("10", n);
			}		
		}				
		return map ;
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
	
	private static double[] getCoordIntesectionLinesOrto ( double x1 , double x2 , double y1 , double y2 ,double  xp ,double yp ) {
		
		double 	m1 , m2 , 
				q , 
				xi = 0  , yi = 0  ; 
		
		double[] coordInter = new double [2] ;		//		System.out.println(x1 + " " + y1 ) ; 	System.out.println(x2 + " " + y2 ) ; 
		
		m1 = ( y1 - y2 ) / ( x1 - x2 ) ;			//		System.out.println("m1 " + m1);
		q = yp + xp / m1 ;							//		System.out.println("q " + q);
		xi = ( ( yp + xp / m1 - y1 ) / m1 + x1 ) * Math.pow( m1 , 2 ) / ( 1 + Math.pow(m1, 2 ) ) ;
		
		yi = - xi / m1 + q ;  
		
		coordInter[0] = xi ; coordInter[1] = yi ;
		
		return coordInter ;
	}
	
	// get map id node coord 
	public static Map getMapCoord ( Graph graph , elementTypeToReturn elementTypeToReturn ) {
		
		Map map =  new HashMap () ;
		
		for ( Node n : graph.getEachNode() )  {
			
			double[] coord = GraphPosLengthUtils.nodePosition(n);
			
			if ( elementTypeToReturn. equals(elementTypeToReturn.string)) 
				map.put(n.getId(), coord) ;
				
			else if  ( elementTypeToReturn. equals(elementTypeToReturn.element)) 
				map.put(n, coord) ;			
		}
		return map ;
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
		
		
		ArrayList <Integer> listElementInt = getListElement(graph, element, elementTypeToReturn.integer) ;
		int idInt = 0 ; 
		try {
			idInt   = Collections.min(listElementInt) ;
		} catch (java.util.NoSuchElementException e) {
			idInt = 0 ; 
		}
		try {
		//	ArrayList <Integer> listElementInt = getListElement(graph, element, elementTypeToReturn.integer) ;
		
			while ( listElementInt.contains(idInt))
				idInt++ ;
		}catch (java.util.NoSuchElementException e) {
			idInt   = 0 ;
		}
		return idInt ;
	}
}
