package RdmGsaNet_pr04;

public interface gsAlgo {
	
	// 	VARIABLES
	final String reactionType = setupGs.getReactionType();
	final String diffusionType = setupGs.getDiffusionType();
	
	final double kill = setupGs.getKill();
	final double feed = setupGs.getFeed();
	final double Da = setupGs.getDa();
	final double Di = setupGs.getDi();
	
	public enum morp {activator, inhibitor }
	public enum interaction {reaction , diffusion, incoming, outcoming}
	
	 
	
	
	
	
	
	// METHODS
	public static void gsAlgoMain () {
		gsAlgoReaction act = new gsAlgoReaction();
		act.gsAlgoCalcMorp( x );
		
		
	}
	
	
	public double gsAlgoCalcMorp( morp x) ;
	
	public void gsAlgoInter(interaction x);
	
	public void gsAlgoExpMorp() ;
	
	public void getValue(double x);



}
