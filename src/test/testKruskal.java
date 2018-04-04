package test;

import java.util.ArrayList;

import org.graphstream.algorithm.Kruskal;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import RdmGsaNetAlgo.graphGenerator;
import RdmGsaNetAlgo.graphToolkit;
import RdmGsaNetAlgo.graphToolkit.element;
import RdmGsaNetAlgo.graphToolkit.elementTypeToReturn;
import RdmGsaNetAlgo.gsAlgoToolkit;

public class testKruskal {

	public static void main(String[] args) {

		Graph graph = new SingleGraph("test") ;
		
		double[] meanPointCoord = new double[2] ;
		meanPointCoord[0] = 10.0 ;
		meanPointCoord[1] = 10.0 ;
	
		graphGenerator.createCompleteGraph(graph, 10, meanPointCoord, 5	,1 );
		
		String css = "edge .notintree {size:1px;fill-color:gray;} " +
				 "edge .intree {size:3px;fill-color:black;}";
		graph.addAttribute("ui.stylesheet", css);
		
		for ( Edge e : graph.getEachEdge() ) {
			//graphToolkit.
			Node 	n0 = e.getNode0() ,
					n1 = e.getNode1() ;
			
			double dist = gsAlgoToolkit.getDistGeom(n0, n1);
			e.addAttribute("weight", dist );
		}
		
			
		Kruskal kruskal = new Kruskal(  "tree" ,  true , false ) ;
		
		kruskal.init(graph) ;
		kruskal.compute();
		ArrayList<Edge> listEdgeToRemove = new ArrayList<Edge> () ;
		for ( Edge e : graph.getEachEdge()) {
		
//			System.out.println(e.getAttributeKeySet());
			boolean tree = e.getAttribute("tree");
		//	System.out.println(tree) ;
			
			if ( tree == false )
				listEdgeToRemove.add(e);
				//	System.out.println(e);
			
		}
	
		for ( Edge e : listEdgeToRemove) 
			graph.removeEdge(e) ;
		
		System.out.println(kruskal.getTreeWeight() ) ;
		
		
		graph.display(false);
	}
}
