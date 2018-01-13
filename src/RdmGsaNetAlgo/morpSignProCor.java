package RdmGsaNetAlgo;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class morpSignProCor {
	
	public enum SPCCtype { ai , ia }
	  
	// map of idNode and signal processing autocorrelation value
	private static Map<String , Double> mapStringSPAC = new HashMap<String, Double> ();
	private static Map<String , Double> mapStringSPACass = new HashMap<String, Double> ();
	private static Map<String , Double> mapStringSPCC = new HashMap<String, Double> ();
	
	/* method to create a map of SPAC value of defined morphogen.
	* 															*/
	public static Map<String, Double> getMapIdSPACval ( Graph graph0 , Graph graph1 , morpAnalysis.morphogen MorpType ) {
		
		Map<String , Double> mapStringMorp0 = new HashMap<String, Double> ();
		Map<String , Double> mapStringMorp1 = new HashMap<String, Double> ();
			
		String morp  ; 
		switch (MorpType) {
			case activator  :	{ morp = "gsAct" ;	
								break; }
			case inhibitor  :	{ morp = "gsInh" ;	
								break; }
			default			:	System.out.println(" morp not defined");
								morp = "000000" ;
		}	
		
		// get maps of idNode and morphogen
		for ( Node n : graph0.getEachNode()) {	mapStringMorp0.put(n.getId(), n.getAttribute(morp)) ; }	
		for ( Node n : graph1.getEachNode()) {	mapStringMorp1.put(n.getId(), n.getAttribute(morp)) ; }
		
		// set spac value in map
		for ( Entry<String, Double> entry : mapStringMorp0.entrySet()) {
			
			String id = entry.getKey();
			
			double morp1 = entry.getValue();
			double morp0 = mapStringMorp0.get(id);
			
			double spac = morp0 * morp1;
			
			mapStringSPAC.put( id, spac );
		}
		return mapStringSPAC ;
	}
	
	/* method to create a map of SPAC value of defined morphogen.
	* morp2 = morp at tn;
	* morp1 = morp at tn-1;
	* morp0 = morp at tn-2;																*/
	public static Map<String , Double> getMapSPACassVal (Graph graph0, Graph graph1, Graph graph2 , morpAnalysis.morphogen MorpType ) {
		
		Map<String , Double> mapStringMorp0 = new HashMap<String, Double> ();
		Map<String , Double> mapStringMorp1 = new HashMap<String, Double> ();
		Map<String , Double> mapStringMorp2 = new HashMap<String, Double> ();
		
		String morp  ; 
		switch (MorpType) {
			case activator  :	{ morp = "gsAct" ;	
								break; }
			case inhibitor  :	{ morp = "gsInh" ;	
								break; }
			default			:	System.out.println(" morp not defined");
								morp = "000000" ;
		}
		for ( Node n : graph0.getEachNode()) {	mapStringMorp0.put(n.getId(), n.getAttribute(morp)) ; }	
		for ( Node n : graph1.getEachNode()) {	mapStringMorp1.put(n.getId(), n.getAttribute(morp)) ; }
		for ( Node n : graph2.getEachNode()) {	mapStringMorp2.put(n.getId(), n.getAttribute(morp)) ; }
		
		for ( Entry<String, Double> entry : mapStringMorp2.entrySet()) {
		
			String id = entry.getKey() ;
			
			double morp2 = entry.getValue();
			double morp1 = mapStringMorp1.get(id);
			double morp0 = mapStringMorp0.get(id);
			
			double SPACass = ( morp2 - morp1) * (morp1 - morp0); 
			
			mapStringSPACass.put(id, SPACass) ;
		}
		return mapStringSPACass ;
	}

	/* method to create a map of SPCC value of morphogens. 
	 * 
	 */
	public static Map<String, Double> getMapSPCCval ( Graph graph0 , Graph graph1  ) {
		
		Map<String , Double> mapStringAct0 = new HashMap<String, Double> ();
		Map<String , Double> mapStringInh0 = new HashMap<String, Double> ();
		
		Map<String , Double> mapStringAct1 = new HashMap<String, Double> ();	
		Map<String , Double> mapStringInh1 = new HashMap<String, Double> ();
		
		for ( Node n : graph0.getEachNode()) {	mapStringAct0.put(n.getId(), n.getAttribute("gsAct")) ; 
												mapStringInh0.put(n.getId(), n.getAttribute("gsInh")) ; }
		
		for ( Node n : graph1.getEachNode()) {	mapStringAct1.put(n.getId(), n.getAttribute("gsAct")) ; 
												mapStringInh1.put(n.getId(), n.getAttribute("gsInh")) ; }
		
		for ( Node n : graph1.getEachNode()) {
			
			String id = n.getId();
		
			double 	val1 = 0 ; 	// not yet defined
			double	val2 = 0 ;	// not yet defined
			
			double spcc = val1 * val2 ;
		
			mapStringSPAC.put( id, spcc );
		}
		return  mapStringSPCC ;
	}

}
