package RdmGsaNet_pr05;

import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.GridGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class setupNetSeed implements setupNet{

	public void setupNetCreate() {
		System.out.println("hello seed");		
	}


	public void setupNetSetGs(Graph graph) {		
		
		Graph GsGraph = setupGs.getGraph( setupGs.GsGraph );

		setupNetLayer.setDefaultConnection( GsGraph , 0 );	
		
		for ( Node n : GsGraph.getEachNode() )  {	}
	
	}
	
	public void setupNetInit(int size, layout typeLayout) {
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
