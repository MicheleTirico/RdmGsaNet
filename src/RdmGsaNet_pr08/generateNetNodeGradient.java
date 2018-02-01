package RdmGsaNet_pr08;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import com.sun.javafx.runtime.async.AsyncOperationListener;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import static org.graphstream.algorithm.Toolkit.*;

import RdmGsaNetAlgo.gsAlgoToolkit;
import graphstream_dev_toolkit.toolkit;

public class generateNetNodeGradient implements generateNetNodeInter {
	
	// COSTANTS
	protected static String setupNet = layerNet.getLayout();

	protected static Graph netGraph = layerNet.getGraph(),
							gsGraph = layerGs.getGraph();	
	
	protected  int seedNumber;
	protected String morp ;
	
	protected boolean isGreater ,
						stillAlive ;
	
	protected double incremAss , 
						incremRel ; 
	
	protected enum layoutSeedGradient { multiRandom , oneCenter , allNetNode }
	protected layoutSeedGradient layoutSeed ;
	
	protected static double probabilityTest ;
	
	protected static Map<Integer , ArrayList<Node>> mapStepSeed =  new HashMap<Integer , ArrayList<Node>>();
	
	protected enum splitSeed { onlyOneRandom , splitMax , splitMaxThreshold , splitProbability }
	
	private splitSeed typeSplit ;
	
	// COSTRUCTOR 
	public generateNetNodeGradient( layoutSeedGradient layoutSeed , int seedNumber , String morp , splitSeed typeSPlit, boolean isGreater , double incremAss , double incremRel , double probabilityTest , boolean stillAlive ) {
		this.layoutSeed = layoutSeed;
		this.seedNumber = seedNumber;
		this.morp = morp;
		this.typeSplit = typeSPlit;
		this.isGreater = isGreater;
		this.incremAss = incremAss ;
		this.incremRel = incremRel ;
		this.probabilityTest = probabilityTest ;
		this.stillAlive = stillAlive ;
	}

	@Override
	public void generateNodeRule(int step) {						//	System.out.println( setupNet );

		setSeedNodes (step , layoutSeed) ;
		// CREATE LIST OF SEEDGRAD 
		ArrayList<Node> listNodeSeedGrad = new ArrayList<Node>();	//	ArrayList<Node> listNewSeed = new ArrayList<Node>();
		
		// handle whether no have new node, set seed list like previous step
		listNodeSeedGrad = 	createListSeedGrad( step );
		mapStepSeed.put(step, listNodeSeedGrad);
		
		if ( listNodeSeedGrad.isEmpty() && step != 1 ) {
			listNodeSeedGrad = mapStepSeed.get( step - 1 );
			mapStepSeed.put(step, listNodeSeedGrad);
		}																				//	System.out.println("list seed " + listNodeSeedGrad);
		
		// Iterator for each node ( NET) with seedGrad = 1 and oldSeedGrad = 0
		for ( Node nNet : listNodeSeedGrad ) {
		
			// still alive
			ArrayList<Node> listStillAlive = new ArrayList<Node>();	
	
			ArrayList<Node> listNewSeed = new ArrayList<Node>();
			
			// CREATE LIST NEIGBHORDS WITH MAX VALUE ----------------------------------------------------------------------------------------------------------------
			ArrayList<String> listIdNeigValMax = createListMaxNeig( gsGraph, nNet , morp);
			
			// used only in onlyOneRandom
			String idNewNode = null ;
			
			ArrayList<String> listNewNode = new ArrayList<String>();
							
			switch (typeSplit) {
				case onlyOneRandom: {	
					onlyOneRandomMethod ( nNet, idNewNode, listIdNeigValMax );		// System.out.println("onlyOneRandom");
				} break;
									
				case splitMax : {	
					splitMaxMethod ( nNet, listNewNode , listIdNeigValMax ); 		// System.out.println("splitMax");
				} break;
				
				case splitMaxThreshold : {
					splitMaxThresholdMethod( nNet, listNewNode, listIdNeigValMax );	// System.out.println("splitMaxThreshold");
				} break ;
				
				case splitProbability : {
					splitProbabilityMethod ( nNet, listNewNode, listIdNeigValMax , listNewSeed , listStillAlive) ;		// System.out.println("splitProbability");
				}
			} 			
			
			// set all old nodes not yet seedGrad for new step
			nNet.setAttribute("seedGrad", 0);
			
			// still alive
			if ( stillAlive ) 
				for ( Node n : listStillAlive) { n.addAttribute("seedGrad", 1 ); }	//	System.out.println("listStillAlive " + listStillAlive);	
			
			for ( Node n : listNewSeed ) {	n.addAttribute("seedGrad", 1);	} 		//	System.out.println("listNewNode " + listNewNode);
		}
	}

	@Override
	public void removeNodeRule(int step) {
		}	

