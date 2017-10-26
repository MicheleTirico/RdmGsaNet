package RdmGsaNet_pr05;

public class gsAlgoExt implements gsAlgo {

	// variables
		extType type;
		morphogen morp;
		
		// costructor
		public gsAlgoExt (extType type, morphogen morp) {
			this.type = type;
			this.morp = morp;
		}

		// methods
		public static double gsComputeExt( extType type, morphogen morp, 
				double feed, double kill, 
				double act, double inh ) {

			double ext;
		
			switch (type) {
			case gsModel: {
				if ( morp == morphogen.activator ) {
					 ext = feed * ( 1 - act ) ;
				}
				if  ( morp == morphogen.inhibitor ) {
					 ext = ( kill + feed) * inh ;
				}
				else {  ext = 0 ; System.out.println(" morp not defined"); }
				break;
			}
			
			case test: {
				System.out.println("type " + type + "not defined"); ext = 0 ; break ; }
			
			default: { System.out.println("ext not defined") ;  ext = 0 ; }
			
			}
			
			return ext;
		}
	

}
