package RdmGsaNet_vectorField_02;

import java.io.IOException;
import java.util.ArrayList;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSinkDGS;

import RdmGsaNetAlgo.graphToolkit;
import RdmGsaNetAlgo.graphToolkit.elementTypeToReturn;
import RdmGsaNetExport.handleNameFile;
import RdmGsaNet_generateGraph.generateNetNode.interpolation;
import RdmGsaNet_mainSim.main;
import RdmGsaNet_vectorField_02.vectorField.vfNeig;
import RdmGsaNet_vectorField_02.vectorField.weigthDist;

public class vectorField {
	
	// COSTANTS 
	protected static Graph graph ;
	private static Graph vecGraph ;
		
	protected String attribute  ;
	private vectorField_inter vfInt ; 
		
	public enum vfNeig { inRadius , onlyNeig }
	protected vfNeig vfN ;
	
	public enum typeInterpolation { averageEdge , averageDist, sumVectors  } 
		
	public enum weigthDist { inverseWeigthed , inverseSquareWeigthed }
	protected weigthDist wdType ;
				
	public enum vectorFieldType { spatial , temporal }
	protected static vectorFieldType  vfType ; 
		
	private double maxIntenVector ; 
	
	// STORING GRAPH EVENTS
	static FileSinkDGS fsd = new FileSinkDGS();
	handleNameFile handle = main.getHandle(); 
	private boolean doStoreStartVec ;
		
	// constructor 
	public vectorField ( Graph graph , String attribute ,  vectorFieldType vfType ) {
		this.graph = graph ;
		this.attribute = attribute ; 
		this.vfType = vfType ;
					
		switch (vfType) {
			case spatial: 	vfInt = new vectorFieldSpatial( graph , attribute )  ;	
				break;
				
			case temporal : 				
				break;
		}
	}
		
	public void setParameters ( Graph vecGraph , double maxIntenVector , vfNeig vfN , weigthDist wdType  ) {
			this.setVecGraph(vecGraph) ;
			this.maxIntenVector = maxIntenVector ;
			this.vfN = vfN ; 
			this.wdType = wdType ;	
		}
		
	public void createLayer (Graph graph , Graph vecGraph , boolean doStoreStartVec)  throws IOException {
			vectorField_inter.createGraph(graph, vecGraph, doStoreStartVec );
		}
			
	public void computeTest ( ) {
			vfInt.test( ) ;
	}
		
	public void computeVf ( ) throws IOException {						//	System.out.println(super.toString());
			vfInt.computeVf ( vfN , wdType , vecGraph , doStoreStartVec );
			vfInt.updateVector(graph, vecGraph , maxIntenVector);
	}	

	protected static double getCoefWeig ( weigthDist wdType , double dist ) {
		
			double coefDist = 0.0 ;
			switch ( wdType ) {
				
				case inverseWeigthed:
					coefDist = 1.0 / dist ;
					break;
				
				case inverseSquareWeigthed :
					coefDist = 1.0 / Math.pow(dist, 2) ;
					break ;						
			}
		return coefDist ;
	}

		
// get methods --------------------------------------------------------------------------------------------------------------------------------------
	public static Graph getVecGraph() {
		return vecGraph;
	}

	public void setVecGraph(Graph vecGraph) {
		this.vecGraph = vecGraph;	
	}

	public static double[] getVectorInterpolate ( Graph vecGraph , double[] nodeCoord , typeInterpolation typeInterpolation ) {
		
		double[]vector = new double[2] ;
		double vectorX = 0.0 , vectorY = 0.0 ;
		
		ArrayList <String> 	listVertex = new ArrayList<String> (graphToolkit.getListVertexRoundPoint(elementTypeToReturn.string, graph, nodeCoord));	//	System.out.println(listVertex );
		
		switch (typeInterpolation) {
		
			case sumVectors: {
				for ( String nVertex : listVertex ) {	//			System.out.println(nVertex);
					Node nVec = vecGraph.getNode(nVertex); 
					double 	intenX , intenY ;
					try {
						 	intenX = nVec.getAttribute("intenX") ;
						 	intenY = nVec.getAttribute("intenY") ;
					}
					catch (Exception e) {
						intenX = 0 ; 
						intenY = 0 ; 
					}
						
					vectorX = vectorX + intenX ;
					vectorY = vectorY + intenY ;		
				}
			} break;
		}
		vector[0] = vectorX ;
		vector[1] = vectorY ;				//	System.out.println(vectorX + " " + vectorY );
		
		return vector ;
	}

	public static double getAngleVectorInterpolate  (Graph vecGraph , double[] nodeCoord ,  typeInterpolation typeInterpolation  ) {
		
		double[] vectorInterpolate = getVectorInterpolate(vecGraph, nodeCoord, typeInterpolation);
		
	//	double coefAng = vectorInterpolate[1] / vectorInterpolate[0] ;
		
	//	double angle = Math.atan(coefAng) ; 
		double dist = Math.pow(  Math.pow(vectorInterpolate[0], 2) + Math.pow(vectorInterpolate[1], 2 ) , 0.5 ) ;
		
		double sinAngle =  vectorInterpolate[1] / dist ;
		double angle = Math.asin(sinAngle) ; 
		
		return angle;
	}

	
	public static double getAngleVector ( ) {
		
		double angle = 0 ;
		
		return angle ;
	}
		
	public static double getIntenVector (  Graph vecGraph ,  String id ) {
		
		return vecGraph.getNode(id).getAttribute("inten");	
	}
	
	public static double[] getIntenCoordVector (  Graph vecGraph , String id ) {
		
		double[] vector = new double[2] ;
		
		vector[0] = vecGraph.getNode(id).getAttribute("intenX");
		vector[1] = vecGraph.getNode(id).getAttribute("intenY");
			
		return vector ;
	
	}
	

}
