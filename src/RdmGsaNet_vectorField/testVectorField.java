package RdmGsaNet_vectorField;

import java.util.Random;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import RdmGsaNetAlgo.graphGenerator;
import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNet_vectorField.vectorField.vectorFieldType;
import RdmGsaNet_vectorField.vectorField.vfNeig;
import RdmGsaNet_vectorField.vectorField.weigthDist;

public class testVectorField {
	
	static Graph gsGraph = new SingleGraph ("grid");
	static Graph vecGraph = new SingleGraph ("vec");
	
	static String attribute = "val" ;
	
	public static vectorField vf = new vectorField ( gsGraph, attribute, vectorFieldType.spatial );
	
	public static void main ( String [ ] args ) {
				
		graphGenerator.createGraphGrid(gsGraph, 10, true) ;
		graphGenerator.setRandomDoubleAttrToGraph(gsGraph, attribute );
		
		
			
		vf.computeTest () ;
		
		
		//vf.setParameters( vecGraph  , 10, vfNeig.onlyNeig, weigthDist.inverseWeigthed );
		
//	 	vf.createLayerVectorField();
		gsGraph.display(false);
		
		
		
		
		

		
	
	}
}
