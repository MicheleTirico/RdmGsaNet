package RdmGsaNet_pr08;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.graphstream.graph.Graph;

import RdmGsaNetAlgo.gsAlgoToolkit;

public class generateNetNodeGradient implements generateNetNodeInter {
	
	// COSTANTS
	int seedNumber;
	Graph netGraph = layerNet.getGraph();
	Graph gsGraph = layerGs.getGraph();	
	
	// COSTRUCTOR 
	public generateNetNodeGradient( int seedNuber ) {
		this.seedNumber = seedNumber;
		setSeedGrad();
	}

	@Override
	public void generateNodeRule(int step) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeNodeRule(int step) {
		// TODO Auto-generated method stub
		
	}

// PRIVATE METHODS ----------------------------------------------------------------------------------------------------------------------------------------
	
	private void setSeedGrad () {
		
		ArrayList<String> listIdNodeMaxDegree = gsAlgoToolkit.getNodeMaxDegree(netGraph);
		int numberOfNodesNet = netGraph.getNodeCount();
		Collection nodeSet = netGraph.getNodeSet();
		
		if ( seedNumber == 1 && numberOfNodesNet == 1 ) {
			
			
		}
	}
}
