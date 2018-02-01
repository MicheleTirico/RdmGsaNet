package RdmGsaNet_pr09;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNet_pr08.simulation;



public class generateNetNodeGradient  {

	// COSTANTS
	protected  enum splitSeed { onlyOneRandom , splitMax , splitMaxThreshold , splitProbability }
	protected splitSeed type ;
	
	protected enum layoutSeed { center , random , allNode }
	protected layoutSeed setLayoutSeed; 
	
	protected int numberMaxSeed ; 
	protected String morp;
	
	// probability costants 
	static double  prob = 0 ;
	protected static String setupNet = layerNet.getLayout();

	protected static Graph netGraph = layerNet.getGraph(),
							gsGraph = layerGs.getGraph();	

	
// COMMON METHODS FOR ALL CLASS WITH GRADIENT APPROACH ----------------------------------------------------------------------------------------------
	
	// create list (node) of neigbohord with max value of morphogen
	protected ArrayList<Node> createListNodeMaxNeig (Graph graph , Node n , String morp ) {
		
		Node nGs = gsGraph.getNode(n.getId());
		double val = 0 ;

		ArrayList<Node> listIdNeigValMax = new ArrayList<Node>();
		
		Iterator<Node> iter = nGs.getNeighborNodeIterator() ;		//	System.out.println("id " + n);
		
		while ( iter.hasNext()) {				
			
			Node neig = iter.next() ;			
			try {
				double morpVal = neig.getAttribute(morp);	
				if ( morpVal  >= val  ) {	
					val = morpVal; 
					listIdNeigValMax.add( neig ) ;
				}
			} catch (java.lang.NullPointerException e) {
				// e.printStackTrace();
				continue ;
			}
		}	
		return listIdNeigValMax;
	}
	
	// create list(id string) of neigbohord with max value of morphogen
	protected ArrayList<String> createListMaxNeig (Graph graph , Node n , String morp ) {
		
		Node nGs = gsGraph.getNode(n.getId());
		double val = 0 ;
		int big = 10000 ;
		ArrayList<String> listIdNeigValMax = new ArrayList<String>();
		
		// get max value of act of neigbords ( in gsGraph)
		Iterator<Node> iter = nGs.getNeighborNodeIterator() ;		//	System.out.println("id " + n);
		
		while ( iter.hasNext()) {				
			
			Node neig = iter.next() ;			
			try {
			double morpVal = neig.getAttribute(morp);	
			if ( morpVal * big >= val * big ) {	
				val = morpVal; 
				listIdNeigValMax.add(neig.getId() ) ;
			}
			} catch (java.lang.NullPointerException e) {
				// e.printStackTrace();
				continue ;
			}
		}	
		return listIdNeigValMax;
	}
	
	protected String getNodeGreater ( String attribute , ArrayList<Node> listNeig ) {
		String winner = null ;
	
		double valWin = 0 ;
		
		for ( Node n : listNeig) {
			double valTest = n.getAttribute(attribute);
			if ( valTest >= valWin ) {
				valWin = valTest;
				winner = n.getId();
			}
				
		}
		
		
		
		return winner ;
	}
// SET LAYOUT SEED NODES ----------------------------------------------------------------------------------------------------------------------------
	protected void setSeedNodes ( int step , int numberMaxSeed , layoutSeed setLayoutSeed ) {

		if ( step != 1 )
			return ;
		
		int nodeCount = netGraph.getNodeCount();
		
		if ( numberMaxSeed > nodeCount )
			numberMaxSeed = nodeCount ;
		
		switch (setLayoutSeed) {
		case allNode:
			setLayoutSeedAllNode();
			break;
			
		case center :
			setLayoutSeedCenter();
			break;

		case random :
			setLayoutSeedRandom(numberMaxSeed);
			break;
		}

	}
	
	// set layout seed all node
	private void setLayoutSeedAllNode ( ) {
		for ( Node n : netGraph.getEachNode() ) 
			n.addAttribute("seedGrad", 1);	
	}
	
	// set layout seed only center
	private void setLayoutSeedCenter ( ) {
		String idNodeCenter = gsAlgoToolkit.getCenterGrid(gsGraph);
		Node idNode = netGraph.getNode(idNodeCenter);
		idNode.addAttribute("seedGrad", 1 );
	}
	
	// set layout seed random ( aggiustare perche non é proprio random )
	private void setLayoutSeedRandom ( int numberMaxSeed ) {	
		int nodeCount = netGraph.getNodeCount();
		int numberNewSeed = 0 ;
		
		for ( Node n : netGraph.getEachNode() ) {
			int isSeed =  n.getAttribute("seedGrad") ;
			if ( isSeed != 1 ) {
				n.addAttribute("seedGrad", 1);
				numberNewSeed++;	
			}
			if ( numberNewSeed >= numberMaxSeed )
				return ;
		}	
	}

// --------------------------------------------------------------------------------------




//GET METHODS ---------------------------------------------------------------------------------------------------------------------------------------
	public static double getProb() 		{ return prob; }

	
}
