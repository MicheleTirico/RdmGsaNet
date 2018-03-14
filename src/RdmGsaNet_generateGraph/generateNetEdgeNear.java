package RdmGsaNet_generateGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNet_mainSim.layerNet;
import RdmGsaNet_mainSim.simulation;
import RdmGsaNet_generateGraph.generateNetEdge;


public class generateNetEdgeNear implements generateNetEdge_Inter {

	// ENUM
	public enum whichNode { all , onlyOld }
	private whichNode type;

	public static double radius  ;	
	
	
	// MAP
	// map / key = (double) step , values = ( list of string)  id node in net
	private static Map < Double , ArrayList<String> > mapStepIdNet = simulation.getMapStepIdNet() ;
		
	// map / key = (double) step , values = (list of string ) id of new nodes in net
	private static Map < Double , ArrayList<String> > mapStepNewNodeId = simulation.getMapStepNewNodeId() ;	
	
	// COSTRUCTOR
	public generateNetEdgeNear( double radius , whichNode type) {
		this.radius = radius ;	
		this.type = type ;
	}	

// PUBLIC METHODS  --------------------------------------------------------------------------------------------------------

// generate edge
	public void generateEdgeRule( double  step ) {

		Map<Double, ArrayList<String>> mapStepNewNodeId = simulation.getMapStepNewNodeId();	//	
			
		try {
			// list of new nodes
			ArrayList<String> listIdNewNode = mapStepNewNodeId.get( (int) step ) ;			//	System.out.println("listIdNewNode " + listIdNewNode);
		
			//list of nodes that maybe are connected to new nodes
			ArrayList<String> listNodesOld = null;											//	System.out.println(listNodesOld);
		
			// define list of nodes maybe are connected to new nodes
			switch (type) {
		
			// all nodes in net graph
			case all : 		{ 	listNodesOld = mapStepIdNet.get( step ) ;	}
								break;
	
			// only old nodes
			case onlyOld : {	listNodesOld = mapStepIdNet.get( step ) ;
								listNodesOld.removeAll(listIdNewNode); 		}
								break;	
			}																				//		System.out.println("listNodesOld " + listNodesOld);
		
			//loop, in the next , we must improve the code with k-nearest neighbors algorithm
			for ( String id : listIdNewNode ) {
			
				Node n1 = netGraph.getNode(id) ;											//	Object a = n1.getId(); 			System.out.println(a);		
			
				// get map of distance
				Map <String , Double> mapDist = generateNetEdge.getMapIdDist( netGraph , n1) ;				//	System.out.println(id + " " + mapDist);
			
				// get min distance between n1 and n2 ( you may have more than one node )
				double minDist = generateNetEdge.getMinDist (mapDist); 										// 	System.out.println(minDist);		
			
				// get a set of nearest nodes
				Set<String> idNear = gsAlgoToolkit.getKeysByValue(mapDist, minDist ); 		// 	System.out.println(idNear);
			
				// create edges
				generateNetEdge.createEdgeInSetNear(n1, idNear, netGraph);	
			}
		}
		catch (java.lang.NullPointerException e) {	/* e.getMessage(); */	}
	}
	
	// remove edge
	public void removeEdgeRule( double step) {	
	}
	
//  PRIVATE METHODS --------------------------------------------------------------------------------------------------------
	
	
}