package RdmGsaNet_generateGraph;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import RdmGsaNet_mainSim.layerGs;
import RdmGsaNet_mainSim.layerNet;
import RdmGsaNet_mainSim.main;

public class generateNetNodeBreakGrid extends main {
	
	// COSTANTS	
	protected static int numberMaxSeed ; 
	protected String morp;
	
	public enum interpolation { averageEdge , averageDist } 
	public interpolation typeInterpolation ;
	protected double sizeGridEdge ;
	
	// probability costants 
	static double  prob = 0 ;
	protected static String setupNet = layerNet.getLayout();

	protected static Graph netGraph = layerNet.getGraph(),
							gsGraph = layerGs.getGraph();
	
	public generateNetNodeBreakGrid ( int numberMaxSeed , interpolation typeInterpolation  ) {
		this.numberMaxSeed = numberMaxSeed ;
		this.typeInterpolation  = typeInterpolation  ;
		
		
		
	}

// COMMON METHODS FOR ALL CLASS WITH GRADIENT APPROACH ----------------------------------------------------------------------------------------------
	

	protected static void setStartSeed ( Graph graph , int step ,  int numberMaxAttribute , String attribute ) {

		if ( step != 1 )
			return ;
		
		int nodeCount = netGraph.getNodeCount();
		
		if ( numberMaxSeed > nodeCount )
			numberMaxSeed = nodeCount ;
		
		for ( int x = 0 ; x < numberMaxAttribute ; x++ ) {
			graph.getNode(x).addAttribute(attribute, 1);
		}
					
		
	}

}
