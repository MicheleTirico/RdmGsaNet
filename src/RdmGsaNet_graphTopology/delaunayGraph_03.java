package RdmGsaNet_graphTopology;

import java.util.ArrayList;
import java.util.Collections;
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


public class delaunayGraph_03 implements topologyGraph_inter { 
	
	// graphs
	private Graph 	oriGraph = topologyGraph.getOriGraph() ,
					delGraph = topologyGraph.getTopGraph() ,
					seedTriGraph = topologyGraph.getSeedTriGraph() ,
					netGraph = layerNet.getGraph() ,
					seedGraph = main.getSeedGraph () ; 
		
	 
	// geotools parameters
	public static  GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
	MultiPoint multiPoint ;
		
	protected DelaunayTriangulationBuilder delaunayBuilder ;
	protected static QuadEdgeSubdivision delSub ;
	protected static IncrementalDelaunayTriangulator incDel ;	
	public static Geometry edges ;
	
	// constructor
	public delaunayGraph_03 (Graph oriGraph , Graph topGraph , Graph seedTriGraph  ) {	
		this.oriGraph = oriGraph ;
		this.delGraph = topGraph ;		
		this.seedTriGraph = seedTriGraph ;
	}

	@Override
	public void test() {
		System.out.println(super.getClass().getSimpleName());
}

	@Override
	public void createGeometryOriGraph() {
		
	//	ArrayList <Node> listNodeNet = new ArrayList<Node >( graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.element));		//	System.out.println(listNodeNet);		
		ArrayList <Node> listNodeSeed = new ArrayList<Node >( graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.element));	//	System.out.println(listNodeSeed);
		
		int i = 0 ;
		Coordinate[] coordPoint = new Coordinate[listNodeSeed.size()] ;
		
		for ( Node nodeSeed : listNodeSeed) {
			
			// get coord of seed and update new geometry coordinate 
			double[ ] coordNodeSeed = GraphPosLengthUtils.nodePosition(nodeSeed) ;	
			coordPoint[i] = new Coordinate ( coordNodeSeed [0]  , coordNodeSeed [1] ) ;
			
			// create point
			Point p = geometryFactory.createPoint( coordPoint[i] ) ;
			
			// set attribute to seedNode
			nodeSeed.addAttribute("point", p);
	    	i++ ;
		}

	    // create geometry
	    multiPoint = geometryFactory.createMultiPoint(coordPoint) ;
	    
	    // create triangulation
	    delaunayBuilder = new DelaunayTriangulationBuilder() ;
		delaunayBuilder.setSites(multiPoint);
		delSub = delaunayBuilder.getSubdivision();		
		
		// create edges
		edges = delaunayBuilder.getEdges(new GeometryFactory());
	}

	@Override
	public void updateGeometryOriGraph( ) {
		
		ArrayList <Node> listNodeSeed = new ArrayList<Node >( graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.element));
	//	System.out.println(listNodeSeed);
		
		Coordinate newCoordPoint = new Coordinate()  ;
		
		incDel = new IncrementalDelaunayTriangulator(delSub);	
		
		for ( Node nodeSeed : listNodeSeed ) {
//			try {
				// get coord of seed an create new geometry coordinate 
				double[ ] coordNodeSeed = GraphPosLengthUtils.nodePosition(nodeSeed) ;		
				newCoordPoint = new Coordinate(coordNodeSeed[0] , coordNodeSeed[1] );
				
				// create vertex
				Vertex ver = new Vertex( newCoordPoint );
				
				// create point
				Point p = geometryFactory.createPoint( newCoordPoint ) ;
			
				// set attribute to seedNode
				nodeSeed.addAttribute("point", p);
				
				// update delaunay 
				try {
					incDel.insertSite(ver) ;
				}
				catch (com.vividsolutions.jts.triangulate.quadedge.LocateFailureException e) {	
					System.out.println( e.getMessage() ) ;
					System.out.println(nodeSeed);
					seedGraph.removeNode(nodeSeed);
					continue ;			
					}		
		}		
		
		// update edges 
		edges = delaunayBuilder.getEdges(new GeometryFactory());	
	}
	

