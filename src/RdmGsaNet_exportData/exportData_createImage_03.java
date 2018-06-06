package RdmGsaNet_exportData;

import java.io.IOException;

import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.GridGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import RdmGsaNetExport.handleNameFile;
import RdmGsaNetViz.handleVizStype.palette;
import RdmGsaNetViz.handleVizStype.stylesheet;
import RdmGsaNet_exportData.exportData_image_01.layer;

public class exportData_createImage_03 extends exportData_main {
	
	private static String pathToStore = handle.getParent(folder) , 
			pathDataMain = folder ;


	static exportData_image_03 expNet = new exportData_image_03() ;
	static exportData_image_03 expGs = new exportData_image_03() ;
	
	public static void main(String[] args) throws IOException {
			
		expNet.setParamVizNet(
				/* setScale					*/ 50 ,
				/* sizeNode, 				*/ 0 ,
				/* sizeEdge, 				*/ .5 ,
				/* colorNode, 				*/ "black" ,
				/* colorEdge, 				*/ "black" ,
				/* palette					*/ palette.blue, 
				/* stylesheet				*/ stylesheet.manual
				);
		
		expGs.setParamVizGs(
				/* setScale					*/ 50 ,
				/* sizeNode, 				*/ 8 ,
				/* sizeEdge, 				*/ 0 ,
				/* colorNode, 				*/ "red" ,
				/* colorEdge, 				*/ "white" ,
				/* palette					*/ palette.blue ,
				/* stylesheet				*/ stylesheet.viz10Color
				);
		
		handleNameFile.createNewGenericFolder(pathDataMain , "image" );
		pathToStore = pathDataMain + "\\image\\"  ;
		
		exportData_image_03.setParamViz(
				/* setScale					*/ 50 ,
				/* sizeNode, 				*/ 1 ,
				/* sizeEdge, 				*/ 0 ,
				/* colorNode, 				*/ "red" ,
				/* colorEdge, 				*/ "white" ,
				/* palette					*/ palette.blue ,
				/* stylesheet				*/ stylesheet.manual
				);
		
		expNet.createImage(false, 20, "netImage" , pathToStore, pathStartNet, pathStepNet);
		
		exportData_image_03.setParamViz(
				/* setScale					*/ 50 ,
				/* sizeNode, 				*/ 10 ,
				/* sizeEdge, 				*/ 0 ,
				/* colorNode, 				*/ "red" ,
				/* colorEdge, 				*/ "white" ,
				/* palette					*/ palette.red ,
				/* stylesheet				*/ stylesheet.viz10Color
				);
		
		expGs.createImage(true, 2500, "gsImage" , pathToStore, pathStartGs, pathStepGs);
		
	//	expGs.createImage(true, 25, pathToStore, pathStartGs, pathStepGs);
		
			
	//	graph = exportData_image_03.getGraphStep(200, pathToStore, pathStartNet, pathStepNet);
	
	//	graph.write("D:\\ownCloud\\RdmGsaNet_exp\\test\\test2.png");	

	}
}