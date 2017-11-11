package RdmGsaNet_pr07;

import java.util.Random;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class layerGs {
	
	// VARIABLES
	private setupGsInter layout ;
	
	// create graph of reaction diffusion layer
	public static Graph gsGraph = new SingleGraph("gsGraph");
	
	// COSTRUCTOR 
	public layerGs (setupGsInter layout) {
		this.layout = layout ;
		
	}
	
	// method to change gs layout
	public void changeLayer ( setupGsInter layout) {
		this.layout = layout ;
	}
	
	// method to create layer gs
	public void createLayer () {
		layout.createLayerGs () ;
	}
	
//	public void setupGsValue (double kill , double feed , double Di , double Da) {
		
	// private methods
	public void setupDisMorp ( setupGsInter.disMorpType type , int randomSeedAct, int randomSeedInh, double homoVal ) {
		
		Graph graph = gsGraph;
		
		switch (type) {		
	
		case homo :
			double x = homoVal;
			
			System.out.println("distribution homogeneus " + x );
			
			for ( Node n:graph.getEachNode() ) { n.setAttribute("GsAct" , x  ) ;}
			for ( Node n:graph.getEachNode() ) { n.setAttribute("GsInh" , x  ) ;}
			
			break;
			
		case random :
			System.out.println("distribution random");
			
			Random act = new Random( randomSeedAct );
			Random inh = new Random( randomSeedInh );
			
			for ( Node n:graph.getEachNode() ) { n.setAttribute("GsAct" , act.nextDouble()   ) ;}
			for ( Node n:graph.getEachNode() ) { n.setAttribute("GsInh" , inh.nextDouble()   ) ;}
			
			break;
			}
		
		}
		
	// get graph
	public static Graph getGraph (Graph graph ) { return graph; }

	}


