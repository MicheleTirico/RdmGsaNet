package dynamicGraphSimplify;

import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

public class dynamicSymplify {
	
	protected Map<String , String > mapFather = new HashMap < String, String> ( ) ;
	protected Graph graph = new SingleGraph("graph");
	protected double epsilon ;
	
	public enum simplifyType { deleteNode , test } ;
	protected static simplifyType simplifyType ;
	
	// interface object
	 dynamicSymplify_inter dsInter;
	
	// constructor
	public dynamicSymplify( Graph graph , double epsilon , simplifyType simplifyType  ) {
		this.graph = graph ;
		this.epsilon = epsilon ;
		this.simplifyType = simplifyType ;
		
		switch (simplifyType ) {
		case deleteNode :
			dsInter =  new dynamicSymplify_deleteNode( graph ,  epsilon   );
			break;
		
		}
	}
	
	public void computeTest( ) {
		dsInter.test();
	}
	
	
	public void compute (  int step ) {				//	System.out.println(graph.getNodeCount() + " "  + graph.getNodeSet());
		
		dsInter.updateFatherAttribute( step , mapFather );
		dsInter.handleGraphGenerator( step );
	
	}
	



}
