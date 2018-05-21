package RdmGsaNet_exportData;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.ui.swingViewer.ViewPanel;

import RdmGsaNetExport.handleNameFile;
import RdmGsaNet_Analysis_02.analysisDGSgs;
import RdmGsaNet_Analysis_02.analysisDGSnet;

public class exportData_main {
	
	protected Graph graph = new SingleGraph ("graph") ,
					netGraph = new SingleGraph ("netGraph") ,
					gsGraph = new SingleGraph ("gsGraph") ;
	
	// HANDLE FILE OBJECT
	protected static handleNameFile handle ;

	// viz constants
	protected static FileSource fs , gsFs , netFs , vecFs , seedFs;
			
	private static ViewPanel view , gsView , netView ;
	
	protected static String fileType = ".dgs" ;
	
	protected static String folder  = "D:\\dataNoCloud\\vf_seedBirt_DynamicRadius_buckets_dieBord_stepCompute\\step_5_stepBird_5\\step_2500\\chaos\\maxStep_2500_generateNetNodeVectorFieldSplitSeedProbInBuckets_03_generateNetEdgeInDynamicRadiusInBuckets_03_val_0.1_00\\" ,
							
							folderMain =   handle.getParent(folder) ,
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
	
	protected static String[] 	pathStart = { pathStartGs , pathStartNet  } ,
								pathStep =  { pathStepGs  , pathStepNet  } ;

}
