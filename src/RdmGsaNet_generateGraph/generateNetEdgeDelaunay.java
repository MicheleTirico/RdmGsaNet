package RdmGsaNet_generateGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.geotools.geometry.jts.JTSFactoryFinder;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.triangulate.quadedge.Vertex;

import RdmGsaNetAlgo.graphToolkit;
import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNetAlgo.graphToolkit.element;
import RdmGsaNetAlgo.graphToolkit.elementTypeToReturn;
import RdmGsaNet_generateGraph.generateNetEdge.genEdgeType;
import RdmGsaNet_graphTopology.delaunayGraph_02;

public class generateNetEdgeDelaunay extends delaunayGraph_02 implements generateNetEdge_Inter {
	
	GeometryFactory geoFac = new GeometryFactory() ;
	

	private int idEdgeInt = 0 ;
	// parameters
	private double distCeckSeed ;



		
	//constructor
	public generateNetEdgeDelaunay(Graph oriGraph, Graph topGraph, boolean createGraph , double distCeckSeed ) {
		super(oriGraph, topGraph, createGraph);
		this.distCeckSeed = distCeckSeed ;
	
	} 	

	@Override
	public void generateEdgeRule(double step) {
	
		System.out.println(edges) ;
		System.out.println( mapNetPoint ) ; 

		if ( edges == null )
			return ;
		
		System.out.println(mapSeedVertex);
			
		ArrayList<Vertex> listVertex = new ArrayList<Vertex> ( mapSeedVertex.values()) ;
		
		List<LineString> list = delSub.getPrimaryEdges(false) ;
		Point[] points = new Point[listVertex.size() ]  ;
		
		System.out.println(list);
		
		int i = 0 ;
		for ( Node node : mapSeedVertex.keySet()) {
			
			Vertex ver = mapSeedVertex.get(node) ;
			
			Coordinate coords = new Coordinate(ver.getX() , ver.getX() );
			Point p = geoFac.createPoint( coords ) ; 
			
		
			points[i] = p ;
			i++ ;
			
		}
		
		MultiPoint multiPoint = geometryFactory.createMultiPoint(points) ;
		
		for ( int mp = 0 ; mp < multiPoint.getNumPoints() ; mp++ ) {

			Geometry geom = multiPoint.getGeometryN(mp);
			
			edges.disjoint(geom);
			
			
		}
		
		ArrayList<Point> listPoint = new ArrayList<Point> () ;
		
		
		for ( Node node : mapSeedVertex.keySet()) {
			
			Vertex ver = mapSeedVertex.get(node) ;
			
			Coordinate coords = new Coordinate(ver.getX() , ver.getX() );
			
			Point p = geoFac.createPoint( coords ) ; 
			
			for ( int n = 0 ; n < edges.getNumGeometries() ; n++) {
			
				LineString line = (LineString) edges.getGeometryN(n);	//	System.out.println(line);
				
				Point start = line.getStartPoint() ;
				Point end = line.getEndPoint();	
				
				if ( start.equals(p) ) {
					System.out.println(p + " " + end ) ;
				
					if ( ! listPoint.contains(start))	
						listPoint.add(p);
					
					else if ( ! listPoint.contains(end))		
						listPoint.add(p);
				}
				
				else if ( end.equals(p) ) {
					System.out.println(p + " " + start ) ;

					if ( ! listPoint.contains(start))	
						listPoint.add(p);
					
					else if ( ! listPoint.contains(end))		
						listPoint.add(p);
			
				}
			}			
		}
		
		System.out.println(listPoint.size());
		
		
		
			
		
			
			
	
		
		
	
	}
	
	@Override
	public void removeEdgeRule(double step) {
	
		
		
	}

}
