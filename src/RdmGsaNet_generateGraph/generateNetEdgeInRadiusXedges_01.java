package RdmGsaNet_generateGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

import RdmGsaNetAlgo.graphToolkit;
import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNetAlgo.graphToolkit.element;
import RdmGsaNetAlgo.graphToolkit.elementTypeToReturn;
import RdmGsaNet_generateGraph.generateNetEdge.genEdgeType;

public class generateNetEdgeInRadiusXedges_01 implements generateNetEdge_Inter {

	// parameters
	private genEdgeType genEdgeType;
	private int idEdgeInt = 0 , idNetInt = 0 ;
	private double distCeckSeed ;
	private boolean runXedges ;

	
	public generateNetEdgeInRadiusXedges_01(  genEdgeType genEdgeType , double distCeckSeed , boolean runXedges ) {
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
		ArrayList<String> listIdToDeleteNet = new ArrayList<String> ();
		ArrayList<String> listIdToDeleteSeed = new ArrayList<String> ();
		ArrayList<String> listIdTNodeToConnect = new ArrayList<String> ();		
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
			
			ArrayList<Edge> listEdgeInRadius = new ArrayList<Edge> ( graphToolkit.getListEdgeInRadius(netGraph, idSeed, 1 , true )) ;		
		//	System.out.println(idSeed + " " + listEdgeInRadius);
			
// handle add father edges ---------------------------------------------------------------------------------------------------------------------------			
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
			try	{ 
				netGraph.addEdge(idEdge, idSeed, idFather );	
			
				Edge e = netGraph.getEdge(idEdge) ;	//		boolean isEdgeInList = graphToolkit.ceckEdgeInList( e , listEdgeInRadius ) ;
				
				//Edge eTest = graphToolkit.getEdgeXInList( e , listEdgeInRadius );

				ArrayList<Edge> listETest = new ArrayList<Edge> ( graphToolkit.getListEdgeXInList(e, listEdgeInRadius) ) ;
				for ( Edge eTest : listETest ) {	
				
					{		if ( !listETest.isEmpty() ) {	//	System.out.println(isEdgeInList);	System.out.println( listEdgeInRadius ) ;	System.out.println(eTest);
				
						//	netGraph.removeEdge(eTest);
						//	listIdToDeleteSeed.add(idSeed) ;
						
						double dist1 = gsAlgoToolkit.getDistGeom( e.getNode0(), netGraph.getNode(idFather) );
						double dist2 = gsAlgoToolkit.getDistGeom( e.getNode1(), netGraph.getNode(idFather) );
						
						String idE0 = eTest.getNode0().getId() ;
						String idE1 = eTest.getNode1().getId() ;		//	System.out.println(idE0 + " " + idE1 ); 
										
						if ( dist1 <= dist2 ) {
						
							netGraph.addEdge(idEdge, idFather , idE0 ) ;
							listETest = new ArrayList<Edge> ( graphToolkit.getListEdgeXInList( netGraph.getEdge(idEdge) , listEdgeInRadius) ) ;
							if ( listETest.isEmpty() )
								continue ;
							else {
								netGraph.removeEdge(idEdge) ;
							}
						} else { 
							netGraph.addEdge(idEdge, idFather , idE1 ) ;	
							listETest = new ArrayList<Edge> ( graphToolkit.getListEdgeXInList( netGraph.getEdge(idEdge) , listEdgeInRadius) ) ;
							if ( listETest.isEmpty() )
								continue ;
							else {
								netGraph.removeEdge(idEdge) ;
							}
						}
						
						//	continue ;
					}
				}					
				}
			}
			
			
			catch 	( org.graphstream.graph.IdAlreadyInUseException e ) { /* e.printStackTrace(); */	}
			catch	( org.graphstream.graph.ElementNotFoundException e) { /* e.printStackTrace(); */	}
			
// compute list of nodes --------------------------------------------------------------------------------------------------------------------------			
			listIdTNodeToConnect = getlistIdTNodeToConnect(listIdTNodeToConnect, nodeNet, idFather);
		
			// get id edge
			listIdEdgeInt = new ArrayList<Integer>( graphToolkit.getListElement(netGraph, element.edge, elementTypeToReturn.integer)) ;
			idEdgeInt = Collections.max(listIdEdgeInt) ;
				
			// get id node
			listIdNetInt = new ArrayList<Integer>( graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.integer)) ;
			idNetInt = Collections.max(listIdNetInt);
				
			for ( String idNear : listIdTNodeToConnect ) {
				
				if ( listIdSeed.contains(idNear) ) {
					seedGraph.removeNode( idNear );
					listIdToDeleteSeed.add(idNear) ;
					continue ;
				}
				
				while ( listIdEdgeInt.contains(idEdgeInt)) 
					idEdgeInt ++ ;
				
				idEdge = Integer.toString(idEdgeInt);
				
				try {				
					netGraph.addEdge(idEdge, idSeed, idNear); 						
					Edge e = netGraph.getEdge(idEdge) ;				
					ArrayList<Edge> listETest = new ArrayList<Edge> ( graphToolkit.getListEdgeXInList(e, listEdgeInRadius) ) ;
					
					for ( Edge eTest : listETest ) {
						
						if ( ! listETest.isEmpty() ) {	//	System.out.println(isEdgeInList);	System.out.println( listEdgeInRadius ) ;	System.out.println(eTest);
					
							//	netGraph.removeEdge(eTest);
							listIdToDeleteSeed.add(idSeed) ;
							
							double dist1 = gsAlgoToolkit.getDistGeom( e.getNode0(), netGraph.getNode(idFather) );
							double dist2 = gsAlgoToolkit.getDistGeom( e.getNode1(), netGraph.getNode(idFather) );
							
							String idE0 = eTest.getNode0().getId() ;
							String idE1 = eTest.getNode1().getId() ;		//	System.out.println(idE0 + " " + idE1 ); 
											
							if ( dist1 <= dist2 ) {
								netGraph.addEdge(idEdge, idFather , idE0 ) ;	
								listETest = new ArrayList<Edge> ( graphToolkit.getListEdgeXInList( netGraph.getEdge(idEdge) , listEdgeInRadius) ) ;
								if ( listETest.isEmpty() )
									continue ;
								else {
									netGraph.removeEdge(idEdge) ;
								}
							}
							else {
								netGraph.addEdge(idEdge, idFather , idE1 ) ;				
								listETest = new ArrayList<Edge> ( graphToolkit.getListEdgeXInList( netGraph.getEdge(idEdge) , listEdgeInRadius) ) ;
								if ( listETest.isEmpty() )
									continue ;
								else {
									netGraph.removeEdge(idEdge) ;
								}						
							}
						}				
					}
				
				
				}
				catch (org.graphstream.graph.IdAlreadyInUseException e) {		}
				catch (org.graphstream.graph.EdgeRejectedException e ) 	{		}
			
				listIdToDeleteSeed.add(idSeed) ;	}		
			
			// delete Nodes
			handleDeleteNodes(listIdToDeleteSeed, listIdToDeleteNet);
				
			// clear list and map
			listIdTNodeToConnect.clear();
			listIdToDeleteSeed.clear();
			listIdToDeleteNet.clear(); 
		}
	}
// --------------------------------------------------------------------------------------------------------------------------------------------------	

	@Override
	public void removeEdgeRule(double step) {
		// TODO Auto-generated method stub
		
	}
// handle methods -----------------------------------------------------------------------------------------------------------------------------------	
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
