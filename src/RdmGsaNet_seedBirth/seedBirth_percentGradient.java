package RdmGsaNet_seedBirth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNetAlgo.graphToolkit;
import RdmGsaNetAlgo.graphToolkit.element;
import RdmGsaNetAlgo.graphToolkit.elementTypeToReturn;
import RdmGsaNet_generateGraph.generateNetNode.interpolation;
import RdmGsaNet_mainSim.main;
import RdmGsaNet_seedBirth.seedBirth.choiceNodeType;
import RdmGsaNet_vectorField_02.vectorField;
import RdmGsaNet_vectorField_02.vectorField.typeInterpolation;

public class seedBirth_percentGradient implements seedBirt_inter  {

	// parameters 
	private Graph 	netGraph = seedBirth.netGraph ,
					seedGraph = seedBirth.seedGraph ,
					vecGraph = main.getVecGraph() ;
	
	private vectorField vectorField = main.getVectorField();

	@Override
	public void test() {
		// TODO Auto-generated method stub	
	}

	@Override
	public ArrayList<String> getListIdToSplit ( double probSplit , double percBirth , int numMaxNewSeed ) {
		
		ArrayList < String > listIdNet = new ArrayList<String>  ( graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.string)) ;
		ArrayList < String > listIdSeed = new ArrayList<String> ( graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.string)) ;
		
		int numMaxBirth = (int) (listIdNet.size() * percBirth) ;		//	System.out.println(numMaxBirth);
	
		return new ArrayList<String> (computeList(listIdNet, listIdSeed, numMaxBirth, seedBirth.getChoiceNodeType())) ;
	}

	@Override
	public Map<Node, Node> createNewSeed(ArrayList<String> listIdToSplit, double dist) {
		
		Map <Node , Node > mapNewSeedFather = new HashMap<Node, Node> ( ) ;
		String idNewSeed ; 
		
		for ( String id : listIdToSplit ) {
			
			int idNewSeedInt = graphToolkit.getMaxIdIntElement(seedGraph, element.node) ;
			
			Node nodeNet = netGraph.getNode(id)  ;		//	System.out.println(nodeNet.getAttributeKeySet());
		
			double [] coord =  GraphPosLengthUtils.nodePosition(nodeNet) ;
			
			idNewSeed = Integer.toString(idNewSeedInt ) ;
			
 			seedGraph.addNode(id) ;
 			Node newSeed = seedGraph.getNode(id) ;
 			newSeed.setAttribute("xyz", coord[0] , coord[1] , 0 );
 			
 			idNewSeedInt++ ;		
		}
		
		return mapNewSeedFather;
	}

	@Override
	public void connectNewSeed( Map<Node, Node> mapNewNodeFather ) {
		// TODO Auto-generated method stub
		
	}

