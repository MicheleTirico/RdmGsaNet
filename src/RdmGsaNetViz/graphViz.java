package RdmGsaNetViz;

import java.util.HashMap;

import java.util.Map;
import org.graphstream.ui.spriteManager.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.view.Viewer;

import RdmGsaNet_pr08.*;

public class graphViz {
	
	static Graph gsGraph = layerGs.getGraph();
	static Graph netGraph = layerNet.getGraph();
	
	private static Map<Double , Graph > mapStepNetGraph = simulation.getMapStepNetGraph() ;

	// set default parameters of gsGraph
	static String styleDefaultbackground = setStyleDefaulBackground ( "white" ) ;
	static String styleDefaultNode = setStyleDefaultNode ("circle" , 10 , "red" ) ;
	static String styleDefaultEdge = setStyleDefaultEdge (1 , "black") ;
	
	static Viewer viewer ;
	
	public static void main( Graph graph ) {
		
		// default style
		gsGraph.addAttribute("ui.stylesheet", styleDefaultbackground + styleDefaultNode + styleDefaultEdge );
		
		// set quality viz
		graph.addAttribute("ui.quality");
	    graph.addAttribute("ui.antialias");
		
		viewer = gsGraph.display(false);
	
	}
	
// PRIVATE METHODS ----------------------------------------------------------------------------------------------------------------------------------------------
	
// set default style of edge
	private static String setStyleDefaultEdge (int sizeXY , String color ) {
		String styleSheetEdge =
	    		"edge {"+
	    				" size: "		+ sizeXY +	"px;	"+
	    				" fill-color: "	+ color	 + " ;		"+
	    				" fill-mode: 	dyn-plain;	"+
	    		"}" ;
		return styleSheetEdge ;
	}
	
// set default style of node
	private static String setStyleDefaultNode (String shape , int sizeXY , String color) {
		String styleSheetNode =
				"node {	"+
						" shape: " 		+ shape 	+ "; "+
	    				" size: " 		+ sizeXY	+ "px; "+
	    				" fill-color: "	+ color		+ "; "+
	    		"}";
		return styleSheetNode;	
	}
	
// set default style of background
	private static String setStyleDefaulBackground (String color) {
		String backgroundStyle = 
				"graph {"+
						" fill-color:" + color + "; }";
		return backgroundStyle ;
	}
	
	
	
	
	
	

	

}
