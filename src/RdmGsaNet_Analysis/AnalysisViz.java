package RdmGsaNet_Analysis;

import java.io.IOException;
import java.util.ArrayList;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import RdmGsaNetExport.expGraph;
import RdmGsaNetViz.graphViz;
import RdmGsaNetViz.setupViz;
import graphstream_dev_io_test.graphstreamWriteGraph;

public class AnalysisViz {

	// start storing
		private static String fileType = ".dgs" ;
		private static String nameStartGs = "layerGsStart_Size_50_Da_1.0_Di_0.5_F_0.03_K_0.062.dgs"  ;

		private static String folderStartGs = "D:\\Dropbox\\Dropbox\\JAVA\\RdmGsaNet_Export\\";
		private static String pathStartGs = folderStartGs + nameStartGs + fileType ;
			
		// step storing		
		private static String nameStepGs = ""; 
		private static String folderStepGs = folderStartGs;
		private static String pathStepGs = folderStepGs + nameStepGs + fileType ;
		
		private static Graph graph = new SingleGraph( "graph"); 
		private static Graph netGraph = new SingleGraph( "netGraph"); 
		
		private static String nameStartNet = "layerGsStep_Size_50_Da_1.0_Di_0.5_F_0.03_K_0.062";
		
		
		public static void main ( String[ ] args ) throws IOException {
						
			expGraph.readGraphDgs(netGraph, folderStartGs, nameStartNet);
			
//			setupViz.Vizmorp(graph, "gsAct");			
			graphViz.vizGraph(graph, pathStartGs, pathStepGs, 100);
			for ( Node n : graph.getEachNode()) { 
				System.out.println(n.getId() + " " +"gsAct " + n.getAttribute("gsAct") +  " gsInh " + n.getAttribute("gsInh"));
				}
			
//			System.out.println( graph.getNodeCount());
//			setupViz.Viz5Color( graph , "gsAct");
			
			
//			setupViz.Viz10Color( graph , "gsAct");
			setupViz.Viz10Color( graph , "gsInh");

//			setupViz.Vizmorp(graph, "gsAct");
//			setupViz.Vizmorp(graph, "gsInh");
			
			
		
			ArrayList<String> arrNetId = new ArrayList<String>();
			for ( Node net : netGraph.getEachNode()) {	
				System.out.print(net.getId());
				arrNetId.add(net.getId());
			}
			
			for ( String idNet : arrNetId) {
				Node n = graph.getNode(idNet);
				System.out.println(n.getId() + " " +"gsAct " + n.getAttribute("gsAct") +  " gsInh " + n.getAttribute("gsInh"));
			}
			

			graph.display(false);
			
				
			
		}
		




}
