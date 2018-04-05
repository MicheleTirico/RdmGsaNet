package RdmGsaNet_vectorField_02;

import java.io.IOException;

import org.graphstream.graph.Graph;

import org.graphstream.graph.implementations.SingleGraph;


import RdmGsaNetAlgo.graphGenerator;
import RdmGsaNetViz.handleVizStype;
import RdmGsaNetViz.handleVizStype.palette;
import RdmGsaNetViz.handleVizStype.stylesheet;
import RdmGsaNet_vectorField_02.vectorField.vectorFieldType;
import RdmGsaNet_vectorField_02.vectorField.vfNeig;
import RdmGsaNet_vectorField_02.vectorField.weigthDist;


public class TESTvectorField {

	
	static Graph gsGraph = new SingleGraph ("grid");
	static Graph vecGraph = new SingleGraph ("vec");
	static String attribute = "val" ;
	static vectorField vf = new vectorField( gsGraph , attribute , vectorFieldType.spatial ) ;

	static double sizeGridEdge ;
	

	public static void main ( String [ ] args ) throws IOException {
		
		sizeGridEdge = Math.pow( vecGraph.getNodeCount() , 0.5 ) - 1 ;
		
		
		graphGenerator.createGraphGrid(gsGraph, 40 , true) ;
		graphGenerator.setRandomDoubleAttrToGraph( gsGraph, attribute );
		
		vf.computeTest();
		vf.setParameters(vecGraph , 12, vfNeig.onlyNeig, weigthDist.inverseWeigthed );
		vf.computeVf();
		
		
		
		/*
		for ( Node nVec : vecGraph. getEachNode()) {
		
			
			//System.out.println(nVec.getAttributeKeySet());
			double[] coord = GraphPosLengthUtils.nodePosition( nVec ); 		
			
			System.out.println(nVec.getId() + " " + coord[0] + " " + coord[1]);
			double inten = nVec.getAttribute("inten") ; 
			System.out.println("inten " + inten);
			double intenX = nVec.getAttribute("intenX") ; 
			System.out.println("intenX " + intenX);
			double intenY = nVec.getAttribute("intenY") ; 
			System.out.println("intenY " + intenY);
		}
		*/
		
		handleVizStype vecViz = new handleVizStype( vecGraph ,stylesheet.manual , "seedGrad", 1) ;
		vecViz.setupIdViz(false, vecGraph, 4 , "black");
		vecViz.setupDefaultParam (vecGraph, "black", "black", 0 , 0.5 );
		vecViz.setupVizBooleanAtr(true, vecGraph, "black", "red", false, false ) ;
		vecViz.setupFixScaleManual(true , vecGraph, sizeGridEdge , 0);
		
		// viz display
		handleVizStype gsViz = new handleVizStype( gsGraph ,stylesheet.viz5Color , attribute , 1) ;
		gsViz.setupDefaultParam (gsGraph, "red", "white", 5 , 0.5 );
		gsViz.setupIdViz(false, gsGraph, 10 , "black");
		gsViz.setupViz(true, true, palette.red);
		
		gsGraph.display(false) ;
		vecGraph.display(false);
				

		
		
	}
		
		

		
	
}
