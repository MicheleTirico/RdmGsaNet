package RdmGsaNet_pr08;

import org.graphstream.graph.Graph;

public class generateNetNode {
	
	private generateNetNodeInter type ;
	private static generateNetNode growth ;

	public generateNetNode (generateNetNodeInter type ) {
		this.type = type ;
	}
	
	public void generateNode ( Graph graph,  int step ) {
		type.generateNodeRule ( graph, step ) ;
	}

//--------------------------------------------------------------------------------------------------------------------
	// private methods		
	public static generateNetNode getGenerateNode () { return growth ; }

}
