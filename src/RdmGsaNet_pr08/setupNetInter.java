package RdmGsaNet_pr08;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.SingleGraph;

public interface setupNetInter {
	
	// COSTANTS
	
//	public enum typeNet { grid ,seed , gis}
	

	// METHODS	

	// method for createLayer
	void createLayerNet ();
	
	//method for setupGsNetLink
	void setGsAtr( setupNetSeed.meanPointPlace point ) ;
	
	//method for setupGsNetLink
	void setNetAtr( Graph graph ) ;


}
