package RdmGsaNet_pr08;

import org.graphstream.graph.Graph;

public interface generateNetNodeInter {
	
	// COSTANTS
	public enum generateNodeType { threshold , moran , delta }
	
	public void generateNodeRule ( Graph graph , int step ) ;

	
}
