package RdmGsaNet_Analysis;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import RdmGsaNetExport.expChart;
import RdmGsaNetExport.expChart.typeChart;
import RdmGsaNetViz.handleVizStype;
import RdmGsaNetViz.setupViz;
import RdmGsaNetViz.handleVizStype.stylesheet;

public class analysisMain {

	protected static String fileType = ".dgs" ;
	
	protected static String folder = "D:\\ownCloud\\RdmGsaNet_exp\\test_pr9_gsInh\\rd_mazes\\maxValue\\maxStep_3000_generateNetNodeGradientProb_generateNetEdgeNear_prob_0.1_00\\" ,
								folderChart = folder +"chart\\" ;
	
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
	private static Graph gsGraph  = analysisDGSgs.gsGraph ;

	private static Graph netGraph = analysisDGSnet.netGraph;

// MAP FOR CHARTS
	// MAP NET
	protected static Map mapNetFreqDegree = new HashMap () , 
							mapNetAverageDegree = new HashMap () ,
								mapNetStepNewNode = new HashMap () ,
									mapNetStepNormalDistributionDegree = new HashMap ();
	
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
			/* run analysis				*/		true , 
			/* run all analysis			*/		true 
			);

// --------------------------------------------------------------------------------------------------------------------------------------------------
	public static void main(String[] args) throws IOException, InterruptedException {
		
		
		
// SET WHICH CHARTS WE WANT TO HAVE ---------------------------------------------------------------
		analysisNet.setWhichAnalysis(
				/* run Viz 							*/ true ,
				/* computeDegree					*/ false ,
				/* computeAverageDegree				*/ false ,
				/* computeNewNode					*/ false ,
				/* computeNormalDegreeDistribution 	*/ false
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
		analysisNet.computeMultipleStat	(300, 5 , pathStartNet, pathStepNet);
	
		analysisGs.computeMultipleStat	(300, 5, pathStartGs, pathStepGs);
		
	//	System.out.println(mapNetAverageDegree);
		System.out.println(mapNetStepNormalDistributionDegree);
		
// 	CREATE CHARTS ---------------------------------------------------------------------------------
		createChartsNet(
				/* storedCharts 							*/ false  ,
				/* createChartDegree 						*/ false , 
				/*create chart Average Degree 				*/ true ,
				/* createChartNewNodes						*/ false,
				/* create chart normal degree distribution 	*/ false
				
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
	private static void createChartsNet ( boolean storeCharts, boolean createChartDegree , boolean createChartAverageDegree , boolean createChartNewNode , boolean createCharNormalDegreedistribution ) throws IOException {
		
		// exit method 
		if ( !analysisDGSnet.run || storeCharts == false  ) return ;
		
		//create chart of frequency degree
		if ( createChartDegree ) {
			xyChart = new expChart(typeChart.XYchartMultipleLine , "frequency degree Net", "Step (t)" , " freq degree (n)" , 800, 600 ,	mapNetFreqDegree );
			xyChart.setVisible(true);
			xyChart.saveChart(storeCharts ,  folderChart, "Net Freq Degree" );	
		}
	
		// create chart average degree
		if ( createChartAverageDegree) {
			xyChart = new expChart(typeChart.XYchartSingleLine , "average Degree", "Step (t)" , " average degree (n)" , 800, 600 ,	mapNetAverageDegree );
			xyChart.setVisible(true);
			xyChart.saveChart(storeCharts ,  folderChart, "Net average degree" );	
			
		}
		//create chart of new node
		if ( createChartNewNode ) {
			xyChart = new expChart(typeChart.XYchartSingleLine , "new node count", "Step (t)" , " new node (n)" , 800, 600 ,	mapNetStepNewNode );
			xyChart.setVisible(true);
			xyChart.saveChart(storeCharts ,  folderChart, "Net new nodes" );	
		}
		
		// create chart degree distribution
		if ( createCharNormalDegreedistribution) {
			xyChart = new expChart(typeChart.XYchartMultipleLine , "normal degree distribution", "Step (t)" , " normal degree distribution (n)" , 800, 600 ,	mapNetStepNormalDistributionDegree );
			xyChart.setVisible(true);
			xyChart.saveChart(storeCharts ,  folderChart, "Net normal degree distribution" );		
		}
	}
	
	private static void createChartsGs ( boolean storeCharts, boolean createChartMax , boolean createChartMin , boolean createChartAve ) throws IOException {
		
		// exit method 
		if ( !analysisDGSgs.run || storeCharts == false  ) return ;
		
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
