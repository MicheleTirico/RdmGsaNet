package RdmGsaNet_generateGraph;

import org.graphstream.graph.Graph;

import RdmGsaNet_mainSim.layerGs;
import RdmGsaNet_mainSim.layerNet;
import RdmGsaNet_mainSim.main;

public class generateNetDelaunay extends main {
	
	// graphs
	protected static Graph	netGraph 	= layerNet.getGraph () ,
							gsGraph  	= layerGs.getGraph () , 
							vecGraph 	= main.getVecGraph () ,
							seedGraph 	= main.getSeedGraph () ,
							delGraph 	= main.getDelaunayGraph ();	
						

	public generateNetDelaunay() {
		// TODO Auto-generated constructor stub
	}
}
