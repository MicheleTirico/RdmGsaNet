package RdmGsaNet_pr08;

public interface generateNetEdgeInter {

	// costants
	public enum generateEdgeType { near , preferentialAttachment  }
	
	
	// METHODS
	public void generateEdgeRule ( int step ) ;
	
	public void removeEdgeRule ( int step ) ;
}
