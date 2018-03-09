package RdmGsaNet_vectorField_02;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import RdmGsaNet_vectorField_02.vectorField.vfNeig;
import RdmGsaNet_vectorField_02.vectorField.weigthDist;
import testVectorField.vf;

public class vectorField {
	
	// COSTANTS 
		protected Graph graph ;
		protected Graph vecGraph ;
		
		protected String attribute  ;
		private vectorField_inter vfInt ; 
		private double radius ;
		
		protected enum vfNeig { inRadius , onlyNeig }
		protected vfNeig vfN ;
		
		protected enum weigthDist { inverseWeigthed , inverseSquareWeigthed }
		protected weigthDist wdType ;
		
		protected enum vectorFieldType { spatial , temporal }
		protected static vectorFieldType  vfType ; 
		
		
		
		// constructor 
		public vectorField ( Graph graph , String attribute ,  vectorFieldType vfType  ) {
			this.graph = graph ;
			this.attribute = attribute ; 
			this.vfType = vfType ;
				
			switch (vfType) {
				case spatial: 	vfInt = new vectorFieldSpatial( graph , vecGraph, attribute )  ;	
					break;
				
				case temporal : 				
					break;
			}
		}
		
		public void setParameters ( Graph vecGraph ,double radius , vfNeig vfN , weigthDist wdType ) {
			this.vecGraph = vecGraph ;
			this.radius = radius ;
			this.vfN = vfN ; 
			this.wdType = wdType ;
		}
		
		
		
		public void computeTest ( ) {
			vfInt.test( ) ;
		}
		
		public void computeVf () {
//			vfInt.createGraph(graph, vecGraph);
			vfInt.computeVf(vfN, wdType , vecGraph );
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

}
