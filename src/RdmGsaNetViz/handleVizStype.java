package RdmGsaNetViz;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class handleVizStype {
	
	private Graph graph;
	String colorStaticNode , colorStaticEdge ;
	double sizeNode , sizeEdge ;
	
	public enum stylesheet { viz5Color , viz10Color , manual  , booleanAtr }
	private stylesheet styleType ;
	
	// COSTRUCTOR
	public handleVizStype (  Graph graph , stylesheet styleType ) {
		this.styleType = styleType ;
		this.graph = graph ;
	}
	
	public void setupDefaultViz ( boolean quality , boolean antiAlias , String attribute ) {
	
		if ( antiAlias )	graph.addAttribute("ui.antialias");
		if ( quality )		graph.addAttribute("ui.quality");
	   
		switch (styleType) {
		
		case viz10Color: 	setViz10Color(attribute); 
			break;
		
		case viz5Color: 	setViz5Color ( attribute );
			break;
		
		case manual :		graph.addAttribute("ui.stylesheet", VizManual() );
			break ;

		}
	}

// --------------------------------------------------------------------------------------------------------------------------------------------------	
	public void setupDefaultParam ( Graph graph , String colorStaticNode , String colorStaticEdge , double sizeNode , double sizeEdge ) {
		this.colorStaticNode = colorStaticNode ;
		this.colorStaticEdge = colorStaticEdge ;
		this.sizeEdge = sizeEdge ;
		this.sizeNode = sizeNode ;
	}
	
	public void setupIdViz ( boolean vizId , Graph graph , double sizeTestId , String colorId  ) {
	
		if ( vizId == false )
			return ;
		
		graph.addAttribute("ui.stylesheet", vizNodeId ( sizeTestId , colorId) );
		
		for ( Node n : graph.getEachNode()) {
			n.addAttribute("ui.label", " " + n.getId());
		}	
	}
	
// SETUP VIZ ----------------------------------------------------------------------------------------------------------------------------------------
	private void  setViz5Color ( String attribute ) {
		graph.addAttribute("ui.stylesheet", Viz5Color(styleType) );
		for ( Node n : graph.getEachNode()) {
			
			double morpColor = n.getAttribute(attribute);
			double color = 0  ; //grey

			if ( morpColor >= 0.2 &&  morpColor <= 0.4) color =  	0.2		;				
			if ( morpColor >= 0.4 &&  morpColor <= 0.6) color =  	0.4		;	 
			if ( morpColor >= 0.6 &&  morpColor <= 0.8) color =  	0.6		;	
			if ( morpColor >= 0.8 &&  morpColor <= 1.0) color =  	0.8		;	
			
			n.addAttribute("ui.color", color );
			}
	}

	private void setViz10Color ( String attribute ) {
		graph.addAttribute("ui.stylesheet", Viz10Color(styleType) );
		for ( Node n : graph.getEachNode()) {
			
			double morpColor = n.getAttribute(attribute);
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
	
	public void setupVizBooleanAtr ( boolean viz , Graph graph , String attribute , String color0 , String color1) {
		graph.addAttribute("ui.stylesheet", setVizBooleanAtr(   color0 ,  color1 ) );			
		
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
	
	public void setupFixScaleManual ( boolean setScale,   Graph graph , double XYmax , double XYMin ) {
		
		if ( setScale == false )
			return ;
					
		try {
			String idNode = "setScale" + 1;

			graph.addNode(idNode);
			graph.getNode(idNode).setAttribute( "xyz", XYmax , XYmax, 0 );
			
			idNode = "setScale" + 2;
			graph.addNode(idNode);
			graph.getNode(idNode).setAttribute( "xyz", XYMin , XYMin, 0 );
		
		} catch (org.graphstream.graph.IdAlreadyInUseException e) { return ;	}
	}
// SET STYLESHEET -----------------------------------------------------------------------------------------------------------------------------------
	private String setVizBooleanAtr ( String color0 , String color1 ) {
		return  "node {"+
				"	size: " + sizeNode + "px;"+
				"	fill-color: "+color0 +","+color1+"; "+
				"	fill-mode: dyn-plain;"+
				"}"+
				
				"node#setScale1 {	size: 0px; }" +
				"node#setScale2 {	size: 0px; }" +

				"edge {"+
				"	size: " + sizeEdge + "px;"+
				"	fill-color:"+colorStaticEdge+" ;"+
				"}" ;		
	}

	private String VizManual() {
		 return "node {"+
				"	size: " + sizeNode + "px;"+
				"	fill-color: "+ colorStaticNode +";"+
				"	fill-mode: dyn-plain;"+
				"}"+
				
				"node#setScale1 {	size: 0px; }" +
				"node#setScale2 {	size: 0px; }" +
				
				"edge {"+
				"	size: " + sizeEdge + "px;"+
				"	fill-color:"+colorStaticEdge+" ;"+
				"}" ;
	}
	
	private String Viz5Color (  stylesheet styleType ){
		
		String color = " fill-color: gray , red , blue , green , yellow ; " ;
		
		return  "node {"+
				"	size: " + sizeNode + "px;"+
				color +
				"	fill-mode: dyn-plain;"+
				"}"+
				"node#setScale1 {	size: 0px; }" +
				"node#setScale2 {	size: 0px; }" +
				"edge {"+
				"	size: " + sizeEdge + "px;"+
				"	fill-color:"+colorStaticEdge+" ;"+
				"}" ;
	}
	
	protected String vizNodeId ( double sizeTestId , String colorId  ) {
		return  "node { "
				+ "size:  1px;"
				+ "fill-color: " + colorId + ";"
				+ "text-alignment: at-right; "
				+ "text-color:" + colorId + ";"
				+ "text-background-mode: plain; "
				+ "text-background-color: white; "
				+ "}";
	}
	
	protected String Viz10Color ( stylesheet styleType ) {
		
		String color = 	" fill-color: rgb(128,128,128), " + 
						" rgb(255,128,0),rgb(255,255,0),rgb(128,255,0)," + 
						" rgb(0,128,255),rgb(0,0,255),rgb(127,0,255)," + 
						" rgb(255,0,255),rgb(255,0,128),rgb(255,0,0) ; ";
		
		return  "node {"+
			"	size: " + sizeNode + "px;"+
			 	color +
			"	fill-mode: dyn-plain;"+
			"}"+
			"node#setScale1 {	size: 0px; }" +
			"node#setScale2 {	size: 0px; }" +
			"edge {"+
			"	size: " + sizeEdge + "px;"+
			"	fill-color:"+colorStaticEdge+" ;"+
			"}" ;
	}
	
}
