package RdmGsaNet_staticBuckets_04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNet_staticBuckets_04.bucket;

public class bucketSet extends abstractBuckets  { 
		
	private boolean createBuckets ;
	public enum bucketNeighbor { N , S , E , W , NE , NW , SE , SW } 
	protected bucketNeighbor bucketNeighbor ; 
	
	public enum axis { X , Y } 
	
	// constructor
 	public bucketSet (  boolean createBuckets , Graph graph , double sizeGridX , double sizeGridY , int numBucketsX ,  int numBucketsY ) {
 		
 		this.createBuckets = createBuckets ;
 		this.graph = graph ; 		
 		
 		abstractBuckets.numBucketsX = numBucketsX ;
		abstractBuckets.numBucketsY = numBucketsY ; 
		abstractBuckets.sizeGridX = sizeGridX ;
		abstractBuckets.sizeGridY = sizeGridY ;
 	}
	 		
 	// method to create started bucket set and create whatever need buckets  
	public void createBuketSet ( ) {	
 		
		if ( createBuckets == false )
			return ; 
		
		numTotBuckets = numBucketsX * numBucketsY ;	
		sizeBucketX = sizeGridX / numBucketsX ;
		sizeBucketY = sizeGridY / numBucketsY ;			//		System.out.println(sizeBucketX +" " + sizeBucketY );
		
		//		System.out.println(bucketSet.getBucketsCount() );
		
		for ( Node node : graph.getEachNode() ) {						//		System.out.println(node + " " + graph.getNodeSet());
			
			double [] coordBucket = bucket.getCoordBucket ( node ) ;		//	System.out.println("coordBucket " + coordBucket[0] + " " + coordBucket[1]);
		
			if ( bucket.ceckNodeHasBucket(node) ) {
				bucket = bucket.getBucket(coordBucket) ;				
				
				if ( bucket == null )
					bucket = new bucket (bucketSet , node ) ; 			//		System.out.println(bucket);
				
				bucket.putNode(node);									//		System.out.println(bucket + " " + bucket.getListNode() ) ;
				continue ;
			}
			
			bucket = new bucket( bucketSet , node ) ;					//		System.out.println(bucket);		
			bucketsCoord.put(coordBucket, bucket) ;						//		System.out.println(bucket+ " " + bucket.getListNode() ) ;
		}
	
	}
	
	// put node in bucketSet
	public void putNode(Node node) {
		
		if ( node == null )
			return ;
		
		// get bucket
		if ( bucket.ceckNodeHasBucket(node)  // && ! bucket.getBucket(node).getListNode().contains(node) 	
			)		 
			bucket.putNode(node); 		
		else {
			double [] coordBucket = bucket.getCoordBucket ( node ) ;	
			bucket = new bucket( bucketSet , node ) ;							//	System.out.println(bucket);		
			bucketsCoord.put(coordBucket, bucket) ;		
		}	
	}
	
	// put edge in bucketSet 
	public void putEdge ( Edge edge ) {
		
		putNode( edge.getNode0() ) ;
		putNode( edge.getNode1() ) ;		
	}
	
	// remove node in bucket
	public void removeNode ( Node node ) {
		bucket.getBucket(node).getListNode().remove(node) ;
	}
	
	// remove edge in bucket
	public void removeEdge ( Edge edge ) {
		bucket.getBucket(edge.getNode0()).getListNode().remove(edge.getNode0()) ;
		bucket.getBucket(edge.getNode1()).getListNode().remove(edge.getNode1()) ;
	}
	
// get methods --------------------------------------------------------------------------------------------------------------------------------------		
	public void test ( ) {
	
	}
	
	public ArrayList<Node> getListNodeBucketNeighbor ( Node node , bucketNeighbor bucketNeighbor  ) {		
		return bucket.getBucketNeighbor(node, bucketNeighbor).getListNode()  ;
	}
 	
	public ArrayList<Node> getListNode ( Node node ) {		
		return bucket.getBucket(node).getListNode()  ;
	}
	
