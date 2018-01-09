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

public class generateNetNodeGradient implements generateNetNodeInter {
	
	// COSTANTS
	int seedNumber;
	static Graph netGraph = layerNet.getGraph();
	static Graph gsGraph = layerGs.getGraph();	
	String morp ;
	boolean isGreater;
	
	static Map<Integer , ArrayList<Node>> mapStepSeed =  new HashMap<Integer , ArrayList<Node>>();
	enum splitSeed {onlyOneRandom , splitMax }
	splitSeed typeSplit;
	
	// COSTRUCTOR 
	public generateNetNodeGradient( int seedNumber , String morp , splitSeed typeSPlit, boolean isGreater ) {
		this.seedNumber = seedNumber;
		this.morp = morp;
		this.typeSplit = typeSPlit;
		this.isGreater = isGreater;
	}

	@Override
	public void generateNodeRule(int step) {
		
		// CREATE LIST OF SEEDGRAD AND OLDSEEDGRAD ------------------------------------------------------------------------------------------------------------------
		ArrayList<Node> listNodeSeedGrad = new ArrayList<Node>();
				
		listNodeSeedGrad = 	createListSeedGrad( step );
		mapStepSeed.put(step, listNodeSeedGrad);
		
//		System.out.println(test);
		
		if ( listNodeSeedGrad.isEmpty() && step != 1 ) {
			listNodeSeedGrad = mapStepSeed.get( step - 1 );
			mapStepSeed.put(step, listNodeSeedGrad);
		}
		
		System.out.println("list seed " + listNodeSeedGrad);
	
		// Iterator for each node ( NET) with seedGrad = 1 and oldSeedGrad = 0
		for ( Node nNet : listNodeSeedGrad ) {
		
			// CREATE LIST NEIGBHORDS WITH MAX VALUE ----------------------------------------------------------------------------------------------------------------
			ArrayList<String> listIdNeigValMax = createListMaxNeig( gsGraph, nNet , morp);
		
			/*
			// print
			System.out.println("list max nodes of " + nNet.getId() + " " + listIdNeigValMax);
			System.out.println("node set " + netGraph.getNodeSet());
		
			Node nGs = gsGraph.getNode(nNet.getId());
			double valMorp = nGs.getAttribute(morp);
			System.out.println(nGs.getId() + " " + valMorp);
			
			for ( String s : listIdNeigValMax) {
				Node n = gsGraph.getNode(s);
				double val = n.getAttribute(morp);
				System.out.println(n.getId() + " " + val);
			}
			*/
			
			String idNewNode = null ;
			ArrayList<String> listNewNode = new ArrayList<String>();
			switch (typeSplit) {
			
				case onlyOneRandom: {	
					onlyOneRandomMethod ( nNet, idNewNode, listIdNeigValMax);	// System.out.println("onlyOneRandom");
				} break;
									
				case splitMax : {	
					splitMaxMethod ( nNet, listNewNode ,  listIdNeigValMax ); 		// System.out.println("splitMax");
				} break;
			} //			System.out.println("list seed " + listNodeSeedGrad);
			
			// set all old nodes not yet seedGrad for new step
			nNet.setAttribute("seedGrad", 0);
		}
	}

	@Override
	public void removeNodeRule(int step) {
		}	

