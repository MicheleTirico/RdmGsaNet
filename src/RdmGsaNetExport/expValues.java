package RdmGsaNetExport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class expValues {
	
	
	
	public static Map<String, ArrayList<Double>> getMapIdGsMorp ( Graph graph ) {
		

		Map<String, ArrayList<Double>> mapIdGsMorp = new HashMap<String, ArrayList<Double>>();
		
		for ( Node n : graph.getEachNode()) {
			
			String idNode = n.getId();
			double act = n.getAttribute("gsAct") ;
			double inh = n.getAttribute("gsInh") ;

			ArrayList<Double> morp = new ArrayList<Double> ();
				
			morp.add(act) ;
			morp.add(inh) ;
			
			mapIdGsMorp.put(idNode, morp) ;
			
		}
		
		return mapIdGsMorp ;
	}
}
