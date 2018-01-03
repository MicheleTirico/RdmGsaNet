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
		
		// list of nodes with seedGrad = 1 ;
		ArrayList<Node> listNodeSeedGrad = new ArrayList<Node>();

		// list of nodes with OldSeedGrad = 1 ;
		ArrayList<Node> listNodeOldSeedGrad = new ArrayList<Node>();
		
		// create listNodeSeedGrad and listNodeOldSeedGrad ;
		for ( Node nNet : netGraph.getEachNode() ) {
			int seedGradInt = nNet.getAttribute("seedGrad") ; 
			int oldSeedGradInt = nNet.getAttribute("oldSeedGrad") ;
			
			if ( seedGradInt == 1 ) 	{	listNodeSeedGrad.add(nNet) ; }
			if ( oldSeedGradInt == 1 ) 	{	listNodeOldSeedGrad.add(nNet) ; }
		}
			
//		System.out.println(listNodeSeedGrad);
//		System.out.println(listNodeOldSeedGrad);
	
		// Iterator for each node ( NET) with seedGrad = 1 and oldSeedGrad = 0
		for ( Node nNet : listNodeSeedGrad ) {
			
//			 System.out.println(nNet.getId());
			
			Node nGs = gsGraph.getNode(nNet.getId());
			
			// get max value of act of neigbords ( in gsGraph)
			Iterator<Node> iter = nGs.getNeighborNodeIterator() ;		//	System.out.println("id " + n);

			double val = 0 ;
			ArrayList<String> listIdNeigValMax = new ArrayList<String>();
			while ( iter.hasNext()) {
				
				Node neig = iter.next() ;
				
				double morpVal = neig.getAttribute(morp);	
//				System.out.println(neig.getId() + " " + morpVal);
				
				if ( morpVal >= val ) {	
					val = morpVal; 
					listIdNeigValMax.add(neig.getId() ) ;
				}
			}	//			System.out.println(listIdNeigValMax);	System.out.println(nGs.getId() + " " + val);
			
			// if nNet attribute oldGraph = 0 -> create new node 
			for ( String id : listIdNeigValMax) {

				// get random node from listIdNeigValMax
				String randomNodeInListValMax = listIdNeigValMax.get((new Random()).nextInt(listIdNeigValMax.size()));	//System.out.println(randomNodeInListValMax);
				
				Node nNetValMax = netGraph.getNode(id);				//	System.out.println(nNetValMax.getId());
				
				int oldSeed = nNetValMax.getAttribute("oldSeedGrad");
//				System.out.println(oldSeed);
				
				try {
					if ( oldSeed == 0 ) {
						netGraph.addNode(id);
					
						// set coordinate 
						Node nFrom = gsGraph.getNode(id);
						Node nTo = netGraph.getNode(id);
						gsAlgoToolkit.setNodeCoordinateFromNode(gsGraph, netGraph, nFrom, nTo);		
//						nNetValMax.setAttribute("oldSeedGrad", 1);
						}
					else { break;}
				}
				catch ( org.graphstream.graph.IdAlreadyInUseException e) {
					System.out.println("node "+ nNetValMax.getId() + " already exist");
					nNetValMax.setAttribute("seedGrad", 1);
				}
				
			}
		// set nNet attribute : oldSeedGrad = 1 ; seedGrad = 0
		
		// set attribute of new node : oldSeedGrad = 0 , seedGrad = 1 ;
		
		
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
	


}
