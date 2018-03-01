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

		protected enum interpolation { linear} 
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

			// set seed nodes ( only first step )
			setSeedNodes(step, numberMaxSeed, setLayoutSeed);
					
			// CREATE LIST OF SEEDGRAD 
			ArrayList<String> listNodeSeedGrad = gsAlgoToolkit.getListStringNodeAttribute(netGraph, "seedGrad" , 1 );			//		System.out.println("number of seed " + listNodeSeedGrad.size() + " " + listNodeSeedGrad);

			for ( String idNode : listNodeSeedGrad ) {
				
				Node nGs = gsGraph.getNode(idNode);
				Node nNet = netGraph.getNode(idNode);
				
				int nGsDegree  = nGs.getDegree() ;			//			System.out.println("degree gs , id " + nGs.getId() + " " + nGsDegree);
				int nNetDegree = nNet.getDegree() ;			//			System.out.println("degree net, id " + nNet.getId() + " " + nNetDegree);
					
				// list of neig
				ArrayList <String> listNeigGsStr  = gsAlgoToolkit.getListNeighborStr ( gsGraph,  idNode) ;		//	System.out.println("listNeigGsString of node " + nGs.getId() + " " + listNeigGsStr);
				ArrayList <String> listNeigNetStr = gsAlgoToolkit.getListNeighborStr ( netGraph, idNode) ;		//	System.out.println("listNeigNetString of node " + nNet.getId() + " "  + listNeigNetStr);
				
				ArrayList <String> listNeigGsStrSeed = new ArrayList<String>();
				ArrayList <String> listNeigGsStrNotSeed = new ArrayList<String>();			//			System.out.println(netGraph.getNodeSet() ) ;
			
				double valInter = computeInterpolation(gsGraph, netGraph, idNode, morp);	//			System.out.println(valInter);
			
			
			
			}
		
			
		}

		@Override
		public void removeNodeRule(int step) {
			// TODO Auto-generated method stub
			
		}
		
		
		

// COMPUTE INTERPOLATION ----------------------------------------------------------------------------------------------------------------------------
		private double computeInterpolation ( Graph graph0 , Graph graph1, String idNode , String attribute ) {
			
			Node n0 = graph0.getNode(idNode);
			Node n1 = graph1.getNode(idNode);

			ArrayList<String> listVertex = gsAlgoToolkit.getListVertex(n0 , n1 );				//			System.out.println(idNode);			System.out.println(listVertex);
			
			int[] nodeCoord = gsAlgoToolkit.getCoordinateOfNodeStr ( idNode );
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
			double[] valArr = getCeckedAveValVertex(graph0, attribute, minX, minY, maxX, maxY); 

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
