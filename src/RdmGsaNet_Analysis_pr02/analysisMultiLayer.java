package RdmGsaNet_Analysis_pr02;

import java.io.IOException;

import RdmGsaNetViz.handleVizStype;
import RdmGsaNetViz.handleVizStype.stylesheet;
import RdmGsaNet_Analysis.analysisDGSCombinedLayer;

public class analysisMultiLayer extends analysisMain  {

	// hnadle viz 
	protected static handleVizStype netViz  = new handleVizStype( netGraph ,stylesheet.manual, "seedGrad") ,
									gsViz 	= new handleVizStype( gsGraph ,stylesheet.viz10Color, "gsInh") ;
		
	static analysisDGSmultiLayer combinedAnalysis = new analysisDGSmultiLayer (
				/* gsViz	*/ 	true ,
				/* netViz	*/	true	
				);
		
	public static void main ( String[ ] args ) throws IOException, InterruptedException {

		combinedAnalysis.computeGlobalStat (3000, 5, pathStart, pathStep , 10 );
			
			
		
	}
	

		
}
