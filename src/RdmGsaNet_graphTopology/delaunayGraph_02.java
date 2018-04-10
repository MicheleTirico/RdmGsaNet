package RdmGsaNet_graphTopology;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.geotools.geometry.jts.JTSFactoryFinder;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

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
import RdmGsaNet_mainSim.simulation;


public class delaunayGraph_02 implements topologyGraph_inter {

	private boolean createGraph  ;
	// graphs
	private Graph 	oriGraph = topologyGraph.getOriGraph() ,
					delGraph = topologyGraph.getTopGraph() ,
					netGraph = layerNet.getGraph() ,
					seedGraph = main.getSeedGraph () ; 
		
	// geotools initiate
	public static  GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
	MultiPoint multiPointOriGraph ;
	Coordinate[] coordsNodesOriGraph ;
		
	protected DelaunayTriangulationBuilder delaunayBuilder ;
	protected static QuadEdgeSubdivision delSub ;
	
	protected static IncrementalDelaunayTriangulator incDel ;
	
	public static Geometry edges ;
	Map < String , double[]> mapIdCoordOrigGraph =  new HashMap< String , double[]>  () ;
	protected static Map < Node , Vertex > mapSeedVertex = new HashMap<>(); 
	protected static Map < Node , Point > mapNetPoint = new HashMap<>(); 
	protected static Map < Node , QuadEdge > mapNodeNetQuadEdge = new HashMap<>(); 
	protected static Map < Node , QuadEdge > mapNodeSeedQuadEdge = new HashMap<>(); 
	// constructor
	public delaunayGraph_02 (Graph oriGraph , Graph topGraph , boolean createGraph  ) {
	
		this.oriGraph = oriGraph ;
		this.delGraph = topGraph ;
		this.createGraph  = createGraph  ;
	}

	@Override
	public void test() {
		System.out.println(super.getClass().getSimpleName());
}

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
	    	Point p = geometryFactory.createPoint(coordsNodesOriGraph[i] ) ;
	    	mapNetPoint.put(oriGraph.getNode(id), p) ;
	    	i++ ;
	    }
	
	    
	    // create geometry
	    multiPointOriGraph = geometryFactory.createMultiPoint(coordsNodesOriGraph) ; 			//	System.out.println(multiPointOriGraph) ;
	
	    // create triangulation
	    delaunayBuilder = new DelaunayTriangulationBuilder() ;
		delaunayBuilder.setSites(multiPointOriGraph);
		delSub = delaunayBuilder.getSubdivision();		
		   
		// create edges
		edges = delaunayBuilder.getEdges(new GeometryFactory());
		

		
	}

	@Override
	public void updateGeometryOriGraph(int step, Map<Double, ArrayList<String>> mapStepNewNodeId) {
		
		ArrayList<Node> listSeedNode = new ArrayList<Node>( graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.element));	//		//	System.out.println(listSeedNode);	
		incDel = new IncrementalDelaunayTriangulator(delSub);					//	System.out.println(listSeedNode);	//	System.out.println(mapStepNewNodeId) ;
		
		for ( Node nodeSeed : listSeedNode ) {
			
			double[] coord = GraphPosLengthUtils.nodePosition(nodeSeed);		//		System.out.println(coord[0] + " " + coord[1]);	
			Vertex ver = new Vertex( coord[0] , coord[1]);
			try {
				incDel.insertSite(ver) ;		
				mapSeedVertex.put(nodeSeed, ver);
			} 
			catch (com.vividsolutions.jts.triangulate.quadedge.LocateFailureException e) {
				// TODO: handle exception
			}
			Coordinate coords = new Coordinate(coord[0] , coord[1]) ;
	    	
	    	Point p = geometryFactory.createPoint( coords ) ;
	    	nodeSeed.addAttribute("point", p);
	
		}	
		
		edges = delaunayBuilder.getEdges(new GeometryFactory());				//	System.out.println(edges);		
		
	}

