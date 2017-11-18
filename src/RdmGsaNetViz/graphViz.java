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

	static Viewer viewer ;
	
	public static void main( Graph graph ) {
		
//		System.out.println(mapStepNetGraph);	
//		viewer.disableAutoLayout();

		// background style
//		graph.addAttribute("ui.stylesheet", "graph { fill-color : red ; }");
		
		// node style
		String styleNode = "	Node node#1_1  {		shape: box;	size: 15px, 20px;				fill-mode: plain;   /* Default.          */				fill-color: red;    /* Default is black. */stroke-mode: plain; /* Default is none.  */				stroke-color: blue; /* Default is black. */} ";
		
		graph.addAttribute("ui.stylesheet", styleNode);
		
		
		// remove style
//		graph.removeAttribute("ui.stylesheet");
		
		// viz
		viewer = graph.display(false) ;
	
	
	
	
	}
	
// Methods
	

}
