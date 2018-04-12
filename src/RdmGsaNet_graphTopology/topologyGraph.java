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
	private static Graph	oriGraph = new SingleGraph("oriGraph"),
							topGraph = main.getDelaunayGraph() , 
							seedTriGraph = new SingleGraph("seedTriGraph");	
	
	private boolean runTopology ,
					createTopGraph ,  
					createSeedTriangleGraph  ;
			
	private topologyGraph_inter tgInt ; 
				
	public enum topologyGraphType { voronoi , delaunay }
	protected static topologyGraphType   tgType ; 
		
	// STORING GRAPH EVENTS
	private static FileSinkDGS fsd = new FileSinkDGS();
	private handleNameFile handle = main.getHandle(); 
	private boolean doStoreTopologyGraph ;
	
	public topologyGraph( boolean runTopology , Graph oriGraph , topologyGraphType tgType , boolean createTopGraph , boolean createSeedTriangleGraph ) {
		this.runTopology = runTopology ;
		this.oriGraph = oriGraph ;
		this.tgType = tgType ;
		this.createTopGraph  = createTopGraph ;
		this.createSeedTriangleGraph = createSeedTriangleGraph ;
		
		switch (tgType) {
			case delaunay: 	tgInt = new delaunayGraph_03 ( oriGraph , topGraph , seedTriGraph   )  ;	
				break;
			
			case voronoi : 				
				break;	
		}	
	}

	public void setParameters (  ) {	}


	public void createLayer ( int step )  throws IOException {
		
		if ( runTopology == false )
			return ;
		
		if ( step == 1 ) {
			tgInt.createGeometryOriGraph();
		
			if ( createTopGraph )	
				tgInt.createGraph();
			if ( createSeedTriangleGraph )
				tgInt.createSeedTriangleGraph();
		}			
	}
		
	public void updateLayer ( int step ) {
	

		if ( runTopology == false )
			return ;
		
		if ( step > 1 ) {
			tgInt.updateGeometryOriGraph( ) ;
	
		if ( createTopGraph )	 
			if ( step == simulation.maxStep )	//	System.out.println(simulation.maxStep);
				tgInt.updateGraph();
	
		if ( createSeedTriangleGraph )
			tgInt.updateSeedTriangleGraph();
		
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

	public static Graph getSeedTriGraph() {
		return seedTriGraph ;
	}

}
