package RdmGsaNet_exportData;

import java.io.IOException;
import java.util.ArrayList;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.GraphParseException;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSinkImages.LayoutPolicy;
import org.graphstream.stream.file.FileSinkImages.OutputPolicy;
import org.graphstream.stream.file.FileSinkImages.OutputType;
import org.graphstream.stream.file.FileSinkImages.Resolution;
import org.graphstream.stream.file.FileSinkImages.Resolutions;
import org.graphstream.stream.file.FileSourceDGS;
import org.graphstream.stream.file.FileSourceFactory;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import RdmGsaNetExport.handleNameFile;
import RdmGsaNetViz.handleVizStype;
import RdmGsaNetViz.handleVizStype.palette;
import RdmGsaNetViz.handleVizStype.stylesheet;
import RdmGsaNet_Analysis_02.analysisDGS;

public class exportData_image extends exportData_main {
	
	static String  	pathStart  ,	 
					pathStep  ; 

	protected static Graph graph ;	
	
	// parameters of viz 
	private static int setScale ; 
	private static double sizeNodeNet , sizeEdgeNet , sizeNodeGs , sizeEdgeGs ; 
	private static String colorNodeNet , colorEdgeNet , colorNodeGs , colorEdgeGs ;
	private static palette paletteColor ;
	
	private static double sizeNode ,  sizeEdge ;
	private static String colorNode ,  colorEdge , nameIm ;
	
	protected enum layer { gsGraph, netGraph, vecGraph, seedGraph}
	private layer layer ;
	
	public void setParamVizNet ( int setScale , double sizeNode , double sizeEdge , 
			String colorStaticNode , String colorStaticEdge  
			) {
		this.setScale = setScale ; 
		this.sizeNodeNet = sizeNode ;
		this.sizeEdgeNet = sizeEdge ;
		this.colorNodeNet = colorStaticNode ;
		this.colorEdgeNet = colorStaticEdge ;
	}
	
	public void setParamVizGs ( int setScale , double sizeNode , double sizeEdge , 
			String colorStaticNode , String colorStaticEdge  ,
			 palette paletteColor 
			) {
		this.setScale = setScale ; 
		this.sizeNodeGs = sizeNode ;
		this.sizeEdgeGs = sizeEdge ;
		this.colorNodeGs = colorNode ;
		this.colorEdgeGs = colorEdge ;
		this.paletteColor = paletteColor ;
	}
		
	public static void createImage ( layer layer , boolean run , int stepInc , int stepMax , String pathToStore , String pathDataMain ) throws IOException {

		if ( run != true )
			return ;
		
		Graph graph = new SingleGraph("test") ;
		
		switch (layer) {
			case netGraph :{
				pathStart = pathStartNet ;	 
				pathStep = pathStepNet ; 
				sizeNode = sizeNodeNet ;  sizeEdge = sizeEdgeNet ; colorNode = colorNodeNet ; colorEdge = colorEdgeNet ; 
				nameIm = "netImage_" ;
			} break;
			
			case gsGraph : {
				pathStart = pathStartGs ;	 
				pathStep = pathStepGs ; 
				sizeNode = sizeNodeGs ;  sizeEdge = sizeEdgeGs ; colorNode = colorNodeGs ; colorEdge = colorEdgeGs ; 
				nameIm = "gsImage_" ;
			} break;
			
			case seedGraph : {
				System.out.println("not implem");
				System.exit(10);	
			} break;
			
			case vecGraph : {
				System.out.println("not implem");
				System.exit(10);			
			} break;
		}
	
		System.out.println(pathStep);
		// setup net viz parameters
		handleVizStype gsViz = new handleVizStype( graph ,stylesheet.viz10Color, "gsInh", 1) ;
	//	gsViz.setupDefaultParam (graph, "red", "white", sizeNodeGs , sizeEdgeGs );
		gsViz.setupFixScaleManual( true, graph, setScale , 0);
		
		handleVizStype netViz = new handleVizStype( graph ,stylesheet.manual , "seedGrad", 1) ;
		netViz.setupFixScaleManual( true, graph, setScale , 0);
		
		FileSinkImages pic = new FileSinkImages(OutputType.PNG, Resolutions.VGA);
		
		pic.setLayoutPolicy(LayoutPolicy.NO_LAYOUT);
	
		handleNameFile.createNewGenericFolder(pathDataMain , "image" );
		pathToStore = pathDataMain + "\\image\\"  ;
		
		// create list of step to create images
		ArrayList<Double> incList = new ArrayList<Double>() ;						//	System.out.println(incList);
						
		for ( double n = 1 ; n * stepInc <= stepMax ; n++ ) 	
			incList.add( n * stepInc );			
				
		// set file Source for file step
		fs = FileSourceFactory.sourceFor(pathStep);
		fs.addSink(graph);
		
		
		// import start graph
		try 														{	graph.read(pathStart);		} 
		catch (	ElementNotFoundException | 
				GraphParseException | 
				org.graphstream.graph.IdAlreadyInUseException e) 	{	/*e.printStackTrace();*/	}
		 	
		// set file Source for file step
		fs = FileSourceFactory.sourceFor(pathStep);
		fs.addSink(graph);
		
		// import file step
		try {
			fs.begin(pathStep);
			while ( fs.nextStep()) {
				double step = graph.getStep();							//	System.out.println(step);
				if ( incList.contains(step)) {
					// add methods to run for each step in incList				
					System.out.println("----------------step " + step + " ----------------" );		
					
	
					switch (layer) {
						case netGraph: {
						
					//	System.out.println(graph.getNodeCount());
							
							pic.setStyleSheet(setVizManual(sizeNode, sizeEdge, colorNode, colorEdge)) ;				
		
							pic.writeAll(graph, pathToStore + nameIm + (int) step + ".png");
						}	
						break;
						
						case gsGraph : {
							
									
						}break ;

					}
			
					
				
					// stop iteration    			
					if ( stepMax == step )  
						break; 
				}			
			}
	
		} catch (IOException e) {		}				
	
		
		fs.end();
	




	}
	private static String setVizManual() {
		 return "node {"+
				"	size: " + sizeNode + "px;"+
				"	fill-color: "+ colorNode +";"+
				"	fill-mode: dyn-plain;"+
				"}"+
				
				"node#setScale1 {	size: 0px; }" +
				"node#setScale2 {	size: 0px; }" +
				
				"edge {"+
				"	size: " + sizeEdge + "px;"+
				"	fill-color:"+colorEdge+" ;"+
				"}" ;
	}
		 
	
	public static String setVizManual(double sizeNode , double sizeEdge ,	String colorNode , String colorEdge ) {
		 return "node {"+
					"	size: " + sizeNode + "px;"+
					"	fill-color: "+ colorNode +";"+
					"	fill-mode: dyn-plain;"+
					"}"+
					
					"node#setScale1 {	size: 0px; }" +
					"node#setScale2 {	size: 0px; }" +
					
					"edge {"+
					"	size: " + sizeEdge + "px;"+
					"	fill-color:"+colorEdge+" ;"+
					"}" ;
		}
			 
	

}
