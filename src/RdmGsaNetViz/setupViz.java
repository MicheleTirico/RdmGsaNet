package RdmGsaNetViz;
import java.io.IOException;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSinkImages.LayoutPolicy;
import org.graphstream.stream.file.FileSinkImages.OutputType;
import org.graphstream.stream.file.FileSinkImages.Resolutions;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNet_mainSim.layerGs;
import RdmGsaNet_mainSim.layerNet;


public class setupViz {

	// GRAPHS
	static Graph gsGraph = layerGs.getGraph(), 
					netGraph = layerNet.getGraph();

	// method to return a screenshot
	public static void getScreenshot ( Graph graph, String file ) {
		graph.addAttribute("ui.screeshot", file );
	}
	
	// method to return an image 
	public static void  getImage (Graph graph ,String folder, String name ) throws IOException {
		FileSinkImages pic = new FileSinkImages(OutputType.PNG, Resolutions.VGA);	
		pic.setLayoutPolicy(LayoutPolicy.NO_LAYOUT ); 
		pic.writeAll(graph, folder +"/"+ name);
	}
	
// VIZ METHODS --------------------------------------------------------------------------------------------------------------------------------------
// methods of visualization
	public static void setViz ( Graph graph , String attribute , String VizStylesheet ) {
		
		graph.addAttribute("ui.stylesheet", VizStylesheet);
		
	}
	
	
	
	public static void Viz4Color( Graph graph ) {
		
		graph.addAttribute("ui.stylesheet", setViz4Color ( ) );

		for ( Node n : graph.getEachNode()) {
			double act = n.getAttribute("gsAct");
			double inh = n.getAttribute("gsInh");
			
			double color = 0  ; //grey
			
			if ( act >= 0.95 && inh >= 0.95) 	{	 color =  	.25		;	} 		// red
			if ( act >= 0.9 && inh <= 0.1) 		{	 color =  	.5		;	}		// yellow
			if ( act <= 0.01 && inh >= 0.99) 	{	 color =  	.75		;	}		// green
			if ( act <= 0.05 && inh <= 0.05) 	{	 color =  	1		;	}		// blue
			
			n.addAttribute("ui.color", color );
		}
	}
	
	public static void VizNodeId ( Graph graph ) {
		graph.addAttribute("ui.stylesheet", setVizNodeId () );
		
		for ( Node n : graph.getEachNode()) {
			n.addAttribute("ui.label", " " + n.getId());
		}
	}
	
	public static void Viz5Color( Graph graph , String morp   ) {
		
		
	}
	
	public static void Viz10Color( Graph graph , String morp  ) {
	
	graph.addAttribute("ui.stylesheet", setViz10Color ( ) );
	
	for ( Node n : graph.getEachNode()) {
		double act = n.getAttribute("gsAct");
		double inh = n.getAttribute("gsInh");
	
		double morpColor = 0 ;
		
		if ( morp == "gsAct" ) 	{ morpColor = act 	;	}
		else { if ( morp == "gsInh" ) 	{  morpColor = inh ;	} 
		
		double color = 0  ; //grey

		if ( morpColor >= 0.10 &&  morpColor <= 0.20) 	{	 color =  	0.1		;	} 		// 
		if ( morpColor >= 0.20 &&  morpColor <= 0.30)	{	 color =  	0.2		;	}		// 
		if ( morpColor >= 0.30 &&  morpColor <= 0.40) 	{	 color =  	0.3		;	}		// 
		if ( morpColor >= 0.40 &&  morpColor <= 0.50) 	{	 color =  	0.4		;	}		// 
		if ( morpColor >= 0.50 &&  morpColor <= 0.60) 	{	 color =  	0.5		;	} 		// 
		if ( morpColor >= 0.60 &&  morpColor <= 0.70)	{	 color =  	0.6		;	}		// 
		if ( morpColor >= 0.70 &&  morpColor <= 0.80) 	{	 color =  	0.7		;	}		// 
		if ( morpColor >= 0.80 &&  morpColor <= 0.90) 	{	 color =  	0.8		;	}		// 
		if ( morpColor >= 0.90 &&  morpColor <= 1.00) 	{	 color =  	0.9		;	}		// 
		
		n.addAttribute("ui.color", color );
		}
	}
}

