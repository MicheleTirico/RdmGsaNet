package RdmGsaNetViz;
import java.io.IOException;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSinkImages.LayoutPolicy;
import org.graphstream.stream.file.FileSinkImages.OutputType;
import org.graphstream.stream.file.FileSinkImages.Resolutions;

import RdmGsaNet_pr08.*;

public class setupViz {

	static Graph gsGraph = layerGs.getGraph();
	static Graph netGraph = layerNet.getGraph();

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
	
	
// methods of visualization
	
	public static void Viz4Color( Graph graph ) {
		
		graph.addAttribute("ui.stylesheet", setViz4Color ( ) );
//		graph.addAttribute("ui.quality");
//	    graph.addAttribute("ui.antialias");
		
		for ( Node n : graph.getEachNode()) {
			double act = n.getAttribute("gsAct");
			double inh = n.getAttribute("gsInh");
			
			double color = 0  ; //grey
			
			if ( act >= 0.95 && inh >= 0.95) 	{	 color =  	.25		;	} 		// red
			if ( act >= 0.9 && inh <= 0.1) 	{	 color =  	.5		;	}		// yellow
			if ( act <= 0.01 && inh >= 0.99) 	{	 color =  	.75		;	}		// green
			if ( act <= 0.05 && inh <= 0.05) 	{	 color =  	1		;	}		// blue
			
			n.addAttribute("ui.color", color );
		}
	}


	private static String setViz4Color () {
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

	private static String setViz10Color () {
		return  "node {"+
			"	size: 5px;"+
			"	fill-color: rgb(0,0,0), "
			+ "				rgb(80,80,80),rgb(160,160,80),rgb(255,255,80),"
			+ "				rgb(160,80,80),rgb(160,160,160),rgb(255,160,80),"
			+ "				rgb(255,80,80),rgb(160,160,160),rgb(255,255,255) ; "+
			"	fill-mode: dyn-plain;"+
			"}"+
			"edge {"+
			"	size: 0.1px;"+
			"	fill-color: white;"+
			"}" ;
}
	
public static void Viz5Color( Graph graph , String morp   ) {
		
		graph.addAttribute("ui.stylesheet", setViz4Color ( ) );

		
		for ( Node n : graph.getEachNode()) {
			double act = n.getAttribute("gsAct");
			double inh = n.getAttribute("gsInh");
		
			double morpColor = 0 ;
			
			if ( morp == "gsAct" ) 	{ morpColor = act 	;			}
			else { if ( morp == "gsInh" ) 	{  morpColor = inh ;	} 
			
			double color = 0  ; //grey
			
			if ( morpColor >= 0.20 ) 	{	 color =  	.25		;	} 		// red
			if ( morpColor >= 0.40 )	{	 color =  	.5		;	}		// yellow
			if ( morpColor >= 0.60 ) 	{	 color =  	.75		;	}		// green
			if ( morpColor >= 0.80 ) 	{	 color =  	1		;	}		// blue
			
			n.addAttribute("ui.color", color );
			}
		}
	}
	
public static void Viz10Color( Graph graph , String morp   ) {
	
	graph.addAttribute("ui.stylesheet", setViz10Color ( ) );

	
	for ( Node n : graph.getEachNode()) {
		double act = n.getAttribute("gsAct");
		double inh = n.getAttribute("gsInh");
	
		double morpColor = 0 ;
		
		if ( morp == "gsAct" ) 	{ morpColor = act 	;			}
		else { if ( morp == "gsInh" ) 	{  morpColor = inh ;	} 
		
		double color = 0  ; //grey
		
		if ( morpColor >= 0.10 ) 	{	 color =  	0.1		;	} 		// red
		if ( morpColor >= 0.20 )	{	 color =  	0.2		;	}		// yellow
		if ( morpColor >= 0.30 ) 	{	 color =  	0.3		;	}		// green
		if ( morpColor >= 0.40 ) 	{	 color =  	0.4		;	}		// blue
		if ( morpColor >= 0.50 ) 	{	 color =  	0.5		;	} 		// red
		if ( morpColor >= 0.60 )	{	 color =  	0.6		;	}		// yellow
		if ( morpColor >= 0.70 ) 	{	 color =  	0.7		;	}		// green
		if ( morpColor >= 0.80 ) 	{	 color =  	0.8		;	}		// blue
		if ( morpColor >= 0.90 ) 	{	 color =  	0.9		;	}		// blue
		
		n.addAttribute("ui.color", color );
		}
	}
}

	public static void Vizmorp( Graph graph , String morp ) {
	
		graph.addAttribute("ui.stylesheet", setVizMorp ( morp ) );
	
		for ( Node n : graph.getEachNode()) {		
			double color = n.getAttribute(morp);	
			n.addAttribute("ui.color", color );
			}
		}
	
	private static String setVizMorp ( String morp ) {
		String colorRange ;
		if ( morp == "gsAct" ) 	{ colorRange = "grey , red" ; 				}
		else { if ( morp == "gsInh" ) 	{ colorRange = " grey, green" ;				} 
		else 					{ colorRange = "0" ;
								  System.out.println("color not defined");	}
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

}
