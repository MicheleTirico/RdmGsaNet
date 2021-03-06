package RdmGsaNetAlgo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.OptionalDouble;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.graphicGraph.StyleGroupSet.ZIndex;

public class morpSpatialAutoCor extends graphAnalysis {
	
	public static enum distanceMatrixType { topo , weight }
	public static distanceMatrixType type ;
	
	private static double zi;
	private static double sumWijZj ;
	
	public static double[][] getMatrixLisa ( Graph graph ,  double[][] distanceMatrix  ) {
		
		int nodeCount = graph.getNodeCount();
	
		double [][] matrixLisa = new double [nodeCount] [nodeCount];
		
		for ( int x = 0 ; x < matrixLisa.length ; x++) {	
			for ( int y = 0 ; y < matrixLisa.length ; y++) {
				
				if ( distanceMatrix [x][y] == 0 ) 	{	matrixLisa[x][y] = 0 ;	}
				else 								{	matrixLisa[x][y] = ( 1 / distanceMatrix [x][y] );	}
				}
			}
		
		return matrixLisa ;
	}
	
	public static ArrayList< String > getNodeInRadId (Graph graph , Node n , double radius, distanceMatrixType type, ArrayList<String> listIdGs) {
		
		int NodePosInList = listIdGs.indexOf(n.getId());
		
		double [][] distanceMatrix = getDistanceMatrix(graph, type) ;
		
		ArrayList< String > nodeInRadId = new ArrayList<String>(); 

		for ( int x = 0 ; x < distanceMatrix.length ; x++) {		
			int y = NodePosInList ; 
			if ( distanceMatrix[x][y] <= radius /* && n.getId() != listIdGs.get(x) */  ) {
				String idNode = listIdGs.get(x); 
				nodeInRadId.add(idNode); 
			}
		}	//		System.out.println(nodeInRadId);	
		return nodeInRadId;
	}
	
	public static Map < String, Double> getMapIdMorpInRad ( Graph graph , morphogen MorpType, ArrayList<String> nodeInRadId  ) { 
		
		Map < String, Double> mapIdMorpInRad = new HashMap<String, Double >();
		String morp = null ;
		
		switch (MorpType) {
		case activator :	{ morp = "gsAct";
							break; }
		case inhibitor :	{ morp = "gsInh";
							break; }
		}
		
		for ( String nodeStr : nodeInRadId) {	
		
			Node node = graph.getNode(nodeStr);
			mapIdMorpInRad.put(nodeStr, node.getAttribute(morp));
		}
		
		return  mapIdMorpInRad;
	}
	
	public static  double getMeanInRad ( Graph graph, Map < String, Double> mapIdMorpInRad  ) {
		double meanInRad   ;
			
		ArrayList<Double> values = new ArrayList<Double> ( mapIdMorpInRad.values() );					//	System.out.println(values);		
		meanInRad = values	.stream()
							.mapToDouble(val -> val)
							.average()
							.getAsDouble();//		System.out.println(meanInRad);
	
		return meanInRad;
	}
	
	public static int getNodeInRadNumb ( Graph graph ,  ArrayList<String> nodeInRadId )  {
		int nodeInRadNumb = nodeInRadId.size() ;
		return nodeInRadNumb ;
	}
	
	public static double getLisaVal ( 	Graph graph , Node n, ArrayList < String > nodeInRadId , Map < String, Double> mapIdMorpInRad, 
										int nodeInRadNumb , double meanInRad, double m2 , 
										double [][] matrixLisa , ArrayList<String> listIdGs ) {
		
		String idNode = n.getId();																		//	System.out.println(idNode);
		double xi = mapIdMorpInRad.get(idNode);															//	System.out.println("xi " +xi);	
		zi = xi - meanInRad;																			//	System.out.println("zi " + zi);
		
		sumWijZj = 0 ;
		
		for ( String idInRad : nodeInRadId ) {
			
			double xj = mapIdMorpInRad.get(idInRad) ;													//	System.out.println("meanInRad " + meanInRad);
		
			double zj = xj - meanInRad ;																//	System.out.println("idNode "  +idInRad + " / xj " + xj);	//		System.out.println("zj " + zj);
			
			double valMatrixLisa = matrixLisa[listIdGs.indexOf(idInRad)][listIdGs.indexOf(idNode)];		//	System.out.println("valMatrixLisa " + valMatrixLisa);
			
			double newSum = zj * valMatrixLisa ;														//	System.out.println("newSum " + newSum );
			
			sumWijZj = sumWijZj + newSum ;																//	System.out.println("sumWijZj " + sumWijZj );
		
		}																								//	System.out.println("sumWijZj " + sumWijZj );
		double lisaVal = ( zi / m2 ) * sumWijZj ;
	
		return lisaVal ;
	}
	
	public static double getM2 ( Graph graph ,morphogen MorpType,  Map<String , ArrayList<Double >> mapMorp1 ) {
		
		int morp = 0 ;
		int countNodes = graph.getNodeCount(); 	
		ArrayList<Double> values = new ArrayList<Double>();
		
		switch (MorpType) {
		case activator :	{ morp = 0;
							break; }
		case inhibitor :	{ morp = 1;
							break; }
		}
		
		for ( Node n : graph.getEachNode()) {
		
			ArrayList<Double> listMorp =  (ArrayList<Double>) mapMorp1.get(n.getId()) ; 
			double morpVal = listMorp.get(morp);
			values.add(morpVal);	
		}
		
		double sumValues = values	.stream()
									.mapToDouble(val -> val)
									.sum();																//	System.out.println(meanInRad);
		
		double m2 = sumValues / countNodes  ;
		
		return m2;		
	}
	
// METHODS ---------------------------------------------------------------------------------------------------
	public static double[][] getDistanceMatrix ( Graph graph, distanceMatrixType type ) {
		
		int nodeCount = graph.getNodeCount();	
		double [][] distanceMatrix = new double [nodeCount] [nodeCount];

		switch (type) {
		case topo:
			distanceMatrix = gsAlgoToolkit.getDistanceMatrixTopo(graph);
			break;
			
		case weight:
			distanceMatrix = gsAlgoToolkit.getDistanceMatrixWeight(graph);
			break;
		}
		return distanceMatrix;
	}
	
	// set for each an attribute of scatter plot autocorrelation
	public static void setScatterPlotAtr ( Graph graph , String corScatterPlotAtr , double zi , double sumWijZj , double delta ) {
		for ( Node n : graph.getEachNode()) {	
				
			String scatterVal = null;
			if ( 	- delta < 	zi  				&&
								zi 			< delta && 
					- delta <	sumWijZj			&&
								sumWijZj	< delta			) { scatterVal = "NS" ; }
			else {
					if ( zi > 0	&& sumWijZj > 0 ) { scatterVal = "HH" ; }; 
					if ( zi < 0	&& sumWijZj > 0 ) { scatterVal = "LH" ; };
					if ( zi > 0	&& sumWijZj < 0 ) { scatterVal = "HL" ; };	
					if ( zi < 0	&& sumWijZj < 0 ) { scatterVal = "LL" ; };
			}
			n.setAttribute(corScatterPlotAtr, scatterVal);	
		}	
	}
	 	
// get variables 
	public static double getZi () 		{ return zi ; 		}
	public static double getSumWijZj () { return sumWijZj ; }
	
	
	

}
