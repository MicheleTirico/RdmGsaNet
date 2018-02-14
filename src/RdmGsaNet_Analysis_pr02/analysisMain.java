package RdmGsaNet_Analysis_pr02;

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


public  class  analysisMain   {
	
	// HANDLE FILE OBJECT
	protected static handleNameFile handle ;

	protected static String fileType = ".dgs" ;
	
	static String folder = "D:\\ownCloud\\RdmGsaNet_exp\\completeTest_01\\rd_solitions\\prob\\random\\alive\\maxStep_3000_generateNetNodeGradientProb_generateNetEdgeNear_prob_0.2_00\\" ;
									
// START FILES
	// GS graph
	 static String nameStartGs = "layerGs_start_setupGsGrid_grid8_size_50_Da_0.2_Di_0.1_f_0.03_k_0.062_diff_weight"  ,
								folderStartGs = folder ,
									pathStartGs = folderStartGs + nameStartGs + fileType ;
		
	// NET graph
	 static String nameStartNet = "layerNet_start_setupNetSmallGraph"  ,
							folderStartNet = folder,
								pathStartNet = folderStartNet + nameStartNet + fileType ;
	
// STEP FILES	
	// GS graph
	 static String nameStepGs = "layerGs_step_setupGsGrid_grid8_size_50_Da_0.2_Di_0.1_f_0.03_k_0.062_diff_weight" ,
								folderStepGs = folder ,
									pathStepGs = folderStepGs + nameStepGs + fileType ;
		
	// NET graph
	 static String nameStepNet = "layerNet_step_setupNetSmallGraph",
								folderStepNet = folder ,
									pathStepNet = folderStepNet + nameStepNet + fileType ;
	
	static String[] pathStart = { pathStartGs , pathStartNet } ;
	static String[] pathStep =  { pathStepGs  , pathStepNet  } ;
// GRAPHS 
	 static Graph gsGraph = analysisDGSgs.graph ;

	 static Graph netGraph = analysisDGSnet.graph;
	

		
	
}
