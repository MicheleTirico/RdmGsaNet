package RdmGsaNet_staticBuckets;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import RdmGsaNetAlgo.graphGenerator;

public class testBukets {
	
	static Graph graph = new SingleGraph ("graph");
	
	private bucketSet bucketSet = new bucketSet(true, graph) ;
	
	
	public static void main ( String[ ] args ) {
		
		double [] meanPointCoord = new double [2] ;
		meanPointCoord[0] = 5 ;
		meanPointCoord[1] = 5 ;
		
		graphGenerator.createCompleteGraph(graph, 10, meanPointCoord, 3, 10);
		graph.display();
		
		
	
	}

}