	public static void Viz10ColorInh( Graph graph ) {
	
	graph.addAttribute("ui.stylesheet", setViz10Color ( ) );
	
	for ( Node n : graph.getEachNode()) {
	
		double inh = n.getAttribute("gsInh");	
		double morpColor = inh ;
		
		double color = 0  ; //grey
	
		if ( morpColor >= 0.10 &&  morpColor <= 0.20) 	{	 color =  	0.1		;	} 		// 
		if ( morpColor >= 0.20 &&  morpColor <= 0.30)	{	 color =  	0.2		;	}		// 
		if ( morpColor >= 0.30 &&  morpColor <= 0.40) 	{	 color =  	0.3		;	}		// 
		if ( morpColor >= 0.40 &&  morpColor <= 0.50) 	{	 color =  	0.4		;	}		// 
		if ( morpColor >= 0.50 &&  morpColor <= 0.60) 	{	 color =  	0.5		;	} 		// 
		if ( morpColor >= 0.60 &&  morpColor <= 0.70)	{	 color =  	0.6		;	}		// 
		if ( morpColor >= 0.70 &&  morpColor <= 0.80) 	{	 color =  	0.7		;	}		// 
		if ( morpColor >= 0.80 &&  morpColor <= 0.90) 	{	 color =  	0.8		;	}		// 
		if ( morpColor >= 0.90 &&  morpColor <= 1.00) 	{	 color =  	0.9		;	}		// 
		
		n.addAttribute("ui.color", color );
		}
	}

	public static void Viz10ColorAct( Graph graph ) { 
	
	graph.addAttribute("ui.stylesheet", setViz10Color ( ) );
	
	for ( Node n : graph.getEachNode()) {
	
		double inh = n.getAttribute("gsAct");	
		double morpColor = inh ;
		
		double color = 0  ; //grey
	
		if ( morpColor >= 0.10 &&  morpColor <= 0.20) 	{	 color =  	0.1		;	} 		// 
		if ( morpColor >= 0.20 &&  morpColor <= 0.30)	{	 color =  	0.2		;	}		// 
		if ( morpColor >= 0.30 &&  morpColor <= 0.40) 	{	 color =  	0.3		;	}		// 
		if ( morpColor >= 0.40 &&  morpColor <= 0.50) 	{	 color =  	0.4		;	}		// 
		if ( morpColor >= 0.50 &&  morpColor <= 0.60) 	{	 color =  	0.5		;	} 		// 
		if ( morpColor >= 0.60 &&  morpColor <= 0.70)	{	 color =  	0.6		;	}		// 
		if ( morpColor >= 0.70 &&  morpColor <= 0.80) 	{	 color =  	0.7		;	}		// 
		if ( morpColor >= 0.80 &&  morpColor <= 0.90) 	{	 color =  	0.8		;	}		// 
		if ( morpColor >= 0.90 &&  morpColor <= 1.00) 	{	 color =  	0.9		;	}		// 
		
		n.addAttribute("ui.color", color );
		}
	}

	public static void Vizmorp( Graph graph , String morp ) {
	
		graph.addAttribute("ui.stylesheet", setVizMorp ( morp ) );
	
		for ( Node n : graph.getEachNode()) {		
			double color = n.getAttribute(morp);	
			n.addAttribute("ui.color", color );
			}
		}
	
	public static void VizSeedGrad ( Graph graph , String attribute ) {
		
		graph.addAttribute("ui.stylesheet", setVizSeedGrad() );			
		
		for ( Node n : graph.getEachNode () ) {
			try {
				double color = 0 ;
				int isTrue = n.getAttribute(attribute);
				if ( isTrue == 1 )
					color = 1 ;
				n.addAttribute("ui.color", color );
			} catch (java.lang.NullPointerException e) {
				continue ;
			}
		}
	}

// SET SCALE VIZ ------------------------------------------------------------------------------------------------------------------------------------
	 
