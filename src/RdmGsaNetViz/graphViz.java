package RdmGsaNetViz;

import java.io.IOException;

import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.GraphParseException;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceFactory;

public class graphViz {
	
	static Graph graph = new SingleGraph("graph");
	
	static String fileType = ".dgs" ;
	static String nameFile = "testStored" ;
	static String nameFileStart = nameFile + "Start" ;
	static String nameFileStep = nameFile + "Step";
	
	static String folderStart = "D:\\Dropbox\\Dropbox\\JAVA\\test\\";
	static String folderStep = folderStart ;

//	static String pathStart = folderStart + nameFileStart + fileType;
//	static String pathStep = folderStart + nameFileStep + fileType;
	
	private static FileSource fs;
	
	public static void vizGraph ( Graph graph , String pathStart , String pathStep , int step ) throws IOException {
		
		// import start graph
		try {
			graph.read(pathStart);
		} 
		catch (ElementNotFoundException | GraphParseException e1) {
			e1.printStackTrace();
		}
		
		// set file Source for file step
		fs = FileSourceFactory.sourceFor(pathStep);
		fs.addSink(graph);
		
		// setup configuration viz grap 
		setupViz.Viz4Color(graph);
			
		// import file step
		try {
			fs.begin(pathStep);
			
			while ( fs.nextStep()) {
				
				// identify step visualization  
				if ( step == graph.getStep() ) {
					System.out.println(graph.getStep());
					graph.display(true);
				}
			}
		}
		catch (IOException e) {
			// TODO: handle exception
		}
		
		fs.end();
		
	}

	
	
}
		