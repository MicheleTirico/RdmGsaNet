package RdmGsaNet_Analysis_pr02;

import java.io.IOException;
import java.util.ArrayList;

import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.stream.GraphParseException;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceFactory;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import RdmGsaNetViz.handleVizStype;
import RdmGsaNetViz.handleVizStype.palette;
import RdmGsaNetViz.handleVizStype.stylesheet;

public class analysisDGSmultiLayer extends analysisMain implements analysisDGS {

	// COSTANTS
		private boolean	doGsViz ,
						doNetViz ,
						computeGsActivedNodes ;
		/*
		// viz constants
		private static FileSource gsFs , netFs ;
		private static ViewPanel  gsView , netView ;
		*/
		
		protected String dgsId ;
			
		// COSTRUCTOR 
		public analysisDGSmultiLayer ( boolean doGsViz ,boolean doNetViz) {
			this.doGsViz = doGsViz ;
			this.doNetViz = doNetViz ;
		}

		@Override
		public void computeGlobalStat(int stepMax, int stepInc, String[] pathStartArr, String[] pathStepArr , int thread )
				throws IOException, InterruptedException {

			if ( !doGsViz && !doNetViz )
				return ;

			String pathStartGs = pathStartArr[0];
			String pathStepGs  = pathStepArr[0];

			String pathStartNet = pathStartArr[1];
			String pathStepNet = pathStepArr[1];
			
			// create list of step to create images
			ArrayList<Double> incList = analysisDGS.getListStepToAnalyze(stepInc, stepMax);						//	System.out.println(incList);
			handleVizStype	netViz  = new handleVizStype( netGraph , stylesheet.manual , "seedGrad", 1) ,
					 		gsViz 	= new handleVizStype( gsGraph  , stylesheet.viz10Color , "gsInh", 1) ;
			
			netViz.setupFixScaleManual( true , netGraph , 50 , 0 );
			
			
			//dispay graphs 
			if ( doGsViz ) {
				Viewer gsViewer = gsGraph.display(false) ;	
			}
			if ( doNetViz ) {
				Viewer netViewer = netGraph.display(false) ;	
			}
			
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
						
						// setup net viz parameters
						
						netViz.setupViz( true, true, palette.red);
						netViz.setupIdViz( false , netGraph, 1 , "black");
						netViz.setupDefaultParam ( netGraph, "red", "black", 5 , 0.05 );
						
						
						// setup gs viz parameters
						gsViz.setupDefaultParam (gsGraph, "red", "white", 6 , 0.5 );
						gsViz.setupIdViz(false, gsGraph, 10 , "black");
						
						netViz.setupVizBooleanAtr(true, netGraph,  "black", "red" ) ;
						gsViz.setupViz(true, true, palette.blue);
						
						Thread.sleep(thread);
						
						// stop iteration    
						if ( stepMax == step ) { break; }
					}
				}
			} catch (IOException e) {		}				
			gsFs.end() ;	
			netFs.end() ;
		}

		@Override
		public void computeLocalStat(int stepMax, int stepInc, String[] pathStartArr, String[] pathStepArr , int thread )
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			
		}

			
		


		
}
	
	