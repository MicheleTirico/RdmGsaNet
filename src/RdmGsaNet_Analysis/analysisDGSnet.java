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

import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNetViz.setupViz;
import RdmGsaNet_pr08.gsAlgo;
import RdmGsaNet_pr08.layerGs;
import RdmGsaNet_pr08.setupGsGrid;

public class analysisDGSnet extends analysisMain  implements analysisDGS  {

	// CONSTANT
	private String dgsId ;
	private static FileSource fs ;
	private static Graph graph = new SingleGraph ("graph");
	private int degreeFreq ;
	
	// viz constants
	private static ViewPanel  view ;
	
	private int s = 0 ; 
	
	protected static boolean 	run ,
								runAll ,
								computeDegree ,
								computeAverageDegree , /* avD = 2 * edgeCount / nodeCount */
								computeStepNewNode ,
								computeNormalDegreeDistribution,
								runViz ;
	
	// MAP FOR CHARTS

	// private map
	private Map <Integer , Integer >  mapNetStepNodeCount = new HashMap< Integer , Integer > ();
		
	
// --------------------------------------------------------------------------------------------------------------------------------------------------
	// COSTRUCTOR
	public analysisDGSnet ( String dgsId , boolean run , boolean runAll ) {
		this.dgsId = dgsId;
		this.run = run ;
		this.runAll = runAll ;
	}
	
	// set parameters of analysis
	public void setParamAnalysis ( int degreeFreq ) {
			this.degreeFreq = degreeFreq ;
	}
		
	public void setWhichAnalysis (boolean runViz , boolean computeDegree , boolean computeAverageDegree , boolean computeStepNewNode, boolean computeNormalDegreeDistribution   ) {
		this.runViz = runViz ;
		this.computeDegree  = computeDegree ;	
		this.computeAverageDegree = computeAverageDegree ;
		this.computeStepNewNode = computeStepNewNode ;
		this.computeNormalDegreeDistribution = computeNormalDegreeDistribution ;
	}
			
// COMPUTE MULTIPLE ANALYSIS --------------------------------------------------------------------------------------------------------------
	public void computeMultipleStat(int stepMax, int stepInc, String pathStart, String pathStep) throws IOException, InterruptedException  {
		
		if ( run == false  ) { return ; }
		
		// get graph through dgsId of graph
		graph = analysisDGS.returnGraphAnalysis(dgsId);
		 
		// run viz
		if ( runViz )  {
			System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
			graph.addAttribute("ui.quality");
		    graph.addAttribute("ui.antialias");

			Viewer viewer = graph.display(false) ;	
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
					
					if ( computeDegree  ) 
						analysisDGS.computeFreqDegree( degreeFreq, graph , step , mapNetFreqDegree );	
							
					if ( computeAverageDegree )
						analysisDGS.computeAverageDegree( graph, step, mapNetAverageDegree);
					
					if ( computeStepNewNode ) {
						mapNetStepNodeCount.put(s, graph.getNodeCount());
						try {
							mapNetStepNewNode.put(step,(double) graph.getNodeCount() - mapNetStepNodeCount.get(s-1));//		System.out.println(nodeCount0);
						} catch (java.lang.NullPointerException e) {			}
						s++;
					}
				
					if ( computeNormalDegreeDistribution) 	
						analysisDGS.computeStepNormalDegreeDistribution(graph, step, mapNetStepNormalDistributionDegree, true , 9 );
				
					// run viz
					if ( runViz ) {
					    setupViz.setFixScaleManual(graph, 50 , 0);
					    setupViz.VizSeedGrad(graph, "seedGrad");
						Thread.sleep(50);
					}
					// stop iteration    
					if ( stepMax == step ) { break; }
				}
			}
		} catch (IOException e) {		}				
		fs.end();	
	}
}
