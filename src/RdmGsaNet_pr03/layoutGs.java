package hp_RdmGsaNet_pr03;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;


public interface layoutGs {
	
	// declaration of static paramethers
	public Graph GsGraph = new SingleGraph("GsGraph");
	
	public enum typeLayout { grid, Random, Gis }
	
	

//-------------------------------------------------------------------------------------------------------------	
	// methods
	public static void setMorp(Graph graph) {}	// method to define morphogens dstribution
		
	public static void showValueMorp(Graph graph) {}
		
	public static void showGraph(Graph graph) {}	
	
	public void setupTypelayout() ;

}
