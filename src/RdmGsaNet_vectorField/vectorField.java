package RdmGsaNet_vectorField;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

public class vectorField {
	
	// COSTANTS 
	private Graph graph = new SingleGraph("graph");
	private String attribute  ;
	
	public enum vectorFieldType { spatial  , temporal }
	protected static vectorFieldType  vectorFieldType ; 
		
		
	public vectorField ( Graph graph , String attribute , vectorFieldType vectorFieldType  ) {
		this.graph = graph ;
		this.attribute = attribute ;
		this.vectorFieldType = vectorFieldType ;
		
		switch (vectorFieldType) {
		case spatial:
			
			break;

		default:
			break;
		}
	}

	

	public void setupVectorParameters() {
		// TODO Auto-generated method stub
		
	}


	
	
	
	
	

}
