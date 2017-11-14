package RdmGsaNet_pr08;

public class generateNetEdge {
	
	private generateNetEdgeInter type ;
	private static generateNetEdge growth ;
	
	public generateNetEdge ( generateNetEdgeInter type) {
		this.type = type ;
	}
	
	public void generateEdge (int step) {
		type.generateEdgeRule (step) ;
	}

//--------------------------------------------------------------------------------------------------------------------
	// private methods
	
	public static generateNetEdge getGrowthNet () { return growth ; }



}