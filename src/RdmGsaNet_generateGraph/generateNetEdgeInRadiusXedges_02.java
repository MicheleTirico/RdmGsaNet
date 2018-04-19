package RdmGsaNet_generateGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import org.eclipse.swt.internal.cairo.cairo_font_extents_t;
import org.graphstream.graph.Edge;
import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.Node;

import RdmGsaNetAlgo.graphToolkit;
import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNetAlgo.graphToolkit.element;
import RdmGsaNetAlgo.graphToolkit.elementTypeToReturn;
import RdmGsaNet_generateGraph.generateNetEdge.genEdgeType;

public class generateNetEdgeInRadiusXedges_02 implements generateNetEdge_Inter {

	// parameters
	private genEdgeType genEdgeType;
	private int idEdgeInt = 0 , idNetInt = 0 ;
	private double distCeckSeed ;
	private boolean runXedges ;

	
	public generateNetEdgeInRadiusXedges_02(  genEdgeType genEdgeType , double distCeckSeed , boolean runXedges ) {
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
	
	for ( String idSeed : listIdSeed ) {	//	System.out.println(nSeed);
			
			// declare variables 
			Node nodeSeed , nodeNet ;
			String fatSeed , fat , idFather , idEdge , idNode = null , idEdgeX ;	
			
			// update list id seed
			if ( !listIdSeed.contains(idSeed) ) {
				try		{ seedGraph.removeNode(idSeed);	
				}
				catch 	( org.graphstream.graph.ElementNotFoundException e) {		}
			}
			
			ArrayList<Edge> listEdgeInRadius = new ArrayList<Edge> ( graphToolkit.getListEdgeInRadius(netGraph, idSeed, .2 , true )) ;	
			
// handle ad father edges ---------------------------------------------------------------------------------------------------------------------------			
			// get father id
			try {
				nodeSeed = seedGraph.getNode(idSeed) ;						// 	System.out.println(nodeSeed);
				nodeNet = netGraph.getNode(idSeed) ;						//	System.out.println(nodeNet);
				fatSeed = nodeSeed.getAttribute("father");
				idFather = nodeNet.getAttribute("father");	 					
			} 
			catch (java.lang.NullPointerException e) {	e.printStackTrace();	continue ;		}
					
			// get id edge
			idEdgeInt = graphToolkit.getMaxIdIntElement(netGraph, element.edge);
			idEdge = Integer.toString(idEdgeInt);
		
			// add edge soon - father
			try	{ 			
				netGraph.addEdge(idEdge, idSeed, idFather) ; 			
				Edge edge = netGraph.getEdge(idEdge) ;			
				ArrayList<Edge> listETest = new ArrayList<Edge> ( graphToolkit.getListEdgeXInList(edge, listEdgeInRadius) ) ;

				if ( ! listETest.isEmpty() ) {		
//					listIdToDeleteSeed.add(idSeed) ;	
					
					//	System.out.println(listETest);
					for ( Edge eTest : listETest ) {
						
						double dist0 = gsAlgoToolkit.getDistGeom( eTest.getNode0(), netGraph.getNode(idFather) );
						double dist1 = gsAlgoToolkit.getDistGeom( eTest.getNode1(), netGraph.getNode(idFather) );
						
						String idE0 = eTest.getNode0().getId() ;
						String idE1 = eTest.getNode1().getId() ;	
						
						String idWin ; 
						
						if ( dist0 <= dist1 ) 
							idWin = idE0;
						else 
							idWin = idE1 ;
					
					//	System.out.println(idSeed + " " + idFather + " " + idWin);
					
						if ( idFather == idWin )
							continue ;
	
						try {
							idEdgeInt = graphToolkit.getMaxIdIntElement(netGraph, element.edge);
							idEdge = Integer.toString(idEdgeInt);
							netGraph.addEdge(idEdge, idFather, idWin) ;
	//							
							
						} 
						catch (org.graphstream.graph.EdgeRejectedException e) {					}
						
					continue ;
					
					}
				}
				
			}
			
			catch 	( org.graphstream.graph.IdAlreadyInUseException e ) {  e.printStackTrace(); 	}
		//	catch	( org.graphstream.graph.ElementNotFoundException e) {  e.printStackTrace(); 	}

	
// compute list of nodes --------------------------------------------------------------------------------------------------------------------------			
			listIdTNodeToConnect = getlistIdTNodeToConnect(listIdTNodeToConnect, nodeNet, idFather);
			
			if ( listIdTNodeToConnect.isEmpty())
				continue ;
			
			System.out.println(listIdTNodeToConnect);
			
			// get id edge
			listIdEdgeInt = new ArrayList<Integer>( graphToolkit.getListElement(netGraph, element.edge, elementTypeToReturn.integer)) ;
			idEdgeInt = Collections.max(listIdEdgeInt) ;
				
			// get id node
			listIdNetInt = new ArrayList<Integer>( graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.integer)) ;
			idNetInt = Collections.max(listIdNetInt);
			
			listEdgeInRadius = new ArrayList<Edge> ( graphToolkit.getListEdgeInRadius(netGraph, idSeed , .2 , true )) ;	
			System.out.println( listEdgeInRadius ) ; 
			for ( String idNear : listIdTNodeToConnect ) {
				
		
			

			}		
			
			// delete Nodes
			handleDeleteNodes(listIdToDeleteSeed);
				
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
		private void handleDeleteNodes (ArrayList<String> listIdToDeleteSeed ) {
			
			for ( String id : listIdToDeleteSeed ) {
				try 	{ seedGraph.removeNode(id);		
				}
				catch 	( org.graphstream.graph.ElementNotFoundException e) {	}
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
