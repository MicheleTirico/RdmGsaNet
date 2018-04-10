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
import RdmGsaNet_mainSim.simulation;

public class generateNetEdgeDelaunay_02  extends delaunayGraph_02 implements generateNetEdge_Inter {

	private int idEdgeInt = 0 ;
	// parameters
	private double distCeckSeed ;
	//constructor
	public generateNetEdgeDelaunay_02  (Graph oriGraph, Graph topGraph, boolean createGraph , double distCeckSeed ) {
		super(oriGraph, topGraph, createGraph);
		this.distCeckSeed = distCeckSeed ;
	
	} 	

	@Override
	public void generateEdgeRule(double step) {
		
	//	getListNodesDelaunay() ; 
		
		ArrayList<String> listIdSeed = new ArrayList<String>( graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.string)) ;
		
		ArrayList<String> listIdToDeleteNet = new ArrayList<String> ();
		ArrayList<String> listIdToDeleteSeed = new ArrayList<String> ();
		ArrayList<String> listIdTNodeToConnect = new ArrayList<String> ();
		
		ArrayList<Integer> listIdEdgeInt = new ArrayList<Integer>( graphToolkit.getListElement(netGraph, element.edge, elementTypeToReturn.integer)) ;		
		ArrayList<Integer> listIdNetInt = new ArrayList<Integer>( graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.integer)) ;
					
		for ( String idSeed : listIdSeed ) {	//	System.out.println(nSeed);
			
			listIdSeed = new ArrayList<String>( graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.string)) ;
			if ( !listIdSeed.contains(idSeed) ) {
				try {
					seedGraph.removeNode(idSeed);
				}
				catch (org.graphstream.graph.ElementNotFoundException e) {		}
			}
			
			Node nodeSeed , nodeNet ;
			try {
				nodeSeed = seedGraph.getNode(idSeed) ;						// 	System.out.println(nodeSeed);
				nodeNet = netGraph.getNode(idSeed) ;						//	System.out.println(nodeNet);
				String fatSeed = nodeSeed.getAttribute("father");
				String fat = nodeNet.getAttribute("father");				//	System.out.println( nodeNet + " fat " + fat);		System.out.println( nodeSeed + " fatSeed " + fatSeed);
			}catch (java.lang.NullPointerException e) {
				continue ;
			}
	
			String idFather = nodeNet.getAttribute("father");			
			
			listIdEdgeInt = new ArrayList<Integer>( graphToolkit.getListElement(netGraph, element.edge, elementTypeToReturn.integer)) ;
			
			if ( listIdEdgeInt.isEmpty())
				idEdgeInt = 0 ;
			else
				idEdgeInt = Collections.max(listIdEdgeInt);
			
			int idNetInt = 0 ;
			String idEdge ;
			
			while ( listIdEdgeInt.contains(idEdgeInt)) 
					idEdgeInt ++ ;
				
			idEdge = Integer.toString(idEdgeInt);
		
			try {
				netGraph.addEdge(idEdge, idSeed, idFather );
			}
			catch ( org.graphstream.graph.IdAlreadyInUseException e ) { e.printStackTrace();	}
			catch ( org.graphstream.graph.ElementNotFoundException e) {	e.printStackTrace();	}
			/*
			Map <String , Double> mapDistNet = generateNetEdge.getMapIdDist( netGraph , nodeNet ) ;	//	double minDist = mapDistNet.values().stream().mapToDouble(valstat -> valstat).min().getAsDouble();			
			Map < String , Double > mapTopDist = gsAlgoToolkit.getMapTopValues(mapDistNet, 10) ;	//		Set<String> setIdNear = gsAlgoToolkit.getKeysByValue(mapDistNet, minDist ); 
			
			System.out.println(mapTopDist.size());
			
			for ( String idNear : mapTopDist.keySet()) {
				
				double dist =  mapTopDist.get(idNear);
				
				if ( dist < distCeckSeed && !idNear.equals(idFather) ) 
					listIdTNodeToConnect.add(idNear) ;
				else 
					break ;
			}
			*/
			
			ArrayList<Vertex> listVertex = new ArrayList<Vertex> ( mapSeedVertex.values()) ;
			
	//		System.out.println(listVertex.size());
			ArrayList<String> listNetNode = new ArrayList<String>() ;
			
			Vertex vertex = mapSeedVertex.get(nodeSeed) ;
			if ( vertex == null ) 
				continue ;
			
		//	System.out.println(nodeSeed + "  " +  vertex );
		
				
				Coordinate coords = new Coordinate(vertex.getX() , vertex.getX() );
				Point point = geometryFactory.createPoint( coords ) ; 	//	System.out.println(point);
				
				for ( int n = 0 ; n < edges.getNumGeometries() ; n++) {
					
					LineString line = (LineString) edges.getGeometryN(n);	//	System.out.println(line);
					
					Point start = line.getStartPoint() ;
					Point end = line.getEndPoint();	
					
					if ( start.equals(point) || end.equals(point) ) {
						
						Node node = simulation.mapNodeNetPoint.get(point) ;
						
						if ( ! listIdTNodeToConnect.contains(node) )
							listIdTNodeToConnect.add(node.getId()) ;
							
					//System.out.println(vertex + " " + node);
					}			
					
				continue ;
		
				
			}
			//	System.out.println(vertex + " " + listIdTNodeToConnect);
		//	mapSeedVertex.clear();
			
			if ( listIdTNodeToConnect.isEmpty() )
				continue;
			else {
				// create all edge
				listIdEdgeInt = new ArrayList<Integer>( graphToolkit.getListElement(netGraph, element.edge, elementTypeToReturn.integer)) ;
				idEdgeInt = Collections.max(listIdEdgeInt);
				
				listIdNetInt = new ArrayList<Integer>( graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.integer)) ;
				idNetInt = Collections.max(listIdNetInt);
				
				for ( String idNear : listIdTNodeToConnect ) {
					
// if near is a seed---------------------------------------------------------------------------------------------------------------------------------
					if ( listIdSeed.contains(idNear) ) {		//	System.out.println( " id near is a seed") ;
						listIdNetInt = new ArrayList<Integer>( graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.integer)) ;
						
						while ( listIdEdgeInt.contains(idEdgeInt)) 
							idEdgeInt ++ ;
						
						idEdge = Integer.toString(idEdgeInt);
						
						while ( listIdNetInt.contains(idNetInt)) 
							idNetInt ++ ;
						
						String idNode = Integer.toString(idNetInt);
						
						Node nNear = netGraph.getNode(idNear);
						Node nSeed = netGraph.getNode(idSeed);
						
						netGraph.addNode(idNode);
						seedGraph.addNode(idNode);
						
						double[] newNodeCoord = graphToolkit.getCoordNodeMean(nSeed, nNear) ;
						
						Node newNodeSeed = seedGraph.getNode(idNode);
						Node newNodeNet = netGraph.getNode(idNode);
						
						newNodeSeed.setAttribute("xyz", newNodeCoord[0] , newNodeCoord[1] , 0 );
						newNodeNet.setAttribute("xyz", newNodeCoord[0] , newNodeCoord[1] , 0 );
						
						newNodeNet.setAttribute("father", idSeed );
						newNodeSeed.setAttribute("father", idSeed );
						
						newNodeNet.addAttribute("merge", 1);
						
						listIdToDeleteSeed.add(idSeed) ;
						listIdToDeleteNet.add(idSeed) ;
						
						listIdToDeleteSeed.add(idNear) ;
						listIdToDeleteNet.add(idNear) ;
						
						String idFatherNear = nNear.getAttribute("father");
						String idFatherSeed = nSeed.getAttribute("father");
						
						ArrayList<String> listIdNodeToAddEdge = new ArrayList<String>( Arrays.asList( idFatherNear ,  idFatherSeed 	, idNear  , idSeed  , idFather 	));
					
						for ( String s : listIdNodeToAddEdge ) {
							try {
								
								idEdgeInt ++ ;
								idEdge = Integer.toString(idEdgeInt) ; 
								netGraph.addEdge(idEdge, idNode , s );
								idEdgeInt ++ ;
							}
							catch (org.graphstream.graph.EdgeRejectedException e ) 		{  continue;	}
							catch ( org.graphstream.graph.ElementNotFoundException e)	{  continue;	}
						}
					
						idNetInt ++ ;
							
						// if node is not connected
						if ( newNodeNet.getDegree() < 1 ) {				//	System.out.println(newNodeNet.getDegree());
				
							Map <String , Double> mapDistNetDegreee0 = generateNetEdge.getMapIdDist( netGraph , newNodeNet ) ;								
							double minDistDegreee0 = mapDistNetDegreee0 .values().stream().mapToDouble(valstat -> valstat).min().getAsDouble();			
							Set<String> setIdNear = gsAlgoToolkit.getKeysByValue(mapDistNetDegreee0, minDistDegreee0 ); 		//	System.out.println(minDistDegreee0);	System.out.println(setIdNear);
							
							for ( String s : setIdNear ) {
								
								try {
									netGraph.addEdge(idEdge, idNode, s);
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
					}
					
					
// if near is not a seed-----------------------------------------------------------------------------------------------------------------------------
					else if ( !listIdSeed.contains(idNear) ) {	//	System.out.println( " id near is not a seed" ) ;
						
						while ( listIdEdgeInt.contains(idEdgeInt)) 
							idEdgeInt ++ ;
						
						idEdge = Integer.toString(idEdgeInt);
						try {
							netGraph.addEdge(idEdge, idSeed, idNear);
						}
			//			catch ( org.graphstream.graph.ElementNotFoundException e) 	{		}
						catch (org.graphstream.graph.IdAlreadyInUseException e) 	{		}
						catch (org.graphstream.graph.EdgeRejectedException e ) 		{		}
					
						listIdToDeleteSeed.add(idSeed) ;
					}
				}		
			
				for ( String id : listIdToDeleteSeed ) {
					try {
						seedGraph.removeNode(id);
					}
					catch (org.graphstream.graph.ElementNotFoundException e) {		}
				}
				
				for ( String id : listIdToDeleteNet ) {
					try {
						netGraph.removeNode(id);
					}
					catch (org.graphstream.graph.ElementNotFoundException e) {				}
				}
				// clear list and map
				listIdTNodeToConnect.clear();
				listIdToDeleteSeed.clear();
				listIdToDeleteNet.clear();
			}
		}
		mapSeedVertex.clear();
	}

	@Override
	public void removeEdgeRule(double step) {
	
		
		
	}
	
	

	private  ArrayList<String> getListNodesDelaunay ( String idSeed ) {
		
		ArrayList<Vertex> listVertex = new ArrayList<Vertex> ( mapSeedVertex.values()) ;
		
		System.out.println(listVertex.size());
		ArrayList<String> listNetNode = new ArrayList<String>() ;
		
		for ( Vertex vertex : listVertex ) {
			
			Coordinate coords = new Coordinate(vertex.getX() , vertex.getX() );
			Point point = geometryFactory.createPoint( coords ) ; 	//	System.out.println(point);
			
			for ( int n = 0 ; n < edges.getNumGeometries() ; n++) {
				
				LineString line = (LineString) edges.getGeometryN(n);	//	System.out.println(line);
				
				Point start = line.getStartPoint() ;
				Point end = line.getEndPoint();	
				
				if ( start.equals(point) || end.equals(point) ) {
					
					Node node = simulation.mapNodeNetPoint.get(point) ;
					
					if ( ! listNetNode.contains(node) )
						listNetNode.add(node.getId()) ;
					
				//System.out.println(vertex + " " + node);
				}			
			}		
	
			System.out.println(vertex + " " + listNetNode);
		}
	
		mapSeedVertex.clear();
		return listNetNode ;
	

	}

}
