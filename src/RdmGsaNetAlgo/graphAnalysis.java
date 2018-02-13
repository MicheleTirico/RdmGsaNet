package RdmGsaNetAlgo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;


import RdmGsaNet_pr08.*;

public class graphAnalysis {
	
	private static Graph gsGraph = layerGs.getGraph();
	private static Graph netGraph = layerNet.getGraph();
	public enum morphogen {activator, inhibitor }
	protected static ArrayList<String> listIdGs = simulation.getListIdGs();

	private static Map<Double , Graph > mapStepNetGraph = simulation.getMapStepNetGraph() ;
	private static Map<String, ArrayList<Double >> mapMorp0 = simulation.getmapMorp0() ;
	private static Map<String, ArrayList<Double >> mapMorp1 = simulation.getmapMorp1() ;

	private Map<String, Double > mapIdLisa = new HashMap<String , Double >() ;
	
	public enum analysisType { average , max , min , count }
	public enum spatialAutoCor { moran, lisa }
	
	private static double [][] matrixLisa ;
	
// SPATIAL AUTO CORRELATION LISA LOCAL MORAN  ---------------------------------------------------------------------------------------------------------------------------	
	public static void spatialAutoCorLisaLocalMoran ( Graph graph , morphogen MorpType , double radius , morpSpatialAutoCor.distanceMatrixType type) {
		System.out.println("lisa");
		String morp  ;
		if (MorpType == morphogen.activator ) 	{ morp = "Act" ; }
		else									{ morp = "Inh" ; }
		
		String corAttributeStr	 = "gsLisa" + morp ;
		String corScatterPlotAtr = "gsScatter" + morp ;
		
		// get distance matrix
		double [][] distanceMatrix = morpSpatialAutoCor.getDistanceMatrix(graph, type) ;
		System.out.println("finish calcule " + "distanceMatrix" );
		
		// get matrix Lisa ( Wij )
		matrixLisa = morpSpatialAutoCor.getMatrixLisa(gsGraph, distanceMatrix) ;

		System.out.println("finish calcule " + "matrixLisa" );
	
		double m2 = morpSpatialAutoCor.getM2(graph, MorpType, mapMorp1);
		System.out.println( "m2 " + m2);
		
		for ( Node n : graph.getEachNode() ) {			//	System.out.println(n.getId());
			
			ArrayList < String > nodeInRadId = morpSpatialAutoCor.getNodeInRadId(graph, n, radius, type , listIdGs);
			Map < String, Double> mapIdMorpInRad = morpSpatialAutoCor.getMapIdMorpInRad(graph, morphogen.activator , nodeInRadId);
					
			int nodeInRadNumb = morpSpatialAutoCor.getNodeInRadNumb(graph, nodeInRadId);
			double meanInRad  = morpSpatialAutoCor.getMeanInRad(graph, mapIdMorpInRad );					// System.out.println("\n"+"id node " + n.getId());		//			System.out.println("nodeInRadNumb " + nodeInRadNumb);//			System.out.println("meanInRad " + meanInRad);//			System.out.println( "nodeInRadId" + nodeInRadId);//			System.out.println( "mapIdMorpInRad " + mapIdMorpInRad);
			
			double lisaValue = morpSpatialAutoCor.getLisaVal(graph, n, nodeInRadId, mapIdMorpInRad,nodeInRadNumb, meanInRad, m2, matrixLisa, listIdGs);	//			System.out.println( "lisa value "+ lisaValue);
			
			setCorValInGraph(graph, lisaValue, corAttributeStr);											//			Double lisa = n.getAttribute(corAttributeStr);//			System.out.println( "lisa " + lisa );
			
			// set scatterplot val
			double zi = morpSpatialAutoCor.getZi();
			double sumWijZj = morpSpatialAutoCor.getSumWijZj();												//			System.out.println( "zi " + zi );//			System.out.println( "sumWijZj " + sumWijZj );
			
			morpSpatialAutoCor.setScatterPlotAtr(graph, corScatterPlotAtr, zi, sumWijZj, 0.05);
		}
		System.out.println("stop");
	}
	
// SIGNAL PROCESSING AUTO CORRELATION -------------------------------------------------------------------------------------------------------------------------------
	public static void SignalAutoCor ( Graph graph0 , Graph graph1 ) {
		System.out.println("Auto correlation signal processing");
			
		Map<String , Double> mapIdNodeSPAC = morpSignProCor.getMapIdSPACval(graph0, graph1, morphogen.activator);

	}
	
// SIGNAL PROCESSING CROSS CORRELATION -------------------------------------------------------------------------------------------------------------------------------	
	public static void SignalCrossCor (Graph graph0 , Graph graph1 ) {
	
		System.out.println("Cross correlation signal processing");	
		Map<String , Double> mapIdNodeSPCC = morpSignProCor.getMapSPCCval(graph0, graph1 );
	}

// SPATIAL AUTO CORRELATION MORAN  -----------------------------------------------------------------------------------------------------------------------------------		
	public static void spatialAutoCorMoran () {
		
		System.out.println("spatial auto correlation moran");
	}

// DATA ANALYSIS ------------------------------------------------------------------------------------------------------------------------------------	
	// get a statistical value of attribute of node 
	public static double getAttributeStatistic (Graph graph , String attribute , analysisType type  ) { 
		
		double statisticVal = 0 ;
		ArrayList<Double> arrAttr = new ArrayList<Double>();
		
		for ( Node n : graph.getEachNode()) {
			try {
				double attrVal = (double) n.getAttribute(attribute);												//	System.out.println(arrMorp);
				arrAttr.add(attrVal);
			}
			catch (java.lang.NullPointerException e) {
				// TODO: handle exception
			}
		}	
		switch (type) {
			case average: 				
				statisticVal = arrAttr.stream().mapToDouble(valstat -> valstat).average().getAsDouble();		
				break;
			case max : 				
				statisticVal = arrAttr.stream().mapToDouble(valstat -> valstat).max().getAsDouble();			
				break;
			case min :
				statisticVal = arrAttr.stream().mapToDouble(valstat -> valstat).min().getAsDouble();			
				break;
			case count : 
				statisticVal =  arrAttr.stream().mapToDouble(valstat -> valstat).count();			
				break ;
		}
		return statisticVal;
	}
	
