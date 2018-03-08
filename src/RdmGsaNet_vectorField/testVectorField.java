package RdmGsaNet_vectorField;

import java.util.Random;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import RdmGsaNetAlgo.graphGenerator;
import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNet_vectorField.vectorField.vectorFieldType;

public class testVectorField {
	
	static Graph graph = new SingleGraph ("grid");
	static String attribute = "val" ;
	
	
	
	public static void main ( String [ ] args ) {
		
		vectorField vf = new vectorField( graph, attribute, vectorFieldType.spatial );

		
		
		
		graphGenerator.createGraphGrid(graph, 10, true) ;
		graph.display(false);
		
		graphGenerator.setRandomDoubleAttrToGraph(graph, attribute );
		
		
		
		

		
	
	}
}
