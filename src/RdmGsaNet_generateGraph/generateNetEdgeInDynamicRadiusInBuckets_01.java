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
import RdmGsaNet_mainSim.main;
import RdmGsaNet_staticBuckets_01.bucketSet;

public class generateNetEdgeInDynamicRadiusInBuckets_01 implements generateNetEdge_Inter {

	// parameters
	private static genEdgeType genEdgeType;
	private int idEdgeInt = 0 , idNetInt = 0 ;
	private double distCeckSeed ;
	private bucketSet bucketSet ; 

	public generateNetEdgeInDynamicRadiusInBuckets_01 ( genEdgeType genEdgeType , bucketSet bucketSet  ) {
		this.genEdgeType =  genEdgeType ;	
		this.bucketSet = bucketSet ; 
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
	
	public void fatherAndNodeInRadius (   ) {		//	System.out.println(bucketSet.getBucketsCount());
		
		// create array list of element
		ArrayList<String> listIdSeed = new ArrayList<String>( graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.string)) ;
		ArrayList<String> listIdTNodeToConnect = new ArrayList<String> ();		

		for ( String idSeed : listIdSeed ) {
			
			// declare variables 			
			Node nodeSeed , nodeNet ;			
			String  idFather , idEdge    ;	
						
// handle ad father edges ---------------------------------------------------------------------------------------------------------------------------					
			// get father id		
			nodeSeed = seedGraph.getNode(idSeed) ;						// 	System.out.println(nodeSeed);
			nodeNet = netGraph.getNode(idSeed) ;						//	System.out.println(nodeNet);
			idFather = nodeNet.getAttribute("father");	 					
				
			distCeckSeed = gsAlgoToolkit.getDistGeom(nodeSeed, netGraph.getNode( idFather )) ;
		
			if ( distCeckSeed < 0.1 ) 			
				distCeckSeed = 0.1 ;
			
			// get id edge
			idEdgeInt = graphToolkit.getMaxIdIntElement(netGraph, element.edge);
			idEdge = Integer.toString(idEdgeInt);
		
			// add edge soon - father	
			Edge edge = null ; 
				
			netGraph.addEdge(idEdge, idSeed, idFather) ; 							
			edge = netGraph.getEdge(idEdge) ;
		//	System.out.println(bucketSet);
			bucketSet.putEdgeInBucketSet(edge);
	//		System.out.println(edge);
				
			System.out.println( ( graphToolkit.getListEdgeInRadius(netGraph, idSeed, 1.0 , true )) )  ;	
			ArrayList<Edge> listEdgeInRadius = new ArrayList<Edge> ( graphToolkit.getListEdgeInRadiusInBucketSet(netGraph, idSeed, 1 , true , bucketSet )) ;				
			System.out.println( listEdgeInRadius )  ;	
			
			ArrayList<Edge> listXEdges = new ArrayList<Edge> ( graphToolkit.getListEdgeXInList(edge, listEdgeInRadius));	
			
			if ( ! listXEdges.isEmpty() ) { //				System.out.println(listXEdges);
		
				Node near = null ;
				
				for ( Edge e : listXEdges ) {
					
					Node n0 = e.getNode0() ;
					
					Node n1 = e.getNode1() ;
					
					double dist0 = gsAlgoToolkit.getDistGeom( n0 , netGraph.getNode(idFather) );					
					double dist1 = gsAlgoToolkit.getDistGeom( n1 , netGraph.getNode(idFather) );
					
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
					
					netGraph.addEdge(idEdge, near.getId(), idFather ) ;	
					near.addAttribute("father", idFather);
					
					bucketSet.putEdgeInBucketSet(netGraph.getEdge( idEdge ) );		
					
					seedGraph.removeNode(idSeed) ;
					netGraph.removeNode(idSeed) ;
					
					continue ; 			
				}
				catch (NullPointerException e) 		{
					
					// TODO: handle exception 
				}
				catch (EdgeRejectedException e) 	{	
			
					// TODO: handle exception 
				}
				catch (ElementNotFoundException e) 	{ 
				
					// TODO: handle exception
				}		
			}	
			
					
// compute list of nodes --------------------------------------------------------------------------------------------------------------------------			
			listIdTNodeToConnect = getlistIdTNodeToConnectInBucketSet ( distCeckSeed , nodeNet , idFather );	//			System.out.println(listIdTNodeToConnect);	
			
			if ( listIdTNodeToConnect.isEmpty() ) 
				continue ; 
				
				for ( String idNear : listIdTNodeToConnect ) {				
					try {
						idEdgeInt = graphToolkit.getMaxIdIntElement(netGraph, element.edge) ;
						idEdge = Integer.toString(idEdgeInt)  ;
						
						netGraph.addEdge(idEdge, idSeed , idNear) ;
						bucketSet.putEdgeInBucketSet(netGraph.getEdge( idEdge ) );	
						seedGraph.removeNode(idSeed);	
						
						listIdSeed = graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.string) ;
						
						if ( listIdSeed.contains( idNear )) {
							seedGraph.removeNode(idNear);	
							continue ; 
						}
					
						edge = netGraph.getEdge(idEdge) ;	
						
						ArrayList<Edge> listEdgeInRadius2 = new ArrayList<Edge> ( graphToolkit.getListEdgeInRadiusInBucketSet(netGraph, idSeed, 1 , true , bucketSet )) ;		
					
						
						ArrayList<Edge> listXEdges2 = new ArrayList<Edge> ( graphToolkit.getListEdgeXInList(edge, listEdgeInRadius2));	
						
						if ( ! listXEdges2.isEmpty() ) { //				System.out.println(listXEdges);
					
							Node nearEdgeX = null ;
							
							for ( Edge e : listXEdges2 ) {
								
								Node n0 = e.getNode0() ;
								Node n1 = e.getNode1() ;
								
								double dist0 = gsAlgoToolkit.getDistGeom( n0 , netGraph.getNode( idSeed ) );					
								double dist1 = gsAlgoToolkit.getDistGeom( n1 , netGraph.getNode( idSeed ) );
								
								String idWin = null ; 
						
								if ( dist0 <= dist1  && Math.min(dist0, dist1) <= distCeckSeed   ) 
									idWin = n0.getId() ;
								else if  ( dist0 > dist1  && Math.min(dist0, dist1) <= distCeckSeed   ) 
									idWin = n1.getId() ;
													
								if ( idNear == idWin || idSeed == idWin  )			
									continue ;
								
								nearEdgeX = netGraph.getNode(idWin) ;					
							}	
							
							try {
								idEdgeInt = graphToolkit.getMaxIdIntElement(netGraph, element.edge) ;
								idEdge = Integer.toString(idEdgeInt) ;
								netGraph.addEdge(idEdge, nearEdgeX.getId(), idSeed ) ;	
								bucketSet.putEdgeInBucketSet(netGraph.getEdge( idEdge ) );	
								seedGraph.removeNode(idSeed) ;
								seedGraph.removeNode(idNear) ;
									
								break ; 							
							}
							catch (NullPointerException e) 		{
								// TODO: handle exception 
							}
							catch (EdgeRejectedException e) 	{
								// TODO: handle exception 
							}
							catch (ElementNotFoundException e) 	{
								// TODO: handle exception 
							}

					}
						
					}
					 catch (ElementNotFoundException e) {
						// TODO: handle exception 
					}
					catch (EdgeRejectedException e) {
						// TODO: handle exception
					}
				}
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
	private ArrayList<String> getlistIdTNodeToConnect ( double distCeckSeed , Node nodeNet , String idFather ) {
				
		ArrayList<String> listIdTNodeToConnect  = new ArrayList<String> ( );				
		Map <String , Double> mapDistNet = generateNetEdge.getMapIdDist( netGraph , nodeNet ) ;	//	double minDist = mapDistNet.values().stream().mapToDouble(valstat -> valstat).min().getAsDouble();			
		Map < String , Double > mapTopDist = gsAlgoToolkit.getMapTopValues(mapDistNet, 10 ) ;	//		Set<String> setIdNear = gsAlgoToolkit.getKeysByValue(mapDistNet, minDist ); 
		
		for ( String idNear : mapTopDist.keySet()) {
			double dist =  mapTopDist.get(idNear);
			if ( dist <= distCeckSeed && !idNear.equals(idFather) ) 
				listIdTNodeToConnect.add(idNear) ;
			else 
				break ;
		}
			
		return listIdTNodeToConnect ;
	}
	
	private ArrayList<String> getlistIdTNodeToConnectInBucketSet ( double distCeckSeed , Node nodeNet , String idFather ) {
		
		ArrayList<String> listIdTNodeToConnect  = new ArrayList<String> ( );				
		Map <String , Double> mapDistNet = generateNetEdge.getMapIdDistInBucketSet( nodeNet, bucketSet) ;	//	double minDist = mapDistNet.values().stream().mapToDouble(valstat -> valstat).min().getAsDouble();			
		Map < String , Double > mapTopDist = gsAlgoToolkit.getMapTopValues(mapDistNet, 10 ) ;	//		Set<String> setIdNear = gsAlgoToolkit.getKeysByValue(mapDistNet, minDist ); 
		
		for ( String idNear : mapTopDist.keySet()) {
			double dist =  mapTopDist.get(idNear);
			if ( dist <= distCeckSeed && !idNear.equals(idFather) ) 
				listIdTNodeToConnect.add(idNear) ;
			else 
				break ;
		}
			
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
			netGraph.addEdge(idEdge, nNet, nFather) ;	
			bucketSet.putEdgeInBucketSet(netGraph.getEdge( idEdge ) );	
			//		System.out.println(seedGraph + " " + nSeed.getId( ) + " " + father);
		}
	}
	
	

}
