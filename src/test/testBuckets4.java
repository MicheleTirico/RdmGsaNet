package test;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import com.amazonaws.services.s3.internal.BucketNameUtils;

import RdmGsaNetAlgo.graphGenerator;
import RdmGsaNetAlgo.graphGenerator.spanningTreeAlgo;

import RdmGsaNet_staticBuckets_04.bucketSet;

public class testBuckets4 {
	
	static Graph graph = new SingleGraph ("graph");

	static bucketSet bucketSet = new bucketSet( true , graph , 10, 10 , 3 , 3  ) ;

	public static void main ( String[ ] args ) {
		
		double [] meanPointCoord = new double [2] ;
		meanPointCoord[0] = 5 ;
		meanPointCoord[1] = 5 ;
		
		graphGenerator.createCompleteGraph(graph, 100 , meanPointCoord, 5, 10);
		graphGenerator.createSpaningTree(graph, spanningTreeAlgo.krustal);
		
		graph.display(false);
		
		Node n = graph.getNode("50")  ;										// System.out.println(graph.getNodeSet() ) ;
		
		bucketSet.createBuketSet( ) ;	
		// 
		
		String idNewNode  = Integer.toString(graph.getNodeCount() + 1 ) ;
		graph.addNode(idNewNode) ;
		
		Node newNode =  graph.getNode(idNewNode ) ;
		newNode.setAttribute("xyz", 0.2 , 0.2 , 0 );
		
		bucketSet.putNode(newNode) ;

	//	bucketSet.test(n , bucketNeighbor.N);
	//	System.out.println(bucketSet.getListNodeBucketNeighbor(n, bucketNeighbor.N) );
		
//		System.out.println(bucketSet.getSizeBucket(axis.X));
//		System.out.println(bucketSet.getSizeBucket(axis.Y));
 		
		System.out.println(bucketSet.getListNodeNeighbor( n , 1 ) ) ;
		
		System.out.println(bucketSet.getListNodeBuffer(n) )  ;
	
		
	}
}