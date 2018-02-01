package RdmGsaNet_pr09;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import RdmGsaNetExport.handleNameFile;
import RdmGsaNetViz.setupViz;
import RdmGsaNet_pr09.generateNetEdgeNear.whichNode;
import RdmGsaNet_pr09.generateNetNodeGradient.splitSeed;
import RdmGsaNet_pr09.gsAlgoDiffusion.weightType;
import RdmGsaNet_pr09.layerNet.meanPointPlace;
import RdmGsaNet_pr09.setupGs_Inter.disMorpType;
import RdmGsaNet_pr09.setupGs_Inter.gsGridType;
import RdmGsaNet_pr09.setupNetSmallGraph.smallGraphType;
import RdmGsaNet_pr09.setupNetSmallGrid.typeGrid;

public class main {
	private static int stopSim = 1;
	
	private static enum RdmType { holes , solitions , movingSpots , pulsatingSolitions , mazes , U_SkateWorld , f055_k062 }
	private static RdmType type ;
	
	// STORE DGS PARAMETERS
	private static boolean 	doStoreStartGs 	= false, 
							doStoreStepGs 	= false,
							doStoreStartNet = false, 
							doStoreStepNet 	= false,
							doStoreIm		= false ;
	
	private static String 	fileType = ".dgs" ,
							fileTypeIm = "png" ;
	
	private static double 	feed , kill ;
	
	// folder
	private static  String 	folder = "D:\\ownCloud\\RdmGsaNet_exp\\test_gradient_03\\RD_mazes\\" ;

	// path
	private static String 	pathStepNet ,	pathStepGs ,	pathStartNet ,	pathStartGs ;
	private static String 	folderNew = handleNameFile.getPath();
	
	//name file
	private static String 	nameStartGs , nameStartNet , nameStepGs , nameStepNet ;
	
	// HANDLE FILE OBJECT
	private static handleNameFile handle ;
	
	// create reaction diffusion layer ( gs = Gray Scott )
	static layerGs gsLayer = new layerGs(
		/* size grid , type grid 				*/	new setupGsGrid( 50 , gsGridType.grid8 ) ) ;

	static layerNet netLayer = new layerNet (
//		/* create only one node					*/ new setupNetSeed()	
//		/* small grid of 9 nodes 				*/ new setupNetSmallGrid(typeGrid.grid4)
		/* layout small graph 					*/ new setupNetSmallGraph( smallGraphType.star4Edge)
		);
	
	// get  Graphs ( only to test results ) 
	protected static Graph gsGraph = layerGs.getGraph() ,
							netGraph = layerNet.getGraph() ;
	
	// Initialization object simulation, composed by gsAlgo and growthNet
	static simulation run = new simulation() ;

	static generateNetNode generateNetNode = new generateNetNode (
//		/* threshold for activator, threshold for inhibitor 	*/	new generateNetNodeThreshold(12, 11) 
		/* type of split seed 									*/	new generateNetNodeGradient(splitSeed.onlyOneRandom)
			) ;

