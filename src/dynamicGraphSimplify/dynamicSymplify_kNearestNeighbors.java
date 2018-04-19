package dynamicGraphSimplify;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import RdmGsaNetAlgo.graphToolkit;
import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNetAlgo.graphToolkit.element;
import RdmGsaNetAlgo.graphToolkit.elementTypeToReturn;

public class dynamicSymplify_kNearestNeighbors implements dynamicSymplify_inter  {

	private Graph 	netGraph = new SingleGraph("netGraph") ,
					seedGraph = new SingleGraph("seedGraph") ;
	
	private double 	epsilon ;
	
	// constructor 
	public dynamicSymplify_kNearestNeighbors ( Graph netGraph , Graph seedGraph , double epsilon ) {
		this.netGraph = netGraph ;
		this.seedGraph = seedGraph ;
		this.epsilon = epsilon; 
	}

	@Override
	public void test() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateFatherAttribute(int step, Map<String, String> mapFather) {
	
	}

	@Override
	public void handleGraphGenerator(int step) { 	//	System.out.println(super.getClass().getName());
		
		if ( step <= 2 )
			return ; 

		ArrayList <String> listIdSeed = new ArrayList<String> ( graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.string));	//	System.out.println(listNodeNet);
		ArrayList<Integer> listIdEdgeInt = new ArrayList<Integer> ( graphToolkit.getListElement(netGraph, element.edge, elementTypeToReturn.integer)) ;			

		for ( String idSeed :  listIdSeed ) {						//	System.out.println(idSeed);	
		
			Node nodeNetSeed = netGraph.getNode(idSeed) ;			// System.out.println(netGraph.getNodeSet());			
			ArrayList<String> listIdNeig = new ArrayList<String> () ;
			try {
				listIdNeig = new ArrayList<String> (graphToolkit.getListNeighbor( netGraph, idSeed, elementTypeToReturn.string)) ;
			} catch (NullPointerException e) {
				continue ;
			}
				
			for ( String idNeig : listIdNeig ) {
			
				ArrayList<String> listIdNeig2 = new ArrayList<String> (graphToolkit.getListNeighbor( netGraph, idNeig , elementTypeToReturn.string)) ;
				
				Node nodeFat = netGraph.getNode(idNeig) ;	//	System.out.println(listIdNeig2.size());

				if ( listIdNeig2.size() > 2 ) 
					continue ;
				
				listIdNeig2.remove(idSeed);					//	System.out.println(listIdNeig2);
			
				for ( String idNeig2 : listIdNeig2) {
					
					Node nodeGranFat = netGraph.getNode(idNeig2) ;
					
					double dist = graphToolkit.getDistNodeEdge(nodeNetSeed, nodeGranFat, nodeFat, false );		//	System.out.println("dist " + dist);		System.out.println("epsilon " + epsilon );
					
					if ( dist > epsilon )
						continue;
					
					else if ( dist <= epsilon  ) {	//			//		System.out.println("nodeNetSeed " + nodeNetSeed ) ;		//		System.out.println("nodeFat " + nodeFat ) ;			//		System.out.println("nodeGranFat " + nodeGranFat ) ;		//		System.out.println("dist " + dist);	
						
						listIdEdgeInt = new ArrayList<Integer> ( graphToolkit.getListElement(netGraph, element.edge, elementTypeToReturn.integer)) ;			
						int idEdgeInt = Collections.max(listIdEdgeInt) ;		
						while ( listIdEdgeInt.contains(idEdgeInt))
							idEdgeInt++ ;

						String idEdge = Integer.toString(idEdgeInt) ;
						try {
							netGraph.addEdge(idEdge, nodeNetSeed, nodeGranFat) ;
							netGraph.removeNode(nodeFat);			
						}
						catch (org.graphstream.graph.EdgeRejectedException e) {
						//	continue;
						}
					
					}																						
				}	
			}
		}
	}		
}
