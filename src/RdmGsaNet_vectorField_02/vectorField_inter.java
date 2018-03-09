package RdmGsaNet_vectorField_02;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import RdmGsaNet_vectorField_02.vectorField.vfNeig;
import RdmGsaNet_vectorField_02.vectorField.weigthDist;

public interface vectorField_inter {
	
	public void test ( ) ; 
	
	public void computeVf ( vfNeig vfN , weigthDist wdType , Graph vecGraph) ;

	public void getVector( Node n ) ;
	
	public void createGraph(Graph graph , Graph vecGraph) ;

}
