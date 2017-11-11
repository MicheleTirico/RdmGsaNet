package RdmGsaNet_pr08;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class run {

	static growthNet growthNet = new growthNet(new growthNetNear()) ;
	static layerGs gsLayer = new layerGs(new setupGsGrid( 2 , setupGsInter.gsGridType.grid4) ) ;
	static Graph gsGraph = layerGs.getGraph(layerGs.gsGraph) ;
	static layerNet netLayer = new layerNet(new setupNetSeed () ) ;
	static simulation run = new simulation() ;
		
	public static void main(String[] args) {
		
		// setup values of layer gs, we defined the type of gs layout
		// setupGsGrid ( size of grid, type or grid) 
			// (grid 4 or grid 8)
		
		// generate the graph gs
		gsLayer.createLayer();
		
		// setup values of distribution of gs morphogens
		// setupDisMorp (type of distribution, random seed act (only random) , random seed inh (only random) , values (only homo)) 
			// type of distribution = random or homo
		gsLayer.setupDisMorp(setupGsInter.disMorpType.random , 12 , 34 , 0.5 );
//		gsLayer.changeLayer( new setupGsGis() ) ;	
		
//-------------------------------------------------------------------------------------------------------------------------------
		// SETUP START VALUES LAYER GS
			// gsAlgo ( reaction , diffusion, ext, Da , Di , kill , feed )
		gsAlgo values = new gsAlgo( gsAlgo.reactionType.ai2 , gsAlgo.diffusionType.fick , gsAlgo.extType.gsModel , 
									0.4 , 0.1 , 0.5 , 0.8 ) ;

//-------------------------------------------------------------------------------------------------------------------------------		
		// CREATE LAYER NET
//		netLayer.changeLayer(new setupNetRandom() ) ;
		netLayer.setupGsNetLink( setupNetInter.meanPointPlace.center);
		
		run.runSim( 5 );
	}
}
