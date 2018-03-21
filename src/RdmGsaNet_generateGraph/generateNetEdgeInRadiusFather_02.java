package RdmGsaNet_generateGraph;

import java.util.ArrayList;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNetAlgo.graphToolkit;
import RdmGsaNetAlgo.graphToolkit.element;
import RdmGsaNetAlgo.graphToolkit.elementTypeToReturn;

import RdmGsaNet_generateGraph.generateNetEdge.genEdgeType;

public class generateNetEdgeInRadiusFather_02  implements generateNetEdge_Inter {

	private genEdgeType genEdgeType;

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
		ArrayList <Node> listNodeSeed = new ArrayList<Node> ( graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.element));	// System.out.println(listNodeSeed);	
		ArrayList <Node> listNodeNet = new ArrayList<Node>  ( graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.element));		//	System.out.println(listNodeNet);
				
		// list id string
		ArrayList <String> listIdNet = new ArrayList<String>  ( graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.string));	//	System.out.println(listNodeNet);
		ArrayList <String> listIdSeed = new ArrayList<String> ( graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.string));	//	System.out.println(listNodeNet);
			
		// list id int
		ArrayList<Integer> listIdNetInt = new ArrayList<Integer> ( graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.integer)) ;			
		ArrayList<Integer> listIdSeedInt = new ArrayList<Integer>( graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.integer)) ;			

		// list id int
		ArrayList<Integer> listIdEdgeNet = new ArrayList<Integer> ( graphToolkit.getListElement(netGraph, element.edge, elementTypeToReturn.string)) ;			
				
		// print
