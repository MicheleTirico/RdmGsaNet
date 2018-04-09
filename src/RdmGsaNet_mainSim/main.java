package RdmGsaNet_mainSim;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import com.vividsolutions.jts.geom.Point;

import RdmGsaNetExport.handleNameFile;
import RdmGsaNetExport.handleNameFile.typeFile;

import RdmGsaNetViz.handleVizStype;
import RdmGsaNetViz.handleVizStype.palette;
import RdmGsaNetViz.handleVizStype.stylesheet;

import RdmGsaNet_generateGraph.generateNetEdge;
import RdmGsaNet_generateGraph.generateNetEdgeInRadiusFather;

import RdmGsaNet_generateGraph.generateNetEdgeNear;
import RdmGsaNet_generateGraph.generateNetEdgeNear.whichNode;

import RdmGsaNet_generateGraph.generateNetEdge.genEdgeType;
import RdmGsaNet_generateGraph.generateNetEdgeDelaunay;
import RdmGsaNet_generateGraph.generateNetEdgeDelaunay_02;
import RdmGsaNet_generateGraph.generateNetEdgeInRadiusFather_02;
import RdmGsaNet_generateGraph.generateNetNode;
import RdmGsaNet_generateGraph.generateNetNode.interpolation;
import RdmGsaNet_generateGraph.generateNetNode.layoutSeed;
import RdmGsaNet_generateGraph.generateNetNodeVectorFieldSeedCost;
import RdmGsaNet_generateGraph.generateNetNodeVectorFieldSplitSeedProb;
import RdmGsaNet_generateGraph.generateNetNodeVectorFieldSplitSeedProb_02;
import RdmGsaNet_graphTopology.topologyGraph;
import RdmGsaNet_graphTopology.topologyGraph.topologyGraphType;
import RdmGsaNet_gsAlgo.gsAlgo;
import RdmGsaNet_gsAlgo.gsAlgoDiffusion;
import RdmGsaNet_gsAlgo.gsAlgoDiffusion.weightType;

import RdmGsaNet_mainSim.layerNet.meanPointPlace;

import RdmGsaNet_setupLayer.setupGsGrid;
import RdmGsaNet_setupLayer.setupGs_Inter.disMorpType ;
import RdmGsaNet_setupLayer.setupGs_Inter.gsGridType;
import RdmGsaNet_setupLayer.setupNetFistfulNodes;
import RdmGsaNet_setupLayer.setupNetFistfulNodes.typeRadius;
import RdmGsaNet_setupLayer.setupNetMultiGraph;
import RdmGsaNet_setupLayer.setupNetSeed;
import RdmGsaNet_setupLayer.setupNetSmallGrid;

import RdmGsaNet_vectorField_02.vectorField;
import RdmGsaNet_vectorField_02.vectorField.vectorFieldType;
import RdmGsaNet_vectorField_02.vectorField.vfNeig;
import RdmGsaNet_vectorField_02.vectorField.weigthDist;

import dynamicGraphSimplify.dynamicSymplify;
import dynamicGraphSimplify.dynamicSymplify.simplifyType ;

public class main {
	private static int stopSim = 30 ;
	private static double sizeGridEdge ;
	
	private static enum RdmType { holes , solitions , movingSpots , pulsatingSolitions , mazes , U_SkateWorld , f055_k062 , chaos , spotsAndLoops , worms }
	private static RdmType type ;
	
//	private static Map<Double , Graph > mapStepNetGraph = simulation.getMapStepNetGraph() ;
//	private static Map<String, ArrayList<Double >> mapMorp0 = simulation.getmapMorp0() ;
//	private static Map<String, ArrayList<Double >> mapMorp1 = simulation.getmapMorp1() ;
	
