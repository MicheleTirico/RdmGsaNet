package RdmGsaNet_pr06;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import RdmGsaNet_pr06.setupNetInter.meanPointPlace;

public class layerNet {	
	  


	
	// COSTANTS
	Graph netGraph = new SingleGraph("netGraph");
	Graph gsGraph = setupGs.getGraph(setupGs.GsGraph);
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
	
	public void setupGsNetLink(int size , meanPointPlace point) {
		meanPointPlace x = point;
		layout.setGsAtr(gsGraph, point );
		layout.setNetAtr();
	}
		
//-----------------------------------------------------------------------------------------------------	
	
	

	
	
	
}
