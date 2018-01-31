package RdmGsaNet_pr08;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.stream.file.FileSinkDGS;
import org.graphstream.stream.file.FileSinkImages.OutputType;

import RdmGsaNetAlgo.graphAnalysis;
import RdmGsaNetAlgo.graphAnalysis.spatialAutoCor;
import RdmGsaNetAlgo.morpSpatialAutoCor;
import RdmGsaNetExport.expGraph;
import RdmGsaNetExport.handleNameFile;
import RdmGsaNetViz.setupViz;
import RdmGsaNetViz.TESTViz;
import RdmGsaNet_pr08.generateNetNodeGradient.splitSeed;

public class TESTmain {
	
	private static String 	fileType = ".dgs" ;
	private static String folder = "D:\\ownCloud\\RdmGsaNet_exp\\test_handleNameFile\\" ;
	private static String pathStepNet ;
	private static String pathStepGs ;
	private static String pathStartNet ;	
	private static String pathStartGs ;
	
	static String folderNew = handleNameFile.getPath();
	
	private static String nameStartGs;
	private static String nameStartNet ;
	
	private static String nameStepGs ;
	private static String nameStepNet ;
	
	
	
	
	static generateNetEdge generateNetEdge = new generateNetEdge (new generateNetEdgeNear( 
			/* radius max ?	*/				0 
			/* which node link ? 	*/		, generateNetEdgeNear.whichNode.all )) ;
		
	public static void main(String[] args) throws IOException, InterruptedException {	
		
		
	
		handleNameFile handle = new handleNameFile(folder);
		
		gsAlgo values = new gsAlgo( gsAlgo.reactionType.ai2 , gsAlgo.diffusionType.weight , gsAlgo.extType.gsModel , 
				/* Da 	*/			0.2,			
				/* Di 	*/			0.1, 		
				/* feed */			0.03 , 	
				/* kill */			0.062 ,		
									true , 1E-5 ,
									true , 1E-5 , 1 ) ;
	  
		String pathStepNet = handle.getPathStepNet() ; 
		System.out.println("pathStepNet " + pathStepNet);
		
		String PathStepGs = handle.getPathStepGs();
		System.out.println("PathStepGs " + PathStepGs);
		
		String pathStartNet = handle.getPathStartNet();	
		System.out.println("pathStartNet " + pathStartNet);
		
		String pathStartGs = handle.getPathStartGs();
		System.out.println("pathStartGs " + pathStartGs);
		/*
		 folderNew = handleNameFile.getPath();
		
		 nameStartGs = handleNameFile.getNameGs(true);
		 nameStartNet = handleNameFile.getNameNet(true);
		
		 nameStepGs = handleNameFile.getNameGs(false);
		 nameStepNet = handleNameFile.getNameNet(false);
		
		System.out.println(folder);
		System.out.println(folderNew);
		System.out.println(nameStartGs);
		System.out.println(nameStartNet);
		System.out.println(nameStepGs);
		System.out.println(nameStepNet);
	
		
		pathStepNet = folderNew + nameStepNet + fileType;
		pathStepGs = folderNew + nameStepGs + fileType;
		pathStartNet = folderNew + nameStartNet + fileType;	
		pathStartGs = folderNew + nameStartGs + fileType;
		
		System.out.println(pathStepNet);
			*/
	
	}
		

// GET METHODS --------------------------------------------------------------------------------------------------------------------------------------
	public static  String getPathStepNet( ) { return pathStepNet;} 
	public static String getPathStepGs( )	{ return pathStepGs;} 
	public static String getPathStartNet( ) { return pathStartNet;} 
	public static String getPathStartGs( ) 	{ return pathStartGs;} 
}













