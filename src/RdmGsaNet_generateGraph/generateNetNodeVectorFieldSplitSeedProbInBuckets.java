package RdmGsaNet_generateGraph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNet_vectorField_02.vectorField.typeInterpolation ;
import RdmGsaNetAlgo.graphToolkit;
import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNetAlgo.graphToolkit.element;
import RdmGsaNetAlgo.graphToolkit.elementTypeToReturn;

import RdmGsaNet_generateGraph.generateNetNode.layoutSeed;

public class generateNetNodeVectorFieldSplitSeedProbInBuckets extends generateNetNodeVectorField implements generateNetNode_Inter {
	
	// parameters only this class
	private static double prob ;
	private double angleVectorNewSeed ;
	private boolean stillAlive ;
	private double radians ;
	private double coefInten , maxInten ;
	
	// constructor
	public generateNetNodeVectorFieldSplitSeedProbInBuckets (int numberMaxSeed, layoutSeed setLayoutSeed,
			typeInterpolation typeInterpolation, boolean createSeedGraph, boolean updateNetGraph ,
			double prob , double angleVectorNewSeed  , boolean stillAlive , double coefInten , double maxInten) {
		
		super(numberMaxSeed, setLayoutSeed, typeInterpolation, createSeedGraph, updateNetGraph);
		
		// parameters only this class
		this.prob = prob ;
		this.angleVectorNewSeed  = angleVectorNewSeed  ;
		this.stillAlive = stillAlive ;
		this.coefInten = coefInten ;
		this.maxInten = maxInten ;
		
		radians = Math.toRadians(angleVectorNewSeed) ; 	
	}

	@Override
	public void generateNodeRule(int step) throws IOException {
		
		generateNetNode.setSeedNodes(step, numberMaxSeed, setLayoutSeed);
		
		generateNetNodeVectorField.handleCreateSeedGraph(createSeedGraph, step); 
	
		// list node element
		ArrayList <Node> listNodeSeed = new ArrayList<Node> ( graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.element));	//	System.out.println(listNodeSeed);	
		ArrayList<Integer> listIdSeedInt = new ArrayList<Integer>( graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.integer)) ;			
		
		// map to update
		Map< Integer , double[] > mapIdNewSeedCoord = new HashMap< Integer , double[] > ();
		Map< Integer , String > mapIdNewSeedFather = new HashMap< Integer , String > ();
		
		// print
		System.out.println(seedGraph + " " + listIdSeedInt.size()  /* + " " + listIdSeedInt */ );//		System.out.println(netGraph + " " + listIdNetInt.size() + " " + listIdNetInt );
	
		int idLocal = 0 ;

		for ( Node nodeSeed :  listNodeSeed ) {
				
			String idSeed = nodeSeed.getId() ;
			
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
			
			int numberMaxNewSeed = 2 ;
			int numberNewSeed = gsAlgoToolkit.getBinomial(numberMaxNewSeed, prob);	
			
			if ( stillAlive ) 
				if ( numberNewSeed == 0)
					numberNewSeed = 1 ;	

			if ( numberNewSeed == 1 ) {
			
				newNodeSeedCoord[0] = xTopVector ;
				newNodeSeedCoord[1] = yTopVector ;
				
				mapIdNewSeedCoord.put(idLocal, newNodeSeedCoord) ;
				mapIdNewSeedFather.put(idLocal, idSeed) ;
				idLocal++ ;				
			}
			
			else if ( numberNewSeed > 1 ) {		//	System.out.println();	//	System.out.println("node " + nodeCoord[0] + " " + nodeCoord[1] );	//	System.out.println("vector " + vector[0] + " " + vector[1] );
				
				double[] newNodesCoord = new double [2] ;
				
				newNodesCoord = generateNetNodeVectorField.getNewCordAngle( coefInten , nodeCoord , vector , radians , 0.1 );
				
				mapIdNewSeedCoord.put(idLocal, newNodesCoord) ;
				mapIdNewSeedFather.put(idLocal, idSeed) ;							//		System.out.println("node 1 " + newNodesCoord[0] + " " + newNodesCoord[1] );
				
				idLocal++ ;
				
				newNodesCoord = generateNetNodeVectorField.getNewCordAngle( coefInten ,  nodeCoord , vector , - radians , 0.1 );
				
				mapIdNewSeedCoord.put(idLocal, newNodesCoord) ;
				mapIdNewSeedFather.put(idLocal, idSeed) ;							//		System.out.println("node 1 " + newNodesCoord[0] + " " + newNodesCoord[1] );
				
				idLocal++ ;
			}			
		}																			//	for ( int i : mapIdNewSeedCoord.keySet()) {		System.out.println(i + " " + mapIdNewSeedCoord.get(i)[0] + " " + mapIdNewSeedCoord.get(i)[1]);		}
	
	for ( Node n : netGraph.getEachNode() )
		n.setAttribute("seedGrad", 0 );
	
	// update father and coord to seedGraph and netGraph
	for ( int i: mapIdNewSeedCoord.keySet() ) {
		
		ArrayList<Integer> listIdNetIntLocal = new ArrayList<Integer> ( graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.integer)) ;		//	System.out.println(listIdNetIntLocal);
		ArrayList<Integer> listIdSeedIntLocal = new ArrayList<Integer> ( graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.integer)) ;		//	System.out.println(listIdNetIntLocal);
		
		int idInt =  Collections.max(listIdNetIntLocal) ; //Math.max(listIdNetIntLocal, b) ;
		while ( listIdNetIntLocal.contains(idInt) && listIdSeedIntLocal.contains(idInt) ) 
			idInt ++ ;
		
		String id = Integer.toString(idInt) ;			//	System.out.println(idInt);
		try {
			netGraph.addNode(id);
			seedGraph.addNode(id);
			bucketSet.putNodeInBucketSet(netGraph.getNode(id) ) ;	
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
			bucketSet.putNodeInBucketSet(netGraph.getNode(id) ) ;	
		}
		
		Node nodeNet = netGraph.getNode(id);
		Node nodeSeed = seedGraph.getNode(id);
		
		double[ ] coord = mapIdNewSeedCoord.get(i);
		String father = mapIdNewSeedFather.get(i);
		
		nodeSeed.addAttribute("xyz", coord[0] , coord[1] , 0 );
		nodeSeed.addAttribute("father", father );
		
		nodeNet.addAttribute("xyz", coord[0] , coord[1] , 0 );
		nodeNet.addAttribute("father", father );	
		
		nodeNet.setAttribute("seedGrad", 1);
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
	
	public static double getProb() 	{ 
		return prob; 	
	}
	
}