package RdmGsaNet_pr08;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

public class generateNetNode {
	
	// VARIABLES 
	// map
	private static Map < Double , ArrayList<String> > mapStepIdGsCon = simulation.getMapStepIdGsCon() ;
	private static Map < Double , ArrayList<String> > mapStepNewNodeIdGs = simulation.getMapStepNewNodeIdGs() ;
	
	// graph
	private static Graph gsGraph = layerGs.getGraph();
	private static Graph netGraph = layerNet.getGraph();

	// variables for constructor
	private static generateNetNodeInter type ;
	private static generateNetNode growth ;

	// constructor
	public generateNetNode (generateNetNodeInter type ) {
		this.type = type ;
	}
	
	public void generateNode ( int step ) {
		type.generateNodeRule ( step ) ;
		identifyGsNodeCon ( step ) ;  
		createNodeNet (step ) ;
	
	}

//--------------------------------------------------------------------------------------------------------------------
	// PRIVATE METHODS		
	
	/* in this private methods, we identify at each step , gs nodes that are connected to net nodes.
	* In order to discover that, we create a map where the key is the step simulation , and the value is a list of gs nodes  connected to net nodes. */	
	private void identifyGsNodeCon ( int step ) {
		
		// list of id ( from gs layer) in common with netLayer
		ArrayList<String> listIdCon = new ArrayList<String>(); 
		
		// iterator
		Iterator<Node> iterNodeGs = gsGraph.getNodeIterator();
			
			while (iterNodeGs.hasNext()) {
				Node n = iterNodeGs.next();
					
				// ask if node in gsGraph is connected with netGraph
				int isCon = n.getAttribute("con");
				
				// if gs node is connected, add idGs to local list
				if ( isCon == 1 ) {
					String idGs = n.getId();
					listIdCon.add(idGs);

					}
				}
			
			// add local list to map, in order to create a map with each nodes connected at each step
			mapStepIdGsCon.put((double) step, listIdCon) ;
//			System.out.println(mapStepIdGsCon)	;

	}
	
	// method to return a list of new nodes, at each step
	private ArrayList<String> returnNewNodes (double step ) {
		
		// create new list of values at step 0 and at step of return
		ArrayList<String> oldNodes = mapStepIdGsCon.get( (double) (step - 1) ) ;	
		ArrayList<String> AllNodes = mapStepIdGsCon.get( (double) step  ) ;
		
		//Initialized new empty list of new nodes and list list of nodes in common for step 0 and 1 
		ArrayList<String> newNodes = new ArrayList<String> () ;
		ArrayList<String> commonNodes = new ArrayList<String> (  AllNodes ) ;
	
		// try to add all nodes of step 0 to common list
		try { commonNodes.retainAll(oldNodes);	}
			catch (java.lang.NullPointerException e) {
				commonNodes.addAll(AllNodes) ;
				}
		
		// try to add all nodes of step 0 to new nodes
		try { newNodes.addAll( oldNodes );	}
			catch ( java.lang.NullPointerException e ) {	
				//
				}
		
		// calcule new nodes
		newNodes.addAll( AllNodes ); 
		newNodes.removeAll(commonNodes);
		
		// create a map of list of new nodes for each step
		mapStepNewNodeIdGs.put( step,  newNodes ) ;
		
		return newNodes ;
	}
	
	// method to create new nodes in net layer, add attributes and set XYZ coordinates from gsLayer
	private void createNodeNet ( double step ) {
		
		// pass value from returnNewNodes
		ArrayList<String> newNodes = returnNewNodes ( step )  ;	// System.out.println("newNodes " + newNodes);
		
		// initialize list of new nodes
		ArrayList<String> listCreateNode ;
		
		// switch step 
		if ( step == 1.0 )  {	listCreateNode =  mapStepIdGsCon.get(1.0);		}
		else 				{	listCreateNode = newNodes;	}		//		System.out.println("listCreateNode " + listCreateNode);
	
		// loop for each node in list new nodes
		for ( String id : listCreateNode) {
			
			// create new node
			netGraph.addNode(id) ;
			
			// get nodes
			Node gsNode = gsGraph.getNode(id);
			Node netNode = netGraph.getNode(id);
			
			// set attributes of nodes
			netNode.setAttribute("con", 1);
			netNode.setAttribute("idGs", id);
			
			// set xyz coordinate
			double [] gsNodeConXYZ = GraphPosLengthUtils.nodePosition(gsNode) ;	// System.out.println("gsX " + gsNodeConXYZ [0] + " gsY " + gsNodeConXYZ [1]);
			netNode.setAttribute( "xyz" , gsNodeConXYZ[0] , gsNodeConXYZ[1] , gsNodeConXYZ[2] );
		}
	}
	
		
		
//----------------------------------------------------------------------------------------------------------------------------------------------------------	
	// get method
	public static generateNetNode getGenerateNode () { return growth ; }
}
