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
	static Graph netGraph = layerNet.getGraph();
	static Graph gsGraph = layerGs.getGraph();	
	String morp ;
	enum splitSeed {onlyOneRandom , splitMax }
	splitSeed typeSplit;
	
	// COSTRUCTOR 
	public generateNetNodeGradient( int seedNumber , String morp , splitSeed typeSPlit ) {
		this.seedNumber = seedNumber;
		this.morp = morp;
		this.typeSplit = typeSPlit;
	}

	@Override
	public void generateNodeRule(int step) {
		
		// CREATE LIST OF SEEDGRAD AND OLDSEEDGRAD ------------------------------------------------------------------------------------------------------------------
		ArrayList<Node> listNodeSeedGrad = createListSeedGrad(netGraph);
	
		// Iterator for each node ( NET) with seedGrad = 1 and oldSeedGrad = 0
		for ( Node nNet : listNodeSeedGrad ) {
		
			// CREATE LIST NEIGBHORDS WITH MAX VALUE ----------------------------------------------------------------------------------------------------------------
			ArrayList<String> listIdNeigValMax = createListMaxNeig(gsGraph, nNet , morp);
		
			String idRandomNew = null ;
			switch (typeSplit) {
			case onlyOneRandom: {	onlyOneRandomMethod (idRandomNew, listIdNeigValMax);	}
									break;
									
			case splitMax : 	{	splitMaxMethod (idRandomNew, listIdNeigValMax); }
									break;
			}
			
			
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
	private void splitMaxMethod(String idRandomNew , ArrayList<String> listIdNeigValMax) {
		
		try {
			for ( String s : listIdNeigValMax) {
				// create new node
				netGraph.addNode(s);
		
				// call new node
				Node netNodeRandomNew = netGraph.getNode(s);
					
				// set parameters
				netNodeRandomNew.addAttribute("seedGrad", 1);
						
				// set coordinate 
				Node nFrom = gsGraph.getNode(s);
				Node nTo = netGraph.getNode(s);
				gsAlgoToolkit.setNodeCoordinateFromNode(gsGraph, netGraph, nFrom, nTo);		//	System.out.println(netNodeRandomNew.getAttributeKeySet());			
			}
		}
		catch (org.graphstream.graph.IdAlreadyInUseException e) {	}
	}
	
	private static void onlyOneRandomMethod (String idRandomNew , ArrayList<String> listIdNeigValMax) {
	
		for ( int i = 0 ; i < Math.pow(listIdNeigValMax.size() , 2 ) ; i++ ) {
	
			// get random node from listIdNeigValMax
			idRandomNew = listIdNeigValMax.get((new Random()).nextInt(listIdNeigValMax.size()));	//System.out.println(randomNodeInListValMax);
	
			try {
				// create new node
				netGraph.addNode(idRandomNew);
				break;
			}
			catch (org.graphstream.graph.IdAlreadyInUseException e) {	continue;		}		
		}
	
		// call new node
		Node netNodeRandomNew = netGraph.getNode(idRandomNew);
					
		// set parameters
		netNodeRandomNew.addAttribute("seedGrad", 1);
				
		// set coordinate 
		Node nFrom = gsGraph.getNode(idRandomNew);
		Node nTo = netGraph.getNode(idRandomNew);
		gsAlgoToolkit.setNodeCoordinateFromNode(gsGraph, netGraph, nFrom, nTo);		//	System.out.println(netNodeRandomNew.getAttributeKeySet());		
	}
	
	private ArrayList<Node> createListSeedGrad (Graph graph) {
		
		// list of nodes with seedGrad = 1 ;
		ArrayList<Node> listNodeSeedGrad = new ArrayList<Node>();

		// create listNodeSeedGrad ;
		for ( Node nNet : graph.getEachNode() ) {
					
			int seedGradInt = nNet.getAttribute("seedGrad") ; 
			if ( seedGradInt == 1 ) 	{	listNodeSeedGrad.add(nNet) ; }
		}
		return listNodeSeedGrad;
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
		}	
		return listIdNeigValMax;
	}

}
