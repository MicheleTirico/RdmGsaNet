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
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

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
import RdmGsaNet_mainSim.simulation;

public class generateNetEdgeDelaunay_03  extends delaunayGraph_02 implements generateNetEdge_Inter {

	private int idEdgeInt = 0 , idNetInt = 0 ;
	// parameters
	private double distCeckSeed ;
	//constructor
	public generateNetEdgeDelaunay_03  (Graph oriGraph, Graph topGraph, boolean createGraph , double distCeckSeed ) {
		super(oriGraph, topGraph, createGraph);
		this.distCeckSeed = distCeckSeed ;
	} 	

	@Override
	public void generateEdgeRule(double step) {

		// create array list of element
		ArrayList<String> listIdSeed = new ArrayList<String>( graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.string)) ;
		ArrayList<String> listIdToDeleteNet = new ArrayList<String> ();
		ArrayList<String> listIdToDeleteSeed = new ArrayList<String> ();
		ArrayList<String> listIdTNodeToConnect = new ArrayList<String> ();	
		ArrayList<String> listIdTNodeToConnect_delaunay = new ArrayList<String> ();		
		
		ArrayList<Integer> listIdEdgeInt = new ArrayList<Integer>( graphToolkit.getListElement(netGraph, element.edge, elementTypeToReturn.integer)) ;		
		ArrayList<Integer> listIdNetInt = new ArrayList<Integer>( graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.integer)) ;
					
		for ( String idSeed : listIdSeed ) {	//	System.out.println(nSeed);
			
			// declare variables 
			Node nodeSeed , nodeNet ;
			String fatSeed , fat , idFather , idEdge , idNode = null ;	
			
			// update list id seed
			if ( !listIdSeed.contains(idSeed) ) {
				try		{ seedGraph.removeNode(idSeed);	
				}
				catch 	( org.graphstream.graph.ElementNotFoundException e) {		}
			}
			
			// handle ad father edges ---------------------------------------------------------------------------------------------------------------------------			
			// get father id
			try {
				nodeSeed = seedGraph.getNode(idSeed) ;						// 	System.out.println(nodeSeed);
				nodeNet = netGraph.getNode(idSeed) ;						//	System.out.println(nodeNet);
				fatSeed = nodeSeed.getAttribute("father");
				idFather = nodeNet.getAttribute("father");	 					
			} 
			catch (java.lang.NullPointerException e) {		continue ;		}
					
			// get id edge
			idEdgeInt = graphToolkit.getMaxIdIntElement(netGraph, element.edge);
			idEdge = Integer.toString(idEdgeInt);
		
			// add edge soon - father
			try		{ netGraph.addEdge(idEdge, idSeed, idFather );	
			}
			catch 	( org.graphstream.graph.IdAlreadyInUseException e ) { e.printStackTrace();	}
			catch	( org.graphstream.graph.ElementNotFoundException e) {	e.printStackTrace();	}
			
			// compute map of distance --------------------------------------------------------------------------------------------------------------------------			
	//		listIdTNodeToConnect = getlistIdTNodeToConnect(listIdTNodeToConnect, nodeNet, idFather);
	//		System.out.println("\n" + listIdTNodeToConnect.size() + " " +  listIdTNodeToConnect ) ;
			
			Point netPoint = nodeNet.getAttribute("point") ;	//	System.out.println(netPoint);
			
			ArrayList<Point> listPointToConnect = new ArrayList () ;
			Point p = nodeSeed.getAttribute("point");
			
			if ( p == null ) {
			
				double[] coord = GraphPosLengthUtils.nodePosition(nodeSeed);		
				Coordinate coords = new Coordinate(coord[0] , coord[1]) ;
				p = geometryFactory.createPoint( coords ) ;
				nodeSeed.addAttribute("point", p);
			}
			
			for ( int x = 0 ; x < edges.getNumGeometries(); x++ ) {
		    	
		    	LineString line = (LineString) edges.getGeometryN(x);	//	System.out.println(line);
	    	
		    	Point start = line.getStartPoint() ;
				Point end = line.getEndPoint();			
				
				
		    	if ( ! line.disjoint( p ) )  {
		    		double dist = line.getLength() ; 			//	System.out.println(line.getLength() ) ;
		    		if ( dist < distCeckSeed && dist != 0) {	//	System.out.println(line.getLength() + " " + p + " " + start +" " + end ) ;
		    			if ( start.equals(p)) {			//start.equalsExact(p, 0.01)
		    				listPointToConnect.add(end) ;
		    				Node n = simulation.mapNodeNetPoint.get(end) ;
		    				listIdTNodeToConnect_delaunay.add( n.getId() );
		    			}
		    			else {
		    				listPointToConnect.add(start) ;
		    				Node n = simulation.mapNodeNetPoint.get(start) ;
		    				listIdTNodeToConnect_delaunay.add( n.getId() );
		    			}			
		    		}
		    	}
			}
		//	System.out.println ( listIdTNodeToConnect_delaunay.size()) ;
			//System.out.println(simulation.mapNodeNetPoint) ;
	//		System.out.println(listPointToConnect.size() + " " + listPointToConnect ) ;	
			
			// get id edge
			listIdEdgeInt = new ArrayList<Integer>( graphToolkit.getListElement(netGraph, element.edge, elementTypeToReturn.integer)) ;
			idEdgeInt = Collections.max(listIdEdgeInt) ;
				
			// get id node
			listIdNetInt = new ArrayList<Integer>( graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.integer)) ;
			idNetInt = Collections.max(listIdNetInt);
						
			for ( String idNear : listIdTNodeToConnect_delaunay ) {
			/*		
				// if near is a seed---------------------------------------------------------------------------------------------------------------------------------	
				if ( listIdSeed.contains(idNear) ) 		//	System.out.println( " id near is a seed") ;
					handleNearIsSeed(listIdEdgeInt, listIdNetInt, listIdToDeleteSeed, listIdToDeleteNet, idNode, idFather, idNear, idSeed, idEdge);
	
				// if near is not a seed-----------------------------------------------------------------------------------------------------------------------------
				else if ( !listIdSeed.contains(idNear) ) 	//	System.out.println( " id near is not a seed" ) ;	
					handleNearIsNotSeed(listIdEdgeInt, listIdToDeleteSeed, idEdge, idSeed, idNear);
				*/	
			
			}		
			
			// delete Nodes
			handleDeleteNodes(listIdToDeleteSeed, listIdToDeleteNet);
			
			// clear list and map
			listIdTNodeToConnect_delaunay.clear();
			listIdTNodeToConnect.clear();	
			listIdToDeleteSeed.clear();	
			listIdToDeleteNet.clear();
		}				
	}

