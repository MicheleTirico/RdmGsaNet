package RdmGsaNet_Analysis_pr02;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.GraphParseException;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceFactory;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import RdmGsaNetAlgo.graphAnalysis.analysisType;
import RdmGsaNetExport.expImage;
import RdmGsaNetViz.handleVizStype;
import RdmGsaNetViz.setupViz;
import RdmGsaNetViz.handleVizStype.palette;
import RdmGsaNetViz.handleVizStype.stylesheet;
import RdmGsaNet_Analysis_pr02.analysisGlobal;

public class analysisDGSgs  extends analysisMain implements analysisDGS  {

	// CONSTANT
	private String 	dgsId ,
					morp ;

	// viz constants
	private static ViewPanel  view ;
	private int stepIncIm ;
	
	// parameters of viz 

	private double sizeNodeGs , sizeEdgeGs ; 
	private palette paletteColor;

	protected static Graph graph = new SingleGraph ("graph");
	
	private int degreeFreq ;
	
	protected static boolean run ,
							getImage  ,
							runViz ,
							computeStepMaxMorp ,
							computeStepMinMorp ,
							computeStepAveMorp,
							computeGsActivedNodes ; 
	
// --------------------------------------------------------------------------------------------------------------------------------------------------
	// COSTRUCTOR
	public analysisDGSgs ( String dgsId , boolean run ) {
		this.dgsId = dgsId;
		this.run = run ;	
	}
		
	public void setParamVizGs ( double sizeNodeGs , double sizeEdgeGs , palette paletteColor ) { 
		this.sizeNodeGs = sizeNodeGs ;
		this.sizeEdgeGs = sizeEdgeGs ;
		this.paletteColor = paletteColor ;
	}	

	// set parameters of analysis
	public void setParamAnalysis ( String morp  , int stepIncIm ) {
		this.morp = morp ;
		this.stepIncIm = stepIncIm ;	
	}
			
	public void setWhichGlobalAnalysis (boolean runViz , boolean getImage ,
										boolean computeStepMaxMorp , boolean computeStepMinMorp , boolean computeStepAveMorp ,
										boolean computeGsActivedNodes ) {
		this.runViz = runViz ;
		this.getImage = getImage ;
		this.computeStepMaxMorp = computeStepMaxMorp ;
		this.computeStepMinMorp = computeStepMinMorp ;
		this.computeStepAveMorp = computeStepAveMorp ;
		this.computeGsActivedNodes = computeGsActivedNodes ; 
		
		if ( getImage ) 
			analysisGlobal.handle.createFolder(folder + "analysis\\", "image", false ) ;
					
	}

// COMPUTE MULTIPLE ANALYSIS --------------------------------------------------------------------------------------------------------------
	
	public void computeGlobalStat (int stepMax, int stepInc, String[] pathStartArr, String[] pathStepArr  , int thread ) 
			throws IOException, InterruptedException {

		if ( run == false ) 
			return ; 
		
		String pathStart = pathStartArr[0];
		String pathStep = pathStepArr[0];
		
		// get graph through dgsId of graph
		graph = analysisDGS.returnGraphAnalysis(dgsId);		
		handleVizStype viz 	= null ;
		
		// run viz
		if ( runViz ) {			
			// setup gs viz parameters
			viz = new handleVizStype( graph ,stylesheet.viz10Color, "gsInh", 1) ;
			viz.setupDefaultParam (graph, "red", "white", sizeNodeGs , sizeEdgeGs );
			Viewer gsViewer = graph.display(false) ;
		}
		
		// create list of step to create images
		ArrayList<Double> incList = analysisDGS.getListStepToAnalyze(stepInc, stepMax);						//	System.out.println(incList);

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
				
					// run viz
					if ( runViz ) {	
						viz.setupViz(true, true, paletteColor );
						viz.setupIdViz(false, graph, 10 , "black");
						Thread.sleep(thread);			
					}
					
					if ( getImage ) 
						if (  analysisDGS.getListStepToAnalyze( stepIncIm , stepMax).contains(step) ) 
							expImage.getImage(graph, folder + "analysis\\image\\" , "gsImage" + step + ".png" );
					
					if ( computeStepMaxMorp )
						analysisDGS.computeStepMorp(graph, step, analysisGlobal.mapGsStepMaxMorp , analysisType.max );
					
					if ( computeStepMinMorp )
						analysisDGS.computeStepMorp(graph, step, analysisGlobal.mapGsStepMinMorp , analysisType.min );
					
					if ( computeStepAveMorp )
						analysisDGS.computeStepMorp(graph, step, analysisGlobal.mapGsStepAveMorp , analysisType.average );
					
					if ( computeGsActivedNodes ) 
						analysisDGS.computeGsActivedNodes (graph, step, analysisGlobal.mapGsActivedNodes  );
								
					// stop iteration    
					if ( stepMax == step ) { break; }
				}
			}
		} catch (IOException e) {		}				
		fs.end();	
	}

	@Override
	public void computeLocalStat(int stepMax, int stepInc, String[] pathStartArr , String[] pathStepArr  , int thread )
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
	
	}


}
