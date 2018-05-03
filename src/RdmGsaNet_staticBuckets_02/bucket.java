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
	
	private ArrayList<Node> listNode = new ArrayList<Node>  ( ) ;
	private ArrayList<Node> listEdge = new ArrayList<Node>  ( ) ;
	
	private static String id ;
		
	// constructor
	public bucket ( bucketSet bucketSet , Node node  ) {		
		this.bucketSet = bucketSet ; 
		
		buckets.add(bucket) ;
		if ( node != null )
			listNode = new ArrayList<Node> (Arrays.asList(node)) ; 
	}
	
// create methods ----------------------------------------------------------------------------------------------------------------------------------- 
	
	// ceck if node has yet a bucket 
	protected static boolean ceckNodeHasBucket ( Node node ) {

		double [] coordBucket =  getCoordBuket ( node ) ;
		
		for ( double[] x : bucketsCoord.keySet() ) 
			if ( Arrays.equals(coordBucket, x ) ) 
				return true;	
						
		return false ; 
	}
			
	
// put methods --------------------------------------------------------------------------------------------------------------------------------------	
	public void putNode ( Node node ) {	
		
		// put node in bucket
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
		
		double [] coordBucket =  getCoordBuket ( node ) ;
		 
		for ( double[] x : bucketsCoord.keySet() ) 
			if ( Arrays.equals(coordBucket , x ) ) 
				return bucketsCoord.get(x);	
						
		return null ; 	
	}
	
	// get list bucket from node 
	protected ArrayList<bucket> getListBucket ( Node node ) {

		double [] coordBucket =  getCoordBuket ( node ) ;	
		ArrayList<bucket> listBuckets = new ArrayList<bucket> ( Arrays.asList(getBucket(node)) ) ;
			
	//	ceckBucketBuffer ( listBuckets , getBucket( new double[]  { coordBucket[0] - sizeBucketX  , coordBucket[1] - sizeBucketY  }  )  ) ;
		
		ceckBucketBuffer ( listBuckets ,getBucket( new double[]  { coordBucket[0] - sizeBucketX  , coordBucket[1] - sizeBucketY  }  )  ) ; 	
		ceckBucketBuffer ( listBuckets ,getBucket( new double[]  { coordBucket[0] + sizeBucketX  , coordBucket[1] + sizeBucketY  }  )  ) ;		
		ceckBucketBuffer ( listBuckets ,getBucket( new double[]  { coordBucket[0] + sizeBucketX  , coordBucket[1] - sizeBucketY  }  )  ) ;
		ceckBucketBuffer ( listBuckets ,getBucket( new double[]  { coordBucket[0] - sizeBucketX  , coordBucket[1] + sizeBucketY  }  )  ) ;
		
		ceckBucketBuffer ( listBuckets ,getBucket( new double[]  { coordBucket[0] , coordBucket[1] - sizeBucketY  }  )  ) ;
		ceckBucketBuffer ( listBuckets ,getBucket( new double[]  { coordBucket[0] , coordBucket[1] + sizeBucketY  }  )  ) ;
		ceckBucketBuffer ( listBuckets ,getBucket( new double[]  { coordBucket[0] - sizeBucketX  , coordBucket[1] }  )  ) ;
		ceckBucketBuffer ( listBuckets ,getBucket( new double[]  { coordBucket[0] + sizeBucketX  , coordBucket[1] }  )  ) ;
		
		/*
		listBuckets.add(getBucket( new double[]  { coordBucket[0] - sizeBucketX  , coordBucket[1] - sizeBucketY  }  )  ) ; 	
		listBuckets.add(getBucket( new double[]  { coordBucket[0] + sizeBucketX  , coordBucket[1] + sizeBucketY  }  )  ) ;		
		listBuckets.add(getBucket( new double[]  { coordBucket[0] + sizeBucketX  , coordBucket[1] - sizeBucketY  }  )  ) ;
		listBuckets.add(getBucket( new double[]  { coordBucket[0] - sizeBucketX  , coordBucket[1] + sizeBucketY  }  )  ) ;
		
		listBuckets.add(getBucket( new double[]  { coordBucket[0] , coordBucket[1] - sizeBucketY  }  )  ) ;
		listBuckets.add(getBucket( new double[]  { coordBucket[0] , coordBucket[1] + sizeBucketY  }  )  ) ;
		listBuckets.add(getBucket( new double[]  { coordBucket[0] - sizeBucketX  , coordBucket[1] }  )  ) ;
		listBuckets.add(getBucket( new double[]  { coordBucket[0] + sizeBucketX  , coordBucket[1] }  )  ) ;
		*/
//		System.out.println(listBuckets.size());
//		System.out.println(getBucket( new double[]  { coordBucket[0] + sizeBucketX  , coordBucket[1] - sizeBucketY  } ) ) ;
//		System.out.println( coordBucket[1] - sizeBucketY ) ; 
		return listBuckets ; 	
	}
	
	private void ceckBucketBuffer ( ArrayList<bucket>  listBuckets , bucket bucketToAdd ) {
		
		if ( bucketToAdd != null )
			listBuckets.add(bucketToAdd) ; 	
		else 
			listBuckets.add( new bucket(bucketSet, null) ) ;
	}
	
	// get bucket from coord  
	protected static bucket getBucket ( double [ ] coord ) {
			
		double [] coordBucket =  getCoordBuket (coord) ;
		 
		for ( double[] x : bucketsCoord.keySet() ) 
			if ( Arrays.equals(coordBucket , x ) ) 
				return bucketsCoord.get(x);	
						
		return null ; 
	}
	
	// get coordinate of min vertex of bucket from node
	protected static double [] getCoordBuket ( Node node ) {
		
		double [ ]  coordBucket = new double[2] , 
					nodeCoord = GraphPosLengthUtils.nodePosition(node) ;		//	System.out.println("nodeCoord " + nodeCoord[0] + " " + nodeCoord[1] ) ;	//	System.out.println(sizeBucketX) ;
		
		coordBucket[0] = (int) ( nodeCoord[0] / sizeBucketX );
		coordBucket[1] = (int) ( nodeCoord[1] / sizeBucketY ) ;	
		
		return coordBucket ;
	}
			
	// get coord of bucket from generic coordinates 
	protected static double [] getCoordBuket  ( double []  coord ) {
		
		double [ ]  coordBucket = new double[2] ;		
		
		coordBucket[0] = (int) ( coord[0] / sizeBucketX );
		coordBucket[1] = (int) ( coord[1] / sizeBucketY ) ;	
		
		return coordBucket ;
	}	
}
