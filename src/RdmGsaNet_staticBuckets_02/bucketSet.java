package RdmGsaNet_staticBuckets_02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;


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
		sizeBucketY = sizeGridY / numBucketsY ;	
		
		System.out.println( buckets ) ; 
		for ( Node node : graph.getEachNode() ) {
		

			putNode(node) ;

			
			
		
		}
		

		System.out.println( buckets ) ; 
	}
	
	public void putNode ( Node node ) {
		
		double [] coordBucket = bucket.getCoordBuketFromNode ( node ) ;	
		bucket = new bucket( bucketSet ) ;
		bucketsId.put(Integer.toString( bucketSet.getBucketsCount() ) , bucket );
		bucketsCoord.put(coordBucket, bucket) ;		
		bucket.putNode(node);
		buckets.put(bucket, null) ;
		
			
	}
	// put all nodes in bucket, create bucket if needed
	private void putAllNodesInBucketSet ( ) {
		
		for ( Node node : graph.getEachNode() ) {

			bucket = bucket.getBucket ( node ) ;
			 
			if ( bucket == null ) {
				
				double [] coordBucket = bucket.getCoordBuketFromNode ( node ) ;	//		System.out.println("coordBucket " + coordBucket[0] + " " + coordBucket[1]);
				bucket = new bucket( bucketSet ) ;
				bucketsId.put(Integer.toString( bucketSet.getBucketsCount() ) , bucket );
				bucketsCoord.put(coordBucket, bucket) ;		
			}		
			
			bucket.putNode( node );
		
		}	//	System.out.println(buckets.size() );	
	}

// get methods --------------------------------------------------------------------------------------------------------------------------------------	
	

	public static int getBucketsCount ( ) {
		return buckets.size() ;
	}
}

