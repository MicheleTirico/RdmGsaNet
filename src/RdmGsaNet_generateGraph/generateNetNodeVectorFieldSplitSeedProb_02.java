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

import RdmGsaNetAlgo.graphToolkit;
import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNetAlgo.graphToolkit.element;
import RdmGsaNetAlgo.graphToolkit.elementTypeToReturn;
import RdmGsaNet_generateGraph.generateNetNode.interpolation;
import RdmGsaNet_generateGraph.generateNetNode.layoutSeed;

public class generateNetNodeVectorFieldSplitSeedProb_02 extends generateNetNodeVectorField implements generateNetNode_Inter {

	
	ArrayList  <Node> listIdNewSeedNodes = new ArrayList<Node> ( ) ;
	
	
	
	// parameters only this class
	private double prob ;
	private double angleVectorNewSeed ;
	private boolean stillAlive ;
	private double radians ;
	

	
	public generateNetNodeVectorFieldSplitSeedProb_02(int numberMaxSeed, layoutSeed setLayoutSeed,
			interpolation typeInterpolation, boolean createSeedGraph, boolean updateNetGraph ,
			double prob , double angleVectorNewSeed  , boolean stillAlive) {
		
		super(numberMaxSeed, setLayoutSeed, typeInterpolation, createSeedGraph, updateNetGraph);
		
		this.prob = prob ;
		this.angleVectorNewSeed  = angleVectorNewSeed  ;
		this.stillAlive = stillAlive ;
		
		radians = Math.toRadians(angleVectorNewSeed) ; 
	}

	@Override
	public void generateNodeRule(int step) throws IOException {
		
		System.out.println(seedGraph.getNodeCount());
		System.out.println(netGraph.getNodeCount());
		generateNetNode.setSeedNodes(step, numberMaxSeed, setLayoutSeed);
		
		generateNetNodeVectorField.handleCreateSeedGraph(createSeedGraph, step); 
		
		ArrayList <Node> listNodeSeed = new ArrayList<Node> ( graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.element));	//	System.out.println(listNodeSeed);
	
		ArrayList <Node> listNodeNet = new ArrayList<Node> ( graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.element));		//	System.out.println(listNodeNet);
		
		ArrayList <String> listIdNet = new ArrayList<String> ( graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.string));	//	System.out.println(listNodeNet);
		
		ArrayList<Integer> listIdNetInt = new ArrayList<Integer>() ;			
		listIdNet.stream().forEach( s -> listIdNetInt.add(Integer.parseInt(s)));
		
		Map<String, double[] > mapIdNewSeedCoord = new HashMap<String, double[] >();
			
		int idLocal = 0 ;

		for ( Node nodeSeed :  listNodeSeed ) {
				
			String idSeed = nodeSeed.getId() ;
			
			double[] 	nodeCoord = GraphPosLengthUtils.nodePosition(nodeSeed) ,
						vector = getVector(vecGraph, nodeCoord, typeInterpolation ) ;		
			
			double 	xTopVector = generateNetNode.ceckCoordInGrid ( gsGraph , nodeCoord[0] + vector[0] ) ,
					yTopVector = generateNetNode.ceckCoordInGrid ( gsGraph , nodeCoord[1] + vector[1] ) ;
			
			// 	System.out.println(idSeed  + " "  + vector[0] + " " + vector[1]);				

			String idNewNodeSeed ;
			Node newLocalNode = null ;
			
			double [] newNodeSeedCoord = new double[2] ;
			
			int numberMaxNewSeed = 2 ;
			int numberNewSeed = gsAlgoToolkit.getBinomial(numberMaxNewSeed, prob);	
			
			if ( stillAlive ) 
				if ( numberNewSeed == 0)
					numberNewSeed = 1 ;	

			if ( numberNewSeed == 1 ) {
			
				newNodeSeedCoord[0] = xTopVector ;
				newNodeSeedCoord[1] = yTopVector ;
				
				mapIdNewSeedCoord.put(Integer.toString(idLocal), newNodeSeedCoord) ;
				idLocal++ ;
			
				}
			
			else if ( numberNewSeed > 1) {
				continue;
			}
			
		
			
		}
		System.out.println(mapIdNewSeedCoord);

		int idToAddInt = Collections.max(listIdNetInt) + 1 ;
		
		for ( String idLocalMap : mapIdNewSeedCoord.keySet()) {
			System.out.println(idToAddInt) ; 
			seedGraph.addNode(Integer.toString(idToAddInt));
			
			idToAddInt++ ;
		}

		mapIdNewSeedCoord.clear();
		
	}

	@Override
	public void removeNodeRule(int step) {
		// TODO Auto-generated method stub
		
	}
}