package RdmGsaNet_pr08;

import java.util.ArrayList;
import java.util.Collection;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import static org.graphstream.algorithm.Toolkit.*;

import RdmGsaNetAlgo.gsAlgoToolkit;

public class generateNetNodeGradient implements generateNetNodeInter {
	
	// COSTANTS
	int seedNumber;
	Graph netGraph = layerNet.getGraph();
	Graph gsGraph = layerGs.getGraph();	
	
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
			
	// Iterator for each node ( NET) with seedGrad = 1 and oldSeedGrad = 0
		
		for ( Node nNet : listNodeSeedGrad ) {
			
			
		}
			
		// get max value of act of neigbords ( in gsGraph)
		
		// if nNet attribute oldGraph = 0 -> create new node 
		
		// set nNet attribute : oldSeedGrad = 1 ; seedGrad = 0
		
		// set attribute of new node : oldSeedGrad = 0 , seedGrad = 1 ;
		
		
		
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
