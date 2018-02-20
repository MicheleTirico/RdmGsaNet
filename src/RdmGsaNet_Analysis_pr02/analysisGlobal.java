package RdmGsaNet_Analysis_pr02;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import RdmGsaNetExport.expChart;
import RdmGsaNetExport.handleNameFile;
import RdmGsaNetExport.expChart.typeChart;
import RdmGsaNetViz.handleVizStype;
import RdmGsaNetViz.handleVizStype.stylesheet;

public class analysisGlobal extends analysisMain {
	
	// MAP FOR CHARTS
	// MAP NET
	protected static Map	mapNetFreqDegree = new HashMap () , 
							mapNetFreqDegreeRel = new HashMap () , 
							mapNetAverageDegree = new HashMap () ,
							mapNetStepNewNode = new HashMap () ,
							mapNetStepNormalDistributionDegree = new HashMap (),
							mapNetStepNewNodeRel = new HashMap(),												
							mapNetStepNewSeed = new HashMap(),
							mapNetStepNewSeedRel = new HashMap() , 
							mapNetStepGlobalClustering  = new HashMap() ,																
							mapNetStepGlobalDensity = new HashMap() ,
	// MAP NET
							mapGsStepMaxMorp = new HashMap () , 
							mapGsStepMinMorp = new HashMap () , 
							mapGsStepAveMorp = new HashMap () ,
							mapGsActivedNodes = new HashMap () ;
 
	// CREATE CHARTS
	static expChart xyChart = null ;
		
	// Initialize net analysis
	private static analysisDGSnet analysisNet = new analysisDGSnet(
				/* id dgs 					*/		"dgsNet" , 
				/* run analysis	global		*/		true 
				);
		
	// Initialize net analysis
	private static analysisDGSgs analysisGs = new analysisDGSgs(
				/* id dgs 					*/		"dgsGs" , 
				/* run analysis				*/		false 
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

			
// SET WHICH STAT TO COMPUTE ------------------------------------------------------------------------------------------------------------------------
		
		// global analysis
		analysisNet.setWhichGlobalAnalysis(
					/* run Viz 							*/ false ,
					/* getImage							*/ true ,
					/* computeDegree					*/ true ,
					/* computeDegreeRel					*/ true ,	// return the same result of normal degree distribution
					/* computeAverageDegree				*/ true ,
					/* computeNewNode					*/ true ,
					/* computeStepNewNodeRel			*/ true , 
					/* computeNormalDegreeDistribution 	*/ true ,
					/* computeNewSeedCount				*/ true ,
					/* computeNewSeedCountRel			*/ true ,
					/* computeStepGlobalClustering		*/ true ,
					/* compute Global Density			*/ true
					);
			
		analysisGs.setWhichGlobalAnalysis(
					/* run Viz 					*/ false ,
					/* getImage					*/ true ,
					/* computeStepMaxMorp		*/ true , 
					/* computeStepMinMorp		*/ true ,
					/* computeStepAveMorp 		*/ true ,
					/* computeGsActivedNodes	*/ false 
					);
				
			
// SET PARAMETERS OF ANALYSIS ---------------------------------------------------------------------
		analysisNet.setParamAnalysis( 
					/* degree frequency 	*/ 9 ,
					/* step in im 			*/ 200 
					);
			
		analysisGs.setParamAnalysis(
					/* morp 				*/ "gsAct",	// not yet used
					/* step in im 			*/ 200);
			
		
// RUN GLOBAL ANALYSIS ------------------------------------------------------------------------------------------------------------------------------
		analysisNet.computeGlobalStat	(5000 , 5 , pathStart, pathStep , 10 );
		
		analysisGs.computeGlobalStat	(5000 , 5 , pathStart, pathStep , 1 ); 
			
			
				
//	 		System.out.println(mapNetStepNewSeed);
//			System.out.println(mapNetStepNormalDistributionDegree);
			
//	 CREATE CHARTS ---------------------------------------------------------------------------------
		createChartsNet(
					/* stored Charts 							*/ true ,
					/* create Chart Degree 						*/ true ,
					/* create Chart DegreeRel					*/ true , 
					/* create chart Average Degree 				*/ true ,
					/* create Chart NewNodes					*/ true ,
					/* create Chart NewNodes Rel				*/ true ,
					/* create chart normal degree distribution 	*/ true	,
					/* create chart NewSeedCount				*/ true , 
					/* create chart NewSeedCountRel				*/ true ,
					/* create chart clustering					*/ true ,
					/* create chart density 					*/ true
					);

		createChartsGs(
					/* storeCharts 			*/ true  ,
					/* createChartMax		*/ true , 
					/* createChartMin		*/ true ,
					/* createChartAve		*/ true
					);
		
			
			 
			
	// SET VIZ ----------------------------------------------------------------------------------------
			// net viz 
		
		
		}	
			
