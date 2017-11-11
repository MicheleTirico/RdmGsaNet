package RdmGsaNet_pr08;

public class growthNet {
	
	private growthNetInter type ;
	private static growthNet growth ;
	
	public growthNet ( growthNetInter type) {
		this.type = type ;
	}
	
	public void growth (int step) {
		type.growthRules(step) ;
	}

//--------------------------------------------------------------------------------------------------------------------
	// private methods
	
	public static growthNet getGrowthNet () { return growth ; }



}