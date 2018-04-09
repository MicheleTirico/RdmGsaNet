package RdmGsaNet_graphTopology;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.geotools.data.DataUtilities;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.FeatureCollection;
import org.geotools.geometry.GeometryBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;
import org.opengis.feature.simple.SimpleFeature;

import com.mongodb.client.model.geojson.MultiLineString;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.triangulate.DelaunayTriangulationBuilder;
import com.vividsolutions.jts.triangulate.IncrementalDelaunayTriangulator;
import com.vividsolutions.jts.triangulate.quadedge.QuadEdge;
import com.vividsolutions.jts.triangulate.quadedge.QuadEdgeSubdivision;
import com.vividsolutions.jts.triangulate.quadedge.Vertex;

import RdmGsaNetAlgo.graphGenerator;
import RdmGsaNetAlgo.graphToolkit;
import RdmGsaNetAlgo.graphToolkit.element;
import RdmGsaNetAlgo.graphToolkit.elementTypeToReturn;
import RdmGsaNet_mainSim.layerNet;
import RdmGsaNet_mainSim.main;

public class delaunayGraph implements topologyGraph_inter {

	// graphs
	private Graph 	oriGraph = topologyGraph.getOriGraph() ,
					delGraph = topologyGraph.getTopGraph() ,
					netGraph = layerNet.getGraph() ,
					seedGraph = main.getSeedGraph () ; 
		
	// geotools initiate
	GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
	MultiPoint multiPointOriGraph ;
	Coordinate[] coordsNodesOriGraph ;
	
	QuadEdgeSubdivision subdiv ;
	QuadEdgeSubdivision subDivNewNode ;
	
	MultiPoint multiPointNewNode ;
	
	Map< String , double[]> mapIdCoordOrigGraph =  new HashMap< String , double[]>  () ;
 	
	// constructor
	public delaunayGraph (Graph oriGraph , Graph topGraph ) {
	
		this.oriGraph = oriGraph ;
		this.delGraph = topGraph ;
	}

	@Override
	public void test() {
		System.out.println(super.getClass().getSimpleName());
		
	}

	
// create methods -----------------------------------------------------------------------------------------------------------------------------------
	@Override
	public void createGeometryOriGraph() {
	
		// get map of coord of netGraph
		mapIdCoordOrigGraph = graphToolkit.getMapCoord(oriGraph, elementTypeToReturn.string) ;	//	System.out.println(mapIdCoordOrigGraph);
	
		//set coordinate of geometry
		int i = 0 ;
		coordsNodesOriGraph = new Coordinate[oriGraph.getNodeCount()] ;

	    for ( String id : mapIdCoordOrigGraph.keySet() ) {
	    	double[ ] coordNode = mapIdCoordOrigGraph.get(id);
	    	coordsNodesOriGraph[i] = new Coordinate( coordNode[0] ,coordNode[1] )  ;	    	
	    	i++ ;
	    }
	    
	    // create geometry
	    multiPointOriGraph = geometryFactory.createMultiPoint(coordsNodesOriGraph) ; 			//	System.out.println(multiPointOriGraph) ;
	}

