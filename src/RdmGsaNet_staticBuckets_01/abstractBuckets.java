package RdmGsaNet_staticBuckets_01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import RdmGsaNetAlgo.graphToolkit.element;
import RdmGsaNetAlgo.graphToolkit.elementTypeToReturn;

abstract class abstractBuckets {

	protected enum elementTypeToReturn { element , string , integer }
	protected elementTypeToReturn elementType ;
	
	protected enum element { node , edge }
	protected element element ;
	
	protected static Map < bucket , ArrayList<Node> > buckets = new HashMap < bucket , ArrayList<Node> > () ;
	
	protected static Map < String , bucket > bucketsId = new HashMap < String , bucket > () ;
	
	protected static Map < double[] , bucket > bucketsCoord = new HashMap < double[] , bucket > () ;

	protected ArrayList<Node> listNode = new ArrayList<Node> () ;
	
	protected Graph graph ;
	
	protected static bucket bucket ; 

	protected bucketSet bucketSet ; 
	
	protected static int numBucketsX , numBucketsY , numTotBuckets , graphSizeEdge;
	
	protected static double sizeGridX ;

	protected static double sizeGridY; 
	
	protected static double sizeBucketX , sizeBucketY ; 
}
