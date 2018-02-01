package RdmGsaNet_pr09;

import java.util.ArrayList;

import org.graphstream.graph.Node;

import com.sun.org.apache.xml.internal.utils.ThreadControllerWrapper;

import RdmGsaNetAlgo.gsAlgoToolkit;

public class generateNetNodeGradientOnlyOneRandom extends generateNetNodeGradient implements generateNetNode_Inter {

	// local constants 
	
	
	// COSTRUTOR
	public generateNetNodeGradientOnlyOneRandom ( int numberMaxSeed, layoutSeed setLayoutSeed , String morp ) {
		this.numberMaxSeed = numberMaxSeed ;
		this.setLayoutSeed = setLayoutSeed ;
		this.morp = morp ;
	}
	

	@Override
	public void generateNodeRule(int step) {

		// set seed nodes 
		setSeedNodes(step, numberMaxSeed, setLayoutSeed);
		
		// CREATE LIST OF SEEDGRAD 
		ArrayList<Node> listNodeSeedGrad = 	gsAlgoToolkit.getListNodeAttribute(netGraph, "seedGrad" , 1 );		// 
		System.out.println(listNodeSeedGrad);

		for ( Node nNet : listNodeSeedGrad ) {
			
			// create list of nodes with value greater of nNet
			ArrayList<Node> listNeigValMax = createListNodeMaxNeig( gsGraph, nNet , morp); 	// 	System.out.println("listIdNeigValMax of " + nNet.getId() + " " + listNeigValMax);
			
			// get neig with greater val 
			String theGreater = getNodeGreater(morp, listNeigValMax);							//	
			System.out.println(" winner " + theGreater);
		
			// if in theGreather is empty
			netGraph.getNode(theGreater);
			System.out.println(netGraph.getNodeSet());
		
		
		}
	}

	
	
	
	
	@Override
	public void removeNodeRule(int step) {
		// TODO Auto-generated method stub		
	}


	} 



