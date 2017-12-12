package RdmGsaNetExport;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSinkDGS;
import org.graphstream.stream.file.FileSourceDGS;

public class expGraph {
	
	static String nameFile ;
	static String folder ;
	static String typeFile = ".dgs";
	static String[] repositoryInfo ;
	
	static FileSinkDGS fsd = new FileSinkDGS(); 
	
	public static void writeGraphEachStepDgs (Graph graph, String folder , String nameFile, int step ) throws IOException {
		
		String filePathStep = folder + nameFile +"_step_" + step  + typeFile ;
		graph.write(fsd, filePathStep);
	}
	
	public static  void writeGraphDgs (Graph graph, String folderStart , String nameFileStart ) throws IOException {
		
		String filePathStep = folder + nameFile + typeFile ;
		graph.write(fsd, filePathStep);
	}

	public static void readGraphDgs ( Graph graph , String folder  , String nameFile ) throws IOException {
		
		String filePath = folder + nameFile + typeFile;
		FileSourceDGS source = new FileSourceDGS();
		
		source.addSink( graph );
		source.begin(filePath);
		
		while( source.nextEvents() ){	}
		source.end();
	}
	
	public static void readGraphEachStepDgs ( Graph graph , String folder  , String nameFile ) throws IOException {
		
		String filePath = folder + nameFile + typeFile;
		FileSourceDGS source = new FileSourceDGS();
		
		source.addSink( graph );
		source.begin(filePath);
		
		while( source.nextEvents() ){	}
		source.end();
	}
	
	public static Graph getGraphDgs (  String folder , String nameFile) throws IOException {
		
		Graph graph = new SingleGraph("graph") ;
	
		String filePath = folder + nameFile + typeFile;
		FileSourceDGS source = new FileSourceDGS();
		
		source.addSink( graph );
		source.begin(filePath);
		
		while( source.nextEvents() ){	}
		source.end();
		
		return graph;
	}
	
	public static String[] getRepositoryInfo (  ) {
		
		String[] repositoryInfo = new String[2] ;
		
		repositoryInfo[0] = folder ;		
		repositoryInfo[1] = nameFile ;
	
		return repositoryInfo ;
	}
	

}
