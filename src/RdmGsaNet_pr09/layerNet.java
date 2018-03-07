package RdmGsaNet_pr09;

import java.io.IOException;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSinkDGS;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNetExport.handleNameFile;

public class layerNet {	
	
	// COSTANTS
	static FileSinkDGS fsd = new FileSinkDGS();
	private handleNameFile handle = main.getHandle();
	
	// initialization graph net
	private static Graph netGraph = new SingleGraph("netGraph"),
							gsGraph = layerGs.getGraph();
	
	public enum meanPointPlace { center , random , border }
	private static meanPointPlace point ;
	
	private static setupNet_Inter layout;
	
	// COSTRUCTOR
	public layerNet (setupNet_Inter layout ) {
		this.layout = layout ;
	}
	
	// method to change layer setup
	public void changeLayer ( setupNet_Inter layout) {
		this.layout = layout ;
	}
	
	// method to create layer Net
	public void createLayer ( 	boolean createMeanPoint , meanPointPlace point , 
								boolean setSeedMorp , 
								double seedAct , double seedInh , 
								boolean setSeedMorpInGs ,
								boolean storedDGS ) throws IOException {
		
		// setup parameter of first point in netGraph 
		if ( createMeanPoint ) 
			layout.setMeanPoint ( point ) ; 
		
		// create mean point in netLayer
		layout.createLayerNet ();
		
		// set default values of net graph
		setDefaultAtr () ;
		
		// set morphogens in netGraph
		if (setSeedMorp ) 
			setSeedMorp (  seedAct ,  seedInh ); 
	
		if ( setSeedMorpInGs )  
			setSeedMorpInGs ( ) ; 
		
		// set length
		gsAlgoToolkit.setWeigth(netGraph);
		 
		// stored code
		if ( storedDGS ) 	{ 	
			String pathStart = handle.getPathStartNet();
			netGraph.write(fsd, pathStart);	}
	}
		
// PRIVATE METHODS-----------------------------------------------------------------------------------------------------	
	// method to set default values to network
	private static void setDefaultAtr ( ) {
		
		for ( Node n : netGraph.getEachNode() ) {
			n.addAttribute( "seedAct" , 0 );
			n.addAttribute( "seedInh" , 0 );			
			n.addAttribute( "seedGrad" , 0 );
		}
	}
	
	// method to add morp seed to net
	private void setSeedMorp ( double seedAct , double seedInh ) {
		
		// ask mean nodes ad add seed attributes of morphogens
		for ( Node nNet : netGraph.getEachNode()) {
			nNet.addAttribute( "seedAct" , seedAct );
			nNet.addAttribute( "seedInh" , seedInh );
		}	
	}
	
	private void setSeedMorpInGs () {
		
		for ( Node nNet : netGraph.getEachNode()) {
			
			String idNet = nNet.getId() ;
			double seedAct = nNet.getAttribute( "seedAct" );
			double seedInh = nNet.getAttribute( "seedInh" );
 			
			double[] nNetCoord = GraphPosLengthUtils.nodePosition( nNet) ;
			try {
				Node nGs = gsGraph.getNode( (int) nNetCoord[0] + "_" + (int) nNetCoord[1] );
			
				nGs.setAttribute("gsAct", seedAct );
				nGs.setAttribute("gsInh", seedInh );
			}
		
			catch (java.lang.NullPointerException e) {
				// TODO: handle exception
			}
		}
	}
	
	
// Get Methods -----------------------------------------------------------------------------------------------------	
	// get graph
	public static Graph getGraph ( ) { return netGraph; }

	

	
	public static String getLayout () { return layout.getClass().getSimpleName() ; }
	
}