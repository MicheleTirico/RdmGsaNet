package RdmGsaNet_generateGraph;

import java.util.ArrayList;
import java.util.Iterator;

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
	
	public generateNetNodeBreakGrid ( int numberMaxSeed , String morp, interpolation typeInterpolation  ) {
		this.numberMaxSeed = numberMaxSeed ;
		this.morp = morp ;
		this.typeInterpolation  = typeInterpolation  ;
		
		
		
	}

	

// COMMON METHODS FOR ALL CLASS WITH GRADIENT APPROACH ----------------------------------------------------------------------------------------------	
	
	// handle create new node	
	protected void handleNewNodeCreation ( Graph graph , String idCouldAdded , Node nodeSeed , double xNewNode , double yNewNode ) {
		
		Node nodeCouldAdded = null ;
		// there isn't node
		try {
			netGraph.addNode(idCouldAdded);
			nodeCouldAdded = netGraph.getNode(idCouldAdded); 			//	System.out.println(idCouldAdded);
			nodeCouldAdded.addAttribute("seedGrad", 1);
			nodeSeed.setAttribute("seedGrad", 0 );
			
			// set coordinate
			nodeCouldAdded.setAttribute( "xyz", xNewNode , yNewNode, 0 );	
			}
		
		// if node already exist 
		catch (org.graphstream.graph.IdAlreadyInUseException e) { 		//System.out.println(e.getMessage());
			nodeCouldAdded = netGraph.getNode(idCouldAdded); 			//	System.out.println(idCouldAdded);
			nodeCouldAdded.addAttribute("seedGrad", 0 );
			nodeSeed.setAttribute("seedGrad", 1);
		}
	}
	
	// setup first step of simulation
	protected static void setStartSeed ( Graph graph , int step ,  int numberMaxAttribute , String attribute ) {

		// exit method
		if ( step != 1 )					return ;
		
		int nodeCount = netGraph.getNodeCount();
		
		if ( numberMaxSeed > nodeCount )	numberMaxSeed = nodeCount ;
		
		for ( int x = 0 ; x < numberMaxAttribute ; x++ ) 
			graph.getNode(x).addAttribute(attribute, 1);	
	}
	
	// handle listNeigGsStrSeed ( and not seed )
	protected static void handleListNeigGsSeed ( Node nodeSeed , ArrayList<String> listNeigSeed , ArrayList<String> listNeigNotSeed ) {
			
		Iterator<Node> iter = nodeSeed.getNeighborNodeIterator() ;		
		while (iter.hasNext()) {
				 
			Node neig = iter.next() ;
			int neigValAttr = neig.getAttribute("seedGrad");
				
			if (neigValAttr == 1 )
				listNeigSeed.add(neig.getId());
			else if ( neigValAttr == 0 ) 
				listNeigNotSeed.add(neig.getId()) ;
		}
	}		
}
