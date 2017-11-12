package RdmGsaNet_pr08;

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
	
	// method to change gs layout ( never used ) 
	public void changeLayer ( setupGsInter layout) {
		this.layout = layout ;
	}
	
	// method to create layer gs
	public void createLayer () {
		layout.createLayerGs () ;
	}
			
	// methods to define characteristics of layer gs
	public void setupDisMorp ( setupGsInter.disMorpType type , int randomSeedAct, int randomSeedInh, double homoVal ) {
		
		Graph graph = gsGraph;
		
		switch (type) {		
	
		case homo :
			
			System.out.println("distribution homogeneus " + homoVal );
			
			for ( Node n:graph.getEachNode() ) { n.setAttribute("GsAct" , homoVal  ) ;}
			for ( Node n:graph.getEachNode() ) { n.setAttribute("GsInh" , homoVal  ) ;}
			
			break;
			
		case random :
			System.out.println("distribution random = " + "randomSeedAct = " + randomSeedAct + " randomSeedInh = " + randomSeedInh );
			
			Random act = new Random( randomSeedAct );
			Random inh = new Random( randomSeedInh );
			
			for ( Node n:graph.getEachNode() ) { n.setAttribute("GsAct" , act.nextDouble()   ) ;}
			for ( Node n:graph.getEachNode() ) { n.setAttribute("GsInh" , inh.nextDouble()   ) ;}
			
			break;
			}
		}
//----------------------------------------------------------------------------------------------------------------------------------		
	// get graph
	public static Graph getGraph (Graph graph ) { return graph; }

	}


