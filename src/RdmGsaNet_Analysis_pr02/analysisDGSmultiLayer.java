package RdmGsaNet_Analysis_pr02;

import java.io.IOException;
import java.util.ArrayList;

import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Node;
import org.graphstream.stream.GraphParseException;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceFactory;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import RdmGsaNetExport.handleNameFile;
import RdmGsaNetViz.handleVizStype;
import RdmGsaNetViz.handleVizStype.palette;
import RdmGsaNetViz.handleVizStype.stylesheet;

public class analysisDGSmultiLayer extends analysisMain implements analysisDGS {

	// COSTANTS
		private boolean	run ,
						doGsViz ,
						doNetViz ,
						computeGsActivedNodes ,
						computeGlobalCorrelation ;
		/*
		// viz constants
		private static FileSource gsFs , netFs ;
		private static ViewPanel  gsView , netView ;
		*/
		
		protected String dgsId ;
			
		// COSTRUCTOR 
		public analysisDGSmultiLayer (boolean run ,  boolean doGsViz ,boolean doNetViz , boolean computeGlobalCorrelation ) {
			this.run = run ;
			this.doGsViz = doGsViz ;
			this.doNetViz = doNetViz ;
			this.computeGlobalCorrelation = computeGlobalCorrelation ;
		}
		
		// correlation parameters 
		protected enum correlationValGs  { gsAct , gsInh 	}
		protected enum correlationValNet { degree , seed }
		protected correlationValGs valGs ;
		protected correlationValNet valNet ; 
		
		protected int depth ;
		
		public void setParametersCorrelation ( correlationValGs valGs , correlationValNet valNet , int depth ) {
			this.valGs = valGs ;
			this.valNet = valNet;
			this.depth = depth ; 
				
		}

		@Override
		public void computeGlobalStat(int stepMax, int stepInc, String[] pathStartArr, String[] pathStepArr , int thread )
				throws IOException, InterruptedException {

			if ( !run )
				return ;

			String pathStartGs = pathStartArr[0];
			String pathStepGs  = pathStepArr[0];

			String pathStartNet = pathStartArr[1];
			String pathStepNet = pathStepArr[1];
			
			// create list of step to create images
			ArrayList<Double> incList = analysisDGS.getListStepToAnalyze(stepInc, stepMax);						//	System.out.println(incList);
			
			handleVizStype	netViz  = new handleVizStype( netGraph , stylesheet.manual , "seedGrad", 1) ,
					 		gsViz 	= new handleVizStype( gsGraph  , stylesheet.viz10Color , "gsAct", 1) ;
			
			netViz.setupFixScaleManual( true , netGraph , 100 , 0 );
			
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
						
						
						if ( computeGlobalCorrelation ) {
							for ( Node n : netGraph.getEachNode() ) {
//								double degreeDouble = (double) n.getDegree() ;
								n.addAttribute("degree",  (double) n.getDegree());
								}		
 							analysisDGS.computeGlobalCorrelation(gsGraph, netGraph, "gsInh", "degree" , step , 1 ,  analysisMultiLayer.mapGlobalCorrelation );
						}
	
						
						if ( doNetViz ) {
							// setup net viz parameters
							netViz.setupViz( true, true, palette.red);
							netViz.setupIdViz( false , netGraph, 1 , "black");
							netViz.setupDefaultParam ( netGraph, "red", "white", 2 , 0.05 );
							netViz.setupVizBooleanAtr(true, netGraph,  "white", "red" ) ;
						}
						if ( doGsViz ) {
							gsViz.setupDefaultParam (gsGraph, "red", "white", 4 , 0.5 );
							gsViz.setupIdViz(false, gsGraph, 10 , "black");
							gsViz.setupViz(true, true, palette.blue);
						}
							
						if ( doGsViz | doNetViz )
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
	
	