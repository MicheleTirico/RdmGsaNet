package RdmGsaNetViz;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import RdmGsaNet_pr08.*;

public class setupViz {


	
	static Graph gsGraph = layerGs.getGraph();
	static Graph netGraph = layerNet.getGraph();
		
	private static Map<Double , Graph > mapStepNetGraph = simulation.getMapStepNetGraph() ;

	public static void Viz4Color( Graph graph ) {
		
		graph.addAttribute("ui.stylesheet", setViz4Color ( ) );
//		graph.addAttribute("ui.quality");
//	    graph.addAttribute("ui.antialias");
		
		for ( Node n : graph.getEachNode()) {
			double act = n.getAttribute("gsAct");
			double inh = n.getAttribute("gsInh");
			
			double color = 0  ; //grey
			
			if ( act >= 0.9 && inh >= 0.9) 	{	 color =  	.25		;	} 		// red
			if ( act >= 0.5 && inh <= 0.5) 	{	 color =  	.5		;	}		// yellow
			if ( act <= 0.5 && inh >= 0.5) 	{	 color =  	.75		;	}		// green
			if ( act <= 0.1 && inh <= 0.1) 	{	 color =  	1		;	}		// blue
			
			n.addAttribute("ui.color", color );
		}
	}

private static String setViz4Color () {
	return  "node {"+
			"	size: 4px;"+
			"	fill-color: grey, red, yellow, green, blue; "+
			"	fill-mode: dyn-plain;"+
			"}"+
			"edge {"+
			"	size: 0.1px;"+
			"	fill-color: white;"+
			"}" ;
}

	public static void Vizmorp( Graph graph , String morp ) {
	
	graph.addAttribute("ui.stylesheet", setVizMorp ( morp ) );
	
	for ( Node n : graph.getEachNode()) {
		
		double color = n.getAttribute(morp);	
		
	
		
		n.addAttribute("ui.color", color );
		}
	}
	
	private static String setVizMorp ( String morp ) {
		String colorRange ;
		if ( morp == "gsAct" ) 	{ colorRange = "gray , red" ; 				}
		else { if ( morp == "gsInh" ) 	{ colorRange = " gray, green" ;				} 
		else 					{ colorRange = "0" ;
								  System.out.println("color not defined");	}
		}
		
		
		return  "node {"+
				"	size: 4px;"+
				"	fill-color: "+ colorRange	+ "  	;"+
				"	fill-mode: dyn-plain;"+
				"}"+
				"edge {"+
				"	size: 0.1px;"+
				"	fill-color: white;"+
				"}" ;
	}

		
// PRIVATE METHODS -----------------------------------------------------------------------------------------------
		

// set viz 
	
	
	
	/*
	 * 	private static String setNodeStyle ( int size, String color) {

		return "node {"+
						"	size: "		 + size		+ "px	;"+
						"	fill-color: "+ color	+ "  	;"+
						"	fill-mode: dyn-plain;"+
						"}";	
	}
// set node viz
	public static void setNodeViz (int size, String colors ) {
		styleSheetNode =  
				"node {"+
						"	size: "		 + size		+ "px	;"+
						"	fill-color: "+ colors	+ "  	;"+
						"	fill-mode: dyn-plain;"+
						"}";			
	}

// set edge viz
	public static void setEdgeViz (int size, String colors ) {
		styleSheetEdge = 
				"edge {"+
						"	size: "		 + size		+ "px	;"+
						"	fill-color: "+ colors	+ "  	;"+
						"}" ;
	}
	
	
	 */
	}
