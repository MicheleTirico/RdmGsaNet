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

public class exportData_createImage_04 extends exportData_main {
	
	private static String pathToStore = handle.getParent(folder) , 
			pathDataMain = folder ;


	static 	exportData_image_03 expNet = new exportData_image_03() ,
								expGs = new exportData_image_03() ,
								expSeed = new exportData_image_03(),
								expSingleIm = new exportData_image_03() ;
	
	public static void main(String[] args) throws IOException {
			
	
		handleNameFile.createNewGenericFolder(pathDataMain , "image" );
		pathToStore = "D:\\ownCloud\\TIRICO_publications\\CONF_2018_rdmToGraph\\figures\\matrixRdmStep\\matrixRdmStep_02\\images\\prove\\";
		
		exportData_image_03.setParamViz(
				/* setScale					*/ 50 ,
				/* sizeNode, 				*/ 10 ,
				/* sizeEdge, 				*/ 0 ,
				/* colorNode, 				*/ "red" ,
				/* colorEdge, 				*/ "white" ,
				/* palette					*/ palette.red ,
				/* stylesheet				*/ stylesheet.viz10Color
				);
		
		expGs.createImage(true, 1500, "gsImage" , pathToStore, pathStartGs, pathStepGs);
		
	}
}