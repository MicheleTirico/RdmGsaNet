package RdmGsaNet_generateGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import org.eclipse.swt.internal.cairo.cairo_font_extents_t;
import org.graphstream.graph.Edge;
import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Node;

import RdmGsaNetAlgo.graphToolkit;
import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNetAlgo.graphToolkit.element;
import RdmGsaNetAlgo.graphToolkit.elementTypeToReturn;
import RdmGsaNet_generateGraph.generateNetEdge.genEdgeType;

public class generateNetEdgeInRadiusXedges_04 implements generateNetEdge_Inter {

	// parameters
	private genEdgeType genEdgeType;
	private int idEdgeInt = 0 , idNetInt = 0 ;
	private double distCeckSeed ;
	private boolean runXedges ;

	
	public generateNetEdgeInRadiusXedges_04 ( genEdgeType genEdgeType , double distCeckSeed , boolean runXedges ) {
		this.genEdgeType =  genEdgeType ;
		this.distCeckSeed = distCeckSeed ;
		this.runXedges = runXedges ;
	}
	
	@Override
	public void generateEdgeRule(double step) {
		switch (genEdgeType) {
		case onlyFather: 	
			onlyFather( true ) ;
			break;
			
		case fatherAndNodeInRadius : 				
			fatherAndNodeInRadius ( ) ;
			break ;			
		}		
	}
	
	public void fatherAndNodeInRadius (   ) {
		
		// create array list of element
		ArrayList<String> listIdSeed = new ArrayList<String>( graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.string)) ;
		ArrayList<String> listIdToDeleteSeed = new ArrayList<String> ();
		ArrayList<String> listIdTNodeToConnect = new ArrayList<String> ();		
		ArrayList<Integer> listIdEdgeInt = new ArrayList<Integer>( graphToolkit.getListElement(netGraph, element.edge, elementTypeToReturn.integer)) ;		
		ArrayList<Integer> listIdNetInt = new ArrayList<Integer>( graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.integer)) ;
	
	for ( String idSeed : listIdSeed ) {
	
			
		// declare variables 			
		Node nodeSeed , nodeNet ;			
		String fatSeed , fat , idFather , idEdge , idNode = null , idEdgeX ;	
			
			
		// update list id seed		
		if ( !listIdSeed.contains(idSeed) ) {		
			try	{ 
				seedGraph.removeNode(idSeed);						
			}	
			catch 	( org.graphstream.graph.ElementNotFoundException e) {		}		
		}
			
			
// handle ad father edges ---------------------------------------------------------------------------------------------------------------------------					
		// get father id		
		nodeSeed = seedGraph.getNode(idSeed) ;						// 	System.out.println(nodeSeed);
		nodeNet = netGraph.getNode(idSeed) ;						//	System.out.println(nodeNet);
		fatSeed = nodeSeed.getAttribute("father");
		idFather = nodeNet.getAttribute("father");	 					
				
		// get id edge
		idEdgeInt = graphToolkit.getMaxIdIntElement(netGraph, element.edge);
		idEdge = Integer.toString(idEdgeInt);
	
		// add edge soon - father	
		Edge edge = null ; 
		try {
			netGraph.addEdge(idEdge, idSeed, idFather) ; 					
			edge = netGraph.getEdge(idEdge) ;	
		
			ArrayList<Edge> listEdgeInRadius = new ArrayList<Edge> ( graphToolkit.getListEdgeInRadius(netGraph, idSeed, 2 * distCeckSeed , true )) ;		
			ArrayList<Edge> listXEdges = new ArrayList<Edge> ( graphToolkit.getListEdgeXInList(edge, listEdgeInRadius));	
			
			if ( ! listXEdges.isEmpty() ) { //				System.out.println(listXEdges);
				
				listIdToDeleteSeed.add(idSeed) ;
				Node near = null ;
				
				for ( Edge e : listXEdges ) {
					
					Node n0 = e.getNode0() ;
					Node n1 = e.getNode1() ;
					
					double dist0 = gsAlgoToolkit.getDistGeom( e.getNode0(), netGraph.getNode(idFather) );					
					double dist1 = gsAlgoToolkit.getDistGeom( e.getNode1(), netGraph.getNode(idFather) );
					
					String idWin = null ; 
			
					if ( dist0 <= dist1  && Math.min(dist0, dist1) <= 2 * distCeckSeed   ) 
						idWin = n0.getId() ;
					else if  ( dist0 > dist1  && Math.min(dist0, dist1) <= 2 * distCeckSeed    ) 
						idWin = n1.getId() ;
										
					if ( idFather == idWin ) 
						continue ;
	
					
					near = netGraph.getNode(idWin) ;
								
				}	//		System.out.println(near);
				
				
				try {
					idEdgeInt = graphToolkit.getMaxIdIntElement(netGraph, element.edge) ;
					idEdge = Integer.toString(idEdgeInt) ;
					netGraph.addEdge(idEdge, near.getId(), idFather) ;	//	listIdToDeleteSeed.add(idSeed) ;
					idEdgeInt++ ;
				}
				catch (NullPointerException e) {		//			continue ;
				}
				catch (EdgeRejectedException e) {		//			continue ;
					
				}
				continue ;
			}
		} catch (ElementNotFoundException e) {
			// TODO: handle exception
		}
			

	
// compute list of nodes --------------------------------------------------------------------------------------------------------------------------			
			listIdTNodeToConnect = getlistIdTNodeToConnect(listIdTNodeToConnect, nodeNet, idFather);
			
			if ( ! listIdTNodeToConnect.isEmpty()) {
				for ( String idNear : listIdTNodeToConnect ) {
					try {
						idEdgeInt = graphToolkit.getMaxIdIntElement(netGraph, element.edge) ;
						idEdge = Integer.toString(idEdgeInt)  ;
						
						if ( listIdSeed.contains(idNear)) {
							netGraph.addEdge(idEdge, idSeed , idNear) ;
						}
						else {
							ArrayList<String> listNeig = new ArrayList<String> ( graphToolkit.getListNeighbor(netGraph, idFather, elementTypeToReturn.string)) ;	//		System.out.println(listNeig);
							
							if ( ! listNeig.contains(idNear) ) {	
								netGraph.addEdge(idEdge, idFather , idNear) ;
								listIdToDeleteSeed.add( idSeed ) ;
							}	
						}
					} 
					catch ( EdgeRejectedException e) {
						// TODO: handle exception
					}
					catch ( NullPointerException e) {
						// TODO: handle exception
					}
					
		
				
				}				
				
			}		
			
			// delete Nodes
			handleDeleteNodes(listIdToDeleteSeed );
				
			// clear list and map
			listIdTNodeToConnect.clear();
			listIdToDeleteSeed.clear();

		
		}
	}
