package RdmGsaNetAlgo;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.DepthFirstIterator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;

import javafx.collections.transformation.SortedList;

public class gsAlgoToolkit {
	
	// method to get weighted distance among two nodes in the same graph
	public static double getDistWeight ( Graph graph, Node n1, Node n2) {
		
		setWeigth(graph) ;
		Dijkstra dijkstra = new Dijkstra(Dijkstra.Element.EDGE, null, "length");

		// Compute the shortest paths in g from A to all nodes
		dijkstra.init(graph);
		dijkstra.setSource(graph.getNode(n1.getId()));
		dijkstra.compute();
		
		double dist =  dijkstra.getPathLength(n2);	//System.out.println(dist);
		return dist;			
	}
	
//	 method to set attribute "length" to each edge and distance start and end nodes
	public static void setWeigth ( Graph graph ) {
		
		for ( Edge e : graph.getEachEdge()) {
			e.addAttribute(  "length",  getDistGeom(e.getNode0() ,e.getNode1()));	//			double x = e.getAttribute("length");		System.out.println(x);
		}
	}
	
	// get spatial distance  from 2 nodes 
	public static double getDistGeom ( Node n1 , Node n2 ) {	
		// coordinate of node n1
		double [] n1Coordinate = GraphPosLengthUtils.nodePosition(n1) ;
		double x1 = n1Coordinate [0];
		double y1 = n1Coordinate [1];
		double z1 = n1Coordinate [2];
				
		// coordinate of node n2
		double [] n2Coordinate = GraphPosLengthUtils.nodePosition(n2) ;
		double x2 = n2Coordinate [0];
		double y2 = n2Coordinate [1];
		double z2 = n2Coordinate [2];
				
		// calculate distance
		double distSq = Math.pow( ( x1 - x2 ), 2 )  + Math.pow( ( y1 - y2 ), 2 ) + Math.pow( ( z1 - z2 ), 2 ) ;
		return Math.sqrt( distSq );
	}
	
	// method to get geometric distance among two nodes in the same graph
	public static double getDistTopo ( Graph graph, Node n1, Node n2) {
		
		Dijkstra dist = new Dijkstra();
	
		dist.init(graph);
		dist.setSource(n1);
		dist.compute();

		return dist.getPathLength(n2);	
	}

	// method to get a list of nodes with the max degree
	public static ArrayList<String> getNodeMaxDegree ( Graph graph ) {
		
		ArrayList<String> list = new ArrayList<String> () ;
		Map<String , Integer > map = new HashMap<String , Integer > ();
		
		for( Node n : graph.getEachNode()) { map.put(n.getId(), n.getDegree()); }
		
		Integer maxDegree = map.entrySet()
				.stream()
				.max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
				.get()
				.getValue();
		
		for ( String s : map.keySet()) {
			if ( map.get(s) == maxDegree ) {list.add(s); }
		}
		
		return list;
	}
	
	public static void setNodeCoordinateFromValues ( Graph graph , Node n , double x , double y , double z ) {
		n.setAttribute( "xyz", x , y, z );		
	}
	
	// method to assign the same coordinate between two nodes ( owned by two graph ) 
	public static void setNodeCoordinateFromNode ( Graph graphFrom , Graph graphTo, Node nFrom , Node nTo ) {
		
		// get coordinate nFrom
		String idNFrom = nFrom.getId();
		Node nodeFrom = graphFrom.getNode(idNFrom);
		double [] nFromCoordinate = GraphPosLengthUtils.nodePosition(nodeFrom) ;						//	System.out.println(nGsCoordinate[0]);
		
		// set coordinate of node in netGraph
		String idNTo = nTo.getId();
		Node nodeTo = graphTo.getNode(idNTo);
		nodeTo.setAttribute( "xyz", nFromCoordinate[0] , nFromCoordinate[1] , nFromCoordinate[2] );		
		}	
	