	// PRIVATE METHODS ----------------------------------------------------------------------------------------------------------------------------------
	private static void createChartsNet ( boolean storeCharts, 
				boolean createChartFreqDegree , 
				boolean createChartFreqDegreeRel , boolean createChartAverageDegree , 
				boolean createChartNewNode , boolean createChartNewNodeRel ,
				boolean createCharNormalDegreeDistribution ,
				boolean createChartNewSeedCount	, boolean createChartNewSeedCountRel ,
				boolean createChartGlobalClustering , boolean createChartDensity ) 
						throws IOException {
		
			// exit method 
			if ( !analysisDGSnet.run || storeCharts == false  ) return ;
			
			handle.createFolder(folder + "analysis\\", "chart", false ) ;
			String folderChartNet = folder + "analysis\\chart\\";
			
			//create chart of frequency degree
			if ( createChartFreqDegree ) {
				xyChart = new expChart(typeChart.XYchartMultipleLine , "frequency degree Net", "Step (t)" , " freq degree (n)" , 800, 600 ,	mapNetFreqDegree );
				xyChart.setVisible(true);
				xyChart.saveChart(storeCharts ,  folderChartNet, "Net Freq Degree" );	
			}
			
			if ( createChartFreqDegreeRel ) {
				xyChart = new expChart(typeChart.XYchartMultipleLine , "frequency degree Net rel", "Step (t)" , " freq degree (%)" , 800, 600 ,	mapNetFreqDegreeRel );
				xyChart.setVisible(true);
				xyChart.saveChart(storeCharts ,  folderChartNet, "Net Freq Degree rel" );	
			}
		
			// create chart average degree
			if ( createChartAverageDegree) {
				xyChart = new expChart(typeChart.XYchartSingleLine , "average Degree", "Step (t)" , " average degree (n)" , 800, 600 ,	mapNetAverageDegree );
				xyChart.setVisible(true);
				xyChart.saveChart(storeCharts ,  folderChartNet, "Net average degree" );	
				
			}
			//create chart of new node
			if ( createChartNewNode ) {
				xyChart = new expChart(typeChart.XYchartSingleLine , "new node count", "Step (t)" , " new node (n)" , 800, 600 ,	mapNetStepNewNode );
				xyChart.setVisible(true);
				xyChart.saveChart(storeCharts ,  folderChartNet, "Net new nodes" );	
			}

			//create chart of new node rel
			if ( createChartNewNodeRel ) {
				xyChart = new expChart(typeChart.XYchartSingleLine , "new node count Rel", "Step (t)" , " new node (%)" , 800, 600 ,	mapNetStepNewNodeRel );
				xyChart.setVisible(true);
				xyChart.saveChart(storeCharts ,  folderChartNet, "Net new nodes rel" );	
			}
			
			// create chart degree distribution
			if ( createCharNormalDegreeDistribution) {
				xyChart = new expChart(typeChart.XYchartMultipleLine , "normal degree distribution", "Step (t)" , " normal degree distribution (n)" , 800, 600 ,	mapNetStepNormalDistributionDegree );
				xyChart.setVisible(true);
				xyChart.saveChart(storeCharts ,  folderChartNet, "Net normal degree distribution" );		
			}
			
			// seed stat
			if (  createChartNewSeedCount	) {
				xyChart = new expChart(typeChart.XYchartSingleLine , "count seed ", "Step (t)" , " new node (n)" , 800, 600 ,	mapNetStepNewSeed );
				xyChart.setVisible(true);
				xyChart.saveChart(storeCharts ,  folderChartNet, "Net count seed" );	
			} 
			
			// seed stat
			if (  createChartNewSeedCountRel	) {
				xyChart = new expChart(typeChart.XYchartSingleLine , "count seed rel", "Step (t)" , " new node (n)" , 800, 600 ,	mapNetStepNewSeedRel );
				xyChart.setVisible(true);
				xyChart.saveChart(storeCharts ,  folderChartNet, "Net count seed rel" );	
			}
			
			// clustering 
			if (createChartGlobalClustering ) {
				xyChart = new expChart(typeChart.XYchartSingleLine , "average clustering", "Step (t)" , " new node (n)" , 800, 600 ,	mapNetStepGlobalClustering );
				xyChart.setVisible(true);
				xyChart.saveChart(storeCharts ,  folderChartNet, "Net average clustering" );	
			}
			
			// density
			if ( createChartDensity ) 
				xyChart = new expChart(typeChart.XYchartSingleLine , "density", "Step (t)" , " new node (n)" , 800, 600 ,	mapNetStepGlobalDensity );
				xyChart.setVisible(true);
				xyChart.saveChart(storeCharts ,  folderChartNet, "Net density" );	
		}
		
		private static void createChartsGs ( boolean storeCharts, boolean createChartMax , boolean createChartMin , boolean createChartAve ) throws IOException {
			
			// exit method 
			if ( !analysisDGSgs.run || storeCharts == false  ) return ;
		
			handle = new handleNameFile( 
					
					/* handle file 						*/ true , 
					/* set folder 						*/ folder + "analysis\\" ,
					/* create new folder ? 				*/ false , 
					/*  manual name file (no in main )	*/ "chart"
				);		

			String folderChartGs = folder + "analysis\\chart\\";
			
			//create chart of MAX values of morphogens
			if ( createChartMax ) {
				xyChart = new expChart(typeChart.XYchart2Morp , "max", "Step (t)" , "morp (%)" , 800, 600 ,	mapGsStepMaxMorp );
				xyChart.setVisible(true);
				xyChart.saveChart(storeCharts,  folderChartGs, "Gs max" );	
			}
			
			//create chart of MIN values of morphogens
			if ( createChartMin ) {
				xyChart = new expChart(typeChart.XYchart2Morp , "min", "Step (t)" , "morp (%)" , 800, 600 ,	mapGsStepMinMorp );
				xyChart.setVisible(true);
				xyChart.saveChart(storeCharts,  folderChartGs, "Gs min" );	
			}
			
			//create chart of AVERAGE values of morphogens
			if ( createChartAve ) {
				xyChart = new expChart(typeChart.XYchart2Morp , "ave", "Step (t)" , "morp (%)" , 800, 600 ,	mapGsStepAveMorp );
				xyChart.setVisible(true);
				xyChart.saveChart(storeCharts,  folderChartGs, "Gs ave" );	
			}
		}
	// GET METHODS --------------------------------------------------------------------------------------------------------------------------------------
		// not used	
		public static  Map getMapFreqDegree ( ) { return mapNetFreqDegree ; }
		
}
