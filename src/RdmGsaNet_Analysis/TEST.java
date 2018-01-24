package RdmGsaNet_Analysis;

import java.io.IOException;
import java.util.ArrayList;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;



import RdmGsaNetAlgo.gsAlgoToolkit;
import graphstream_dev_toolkit.toolkit;

public class TEST {
	static Graph graph0 = new SingleGraph("graph");
	static Graph graph1 = new SingleGraph("graph");

	static Graph graph2 = new SingleGraph("graph");
	
	public static void main (String[] args) throws InterruptedException  {
		
		toolkit.createGraphGrid(graph0, 1, true); 
		toolkit.createGraphGrid(graph1, 2, true); 
		
	
		toolkit.createGraphRandom(graph2, 100);
		
		graph0.display(false);
		graph1.display(false);
		
		ArrayList<String> list = gsAlgoToolkit.getListNewNode(graph0 , graph1) ;
		
		System.out.println(list);
	}
	
}
