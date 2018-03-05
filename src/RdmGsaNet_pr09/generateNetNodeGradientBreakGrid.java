package RdmGsaNet_pr09;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNetAlgo.gsAlgoToolkit;

public class generateNetNodeGradientBreakGrid extends generateNetNodeGradient implements generateNetNode_Inter {
		
	protected boolean controlSeed ;

		protected enum interpolation { meanEdge , meanDist } 
		protected interpolation typeInterpolation ;
		
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
			
			// set seed nodes ( only first step )
			setSeedNodes(step, numberMaxSeed, setLayoutSeed);
					
			// CREATE LIST OF SEEDGRAD 
			ArrayList<String> listNodeSeedGrad = gsAlgoToolkit.getListStringNodeAttribute(netGraph, "seedGrad" , 1 );			//		
			System.out.println("number of seed " + listNodeSeedGrad.size() + " " + listNodeSeedGrad);

			for ( String idNode : listNodeSeedGrad ) {
				
				System.out.println(idNode);
				Node nGs = gsGraph.getNode(idNode);
				Node nNet = netGraph.getNode(idNode);
						
				ArrayList <String> listNeigSeed = new ArrayList<String>();
				ArrayList <String> listNeigNotSeed = new ArrayList<String>();			//	System.out.println(netGraph.getNodeSet() ) ;
			
				ArrayList<String> listVertex = new ArrayList<String> ();
			
				//listVertex = gsAlgoToolkit.getListVertex( nGs , nNet );					//	System.out.println(listVertex);
				listVertex = gsAlgoToolkit.getListVertexRound(nNet)
						;					//	System.out.println(listVertex);
				
				
//				for ( String s : listVertex ) 	System.out.println ( s ) ;
				double valInter = computeInterpolation(gsGraph, netGraph , idNode , morp , listVertex );	//			System.out.println(valInter);
				
				for ( String idVertex : listVertex ) {
					
					handleListNeigGsSeed(idVertex, listNeigSeed, listNeigNotSeed);
					
					ArrayList<String> listForDelta = generateNetNodeGradient.getListForDelta(listVertex , listNeigSeed);
					listForDelta.add(idNode) ;
					
//					System.out.println( idVertex + listForDelta);
					double delta = gsAlgoToolkit.getValStad( gsGraph , listForDelta, nNet , morp , true ,valInter)  ;		//		System.out.println("delta " + delta ) ; 	// System.out.println(listForDelta.size());
					
					int numberMaxNewNodes = getNumberMaxNewNodes(delta, listForDelta)  ;
					
					int numberNewNodes = gsAlgoToolkit.getBinomial(numberMaxNewNodes, prob); //		System.out.println( "numberNewNodes " + numberNewNodes );
					
					handleStillAlive(numberNewNodes, controlSeed, nNet);
					
					for ( int x = 0 ; x < numberNewNodes ; x++ ) {

						if ( delta == 0 ) {
							nNet.setAttribute("seedGrad", 1);
							continue ; 
						}
						
						Node nVertex = gsGraph.getNode(idVertex);
						
						// get coordinate new node						
						double [] 	vertexCoord = GraphPosLengthUtils.nodePosition(nVertex) ,
									seedCoord = GraphPosLengthUtils.nodePosition(nNet) ;
						
						double 	distSeedVertex = gsAlgoToolkit.getDistGeom(nVertex, nNet) ,
								xdistMax = Math.abs(distSeedVertex * ( vertexCoord[0] - seedCoord[0]) ),
								ydistMax = Math.abs(distSeedVertex * ( vertexCoord[1] - seedCoord[1]) ) ,
								distMax = Math.max(xdistMax, ydistMax);
					
						if  ( distMax == 0 ) {
							nNet.setAttribute("seedGrad", 1);	
							continue ; //	System.out.println("distMax " + distMax) ;
						}
						
						double coefDist = 0 ;
						if ( delta >= 1) 
							coefDist =  distMax;
						else if (delta < 1 )
							coefDist = delta * distMax ;							//	System.out.println("coefDist " + coefDist) ;
						
						double  xNewNode = vertexCoord[0] + distSeedVertex * ( vertexCoord[0] - seedCoord[0]) / coefDist  , 
								yNewNode = vertexCoord[1] + coefDist * ( vertexCoord[1] - seedCoord[1] ) / distSeedVertex  ;
						
						String 	xId = Double.toString(Math.floor(xNewNode * 100 )  / 100 ),
								yId = Double.toString(Math.floor(yNewNode * 100 )  / 100 );		//	System.out.println(xId);
					
						// get id node maybe add
						String idCouldAdded = xId + "_" + yId ; 								//	System.out.println(idCouldAdded);
						Node nodeCouldAdded = null ;
							
						// there isn't node
						try {
							netGraph.addNode(idCouldAdded);
						//	System.out.println("newNode");
							nodeCouldAdded = netGraph.getNode(idCouldAdded); 			//	System.out.println(idCouldAdded);
							nodeCouldAdded.addAttribute("seedGrad", 1);
							nNet.setAttribute("seedGrad", 1 );
							
							// set coordinate
							nodeCouldAdded.setAttribute( "xyz", xNewNode , yNewNode, 0 );	
							}
						
						// if node already exist 
						catch (org.graphstream.graph.IdAlreadyInUseException e) { 		//System.out.println(e.getMessage());
							nodeCouldAdded = netGraph.getNode(idCouldAdded); 			//	System.out.println(idCouldAdded);
							nodeCouldAdded.addAttribute("seedGrad", 0 );
							nNet.setAttribute("seedGrad", 1);
						}
					}
				}
			}
		}

		@Override
		public void removeNodeRule(int step) {
			// TODO Auto-generated method stub
			
		}
		
		
		