		/*	return binominal value 
	 *	n = number of tries
	 *	p = probability 
	 */
	public static int getBinomial(int n, double p) {
		  int x = 0;
		  for(int i = 0; i < n; i++) {
		    if(Math.random() < p)
		      x++;
		  }
		  return x;
		}
	
// GraphStream toolkit dev ----------------------------------------------------------------------------------------------------------------

	public static double [][] getDistanceMatrixTopo(Graph graph) {
		
		int n = graph.getNodeCount();	
		int[][] matrix = new  int [n][n];
		double [][] matrixTopo = new double [graph.getNodeCount()] [graph.getNodeCount()] ;
		fillDistanceMatrixTopo(graph, matrix , matrixTopo  );
		return matrixTopo;	
	}
	
	public static double[][] getDistanceMatrixWeight ( Graph graph  ) {
		
		int n = graph.getNodeCount();
		double[][] matrix = new double[n][n];
		double [][] matrixWeight = new double [graph.getNodeCount()] [graph.getNodeCount()] ;
		fillDistanceMatrixWeigth(graph, matrix, matrixWeight);
		return matrixWeight;	
	}

	public static double [][] getDistanceMatrixInRadiusWeight ( Graph graph, String nodeTestStr , double radius ) {
		
		double [][] matrixWeightRad = new double [graph.getNodeCount()] [graph.getNodeCount()] ;		
		fillDistanceMatrixInRadiusWeight (graph, nodeTestStr, radius, matrixWeightRad);
		return matrixWeightRad;
	}
	
	public static int [][] getDistanceMatrixInRadiusTopo ( Graph graph , String nodeTestStr , double radius ) {
		int n = graph.getNodeCount();	
		int[][] matrix = new int[n][n];
		fillDistanceMatrixInRadiusTopo(graph, nodeTestStr, matrix , radius );
		return matrix;	
	}
	
	public static int [][] getDistanceMatrixInRadiusGeom ( Graph graph , String nodeTestStr, double radius) {
		int n = graph.getNodeCount();	
		int[][] matrix = new int[n][n];
		fillDistanceMatrixInRadiusGeom(graph, nodeTestStr, matrix , radius );
		return matrix;	
	}

// PRIVATE FILL METHODS ----------------------------------------------------------------------------------------------------------
	
	private static void fillDistanceMatrixTopo ( Graph graph, int [][] matrix , double [][] matrixTopo ) {
		
		ArrayList <String> listId = new ArrayList<String> ();
		for ( Node n : graph.getEachNode()) {	listId.add(n.getId()) ;	}	//		System.out.println(listId);
		
		for (int i = 0; i < matrix.length; i++) {	Arrays.fill(matrix[i], 0);	}
		
		for (Edge e : graph.getEachEdge()) {
			double i = e.getSourceNode().getIndex();
			double j = e.getTargetNode().getIndex();		
			
			matrix[(int)i][(int)j]++;
			if (!e.isDirected())
				matrix[(int)j][(int)i]++;		
		}

			for ( int  x = 0 ; x < matrixTopo.length ; x++) {
				Arrays.fill(matrixTopo[x], 0);
			}
			
			for (Edge e : graph.getEachEdge()) {		
				for ( int x = 0 ; x < matrixTopo.length ; x++) {

					for ( int y = 0 ; y < matrixTopo.length ; y++) {
							
						Node n1 = graph.getNode(listId.get(x));
						Node n2 = graph.getNode(listId.get(y));
					
						int dist = (int) getDistTopo(graph, n1, n2);//					System.out.println(dist) ;
						if ( x != y ) {//						System.out.println(dist) ;
							matrixTopo[x][y] = dist ;
					}			
				}
			}
		}
	}
	
