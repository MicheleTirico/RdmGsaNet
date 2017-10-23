package RdmGsaNet_pr04;

/* 
 * setup start values and methods to encapsulate the parameters. 
 * in order to set starter values in a range, in methods we proposed a test.
 */

public class setupGs   {
	
	// started Gs parameters
	private static double 
	Di, Da,		// diffusion coefficients
	
	kill,		// kill rate
	feed		// feed rate
	;
	
//---------------------------------------------------------------------------------------------------------	
	// layoutGs
	
	// random seed setup
	private static int 
	
	RandomSeedAct ,			// seed to create a random value if distribution of activator in Gslayout is random
	RandomSeedInh			// seed to create a random value if distribution of inhibitor in Gslayout is random
	;

	// type layout
	public enum typeLayout { grid, random, gis }
	private String typeLayout;
	
	//  enumerate distribution of morhogens
	private enum disMorp {homogeneus, random }
	private String disMorp;
	
	// started paramethers for Gs Grid
	private static int GsGridSize;

	
	
//-----------------------------------------------------------------------------------------------------------------
	// SET AND GET
	// diffusion
	public void setDi(double x) { testRangeParam(Di, x, 0, 1); }
	public static double getDi() { return Di; }
	
	public void setDa(double x) { testRangeParam(Da, x, 0, 1); }
	public double getDa() { return Da; }
	
	// kill rate
	public void setKill(double x) { testRangeParam(kill, x, 0, 1); }
	public double getKill() { return kill; }
	
	// feed rate
	public void setFeed(double x) { testRangeParam(feed, x, 0, 1); }
	public double getFeed() { return feed; }
	
	// seed for random act and inh
	public void setRandomSeedAct (int x) { x = RandomSeedAct; }
	public int getRandomSeedAct () { return RandomSeedAct; }
	
	public void setRandomSeedInh (int x) { x = RandomSeedInh; }
	public int getRandomSeedInh () { return RandomSeedInh; }
	
	// type layout of Gs
	public void setTypeLayout (String x) { x = typeLayout ; 
		// test se x fa parte dell'enum
	}
	public String getTypeLayout () { return typeLayout; }
	
	// distribution of Gs
	public void setDisMorp (String x) { x = disMorp; 
	// test se x fa parte dell'enum
	}
	public String getDisMorp () { return disMorp; }
	
	//Grid size
		public void setGsGridSize (int x) { x = GsGridSize;
			// set max and min values, riprendere quello di start values
		}
		 public static int getGsGridSize () { return GsGridSize; }


//-----------------------------------------------------------------------------------------------------------
	// methods 
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
//	SETUP GS LAYOUT
	
	
	
}
