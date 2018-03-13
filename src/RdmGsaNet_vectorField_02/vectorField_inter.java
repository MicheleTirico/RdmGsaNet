package RdmGsaNet_vectorField_02;

import java.io.IOException;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.stream.file.FileSinkDGS;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNetExport.handleNameFile;
import RdmGsaNetExport.handleNameFile.typeFile;
import RdmGsaNet_mainSim.main;
import RdmGsaNet_vectorField_02.vectorField.vfNeig;
import RdmGsaNet_vectorField_02.vectorField.weigthDist;

public interface vectorField_inter {
	
	// STORING GRAPH EVENTS
	static FileSinkDGS fsd = new FileSinkDGS();
	handleNameFile handle = main.getHandle(); 
	static String folder = main.getFolder();
	
	public void test ( ) ; 
	
	public void computeVf ( vfNeig vfN , weigthDist wdType , Graph vecGraph , boolean doStoreStepVec ) throws IOException ;

	public void updateVector (  Graph graph , Graph vecGraph ) ;
	
	public void createVector ( Graph vecGraph ) ;
	

	
// static methods -----------------------------------------------------------------------------------------------------------------------------------	
	public static void createGraph(Graph graph , Graph vecGraph , boolean doStoreStartVec ) throws IOException  {
		
		String pathStart = handle.getPathFile(typeFile.startVec, false , folder) ; 				//		System.out.println(pathStart);
		
		for ( Node n0 : graph.getEachNode() ) {													//	System.out.println(n0.getId());
		
			String  idn1StVec  = n0.getId() ,
					idn1EndVec = n0.getId() + "_endVec" ;
			
			vecGraph.addNode( idn1StVec  ) ;
			vecGraph.addNode( idn1EndVec ) ;
			
			Node 	n1StVec = vecGraph.getNode(idn1StVec) ,
					n1EndVec = vecGraph.getNode(idn1EndVec) ;
			
			gsAlgoToolkit.setNodeCoordinateFromNode(graph, vecGraph, n0, n1StVec);
			gsAlgoToolkit.setNodeCoordinateFromNode(graph, vecGraph, n0, n1EndVec);
			
			String idEdge = n0.getId() + "_vec" ; 
			
			vecGraph.addEdge(idEdge, n1StVec, n1EndVec , true) ;
			Edge e = vecGraph.getEdge(idEdge);
			
			addAttributeNode(n1StVec, true);
			addAttributeNode(n1EndVec, false);
			addAttributeEdge(e);
			
				
		}
		if ( doStoreStartVec )
			vecGraph.write(fsd, pathStart);
		
	}



// "private" methods ----------------------------------------------------------------------------------------------------------------------------------
	
	public static void addAttributeNode ( Node n , boolean isOriginVec ) {
		n.addAttribute("inten", 0.0);
		n.addAttribute("intenX", 0.0);
		n.addAttribute("intenY", 0.0);
		n.addAttribute("originVector", isOriginVec );
	}
	
	public static void addAttributeEdge ( Edge e  ) {
		e.addAttribute("inten", 0.0);
	
	}
	

}