	public static Map<Double, Double> getMapFrequencyRel ( Graph graph , String attribute , int numberFrequency ) {
		
		Map<Double, Double> mapFrequency = new HashMap<>();
		Map <Node, Double> mapIdAtr = new HashMap<>();
		ArrayList<Double> listAtr = new ArrayList<>();
		double val;
		
		for ( Node n : graph.getEachNode() ) {	
			if (attribute == "degree" )  	
				val = n.getDegree();
			else							
				val =  n.getAttribute(attribute) ;
			
			mapIdAtr.put(n, val);
			listAtr.add(val);
		}																							//	System.out.println("listAtr " + listAtr);
		
		double maxAtr = listAtr.stream().mapToDouble(valstat -> valstat).max().getAsDouble();
		double minAtr = listAtr.stream().mapToDouble(valstat -> valstat).min().getAsDouble(); 		//			System.out.println("maxAtr " + maxAtr);		System.out.println("minAtr " + minAtr);
		
		double gap = maxAtr - minAtr;
		double increm = minAtr + gap / numberFrequency;												//	System.out.println("gap " + gap);	System.out.println("increm " + increm);
		
		for ( int x = 0 ; x < numberFrequency ; x++) {
			double key = minAtr + gap * x / numberFrequency ;
			
			double minFreq = minAtr + x* increm ;
			double maxFreq = minAtr + (x + 1)* increm ;
			
			double freq = 	listAtr.stream()
							.filter(p -> p >=  minFreq && p < maxFreq )
							.count();
			
			mapFrequency.put(  key  ,  freq );
		}
		return mapFrequency;	
	}
	
	public static Map getMapFrequencyAss ( Graph graph , String attribute , int numberFrequency ) {
		
		Map<Double, Double> mapFrequency = new HashMap<>();
		Map <Node, Double> mapIdAtr = new HashMap<>();
		ArrayList<Double> listAtr = new ArrayList<>();
		
		for ( Node n : graph.getEachNode() ) {
			double val;
			if (attribute == "degree" )  	
				val = n.getDegree();
			else							
				val =  n.getAttribute(attribute) ;
			mapIdAtr.put(n, val);
			listAtr.add(val);
		}																							//	System.out.println("listAtr " + listAtr);	
		double maxAtr = 1;
		double minAtr = 0;
		
		double gap = maxAtr - minAtr;
		double increm = minAtr + gap / numberFrequency;												//	System.out.println("gap " + gap);	System.out.println("increm " + increm);
		
		for ( int x = 0 ; x < numberFrequency ; x++) {
			double key = minAtr + gap * x / numberFrequency ;		
			double minFreq = minAtr + x* increm ;
			double maxFreq = minAtr + (x + 1)* increm ;			
			double freq = 	listAtr.stream()
							.filter(p -> p >=  minFreq && p < maxFreq )
							.count();
			
			mapFrequency.put( key  ,  freq );
		}
		return mapFrequency;		
	}
	
