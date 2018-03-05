package RdmGsaNet_pr09;

import java.util.ArrayList;
import java.util.Arrays;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import RdmGsaNetAlgo.gsAlgoToolkit;

public class generateNetNodeGradientProbDeltaControlSeed extends generateNetNodeGradient implements generateNetNode_Inter {
	
	protected boolean controlSeed ;
	
	// COSTRUTOR
	public generateNetNodeGradientProbDeltaControlSeed(int numberMaxSeed, layoutSeed setLayoutSeed , rule rule, String morp , double prob 
			 , boolean stillAlive , boolean controlSeed
			) {
		this.numberMaxSeed = numberMaxSeed ;
		this.setLayoutSeed = setLayoutSeed ;
		this.rule = rule ;
		this.morp = morp ;
		this.prob = prob ;
		this.stillAlive = stillAlive ;
		this.controlSeed = controlSeed ;
	}
	
	@Override
	public void generateNodeRule(int step) {

		//	System.out.println(netGraph.getNodeCount());
	
		// set seed nodes ( only first step )
		setSeedNodes(step, numberMaxSeed, setLayoutSeed);
				
		// CREATE LIST OF SEEDGRAD 
		ArrayList<String> listNodeSeedGrad = 	gsAlgoToolkit.getListStringNodeAttribute(netGraph, "seedGrad" , 1 );			//		System.out.println("number of seed " + listNodeSeedGrad.size() + " " + listNodeSeedGrad);

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
			
			for ( String id : listNeigGsStr ) {														//	System.out.println(id);
				
				try {
					Node localNetNode = netGraph.getNode(id) ;
					int seed = localNetNode.getAttribute("seedGrad");		//System.out.println(seed);
				
					if ( seed == 1 )
						listNeigGsStrSeed.add(id);
					else if ( seed != 1 )
						listNeigGsStrNotSeed.add(id);
				} 
				catch (java.lang.NullPointerException e) {	
				}
			}																							//	System.out.println("listNeigGsStrSeed " + listNeigGsStrSeed);
			
			ArrayList<String> listForDelta = new ArrayList<String>();									//	System.out.println(listNeigGsStr);
			if ( controlSeed ) {
				for ( String s : listNeigGsStr ) {
					if ( !listNeigGsStrSeed.contains(s))
						listForDelta.add(s);
				}					
			}
			else if ( !controlSeed ) 
				listForDelta = listNeigGsStr ;															//		System.out.println(idNode + listForDelta);
			
			double delta = gsAlgoToolkit.getValStad ( gsGraph , listForDelta, nGs, morp , true , 0 ) ;		//	System.out.println("delta " + delta ) ; 	System.out.println(listForDelta.size());
			int numberNewNodes = 0 , numberMaxNewNodes = 0  ;
			
			if ( delta <= 0 )
				continue ;		
			else if ( delta >= 1 )
				 numberMaxNewNodes = listForDelta.size() ;	
			else if ( delta > 0 && delta < 1  ) 
				numberMaxNewNodes = (int) (Math.round( delta * listForDelta.size() )  ) ;								//			System.out.println(netGraph.getNodeCount());

			numberNewNodes = gsAlgoToolkit.getBinomial(numberMaxNewNodes, prob); //		System.out.println( "numberNewNodes " + numberNewNodes );
			
			if ( numberNewNodes == 0) {	
				if ( stillAlive )
					continue ;
				else if ( stillAlive == false )
					nNet.setAttribute("seedGrad", 0 );
			}	
			
			for ( int x = 0 ; x < numberNewNodes ; x++ ) {

				// get id node maybe add
				String idCouldAdded = getIdCouldAdded(rule, listForDelta, x) ; 
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
