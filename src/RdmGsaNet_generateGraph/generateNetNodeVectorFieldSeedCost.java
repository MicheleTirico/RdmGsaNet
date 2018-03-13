package RdmGsaNet_generateGraph;

import java.util.ArrayList;

import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNetAlgo.graphToolkit;
import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNetAlgo.graphToolkit.elementTypeToReturn;
import RdmGsaNet_generateGraph.generateNetNode.interpolation;
import RdmGsaNet_generateGraph.generateNetNode.layoutSeed;

public class generateNetNodeVectorFieldSeedCost extends generateNetNodeVectorField implements generateNetNode_Inter {

	public generateNetNodeVectorFieldSeedCost(int numberMaxSeed, layoutSeed setLayoutSeed, interpolation typeInterpolation) {
		super(numberMaxSeed, setLayoutSeed, typeInterpolation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generateNodeRule(int step) {			//		System.out.println(super.getClass().getSimpleName());
		// set seed nodes 
		generateNetNode.setSeedNodes(step, numberMaxSeed, setLayoutSeed);
				
		// CREATE LIST OF SEEDGRAD 
		ArrayList<Node> listNodeSeedGrad = new ArrayList<Node> ( gsAlgoToolkit.getListNodeAttribute(netGraph, "seedGrad" , 1 ) ) ;	//	System.out.println("number of seed " + listNodeSeedGrad.size() + " " + listNodeSeedGrad);

		for ( Node nodeSeed : listNodeSeedGrad ) {
					
			String idSeed = nodeSeed.getId() ;
				
			double[] nodeCoord = GraphPosLengthUtils.nodePosition(nodeSeed) ;
					
			double[] vector = getVector(vecGraph, nodeCoord, typeInterpolation.sumVectors) ;										//c'é 	System.out.println(idSeed  + " "  + vector[0] + " " + vector[1]);
					
			String idCouldAdded = Integer.toString(netGraph.getNodeCount() );
			
			Node nodeCouldAdded = null ;							
		
			double 	xNewNode = nodeCoord[0] + vector[0] ,
					yNewNode = nodeCoord[1] + vector[1] ;
			
			generateNetNode.handleNewNodeCreation(netGraph, idCouldAdded, nodeSeed, xNewNode, yNewNode)	;
			
				
				
				
				
				
				
				
				
				
				}
					
			
	
	
	
	}
	

	@Override
	public void removeNodeRule(int step) {
		// TODO Auto-generated method stub
		
	}
	
	
}
