package dynamicGraphSimplify;

import org.graphstream.graph.Graph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNetAlgo.graphToolkit;
import RdmGsaNetAlgo.gsAlgoToolkit;

import dynamicGraphSimplify.dynamicSymplify.simplifyType ;



public class dynamicSymplify_deleteNode implements dynamicSymplify_inter {

	private Graph graph = new SingleGraph("graph");
	private double epsilon ;
	
	// constructor 
	public dynamicSymplify_deleteNode( Graph graph , double epsilon ) {
		this.graph = graph ;
		this.epsilon = epsilon; 
	}

	@Override
	public void test() {
		System.out.println(super.toString());
		
	}

	
	@Override
	public void updateFatherAttribute(int step , Map<String, String> mapFather ) {	// System.out.println("nodeCount " +graph.getNodeCount());		System.out.println("epsilon " + epsilon);
			
		for ( Node n : graph.getEachNode() ) {
			
			String father = n.getAttribute("father") ;
			mapFather.put(n.getId(), father) ;
			
			String granFat = mapFather.get(father );		
			n.addAttribute("granFat", granFat);				
		}
	
	}

	@Override
	public void computeDistance( int step ) {
		

		
		
	}

	@Override
	public void handleGraphGenerator(int step ) {
		
		
		for ( Node n : graph.getEachNode() ) {
			
			String idFather = n.getAttribute ( "father" );
			String idGranFat = n.getAttribute ( "granFat" );

			if ( idFather == null | idGranFat == null )
				continue ;
			
			Node nodeFather = graph.getNode(idFather) ;
			Node nodeGrapFat = graph.getNode(idGranFat) ; 	//		
			System.out.println(n.getId() + " " + nodeFather.getId() + " " + nodeGrapFat .getId());
			double dist = graphToolkit.getDistNodeEdge(nodeGrapFat, nodeFather, n, true);		//			
		//	System.out.println("dist " + dist);		System.out.println("epsilon " + epsilon );
			
			if ( dist < epsilon ) 
				continue ;
			else if ( dist >= epsilon ) {
				graph.removeNode(nodeFather);
				n.addAttribute("father", idGranFat );
				
			}
				
		
			
		}
	
		
		
	}

}
