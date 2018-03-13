package RdmGsaNet_generateGraph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNet_generateGraph.generateNetNode.layoutSeed;
import RdmGsaNet_generateGraph.generateNetNode.rule;
import RdmGsaNet_mainSim.layerGs;
import RdmGsaNet_mainSim.layerNet;
import RdmGsaNet_mainSim.main;

public class generateNetNodeGradient extends main  {

	// COSTANTS
	protected int numberMaxSeed = generateNetNode.numberMaxSeed  ;
	protected layoutSeed setLayoutSeed ;
	protected static rule ruleType  ;
	protected String morp = generateNetNode.morp  ;
	protected static double prob = 0  ;
	protected  boolean stillAlive = generateNetNode.stillAlive  ;
	
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

	
	// handle listNeigGsStrSeed 8 and not seed )
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
