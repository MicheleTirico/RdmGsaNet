package RdmGsaNet_pr08;

public class gsAlgoExt {

	// variables
	private gsAlgo.extType type ;
	private gsAlgo.morphogen morp ;
		
	// constructor ( define type of externale effects and morphogen )
	public gsAlgoExt (gsAlgo.extType type, gsAlgo.morphogen morp) {
		this.type = type ;
		this.morp = morp ;
	}

		// method to compute external effets
		public static double gsComputeExt( gsAlgo.extType type, gsAlgo.morphogen morp, double feed, double kill, 
				double act0, double inh0 ) {

			double ext ;
			
			switch (type) {
			
			case gsModel : 
				if ( morp == gsAlgo.morphogen.activator ) { ext = feed * ( 1 - act0 ) ; }
				else {
					if (morp == gsAlgo.morphogen.inhibitor ) { ext = ( kill + feed ) * inh0 ; }
					else { ext = 0 ; System.out.println(" morp not defined"); }
				}
				break;
			
			case test :
				if ( morp == gsAlgo.morphogen.activator ) { ext = 0 ; } 
				else {
					if (morp == gsAlgo.morphogen.inhibitor ) { ext = 0 ; }
					else { ext = 0 ; System.out.println(" morp not defined"); }
				}
				break;
				
			default: 
				{ System.out.println("ext type not defined") ;  ext = 0 ; }
				break;
			}
			
			return ext;
		}
}
		
			
			
			
			