	static generateNetEdge generateNetEdge = new generateNetEdge (
			/* radius , which node to connect		*/	new generateNetEdgeNear( 0 , whichNode.all )) ;
	
// RUN SIMULATION -----------------------------------------------------------------------------------------------------------------------------------		
	public static void main(String[] args) throws IOException, InterruptedException 	{	
		
		// setup handle name file 
		handle = new handleNameFile( false , folder);		

		// setup type RD
		setRdType(RdmType.mazes);			
		
		// SETUP START VALUES LAYER GS
		gsAlgo values = new gsAlgo( 
			/* enum reaction , diffusion ext	*/		gsAlgo.reactionType.ai2 , gsAlgo.diffusionType.weight , gsAlgo.extType.gsModel , 
			/* Da 	*/									0.2,			
			/* Di 	*/									0.1, 		
			/* feed */									feed , 	
			/* kill */									kill ,		
			/* HandleNaN , setIfNaN */					true , 1E-5 , 			/* if true, set default value when act or inh is over NaN  */
			/* handleMinMaxVal , minVal , maxVal */		true , 1E-5 , 1 ) ; 	/* if true, set value for values over the range */
  
		// create path in order to stored all dgs files
		String pathStepNet = handle.getPathStepNet() ; 		//	System.out.println("pathStepNet " + pathStepNet);		
		String PathStepGs = handle.getPathStepGs();			//	System.out.println("PathStepGs " + PathStepGs);		
		String pathStartNet = handle.getPathStartNet();		//	System.out.println("pathStartNet " + pathStartNet);
		String pathStartGs = handle.getPathStartGs();		//	System.out.println("pathStartGs " + pathStartGs);

// GENERATE LAYER GS --------------------------------------------------------------------------------------------------------------------------------		
		// CREATE GS GRAPH
		gsLayer.createLayer ( 
			/* set coordinate 			*/ 	false , 
			/* set default Atribute		*/ 	true , 
			/* store results in folder? */	doStoreStartGs ) ;
		
		// SETUP DISMORP
		gsLayer.setupDisMorp(
			/* enum	type of distribution			*/	disMorpType.homo , 
			/* int 	randomSeedAc (only random)		*/	12 , 
			/* int 	randomSeedInh (only random)		*/	34 , 
			/* double 	act	(only homo)				*/	1 , 
			/* double 	inh	(only homo)				*/	0 );

// SETUP DIFFUSION ----------------------------------------------------------------------------------------------------------------------------------
//		gsAlgoDiffusion.setLaplacianMatrix ( 0.2, 0.05 ) ; // not implemented
		gsAlgoDiffusion.setWeightType ( weightType.matrix );

//  CREATE LAYER NET --------------------------------------------------------------------------------------------------------------------------------		
		// CREATE LAYER NET
		netLayer.createLayer ( 
			/* bol 	createMeanPoint		= 	chose if we have an initial node (or a small graph ) befor starting simulation		*/ true , 
			/* enum	meanPointPlace		=	define were are the mean point of started net graph ( center , border , random )	*/ meanPointPlace.center ,
			/* bol		setSeedMorp		= 	if true, add a fixed value for act and inh only in node in netGraph 				*/ true ,
			/* double	seedAct			=	act value for seed node																*/ 1 , 
			/* double	seedInh			=	inh value for seed node	 															*/ 1 , 
			/* bol		setSeedMorpInGs	=	set act and inh of netGraph in gsGraph												*/ true ,
			/* bol		storedDGS		= 	if true , create a dgs file of started graph										*/ doStoreStartNet
			);
			
// RUN SIMULATION -----------------------------------------------------------------------------------------------------------------------------------			
		simulation.runSim( 
			/* int 		stopSim 		= Max step to stop simulation , 										*/ stopSim ,
			/* bol		printMorp		= print mapMorp1 ,														*/ false ,
			/* bol		genNode			= generate nodes in layer net											*/ true ,
			/* bol		genEdge			= generate edges in layer net											*/ true ,
			/* bol		storedDgsStep	= if true, export the gsGraph in .dgs format at each step 				*/ doStoreStepGs ,
			/* string 	path to stored step gs file 															*/ pathStepGs ,
		 	/* bol		storedDgsStep	= if true, export the netGraph in .dgs format at each step 				*/ doStoreStepNet ,
		 	/* string 	path to stored step net file 															*/pathStepNet
		 	);

		//get seedAlive
		int seedAlive = getSeedAlive(false);
		
		ArrayList listIdNetSeedGrad = getListIdWithAttribute(false, netGraph, "seedGrad");
		printNodeSetAttribute(false , netGraph) ;
		printEdgeSetAttribute(false , netGraph) ;
		
//-------------------------------------------------------------------------------------------------------------------------------		
		// VISUALIZATION 

		setupViz.setFixScale(netGraph, gsGraph);
//		setupViz.Viz4Color( gsGraph );
	//	setupViz.VizNodeId( netGraph );	
		setupViz.Viz10ColorAct( gsGraph ) ;	
//		setupViz.Vizmorp(gsGraph, "gsAct");
//		setupViz.Vizmorp(gsGraph, "gsInh");
		gsGraph.display(false) ;
		setupViz.VizSeedGrad(netGraph, "seedGrad");
		netGraph.display(false) ;
		
	}
	
// PRIVATE METHODS ----------------------------------------------------------------------------------------------------------------------------------
	// PRINT METHODS --------------------------------------------------------------------------------------------------------------------------------
	protected static void printNodeSetAttribute ( boolean print, Graph graph ) {
	
		if ( print )	
			for ( Node n : netGraph.getEachNode() ) 
				System.out.println(n.getId() + " " + n.getAttributeKeySet());
	}
	
	protected static void printEdgeSetAttribute (boolean print, Graph graph ) {
		
		if ( print )	
			for ( Edge e : netGraph.getEachEdge() )
				System.out.println(e.getId() + " " + e.getAttributeKeySet());
	}
		
	// get arrayList of node id with attribute 
	public static ArrayList getListIdWithAttribute ( boolean printListId ,Graph graph , String atr ) {
		ArrayList<String> listId = new ArrayList<String>();
		for ( Node n : graph.getEachNode()) {
			int val = n.getAttribute(atr);
			if ( val == 1 ) 
				listId.add(n.getId());
		}
		if ( printListId) 
			System.out.println( atr + " " + listId);
		return listId;
	}
	
	// get seed Alive number
	private static int getSeedAlive ( boolean printValue ) {

		int seedAlive = 0 ;
		for ( Node n : netGraph.getEachNode()) { 
			int seed = n.getAttribute("seedGrad") ;
			if(  seed == 1 )
				seedAlive = seedAlive + seed;
		}
		if ( printValue )
			System.out.println("seedAlive " + seedAlive);
		return seedAlive;
	}
	
	// set RD start values to use in similtion ( gsAlgo )
	private static  void setRdType ( RdmType type ) {
		
		switch ( type ) {
			case holes: 				{ feed = 0.039 ; kill = 0.058 ; } 
										break ;
			case solitions :			{ feed = 0.030 ; kill = 0.062 ; } 
										break ; 
			case mazes : 				{ feed = 0.029 ; kill = 0.057 ; } 
										break ;
			case movingSpots :			{ feed = 0.014 ; kill = 0.054 ; } 
										break ;
			case pulsatingSolitions :	{ feed = 0.025 ; kill = 0.060 ; } 
										break ;
			case U_SkateWorld :			{ feed = 0.062 ; kill = 0.061 ; } 
										break ;
			case f055_k062 :			{ feed = 0.055 ; kill = 0.062 ; } 
										break ;
		}
		
	}
// GET METHODS --------------------------------------------------------------------------------------------------------------------------------------
	public static layerNet getNetLayer() 		{ return netLayer;	}
	public static int getStopSim() 				{ return stopSim ; } 
	public static handleNameFile getHandle() 	{ return handle; }
}