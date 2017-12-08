package RdmGsaNet_Analysis;

import java.io.IOException;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import RdmGsaNetViz.graphViz;
import graphstream_dev_io_test.graphstreamWriteGraph;

public class AnalysisViz {

	// start storing
		private static String fileType = ".dgs" ;
		private static String nameStartGs = "layerGsStart_Size_5_Da_0.15_Di_0.05_F_0.078_K_0.061.dgs"  ;

		private static String folderStartGs = "D:\\Dropbox\\Dropbox\\JAVA\\RdmGsaNet_Export2\\";
		private static String pathStartGs = folderStartGs + nameStartGs + fileType ;
			
		// step storing		
		private static String nameStepGs = "layerGsStep_Size_5_Da_0.15_Di_0.05_F_0.078_K_0.061"; 
		private static String folderStepGs = folderStartGs;
		private static String pathStepGs = folderStepGs + nameStepGs + fileType ;
		
		private static Graph graph = new SingleGraph( "graph"); 
		
		public static void main ( String[ ] args ) throws IOException {
			
			graphViz.vizGraph(graph, pathStartGs, pathStepGs, 12);
			
			
			
			
		}





}
