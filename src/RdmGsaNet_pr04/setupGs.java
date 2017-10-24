package RdmGsaNet_pr04;

import java.util.Random;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

/* 
 * setup start values and methods to encapsulate the parameters. 
 * in order to set starter values in a range, in methods we proposed a test.
 */

public class setupGs {
	
	// started Gs parameters
	private static double 
		Di, Da,		// diffusion coefficients
	
		kill,		// kill rate
		feed		// feed rate
	;
	
//---------------------------------------------------------------------------------------------------------	
	// LayoutGs
	
	// random seed setup
	private static int 
		RandomSeedAct ,			// seed to create a random value if distribution of activator in Gslayout is random
		RandomSeedInh			// seed to create a random value if distribution of inhibitor in Gslayout is random
	;

	// type layout
	private static enum typeLayout { grid, random, gis }
	private static String typeLayout;
	 
	//  enumerate distribution of morhogens
	private static enum disMorp {homogeneus, random }
	private static String disMorp;
	
	// started paramethers for Gs Grid
	private static int GsGridSize;
	
	// type of grid 
	private static enum GsGridType { grid4, grid8 }
	private static String GsGridType;
	
//-----------------------------------------------------------------------------------------------------------------
	// initialization of Gs Graph
	public static Graph GsGraph = new SingleGraph("GsGraph");
	
//-----------------------------------------------------------------------------------------------------------------
	// setup type of GsAlgo
	private static enum reactionType { linear, ai2 }
	private static String reactionType;
	
	private static enum diffusionType { fick, perimeter, weigth }
	private static String diffusionType;
	
	private static int stopSim;
	
//-----------------------------------------------------------------------------------------------------------------
	// SET AND GET
		
	// diffusion
		public void setDi(double x) { testRangeParam(Di, x, 0, 1); }
		public static double getDi() { return Di; }
	
		public void setDa(double x) { testRangeParam(Da, x, 0, 1); }
		public static double getDa() { return Da; }
	
	// kill rate
		public void setKill(double x) { testRangeParam(kill, x, 0, 1); }
		public static double getKill() { return kill; }
	
	// feed rate
		public void setFeed(double x) { testRangeParam(feed, x, 0, 1); }
		public static double getFeed() { return feed; }
	
	// seed for random act and inh
		public void setRandomSeedAct (int x) { RandomSeedAct = x; }
		public static int getRandomSeedAct () { return RandomSeedAct; }
	
		public void setRandomSeedInh (int x) { RandomSeedInh = x; }
		public static int getRandomSeedInh () { return RandomSeedInh; }
	
	// type layout of Gs
		public void setTypeLayout (String x) { typeLayout = x ; 
		// test se x fa parte dell'enum
		}
		public String getTypeLayout () { return typeLayout; }
	
	// distribution of Gs
		public  void setDisMorp (String x) { disMorp = x; 
	// test se x fa parte dell'enum
		}
		public static String getDisMorp () { return disMorp; }
	
	//Grid size
		public void setGsGridSize (int x) { GsGridSize = x;
			// set max and min values, riprendere quello di start values
		}
		public static int getGsGridSize () { return GsGridSize; }
	
	//Grid type
		public void setGsGridType (String x) { GsGridType = x; 
		// test and set string
	}
		public static String getGsGridType () { return GsGridType; }
	
	//Reaction type
		public void setReactionType (String x) { reactionType = x ; }
		public static String getReactionType () { return reactionType ; }

	//Diffusion type
		public void setDiffusionType (String x) { diffusionType = x ; }
		public static String getDiffusionType () { return diffusionType ; }
		
	// Step Simulation
		public void setStopSim ( int x ) { stopSim = x ; }
		public static int getStopSim () { return stopSim ; }

//-----------------------------------------------------------------------------------------------------------
	// METHODS FOR GET AND SET
	//test and set each parameters
		public void testRangeParam(double var, double test, double min, double max) {
			if (test >= min && test <= max ) {
				var = test;	
			}
	
			else  { 
				System.out.println("value not in range"); 
			}
		}
	
	
	
	// set and test paramether encapsulate in enum
//-----------------------------------------------------------------------------------------------------------
//	OTHER METHODS
	
	// SETUP MORPHOGENS DISTRIBUTIONS	
	public static void setupDisMorp () {
		Graph graph = GsGraph;
		String DisMorp = getDisMorp ();
		
		System.out.println(DisMorp);
		
		switch (DisMorp) {		
		case "homogeneus" :
			System.out.println("distribution homogeneus");
			float x = 0;
			for ( Node n:graph.getEachNode() ) { n.setAttribute("GsAct" , x  ) ;}
			for ( Node n:graph.getEachNode() ) { n.setAttribute("GsInh" , x  ) ;}
			break;
			
		case "random" :
			System.out.println("distribution random");
			Random act = new Random(getRandomSeedAct() );
			Random inh = new Random(getRandomSeedInh() );
			for ( Node n:graph.getEachNode() ) { n.setAttribute("GsAct" , act.nextFloat()   ) ;}
			for ( Node n:graph.getEachNode() ) { n.setAttribute("GsInh" , inh.nextFloat()   ) ;}
			break;
			}
		}		
	}
	

	

