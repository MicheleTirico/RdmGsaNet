package RdmGsaNet_Analysis;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import RdmGsaNetExport.expChart;
import RdmGsaNetExport.expChart.typeChart;
import RdmGsaNetViz.setupViz;

public class analysisMain {
	
	private static String fileType = ".dgs" ;
	
	private static String folder = "D:\\ownCloud\\RdmGsaNet_exp\\test_gradient_03\\RD_solitions\\maxStep_3000_generateNetNodeGradient_generateNetEdgeNear_prob_0.06_00\\" ,
							folderChart = folder +"chart\\" ;
	
// START FILES
	// GS graph
	private static String nameStartGs = "layerGs_start_setupGsGrid_grid8_size_50_Da_0.2_Di_0.1_f_0.03_k_0.062_diff_weight"  ,
							folderStartGs = folder ,
								pathStartGs = folderStartGs + nameStartGs + fileType ;
		
	// NET graph
	private static String nameStartNet = "layerNet_start_setupNetSeed"  ,
							folderStartNet = folder,
								pathStartNet = folderStartNet + nameStartNet + fileType ;
	
// STEP FILES	
	// GS graph
	private static String nameStepGs = "layerGs_step_setupGsGrid_grid8_size_50_Da_0.2_Di_0.1_f_0.03_k_0.062_diff_weight" ,
							folderStepGs = folder ,
								pathStepGs = folderStepGs + nameStepGs + fileType ;
		
	// NET graph
	private static String nameStepNet = "layerNet_step_setupNetSeed",
							folderStepNet = folder ,
								pathStepNet = folderStepNet + nameStepNet + fileType ;
	
// GRAPHS 
	private static Graph gsGraph  = analysisDGSgs.gsGraph,
							netGraph = analysisDGSnet.netGraph ;

// MAP FOR CHARTS
	// MAP NET
	protected static Map mapNetFreqDegree = new HashMap () , 
							mapNetStepNewNode = new HashMap () ;
	
	// MAP GS
	protected static Map mapGsStepMaxMorp = new HashMap () , 
							mapGsStepMinMorp = new HashMap () , 
								mapGsStepAveMorp = new HashMap () ;
	
	// CREATE CHARTS
	static expChart xyChart = null ;
	
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
			/* run analysis				*/		true , 
			/* run all analysis			*/		true 
			);

// --------------------------------------------------------------------------------------------------------------------------------------------------
	public static void main(String[] args) throws IOException, InterruptedException {
		
		
		
// SET WHICH CHARTS WE WANT TO HAVE ---------------------------------------------------------------
		analysisNet.setWhichAnalysis(
				/* run Viz 				*/ true ,
				/* computeDegree		*/ false ,
				/* computeNewNode		*/ false 
				);
		
		analysisGs.setWhichAnalysis(
				/* run Viz 				*/ true ,
				/* computeStepMaxMorp	*/ false , 
				/* computeStepMinMorp	*/ false ,
				/* computeStepAveMorp 	*/ false 
				);
		
// SET PARAMETERS OF ANALYSIS ---------------------------------------------------------------------
		analysisNet.setParamAnalysis( 
				/* degree frequency 	*/		5
				);
		
		analysisGs.setParamAnalysis("gsAct");
	
// RUN ANALYSIS -----------------------------------------------------------------------------------
		analysisNet.computeMultipleStat	(3000, 5 , pathStartNet, pathStepNet);
	
		analysisGs.computeMultipleStat	(3000, 5, pathStartGs, pathStepGs);
		
	
		
// 	CREATE CHARTS ---------------------------------------------------------------------------------
		createChartsNet(
				/* storedCharts 		*/ false  ,
				/* createChartDegree 	*/ true , 
				/* createChartNewNodes	*/ true
				);
	
		createChartsGs(
				/* storeCharts 			*/ false  ,
				/* createChartMax		*/ true , 
				/* createChartMin		*/ true ,
				/* createChartAve		*/ true
				);
	
		
		
		
		
// SET VIZ ----------------------------------------------------------------------------------------
		// net viz 
	//	netGraph.setAttribute("ui.stypesheet", setVizNodeId () );
		
	//	gsGraph.addAttribute("ui.stypesheet", setVizNodeId () );
	
	
	
	}	
		
// PRIVATE METHODS ----------------------------------------------------------------------------------------------------------------------------------
	private static void createChartsNet ( boolean storeCharts, boolean createChartDegree , boolean createChartNewNode ) throws IOException {
		
		// exit method 
		if ( !analysisDGSnet.run ) return ;
		
		//create chart of frequency degree
		if ( createChartDegree ) {
			xyChart = new expChart(typeChart.XYchartMultipleLine , "frequency degree Net", "Step (t)" , " freq degree (n)" , 800, 600 ,	mapNetFreqDegree );
			xyChart.setVisible(true);
			xyChart.saveChart(storeCharts ,  folderChart, "Net Freq Degree" );	
		}
		
		//create chart of new node
		if ( createChartNewNode ) {
			xyChart = new expChart(typeChart.XYchartSingleLine , "new node count", "Step (t)" , " new node (n)" , 800, 600 ,	mapNetStepNewNode );
			xyChart.setVisible(true);
			xyChart.saveChart(storeCharts ,  folderChart, "Net new nodes" );	
		}
	}
	
	private static void createChartsGs ( boolean storeCharts, boolean createChartMax , boolean createChartMin , boolean createChartAve ) throws IOException {
		
		// exit method 
		if ( !analysisDGSgs.run ) return ;
		
		//create chart of MAX values of morphogens
		if ( createChartMax ) {
			xyChart = new expChart(typeChart.XYchart2Morp , "max", "Step (t)" , "morp (%)" , 800, 600 ,	mapGsStepMaxMorp );
			xyChart.setVisible(true);
			xyChart.saveChart(storeCharts,  folderChart, "Gs max" );	
		}
		
		//create chart of MIN values of morphogens
		if ( createChartMin ) {
			xyChart = new expChart(typeChart.XYchart2Morp , "min", "Step (t)" , "morp (%)" , 800, 600 ,	mapGsStepMinMorp );
			xyChart.setVisible(true);
			xyChart.saveChart(storeCharts,  folderChart, "Gs min" );	
		}
		
		//create chart of AVERAGE values of morphogens
		if ( createChartAve ) {
			xyChart = new expChart(typeChart.XYchart2Morp , "ave", "Step (t)" , "morp (%)" , 800, 600 ,	mapGsStepAveMorp );
			xyChart.setVisible(true);
			xyChart.saveChart(storeCharts,  folderChart, "Gs ave" );	
		}
	}
// GET METHODS --------------------------------------------------------------------------------------------------------------------------------------
	// not used	
	public static  Map getMapFreqDegree ( ) { return mapNetFreqDegree ; }
	

}
