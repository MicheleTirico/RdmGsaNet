package RdmGsaNet_staticBuckets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNet_gsAlgo.gsAlgo.reactionType;
import scala.collection.parallel.ParIterableLike.Foreach;

class bucket extends abstractBuckets  { 	
	
	int idBucketInt = 0 ; 
	private double posX, posY;
	
	private  bucketSet bucketSet ; 

	private String id ;
	private ArrayList<Node> listNode = new ArrayList<Node> () ;
	private bucket bucket ;
	
	// constructor
	public bucket ( bucketSet bucketSet ) {		
		this.bucketSet = bucketSet ; 
	}
	
	
	
	public void putNode ( bucket bucket , Node node ) {
		
		ArrayList < Node> list = getListNode(bucket) ;	
		list.add(node) ;	
		buckets.put(bucket, list ) ;
	}
	

// get methods --------------------------------------------------------------------------------------------------------------------------------------	
	// get coordinate of min vertex of bucket from node
	protected static double [] getCoordBuketFromNode ( Node node ) {
		
		double [ ]  coordBucket = new double[2] , 
					nodeCoord = GraphPosLengthUtils.nodePosition(node) ;		//	System.out.println("nodeCoord " + nodeCoord[0] + " " + nodeCoord[1] ) ;	//	System.out.println(sizeBucketX) ;
		
		coordBucket[0] = (int) ( nodeCoord[0] / sizeBucketX );
		coordBucket[1] = (int) ( nodeCoord[1] / sizeBucketY ) ;	
		
		return coordBucket ;
	}

		
	// ceck if node has yet a bucket 
	protected static boolean ceckNodeHasBucket ( Node node ) {

		double [] coordBucket =  getCoordBuketFromNode ( node ) ;
		
		for ( double[] x : bucketsCoord.keySet() ) 
			if ( Arrays.equals(coordBucket, x ) ) 
				return true;	
						
		return false ; 
	}
		

	// get bucket of node 
	public static bucket getBucket ( Node node ) {
		
		double [] coordBucket =  getCoordBuketFromNode ( node ) ;
		 
		for ( double[] x : bucketsCoord.keySet() ) 
			if ( Arrays.equals(coordBucket , x ) ) 
				return bucketsCoord.get(x);	
						
		return null ; 
	}
	
	// get list of nodes 
	public ArrayList <Node> getListNode ( bucket bucket ) {				
 		return bucket.listNode ;
	}
	
	// get id of bucket  
	public String getId ( bucket bucket ) {	
		return bucket.id ;
	}
	
	// get min X 
	public double getPosX ( bucket bucket ) {
		return bucket.posX ; 
	}
	
	// get min Y 
	public double getPosY ( bucket bucket ) {
		return bucket.posY ; 
	}
}
