package RdmGsaNet_vectorField_02;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import RdmGsaNetAlgo.graphGenerator;
import RdmGsaNet_vectorField_02.vectorField.vectorFieldType;
import RdmGsaNet_vectorField_02.vectorField.vfNeig;
import RdmGsaNet_vectorField_02.vectorField.weigthDist;


public class TESTvectorField {

	
	static Graph gsGraph = new SingleGraph ("grid");
	static Graph vecGraph = new SingleGraph ("vec");
	static String attribute = "val" ;
	
	
	
	public static void main ( String [ ] args ) {
		
		vectorField vf = new vectorField( gsGraph , attribute , vectorFieldType.spatial) ;
		
		graphGenerator.createGraphGrid(gsGraph, 10, true) ;
		graphGenerator.setRandomDoubleAttrToGraph( gsGraph, attribute );
		
		vf.computeTest();
		vf.setParameters(vecGraph , 12, vfNeig.onlyNeig, weigthDist.inverseWeigthed );
		vf.computeVf();
		
		vecGraph.display(false);
		
		for ( Node nVec : vecGraph. getEachNode()) {
			System.out.println(nVec.getAttributeKeySet());
			
			double inten = nVec.getAttribute("inten") ; 
			System.out.println("inten " + inten);
			double intenX = nVec.getAttribute("intenX") ; 
			System.out.println("intenX " + intenX);
			double intenY = nVec.getAttribute("intenY") ; 
			System.out.println("intenY " + intenY);
		}
		
		
		
		gsGraph.display(false);
		
		
	}
		
		

		
	
}
