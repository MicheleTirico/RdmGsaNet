package RdmGsaNet_staticBuckets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Node;


public class bucketSet extends abstractBuckets  { 
	
	
	// parameters 
	private double 	XBucKetSet ,
					YBucKetSet , 
					sizeBucKetSet ,
					XBucket , YBucket ;
	
	
	
	
	
	
 	public bucketSet( double XBucKetSet , double YBucKetSet ) {
 		 this.XBucKetSet = XBucKetSet ;
		 this.YBucKetSet = YBucKetSet ; 
		 sizeBucKetSet = XBucKetSet * YBucKetSet ;
	}
	
	
	public void createBuketSet () {
		
	}
	
	public void insertNode ( Node node ) {
		
		
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
