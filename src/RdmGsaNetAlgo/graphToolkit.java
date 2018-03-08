package RdmGsaNetAlgo;

import java.util.ArrayList;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;


public class graphToolkit {
	
	public enum elementTypeToReturn { element , string }
	public elementTypeToReturn elementType ;
	
	public enum element { node , edge }
	public element element ;
	
	// get list of nodes 
	public static ArrayList getListElement ( Graph graph , element element , elementTypeToReturn elementTypeToReturn  ) {
		ArrayList list = new ArrayList () ;
		
		switch (element) {
			case edge: {
				if ( elementTypeToReturn.equals(elementTypeToReturn.element) )
					for ( Edge e : graph.getEachEdge() )
						list.add(e);
				
				else if ( elementTypeToReturn.equals(elementTypeToReturn.string) )
					for ( Edge e : graph.getEachEdge() )
						list.add(e.getId());
			}	break;
			
			case node : {
				if ( elementTypeToReturn.equals(elementTypeToReturn.element) )
					for ( Node n : graph.getEachNode() )
						list.add(n);
			
				else if ( elementTypeToReturn.equals(elementTypeToReturn.string) )
					for ( Node n : graph.getEachNode() )
						list.add(n.getId());
			}	break;
		}
		return list ;
	}
	
	// get list of elements whit value of attribute like answer	
	public static ArrayList getListElementAttribute (  Graph graph , element element , elementTypeToReturn elementTypeToReturn , String attribute , int valAtr ) {
		
		ArrayList list = new ArrayList () ;
		
		switch (element) {
			case edge: {
				if ( elementTypeToReturn.equals(elementTypeToReturn.element) ) {
					for ( Edge e : graph.getEachEdge() ) {
						int attrTest = e.getAttribute(attribute);
						if ( attrTest == valAtr ) 	
							list.add(e);	
					}
				}
				else if ( elementTypeToReturn.equals(elementTypeToReturn.string) ) {
					for ( Edge e : graph.getEachEdge() ) {
						int attrTest = e.getAttribute(attribute);
						if ( attrTest == valAtr ) 	
							list.add(e.getId());
					}
				}
			}	break;
			
			case node : {
				if ( elementTypeToReturn.equals(elementTypeToReturn.element) ) {
					for ( Node n : graph.getEachNode() ) {
						int attrTest = n.getAttribute(attribute);
						if ( attrTest == valAtr ) 	
							list.add(n);
				}
			}
				 else if ( elementTypeToReturn.equals(elementTypeToReturn.string) ) { 
					for ( Node n : graph.getEachNode() ) {
						int attrTest = n.getAttribute(attribute);
						if ( attrTest == valAtr ) 
							list.add(n.getId());
					}
				}
			}	break;
		}
		return list ;
	}
	
	// get list of nodes at vertex 
	public static ArrayList<String> getListVertexRoundCoord ( elementTypeToReturn elementTypeToReturn , Graph graphSeed , Graph graphVertex ,  String idSeed ) {

		ArrayList listVertex = new ArrayList();
		if ( elementTypeToReturn. equals(elementTypeToReturn.string)) {
			 listVertex = new ArrayList<String>(4);
		}
		else if  ( elementTypeToReturn. equals(elementTypeToReturn.element)) {
			listVertex = new ArrayList<Node>(4);
		}
		
		Node nodeSeed = graphSeed.getNode(idSeed), 
			 nodeMinVertex ;
	
		double [] nodeSeedCoordinate = GraphPosLengthUtils.nodePosition(nodeSeed) ;
		
		double 	xSeed = nodeSeedCoordinate[0], 
				ySeed = nodeSeedCoordinate[1];		
	
		int xMin = (int) xSeed,
			yMin = (int) ySeed;
		
		String 	idNodeMinVertex = xMin + "_" + yMin ,
				nodeXStr = null , nodeYStr = null , nodeXYStr = null;
		
		nodeMinVertex = graphVertex.getNode(idNodeMinVertex); 
		
		nodeXStr  = ( xMin )  +"_" + ( yMin + 1 ) ;
		nodeYStr  = ( xMin + 1 )  +"_" + ( yMin  ) ; 
		nodeXYStr = ( xMin + 1 )  +"_" + ( yMin + 1 ) ;	
			
		if ( elementTypeToReturn. equals(elementTypeToReturn.string)) {
			listVertex.add(nodeXStr) ;
			listVertex.add(nodeYStr) ;
			listVertex.add(nodeXYStr) ;
			listVertex.add( idNodeMinVertex ) ;
		}
		
		else if  ( elementTypeToReturn. equals(elementTypeToReturn.element)) {
			listVertex.add(graphVertex.getNode(nodeXStr) );
			listVertex.add(graphVertex.getNode(nodeYStr) );
			listVertex.add(graphVertex.getNode(nodeXYStr) );
			listVertex.add( graphVertex.getNode(idNodeMinVertex ) );
		}
		return listVertex ;
	}
		
	
	
}
