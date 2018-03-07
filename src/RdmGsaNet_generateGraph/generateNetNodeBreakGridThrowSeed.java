package RdmGsaNet_generateGraph;

import java.util.ArrayList;

import RdmGsaNetAlgo.gsAlgoToolkit;

public class generateNetNodeBreakGridThrowSeed extends generateNetNodeBreakGrid implements generateNetNode_Inter {

	public generateNetNodeBreakGridThrowSeed(int numberMaxSeed , interpolation typeInterpolation) {
		super(numberMaxSeed, typeInterpolation);	
	}

	@Override
	public void generateNodeRule(int step) {
		
		System.out.println("size netGraph " +  netGraph.getNodeCount() + netGraph.getNodeSet());
		sizeGridEdge = Math.pow( gsGraph.getNodeCount() , 0.5 ) - 1 ;
		
		
		setStartSeed(netGraph, step ,  numberMaxSeed , "seedGrad" ) ;
				
		// CREATE LIST OF SEEDGRAD 
		ArrayList<String> listNodeSeedGrad = gsAlgoToolkit.getListStringNodeAttribute(netGraph, "seedGrad" , 1 );			//		
		System.out.println("number of seed " + listNodeSeedGrad.size() + " " + listNodeSeedGrad);

		ArrayList<String> listNetNodeStr = new ArrayList<String>();
	}

	@Override
	public void removeNodeRule(int step) {
		// TODO Auto-generated method stub
		
	}
		
	
		
	}
