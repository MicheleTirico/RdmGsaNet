package test;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import com.amazonaws.services.s3.internal.BucketNameUtils;

import RdmGsaNetAlgo.graphGenerator;
import RdmGsaNetAlgo.graphGenerator.spanningTreeAlgo;

import RdmGsaNet_staticBuckets_03.bucketSet;
import RdmGsaNet_staticBuckets_03.bucketSet.axis;
import RdmGsaNet_staticBuckets_03.bucketSet.bucketNeighbor;

public class testBuckets3 {
	
	static Graph graph = new SingleGraph ("graph");

	static bucketSet bucketSet = new bucketSet( true , graph , 10, 10 , 20 , 20  ) ;

	public static void main ( String[ ] args ) {
		
		double [] meanPointCoord = new double [2] ;
		meanPointCoord[0] = 5 ;
		meanPointCoord[1] = 5 ;
		
		graphGenerator.createCompleteGraph(graph, 100 , meanPointCoord, 5, 10);
		graphGenerator.createSpaningTree(graph, spanningTreeAlgo.krustal);
		
		graph.display(false);
		
		Node n = graph.getNode("50")  ;										// System.out.println(graph.getNodeSet() ) ;
		double [] coordN = GraphPosLengthUtils.nodePosition(n) ;
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
		double x = 0 ;
		while ( x < 50 ) {
			
			double [] coord  = { coordN[0] + x/1000 , coordN[1] + x/1000 }  ;
			
			String id = Integer.toString(graph.getNodeCount() + 1 ) ;
			graph.addNode( id ) ;
			graph.getNode(id).setAttribute("xyz", coord[0] , coord[1] , 0 );
			bucketSet.putNode(graph.getNode(id));
			System.out.println(bucketSet.getListNodeNeighbor( n , 1 ) ) ;
			x++ ;
		}
		
		System.out.println(bucketSet.getBucketsCount()		) ;
	
		
	}
}