// --------------------------------------------------------------------------------------------------------------------------------------------------	

	@Override
	public void removeEdgeRule(double step) {
		// TODO Auto-generated method stub
		
	}
// handle methods -----------------------------------------------------------------------------------------------------------------------------------	
	// handle delete nodes 
	private void handleDeleteNodes (ArrayList<String> listIdToDeleteSeed  ) {
		
		for ( String id : listIdToDeleteSeed ) {
			try { 
				seedGraph.removeNode(id);
				netGraph.removeNode(id) ;
			}
			catch 	( org.graphstream.graph.ElementNotFoundException e) { 	}
	
		}
		
		
	}
	// get map list node to connect
			private ArrayList<String> getlistIdTNodeToConnect ( ArrayList<String> listIdTNodeToConnect , Node nodeNet , String idFather) {
				
			Map <String , Double> mapDistNet = generateNetEdge.getMapIdDist( netGraph , nodeNet ) ;	//	double minDist = mapDistNet.values().stream().mapToDouble(valstat -> valstat).min().getAsDouble();			
			Map < String , Double > mapTopDist = gsAlgoToolkit.getMapTopValues(mapDistNet, 10 ) ;	//		Set<String> setIdNear = gsAlgoToolkit.getKeysByValue(mapDistNet, minDist ); 
			
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
		

	public void onlyFather ( boolean isIdEdgeInt ) {
		for ( Node nSeed : seedGraph.getEachNode() ) {
			
			String idSeed = nSeed.getId() ;
			String father = nSeed.getAttribute("father");
			
			Node nNet = netGraph.getNode (idSeed);
			Node nFather = netGraph.getNode(father) ;
			String idEdge  = null ;
			
			if ( isIdEdgeInt == false )
				idEdge = father + "-" + nSeed  ;
			
			else if ( isIdEdgeInt ) {
				ArrayList<Integer> listIdEdgeInt = new ArrayList<Integer>( graphToolkit.getListElement(netGraph, element.edge, elementTypeToReturn.integer)) ;
				int idInt = 0 ;
				while ( listIdEdgeInt.contains(idInt)) 
					idInt ++ ;
				
				idEdge = Integer.toString(idInt);
			}
			netGraph.addEdge(idEdge, nNet, nFather) ;	//		System.out.println(seedGraph + " " + nSeed.getId( ) + " " + father);
		}
	}
	
	

}
