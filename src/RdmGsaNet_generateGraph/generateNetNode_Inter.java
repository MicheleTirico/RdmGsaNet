package RdmGsaNet_generateGraph;

import java.io.IOException;

public interface generateNetNode_Inter {
	
	// COSTANTS
	public enum generateNodeType { threshold , moran , gradient , splitProb }

	
	
	// METHODS 
	public void generateNodeRule ( int step ) throws IOException ;
	
	public void removeNodeRule (int step ) ;


}
