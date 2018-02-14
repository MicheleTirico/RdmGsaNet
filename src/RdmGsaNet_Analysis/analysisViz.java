package RdmGsaNet_Analysis;

import java.io.IOException;

import RdmGsaNetExport.handleNameFile;
import RdmGsaNetViz.handleVizStype;
import RdmGsaNetViz.handleVizStype.stylesheet;

public class analysisViz extends analysisMain {

	// costants 
	
	private static analysisDGSnet analysisNet = new analysisDGSnet(
			/* id dgs 					*/		"dgsNet" , 
			/* run analysis	global		*/		false ,
			/* run analysis	local		*/		true 
			);
	
	// HANDLE FILE OBJECT
	protected static handleNameFile handle ;
		
// --------------------------------------------------------------------------------------------------------------------------------------------------
	public static void main(String[] args) throws IOException, InterruptedException {
		
		// setup handle name file 
		handle = new handleNameFile( 
			/* handle file 						*/ true , 
			/* set folder 						*/ folder ,
			/* create new folder ? 				*/ false , 
			/*  manual name file (no in main )	*/ "analysis"
		);		

				
// SET WHICH LOCAL ANALYSIS TO COMPUTE --------------------------------------------------------------------------------------------------------------
		analysisNet.setWhichLocalAnalysis(
				/* runVizLocal				*/ false , 
				/* computeLocalClustering	*/ true 
				);
		
		
// RUN LOCAL ANALYSIS ------------------------------------------------------------------------------------------------------------------------------

		analysisNet.computeLocalStat(3000, 5, pathStartNet, pathStepNet );
			
	}
	
}
