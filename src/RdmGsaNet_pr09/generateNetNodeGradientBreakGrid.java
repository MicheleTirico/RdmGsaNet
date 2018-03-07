package RdmGsaNet_pr09;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import RdmGsaNetAlgo.gsAlgoToolkit;

public class generateNetNodeGradientBreakGrid extends generateNetNodeGradient implements generateNetNode_Inter {
		
	protected boolean controlSeed ;

		protected enum interpolation { averageEdge , averageDist } 
		protected interpolation typeInterpolation ;
		protected double sizeGridEdge ;
		
		// COSTRUTOR
		public generateNetNodeGradientBreakGrid (int numberMaxSeed, layoutSeed setLayoutSeed , rule rule, String morp , double prob 
				 , boolean stillAlive , interpolation typeInterpolation 
				) {
			this.numberMaxSeed = numberMaxSeed ;
			this.setLayoutSeed = setLayoutSeed ;
			this.rule = rule ;
			this.morp = morp ;
			this.prob = prob ;
			this.stillAlive = stillAlive ;
			this.typeInterpolation  = typeInterpolation  ;
			
			
		}

		@Override
		public void generateNodeRule(int step) {
			System.out.println("size netGraph " +  netGraph.getNodeCount() + netGraph.getNodeSet());
			sizeGridEdge = Math.pow( gsGraph.getNodeCount() , 0.5 ) - 1 ;
			
			// set seed nodes ( only first step )
			setSeedNodes(step, numberMaxSeed, setLayoutSeed);
					
			// CREATE LIST OF SEEDGRAD 
			ArrayList<String> listNodeSeedGrad = gsAlgoToolkit.getListStringNodeAttribute(netGraph, "seedGrad" , 1 );			//		
			System.out.println("number of seed " + listNodeSeedGrad.size() + " " + listNodeSeedGrad);

			ArrayList<String> listNetNodeStr = new ArrayList<String>();
		
			for ( Node n : netGraph.getEachNode()) 
				listNetNodeStr.add(n.getId()) ;
			
			for ( String idSeed : listNodeSeedGrad ) {
				
//				System.out.println("idSeed " + idSeed);
				Node nodeSeed = netGraph.getNode(idSeed);
						
				ArrayList <String> listNeigSeed = new ArrayList<String>();
				ArrayList <String> listNeigNotSeed = new ArrayList<String>();									//	System.out.println(netGraph.getNodeSet() ) ;

				handleListNeigGsSeed(nodeSeed, listNeigSeed, listNeigNotSeed);									//	System.out.println("listNeigNotSeed " + listNeigNotSeed);				System.out.println("listNeigSeed " + listNeigSeed);
				
				ArrayList<String> listVertex = new ArrayList<String> (4);
				listVertex = gsAlgoToolkit.getListVertexRoundCoord(netGraph, gsGraph, idSeed) ;					//	
//				System.out.println(idSeed + " listVertex " + listVertex);
				
				double valInter = computeInterpolation(listNetNodeStr, gsGraph, netGraph , idSeed , morp , listVertex );		//				System.out.println("valInter " + valInter);
								
				ArrayList<String> listForDelta = listVertex;
				listForDelta.removeAll(listNeigSeed);															//					System.out.println( idSeed + " listForDelta " + listForDelta);
				
//				for ( String s : listForDelta)			System.out.println(s + " " + morp + " " +  gsGraph.getNode(s).getAttribute(morp));
				
				double delta = gsAlgoToolkit.getValStad( gsGraph , listForDelta, nodeSeed , morp , true , valInter)  ;		//										System.out.println("delta " + delta ) ; 
				
			//	delta = Math.abs(delta) ;
				
				//	System.out.println(listForDelta.size());
			
				int numberMaxNewNodes = getNumberMaxNewNodes(delta, listForDelta , true )  ;							//	
//				System.out.println("numberMaxNewNodes " + numberMaxNewNodes);
				
				int numberNewNodes = gsAlgoToolkit.getBinomial(numberMaxNewNodes, prob); 						//	
//				System.out.println( "numberNewNodes " + numberNewNodes );				
			
//				handleStillAlive(numberNewNodes, controlSeed, nodeSeed);
				
				if ( stillAlive )
					if ( numberNewNodes == 0 ) 
						continue;
					
				for ( int x = 0 ; x < numberNewNodes ; x++ ) {
					
					String idVertex = listVertex.get(x);
					Node nodeVertex = gsGraph.getNode(idVertex);
					// System.out.println(idVertex);
				
					// get coordinate new node	
					
					double [] 	vertexCoord = null ;
					
					try { 
						vertexCoord = GraphPosLengthUtils.nodePosition(nodeVertex) ;
					} catch (java.lang.NullPointerException e) {
						nodeSeed.addAttribute("seedGrad", 1);
						continue ;	
					}
					double [] 	seedCoord = GraphPosLengthUtils.nodePosition(nodeSeed) ;
					
					double 	distSeedVertex = gsAlgoToolkit.getDistGeom(nodeVertex, nodeSeed) ; 
				
					double  xNewNode = 0.0 , 
							yNewNode = 0.0 ;
			 		
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
							continue ; 
						}
						
						double coefDist = 0 ;
						if ( delta >= 1) 
							coefDist =  distMax;
						else if (delta < 1 )
							coefDist = delta * distMax ;							//		System.out.println("coefDist " + coefDist) ;
						
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
					
					String xId = Double.toString(Math.floor(xNewNode * 100 )  / 100 ) ;
					String yId = Double.toString(Math.floor(yNewNode * 100 )  / 100 ) ;		//	System.out.println(xId);
					
					// get id node maybe add
					String idCouldAdded = xId + "_" + yId ; 								//	System.out.println("idCouldAdded " + idCouldAdded);
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
					catch (org.graphstream.graph.IdAlreadyInUseException e) { 		//System.out.println(e.getMessage());
						nodeCouldAdded = netGraph.getNode(idCouldAdded); 			//	System.out.println(idCouldAdded);
						nodeCouldAdded.addAttribute("seedGrad", 0 );
						nodeSeed.setAttribute("seedGrad", 1);
					}
				}
			}
		}
					

		

		@Override
		public void removeNodeRule(int step) {
			// TODO Auto-generated method stub
			
		}
		
		
		

