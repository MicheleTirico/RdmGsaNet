package RdmGsaNet_pr07;

public class growthNet {
	
	private growthNetInter type ;
	
	public growthNet ( growthNetInter type) {
		this.type = type ;
	}
	
	
	public void runGrowth () {
		type.growthRules() ;
	}
}
