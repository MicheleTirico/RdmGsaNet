package RdmGsaNetViz;

import java.io.IOException;
import java.util.ArrayList;

import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.GraphParseException;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceFactory;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import RdmGsaNet_Analysis.analysisDGS;

public class _NOTIMPLmultiViz {
	
	// COSTANTS
	private boolean	gsViz ,
					netViz;
	
	// viz constants
	private static FileSource gsFs ,
								netFs ;
	
	private static ViewPanel  gsView , netView ;
	
	protected static String fileType = ".dgs" ;
	
	protected String pathStartGs , pathStartNet ,
						pathStepGs , pathStepNet ;
					
	String dgsId ;
		
	// COSTRUCTOR 
	public _NOTIMPLmultiViz ( boolean gsViz ,boolean netViz) {
		this.gsViz = gsViz ;
		this.netViz = netViz ;
	}
	
	public void setPath ( String dgsId , String pathStart , String pathStep ) {
		
		if ( dgsId == "dgsNet"  ) {
			this.pathStartNet = pathStart ;
			this.pathStepNet = pathStep ;
		}
		else if  ( dgsId == "dgsGs"  ) {
			this.pathStartGs = pathStart ;
			this.pathStepGs = pathStep ;
		}
	}
	
	public void runMultiLayerViz ( int stepMax ,int stepInc  )
			throws IOException, InterruptedException  {
		
		if ( !gsViz && !netViz )
			return ;
		
		// create list of step to create images
		ArrayList<Double> incList = analysisDGS.getListStepToAnalyze(stepInc, stepMax);						//	System.out.println(incList);

		Graph gsGraph = new SingleGraph("gsGraph"),
				netGraph = new SingleGraph("netGraph") ;
		
		// setup viz parameters
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		gsGraph.addAttribute("ui.quality");
	    gsGraph.addAttribute("ui.antialias");
	    netGraph.addAttribute("ui.quality");
	    netGraph.addAttribute("ui.antialias");

		Viewer gsViewer = gsGraph.display(false) ;	
		Viewer netViewer = netGraph.display(false) ;	
	
		
		// read start path
		try {	
			gsGraph.read(pathStartGs);
			netGraph.read(pathStartNet);
		} 
		catch (	ElementNotFoundException | GraphParseException |org.graphstream.graph.IdAlreadyInUseException e) 	{	/*e.printStackTrace();*/	}
						
		// set file Source for file step
		gsFs = FileSourceFactory.sourceFor(pathStepGs);
		gsFs.addSink(gsGraph);
				
		netFs = FileSourceFactory.sourceFor(pathStepNet);
		netFs.addSink(netGraph);

		// import file step
		try {
			gsFs.begin(pathStepGs);
			netFs.begin(pathStepNet);
			while ( gsFs.nextStep() && netFs.nextStep()  ) {
						
				double step = gsGraph.getStep();							//	System.out.println(step);
						
				if ( incList.contains(step)) {
					// add methods to run for each step in incList
					System.out.println("----------------step " + step + " ----------------" );				
					
					setupViz.setFixScaleManual(netGraph, 50 , 0);
					setupViz.VizSeedGrad(netGraph, "seedGrad");
					
					setupViz.Viz10ColorAct(gsGraph);
					
					Thread.sleep(10);
					
					// stop iteration    
					if ( stepMax == step ) { break; }
				}
			}
		} catch (IOException e) {		}				
		gsFs.end() ;	
		netFs.end() ;
	}
}
