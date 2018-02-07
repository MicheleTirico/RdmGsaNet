package RdmGsaNet_Analysis;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import RdmGsaNetExport.expChart;
import RdmGsaNetExport.handleNameFile;
import RdmGsaNetExport.expChart.typeChart;
import RdmGsaNetExport.handleNameFile.toHandleType;
import RdmGsaNetViz.handleVizStype;
import RdmGsaNetViz.setupViz;
import RdmGsaNetViz.handleVizStype.stylesheet;

public class analysisMain {

	protected static String fileType = ".dgs" ;
	
	protected static String folder = "D:\\ownCloud\\RdmGsaNet_exp\\completeTest_01\\rd_mazes\\prob\\maxValue\\die\\maxStep_3000_generateNetNodeGradientProb_generateNetEdgeNear_prob_0.6_00\\" ;
									
// START FILES
	// GS graph
	protected static String nameStartGs = "layerGs_start_setupGsGrid_grid8_size_50_Da_0.2_Di_0.1_f_0.029_k_0.057_diff_weight"  ,
								folderStartGs = folder ,
									pathStartGs = folderStartGs + nameStartGs + fileType ;
		
	// NET graph
	protected static String nameStartNet = "layerNet_start_setupNetSmallGraph"  ,
							folderStartNet = folder,
								pathStartNet = folderStartNet + nameStartNet + fileType ;
	
// STEP FILES	
	// GS graph
	protected static String nameStepGs = "layerGs_step_setupGsGrid_grid8_size_50_Da_0.2_Di_0.1_f_0.029_k_0.057_diff_weight" ,
								folderStepGs = folder ,
									pathStepGs = folderStepGs + nameStepGs + fileType ;
		
	// NET graph
	protected static String nameStepNet = "layerNet_step_setupNetSmallGraph",
								folderStepNet = folder ,
									pathStepNet = folderStepNet + nameStepNet + fileType ;
	
// GRAPHS 
	protected static Graph gsGraph = analysisDGSgs.graph ;

	protected static Graph netGraph = analysisDGSnet.graph;

// MAP FOR CHARTS
	// MAP NET
	protected static Map mapNetFreqDegree = new HashMap () , 
							mapNetFreqDegreeRel = new HashMap () , 
								mapNetAverageDegree = new HashMap () ,
									mapNetStepNewNode = new HashMap () ,
										mapNetStepNormalDistributionDegree = new HashMap (),
											mapNetStepNewNodeRel = new HashMap(),
												mapNetStepNewSeed = new HashMap(),
													mapNetStepNewSeedRel = new HashMap();
		
	// MAP GS
	protected static Map mapGsStepMaxMorp = new HashMap () , 
							mapGsStepMinMorp = new HashMap () , 
								mapGsStepAveMorp = new HashMap () ;
	
	// CREATE CHARTS
	static expChart xyChart = null ;
	
	// hnadle viz 
	protected static handleVizStype netViz  = new handleVizStype( netGraph ,stylesheet.manual, "seedGrad") ,
									gsViz 	= new handleVizStype( gsGraph ,stylesheet.viz10Color, "gsInh") ;
	
// INITIALIZE ANALYSIS ----------------------------------------------------------------------------
	// Initialize net analysis
	private static analysisDGSnet analysisNet = new analysisDGSnet(
			/* id dgs 					*/		"dgsNet" , 
			/* run analysis				*/		true , 
			/* run all analysis			*/		true 
			);
	
	// Initialize net analysis
	private static analysisDGSgs analysisGs = new analysisDGSgs(
			/* id dgs 					*/		"dgsGs" , 
			/* run analysis				*/		false , 
			/* run all analysis			*/		true 
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

		
// SET WHICH CHARTS WE WANT TO HAVE ---------------------------------------------------------------
		analysisNet.setWhichAnalysis(
				/* run Viz 							*/ true ,
				/* getImage							*/ true ,
				/* computeDegree					*/ true ,
				/* computeDegreeRel					*/ true ,
				/* computeAverageDegree				*/ true ,
				/* computeNewNode					*/ true ,
				/* computeStepNewNodeRel			*/ true ,
				/* computeNormalDegreeDistribution 	*/ true ,
				/* computeNewSeedCount				*/ true ,
				/* computeNewSeedCountRel			*/ true
				);
		
		analysisGs.setWhichAnalysis(
				/* run Viz 				*/ false ,
				/* getImage				*/ true ,
				/* computeStepMaxMorp	*/ true , 
				/* computeStepMinMorp	*/ true ,
				/* computeStepAveMorp 	*/ true 
				);
		
// SET PARAMETERS OF ANALYSIS ---------------------------------------------------------------------
		analysisNet.setParamAnalysis( 
				/* degree frequency 	*/ 10 ,
				
				/* step in im 			*/ 200 
				);
		
		analysisGs.setParamAnalysis(
				/* morp 				*/ "gsAct",
				/* step in im 			*/ 100);
		
	
// RUN ANALYSIS -----------------------------------------------------------------------------------
		analysisNet.computeMultipleStat	(3000 , 5 , pathStartNet, pathStepNet);
	
		analysisGs.computeMultipleStat	(30, 5, pathStartGs, pathStepGs);
		

//
		
		// System.out.println(mapNetStepNewSeed);
//		System.out.println(mapNetStepNormalDistributionDegree);
		
// 	CREATE CHARTS ---------------------------------------------------------------------------------
		createChartsNet(
				/* storedCharts 							*/ true ,
				/* createChartDegree 						*/ true ,
				/* createChartDegreeRel						*/ true , 
				/*create chart Average Degree 				*/ true ,
				/* createChartNewNodes						*/ true ,
				/* createChartNewNodesRel					*/ true ,
				/* create chart normal degree distribution 	*/ true	,
				/* create chart NewSeedCount				*/ true , 
				/* create chart NewSeedCountRel				*/ true
				);

		createChartsGs(
				/* storeCharts 			*/ false  ,
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
			boolean createChartNewSeedCount	, boolean createChartNewSeedCountRel ) throws IOException {
		
	
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
			xyChart.saveChart(storeCharts ,  folderChartNet, "Net Freq Degree rel " );	
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
		
		if (  createChartNewSeedCount	) {
			xyChart = new expChart(typeChart.XYchartSingleLine , "count seed ", "Step (t)" , " new node (n)" , 800, 600 ,	mapNetStepNewSeed );
			xyChart.setVisible(true);
			xyChart.saveChart(storeCharts ,  folderChartNet, "Net count seed " );	
		}
		
		if (  createChartNewSeedCountRel	) {
			xyChart = new expChart(typeChart.XYchartSingleLine , "count seed rel", "Step (t)" , " new node (n)" , 800, 600 ,	mapNetStepNewSeedRel );
			xyChart.setVisible(true);
			xyChart.saveChart(storeCharts ,  folderChartNet, "Net count seed rel" );	
		}
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