	private static void fillDistanceMatrixWeigth(Graph graph,  double[][] matrix , double[][] matrixWeight ) {
	
		ArrayList <String> listId = new ArrayList<String> ();
		for ( Node n : graph.getEachNode()) {	listId.add(n.getId()) ;	}	//		System.out.println(listId);
		
		for (int i = 0; i < matrix.length; i++) {	Arrays.fill(matrix[i], 0);	}
		
		for (Edge e : graph.getEachEdge()) {
			double i = e.getSourceNode().getIndex();
			double j = e.getTargetNode().getIndex();		
			
			matrix[(int)i][(int)j]++;
			if (!e.isDirected())
				matrix[(int)j][(int)i]++;		
		}

			for ( int  x = 0 ; x < matrixWeight.length ; x++) {
				Arrays.fill(matrixWeight[x], 0);
			}
			
			for (Edge e : graph.getEachEdge()) {		
				for ( int x = 0 ; x < matrixWeight.length ; x++) {

					for ( int y = 0 ; y < matrixWeight.length ; y++) {
							
						Node n1 = graph.getNode(listId.get(x));
						Node n2 = graph.getNode(listId.get(y));
					
						double dist = getDistWeight(graph, n1, n2);//					System.out.println(dist) ;
						if ( x != y ) {//						System.out.println(dist) ;
							matrixWeight[x][y] = dist ;
					}			
				}
			}
		}	
	}

	public static ArrayList<String> getIdInRadiusTopo ( Graph graph , Node startNode , double radius) {
		
		ArrayList<String> nodeIdInRadius = new ArrayList<String>();
		
		DepthFirstIterator<Node> iter = new DepthFirstIterator<>(startNode);		
		while ( iter.hasNext()) {
			Node n = iter.next();
		
			int dist = (int) getDistTopo(graph, startNode, n) ;		
			
			if ( dist < radius) 	{ nodeIdInRadius.add(n.getId()); }
			else 					{ break; }	
		}	
		return nodeIdInRadius;
	}

	public static ArrayList<String> getIdInRadiusWeight ( Graph graph , Node startNode , double radius) /*throws InterruptedException*/ {
		
		ArrayList<String> nodeIdInRadius = new ArrayList<String>();
		
		DepthFirstIterator<Node> iter = new DepthFirstIterator<>(startNode);		
		while ( iter.hasNext()) {
			Node n = iter.next();
		
			double dist = getDistWeight(graph, startNode, n);
			
			if ( dist < radius) 	{ nodeIdInRadius.add(n.getId()); }
			else 					{ break; }
//			for(Edge e: n.getEachEdge()){  e.addAttribute("ui.class","highlight"); Thread.sleep(100);  }		
		}
		
//		graph.addAttribute("ui.stylesheet","" +           "edge.highlight {  " +	             "   fill-color: rgb(200,39,65);\n" +	             "   size: 3px;" +	             "}");
		
		return nodeIdInRadius;
	}

	public static ArrayList<String> getIdInRadiusGeom ( Node startNode , double radius) /* throws InterruptedException */ {
		
		ArrayList<String> nodeIdInRadius = new ArrayList<String>();
		
		DepthFirstIterator<Node> iter = new DepthFirstIterator<>(startNode);		
		while ( iter.hasNext()) {
			Node n = iter.next();
		
			double dist = getDistGeom(startNode, n) ;
			
			if ( dist < radius) 	{ nodeIdInRadius.add(n.getId()); }
			else 					{ continue; }
//			for(Edge e: n.getEachEdge()){  e.addAttribute("ui.class","highlight"); Thread.sleep(100);  }	
		}		
		return nodeIdInRadius;
	}
	
		
// PRIVATE FILL METHODS ------------------------------------------------------------------------------------------------------------------
	
	private static void fillDistanceMatrixInRadiusWeight ( Graph graph , String nodeTestStr , double radius, double[][] matrixWeightRad ) {
		
		Node nodeTest = graph.getNode(nodeTestStr);
		
		ArrayList <String> listId = new ArrayList<String> ();	
		for ( Node n : graph.getEachNode()) {	listId.add(n.getId()) ;	}
		
		int nodeNumber = graph.getNodeCount();
		double [][] matrixWeight = getDistanceMatrixWeight(graph);

			for ( int x = 0 ; x < nodeNumber ; x++ ) {
				
				String IdN = listId.get(x);
				Node n2 = graph.getNode(IdN) ;
				double distN1 = getDistWeight(graph, nodeTest, n2) ; //				System.out.println(distN1);
				
				for ( int y = 0 ; y < nodeNumber ; y++ ) {

					if (  distN1 < radius ) {
						matrixWeightRad[x][y] = matrixWeight[x][y] ;
						matrixWeightRad[y][x] = matrixWeight[y][x] ;
					}
					else {
						matrixWeightRad[x][y] = 0 ;
						matrixWeightRad[y][x] = 0 ;
					}				
				}
			}
		}
	
