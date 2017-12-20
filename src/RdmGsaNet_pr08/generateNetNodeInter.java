package RdmGsaNet_pr08;

import org.graphstream.graph.Graph;

public interface generateNetNodeInter {
	
	// COSTANTS
	public enum generateNodeType { threshold , moran , gradient}
	
	public void generateNodeRule ( int step ) ;
	
	public void removeNodeRule (int step ) ;
	


}
