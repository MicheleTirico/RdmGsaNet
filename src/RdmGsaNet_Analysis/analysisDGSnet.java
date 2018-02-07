package RdmGsaNet_Analysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.SynchronousQueue;

import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.GraphParseException;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceFactory;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import RdmGsaNetAlgo.graphAnalysis;
import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNetExport.expImage;
import RdmGsaNetExport.handleNameFile;
import RdmGsaNetViz.setupViz;
import RdmGsaNetViz.handleVizStype.palette;
import RdmGsaNet_pr08.gsAlgo;
import RdmGsaNet_pr08.layerGs;
import RdmGsaNet_pr08.setupGsGrid;

public class analysisDGSnet extends analysisMain  implements analysisDGS  {

	// CONSTANT
	private String dgsId ;
	private static FileSource fs ;
	protected static Graph graph = new SingleGraph ("graph");
	private int degreeFreq ;
	
	// viz constants
	private static ViewPanel  view ;
	private int stepIncIm ;
	
	private int s1 = 0 , s2 = 0 ; 
	
	
	protected static boolean 	run ,
								getImage ,
								computeFreqDegree ,
								computeFreqDegreeRel ,
								computeAverageDegree , /* avD = 2 * edgeCount / nodeCount */
								computeStepNewNode ,
								computeNormalDegreeDistribution,
								computeStepNewNodeRel ,
								computeNewSeedCount ,
								computeNewSeedCountRel ,
								runViz ;
	
// MAP FOR CHARTS
	// private map
	private Map <Integer , Integer >  mapNetStepNodeCount = new HashMap< Integer , Integer > (),
										mapNetStepNodeCountRel = new HashMap< Integer , Integer > () ;
	
// --------------------------------------------------------------------------------------------------------------------------------------------------
	// COSTRUCTOR
	public analysisDGSnet ( String dgsId , boolean run , boolean getImage ) {
		this.dgsId = dgsId;
		this.run = run ;
		
	}
	
	// set parameters of analysis
	public void setParamAnalysis ( int degreeFreq , int stepIncIm ) {
		this.degreeFreq = degreeFreq ;
		this.stepIncIm = stepIncIm ;	
	}
		
	public void setWhichAnalysis (	boolean runViz , boolean getImage , boolean computeFreqDegree , boolean computeFreqDegreeRel , boolean computeAverageDegree , 
									boolean computeStepNewNode, boolean computeStepNewNodeRel, boolean computeNormalDegreeDistribution ,
									boolean computeNewSeedCount , boolean computeNewSeedCountRel ) {
		
		this.runViz = runViz ;
		this.getImage = getImage ;
		this.computeFreqDegree  = computeFreqDegree ;	
		this.computeFreqDegreeRel  = computeFreqDegreeRel ;	
		this.computeAverageDegree = computeAverageDegree ;
		this.computeStepNewNode = computeStepNewNode ;
		this.computeStepNewNodeRel = computeStepNewNodeRel ;
		this.computeNormalDegreeDistribution = computeNormalDegreeDistribution ;
		this.computeNewSeedCount = computeNewSeedCount ;
		this.computeNewSeedCountRel = computeNewSeedCountRel ;
		
		if ( getImage ) {
			handle.createFolder(folder + "analysis\\", "image", false ) ;
		}
			
	}
			
// COMPUTE MULTIPLE ANALYSIS --------------------------------------------------------------------------------------------------------------
	public void computeMultipleStat(int stepMax, int stepInc, String pathStart, String pathStep) throws IOException, InterruptedException  {
		
		if ( run == false  )  
			return ; 
		
		// get graph through dgsId of graph
		graph = analysisDGS.returnGraphAnalysis(dgsId);
		 
		// run viz
		if ( runViz )  {
			// setup net viz parameters
			netViz.setupViz( true, true, palette.red);
			netViz.setupIdViz( false , graph, 1 , "black");
			netViz.setupDefaultParam ( graph, "red", "black", 5 , 0.05 );
			netViz.setupFixScaleManual( true , graph, 50, 0);
			Viewer netViewer = graph.display(false) ;	
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
					
					if ( getImage ) 	
						if (  analysisDGS.getListStepToAnalyze( stepIncIm , stepMax).contains(step) ) 
							expImage.getImage(graph, folder + "analysis\\image\\" , "netImage" + step + ".png" );
						
					if ( computeFreqDegree  ) 
						analysisDGS.computeFreqDegree( degreeFreq, graph , step , mapNetFreqDegree );	
					
					if ( computeFreqDegree  ) 
						analysisDGS.computeFreqDegreeRel( degreeFreq, graph , step , mapNetFreqDegreeRel );	
							
					if ( computeAverageDegree )
						analysisDGS.computeAverageDegree( graph, step, mapNetAverageDegree);
					
					if ( computeStepNewNode ) {
						mapNetStepNodeCount.put(s1, graph.getNodeCount());
						try {
							mapNetStepNewNode.put(step,(double) graph.getNodeCount() - mapNetStepNodeCount.get(s1-1));//		System.out.println(nodeCount0);
						} catch (java.lang.NullPointerException e) {			}
						s1++;
					}
					
					if ( computeStepNewNodeRel) {
						mapNetStepNodeCountRel.put(s2, graph.getNodeCount());
						try {
							mapNetStepNewNodeRel.put(step,(double) ( graph.getNodeCount() - mapNetStepNodeCountRel.get(s2-1) ) /  graph.getNodeCount() );//		System.out.println(nodeCount0);
						} catch (java.lang.NullPointerException e) {			}
						s2++;	
					}
								
					if ( computeNormalDegreeDistribution) 	
						analysisDGS.computeStepNormalDegreeDistribution(graph, step, mapNetStepNormalDistributionDegree, true , 9 );
				
					if ( computeNewSeedCount )
						analysisDGS.computeStepCountNewSeed(graph, step, mapNetStepNewSeed, false);
					
					if ( computeNewSeedCountRel )
						analysisDGS.computeStepCountNewSeed(graph, step, mapNetStepNewSeedRel, true);
					
					
					// run viz
					if ( runViz ) {
						netViz.setupVizBooleanAtr(true, graph,  "black", "red" ) ;
						Thread.sleep(50);
					}
					// stop iteration    
					if ( stepMax == step ) { break; }
				}
			}
		} catch (IOException e) {		}				
		fs.end();	
		System.out.println(mapNetStepNodeCount);
	}
}
