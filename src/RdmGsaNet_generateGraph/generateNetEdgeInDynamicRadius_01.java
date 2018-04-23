package RdmGsaNet_generateGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import org.eclipse.swt.internal.cairo.cairo_font_extents_t;
import org.graphstream.graph.DepthFirstIterator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Node;
import org.jfree.eastwood.GSeriesLabelGenerator;

import RdmGsaNetAlgo.graphToolkit;
import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNetAlgo.graphToolkit.element;
import RdmGsaNetAlgo.graphToolkit.elementTypeToReturn;
import RdmGsaNet_generateGraph.generateNetEdge.genEdgeType;

public class generateNetEdgeInDynamicRadius_01 implements generateNetEdge_Inter {

	// parameters
	private genEdgeType genEdgeType;
	private int idEdgeInt = 0 , idNetInt = 0 ;
	private double distCeckSeed ;

	public generateNetEdgeInDynamicRadius_01 ( genEdgeType genEdgeType  ) {
		this.genEdgeType =  genEdgeType ;	
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
				
			distCeckSeed = gsAlgoToolkit.getDistGeom(nodeSeed, netGraph.getNode(idFather )) ;
		
			if ( distCeckSeed >= 0.1 ) 		//				System.out.println(distCeckSeed);
				distCeckSeed = 0.1 ;
			
			// get id edge
			idEdgeInt = graphToolkit.getMaxIdIntElement(netGraph, element.edge);
			idEdge = Integer.toString(idEdgeInt);
		
			// add edge soon - father	
			Edge edge = null ; 
		
			netGraph.addEdge(idEdge, idSeed, idFather) ; 					
			edge = netGraph.getEdge(idEdge) ;	
			
			ArrayList<Edge> listEdgeInRadius = new ArrayList<Edge> ( graphToolkit.getListEdgeInRadius(netGraph, idSeed, distCeckSeed , true )) ;		
			ArrayList<Edge> listXEdges = new ArrayList<Edge> ( graphToolkit.getListEdgeXInList(edge, listEdgeInRadius));	
			
			if ( ! listXEdges.isEmpty() ) { //				System.out.println(listXEdges);
		
				Node near = null ;
				
				for ( Edge e : listXEdges ) {
					
					Node n0 = e.getNode0() ;
					Node n1 = e.getNode1() ;
					
					double dist0 = gsAlgoToolkit.getDistGeom( e.getNode0(), netGraph.getNode(idFather) );					
					double dist1 = gsAlgoToolkit.getDistGeom( e.getNode1(), netGraph.getNode(idFather) );
					
					String idWin = null ; 
			
					if ( dist0 <= dist1  && Math.min(dist0, dist1) <= distCeckSeed   ) 
						idWin = n0.getId() ;
					else if  ( dist0 > dist1  && Math.min(dist0, dist1) <= distCeckSeed   ) 
						idWin = n1.getId() ;
										
					if ( idFather == idWin )			
						continue ;
					
					near = netGraph.getNode(idWin) ;
								
				}	
				
				try {
					idEdgeInt = graphToolkit.getMaxIdIntElement(netGraph, element.edge) ;
					idEdge = Integer.toString(idEdgeInt) ;
					netGraph.addEdge(idEdge, near.getId(), idFather) ;	
					
					if ( ! listIdToDeleteSeed.contains(idSeed))
						listIdToDeleteSeed.add(idSeed) 	;	//		double dist = gsAlgoToolkit.getDistGeom(near, netGraph.getNode(idFather));
					
					idEdgeInt++ ;
				}
				catch (NullPointerException e) {	//	continue ;
				}
				catch (EdgeRejectedException e) {	//	e.printStackTrace();	//			continue ;			
				}
				continue ; 
			}	
			
					
// compute list of nodes --------------------------------------------------------------------------------------------------------------------------			
			listIdTNodeToConnect = getlistIdTNodeToConnect( distCeckSeed , nodeNet, idFather);
				
			if ( ! listIdTNodeToConnect.isEmpty() ) {
				
				for ( String idNear : listIdTNodeToConnect ) {				
					try {
						DepthFirstIterator it = new DepthFirstIterator(nodeNet);
						
						while(it.hasNext() ){
				            Node nIter = it.next();
			
				            int depthMin = 2 ,
				            	depthMax = 5 ;
				            
				            if ( it.getDepthOf(nodeNet) >= depthMin && it.getDepthOf(nodeNet) <= depthMax  ) {
	
				            	System.out.println( it.getDepthOf(nodeNet));
					            double dist = gsAlgoToolkit.getDistGeom(nIter , nodeNet);
					            
					            if ( dist < distCeckSeed ) {
					            	idEdgeInt = graphToolkit.getMaxIdIntElement(netGraph, element.edge) ;
									idEdge = Integer.toString(idEdgeInt)  ;				
									netGraph.addEdge(idEdge , idNear , idSeed ) ;
	
									edge = netGraph.getEdge(idEdge) ;	
									
									ArrayList<Edge> listEdgeInRadius2 = new ArrayList<Edge> ( graphToolkit.getListEdgeInRadius(netGraph, idSeed, dist , true )) ;		
									ArrayList<Edge> listXEdges2 = new ArrayList<Edge> ( graphToolkit.getListEdgeXInList(edge, listEdgeInRadius2));	
									
									if ( ! listXEdges2.isEmpty() ) { //				System.out.println(listXEdges);
										
										Node nodeWin = null ;
										
										for ( Edge e : listXEdges2 ) {
											
											Node n0 = e.getNode0() ;
											Node n1 = e.getNode1() ;
											
											double dist0 = gsAlgoToolkit.getDistGeom( e.getNode0(), nIter );					
											double dist1 = gsAlgoToolkit.getDistGeom( e.getNode1(), nIter );
											
											String idWin = null ; 
									
											if ( dist0 <= dist1  && Math.min(dist0, dist1) <= distCeckSeed   ) 
												idWin = n0.getId() ;
											else if  ( dist0 > dist1  && Math.min(dist0, dist1) <= distCeckSeed   ) 
												idWin = n1.getId() ;
																
											if ( nIter.getId() == idWin )			
												continue ;
											
											nodeWin = netGraph.getNode(idWin) ;
														
										}
										try {
											idEdgeInt = graphToolkit.getMaxIdIntElement(netGraph, element.edge) ;
											idEdge = Integer.toString(idEdgeInt) ;
											netGraph.addEdge(idEdge, nodeWin.getId(), nIter.getId() ) ;	
											
											if ( ! listIdToDeleteSeed.contains(idSeed))
												listIdToDeleteSeed.add(idSeed) 	;	//		double dist = gsAlgoToolkit.getDistGeom(near, netGraph.getNode(idFather));

											idEdgeInt++ ;							
										}
										catch (NullPointerException e) { 	//	e.printStackTrace();		
										}
										catch (EdgeRejectedException e) {	//	e.printStackTrace();		
										}				
									}									
					            }				            	
				            }		
						}
					}
					catch (EdgeRejectedException e) {
						// TODO: handle exception
					}	
				}
			}
					
			if ( ! listIdToDeleteSeed.isEmpty())		
				handleDeleteNodes (listIdToDeleteSeed) ;		
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
			private ArrayList<String> getlistIdTNodeToConnect ( double distCeckSeed , Node nodeNet , String idFather) {
				
			ArrayList<String> listIdTNodeToConnect  = new ArrayList<String> ( );				
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
