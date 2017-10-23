package RdmGsaNet_pr04;

import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.GridGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

public class setupGsGrid {
	
	// DECLARE VARIABLES
	public enum typeGrid { grid4 , grid8 }			// type layout
	private typeGrid type ;
	
	// declare size of Grid, declared in setupGs
	static int size = setupGs.getGsGridSize();
	
	
			;
	
//	gino.getGsGridSize();
	
	
	
	// Gs Graph initialization
	public Graph GsGraph = new SingleGraph("GsGraph");
	
	
//----------------------------------------------------------------------------------------------------------------
	public setupGsGrid (typeGrid type, int size) {
		this.type = type;
		this.size = size;
		
		setGrid(GsGraph);								// costructor.method
	}
	
	// methods to setting graph and attributes
		private Graph setGrid(Graph graph) {
			switch (type) {
			case grid4: 
				System.out.println("layoutGs grid4");
				Generator GsGen4 = new GridGenerator(false, false);
				GsGen4.addSink(graph);
				GsGen4.begin();
				for(int i = 0 ; i < size; i++) { 	GsGen4.nextEvents(); 	}
				GsGen4.end();
				break;	
			case grid8:
				System.out.println("layoutGs grid8");
				Generator GsGen8 = new GridGenerator(true, false);
				GsGen8.addSink(graph);
				GsGen8.begin();
				for(int i = 0; i < size ; i++) { 	GsGen8.nextEvents();	}
				GsGen8.end();
				break;
				}
			return graph;
			}

	

//----------------------------------------------------------------------------------------------------------------
	// GET AND SET
		
	


}
