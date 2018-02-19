package RdmGsaNet_Analysis_pr02;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import RdmGsaNetExport.handleNameFile;
import RdmGsaNet_Analysis_pr02.analysisDGSmultiSim.layerToAnalyze;
import RdmGsaNet_Analysis_pr02.analysisDGSmultiSim.typeIndicator;
import RdmGsaNet_Analysis_pr02.analysisDGSmultiSim.typeMultiSim;
import RdmGsaNet_Analysis_pr02.analysisLocal.nodeIndicators;

public class analysisMultiSim extends analysisMain {

	
	// Costants 
	protected static String nameFolderAnalysis = "multiSimAnalysis" ,
							nameFolderMap = "mapToAnalyze" 
							;  
			
		// HANDLE FILE OBJECT
		protected static handleNameFile handle ;

		private static analysisDGSmultiSim multiSim = new analysisDGSmultiSim( 
				/* id analysis		*/ "id" , 
				/* run analysis ? 	*/ true
				) ;
		
		
		
		// ----------------------------------------------------------------------------------------------------------------------------------------------
		public static void main(String[] args) throws IOException, InterruptedException {
		
				// setup handle name file 
				handle = new handleNameFile( 
						/* handle file 						*/ true , 
						/* set folder 						*/ folderMultiSim ,
						/* create new folder ? 				*/ false , 
						/*  manual name file (no in main )	*/ nameFolderAnalysis
					);		

		// SET PARAMETERS ANALYSIS ----------------------------------------------------------------------------------------------------------------------
				multiSim.setParamAnalysisLocal(
				
						);
				
		// SET WHICH LOCAL ANALYSIS TO COMPUTE ----------------------------------------------------------------------------------------------------------	
				multiSim.setWhichGlobalAnalysis(
						/* layerToAnalyze			*/ layerToAnalyze.net,		 
						/* typeMultiSim 			*/ typeMultiSim.probability,
						/* compute Clustering 		*/ true , 
						/* compute Density 			*/ true ,
						/* compute Average Degree	*/ true , 
						/* compute NewNodeRel  		*/ true ,
						/* compute SeedCountRel		*/ true
						);
				
			 	
		// RUN LOCAL ANALYSIS ---------------------------------------------------------------------------------------------------------------------------

				multiSim.computeGlobalMultiSim(3000, 1, folderMultiSim );
					
			}


	// PRIVATE METHODS ----------------------------------------------------------------------------------------------------------------------------------
			

	// GET METHODS --------------------------------------------------------------------------------------------------------------------------------------
		public static String getIndicator ( nodeIndicators ind ) {
			return ind.toString();
		}

}
