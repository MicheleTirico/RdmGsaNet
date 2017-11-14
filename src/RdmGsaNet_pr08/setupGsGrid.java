package RdmGsaNet_pr08;

import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.GridGenerator;
import org.graphstream.graph.Graph;

public class setupGsGrid implements setupGsInter {
	
	private static int size ;
	private static gsGridType type;
		
	public setupGsGrid (int size, gsGridType type ) {
		this.size = size ;
		this.type = type ;
	}

	public void createLayerGs() {
		// in this method ( initialized in interface setupGsInter ) we create the graph gs for the layer gs.
		
//		System.out.println(size);	System.out.println(type);
		
		// create boolean switch to choice grid type ( degree 4 or 8 )
		boolean typebol;
		if (type == gsGridType.grid4) { typebol = false ; }
		else { typebol = true ;	}
	
		// call graph of layer gs
		Graph graph = layerGs.getGraph( layerGs.gsGraph );
			
		// generate graph
		Generator gsGen = new GridGenerator(typebol , false);
		gsGen.addSink(graph);
		gsGen.begin();
		for(int i = 0 ; i < size ; i++) { 	gsGen.nextEvents(); 	}
		gsGen.end();
	}
	
	public static int getGsGridSize () { return size; }
	

}
