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
	
		


		//	nameStartGs = "layerGs_start_setupGsGrid_grid8_size_50_Da_0.2_Di_0.1_f_0.03_k_0.062_diff_weight" ;
					//handleNameFile.getCompleteNameInFolder(folder, "layerGs_Start")  ;

		combinedAnalysis.computeGlobalStat (3000 , 5 , pathStart , pathStep , 10 );
			
			
		
	}
	

		
}