	// old and not used
	public ArrayList<Node> getListNodeBuffer ( Node node ) {	
	
		ArrayList<bucket> listBuckets = new ArrayList<bucket> ( bucket.getListBucket(node) ) ;	//	System.out.println(listBuckets);
		ArrayList<Node> listNodeReturn = new ArrayList<Node> () ;
		
	//	listBuckets.parallelStream().forEach(bucket -> testforEach(bucket, listNodeReturn));
		
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
	
	private static void testforEach (  bucket bucket , ArrayList<Node> listNodeReturn ) {
	
		
		if ( bucket != null ) {	//	System.out.println(bucket + " "  + bucket.getListNode());
			ArrayList<Node> listNode = new ArrayList<Node> (bucket.getListNode()) ;
		
			listNode.parallelStream().forEach( node -> { 
				if ( ! listNodeReturn.contains(node) ) 
					listNodeReturn.add(node)  ;
			}) ;
		}	
	}
		
	public ArrayList<Edge> getListEdgeNeighbor ( Node node , double radius ) {
	
		ArrayList<Edge> listEdge = new ArrayList<Edge> ( ) ;
		
		for ( Node n : getListNodeNeighbor(node, radius)) 
			for ( Edge e : n.getEdgeSet() ) 
				if ( ! listEdge.contains(e))
					listEdge.add(e) ;
		
		return listEdge ;
	}
	
	public double getSizeBucket ( axis axis ) {
		
		switch (axis) {
			case X:
				return  sizeBucketX ;
			case Y:
				return  sizeBucketY ;	
			default:
				return 0.0 ;
		}
	} 
	
	public ArrayList<Node> getListNodeNeighbor ( Node node , double radius ) {
		
		if ( radius > Math.min(sizeBucketX, sizeBucketY) )
			radius = Math.min(sizeBucketX, sizeBucketY) ; 
		
		double [] 	coordBucket = bucket.getCoordBucket ( node ) ,
					nodeCoord = GraphPosLengthUtils.nodePosition(node) ;
		
		ArrayList <Node> listNode = new ArrayList<Node> (  bucket.getBucket(node).getListNode() ) ;
		
		// radius in bucket
		if ( nodeCoord[0] - coordBucket[0] + radius < 1 && nodeCoord[0] - coordBucket[0] - radius > 0 ) {
			if ( nodeCoord[1] - coordBucket[1]  + radius < 1 && nodeCoord[1] - coordBucket[1]  - radius > 0 )
				return listNode ;
		}		
		else { 
			// radius touch W bucket
			if ( nodeCoord[0] - coordBucket[0] >=  radius ) {
				listNode.addAll( bucket.getBucketNeighbor(node, bucketNeighbor.W).getListNode()) ; 
				if ( nodeCoord[1] - coordBucket[1] >=  radius ) {
					listNode.addAll( bucket.getBucketNeighbor(node, bucketNeighbor.S).getListNode()) ;
					listNode.addAll( bucket.getBucketNeighbor(node, bucketNeighbor.SW).getListNode()) ;
				} else {
					listNode.addAll( bucket.getBucketNeighbor(node, bucketNeighbor.N).getListNode()) ;
					listNode.addAll( bucket.getBucketNeighbor(node, bucketNeighbor.NW).getListNode()) ;
				}
			}
			// radius touch S bucket
			else if ( nodeCoord[1] - coordBucket[1] >=  radius ) {
				listNode.addAll( bucket.getBucketNeighbor(node, bucketNeighbor.S).getListNode()) ; 
				if ( nodeCoord[0] - coordBucket[0] >=  radius ) {
					listNode.addAll( bucket.getBucketNeighbor(node, bucketNeighbor.SW).getListNode()) ;
					listNode.addAll( bucket.getBucketNeighbor(node, bucketNeighbor.W).getListNode()) ;
				} else {
					listNode.addAll( bucket.getBucketNeighbor(node, bucketNeighbor.SE).getListNode()) ;
					listNode.addAll( bucket.getBucketNeighbor(node, bucketNeighbor.E).getListNode()) ;
				}
			}
			// radius touch E bucket
			else if ( nodeCoord[0] - coordBucket[0] +  radius >= sizeBucketX ) {
				listNode.addAll( bucket.getBucketNeighbor(node, bucketNeighbor.E).getListNode()) ; 
				if ( nodeCoord[1] - coordBucket[1] +  radius >= sizeBucketY ) {
					listNode.addAll( bucket.getBucketNeighbor(node, bucketNeighbor.N).getListNode()) ;
					listNode.addAll( bucket.getBucketNeighbor(node, bucketNeighbor.NE).getListNode()) ;
				} else {
					listNode.addAll( bucket.getBucketNeighbor(node, bucketNeighbor.S).getListNode()) ;
					listNode.addAll( bucket.getBucketNeighbor(node, bucketNeighbor.SE).getListNode()) ;
				}
			}
			
			// radius touch N bucket
			else if ( nodeCoord[1] - coordBucket[1] +  radius >= sizeBucketY ) {
				listNode.addAll( bucket.getBucketNeighbor(node, bucketNeighbor.N).getListNode()) ; 
				if ( nodeCoord[0] - coordBucket[0] +  radius >= sizeBucketX ) {
					listNode.addAll( bucket.getBucketNeighbor(node, bucketNeighbor.E).getListNode()) ;
					listNode.addAll( bucket.getBucketNeighbor(node, bucketNeighbor.NE).getListNode()) ;
				} else {
					listNode.addAll( bucket.getBucketNeighbor(node, bucketNeighbor.W).getListNode()) ;
					listNode.addAll( bucket.getBucketNeighbor(node, bucketNeighbor.NW).getListNode()) ;
				}
			}	
		}	
		return listNode ;	
	}
	
	public static int getBucketsCount ( ) {
		return bucketsCoord.size() ;
	}


}

