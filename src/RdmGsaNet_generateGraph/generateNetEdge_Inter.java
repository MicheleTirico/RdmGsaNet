package RdmGsaNet_generateGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNet_mainSim.layerNet;
import RdmGsaNet_mainSim.main;

public interface generateNetEdge_Inter {

	// VARIABLES
	
	public static Graph netGraph  = layerNet.getGraph() , 
						seedGraph = main.getSeedGraph() ;
	
	// costants
//	public enum generateEdgeType { near , preferentialAttachment  }
	
	
	
	// METHODS
	public void generateEdgeRule ( double step ) ;

	public void removeEdgeRule(double step);

	public void setPivot (boolean createPivot, double maxDistPivot , Graph graph , String idNode , String idFather  ) ;
	
	
		
}
