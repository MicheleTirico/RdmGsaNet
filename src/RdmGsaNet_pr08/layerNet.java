package RdmGsaNet_pr08;

import java.io.IOException;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSinkDGS;

public class layerNet {	
	
	// COSTANTS
	static FileSinkDGS fsd = new FileSinkDGS();
	
	// initialization graph net
	private static Graph netGraph = new SingleGraph("netGraph");
	private static Graph gsGraph = layerGs.getGraph();
	
	public enum meanPointPlace { center , random , border }
	meanPointPlace point ;
	
	private setupNetInter layout;
	
	// COSTRUCTOR
	public layerNet (setupNetInter layout ) {
		this.layout = layout ;
	}
	
	// method to change layer setup
	public void changeLayer ( setupNetInter layout) {
		this.layout = layout ;
	}
	
	// method to create layer Net
	public void createLayer ( 	boolean createMeanPoint , meanPointPlace point , 
								boolean setSeedMorp , 
								double seedAct , double seedInh , 
								boolean setSeedMorpInGs ,
								boolean storedDGS) throws IOException {
		
		// setup parameter of first point in netGraph 
		if ( createMeanPoint == true ) {layout.setMeanPoint ( point ) ; }
		
		// create mean point in netLayer
		layout.createLayerNet ();
		
		// set default values of net graph
		setDefaultAtr () ;
		
		// set morphogens in netGraph
		if (setSeedMorp == true ) { setSeedMorp (  seedAct ,  seedInh ); }
	
		if ( setSeedMorpInGs = true ) { setSeedMorpInGs ( ) ; }
		
		// stored code
		if ( storedDGS == true ) 	{ 	 
			String fileType = main.getFileType();
			String nameFile = 	"layerNetStart" +
								"_meanPoint_" + point +
								"_seedAct_" + seedAct +
								"_seedInh_" + seedInh ;
			
			String nameFileStart = nameFile ;
			String folderStart = main.getFolderStartGs();
			String pathStart = folderStart + nameFileStart + fileType;
			
			netGraph.write(fsd, pathStart);	}
	}
		
// PRIVATE METHODS-----------------------------------------------------------------------------------------------------	
	// method to set default values to network
	private static void setDefaultAtr ( ) {
		for ( Node n : netGraph.getEachNode() ) {
			n.addAttribute( "seedAct" , 0 );
			n.addAttribute( "seedInh" , 0 );			
			n.addAttribute( "con" , 0 );
			n.addAttribute( "seedGrad" , 0 );
			n.addAttribute("oldSeedGrad", 0 );
		
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
 			
			Node nGs = gsGraph.getNode( idNet );
			nGs.setAttribute("gsAct", seedAct );
			nGs.setAttribute("gsInh", seedInh );
		}
		
		
		
	}
	
// Get Methods -----------------------------------------------------------------------------------------------------	
	// get graph
	public static Graph getGraph ( ) { return netGraph; }

	
	
	
}