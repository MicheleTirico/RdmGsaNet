package RdmGsaNetAlgo;

import java.util.ArrayList;
import java.util.Random;

import org.graphstream.algorithm.Kruskal;
import org.graphstream.algorithm.generator.BarabasiAlbertGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.GridGenerator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import RdmGsaNetAlgo.graphToolkit.element;
import RdmGsaNetAlgo.graphToolkit.elementTypeToReturn;

public class graphGenerator {
	
	// create grid
	public static void createGraphGrid ( Graph graph , int size , boolean type ) {
				
		Generator gsGen = new GridGenerator(type , false);
		gsGen.addSink(graph);
		gsGen.begin();
		
		for(int i = 0 ; i < size ; i++)  	
			gsGen.nextEvents(); 	
		
		gsGen.end();	
	}
			
	// create a random graph
	public static void createGraphRandom (Graph graph , int size ) {
	
		// create a random graph
		BarabasiAlbertGenerator gen = new BarabasiAlbertGenerator();	
		gen.addSink(graph);		
		gen.begin();
		
		for(int i =0; i< size ; i++)
			gen.nextEvents() ;
	}
	
	// set random attr value ( 0 <= x <= 1 ) to node
	public static void setRandomDoubleAttrToGraph ( Graph graph , String attribute ) {
		
		Random rnd = new Random () ;
		for ( Node n : graph.getEachNode() ) 
			n.addAttribute(attribute, rnd.nextDouble());		
	}

	// create edge with merge of node id
	public static void createEdge ( Graph graph  , Node n0 , Node n1 ) {
		
		String idEdge = n0.getId() + "-" + n1.getId() ;
		graph.addEdge(idEdge, n0, n1 );
	}
	
	// add node and set Coordinates
	public static void addNodeWithCoord ( Graph graph, String id , double x , double y , double z ) {
		
		graph.addNode(id);	
		Node n = graph.getNode(id) ;		
		n.setAttribute("xyz", x , y , z );
	}

	// create list of nodes in square
	public static ArrayList<Node> createListNodeInSquare ( Graph  graph , int numNodes , double[] meanPointCoord , double radius , int randomSeed ) {
		
		ArrayList<Node> listNodes = new ArrayList<Node> () ;
		
		double minX = meanPointCoord[0] - radius;
		double maxX = meanPointCoord[0] + radius;
		double minY = meanPointCoord[1] - radius;
		double maxY = meanPointCoord[1] + radius;
		
		double sizeSquare = maxX - minX ;
		
		Random rnd = new Random(randomSeed);
		
		int idMaxInt ;
		
		try {
			idMaxInt = graphToolkit.getMaxIdIntElement(graph, element.node)  ;	//	System.out.println(idMaxInt);
		} catch (java.util.NoSuchElementException e) {
			idMaxInt = 0 ; 
		}
		
		for ( int idInt = idMaxInt  ; idInt < numNodes + idMaxInt ; idInt++ ) {
			
			double rndX =  minX + rnd.nextDouble() * sizeSquare ;
			double rndY =  minY + rnd.nextDouble() * sizeSquare ;
			
			String id = Integer.toString(idInt) ;
			graph.addNode(id);
			
			Node newNode = graph.getNode(id);
			newNode.setAttribute("xyz", rndX , rndY , 0 );
			
			listNodes.add(newNode); 
		}
		return listNodes;
	}

	// create  complete graph
	public static void createCompleteGraph ( Graph graph , int numberOfNode , double[] meanPointCoord , double radius , int randomSeed ) {
		
		ArrayList<Node> listNode = new ArrayList<Node> ( graphGenerator.createListNodeInSquare(graph, numberOfNode, meanPointCoord, radius, randomSeed) ) ;
		ArrayList<Integer> listEdgeInt = graphToolkit.getListElement(graph, element.edge, elementTypeToReturn.integer) ;
		
		int idEdegeInt = 0 ;
		for ( Node n : graph.getEachNode()) {
			
			for ( Node n2 :listNode ) {
				if ( n2 == n ) 
					continue ;
				listEdgeInt = graphToolkit.getListElement(graph, element.edge, elementTypeToReturn.integer) ;
				while ( listEdgeInt.contains(idEdegeInt))
					idEdegeInt++ ;
				
				String idEdge = Integer.toString(idEdegeInt) ;
				try {
					graph.addEdge(idEdge, n, n2);
				}
				catch (org.graphstream.graph.EdgeRejectedException e) {
					// TODO: handle exception
				}
			}
		}
	}
	
	// create  complete graph from list of nodes
	public static void createCompleteGraphFromListNode ( Graph graph , ArrayList<Node> listNode ) {
		
		ArrayList<Integer> listEdgeInt = graphToolkit.getListElement(graph, element.edge, elementTypeToReturn.integer) ;
		
		int idEdegeInt = 0 ;
		
		for ( Node n : listNode ) {
			
			for ( Node n2 :listNode ) {
				if ( n2 == n ) 
					continue ;
				listEdgeInt = graphToolkit.getListElement(graph, element.edge, elementTypeToReturn.integer) ;
				while ( listEdgeInt.contains(idEdegeInt))
					idEdegeInt++ ;
				
				String idEdge = Integer.toString(idEdegeInt) ;
				try {
					graph.addEdge(idEdge, n, n2);
				}
				catch (org.graphstream.graph.EdgeRejectedException e) {
					// TODO: handle exception
				}
			}
		}
	}
	public enum spanningTreeAlgo { krustal , test }
	
	public static void createSpaningTree ( Graph graph , spanningTreeAlgo spanningTreeAlgo  ) {
		
		for ( Edge e : graph.getEachEdge() ) {
			
			Node 	n0 = e.getNode0() ,
					n1 = e.getNode1() ;
			
			double dist = gsAlgoToolkit.getDistGeom(n0, n1);
			e.addAttribute("weight", dist );
		}
		
		switch (spanningTreeAlgo) {
			case krustal: {
				
				// compute Krustal algoritm
				Kruskal kruskal = new Kruskal( "tree" , true , false ) ;
				kruskal.init(graph) ;
				kruskal.compute();
				
				// create list of edge to remove
				ArrayList<Edge> listEdgeToRemove = new ArrayList<Edge> () ;
				for ( Edge e : graph.getEachEdge()) {
				
					boolean tree = e.getAttribute("tree");	
					if ( tree == false )
						listEdgeToRemove.add(e);	
				}
			
				// remove edge
				for ( Edge e : listEdgeToRemove) 
					graph.removeEdge(e) ;
				
			} break;
			
			case test :
				break ;
		}
		
	}
	
}
