package RdmGsaNet_staticBuckets_02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNetAlgo.gsAlgoToolkit;

class bucket extends abstractBuckets  { 	
	
	private static double posX, posY ;
	private static double[] coord ;
	private static ArrayList<Node> listNode = new ArrayList<Node>  ( ) ;
	
	private static String id ;
		
	// constructor
	public bucket ( bucketSet bucketSet  ) {		
		this.bucketSet = bucketSet ; 	
	}
	
// create methods ----------------------------------------------------------------------------------------------------------------------------------- 
	
	
// put methods --------------------------------------------------------------------------------------------------------------------------------------	
	public void putNode ( Node node ) {
			
		
		System.out.println(listNode);
		listNode.add(node) ;
		buckets.put( bucket , listNode ) ;	
		
		posX = getCoordBuketFromNode ( node ) [0];
		posY = getCoordBuketFromNode ( node ) [1];
	//	coord[0] = posX ; coord[1] = posY ;
		
	//	ArrayList<String> set = new ArrayList<String> ( gsAlgoToolkit.getKeysByValue(bucketsId, bucket) ); 
	//	id = set.get(0) ;

	}
	
	public void putEdge ( Edge edge ) {

	}
	
	
// get methods --------------------------------------------------------------------------------------------------------------------------------------	
	public String getId () {
		return id ; 
	}
	
	public double[] getCoord () {
		return coord ; 
	}

	protected ArrayList<Node> getListNode (  ) {
		return listNode;			
	}
	
	protected bucket getBucket ( Node node ) {
		double [] coordBucket =  getCoordBuketFromNode ( node ) ;
		 
		for ( double[] x : bucketsCoord.keySet() ) 
			if ( Arrays.equals(coordBucket , x ) ) 
				return bucketsCoord.get(x);	
						
		return null ;
	}
	
	// get coordinate of min vertex of bucket from node
	protected static double [] getCoordBuketFromNode ( Node node ) {
		
		double [ ]  coordBucket = new double[2] , 
					nodeCoord = GraphPosLengthUtils.nodePosition(node) ;		//	System.out.println("nodeCoord " + nodeCoord[0] + " " + nodeCoord[1] ) ;	//	System.out.println(sizeBucketX) ;
		
		coordBucket[0] = (int) ( nodeCoord[0] / sizeBucketX );
		coordBucket[1] = (int) ( nodeCoord[1] / sizeBucketY ) ;	
		
		return coordBucket ;
	}
			
	// get coord of bucket from generic coordinates 
	protected static double [] getCoordBuketFromCoord  ( double []  coord ) {
		
		double [ ]  coordBucket = new double[2] ;		
		
		coordBucket[0] = (int) ( coord[0] / sizeBucketX );
		coordBucket[1] = (int) ( coord[1] / sizeBucketY ) ;	
		
		return coordBucket ;
	}


}
