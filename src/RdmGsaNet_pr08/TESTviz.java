package RdmGsaNet_pr08;

import java.util.ArrayList;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;



import RdmGsaNetViz.setupViz;
import graphstream_dev_toolkit.toolkit;

public class TESTviz  extends setupViz {
	
	private static ViewPanel view;

	static Graph  graph = new SingleGraph ("graph")  ;
				
	
	public static void main(String[] args) {
		

		// set property
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		     
		// create graph
//		toolkit.createGraphRandom(graph, 10);
		toolkit.createGraphGrid(graph, 10, true);

		graph.addAttribute("ui.quality");
	    graph.addAttribute("ui.antialias");
		
	    graph.addAttribute("ui.stylesheet", setVizNodeId () ) ;
	
	    view = graph.display(false).getDefaultView();
//	    view.resizeFrame(800, 600);
		view.getCamera().setViewCenter(5, 5, 0);
	}

}
