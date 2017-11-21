package RdmGsaNet_pr08;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class layerNet {	
	
	// COSTANTS
	// initialization graph net
	private static Graph netGraph = new SingleGraph("netGraph");
	private static Graph gsGraph = layerGs.getGraph();
	
	public enum meanPointPlace { center , random , border }
	meanPointPlace point ;
	
	private setupNetInter layout;
	
	// COSTRUCTOR
	public layerNet (setupNetInter layout ) {
		this.layout = layout ;
	}
	
	// method to change layer setup
	public void changeLayer ( setupNetInter layout) {
		this.layout = layout ;
	}
	
	// method to create layer Net
	public void createLayer ( boolean createMeanPoint , meanPointPlace point , boolean setSeedMorp , double seedAct , double seedInh , boolean setSeedMorpInGs ) {
		
		// set default values of net graph
		setDefaultAtr () ;
		
		// setup parameter of first point in netGraph 
		if ( createMeanPoint == true ) {layout.setMeanPoint ( point ) ; }
		
		// create mean point in netLayer
		layout.createLayerNet ();
		
		// set morphogens in netGraph
		if (setSeedMorp == true ) { setSeedMorp (  seedAct ,  seedInh ); }
	
		if ( setSeedMorpInGs = true ) { setSeedMorpInGs ( ) ; }
	}
	
		
// PRIVATE METHODS-----------------------------------------------------------------------------------------------------	
	// method to set default values to network
	private void setDefaultAtr ( ) {
		for ( Node n : netGraph.getEachNode() ) {
			n.addAttribute( "idGs" , 0 );
			n.addAttribute( "con" , 0 );
		}
	}
	
	// method to add morp seed to net
	private void setSeedMorp ( double seedAct , double seedInh ) {
		
		// ask mean nodes ad add seed attributes of morphogens
		for ( Node nNet : netGraph.getEachNode()) {
			nNet.addAttribute( "seedAct" , seedAct );
			nNet.addAttribute( "seedInh" , seedInh );
		}	
	}
	
	private void setSeedMorpInGs () {
		for ( Node nNet : netGraph.getEachNode()) {
			
			String idNet = nNet.getId() ;
			double seedAct = nNet.getAttribute( "seedAct" );
			double seedInh = nNet.getAttribute( "seedInh" );
 			
			Node nGs = gsGraph.getNode( idNet );
			nGs.setAttribute("gsAct", seedAct );
			nGs.setAttribute("gsInh", seedInh );
		}
		
		
		
	}
	
// Get Methods -----------------------------------------------------------------------------------------------------	
	// get graph
	public static Graph getGraph ( ) { return netGraph; }

	
	
	
}