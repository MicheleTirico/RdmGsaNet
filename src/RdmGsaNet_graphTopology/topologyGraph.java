package RdmGsaNet_graphTopology;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSinkDGS;

import com.vividsolutions.jts.geom.Point;

import RdmGsaNetAlgo.graphToolkit;
import RdmGsaNetAlgo.graphToolkit.element;
import RdmGsaNetAlgo.graphToolkit.elementTypeToReturn;
import RdmGsaNetExport.handleNameFile;

import RdmGsaNet_mainSim.main;
import RdmGsaNet_mainSim.simulation;

public class topologyGraph  {
	
	// graphs
	private static Graph	oriGraph = new SingleGraph("origraph"),
							topGraph = main.getDelaunayGraph();	
	
	private boolean createGraph  ;
			
	private topologyGraph_inter tgInt ; 
				
	public enum topologyGraphType { voronoi , delaunay }
	protected static topologyGraphType   tgType ; 
		
	// STORING GRAPH EVENTS
	static FileSinkDGS fsd = new FileSinkDGS();
	handleNameFile handle = main.getHandle(); 
	private boolean doStoreTopologyGraph ;
	
	protected static Map < Node , Point > mapOriNodePoint = new HashMap<>(); 
	
	public topologyGraph( Graph oriGraph , topologyGraphType tgType , boolean createGraph  ) {
		this.oriGraph = oriGraph ;
		this.tgType = tgType ;
		this.createGraph  = createGraph ;
		
		switch (tgType) {
		case delaunay: 	tgInt = new delaunayGraph_02 ( oriGraph , topGraph , createGraph )  ;	
			break;
		
		case voronoi : 				
			break;
		}
	}

	public void setParameters (  ) {

	}

	
	
	
	
	public void createLayer ( int step )  throws IOException {
		
		if ( step == 1 ) {
			tgInt.createGeometryOriGraph();
			tgInt.createGraph();
		}
		
		
	}
	
	
	public void updateLayer(  int step , Map < Double , ArrayList<String> > mapStepNewNodeId  ) {

		if ( step !=1 ) {
			tgInt.updateGeometryOriGraph( step , mapStepNewNodeId  ) ;
			
			if ( step == simulation.maxStep) 
				tgInt.updateGraph();
		}
	}
	
	
	
	public void computeTest ( ) {
		tgInt.test();
		
	
	}
	
// get methods --------------------------------------------------------------------------------------------------------------------------------------
	
	public static Graph getOriGraph () {
		return oriGraph ;
	}


	public static Graph getTopGraph () {
		return topGraph ;
	}

}
