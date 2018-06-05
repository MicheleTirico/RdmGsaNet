package RdmGsaNet_exportData;

import java.io.IOException;

import RdmGsaNetViz.handleVizStype.palette;
import RdmGsaNet_exportData.exportData_image.layer;

public class exportData_createImage_03 extends exportData_main {
	
	private static String pathToStore = handle.getParent(folder) , 
			pathDataMain = folder ;

	
	static exportData_image expNet = new exportData_image() ;
	static exportData_image expGs = new exportData_image() ;
	
	public static void main(String[] args) throws IOException {
	
			
		expNet.setParamVizNet(
				/* setScale					*/ 50 ,
				/* sizeNode, 				*/ 0.01 ,
				/* sizeEdge, 				*/ 0.01 ,
				/* colorNode, 				*/ "black" ,
				/* colorEdge, 				*/ "black" 
				);
			
		expGs.setParamVizGs(
				/* setScale					*/ 50 ,
				/* sizeNode, 				*/ 5 ,
				/* sizeEdge, 				*/ 0 ,
				/* colorNode, 				*/ "black" ,
				/* colorEdge, 				*/ "white" ,
				/* palette color 			*/ palette.blue
				);
		
		exportData_image.createImage(layer.netGraph, true , 100, 2500, pathToStore, pathDataMain);
	//	exportData_image_02.createImage(true , 100, 2500, pathToStore, pathDataMain);
//		exportData_image.createImage(layer.gsGraph , false , 100, 500, pathToStore, pathDataMain);

	}
		
		
		


}