package RdmGsaNet_generateGraph;

import java.util.ArrayList;


import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNetAlgo.graphAnalysis;
import RdmGsaNetAlgo.graphInterpolation;
import RdmGsaNetAlgo.graphToolkit;
import RdmGsaNetAlgo. graphToolkit.*;
import RdmGsaNetAlgo.gsAlgoToolkit;

public class generateNetNodeBreakGridThrowSeed extends generateNetNodeBreakGrid implements generateNetNode_Inter {
	
	private  boolean isStandValAbs ;

	public generateNetNodeBreakGridThrowSeed(int numberMaxSeed , String morp , double prob , interpolation typeInterpolation ,boolean stillAlive , boolean isStandValAbs) {
		super(numberMaxSeed, morp , prob , typeInterpolation ,  stillAlive );
		this.isStandValAbs = isStandValAbs ;
	
	}

	@Override
	public void generateNodeRule(int step) {
		
	//	System.out.println("size netGraph " +  netGraph.getNodeCount() + netGraph.getNodeSet());
		sizeGridEdge = Math.pow( gsGraph.getNodeCount() , 0.5 ) - 1 ;
		int netGraphNodeCount = netGraph.getNodeCount() ;
		
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
			handleListNeigGsSeed(nodeSeed, listNeigSeed, listNeigNotSeed);															//	System.out.println("listNeigNotSeed " + listNeigNotSeed);				System.out.println("listNeigSeed " + listNeigSeed);
			
			// get list of vertex around idSeed
			listVertex = graphToolkit.getListVertexRoundCoord(elementTypeToReturn.string , netGraph, gsGraph, idSeed) ;				//	System.out.println(idSeed + " listVertex " + listVertex);
			
			// compute val Interpolated of idSeed
			valInter = graphInterpolation.computeInterpolation( listNetNodeStr, gsGraph, netGraph , idSeed , morp , listVertex );	//	System.out.println("valInter " + valInter);
			
			// create list of nodes which compute standard values 
			ArrayList<String> listForStandVal = listVertex;
		//	listForStandVal.removeAll(listNeigSeed);	
			
			double standVal = graphAnalysis.getValStad( gsGraph , listForStandVal, nodeSeed , morp , true , valInter)  ;	
			
			if ( isStandValAbs) 
				standVal = Math.abs(standVal) ;
			
			int numberMaxNewNodes = getNumberMaxNewNodes(standVal, listForStandVal , isStandValAbs )  ;							//	System.out.println("numberMaxNewNodes " + numberMaxNewNodes);
				
			int numberNewNodes = gsAlgoToolkit.getBinomial(numberMaxNewNodes, prob); 											//	System.out.println( "numberNewNodes " + numberNewNodes );				
		
			if ( stillAlive )
				if ( numberNewNodes == 0 ) 
					continue;

			for ( int x = 0 ; x < numberNewNodes ; x++ ) {
				
				String idVertex = listVertex.get(x);
				Node nodeVertex = gsGraph.getNode(idVertex);																	//	System.out.println(idVertex);
			
				// get coordinate of vertex	
				double [] vertexCoord = getVertexCoord(nodeVertex, nodeSeed) ; 
				
				// get coordinate of seed 
				double [] 	seedCoord = GraphPosLengthUtils.nodePosition(nodeSeed) ;
				
			//	System.out.println ( seedCoord[0] + " " + seedCoord[1] ) ; 
				// get dist between seed and vertex 
				double 	distSeedVertex = gsAlgoToolkit.getDistGeom(nodeVertex, nodeSeed ) ; 
			
				double[] newNodeCoord = getCoordinateNewNode(distSeedVertex, vertexCoord, seedCoord, nodeSeed, standVal) ;
				
				double xNewNode = newNodeCoord[0] , 
						yNewNode = newNodeCoord[1] ;				// System.out.println(xNewNode + " " + yNewNode);
				
				if ( xNewNode < 0 || yNewNode < 0 )
					continue ; 
				
//				String xId = Double.toString(Math.floor(xNewNode * 100 )  / 100 ) ;
//				String yId = Double.toString(Math.floor(yNewNode * 100 )  / 100 ) ;		//	System.out.println(xId);
				
				// get id node maybe add
//				String idCouldAdded = xId + "_" + yId ; 								//	System.out.println("idCouldAdded " + idCouldAdded);
				
				
				String idCouldAdded = Integer.toString(netGraph.getNodeCount() );
				
				Node nodeCouldAdded = null ;							
			
				handleNewNodeCreation(netGraph, idCouldAdded, nodeSeed, xNewNode, yNewNode)	;
				
			}
		}
	
	}

	
	
	
	
	
	
	
	
	@Override
	public void removeNodeRule(int step) {
		// TODO Auto-generated method stub
		
	}
		
	
		
	}
