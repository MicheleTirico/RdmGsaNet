package RdmGsaNetExport;

import java.io.IOException;

import org.graphstream.graph.Graph;
import org.graphstream.stream.file.FileSinkDGS;
import org.graphstream.stream.file.FileSourceDGS;

public class expGraph {
	
	static String nameFile ;
	static String dossier ;
	static String typeFileExp = ".dgs";
	
	static FileSinkDGS fsd = new FileSinkDGS(); 
	
	public static void writeGraphEachStepDgs (Graph graph, String dossierExp , String nameFileExp, int step ) throws IOException {
		
		String filePathStep = dossierExp+ nameFileExp +"_step_" + step  + typeFileExp ;
		graph.write(fsd, filePathStep);
	}
	
	public static void readGraphEachStepDgs ( Graph graph , String dossierExp , String nameFileExp ) throws IOException {
		
		String filePath = dossierExp + nameFileExp + typeFileExp;
		FileSourceDGS source = new FileSourceDGS();
		
		source.addSink( graph );
		source.begin(filePath);
		
		while( source.nextEvents() ){	}
		source.end();

	}
	
	

}

