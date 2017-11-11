package RdmGsaNet_pr07;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import RdmGsaNet_pr06.setupNetInter.meanPointPlace;

public class run {

	public static void main(String[] args) {
		
		// setup values of layer gs, we defined the type of gs layout
		// setupGsGrid ( size of grid, type or grid) 
			// (grid 4 or grid 8)
		layerGs gsLayer = new layerGs(new setupGsGrid( 2 , setupGsInter.gsGridType.grid4) ) ;
		
		// generate the graph gs
		gsLayer.createLayer();
		
		// setup values of distribution of gs morphogens
		// setupDisMorp (type of distribution, random seed act (only random) , random seed inh (only random) , values (only homo)) 
			// type of distribution = random or homo
		gsLayer.setupDisMorp(setupGsInter.disMorpType.random , 12 , 34 , 0.5 );
//		gsLayer.changeLayer( new setupGsGis() ) ;
		
		Graph gsGraph = layerGs.getGraph(layerGs.gsGraph);
		
//		gsGraph.display();
		
//		for ( Node n : gsGraph.getEachNode()) { Object a = n.getAttribute("GsAct") ; System.out.println( a ); }
		
		// setup start values of gsAlgo
			// gsAlgo ( Da , Di , kill , feed )
		gsAlgo values = new gsAlgo(0.4, 0.1, 0.5, 0.8);
		
		// run simulation gsAlgo
		gsAlgo.gsAlgoMain( 5 , gsAlgo.reactionType.ai2, gsAlgo.diffusionType.fick, gsAlgo.extType.gsModel);

		layerNet netLayer = new layerNet(new setupNetSeed () ) ;
		
//		netLayer.changeLayer(new setupNetRandom() ) ;
		
		netLayer.setupGsNetLink( setupNetInter.meanPointPlace.center);
		
//		for ( Node n : gsGraph.getEachNode()) { Object a = n.getAttribute("GsAct") ; System.out.println( a ); }
		
		growthNet growthNet = new growthNet(new growthNetNear()) ;
		
		growthNet.runGrowth();
	}



}
