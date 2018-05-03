package test;

import java.util.ArrayList;
import java.util.Arrays;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;


import RdmGsaNetAlgo.graphGenerator;
import RdmGsaNetAlgo.graphGenerator.spanningTreeAlgo;

import RdmGsaNet_staticBuckets_02.bucketSet;

public class testBuckets2 {
	
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
		
		bucketSet.createBuketSet( 10, 10 , 10 , 10 ) ;					// 
//		System.out.println(bucketSet.getBucketsCount() ) ;
		
		String idNewNode  = Integer.toString(graph.getNodeCount() + 1 ) ;
		graph.addNode(idNewNode) ;
		
		Node newNode =  graph.getNode(idNewNode ) ;
		newNode.setAttribute("xyz", 0.2 , 0.2 , 0 );
		
		bucketSet.putNode(newNode) ;
//		System.out.println(bucketSet.getBucketsCount() ) ;
	
		for ( Node node : graph.getEachNode() ) {
		//	System.out.println(bucketSet.getListNodeBuffer(node));
		//	System.out.println(bucketSet.getListNode(node));
		}
		Node node = graph.getNode(50) ; 
	//	System.out.println(bucketSet.getListNodeBuffer(node));
		
	//	System.out.println(bucketSet.getListEdge(node, true));

	//	System.out.println(bucketSet.getListEdge(node, false));
	
		
			
			
		
		
	
		
	}
}