package RdmGsaNetAlgo;

import java.util.HashMap;
import java.util.Map;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.algorithm.measure.ClosenessCentrality;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public abstract class graphIndicators {
	private static Map< Node , Double > map = new HashMap();
	
	public static Map< Node , Double > getMapCloseness ( Graph graph , String attribute ) {
		
//		System.out.println(graph.getNode("25_25").getAttributeKeySet());
		
		ClosenessCentrality cc = new ClosenessCentrality(attribute);
		
//		for ( Node n : graph.getEachNode() ) {	n.addAttribute("length", 1 );	}
//		cc.copyValuesTo("length");
		cc.getNormalizationMode();
		cc.init(graph);
		cc.compute();
		
		for ( Node n : graph.getEachNode() ) {
			double val = (double) n.getAttribute(attribute) * 1 	;
			map.put(n, val);
		}
		return map;
		
	}
	/*
	BetweennessCentrality bcb = new BetweennessCentrality();
	 *		bcb.setWeightAttributeName("weight");
	 *		bcb.init(graph);
	 *		bcb.compute();
*/
	
	public static Map getMapNodeClustering ( Graph graph ) {
		
		for ( Node n : graph.getEachNode() ) {
			double val = Toolkit.clusteringCoefficient(n);
			map.put(n, val) ;
		}
		return map;
	}
	
}
