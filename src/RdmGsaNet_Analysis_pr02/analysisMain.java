package RdmGsaNet_Analysis_pr02;

import java.io.File;
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
	
	protected static String folder  = "D:\\ownCloud\\RdmGsaNet_exp\\completeTest_01\\rd_mazes\\prob\\random\\alive\\maxStep_3000_generateNetNodeGradientProb_generateNetEdgeNear_prob_0.4_00\\" ;
	protected static String folderMultiSim = "D:\\ownCloud\\RdmGsaNet_exp\\completeTest_01\\rd_solitions\\prob\\random\\alive\\" ;								

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
	
	protected static String[] pathStart = { pathStartGs , pathStartNet } ;
	protected static String[] pathStep =  { pathStepGs  , pathStepNet  } ;

	// GRAPHS 
	protected static Graph gsGraph = analysisDGSgs.graph ;
	protected static Graph netGraph = analysisDGSnet.graph;
	

		
	// private met
	public static String getFirstletterString ( String string , int startCharPos ,int finalCharPos ) {
		return string.substring(startCharPos, finalCharPos); 		
	}
	
	public static String getlayerGsStartInFolder2 ( String folder ) {
				
		String nameFileComplete = null ;
		String testName = "layerGs_star" ;
		File path = new File( folder );
		
		File [] files = path.listFiles();
		int lengthPath = folder.length();
	
		 for (int i = 0; i < files.length; i++){					//	System.out.println(files[i]);
			 String name = files[i].toString();
			// System.out.println(name);
			 String firstChar = null ;
			 try {
			  firstChar = getFirstletterString(name, lengthPath , lengthPath+1);
		
			 }
			 catch (java.lang.StringIndexOutOfBoundsException e) {	continue ;}
			if ( firstChar == "l")
				System.out.println("peppe");
		 
		 }
		return nameFileComplete;		
	}
	
	public static String getlayerGsStartInFolder ( String folder ) {
		
		String nameFileComplete = null ;
		File path = new File( folder );
		String test = "analysis";
		File [] files = path.listFiles();
		for (int i = 0; i < files.length; i++){
			String nameFile = files[i].getName().toString();
			String  firstChar = getFirstletterString(nameFile, 0 , 8);
			System.out.println(firstChar);
			System.out.println(test);
			if ( firstChar == test ) {
				System.out.println("peppe");
		
				}
			}
			return nameFileComplete;		
	}
	
	
}
