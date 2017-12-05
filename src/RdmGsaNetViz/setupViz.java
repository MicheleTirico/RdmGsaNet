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
	
	public static void Viz4Color( Graph graph ) {
		
		graph.addAttribute("ui.stylesheet", setViz4Color ( ) );
//		graph.addAttribute("ui.quality");
//	    graph.addAttribute("ui.antialias");
		
		for ( Node n : graph.getEachNode()) {
			double act = n.getAttribute("gsAct");
			double inh = n.getAttribute("gsInh");
			
			double color = 0  ; //grey
			
			if ( act >= 0.9 && inh >= 0.9) 	{	 color =  	.25		;	} 		// red
			if ( act >= 0.5 && inh <= 0.5) 	{	 color =  	.5		;	}		// yellow
			if ( act <= 0.5 && inh >= 0.5) 	{	 color =  	.75		;	}		// green
			if ( act <= 0.1 && inh <= 0.1) 	{	 color =  	1		;	}		// blue
			
			n.addAttribute("ui.color", color );
		}
	}


	private static String setViz4Color () {
		return  "node {"+
			"	size: 4px;"+
			"	fill-color: grey, red, yellow, green, blue; "+
			"	fill-mode: dyn-plain;"+
			"}"+
			"edge {"+
			"	size: 0.1px;"+
			"	fill-color: white;"+
			"}" ;
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