	// STORE DGS PARAMETERS
	private static boolean 	doStoreStartGs 		= true , 
							doStoreStepGs 		= true ,
							doStoreStartNet 	= true , 
							doStoreStepNet 		= true ,
							doStoreStartVec 	= true ,
							doStoreStepVec 		= true ,
							doStoreStartSeed	= true ,
							doStoreStepSeed		= true ,
							doStoreIm			= false ;
	
	public static boolean storeGsValues = false ;
	
	private static String 	fileType   = ".dgs" ,
							fileTypeIm = "png" ;
	
	private static double 	feed , kill ;
		
	// folder
	private static  String 	folder = "D:\\ownCloud\\RdmGsaNet_exp\\test\\01\\" ;

	// path
	private static String 	pathStepNet ,	pathStepGs ,	pathStartNet ,	pathStartGs , pathStartVec , pathStepVec ,
							folderNew = handleNameFile.getPath();
	
	//name file
	private static String 	nameStartGs , nameStartNet , nameStepGs , nameStepNet ;
	
	// HANDLE FILE OBJECT
	private static handleNameFile handle ;
	
	// create reaction diffusion layer ( gs = Gray Scott )
	static layerGs gsLayer = new layerGs(
		/* size grid , type grid 				*/	new setupGsGrid( 50 , gsGridType.grid8 ) ) ;

	static layerNet netLayer = new layerNet (
//		/* create only one node			*/ new setupNetSeed()	
		/* small grid of 9 nodes 		*/ new setupNetSmallGrid(setupNetSmallGrid.typeGrid.grid4 , true )
//		/* layout small graph 			*/ new setupNetSmallGraph( smallGraphType.star4Edge )
//		/* create a fistful of node 	*/ new setupNetFistfulNodes( 10 , typeRadius.square , 2 , false )
//		/* create multi graph 			*/ new setupNetMultiGraph ( 10 , 20.0 , 7 , 1.5 , true , 10  )
			);
	
	// get  Graphs ( only to test results ) 
	protected static Graph 	gsGraph   = layerGs.getGraph() ,
							netGraph  = layerNet.getGraph() ,
							vecGraph  = new SingleGraph ( "vecGraph" ) ,
							seedGraph = new SingleGraph ( "seedGraph" ) ,
							delGraph  = new SingleGraph ( "delGraph" ) ;
							
	
	// Initialization object simulation, composed by gsAlgo and growthNet
	protected static simulation run = new simulation() ;	
	
	protected static generateNetNode generateNetNode = new generateNetNode (
//		 		*/	new generateNetNodeThreshold        			( 12, 11 )  
//					new generateNetNodeGradientOnlyOne 				( 8 , layoutSeed.allNode , rule.maxValue, "gsInh")
//					new generateNetNodeGradientProb	    			( 8 , layoutSeed.allNode , rule.random , "gsInh", 1 , true )
//					new generateNetNodeGradientProbDelta 			( 8 , layoutSeed.allNode, rule.random, "gsAct", .8, false )
//					new generateNetNodeGradientProbDeltaControlSeed ( 8 , layoutSeed.allNode, rule.random, "gsInh", 1, true , true ) 	
//					new generateNetNodeBreakGridThrowSeed			( 8 , interpolation.averageEdge )	
//					new generateNetNodeBreakGridThrowSeed			( 10 , "gsAct" , .1 , interpolation.averageEdge , true , true ) 
//					new generateNetNodeVectorFieldSeedCost			( 10 , layoutSeed.allNode, interpolation.sumVectors , -1 , true , true )
//					new generateNetNodeVectorFieldSplitSeedProb		( 5 , layoutSeed.random, interpolation.sumVectors , true , true, 0.2 , 90 , true ) 
					new generateNetNodeVectorFieldSplitSeedProb_02	( 4 , layoutSeed.allNode , interpolation.sumVectors , true , true , .2 , 45 , true , 5 ) 
			) ;
	

