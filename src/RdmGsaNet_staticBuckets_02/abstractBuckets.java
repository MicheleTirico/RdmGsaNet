package RdmGsaNet_staticBuckets_02;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import RdmGsaNetAlgo.graphToolkit.element;
import RdmGsaNetAlgo.graphToolkit.elementTypeToReturn;

abstract class abstractBuckets {
	
	protected static Map < bucket , ArrayList<Node> > mapBuckets = new HashMap < bucket , ArrayList<Node> > () ;
	
	protected static Map < double[] , bucket > bucketsCoord = new HashMap < double[] , bucket > () ;
	
	protected static ArrayList<bucket> buckets = new ArrayList <bucket> ( ); 
	
	protected Graph graph ;
	
	protected bucket bucket ; 

	protected bucketSet bucketSet ; 
	
	protected static int numBucketsX , numBucketsY , numTotBuckets , graphSizeEdge;
	
	protected static double sizeGridX , sizeGridY , sizeBucketX , sizeBucketY ; 
}
