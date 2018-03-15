package RdmGsaNet_generateGraph;

import java.util.ArrayList;

import org.graphstream.graph.Node;

import RdmGsaNetAlgo.graphGenerator;
import RdmGsaNetAlgo.graphToolkit;
import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNetAlgo.graphToolkit.element;
import RdmGsaNetAlgo.graphToolkit.elementTypeToReturn;
import RdmGsaNetExport.expTime;
import RdmGsaNet_generateGraph.generateNetEdge;

public class generateNetEdgeInRadiusFather  implements generateNetEdge_Inter  {

	public enum genEdgeType { onlyFather , fatherAndNodeInRadius }
	private genEdgeType genEdgeType ;
	
	public generateNetEdgeInRadiusFather(  genEdgeType genEdgeType ) {
		this.genEdgeType =  genEdgeType ;
	}
	
	@Override
	public void generateEdgeRule(double step) {				//	long startTime = expTime.setStartTime();
		
		ArrayList<Node> listNodeSeedGrad = new ArrayList<Node> (  graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.element ) ) ;
//		listNodeSeedGrad = gsAlgoToolkit.getListNodeAttribute(netGraph, "seedGrad" , 1 )  ;	//	
//		System.out.println("number of seed " + listNodeSeedGrad.size() + " " + listNodeSeedGrad);	
			
		for ( Node nodeSeed : listNodeSeedGrad ) {
			
		//	System.out.println("seedGraph " + nodeSeed.getId() + " " + nodeSeed.getAttributeKeySet() + " " + nodeSeed.getAttribute("father"));
			String idFather = nodeSeed.getAttribute("father") ;					//		System.out.println(nodeSeed.getId() + " " + idFather );
			
			Node nodeFather = netGraph.getNode(idFather) ;	 					//			System.out.println(nodeFather.getAttributeKeySet());
			Node nodeSoon = netGraph.getNode(nodeSeed.getId());
			switch (genEdgeType) {
			case onlyFather:
			//	createEdgeOnlyFather(  nodeSoon , nodeFather );
				break;
			case fatherAndNodeInRadius :
				;
				break ;
	
			default:
				break;
			}
		}
	//	System.out.println("genEdge " +  expTime.getTimeMethod(startTime));
	}

	@Override
	public void removeEdgeRule(double step) {
		// TODO Auto-generated method stub
		
	}
	
	private void createEdgeOnlyFather ( Node nodeSeed , Node nodeFather ) {
//		System.out.println(nodeFather.getAttributeKeySet());	
		graphGenerator.createEdge(netGraph , nodeSeed, nodeFather);  //	
		
	}

}
