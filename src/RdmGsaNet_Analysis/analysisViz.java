 package RdmGsaNet_Analysis;


import java.io.IOException;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import RdmGsaNetExport.expGraph;
import RdmGsaNetViz.graphViz;
import RdmGsaNetViz.handleVizStype;
import RdmGsaNetViz.multiViz;
import RdmGsaNetViz.handleVizStype.palette;
import RdmGsaNetViz.handleVizStype.stylesheet;

public class analysisViz extends analysisMain {
	
	protected static Graph gsGraph = new SingleGraph("gsGraph") ,
							netGraph = new SingleGraph("netGraph");

	
	
	
	static multiViz multiViz = new multiViz(
			/* gsViz	*/ 	true ,
			/* netViz	*/	true 
			);
			
	public static void main ( String[ ] args ) throws IOException, InterruptedException {
			
		multiViz.setPath("dgsNet" , pathStartNet, pathStepNet);
		multiViz.setPath("dgsGs" , pathStartGs, pathStepGs);
		
		
		multiViz.runMultiLayerViz(5000, 5 );
	
	}
}

