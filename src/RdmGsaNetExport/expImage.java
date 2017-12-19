package RdmGsaNetExport;

import java.io.IOException;

import org.graphstream.graph.Graph;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSinkImages.LayoutPolicy;
import org.graphstream.stream.file.FileSinkImages.OutputType;
import org.graphstream.stream.file.FileSinkImages.Resolutions;

import RdmGsaNetViz.setupViz;

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
			pic.writeAll(graph, folderIm +"/"+ nameIm + "_step_"+stepIm);
		}
	}
	public static void createImageAtStep ( Graph graph , String folderIm, String nameIm , double  step ) throws IOException  {
		
		try {
			
		
		String pathIm = folderIm + nameIm +  (int) step+ ".png" ;
		setupViz.Viz10ColorAct(graph);
		
		FileSinkImages pic = new FileSinkImages(OutputType.PNG, Resolutions.VGA);
		pic.setLayoutPolicy(LayoutPolicy.COMPUTED_IN_LAYOUT_RUNNER);
		pic.writeAll(graph, pathIm );
		
//		System.out.println(pathIm);
		}
		catch (java.lang.NullPointerException e) {
			// TODO: handle exception
		}
		catch (java.util.ConcurrentModificationException e) {
			// TODO: handle exception
		}
		
	}

}
