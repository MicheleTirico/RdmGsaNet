package RdmGsaNet_generateGraph;


import java.util.ArrayList;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import RdmGsaNetAlgo.graphToolkit;
import RdmGsaNetAlgo.graphToolkit.elementTypeToReturn;
import RdmGsaNet_generateGraph.generateNetNode.interpolation;
import RdmGsaNet_generateGraph.generateNetNode.layoutSeed;
import RdmGsaNet_generateGraph.generateNetNode.rule;
import RdmGsaNet_mainSim.layerGs;
import RdmGsaNet_mainSim.layerNet;
import RdmGsaNet_mainSim.main;

public class generateNetNodeVectorField extends main  {
	
	
	// COSTANTS	
	protected int numberMaxSeed  ;
	protected layoutSeed setLayoutSeed ;
	protected interpolation typeInterpolation ; 
	protected boolean 	createSeedGraph ,
						updateNetGraph;
	
	// graphs
	protected static Graph	netGraph = layerNet.getGraph() ,
							gsGraph  = layerGs.getGraph() , 
							vecGraph = main.vecGraph , 
							seedGraph = main.getSeedGraph() ;	
							
	// constructor
	public generateNetNodeVectorField( int numberMaxSeed, layoutSeed setLayoutSeed , interpolation typeInterpolation , boolean createSeedGraph , boolean updateNetGraph) {
		this.numberMaxSeed = numberMaxSeed ;
		this.setLayoutSeed = setLayoutSeed ;
		this.typeInterpolation = typeInterpolation ;
		this.createSeedGraph = createSeedGraph ;
		this.updateNetGraph = updateNetGraph ;
	}
	
// COMMON METHODS FOR ALL CLASS WITH GRADIENT APPROACH ----------------------------------------------------------------------------------------------
	
	protected static double[] getVector ( Graph vecGraph , double[] nodeCoord , interpolation typeInterpolation ) {
		
		double[]vector = new double[2] ;
		double vectorX = 0.0 , vectorY = 0.0 ;
		
		ArrayList <String> 	listVertex = new ArrayList<String> (graphToolkit.getListVertexRoundPoint(elementTypeToReturn.string, gsGraph, nodeCoord));	//	System.out.println(listVertex );
		
		switch (typeInterpolation) {
		
			case sumVectors: {
				for ( String nVertex : listVertex ) {
		//			System.out.println(nVertex);
					Node nVec = vecGraph.getNode(nVertex); 
					
					double 	intenX = nVec.getAttribute("intenX") ,
							intenY = nVec.getAttribute("intenY") ; 
											
					vectorX = vectorX + intenX ;
					vectorY = vectorY + intenY ;		
				}
			} break;
		}
		vector[0] = vectorX ;
		vector[1] = vectorY ;				//	System.out.println(vectorX + " " + vectorY );
		
		return vector ;
	}

}