//		System.out.println(seedGraph + " " + listIdSeedInt.size() + " " + listIdSeedInt );
//		System.out.println(netGraph + " " + listIdNetInt.size() + " " + listIdNetInt );
//		System.out.println(netGraph + " edge " + listIdEdgeNet.size() + " " + listIdEdgeNet );

			
		switch (genEdgeType) {
			case onlyFather: 	
				onlyFather() ;
				break;
				
			case fatherAndNodeInRadius : 				
				fatherAndNodeInRadius () ;
				break ;
				
			case fatherAndSeed :
				fatherAndSeed() ;
				break ; 
		}
		
		// distCeckSeedMethod( distCeckSeed ) ;
		
		for ( Node nodeSeed : seedGraph.getEachNode() ) {
			
			String id = nodeSeed.getId() ;// System.out.println(id);
			
			Node nodeNetSeed = netGraph.getNode(id);
		
			Map <String , Double> mapDist = generateNetEdge.getMapIdDist( netGraph , nodeNetSeed ) ;	//		System.out.println(mapDist.size());
		
			for ( String idNear : mapDist.keySet() ) {
				
				double dist = mapDist.get(idNear) ;
				
				if ( dist < distCeckSeed ) {
				//	System.out.println("idNear " + idNear);
					try {
						String idEdge = id + "-" + idNear ;
						Node nodeNear = netGraph.getNode(idNear) ;
						netGraph.addEdge(idEdge, nodeNetSeed, nodeNear) ;
					}
					catch (org.graphstream.graph.IdAlreadyInUseException e) {
						String idEdge = idNear + "-" + id  ;
						Node nodeNear = netGraph.getNode(idNear) ;
						netGraph.addEdge(idEdge, nodeNetSeed, nodeNear) ;
					}
					catch (org.graphstream.graph.EdgeRejectedException e) {
						continue ;
					}
					// System.out.println(listIdSeed) ;
					if ( listIdSeed.contains(idNear) ) {
						
						System.out.println(idNear);
						
						try {
						seedGraph.removeNode(nodeSeed);
						} catch ( java.lang.ArrayIndexOutOfBoundsException e) {
				
						}
					}
				
					else {
					//	System.out.println(netGraph + " " + netGraph.getNodeSet());
					//	System.out.println(seedGraph + " " + seedGraph.getNodeSet());
						
						Node nodeSeedNear = seedGraph.getNode(idNear) ;
						Node nodeNear = netGraph.getNode(idNear) ;
						
//						double[] coordNodeMean = graphToolkit.getCoordNodeMean ( nodeSeed , nodeSeedNear );
						
//						nodeSeedNear.setAttribute("xyz", coordNodeMean[0] , coordNodeMean[1] , 0 );
//						nodeNear.setAttribute("xyz", coordNodeMean[0] , coordNodeMean[1] , 0 );
						
				//		String father = nodeSeed.getAttribute("father");
						
				//		System.out.println("father " + father);
						
						
						seedGraph.removeNode(nodeSeed);
						
						
					}
				}
			}
		}
		
		// distCeckMethod ( distCeckSeed , netGraph );
		
		
					
				
				
			
		}
		
	

	@Override
	public void removeEdgeRule(double step) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPivot(boolean createPivot, double maxDistPivot) {
		// TODO Auto-generated method stub
		
	}
	
	public static void distCeckMethod ( double distCeck , Graph graph ) {
		
		
		if ( distCeck == 0 ) 
			return ;
		
		else if ( distCeck !=0 ){
			for ( Node nodeSeed : seedGraph.getEachNode() ) {
				
				String id = nodeSeed.getId();
				Node nodeGraph = graph.getNode(id);
				
				Map <String , Double> mapDist = generateNetEdge.getMapIdDist( graph , nodeGraph ) ;	//		System.out.println(mapDist.size());
				
				for ( String near : mapDist.keySet() ) {
					
					double dist = mapDist.get(near) ;
					
					if ( dist < distCeck ) {
						
						Node netSeed = netGraph.getNode(id);
						Node netNear = netGraph.getNode(near);
						
						try {
							String idEdge = id + "-" + near ;
							netGraph.addEdge(idEdge, netSeed , netNear ) ;		
						} 
						catch (org.graphstream.graph.IdAlreadyInUseException e1 ) {
							String idEdge =  near + "-" + id ;
							try {
							netGraph.addEdge(idEdge, netSeed , netNear ) ;	
						} catch ( org.graphstream.graph.EdgeRejectedException e ) {
							// TODO: handle exception
						}
						}
					 catch ( org.graphstream.graph.EdgeRejectedException e ) {
						// TODO: handle exception
					}
						
						seedGraph.removeNode(nodeSeed);
					}
					
				}
				
			}
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
	
	
	public void onlyFather ( ) {
		for ( Node nSeed : seedGraph.getEachNode() ) {
			
			String idSeed = nSeed.getId() ;
			String father = nSeed.getAttribute("father");
			
			Node nNet = netGraph.getNode (idSeed);
			Node nFather = netGraph.getNode(father) ;
			
			String idEdge = father + "-" + nSeed  ;
			
			netGraph.addEdge(idEdge, nNet, nFather) ;	//		System.out.println(seedGraph + " " + nSeed.getId( ) + " " + father);
		}
	}
	
	public void fatherAndNodeInRadius () {
		
		for ( Node nSeed : seedGraph.getEachNode() ) {		//	System.out.println(nSeed);
			
			String idSeed = nSeed.getId() ;
			String father = nSeed.getAttribute("father");
			
			Node nNet = netGraph.getNode (idSeed);
			Node nFather = netGraph.getNode(father) ;
			
			String idEdge = father + "-" + nSeed  ;
			
			netGraph.addEdge(idEdge, nNet, nFather) ;	//
			
			Map <String , Double> mapDist = generateNetEdge.getMapIdDist( netGraph , nNet ) ;	//	System.out.println(mapDist);
			
			for ( String s : mapDist.keySet()) {
				
				double dist = mapDist.get(s) ;
				Node node2 = netGraph.getNode(s);
				String id = s + "-" + nSeed ;
				try {
					if ( dist < distCeckSeed ) {
						netGraph.addEdge(id, nNet, node2 ) ;
					}
				} catch (org.graphstream.graph.IdAlreadyInUseException | org.graphstream.graph.EdgeRejectedException e) {
					
				}
				
				
				
			}
			
			
			
		}
		
	}
	
	
		// doesn't work 
		public void fatherAndSeed ( ) { 	
			
			for ( Node nSeed : seedGraph.getEachNode() ) {
		
			
			String idSeed = nSeed.getId() ;
			String father = nSeed.getAttribute("father");
			
			Node nNet = netGraph.getNode (idSeed);
			Node nFather = netGraph.getNode(father) ;
			
			String idEdge = father + "-" + nSeed  ;
			
			netGraph.addEdge(idEdge, nNet, nFather) ;	//		System.out.println(seedGraph + " " + nSeed.getId( ) + " " + father);
		
			Map <String , Double> mapDist = generateNetEdge.getMapIdDist( seedGraph , nSeed ) ;	//	System.out.println(mapDist);
		
			for ( String s : mapDist.keySet()) {
				
				double dist = mapDist.get(s) ;
				Node nNetNear = netGraph.getNode(s);
			
				Node nSeedNear = seedGraph.getNode(s) ;
		
	
				if ( dist < 0.1 ) {
					
					seedGraph.removeNode(s);
		//				netGraph.removeNode(s); 
					double[] coordSeed = GraphPosLengthUtils.nodePosition(nSeed);
					double[] coordNetNear = GraphPosLengthUtils.nodePosition(nSeedNear);
						
					double 	xMin = Math.min(coordSeed[0], coordNetNear[0]) , 
							yMin = Math.min(coordSeed[1], coordNetNear[1]) ,
								
							xNewCoord = xMin + ( coordSeed[0] - coordNetNear[0] ) / 2 ,
							yNewCoord = yMin + ( coordSeed[1] - coordNetNear[1] ) / 2 ;
						
					nSeed.addAttribute("xyz", xNewCoord , yNewCoord , 0 );
					System.out.println("peppe");
				}
			}	
		}
	}
}