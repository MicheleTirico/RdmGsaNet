package RdmGsaNet_exportData;

import java.io.IOException;
import java.util.ArrayList;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;
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

public class exportData_image_02 extends exportData_main {
	
	static String  	pathStart = pathStartNet ,	 
					pathStep = pathStepNet ; 

	protected static Graph graph ;	
	
	// parameters of viz 
	private static int setScale ; 
	private static double sizeNode , sizeEdge ; 
	private static String colorStaticNode , colorStaticEdge ;
		
	public void setParamViz ( int setScale , double sizeNode , double sizeEdge , 
			String colorStaticNode , String colorStaticEdge  
			) {
		this.setScale = setScale ; 
		this.sizeNode = sizeNode ;
		this.sizeEdge = sizeEdge ;
		this.colorStaticNode = colorStaticNode ;
		this.colorStaticEdge = colorStaticEdge ;
	}
	
	public static void createImage ( boolean run , int 	stepInc , int stepMax , String pathToStore , String pathDataMain ) throws IOException {
		
	Graph graph = new SingleGraph("test") ;
	

	// setup net viz parameters
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
			
				pic.setStyleSheet(setVizManual());
				
				
				pic.writeAll(graph, pathToStore+"netImage"+step+".png");
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
		 
	

}