	protected static generateNetEdge generateNetEdge = 	new generateNetEdge (			
//					new generateNetEdgeNear( 2 , whichNode.all )
//					new generateNetEdgeInRadiusFather 	( genEdgeType.onlyFather )
//					new generateNetEdgeInRadiusFather_02 ( genEdgeType.fatherAndNodeInRadius , .5 )
					new generateNetEdgeDelaunay_02 ( netGraph , delGraph , false , 0.5 )
			) ;
	
	protected static vectorField vectorField = new vectorField( gsGraph , "gsInh" , vectorFieldType.spatial  ) ;
	
	protected static dynamicSymplify dynamicSymplify = new dynamicSymplify( true , netGraph , seedGraph , .1 , simplifyType.kNearestNeighbors ) ; 
	
	protected static topologyGraph delaunayGraph = new topologyGraph(netGraph, topologyGraphType .delaunay , false ) ;
	
// RUN SIMULATION -----------------------------------------------------------------------------------------------------------------------------------		
	public static void main(String[] args) throws IOException, InterruptedException 	{	
	
		delaunayGraph.setParameters();
		
		dynamicSymplify.setParameters_Pivot( true , 0.5 );
		
		// setup handle name file 
		handle = new handleNameFile( 
			/* handle file 					*/ true , 
			getFolder() ,
			/* create new folder ? 			*/ true ,
			/* manual name file-no in main 	*/ " "
			);		

		// setup type RD
		setRdType ( RdmType.holes );			
		
		// SETUP START VALUES LAYER GS
		gsAlgo values = new gsAlgo( 	
			/* enum reaction , diffusion ext		*/	gsAlgo.reactionType.ai2 , gsAlgo.diffusionType.weight , gsAlgo.extType.gsModel , 
			/* Da 									*/	0.2,			
			/* Di 									*/	0.1, 		
			/* feed									*/	feed , 	
			/* kill 								*/	kill ,		
			/* HandleNaN , setIfNaN 				*/	false , 1E-5 , 			/* if true, set default value when act or inh is over NaN  */
			/* handleMinMaxVal , minVal , maxVal 	*/	false , 1E-5 , 1 ) ; 	/* if true, set value for values over the range */
  
		// create path in order to stored all dgs files
		String pathStepNet  = handle.getPathFile(typeFile.stepNet, false , getFolder()) ; 				//	System.out.println("pathStepNet " + pathStepNet);		
		String pathStepSeed = handle.getPathFile(typeFile.stepSeed, false , getFolder()) ;	 			//	System.out.println("pathStepSeed " + pathStepSeed);
		String pathStepGs   = handle.getPathFile(typeFile.stepGs,  true , getFolder()) ;				//	System.out.println("pathStepGs " + pathStepGs);		
		String pathStartNet = handle.getPathFile(typeFile.startNet, true , getFolder()) ;				//	System.out.println("pathStartNet " + pathStartNet);
		String pathStartGs  = handle.getPathFile(typeFile.startGs, true , getFolder())	;				//	System.out.println("pathStartGs " + pathStartGs);
		String pathStartVec = handle.getPathFile(typeFile.startVec, true , getFolder()) ;				//	System.out.println("pathStartVec " + pathStartGs);
		String pathStepVec 	= handle.getPathFile(typeFile.stepVec, true , getFolder()) ;
		
// GENERATE LAYER GS --------------------------------------------------------------------------------------------------------------------------------		
		// create new layer gs
		gsLayer.createLayer ( 
				/* set coordinate 			*/ 	false , 
				/* set default Attribute	*/ 	true , 
				/* store results in folder? */	doStoreStartGs ,
				/* store gs values			*/  true ) ;
		
		// Setup distribution of morphogens
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
		netLayer.createLayer ( 
				/* bol 		createMeanPoint	= 	chose if we have an initial node (or a small graph ) befor starting simulation		*/ true , 
				/* enum		meanPointPlace	=	define were are the mean point of started net graph ( center , border , random )	*/ meanPointPlace.center ,
				/* bol		setSeedMorp		= 	if true, add a fixed value for act and inh only in node in netGraph 				*/ true ,
				/* double	seedAct			=	act value for seed node																*/ 1 , 	// 0.5
				/* double	seedInh			=	inh value for seed node	 															*/ 1 , 	// 0.25 
				/* bol		setSeedMorpInGs	=	set act and inh of netGraph in gsGraph												*/ true ,
				/* bol		storedDGS		= 	if true , create a dgs file of started graph										*/ doStoreStartNet
				);
		
		sizeGridEdge = Math.pow( gsGraph.getNodeCount() , 0.5 ) - 1 ;
		
		// set vector field parmeters
		vectorField.setParameters( 
				/* name graph of vector field 		*/	vecGraph , 
				/* radius							*/	10 ,		// not yet implem 
				/* neigborh to compute each vector	*/	vfNeig.onlyNeig, 
				/* tipe weigth of distance			*/	weigthDist.inverseSquareWeigthed 
				);
		
		// create layer od vector Field
		vectorField.createLayer(gsGraph, vecGraph, doStoreStartVec);					//	System.out.println(vecGraph.getNodeCount());

//		generateNetEdge.setParameters_Pivot ( true , 0.2 );		
	
// RUN SIMULATION -----------------------------------------------------------------------------------------------------------------------------------			
		simulation.runSim( 
			/* bol		runSim																					*/	true,
			/* int 		stopSim 		= Max step to stop simulation , 										*/ stopSim ,
			/* bol		printMorp		= print mapMorp1 ,														*/ false ,
			/* bol		genNode			= generate nodes in layer net											*/ true ,
			/* bol		genEdge			= generate edges in layer net											*/ true ,
			/* bol 		run vec																					*/ true ,
			/* bol 		run delaunay 																			*/ true ,
			/* bol		storedDgsStep	= if true, export the gsGraph in .dgs format at each step 				*/ doStoreStepGs ,
			/* string 	path to store step gs file 																*/ pathStepGs ,
		 	/* bol		storedDgsStep	= if true, export the netGraph in .dgs format at each step 				*/ doStoreStepNet ,
		 	/* string 	path to store step net file 															*/ pathStepNet, 
		 	/* bol 		store vec step ? 																		*/ doStoreStepVec ,
		 	/* string 	path to store step vec file																*/ pathStepVec ,
		 	/* bol		store start seed graph ?																*/ doStoreStartSeed ,
		 	/* bol		store step seed graph ?																	*/ doStoreStepSeed, 
		 	/* string 	pahh to store step graph 																*/ pathStepSeed 
		 	);
		
		
		//get seedAlive
	//	int seedAlive = getSeedAlive(false);
//		for ( Node n : netGraph.getEachNode()) {			Point p = n.getAttribute("point") ;		System.out.println(p);	}
		
	//	ArrayList listIdNetSeedGrad = getListIdWithAttribute( false , netGraph, "seedGrad");
		printNodeSetAttribute(false , gsGraph) ;
		printEdgeSetAttribute(false , netGraph) ;
		
//-------------------------------------------------------------------------------------------------------------------------------		
		// VISUALIZATION 

		try {
			// setup viz gsGraph
			System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
			
			// setup viz netGraph
			handleVizStype netViz = new handleVizStype( netGraph ,stylesheet.manual , "merge", 1) ;
			netViz.setupIdViz(true , netGraph, 10 , "black");
			netViz.setupDefaultParam (netGraph, "black", "black", 6 , 0.5 );
			netViz.setupVizBooleanAtr(true, netGraph, "black", "red" , false , false ) ;
			netViz.setupFixScaleManual(true  , netGraph, sizeGridEdge , 0);
			
			//  setup viz gsGraph
			handleVizStype gsViz = new handleVizStype( gsGraph ,stylesheet.viz10Color , "gsInh", 1) ;	
			gsViz.setupDefaultParam (gsGraph, "red", "white", 5 , 0.01 );
			gsViz.setupIdViz(false, gsGraph, 10 , "black");
			gsViz.setupViz(true, true, palette.red);
			
			//  setup viz vecGraph
			handleVizStype vecViz = new handleVizStype( vecGraph ,stylesheet.manual , "seedGrad", 1) ;
			vecViz.setupIdViz(false, vecGraph, 4 , "black");
			vecViz.setupDefaultParam (vecGraph, "black", "black", 3 , 1 );
			vecViz.setupVizBooleanAtr(true, vecGraph, "black", "black" , true , false ) ;
			vecViz.setupFixScaleManual(true , vecGraph, sizeGridEdge , 0);
			
			// setup viz seed graph 
			handleVizStype seedViz = new handleVizStype( netGraph ,stylesheet.manual , "seedGrad", 1)  ; 
			seedViz.setupIdViz(false, seedGraph, 10 , "black");
			seedViz.setupDefaultParam (seedGraph, "black", "black", 4 , .01);
			seedViz.setupVizBooleanAtr(false , seedGraph, "black", "red" , false , false ) ;
			seedViz.setupFixScaleManual( true , seedGraph, sizeGridEdge , 0);
			
			// setup viz delaunay graph 
			handleVizStype delViz = new handleVizStype( delGraph ,stylesheet.manual , "seedGrad", 1)  ; 
			delViz.setupIdViz(false, delGraph , 10 , "black");
			delViz.setupDefaultParam (delGraph , "black", "black", 4 , .01);
			delViz.setupVizBooleanAtr(false , delGraph , "black", "red" , false , false ) ;
			delViz.setupFixScaleManual( true , delGraph , sizeGridEdge , 0);
			
//			gsGraph.display(false);
			netGraph.display(false);
			vecGraph.display(false);
//			seedGraph.display(false);
		//	delGraph.display(false) ;
		}
		catch (java.lang.ArrayIndexOutOfBoundsException e) {
			System.out.println(e.getMessage() ) ;
		}

	}
	
// PRIVATE METHODS ----------------------------------------------------------------------------------------------------------------------------------
	// PRINT METHODS --------------------------------------------------------------------------------------------------------------------------------
	protected static void printNodeSetAttribute ( boolean print, Graph graph ) {
	
		if ( print )	
			for ( Node n : graph.getEachNode() ) 
				System.out.println(n.getId() + " " + n.getAttributeKeySet());
	}
	
