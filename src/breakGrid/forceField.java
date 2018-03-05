package breakGrid;

import java.util.Iterator;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class forceField {

	// COSTANTS 
	private Graph graph = new SingleGraph("graph");
	private String attribute ;
	
	
	public forceField ( Graph graph , String attribute ) {
		this.graph = graph ;
		this.attribute = attribute ;
	}
	
	public void computeForce (Node node) {

		double nodeValAttr = node.getAttribute(attribute) ;
		
		Iterator<Node> iter = node.getNeighborNodeIterator() ;		
		while (iter.hasNext()) {
			 
			Node neig = iter.next() ;
			double neigValAttr = neig.getAttribute(attribute);	
		}
		
		
		
		
	}
	
//	public double get
	public void computeDirection () {
		
	}
}
