package RdmGsaNet_vectorField;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import RdmGsaNet_vectorField.vectorField.vfNeig;
import RdmGsaNet_vectorField.vectorField.weigthDist;

public interface vectorField_inter {
	
	public void computeVf ( vfNeig vfN , weigthDist wdType) ;

	public void getVector( Node n ) ;

	public void test ( ); 
	
// static methods -----------------------------------------------------------------------------------------------------------------------------------
	
	
}
