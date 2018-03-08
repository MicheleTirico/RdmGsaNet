package RdmGsaNetAlgo;

import java.util.ArrayList;
import java.util.Arrays;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

public class graphInterpolation {
	
	// COMPUTE INTERPOLATION ----------------------------------------------------------------------------------------------------------------------------
	public static double computeInterpolation ( ArrayList<String> listNetNodeStr ,  Graph graph0 , Graph graph1, String idNode1 , String attribute , ArrayList<String> listVertex ) {
				
		Node node1 = graph1. getNode(idNode1) ;
		double[] nodeCoord = GraphPosLengthUtils.nodePosition(node1)  ; 				// 	System.out.println(nodeCoord[0] + " " + nodeCoord[1]);
		double nodeX = nodeCoord[0] , nodeY = nodeCoord[1];
				
		double minX = 1000000000 , minY =  1000000000 , maxX = -1 , maxY = -1 ;
				
		for ( String idVertex : listVertex ) {
					
			double[] vertexCoord = gsAlgoToolkit.getCoordinateOfNodeStr ( idVertex );
			double vertexX = vertexCoord[0] , vertexY = vertexCoord[1];
					
			if ( vertexX <=  minX )			minX = vertexX ;
			if ( vertexX >=  maxX )			maxX = vertexX ;
					
			if ( vertexY <=  minY )			minY = vertexY ;
			if ( vertexY >=  maxY )			maxY = vertexY  ;
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
			
	// private method return array of average values 
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

		Node 	node00 = null , 	node10 = null ,		node01 = null , 	node11 = null ; 
													
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
			arrVal[3] = 0 ; 			System.out.println(idNode00); System.out.println(idNode10); System.out.println(idNode01) ; System.out.println(idNode11);
		}
		
		for ( int pos= 0 ; pos < 4 ; pos++ ) {
			if ( arrVal[pos] <= 0 )				arrVal[pos] = 0 ;
			if ( arrVal[pos] >= 1 )				arrVal[pos] = 1 ;																	
		}
		return arrVal;		
	}
}
