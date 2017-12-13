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
		private static String nameStartGs = "layerGsStart_Size_50_Da_0.1_Di_0.05_F_0.014_K_0.054.dgs"  ;

		private static String folderStartGs = "C:\\Users\\Michele TIRICO\\ownCloud\\RdmGsaNet_exp\\dgs\\Da_0.2_Di_0.1\\dt_1.0\\danno\\";
		private static String pathStartGs = folderStartGs + nameStartGs + fileType ;
			
		// step storing		
		private static String nameStepGs = "layerGsStep_Size_50_Da_0.1_Di_0.05_F_0.014_K_0.054"; 
		private static String folderStepGs = folderStartGs;
		private static String pathStepGs = folderStepGs + nameStepGs + fileType ;
		
		private static Graph graph = new SingleGraph( "graph"); 
		private static Graph netGraph = new SingleGraph( "netGraph"); 
		
		private static String nameStartNet = "layerNetStart_meanPoint_center_seedAct_1.0_seedInh_0.0.dgs";
		
		
		public static void main ( String[ ] args ) throws IOException {
						
			expGraph.readGraphDgs(netGraph, folderStartGs, nameStartNet);
			
//			setupViz.Vizmorp(graph, "gsInh");			
			graphViz.vizGraph(graph, pathStartGs, pathStepGs, 3000);
			for ( Node n : graph.getEachNode()) { 
				System.out.println(n.getId() + " " +"gsAct " + n.getAttribute("gsAct") +  " gsInh " + n.getAttribute("gsInh"));
				}
			
//			System.out.println( graph.getNodeCount());
//			setupViz.Viz5Color( graph , "gsInh");
//			setupViz.Viz4Color(graph);
			
//			setupViz.Viz10Color( graph , "gsAct");
//			setupViz.Viz10Color( graph , "gsInh");
			setupViz.Viz10ColorAct( graph );
//			setupViz.Viz10ColorInh( graph );

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
