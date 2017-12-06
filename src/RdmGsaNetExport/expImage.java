package RdmGsaNetExport;

import java.io.IOException;

import org.graphstream.graph.Graph;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSinkImages.LayoutPolicy;
import org.graphstream.stream.file.FileSinkImages.OutputType;
import org.graphstream.stream.file.FileSinkImages.Resolutions;

public class expImage {
	
	public static void  getImage (Graph graph ,String folderIm, String nameIm ) throws IOException {
		
		FileSinkImages pic = new FileSinkImages(OutputType.PNG, Resolutions.VGA);	
		pic.setLayoutPolicy(LayoutPolicy.NO_LAYOUT ); 
		pic.writeAll(graph, folderIm +"/"+ nameIm);
	}
	
	public static void getImageStep (Graph graph ,String folderIm, String nameIm , int stepIm ) throws IOException {
		
		int step = 0;
		if (   step == stepIm ) {
			FileSinkImages pic = new FileSinkImages(OutputType.PNG, Resolutions.VGA);	
			pic.setLayoutPolicy(LayoutPolicy.NO_LAYOUT ); 
			pic.writeAll(graph, folderIm +"/"+ nameIm);
		}
	}

}
