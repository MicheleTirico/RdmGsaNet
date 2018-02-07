 package RdmGsaNet_Analysis;

import java.io.IOException;

import RdmGsaNetViz.multiViz;

public class analysisViz extends analysisMain {
	
	static multiViz multiViz = new multiViz(
			
			/* gsViz	*/ 	true ,
			/* netViz	*/	true
			
			);

	
	
			
	public static void main ( String[ ] args ) throws IOException, InterruptedException {
			
		multiViz.setPath( "dgsNet" , pathStartNet, pathStepNet);
		multiViz.setPath( "dgsGs" , pathStartGs, pathStepGs);
	
		multiViz.runMultiLayerViz( 5000 , 5 );
		
		
	
	}
}

