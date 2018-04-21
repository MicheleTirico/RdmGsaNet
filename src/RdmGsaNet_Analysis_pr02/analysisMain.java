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
	protected static FileSource fs , gsFs , netFs , vecFs , seedFs;
			
	private static ViewPanel view , gsView , netView ;
	
	protected static String fileType = ".dgs" ;
	
	protected static String folder  = "D:\\ownCloud\\RdmGsaNet_exp\\vf_seedBirth_02\\sumVectors\\DynamicRadius\\circle_10\\solitions\\maxStep_1500_generateNetNodeVectorFieldSplitSeedProb_02_generateNetEdgeInDynamicRadius_01_val_45.0_00\\" ,
							
							folderMain =   handle.getParent(folder) ,			//"C:\\Users\\frenz\\ownCloud\\RdmGsaNet_exp\\vf_seedProb_multiRDmPoint_01\\holes\\" ,
							folderMultiSim = folderMain ,
							folderCommonFiles = folderMain + "commonFiles\\" ;								
	
	// START FILES
	// GS graph
	protected static String nameStartGs = handle.getCompleteNameInFolder(folder, "layerGs_start")   ,
								folderStartGs = folder ,
									pathStartGs = handle.getCompletePathInFolder(folderCommonFiles, "layerGs_start") ;
		
	// NET graph
	protected static String nameStartNet = "layerNet_start_setupNetSmallGraph"  ,
							folderStartNet = folder,
								pathStartNet = handle.getCompletePathInFolder(folderCommonFiles, "layerNet_start") ;
	
// STEP FILES	
	// GS graph
	protected static String nameStepGs = "layerGs_step_setupGsGrid_grid8_size_50_Da_0.2_Di_0.1_f_0.03_k_0.062_diff_weight" ,
								folderStepGs = folder ,
									pathStepGs = handle.getCompletePathInFolder(folderCommonFiles, "layerGs_step") ;
		
	// NET graph
	protected static String nameStepNet = "layerNet_step_setupNetSmallGraph",
								folderStepNet = folder ,
									pathStepNet = handle.getCompletePathInFolder(folder, "layerNet_step") ;
	
	protected static String[] pathStart = { pathStartGs , pathStartNet  };
	protected static String[] pathStep =  { pathStepGs  , pathStepNet  } ;

	// GRAPHS 
	protected static Graph 	gsGraph = analysisDGSgs.graph ,
							netGraph = analysisDGSnet.graph ,
							vecGraph = new SingleGraph("vecGraph") 
						//	seedGraph = new SingleGraph("seedGraph")
							;
	
}
