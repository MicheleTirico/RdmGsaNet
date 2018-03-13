package RdmGsaNet_generateGraph;

import java.util.ArrayList;
import java.util.Iterator;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNet_mainSim.layerGs;
import RdmGsaNet_mainSim.layerNet;
import RdmGsaNet_mainSim.main;

public class generateNetNodeBreakGrid extends main {
	
	// COSTANTS	
	protected static int numberMaxSeed ; 
	protected String morp;
	protected boolean stillAlive ; 
	
	public enum interpolation { averageEdge , averageDist, sumVectors  } 
	public interpolation typeInterpolation ;
	protected double sizeGridEdge ;
	
	// probability costants 
	static double  prob = 0 ;
	protected static String setupNet = layerNet.getLayout();

	protected static Graph netGraph = layerNet.getGraph(),
							gsGraph = layerGs.getGraph();
	
	public generateNetNodeBreakGrid ( int numberMaxSeed , String morp , double prob , interpolation typeInterpolation , boolean stillAlive  ) {
		this.numberMaxSeed = numberMaxSeed ;
		this.morp = morp ;
		this.prob = prob ;
		this.typeInterpolation  = typeInterpolation  ;
		this.stillAlive = stillAlive ; 	
	}

	

// COMMON METHODS FOR ALL CLASS WITH GRADIENT APPROACH ----------------------------------------------------------------------------------------------	
	
	// handle create new node	
	protected void handleNewNodeCreation ( Graph graph , String idCouldAdded , Node nodeSeed , double xNewNode , double yNewNode ) {
		
		Node nodeCouldAdded = null ;
		// there isn't node
		try {
			netGraph.addNode(idCouldAdded);
			nodeCouldAdded = netGraph.getNode(idCouldAdded); 			//	System.out.println(idCouldAdded);
			nodeCouldAdded.addAttribute("seedGrad", 1);
			nodeSeed.setAttribute("seedGrad", 0 );
			
			// set coordinate
			nodeCouldAdded.setAttribute( "xyz", xNewNode , yNewNode, 0 );	
			}
		
		// if node already exist 
		catch (org.graphstream.graph.IdAlreadyInUseException e) { 		//
			System.out.println(e.getMessage());
			nodeCouldAdded = netGraph.getNode(idCouldAdded); 			//	System.out.println(idCouldAdded);
			nodeCouldAdded.addAttribute("seedGrad", 0 );
			nodeSeed.setAttribute("seedGrad", 1);
		}
	}
	
	// setup first step of simulation
	protected static void setStartSeed ( Graph graph , int step ,  int numberMaxAttribute , String attribute ) {

		// exit method
		if ( step != 1 )					return ;
		
		int nodeCount = netGraph.getNodeCount();
		
		if ( numberMaxSeed > nodeCount )	numberMaxSeed = nodeCount ;
		
		for ( int x = 0 ; x < numberMaxAttribute ; x++ ) 
			try {
				graph.getNode(x).addAttribute(attribute, 1);	
			} catch (java.lang.NullPointerException e) {
				continue ;
			}
		}
	
	// handle listNeigGsStrSeed ( and not seed )
	protected static void handleListNeigGsSeed ( Node nodeSeed , ArrayList<String> listNeigSeed , ArrayList<String> listNeigNotSeed ) {
			
		Iterator<Node> iter = nodeSeed.getNeighborNodeIterator() ;		
		while (iter.hasNext()) {
				 
			Node neig = iter.next() ;
			int neigValAttr = neig.getAttribute("seedGrad");
				
			if (neigValAttr == 1 )
				listNeigSeed.add(neig.getId());
			else if ( neigValAttr == 0 ) 
				listNeigNotSeed.add(neig.getId()) ;
		}
	}
	
	// control Seed methods 
	protected static int getNumberMaxNewNodes ( double standVal , ArrayList<String> listForStandVal , boolean isStandValAbs ) {
			
		int numberMaxNewNodes = 0 ;
		
		if ( isStandValAbs ) 		standVal = Math.abs(standVal) ;
			
			if ( standVal <= 0 )	
				return 0 ;		
			else if ( standVal >= 1 )
				 numberMaxNewNodes = listForStandVal.size() ;	
			else if ( standVal > 0 && standVal < 1  ) 
				numberMaxNewNodes = (int) (Math.round( standVal * listForStandVal.size() )  ) ;								//			System.out.println(netGraph.getNodeCount());

			return numberMaxNewNodes ;
		}
		
	protected double[] getVertexCoord (  Node nodeVertex  , Node nodeSeed ) {
		
		double [] 	vertexCoord = null ;
		
		try { 
			vertexCoord = GraphPosLengthUtils.nodePosition(nodeVertex) ;
		} catch (java.lang.NullPointerException e) {
			nodeSeed.addAttribute("seedGrad", 1);
			return vertexCoord ;	
		}
		return vertexCoord;
	}
	
	protected double[] getCoordinateNewNode ( double distSeedVertex , double[] vertexCoord , double[] seedCoord , Node nodeSeed , double standVal ) {
		
		double[] newNodeCoord = new double[2] ;
		double  xNewNode = 0.0 , 
				yNewNode = 0.0 ;
		try {
			if ( distSeedVertex == 0 ) {
				xNewNode = vertexCoord[0] ;
				yNewNode = vertexCoord[1] ;
			}
			else {
				double 	a2 = Math.pow(vertexCoord[0] - seedCoord[0] , 2 ) ,
						c2 = Math.pow(vertexCoord[1] - seedCoord[1] , 2 ) ;
				
				double val1 , val2 , distMax ;
	
				val1 = Math.pow(1 + a2 / c2 , 0.5);
				val2 = Math.pow(1 + c2 / a2 , 0.5);
			
				if ( vertexCoord[0] == seedCoord[0] || vertexCoord[1] == seedCoord[1]  )
					distMax = 1 ;
				else 
					distMax = Math.max(val1, val2);
				
				if ( distMax > Math.pow(2, 0.5) )
					distMax = Math.pow(2, 0.5) - 0.01 ;
				
				if  ( distMax == 0 ) {
					nodeSeed.setAttribute("seedGrad", 1);	
					return newNodeCoord ; 									//	System.out.println(distMax);
				}
				
				double coefDist = 0 ;
				if ( standVal >= 1) 
					coefDist =  distMax;
				else if (standVal < 1 )
					coefDist = standVal * distMax ;							//		System.out.println("coefDist " + coefDist) ;
				
				xNewNode = vertexCoord[0] + distSeedVertex * ( vertexCoord[0] - seedCoord[0]) / coefDist  ; 
				yNewNode = vertexCoord[1] + coefDist * ( vertexCoord[1] - seedCoord[1] ) / distSeedVertex  ;
		 					
				if ( xNewNode < 0 )		xNewNode = 0;
				if ( yNewNode < 0 )		yNewNode = 0 ;
				
				if ( xNewNode > sizeGridEdge )	xNewNode = sizeGridEdge ;
				if ( yNewNode > sizeGridEdge )	yNewNode = sizeGridEdge ;
			
				if (  xNewNode -  vertexCoord[0] >= 1 ) 
					xNewNode = vertexCoord[0] + 1 ;
	
				if (  yNewNode -  vertexCoord[1] >= 1 ) 
					yNewNode = vertexCoord[1] + 1 ;
			
			}
		} catch (java.lang.NullPointerException e) {
			xNewNode = -1 ;
			yNewNode = -1 ;
		}
		newNodeCoord[0] = xNewNode ;
		newNodeCoord[1] = yNewNode ;

		return newNodeCoord ;
	}
		
}
