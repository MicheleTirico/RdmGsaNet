package RdmGsaNet_Analysis_pr02;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import RdmGsaNetAlgo.graphIndicators;
import RdmGsaNetExport.handleNameFile;
import RdmGsaNetViz.handleVizStype;
import RdmGsaNetViz.handleVizStype.stylesheet;


public  class analysisLocal extends analysisMain  {
	
	// Costants 
	protected enum nodeIndicators { clustering , closeness , betweenness } 

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

	
	// ----------------------------------------------------------------------------------------------------------------------------------------------
		public static void main(String[] args) throws IOException, InterruptedException {
	
			// setup handle name file 
			handle = new handleNameFile( 
					/* handle file 						*/ true , 
					/* set folder 						*/ folder ,
					/* create new folder ? 				*/ false , 
					/*  manual name file (no in main )	*/ "analysis"
				);		

	// SET PARAMETERS ANALYSIS ----------------------------------------------------------------------------------------------------------------------
			analysisNet.setParamAnalysisLocal(
					/* normalize closeness		*/ true , 
					/* normalize betweenness 	*/ true
					);
			
	// SET WHICH LOCAL ANALYSIS TO COMPUTE ----------------------------------------------------------------------------------------------------------	
			analysisNet.setWhichLocalAnalysis(
					/* runVizLocal				*/ true  , 
					/* getImage					*/ false , 	// doesn't work
					/* computeLocalClustering	*/ false ,	
					/* compute closeness 		*/ true ,	// doesn't work with fix scale  
					/* compute betweenness 		*/ false
					);
			
			
	// RUN LOCAL ANALYSIS ---------------------------------------------------------------------------------------------------------------------------

			analysisNet.computeLocalStat( 3000, 5 , pathStart, pathStep , 10);
				
		}


// PRIVATE METHODS ----------------------------------------------------------------------------------------------------------------------------------
		

// GET METHODS --------------------------------------------------------------------------------------------------------------------------------------
	public static String getIndicator ( nodeIndicators ind ) {
		return ind.toString();
	}

	
}
