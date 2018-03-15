package RdmGsaNet_vectorField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNetAlgo.graphToolkit;
import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNetAlgo.graphToolkit.elementTypeToReturn;
import RdmGsaNet_vectorField.vectorField.weigthDist;

public class vectorFieldSpatial extends vectorField implements vectorField_inter {

	public vectorFieldSpatial(Graph graph, String attribute ) {
		super( graph, attribute , vfType );
	}

	
	@Override
	public void computeVf( vfNeig vfN , weigthDist wdType ) {
		
		ArrayList<Node> listForVf = new ArrayList<Node> ( ) ;
		
		for ( Node nGra : graph.getEachNode() ) {
			
			String idnGra = nGra.getId( ) ;
			double[] nGraCoord = GraphPosLengthUtils.nodePosition( nGra ) ; 
			
			double 	graVal = nGra.getAttribute(attribute)  ,
					nGraCoordX = nGraCoord[0] , 
					nGraCoordY = nGraCoord[1] ; 
			
			// compute list of Nodes to compute vector
			switch (vfN) {
			case inRadius : 
				
				break;

			case onlyNeig : 
				listForVf = graphToolkit.getListNeighbor ( graph, idnGra, elementTypeToReturn.element );
				break;
			}

			int sizeListForVf = listForVf.size() ;
			
			// create map id - val and id - coord
			Map < Node , Double > mapIdVal = new HashMap< Node , Double > (sizeListForVf);
			Map < Node , double[] > mapIdCoord = new HashMap< Node , double[] > (sizeListForVf);
			
			// update maps
			for ( Node nNeig : listForVf ) {
				double[] nNeigCoord = GraphPosLengthUtils.nodePosition( nNeig ) ;
				double val = graph.getAttribute(attribute) ;
				mapIdCoord.put(nNeig, nNeigCoord ) ;
				mapIdVal.put(nNeig, val) ;
			}
			
			double deltaIntenX = 0 , deltaIntenY = 0 , deltaInten = 0 ; 
			
			for ( Node idnNeig : listForVf ) {
				
				double 	neigVal = mapIdVal.get(idnNeig) ,
						deltaVal = graVal - neigVal ;
				
				double[] neigCoord = mapIdCoord.get(idnNeig) ; 
		
				double	distX = Math.abs(nGraCoord[0] - neigCoord[0] ) ,
						distY = Math.abs(nGraCoord[1] - neigCoord[1] ) ,
						dist  = Math.pow ( Math.pow(distX, 2) + Math.pow(distY, 2) , 0.5 ) ; 
				
				double 	coefWeig = getCoefWeig ( wdType, dist  ) ,
						inten = deltaVal * coefWeig ; 
			
				double 	intenX = inten * distX / dist , 
						intenY = inten * distY / dist ;
				
				// update 
				deltaInten  = deltaInten  + inten  ; 
				deltaIntenX = deltaIntenX + intenX ;
				deltaIntenY = deltaIntenY + intenY ;
			}
			
//			Node nVec = vecGraph.getNode(idnGra) ;
//			nVec.addAttribute("inten", deltaInten);
//			nVec.addAttribute("intenX", deltaIntenX);
//			nVec.addAttribute("intenY", deltaIntenY);
			
		}
		
	}

	@Override
	public void getVector ( Node n ) {
		// TODO Auto-generated method stub
	}

	@Override
	public void test() {
		// TODO Auto-generated method stub
		
	}

	

	
	

}
