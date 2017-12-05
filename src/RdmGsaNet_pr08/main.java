package RdmGsaNet_pr08;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

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
	
	public static String nameFileExp ; 
	public static String dossierExp ; 
	
	/* create reaction diffusion layer ( gs = Gray Scott )
	* setupGsGrid 	->	int size		=	graph size , 
	* 					enum gsGridType	=	set type of grid ( degree 4 or 8 ) 
	*/
	static layerGs gsLayer = new layerGs(new setupGsGrid( 200 , setupGsInter.gsGridType.grid8 ) ) ;
	
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
		
	public static void main(String[] args) throws IOException {	

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
			/*	gsAlgo ( 	enum	reactionType 
			* 				enum	diffusionType 
			* 				enum	extType 
			* 				Da , Di , feed , kill 
			* 				bol 	HandleNaN		= if true, set default value when act or inh is over NaN 
			* 				double	setIfNaN		= defalt value if act or inh is NaN 
			* 				bol		handleMinMaxVal	= if true, set value for values over the range
			*  				double	minVal			= default value if morph < minVal, set minVal
			*  				double	mmaxVal			= default value if morph > maxVal, set maxVal
			*/
		gsAlgo values = new gsAlgo( gsAlgo.reactionType.ai2 , gsAlgo.diffusionType.fick , gsAlgo.extType.gsModel , 
				/* Da 	*/			0.6,			
				/* Di 	*/			0.2, 		
				/* feed */			0.05 , 	
				/* kill */			0.05 ,		
									true , 1E-5 ,
									true , 1E-5 , 1 ) ;
//-------------------------------------------------------------------------------------------------------------------------------
// EXPORT VALUES	
		// export graph at each step
		nameFileExp =	"export"	+
													"_Sim_"		+ simulation.getStopSim()   +
													"_Size_"	+ setupGsGrid.getGsGridSize() +
													"_Da_"		+ gsAlgo.getDa() +
													"_Di_" 		+ gsAlgo.getDi() + 
													"_F_" 		+ gsAlgo.getFeed() +
													"_K_" 		+ gsAlgo.getKill()  ;
				
				
		dossierExp	= "D:\\Dropbox\\Dropbox\\JAVA\\RdmGsaNet_Export\\graph\\export_03\\";
		

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
		netLayer.createLayer ( true , layerNet.meanPointPlace.center , true , 1 , 1 , false); 
 		
//		for ( Node nNet : netGraph.getEachNode() )//	 {		System.out.println(n.getId());			double act = n.getAttribute("seedAct");			double inh = n.getAttribute("seedInh");//			System.out.println ( act ) ;//			System.out.println ( inh ) ;	}
//			for ( Node nGs : gsGraph.getEachNode() ) {			double inh = nGs.getAttribute("gsInh");//			System.out.println ( n.getId() + "    " + inh ) ;}
			
//-------------------------------------------------------------------------------------------------------------------------------		
		/* RUN simulation
		 * // runSim ( 	int 	stopSim 	= Max step to stop simulation , 
		 * 				bol		printMorp	= true = print mapMorp1 ,
		 * 				bol		genNode		= generate nodes in layer net
		 * 				bol		genEdge		= generate edges in layer net
		 * 				bol		gsGraphExp	= if true, export the gsGraph in .dgs format at each step 
		 *				) 	*/		
		run.runSim( 500 , false , false , false , true  );
	
//-------------------------------------------------------------------------------------------------------------------------------		
		// VISUALIZATION 

		setupViz.Viz4Color(gsGraph);
		
//		setupViz.Vizmorp(gsGraph, "gsAct");
//		setupViz.Vizmorp(gsGraph, "gsInh");

		gsGraph.display(false) ;
		
		// get images
		String folderIm = "D:\\Dropbox\\Dropbox\\JAVA\\RdmGsaNet_Export\\image\\image_03\\" ;
		String nameFileIm =	"image"	+
							"_Sim_"		+ simulation.getStopSim()   +
							"_Size_"	+ setupGsGrid.getGsGridSize() +
							"_Da_"		+ gsAlgo.getDa() +
							"_Di_" 		+ gsAlgo.getDi() + 
							"_F_" 		+ gsAlgo.getFeed() +
							"_K_" 		+ gsAlgo.getKill() +
							".png";
		
		setupViz.getImage(gsGraph, folderIm , nameFileIm );
	}
	
	public static String getNameFileExp() 	{ return nameFileExp ; }
	public static String getDossierExp () 	{ return dossierExp ; }
	
	
}
