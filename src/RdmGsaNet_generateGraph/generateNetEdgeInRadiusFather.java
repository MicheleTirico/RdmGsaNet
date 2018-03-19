package RdmGsaNet_generateGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNetAlgo.graphGenerator;
import RdmGsaNetAlgo.graphToolkit;
import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNetAlgo.graphToolkit.element;
import RdmGsaNetAlgo.graphToolkit.elementTypeToReturn;
import RdmGsaNetExport.expTime;
import RdmGsaNet_generateGraph.generateNetEdge;
import dynamicGraphSimplify.dynamicSymplify_deleteNode;

public class generateNetEdgeInRadiusFather  implements generateNetEdge_Inter  {

	int x = 0;
	public enum genEdgeType { onlyFather , fatherAndNodeInRadius }
	private genEdgeType genEdgeType ;
	
	public generateNetEdgeInRadiusFather(  genEdgeType genEdgeType ) {
		this.genEdgeType =  genEdgeType ;
	}
	
	@Override
	public void generateEdgeRule(double step) {				//	long startTime = expTime.setStartTime();
	
		// parameters Pivot
		boolean createPivot = generateNetEdge.getCreatePivot ( ) ;
		double maxDistPivot = generateNetEdge.getMaxDistPivot( ) ;

		Map< String , String > mapIdForGenerateEdge = new HashMap<String , String> () ;
		mapIdForGenerateEdge = dynamicSymplify_deleteNode.getMapIdForGenerateEdge();
		
		ArrayList<String> listNodeSeedGrad = new ArrayList<String> (  
			graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.string ) ) ;						//	System.out.println("listNodeSeedGrad " + listNodeSeedGrad);	System.out.println("listNodeNet " + listNodeNet);//	System.out.println(listNodeSeedGrad);
		ArrayList<String> listNodeNet = new ArrayList<String> (  
				graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.string ) ) ;
	
		for ( String  idNodeSeed : mapIdForGenerateEdge.keySet() ) {
			
			Node nNet = netGraph.getNode(idNodeSeed) ; 
			Node nSeed = seedGraph.getNode(idNodeSeed) ; 			
			
			String idFather = mapIdForGenerateEdge.get(idNodeSeed) ;	
			Node nFather = netGraph.getNode(idFather) ;
				
			switch (genEdgeType) {
			case onlyFather:
				createEdgeOnlyFather ( netGraph, idNodeSeed, idFather);
				break;
			case fatherAndNodeInRadius :
				;
				break ;
	
			default:
				break;
			}
		}	//	System.out.println("genEdge " +  expTime.getTimeMethod(startTime));
	}

	@Override
	public void removeEdgeRule(double step) {
		// TODO Auto-generated method stub
		
	}
	

