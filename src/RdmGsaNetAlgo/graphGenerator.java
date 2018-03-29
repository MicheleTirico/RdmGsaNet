package RdmGsaNetAlgo;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import org.graphstream.algorithm.generator.BarabasiAlbertGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.GridGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import RdmGsaNetAlgo.graphToolkit.element;

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

	public static void createEdge ( Graph graph  , Node n0 , Node n1 ) {
		
		String idEdge = n0.getId() + "-" + n1.getId() ;
		graph.addEdge(idEdge, n0, n1 );
		  	
	}
	
public static ArrayList<Node> createListNodeInSquare ( Graph  graph , int numNodes , double[] meanPointCoord , double radius ) {
		
		ArrayList<Node> listNodes = new ArrayList<Node> () ;
		
		double minX = meanPointCoord[0] - radius;
		double maxX = meanPointCoord[0] + radius;
		double minY = meanPointCoord[1] - radius;
		double maxY = meanPointCoord[1] + radius;
		
		double sizeSquare = maxX - minX ;
		
		Random rnd = new Random();
	
		int idMaxInt = graphToolkit.getMaxIdIntElement(graph, element.node)  ;	//	System.out.println(idMaxInt);
		
		for ( int idInt = idMaxInt  ; idInt <= numNodes + idMaxInt ; idInt++ ) {
			
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
	

	
}
