package RdmGsaNet_pr05;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class setupNetLayer {	
	  
//    private setupNet.layout typeLayout; 
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
	
	public void InitLayer (int size , setupNet.layout typeLayout) {
		type.setupNetInit( size , typeLayout);
		
		// define all operation in common for each type net
		setDefaultConnection(setupNet.netGraph , 1);	
	}
	
//-----------------------------------------------------------------------------------------------------	
	// PRIVATE METHODS 
	public static void setDefaultConnection (Graph graph, int connection) {
		if (connection != 0 && connection != 1) {
			System.out.println("value connection not in in range"); 
		}
		
		// add attribute to know if netGraph node is connected to gsGraph node
			// conn = 0 -> node not connect
			// conn = 1 -> node connect
		
		for ( Node n : graph.getEachNode()) { n.addAttribute("conn", connection); }
	}

	
	
	
}
