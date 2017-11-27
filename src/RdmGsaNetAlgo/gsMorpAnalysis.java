package RdmGsaNetAlgo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import RdmGsaNetAlgo.gsMorpSpatialAutoCor.distanceMatrixType;

import static org.graphstream.algorithm.Toolkit.*;

import RdmGsaNet_pr08.*;
import graphstream_dev.*;

public class gsMorpAnalysis {
	
	private static Graph gsGraph = layerGs.getGraph();
	private static Graph netGraph = layerNet.getGraph();
	public enum morphogen {activator, inhibitor }
	protected static ArrayList<String> listIdGs = simulation.getListIdGs();

	private static Map<Double , Graph > mapStepNetGraph = simulation.getMapStepNetGraph() ;
	private static Map<String, ArrayList<Double >> mapMorp0 = simulation.getmapMorp0() ;
	private static Map<String, ArrayList<Double >> mapMorp1 = simulation.getmapMorp1() ;

	private Map<String, Double > mapIdLisa = new HashMap<String , Double >() ;
	
	public enum spatialAutoCor { moran, lisa }
	
	private static double [][] matrixLisa ;
	
	public static void spatialAutoCor (spatialAutoCor SpatialType , Graph graph , morphogen MorpType , double radius , gsMorpSpatialAutoCor.distanceMatrixType type) {
		

		switch ( SpatialType ) {
		case moran : {	spatialAutoCorMoran();}
		break ;
		
		case lisa : {	spatialAutoCorLisa(graph , radius , type , MorpType); }
		break ;
		}	
	}
	
	public static void SignalAutoCor () {
		System.out.println("signal");
	}

// PRIVATE METHODS --------------------------------------------------------------------------------------------------------------
	
	private static void spatialAutoCorMoran () {
		System.out.println("moran");
	
	} 
	
	private static void spatialAutoCorLisa ( Graph graph , double radius ,  gsMorpSpatialAutoCor.distanceMatrixType type , morphogen MorpType  ) {
		System.out.println("lisa");
	
		// get distance matrix
		double [][] distanceMatrix = gsMorpSpatialAutoCor.getDistanceMatrix(graph, type) ;
		
		// get matrix Lisa ( Wij )
		matrixLisa = gsMorpSpatialAutoCor.getMatrixLisa(gsGraph, distanceMatrix) ;
//		graphstream_dev_toolkit.runTest.printMatrix2dDouble(matrixLisa);
		
		// loop
		Iterator<Node> iterNode = graph.iterator();
//		Node n = graph.getNode("0_0");
//		ArrayList < String > nodeInRadId = gsMorpSpatialAutoCor.getNodeInRadId(graph, n, radius, type , listIdGs);
		
		// start iterator loop
		while (iterNode.hasNext()) {				
			Node n = iterNode.next();			//	System.out.println(n.getId());
			
			ArrayList < String > nodeInRadId = gsMorpSpatialAutoCor.getNodeInRadId(graph, n, radius, type , listIdGs);
			Map < String, Double> mapIdMorpInRad = gsMorpSpatialAutoCor.getMapIdMorpInRad(graph, morphogen.activator , nodeInRadId);
					
			int nodeInRadNumb = gsMorpSpatialAutoCor.getNodeInRadNumb(graph, nodeInRadId);
			double meanInRad  = gsMorpSpatialAutoCor.getMeanInRad(graph, mapIdMorpInRad );
			
			double m2 = gsMorpSpatialAutoCor.getM2(graph, MorpType, mapMorp1);
			
			double lisaValue = gsMorpSpatialAutoCor.getLisaVal(graph, n, nodeInRadId, mapIdMorpInRad, nodeInRadNumb, meanInRad, m2, matrixLisa, listIdGs);
			
			
//			System.out.println("nodeInRadNumb " + nodeInRadNumb);
//			System.out.println("meanInRad " + meanInRad);
//			System.out.println( "nodeInRadId" + nodeInRadId);
//			System.out.println( "mapIdMorpInRad " + mapIdMorpInRad);
//			System.out.println( "m2 " + m2);
			System.out.println( lisaValue);
		}
			
			
			
		
		
		
		
	
		
		// get variables
		
		// return map of id nodes and lisa
		
		//  add attribute to gsGraph
		
		
	}

}
