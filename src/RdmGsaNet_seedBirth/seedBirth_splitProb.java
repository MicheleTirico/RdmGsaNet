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

public class seedBirth_splitProb implements seedBirt_inter {
	

	// parameters 
	private Graph 	netGraph = seedBirth.netGraph ,
					seedGraph = seedBirth.seedGraph ;
	
	@Override
	public void test() {	
		System.out.println(super.getClass().getSimpleName());		
	}
	

	@Override
	public ArrayList<String> getListIdToSplit ( double probSplit , double percBirth ) {		
	
		ArrayList < String > listIdNet = new ArrayList<String>  ( graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.string)) ;
		ArrayList < String > listIdSeed = new ArrayList<String> ( graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.string)) ;
		
		int numMaxBirth = (int) (listIdNet.size() * percBirth) ;		//	System.out.println(numMaxBirth);
	
		ArrayList <String> listIdToSplit = new ArrayList<String> () ;
		
		for ( String id : listIdNet ) {
			
			Random rnd = new Random() ;		
			int index = rnd.nextInt(listIdNet.size() ) ;	
			String randomId = listIdNet.get(index);		
			int degree = netGraph.getNode(randomId).getDegree();	//	System.out.println(degree);
			
			if ( ! listIdToSplit.contains(randomId) && degree == 2 && ! listIdSeed.contains(randomId) )
				listIdToSplit.add(randomId);
			
			if ( listIdToSplit.size() >= numMaxBirth )
				break ; 			
		}		
		return listIdToSplit;	
	}


	@Override
	public Map <Node , Node > createNewSeed(ArrayList<String> listIdToSplit, double dist ) {
		
		Map <Node , Node > mapNewSeedFather = new HashMap<Node, Node> ( ) ;
		
		String idNewNode , idEdge;
		
		for ( String id : listIdToSplit ) {
			
			int idNewNodeInt = graphToolkit.getMaxIdIntElement(netGraph, element.node) ;
			
			Node n0 = netGraph.getNode(id) ;	//	System.out.println(n0.getAttributeKeySet());
			
			ArrayList<Node> listNeig = new ArrayList<Node> ( graphToolkit.getListNeighbor(netGraph, id , elementTypeToReturn.element) ) ;
			
			Node n1 = listNeig.get(0) ;
			Node n2 = listNeig.get(1) ;				
		
			double[] 	coordN1 = GraphPosLengthUtils.nodePosition(n1) , 	
						coordN2 = GraphPosLengthUtils.nodePosition(n2) ,
						coordN0 = GraphPosLengthUtils.nodePosition(n0) ,
						coordNewSeed = new double[3];
			
			double 	coefAng = ( coordN2[1] - coordN1[1] )  / ( coordN2[0] - coordN1[0] )  ,
					coefAngPerp = - 1 / coefAng ,
					x , y ;		//	System.out.println("coefAng " + coefAng) ;	//	System.out.println("coefAngPerp " + coefAngPerp);			
			
			boolean signBol = new Random().nextBoolean() ;
			int sign ; 
			
			if ( signBol )
				sign = 1 ;
			else 
				sign = - 1 ;		//			System.out.println(sign);
			
			x = sign * dist * coefAngPerp *  Math.pow(        1 /  Math.pow( coefAngPerp ,2) +1            , 0.5) + coordN0[0]   ;
			y =  1 / coefAngPerp * ( x - coordN0[0]) + coordN0[1] ;		//	System.out.println("x " + x ) ;		//	System.out.println("y " + y );
					
			coordNewSeed[0] = x ;
			coordNewSeed[1] = y ;
			coordNewSeed[2] = 0 ;
			
			// add nodes
			idNewNode = Integer.toString(idNewNodeInt);

			Node newNodeSeed = seedGraph.addNode(idNewNode) ;
			Node newNodeNet = netGraph.addNode(idNewNode) ;
		
			newNodeSeed.setAttribute("xyz", coordNewSeed[0], coordNewSeed[1] , 0);
			newNodeNet.setAttribute("xyz", coordNewSeed[0], coordNewSeed[1] , 0);
		
			mapNewSeedFather.put(newNodeNet, n0) ;
	
			idNewNodeInt ++ ;
		}
		return mapNewSeedFather;
	}


	@Override
	public void connectNewSeed(Map<Node, Node> mapNewNodeFather) {
		
		int idEdgeInt = graphToolkit.getMaxIdIntElement(netGraph, element.edge) ; 
		String idEdge ;
		for ( Node newNode : mapNewNodeFather.keySet()) {
			
			idEdgeInt = graphToolkit.getMaxIdIntElement(netGraph, element.edge) ; 
			idEdge = Integer.toString(idEdgeInt) ;
			
			netGraph.addEdge(idEdge, newNode , mapNewNodeFather.get(newNode)) ;
			idEdgeInt++ ;
		}
		
	}
	
	
	
}
