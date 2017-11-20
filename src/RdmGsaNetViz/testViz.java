package RdmGsaNetViz;

import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import RdmGsaNet_pr08.*;

public class testViz {
	
	static Graph gsGraph = layerGs.getGraph();
	static Graph netGraph = layerNet.getGraph();
	
	private static Map<Double , Graph > mapStepNetGraph = simulation.getMapStepNetGraph() ;

	public static void main( Graph graph ) {
		displayColor (gsGraph, "gsAct");
	}

// PRIVATE METHODS ----------------------------------------------------------------------------------------------------------------------------------------------
	public static void displayColor (Graph graph , String morp) {
		
		graph.addAttribute("ui.stylesheet", styleSheet);
		graph.addAttribute("ui.quality");
	    graph.addAttribute("ui.antialias");

		
		for ( Node n : graph.getEachNode()) {
			double val = n.getAttribute(morp);
			double color = val; 
			n.setAttribute("ui.color", color);
		}	
	}
	
	
	
	protected static String styleSheet =
			"node {"+
					"	size: 3px;"+
					"	fill-color: yellow , green , red ; "+
					"	fill-mode: dyn-plain;"+
					"}"+
					"edge {"+
					"	size: 0.2px;"+
					"	fill-color: #444;"+
					"}";
		

}	


