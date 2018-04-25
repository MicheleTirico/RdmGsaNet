package RdmGsaNet_staticBuckets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNet_gsAlgo.gsAlgo.reactionType;

 final class bucket extends abstractBuckets  { 	
	
	private double 	sizeBucketX , sizeBucketY ,
					posX , posY ;
	
	private String id ;
	private ArrayList<Node> listNode = new ArrayList<Node> () ;
	
	private bucket ( String id ) {
		
		this.id = id ;
		sizeBucketX =  graphSizeEdge / numBucketsX ;
		sizeBucketY = graphSizeEdge / numBucketsY ;		
	}
	
	
	public bucket getBuket ( Node node ) {
		return null;
	}
	
	public bucket createBuketFromNode ( Node node ) {	
		return null;
	}
	

// get methods --------------------------------------------------------------------------------------------------------------------------------------	
	
	
	// get bucket of node 
	public bucket getBucket ( bucketSet bucketSet , Node node ) {
		
		double [] nodeCoord = GraphPosLengthUtils.nodePosition(node) ;
		
		double [] coordBucket ;
		
		
		Map < bucket , ArrayList<Node> > ba = bucketSet.buckets ;
		
		
		
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
	
	// get coordinate of min vertex of bucket from node
	public double [] getCoordBuketFromNode ( Node node ) {
		
		double [ ]  coordBucket = new double[2] , 
					nodeCoord = GraphPosLengthUtils.nodePosition(node) ;
		
		coordBucket[0] = (int) ( nodeCoord[0] / sizeBucketX );
		coordBucket[1] = (int) ( nodeCoord[1] / sizeBucketY ) ;	
		
		return coordBucket ;
	}
	
	// ceck if node has yet a bucket 
	public boolean ceckNodeHasbucket ( bucketSet bucketSet) {

		return false ;
	}

}
