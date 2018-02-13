package RdmGsaNet_Analysis;
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

import RdmGsaNetViz.handleVizStype.palette;
import RdmGsaNet_Analysis.analysisDGS;
import RdmGsaNet_Analysis.analysisMain;
import RdmGsaNet_Analysis.analysisCombinedLayer;

public class analysisDGSCombinedLayer extends analysisMain {

	// COSTANTS
	private boolean	doGsViz ,
					doNetViz ,
					computeGsActivedNodes ;
	
	// viz constants
	private static FileSource gsFs ,
								netFs ;
	
	private static ViewPanel  gsView , netView ;
	
	protected static String fileType = ".dgs" ;
	
	protected String pathStartGs , pathStartNet ,
						pathStepGs , pathStepNet ,
							dgsId ;
		
	// COSTRUCTOR 
	public analysisDGSCombinedLayer ( boolean doGsViz ,boolean doNetViz) {
		this.doGsViz = doGsViz ;
		this.doNetViz = doNetViz ;
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
	
	public void runCombinedLayerStat ( int stepMax ,int stepInc  )
			throws IOException, InterruptedException  {
		
		if ( !doGsViz && !doNetViz )
			return ;
		
		// create list of step to create images
		ArrayList<Double> incList = analysisDGS.getListStepToAnalyze(stepInc, stepMax);						//	System.out.println(incList);

		// setup net viz parameters
		netViz.setupViz( true, true, palette.red);
		netViz.setupIdViz( false , netGraph, 1 , "black");
		netViz.setupDefaultParam ( netGraph, "red", "black", 5 , 0.05 );
		netViz.setupFixScaleManual( true , netGraph, 50, 0);
		
		// setup gs viz parameters
		gsViz.setupDefaultParam (gsGraph, "red", "white", 6 , 0.5 );
		gsViz.setupIdViz(false, gsGraph, 10 , "black");

		
		//dispay graphs 
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
					
					netViz.setupVizBooleanAtr(true, netGraph,  "black", "red" ) ;
					gsViz.setupViz(true, true, palette.blue);
					
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
