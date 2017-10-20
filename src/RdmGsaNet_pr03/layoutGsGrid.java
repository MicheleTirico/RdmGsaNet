package RdmGsaNet_pr03;
 
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.GridGenerator;

import java.util.Random;

public  class layoutGsGrid extends layoutGsAbs implements layoutGs   {
	
	
	// enumerate GRID
	public enum type { grid4 , grid8 }			// type layout
	
	//  enumerate distribution of morhogens
	public enum disMorp { homogeneus, random }	// morphogens distribution layout 
	
	// variables
	type type ;
	disMorp distribution ;
	int size ;
	
	// constructor
	public layoutGsGrid (type type, int size, disMorp distribution ) {
		this.type = type ;								// costructor.parameters
		this.distribution = distribution ;				// costructor.parameters
		this.size = size;								// costructor.parameters

		setGrid(GsGraph);								// costructor.method
		layoutGs.setMorp(GsGraph);						// costructor.method	
		layoutGs.showValueMorp(GsGraph);
	//	layoutGs.showGraph(GsGraph);
	
	}
	
	// methods to setting graph and attributes
	private Graph setGrid(Graph graph) {
		switch (type) {
		case grid4: 
			System.out.println("layoutGs grid4");
			Generator GsGen4 = new GridGenerator(false, false);
			GsGen4.addSink(graph);
			GsGen4.begin();
			for(int i=0; i<size; i++) { 	GsGen4.nextEvents(); 	}
			GsGen4.end();
			break;	
		case grid8:
			System.out.println("layoutGs grid8");
			Generator GsGen8 = new GridGenerator(true, false);
			GsGen8.addSink(graph);
			GsGen8.begin();
			for(int i=0; i<size; i++) { 	GsGen8.nextEvents();	}
			GsGen8.end();
			break;
			}
		return graph;
		}

	

	

	}
	
	
	
	

			