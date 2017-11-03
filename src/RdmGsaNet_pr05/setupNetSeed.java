package RdmGsaNet_pr05;

import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.GridGenerator;
import org.graphstream.graph.Graph;

public class setupNetSeed implements setupNet{

	public void setupNetCreate() {
		System.out.println("hello seed");	
		
		
	}

	public void setupNetInit(int size, start point) {
		
		createGrid(size, netGraph, false ) ;
		
		System.out.println("size seed " + size);
		
		
	}

//-----------------------------------------------------------------------------------------------------	
	// PRIVATE METHODS 
	private void createGrid (int size, Graph graph, boolean gridType) {
		Generator GsGen4 = new GridGenerator(gridType, false);
		GsGen4.addSink(graph);
		GsGen4.begin();
		for(int i = 0 ; i < size ; i++) { 	GsGen4.nextEvents(); 	}
		GsGen4.end();
	}



}
