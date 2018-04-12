package RdmGsaNet_Analysis_pr02;

import java.io.IOException;
import java.util.ArrayList;

import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
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
						doVecViz ,
						doSeedViz , 
						computeGsActivedNodes ,
						computeGlobalCorrelation ;
		 
		
		
		// parameters of viz 
		private int setScale ; 
		private double sizeNodeNet , sizeEdgeNet , sizeNodeGs , sizeEdgeGs , sizeNodeVec , sizeEdgeVec , sizeNodeSeed , sizeEdgeSeed ; 
		private String colorStaticNode , colorStaticEdge , colorBooleanNodeTrue , colorBooleanNodeFalse;
		private palette paletteColor;
		
		protected String dgsId ;
			
		// COSTRUCTOR 
		public analysisDGSmultiLayer (boolean run ,  boolean doGsViz , boolean doNetViz , boolean doVecViz , boolean doSeedViz,  boolean computeGlobalCorrelation ) {
			this.run = run ;
			this.doGsViz = doGsViz ;
			this.doNetViz = doNetViz ;
			this.doVecViz = doVecViz ;
			this.doSeedViz = doSeedViz ;
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
		
		// setup viz parameters 
		public void setParamVizNet ( int setScale , double sizeNodeNet , double sizeEdgeNet , 
				String colorStaticNode , String colorStaticEdge , 
				String colorBooleanNodeTrue ,String colorBooleanNodeFalse ) {
			this.setScale = setScale ; 
			this.sizeNodeNet = sizeNodeNet ;
			this.sizeEdgeNet = sizeEdgeNet ;
			this.colorStaticNode = colorStaticNode ;
			this.colorStaticEdge = colorStaticEdge ;
			this.colorBooleanNodeTrue = colorBooleanNodeTrue ;
			this.colorBooleanNodeFalse = colorBooleanNodeFalse ;
		}
		
		public void setParamVizGs ( double sizeNodeGs , double sizeEdgeGs , palette paletteColor ) { 
			this.sizeNodeGs = sizeNodeGs ;
			this.sizeEdgeGs = sizeEdgeGs ;
			this.paletteColor = paletteColor ;
		}	
		
		public void setParamVizVec ( double sizeNodeVec , double sizeEdgeVec ) { 
			this.sizeNodeVec = sizeNodeVec ;
			this.sizeEdgeVec = sizeEdgeVec ;
		}	
		
		public void setParamVizSeed ( double sizeNodeSeed , double sizeEdgeSeed ) { 
			this.sizeNodeSeed = sizeNodeSeed ;
			this.sizeEdgeSeed = sizeEdgeSeed ;
		}

		@Override
		public void computeGlobalStat(int stepMax, int stepInc, String[] pathStartArr, String[] pathStepArr , int thread )
				throws IOException, InterruptedException {

			if ( !run )
				return ;

			Graph	seedGraph = new SingleGraph("seedGraph");
			
			String pathStartGs = pathStartArr[0];
			String pathStepGs  = pathStepArr[0];

			String pathStartNet = pathStartArr[1];
			String pathStepNet = pathStepArr[1];
			
			String pathStartVec = folderCommonFiles + "layerVec_start.dgs";
			String pathStepVec  = folderCommonFiles + "layerVec_step.dgs";
			
			String pathStartSeed = folderCommonFiles + "layerSeed_start.dgs";
			String pathStepSeed  = folder + "layerSeed_step.dgs";
			System.out.println(pathStepSeed);
			
			// create list of step to create images
			ArrayList<Double> incList = analysisDGS.getListStepToAnalyze(stepInc, stepMax);						//	System.out.println(incList);
			
			handleVizStype	netViz  = new handleVizStype( netGraph , stylesheet.manual , "seedGrad", 1) ,
					 		gsViz 	= new handleVizStype( gsGraph  , stylesheet.viz10Color , "gsInh", 1) ,
					 		vecViz 	= new handleVizStype( vecGraph  , stylesheet.viz10Color , "gsInh", 1) ,
					 		seedViz	= new handleVizStype( seedGraph  , stylesheet.viz10Color , "gsInh", 1) ;
							
			
			netViz.setupFixScaleManual( true , netGraph , setScale , 0 );
			vecViz.setupFixScaleManual( true , vecGraph , setScale , 0 );
			seedViz.setupFixScaleManual( true , seedGraph , setScale , 0 );
			
			//dispay graphs 
			if ( doGsViz ) {
				Viewer gsViewer = gsGraph.display(false) ;	
			}
			if ( doNetViz ) {
				Viewer netViewer = netGraph.display(false) ;	
			}
			
			if ( doVecViz ) {
				Viewer vecViewer = vecGraph.display(false) ;	
			}
			if ( doSeedViz ) {
				Viewer seedViewer = seedGraph.display(false) ;	
			}
			
			// read start path
			try {	
				gsGraph.read(pathStartGs);
				netGraph.read(pathStartNet);
				vecGraph.read(pathStartVec);
			//	seedGraph.read(pathStartSeed);
			} 
			
			catch (	ElementNotFoundException | GraphParseException |org.graphstream.graph.IdAlreadyInUseException | java.io.IOException e) 	{ /*	e.printStackTrace();	*/ }
							
			// set file Source for file step
			gsFs = FileSourceFactory.sourceFor(pathStepGs);
			gsFs.addSink(gsGraph);
					
			netFs = FileSourceFactory.sourceFor(pathStepNet);
			netFs.addSink(netGraph);
			
			try {
				vecFs = FileSourceFactory.sourceFor(pathStepVec);
				vecFs.addSink(vecGraph);
			} catch (java.io.IOException e) { 			}
			
			try {
				seedFs = FileSourceFactory.sourceFor(pathStepSeed);
				seedFs.addSink(seedGraph);
			} catch (java.io.IOException e) { 			}
			

			// import file step
			try {
				gsFs.begin(pathStepGs);
				netFs.begin(pathStepNet);
				try {
					vecFs.begin(pathStepVec);
				} catch (java.lang.NullPointerException e) {				}
				
				try {
					seedFs.begin(pathStepSeed);
				} catch (java.lang.NullPointerException e) {				}
				
				while (  gsFs.nextStep() && netFs.nextStep()  &&  vecFs.nextStep() && seedFs.nextStep() ) {
							
					double step = gsGraph.getStep();							//	System.out.println(step);
							
					if ( incList.contains(step)) {
						// add methods to run for each step in incList
						System.out.println("----------------step " + step + " ----------------" );						
						
						if ( computeGlobalCorrelation ) {
							for ( Node n : netGraph.getEachNode() ) {
//								double degreeDouble = (double) n.getDegree() ;
								n.addAttribute("degree",  (double) n.getDegree());
								}		
 							analysisDGS.computeGlobalCorrelation2(gsGraph, netGraph, "gsInh", "degree" , step , 1 ,  analysisMultiLayer.mapGlobalCorrelation, false  );
						}
						
						if ( doNetViz ) {
							// setup net viz parameters
							netViz.setupViz( true, true, palette.red);
							netViz.setupIdViz( false , netGraph, 1 , "black");
							netViz.setupDefaultParam ( netGraph, colorStaticNode, colorStaticEdge, sizeNodeNet , sizeEdgeNet );
							netViz.setupVizBooleanAtr(true, netGraph,  colorBooleanNodeFalse, colorBooleanNodeTrue, false, false ) ;
						}
						if ( doGsViz ) {
							gsViz.setupDefaultParam (gsGraph, "red", "white", sizeNodeGs , sizeEdgeGs );
							gsViz.setupIdViz(false, gsGraph, 10 , "black");		
							gsViz.setupViz(true, true, paletteColor);
						}
						
						if ( doVecViz ) {
							vecViz.setupIdViz(false, vecGraph, 4 , "black");
							vecViz.setupDefaultParam (vecGraph, "black", "black", sizeNodeVec , sizeEdgeVec );
							vecViz.setupVizBooleanAtr(true, vecGraph, "black", "red" , true, true ) ;
						}
						
						if ( doSeedViz ) {
							seedViz.setupIdViz(false, seedGraph, 4 , "black");
							seedViz.setupDefaultParam (seedGraph, "black", "black", sizeNodeSeed , sizeEdgeSeed );
							seedViz.setupVizBooleanAtr(false, seedGraph, "black", "red" , true, true ) ;
						}
							
						
						
							
						if ( doGsViz | doNetViz | doVecViz | doSeedViz )
							Thread.sleep(thread);
						
						// stop iteration    
						if ( stepMax == step ) { break; }
					}
				}
			} catch (IOException  e ) {		}				
			gsFs.end() ;	
			netFs.end() ;
			vecFs.end() ;
			seedFs.end() ;
		}

		@Override
		public void computeLocalStat(int stepMax, int stepInc, String[] pathStartArr, String[] pathStepArr , int thread )
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			
		}

			
		


		
}
	
	