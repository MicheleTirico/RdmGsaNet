package RdmGsaNet_pr08;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class layerNet {	
	
	// COSTANTS
	// initialization of graph net
	Graph netGraph = new SingleGraph("netGraph");
	Graph gsGraph = layerGs.getGraph(layerGs.gsGraph);
	
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
	public void createLayer () {
		layout.createLayerNet ();	
	}
	
	// methood that call 2 other methods ( both methods are declared but not implemented in interface setupNetInter ) : 
		// setGsAtr : add to each node of gs graph an attribute that means we have like between gs and net graph
		// setNetAtr : add attributes to graph net in order to have connection with gs graph
	public void setupGsNetLink(setupNetInter.meanPointPlace point) {
		layout.setGsAtr(gsGraph, point );
		layout.setNetAtr();
	}
		
//-----------------------------------------------------------------------------------------------------	
	
	

	
	
	
}