	public static Map getMapFrequencyDegree ( Graph graph ,  int numberFrequency , boolean isRel ) {
		
		Map<Double, Double> mapFrequency = new HashMap<>();
		Map<Double, Double> mapFrequencyRel = new HashMap<>();
		Map <Node, Double> mapIdAtr = new HashMap<>();
		ArrayList<Double> listAtr = new ArrayList<>();
		
		for ( Node n : graph.getEachNode() ) {	
			double val = n.getDegree();
			mapIdAtr.put(n, val);
			listAtr.add(val);
		}
		
		double maxAtr = listAtr.stream().mapToDouble(valstat -> valstat).max().getAsDouble();
		double minAtr = 1;
		
		double gap = maxAtr - minAtr;
		double increm = 1 ;												//	System.out.println("gap " + gap);	System.out.println("increm " + increm);
		
		for ( int x = 0 ; x < numberFrequency ; x++) {
			double key = minAtr +  x  ;		
			double minFreq = minAtr + x* increm ;
			double maxFreq = minAtr + (x + 1)* increm ;			
			double freq = 	listAtr.stream()
							.filter(p -> p >=  minFreq && p < maxFreq )
							.count();
			
			mapFrequency.put( key  ,  freq );
		}
		
		if ( isRel) {
			for ( Double key : mapFrequency.keySet()) {
				mapFrequencyRel.put(key, mapFrequency.get(key)  /graph.getNodeCount() )  ;
			}
			return mapFrequencyRel ;	
		}
		else {
			return mapFrequency;		
		}
	}

	public static  double getAttributeCount (Graph graph , double step , String attribute ) {
		
		ArrayList<Integer> listAttr = new ArrayList();
		for ( Node n : graph.getEachNode()) {
			try {
				int valAtr = n.getAttribute(attribute) ;
				if ( valAtr == 1 ) 
					listAttr.add(n.getAttribute(attribute));
			}
			catch (java.lang.NullPointerException e) {
				// TODO: handle exception
			}
		}
		return listAttr.size() ;
	}
	
	// get map of normal degree distribution // map <degree , normal distribution >  
	public static Map getNormalDegreeDistribution ( Graph graph ) {
		
		int[] degreeDist = Toolkit.degreeDistribution(graph);

		Map< Double , Double > mapDegreeNormalDistr = new HashMap< Double , Double > ();
		
		for ( int i = 0 ; i < degreeDist.length ; i++ )  {
			mapDegreeNormalDistr.put((double) i, (double) degreeDist[i] / graph.getNodeCount());	
		}
		return mapDegreeNormalDistr;	
	}
	
	// return map of nodes with clustering coefficient
	public static Map getMapNodeClustering ( Graph graph ) {
		Map<Node, Double > map = new HashMap<Node, Double>() ;
		
		for ( Node n : graph.getEachNode() ) {
			Double val = Toolkit.clusteringCoefficient(n);
			map.put(n, val) ;
		}
		return map;
	}
	
	
// PRIVATE METHODS ---------------------------------------------------------------------------------------------------------------------------------------------------
	// set for each nodes an attribute that means the value of correlation
	private static void setCorValInGraph ( Graph graph , double val , String corAttributeStr) {		
		for ( Node n : graph.getEachNode()) {	n.setAttribute(corAttributeStr, val);	}
	}
		
	// not yet implemented
	private static Map getMapFequency ( Graph graph , String attribute , int numberFrequency , String typeFrequency ) {
		
		Map<Double, Double> mapFrequency = new HashMap<>();
		Map <Node, Double> mapIdAtr = new HashMap<>();
		ArrayList<Double> listAtr = new ArrayList<>();
		double val;
		
		for ( Node n : graph.getEachNode() ) {
			
			if (attribute == "degree" )  	
				val = n.getDegree();
			else							
				val =  n.getAttribute(attribute) ;
			
			mapIdAtr.put(n, val);
			listAtr.add(val);
		}																							//	System.out.println("listAtr " + listAtr);
		
		double maxAtr = listAtr.stream().mapToDouble(valstat -> valstat).max().getAsDouble();
		double minAtr = listAtr.stream().mapToDouble(valstat -> valstat).min().getAsDouble(); 		//	System.out.println("maxAtr " + maxAtr);		System.out.println("minAtr " + minAtr);
		
		double gap = maxAtr - minAtr;
		double increm = minAtr + gap / numberFrequency;												//	System.out.println("gap " + gap);	System.out.println("increm " + increm);
		
		for ( int x = 0 ; x < numberFrequency ; x++) {
			double key = minAtr + gap * x / numberFrequency ;
			
			double minFreq = minAtr + x* increm ;
			double maxFreq = minAtr + (x + 1)* increm ;
			
			double freq = 	listAtr.stream()
							.filter(p -> p >=  minFreq && p < maxFreq )
							.count();
			
			mapFrequency.put(  key  ,  freq);
		}
		return mapFrequency;
	}
}


