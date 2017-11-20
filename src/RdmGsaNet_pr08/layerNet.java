package RdmGsaNet_pr08;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class layerNet {	
	
	// COSTANTS
	// initialization graph net
	private static Graph netGraph = new SingleGraph("netGraph");
	private static Graph gsGraph = layerGs.getGraph();
	
	private setupNetInter layout;
	
	// COSTRUCTOR
	public layerNet (setupNetInter layout ) {
		this.layout = layout ;
	}
	
	// method to change layer setup
	public void changeLayer ( setupNetInter layout) {
		this.layout = layout ;
	}
	
	// method to create layer Net
	public void createLayer (boolean setDefaultAtr) {
		layout.createLayerNet ();	
		if (setDefaultAtr == true ) { setDefaultAtr () ; }
	}
	
	// methood that call 2 other methods ( both methods are declared but not implemented in interface setupNetInter ) : 
		// setGsAtr : add to each node of gs graph an attribute that means we have like between gs and net graph
		// setNetAtr : add attributes to graph net in order to have connection with gs graph
	public void setupGsNetLink(setupNetSeed.meanPointPlace point) {
		
		layout.setGsAtr ( point ) ; 
		layout.setNetAtr ( netGraph ) ;
	}
	 
		
// PRIVATE METHODS-----------------------------------------------------------------------------------------------------	
	// method to set default values to network
	private void setDefaultAtr ( ) {
		for ( Node n : netGraph.getEachNode() ) {
			n.addAttribute( "idGs" , 0 );
			n.addAttribute( "con" , 0 );
		}
	}
// Get Methods -----------------------------------------------------------------------------------------------------	
	// get graph
	public static Graph getGraph ( ) { return netGraph; }

	
	
	
}