	public static void setFixScaleManual ( Graph graph , double XYmax , double XYMin ) {
		
		try {
			String idNode = "setScale" + 1;

			graph.addNode(idNode);
			graph.getNode(idNode).setAttribute( "xyz", XYmax , XYmax, 0 );
			
			idNode = "setScale" + 2;
			graph.addNode(idNode);
			graph.getNode(idNode).setAttribute( "xyz", XYMin , XYMin, 0 );
		
		} catch (org.graphstream.graph.IdAlreadyInUseException e) { return ;	}
		}
	
	// doesn't work
	public static void setFixScale ( Graph graphToFix , Graph graphFixed ) {
			
		try { 
		double xMax = 0 , xMin = 1000000000 , yMax = 0, yMin = 1000000000 ; 
		
		for ( Node n : graphFixed.getEachNode()) {
			
			double [] nCoordinate = GraphPosLengthUtils.nodePosition(n) ;
			
			if ( nCoordinate [0] > xMax )
				xMax = nCoordinate [0] ;
			if ( nCoordinate [0] < xMin )
				xMin = nCoordinate [0] ;
			
			if ( nCoordinate [1] > yMax )
				yMax = nCoordinate [1] ;
			if ( nCoordinate [1] < yMin )
				yMin = nCoordinate [1] ;
		}
		
		// create 2 nodes and set coordinate (xMin, yMin) and (xMax, yMax)
		String idNode = "setScale" + 1;
		graphToFix.addNode(idNode);
		graphToFix.getNode(idNode).setAttribute( "xyz", xMin , yMin, 0 );
		
		idNode = "setScale" + 2;
		graphToFix.addNode(idNode);
		graphToFix.getNode(idNode).setAttribute( "xyz", xMax , yMax, 0 );
		
		// set Viz
		Node n = graphToFix.getNode(idNode);
	//	n.addAttribute("ui.class", "invisible");
		} catch (org.graphstream.graph.IdAlreadyInUseException e) {		return ;	}
	
	}
// SET ATTRIBUTE VIZ --------------------------------------------------------------------------------------------------------------------------------
	
	protected static String setVizInvisible () {
		return "";
	}
	
	protected static String setVizSeedGrad () {
		return  "node {"+
				"	size: 5px;"+
				"	fill-color: black,  red; "+
				"	fill-mode: dyn-plain;"+
				"}"+
				
				"node#setScale1 {	size: 0px; }" +
				"node#setScale2 {	size: 0px; }" +

				"edge {"+
				"	size: 0.5px;"+
				"	fill-color: black;"+
				"}" ;	
	}
	
	protected static String setVizNodeId () {
		return  "node { "
				+ "size: 5px;"
				+ "fill-color: black;"
				+ "text-alignment: at-right; "
				+ "text-color: black; "
				+ "text-background-mode: plain; "
				+ "text-background-color: white; "
				+ "}";
	}

	protected static String setVizMorp ( String morp ) {
		String colorRange ;
		if ( morp == "gsAct" ) 			
			colorRange = "grey , red" ; 
		else { 
			if ( morp == "gsInh" ) 	 
				colorRange = " grey, green" ;				
				else { 
					colorRange = "0" ;			 
					System.out.println("color not defined");		
				}
		}
		return  "node {"+
				"	size: 4px;"+
				"	fill-color: "+ colorRange	+ "  	;"+
				"	fill-mode: dyn-plain;"+
				"}"+
				"edge {"+
				"	size: 0.1px;"+
				"	fill-color: white;"+
				"}" ;
	}	

	protected static String setViz4Color () {
		return  "node {"+
			"	size: 5px;"+
			"	fill-color: grey, red, yellow, green, blue; "+
			"	fill-mode: dyn-plain;"+
			"}"+
			"edge {"+
			"	size: 0.1px;"+
			"	fill-color: white;"+
			"}" ;		
	}

	protected static String setViz10Color () {
		return  "node {"+
			"	size: 6px;"+
			"	fill-color: rgb(128,128,128), "
			+ "				rgb(255,128,0),rgb(255,255,0),rgb(128,255,0),"
			+ "				rgb(0,128,255),rgb(0,0,255),rgb(127,0,255),"
			+ "				rgb(255,0,255),rgb(255,0,128),rgb(255,0,0) ; "+
			"	fill-mode: dyn-plain;"+
			"}"+
			"edge {"+
			"	size: 0.1px;"+
			"	fill-color: white;"+
			"}" ;
	}
	
}
