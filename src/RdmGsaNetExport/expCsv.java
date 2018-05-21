package RdmGsaNetExport;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Graph;

public class expCsv {

	public Map mapExp = new HashMap() ;
	
	public static void createCsv (String path , String nameFile ) throws IOException {

		FileWriter sw = new FileWriter(  path + nameFile + ".csv");
	}
	
	
	
}