	public void setSeedNodes( int step , layoutSeedGradient layoutSeed  ) {

		if ( step == 1 ) {
			
			switch (layoutSeed) {
				case multiRandom : 
					setSeedMultiRandom( seedNumber);
					break;
																						
				case allNetNode : 
					setSeedAllNetNode () ;
					break;
			}
		}
	}
	
// PRIVATE METHODS ----------------------------------------------------------------------------------------------------------------------------------
	
	// METHODS TO SET STARTED SEED ------------------------------------------------------------------------------------------------------------------
	private static void setSeedMultiRandom ( int seedNumber ) {	
		
		ArrayList<String> listIdNodeMaxDegree = gsAlgoToolkit.getNodeMaxDegree(netGraph);	
		String idSeed = null;
		
		if ( seedNumber == 1 ) { 	// setup for seedNumber = 1	
			for ( String s : listIdNodeMaxDegree) 
				idSeed = s ;				
			
			Node seedNode = netGraph.getNode(idSeed);
			seedNode.addAttribute("seedGrad", 1);
		}
		
		else { 						// setup for seedNumber >= 9
			if ( seedNumber >= 9 ) {													//	System.out.println("seedNumber = " +seedNumber );	//	System.out.println("seed Gradient over number of nodes , set seedNumber = countNodes");
				for ( Node seedNode : netGraph.getEachNode()) {	seedNode.setAttribute("seedGrad", 1 );	}
		}
			
		else {						// setup for 1 < seedNumber < 9  
			if ( seedNumber > 1 && seedNumber < 9 ) {								//	System.out.println("seedNumber = " +seedNumber );
				ArrayList<String> listSeed = new ArrayList<String>() ;
			
				for ( String s : listIdNodeMaxDegree)  
					listSeed.add(s); 												//	System.out.println(listSeed);
				
				for ( int i = 1 ; listSeed.size() < seedNumber  ; i++ )  {			//	System.out.println(i);
					
					Node nodeRandom = randomNode(netGraph);
					String idNodeRandom = nodeRandom.getId();
					
					if ( !listSeed.contains(idNodeRandom) ) 	
						listSeed.add(idNodeRandom);			
					
					listSeed.forEach(s -> netGraph.getNode(s).setAttribute("seedGrad", 1));
					}
				}
			}
		}
	}
	
	private static void setSeedAllNetNode ( ) {	
		
		for ( Node n : netGraph.getEachNode() ) 
			n.addAttribute("seedGrad", 1);	
	
	}
	
	// METHODS TO HANDLE PROBABILITY ----------------------------------------------------------------------------------------------------------------
	private void splitMaxThresholdMethod (Node nNet, ArrayList<String> listNewNode ,  ArrayList<String> listIdNeigValMax ) {
		splitMax(nNet, listNewNode, listIdNeigValMax , true);
	}
	
	private void splitMaxMethod (Node nNet, ArrayList<String> listNewNode ,  ArrayList<String> listIdNeigValMax ) {
		splitMax(nNet, listNewNode, listIdNeigValMax , false);
	}
	
	private void splitProbabilityMethod (Node nNet, ArrayList<String> listNewNode ,  ArrayList<String> listIdNeigValMax, ArrayList<Node> listNewSeed , ArrayList<Node> listStillAlive) {
		
		int numberMaxNewNodes = listIdNeigValMax.size();										//		System.out.println("numberMaxNewNodes " + numberMaxNewNodes);
		int numberNewNodes = gsAlgoToolkit.getBinomial(numberMaxNewNodes, probabilityTest);		//		System.out.println("numberNewNodes " + numberNewNodes);
		
		if (numberNewNodes == 0 ) {
			listNewSeed.add(nNet);													//	System.out.println(nNet.getId());
			
		} else {			
			for ( int posList = 0 ,  incremNewNode = 0 ; posList <= numberMaxNewNodes && incremNewNode < numberNewNodes ; posList++ , incremNewNode++) {
			
				try {
					String newNodeId = listIdNeigValMax.get(incremNewNode);			//	System.out.println("newNodeId " + newNodeId);
					netGraph.addNode(newNodeId);
		
					// set seed values
					Node newNode = netGraph.getNode(newNodeId);	
					newNode.addAttribute("seedGrad", 1);
				
					// add new node in list of new nodes
					listNewNode.add(newNodeId);
			
					// set coordinate 
					Node nFrom = gsGraph.getNode(newNodeId);
					Node nTo = netGraph.getNode(newNodeId);
					gsAlgoToolkit.setNodeCoordinateFromNode(gsGraph, netGraph, nFrom, nTo);		//	System.out.println(netNodeRandomNew.getAttributeKeySet());			
				}
				catch (org.graphstream.graph.IdAlreadyInUseException e) { 
					if ( stillAlive )
						listStillAlive.add(nNet);
					continue;		
				}	
				
				catch (java.lang.NullPointerException e) 				{ 
//					nNet.setAttribute("seedGrad", 1);
					continue; }		
			}		
		}
	}
	