// handle methods -----------------------------------------------------------------------------------------------------------------------------------	
		
	// get map list node to connect
	private ArrayList<String> getlistIdTNodeToConnect ( ArrayList<String> listIdTNodeToConnect , Node nodeNet , String idFather) {
		
	Map <String , Double> mapDistNet = generateNetEdge.getMapIdDist( netGraph , nodeNet ) ;	//	double minDist = mapDistNet.values().stream().mapToDouble(valstat -> valstat).min().getAsDouble();			
	Map < String , Double > mapTopDist = gsAlgoToolkit.getMapTopValues(mapDistNet, 10) ;	//		Set<String> setIdNear = gsAlgoToolkit.getKeysByValue(mapDistNet, minDist ); 
	
	for ( String idNear : mapTopDist.keySet()) {
		double dist =  mapTopDist.get(idNear);
		if ( dist < distCeckSeed && !idNear.equals(idFather) ) 
			listIdTNodeToConnect.add(idNear) ;
		else 
			break ;
	}
	
	if ( listIdTNodeToConnect.isEmpty() )
		return listIdTNodeToConnect ;
	
	return listIdTNodeToConnect ;
	
	}
	// handle delete nodes 
	private void handleDeleteNodes (ArrayList<String> listIdToDeleteSeed , ArrayList<String> listIdToDeleteNet ) {
		
		for ( String id : listIdToDeleteSeed ) {
			try 	{ seedGraph.removeNode(id);		
			}
			catch 	( org.graphstream.graph.ElementNotFoundException e) {	}
		}
		
		for ( String id : listIdToDeleteNet ) {
			try		{ netGraph.removeNode(id);
			}
			catch 	( org.graphstream.graph.ElementNotFoundException e) {			}
		}
	}
	
	// handle if Near Is a seed 
	private void handleNearIsNotSeed (ArrayList listIdEdgeInt , ArrayList listIdToDeleteSeed , String idEdge, String idSeed, String idNear) {
		while ( listIdEdgeInt.contains(idEdgeInt)) 
			idEdgeInt ++ ;
		
		idEdge = Integer.toString(idEdgeInt);
		try { netGraph.addEdge(idEdge, idSeed, idNear); 
		}
		catch (org.graphstream.graph.ElementNotFoundException e) {		}
		catch (org.graphstream.graph.IdAlreadyInUseException e) {		}
		catch (org.graphstream.graph.EdgeRejectedException e ) 	{		}
	
		listIdToDeleteSeed.add(idSeed) ;
	}
	
	// handle if Near Is a seed 
	private void handleNearIsSeed (
			ArrayList listIdEdgeInt , ArrayList listIdNetInt, ArrayList	listIdToDeleteSeed ,ArrayList	listIdToDeleteNet ,
			String idNode , String idFather ,	String idNear , String idSeed , String idEdge 
			) {
		
		Node nNear , nSeed ,newNodeSeed	, newNodeNet ;
		
		idNetInt = graphToolkit.getMaxIdIntElement(netGraph, element.node) ;
		while ( listIdEdgeInt.contains(idEdgeInt)) 							
			idEdgeInt ++ ;

		idEdge = Integer.toString(idEdgeInt);
		
		while ( listIdNetInt.contains(idNetInt) ) 
			idNetInt ++ ;
		
		idNode = Integer.toString(idNetInt);
		
		nNear = netGraph.getNode(idNear);
		nSeed = netGraph.getNode(idSeed);
		
		// add nodes 
		try {
			netGraph.addNode(idNode);
			seedGraph.addNode(idNode);
		}
		catch (org.graphstream.graph.IdAlreadyInUseException e) {
			
			listIdNetInt = graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.string) ;
			
			while ( listIdNetInt.contains(idNetInt) ) 
				idNetInt ++ ;
			
			netGraph.addNode(idNode);
			seedGraph.addNode(idNode);
			
		}
		
		
		//set coordinate of new node
		try {
			double[] newNodeCoord = graphToolkit.getCoordNodeMean(nSeed, nNear) ;
			newNodeSeed = seedGraph.getNode(idNode);
			newNodeNet = netGraph.getNode(idNode);
			newNodeSeed.setAttribute("xyz", newNodeCoord[0] , newNodeCoord[1] , 0 );
			newNodeNet.setAttribute("xyz", newNodeCoord[0] , newNodeCoord[1] , 0 );
		} 
		catch (java.lang.NullPointerException e) {
			return ;
		}
		// set attribute to new node
		newNodeNet.setAttribute("father", idSeed );
		newNodeSeed.setAttribute("father", idSeed );						
		newNodeNet.addAttribute("merge", 1);
		
		// update list of node to delete
		listIdToDeleteSeed.add(idSeed) ;
		listIdToDeleteNet.add(idSeed) ;
		listIdToDeleteSeed.add(idNear) ;
		listIdToDeleteNet.add(idNear) ;
		
		String idFatherNear = nNear.getAttribute("father");
		String idFatherSeed = nSeed.getAttribute("father");
		
		// create list node to add
		ArrayList<String> listIdNodeToAddEdge = new ArrayList<String>( Arrays.asList( idFatherNear ,  idFatherSeed  	, idNear 	, idSeed , idFather 	));
	
		// add edges
		for ( String s : listIdNodeToAddEdge ) {
			try {
				idEdgeInt ++ ;
				idEdge = Integer.toString(idEdgeInt) ; 
				netGraph.addEdge(idEdge, idNode , s );
				idNetInt ++ ;
			}
			catch (org.graphstream.graph.EdgeRejectedException e ) 		{  continue;	}
			catch ( org.graphstream.graph.ElementNotFoundException e)	{  continue;	}
		}
	
		// if node is not connected
		if ( newNodeNet.getDegree() < 1 ) 				//	System.out.println(newNodeNet.getDegree());		
			handleNodeNotConnect(newNodeNet, idEdge, idNode, listIdEdgeInt);
	
		
	}
		
	// handle if node is not connect
	private void handleNodeNotConnect ( Node newNodeNet , String idEdge, String idNode , ArrayList listIdEdgeInt ) {
			
			Map <String , Double> mapDistNetDegreee0 = generateNetEdge.getMapIdDist( netGraph , newNodeNet ) ;								
			double minDistDegreee0 = mapDistNetDegreee0 .values().stream().mapToDouble(valstat -> valstat).min().getAsDouble();			
			Set<String> setIdNear = gsAlgoToolkit.getKeysByValue(mapDistNetDegreee0, minDistDegreee0 ); 		//	System.out.println(minDistDegreee0);	System.out.println(setIdNear);
			
			for ( String s : setIdNear ) {
				
				try { netGraph.addEdge(idEdge, idNode, s);
				} 
				catch (org.graphstream.graph.IdAlreadyInUseException e) {
					while ( listIdEdgeInt.contains(idEdgeInt) ) {	
						netGraph.addEdge(idEdge, idNode, s);
						idNetInt ++ ;
					}
				}
				idEdgeInt ++ ;
			}
		
	

	}

	@Override
	public void removeEdgeRule(double step) {
		// TODO Auto-generated method stub
		
	}

}
