package RdmGsaNet_pr05;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

public interface setupNet {
	
	Graph netGraph = new SingleGraph("netGraph");
	
//	public enum typeNet { grid ,seed , gis}
	public enum start{ center , random, border }
	
	void setupNetCreate ();
	
	void setupNetInit(int size , start startPoint ) ;

	

}
