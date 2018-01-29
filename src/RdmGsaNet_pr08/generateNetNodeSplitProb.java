package RdmGsaNet_pr08;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class generateNetNodeSplitProb implements generateNetNodeInter {
	
	// COSTANTS
	static Graph netGraph = layerNet.getGraph();
	static Graph gsGraph = layerGs.getGraph();	
		
	static Map<Integer , ArrayList<Node>> mapStepSeed =  new HashMap<Integer , ArrayList<Node>>();
	
		
	// COSTRUCTOR 
	public generateNetNodeSplitProb( ) {
	}
	

	@Override
	public void generateNodeRule(int step) {
		// TODO Auto-generated method stub
		
		// CREATE LIST OF SEEDGRAD AND OLDSEEDGRAD 
				ArrayList<Node> listNodeSeedGrad = new ArrayList<Node>();
				
				// handle whether no have new node, set seed list like previous step
				listNodeSeedGrad = 	generateNetNodeGradient.createListSeedGrad( step );
				mapStepSeed.put(step, listNodeSeedGrad);
				
				if ( listNodeSeedGrad.isEmpty() && step != 1 ) {
					listNodeSeedGrad = mapStepSeed.get( step - 1 );
					mapStepSeed.put(step, listNodeSeedGrad);
				}
				// 
				
				System.out.println("list seed " + listNodeSeedGrad);

	}

	@Override
	public void removeNodeRule(int step) {
		// TODO Auto-generated method stub
		
	}


}
