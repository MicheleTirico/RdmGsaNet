package RdmGsaNetExport;

import java.io.File;

import RdmGsaNet_pr08.generateNetEdge;
import RdmGsaNet_pr08.generateNetNode;
import RdmGsaNet_pr08.main;
import RdmGsaNet_pr08.simulation;

public class handleNameFile {
	
	private static String folder;
	

	// CONSTANT
	private String 	nameNewFolder ,  
	 				genNode = generateNetNode.getGenerateType (), 
	 				genEdge = generateNetEdge.getGenerateType ()
	
	 				;


	int maxStep = main.getStopSim();
	// CONSTRUCTOR
	
	public handleNameFile (String folder ) {
		this.folder = folder ;
		
		String nameNewFolder = getNameNewFolder ( maxStep , genNode , genEdge );
		
		createFolder(folder , nameNewFolder) ;
	}
	
	
	
	

// PRIVATE METHODS ------------------------------------------------------------------------------------------------------------------------
	public void createFolder (String folder, String nameNewFolder ) {
		
		 
		
		
		for (int numberFolder = 0 ; numberFolder < 10 ; numberFolder++ ) {
			String path = folder + nameNewFolder ;
			File file = new File(path);
			
			
			if (!file.exists()) {
				if (file.mkdir()) 	{	System.out.println("Directory is created!");
	            } else 				{	System.out.println("Failed to create directory!");	}
			}
			
			if ( file.exists()) {
				path = path + "_" + numberFolder;
				if (!file.exists()) {
					if (file.mkdir()) 	{	System.out.println("Directory is created!");
		            } else 				{	System.out.println("Failed to create directory!");	}
				}
			}
		}
		
		
		
    }
	
	
	private String getNameNewFolder ( int maxStep , String genNode , String genEdge ) {
		return	"maxStep_" + maxStep +
				 "_" + genNode +
				 "_" + genEdge				;
		}
}
