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
			e.addAttribute("weight", 1 );
		}
		
			
		Kruskal kruskal = new Kruskal("ui.class" , "intree", "notintree") ;
		
		kruskal.init(graph) ;
		kruskal.compute();
		
		System.out.println(kruskal.getTreeWeight() ) ;
		
		
		graph.display(false);
	}
}
