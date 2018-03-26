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
	
	
		Map< String , String > mapIdForGenerateEdge = new HashMap<String , String> () ;
		mapIdForGenerateEdge = dynamicSymplify_deleteNode.getMapIdForGenerateEdge();
		
	//	ArrayList<String> listNodeSeedGrad = new ArrayList<String> ( graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.string ) ) ;						//	System.out.println("listNodeSeedGrad " + listNodeSeedGrad);	System.out.println("listNodeNet " + listNodeNet);//	System.out.println(listNodeSeedGrad);
	//	ArrayList<String> listNodeNet = new ArrayList<String> ( graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.string ) ) ;
	
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
}
