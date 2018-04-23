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
import RdmGsaNet_gsAlgo.gsAlgo.reactionType;

public class seedBirth_onlySetSeed  implements seedBirt_inter {
	
	// parameters 
	private Graph 	netGraph = seedBirth.netGraph ,
					seedGraph = seedBirth.seedGraph ;

	@Override
	public void test() {	
}

	@Override
	public ArrayList<String> getListIdToSplit(double probSplit, double percBirth , int numMaxNewSeed ) {

		ArrayList < String > listIdNet = new ArrayList<String>  ( graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.string)) ;
		ArrayList < String > listIdSeed = new ArrayList<String> ( graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.string)) ;
		
		int numMaxBirth = 0 ;
		if ( numMaxNewSeed != 0 )
			numMaxBirth = numMaxNewSeed ; 
		else if ( percBirth != 0 )
			numMaxBirth = (int) (listIdNet.size() * percBirth) ;		//	System.out.println(numMaxBirth);
	
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
		
		return mapNewSeedFather ;
	}

	

	@Override
	public void connectNewSeed(Map<Node, Node> mapNewNodeFather ) {
		// TODO Auto-generated method stub
		
	}

}
