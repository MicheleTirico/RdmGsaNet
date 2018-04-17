package RdmGsaNet_generateGraph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNetAlgo.graphToolkit;
import RdmGsaNetAlgo.graphToolkit.element;
import RdmGsaNetAlgo.graphToolkit.elementTypeToReturn;

import RdmGsaNet_vectorField_02.vectorField.typeInterpolation ;
import RdmGsaNet_generateGraph.generateNetNode.layoutSeed;
import RdmGsaNet_mainSim.main;
import RdmGsaNet_vectorField_02.vectorField;

public class generateNetNodeVectorFieldSplitSeedGradient extends generateNetNodeVectorField implements generateNetNode_Inter  {
	
	// graphs
	private Graph vecGraph = main.getVecGraph() ;
	
	// vector field
	private vectorField vectorField = main.getVectorField() ;
	
	// parameters only this class
	private double 	maxInten ,
					angleTestDecimal ;
	
	public generateNetNodeVectorFieldSplitSeedGradient(int numberMaxSeed, layoutSeed setLayoutSeed,
			typeInterpolation typeInterpolation, boolean createSeedGraph, boolean updateNetGraph ,
			
			// parameters only this class
			double maxInten 	
			) {
		
		super(numberMaxSeed, setLayoutSeed, typeInterpolation, createSeedGraph, updateNetGraph);
		
		// parameters only this class
		this.maxInten = maxInten ;
	}

