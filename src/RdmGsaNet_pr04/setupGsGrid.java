package RdmGsaNet_pr04;

import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.GridGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

public class setupGsGrid extends viz {
	 
	// return size and type of Grid, declared in setupGs
	private static int size = setupGs.getGsGridSize();
	private static String type = setupGs.getGsGridType();		
	
	// method to initialize the Gs graph 
	public static Graph setGrid() {
		
		Graph graph = GsGraph;
		System.out.println(type);
		
		switch (type) {
		case "grid4" :
			Generator GsGen4 = new GridGenerator(false, false);
			GsGen4.addSink(graph);
			GsGen4.begin();
			for(int i = 0 ; i < size ; i++) { 	GsGen4.nextEvents(); 	}
			GsGen4.end();
			break;
		 
		case "grid8" :
			Generator GsGen8 = new GridGenerator(true, false);
			GsGen8.addSink(graph);
			GsGen8.begin();
			for(int i = 0; i < size ; i++) { 	GsGen8.nextEvents();	}
			GsGen8.end();
			break;
			}	
		
		return graph;
	
//----------------------------------------------------------------------------------------------------------------

	}
}
