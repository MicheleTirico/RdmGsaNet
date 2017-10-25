package RdmGsaNet_pr05;

public interface gsAlgo  {
	
	public enum morp {activator, inhibitor }
	public enum interaction {reaction , diffusion, ext }
	
	public enum reactionType {ai2, linear }
	public enum diffusionType {D1, D2}
	public enum extType {E1, E2}
	
	
	
	// methods
	public void gsInit(boolean x) ;
	
	public void gsCompute() ;
	
	public static void gsAlgoMain (reactionType r, diffusionType d, extType e) {
		
		
		
	}
	
	
}
