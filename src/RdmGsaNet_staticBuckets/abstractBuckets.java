package RdmGsaNet_staticBuckets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

abstract class abstractBuckets {

	protected static Map < bucket , ArrayList<Node> > buckets = new HashMap < bucket , ArrayList<Node> > () ;
	protected static Map < String , bucket > bucketsId = new HashMap < String , bucket > () ;
	protected static Map < double[] , bucket > bucketsCoord = new HashMap < double[] , bucket > () ;


	protected Graph graph ;
	
	protected bucket bucket ; 
	protected int graphSizeEdge ;

//	public bucket bucket;
	
	protected static int numBucketsX , numBucketsY , numTotBuckets ;
	
	protected static double sizeGridX ;

	protected static double sizeGridY; 
	
	protected static double sizeBucketX , sizeBucketY ; 
}
