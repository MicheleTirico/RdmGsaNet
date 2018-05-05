package RdmGsaNet_staticBuckets_04;

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
import RdmGsaNet_staticBuckets_03.bucketSet.bucketNeighbor;
import demo.CanvasExportTask;


public class bucket extends abstractBuckets  { 	
	
	private ArrayList<Node> listNode = new ArrayList<Node>  ( ) ;
	
	private static String id ;
		
	// constructor
	public bucket ( bucketSet bucketSet , Node node  ) {		
		this.bucketSet = bucketSet ; 
			
		if ( node != null )
			listNode = new ArrayList<Node> (Arrays.asList(node)) ; 
	}
	
// create methods ----------------------------------------------------------------------------------------------------------------------------------- 
	
	// ceck if node has yet a bucket 
	protected static boolean ceckNodeHasBucket ( Node node ) {

		double [] coordBucket =  getCoordBucket ( node ) ;
		
		for ( double[] x : bucketsCoord.keySet() ) 
			if ( Arrays.equals(coordBucket, x ) ) 
				return true;	
						
		return false ; 
	}
			
	
// put methods --------------------------------------------------------------------------------------------------------------------------------------	
	public void putNode ( Node node ) {	
		
		// put node in bucket
		if ( ! getBucket(node).getListNode().contains(node) )
			getBucket(node).getListNode().add(node) ; //			System.out.println(listNode);		
	}
	
	public void putEdge ( Edge edge ) {
		
		getBucket(edge.getNode0() ).getListNode().add(edge.getNode0() ) ;
		getBucket(edge.getNode1() ).getListNode().add(edge.getNode1() ) ;
	}
	
	
// get methods --------------------------------------------------------------------------------------------------------------------------------------	
	
	protected ArrayList<Node> getListNode (  ) {
		return listNode;			
	}
	
	// get bucket from node 
	protected static bucket getBucket ( Node node ) {
		
		double [] coordBucket =  getCoordBucket ( node ) ;
		 
		for ( double[] x : bucketsCoord.keySet() ) 
			if ( Arrays.equals(coordBucket , x ) ) 
				return bucketsCoord.get(x);	
						
		return null ; 	
	}

	protected bucket getBucketNeighbor ( Node node , RdmGsaNet_staticBuckets_04.bucketSet.bucketNeighbor bucketNeighbor ) {
		
		double [] coordBucket = getCoordBucket ( node ) ;
		switch (bucketNeighbor) {
			case N: 
				return ceckAndGetBucket ( getBucket( new double[]  { coordBucket[0]  , coordBucket[1] + sizeBucketY  }  ));		
			
			case S :
				return ceckAndGetBucket ( getBucket( new double[]  { coordBucket[0]  , coordBucket[1] - sizeBucketY  }  ));		
			
			case E: 
				return ceckAndGetBucket ( getBucket( new double[]  { coordBucket[0] + sizeBucketX , coordBucket[1]  }  ));	
			
			case W :
				return ceckAndGetBucket ( getBucket( new double[]  { coordBucket[0] - sizeBucketX , coordBucket[1]  }  ));
			
			case NE :
				return ceckAndGetBucket ( getBucket( new double[]  { coordBucket[0] + sizeBucketX , coordBucket[1] + sizeBucketY   }  ));
			
			case NW :
				return ceckAndGetBucket ( getBucket( new double[]  { coordBucket[0] - sizeBucketX , coordBucket[1] + sizeBucketY   }  ));
				
			case SE :
				return ceckAndGetBucket ( getBucket( new double[]  { coordBucket[0] + sizeBucketX , coordBucket[1] - sizeBucketY   }  ));
		
			case SW :
				return ceckAndGetBucket ( getBucket( new double[]  { coordBucket[0] - sizeBucketX , coordBucket[1] - sizeBucketY   }  ));
				
			default :
					return null ; 			
			}
		}
		
	
	// get list bucket from node 
	protected ArrayList<bucket> getListBucket ( Node node ) {

		double [] coordBucket =  getCoordBucket ( node ) ;	
		ArrayList<bucket> listBuckets = new ArrayList<bucket> ( Arrays.asList(getBucket(node)) ) ;
				
		ceckBucketBuffer ( listBuckets ,getBucket( new double[]  { coordBucket[0] - sizeBucketX  , coordBucket[1] - sizeBucketY  }  )  ) ; 	
		ceckBucketBuffer ( listBuckets ,getBucket( new double[]  { coordBucket[0] + sizeBucketX  , coordBucket[1] + sizeBucketY  }  )  ) ;		
		ceckBucketBuffer ( listBuckets ,getBucket( new double[]  { coordBucket[0] + sizeBucketX  , coordBucket[1] - sizeBucketY  }  )  ) ;
		ceckBucketBuffer ( listBuckets ,getBucket( new double[]  { coordBucket[0] - sizeBucketX  , coordBucket[1] + sizeBucketY  }  )  ) ;
		
		ceckBucketBuffer ( listBuckets ,getBucket( new double[]  { coordBucket[0] , coordBucket[1] - sizeBucketY  }  )  ) ;
		ceckBucketBuffer ( listBuckets ,getBucket( new double[]  { coordBucket[0] , coordBucket[1] + sizeBucketY  }  )  ) ;
		ceckBucketBuffer ( listBuckets ,getBucket( new double[]  { coordBucket[0] - sizeBucketX  , coordBucket[1] }  )  ) ;
		ceckBucketBuffer ( listBuckets ,getBucket( new double[]  { coordBucket[0] + sizeBucketX  , coordBucket[1] }  )  ) ;
		
		return listBuckets ; 	
	}
	
	private void ceckBucketBuffer ( ArrayList<bucket>  listBuckets , bucket bucketToAdd ) {
		
		if ( bucketToAdd != null )
			listBuckets.add(bucketToAdd) ; 	
		else 
			listBuckets.add( new bucket(bucketSet, null) ) ;		
	}
	
	private bucket ceckAndGetBucket ( bucket bucketToAdd  ) {
		
		if ( bucketToAdd != null )
			return bucketToAdd ;
		else 
			return new bucket(bucketSet, null) ;
	}
	
	// get bucket from coord  
	protected static bucket getBucket ( double [ ] coord ) {
			
		double [] coordBucket =  getCoordBucket (coord) ;
		 
		for ( double[] x : bucketsCoord.keySet() ) 
			if ( Arrays.equals(coordBucket , x ) ) 
				return bucketsCoord.get(x);	
						
		return null ; 
	}
	
	// get coordinate of min vertex of bucket from node
	protected static double [] getCoordBucket ( Node node ) {
		try {
			double [ ]  coordBucket = new double[2] , 
						nodeCoord = GraphPosLengthUtils.nodePosition(node) ;		//	System.out.println("nodeCoord " + nodeCoord[0] + " " + nodeCoord[1] ) ;	//	System.out.println(sizeBucketX) ;
			
			coordBucket[0] = sizeBucketX * (int) ( nodeCoord[0] / sizeBucketX );
			coordBucket[1] = sizeBucketY * (int) ( nodeCoord[1] / sizeBucketY ) ;	
			
			return coordBucket ;
		}
		catch (NullPointerException e) {
			return null ; 
		}
	}
			
	// get coord of bucket from generic coordinates 
	protected static double [] getCoordBucket  ( double []  coord ) {
		
		double [ ]  coordBucket = new double[2] ;		
		
		coordBucket[0] = (int) ( coord[0] / sizeBucketX );
		coordBucket[1] = (int) ( coord[1] / sizeBucketY ) ;	
		
		return coordBucket ;
	}	
}
