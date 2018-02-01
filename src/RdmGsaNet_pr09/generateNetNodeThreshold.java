package RdmGsaNet_pr09;

import java.util.Iterator;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

/* in this class, we generate a node in netLayer if act and inh are greater 
 * than a fixed threshold
 */

public class generateNetNodeThreshold implements generateNetNode_Inter {
	
	// COSTANTS AND VARIABLES
	private double  thAct , thInh ;
	private Graph gsGraph = layerGs.getGraph();
	
	// COSTRUCTOR
	public generateNetNodeThreshold (double thAct , double thInh) {
		this.thAct = thAct ;
		this.thInh = thInh ;
	}

	public void generateNodeRule( int step) {			//	System.out.println(thAct);
		
		// iterator
		Iterator<Node> iterNode = gsGraph.getNodeIterator();
		
		while (iterNode.hasNext()) {

			Node n = iterNode.next();
			
			double act = n.getAttribute("gsAct") ;	
			double inh = n.getAttribute("gsInh") ;		
	 
			if ( act >= thAct && inh >= thInh ) 	
				// get attribute "con" to node , in order to see new connection between layers
				n.setAttribute( "con", 1 );			
		}	
	}

	@Override
	public void removeNodeRule(int step) {
		// TODO Auto-generated method stub
		
	}

	
}