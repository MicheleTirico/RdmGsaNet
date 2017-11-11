package RdmGsaNet_pr07;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class layerNet {	
	
	// COSTANTS
	Graph netGraph = new SingleGraph("netGraph");
	Graph gsGraph = layerGs.getGraph(layerGs.gsGraph);
	
	private setupNetInter layout;
	
	// COSTRUCTOR
	public layerNet (setupNetInter layout ) {
		this.layout = layout ;
	}
	
	public void changeLayer ( setupNetInter layout) {
		this.layout = layout ;
	}
	
	public void createLayer () {
		layout.createLayerNet ();	
	}
	
	public void setupGsNetLink(setupNetInter.meanPointPlace point) {
		setupNetInter.meanPointPlace x = point;
		layout.setGsAtr(gsGraph, point );
		layout.setNetAtr();
	}
		
//-----------------------------------------------------------------------------------------------------	
	
	

	
	
	
}
