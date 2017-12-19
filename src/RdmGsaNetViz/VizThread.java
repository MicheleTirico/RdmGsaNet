package RdmGsaNetViz;

import org.graphstream.graph.Graph;

public class VizThread {

	static String nodeVizMorp = setupViz.setViz10Color();
	
	public static void vizThreadMorp (Graph graph) {
		
		
		graph.addAttribute("ui.stylesheet",nodeViz);

		
		}
	
	static String nodeViz =  "node.highlight {"+
			"	size: 5px;"+
			"	fill-color: rgb(128,128,128), "
			+ "				rgb(255,128,0),rgb(255,255,0),rgb(128,255,0),"
			+ "				rgb(0,128,255),rgb(0,0,255),rgb(127,0,255),"
			+ "				rgb(255,0,255),rgb(255,0,128),rgb(255,0,0) ; "+
			"	fill-mode: dyn-plain;"+
			"}"+
			"edge {"+
			"	size: 0.1px;"+
			"	fill-color: white;"+
			"}" ;
		
	
}
