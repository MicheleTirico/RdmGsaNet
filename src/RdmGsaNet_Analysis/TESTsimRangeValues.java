package RdmGsaNet_Analysis;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.graphstream.graph.Graph;

import RdmGsaNet_pr08.gsAlgo;
import RdmGsaNet_pr08.gsAlgoDiffusion;
import RdmGsaNet_pr08.layerGs;
import RdmGsaNet_pr08.layerNet;
import RdmGsaNet_pr08.setupGsGrid;
import RdmGsaNet_pr08.setupNetSeed;
import RdmGsaNet_pr08.simulation;
import RdmGsaNet_pr08.setupGsInter;
import RdmGsaNet_pr08.generateNetNode;
import RdmGsaNet_pr08.generateNetNodeThreshold;
import RdmGsaNet_pr08.generateNetEdge;
import RdmGsaNet_pr08.generateNetEdgeNear;

/* simRangeValues
 * In this class we run more than simulation. We defined the range of the paramether of RDm, the max step
 * and the folder to stored results.
 * NB: before simulation, check new classes setup in order to be sure these are the latest developed.
 */

public class TESTsimRangeValues {
	
	// start storing
	private static String fileType = ".dgs" ;
	private static String nameStartGs  ;

	private static String folderStartGs = "D:\\Dropbox\\Dropbox\\JAVA\\RdmGsaNet_Export2\\";
	private static String pathStartGs = folderStartGs + nameStartGs + fileType ;
	
	// step storing
	private static String nameStepGs ; 
	private static String folderStepGs = folderStartGs;
	private static String pathStepGs ;
	
	
	/* create reaction diffusion layer ( gs = Gray Scott )
	* setupGsGrid 	->	int size		=	graph size , 
	* 					enum gsGridType	=	set type of grid ( degree 4 or 8 )  */
	static layerGs gsLayer = new layerGs(new setupGsGrid( 5 , setupGsInter.gsGridType.grid8 ) ) ;
	
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

	public static void main(String[] args) throws IOException, InterruptedException {
	
		// set list of parameters
		ArrayList<Double> DaPar = new ArrayList<Double> (Arrays.asList(0.1)); 
		ArrayList<Double>  DiPar = new ArrayList<Double> (Arrays.asList(0.1)); 
		ArrayList<Double> feedPar = setListParRange (0, 1 , 0.1 , 2 );	//	System.out.println(DaPar);
		ArrayList<Double> killPar = setListParRange (0, 2 , 0.2 , 2 );	//	System.out.println(DaPar);
		
		for ( Double Da : DaPar  ) {
			for ( Double Di : DiPar  ) {
				for ( Double feed : feedPar  ) {
					for ( Double kill : killPar) {
						
						// set Gs values
						gsAlgo values = new gsAlgo( gsAlgo.reactionType.ai2 , gsAlgo.diffusionType.weight , gsAlgo.extType.gsModel , 
								/* Da 	*/			Da,			
								/* Di 	*/			Di, 		
								/* feed */			feed , 	
								/* kill */			kill ,		
													true , 1E-5 ,
													true , 1E-5 , 1 ) ;
						
						nameStartGs  =	"layerGsStart"	+
								"_Size_"		+ setupGsGrid.getGsGridSize() +
								"_Da_"			+ gsAlgo.getDa() +
								"_Di_" 			+ gsAlgo.getDi() + 
								"_F_" 			+ gsAlgo.getFeed() +
								"_K_" 			+ gsAlgo.getKill()  ;
					
						// CREATE GS GRAPH
						gsLayer.createLayer ( false , true , false ) ;
						
						// SETUP DISMORP
						gsLayer.setupDisMorp(setupGsInter.disMorpType.homo , 12 , 34 , 1 , 0 );

						// SETUP DIFFUSION
						gsAlgoDiffusion.setLaplacianMatrix ( 0.2, 0.05 ) ;
						gsAlgoDiffusion.setWeightType ( gsAlgoDiffusion.weightType.matrix );
						
						// CREATE LAYER NET
						netLayer.createLayer ( true , layerNet.meanPointPlace.center , true , 1 , 1 , true , false ); 
				 		
						nameStepGs =	"layerGsStep"	+
								"_Size_"		+ setupGsGrid.getGsGridSize() +
								"_Da_"			+ Da +
								"_Di_" 			+ Di + 
								"_F_" 			+ feed +
								"_K_" 			+ kill ;
						pathStepGs = folderStepGs + nameStepGs + fileType ;
						
						// RUN simulation
						run.runSim( 1 , false , false , false , false , pathStepGs, false, fileType );
					}
				}	
			}
		}
	}
		
// PRIVATE METHODS --------------------------------------------------------------------------------------------------------------------------
	
	// create list of parameters in defined range 
	private static ArrayList<Double> setListParRange ( double min , double max , double inc , int round) {
	
		ArrayList<Double> arrList = new ArrayList<Double>() ;
		arrList.add(min);
		for ( double y = min + inc ; y <= max ;  y+= inc ) {
		
			BigDecimal val = new BigDecimal(y); 
			val =  val.setScale(round, BigDecimal.ROUND_HALF_UP); 
				
			arrList.add(val.doubleValue());
		}
		return arrList ;
	}

	
	
}
