package dynamicGraphSimplify;

import org.graphstream.graph.Graph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNetAlgo.graphToolkit;
import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNet_generateGraph.generateNetEdgeInRadiusFather;
import RdmGsaNet_mainSim.main;
import RdmGsaNetAlgo.graphToolkit.element;
import RdmGsaNetAlgo.graphToolkit.elementTypeToReturn;
import dynamicGraphSimplify.dynamicSymplify.simplifyType ;


public class dynamicSymplify_deleteNode  implements dynamicSymplify_inter {

	private Graph netGraph  = new SingleGraph("netGraph ") ;
	private static Map< String , String > mapIdForGenerateEdge = new HashMap<String , String> () ; 
	private double 	epsilon ;
	
	// constructor 
	public dynamicSymplify_deleteNode( Graph netGraph , double epsilon ) {
		this.netGraph  = netGraph  ;
		this.epsilon = epsilon; 
	}

	@Override
	public void test() {
		System.out.println(super.toString());
	}

	
	@Override
	public void updateFatherAttribute(int step , Map<String, String> mapFather ) {	// System.out.println("nodeCount " +graph.getNodeCount());		System.out.println("epsilon " + epsilon);
			
		for ( Node n : netGraph .getEachNode() ) {
			
			String father = n.getAttribute("father") ;
			mapFather.put(n.getId(), father) ;
			
			String granFat = mapFather.get(father );		
			n.addAttribute("granFat", granFat);				
		}	
	}

	@Override
	public void handleGraphGenerator (int step ) {
		ArrayList<String> listNodeNet = new ArrayList<String> (  
				graphToolkit.getListElement(netGraph , element.node, elementTypeToReturn.string ) )  ;			// System.out.println("listNodeSeedGrad " + listNodeNet);
	
		mapIdForGenerateEdge.clear();
				
		for ( Node node : netGraph.getEachNode() ) {																//	System.out.println(n.getId() + n.getAttributeKeySet());
			
			String 	id = node.getId()	,	
					idFather = node.getAttribute("father");
			
			Node nFat = netGraph.getNode(idFather) ;
			
			if ( idFather == null )
				continue ; 
			try {
			
				while ( !listNodeNet.contains(idFather)) 	
					idFather = nFat.getAttribute("father");
				
				String idGranFat = nFat.getAttribute("father");
				Node nGranFat = netGraph.getNode(idGranFat);
				
				if ( idGranFat == null )	
					continue ;
				
				while ( !listNodeNet.contains(idGranFat)) 
					idGranFat= nGranFat.getAttribute("father");													//	System.out.println( "idNodeSeed " + n.getId() + " idFather " + idFather + " granFat " + idGranFat  ) ;
	
				double dist = graphToolkit.getDistNodeEdge(nGranFat, nFat, node, true);							//	System.out.println("dist " + dist);		System.out.println("epsilon " + epsilon );
				
				if ( dist > epsilon )
					continue;
				
				else if ( dist < epsilon  ) {																	//	System.out.println("dist " + dist);	
					netGraph.removeNode(nFat);
					node.setAttribute("father", idGranFat);
					idFather = idGranFat ;																		//	System.out.println( "idNodeSeed " + n.getId() + " idFather " + idFather + " granFat " + idGranFat  ) ;				
				}																								//	System.out.println(n.getId() + " " + idFather );
		
		
				//	generateNetEdgeInRadiusFather.createEdgeOnlyFather(graph, n.getId(), idFather);	
				getMapIdForGenerateEdge().put(id, idFather);	
			}
			catch (java.lang.NullPointerException e) {		}
		}
	}	
	

		
		
// GET AND SET METHODS ------------------------------------------------------------------------------------------------------------------------------	

	public static Map< String , String > getMapIdForGenerateEdge() {
		return mapIdForGenerateEdge;
	}
	public void setMapIdForGenerateEdge(Map< String , String > mapIdForGenerateEdge) {
		this.mapIdForGenerateEdge = mapIdForGenerateEdge;
	}

	

}
