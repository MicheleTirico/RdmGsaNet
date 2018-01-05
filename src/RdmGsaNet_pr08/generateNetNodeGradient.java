package RdmGsaNet_pr08;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import static org.graphstream.algorithm.Toolkit.*;

import RdmGsaNetAlgo.gsAlgoToolkit;

public class generateNetNodeGradient implements generateNetNodeInter {
	
	// COSTANTS
	int seedNumber;
	Graph netGraph = layerNet.getGraph();
	Graph gsGraph = layerGs.getGraph();	
	String morp = "gsAct";
	
	// COSTRUCTOR 
	public generateNetNodeGradient( int seedNumber ) {
		this.seedNumber = seedNumber;
	}

	@Override
	public void generateNodeRule(int step) {
		
// CREATE LIST OF SEEDGRAD AND OLDSEEDGRAD ------------------------------------------------------------------------------------------------------------------
		ArrayList<Node> listNodeSeedGrad = createListSeedGrad(netGraph);
	
//		System.out.println("list node seed grad " + listNodeSeedGrad);
//		System.out.println("node count "  + netGraph.getNodeCount());
//	 	System.out.println("list of node net "  + netGraph.getNodeSet());
		
// Iterator for each node ( NET) with seedGrad = 1 and oldSeedGrad = 0
		for ( Node nNet : listNodeSeedGrad ) {
		
			// CREATE LIST NEIGBHORDS WITH MAX VALUE ----------------------------------------------------------------------------------------------------------------
			ArrayList<String> listIdNeigValMax = createListMaxNeig(gsGraph, nNet , morp);
			
//			System.out.println(" list of neig with max value " + listIdNeigValMax);	
//			System.out.println(nGs.getId() + " " + val);
			
			for ( int i = 0 ; i < Math.pow(listIdNeigValMax.size() , 2 ) ; i++ ) {
			
				// get random node from listIdNeigValMax
				String idRandomNew = listIdNeigValMax.get((new Random()).nextInt(listIdNeigValMax.size()));	//System.out.println(randomNodeInListValMax);
			
				Node gsNodeRandomNew = gsGraph.getNode(idRandomNew);
				
				try {
					// create new node
					netGraph.addNode(idRandomNew);
					
					// call new node
					Node netNodeRandomNew = netGraph.getNode(idRandomNew);
					
					// set parameters
					netNodeRandomNew.addAttribute("seedGrad", 1);
//					netNodeRandomNew.addAttribute("oldSeedGrad", 1);		int oldSeedGrad = netNodeRandomNew.getAttribute("oldSeedGrad"); //	System.out.println(oldSeedGrad);
					
					// set coordinate 
					Node nFrom = gsGraph.getNode(idRandomNew);
					Node nTo = netGraph.getNode(idRandomNew);
					gsAlgoToolkit.setNodeCoordinateFromNode(gsGraph, netGraph, nFrom, nTo);		//	System.out.println(netNodeRandomNew.getAttributeKeySet());
				
					break;
				}
				catch (org.graphstream.graph.IdAlreadyInUseException e) {
					continue;
				}		
			}
			// set all old nodes not yet seedGrad for new step
			nNet.setAttribute("seedGrad", 0);
			
		}
			
			
			
			
// CREATE NODE ---------------------------------------------------------------------------------------------

			
			/*
			// if nNet attribute oldGraph = 0 -> create new node 
			for ( String id : listIdNeigValMax) {
				
				// get random node from listIdNeigValMax
				String randomNodeInListValMax = listIdNeigValMax.get((new Random()).nextInt(listIdNeigValMax.size()));	//System.out.println(randomNodeInListValMax);
				
				Node nGsValMax = gsGraph.getNode(id);				//	
				
				System.out.println("random id in list max val " + nGsValMax.getId());
//				System.out.println(nGsValMax.getAttributeKeySet());
//				int oldSeedGrad = nNetValMax.getAttribute("oldSeedGrad");
//				int seedGrad = nNetValMax.getAttribute("seedGrad");
				int con  = nGsValMax.getAttribute("con");
//				System.out.println(con);
				
				try {
					if ( con == 0 ) {
						netGraph.addNode(id);
						
						Node nNetNew = netGraph.getNode(id);
					
						// set coordinate 
						Node nFrom = gsGraph.getNode(id);
						Node nTo = netGraph.getNode(id);
						gsAlgoToolkit.setNodeCoordinateFromNode(gsGraph, netGraph, nFrom, nTo);		
						
						nNetNew.addAttribute("seedGrad", 1);

//						nNetValMax.setAttribute("seedGrad", 1);
						}
					else { break;}
				}
				catch ( org.graphstream.graph.IdAlreadyInUseException e) {
					System.out.println("node "+ nGsValMax.getId() + " already exist");
					nGsValMax.setAttribute("seedGrad", 1);
			
				}
				
			}
		// set nNet attribute : oldSeedGrad = 1 ; seedGrad = 0
		
		// set attribute of new node : oldSeedGrad = 0 , seedGrad = 1 ;
//		System.out.println(listNodeSeedGrad);
		*/
		
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
	private ArrayList<Node> createListSeedGrad (Graph graph) {
		
		// list of nodes with seedGrad = 1 ;
		ArrayList<Node> listNodeSeedGrad = new ArrayList<Node>();
		

				// create listNodeSeedGrad ;
				for ( Node nNet : graph.getEachNode() ) {
					
					int seedGradInt = nNet.getAttribute("seedGrad") ; 

//					int oldSeedGrad = graph.getAttribute("oldSeedGrad");
					
					if ( 
							seedGradInt == 1 
//							&& oldSeedGrad == 0
							) 	{	listNodeSeedGrad.add(nNet) ; }
				}
				return listNodeSeedGrad;
	}

	private ArrayList<Node> createListOldSeedGrad () {
		
		// list of nodes with oldSeedGrad = 1 ;
				ArrayList<Node> listNodeOldSeedGrad = new ArrayList<Node>();

				// create listNodeOldSeedGrad ;
				for ( Node nNet : netGraph.getEachNode() ) {
					
					int seedGradInt = nNet.getAttribute("oldSeedGrad") ; 
					if ( seedGradInt == 1 ) 	{	listNodeOldSeedGrad.add(nNet) ; }
				}
				return listNodeOldSeedGrad;
	}
	
	// create list of neigbrd with max value of morphogen
	private ArrayList<String> createListMaxNeig (Graph graph , Node n , String morp ) {
		
		Node nGs = gsGraph.getNode(n.getId());
		double val = 0 ;
		ArrayList<String> listIdNeigValMax = new ArrayList<String>();
		
		// get max value of act of neigbords ( in gsGraph)
		Iterator<Node> iter = nGs.getNeighborNodeIterator() ;		//	System.out.println("id " + n);
		
		while ( iter.hasNext()) {				
			
			Node neig = iter.next() ;			
			double morpVal = neig.getAttribute(morp);	
			if ( morpVal >= val ) {	
				val = morpVal; 
				listIdNeigValMax.add(neig.getId() ) ;
			}
		}	//			System.out.println(li
		return listIdNeigValMax;
	}

}
