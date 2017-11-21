package RdmGsaNet_pr08;

import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.GridGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Random;

public class setupNetSeed implements setupNetInter {
		
	private static Graph gsGraph = layerGs.getGraph() ;
	private static Graph netGraph = layerNet.getGraph() ;
	
	// create layer Net 
	public void createLayerNet() {	
		System.out.println("hello seed");	

		// list id gs nodes  ( con == 1 )
		ArrayList<String> listIdGsCon = new ArrayList<String> () ;
		
		// create list of id with con = 1 => that means we create a list of meanPoint
		for ( Node nGs : gsGraph.getEachNode()) {
			int con = nGs.getAttribute("con") ;
			if (  con == 1 ) {
				listIdGsCon.add(nGs.getId());	}
		}//		System.out.println(listIdGsCon);
		
		// create seed node in netGraph and set coordinate
		for ( String id : listIdGsCon ) {
			
			// get node in gsGraph with con == 1
			Node nGs = gsGraph.getNode(id);
			
			// get array of coordinate of node gs with con == 1
			double [] nGsCoordinate = GraphPosLengthUtils.nodePosition(nGs) ;						//	System.out.println(nGsCoordinate[0]);
			
			// create node in netGraph
			netGraph.addNode(id);
			
			// setcoordinate of node in netGraph
			Node nNet = netGraph.getNode(id); 														//	System.out.println(nNet.getId());
			nNet.setAttribute( "xyz", nGsCoordinate[0] , nGsCoordinate[1] , nGsCoordinate[2] );		//	double [] nNetCoordinate = GraphPosLengthUtils.nodePosition(nNet) ;			System.out.println(nNetCoordinate[0]);
		}
	}

	// create the first point in gsGraph
	public void setMeanPoint ( layerNet.meanPointPlace point) {
		
//		setupNetInter.setDefaultConnectionNode (graph, 0 );
		int gridSize = setupGsGrid.getGsGridSize();				//		System.out.println(point);

		switch (point) {
		case random: { 

			int randomX = (int) ( Math.random() * gridSize );
			int randomY = (int) ( Math.random() * gridSize );
			
			String idString = convertIdToString(randomX , randomY) ;
			
			Node seed = gsGraph.getNode(idString);
			seed.setAttribute("con", 1);
			
			break;
		}

		case center: {
			int idCenter = (int)  Math.floor(gridSize / 2) ;			
			String idString = convertIdToString(idCenter, idCenter) ; 
			
			Node seed = gsGraph.getNode(idString);	
			seed.setAttribute("con", 1);
			
			break;			
		}
		
		case border : {	
			int randomDir = (int) Math.round( Math.random() );
			String idString = null;
			
			if (randomDir == 0) {
				int random = (int) ( Math.random() * gridSize );
				idString = convertIdToString(random , 0) ;
			}
			if (randomDir == 1) {
				int random = (int) ( Math.random() * gridSize );
				idString = convertIdToString( 0 , random) ;
			}													//			System.out.println(idString);
			
			Node seed = gsGraph.getNode(idString);	
			seed.setAttribute("con", 1);
			
			break;
			}
		}
	}

	
//-----------------------------------------------------------------------------------------------------	
	// PRIVATE METHODS 
	private String convertIdToString( int x , int y ) {
		
		String idString = (String) (x + "_" + y);
		return idString;
	}

	




}