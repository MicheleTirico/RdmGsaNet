package RdmGsaNet_generateGraph;

import java.util.ArrayList;
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
				graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.string ) ) ;						//	System.out.println("listNodeSeedGrad " + listNodeSeedGrad);	System.out.println("listNodeNet " + listNodeNet);
		
		ArrayList<String> listNodeNet = new ArrayList<String> (  
				graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.string ) ) ;

		ArrayList<String> listNodeNetSeedAttr = new ArrayList<String> ( 
				graphToolkit.getListElementAttribute(netGraph, element.node, elementTypeToReturn.string, "seedGrad", 1 ) );	//	System.out.println("listNodeNetSeedAttr " + listNodeNetSeedAttr);

		for ( String  idNodeSeed : mapIdForGenerateEdge.keySet() ) {
			
			Node nNet = netGraph.getNode(idNodeSeed) ; 
			Node nSeed = seedGraph.getNode(idNodeSeed) ; 
			
			String idFather = mapIdForGenerateEdge.get(idNodeSeed) ;			//	
			System.out.println( "idNodeSeed " + idNodeSeed + " idFather " + idFather  ) ;	//	
			//	System.out.println(nNet.getAttributeKeySet());	//	System.out.println(nSeed.getAttributeKeySet());
			
			
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
		//	System.out.println( "idNodeSeed " + idNodeSeed + " idFather " + idFather  ) ;
			
		 setPivot( createPivot, maxDistPivot , netGraph , idNodeSeed , idFather  ) ;	

		 System.out.println(netGraph.getNodeSet());
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
		} catch (org.graphstream.graph.IdAlreadyInUseException | java.lang.NullPointerException e) {	//	e.printStackTrace() ;
			return ;
		}	
	}

	
	@Override
	public void setPivot(boolean createPivot, double maxDistPivot , Graph graph , String idNode , String idFather   ) {		// System.out.println(createPivot  + " " + maxDistPivot);
		
		// exit method
		if ( !createPivot  )
			return ;
		try {
			Node node = graph.getNode(idNode);
			Node nodeFather = graph.getNode(idFather);	//	System.out.println( "idNodeSeed " + node.getId() + " idFather " + nodeFather.getId()  ) ;
			
			double[] nodeCoord = GraphPosLengthUtils.nodePosition(node) ;
						
			double[] nodeFatherCoord = GraphPosLengthUtils.nodePosition(nodeFather) ;
						
			double 	distX = Math.abs(nodeCoord[0] - nodeFatherCoord[0] ) ,
					distY = Math.abs(nodeCoord[1] - nodeFatherCoord[1] );
						
			double distNode = Math.pow(Math.pow(distX, 2) + Math.pow(distY, 2), 0.5 ) ;	//	System.out.println(distNode);
		
			double[] nodePivotCoord = null ;
						
			if ( distNode < maxDistPivot )
				return; 
			else if ( distNode >= maxDistPivot ) {
				
				System.out.println(distNode);
							
				nodePivotCoord[0] = Math.min(nodeCoord[0], nodeFatherCoord[0]) + distX / 2 ;
				nodePivotCoord[1] = Math.min(nodeCoord[1], nodeFatherCoord[1]) + distY / 2 ;
							
						//	int x = 1  ;
						//	int idNodeInt = Integer.parseInt(node.getId()) + x ;
							
				String idPivot = "pivot_"  ;
							
							graph.addNode("gino");
							
							
//							Node nodePivot = graph.getNode(idPivot) ;
							
//							nodePivot.setAttribute("xyz", nodePivotCoord[0] , nodePivotCoord[1] , 0 );
//							nodePivot.addAttribute("father", nodeFather.getId());
//							node.setAttribute("father", idPivot);
							
						}
			
			
		}catch (java.lang.NullPointerException e) {
			 return ;
		}		
	}


}