	protected static void printEdgeSetAttribute (boolean print, Graph graph ) {
		
		if ( print )	
			for ( Edge e : graph.getEachEdge() )
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
			case chaos :				{ feed = 0.026 ; kill = 0.051 ; } 
										break ;
			case spotsAndLoops :		{ feed = 0.018 ; kill = 0.051 ; } 
										break ;
			case worms :				{ feed = 0.078 ; kill = 0.061 ; } 
										break ;
	
				
		}
		
	}

// GET METHODS --------------------------------------------------------------------------------------------------------------------------------------
	public static layerNet getNetLayer() 		{ return netLayer;	}
	public static int getStopSim() 				{ return stopSim ; } 
	public static handleNameFile getHandle() 	{ return handle; }

	public static String getPathStartVec() {
		return pathStartVec;
	}

	public static void setPathStartVec(String pathStartVec) {
		main.pathStartVec = pathStartVec;
	}

	public static String getFolder() {
		return folder;
	}

	public static void setFolder(String folder) {
		main.folder = folder;
	}

	public static Graph getDelaunayGraph () {
		return delGraph;
	}
	
	public static Graph getSeedGraph() {
		return seedGraph;
	}
	
	public static Graph getVecGraph (){
		return vecGraph;
	}

	public static void setSeedGraph(Graph seedGraph) {
		main.seedGraph = seedGraph;
	}
}


