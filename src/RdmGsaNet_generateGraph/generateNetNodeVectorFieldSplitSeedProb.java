package RdmGsaNet_generateGraph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNetAlgo.graphToolkit;
import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNetAlgo.graphToolkit.element;
import RdmGsaNetAlgo.graphToolkit.elementTypeToReturn;
import RdmGsaNet_generateGraph.generateNetNode.interpolation;
import RdmGsaNet_generateGraph.generateNetNode.layoutSeed;

public class generateNetNodeVectorFieldSplitSeedProb extends generateNetNodeVectorField implements generateNetNode_Inter {

	// parameters only this class
	private double prob ;
	private double angleVectorNewSeed ;
	private boolean stillAlive ;
	private double radians ;
	
	public generateNetNodeVectorFieldSplitSeedProb(int numberMaxSeed, layoutSeed setLayoutSeed,
			interpolation typeInterpolation, boolean createSeedGraph, boolean updateNetGraph ,
			double prob , double angleVectorNewSeed  , boolean stillAlive) {
		
		super(numberMaxSeed, setLayoutSeed, typeInterpolation, createSeedGraph, updateNetGraph);
		
		this.prob = prob ;
		this.angleVectorNewSeed  = angleVectorNewSeed  ;
		this.stillAlive = stillAlive ;
		
		radians = Math.toRadians(angleVectorNewSeed) ; 
	}

	@Override
	public void generateNodeRule(int step) throws IOException {//		System.out.println(super.getClass().getSimpleName());

	//	System.out.println(seedGraph.getNodeCount() + " " + seedGraph.getNodeSet());
		generateNetNode.setSeedNodes(step, numberMaxSeed, setLayoutSeed);
		
		generateNetNodeVectorField.handleCreateSeedGraph(createSeedGraph, step); 
		
		ArrayList<Node> listNodeSeedGrad = new ArrayList<Node> () ;
		
		// CREATE LIST OF SEEDGRAD 
		if ( createSeedGraph ) 
			listNodeSeedGrad = graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.element );					//	System.out.println("createSeedGraph" );
		else																													 	//	System.out.println("iter in netGraph" );
			listNodeSeedGrad =  gsAlgoToolkit.getListNodeAttribute(netGraph, "seedGrad" , 1 )  ;									//	System.out.println("number of seed " + listNodeSeedGrad.size() + " " + listNodeSeedGrad);
			
		ArrayList<String> listNodeNet = new ArrayList<String> (  
				graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.string ) ) ;

	//	System.out.println(listNodeSeedGrad);
		
		ArrayList<Integer> listCast = new ArrayList<Integer>() ;					
		listNodeNet.stream().forEach( s -> listCast.add(Integer.parseInt(s)));
		
		int idNum = Collections.max(listCast) + 1;	//		System.out.println(listNodeNet);
		
		for ( Node nodeSeed : listNodeSeedGrad ) {	
			
			String idSeed = nodeSeed.getId() ;
			
			double[] 	nodeCoord = GraphPosLengthUtils.nodePosition(nodeSeed) ,
						vector = getVector(vecGraph, nodeCoord, typeInterpolation ) ;										// 	System.out.println(idSeed  + " "  + vector[0] + " " + vector[1]);				

			String idCouldAdded = null; 			//	String idCouldAdded = Integer.toString(netGraph.getNodeCount() );
			Node nodeCouldAdded = null ;							
			
			double 	xTopVector = generateNetNode.ceckCoordInGrid ( gsGraph , nodeCoord[0] + vector[0] ) ,
					yTopVector = generateNetNode.ceckCoordInGrid ( gsGraph , nodeCoord[1] + vector[1] ) ;
			
			int numberMaxNewSeed = 2 ;
			int numberNewSeed = gsAlgoToolkit.getBinomial(numberMaxNewSeed, prob);	
			
			if ( stillAlive ) 
				if ( numberNewSeed == 0)
					numberNewSeed = 1 ;	

			if ( numberNewSeed == 1 ) {
				// System.out.println(numberNewSeed);
			
				idCouldAdded = Integer.toString( idNum ); 
				
				if (updateNetGraph ) 
					generateNetNode.handleNewNodeCreation(netGraph, idCouldAdded, nodeSeed, xTopVector, yTopVector , true  )	;
				
				if ( createSeedGraph ) {
					nodeSeed.setAttribute( "xyz", xTopVector , yTopVector, 0 );
				}
				idNum ++ ;	
			}
			
			else if ( numberNewSeed > 1) {
//				System.out.println(seedGraph.getNodeCount() + " " + seedGraph.getNodeSet());
				
		//		System.out.println(vector[0] + " " + vector[1]);
				double intVect = Math.pow(Math.pow(vector[0]  , 2 ) + Math.pow(vector[1]  , 2 ) , 0.5 ) ; 
				
			//	System.out.println(intVect);
				
				double 	xc = nodeCoord[0] , yc = nodeCoord[1] , 
						x1 = xc + intVect * Math.cos(radians) ,
						y1 = yc + intVect * Math.sin(radians) , 
				
						x2 = x1 ,
						y2  = yc - intVect * Math.sin(radians) ;
		
				double [] 	xNewSeedCoord = new double[numberMaxNewSeed] ,
							yNewSeedCoord = new double[numberMaxNewSeed] ;
				
				xNewSeedCoord[0] = x1 ; 
				xNewSeedCoord[1] = x2 ;

				yNewSeedCoord[0] = y1 ; 
				yNewSeedCoord[1] = y2 ;
				
				for ( int i = 0 ; i < numberNewSeed ; i++ ) {
					idCouldAdded = Integer.toString( idNum ); 
				//	System.out.println(idCouldAdded + " " + xNewSeedCoord[i] + " " + yNewSeedCoord[i] );
					
					if ( updateNetGraph ) 
						generateNetNode.handleNewNodeCreation( netGraph, idCouldAdded, nodeSeed, xNewSeedCoord[i], yNewSeedCoord[i] , true  )	;
					
					if ( createSeedGraph ) {
						nodeSeed.setAttribute( "xyz", xNewSeedCoord[i], yNewSeedCoord[i] , 0 );
					}
				
				//	String idNewSeed = Integer.toString(idNum) ;
				//	seedGraph.addNode(idCouldAdded);
				//	Node newSeed = seedGraph.getNode(idNewSeed) ;
					
					idNum ++ ;	
					
				}
				
			//	System.out.println(seedGraph.getNodeSet());
				
			}
			
			
			
			
			
			
			
			
			
			
			
			
		
			
			
			
		}
		
		
	}

	@Override
	public void removeNodeRule(int step) {
		// TODO Auto-generated method stub
		
	}

}
