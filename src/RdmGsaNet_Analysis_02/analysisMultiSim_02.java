package RdmGsaNet_Analysis_02;

import java.io.IOException;


import RdmGsaNetExport.handleNameFile;
import RdmGsaNet_Analysis_02.analysisDGSmultiSim_02.layerToAnalyze;
import RdmGsaNet_Analysis_02.analysisLocal.nodeIndicators;

public class analysisMultiSim_02 extends analysisMain {

	
	// Costants 
	protected static String nameFolderAnalysis = "multiSimAnalysis" ,
							nameFolderMap = "mapToAnalyze" 
							;  
			
	// HANDLE FILE OBJECT
	protected static handleNameFile handle ;

	private static analysisDGSmultiSim_02 multiSim = new analysisDGSmultiSim_02 ( 
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
		multiSim.setParamAnalysisLocal( );
		
// SET WHICH LOCAL ANALYSIS TO COMPUTE ----------------------------------------------------------------------------------------------------------	
		multiSim.setWhichGlobalAnalysis(
				/* layerToAnalyze				*/ layerToAnalyze.net
				);
		
		multiSim.setWhichGlobalAnalysisNet(
				/* compute Clustering 			*/ true , 
				/* compute Density 				*/ true ,
				/* compute Average Degree		*/ true , 
				/* compute NewNodeRel  			*/ true ,
				/* compute SeedCountRel			*/ true ,
				/* compute DensityRegularGraph 	*/ true 
				);
		
		multiSim.setWhichGlobalAnalysisMultiLayer(
				/* compute GlobalCorrelation Degree-gsInh	*/ false ,
				/* compute GlobalCorrelation Seed - gsInh	*/ false 				
				);
	
	 	
// RUN ANALYSIS ---------------------------------------------------------------------------------------------------------------------------

		multiSim.computeGlobalMultiSim( 5000 , 5 , folderMultiSim );
			
	}
			

// GET METHODS --------------------------------------------------------------------------------------------------------------------------------------
	public static String getIndicator ( nodeIndicators ind ) {
		return ind.toString();
	}

}