	private static void fillDistanceMatrixInRadiusGeom(Graph graph, String  nodeTestStr, int[][] matrix, double radius ) {
		
		Node nodeTest = graph.getNode(nodeTestStr);
		
		for (int i = 0; i < matrix.length; i++) 
			Arrays.fill(matrix[i], 0);

		for (Edge e : graph.getEachEdge()) {
	
			int i = e.getSourceNode().getIndex();
			int j = e.getTargetNode().getIndex();
			Node n1 = graph.getNode(i);
			Node n2 = graph.getNode(j);
			
			double distN1 = getDistGeom ( n1 , nodeTest ) ;
			double distN2 = getDistGeom ( n2 , nodeTest ) ;
				
			if  ( distN1 <= radius && distN2 <= radius ) {			
				matrix[i][j]++;		
				if (!e.isDirected())
					matrix[j][i]++;
				}
			}
		// remove row with all values == 0
		}

	private static void fillDistanceMatrixInRadiusTopo(Graph graph, String nodeTestStr, int[][] matrix, double radius ) {
	
		Node nodeTest = graph.getNode(nodeTestStr);
	
		for (int i = 0; i < matrix.length; i++) 
			Arrays.fill(matrix[i], 0);

		for (Edge e : graph.getEachEdge()) {

			int i = e.getSourceNode().getIndex();
			int j = e.getTargetNode().getIndex();
			Node n1 = graph.getNode(i);
			Node n2 = graph.getNode(j);
		
			double distN1 = getDistTopo ( graph, n1 , nodeTest ) ;
			double distN2 = getDistTopo ( graph, n2 , nodeTest ) ;
			
			if  ( distN1 <= radius && distN2 <= radius ) {			
				matrix[i][j]++;		
				if (!e.isDirected())
					matrix[j][i]++;
				}
			}
	// remove row with all values == 0
		}

	// get list of new nodes
	public static ArrayList<String> getListNewNode ( Graph graph0 , Graph graph1  ) {
		
		ArrayList<String> listNewNode = new ArrayList<String>();
		
		try {
		
		ArrayList<String> listNode0 = new ArrayList<String>();
		
		for ( Node n0 : graph0.getEachNode() ) {	listNode0.add(n0.getId()); }
		
		for ( Node n1 : graph1.getEachNode() ) {
			if ( !listNode0.contains(n1.getId()) )
				listNewNode.add(n1.getId()) ;	
		}
		} catch (java.lang.NullPointerException e) {
			// TODO: handle exception
		}
		return listNewNode;	
	}
	
	public static String getCenterGrid ( Graph graph ) {
		 
		int nodeCount = graph.getNodeCount();
		double gridSize = Math.pow(nodeCount, ( 0.5 ) ) ;
		int idCenter = (int)  Math.floor(gridSize / 2) ;			

		return idCenter + "_" + idCenter ;	
	}

	// get list of neighbor String
	public static ArrayList<String> getListNeighborStr ( Graph graph , String idNode ) {
		
		Node node = graph.getNode(idNode) ;	
		ArrayList<String> listNeig = new ArrayList<String>();
		
		Iterator<Node> iter = node.getNeighborNodeIterator() ;	
		while (iter.hasNext()) {		 
			Node neig = iter.next() ;		//		System.out.println(neig.getId() + neig.getAttributeKeySet());
			if ( !listNeig.contains(neig.getId()))
				listNeig.add(neig.getId());
		}
		return listNeig ;
	}
	