// private methods ----------------------------------------------------------------------------------------------------------------------------------
	
	private ArrayList <String> computeList ( ArrayList<String> listIdNet , ArrayList < String > listIdSeed , int numMaxBirth , choiceNodeType choiceNodeType ) {
		
		ArrayList <String> listIdToSplit = new ArrayList<String> () ;
	
		switch (choiceNodeType) {
		
		case maxInten: 
			maxIntenCompute(listIdToSplit, listIdNet, listIdSeed, numMaxBirth, choiceNodeType);
			break;
		
		case ortoAngleVector : 
		
			ortoAngleVectorComputeIterRandom (listIdToSplit, listIdNet, listIdSeed, numMaxBirth, choiceNodeType);
			break ;
		}
		
		return listIdToSplit ;
	}
	
	private void ortoAngleVectorComputeIterRandom  ( ArrayList <String> listIdToSplit , ArrayList<String> listIdNet , ArrayList < String > listIdSeed , int numMaxBirth , choiceNodeType choiceNodeType ) {	//	System.out.println(numMaxBirth);
		
		for ( String id : listIdNet ) {
			
			Random rnd = new Random() ;		
			int index = rnd.nextInt(listIdNet.size() ) ;	
			String randomId = listIdNet.get(index);		
			int degree = netGraph.getNode(randomId).getDegree();	
			
			if ( degree != 2 )
				continue ;
		
			// get angle 
			double[] nodeCoord = GraphPosLengthUtils.nodePosition( netGraph.getNode(randomId) ) ;
			double angleVector = vectorField.getAngleVectorInterpolate(vecGraph, nodeCoord, typeInterpolation.sumVectors) ;	//		System.out.println(angleNode) ;
			
		//	if ( angleNode < 0 )			angleNode = Math.PI  - Math.abs( angleNode ) ;			
			
			ArrayList<String> listNeig = new ArrayList<String> ( graphToolkit.getListNeighbor(netGraph, randomId, elementTypeToReturn.string) ) ;
			
			String 	idNeig0 = listNeig.get(0) ,
					idNeig1 = listNeig.get(1);
			
			double[] nodeCoordNeig0 = GraphPosLengthUtils.nodePosition( netGraph.getNode(idNeig0) ) ,	
					 nodeCoordNeig1 = GraphPosLengthUtils.nodePosition( netGraph.getNode(idNeig1) ) ;
					
			double dist = Math.pow(  Math.pow(nodeCoordNeig1[0] - nodeCoordNeig0[0] , 2) + Math.pow(nodeCoordNeig1[1] - nodeCoordNeig0[1], 2 ) , 0.5 ) ;	//		System.out.println();	//		System.out.println(dist);//		System.out.println(nodeCoordNeig1[0] - nodeCoordNeig0[0]) ; 
		
			double sinAngle =  ( nodeCoordNeig1[1] - nodeCoordNeig0[1] ) / dist ;	
			double cosAngle =  ( nodeCoordNeig1[0] - nodeCoordNeig0[0] ) / dist ;	

			double angleNeig =  0.0 ; 
			
			if ( sinAngle >= 0 && cosAngle >= 0 ) 
				angleNeig = Math.asin( Math.abs(sinAngle) ) ; 
			
			else if ( sinAngle >= 0 && cosAngle <= 0 ) 
				angleNeig =  Math.PI - Math.asin( Math.abs(sinAngle) ) ; 
			
			else if ( sinAngle <= 0 && cosAngle <= 0 ) 
				angleNeig = Math.PI + Math.asin( Math.abs(sinAngle) ) ; 
			
			else if ( sinAngle <= 0 && cosAngle >= 0 ) 
				angleNeig = Math.PI * 2 - Math.asin( Math.abs(sinAngle) ) ; 
			
			double angleTest = seedBirth.angleTest ;	//	System.out.println(angleTest) ; 
		
			if (  Math.max ( angleNeig , angleVector ) - Math.min ( angleNeig , angleVector ) >= Math.PI / 2 - angleTest &&
					Math.max ( angleNeig , angleVector ) - Math.min ( angleNeig , angleVector ) <= Math.PI / 2 + angleTest )  {
				if ( ! listIdToSplit.contains(randomId) && degree == 2 && ! listIdSeed.contains(randomId) ) {
					listIdToSplit.add(randomId);			//		System.out.println( angleTest ) ; 		//		System.out.println(randomId + " " +  angleVector  + " " +  angleNeig );
				}
			}
			
			if ( listIdToSplit.size() >= numMaxBirth )		
				break ; 					
		}
	}
	
	private void maxIntenCompute (ArrayList <String> listIdToSplit , ArrayList<String> listIdNet , ArrayList < String > listIdSeed , int numMaxBirth , choiceNodeType choiceNodeType ) {
		
		for ( String id : listIdNet ) {
			
			Random rnd = new Random() ;		
			int index = rnd.nextInt(listIdNet.size() ) ;	
			String randomId = listIdNet.get(index);		
			int degree = netGraph.getNode(randomId).getDegree();	//	System.out.println(degree);
			
			double[] nodeCoord = GraphPosLengthUtils.nodePosition( netGraph.getNode(randomId) ) ;
			double [] vector = vectorField.getVectorInterpolate(vecGraph, nodeCoord, typeInterpolation.sumVectors) ;
		
			double vectorInten = Math.pow( Math.pow(vector[0], 2) + Math.pow(vector[1], 2) , 0.5 ) ;
			ArrayList<String> listNeig = new ArrayList<String> ( graphToolkit.getListNeighbor(netGraph, randomId, elementTypeToReturn.string) ) ;
				
			double intenMaxNeig = 0.0 ;
			
			for ( String idNeig : listNeig ) {
				
				double[] nodeCoordNeig = GraphPosLengthUtils.nodePosition( netGraph.getNode(idNeig) ) ;
				double [] vectorNeig = vectorField.getVectorInterpolate(vecGraph, nodeCoordNeig, typeInterpolation.sumVectors) ;
			
				double vectorIntenNeig = Math.pow( Math.pow(vectorNeig[0], 2) + Math.pow(vectorNeig[1], 2) , 0.5 ) ;
				
				if ( vectorIntenNeig > vectorInten)
					continue ;
				else 
					intenMaxNeig = vectorIntenNeig ;
			}
			
			if ( ! listIdToSplit.contains(randomId) && degree == 2 && ! listIdSeed.contains(randomId) && vectorInten > intenMaxNeig )
				listIdToSplit.add(randomId);
			
			if ( listIdToSplit.size() >= numMaxBirth )
				break ; 			
		}
	}
	
	
}
