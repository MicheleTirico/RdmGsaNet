package RdmGsaNet_pr08;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import RdmGsaNetExport.handleNameFile;
import RdmGsaNetViz.setupViz;
import RdmGsaNet_pr08.generateNetNodeGradient.splitSeed;

public class main {
	private static int stopSim = 3000 ;
	
	private static Map<Double , Graph > mapStepNetGraph = simulation.getMapStepNetGraph() ;
	private static Map<String, ArrayList<Double >> mapMorp0 = simulation.getmapMorp0() ;
	private static Map<String, ArrayList<Double >> mapMorp1 = simulation.getmapMorp1() ;
	
	// STORE DGS PARAMETERS
	
	private static boolean 	doStoreStartGs 	= true, 
							doStoreStepGs 	= true,
							doStoreStartNet = true, 
							doStoreStepNet 	= true,
							doStoreIm		= false ;
	
	private static String 	fileType = ".dgs" ,
							fileTypeIm = "png" ;
	
	// folder
	private static  String 	folder = "D:\\ownCloud\\RdmGsaNet_exp\\gradient_multiSeed\\" ;

	private static String 	pathStepNet ,	pathStepGs ,	pathStartNet ,	pathStartGs ;
	
	private static String 	folderNew = handleNameFile.getPath();
	
	private static String 	nameStartGs , nameStartNet , nameStepGs , nameStepNet ;
	
	// HANDLE FILE OBJECT
	private static handleNameFile handle ;
	
	/* create reaction diffusion layer ( gs = Gray Scott )
	* 		setupGsGrid 	->	int size		=	graph size , 
	* 							enum gsGridType	=	set type of grid ( degree 4 or 8 )  */
	static layerGs gsLayer = new layerGs(new setupGsGrid( 50 , setupGsInter.gsGridType.grid8 ) ) ;
	
	// generate layer of Net
//	static layerNet netLayer = new layerNet (new setupNetSmallGrid ( setupNetSmallGrid.typeGrid.grid4) );	
	static layerNet netLayer = new layerNet (new setupNetSeed());
	
	// call gs graph ( to test code , not important )
	static Graph gsGraph = layerGs.getGraph() ;
	
	// call NET graph ( to test code , not important )
	static Graph netGraph = layerNet.getGraph() ;
	
	// Initialization object simulation, composed by gsAlgo and growthNet
	static simulation run = new simulation() ;
	
	// initialization of rules to evolving Net	
		// generateNetNodeThreshold ( threshold for activator, threshold for inhibitor )
		// generateNetNodeThreshold ( ) 
	static generateNetNode generateNetNode = new generateNetNode (new generateNetNodeGradient(
			/* number of started seed 	*/	  1
			/* morphogen 				*/	, "gsAct" 
			/* type of seed behavior 	*/	, splitSeed.splitProbability
			/* seed move to greater ? 	*/	, true 
			/* set increment Ass		*/	, 0.001
			/* set increm Res (not impl	*/	, 0
			/* set prob test			*/	, 0.10
			/* still alive 				*/	, true
											)) ;
	
	// generateNetEdgeNear (  )
	static generateNetEdge generateNetEdge = new generateNetEdge (new generateNetEdgeNear( 
			/* radius max ?	*/				0 
			/* which node link ? 	*/		, generateNetEdgeNear.whichNode.all )) ;
		
