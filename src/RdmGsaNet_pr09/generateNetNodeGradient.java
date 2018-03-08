package RdmGsaNet_pr09;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import RdmGsaNetAlgo.gsAlgoToolkit;

public class generateNetNodeGradient  {

	// COSTANTS
	protected  enum splitSeed { onlyOneRandom , splitMax , splitMaxThreshold , splitProbability }
	protected splitSeed type ;
	
	protected boolean stillAlive;
	
	protected enum layoutSeed { center , random , allNode }
	protected layoutSeed setLayoutSeed; 
	
	protected enum rule { random , maxValue , minValue }
	protected rule rule ;
	
	protected int numberMaxSeed ; 
	protected String morp;
	
	// probability costants 
	static double  prob = 0 ;
	protected static String setupNet = layerNet.getLayout();

	protected static Graph netGraph = layerNet.getGraph(),
							gsGraph = layerGs.getGraph();	

	
// COMMON METHODS FOR ALL CLASS WITH GRADIENT APPROACH ----------------------------------------------------------------------------------------------
	
	// create list (node) of neigbohord with max value of morphogen
	protected ArrayList<Node> createListNodeMaxNeig (Graph graph , Node n , String morp  ) {
		
		Node nGs = graph.getNode(n.getId());
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
protected ArrayList<String> createListNodeMaxNeigStr (Graph graph , Node n , String morp  ) {
		
		Node nGs = graph.getNode(n.getId());
		double val = 0 ;

		ArrayList<String> listIdNeigValMax = new ArrayList<String>();
		
		Iterator<Node> iter = nGs.getNeighborNodeIterator() ;		//	System.out.println("id " + n);
		
		while ( iter.hasNext()) {				
			
			Node neig = iter.next() ;			
			try {
				double morpVal = neig.getAttribute(morp);	
				if ( morpVal  >= val  ) {	
					val = morpVal; 
					listIdNeigValMax.add( neig.getId() ) ;
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
	
	// get node with the greater value of attribute in list of nodes
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
	
	protected String getNodeSmallest ( String attribute , ArrayList<Node> listNeig ) {
		
		String winner = null ;
		double valWin = 10 ;	
		for ( Node n : listNeig) {
			double valTest = n.getAttribute(attribute);
			if ( valTest <= valWin ) {
				valWin = valTest;
				winner = n.getId();
			}			
		}
		return winner ;
	}
	
	// get random node from a list of nodes 
	protected String getRandomNode ( ArrayList<Node> listNeig ) {
		
		Random randomGenerator = new Random( ) ;
		int index = randomGenerator.nextInt(listNeig.size());	
		
		return  listNeig.get(index).getId();	
	}
	
protected String getRandomNodeId ( ArrayList<String> listNeig ) {
		
		Random randomGenerator = new Random( ) ;
		int index = randomGenerator.nextInt(listNeig.size());	
		
		return  listNeig.get(index);	
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
			setLayoutSeedAllNode();					break;
			
		case center :
			setLayoutSeedCenter();					break;

		case random :
			setLayoutSeedRandom(numberMaxSeed);		break;
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

	protected String getIdCouldAdded ( rule rule , ArrayList<String> list , int numberOfTry ) {
	
		String idCouldAdded = null ; 
		
		switch (rule) {
		case random: {
			idCouldAdded = list.get(numberOfTry);
			}break;

		case maxValue: {
			System.out.println("not implem");
			/*
			ArrayList<String> listIdNodeSorted = gsAlgoToolkit.getSortedListNodeAtr ( listNeigNode, morp );	
			idCouldAdded = listIdNodeSorted.get( x  );				//	System.out.println(idCouldAdded);
			*/
			} break;
		
		case minValue : {
			System.out.println("not implem");
			/*
			 idCouldAdded = getNodeSmallest(morp, listNeigNode);
			 */
			}
		}
		return idCouldAdded ;
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
	
	
	// handle still alive
	protected static void handleStillAlive ( int  numberNewNodes  , boolean stillAlive , Node nNet ) {

		if ( numberNewNodes == 0) {	
			if ( stillAlive )
				nNet.setAttribute("seedGrad", 1 );
			else if ( stillAlive == false )
				nNet.setAttribute("seedGrad", 0 );
		}	
	}
	
	// control Seed methods 
	protected static int getNumberMaxNewNodes (double delta , ArrayList<String> listForDelta , boolean isDeltaAbs ) {
		
		int numberMaxNewNodes = 0 ;
		
		if ( isDeltaAbs ) 
			delta = Math.abs(delta) ;
		
			
		
		
		if ( delta <= 0 )
			return 0 ;		
		else if ( delta >= 1 )
			 numberMaxNewNodes = listForDelta.size() ;	
		else if ( delta > 0 && delta < 1  ) 
			numberMaxNewNodes = (int) (Math.round( delta * listForDelta.size() )  ) ;								//			System.out.println(netGraph.getNodeCount());

		return numberMaxNewNodes ;
	}
	
	protected static ArrayList<String> getListForDelta ( ArrayList<String> listNeigGsStr , ArrayList<String> listNeigGsStrSeed ) {
		
		ArrayList<String> listForDelta = new ArrayList<String>();									//	System.out.println(listNeigGsStr);
	
			for ( String s : listNeigGsStr ) 
				if ( !listNeigGsStrSeed.contains(s))
					listForDelta.add(s);
								
		return listForDelta; 
	}

//GET METHODS ---------------------------------------------------------------------------------------------------------------------------------------
	public static double getProb() 		{ return prob; }

	
}