	// get list of neighbor String
	public static ArrayList<String> getListNeighborStrCeckAtr ( Graph graph0 , Graph graph1 , String idNode , String attribute ) {
			
		Node node = graph0.getNode(idNode) ;	
		ArrayList<String> listNeig = new ArrayList<String>();
			
		Iterator<Node> iter = node.getNeighborNodeIterator() ;	
		while (iter.hasNext()) {		 
			Node neig0 = iter.next() ;		//		
			Node neig1 = graph0.getNode(neig0.getId());
			
		//	System.out.println(neig0.getId() + neig0.getAttributeKeySet());
			
		//	System.out.println(neig1.getId() + neig1.getAttributeKeySet()) ;
			
			int neigAtr = neig1.getAttribute(attribute) ;
		//	System.out.println(neigAtr);
			if ( !listNeig.contains(neig0.getId() ) && neigAtr ==  1 )
				listNeig.add(neig0.getId());
		}
		
		return listNeig ;
	}
		
	
	// get list of neighbor String
		public static ArrayList<Node> getListNeighbor( Graph graph , Node n ) {
			
			ArrayList<Node> listNeig = new ArrayList<Node>();
			
			Iterator<Node> iter = n.getNeighborNodeIterator() ;	
			while (iter.hasNext()) {		 
				Node neig = iter.next() ;	
			//	System.out.println(neig.getId() + neig.getAttributeKeySet());
				listNeig.add(neig);
			}


			return listNeig ;
		}
	
	// get list of node with attribute
	public static ArrayList<Node> getListNodeAttribute ( Graph graph , String attribute , int valAtr ) {
	
		ArrayList<Node> list = new ArrayList<Node>();
		
		for ( Node n : graph.getEachNode() ) {
			
			int valTest =  n.getAttribute(attribute) ;
			if ( valTest == valAtr )
				list.add(n) ;	
		}
		return list ;
	}
	
	public static ArrayList<String> getListStringNodeAttribute ( Graph graph , String attribute , int valAtr ) {
		
		ArrayList<String> list = new ArrayList<String>();
		
		for ( Node n : graph.getEachNode() ) {
			
			int valTest =  n.getAttribute(attribute) ;
			if ( valTest == valAtr )
				list.add(n.getId()) ;	
		}
		return list ;
	}

	// method to create a list of nodes, sorted by value ( max -> min )
	public static ArrayList<String> getSortedListNodeAtr (  ArrayList<Node> listToSort , String attribute ) {
	
		ArrayList<String> listSorted = new ArrayList<String> ( );
		
		Map<Node , Double> mapNodeVal = new HashMap<Node, Double >();
		for ( Node n : listToSort)
			mapNodeVal.put(n, n.getAttribute(attribute));
		Map mapNodeSorted = getMapSortedByValue(mapNodeVal);
		
		Set<Node> list = mapNodeSorted.keySet();

		for ( Node n : list ) {
			listSorted.add(n.getId());
		}
		return listSorted ;		
	}
	
	/*	method to return autocorrelation with neigbords
	 * < 0 -> autocorrelation negative
	 * = 0 -> no correlation	
	 * > 0 -> autocorrelation
	 */
	public static double getAutoCorrelationAttr ( Graph graph, String idNode , String attribute ) {
		
		Node node = graph.getNode(idNode) ;
		double autoCor = 0 ;
		double valNode = node.getAttribute(attribute) ;
	
//		System.out.println(valNode);
		System.out.println(node.getAttributeKeySet());
		 
		Iterator<Node> iter =  node.getNeighborNodeIterator() ;		//	System.out.println("id " + n);
		double sumVal = 0 ;
		while ( iter.hasNext()) {				
			
			Node neig = iter.next() ;			
			
			 System.out.println(neig.getAttributeKeySet());
				double valNeig = neig.getAttribute(attribute);	
				double val = valNeig - valNode ;
				
				sumVal = sumVal + val ;
				}
		autoCor = sumVal / node.getDegree() ;
		return  autoCor;
	}
	