	@Override
	public void setSeedNodes( int step ) {

		if ( step == 1 ) {
			ArrayList<String> listIdNodeMaxDegree = gsAlgoToolkit.getNodeMaxDegree(netGraph);
				
			String idSeed = null;														//	System.out.println(seedNumber);
			if ( seedNumber == 1 ) {													//	System.out.println("seedNumber = " +seedNumber );
				for ( String s : listIdNodeMaxDegree) { idSeed = s;	}					//	System.out.println(idSeed);
				Node seedNode = netGraph.getNode(idSeed);
				seedNode.setAttribute("seedGrad", 1);
			}
			
			else {
				if ( seedNumber > 9 ) {													//	System.out.println("seedNumber = " +seedNumber );	//	System.out.println("seed Gradient over number of nodes , set seedNumber = countNodes");
					for ( Node seedNode : netGraph.getEachNode()) {	seedNode.setAttribute("seedGrad", 1 );	}
			}
				
			else {
				if ( seedNumber > 1 && seedNumber < 9 ) {								//	System.out.println("seedNumber = " +seedNumber );
					ArrayList<String> listSeed = new ArrayList<String>() ;
				
					for ( String s : listIdNodeMaxDegree) { listSeed.add(s); }			//	System.out.println(listSeed);
					
					for ( int i = 1 ; listSeed.size() < seedNumber  ; i++ )  {			//	System.out.println(i);
						Node nodeRandom = randomNode(netGraph);
						String idNodeRandom = nodeRandom.getId();
						if ( !listSeed.contains(idNodeRandom) ) {	listSeed.add(idNodeRandom);	}		
						listSeed.forEach(s -> netGraph.getNode(s).setAttribute("seedGrad", 1));
						}
					}
				}
			}																			//			System.out.println(numberOfNodesNet);		//			System.out.println(listIdNodeMaxDegree);		//			System.out.println(nodeSet);		
		}
	}
	
// PRIVATE METHODS ----------------------------------------------------------------------------------------------------------------------------------------
	private void splitMaxMethod(Node nNet, ArrayList<String> listNewNode ,  ArrayList<String> listIdNeigValMax) {
		
			for ( String newNodeId : listIdNeigValMax )  {	//	System.out.println(newNodeId);

				try {
					Node seedMorp = gsGraph.getNode(nNet.getId());
					double valSeed = seedMorp.getAttribute(morp);
				
					Node newNodeTest = gsGraph.getNode(newNodeId) ;
					double valNewNode = newNodeTest.getAttribute(morp); // System.out.println("new node " + newNodeId);
				
					if ( isGreater == true ) { //	System.out.println("true");
					
						if ( valNewNode >= valSeed) {
							netGraph.addNode(newNodeId);	// System.out.println(newNodeId);
						} else { break; }
					
					} else { netGraph.addNode(newNodeId); 	// System.out.println("false");
					}
				
					Node newNode = netGraph.getNode(newNodeId);	
					newNode.addAttribute("seedGrad", 1);
				
					listNewNode.add(newNodeId);
			
					// set coordinate 
					Node nFrom = gsGraph.getNode(newNodeId);
					Node nTo = netGraph.getNode(newNodeId);
					gsAlgoToolkit.setNodeCoordinateFromNode(gsGraph, netGraph, nFrom, nTo);		//	System.out.println(netNodeRandomNew.getAttributeKeySet());			
				}
				catch (org.graphstream.graph.IdAlreadyInUseException e) {	}	
				}
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
	
	private ArrayList<Node> createListSeedGrad (int step ) {
		
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
	
	/*
	private ArrayList<Node> setOldSeed ( int step ) {
		
		// list of nodes with seedGrad = 1 ;
		ArrayList<Node> listNodeSeedGrad = new ArrayList<Node>();
		
		Map<Double, Graph> mapStepNetGraph = simulation.getMapStepNetGraph();
		Graph gr = null ;
		gr = mapStepNetGraph.get( (double) step - 2 );
		System.out.println(gr.getNodeCount());
		
		return listNodeSeedGrad;
	}
	*/
	
// create list of neigbrd with max value of morphogen
	private ArrayList<String> createListMaxNeig (Graph graph , Node n , String morp ) {
		
		Node nGs = gsGraph.getNode(n.getId());
		double val = 0 ;
		int big = 1 ;
		ArrayList<String> listIdNeigValMax = new ArrayList<String>();
		
		// get max value of act of neigbords ( in gsGraph)
		Iterator<Node> iter = nGs.getNeighborNodeIterator() ;		//	System.out.println("id " + n);
		
		while ( iter.hasNext()) {				
			
			Node neig = iter.next() ;			
			double morpVal = neig.getAttribute(morp);	
			if ( morpVal * big >= val * big ) {	
				val = morpVal; 
				listIdNeigValMax.add(neig.getId() ) ;
			}
		}	
		return listIdNeigValMax;
	}

}
