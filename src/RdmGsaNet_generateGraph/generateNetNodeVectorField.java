package RdmGsaNet_generateGraph;


import java.io.IOException;
import java.util.ArrayList;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.stream.file.FileSinkDGS;

import RdmGsaNetAlgo.graphToolkit;
import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNetAlgo.graphToolkit.element;
import RdmGsaNetAlgo.graphToolkit.elementTypeToReturn;
import RdmGsaNetExport.handleNameFile;
import RdmGsaNet_generateGraph.generateNetNode.interpolation;
import RdmGsaNet_generateGraph.generateNetNode.layoutSeed;
import RdmGsaNet_generateGraph.generateNetNode.rule;
import RdmGsaNet_mainSim.layerGs;
import RdmGsaNet_mainSim.layerNet;
import RdmGsaNet_mainSim.main;

public class generateNetNodeVectorField extends main  {
	
	protected  static FileSinkDGS fsd = new FileSinkDGS();
	protected static handleNameFile handle = main.getHandle();
	
	// COSTANTS	
	protected int numberMaxSeed  ;
	protected layoutSeed setLayoutSeed ;
	protected interpolation typeInterpolation ; 
	protected boolean 	createSeedGraph ,
						updateNetGraph;
	
	// graphs
	protected static Graph	netGraph = layerNet.getGraph() ,
							gsGraph  = layerGs.getGraph() , 
							vecGraph = main.getVecGraph () , 
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
				for ( String nVertex : listVertex ) {	//			System.out.println(nVertex);
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
	
	protected static void handleCreateSeedGraph (boolean createSeedGraph , int step) throws IOException {
		
		if ( createSeedGraph ) {
			if ( step == 1)	{				//		System.out.println("createSeedGraph step1" );
				ArrayList<String> list = new ArrayList<String> ( graphToolkit.getListElementAttribute( netGraph, element.node, elementTypeToReturn.string, "seedGrad", 1 ) ) ;
				for ( String id : list ) {
					
					Node n = netGraph.getNode(id) ;
					seedGraph.addNode( id );
					Node nodeSeed = seedGraph.getNode(id) ;
					gsAlgoToolkit.setNodeCoordinateFromNode(netGraph, seedGraph, n, nodeSeed);
					
					String pathStart = handle.getPathStartSeed();
					seedGraph.write(fsd, pathStart);			
				}
			}			
		}
	}
	
	// doesn't works
	protected static double [] getCoordAngleVector ( double coefImplemVect , double angleRad , double[] vector , double[] coordSeed ) {
		
		double[]coordNodes = new double[4];
			
		double 	intVect =  Math.pow(Math.pow(vector[0]  , 2 ) + Math.pow(vector[1]  , 2 ) , 0.5 ) ,
				tan =  vector[1] / vector[0] ,
				angleVector = 0.0 ;
		
		if 		( vector[0] > 0 && vector[1] > 0) 
			angleVector = Math.atan(tan)  ;
		
		else if	 ( vector[0] < 0 && vector[1] < 0) 
			angleVector = Math.atan(tan) + Math.PI  ;
		
		else if	 ( vector[0] > 0 && vector[1] < 0) 
			angleVector =   Math.atan(tan) +  Math.PI  ;
		
		else if	 ( vector[0] < 0 && vector[1] > 0) 
			angleVector =  Math.atan(tan) + 0.5 * Math.PI  ;
			
			
		double 	angle1 = angleVector + angleRad ,
				angle2 = angleVector - angleRad ,
				
				x1 = coordSeed[0] + intVect * Math.cos(angle1) ,
				y1 = coordSeed[1] + intVect * Math.sin(angle1) ,
				
				x2 = coordSeed[0] + intVect * Math.cos(angle2) ,
				y2 = coordSeed[1] + intVect * Math.sin(angle2) ;
		
		coordNodes[0] = x1 ;
		coordNodes[1] = y1 ;
		coordNodes[2] = x2 ;
		coordNodes[3] = y2 ;
		
//		System.out.println(x1 + " "  + y1 );	System.out.println(x2 + " "  + y2 );
		return coordNodes;
	}
	
	protected static double [] getNewCordAngle ( double coefInten , double [ ] nodeCoord ,  double [ ] vector , double radians , double maxDistImplem ) {
		
		double [] newNodeCoord = new double [2] ;												//	System.out.println( Math.atan(vector[1] / vector[0] ) );
		double 	alfa = 0.0 ;
		
		if 		( vector[0] > 0 && vector[1] > 0) 
			alfa = Math.atan(vector[1] / vector[0] )  ;
		
		else if	 ( vector[0] < 0 && vector[1] < 0) 
			alfa = Math.atan(vector[1] / vector[0] ) + Math.PI  ;
		
		else if	 ( vector[0] > 0 && vector[1] < 0) 
			alfa =  Math.atan(vector[1] / vector[0] ) ;
		
		else if	 ( vector[0] < 0 && vector[1] > 0) 
			alfa = - Math.atan(vector[1] / vector[0] ) + Math.PI  ;
		
		double	r =  Math.pow(Math.pow(vector[0]  , 2 ) + Math.pow(vector[1]  , 2 ) , 0.5 ) ;	//	System.out.println(alfa);
		
		newNodeCoord[0] = nodeCoord[0] + coefInten * r * Math.cos(alfa + radians) ;
	
		if (Math.abs(newNodeCoord[0] - nodeCoord[0] ) > maxDistImplem )
			newNodeCoord[0] = nodeCoord[0] + maxDistImplem ;
		
		newNodeCoord[1] = nodeCoord[1] + coefInten * r * Math.sin(alfa + radians) ;
		if (Math.abs(newNodeCoord[1] - nodeCoord[1] ) > maxDistImplem )
			newNodeCoord[1] = nodeCoord[1] + maxDistImplem ;
		
		return newNodeCoord ;
	}
	

}
