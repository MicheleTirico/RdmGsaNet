package RdmGsaNetExport;

import java.io.File;
import RdmGsaNet_pr09.*;


public class handleNameFile {
	
	// CONSTANT	
	private static String 	fileType = ".dgs" ,
							folder,
							pathStepNet,
							pathStepGs ,
							pathStartNet ,
							pathStartGs ,
							nameStepNet ,
							nameStepGs;
	
	// FOLDER PARAMETERS
	private static String 	nameNewFolder;
	private String   genNode = generateNetNode.getGenerateType (),
						genEdge = generateNetEdge.getGenerateType ();
	
	static String path = null ;
	private  int 	maxStep = main.getStopSim();
	
	// CONSTRUCTOR
	public handleNameFile ( boolean toHandle ,String folder ) {
		
		if ( toHandle = false )
			return ;
		
		this.folder = folder ;
		nameNewFolder = getNameNewFolder ( maxStep , genNode , genEdge );
		String path = createFolder(folder , nameNewFolder) ;	
	}
		
	public static String getPath () {return path; }
	
	// create new folder where stored all dgs files and return new path
	private String createFolder (String folder, String nameNewFolder ) {

		for ( int numFol = 0 ; numFol <= 50 ; numFol++ ) {
			if ( numFol < 10 ) 
				path = folder + nameNewFolder + "_0" + numFol;
			else
				path = folder + nameNewFolder + "_" + numFol;

			File file = new File(path);
			
			if (!file.exists()) {
				if (file.mkdir()) 	{	System.out.println("Directory is created!");
	            } else 				{	System.out.println("Failed to create directory!");	}
			break ;
			}
		}
		return path;
	}
	
	// get name DGS file of Net
	private static String getNameNet ( boolean isStart) {
		
		String time = null ;
		if ( isStart == true ) 		time = "start";
		else						time = "step" ;
		
		return 	"layerNet_" + time + 
				"_" + layerNet.getLayout() ;  
	}
	
	// get name DGS file of Gs
	private static String getNameGs ( boolean isStart) {
		String time = null ;
		if ( isStart == true ) 		time = "start";
		else						time = "step" ;
		
		return 	"layerGs_" + time + 
				"_" + layerGs.getLayout() +
				"_" + setupGsGrid.getGridType() + 
				"_size_" + setupGsGrid.getGsGridSize()  +
				"_Da_" + gsAlgo.getDa() +
				"_Di_" + gsAlgo.getDi()  +
				"_f_" + gsAlgo.getFeed()  +
				"_k_" + gsAlgo.getKill() +		
				"_diff_" + gsAlgo.getDiffusionType() ;
	}
	
	private  String getNameNewFolder ( int maxStep , String genNode , String genEdge ) {
		return	"maxStep_" + maxStep +
				 "_" + genNode +
				 "_" + genEdge +
				 "_prob_" + generateNetNodeGradient.getProb();
		}
	
	public static String getPathStepNet( ) 	{ 
		nameStepNet = getNameNet(false);
		String pathStepNet =   path + "\\" +  nameStepNet + fileType;
		return pathStepNet;
	} 
	
	public static String getPathStepGs( )	{
		nameStepGs = getNameGs(false);
		String pathStepGs =  path + "\\" + nameStepGs + fileType;
		return pathStepGs;
	} 
	
	public static String getPathStartNet( ) { 
		String nameStartNet = getNameNet(true);
		pathStartNet = path + "\\" + nameStartNet + fileType;
		return pathStartNet;
	}
	
	public static String getPathStartGs( ) 	{ 
		String nameStartGs = getNameGs(true); 
		pathStartGs = path + "\\" + nameStartGs + fileType ;
		return pathStartGs;
	} 
}
