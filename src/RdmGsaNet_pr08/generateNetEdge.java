package RdmGsaNet_pr08;

public class generateNetEdge {
	
	private static generateNetEdgeInter type ;
	private static generateNetEdge growth ;
	
	public generateNetEdge ( generateNetEdgeInter type) {
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