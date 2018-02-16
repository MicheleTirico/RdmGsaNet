package RdmGsaNetAlgo;

import java.util.HashMap;
import java.util.Map;

import org.graphstream.algorithm.BetweennessCentrality;
import org.graphstream.algorithm.Toolkit;
import org.graphstream.algorithm.measure.ClosenessCentrality;
import org.graphstream.algorithm.measure.AbstractCentrality.NormalizationMode;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public abstract class graphIndicators {
	private static Map< Node , Double > map = new HashMap< Node , Double > ();
	
	public static Map< Node , Double > getMapCloseness ( Graph graph , String attribute , boolean normalize ) {
		
		ClosenessCentrality cc = null ;
		if ( normalize )
			 cc = new ClosenessCentrality ( attribute , NormalizationMode.MAX_1_MIN_0, true, false);
	
		else if ( normalize == false ) 
			cc = new ClosenessCentrality ( attribute , NormalizationMode.NONE, true, false);
			
		cc.init(graph);
		cc.compute();
		
		for ( Node n : graph.getEachNode() ) {
			double val = (double) n.getAttribute(attribute) 	;
			map.put(n, val);
		}
		return map;	
	}

	public static Map< Node , Double > getMapBetweenness ( Graph graph , String attribute , boolean normalize ) {
		
		int nodeCount = graph.getNodeCount() ;
		double valNorm = 2.0 / ( ( nodeCount - 1 ) * ( nodeCount - 2 ) ) ;
		
		BetweennessCentrality bc = new BetweennessCentrality ( attribute );
	
		bc.init(graph);
		bc.compute();
		
		for ( Node n : graph.getEachNode() ) {
		
			double bet = (double) n.getAttribute(attribute);
			double val  = 0 ; 
			
			if ( normalize ) 
				val = bet * valNorm ;
			
			else if ( normalize == false ) 
				val = bet;
			
			map.put(n, val);
		}
		return map;
		
	}

	public static Map< Node , Double > getMapNodeClustering ( Graph graph ) {
		
		for ( Node n : graph.getEachNode() ) {
			double val = Toolkit.clusteringCoefficient(n);
			map.put(n, val) ;
		}
		return map;
	}
	
}
