package dynamicGraphSimplify;

import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

public class dynamicSymplify {
	
	protected Map<String , String > mapFather = new HashMap < String, String> ( ) ;
	
	protected Graph netGraph = new SingleGraph("netGraph") ,
					seedGraph = new SingleGraph("seedGraph") ;
	
	protected double epsilon ;
	private boolean runSymplify ;
	
	public enum simplifyType { deleteNode , test, kNearestNeighbors } ;
	protected static simplifyType simplifyType ;
	
	// interface object
	 dynamicSymplify_inter dsInter;
	
	// constructor
	public dynamicSymplify( boolean runSymplify ,  Graph netGraph  , Graph seedGraph ,double epsilon , simplifyType simplifyType  ) {
		this.runSymplify = runSymplify ;
		this.netGraph  = netGraph  ;
		this.seedGraph = seedGraph ;
		this.epsilon = epsilon ;
		this.simplifyType = simplifyType ;
		
		switch (simplifyType ) {
		case deleteNode :
			dsInter =  new dynamicSymplify_deleteNode( netGraph  ,  epsilon  );
			break;
		case kNearestNeighbors :
			dsInter =  new dynamicSymplify_kNearestNeighbors ( netGraph  , seedGraph ,  epsilon  );
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
