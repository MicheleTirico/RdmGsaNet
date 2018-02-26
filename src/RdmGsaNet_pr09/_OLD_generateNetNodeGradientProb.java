package RdmGsaNet_pr09;

import java.util.ArrayList;
import org.graphstream.graph.Node;
import RdmGsaNetAlgo.gsAlgoToolkit;

public class _OLD_generateNetNodeGradientProb extends generateNetNodeGradient implements generateNetNode_Inter {
	
	// COSTRUTOR
	public _OLD_generateNetNodeGradientProb ( int numberMaxSeed, layoutSeed setLayoutSeed , rule rule, String morp , double prob , boolean stillAlive ) {
		this.numberMaxSeed = numberMaxSeed ;
		this.setLayoutSeed = setLayoutSeed ;
		this.rule = rule ;
		this.morp = morp ;
		this.prob = prob ;
		this.stillAlive = stillAlive ;
	}
	
	@Override
	public void generateNodeRule(int step) {
		
		// set seed nodes ( only first step )
		setSeedNodes(step, numberMaxSeed, setLayoutSeed);
				
		// CREATE LIST OF SEEDGRAD 
		ArrayList<Node> listNodeSeedGrad = 	gsAlgoToolkit.getListNodeAttribute(netGraph, "seedGrad" , 1 );		// System.out.println("number of seed " + listNodeSeedGrad.size() + " " + listNodeSeedGrad);

		for ( Node nNet : listNodeSeedGrad ) {		
			
			// create list of nodes with value greater of nNet 
			ArrayList<Node> listNeigValMax = createListNodeMaxNeig( gsGraph, nNet , morp); 		// 	System.out.println("listIdNeigValMax of " + nNet.getId() + " " + listNeigValMax);
		
			ArrayList<String> listNodeAlreadyCecked = new ArrayList<String>() ;
			
			int numberMaxNewNodes = listNeigValMax.size();										//	System.out.println("numberMaxNewNodes " + numberMaxNewNodes);
			int numberNewNodes = gsAlgoToolkit.getBinomial(numberMaxNewNodes, prob);			//	System.out.println("numberNewNodes " + numberNewNodes);
						
			if ( numberNewNodes == 0) {
				if ( stillAlive )
					continue ;
				else if ( stillAlive == false )
					nNet.setAttribute("seedGrad", 0 );
			}		
					
			for ( int x = 0 ; x < numberNewNodes ; x++ ) {
			
				String idCouldAdded = null ; 
				Node nodeCouldAdded = null ;
				
				switch (rule) {
				case random:
					while ( !listNodeAlreadyCecked.contains(idCouldAdded)) {
						idCouldAdded = getRandomNode(listNeigValMax);
						listNodeAlreadyCecked.add(idCouldAdded);
					}
					break;

				case maxValue: {
					ArrayList<String> listIdNodeSorted = gsAlgoToolkit.getSortedListNodeAtr ( listNeigValMax, morp );	
					idCouldAdded = listIdNodeSorted.get( x  );				//	System.out.println(idCouldAdded);
					}
					break;
				
				case minValue :
					idCouldAdded = getNodeSmallest(morp, listNeigValMax);
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
					
					/* complicato, ma da la stessa cosa ?
				 	Node nodeAlreadyExist = netGraph.getNode(idCouldAdded);
					 
					int hasSeed = nodeAlreadyExist.getAttribute("seedGrad");	//	System.out.println(hasSeed);
					
					if ( hasSeed == 1 ) {
						nNet.setAttribute("seedGrad", 0);
						//	continue ;  
					}
					else if ( hasSeed == 0 ) {
						nodeAlreadyExist.setAttribute("seedGrad", 1);
						nNet.setAttribute("seedGrad", 0);	
					}
					*/
					
				}
			}
		}	
	}
	

	@Override
	public void removeNodeRule(int step) {
		// TODO Auto-generated method stub
		
	}

}