// create and update delGraph -----------------------------------------------------------------------------------------------------------------------
	@Override
	public void createGraph() {	
	
		ArrayList<Point> listPoint = new ArrayList<Point>();
		Map < Point, ArrayList<Point> > mapPointCon = new HashMap< Point, ArrayList<Point> >();
	
	    int idNodeInt = 0 , 
	    	idEdgeInt = 0 ;
	 	
		for ( int n = 0 ; n < edges.getNumGeometries() ; n++ ) {
				
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
		
		delGraph.clear();
		
		createGraph() ;
   
		
	
	}

	
// create and update seedTriangleGraph --------------------------------------------------------------------------------------------------------------
	@Override
	public void createSeedTriangleGraph() {
		
//		ArrayList <Node> listNodeNet = new ArrayList<Node >( graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.element));		//	System.out.println(listNodeNet);
		ArrayList <Node> listNodeSeed = new ArrayList<Node >( graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.element));	//	System.out.println(listNodeSeed);
		ArrayList<Point> listPointSeed = new ArrayList<Point> ();	
		Map <Point , String > mapPointId = new HashMap<Point, String> () ;
		
		for ( Node nodeSeed : listNodeSeed) {

			double[] coordSeed = GraphPosLengthUtils.nodePosition(nodeSeed);
			Point pointSeed = nodeSeed.getAttribute("point");
			String idNodeSeed = nodeSeed.getId() ;
			
			// create new node
			seedTriGraph.addNode(idNodeSeed) ;
			Node nodeSeedTri = seedTriGraph.getNode(idNodeSeed ) ;
			
			// set attributes
			nodeSeedTri.addAttribute("point", pointSeed );
			nodeSeedTri.addAttribute("xyz", coordSeed[0] , coordSeed[1] , 0 );	
		
			// add point to list point seed
			listPointSeed.add(pointSeed);
			mapPointId.put(pointSeed, idNodeSeed);
		}
				
		int idEdgeInt = 0 ;
		String idEdge ;
		
		int idNodeEndInt = graphToolkit.getMaxIdIntElement(seedTriGraph, element.node) ;		//		System.out.println(idNodeEndInt);
		String idNodeEnd ;
		
		for ( int n = 0 ; n < edges.getNumGeometries() ; n++ ) {
			
			LineString line = (LineString) edges.getGeometryN(n);	//	System.out.println(line);
		
			Point start = line.getStartPoint() ;
			Point end = line.getEndPoint();		

			if ( listPointSeed.contains( start ) ) {
				idNodeEndInt = graphToolkit.getMaxIdIntElement(seedTriGraph, element.node) ;
				// create node
				idNodeEnd = Integer.toString(idNodeEndInt) ;
				seedTriGraph.addNode(idNodeEnd) ;
				Node nodeEnd = seedTriGraph.getNode(idNodeEnd) ;
				nodeEnd.addAttribute("xyz", end.getX() ,end.getY() , 0 ) ;
				nodeEnd.addAttribute("point", end);
				idNodeEndInt++ ;
				
				// create edge
				idEdge = Integer.toString(idEdgeInt) ;
				Node nodeStart = seedTriGraph.getNode(mapPointId.get(start));
				seedTriGraph.addEdge(idEdge, nodeStart, nodeEnd);
				idEdgeInt++ ;
			}
			
			if ( listPointSeed.contains( end  )  ) {
				idNodeEndInt = graphToolkit.getMaxIdIntElement(seedTriGraph, element.node) ;
				// create node
				idNodeEnd = Integer.toString(idNodeEndInt) ;
				seedTriGraph.addNode(idNodeEnd) ;
				Node nodeEnd = seedTriGraph.getNode(idNodeEnd) ;
				nodeEnd.addAttribute("xyz", start.getX() , start.getY() , 0 ) ;
				nodeEnd.addAttribute("point", start);
				idNodeEndInt++ ;
			
				// create edge
				idEdge = Integer.toString(idEdgeInt) ;
				Node nodeStart = seedTriGraph.getNode(mapPointId.get(end));
				seedTriGraph.addEdge(idEdge, nodeStart, nodeEnd);
				idEdgeInt++ ;	
			}
			else
				continue ;		
		}		
	}

	@Override
	public void updateSeedTriangleGraph() {

		seedTriGraph.clear();
		createSeedTriangleGraph();
		
	}
}
