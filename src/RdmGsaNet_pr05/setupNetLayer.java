package RdmGsaNet_pr05;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

//public class setupNetLayer {
public class setupNetLayer {	
	  
    private setupNet.start startPoint; 
	private setupNet type;
	
	public setupNetLayer ( setupNet type ) {
		this.type = type;
	}
	
	public void changeLayer ( setupNet type ) {
		this.type = type ;
	}
	
	public void createLayer () {
		type.setupNetCreate();
	}
	
	public void InitLayer (int size , setupNet.start startPoint ) {
		type.setupNetInit( size , startPoint);
		
		// define all operation in common for each type net
		setDefaultConnection(setupNet.netGraph , 0);	
	}
	
//-----------------------------------------------------------------------------------------------------	
	// PRIVATE METHODS 
	private void setDefaultConnection (Graph graph, int connection) {
		
		// add attribute to know if netGraph node is connected to gsGraph node
			// conn = 0 -> node not connect
			// conn = 1 -> node connect
		
		for ( Node n : graph.getEachNode()) { n.addAttribute("conn", connection); }
	}

	
	
	
}