// create and update delGraph -----------------------------------------------------------------------------------------------------------------------
	@Override
	public void createGraph() {	
		
		if ( createGraph != true )
			return ; 														//	System.out.println(createGraph);
		
		Geometry edges = delaunayBuilder.getEdges(new GeometryFactory());	//	System.out.println(edges);
		
		ArrayList<Point> listPoint = new ArrayList<Point>();
		Map < Point, ArrayList<Point> > mapPointCon = new HashMap< Point, ArrayList<Point> >();

	    	
	    int idNodeInt = 0 , 
	    	idEdgeInt = 0 ;
	 	
		for ( int n = 0 ; n < edges.getNumGeometries() ; n++) {
				
			LineString line = (LineString) edges.getGeometryN(n);	//	System.out.println(line);
		
			Point start = line.getStartPoint() ;
			Point end = line.getEndPoint();							//	System.out.println(line.getEndPoint());
			
			// set id point 
			int pointId = idNodeInt;
			start.setSRID(pointId)  ;
			end.setSRID(pointId) ;
				
			if ( !listPoint.contains(start)) {
				listPoint.add(start);
				mapPointCon.put(start, null);
			}
				
			if ( !listPoint.contains(end)) {
				listPoint.add(end);
				mapPointCon.put(end, null);
			}
				
			ArrayList<Point> list = new ArrayList<Point> (  ) ;
				
			if ( mapPointCon.get(start) == null )  {
				list.add(end) ;							//		System.out.println(list);
				mapPointCon.put(start, list) ;
			}
				
			else {
				list = mapPointCon.get(start);
				list.add(end) ;							//		System.out.println(list);
				mapPointCon.put(start, list) ;
			}
				
			list = new ArrayList<Point> (  ) ;
				
			if ( mapPointCon.get(end) == null )  {
				list.add(start) ;						//		System.out.println(list);
				mapPointCon.put(end, list) ;
			}
				
			else {
				list = mapPointCon.get(end);
				list.add(start) ;						//		System.out.println(list);
				mapPointCon.put(end, list) ;
			}	
		}
		
		// create graph
		for ( Point start : mapPointCon.keySet()) {
	    		
		    // create start node
			String idNode = Integer.toString(idNodeInt);
			graphGenerator.addNodeWithCoord(delGraph, idNode, start.getX(), start.getY(), 0);
			Node nodeStart = delGraph.getNode(idNode);			
			idNodeInt++ ;
	
			for ( Point end : mapPointCon.get(start) ) {
		    		
		    	// create end node
				idNode = Integer.toString(idNodeInt);
				graphGenerator.addNodeWithCoord(delGraph, idNode, end.getX(), end.getY(), 0);						
				Node nodeEnd = delGraph.getNode(idNode);	
				idNodeInt++ ;
					
				// create edges
				String idEdge = Integer.toString(idEdgeInt);
				delGraph.addEdge( idEdge, nodeStart , nodeEnd  ) ;
				idEdgeInt++  ;			    	
			}    		    
		}   
	
	}
	
	@Override
	public void updateGraph() {

		if ( createGraph = false )
			return ;
		
		delGraph.clear();
		Geometry edges = delaunayBuilder.getEdges(new GeometryFactory());//	  System.out.println(edges.getLength());
	
	  	int maxIdNode = 0 ;
	  	int maxIdEdge = 0 ;
	  	
	    int idNodeInt = maxIdNode , idEdgeInt = maxIdEdge  ;
	    
	    for ( int n = 0 ; n < edges.getNumGeometries() ; n++) {
			
			LineString line = (LineString) edges.getGeometryN(n);	//	System.out.println(line);
	
			Point start = line.getStartPoint() ;
			Point end = line.getEndPoint();							//	System.out.println(line.getEndPoint());
			
			// create start node
			String idNode = Integer.toString(idNodeInt);
			graphGenerator.addNodeWithCoord(delGraph, idNode, start.getX(), start.getY(), 0);
			Node nodeStart = delGraph.getNode(idNode);
			idNodeInt++ ;
			
			// create end node
			idNode = Integer.toString(idNodeInt);
			graphGenerator.addNodeWithCoord(delGraph, idNode, end.getX(), end.getY(), 0);
			Node nodeEnd = delGraph.getNode(idNode);
			idNodeInt++ ;
			
			// create edge
			String idEdge = Integer.toString(idEdgeInt);
			delGraph.addEdge(idEdge, nodeStart, nodeEnd) ;
			idEdgeInt++  ;
		}
	    
		
	
	}
}
