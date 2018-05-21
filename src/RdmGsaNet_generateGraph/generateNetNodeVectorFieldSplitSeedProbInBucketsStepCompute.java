package RdmGsaNet_generateGraph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.IdAlreadyInUseException;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNet_vectorField_02.vectorField.typeInterpolation ;
import RdmGsaNet_mainSim.*;

import RdmGsaNetAlgo.graphToolkit;
import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNetAlgo.graphToolkit.element;
import RdmGsaNetAlgo.graphToolkit.elementTypeToReturn;

import RdmGsaNet_generateGraph.generateNetNode.layoutSeed;
import RdmGsaNet_mainSim.simulation;

public class generateNetNodeVectorFieldSplitSeedProbInBucketsStepCompute extends generateNetNodeVectorField implements generateNetNode_Inter {
	
	// parameters only this class

	private boolean dieBord ;
	private double  maxInten ;
	private  int stepToCompute  ;
	// constructor
	public generateNetNodeVectorFieldSplitSeedProbInBucketsStepCompute (int numberMaxSeed, layoutSeed setLayoutSeed,
			typeInterpolation typeInterpolation, boolean createSeedGraph, boolean updateNetGraph ,
			 boolean dieBord , double maxInten , int stepToCompute ) {
		
		super(numberMaxSeed, setLayoutSeed, typeInterpolation, createSeedGraph, updateNetGraph);
		
		// parameters only this class
		this.dieBord = dieBord ;
		this.maxInten = maxInten ;	
		this.stepToCompute = stepToCompute ;
	}

	@Override
	public void generateNodeRule(int step) throws IOException {
		
//		if ( ! simulation.isStepToStore(step, main.stepToCompute )  ) 			return ;	
//			System.out.println(step);
		
		generateNetNode.setSeedNodes(step, numberMaxSeed, setLayoutSeed);
		
		generateNetNodeVectorField.handleCreateSeedGraph(createSeedGraph, step); 
	
		// list node element
		ArrayList <Node> listNodeSeed = new ArrayList<Node> ( graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.element));	//	System.out.println(listNodeSeed);	
		ArrayList<Integer> listIdSeedInt = new ArrayList<Integer>( graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.integer)) ;			
		ArrayList<Integer> listIdNetInt = new ArrayList<Integer>( graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.integer)) ;	
		
		int idNodeInt = Math.max(graphToolkit.getMaxIdIntElement(netGraph, element.node) , graphToolkit.getMaxIdIntElement(seedGraph, element.node) ) ;
		
		// map to update
		Map< Integer , double[] > mapIdNewSeedCoord = new HashMap< Integer , double[] > ();
		Map< Integer , String > mapIdNewSeedFather = new HashMap< Integer , String > ();
		
		// print
		System.out.println(seedGraph + " " + listIdSeedInt.size()  /* + " " + listIdSeedInt */ );//		System.out.println(netGraph + " " + listIdNetInt.size() + " " + listIdNetInt );
	
		int idLocal = 0 ;

		for ( Node nodeSeed :  listNodeSeed ) {
		
			String idSeed = nodeSeed.getId() ;
			
		//	bucketSet.putNode(netGraph.getNode(idSeed)) ;
			
			double[] 	nodeCoord = GraphPosLengthUtils.nodePosition(nodeSeed) ,
						vector = getVector(vecGraph, nodeCoord, typeInterpolation ) ;		
			
			// check vector
			double vectorInten = Math.pow( Math.pow(vector[0], 2) + Math.pow(vector[1], 2) , 0.5 ) ;
			
			if ( vectorInten > maxInten) {
				vector[0]  = vector[0] / vectorInten * maxInten ; 
				vector[1]  = vector[1] / vectorInten * maxInten ;		
			}
			
			// check vector in grid
			double 	xTopVector = generateNetNode.checkCoordInGrid ( gsGraph , nodeCoord[0] + vector[0] ) ,
					yTopVector = generateNetNode.checkCoordInGrid ( gsGraph , nodeCoord[1] + vector[1] ) ;						// 	System.out.println(idSeed  + " "  + vector[0] + " " + vector[1]);						
			
			double [] newNodeSeedCoord = new double[2] ;
						
			newNodeSeedCoord[0] = xTopVector ;
			newNodeSeedCoord[1] = yTopVector ;
			
			if ( dieBord ) {		
				if ( newNodeSeedCoord[0] < 1 || newNodeSeedCoord[1] < 1 || newNodeSeedCoord[0] > sizeGridEdge -1  || newNodeSeedCoord[1] > sizeGridEdge - 1  )
					continue ;
			}	
			
			ArrayList<Integer> listIdNetIntLocal = new ArrayList<Integer> ( graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.integer)) ;		//	System.out.println(listIdNetIntLocal);
			ArrayList<Integer> listIdSeedIntLocal = new ArrayList<Integer> ( graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.integer)) ;		//	System.out.println(listIdNetIntLocal);
			
			int idInt =  Collections.max(listIdNetIntLocal) ; //Math.max(listIdNetIntLocal, b) ;
			while ( listIdNetIntLocal.contains(idInt) && listIdSeedIntLocal.contains(idInt) ) 
				idInt ++ ;
			
			String id = Integer.toString(idInt) ;			//	System.out.println(idInt);
			
			try {
				netGraph.addNode(id);
				seedGraph.addNode(id);
				bucketSet.putNode(netGraph.getNode(id));
			}
			catch (org.graphstream.graph.IdAlreadyInUseException e) {
				
				listIdSeedIntLocal = new ArrayList<Integer> ( graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.integer)) ;
				listIdNetIntLocal = new ArrayList<Integer> ( graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.integer)) ;
				
				idInt = Math.max(Collections.max(listIdNetIntLocal) , Collections.max(listIdSeedIntLocal) )     ;
				idInt ++ ; 
				while ( listIdNetIntLocal.contains(idInt) &&  listIdSeedIntLocal.contains(idInt))
					idInt ++ ;
				
				id = Integer.toString(idInt) ;			//	System.out.println(idInt);
				
				netGraph.addNode(id);
				seedGraph.addNode(id);
				bucketSet.putNode(netGraph.getNode(id));			
			}
			
			Node nodeNewNet = netGraph.getNode(id);
			Node nodeNewSeed = seedGraph.getNode(id);
						
			nodeNewSeed.addAttribute("xyz", newNodeSeedCoord[0] , newNodeSeedCoord[1] , 0 );
			nodeNewSeed.addAttribute("father", idSeed );
			
			nodeNewNet.addAttribute("xyz", newNodeSeedCoord[0] , newNodeSeedCoord[1] , 0 );
			nodeNewNet.addAttribute("father", idSeed );	
			
			nodeNewNet.setAttribute("seedGrad", 1);
		}
		
		// remove old seed
		for ( int i : listIdSeedInt) //	System.out.println(i);
			seedGraph.removeNode(Integer.toString(i));	
	}
				
	@Override
	public void removeNodeRule(int step) {
		// TODO Auto-generated method stub		
	}

	
	
// GET METHODS --------------------------------------------------------------------------------------------------------------------------------------
	
}