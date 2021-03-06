package RdmGsaNet_setupLayer;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import RdmGsaNet_mainSim.layerNet.meanPointPlace;

public interface setupNet_Inter {
	
	// COSTANTS
	 meanPointPlace point = null ;
	

	// method for createLayer
	void createLayerNet (  );

	void setMeanPoint(meanPointPlace point) ;

	boolean getSetMorpOnlyCenter ( ) ;
	
//-----------------------------------------------------------------------------------------------------	
	static void setMeanPointInter(Graph gsGraph , meanPointPlace point ) {

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
			}													
			
			Node seed = gsGraph.getNode(idString);	
			seed.setAttribute("con", 1);
			
			break;
			}
		}
	}
	
// PRIVATE METHODS ------------------------------------------------------------------------------------------------------------------------
	static String convertIdToString( int x , int y ) {	
		String idString = (String) (x + "_" + y);
		return idString;
	}

// GET METHODS ----------------------------------------------------------------------------------------------------------------------------	
	public static String getMeanPointPlace ( ) { return point.getClass().getSimpleName(); }

	public static String getMeanPointStr ( Graph graph ) {
		
		String idMeanPoint = null ;
		
		for ( Node nGs : graph.getEachNode()) {
			int con = nGs.getAttribute("con") ;
			if (  con == 1 ) 
				idMeanPoint = nGs.getId();	
		}
		return idMeanPoint ;
	}

	
}

