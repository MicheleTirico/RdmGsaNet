package RdmGsaNet_pr09;

import java.util.ArrayList;

import org.graphstream.graph.Node;

import RdmGsaNetAlgo.gsAlgoToolkit;

public class generateNetNodeGradientProbDelta extends generateNetNodeGradient implements generateNetNode_Inter {
	
	public generateNetNodeGradientProbDelta(int numberMaxSeed, layoutSeed setLayoutSeed , rule rule, String morp , double prob 
			 , boolean stillAlive 
			) {
		this.numberMaxSeed = numberMaxSeed ;
		this.setLayoutSeed = setLayoutSeed ;
		this.rule = rule ;
		this.morp = morp ;
		this.prob = prob ;
		this.stillAlive = stillAlive ;
	}
	
	
	@Override
	public void generateNodeRule(int step) {

		System.out.println(netGraph.getNodeCount());
		// set seed nodes ( only first step )
		setSeedNodes(step, numberMaxSeed, setLayoutSeed);
				
		// CREATE LIST OF SEEDGRAD 
		ArrayList<Node> listNodeSeedGrad = 	gsAlgoToolkit.getListNodeAttribute(netGraph, "seedGrad" , 1 );		// System.out.println("number of seed " + listNodeSeedGrad.size() + " " + listNodeSeedGrad);

		for ( Node nNet : listNodeSeedGrad ) {
		
			Node nGs = gsGraph.getNode(nNet.getId());
			int nDgsDegree = nGs.getDegree() ;
			
			if ( nDgsDegree > 8)
				nDgsDegree = 8 ;
			
			// list of neig
			ArrayList <String> listNeigString = gsAlgoToolkit.getListNeighborString ( gsGraph, nNet.getId() ) ;
		
			ArrayList<Node> listNeigNode = new ArrayList<Node>();
			for ( String s : listNeigString) 
				listNeigNode.add(gsGraph.getNode(s)) ;
		
		//	System.out.println(listNeigString);
			ArrayList<String> listNodeAlreadyCecked = new ArrayList<String>() ;
		
			double delta = gsAlgoToolkit.getAutoCorrelationAttrInListNeig(gsGraph, listNeigString, nGs, morp ,true) ;
			int numberNewNodes = 0 ;
			
//			System.out.println( "delta " + delta );
			
			if ( delta <= 0 )
				continue ;
			
			else if ( delta > 0 ) 
				numberNewNodes = (int) (Math.round( delta * nDgsDegree)  ) ;
			
			if ( numberNewNodes == 0) {
				if ( stillAlive )
					continue ;
				else if ( stillAlive == false )
					nNet.setAttribute("seedGrad", 0 );
			}
//			System.out.println(nDgsDegree);
			System.out.println( "numberNewNodes " + numberNewNodes );
			
			for ( int x = 0 ; x < numberNewNodes ; x++ ) {
				
				String idCouldAdded = null ; 
				Node nodeCouldAdded = null ;
				
				switch (rule) {
				case random:
					while ( !listNodeAlreadyCecked.contains(idCouldAdded)) {
						idCouldAdded = getRandomNode(listNeigNode);
						listNodeAlreadyCecked.add(idCouldAdded);
					}
					break;

				case maxValue: {
					ArrayList<String> listIdNodeSorted = gsAlgoToolkit.getSortedListNodeAtr ( listNeigNode, morp );	
					idCouldAdded = listIdNodeSorted.get( x  );				//	System.out.println(idCouldAdded);
					}
					break;
				
				case minValue :
					idCouldAdded = getNodeSmallest(morp, listNeigNode);
					break ;
				}
				
				// there isn't node
				try {
					netGraph.addNode(idCouldAdded);
					nodeCouldAdded = netGraph.getNode(idCouldAdded); 			//	System.out.println(idCouldAdded);
					nodeCouldAdded.addAttribute("seedGrad", 1);
					nNet.setAttribute("seedGrad", 0 );
					
					// set coordinate
					Node nFrom = gsGraph.getNode(idCouldAdded); 
					Node nTo = nodeCouldAdded ;
					gsAlgoToolkit.setNodeCoordinateFromNode(gsGraph, netGraph, nFrom, nTo);
				}
				
				// if node already exist 
				catch (org.graphstream.graph.IdAlreadyInUseException e) { 		//	System.out.println(e.getMessage());
					nodeCouldAdded = netGraph.getNode(idCouldAdded); 			//	System.out.println(idCouldAdded);
					nodeCouldAdded.addAttribute("seedGrad", 1);
					nNet.setAttribute("seedGrad", 0);
				}
			
			}
			
		}
					
				
		
	}

	@Override
	public void removeNodeRule(int step) {
		// TODO Auto-generated method stub
		
	}

}
