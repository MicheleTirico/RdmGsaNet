package RdmGsaNetAlgo;

import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import RdmGsaNet_mainSim.simulation;

public class geographyToolkit {

public static void setPointAttributeToNode (Graph graph , Map<Point, Node > mapPointNode ) {
		
		for ( Node n : graph.getEachNode() ) {
			
			GeometryFactory geometryFactory = new GeometryFactory();		
			double[] coord = GraphPosLengthUtils.nodePosition( n ) ;
			
			Coordinate coords =  new Coordinate(coord[0], coord[1]) ;
			Point p = geometryFactory.createPoint(coords) ;
			
			mapPointNode.put( p , n ) ;
			n.addAttribute( "point" , p );
	}
	}
}
