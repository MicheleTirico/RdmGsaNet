package RdmGsaNet_vectorField_02;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNetAlgo.graphToolkit;
import RdmGsaNetAlgo.graphToolkit.elementTypeToReturn;
import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNet_vectorField_02.vectorField.vfNeig;
import RdmGsaNet_vectorField_02.vectorField.weigthDist;
import jdk.nashorn.internal.runtime.StoredScript;

public class vectorFieldSpatial implements vectorField_inter {

	private Graph graph ;
	private String attribute ;

	
	public vectorFieldSpatial ( Graph graph , String attribute ) {
		this.graph = graph ;
		this.attribute = attribute ; 
	}
		
	@Override
	public void test() {
		System.out.println("test"); //		for ( Node n : graph.getEachNode())  {//			System.out.println(n.getAttributeKeySet());//			double val = n.getAttribute(attribute);//			System.out.println(val);}	
	}

	@Override
	public void computeVf( vfNeig vfN, weigthDist wdType , Graph vecGraph , boolean doStoreStepVec ) throws IOException {
		
		ArrayList<Node> listForVf = new ArrayList<Node> ( ) ;
		
		for ( Node nGra : graph.getEachNode() ) {
			
			// get node
			String idn1StVec  = nGra.getId() ;
			String idn1EndVec = nGra.getId() + "_endVec" ;				//	System.out.println(vecGraph.getNodeSet());
			
			Node n1stVec = vecGraph.getNode(idn1StVec) ;
			Node n1EndVec = vecGraph.getNode(idn1EndVec) ;
						
			String idnGra = nGra.getId( ) ;
			double[] nGraCoord = GraphPosLengthUtils.nodePosition( nGra ) ; 
			
			double 	graVal = nGra.getAttribute(attribute)  ; //	
			
			if ( graVal > 1 )
				graVal = 1 ;
			if ( graVal < 0 )
				graVal = 0 ;											//	System.out.println("graVal " + graVal);
			
			// compute list of Nodes to compute vector
			switch (vfN) {
			case inRadius : 
				
				break;

			case onlyNeig : 
				listForVf = graphToolkit.getListNeighbor ( graph, idnGra, elementTypeToReturn.element );	//	System.out.println(listForVf);
				break;
			}		
			
			// create map id - val and id - coord
			int sizeListForVf = listForVf.size() ;
			Map < Node , Double > mapIdVal = new HashMap< Node , Double > (sizeListForVf);
			Map < Node , double[] > mapIdCoord = new HashMap< Node , double[] > (sizeListForVf);
			
			Map < Node , Double > mapIdInten = new HashMap< Node , Double > (sizeListForVf);
			
			// update maps
			for ( Node nNeig : listForVf ) {
				
				double[] nNeigCoord = GraphPosLengthUtils.nodePosition( nNeig ) ; 	// 	System.out.println(nNeig.getAttributeKeySet() );
				double val = nNeig.getAttribute(attribute) ; 						// 	System.out.println(val);
				
				if ( val > 1 ) 		val = 1 ;
				if ( val < 0 ) 		val = 0 ;
				
				mapIdCoord.put(nNeig, nNeigCoord ) ;
				mapIdVal.put(nNeig, val) ;
			}																		//	System.out.println(idnGra + " " + mapIdVal);
			
			double deltaIntenX = 0 , deltaIntenY = 0 , deltaInten = 0 ; 
			
			for ( Node idnNeig : listForVf ) {
				
				double 	neigVal = mapIdVal.get(idnNeig) ,
						deltaVal =  graVal * neigVal ;
				
				double[] neigCoord = mapIdCoord.get(idnNeig) ; 
		
				double	distX = nGraCoord[0] - neigCoord[0] , // Math.abs(nGraCoord[0] - neigCoord[0] ) ,
						distY = nGraCoord[1] - neigCoord[1] , // Math.abs(nGraCoord[1] - neigCoord[1] ) ,
						dist  = Math.pow ( Math.pow(distX, 2) + Math.pow(distY, 2) , 0.5 ) ; 
				
				double 	coefWeig = vectorField.getCoefWeig ( wdType , dist  ) ,
						inten = 0.0 ;
				
				if ( graVal > neigVal )
					inten = - deltaVal * coefWeig ; 
				else if ( graVal < neigVal )
					inten = + deltaVal * coefWeig ; 
				 
				double 	intenX = inten * distX / dist , 
						intenY = inten * distY / dist ;
				
				// update 
				deltaInten  = deltaInten  + inten  ; 
				deltaIntenX = deltaIntenX + intenX ;
				deltaIntenY = deltaIntenY + intenY ;		//		System.out.println(deltaInten );
				mapIdInten.put( idnNeig, inten );
			}
			
			
			n1stVec.addAttribute("inten", deltaInten);
			n1stVec.addAttribute("intenX", deltaIntenX);
			n1stVec.addAttribute("intenY", deltaIntenY);
			n1stVec.addAttribute("originVector", true );		
		}
		
		if ( doStoreStepVec == true ) 	{ 	 
			String pathStart = handle.getPathStartNet();
			vecGraph.write(fsd, pathStart);	
		}
	}
	