	public static double getDeltaPowCeckSeed ( Graph graph , ArrayList<String> listNeig, String idNode , String attribute ) {
		
		Node node = graph.getNode(idNode) ;
		double delta = 0.0 , sumVal = 0.0 , sumValPow = 0.0 ; ;
		
		return delta ;
	}
	
	
	public static double getDeltaPow ( Graph graph , ArrayList<String> listNeig, Node node , String attribute ) {
		
		double delta = 0.0 , sumVal = 0.0 , sumValPow = 0.0 , numValues = 0 ;
		double nodeVal = node.getAttribute(attribute) ;
		
		if ( nodeVal > 1 )
			nodeVal = 1 ;
		
		System.out.println("valNode " + nodeVal ); 
		
		for ( String idNode : listNeig ) {
		//	System.out.println(idNode);
			Node neig = graph.getNode(idNode) ;

			double neigVal = neig.getAttribute(attribute);
			if ( neigVal < 0 )
				neigVal = 0 ; 
			if ( neigVal > 1 )
				neigVal = 1 ; 
			System.out.println("neigVal " + neigVal );
			sumVal = sumVal + neigVal - nodeVal;
			sumValPow = sumValPow + Math.pow(neigVal - nodeVal, 2 ) ;
			numValues++;
		}
		System.out.println("sumVal " + sumVal );
		System.out.println("sumValPow " + sumValPow );
		System.out.println("numValues " + numValues );
		
		delta = sumVal / sumValPow / numValues ;
		if (  Double.isNaN(delta) )
			delta = 0.0 ;
//		System.out.println("delta " + delta );
		return delta ;
	}
	
	public static double getAutoCorrelationAttrInListNeig ( Graph graph ,ArrayList<String> listNeig, Node node , String attribute , boolean isRel ) {
		
		double autoCor = 0 ;
		double valNode = node.getAttribute(attribute) ;
	
		double normal= 0.0 , minVal= 1.0 , maxVal = 0.0 ;
		
		if ( valNode > 1 )
			valNode = 1 ;
//		System.out.println(valNode);
//		System.out.println(node.getAttributeKeySet());
		 
		double sumVal = 0 ;
		for ( String idNode : listNeig ) {
			
	
			Node neig = graph.getNode(idNode) ;

			double valNeig = neig.getAttribute(attribute);	
			if ( valNeig <= minVal)
				minVal = valNeig ;
			
			if ( valNeig >= maxVal )
				maxVal = valNeig ;
			//	System.out.println(neig.getId() + " " + valNeig);
			double val = valNeig - valNode ;
				
			sumVal = sumVal + val ;	
		}
		
		autoCor = sumVal / node.getDegree() ;
		
		if ( isRel ) {
			normal = maxVal - minVal ;
			autoCor = autoCor * normal ;
			
		}
		
		return  autoCor;
	}
	 
	
//METHODS TO HANDLE COLLECTIONS ---------------------------------------------------------------------------------------------------------------------
	
	// method to obtain a set of key with an assigned value
	public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
			   
		return map.entrySet()
			      .stream()
			      .filter(entry -> Objects.equals(entry.getValue(), value))
			      .map(Map.Entry::getKey)
			      .collect(Collectors.toSet());	
		}

	// method to sorted map by values 
	private static Map<Node, Double> getMapSortedByValue(Map unsortMap) {
		 // 1. Convert Map to List of Map
	     List<Map.Entry<Node, Double>> list =  new LinkedList<Map.Entry<Node, Double>>(unsortMap.entrySet());

	     // 2. Sort list with Collections.sort(), provide a custom Comparator
	     //    Try switch the o1 o2 position for a different order
	    Collections.sort(list, new Comparator<Map.Entry<Node, Double>>() {
	    	public int compare(Map.Entry<Node, Double> o1, Map.Entry<Node, Double> o2) { 
	    		return (o1.getValue()).compareTo(o2.getValue());	
	    	}
	    });

	    // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
	    Map<Node, Double> sortedMap = new LinkedHashMap<Node, Double>();
	    for (Map.Entry<Node, Double> entry : list) {
	    	sortedMap.put(entry.getKey(), entry.getValue());	
	    }
	    return sortedMap;
	 }	
}