package dynamicGraphSimplify;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

public class dynamicSymplify {
	
	protected Graph graph = new SingleGraph("graph");
	protected double epsilon ;
	
	public enum simplifyType { deleteNode , test } ;
	protected static simplifyType simplifyType ;
	
	// interface object
	 dynamicSymplify_inter dsInter;
	
	// constructor
	public dynamicSymplify( Graph graph , double epsilon , simplifyType simplifyType ) {
		this.graph = graph ;
		this.epsilon = epsilon ;
		this.simplifyType = simplifyType ;
		
		switch (simplifyType ) {
		case deleteNode :
			dsInter =  new dynamicSymplify_deleteNode( graph ,  epsilon   );
			break;
		
		}
		// TODO Auto-generated constructor stub
	}
	
	public void computeTest( ) {
	//	dsInter.test();
	}
	
	public void setParameters ( ) {
		
	}
	
	public void compute () {
		dsInter.updateFatherAttribute();
		dsInter.computeDistance();
		dsInter.handleGraphGenerator();
		
	}
	

}
