package RdmGsaNet_graphTopology;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSinkDGS;

import RdmGsaNetExport.handleNameFile;

import RdmGsaNet_mainSim.main;

public class topologyGraph  {
	
	// graphs
	private static Graph	oriGraph = new SingleGraph("origraph"),
							topGraph = main.getDelaunayGraph();	
			
	private topologyGraph_inter tgInt ; 
				
	public enum topologygraphType { voronoi , delaunay }
	protected static topologygraphType  tgType ; 
		
	// STORING GRAPH EVENTS
	static FileSinkDGS fsd = new FileSinkDGS();
	handleNameFile handle = main.getHandle(); 
	private boolean doStoreTopologyGraph ;
	
	
	public topologyGraph( Graph oriGraph , topologygraphType tgType ) {
		this.oriGraph = oriGraph ;
		this.tgType = tgType ;
		
		switch (tgType) {
		case delaunay: 	tgInt = new delaunayGraph ( oriGraph , topGraph )  ;	
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