// COMPUTE INTERPOLATION ----------------------------------------------------------------------------------------------------------------------------
		private double computeInterpolation ( Graph graph0 , Graph graph1, String idNode , String attribute , ArrayList<String> listVertex ) {
			
//			Node n0 = graph0.getNode(idNode);
//			Node n1 = graph1.getNode(idNode);
//			ArrayList<String> listVertex = gsAlgoToolkit.getListVertex(n0 , n1 );				//	System.out.println(idNode);			System.out.println(listVertex);
			
			int[] nodeCoord = gsAlgoToolkit.getCoordinateOfNodeStr ( idNode ) ; 				// 	System.out.println(nodeCoord[0] + " " + nodeCoord[1]);
			int nodeX = nodeCoord[0] , nodeY = nodeCoord[1];
			
			int minX = 1000000000 , minY =  1000000000 , maxX = -1 , maxY = -1 ;
			
			for ( String idVertex : listVertex ) {
				
				int[] vertexCoord = gsAlgoToolkit.getCoordinateOfNodeStr ( idVertex );
				int vertexX = vertexCoord[0] , vertexY = vertexCoord[1];
				
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
			double[] valArr = getCeckedAveValVertex(graph0, attribute, minX, minY, maxX, maxY); 				//	System.out.println(valArr[0] + " " + valArr[1] + " " + valArr[2] + " "+ valArr[3] + " " );
			
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
		
		private static double[] getCeckedAveValVertex ( Graph graph , String attribute, int minX , int minY , int maxX, int maxY ) {
			
			double[] arrVal = new double[4];
			
			// list val = val00 , val01 , val10 ,val11 
			arrVal[0] = graph.getNode(minX + "_" + minY ).getAttribute(attribute) ;						
			arrVal[1] = graph.getNode(minX + "_" + maxY ).getAttribute(attribute) ;								//	System.out.println(minX + "_" + maxY);
			arrVal[2] = graph.getNode(maxX + "_" + minY ).getAttribute(attribute) ;								//	System.out.println( maxX + "_" + minY );
			arrVal[3] = graph.getNode(maxX + "_" + maxY ).getAttribute(attribute) ;								//	System.out.println(maxX + "_" + maxY );
			
			for ( int pos= 0 ; pos < 4 ; pos++ ) {
				if ( arrVal[pos] <= 0 )
					arrVal[pos] = 0 ;
				if ( arrVal[pos] >= 1 )
					arrVal[pos] = 1 ;																			//	System.out.println( arrVal[pos]);
			}
			return arrVal;
		}

	}
