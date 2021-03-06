package RdmGsaNet_generateGraph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.graphstream.graph.Node;
import org.graphstream.stream.file.FileSinkDGS;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNetAlgo.graphToolkit;
import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNetExport.expTime;
import RdmGsaNetExport.handleNameFile;
import RdmGsaNetAlgo.graphToolkit.element;
import RdmGsaNetAlgo.graphToolkit.elementTypeToReturn;
import RdmGsaNet_generateGraph.generateNetNode.interpolation;
import RdmGsaNet_generateGraph.generateNetNode.layoutSeed;
import RdmGsaNet_mainSim.main;

public class generateNetNodeVectorFieldSeedCost extends generateNetNodeVectorField implements generateNetNode_Inter {
	
	// parameters only this class
	private int startStep ;
	
	// CONSTRUCTOR 
	public generateNetNodeVectorFieldSeedCost(int numberMaxSeed, layoutSeed setLayoutSeed, interpolation typeInterpolation , int startStep , boolean createSeedGraph , boolean updateNetGraph ) {
		super(numberMaxSeed, setLayoutSeed, typeInterpolation , createSeedGraph , updateNetGraph );
		this.startStep = startStep ;
	}

	@Override
	public void generateNodeRule(int step) throws IOException {			//		System.out.println(super.getClass().getSimpleName());	System.out.println("node count "+ seedGraph +" " + seedGraph.getNodeCount());	System.out.println("node count "+ netGraph +" " + netGraph.getNodeCount());
		
		
		// set seed nodes 
		long startTime = expTime.setStartTime();
		
		generateNetNode.setSeedNodes(step, numberMaxSeed, setLayoutSeed);
		
		generateNetNodeVectorField.handleCreateSeedGraph(createSeedGraph, step);
		
		if ( step <= startStep )
			return ; 
		
		ArrayList<Node> listNodeSeedGrad = new ArrayList<Node> () ;
		
		// CREATE LIST OF SEEDGRAD 
		if ( createSeedGraph ) 
			listNodeSeedGrad = graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.element );					//	System.out.println("createSeedGraph" );
		else																													 	//	System.out.println("iter in netGraph" );
			listNodeSeedGrad =  gsAlgoToolkit.getListNodeAttribute(netGraph, "seedGrad" , 1 )  ;									//	System.out.println("number of seed " + listNodeSeedGrad.size() + " " + listNodeSeedGrad);
			

		ArrayList<String> listNodeNet = new ArrayList<String> (  
				graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.string ) ) ;

		ArrayList<Integer> listCast = new ArrayList<Integer>() ;					
		listNodeNet.stream().forEach( s -> listCast.add(Integer.parseInt(s)));
		
		ArrayList <Integer> list = new ArrayList<Integer> () ;
		for ( Node n : listNodeSeedGrad ) {
			String id = n.getId() ;
			Integer x = Integer.parseInt(id);
			list.add(x);		
		}
		
		int idNum = Collections.max(listCast) + 1  ;		// int idNum = listNodeSeedGrad.size() *  step   ;
		
		for ( Node nodeSeed : listNodeSeedGrad ) {																					//	System.out.println(nodeSeed.getAttributeKeySet());
				
			String idSeed = nodeSeed.getId() ;
				
			double[] 	nodeCoord = GraphPosLengthUtils.nodePosition(nodeSeed) ,
						vector = getVector(vecGraph, nodeCoord, typeInterpolation ) ;										// 	System.out.println(idSeed  + " "  + vector[0] + " " + vector[1]);				

			String idCouldAdded = Integer.toString(idNum ); 			//	String idCouldAdded = Integer.toString(netGraph.getNodeCount() );
			Node nodeCouldAdded = null ;							
			
			double 	xNewNode = generateNetNode.ceckCoordInGrid ( gsGraph , nodeCoord[0] + vector[0] ) ,
					yNewNode = generateNetNode.ceckCoordInGrid ( gsGraph , nodeCoord[1] + vector[1] ) ;
			
			if (updateNetGraph ) 
				generateNetNode.handleNewNodeCreation(netGraph, idCouldAdded, nodeSeed, xNewNode, yNewNode , true  )	;
			
			if ( createSeedGraph ) {
				nodeSeed.setAttribute( "xyz", xNewNode , yNewNode, 0 );
			}
			idNum ++ ;	
		}	//		System.out.println(netGraph.getNodeCount());	//	System.out.println("genNode " + expTime.getTimeMethod(startTime));
	}
	
	@Override
	public void removeNodeRule(int step) {
		// TODO Auto-generated method stub
		
	}
	
	
}
