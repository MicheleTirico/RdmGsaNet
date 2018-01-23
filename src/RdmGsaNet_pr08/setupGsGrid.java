package RdmGsaNet_pr08;

import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.GridGenerator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import RdmGsaNetAlgo.gsAlgoToolkit;



public class setupGsGrid implements setupGsInter {
	
	private static int size ;
	private static gsGridType type;
	static Graph graph = layerGs.getGraph(  );
		
	public setupGsGrid (int size, gsGridType type ) {
		this.size = size ;
		this.type = type ;
	}

	// in this method ( initialized in interface setupGsInter ) we create the graph gs for the layer gs.
	public void createLayerGs() {
		
			
		// create boolean switch to choice grid type ( degree 4 or 8 )
		boolean typebol;
		if (type == gsGridType.grid4) 	{ typebol = false ; }
		else 							{ typebol = true ;	}
		
//		if ( type == gsGridType.grid8weig ) {	setupWeight(); }
		// call graph of layer gs
		
		// generate graph
		Generator gsGen = new GridGenerator(typebol , false);
		gsGen.addSink(graph);
		gsGen.begin();
		for(int i = 0 ; i < size ; i++) { 	gsGen.nextEvents(); 	}
		gsGen.end();
	}
	
	public void setCoordinate() {
		
	}
	
	
	
//--------------------------------------------------------------------------------------------------------------
	// GET AND SET
	public static int getGsGridSize () { return size; }

	public static String getGridType() { return type.name(); }
}
