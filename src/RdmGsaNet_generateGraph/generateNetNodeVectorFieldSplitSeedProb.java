package RdmGsaNet_generateGraph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

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

		System.out.println(seedGraph +" " + seedGraph.getNodeCount() + " " + seedGraph.getNodeSet());
	//	System.out.println(netGraph  +" " + netGraph.getNodeCount() + " " + netGraph.getNodeSet());
		
	//	System.out.println(seedGraph +" " + seedGraph.getNodeCount() );
	//	System.out.println(netGraph  +" " + netGraph.getNodeCount() );
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
	
		ArrayList<Node> listNodeToUpdate = new ArrayList<Node>();
		ArrayList<String> listNodeToRemove = new ArrayList<String>();
		
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
				
				double[] newNodesCoord = getCoordAngleVector(1, radians, vector, nodeCoord);
	
				double 	x1 = newNodesCoord[0] ,
						y1 = newNodesCoord[1];
				
				double 	x2 = newNodesCoord[2],
						y2 = newNodesCoord[3];
				
					
				double [] 	xNewSeedCoord = new double[numberMaxNewSeed] ,
							yNewSeedCoord = new double[numberMaxNewSeed] ;
				
				xNewSeedCoord[0] = x1 ; 
				xNewSeedCoord[1] = x2 ;

				yNewSeedCoord[0] = y1 ; 
				yNewSeedCoord[1] = y2 ;
				
				for ( int i = 0 ; i < numberNewSeed ; i++ ) {
					idCouldAdded = Integer.toString( idNum ); 
				//	System.out.println(nodeSeed ) ;
				//	System.out.println(idCouldAdded + " " + xNewSeedCoord[i] + " " + yNewSeedCoord[i] );
					
					if ( updateNetGraph ) {
						try {															//	System.out.println(idCouldAdded);
							netGraph.addNode(idCouldAdded) ;
							seedGraph.addNode(idCouldAdded) ;
							
							Node nNet = netGraph.getNode(idCouldAdded);
							Node nSeed = seedGraph.getNode(idCouldAdded);				//	System.out.println(nodeSeed.getId());
							
							nSeed.addAttribute("father", nodeSeed.getId());	
							nNet.addAttribute("father", nodeSeed.getId());				//	System.out.println(n.getAttributeKeySet());
							
							nodeCouldAdded = netGraph.getNode(idCouldAdded); 			//	System.out.println(idCouldAdded);
							nodeCouldAdded.addAttribute("seedGrad", 1);
							
							nodeSeed.setAttribute("seedGrad", 0 );
						//	System.out.println(nodeSeed.getId());
							nodeCouldAdded.addAttribute("father", nodeSeed.getId() );
							
							// set coordinate
							nNet.setAttribute( "xyz", xNewSeedCoord[i]  , yNewSeedCoord[i], 0 );	
							nSeed.setAttribute( "xyz", xNewSeedCoord[i]  , yNewSeedCoord[i], 0 );	
								
							listNodeToUpdate.add(nodeCouldAdded);
								
							seedGraph.removeNode(nodeSeed);
							}
							
						// if node already exist 
						catch (org.graphstream.graph.IdAlreadyInUseException e) { 		//
							System.out.println(e.getMessage());
							nodeCouldAdded = netGraph.getNode(idCouldAdded); 			//	System.out.println(idCouldAdded);
							nodeCouldAdded.addAttribute("seedGrad", 0 );
							nodeSeed.setAttribute("seedGrad", 1);
						}
						
							
					}
	
					
					if ( createSeedGraph ) 
						nodeSeed.setAttribute( "xyz", xNewSeedCoord[i], yNewSeedCoord[i] , 0 );
								
				//	listNodeToRemove.add(idSeed);	
					idNum ++ ;
				}
				
			}

		}	//		System.out.println(idNum);	
//		System.out.println(seedGraph.getNodeSet());
		
		
	//	System.out.println(listNodeToRemove);
		
		ArrayList<String> list = graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.string) ;
	//	System.out.println("listNodeToUpdate " + listNodeToUpdate);	
		
		list.stream().forEach( s -> listCast.add(Integer.parseInt(s)));
		
		int idToUpdate = Collections.max(listCast)  + 1  ;	//		
	//	System.out.println(list);
	//	System.out.println(Collections.max(listCast) ) ;
	//	System.out.println(idToUpdate);
		
		
		for ( Node n : listNodeToUpdate ) {		//	System.out.println(seedGraph.getNodeCount() +" " + seedGraph.getNodeSet());	System.out.println(idToUpdate);
			String id = Integer.toString(idToUpdate);		//	System.out.println("oldId " + n + " newId "+  idToUpdate ) ;
			
			seedGraph.addNode(id);
			Node nodeToUp = seedGraph.getNode(id);
			
			double[] coord = GraphPosLengthUtils.nodePosition(n);
			nodeToUp.setAttribute( "xyz", coord[0], coord[0] , 0 );
			
			String father = n.getAttribute("father") ; // 		
			
			nodeToUp.setAttribute("father", father);
	
		//
		//	System.out.println(father);
			
		
		
				idToUpdate++;
		}
		
	//	System.out.println(seedGraph.getNodeCount() + " " + seedGraph.getNodeSet());
		list = graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.string) ;	//	System.out.println(list);
		
		 for  ( Node n : listNodeToUpdate ) {
			 String id = n.getId();
			 if ( list.contains(id) ) {
				 seedGraph.removeNode(id);
			 }
		 }
			System.out.println(seedGraph.getNodeCount() + " " + seedGraph.getNodeSet());
			System.out.println(netGraph.getNodeCount() + " " + netGraph.getNodeSet());

	}

	@Override
	public void removeNodeRule(int step) {
		// TODO Auto-generated method stub
		
	}

}
