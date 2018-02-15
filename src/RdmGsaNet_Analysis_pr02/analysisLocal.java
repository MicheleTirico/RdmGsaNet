package RdmGsaNet_Analysis_pr02;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import RdmGsaNetExport.handleNameFile;
import RdmGsaNetViz.handleVizStype;
import RdmGsaNetViz.handleVizStype.stylesheet;


public  class analysisLocal extends analysisMain  {
	
	// Costants 
	protected enum nodeIndicators { clustering , closeness } 
	
	
	// handle viz 
	
	
	// MAP FOR CHARTS
	protected static Map	
	// MAP NET						
							map = new HashMap () , 	
	// MAP NET
							map2 = new HashMap () ;
		
	// HANDLE FILE OBJECT
	protected static handleNameFile handle ;

	private static analysisDGSnet analysisNet = new analysisDGSnet(
			/* id dgs 					*/		"dgsNet" , 
			/* run analysis	local		*/		true 
			);
	
	// Initialize net analysis
	private static analysisDGSgs analysisGs = new analysisDGSgs(
			/* id dgs 					*/		"dgsGs" , 
			/* run analysis				*/		true 
			);

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
					/* computeLocalClustering	*/ false ,
					/* compute closeness 		*/ true 
					);
			
			
	// RUN LOCAL ANALYSIS ------------------------------------------------------------------------------------------------------------------------------

			analysisNet.computeLocalStat(3000, 5, pathStart, pathStep , 10 );
				
		}


// PRIVATE METHODS ----------------------------------------------------------------------------------------------------------------------------------
		// GET METHODS --------------------------------------------------------------------------------------------------------------------------------------
		public static String getIndicator ( nodeIndicators ind ) {
			return ind.toString();
		}

	
}
