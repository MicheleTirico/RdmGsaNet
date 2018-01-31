package RdmGsaNet_pr09;

import org.graphstream.graph.Graph;

public interface generateNetNode_Inter {
	
	// COSTANTS
	public enum generateNodeType { threshold , moran , gradient , splitProb }
	
	public void generateNodeRule ( int step ) ;
	
	public void removeNodeRule (int step ) ;

//	public void setSeedNodes(int step);
	



}
