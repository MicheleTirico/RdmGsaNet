package RdmGsaNet_pr08;

import java.util.Collection;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import RdmGsaNetViz.testViz;

public class main {
	
	// create reaction diffusion layer ( gs = Gray Scott )
		// setupGsGrid ( graph size , type of grid ( degree 4 or 8 ) )
	static layerGs gsLayer = new layerGs(new setupGsGrid( 100 , setupGsInter.gsGridType.grid4 ) ) ;
	
	// generate layer of Net
	static layerNet netLayer = new layerNet(new setupNetSeed () ) ;	
	
	// call gs graph ( to test code , not important )
	static Graph gsGraph = layerGs.getGraph() ;
	
	// call NET graph ( to test code , not important )
	static Graph netGraph = layerNet.getGraph() ;
	
	// Initialization object simulation, composed by gsAlgo and growthNet
	static simulation run = new simulation() ;
	
	// initialization of rules to evolving Net	
		// generateNetNodeThreshold ( threshold for activator, threshold for inhibitor )
	static generateNetNode generateNetNode = new generateNetNode (new generateNetNodeThreshold( 0.2 , 0.2 )) ;
	
		// generateNetEdgeNear ( max radius of search )
	static generateNetEdge generateNetEdge = new generateNetEdge (new generateNetEdgeNear(0.5 , generateNetEdgeNear.whichNode.all )) ;
		
	public static void main(String[] args) {

//-------------------------------------------------------------------------------------------------------------------------------		
		// GENERATE LAYER GS
		// method to generate the graph gs
		gsLayer.createLayer ( false , true ) ;
		
		// setup values of distribution of gs morphogens
			// setupDisMorp (type of distribution, random seed act (only random) , random seed inh (only random) , values (only homo) ) 
			// type of distribution = random or homo
		gsLayer.setupDisMorp(setupGsInter.disMorpType.homo , 12 , 34 , 1 , 0 );

		// method to change type of layer ( not used)
//		gsLayer.changeLayer( new setupGsGis() ) ;	
		
//-------------------------------------------------------------------------------------------------------------------------------
		// SETUP START VALUES LAYER GS
			// gsAlgo ( reaction , diffusion, ext, Da , Di , kill , feed )
		gsAlgo values = new gsAlgo( gsAlgo.reactionType.ai2 , gsAlgo.diffusionType.fick , gsAlgo.extType.gsModel , 
									1,			//Da
									0.5 , 		//Di
									0.062 , 		//kill
									0.03 ,		//feed
									true,  1E-20 ,
									true , 1E-20 , 1 ) ;

//-------------------------------------------------------------------------------------------------------------------------------		
		// CREATE LAYER NET
		
		// method to change type of layer ( not used)
//		netLayer.changeLayer(new setupNetRandom() ) ;
 
		/* method to create the layer
		 * createLayer ( bol 	createMeanPoint	= 	chose if we have an initial node (or a small graph ) befor starting simulation
		 * 				 enum	meanPointPlace	=	define were are the mean point of started net graph 	( center , border , random )
		 * 				 bol	setSeedMorp		= 	if true, add a fixed value for act and inh only in node in netGraph 
		 * 				 double	seedAct			=	act value for seed node
		 * 				 double	seedInh			=	inh value for seed node		
		 * 				 bol	setSeedMorpInGs	=	set act and inh of netGraph in gsGraph
		 * 				)*/
		netLayer.createLayer ( true , layerNet.meanPointPlace.center , true , 0 , 1 , false); 
 		
//		for ( Node nNet : netGraph.getEachNode() )//	 {		System.out.println(n.getId());			double act = n.getAttribute("seedAct");			double inh = n.getAttribute("seedInh");//			System.out.println ( act ) ;//			System.out.println ( inh ) ;	}
//			for ( Node nGs : gsGraph.getEachNode() ) {			double inh = nGs.getAttribute("gsInh");//			System.out.println ( n.getId() + "    " + inh ) ;}
			
//-------------------------------------------------------------------------------------------------------------------------------		
		/* RUN simulation
		 * // runSim ( 	int 	stopSim 	= Max step to stop simulation , 
		 * 				bol		printMorp	= true = print mapMorp1 ,
		 * 				bol		genNode		= generate nodes in layer net
		 * 				bol		genEdge		= generate edges in layer net
		 *				) 	*/		
		run.runSim( 50
				, false , false , false  );
		
		System.out.println(simulation.getmapMorp1());
	
//-------------------------------------------------------------------------------------------------------------------------------		
		// EXPORT VALUES


//-------------------------------------------------------------------------------------------------------------------------------		
		// VISUALIZATION 


		testViz.displayColor3(gsGraph, 0.5);
		gsGraph.display(false) ;
		
//		netGraph.display(false);
	}
	
	
}
