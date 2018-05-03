package RdmGsaNet_generateGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNet_mainSim.layerNet;
import RdmGsaNet_mainSim.main;
import RdmGsaNet_staticBuckets_02.bucketSet;

public class generateNetEdge extends main {
	
	//  pivot parameters
	private static boolean createPivot ;
	private static double maxDistPivot ;
	
	private static generateNetEdge_Inter type ;
	private static generateNetEdge growth ;
	
	public  enum genEdgeType { onlyFather , fatherAndNodeInRadius , fatherAndNearEdge }
	public genEdgeType genEdgeType ;
	
	public generateNetEdge ( generateNetEdge_Inter type ) {
		this.type = type ;
	}
	
	public void generateEdge ( double step ) {
		type.generateEdgeRule ( step ) ;

	}
	
	public void setParameters_Pivot ( boolean createPivot , double maxDistPivot ) {
	
		this.createPivot = createPivot ;
		this.maxDistPivot = maxDistPivot ;
	}

//--------------------------------------------------------------------------------------------------------------------
	// private methods
	
	// method to create one edge for each nodes in set and the new node
			// set id edge like idNode1 - idNode2 ( doesn't work )
			protected static void createEdgeInSetNear ( Node n1 , Set<String> idNear , Graph graph ) {
				
				// declare id for new node
				String idN1 = n1.getId();													//		System.out.println("idN1 " + n1.getId() ) ;
				
				// create an edge for each new node
				for ( String idN2 : idNear) {
					
					Node n2 = graph.getNode(idN2) ;											//		System.out.println("idN2 " + n2.getId() ) ;
					
					// try create an edge. It return exception whether nodes are yet connected -> continue
					try 														{ graph.addEdge(  idN1 + "-" + idN2 ,  n1 , n2 );/* System.out.println(graph.getEdge(idN1 + "-" + idN2)) */ ;	}
					catch ( org.graphstream.graph.EdgeRejectedException e  ) 	{ 
						e.getMessage(); 
						continue ; 
					}
					catch ( org.graphstream.graph.IdAlreadyInUseException e)	{
						e.getMessage(); 
						continue ; 
					}
				}	  	
			}
			
		// method to create map of distance
				// map / key = (string) id of nodes n2 , (double) distance between n1 and n2
			protected static Map <String , Double> getMapIdDist( Graph graph , Node n1 ) {
				
				// Initialized map of distances
				Map <String , Double> mapDist = new HashMap<String, Double> ( ) ; 
				
				// iteration
				Iterator<Node> iterNode = graph.getNodeIterator();
				
				while (iterNode.hasNext()) {

					Node n2 = iterNode.next();
					
					String n2Str = n2.getId();
					String n1Str = n1.getId();
					
					if ( n2.getId() != n1Str ) 	
						mapDist.put(n2Str, gsAlgoToolkit.getDistGeom(n1, n2)) ; 
					}
				return mapDist ;
				}
			
			protected static Map <String , Double> getMapIdDistInBucketSet ( Node n1 , bucketSet bucketSet ) {
				
				// Initialized map of distances
				Map <String , Double> mapDist = new HashMap<String, Double> ( ) ; 
				
				for ( Node n2 : bucketSet.getListNodeBuffer(n1) ) {
					
					String n2Str = n2.getId();
					String n1Str = n1.getId();
					
					if ( n2.getId() != n1Str ) 	
						mapDist.put(n2Str, gsAlgoToolkit.getDistGeom(n1, n2)) ;
				}
				return mapDist ;
				}
			
		// method to get min value in list from a map
			protected static double getMinDist ( Map <String , Double>  map ) {
			
				ArrayList<Double> list = new ArrayList<Double>() ;
				list.addAll(map.values()); 														//	System.out.println(list);
				
				// calculate min distance
				double minDist = Collections.min(list) ;										//	System.out.println(minDist);
				
				return minDist;	
			}	
			
	
	public static generateNetEdge 	getGrowthNet () 	{ return growth ; }

	public static String 			getGenerateType () 	{ return type.getClass().getSimpleName(); }


	public static boolean getCreatePivot ( ) {
		return createPivot ; 
	}
	
	static double getMaxDistPivot () {
		return maxDistPivot ;
	}

}