	public static void main(String[] args) throws IOException, InterruptedException 	{	
		
		handle = new handleNameFile(folder);		

		// SETUP START VALUES LAYER GS
		/*	gsAlgo -> 	enum	reactionType 
		* 				enum	diffusionType 
		* 				enum	extType 
		* 				Da , Di , feed , kill 
		* 				bol 	HandleNaN		= if true, set default value when act or inh is over NaN 
		* 				double	setIfNaN		= defalt value if act or inh is NaN 
		* 				bol		handleMinMaxVal	= if true, set value for values over the range
		*  				double	minVal			= default value if morph < minVal, set minVal
		*  				double	mmaxVal			= default value if morph > maxVal, set maxVal */
		gsAlgo values = new gsAlgo( gsAlgo.reactionType.ai2 , gsAlgo.diffusionType.weight , gsAlgo.extType.gsModel , 
			/* Da 	*/			0.2,			
			/* Di 	*/			0.1, 		
			/* feed */			0.03 , 	
			/* kill */			0.062 ,		
								true , 1E-5 ,
								true , 1E-5 , 1 ) ;
  
		// create path in order to stored all dgs files
		String pathStepNet = handle.getPathStepNet() ; 		//	System.out.println("pathStepNet " + pathStepNet);		
		String PathStepGs = handle.getPathStepGs();			//	System.out.println("PathStepGs " + PathStepGs);		
		String pathStartNet = handle.getPathStartNet();		//	System.out.println("pathStartNet " + pathStartNet);
		String pathStartGs = handle.getPathStartGs();		//	System.out.println("pathStartGs " + pathStartGs);

//-------------------------------------------------------------------------------------------------------------------------------		
		// GENERATE LAYER GS
	
		/* CREATE GS GRAPH
		 *  method to generate the graph gs
		 *  createLayer = 	bol		setCoordinate	=
		 *  				bol		setDefaultAtr 	=
		 *  				bol		storedDGS		= if true , create a dgs file of started graph  */
		gsLayer.createLayer ( false , true , doStoreStartGs ) ;
		
	/* SETUP DISMORP
	 *  setup values of distribution of gs morphogens
	 *  setupDisMorp ->	enum	type of distribution
	  					int 	randomSeedAct 	=	(only random)  
	  					int 	randomSeedInh 	=	(only random)  
	  					double 	act				=	(only homo) 
	  					double 	inh				=	(only homo)  */
		gsLayer.setupDisMorp(setupGsInter.disMorpType.homo , 12 , 34 , 1 , 0 );

//-------------------------------------------------------------------------------------------------------------------------------
	
	// SETUP DIFFUSION
//		gsAlgoDiffusion.setLaplacianMatrix ( 0.2, 0.05 ) ; // not implemented
		gsAlgoDiffusion.setWeightType ( gsAlgoDiffusion.weightType.matrix );

//-------------------------------------------------------------------------------------------------------------------------------		
		// CREATE LAYER NET
		/* method to create the layer
		 * createLayer (	bol 	createMeanPoint	= 	chose if we have an initial node (or a small graph ) befor starting simulation
		 * 				 	enum	meanPointPlace	=	define were are the mean point of started net graph 	( center , border , random )
		 * 				 	bol		setSeedMorp		= 	if true, add a fixed value for act and inh only in node in netGraph 
		 * 				 	double	seedAct			=	act value for seed node
		 * 				 	double	seedInh			=	inh value for seed node		
		 * 				 	bol		setSeedMorpInGs	=	set act and inh of netGraph in gsGraph
		 *  			 	bol		storedDGS		= 	if true , create a dgs file of started graph
		 * 				)*/
		netLayer.createLayer ( true , layerNet.meanPointPlace.center , true , 1 , 1 , true , doStoreStartNet ); 
			
//-------------------------------------------------------------------------------------------------------------------------------		
		/* RUN simulation
		 * // runSim ( 	int 	stopSim 		= Max step to stop simulation , 
		 * 				bol		printMorp		= true = print mapMorp1 ,
		 * 				bol		genNode			= generate nodes in layer net
		 * 				bol		genEdge			= generate edges in layer net
		 * 				bol		storedDgsStep	= if true, export the gsGraph in .dgs format at each step 
		 *  			bol		storedDgsStep	= if true, export the netGraph in .dgs format at each step 
		 *) 	*/		
		run.runSim( stopSim , false , true , true , doStoreStepGs , pathStepGs, doStoreStepNet , pathStepNet );	//	

		int seedAlive = 0 ;
		for ( Node n : netGraph.getEachNode()) { 
		//	System.out.println("idNode " + n.getId() + " attr " + n.getAttributeKeySet()  + " seedGrad " + n.getAttribute("seedGrad"));
			int seed = n.getAttribute("seedGrad") ;
			if(  seed == 1 )
				seedAlive = seedAlive + seed;
		}
		
		System.out.println("seedAlive " + seedAlive);
//-------------------------------------------------------------------------------------------------------------------------------		
		// VISUALIZATION 

//		setupViz.Viz4Color( gsGraph );
		setupViz.VizNodeId( netGraph );	
		setupViz.Viz10ColorAct( gsGraph ) ;	
//		setupViz.Vizmorp(gsGraph, "gsAct");
//		setupViz.Vizmorp(gsGraph, "gsInh");
		gsGraph.display(false) ;
		netGraph.display(false) ;	
	}
	
// PRIVATE METHODS ----------------------------------------------------------------------------------------------------------------------------------

// GET METHODS --------------------------------------------------------------------------------------------------------------------------------------
	public static layerNet getNetLayer() 		{ return netLayer;	}
	public static int getStopSim() 				{ return stopSim ; } 
	public static handleNameFile getHandle() 	{ return handle; }
}