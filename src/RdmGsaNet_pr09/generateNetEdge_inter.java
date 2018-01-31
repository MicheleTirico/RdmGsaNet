package RdmGsaNet_pr09;

public interface generateNetEdge_inter {

	// costants
	public enum generateEdgeType { near , preferentialAttachment  }
	
	
	// METHODS
	public void generateEdgeRule ( double step ) ;

	void removeEdgeRule(double step);
}
