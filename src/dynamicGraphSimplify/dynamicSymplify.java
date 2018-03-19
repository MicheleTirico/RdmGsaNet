package dynamicGraphSimplify;

import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

public class dynamicSymplify {
	
	protected Map<String , String > mapFather = new HashMap < String, String> ( ) ;
	protected Graph graph = new SingleGraph("graph");
	protected double epsilon ;
	private boolean runSymplify ;
	
	public enum simplifyType { deleteNode , test } ;
	protected static simplifyType simplifyType ;
	
	// interface object
	 dynamicSymplify_inter dsInter;
	
	// constructor
	public dynamicSymplify( boolean runSymplify ,  Graph graph , double epsilon , simplifyType simplifyType  ) {
		this.runSymplify = runSymplify ;
		this.graph = graph ;
		this.epsilon = epsilon ;
		this.simplifyType = simplifyType ;
		
		switch (simplifyType ) {
		case deleteNode :
			dsInter =  new dynamicSymplify_deleteNode( graph ,  epsilon   );
			break;
		
		}
	}
	
	public void computeTest(  ) {
		dsInter.test();
	}
	
	
	public void compute (  int step ) {				//	System.out.println(graph.getNodeCount() + " "  + graph.getNodeSet());
		if ( runSymplify == false )
			return ;
		dsInter.updateFatherAttribute( step , mapFather );
		dsInter.handleGraphGenerator( step );
	
	}
	



}