	@Override
	public void generateNodeRule(int step) throws IOException {

		generateNetNode.setSeedNodes(step, numberMaxSeed, setLayoutSeed);
		
		generateNetNodeVectorField.handleCreateSeedGraph(createSeedGraph, step); 
	
		// list node element
		ArrayList <Node> listNodeSeed = new ArrayList<Node> ( graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.element));	//	System.out.println(listNodeSeed);	
		ArrayList<Integer> listIdSeedInt = new ArrayList<Integer>( graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.integer)) ;			
		
		// map to update
		Map< Integer , double[] > mapIdNewSeedCoord = new HashMap< Integer , double[] > ();
		Map< Integer , String > mapIdNewSeedFather = new HashMap< Integer , String > ();
		
		// print
		System.out.println(seedGraph + " " + listIdSeedInt.size()  /* + " " + listIdSeedInt */ );//		System.out.println(netGraph + " " + listIdNetInt.size() + " " + listIdNetInt );
	
		int idLocal = 0 ;

		for ( Node nodeSeed :  listNodeSeed ) {
				
			String idSeed = nodeSeed.getId() ;
			
			double[] nodeCoord = GraphPosLengthUtils.nodePosition(nodeSeed) ,
					 vector = vectorField.getVectorInterpolate(vecGraph, nodeCoord, typeInterpolation) ;	
			
			// check vector
			double vectorInten = Math.pow( Math.pow(vector[0], 2) + Math.pow(vector[1], 2) , 0.5 ) ;
			
			if ( vectorInten > maxInten ) {
				vector[0]  = vector[0] / vectorInten * maxInten ; 
				vector[1]  = vector[1] / vectorInten * maxInten ;		
			}
			
			// check vector in grid
			double 	xTopVector = generateNetNode.checkCoordInGrid ( gsGraph , nodeCoord[0] + vector[0] ) ,
					yTopVector = generateNetNode.checkCoordInGrid ( gsGraph , nodeCoord[1] + vector[1] ) ;						// 	System.out.println(idSeed  + " "  + vector[0] + " " + vector[1]);						
			
			ArrayList <Node> listVertex = new ArrayList<Node> (graphToolkit.getListVertexRoundPoint(elementTypeToReturn.element, vecGraph, nodeCoord));
			
			Map < String , Node > mapIdVertex = new HashMap<String, Node > ( graphToolkit.getMapIdVertexRpoundPoint(elementTypeToReturn.element, vecGraph, nodeCoord)) ;	//	System.out.println(mapIdVertex);
			
			Node 	startVector_00 = mapIdVertex.get("00") , 
					startVector_10 = mapIdVertex.get("10") , 
					startVector_01 = mapIdVertex.get("01"),
					startVector_11 = mapIdVertex.get("11");
			
			double[] intenXY_00 = new double[2] ,  intenXY_10 = new double[2] ,  intenXY_01 = new double[2] ,  intenXY_11 = new double[2] ; 
			
//			double 	inten_00 = startVector_00.getAttribute("inten") , 			inten_10 = startVector_10.getAttribute("inten") ,			inten_01 = startVector_01.getAttribute("inten") ,			inten_11 = startVector_11.getAttribute("inten") ;  
			
			intenXY_00[0] = startVector_00.getAttribute("intenX"); 
			intenXY_00[1] = startVector_00.getAttribute("intenY"); 
			
			intenXY_10[0] = startVector_10.getAttribute("intenX"); 
			intenXY_10[1] = startVector_10.getAttribute("intenY"); 
			
			intenXY_01[0] = startVector_01.getAttribute("intenX"); 
			intenXY_01[1] = startVector_01.getAttribute("intenY"); 
			
			intenXY_11[0] = startVector_11.getAttribute("intenX"); 
			intenXY_11[1] = startVector_11.getAttribute("intenY"); 
			
			ArrayList< double[] > listCoordNewSeed = new ArrayList< double [] > () ;
			
			double[] firstSeedCoord = new double[2] ;
			firstSeedCoord[0] = nodeCoord[0] + intenXY_00[0] ;
			firstSeedCoord[1] = nodeCoord[1] + intenXY_00[1] ;
			
			mapIdVertex.remove("00");	//	listCoordNewSeed.add(firstSeedCoord) ;
			
			double[] coordToUpdate = new double [2] ;
			coordToUpdate [0] = nodeCoord[0] + intenXY_00[0] ;
			coordToUpdate [1] = nodeCoord[1] + intenXY_00[1] ; 
			
			for ( String coin : mapIdVertex.keySet() ) {
				
				Node nodeCoin = mapIdVertex.get(coin) ;
				
				double 	intenX = nodeCoin.getAttribute("intenX") ,  
						intenY = nodeCoin.getAttribute("intenY")  ;			//	System.out.println(intenX + " " + intenY );
		
				
				if ( intenX * intenXY_00[0] >= 0 && intenY * intenXY_00[1] >= 0 ) {  // conc
					
					listCoordNewSeed.remove(coordToUpdate) ;
					
					coordToUpdate [0] =/* nodeCoord[0] + */ coordToUpdate[0] + intenX ;
					coordToUpdate [1] =/* nodeCoord[1] + */ coordToUpdate[1] + intenY ; 
					listCoordNewSeed.add(coordToUpdate) ;
					continue ;
				}
					
				else {  		// disc
					double[] newSeedCoord = new double[2] ;
					newSeedCoord[0] = nodeCoord[0] + intenX ;
					newSeedCoord[1] = nodeCoord[1] + intenY ;
					listCoordNewSeed.add(newSeedCoord) ;
				}				
			}
			
			for (double[] coordToAdd : listCoordNewSeed ) {
				
				mapIdNewSeedCoord.put(idLocal, coordToAdd) ;
				mapIdNewSeedFather.put(idLocal, idSeed) ;							//		System.out.println("node 1 " + newNodesCoord[0] + " " + newNodesCoord[1] );
				
				idLocal++ ;
			}

		}
		for ( Node n : netGraph.getEachNode() )
			n.setAttribute("seedGrad", 0 );
		
		// update father and coord to seedGraph and netGraph
		for ( int i: mapIdNewSeedCoord.keySet() ) {
			
			ArrayList<Integer> listIdNetIntLocal = new ArrayList<Integer> ( graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.integer)) ;		//	System.out.println(listIdNetIntLocal);
			ArrayList<Integer> listIdSeedIntLocal = new ArrayList<Integer> ( graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.integer)) ;		//	System.out.println(listIdNetIntLocal);
			
			int idInt =  Collections.max(listIdNetIntLocal) ; //Math.max(listIdNetIntLocal, b) ;
			while ( listIdNetIntLocal.contains(idInt) && listIdSeedIntLocal.contains(idInt) ) 
				idInt ++ ;
			
			String id = Integer.toString(idInt) ;			//	System.out.println(idInt);
			try {
				netGraph.addNode(id);
				seedGraph.addNode(id);
			}
			catch (org.graphstream.graph.IdAlreadyInUseException e) {
				
				listIdSeedIntLocal = new ArrayList<Integer> ( graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.integer)) ;
				listIdNetIntLocal = new ArrayList<Integer> ( graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.integer)) ;
				
				idInt =Math.max(Collections.max(listIdNetIntLocal) , Collections.max(listIdSeedIntLocal) )     ;
				idInt ++ ; 
				while ( listIdNetIntLocal.contains(idInt) &&  listIdSeedIntLocal.contains(idInt))
					idInt ++ ;
				
				id = Integer.toString(idInt) ;			//	System.out.println(idInt);
				
				netGraph.addNode(id);
				seedGraph.addNode(id);
			}
			
			Node nodeNet = netGraph.getNode(id);
			Node nodeSeed = seedGraph.getNode(id);
			
			double[ ] coord = mapIdNewSeedCoord.get(i);
			String father = mapIdNewSeedFather.get(i);
			
			nodeSeed.addAttribute("xyz", coord[0] , coord[1] , 0 );
			nodeSeed.addAttribute("father", father );
			
			nodeNet.addAttribute("xyz", coord[0] , coord[1] , 0 );
			nodeNet.addAttribute("father", father );	
			
			nodeNet.setAttribute("seedGrad", 1);
		}
		
		// remove old seed
		for ( int i : listIdSeedInt) //	System.out.println(i);
			seedGraph.removeNode(Integer.toString(i));	
	}

	@Override
	public void removeNodeRule(int step) {
		// TODO Auto-generated method stub
		
	}

}
