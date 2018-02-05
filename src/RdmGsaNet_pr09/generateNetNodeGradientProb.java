package RdmGsaNet_pr09;

import java.util.ArrayList;
import org.graphstream.graph.Node;
import RdmGsaNetAlgo.gsAlgoToolkit;

public class generateNetNodeGradientProb extends generateNetNodeGradient implements generateNetNode_Inter {
	

	// COSTRUTOR
	public generateNetNodeGradientProb ( int numberMaxSeed, layoutSeed setLayoutSeed , rule rule, String morp , double prob ) {
		this.numberMaxSeed = numberMaxSeed ;
		this.setLayoutSeed = setLayoutSeed ;
		this.rule = rule ;
		this.morp = morp ;
		this.prob = prob ;
	}
	
	@Override
	public void generateNodeRule(int step) {
		
		// set seed nodes 
		setSeedNodes(step, numberMaxSeed, setLayoutSeed);
				
		// CREATE LIST OF SEEDGRAD 
		ArrayList<Node> listNodeSeedGrad = 	gsAlgoToolkit.getListNodeAttribute(netGraph, "seedGrad" , 1 );		// System.out.println("number of seed " + listNodeSeedGrad.size() + " " + listNodeSeedGrad);

		for ( Node nNet : listNodeSeedGrad ) {		
			
			// create list of nodes with value greater of nNet 
			ArrayList<Node> listNeigValMax = createListNodeMaxNeig( gsGraph, nNet , morp); 		// 	System.out.println("listIdNeigValMax of " + nNet.getId() + " " + listNeigValMax);
		
			int numberMaxNewNodes = listNeigValMax.size();										//	System.out.println("numberMaxNewNodes " + numberMaxNewNodes);
			int numberNewNodes = gsAlgoToolkit.getBinomial(numberMaxNewNodes, prob);			//	System.out.println("numberNewNodes " + numberNewNodes);
			
			for ( int x = 1 ; x <= numberNewNodes ; x++ ) {
			
				String idCouldAdded = null ; 
				Node nodeCouldAdded = null ;
				
				switch (rule) {
				case random:
					idCouldAdded = getRandomNode(listNeigValMax);
					break;

				case maxValue: 
					idCouldAdded = getNodeGreater(morp, listNeigValMax);							//	System.out.println(" winner " + idTheGreater);
					break;
				
				case minValue :
					idCouldAdded = getNodeSmallest(morp, listNeigValMax);
					break ;
				}
				
				// there isn't node
				try {
					netGraph.addNode(idCouldAdded);
					nodeCouldAdded = netGraph.getNode(idCouldAdded); //	System.out.println(idCouldAdded);
					nodeCouldAdded.addAttribute("seedGrad", 1);
					nNet.setAttribute("seedGrad", 0 );
					
					// set coordinate
					Node nFrom = gsGraph.getNode(idCouldAdded); 
					Node nTo = nodeCouldAdded ;
					gsAlgoToolkit.setNodeCoordinateFromNode(gsGraph, netGraph, nFrom, nTo);
				}
				
				// if node already exist 
				catch (org.graphstream.graph.IdAlreadyInUseException e) { 	//	System.out.println(e.getMessage());
					Node nodeAlreadyExist = netGraph.getNode(idCouldAdded);
					int hasSeed = nodeAlreadyExist.getAttribute("seedGrad");	//	System.out.println(hasSeed);
					
					if ( hasSeed == 1 ) 
						continue ;  // continue or break ??
					else if ( hasSeed == 0 ) {
						nodeAlreadyExist.setAttribute("seedGrad", 1);
						nNet.setAttribute("seedGrad", 0);	
					}
				}
			}
		}	
	}

	@Override
	public void removeNodeRule(int step) {
		// TODO Auto-generated method stub
		
	}

}
