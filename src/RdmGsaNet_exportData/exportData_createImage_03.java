package RdmGsaNet_exportData;



import java.io.IOException;
import java.util.ArrayList;

import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.GraphParseException;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceFactory;
import org.graphstream.stream.file.FileSinkImages.LayoutPolicy;
import org.graphstream.stream.file.FileSinkImages.OutputType;
import org.graphstream.stream.file.FileSinkImages.Resolutions;

import RdmGsaNetViz.handleVizStype.palette;

public class exportData_createImage_03 extends exportData_main {
	
	private static String pathToStore = handle.getParent(folder) , 
			pathDataMain = folder ;

	
	static exportData_image exp = new exportData_image() ;
	
	
	

	
	
	
	public static void main(String[] args) throws IOException {
	
	
		exp.setParamViz(
				/* setScale					*/ 50 ,
				/* sizeNode, 				*/ 0.01 ,
				/* sizeEdge, 				*/ 0.01 ,
				/* colorStaticNode, 		*/ "black" ,
				/* colorStaticEdge, 		*/ "black" 
				);
			
		exportData_image.createImage(true, 100, 5000, pathToStore, pathDataMain);

	}
		
		
		


	
}