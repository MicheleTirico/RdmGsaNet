package RdmGsaNet_staticBuckets_02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNetAlgo.gsAlgoToolkit;


public class bucketSet extends abstractBuckets  { 
		
	private boolean createBuckets ;
	
	// constructor
 	public bucketSet (  boolean createBuckets , Graph graph ) {
 		
 		this.createBuckets = createBuckets ;
 		this.graph = graph ; 		
 	}
	 		
 	// method to create started bucket set and create whatever need buckets  
	public void createBuketSet ( double sizeGridX , double sizeGridY , int numBucketsX ,  int numBucketsY ) {
		
 		this.numBucketsX = numBucketsX ;
		this.numBucketsY = numBucketsY ; 
		this.sizeGridX = sizeGridX ;
		this.sizeGridY = sizeGridY ;
 		
		if ( createBuckets == false )
			return ; 
		
		numTotBuckets = numBucketsX * numBucketsY ;	
		sizeBucketX = sizeGridX / numBucketsX ;
		sizeBucketY = sizeGridY / numBucketsY ;							//		System.out.println(bucketSet.getBucketsCount() );
		
		for ( Node node : graph.getEachNode() ) {						//		System.out.println(node + " " + graph.getNodeSet());
			
			double [] coordBucket = bucket.getCoordBuket ( node ) ;		//		System.out.println("coordBucket " + coordBucket[0] + " " + coordBucket[1]);
		
			if ( bucket.ceckNodeHasBucket(node) ) {
				bucket = bucket.getBucket(coordBucket) ;				//		System.out.println(bucket);
				bucket.putNode(node);									//		System.out.println(bucket + " " + bucket.getListNode() ) ;
				continue ;
			}
			
			bucket = new bucket( bucketSet , node ) ;					//		System.out.println(bucket);	
			
			bucketsCoord.put(coordBucket, bucket) ;						//		System.out.println(bucket+ " " + bucket.getListNode() ) ;
		}
	
	}
	
	// put node in bucketSet
	public void putNode(Node node) {
		
		// get bucket
		if ( bucket.ceckNodeHasBucket(node)) 
			bucket.getBucket(node).getListNode().add(node) ;		
		
		else {
			double [] coordBucket = bucket.getCoordBuket ( node ) ;	
			bucket = new bucket( bucketSet , node ) ;							//	System.out.println(bucket);		
			bucketsCoord.put(coordBucket, bucket) ;		
		}	
	}
	
	
	public void putEdge ( Edge edge ) {
		
		putNode( edge.getNode0() ) ;
		putNode( edge.getNode1() ) ;
		
	}

// get methods --------------------------------------------------------------------------------------------------------------------------------------	
	
	public static int getBucketsCount ( ) {	
		return buckets.size() ;
	}
	
	public ArrayList<Node> getListNode (Node node ) {		
		return bucket.getBucket(node).getListNode()  ;
	}
	
	public ArrayList<Node> getListNodeBuffer ( Node node ) {	
	
		ArrayList<bucket> listBuckets = new ArrayList<bucket> ( bucket.getListBucket(node) ) ;	//	System.out.println(listBuckets);
		ArrayList<Node> listNodeReturn = new ArrayList<Node> () ;
		
		for ( bucket bucket : listBuckets ) {
			
			if ( bucket != null ) {	//	System.out.println(bucket + " "  + bucket.getListNode());
				ArrayList<Node> listNode = new ArrayList<Node> (bucket.getListNode()) ;
				for ( Node n : listNode ) 
					if ( ! listNodeReturn.contains(n))
						listNodeReturn.add(n) ;
			}
		}
		return listNodeReturn ;
	}
	
	public  ArrayList<Edge> getListEdge ( Node node , boolean inBuffer ) {	
		
		ArrayList<Edge> listEdge = new ArrayList<Edge> ( );
		ArrayList<Node> listNode = new ArrayList<Node> ( ) ;
		
		if ( inBuffer )
			listNode = getListNodeBuffer(node) ;
		else 
			listNode = getListNode(node) ;
		
		for ( Node n : listNode ) {
			if ( n == null)
				continue ;
			
			ArrayList<Edge> listEdgeIntern = new ArrayList<Edge> ( n.getEdgeSet() );
			for ( Edge e : listEdgeIntern )
				if ( ! listEdge.contains(e))
					listEdge.add(e) ;
		}
		
		return listEdge ; 
	}
	
	


}

