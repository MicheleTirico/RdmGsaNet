package RdmGsaNet_pr08;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import static org.graphstream.algorithm.Toolkit.*;

import RdmGsaNetAlgo.gsAlgoToolkit;
import graphstream_dev_io_test.graphstreamReadGraph;

public class generateNetNodeGradient implements generateNetNodeInter {
	
	// COSTANTS
	int seedNumber;
	Graph netGraph = layerNet.getGraph();
	Graph gsGraph = layerGs.getGraph();	
	
	// COSTRUCTOR 
	public generateNetNodeGradient( int seedNumber ) {
		this.seedNumber = seedNumber;
	}

	@Override
	public void generateNodeRule(int step) {
		
		}

	@Override
	public void removeNodeRule(int step) {
		}	

// PRIVATE METHODS ----------------------------------------------------------------------------------------------------------------------------------------
	
	private void setSeedGrad () {
		
		}

	@Override
	public void setSeedNodes( int step ) {

		if ( step == 1 ) {
			ArrayList<String> listIdNodeMaxDegree = gsAlgoToolkit.getNodeMaxDegree(netGraph);
			int numberOfNodesNet = netGraph.getNodeCount();
			Collection nodeSet = netGraph.getNodeSet();
//			System.out.println("gino");
//			System.out.println(listIdNodeMaxDegree);
				
			String idSeed = null;

			System.out.println(seedNumber);
			if ( seedNumber == 1 ) {
		
				System.out.println("seedNumber = " +seedNumber );
				for ( String s : listIdNodeMaxDegree) { idSeed = s;	}
//				System.out.println(idSeed);
				
				Node seedNode = netGraph.getNode(idSeed);
				seedNode.addAttribute("seedGrad", 1);
				java.lang.System.exit(100);
			}
			else {
			if ( seedNumber > 9 ) {
				System.out.println("seedNumber = " +seedNumber );
				System.out.println("seed Gradient over number of nodes , set seedNumber = countNodes");
				for ( Node seedNode : netGraph.getEachNode()) {
					seedNode.addAttribute("seedGrad", 1 );
				}
				java.lang.System.exit(100);
			}
			else {
			if ( seedNumber > 1 && seedNumber < 9 ) {
				System.out.println("seedNumber = " +seedNumber );
				ArrayList<String> listSeed = new ArrayList<String>() ;
				
				for ( String s : listIdNodeMaxDegree) { listSeed.add(s);	}
				System.out.println(listSeed);
				for ( int i = 1 ; listSeed.size() < seedNumber  ; i++ )  {
//					System.out.println(i);
					Node nodeRandom = randomNode(netGraph);
					String idNodeRandom = nodeRandom.getId();
					if ( !listSeed.contains(idNodeRandom) ) {
						listSeed.add(idNodeRandom);
					}
					
//					System.out.println(listSeed);
					
					listSeed.forEach(s -> netGraph.getNode(s).addAttribute("seedGrad", 1));
					
				}
			}
			}
			
					
				
				
				
				
				
				java.lang.System.exit(100);
			}
//			System.out.println(numberOfNodesNet);		
//			System.out.println(listIdNodeMaxDegree);		
//			System.out.println(nodeSet);		
		}
		
		//		
	}
}
