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
import RdmGsaNet_exportData.exportData_main.layerToAnalyze;

public class exportData_image_03 extends exportData_main {

	private static int setScale ; 
	private static double sizeNode , sizeNodeNet ,  sizeNodeGs  , sizeEdge ,  sizeEdgeNet , sizeEdgeGs ; 
	private static String colorStaticNode , colorStaticNodeNet , colorStaticEdge , colorStaticEdgeNet , colorStaticEdgeGs ;
	private static palette paletteColor ,paletteColorGs ;	
	private static stylesheet stylesheet , stylesheetNet , stylesheetGs ;
	private Graph graph = new SingleGraph("graph") ;
	
	public void setParamVizGs ( int setScale , double sizeNode , double sizeEdge , 
			String colorStaticNode , String colorStaticEdge  ,
			palette paletteColor , stylesheet stylesheet
			) {
		this.stylesheetGs = stylesheet ;
		this.setScale = setScale ; 
		this.sizeNodeGs = sizeNode ;
		this.sizeEdgeGs = sizeEdge ;
		this.colorStaticEdgeGs = colorStaticEdge ;
		this.paletteColorGs = paletteColor ;		
	}
	
	public void setParamVizNet ( int setScale , double sizeNode , double sizeEdge , 
			String colorStaticNode , String colorStaticEdge  ,
			palette paletteColor , stylesheet stylesheet
			) {
		this.stylesheetNet = stylesheet ;
		this.setScale = setScale ; 
		this.sizeNodeNet = sizeNode ;
		this.sizeEdgeNet = sizeEdge ;
		this.colorStaticNodeNet = colorStaticEdge ;
		this.colorStaticEdgeNet = colorStaticEdge ;
	}
	
	
	public static void setParamViz ( int setScale , double sizeNode , double sizeEdge , 
			String colorStaticNode , String colorStaticEdge  ,
			palette paletteColor , stylesheet stylesheet
			) {
		exportData_image_03.stylesheet = stylesheet ;
		exportData_image_03.setScale = setScale ; 
		exportData_image_03.sizeNode = sizeNode ;
		exportData_image_03.sizeEdge = sizeEdge ;
		exportData_image_03.colorStaticEdge = colorStaticEdge ;
		exportData_image_03.paletteColor = paletteColor ;		
	}
		
	public void createImage ( boolean run , int stepToReturn , String nameIm , String pathToStore , String pathStart , String pathStep ) throws IOException {
	
		if ( !run )
			return ;
		// read graph 
		graph = getGraphStep(stepToReturn, pathToStore, pathStart, pathStep);	//	System.out.println(paletteColor);
		
		// handle viz
		handleVizStype	viz  = new handleVizStype( graph , stylesheet , "gsInh", 1) ;		
		viz.setupDefaultParam (graph, colorStaticNode, colorStaticEdge, sizeNode, sizeEdge);
		viz.setupIdViz(false, graph, 10 , "black"); 
		viz.setupViz(true, true, paletteColor);
		viz.setupFixScaleManual(true, graph, setScale, 0);
		
		graph.addAttribute("ui.screenshot", pathToStore+nameIm+"_"+stepToReturn+".png");
		graph.display(false);		
	}
	
	public void createSingleImage ( boolean run , int stepToReturn , String nameIm , String pathToStore , layerToAnalyze layer ) throws IOException  {
		
		if ( !run)
			return ;
		
		String[] path = getPath( folderMain , layer );
		
		String pathStep = path[1] ;
		String pathStart = path[0] ;
				
		graph = getGraphStep(stepToReturn, pathToStore, pathStart, pathStep);	//	System.out.println(paletteColor);
		
		// handle viz
		handleVizStype	viz  = new handleVizStype( graph , stylesheet , "gsInh", 1) ;		
		viz.setupDefaultParam (graph, colorStaticNode, colorStaticEdge, sizeNode, sizeEdge);
		viz.setupIdViz(false, graph, 10 , "black"); 
		viz.setupViz(true, true, paletteColor);
		viz.setupFixScaleManual(true, graph, setScale, 0);
		
		graph.addAttribute("ui.screenshot", pathToStore+nameIm+"_"+stepToReturn+".png");
		graph.display(false);	
	//	System.out.println(pathStart); System.out.println(pathStep);
		
		
	}
	
	protected  Graph getGraphStep (  int stepToReturn , String pathToStore , String pathStart , String pathStep ) throws IOException {

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
			while ( fs.nextStep() ) 					
				if ( graph.getStep()  == stepToReturn)
					return graph; 					
			
		} catch (IOException e) {		}				
	
		fs.end();				
		return graph ;
	}
	
}
