package RdmGsaNet_pr09;

import java.io.IOException;
import java.util.Random;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSinkDGS;

import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNetExport.handleNameFile;

public class layerGs {
	
	// STORING GRAPH EVENTS
	static FileSinkDGS fsd = new FileSinkDGS();
	private handleNameFile handle = main.getHandle();
	protected static boolean storeGsValues;
	
	// VARIABLES
	private static setupGs_Inter layout ;
	
	// create graph of reaction diffusion layer
	private static Graph graph = new SingleGraph("gsGraph");
	
	// COSTRUCTOR 
	public layerGs (setupGs_Inter layout) {
		this.layout = layout ;
	}
	
	// method to change gs layout ( never used ) 
	public void changeLayer ( setupGs_Inter layout) {
		this.layout = layout ;
	}
	
	// method to create layer gs
	public void createLayer ( boolean setCoordinate, boolean setDefaultAtr , boolean storedDGS , boolean storeGsValues ) throws IOException {
		layout.createLayerGs () ; 
		if (setCoordinate == true ) { layout.setCoordinate () ; }
		if (setDefaultAtr == true ) { setDefaultAtr () ; }	
		if ( storedDGS == true ) 	{ 	 
			String pathStart = handle.getPathStartGs();
			graph.write(fsd, pathStart);		
		}
		setEdgeLength();
		this.storeGsValues = storeGsValues ;
	}
			
	// methods to define characteristics of layer gs
	public void setupDisMorp ( setupGs_Inter.disMorpType type , int randomSeedAct, int randomSeedInh, double homoValAct , double homoValInh) {
		
		switch (type) {		
	
		case homo :
			System.out.println("distribution homogeneus / Act = " + homoValAct + " Inh = " + homoValInh );
			
			for ( Node n:graph.getEachNode() ) { n.setAttribute("gsAct" , homoValAct  ) ;}
			for ( Node n:graph.getEachNode() ) { n.setAttribute("gsInh" , homoValInh  ) ;}
			
			break;
			
		case random :
			System.out.println("distribution random / " + "randomSeedAct = " + randomSeedAct + " randomSeedInh = " + randomSeedInh );
			
			Random act = new Random( randomSeedAct );
			Random inh = new Random( randomSeedInh );
			
			for ( Node n:graph.getEachNode() ) { n.setAttribute("gsAct" , act.nextDouble()   ) ;}
			for ( Node n:graph.getEachNode() ) { n.setAttribute("gsInh" , inh.nextDouble()   ) ;}
			
			break;
			}
		}
	
//----------------------------------------------------------------------------------------------------------------------------------		
	private void setDefaultAtr ( ) {
		
		for ( Node n : graph.getEachNode() ) {

			n.addAttribute( "gsAct" , 0 );
			n.addAttribute( "gsInh" , 0 );
			n.addAttribute( "con" , 0 );
			if ( storeGsValues ) {
				n.setAttribute("gsRea", 0 );
				n.setAttribute("gsDifAct", 0 );
				n.setAttribute("gsDifInh", 0 );
				n.setAttribute("gsExtFeed", 0 );
				n.setAttribute("gsExtKill", 0 );
			}
		}
	}

	// set length attribute at each edge
	private static void setEdgeLength ( ) {
		for ( Edge e : graph.getEachEdge()) {
			double lengthEdge = gsAlgoToolkit.getDistGeom(e.getNode0(), e.getNode1() ) ;
			e.addAttribute("length", lengthEdge);
		}
	}

// GET METHODS ----------------------------------------------------------------------------------------------------------------------------
	public static Graph getGraph ( ) { return graph; }
	
	public static String getTypeGrid () { return layout.getClass().getSimpleName(); }
		
	public static String getLayout () { return layout.getClass().getSimpleName() ; }

}