// COMPUTE INTERPOLATION ----------------------------------------------------------------------------------------------------------------------------
		private double computeInterpolation ( ArrayList<String> listNetNodeStr ,  Graph graph0 , Graph graph1, String idNode , String attribute , ArrayList<String> listVertex ) {
			
//			Node n0 = graph0.getNode(idNode);
//			Node n1 = graph1.getNode(idNode);
//			ArrayList<String> listVertex = gsAlgoToolkit.getListVertex(n0 , n1 );				//	System.out.println(idNode);			System.out.println(listVertex);
			
			double[] nodeCoord = gsAlgoToolkit.getCoordinateOfNodeStr ( idNode ) ; 				// 	System.out.println(nodeCoord[0] + " " + nodeCoord[1]);
			double nodeX = nodeCoord[0] , nodeY = nodeCoord[1];
			
			double minX = 1000000000 , minY =  1000000000 , maxX = -1 , maxY = -1 ;
			
			for ( String idVertex : listVertex ) {
				
				double[] vertexCoord = gsAlgoToolkit.getCoordinateOfNodeStr ( idVertex );
				double vertexX = vertexCoord[0] , vertexY = vertexCoord[1];
				
				if ( vertexX <=  minX )
					minX = vertexX ;
				if ( vertexX >=  maxX )
					maxX = vertexX ;
				
				if ( vertexY <=  minY )
					minY = vertexY ;
				if ( vertexY >=  maxY )
					maxY = vertexY  ;
			}																									//	System.out.println(minX +" "+ minY +" "+ maxX +" "+ maxY);
			
			double 	distX = Math.abs(nodeX - minX) ,
					distY = Math.abs(nodeY - minY) ;															//	System.out.println("distX " + distX);//			System.out.println("distY " + distY);
			
			// list val = val00 , val01 , val10 ,val11 	
			double[] valArr = getCeckedAveValVertex(listNetNodeStr, graph0, attribute, minX, minY, maxX, maxY); 				//	System.out.println(valArr[0] + " " + valArr[1] + " " + valArr[2] + " "+ valArr[3] + " " );
			
			double 	aveX0 = Math.abs(valArr[0] - valArr[1]) * distX + Math.min(valArr[0] , valArr[1] ) ,
					aveX1 = Math.abs(valArr[2] - valArr[3]) * distX + Math.min(valArr[2] , valArr[3] ) ,
					aveY0 = Math.abs(valArr[0] - valArr[2]) * distY + Math.min(valArr[0] , valArr[2] ) ,
					aveY1 = Math.abs(valArr[1] - valArr[3]) * distY + Math.min(valArr[1] , valArr[3] ) ;		//	System.out.println(aveX0 = Math.abs(valArr[0] - valArr[1]) / Math.min(valArr[0] , valArr[1] ));
			
			double[] aveVal = new double[4] ; 
		
			aveVal[0] = aveX0;
			aveVal[1] = aveX1;
			aveVal[2] = aveY0;
			aveVal[3] = aveY1;																					//	for ( double val : aveVal ) System.out.println(val);
			
			return Arrays.stream(aveVal).average().getAsDouble();			
		}
		
		private static double[] getCeckedAveValVertex ( ArrayList<String> listNetNodeStr , Graph graph , String attribute, double minX , double minY , double maxX, double maxY ) {
						
			double[] arrVal = new double[4];
			double sizeMax = Math.pow( graph.getNodeCount() , 0.5 ) ;
			
			if ( maxX >= sizeMax ) {
				minX = minX - 1 ;
				maxX = maxX - 1 ;
			}
			if ( maxY >= sizeMax ) {
				minY = minY - 1 ;
				maxY = maxY - 1 ;
			}
//			System.out.println(listNetNodeStr);
/*
			Node
			node00 = getNodeCechedType(listNetNodeStr, graph, minX, minY),
					node10 = getNodeCechedType(listNetNodeStr, graph, maxX, minY),
							node01 = getNodeCechedType(listNetNodeStr, graph, minX, maxY),
									node11 = getNodeCechedType(listNetNodeStr, graph, maxX, maxY)	;
			
*/
			Node 	node00 = null , 
					node10 = null ,
					node01 = null , 
					node11 = null ; 
												
			String 	idNode00 = (int) minX + "_" + (int) minY ,
					idNode10 = (int) maxX + "_" + (int) minY ,
					idNode01 = (int) minX + "_" + (int) maxY ,
					idNode11 = (int) maxX + "_" + (int) maxY ;
			
				node00 = graph.getNode(idNode00) ;
				node10 = graph.getNode(idNode10) ;
				node01 = graph.getNode(idNode01) ; 
				node11 = graph.getNode(idNode11) ; 
										
			
				
				
			try {	
				arrVal[0] = node00.getAttribute(attribute) ;
				arrVal[1] = node01.getAttribute(attribute) ;
				arrVal[2] = node10.getAttribute(attribute) ;
				arrVal[3] = node11.getAttribute(attribute) ;
			} catch (java.lang.NullPointerException e) 	{
				arrVal[0] = 0 ;
				arrVal[1] = 0 ;
				arrVal[2] = 0 ;
				arrVal[3] = 0 ;
				
				System.out.println(idNode00);
				System.out.println(idNode10);
				System.out.println(idNode01);
				System.out.println(idNode11);
				
			}
			/*
			try {	arrVal[0] = node00.getAttribute(attribute) ;	} catch (java.lang.NullPointerException e) 				{		arrVal[0] = 0 ; System.out.println("idVertex " + idNode00); }
			try {	arrVal[1] = node01.getAttribute(attribute) ;	} catch (java.lang.NullPointerException e) 				{		arrVal[1] = 0 ; System.out.println("idVertex " + idNode10); }
			try	{	arrVal[2] = node10.getAttribute(attribute) ;	} catch (java.lang.NullPointerException e) 				{		arrVal[2] = 0 ; System.out.println("idVertex " + idNode01); }							//	System.out.println( maxX + "_" + minY );
			try {	arrVal[3] = node11.getAttribute(attribute) ;	} catch (java.lang.NullPointerException e) 				{		arrVal[3] = 0 ; System.out.println("idVertex " + idNode11); }
			*/
			
			
			
					
			for ( int pos= 0 ; pos < 4 ; pos++ ) {
				if ( arrVal[pos] <= 0 )
					arrVal[pos] = 0 ;
				if ( arrVal[pos] >= 1 )
					arrVal[pos] = 1 ;																			//	System.out.println( arrVal[pos]);
			}
			return arrVal;
		}

		private static Node getNodeCechedType ( ArrayList<String> listNetNodeStr , Graph graph , double valX , double valY ) {
			Node node ;
			String nodeStr = valX + "_" + valY ;
			
			if ( listNetNodeStr.contains(nodeStr) )
				return node = graph.getNode(nodeStr) ;
				
			else {
				nodeStr = (int) valX + "_" + valY ;
				if ( listNetNodeStr.contains(nodeStr) )
					return node = graph.getNode(nodeStr) ;
				else {
					nodeStr =  valX + "_" + (int) valY ;
					if ( listNetNodeStr.contains(nodeStr) )
						return node = graph.getNode(nodeStr) ;
					else {
						nodeStr =(int) valX + "_" + (int) valY ;
						return node = graph.getNode(nodeStr) ;
					}
				}
			}
			
			
		}
	}
