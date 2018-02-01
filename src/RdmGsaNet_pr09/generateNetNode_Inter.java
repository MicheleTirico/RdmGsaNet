package RdmGsaNet_pr09;



public interface generateNetNode_Inter {
	
	// COSTANTS
	public enum generateNodeType { threshold , moran , gradient , splitProb }
	
	// METHODS 
	public void generateNodeRule ( int step ) ;
	
	public void removeNodeRule (int step ) ;

	



}
