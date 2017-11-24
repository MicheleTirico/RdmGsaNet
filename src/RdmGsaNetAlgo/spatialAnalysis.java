package RdmGsaNetAlgo;

import java.util.ArrayList;
import java.util.Map;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Graph;
import static org.graphstream.algorithm.Toolkit.*;

import RdmGsaNet_pr08.*;

public class spatialAnalysis {
	
	private static Graph gsGraph = layerGs.getGraph();
	private static Graph netGraph = layerNet.getGraph();
	
	private static Map<Double , Graph > mapStepNetGraph = simulation.getMapStepNetGraph() ;
	private static Map<String, ArrayList<Double >> mapMorp0 = simulation.getmapMorp0() ;
	private static Map<String, ArrayList<Double >> mapMorp1 = simulation.getmapMorp1() ;

	public enum spatialAutoCor { moran, lisa }

	public static void spatialAutoCor (spatialAutoCor type) {
		
		switch ( type) {
		case moran : {	spatialAutoCorMoran();}
		break ;
		
		case lisa : {	spatialAutoCorLisa(); }
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
	
	private static void spatialAutoCorLisa () {
		System.out.println("lisa");
		
		// create matrix ( external method )
		int [][] gino = Toolkit.getAdjacencyMatrix(netGraph);
//		System.out.println(gino);
		
		// get variables
		
		// return map of id nodes and lisa
		
		//  add attribute to gsGraph
		
		
	}

}
