package RdmGsaNet_Analysis_pr02;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import RdmGsaNetExport.handleNameFile;
import RdmGsaNet_Analysis_pr02.analysisLocal.nodeIndicators;

public class analysisMultiSim extends analysisMain {

	
	// Costants 


		// MAP FOR CHARTS
		protected static Map	
		// MAP NET						
								map = new HashMap () , 	
		// MAP NET
								map2 = new HashMap () ;
			
		// HANDLE FILE OBJECT
		protected static handleNameFile handle ;

		private static analysisDGSmultiSim multiSim = new analysisDGSmultiSim(
				
				) ;
		
		
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
				multiSim.setParamAnalysisLocal(
				
						);
				
		// SET WHICH LOCAL ANALYSIS TO COMPUTE ----------------------------------------------------------------------------------------------------------	
				multiSim.setWhichLocalAnalysis(

						);
				
				
		// RUN LOCAL ANALYSIS ---------------------------------------------------------------------------------------------------------------------------

				multiSim.computeGlobalMultiSim(3000, 5, folderMultiSim);
					
			}


	// PRIVATE METHODS ----------------------------------------------------------------------------------------------------------------------------------
			

	// GET METHODS --------------------------------------------------------------------------------------------------------------------------------------
		public static String getIndicator ( nodeIndicators ind ) {
			return ind.toString();
		}

}
