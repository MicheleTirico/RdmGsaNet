package RdmGsaNetViz;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class handleVizStype {
	
	private Graph graph;
	String colorStaticNode , colorStaticEdge ;
	double sizeNode , sizeEdge ;
	
	public enum palette { red , blue , multi }
	private palette mainColor ;
	public enum stylesheet { viz5Color , viz10Color , manual  , booleanAtr }
	private stylesheet styleType ;
	private String attributeToAnalyze ; 
	// COSTRUCTOR
	public handleVizStype (  Graph graph , stylesheet styleType , String attributeToAnalyze ) {
		this.styleType = styleType ;
		this.graph = graph ;
		this.attributeToAnalyze = attributeToAnalyze ;
	
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
	}
	
	public void setupViz ( boolean quality , boolean antiAlias , palette mainColor ) {
	
		if ( antiAlias )	graph.addAttribute("ui.antialias");
		if ( quality )		graph.addAttribute("ui.quality");
	   
		switch (styleType) {
		
		case viz10Color: 	setViz10Color( attributeToAnalyze , mainColor); 
			break;
		
		case viz5Color: 	setViz5Color ( attributeToAnalyze , mainColor );
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
	private void  setViz5Color ( String attributeToAnalyze , palette multiColor  ) {
	
		graph.addAttribute("ui.stylesheet", Viz5Color(styleType , multiColor ) );
		for ( Node n : graph.getEachNode()) {
			
			double morpColor = n.getAttribute(attributeToAnalyze);
			double color = 0  ; //grey

			if ( morpColor >= 0.2 &&  morpColor <= 0.4) color =  	0.2		;				
			if ( morpColor >= 0.4 &&  morpColor <= 0.6) color =  	0.4		;	 
			if ( morpColor >= 0.6 &&  morpColor <= 0.8) color =  	0.6		;	
			if ( morpColor >= 0.8 &&  morpColor <= 1.0) color =  	0.8		;	
			
			n.addAttribute("ui.color", color );
			}
	}

	private void setViz10Color ( String attributeToAnalyze , palette mainColor ) {
		graph.addAttribute("ui.stylesheet", Viz10Color(styleType , mainColor) );
		for ( Node n : graph.getEachNode()) {
			
			double morpColor = n.getAttribute(attributeToAnalyze);
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
	
	public void setupVizBooleanAtr ( boolean viz , Graph graph , String color0 , String color1) {
		graph.addAttribute("ui.stylesheet", setVizBooleanAtr(   color0 ,  color1 ) );			
		
		for ( Node n : graph.getEachNode () ) {
			try {
				double color = 0 ;
				int isTrue = n.getAttribute(attributeToAnalyze);
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
	
	private String Viz5Color (  stylesheet styleType , palette mainColor ) {
		
		String color = null ;
		switch ( mainColor ) {
		case blue:
			color = "fill-color: rgb(230, 240, 255) , rgb(179, 209, 255), rgb(102, 163, 255) , rgb(51, 133, 255), rgb(0, 71, 179) ;" ;
			break;
		
		case multi :
			color = " fill-color: gray , red , blue , green , yellow ; " ;
			break;
			
		case red :
			color = "fill-color: rgb(255, 235, 230), rgb(255, 173, 153) , rgb(255, 92, 51) , rgb(230, 46, 0) , 	rgb(179, 36, 0) ;" ;
			break ;
		}
		
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
	
	protected String Viz10Color ( stylesheet styleType , palette mainColor ) {
		
		String color = null ;
		switch ( mainColor ) {
		case blue:
			color = " fill-color: rgb(230, 240, 255)	, " + 
					" rgb(204, 224, 255) , rgb(179, 209, 255) , rgb(128, 179, 255) , " +
					" rgb(102, 163, 255) , rgb(77, 148, 255)  , rgb(51, 133, 255)  , " +
					" rgb(0, 102, 255)   , rgb(0, 82, 204)    , rgb(0, 31, 77)     ; " ;
				
			break;
		
		case multi :
			color = " fill-color: rgb(128,128,128), " + 
					" rgb(255,128,0),rgb(255,255,0),rgb(128,255,0)," + 
					" rgb(0,128,255),rgb(0,0,255),rgb(127,0,255)," + 
					" rgb(255,0,255),rgb(255,0,128),rgb(255,0,0) ; ";
			break;
			
		case red :
			color = " fill-color: 	rgb(255, 235, 230), " + 
					" rgb(255, 214, 204),  rgb(255, 194, 179) , rgb(255, 173, 153) , " + 
					" rgb(255, 133, 102) , rgb(255, 92, 51)   , rgb(255, 51, 0)    , " + 
					" rgb(230, 46, 0)    , rgb(179, 36, 0)    ,	rgb(102, 20, 0)	   ; " ;
			break;
		}
		
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
