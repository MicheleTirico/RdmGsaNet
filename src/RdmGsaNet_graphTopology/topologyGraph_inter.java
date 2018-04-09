package RdmGsaNet_graphTopology;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.stream.file.FileSinkDGS;

import RdmGsaNetExport.handleNameFile;
import RdmGsaNet_mainSim.main;


public interface topologyGraph_inter {
	
	// STORING GRAPH EVENTS
	static FileSinkDGS fsd = new FileSinkDGS();
	handleNameFile handle = main.getHandle(); 
	static String folder = main.getFolder();

	// test
	public void test ( ) ; 
	
	
	public void createGeometryOriGraph () ;
	
	public void createGraph () ;
			
	public void updateGeometryOriGraph(  int step , Map < Double , ArrayList<String> > mapStepNewNodeId  ) ;

	public void updateGraph () ;
	

	
	
	

}