// PRIVATE METHODS ----------------------------------------------------------------------------------------------------------------------------------
	public static void createEdgeOnlyFather ( Graph graph , String idNode , String idFather ) {

		Node node = graph.getNode(idNode);
		String idEdge = idNode +"-" + idFather ;	// System.out.println("idEdge " + idEdge);
		Node nodeFather = graph.getNode(idFather);
		try {
			graph.addEdge(idEdge, node , nodeFather) ;
		} catch (org.graphstream.graph.IdAlreadyInUseException | java.lang.NullPointerException e) {	//		e.printStackTrace() ;
			return ;
		}	
	}

	@Override
	public void setPivot(boolean createPivot, double maxDistPivot) {		// 	System.out.println(createPivot + " " + maxDistPivot);
		
		ArrayList<String> listNodeNet = new ArrayList<String> (  
				graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.string ) ) ;
	
		ArrayList<Integer> listCast = new ArrayList<Integer>() ;					
		listNodeNet.stream().forEach( s -> listCast.add(Integer.parseInt(s)));
		
		int idPivotInt = Collections.max(listCast) + 1;	//		System.out.println(listNodeNet);
		
		for ( Node node : netGraph.getEachNode()) {			//System.out.println(n + " " +  n.getAttributeKeySet());
			
			String idNode = node.getId() ;
			String idFather = node.getAttribute("father");
			Node nodeFather = netGraph.getNode(idFather) ;
			
			if ( idFather == null )
				continue ;
			
			double[] nodeCoord = GraphPosLengthUtils.nodePosition(node) ;
			
			double[] nodeFatherCoord = GraphPosLengthUtils.nodePosition(nodeFather) ;
						
			double 	distX = Math.abs(nodeCoord[0] - nodeFatherCoord[0] ) ,
					distY = Math.abs(nodeCoord[1] - nodeFatherCoord[1] );
						
			double distNode = Math.pow(Math.pow(distX, 2) + Math.pow(distY, 2), 0.5 ) ;	//	System.out.println(distNode);

			double[] nodePivotCoord = new double[2] ;
			
			if ( distNode >= maxDistPivot ) {		//	System.out.println(distNode);
				
				nodePivotCoord[0] = Math.min(nodeCoord[0], nodeFatherCoord[0]) + distX / 2 ;
				nodePivotCoord[1] = Math.min(nodeCoord[1], nodeFatherCoord[1]) + distY / 2 ;
				
				String idPivot = Integer.toString(idPivotInt  )  ;		//	System.out.println(netGraph.getNodeSet());
	
				netGraph.addNode(idPivot);
				Node nodePivot = netGraph.getNode(idPivot) ; 		//	System.out.println(netGraph.getNodeSet());			//	System.out.println(idPivot);//	System.out.println(nodePivotCoord[0] + " " + nodePivotCoord[1]);
				
				nodePivot.setAttribute("xyz", nodePivotCoord[0] , nodePivotCoord[1] , 0 );
				nodePivot.addAttribute("father", idFather );
				node.setAttribute("father", idPivot);
				
				idPivotInt++;
			}
			
		}		
	}

	public void setPivot2 ( boolean createPivot, double maxDistPivot , Graph graph ,  Map< String , String > mapIdForGenerateEdge) {
		
		Map <String , String > mapToMerge = new HashMap<String , String >(); 
		int idPivotInt = 0 ;
		
		if ( !createPivot  )
			return ;
		
		for ( String idNode : mapIdForGenerateEdge.keySet()) {
			
			try {
				String idFather = mapIdForGenerateEdge.get(idNode);
				Node node = graph.getNode(idNode) ; 
				Node nodeFather = graph.getNode(idFather);
				
				double[] nodeCoord = GraphPosLengthUtils.nodePosition(node) ;
				
				double[] nodeFatherCoord = GraphPosLengthUtils.nodePosition(nodeFather) ;
							
				double 	distX = Math.abs(nodeCoord[0] - nodeFatherCoord[0] ) ,
						distY = Math.abs(nodeCoord[1] - nodeFatherCoord[1] );
							
				double distNode = Math.pow(Math.pow(distX, 2) + Math.pow(distY, 2), 0.5 ) ;	//	System.out.println(distNode);
	
				double[] nodePivotCoord = new double[2] ;
				
				if ( distNode >= maxDistPivot ) {		//				System.out.println(distNode);
					
					nodePivotCoord[0] = Math.min(nodeCoord[0], nodeFatherCoord[0]) + distX / 2 ;
					nodePivotCoord[1] = Math.min(nodeCoord[1], nodeFatherCoord[1]) + distY / 2 ;

					ArrayList<String> listNodeNet = new ArrayList<String> (  
							graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.string ) ) ;

					ArrayList<Integer> listCast = new ArrayList<Integer>() ;					
					listNodeNet.stream().forEach( s -> listCast.add(Integer.parseInt(s)));

					String idPivot = Integer.toString(Collections.max(listCast) + 1 )  ;

					graph.addNode(idPivot);
					
					Node nodePivot = graph.getNode(idPivot) ;

					nodePivot.setAttribute("xyz", nodePivotCoord[0] , nodePivotCoord[1] , 0 );
					nodePivot.addAttribute("father", idFather );
					node.setAttribute("father", idPivot);
					
					mapToMerge.put(idPivot, idFather );	
					mapToMerge.put(idNode, idPivot) ;
				}
			
			} catch (java.lang.NullPointerException e) { 
				// e.printStackTrace().
			}
		}
		for ( String idNode : mapToMerge.keySet()) 
			mapIdForGenerateEdge.put(idNode, mapToMerge.get(idNode));
	}
	

}
