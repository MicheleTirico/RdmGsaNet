package RdmGsaNetAlgo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

public class gsAlgoToolkit {
	
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
	
	private static void setWeigth ( Graph graph ) {
		
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
	
	public static double getDistTopo ( Graph graph, Node n1, Node n2) {
		
		Dijkstra dist = new Dijkstra();
	
		dist.init(graph);
		dist.setSource(n1);
		dist.compute();

		return dist.getPathLength(n2);	
	}

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
	
	// method to obtain a set of key with an assigned value
	public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
		   
		return map.entrySet()
		          .stream()
		          .filter(entry -> Objects.equals(entry.getValue(), value))
		          .map(Map.Entry::getKey)
		          .collect(Collectors.toSet());
		}
		
}