	@Override
	public void createGraph() {			//	System.out.println(super.getClass().getSimpleName());
		
		DelaunayTriangulationBuilder delaunayBuilder = new DelaunayTriangulationBuilder() ;
		delaunayBuilder.setSites(multiPointOriGraph);
		
		Geometry edges = delaunayBuilder.getEdges(new GeometryFactory());	//	System.out.println(edges);
		int idNodeInt = graphToolkit.getMaxIdIntElement(netGraph, element.node) , idEdgeInt = 0  ;
		
		subdiv = delaunayBuilder.getSubdivision() ;
	
		ArrayList<Point> listPoint = new ArrayList<Point>();
		Map <Point, String> mapPointIdNet = new HashMap<Point, String> ();
		for ( int n = 0 ; n < edges.getNumGeometries() ; n++) {
		
			LineString line = (LineString) edges.getGeometryN(n);			//	System.out.println(line);
	
			Point start = line.getStartPoint() ;
			Point end = line.getEndPoint();									//	System.out.println(line.getEndPoint());
			
			if ( listPoint.contains(start)) {
				System.out.println(start);
			}
			
			listPoint.add(start);
			listPoint.add(end);
			
			// create start node
			String idNode = Integer.toString(idNodeInt);
			mapPointIdNet.put(start, idNode);
			graphGenerator.addNodeWithCoord(delGraph, idNode, start.getX(), start.getY(), 0);
			Node nodeStart = delGraph.getNode(idNode);
			idNodeInt++ ;
		
			// create end node
			idNode = Integer.toString(idNodeInt);
			graphGenerator.addNodeWithCoord(delGraph , idNode, end.getX(), end.getY(), 0);
			Node nodeEnd = delGraph.getNode(idNode);
			mapPointIdNet.put(end, idNode);
			idNodeInt++ ;
			
			// create edge
			String idEdge = Integer.toString(idEdgeInt);
			delGraph.addEdge(idEdge, nodeStart, nodeEnd) ;
			idEdgeInt++  ;
		}
		System.out.println(mapPointIdNet);
	}
	
// update methods -----------------------------------------------------------------------------------------------------------------------------------
	@Override
	public void updateGeometryOriGraph( int step , Map < Double , ArrayList<String> > mapStepNewNodeId  ) {	//	System.out.println(mapStepNewNodeId);

		multiPointNewNode = null ;
		ArrayList<String> listNetNode = new ArrayList<String>( graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.string));	//	System.out.println(listSeed );

		Collection<Vertex> colVertex ;
		Coordinate[] coordsNewNodes = new Coordinate[listNetNode.size()] ;
		
		
		int i = 0 ;
		for ( String idSeed : listNetNode ) {	
			
			Node nodeNet = netGraph.getNode(idSeed);
			double[ ] coordNode = GraphPosLengthUtils.nodePosition(nodeNet) ;
		   	coordsNewNodes[i] = new Coordinate( coordNode[0] ,coordNode[1] )  ;	   	
		   	i++ ;
		}
		
		multiPointNewNode = geometryFactory.createMultiPoint(coordsNewNodes) ; 

	//	System.out.println(netGraph.getNodeCount() ) ;
	//	System.out.println(coordsNodesOriGraph.length);
	//	System.out.println(coordsNewNodes.length);
		
	// System.out.println(peppe.length);
	}
	
	@Override
	public void updateGraph() {
		
	//	System.out.println(netGraph.getNodeSet());
	//	System.out.println(delGraph.getNodeSet());
	//	System.out.println(graphToolkit.getMaxIdIntElement(delGraph, element.node));
		
		DelaunayTriangulationBuilder delaunayBuilder = new DelaunayTriangulationBuilder() ;
		delaunayBuilder.setSites(multiPointNewNode);
		
		Geometry edges = delaunayBuilder.getEdges(new GeometryFactory());	//	System.out.println(edges);

		int 	idNodeInt = graphToolkit.getMaxIdIntElement(delGraph, element.node)  , 
				idEdgeInt = graphToolkit.getMaxIdIntElement(delGraph, element.edge)  ;
	
		for ( int n = 0 ; n < edges.getNumGeometries() ; n++) {
			
			LineString line = (LineString) edges.getGeometryN(n);			//	System.out.println(line);
	
			Point start = line.getStartPoint() ;
			Point end = line.getEndPoint();									//	System.out.println(line.getEndPoint());
			
			// create start node
			String idNode = Integer.toString(idNodeInt);
		
			graphGenerator.addNodeWithCoord(delGraph, idNode, start.getX(), start.getY(), 0);
			Node nodeStart = delGraph.getNode(idNode);
			idNodeInt++ ;
		
			// create end node
			idNode = Integer.toString(idNodeInt);
			graphGenerator.addNodeWithCoord(delGraph , idNode, end.getX(), end.getY(), 0);
			Node nodeEnd = delGraph.getNode(idNode);
			idNodeInt++ ;
			
			// create edge
			String idEdge = Integer.toString(idEdgeInt);
			delGraph.addEdge(idEdge, nodeStart, nodeEnd) ;
			idEdgeInt++  ;
		}
		
		

	}

	
	
	
}
