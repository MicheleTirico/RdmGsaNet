package RdmGsaNet_pr09;

public class generateNetEdge {
	
	private static generateNetEdge_Inter type ;
	private static generateNetEdge growth ;
	
	public generateNetEdge ( generateNetEdge_Inter type) {
		this.type = type ;
	}
	
	public void generateEdge ( double step ) {
		type.generateEdgeRule ( step ) ;
	}

//--------------------------------------------------------------------------------------------------------------------
	// private methods
	
	public static generateNetEdge 	getGrowthNet () 	{ return growth ; }

	public static String 			getGenerateType () 	{ return type.getClass().getSimpleName(); }



}