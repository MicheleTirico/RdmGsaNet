package RdmGsaNet_pr09;

import java.util.ArrayList;
import java.util.Iterator;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNet_pr08.layerNet.meanPointPlace;

public class setupNetSmallGrid implements setupNet_Inter {

	// COSTANTS
	// get graphs
	private static Graph gsGraph = layerGs.getGraph() ,
							netGraph = layerNet.getGraph() ;
		
	// type grid
	public enum typeGrid { grid4 , grid8 }
	private static typeGrid type;
	
	// COSTRUCTOR
	public setupNetSmallGrid( typeGrid type) {
		this.type = type ;
	}
	
	public void createLayerNet() {												//	System.out.println("create small grid");
		
		// list id gs nodes  ( con == 1 )
		ArrayList<String> listIdGsCon = new ArrayList<String> () ;
				
		// create list of id with con = 1 => that means we create a list of meanPoint
		for ( Node nGs : gsGraph.getEachNode()) {
			int con = nGs.getAttribute("con") ;
				if (  con == 1 ) {
					listIdGsCon.add(nGs.getId());	}	
		}		//	System.out.println(listIdGsCon);
				
		// create seed node in netGraph and set coordinate
		for ( String id : listIdGsCon ) {
					
			// get node in gsGraph with con == 1
			Node nGs = gsGraph.getNode(id);
					
			// get array of coordinate of node gs with con == 1
			double [] nGsCoordinate = GraphPosLengthUtils.nodePosition(nGs) ;						//	System.out.println(nGsCoordinate[0]);
					
			// create node in netGraph
			netGraph.addNode(id);
					
			// set coordinate of node in netGraph
			Node nNet = netGraph.getNode(id); 														//	System.out.println(nNet.getId());
			nNet.setAttribute( "xyz", nGsCoordinate[0] , nGsCoordinate[1] , nGsCoordinate[2] );		//	double [] nNetCoordinate = GraphPosLengthUtils.nodePosition(nNet) ;			System.out.println(nNetCoordinate[0]);
		
			ArrayList<String> neigList = new ArrayList<String>();
			
			switch (type) {
			case grid4:  {	neigList = getListInRadiusGeom(netGraph, nNet, 1.01); 	}	break;
			case grid8: {	neigList = getListInRadiusGeom(netGraph, nNet, 1.5);	}	break;
			}
			
		for ( String idNeig : neigList ) {	
			try 													{ 	netGraph.addEdge(  nNet.getId() + "-" + idNeig ,  nNet , netGraph.getNode(idNeig) );	}
			catch (org.graphstream.graph.EdgeRejectedException e) 	{	continue;	}
			}	
		}	
	}
	
	private static ArrayList<String> getListInRadiusGeom (Graph graph, Node n , double radius ) {
		
		ArrayList<String> arrNeig = new ArrayList<String> ();
		
		for ( Node neig : graph.getEachNode()) {
			double dist = RdmGsaNetAlgo.gsAlgoToolkit.getDistGeom(n, neig);
			if ( dist < radius ) 
				arrNeig.add(neig.getId());
		}
		return arrNeig ;
	}

	@Override
	public void setMeanPoint(meanPointPlace point) {
		// TODO Auto-generated method stub
		
	}
}
