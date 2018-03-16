package RdmGsaNet_generateGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import RdmGsaNetAlgo.graphGenerator;
import RdmGsaNetAlgo.graphToolkit;
import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNetAlgo.graphToolkit.element;
import RdmGsaNetAlgo.graphToolkit.elementTypeToReturn;
import RdmGsaNetExport.expTime;
import RdmGsaNet_generateGraph.generateNetEdge;
import dynamicGraphSimplify.dynamicSymplify_deleteNode;

public class generateNetEdgeInRadiusFather  implements generateNetEdge_Inter  {

	public enum genEdgeType { onlyFather , fatherAndNodeInRadius }
	private genEdgeType genEdgeType ;
	
	public generateNetEdgeInRadiusFather(  genEdgeType genEdgeType ) {
		this.genEdgeType =  genEdgeType ;
	}
	
	@Override
	public void generateEdgeRule(double step) {				//	long startTime = expTime.setStartTime();
		
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
			
			String idFather = mapIdForGenerateEdge.get(idNodeSeed) ;			//	System.out.println( "idNodeSeed " + idNodeSeed + " idFather " + idFather  ) ;	//	System.out.println( "granFat " + idGranFat  ) ;	//	System.out.println(nNet.getAttributeKeySet());	//	System.out.println(nSeed.getAttributeKeySet());
			
			
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
	
	public static void createEdgeOnlyFather ( Graph graph , String idNode , String idFather ) {

		Node node = graph.getNode(idNode);
		String idEdge = idNode +"-" + idFather ;	// System.out.println("idEdge " + idEdge);
		Node nodeFather = graph.getNode(idFather);
		try {
			graph.addEdge(idEdge, node , nodeFather) ;
		} catch (org.graphstream.graph.IdAlreadyInUseException | java.lang.NullPointerException e) {	
			return ;
		}	
	}

}
