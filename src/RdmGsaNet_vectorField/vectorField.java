package RdmGsaNet_vectorField;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import RdmGsaNet_vectorField.vectorField.weigthDist;

public class vectorField {
	
	// COSTANTS 
	protected Graph vecGraph = new SingleGraph("vecGraph");
	protected Graph graph = new SingleGraph("graph");
	
	vectorField_inter vfInt ; 
	
	protected String attribute  ;
	
	private double radius ;
	
	protected enum vfNeig { inRadius , onlyNeig }
	protected vfNeig vfN ;
	
	protected enum weigthDist { inverseWeigthed , inverseSquareWeigthed }
	protected weigthDist wdType ;
	
	protected enum vectorFieldType { spatial , temporal }
	protected static vectorFieldType  vfType ; 
		
	// constructor 
	public vectorField ( Graph graph , String attribute , vectorFieldType vfType  ) {		
		this.graph = graph ;
		this.attribute = attribute ;
		this.vfType = vfType ;
		
		switch (vfType) {
			case spatial: 
				vfInt = new vectorFieldSpatial (graph, attribute) ;	
				break;
			
			case temporal : 
				vfInt = new vectorFieldTemporal(graph, attribute, vfType) ;
				break;
		}
	}
	
	public void setParameters ( Graph vecGraph , double radius , vfNeig vfN , weigthDist wdType ) {
		this.vecGraph = vecGraph ;
		this.radius = radius ;
		this.vfN = vfN ; 
	}	
	
	public void createLayerVectorField() {
		vfInt.computeVf(vfN , wdType);
	}
	
	public void computeTest() {		
		vfInt.test();
	}
	
//---------------------------------------------------------------------------------------------------------------------------------------------------	
	protected static double getCoefWeig ( weigthDist wdType , double dist ) {
	
		double coefDist = 0.0 ;
		switch ( wdType) {
			case inverseWeigthed:
				coefDist = 1.0 / dist ;
				break;
			case inverseSquareWeigthed :
				coefDist = 1.0 / Math.pow(dist, 2) ;
				break ;	
		}
	return coefDist ;
	}




	

}
