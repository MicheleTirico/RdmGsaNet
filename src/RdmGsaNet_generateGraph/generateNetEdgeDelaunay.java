package RdmGsaNet_generateGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.graphstream.graph.Node;

import RdmGsaNetAlgo.graphToolkit;
import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNetAlgo.graphToolkit.element;
import RdmGsaNetAlgo.graphToolkit.elementTypeToReturn;
import RdmGsaNet_generateGraph.generateNetEdge.genEdgeType;

public class generateNetEdgeDelaunay implements generateNetEdge_Inter {

	
	private int idEdgeInt = 0 ;
	// parameters
	private double distCeckSeed ;
			
	//constructor
	public generateNetEdgeDelaunay (  double distCeckSeed  ) {	
		this.distCeckSeed = distCeckSeed ;
	}

	@Override
	public void generateEdgeRule(double step) {
		
		ArrayList<String> listIdSeed = new ArrayList<String>( graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.string)) ;
		
		ArrayList<String> listIdToDeleteNet = new ArrayList<String> ();
		ArrayList<String> listIdToDeleteSeed = new ArrayList<String> ();
		ArrayList<String> listIdTNodeToConnect = new ArrayList<String> ();
		
		ArrayList<Integer> listIdEdgeInt = new ArrayList<Integer>( graphToolkit.getListElement(netGraph, element.edge, elementTypeToReturn.integer)) ;		
//		ArrayList<Integer> listIdSeedInt = new ArrayList<Integer>( graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.integer)) ;
		ArrayList<Integer> listIdNetInt = new ArrayList<Integer>( graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.integer)) ;
					
		for ( String idSeed : listIdSeed ) {	//	System.out.println(nSeed);
			
			listIdSeed = new ArrayList<String>( graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.string)) ;
			if ( !listIdSeed.contains(idSeed) ) {	
				try {
					seedGraph.removeNode(idSeed);
				} catch (org.graphstream.graph.ElementNotFoundException e) {		}
			}
			
			Node nodeNet ;
			try {
				nodeNet = netGraph.getNode(idSeed) ;						//	System.out.println(nodeNet);
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
			
			for ( String idNear : mapTopDist.keySet()) {
				double dist =  mapTopDist.get(idNear);
				if ( dist < distCeckSeed && !idNear.equals(idFather) ) 
					listIdTNodeToConnect.add(idNear) ;
				else 
					break ;
			}
			*/
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
						
						ArrayList<String> listIdNodeToAddEdge = new ArrayList<String>( Arrays.asList( idFatherNear ,  idFatherSeed  
																									, idNear  
																									, idSeed  
																									, idFather 
																									));
					
						for ( String s : listIdNodeToAddEdge ) {
							try {
								
								idEdgeInt ++ ;
								idEdge = Integer.toString(idEdgeInt) ; 
								netGraph.addEdge(idEdge, idNode , s );
								idEdgeInt ++ ;
							}
				//			catch (org.graphstream.graph.IdAlreadyInUseException e) 	{  continue;	}
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
						catch (org.graphstream.graph.IdAlreadyInUseException e) {		}
						catch (org.graphstream.graph.EdgeRejectedException e ) 	{		}
					
						listIdToDeleteSeed.add(idSeed) ;
					}
				}		
			
				for ( String id : listIdToDeleteSeed ) {
					try {
						seedGraph.removeNode(id);
					}
					catch (org.graphstream.graph.ElementNotFoundException e) {
						// TODO: handle exception
					}
				}
				
				for ( String id : listIdToDeleteNet ) {
					try {
						netGraph.removeNode(id);
					}
					catch (org.graphstream.graph.ElementNotFoundException e) {
						// TODO: handle exception
					}
				}
				// clear list and map
				listIdTNodeToConnect.clear();
				listIdToDeleteSeed.clear();
				listIdToDeleteNet.clear();
			}
		}
		
		
	}

	@Override
	public void removeEdgeRule(double step) {
		// TODO Auto-generated method stub
		
	}
}
