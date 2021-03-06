package RdmGsaNet_generateGraph;

import java.util.ArrayList;

import org.graphstream.graph.Node;

import com.sun.org.apache.xml.internal.utils.ThreadControllerWrapper;

import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNet_generateGraph.generateNetNode.layoutSeed;
import RdmGsaNet_generateGraph.generateNetNode.rule;

public class generateNetNodeGradientOnlyOne extends generateNetNodeGradient implements generateNetNode_Inter {

	
	
	// COSTRUTOR
	public generateNetNodeGradientOnlyOne ( int numberMaxSeed, layoutSeed setLayoutSeed , rule ruleType, String morp  ) {
		this.numberMaxSeed = numberMaxSeed ;
		this.setLayoutSeed = setLayoutSeed ;
		this.ruleType = ruleType ;
		this.morp = morp ;
	}

	@Override
	public void generateNodeRule(int step) {

		// set seed nodes 
		generateNetNode.setSeedNodes(step, numberMaxSeed, setLayoutSeed);
		
		// CREATE LIST OF SEEDGRAD 
		ArrayList<Node> listNodeSeedGrad = 	gsAlgoToolkit.getListNodeAttribute(netGraph, "seedGrad" , 1 );		// System.out.println("number of seed " + listNodeSeedGrad.size() + " " + listNodeSeedGrad);

		for ( Node nNet : listNodeSeedGrad ) {
			
			// create list of nodes with value greater of nNet 
			ArrayList<Node> listNeigValMax = createListNodeMaxNeig( gsGraph, nNet , morp); 		// 	System.out.println("listIdNeigValMax of " + nNet.getId() + " " + listNeigValMax);
			
			String idCouldAdded = null ; 
			
			// get neig could be added
			switch (ruleType) {
			case random:
				idCouldAdded = getRandomNode(listNeigValMax);
				break;

			case maxValue: 
				idCouldAdded = getNodeGreater(morp, listNeigValMax);							//	System.out.println(" winner " + idTheGreater);
				break;
			}

			Node nodeCouldAdded ;
			
			// there isn't node
			try { 							
				netGraph.addNode(idCouldAdded);
				nodeCouldAdded = netGraph.getNode(idCouldAdded);

				nodeCouldAdded.addAttribute("seedGrad", 1);
				nNet.setAttribute("seedGrad", 0 );
				
				// set coordinate
				Node nFrom = gsGraph.getNode(idCouldAdded); 
				Node nTo = nodeCouldAdded ;
				gsAlgoToolkit.setNodeCoordinateFromNode(gsGraph, netGraph, nFrom, nTo);
			}	// if already exist node
			catch (org.graphstream.graph.IdAlreadyInUseException e) { 	//	System.out.println(e.getMessage());
				Node nodeAlreadyExist = netGraph.getNode(idCouldAdded);
				int hasSeed = nodeAlreadyExist.getAttribute("seedGrad");	//	System.out.println(hasSeed);
				
				if ( hasSeed == 1 ) 
					continue;
				else if ( hasSeed == 0 ) {
					nodeAlreadyExist.setAttribute("seedGrad", 1);
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



