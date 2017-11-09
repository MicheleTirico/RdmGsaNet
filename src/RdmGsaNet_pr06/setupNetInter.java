package RdmGsaNet_pr06;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import com.sun.javafx.geom.Edge;

import RdmGsaNet_pr06.setupNetInter.meanPointPlace;

public interface setupNetInter {
	
	// COSTANTS
	
//	public enum typeNet { grid ,seed , gis}
	public enum meanPointPlace {center, random , border }

	// METHODS	

	// method for createLayer
	void createLayerNet ();
	
	//method for setupGsNetLink
	void setGsAtr(Graph graph, meanPointPlace peppe) ;
	
	//method for setupGsNetLink
	void setNetAtr() ;
	
// COMMON METHODS
	public static void createLayer( )  {
		
		Graph gsGraph = setupGs.getGraph(setupGs.GsGraph);
		Graph netGraph = new SingleGraph("netGraph") ;
		
//		Graph gsGraph = setupGs.getGraph(gsGraph) ;	
	}
	
	// PRIVATE METHODS 
		static void setDefaultConnectionNode (Graph graph, int connection) {
			
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
			
			
		static void setDefaultLinkEdge (Graph graph, int connection) {
			if ( connection == 0 && connection == 1 ) {
//				for ( Edge e : graph.getEachEdge()) { 
			}
			else {
				System.out.println("value connection edge not in in range"); 
			}
			
			
		}
	

}
