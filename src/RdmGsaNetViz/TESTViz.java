package RdmGsaNetViz;

import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import RdmGsaNet_pr08.*;

public class TESTViz {
	
	static Graph gsGraph = layerGs.getGraph();
	static Graph netGraph = layerNet.getGraph();
	
	private static Map<Double , Graph > mapStepNetGraph = simulation.getMapStepNetGraph() ;

	public static void main( Graph graph, String morp ) {
		displayColor1 (gsGraph, morp);
	}

// PRIVATE METHODS ----------------------------------------------------------------------------------------------------------------------------------------------
	public static void displayColor1 (Graph graph , String morp) {
		
		graph.addAttribute("ui.stylesheet", styleSheet3col);
//		graph.addAttribute("ui.quality");
//	    graph.addAttribute("ui.antialias");

		
		for ( Node n : graph.getEachNode()) {
			double val = n.getAttribute(morp);
			double color = val; 
			n.setAttribute("ui.color", color);
		}	
	}
	
public static void displayColor2 (Graph graph ) {
		
		graph.addAttribute("ui.stylesheet", styleSheet4col);
//		graph.addAttribute("ui.quality");
//	    graph.addAttribute("ui.antialias");

		
		for ( Node n : graph.getEachNode()) {
			double act = n.getAttribute("gsAct");
			double inh = n.getAttribute("gsInh");

			double val = 0; 
			if ( act <= 0.1 && inh <= 0.1) {	 val =  	.2		;	}
			if ( act >= 0.9 && inh <= 0.1) {	 val =  	.4		;	}
			if ( act <= 0.1 && inh >= 0.9) {	 val =  	.6		;	}
			if ( act >= 0.9 && inh >= 0.9) {	 val =  	.8		;	}
			
			double color = val; 
			n.setAttribute("ui.color", color);
		}	
	}

public static void displayColor3 (Graph graph , double th ) {
	
	graph.addAttribute("ui.stylesheet", styleSheet3col);
//	graph.addAttribute("ui.quality");
//    graph.addAttribute("ui.antialias");

	
	for ( Node n : graph.getEachNode()) {
		double act = n.getAttribute("gsAct");
		double inh = n.getAttribute("gsInh");

		double val = 0; 
		if ( act <= 0.1 && inh <= 0.1) {	 val =  	0.2		;	}
		if ( act >= 0.9 && inh >= 0.9) {	 val =  	0.6		;	}		
		else 							{	 val =  	0.4		;	}
		
		double color = val; 
		n.setAttribute("ui.color", color);
	}	
}
	
	protected static String styleSheet4col =
			"node {"+
					"	size: 3px;"+
					"	fill-color: red, yellow, green, blue; "+
					"	fill-mode: dyn-plain;"+
					"}"+
					"edge {"+
					"	size: 0.1px;"+
					"	fill-color: white;"+
					"}";
	
	protected static String styleSheet6col =
			"node {"+
					"	size: 3px;"+
					"	fill-color: #ff0000	, #ffff00, #bfff00, #00bfff; "+
					"	fill-mode: dyn-plain;"+
					"}"+
					"edge {"+
					"	size: 0.1px;"+
					"	fill-color: white;"+
					"}";
		
	protected static String styleSheet3col =
			"node {"+
					"	size: 3px;"+
					"	fill-color: red, yellow, green ; "+
					"	fill-mode: dyn-plain;"+
					"}"+
					"edge {"+
					"	size: 0.1px;"+
					"	fill-color: white;"+
					"}";
		

}	


