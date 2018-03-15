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

	Graph graph = new SingleGraph("graph");
	double epsilon ;
	






	public dynamicSymplify_deleteNode( Graph graph , double epsilon ) {
		this.graph = graph ;
		this.epsilon = epsilon; 
		// TODO Auto-generated constructor stub
	}

	@Override
	public void test() {
		System.out.println(super.toString());
		
	}

	@Override
	public void updateFatherAttribute() {
		System.out.println(graph.getNodeCount());
		System.out.println(epsilon);

		
		
	}

	@Override
	public void computeDistance() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleGraphGenerator() {
		// TODO Auto-generated method stub
		
	}

}
