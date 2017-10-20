package RdmGsaNet_pr03;

/* 
 * setup start values and methods to encapsulate the parameters. 
 * in order to set starter values in a range, in methods we proposed a test.
 */

public class startValuesGs implements startValues  {
	
	// started Gs parameters
	private static double 
	Di, Da,		// diffusion coefficients
	
	kill,		// kill rate
	feed		// feed rate
	;
	
	// layoutGs
	private static int 
	layoutGsGridSize ,		// size of grid of layout Gs
	RandomSeedAct ,			// seed to create a random value if distribution of activator in Gslayout is random
	RandomSeedInh			// seed to create a random value if distribution of inhibitor in Gslayout is random
	;
	
	
	//-----------------------------------------------------------------------------------------------------------------
	// set and get diffusion
	public void setDi(double x) { testRangeParam(Di, x, 0, 1); }
	
	public double getDi() { return Di; }
	
	public void setDa(double x) { testRangeParam(Da, x, 0, 1); }
	
	public double getDa() { return Da; }
	
	
	// set and get kill rate
	public void setKill(double x) { testRangeParam(kill, x, 0, 1); }
	
	public double getKill() { return kill; }
	
	
	// set and get feed rate
	public void setFeed(double x) { testRangeParam(feed, x, 0, 1); }
	
	public double getFeed() { return feed; }
	
	// get and set Grid size
	public void setGsGridSize (int x) { x = layoutGsGridSize; }
	
	public int getGsGridSize () { return layoutGsGridSize; }

	
	// get and set seed for random act and inh
	public void setRandomSeedAct (int x) { x = RandomSeedAct; }
	
	public int getRandomSeedAct () { return RandomSeedAct; }
	
	public void setRandomSeedInh (int x) { x = RandomSeedInh; }
	
	public int getRandomSeedInh () { return RandomSeedInh; }
	
	//-----------------------------------------------------------------------------------------------------------------
	// methods 
	//test and set each parameters and 
	public void testRangeParam(double var, double test, double min, double max) {
		if (test >= min && test <= max ) {
			var = test;	
		}
	
		else  { 
			System.out.println("value not in range"); 
		}
	}
	
}
