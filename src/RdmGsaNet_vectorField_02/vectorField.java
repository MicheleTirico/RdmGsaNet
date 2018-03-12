package RdmGsaNet_vectorField_02;

import java.io.IOException;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSinkDGS;

import RdmGsaNetExport.handleNameFile;
import RdmGsaNet_mainSim.main;
import RdmGsaNet_vectorField_02.vectorField.vfNeig;
import RdmGsaNet_vectorField_02.vectorField.weigthDist;
import testVectorField.vf;

public class vectorField {
	
	// COSTANTS 
		protected Graph graph ;
		private static Graph vecGraph ;
		private boolean viz ;
		
		protected String attribute  ;
		private vectorField_inter vfInt ; 
		private double radius ;
		
		public enum vfNeig { inRadius , onlyNeig }
		protected vfNeig vfN ;
		
		public enum weigthDist { inverseWeigthed , inverseSquareWeigthed }
		protected weigthDist wdType ;
		
		public enum vectorFieldType { spatial , temporal }
		protected static vectorFieldType  vfType ; 
		
		// STORING GRAPH EVENTS
		static FileSinkDGS fsd = new FileSinkDGS();
		handleNameFile handle = main.getHandle(); 
		private boolean doStoreStartVec ;
		
		// constructor 
		public vectorField ( Graph graph , String attribute ,  vectorFieldType vfType , boolean viz ) {
			this.graph = graph ;
			this.attribute = attribute ; 
			this.vfType = vfType ;
			this.viz = viz ;
			
			
			switch (vfType) {
				case spatial: 	vfInt = new vectorFieldSpatial( graph , attribute )  ;	
					break;
				
				case temporal : 				
					break;
			}
		}
		
		public void setParameters ( Graph vecGraph ,double radius , vfNeig vfN , weigthDist wdType  ) {
			this.setVecGraph(vecGraph) ;
			this.radius = radius ;
			this.vfN = vfN ; 
			this.wdType = wdType ;	
		}
		
		public void createLayer (Graph graph , Graph vecGraph , boolean doStoreStartVec)  throws IOException {
			vectorField_inter.createGraph(graph, vecGraph, doStoreStartVec );
		}
		
		
		
		public void computeTest ( ) {
			vfInt.test( ) ;
		}
		
		public void computeVf ( ) throws IOException {
			System.out.println("peppe");
			vfInt.computeVf ( vfN , wdType , vecGraph , doStoreStartVec );
		//	vfInt.createVector ( vecGraph );
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

		
		public static Graph getVecGraph() {
			return vecGraph;
		}

		public void setVecGraph(Graph vecGraph) {
			this.vecGraph = vecGraph;
		}

}
