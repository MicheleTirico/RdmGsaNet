package RdmGsaNet_pr08;

import java.util.Random;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class layerGs {
	
	// VARIABLES
	private setupGsInter layout ;
	
	// create graph of reaction diffusion layer
	private static Graph gsGraph = new SingleGraph("gsGraph");
	
	// COSTRUCTOR 
	public layerGs (setupGsInter layout) {
		this.layout = layout ;
	}
	
	// method to change gs layout ( never used ) 
	public void changeLayer ( setupGsInter layout) {
		this.layout = layout ;
	}
	
	// method to create layer gs
	public void createLayer ( boolean setCoordinate, boolean setDefaultAtr ) {
		layout.createLayerGs () ; 
		if (setCoordinate == true ) { layout.setCoordinate () ; }
		if (setDefaultAtr == true ) { setDefaultAtr () ; }
	}
			
	// methods to define characteristics of layer gs
	public void setupDisMorp ( setupGsInter.disMorpType type , int randomSeedAct, int randomSeedInh, double homoValAct , double homoValInh) {
		
		switch (type) {		
	
		case homo :
			
			System.out.println("distribution homogeneus / Act = " + homoValAct + " Inh = " + homoValInh );
			
			for ( Node n:gsGraph.getEachNode() ) { n.setAttribute("gsAct" , homoValAct  ) ;}
			for ( Node n:gsGraph.getEachNode() ) { n.setAttribute("gsInh" , homoValInh  ) ;}
			
			break;
			
		case random :
			System.out.println("distribution random / " + "randomSeedAct = " + randomSeedAct + " randomSeedInh = " + randomSeedInh );
			
			Random act = new Random( randomSeedAct );
			Random inh = new Random( randomSeedInh );
			
			for ( Node n:gsGraph.getEachNode() ) { n.setAttribute("gsAct" , act.nextDouble()   ) ;}
			for ( Node n:gsGraph.getEachNode() ) { n.setAttribute("gsInh" , inh.nextDouble()   ) ;}
			
			break;
			}
		}
	
//----------------------------------------------------------------------------------------------------------------------------------		
	private void setDefaultAtr ( ) {
		
		for ( Node n : gsGraph.getEachNode() ) {
			n.addAttribute( "idNet" , 0 );
			n.addAttribute( "gsAct" , 0 );
			n.addAttribute( "gsInh" , 0 );
			n.addAttribute( "con" , 0 );
		}
	}
	
	
	
	
	// get graph
	public static Graph getGraph ( ) { return gsGraph; }

	}


