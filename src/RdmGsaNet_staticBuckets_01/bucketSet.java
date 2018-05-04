package RdmGsaNet_staticBuckets_01;

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
		
		putAllNodesInBucketSet();		
	}
	
	// put all nodes in bucket, create bucket if needed
	private void putAllNodesInBucketSet ( ) {
		
		for ( Node node : graph.getEachNode() ) {

			bucket = bucket.getBucket (node ) ;
			 
			if ( bucket == null ) {
				
				double [] coordBucket = bucket.getCoordBuketFromNode ( node ) ;	//		System.out.println("coordBucket " + coordBucket[0] + " " + coordBucket[1]);
				bucket = new bucket(bucketSet ) ;
				bucketsId.put(Integer.toString(buckets.size() + 1 ) , bucket );
				bucketsCoord.put(coordBucket, bucket) ;		
			}		
			bucket.putNode( bucket , node );
			bucket.getListNode(bucket).add(node) ;
		}	//	System.out.println(buckets.size() );	
	}
	
	// put single node in bucket,create bucket if required
	public void putNodeInBucketSet ( Node node ) {
		
		if ( createBuckets == false )
			return ; 
		
		bucket = bucket.getBucket (node ) ;
		
		if ( bucket == null ) {
			
			double [] coordBucket = bucket.getCoordBuketFromNode ( node ) ;	//		System.out.println("coordBucket " + coordBucket[0] + " " + coordBucket[1]);
			bucket = new bucket( bucketSet ) ;
			bucketsId.put(Integer.toString( bucketSet.getBucketsCount()  ) , bucket );
	
			bucketsCoord.put(coordBucket, bucket) ;		
		}		
		bucket.putNode( bucket , node );
		bucket.getListNode(bucket).add(node) ;
	}
	
	public void putEdgeInBucketSet ( Edge edge ) {
		
		if ( createBuckets == false )
			return ; 
	ArrayList<Node> listStartEndNode = new ArrayList<Node> ( Arrays.asList(edge.getNode0(), edge.getNode1())) ;
		
		for ( Node n : listStartEndNode ) {
			bucket = bucket.getBucket ( n ) ;
			
			if ( bucket == null ) {
				
				double [] coordBucket = bucket.getCoordBuketFromNode ( n ) ;	//		System.out.println("coordBucket " + coordBucket[0] + " " + coordBucket[1]);
				bucket = new bucket(bucketSet ) ;
				bucketsId.put(Integer.toString(buckets.size() + 1 ) , bucket );
			
				bucketsCoord.put(coordBucket, bucket) ;		
			}		
			bucket.putNode( bucket , n );
		}																		//		System.out.println( bucket.getId() + " " + getListEdgeInBucket(bucket) ) ; 
	}

// get methods --------------------------------------------------------------------------------------------------------------------------------------	
	public ArrayList<Edge> getListEdgeInBucket ( bucket bucket ) {
		
		ArrayList<Edge> listEdge = new ArrayList<Edge> ( ); 
		ArrayList<Node> listNode = new ArrayList<Node> ( bucket.getListNode(bucket) ); 
		
		for ( Node n : listNode ) {
			for ( Edge e : n.getEdgeSet( ) ) {
				if ( ! listEdge.contains(e))
					listEdge.add(e) ;
			}
		}		
		return listEdge ;
	}
	
	public ArrayList<Edge> getListEdgeInBucket ( Node node ) {
		
		bucket = bucket.getBucket(node) ; 
		return getListEdgeInBucket (  bucket )  ;
		
	}
	
	public ArrayList<Edge> getListEdgeInListBuckets (  Node node ) {
		
		ArrayList<Edge> listEdges = new ArrayList<Edge> () ;
		ArrayList<bucket> listBuckets = bucket.getListBuckets(node) ;
		for (bucket b : listBuckets ) 
			listEdges.addAll(getListEdgeInBucket (  bucket )) ;	
		
		return listEdges ;		
	}
		
	public Map < bucket , ArrayList<Node> > getBuckets ( ) {
		return buckets  ;
	}	
	
	// get list of edges in bucket
	public ArrayList<Edge> getListEdgesInBucket ( Edge edge ) {
			
		ArrayList<Edge> listEdge = new ArrayList<Edge> ( ); 
		ArrayList<bucket> listBuckets = new ArrayList <bucket> (bucket.getListBuckets(edge) ) ;		//	
		System.out.println(listBuckets);
		
		for ( bucket b : listBuckets ) {
			for ( Edge e : b.getListEdges ( b ) ) 
				if ( ! listEdge.contains(e))
					listEdge.add(e) ;
		}
		return listEdge ;
	}	
	
	// get list of edges in bucket in radius
	public ArrayList<Edge> getListEdgesInNeighborQuadrantBuckets ( Edge edge , double radius ) {
			
		ArrayList<Edge> listEdge = new ArrayList<Edge> ( ); 
		ArrayList<bucket> listBuckets = new ArrayList <bucket> (bucket.getListBuckets(edge) ) ;
		ArrayList<Node> listStartEndNode = new ArrayList<Node> ( Arrays.asList(edge.getNode0(), edge.getNode1())) ;
		
		for ( Node n : listStartEndNode ) {
			bucket = bucket.getBucket(n) ;
		
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
		
	//	System.out.println(listBuckets);
		for ( bucket b : listBuckets ) {
			for ( Edge e : b.getListEdges ( b ) ) 
				if ( ! listEdge.contains(e)) 
					listEdge.add(e) ;
		}
		
		return listEdge ;
	}	
		
	// get list of nodes in bucket
	public ArrayList<Node> getListNodesInBucket ( Node node ) {

		bucket = bucket.getBucket(node) ;	
		return bucket.getListNode( bucket );
	}

	// get list of nodes in bucket and in buckets neighbors
	public ArrayList<Node> getListNodesInNeighborQuadrantBuckets (  Node node , double radius ) {
		
		ArrayList<Node> list = new ArrayList<Node> ( bucket.getListNode( bucket ) ) ;
			
		double[] coordBucket = bucket.getCoordBuketFromNode ( node ) ,
				 nodeCoord = GraphPosLengthUtils.nodePosition(node) ,
				 coord = new double [2] ;

		// cech whether radius is in bucket
		if ( nodeCoord[0] - coordBucket[0] + radius < 1 && nodeCoord[0] - coordBucket[0] - radius > 0 )
			if ( nodeCoord[1] - coordBucket[1]  + radius < 1 && nodeCoord[1] - coordBucket[1]  - radius > 0 )
				return list ; 
				
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
			
			bucket = bucket.getBucketFromCoord(bucketNeig) ;	//			System.out.println(i[0] + " " + i[1]);	
			try {
				for ( Node n : bucket.getListNode( bucket ) ) {  // System.out.println(bucket.getListNode(bucket, elementTypeToReturn.element)) ;
					if ( ! list.contains(n))
						list.add(n);
				}
			} catch ( NullPointerException e) {				
			}
		}
		
		return list;		
	}	

	public static int getBucketsCount ( ) {
		return buckets.size() ;
	}
}

