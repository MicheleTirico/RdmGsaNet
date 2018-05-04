package test;

import java.util.ArrayList;
import java.util.Arrays;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNetAlgo.graphGenerator;
import RdmGsaNetAlgo.graphGenerator.spanningTreeAlgo;
import RdmGsaNetAlgo.graphToolkit.elementTypeToReturn;
import RdmGsaNet_staticBuckets_01.bucketSet;

public class testBuckets {
	
	static Graph graph = new SingleGraph ("graph");

	static bucketSet bucketSet = new bucketSet( true , graph ) ;

	public static void main ( String[ ] args ) {
		
		double [] meanPointCoord = new double [2] ;
		meanPointCoord[0] = 5 ;
		meanPointCoord[1] = 5 ;
		
		graphGenerator.createCompleteGraph(graph, 100 , meanPointCoord, 5, 10);
		graphGenerator.createSpaningTree(graph, spanningTreeAlgo.krustal);
		graph.display(false);
		
		Node n = graph.getNode("50")  ;										// System.out.println(graph.getNodeSet() ) ;
		
	//	System.out.println(GraphPosLengthUtils.nodePosition(n)[0] + " " + GraphPosLengthUtils.nodePosition(n)[1]);
		bucketSet.createBuketSet( 10, 10 , 50 , 50 ) ;					// System.out.println(bucketSet.getBuckets().size() ) ;
		
		String idNewNode  = Integer.toString(graph.getNodeCount() + 1 ) ;
		graph.addNode(idNewNode) ;
		
		Node newNode =  graph.getNode(idNewNode ) ;
		newNode.setAttribute("xyz", 0.2 , 0.2 , 0 );
		
		bucketSet.putNodeInBucketSet(newNode);							// System.out.println(bucketSet.getBuckets().size() ) ;

		ArrayList<Node> list = bucketSet.getListNodesInBucket(n) ;
	//	System.out.println( list ) ;
			
		list = bucketSet.getListNodesInNeighborQuadrantBuckets( n , 0.1 ) ;
	//	System.out.println( list ) ;
		
		for ( Node node : list ) {
	//		System.out.println(node + " " + GraphPosLengthUtils.nodePosition(node)[0] + " " + GraphPosLengthUtils.nodePosition(node)[1] );
		}
		Edge edge = n.getEdge(1) ;
		System.out.println(edge);
		
		ArrayList<Edge> listEdge = new ArrayList<Edge> (bucketSet.getListEdgesInBucket(edge)) ;	
		System.out.println(listEdge);
		
		listEdge = new ArrayList<Edge> (bucketSet.getListEdgesInNeighborQuadrantBuckets(edge, 0.5)) ;	
		System.out.println(listEdge);
		
		

	
		
	
		
	}
}