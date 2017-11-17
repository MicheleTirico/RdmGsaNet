package RdmGsaNet_pr08;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

public class generateNetEdgeNear implements generateNetEdgeInter{

	// ENUM
	public enum whichNode { all , onlyOld }
	private whichNode type;
	
	// VARIABLES
	static double radius ;	
	private static Graph netGraph = layerNet.getGraph();
	
	// MAP
	// map / key = (double) step , values = ( list of string)  id node in net
	private static Map < Double , ArrayList<String> > mapStepIdNet = simulation.getMapStepIdNet() ;
		
	// map / key = (double) step , values = (list of string ) id of new nodes in net
	private static Map < Double , ArrayList<String> > mapStepNewNodeId = simulation.getMapStepNewNodeId() ;	
	
	// COSTRUCTOR
	public generateNetEdgeNear( double radius , whichNode type) {
		this.radius = radius ;	
		this.type = type ;
	}	

// PUBLIC METHODS  --------------------------------------------------------------------------------------------------------

// generate edge
	public void generateEdgeRule( double  step ) {

		// list of new nodes
		ArrayList<String> listIdNewNode = mapStepNewNodeId.get( step ) ;				//	System.out.println(listIdNewNode);
		
		//list of nodes that maybe are connected to new nodes
		ArrayList<String> listNodesOld ;												//	System.out.println(listNodesOld);
		
		// define list of nodes maybe are connected to new nodes
		switch (type) {
		
		// all nodes in net graph
		case all : 		{ 	listNodesOld = mapStepIdNet.get( step ) ;	}
						break;
	
		// only old nodes
		case onlyOld : {	listNodesOld = mapStepIdNet.get( step ) ;
							listNodesOld.removeAll(listIdNewNode); 		}
						break;	
		}																				//	System.out.println(listNodesOld);
		
		//loop, in the next , we must improve the code with k-nearest neighbors algorithm
		for ( String id : listIdNewNode ) {
			
			Node n1 = netGraph.getNode(id) ;											//	Object a = n1.getId(); 			System.out.println(a);		
			
			// get map of distance
			Map <String , Double> mapDist = getMapIdDist( netGraph , n1) ;				//	System.out.println(id + " " + mapDist);
			
			// get min distance between n1 and n2 ( you may have more than one node )
			double minDist = getMinDist (mapDist); 										// System.out.println(minDist);		
			
			// get a set of nearest nodes
			Set<String> idNear = getKeysByValue(mapDist, minDist ); 					// System.out.println(idNear);
			
			// create edges
			createEdge(n1, idNear, netGraph);	
		}
	}
// remove edge
	@Override
	public void removeEdgeRule( double step) {
		// TODO Auto-generated method stub	
	}
	
//  PRIVATE METHODS --------------------------------------------------------------------------------------------------------
	
// method to create one edge for each nodes in set and the new node
	private static void createEdge ( Node n1 , Set<String> idNear , Graph graph) {
		
		// declare id for new node
		String idN1 = n1.getId();													//	System.out.println("idN1 " + n1.getId() ) ;
		
		// create an edge for each new node
		for ( String idN2 : idNear) {
			
			Node n2 = graph.getNode(idN2) ;											//	System.out.println("idN2 " + n2.getId() ) ;
			
			// try create an edge. It return exception whether nodes are yet connected -> continue
			try 													{ 	graph.addEdge(  idN1 + "-" + idN2 ,  n1 , n2 );	}
			catch (org.graphstream.graph.EdgeRejectedException e) 	{	continue;	}
		}	  	
	}
	
// method to create map of distance
		// map / key = (string) id of nodes n2 , (double) distance between n1 and n2
	private static Map <String , Double> getMapIdDist( Graph graph , Node n1 ) {
		
		// Initialized map of distances
		Map <String , Double> mapDist = new HashMap<String, Double> ( ) ; 
		
		// iteration
		Iterator<Node> iterNode = graph.getNodeIterator();
		
		while (iterNode.hasNext()) {

			Node n2 = iterNode.next();
			
			String n2Str = n2.getId();
			String n1Str = n1.getId();
			
			if ( n2.getId() != n1Str ) {	mapDist.put(n2Str, calcDist(n1, n2)) ; }
			}
		
		return mapDist ;
		}

// method to calculate distance between two nodes
	private static double calcDist (Node n1 , Node n2 ) {
		double dist ;
		
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
		dist = Math.sqrt( distSq );
		
		return dist;
	}

// method to obtain a set of key with an assigned value
	private static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
	   
		return map.entrySet()
	              .stream()
	              .filter(entry -> Objects.equals(entry.getValue(), value))
	              .map(Map.Entry::getKey)
	              .collect(Collectors.toSet());
	    }
	
// method to get min value in list from a map
	private static double getMinDist ( Map <String , Double>  map ) {
	
		ArrayList<Double> list = new ArrayList<Double>() ;
		list.addAll(map.values()); 														//	System.out.println(list);
		
		// calculate min distance
		double minDist = Collections.min(list) ;										//	System.out.println(minDist);
		
		return minDist;	
	}		
}