package RdmGsaNetAlgo;

import java.util.Random;
import java.util.Set;

import org.graphstream.algorithm.generator.BarabasiAlbertGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.GridGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

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

	
}
