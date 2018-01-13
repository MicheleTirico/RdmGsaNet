 package RdmGsaNet_Analysis;

import java.io.IOException;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import RdmGsaNetExport.expGraph;
import RdmGsaNetViz.graphViz;
import RdmGsaNetViz.setupViz;

public class analysisViz {

	// start storing
	private static String fileType = ".dgs" ;
	private static String nameStartGs = "layerGsStart_Size_50_Da_0.2_Di_0.1_F_0.062_K_0.061"  ;

	private static String folderStartGs = "C:\\Users\\frenz\\ownCloud\\RdmGsaNet_exp\\dgs\\Da_0.2_Di_0.1_02\\";
	private static String pathStartGs = folderStartGs + nameStartGs + fileType ;
			
	// step storing		
	private static String nameStepGs = "layerGsStep_Size_50_Da_0.2_Di_0.1_F_0.062_K_0.061"; 
	private static String folderStepGs = folderStartGs;
	private static String pathStepGs = folderStepGs + nameStepGs + fileType ;
		
	private static Graph graph = new SingleGraph( "graph"); 
	private static Graph netGraph = new SingleGraph( "netGraph"); 
		
	private static String nameStartNet = "layerNetStart_meanPoint_center_seedAct_1.0_seedInh_1.0";
		
	// export images
	private static String folderIm =  "C:\\Users\\frenz\\ownCloud\\RdmGsaNet_exp\\dgs\\Da_0.2_Di_0.1_02\\image\\";
	private static String nameIm = nameStepGs + "_step_";
			
	public static void main ( String[ ] args ) throws IOException {
			
		expGraph.readGraphDgs(netGraph, folderStartGs, nameStartNet);
			
//			setupViz.Vizmorp(graph, "gsInh");			
//			graphViz.vizGraph(graph, pathStartGs, pathStepGs, 3000 );
			
		graphViz.getImageStep(graph, pathStartGs, pathStepGs, 10000 , 100, folderIm, nameIm);
			
//			for ( Node n : graph.getEachNode()) { 			System.out.println(n.getId() + " " +"gsAct " + n.getAttribute("gsAct") +  " gsInh " + n.getAttribute("gsInh"));}
			

//			setupViz.Viz5Color( graph , "gsInh");
//			setupViz.Viz4Color(graph);
			
		setupViz.Viz10ColorAct( graph );
//			setupViz.Viz10ColorInh( graph );

//			setupViz.Vizmorp(graph, "gsAct");
//			setupViz.Vizmorp(graph, "gsInh");
			
		graph.display(false);
			
		
//			ArrayList<String> arrNetId = new ArrayList<String>();
//			for ( Node net : netGraph.getEachNode()) {	System.out.print(net.getId());			arrNetId.add(net.getId());			}
		
//			for ( String idNet : arrNetId) {				Node n = graph.getNode(idNet);	System.out.println(n.getId() + " " +"gsAct " + n.getAttribute("gsAct") +  " gsInh " + n.getAttribute("gsInh"));}			
		}
}
