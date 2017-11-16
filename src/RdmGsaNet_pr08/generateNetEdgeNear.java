package RdmGsaNet_pr08;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class generateNetEdgeNear implements generateNetEdgeInter{

	// VARIABLES
	static double radius ;	
	private static Graph gsGraph = layerGs.getGraph();
	private static Graph netGraph = layerNet.getGraph();
	
	// COSTRUCTOR
	public generateNetEdgeNear( double radius ) {
		this.radius = radius ;	
	}	
	

	// PUBLIC METHODS
	@Override 
	public void generateEdgeRule(int step) {

		Node n1 = null ;
		Node n2 = NearNode( n1 );
		
		createEdge (n1 , n2) ;
		
	}

	@Override
	public void removeEdgeRule(int step) {
		// TODO Auto-generated method stub
		
	}


//---------------------------------------------------------------------------------------------------------
	// PRIVATE METHODS
	
	// return node closer to a fixed node in radius . 
	// if we have more than one node closer ??
	private static Node NearNode (Node n ) {
		
		
		
		
		Node nearNode = null ;
		
		
		return nearNode ;
	}
	
	private static void createEdge ( Node n1 , Node n2 ) {
		
	}



	
	
}