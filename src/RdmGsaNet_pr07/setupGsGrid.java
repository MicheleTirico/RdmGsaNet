package RdmGsaNet_pr07;

import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.GridGenerator;
import org.graphstream.graph.Graph;

public class setupGsGrid implements setupGsInter {
	
	public static int size ;
	private gsGridType type;
		
	public setupGsGrid (int size, gsGridType type ) {
		this.size = size ;
		this.type = type ;
	}

	@Override
	public void createLayerGs() {
		
//		System.out.println(size);
//		System.out.println(type);
		
		boolean typebol;
		if (type == gsGridType.grid4) { typebol = false ; }
		else { typebol = true ;	}
	
		Graph graph = layerGs.getGraph( layerGs.gsGraph );
			
		Generator GsGen = new GridGenerator(typebol , false);
		GsGen.addSink(graph);
		GsGen.begin();
		for(int i = 0 ; i < size ; i++) { 	GsGen.nextEvents(); 	}
		GsGen.end();
	}
	
	public static int getGsGridSize () { return size; }
	

}