	private void splitMax (Node nNet, ArrayList<String> listNewNode ,  ArrayList<String> listIdNeigValMax , boolean isIncremThreshold ) {
		
		System.out.println( listIdNeigValMax);
		for ( String newNodeId : listIdNeigValMax )  {	//		System.out.println(newNodeId);

			try {
				Node seedMorp = gsGraph.getNode(nNet.getId());
				double valSeed = seedMorp.getAttribute(morp);
			
				Node newNodeTest = gsGraph.getNode(newNodeId) ;
				double valNewNode = newNodeTest.getAttribute(morp); 
				
				
				if ( isGreater == false && isIncremThreshold == false )  { //	System.out.print("ff ");
					netGraph.addNode(newNodeId);
					}
				
				if ( isGreater == true && isIncremThreshold == false )  { // 	System.out.print("tf ");
					if ( valNewNode > valSeed ) {	netGraph.addNode(newNodeId); } 
				}
				
				if ( isGreater == true && isIncremThreshold == true )  { //		System.out.print("tt ");
					double increm = valNewNode - valSeed  ;
					if ( valNewNode > valSeed && increm > incremAss ) {
						netGraph.addNode(newNodeId);
					}
				}
				
				if ( isGreater == false && isIncremThreshold == true )  { //	
					System.out.println("isGreater = f && isIncremThreshold = t ");
					System.out.print("method not possible");
				}
				
				// set seed values
				Node newNode = netGraph.getNode(newNodeId);	
				newNode.addAttribute("seedGrad", 1);
				
				// add new node in list of new nodes
				listNewNode.add(newNodeId);
			
				// set coordinate 
				Node nFrom = gsGraph.getNode(newNodeId);
				Node nTo = netGraph.getNode(newNodeId);
				gsAlgoToolkit.setNodeCoordinateFromNode(gsGraph, netGraph, nFrom, nTo);		//	System.out.println(netNodeRandomNew.getAttributeKeySet());			
			}
			catch (org.graphstream.graph.IdAlreadyInUseException e) { continue;	}	
			catch (java.lang.NullPointerException e) 				{ continue; }
		}																					//	System.out.println("listNewNode " + listNewNode);
	}
	
	private static void onlyOneRandomMethod ( Node nNet, String idNewNode , ArrayList<String> listIdNeigValMax) {
	
		for ( int i = 0 ; i < Math.pow(listIdNeigValMax.size() , 2 ) ; i++ ) {
	
			// get random node from listIdNeigValMax
			idNewNode = listIdNeigValMax.get((new Random()).nextInt(listIdNeigValMax.size()));	//System.out.println(randomNodeInListValMax);
			
			try {
				// create new node
				netGraph.addNode(idNewNode);
				break;
			}
			catch (org.graphstream.graph.IdAlreadyInUseException e) {	continue;		}		
		}
	
		// call new node
		Node netNodeRandomNew = netGraph.getNode(idNewNode);
					
		// set parameters
		netNodeRandomNew.addAttribute("seedGrad", 1);
				
		// set coordinate 
		Node nFrom = gsGraph.getNode(idNewNode);
		Node nTo = netGraph.getNode(idNewNode);
		gsAlgoToolkit.setNodeCoordinateFromNode(gsGraph, netGraph, nFrom, nTo);		//	System.out.println(netNodeRandomNew.getAttributeKeySet());		
	}
	
	
// SERVICE METHODS ----------------------------------------------------------------------------------------------------------------------------------
	protected static ArrayList<Node> createListSeedGrad (int step ) {
		
		Map<Double, Graph> mapStepNetGraph = simulation.getMapStepNetGraph();
		Graph gr = null ;

		// list of nodes with seedGrad = 1 ;
		ArrayList<Node> listNodeSeedGrad = new ArrayList<Node>();
		
		try {		
			gr = mapStepNetGraph.get( (double) step - 1 );

			// create listNodeSeedGrad ;
			for ( Node nNet : gr.getEachNode() ) {
					
				int seedGradInt = nNet.getAttribute("seedGrad") ; //				System.out.println(seedGradInt);
				if ( seedGradInt == 1 ) 	{	listNodeSeedGrad.add(nNet) ; }
			}
			
		} catch (java.lang.NullPointerException e) {	}
		
		return listNodeSeedGrad;
	}
	
	// create list of neigbrd with max value of morphogen
	private ArrayList<String> createListMaxNeig (Graph graph , Node n , String morp ) {
		
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
				e.printStackTrace();
				continue ;
			}
		}	
		return listIdNeigValMax;
	}

// GET METHODS ----------------------------------------------------------------------------------------------------------------------------
	public static double getProb() 		{ return probabilityTest; }

}
