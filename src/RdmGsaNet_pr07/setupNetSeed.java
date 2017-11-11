package RdmGsaNet_pr07;

import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.GridGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.lang.Math;
import java.util.Random;

public class setupNetSeed implements setupNetInter{
		
	meanPointPlace point ;

	public void setupNetCreate() {
		System.out.println("hello seed");		
	}
	
	public void createLayerNet() {	
		setupNetInter.createLayer( );
	}

	public void setGsAtr(Graph graph, meanPointPlace point) {
		
		setupNetInter.setDefaultConnectionNode (graph, 0 );
		int gridSize = setupGsGrid.getGsGridSize();
		
//		System.out.println(point);

		switch (point) {
		case random: { 

			int randomX = (int) ( Math.random() * gridSize );
			int randomY = (int) ( Math.random() * gridSize );
			
			String idString = convertIdToString(randomX , randomY) ;
			
			Node seed = graph.getNode(idString);
			seed.setAttribute("con", 1);
			
			break;
		}

		case center: {

			int idCenter = (int)  Math.floor(gridSize / 2) ;
			
			String idString = convertIdToString(idCenter, idCenter) ; 
			
			Node seed = graph.getNode(idString);	
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
			}
			
//			System.out.println(idString);
			Node seed = graph.getNode(idString);	
			seed.setAttribute("con", 1);
			
			break;
			}
		}
	}

	public void setNetAtr() {
		
		
	}

//-----------------------------------------------------------------------------------------------------	
	// PRIVATE METHODS 

	private String convertIdToString( int x , int y ) {
		
		String idString = (String) (x + "_" + y);
		return idString;
	}



}
