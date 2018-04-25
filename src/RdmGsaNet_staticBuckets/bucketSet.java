package RdmGsaNet_staticBuckets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;


public class bucketSet extends abstractBuckets  { 
	
	
	// parameters 
	private double 	XBucKetSet ,
					YBucKetSet , 
					sizeBucKetSet ,
					XBucket , YBucket ;
	
	private boolean createBuckets ;
	
 	public bucketSet (  boolean createBuckets , Graph graph   ) {
 		
 		this.createBuckets = createBuckets ;
 		this.graph = graph ; 
 		
 		graphSizeEdge = (int) Math.pow( graph.getNodeCount() , 0.5 ) - 1 ;
 	}
	
 	
	
	private void createBuketSet ( Graph graph , int numBucketsX ,  int numBucketsY ) {
		
 		this.numBucketsX = numBucketsX ;
		this.numBucketsY = numBucketsY ; 
		
		numTotBuckets = numBucketsX * numBucketsY ;	
	}
	
	public void insertNode ( Node node ) {
		
		numTotBuckets = buckets.size() + 1 ;
		
		String idBucket = Integer.toString(numTotBuckets);

		
		
		
		
		
		
	}
	
	public Map < bucket , ArrayList<Node> >  getBuketSet ( ) {
		return buckets  ;
	}
	
	/*
	public void setupSizeBuketSet ( double XBucKetSet , double YBucKetSet ) {
		 this.XBucKetSet = XBucKetSet ;
		 this.YBucKetSet = YBucKetSet ; 
		 sizeBucKetSet = XBucKetSet * YBucKetSet ;
	}
	*/
 	
}
