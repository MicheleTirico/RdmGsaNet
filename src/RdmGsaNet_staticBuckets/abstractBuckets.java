package RdmGsaNet_staticBuckets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

abstract class abstractBuckets {

	protected Map < bucket , ArrayList<Node> > buckets = new HashMap < bucket , ArrayList<Node> > () ;

	protected Graph graph ;
	
	protected int graphSizeEdge ;
	
	protected int numBucketsX , numBucketsY , numTotBuckets ; 
	

	
}
