package RdmGsaNet_pr08;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.stream.file.FileSinkDGS;
import org.graphstream.stream.file.FileSinkImages.OutputType;

import RdmGsaNetAlgo.morpAnalysis;
import RdmGsaNetAlgo.morpAnalysis.spatialAutoCor;
import RdmGsaNetAlgo.morpSpatialAutoCor;
import RdmGsaNetExport.expGraph;
import RdmGsaNetViz.setupViz;
import RdmGsaNetViz.testViz;

public class main {
	
	private static Map<Double , Graph > mapStepNetGraph = simulation.getMapStepNetGraph() ;
	private static Map<String, ArrayList<Double >> mapMorp0 = simulation.getmapMorp0() ;
	private static Map<String, ArrayList<Double >> mapMorp1 = simulation.getmapMorp1() ;
	
	// start storing
	private static String fileType = ".dgs" ;
	private static String nameStartGs  ;

	private static String folderStartGs = "C:\\Users\\frenz\\ownCloud\\RdmGsaNet_exp\\dgs\\Da_0.2_Di_0.1_02\\";
	private static String pathStartGs = folderStartGs + nameStartGs ;
	
	// step storing
	private static String nameStepGs ; 
	private static String folderStepGs = folderStartGs;
	private static String pathStepGs ;
	
	// private initial configuration 
	
	
	/* create reaction diffusion layer ( gs = Gray Scott )
	* setupGsGrid 	->	int size		=	graph size , 
	* 					enum gsGridType	=	set type of grid ( degree 4 or 8 )  */
	static layerGs gsLayer = new layerGs(new setupGsGrid( 2 , setupGsInter.gsGridType.grid8 ) ) ;
	
	// generate layer of Net
//	static layerNet netLayer = new layerNet (new setupNetSmallGrid ( setupNetSmallGrid.typeGrid.grid8) );	
	static layerNet netLayer = new layerNet (new setupNetSeed());
	
	// call gs graph ( to test code , not important )
	static Graph gsGraph = layerGs.getGraph() ;
	
	// call NET graph ( to test code , not important )
	static Graph netGraph = layerNet.getGraph() ;
	
	// Initialization object simulation, composed by gsAlgo and growthNet
	static simulation run = new simulation() ;
	
	// initialization of rules to evolving Net	
		// generateNetNodeThreshold ( threshold for activator, threshold for inhibitor )
	static generateNetNode generateNetNode = new generateNetNode (new generateNetNodeGradient(1)) ;
	
		// generateNetEdgeNear ( max radius of search )
	static generateNetEdge generateNetEdge = new generateNetEdge (new generateNetEdgeNear(0.5 , generateNetEdgeNear.whichNode.all )) ;
		
	public static void main(String[] args) throws IOException, InterruptedException 	{	
		
		
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
			/* feed */			0.062 , 	
			/* kill */			0.061 ,		
								true , 1E-5 ,
								true , 1E-5 , 1 ) ;
  
//-------------------------------------------------------------------------------------------------------------------------------		
	// GENERATE LAYER GS
		nameStartGs  =	"layerGsStart"	+
					"_Size_"		+ setupGsGrid.getGsGridSize() +
					"_Da_"			+ gsAlgo.getDa() +
					"_Di_" 			+ gsAlgo.getDi() + 
					"_F_" 			+ gsAlgo.getFeed() +
					"_K_" 			+ gsAlgo.getKill()  ;
		
	/* CREATE GS GRAPH
	 *  method to generate the graph gs
	 *  createLayer = 	bol		setCoordinate	=
	 *  				bol		setDefaultAtr 	=
	 *  				bol		storedDGS		= if true , create a dgs file of started graph
	 */
		gsLayer.createLayer ( false , true , false ) ;
		
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
		netLayer.createLayer ( true , layerNet.meanPointPlace.center , true , 1 , 1 , true , false ); 
		
		nameStepGs =	"layerGsStep"	+
				"_Size_"		+ setupGsGrid.getGsGridSize() +
				"_Da_"			+ gsAlgo.getDa() +
				"_Di_" 			+ gsAlgo.getDi() + 
				"_F_" 			+ gsAlgo.getFeed() +
				"_K_" 			+ gsAlgo.getKill()  ;
		pathStepGs = folderStepGs + nameStepGs + fileType ;
				
//-------------------------------------------------------------------------------------------------------------------------------		
		/* RUN simulation
		 * // runSim ( 	int 	stopSim 		= Max step to stop simulation , 
		 * 				bol		printMorp		= true = print mapMorp1 ,
		 * 				bol		genNode			= generate nodes in layer net
		 * 				bol		genEdge			= generate edges in layer net
		 * 				bol		storedDgsStep	= if true, export the gsGraph in .dgs format at each step 
		 *) 	*/		
		run.runSim( 5 , false , true , false , false , pathStepGs );	//		

//		for ( Node n : gsGraph.getEachNode()) { System.out.println(n.getId() + " " +"gsAct " + n.getAttribute("gsAct") +  " gsInh " + n.getAttribute("gsInh"));}
		for ( Node n : gsGraph.getEachNode()) { 
//			System.out.println(n.getId() + n.getAttributeKeySet());
//			System.out.println(n.getId() + " seedGrad " + n.getAttribute("seedGrad") + " oldSeedGrad " + n.getAttribute("oldSeedGrad") );	
		int con = n.getAttribute("con");
//		System.out.println(con);
		}

		
//		for ( Node n : gsGraph.getEachNode()) {					System.out.println(n.getId() + n.getAttributeKeySet());		}
//		for ( Node n : netGraph.getEachNode()) {					System.out.println(n.getId() + n.getAttributeKeySet() ) ; }
//			System.out.println(n.getId() + " " +"gsAct " + n.getAttribute("seedAct") +  " gsInh " + n.getAttribute("seedInh"));
//		for ( Edge e : netGraph.getEachEdge()) { System.out.println(e.getId()) ;}
		
			
//-------------------------------------------------------------------------------------------------------------------------------		
		// VISUALIZATION 

		setupViz.Viz4Color(gsGraph);

		setupViz.Vizmorp(gsGraph, "gsAct");
//		setupViz.Vizmorp(gsGraph, "gsInh");

//		gsGraph.display(false) ;
//		netGraph.display(false) ;
		
		// get images
		String folderIm = "D:\\Dropbox\\Dropbox\\JAVA\\RdmGsaNet_Export\\image\\image_04\\" ;
		String nameFileIm =	"image"	+
							"_Sim_"		+ simulation.getStopSim()   +
							"_Size_"	+ setupGsGrid.getGsGridSize() +
							"_Da_"		+ gsAlgo.getDa() +
							"_Di_" 		+ gsAlgo.getDi() + 
							"_F_" 		+ gsAlgo.getFeed() +
							"_K_" 		+ gsAlgo.getKill() +
							".png";
		
//		setupViz.getImage(gsGraph, folderIm , nameFileIm );
	}

	
//-------------------------------------------------------------------------------------------------------------------------------		

	public static String getFileType () 		{ return fileType ; }
	public static String getNameStartGs () 		{ return nameStartGs ; }
	public static String getFolderStartGs () 	{ return folderStartGs ; }
	public static String getPathStartGs () 		{ return pathStartGs ; }
	public static String getNameStepGs () 		{ return nameStepGs ; }
	public static layerNet getNetLayer() 		{return netLayer;	}

}