package RdmGsaNet_pr08;

import java.util.ArrayList;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import RdmGsaNetViz.setupViz;
import graphstream_dev_toolkit.toolkit;

public class TESTviz {

	static Graph netGraph = new SingleGraph ("")  ,
					gsGraph = new SingleGraph ("") ;
	
	public static void main(String[] args) {
		
		
		toolkit.createGraphRandom(netGraph, 10);
		toolkit.createGraphGrid(gsGraph, 10, true);
		
		netGraph.display(true);
		gsGraph.display(true);
		
		main.printEdgeSetAttribute(true, netGraph);
//		setupViz.setFixScale(netGraph, gsGraph);

	}

}
