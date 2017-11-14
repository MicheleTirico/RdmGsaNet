package RdmGsaNet_pr08;

import java.util.Collection;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class run {
	
	// create reaction diffusion layer ( gs = Gray Scott )
		// setupGsGrid ( graph size , type of grid ( degree 4 or 8 ) )
	static layerGs gsLayer = new layerGs(new setupGsGrid( 2 , setupGsInter.gsGridType.grid4) ) ;
	
	// call gs graph ( to test code , not important )
	static Graph gsGraph = layerGs.getGraph(layerGs.gsGraph) ;
	
	// call NET graph ( to test code , not important )
	static Graph netGraph = layerNet.getGraph(layerNet.netGraph) ;
	
	// generate layer of Net
	static layerNet netLayer = new layerNet(new setupNetSeed () ) ;
	
	// Initialization object simulation, composed by gsAlgo and growthNet
	static simulation run = new simulation() ;
	
	// initialization of rules to evolving Net
	static generateNetNode generateNetNode = new generateNetNode (new generateNodeThreshold( 0.5 , 0.5 )) ;
	static generateNetEdge generateNetEdge = new generateNetEdge (new generateNetEdgeNear()) ;
		
	public static void main(String[] args) {

//-------------------------------------------------------------------------------------------------------------------------------		
		// GENERATE LAYER GS
		// method to generate the graph gs
		gsLayer.createLayer();
		
		// setup values of distribution of gs morphogens
			// setupDisMorp (type of distribution, random seed act (only random) , random seed inh (only random) , values (only homo) ) 
			// type of distribution = random or homo
		gsLayer.setupDisMorp(setupGsInter.disMorpType.random , 12 , 34 , 0.5 );

		// method to change type of layer ( not used)
//		gsLayer.changeLayer( new setupGsGis() ) ;	
		
//-------------------------------------------------------------------------------------------------------------------------------
		// SETUP START VALUES LAYER GS
			// gsAlgo ( reaction , diffusion, ext, Da , Di , kill , feed )
		gsAlgo values = new gsAlgo( gsAlgo.reactionType.ai2 , gsAlgo.diffusionType.fick , gsAlgo.extType.gsModel , 
									0.4 , 0.1 , 0.5 , 0.8 ) ;

//-------------------------------------------------------------------------------------------------------------------------------		
		// CREATE LAYER NET
		// method to change type of layer ( not used)
//		netLayer.changeLayer(new setupNetRandom() ) ;
		netLayer.setupGsNetLink( setupNetInter.meanPointPlace.center);
		netLayer.createLayer();
		
//-------------------------------------------------------------------------------------------------------------------------------		
		// RUN simulation
			// runSim ( Max step to stop simulation )
		run.runSim( 10 );
		
//		gsGraph.display();
//		for ( Node n : gsGraph.getEachNode()) {	 int a = n.getAttribute("con");  System.out.println(a);		}
		
//-------------------------------------------------------------------------------------------------------------------------------		
		// EXPORT VALUES


//-------------------------------------------------------------------------------------------------------------------------------		
		// VISUALIZATION 
		
		
	}
}
