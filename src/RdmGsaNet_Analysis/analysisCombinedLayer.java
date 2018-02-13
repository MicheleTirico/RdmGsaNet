 package RdmGsaNet_Analysis;

import java.io.IOException;

import RdmGsaNetViz.multiViz;

public class analysisCombinedLayer extends analysisMain {
	
	static analysisDGSCombinedLayer combinedAnalysis = new analysisDGSCombinedLayer (
			/* gsViz	*/ 	true ,
			/* netViz	*/	true	
			);

	
	
	
	
			
	public static void main ( String[ ] args ) throws IOException, InterruptedException {
			
		combinedAnalysis.setPath( "dgsNet" , pathStartNet, pathStepNet);
		combinedAnalysis.setPath( "dgsGs" , pathStartGs, pathStepGs);
	
		combinedAnalysis.runCombinedLayerStat( 5000 , 5 );
		
		
	
	}
}

