package RdmGsaNet_staticBuckets_01;

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
import RdmGsaNet_gsAlgo.gsAlgo.reactionType;
import RdmGsaNet_staticBuckets_01.abstractBuckets.elementTypeToReturn;

class bucket extends abstractBuckets  { 	
	
	private double posX, posY;

	private static String id ;
	
	
	// constructor
	public bucket ( bucketSet bucketSet  ) {		
		this.bucketSet = bucketSet ; 
	}
		
	public void putNode ( bucket bucket , Node node ) {
		
		ArrayList < Node> list = getListNode( bucket ) ;	
		list.add(node) ;	
		buckets.put( bucket, list ) ;	
		
		posX = getCoordBuketFromNode ( node ) [0];
		posY = getCoordBuketFromNode ( node ) [1];
		
		ArrayList<String> set = new ArrayList<String> ( gsAlgoToolkit.getKeysByValue(bucketsId, bucket) ); 
		id = set.get(0) ;
	
		bucket.getListNode(bucket).add(node) ;

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
		
	// get coord of bucket from generic coordinates 
	protected static double [] getCoordBuketFromCoord  ( double []  coord ) {
		
		double [ ]  coordBucket = new double[2] ;		
		
		coordBucket[0] = (int) ( coord[0] / sizeBucketX );
		coordBucket[1] = (int) ( coord[1] / sizeBucketY ) ;	
		
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
	protected static bucket getBucket ( Node node ) {
		
		double [] coordBucket =  getCoordBuketFromNode ( node ) ;
		 
		for ( double[] x : bucketsCoord.keySet() ) 
			if ( Arrays.equals(coordBucket , x ) ) 
				return bucketsCoord.get(x);	
						
		return null ; 
	}
	
	// get bucket of edge 
	protected static ArrayList<bucket> getListBuckets ( Edge edge ) {
		
		ArrayList<bucket> listBuckets = new ArrayList<bucket> ( );
		Node node0 = edge.getNode0() , node1 = edge.getNode1() ;
	//	double[] coordNode0 = new double [2] , coordNode1 = new double [2] ;		
		bucket bucket0 = getBucket (node0) ,  bucket1 = getBucket (node1) ,  bucket2 = null ;	
		double[] coordBucket0 = bucket0.getCoordBucket(bucket0) , coordBucket1 = bucket1.getCoordBucket(bucket1) ;
		
		listBuckets.add(bucket0);

		if ( coordBucket0[0] == coordBucket1[0] || coordBucket0[1] == coordBucket1[1] ) {	
			
			if ( ! listBuckets.contains(bucket1)  && bucket1 != null )
				listBuckets.add(bucket1);
		}
		else if (coordBucket0[0] != coordBucket1[0] &&  coordBucket0[1] != coordBucket1[1] ) {
			
			if ( ! listBuckets.contains(bucket1)  && bucket2 != null  )
				listBuckets.add(bucket1);
			
			double [] coord = new double[2] ;
			coord[0] = coordBucket0[0] ; 
			coord[1] = coordBucket1[1] ; 
			bucket2 = getBucketFromCoord(coord);
			
			if ( ! listBuckets.contains(bucket2) && bucket2 != null )
				listBuckets.add(bucket2);	
			
			coord = new double[2] ;
			coord[0] = coordBucket1[0] ; 
			coord[1] = coordBucket0[1] ; 
			bucket2 = getBucketFromCoord(coord);
			
			if ( ! listBuckets.contains(bucket2)  && bucket2 != null )
				listBuckets.add(bucket2);		
		}
		return listBuckets ;
	}
	
	protected static ArrayList<bucket> getListBuckets ( Node node ) {
		
		ArrayList<bucket> listBuckets = new ArrayList<bucket> ( );

	//	double[] coordNode0 = new double [2] , coordNode1 = new double [2] ;		
		bucket bucket = getBucket (node) ;	
		double[] coordBucket = bucket.getCoordBucket(bucket) ;
		
		listBuckets.add(bucket);

		return listBuckets ;
	}
	
	protected ArrayList<bucket> getListBucketsInRadius ( Edge edge , double radius ) {
		
		ArrayList<bucket> listBuckets = new ArrayList<bucket> ( getListBuckets ( edge ) ) ; 
		 
		ArrayList<Node> listStartEndNode = new ArrayList<Node> ( Arrays.asList(edge.getNode0(), edge.getNode1())) ;
	
		for ( Node n : listStartEndNode ) {
			bucket bucket = getBucket(n) ;
		
			double[] coordBucket = bucket.getCoordBuketFromNode (n) ,
					 nodeCoord = GraphPosLengthUtils.nodePosition(n) ,
					 coord = new double [2] ;
		
			// cech whether radius is in bucket
			if ( nodeCoord[0] - coordBucket[0] + radius < 1 && nodeCoord[0] - coordBucket[0] - radius > 0 )
				if ( nodeCoord[1] - coordBucket[1]  + radius < 1 && nodeCoord[1] - coordBucket[1]  - radius > 0 )
					continue ; 
			
			// list of coordinates inside buckets neighbors
			ArrayList <double[]> listCoords = new ArrayList <double[]> ();
			
			// quad sud west
			if ( nodeCoord[0] - coordBucket[0] <= sizeBucketX/2  && nodeCoord[1] - coordBucket[1] <= sizeBucketY/2  ) {	//	System.out.println("SW");
				
				// add nodes of bucket x
			 	coord = new double [2] ;	
			 	coord [0] = nodeCoord[0] - sizeBucketX/2  ;coord[1] = nodeCoord[1] ;						
			 	listCoords.add( coord );
				
				// add nodes of bucket y
				coord = new double [2] ;	
				coord [0] = nodeCoord[0] ; coord[1] = nodeCoord[1] - sizeBucketY/2 ;						
				listCoords.add( coord );
							
				// add nodes of bucket xy 
				coord = new double [2] ;	
				coord [0] = nodeCoord[0] - sizeBucketX/2  ; coord[1] = nodeCoord[1] - sizeBucketY/2 ;		
				listCoords.add(coord ) ;																				
			}		
			// quad sud est
			else if ( nodeCoord[0] - coordBucket[0] >= sizeBucketX/2  && nodeCoord[1] - coordBucket[1] <= sizeBucketY/2  ) { //	System.out.println("SE");
				
				// add nodes of bucket x
				coord = new double [2] ;
				coord [0] = nodeCoord[0] + sizeBucketX/2  ; coord[1] = nodeCoord[1] ;
				listCoords.add(coord) ;
				
				// add nodes of bucket y
				coord = new double [2] ;
				coord [0] = nodeCoord[0] ; coord[1] = nodeCoord[1] - sizeBucketY/2 ;
				listCoords.add(coord) ;
				
				// add nodes of bucket xy
				coord = new double [2] ;
				coord [0] = nodeCoord[0] + sizeBucketX/2  ; coord[1] = nodeCoord[1] - sizeBucketY/2 ;
				listCoords.add(coord) ;
			}		
			// quad nord west
			else if ( nodeCoord[0] - coordBucket[0] <= sizeBucketX/2  && nodeCoord[1] - coordBucket[1] >= sizeBucketY/2  ) { //	System.out.println("NW");
				
				// add nodes of bucket x
				coord = new double [2] ;
				coord [0] = nodeCoord[0] - sizeBucketX/2  ; coord[1] = nodeCoord[1] ;
				listCoords.add(coord) ;
				
				// add nodes of bucket y
				coord = new double [2] ;
				coord [0] = nodeCoord[0] ; coord[1] = nodeCoord[1] + sizeBucketY/2 ;
				listCoords.add(coord) ;
				
				// add nodes of bucket xy 
				coord = new double [2] ;
				coord [0] = nodeCoord[0] - sizeBucketX/2  ; coord[1] = nodeCoord[1] + sizeBucketY/2 ;
				listCoords.add(coord) ;
			}		
			// quad nord est
			else if ( nodeCoord[0] - coordBucket[0] >= sizeBucketX/2  && nodeCoord[1] - coordBucket[1] >= sizeBucketY/2  ) { //	System.out.println("NE");
				
				// add nodes of bucket x
				coord = new double [2] ;
				coord [0] = nodeCoord[0] + sizeBucketX/2  ; coord[1] = nodeCoord[1] ;
				listCoords.add(coord) ;
				
				// add nodes of bucket y
				coord = new double [2] ;
				coord [0] = nodeCoord[0] ; coord[1] = nodeCoord[1] + sizeBucketY/2 ;
				listCoords.add(coord) ;
				
				// add nodes of bucket xy 
				coord = new double [2] ;
				coord [0] = nodeCoord[0] + sizeBucketX/2  ; coord[1] = nodeCoord[1] + sizeBucketY/2 ;
				listCoords.add(coord) ;		
			}	
			for ( double[] bucketNeig : listCoords ) {
				bucket = bucket.getBucketFromCoord(bucketNeig) ;		//	System.out.println(bucket);
				if ( ! listBuckets.contains(bucket) && bucket != null )
					listBuckets.add(bucket) ;
			}		
		}	
		return listBuckets ;
	}
	
	// get bucket of node 
	protected static bucket getBucketFromCoord ( double [ ] coord ) {
			
		double [] coordBucket =  getCoordBuketFromCoord(coord) ;
		 
		for ( double[] x : bucketsCoord.keySet() ) 
			if ( Arrays.equals(coordBucket , x ) ) 
				return bucketsCoord.get(x);	
						
		return null ; 
	}
	
	// get list of edges
	protected ArrayList<Edge> getListEdges ( bucket bucket ) {
		
		ArrayList < Node > listNode = new ArrayList < Node > ( bucket.getListNode(bucket ));
		
		ArrayList < Edge > listEdge = new ArrayList < Edge > ( );
		
		for ( Node n : listNode ) 
			for ( Edge e : n.getEdgeSet() )
				if ( ! listEdge.contains(e) )
					listEdge.add(e) ;

		return listEdge ; 
	}
	
	// get list of nodes 
	protected ArrayList<Node> getListNode ( bucket bucket  ) {
		return bucket.listNode;			
	}
	
	// get id of bucket  
	protected static String getId (  ) {	
		return bucket.id ;
	}
	
	// get min X 
	protected double getPosX ( bucket bucket ) {
		return bucket.posX ; 
	}
	
	// get min Y 
	protected double getPosY ( bucket bucket ) {
		return bucket.posY ; 
	}
	
	protected double [] getCoordBucket  ( bucket bucket ) {
		
		double[] coordBucket = new double[2] ;
		coordBucket[0] = bucket.getPosX(bucket);
		coordBucket[1] = bucket.getPosY(bucket);
		
		return coordBucket ;
	}
}
