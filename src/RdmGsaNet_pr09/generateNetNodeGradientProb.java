package RdmGsaNet_pr09;

import java.util.ArrayList;

import org.graphstream.graph.Node;

import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNet_pr09.generateNetNodeGradient.layoutSeed;
import RdmGsaNet_pr09.generateNetNodeGradient.rule;

public class generateNetNodeGradientProb  extends generateNetNodeGradient implements generateNetNode_Inter {
	
	// COSTRUTOR
	public generateNetNodeGradientProb ( int numberMaxSeed, layoutSeed setLayoutSeed , rule rule, String morp , double prob , 
			boolean stillAlive ) {
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
		ArrayList<String> listNodeSeedGrad = 	gsAlgoToolkit.getListStringNodeAttribute(netGraph, "seedGrad" , 1 );			//		System.out.println("number of seed " + listNodeSeedGrad.size() + " " + listNodeSeedGrad);		// System.out.println("number of seed " + listNodeSeedGrad.size() + " " + listNodeSeedGrad);

		for ( String idNode : listNodeSeedGrad ) {
			
			Node nGs = gsGraph.getNode(idNode);
			Node nNet = netGraph.getNode(idNode);
			
			int nGsDegree  = nGs.getDegree() ;			//			System.out.println("degree gs , id " + nGs.getId() + " " + nGsDegree);
			int nNetDegree = nNet.getDegree() ;			//			System.out.println("degree net, id " + nNet.getId() + " " + nNetDegree);
				
			// list of neig
			ArrayList <String> listNeigGsStr  = gsAlgoToolkit.getListNeighborStr ( gsGraph,  idNode) ;		//	System.out.println("listNeigGsString of node " + nGs.getId() + " " + listNeigGsStr);
			ArrayList <String> listNeigNetStr = gsAlgoToolkit.getListNeighborStr ( netGraph, idNode) ;		//	System.out.println("listNeigNetString of node " + nNet.getId() + " "  + listNeigNetStr);
			
			ArrayList <String> listNeigGsStrSeed = new ArrayList<String>();
			ArrayList <String> listNeigGsStrNotSeed = new ArrayList<String>();//			System.out.println(netGraph.getNodeSet() ) ;
							
			// create list of nodes with value greater of nNet 
			ArrayList<String> listNeigValMaxStr = createListNodeMaxNeigStr( gsGraph, nNet , morp); 		// 	System.out.println("listIdNeigValMax of " + nNet.getId() + " " + listNeigValMax);
		
			int numberMaxNewNodes = listNeigValMaxStr.size();										//	System.out.println("numberMaxNewNodes " + numberMaxNewNodes);
			int numberNewNodes = gsAlgoToolkit.getBinomial(numberMaxNewNodes, prob);			//	System.out.println("numberNewNodes " + numberNewNodes);
						
			if ( numberNewNodes == 0) {	
				if ( stillAlive )
					continue ;
				else if ( stillAlive == false )
					nNet.setAttribute("seedGrad", 0 );
			}		
					
			for ( int x = 0 ; x < numberNewNodes ; x++ ) {

				// get id node maybe add
				String idCouldAdded = getIdCouldAdded( rule, listNeigGsStr , x ) ; 
				Node nodeCouldAdded = null ;
			
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
					nodeCouldAdded.addAttribute("seedGrad", 0);
					nNet.setAttribute("seedGrad", 1);
				}
			}
		}	
	}
	

	@Override
	public void removeNodeRule(int step) {
		// TODO Auto-generated method stub
		
	} 

}
