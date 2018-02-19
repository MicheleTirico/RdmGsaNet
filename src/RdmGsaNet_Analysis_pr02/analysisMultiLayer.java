package RdmGsaNet_Analysis_pr02;

import java.io.IOException;

import RdmGsaNetExport.handleNameFile;
import RdmGsaNetViz.handleVizStype;
import RdmGsaNetViz.handleVizStype.stylesheet;
import RdmGsaNet_Analysis.analysisDGSCombinedLayer;

public class analysisMultiLayer extends analysisMain  {

	// hnadle viz 
	
		
	static analysisDGSmultiLayer combinedAnalysis = new analysisDGSmultiLayer (
				/* gsViz	*/ 	true ,
				/* netViz	*/	true	
				);
		
	public static void main ( String[ ] args ) throws IOException, InterruptedException {
	
		combinedAnalysis.computeGlobalStat (3000 , 5 , pathStart , pathStep , 10 );
			
			
		
	}
	

		
}
