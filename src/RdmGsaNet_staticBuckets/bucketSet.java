package RdmGsaNet_staticBuckets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;


public class bucketSet extends abstractBuckets  { 
	
	bucketSet bucketSet ; 
	// parameters 
	private double 	XBucKetSet ,
					YBucKetSet , 
					sizeBucKetSet ,
					XBucket , YBucket  ;
	
	private boolean createBuckets ;
	
	// constructor
 	public bucketSet (  boolean createBuckets , Graph graph   ) {
 		
 		this.createBuckets = createBuckets ;
 		this.graph = graph ; 		
 	}
	
 		
	public void createBuketSet ( double sizeGridX , double sizeGridY , int numBucketsX ,  int numBucketsY ) {
		
 		this.numBucketsX = numBucketsX ;
		this.numBucketsY = numBucketsY ; 
		this.sizeGridX = sizeGridX ;
		this.sizeGridY = sizeGridY ;
 		
		numTotBuckets = numBucketsX * numBucketsY ;	
		sizeBucketX = sizeGridX / numBucketsX ;
		sizeBucketY = sizeGridY / numBucketsY ;	
		
		putAllNodesInBucketSet();		
	}
	
	private void putAllNodesInBucketSet ( ) {
		
		for ( Node node : graph.getEachNode() ) {

			bucket = bucket.getBucket (node ) ;
			 
			if ( bucket == null ) {
				
				double [] coordBucket = bucket.getCoordBuketFromNode ( node ) ;	//		System.out.println("coordBucket " + coordBucket[0] + " " + coordBucket[1]);
				bucket = new bucket(bucketSet) ;
				bucketsId.put(Integer.toString(buckets.size() + 1 ) , bucket );
				bucketsCoord.put(coordBucket, bucket) ;		
			}		
			bucket.putNode( bucket , node );
		}	//	System.out.println(buckets.size() );	
	}
	
	public void putNodeInBucketSet ( Node node ) {
		
		bucket = bucket.getBucket (node ) ;
		
		if ( bucket == null ) {
			
			double [] coordBucket = bucket.getCoordBuketFromNode ( node ) ;	//		System.out.println("coordBucket " + coordBucket[0] + " " + coordBucket[1]);
			bucket = new bucket(bucketSet) ;
			bucketsId.put(Integer.toString(buckets.size() + 1 ) , bucket );
			bucketsCoord.put(coordBucket, bucket) ;		
		}	
		
		bucket.putNode( bucket , node );
		
	}

// get methods --------------------------------------------------------------------------------------------------------------------------------------
	public Map < bucket , ArrayList<Node> > getBuckets ( ) {
		return buckets  ;
	}
 	
}
