package RdmGsaNet_Analysis_pr02;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSource;
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
	
	protected static Graph graph = new SingleGraph ("graph");
	
	// HANDLE FILE OBJECT
	protected static handleNameFile handle ;

	// viz constants
	protected static FileSource fs , gsFs ,netFs;
			
	private static ViewPanel view , gsView , netView ;
	
	
	protected static String fileType = ".dgs" ;
	
	protected static String folder  = "D:\\ownCloud\\RdmGsaNet_exp\\completeTest_01\\rd_solitions\\prob\\random\\alive\\maxStep_3000_generateNetNodeGradientProb_generateNetEdgeNear_prob_0.2_00\\" ;
	protected static String folderMultiSim = "D:\\ownCloud\\RdmGsaNet_exp\\completeTest_01\\rd_solitions\\prob\\random\\alive\\" ;								
	
	// START FILES
	// GS graph
	protected static String nameStartGs = handle.getCompleteNameInFolder(folder, "layerGs_start")   ,
								folderStartGs = folder ,
									pathStartGs = handle.getCompletePathInFolder(folder, "layerGs_start") ;
		
	// NET graph
	protected static String nameStartNet = "layerNet_start_setupNetSmallGraph"  ,
							folderStartNet = folder,
								pathStartNet = handle.getCompletePathInFolder(folder, "layerNet_start") ;
	
// STEP FILES	
	// GS graph
	protected static String nameStepGs = "layerGs_step_setupGsGrid_grid8_size_50_Da_0.2_Di_0.1_f_0.03_k_0.062_diff_weight" ,
								folderStepGs = folder ,
									pathStepGs = handle.getCompletePathInFolder(folder, "layerGs_step") ;
		
	// NET graph
	protected static String nameStepNet = "layerNet_step_setupNetSmallGraph",
								folderStepNet = folder ,
									pathStepNet = handle.getCompletePathInFolder(folder, "layerNet_step") ;
	
	protected static String[] pathStart = { pathStartGs , pathStartNet  };
	protected static String[] pathStep =  { pathStepGs  , pathStepNet  } ;

	// GRAPHS 
	protected static Graph gsGraph = analysisDGSgs.graph ;
	protected static Graph netGraph = analysisDGSnet.graph;
	
}
