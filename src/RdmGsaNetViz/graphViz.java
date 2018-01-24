package RdmGsaNetViz;

import java.io.IOException;
import java.util.ArrayList;

import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.GraphParseException;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceFactory;
import org.graphstream.ui.view.Viewer;
import org.graphstream.stream.file.FileSinkImages.LayoutPolicy;
import org.graphstream.stream.file.FileSinkImages.OutputType;
import org.graphstream.stream.file.FileSinkImages.Resolutions;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import RdmGsaNetExport.expImage;

public class graphViz {
	
	static Graph graph = new SingleGraph("graph");
	
	static String fileType = ".dgs" ;
	static String nameFile = "testStored" ;
	static String nameFileStart = nameFile + "Start" ;
	static String nameFileStep = nameFile + "Step";
		
	private static FileSource fs;
	
	public static void vizGraph ( Graph graph , String pathStart , String pathStep , int step ) throws IOException {
		
		// import start graph
		try 														{	graph.read(pathStart);	} 
		catch (ElementNotFoundException | GraphParseException e1) 	{	e1.printStackTrace();	}
		
		// set file Source for file step
		fs = FileSourceFactory.sourceFor(pathStep);
		fs.addSink(graph);
		
		// import file step
		try {
			fs.begin(pathStep);
			
			while ( fs.nextStep()) {
				
				// identify step visualization  
				if ( step == graph.getStep() ) {
					System.out.println(graph.getStep());
					break;
				}
			}
		}
		catch (IOException e) {		}	
		fs.end();
	}
	
	public static void getImageStep ( Graph graph , String pathStart , String pathStep , int stepMax , int stepInc , String folderIm , String nameIm) throws IOException {

		// create list of step to create images
		ArrayList<Double> incList = new ArrayList<Double>();
		for ( double n = 1 ; n * stepInc <= stepMax ; n++ ) {	incList.add( n * stepInc );	}
			
		System.out.println(incList);
		
		// import start graph
		try 														{	graph.read(pathStart);	} 
		catch (ElementNotFoundException | GraphParseException e1) 	{	e1.printStackTrace();	}
		
		// set file Source for file step
		fs = FileSourceFactory.sourceFor(pathStep);
		fs.addSink(graph);
		
		// import file step
		try {
			fs.begin(pathStep);
			
			while ( fs.nextStep()) {

				double step = graph.getStep();
				System.out.println(step);
				
				if ( incList.contains(step)) {
					expImage.createImageAtStep(graph, folderIm, nameIm, step);		
				}
				
				// identify step visualization  
				if ( stepMax == step ) {
					System.out.println(graph.getStep());
					System.out.println();
					
					break;
				}
			}
		}
		catch (IOException e) {		}	
		fs.end();
	}

}
		