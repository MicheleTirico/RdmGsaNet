package RdmGsaNet_generateGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;
import org.jfree.chart.labels.SymbolicXYItemLabelGenerator;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import RdmGsaNetAlgo.graphToolkit;
import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNetAlgo.graphToolkit.element;
import RdmGsaNetAlgo.graphToolkit.elementTypeToReturn;

import RdmGsaNet_generateGraph.generateNetEdge.genEdgeType;

public class generateNetEdgeInRadiusFather_02  implements generateNetEdge_Inter {

	private genEdgeType genEdgeType;
	private int idEdgeInt = 0 ;
	// parameters
	private double distCeckSeed ;
			
	//constructor
	public generateNetEdgeInRadiusFather_02 (  genEdgeType genEdgeType , double distCeckSeed  ) {
		this.genEdgeType =  genEdgeType ;
		this.distCeckSeed = distCeckSeed ;
	}
		
	@Override
	public void generateEdgeRule ( double step ) {
		
		// list node element
//		ArrayList <Node> listNodeSeed = new ArrayList<Node> ( graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.element));	// System.out.println(listNodeSeed);	
//		ArrayList <Node> listNodeNet = new ArrayList<Node>  ( graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.element));		//	System.out.println(listNodeNet);
//				
		// list id string
//		ArrayList <String> listIdNet = new ArrayList<String>  ( graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.string));	//	System.out.println(listNodeNet);
//		ArrayList <String> listIdSeed = new ArrayList<String> ( graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.string));	//	System.out.println(listNodeNet);
			 
		// list id int
//		ArrayList<Integer> listIdNetInt = new ArrayList<Integer> ( graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.integer)) ;			
//		ArrayList<Integer> listIdSeedInt = new ArrayList<Integer>( graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.integer)) ;			

		// list id edge int
//		ArrayList<Integer> listIdEdgeNet = new ArrayList<Integer> ( graphToolkit.getListElement(netGraph, element.edge, elementTypeToReturn.string)) ;			
				
		// print
//		System.out.println(seedGraph + " " + listIdSeedInt.size() + " " + listIdSeedInt );
//		System.out.println(netGraph + " " + listIdNetInt.size() + " " + listIdNetInt );
//		System.out.println(netGraph + " edge " + listIdEdgeNet.size() + " " + listIdEdgeNet );

			
		switch (genEdgeType) {
			case onlyFather: 	
				onlyFather( true ) ;
				break;
				
			case fatherAndNodeInRadius : 				
				fatherAndNodeInRadius ( ) ;
				break ;
				
			
		}
	}

//---------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public void removeEdgeRule(double step) {
		// TODO Auto-generated method stub
		
	}

//---------------------------------------------------------------------------------------------------------------------------------------------------
		
	public void fatherAndNodeInRadius (   ) {
		
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
				// listIdToDeleteSeed.add(idSeed) ;
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
				continue;
			else {
				// create all edge
				listIdEdgeInt = new ArrayList<Integer>( graphToolkit.getListElement(netGraph, element.edge, elementTypeToReturn.integer)) ;
				idEdgeInt = Collections.max(listIdEdgeInt);
				
				listIdNetInt = new ArrayList<Integer>( graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.integer)) ;
				idNetInt = Collections.max(listIdNetInt);
				
				for ( String idNear : listIdTNodeToConnect ) {
	//				System.out.println();
	//				System.out.println("list seed " + listIdSeed ) ;
	//				System.out.println("idNear " + idNear );
					
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
	
		
	public static void distCeckSeedMethod ( double distCeck  ) {
		if ( distCeck == 0 ) 
			return ;
		
		else if ( distCeck !=0 ){
			for ( Node nodeSeed : seedGraph.getEachNode() ) {
				
				Map <String , Double> mapDist = generateNetEdge.getMapIdDist( seedGraph , nodeSeed ) ;	//	System.out.println(mapDist);
				
				for ( String nearSeed : mapDist.keySet() ) {
					
					double dist = mapDist.get(nearSeed) ;
					
					if ( dist < distCeck ) {
						
						String idSeed = nodeSeed.getId() ;
						Node nodeNetNear = netGraph.getNode(nearSeed) ;
						Node nodeSeedNear = seedGraph.getNode(nearSeed);
						
						Node nodeNetSeed = netGraph.getNode(idSeed) ;
						String nearFather = nodeNetNear.getAttribute("father");
						Node nodeFather = netGraph.getNode(nearFather) ;
						
						double[] coordNodeMean = graphToolkit.getCoordNodeMean ( nodeSeed , nodeSeedNear );
						
						nodeSeed.setAttribute("xyz", coordNodeMean[0] , coordNodeMean[1] , 0 );
						
						netGraph.removeNode(nodeNetNear);
						seedGraph.removeNode(nodeSeedNear) ;
						
						
						try {
							String idEdge = nodeSeed.getId() + "-" + nearFather ;
							netGraph.addEdge(idEdge, nodeFather , nodeNetSeed ) ;		
						}
						
						catch (org.graphstream.graph.IdAlreadyInUseException e1 ) {
							String idEdge =  nearFather + "-" +  nodeSeed.getId() ;
							try {
								netGraph.addEdge(idEdge, nodeFather , nodeNetSeed ) ; 
							} catch (org.graphstream.graph.EdgeRejectedException e ) {
								continue ;
							}
						}
						
						 catch (org.graphstream.graph.EdgeRejectedException e2) {
							continue ;
						}
					}
				}
			}
		}
	}


	
	
	
	
	
	
	
	
}