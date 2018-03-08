package RdmGsaNet_generateGraph;

import java.util.ArrayList;


import org.graphstream.graph.Node;

import RdmGsaNetAlgo.graphInterpolation;
import RdmGsaNetAlgo.graphToolkit;
import RdmGsaNetAlgo. graphToolkit.*;

public class generateNetNodeBreakGridThrowSeed extends generateNetNodeBreakGrid implements generateNetNode_Inter {

	public generateNetNodeBreakGridThrowSeed(int numberMaxSeed , String morp , interpolation typeInterpolation) {
		super(numberMaxSeed, morp , typeInterpolation);	
	}

	@Override
	public void generateNodeRule(int step) {
		
		System.out.println("size netGraph " +  netGraph.getNodeCount() + netGraph.getNodeSet());
		sizeGridEdge = Math.pow( gsGraph.getNodeCount() , 0.5 ) - 1 ;
		
		setStartSeed(netGraph, step ,  numberMaxSeed , "seedGrad" ) ;
		
		// create list of seedGrad
		ArrayList<String> listNodeSeedGrad = graphToolkit.getListElementAttribute(netGraph, element.node, elementTypeToReturn.string, "seedGrad", 1 ) ;		//	System.out.println("number of seed " + listNodeSeedGrad.size() + " " + listNodeSeedGrad);

		// create list of nodes 
		ArrayList<String> listNetNodeStr = graphToolkit.getListElement(netGraph, element.node , elementTypeToReturn.string); 								//	System.out.println(listNetNodeStr);
		
		for ( String idSeed : listNodeSeedGrad ) {
			
			Node nodeSeed = netGraph.getNode(idSeed);
			double valInter ;
			ArrayList <String> 	listNeigSeed = new ArrayList<String>() ,
								listNeigNotSeed = new ArrayList<String>() ,
								listVertex = new ArrayList<String> (4);			

			// update listNeigSeed and listNeigNotSeed
			handleListNeigGsSeed(nodeSeed, listNeigSeed, listNeigNotSeed);									//	System.out.println("listNeigNotSeed " + listNeigNotSeed);				System.out.println("listNeigSeed " + listNeigSeed);
			
			// get list of vertex around idSeed
			listVertex = graphToolkit.getListVertexRoundCoord(elementTypeToReturn.string , netGraph, gsGraph, idSeed) ;					//	System.out.println(idSeed + " listVertex " + listVertex);
			
			// compute val Interpolated of idSeed
			valInter = graphInterpolation.computeInterpolation( listNetNodeStr, gsGraph, netGraph , idSeed , morp , listVertex );						System.out.println("valInter " + valInter);
			
			/*
			
			double valInter = computeInterpolation(listNetNodeStr, gsGraph, netGraph , idSeed , morp , listVertex );		//				System.out.println("valInter " + valInter);
							
			ArrayList<String> listForDelta = listVertex;
			listForDelta.removeAll(listNeigSeed);															//					System.out.println( idSeed + " listForDelta " + listForDelta);
			
			double delta = gsAlgoToolkit.getValStad( gsGraph , listForDelta, nodeSeed , morp , true , valInter)  ;		//										System.out.println("delta " + delta ) ; 
			
		//	delta = Math.abs(delta) ;
			
			//	System.out.println(listForDelta.size());
		
			int numberMaxNewNodes = getNumberMaxNewNodes(delta, listForDelta , true )  ;							//	
//			System.out.println("numberMaxNewNodes " + numberMaxNewNodes);
			
			int numberNewNodes = gsAlgoToolkit.getBinomial(numberMaxNewNodes, prob); 						//	
//			System.out.println( "numberNewNodes " + numberNewNodes );				
		
//			handleStillAlive(numberNewNodes, controlSeed, nodeSeed);
			
			if ( stillAlive )
				if ( numberNewNodes == 0 ) 
					continue;

			*/
			
			
//			for ( int x = 0 ; x < numberNewNodes ; x++ ) {
				
//				handleNewNodeCreation(netGraph, idCouldAdded, nodeSeed, xNewNode, yNewNode)	}
			}
	
	}

	
	
	
	
	
	
	
	
	@Override
	public void removeNodeRule(int step) {
		// TODO Auto-generated method stub
		
	}
		
	
		
	}
