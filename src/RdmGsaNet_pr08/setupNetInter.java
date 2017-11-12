package RdmGsaNet_pr08;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.SingleGraph;

public interface setupNetInter {
	
	// COSTANTS
	
//	public enum typeNet { grid ,seed , gis}
	public enum meanPointPlace {center, random , border }

	// METHODS	

	// method for createLayer
	void createLayerNet ();
	
	//method for setupGsNetLink
	void setGsAtr(Graph graph, meanPointPlace point) ;
	
	//method for setupGsNetLink
	void setNetAtr() ;

//--------------------------------------------------------------------------------------------------------------------------------
// COMMON METHODS
	/*
	public static void createLayer( )  {
		Graph gsGraph = layerGs.getGraph(layerGs.gsGraph);
		Graph netGraph = new SingleGraph("netGraph") ;	
//		Graph gsGraph = setupGs.getGraph(gsGraph) ;	
	}
	*/

	// method to add for each node of gsGraph a default value of connection to NetGraph		
	public static void setDefaultConnectionNode (Graph graph, int connection) {
		
			// add attribute to know if netGraph node is connected to gsGraph node
			// conn = 0 -> node not connect
			// conn = 1 -> node connect
			
		if (connection != 0 & connection != 1) {
			System.out.println("value connection node not in in range"); 
		}
		else {
			for ( Node n : graph.getEachNode()) { n.addAttribute("con", connection);  }
		}
	}
			
	// method to add for each edge of gsGraph a default value of connection to NetGraph	
	public static void setDefaultLinkEdge (Graph graph, int connection) {
		if ( connection != 0 && connection != 1 ) {
			System.out.println("value connection edge not in in range"); 
		}
		else {
			for ( Edge e : graph.getEachEdge() ) { e.addAttribute("con", connection ); }	
		}
	}
}