	@Override
	public void createVector ( Graph vecGraph ) {
		
		int idEdge = 0 ; 
		
		// add new node
		for ( Node n0 : vecGraph.getEachNode() ) {
			
			boolean orV = n0.getAttribute("originVector");
			if (orV ) {						//	System.out.println(n0.getAttributeKeySet());	System.out.println(n0.getId());
				double[] n0Coord = GraphPosLengthUtils.nodePosition( n0 );
				
				double 	inten = n0.getAttribute("inten") ,
						intenX = n0.getAttribute("intenX") , 
						intenY = n0.getAttribute("intenY") ;
				
				String idN1 = n0.getId() + "_topVec" ;
				vecGraph.addNode( idN1 ) ;
				
				Node n1 = vecGraph.getNode(idN1) ;
				double 	n1CoordX = n0Coord[0] + intenX ,
						n1CoordY = n0Coord[1] + intenY ;
				
				n1.setAttribute("x", n1CoordX);
				n1.setAttribute("y", n1CoordY);
				
				n1.setAttribute("originVector", false );
				n1.setAttribute("inten" , inten ) ;
				n1.setAttribute("intenX" , intenX ) ;
				n1.setAttribute("intenY" , intenY ) ;
				
				vecGraph.addEdge(Integer.toString(idEdge), n0, n1 , true) ;
				
				idEdge++ ;			
				
			}		
		}
	}

	@Override
	public void updateVector(Graph graph , Graph vecGraph , double maxIntenVector ) {

		for ( Node nGs : graph.getEachNode() ) {
			
			// get node
			String idn1StVec  = nGs.getId() ;
			String idn1EndVec = nGs.getId() + "_endVec" ;
					
			Node n1stVec = vecGraph.getNode(idn1StVec) ;
			Node n1EndVec = vecGraph.getNode(idn1EndVec) ;
			
			double[] nGsCoord = GraphPosLengthUtils.nodePosition( nGs );
			
			double 	inten  = n1stVec.getAttribute("inten") ,
					intenX = n1stVec.getAttribute("intenX") , 
					intenY = n1stVec.getAttribute("intenY") ;
		
			double 	n1EndVecX = nGsCoord[0] + intenX ,
					n1EndVecY = nGsCoord[1] + intenY ;
			
			n1EndVec.setAttribute("x", n1EndVecX);
			n1EndVec.setAttribute("y", n1EndVecY);
		
			n1EndVec.setAttribute("inten" , inten ) ;
			n1EndVec.setAttribute("intenX" , intenX ) ;
			n1EndVec.setAttribute("intenY" , intenY ) ;		
			
			
		}
		for ( Edge e : vecGraph.getEachEdge() ) {
			double inten = e.getNode0().getAttribute("inten") ;
			e.setAttribute("inten", inten);
		}
		
	}
	
}
