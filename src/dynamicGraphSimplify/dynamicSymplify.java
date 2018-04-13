package dynamicGraphSimplify;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNetAlgo.graphToolkit;
import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNetAlgo.graphToolkit.element;
import RdmGsaNetAlgo.graphToolkit.elementTypeToReturn;

public class dynamicSymplify {
	
	protected Map<String , String > mapFather = new HashMap < String, String> ( ) ;
	
	protected Graph netGraph = new SingleGraph("netGraph") ,
					seedGraph = new SingleGraph("seedGraph") ;
	
	protected double epsilon ;
	private boolean runSymplify ;
	
	// setup pivot
	private boolean createPivot ; 
	private double maxDistPivot ;
	
	public enum simplifyType { deleteNode , test, kNearestNeighbors } ;
	protected static simplifyType simplifyType ;
	
	// interface object
	 dynamicSymplify_inter dsInter;
	
	// constructor
	public dynamicSymplify( boolean runSymplify ,  Graph netGraph  , Graph seedGraph ,double epsilon , simplifyType simplifyType  ) {
		
		this.runSymplify = runSymplify ;
		this.netGraph  = netGraph  ;
		this.seedGraph = seedGraph ;
		this.epsilon = epsilon ;
	//	this.simplifyType = simplifyType ;
		
		switch (simplifyType ) {
		
		case deleteNode :
			dsInter =  new dynamicSymplify_deleteNode( netGraph  ,  epsilon  );
			break;
		
		case kNearestNeighbors :
			dsInter =  new dynamicSymplify_kNearestNeighbors ( netGraph  , seedGraph ,  epsilon  );
			break;
				
		}
	}
	
	public void setParameters_Pivot ( boolean createPivot , double maxDistPivot ) {
		
		this.createPivot = createPivot ;
		this.maxDistPivot = maxDistPivot ;
	}
	
	public void computeTest(  ) {
		dsInter.test();
	}
	
	
	public void compute (  int step ) {				//	System.out.println(graph.getNodeCount() + " "  + graph.getNodeSet());
		
		if ( runSymplify == false )
			return ;
	
		dsInter.updateFatherAttribute( step , mapFather );
		dsInter.handleGraphGenerator( step );
		
		if ( createPivot ) 
			setPivot ( createPivot , maxDistPivot ) ;
	}
	
	public void setPivot(boolean createPivot, double maxDistPivot) {
		
		if ( ! createPivot )
			return ; 
		
		ArrayList<String> listIdSeed = new ArrayList<String>( graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.string)) ;	
		ArrayList<Integer> listIdEdgeInt = new ArrayList<Integer>( graphToolkit.getListElement(netGraph, element.edge, elementTypeToReturn.integer)) ;		
		ArrayList<Integer> listIdNetInt = new ArrayList<Integer>( graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.integer)) ;
		
		for ( String idSeed : listIdSeed ) {
			
			Node nodeSeedNet = netGraph.getNode(idSeed) ;
			ArrayList<String> listIdNeig = new ArrayList<String> (graphToolkit.getListNeighbor( netGraph, idSeed, elementTypeToReturn.string)) ;
			
			if (listIdNeig.isEmpty())
				continue ;
			
			for ( String idNeig : listIdNeig ) {
				
				Node nodeNeig = netGraph.getNode(idNeig);
			
				double distNeig = gsAlgoToolkit.getDistGeom( nodeSeedNet , nodeNeig ) ;
			
				if ( distNeig < maxDistPivot )
					continue ;
				
				else if ( distNeig >= maxDistPivot ) {		// System.out.println("distNeig " + distNeig);//	System.out.println("numPivot " + numPivot);
					
					// compute coord of pivot
					double[] 	pivotCoord = new double [2] ,
								distNeigAx = new double[2] ;	
					
					double[] 	seedCoord = GraphPosLengthUtils.nodePosition(nodeSeedNet),
								neigCoord = GraphPosLengthUtils.nodePosition(nodeNeig);
							
					distNeigAx[0] = Math.abs(seedCoord[0] - neigCoord[0] );
					distNeigAx[1] = Math.abs(seedCoord[1] - neigCoord[1] );
				
					pivotCoord [0] = Math.min (seedCoord[0] , neigCoord[0] ) + distNeigAx[0] / 2  ;
					pivotCoord [1] = Math.min (seedCoord[1] , neigCoord[1] ) + distNeigAx[1] / 2  ;

					// get last id node
					listIdNetInt = new ArrayList<Integer>( graphToolkit.getListElement(netGraph, element.node, elementTypeToReturn.integer)) ;
					int idPivotInt = Collections.max(listIdNetInt) + 1 ;
					
					while ( listIdNetInt.contains(idPivotInt))
						idPivotInt++;
					
					// create pivot
					String idPivot = Integer.toString(idPivotInt);
					netGraph.addNode(idPivot);
		
					// set coord of pivot
					Node nodePivot = netGraph.getNode(idPivot) ;
					nodePivot.setAttribute ( "xyz", pivotCoord [0] , pivotCoord [1] , 0 ) ;
					
					// get last id edge
					listIdEdgeInt = new ArrayList<Integer>( graphToolkit.getListElement(netGraph, element.edge, elementTypeToReturn.integer)) ;		
					int idEdgeInt = Collections.max(listIdEdgeInt) ;
					while ( listIdEdgeInt.contains(idEdgeInt))
						idEdgeInt++;
					
					// add first edge
					String idEdge = Integer.toString(idEdgeInt);
					netGraph.addEdge(idEdge, nodePivot, nodeSeedNet);
					
					// add second edge
					idEdgeInt++;
					idEdge = Integer.toString(idEdgeInt);
					netGraph.addEdge(idEdge, nodePivot, nodeNeig);
					
					// remove edge
					Edge edge = nodeSeedNet.getEdgeBetween(idNeig);
					netGraph.removeEdge(edge);
				}			
			}			
		}		
	}
}
