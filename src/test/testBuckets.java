package test;

import java.util.Arrays;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import RdmGsaNetAlgo.graphGenerator;

import RdmGsaNet_staticBuckets.bucketSet;

public class testBuckets {
	
	static Graph graph = new SingleGraph ("graph");

	static bucketSet bucketSet = new bucketSet(true, graph) ;

	public static void main ( String[ ] args ) {
		
		double [] meanPointCoord = new double [2] ;
		meanPointCoord[0] = 5 ;
		meanPointCoord[1] = 5 ;
		
		graphGenerator.createCompleteGraph(graph, 50 , meanPointCoord, 5, 10);
		graph.display(false);
		
		Node n = graph.getNode(5) ;	//	
		System.out.println(graph.getNodeSet() ) ;
		
		bucketSet.createBuketSet( 10, 10 , 10 , 10 ) ;
//		System.out.println("node " + n);
		
		System.out.println(bucketSet.getBuckets().size() ) ;
		
		String idNewNode  = Integer.toString(graph.getNodeCount() + 1 ) ;
		graph.addNode(idNewNode) ;
		
		Node newNode =  graph.getNode(idNewNode ) ;
		newNode.setAttribute("xyz", 0.2 , 0.2 , 0 );
		
		bucketSet.putNodeInBucketSet(newNode);
		

		System.out.println(bucketSet.getBuckets().size() ) ;

	

	
		
	
		
	}
}