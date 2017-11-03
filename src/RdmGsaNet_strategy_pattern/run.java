package RdmGsaNet_strategy_pattern;

/* applicazione del pattern strategy per la scelta di diverse strategie di analisi e algoritmi in generale.
 * Applicabile anche nel caso di scelta dei layout di gs e net.

*/

public class run {
	
	public static void main(String[] args) {
		
		gsAlgoMain main = new gsAlgoMain(new gsAlgoReaction());
		
		main.setInter(new gsAlgoDiffusion()); 		// definisco l'algoritmo di analisi
		main.interaction();							// eseguo algoritmo
		main.setInter(new gsAlgoDiffusion());		// cambio algoritmo
		
		gsAlgoMain main2 = new gsAlgoMain( new gsAlgoDiffusion());
		
		main2.interaction();
	
		 
		
		
		
	}

}
