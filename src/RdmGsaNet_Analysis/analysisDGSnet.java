package RdmGsaNet_Analysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.GraphParseException;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceFactory;

import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNetViz.setupViz;
import RdmGsaNet_pr08.gsAlgo;

public class analysisDGSnet implements analysisDGS {

	// CONSTANT
	private String dgsId ;
	private static FileSource fs ;
	private Graph graph = new SingleGraph ("graph");
	private int degreeFreq ;
	
	private int s = 0 ; 
	
	protected static boolean 	run ,
								runAll ,
								computeDegree ,
								computeStepNewNode ,
								runViz ;
	
	// MAP FOR CHARTS
	protected Map mapFreqDegree = analysisMain.mapNetFreqDegree ,
					mapNetStepNewNode = analysisMain.mapNetStepNewNode;

	private Map <Integer , Integer >  mapNetStepNodeCount = new HashMap< Integer , Integer > () ;
	
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
		
	public void setWhichAnalysis (boolean runViz , boolean computeDegree , boolean computeStepNewNode  ) {
		this.runViz = runViz ;
		this.computeDegree  = computeDegree  ;	
		this.computeStepNewNode = computeStepNewNode ;
	}
			
// COMPUTE MULTIPLE ANALYSIS --------------------------------------------------------------------------------------------------------------
	public void computeMultipleStat(int stepMax, int stepInc, String pathStart, String pathStep) throws IOException, InterruptedException  {
		
		if ( run == false  ) { return ; }
		
		// get graph through dgsId of graph
		graph = analysisDGS.returnGraphAnalysis(dgsId);
		 
		// run viz
		if ( runViz ) 
			graph.display(false);
		
		// create list of step to create images
		ArrayList<Double> incList = analysisDGS.getListStepAnalysis(stepInc, stepMax);						//	System.out.println(incList);

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
						
						analysisDGS.computeFreqDegree( degreeFreq, graph , step , mapFreqDegree );	
							
					if ( computeStepNewNode ) {
						mapNetStepNodeCount.put(s, graph.getNodeCount());
						try {
							mapNetStepNewNode.put(step,(double) graph.getNodeCount() - mapNetStepNodeCount.get(s-1));//		System.out.println(nodeCount0);
						} catch (java.lang.NullPointerException e) {			}
						s++;
					}
					
					// run viz
					if ( runViz )
						// setupViz.VizNodeId(graph);
						setupViz.VizSeedGrad(graph, "seedGrad");
						Thread.sleep(50);
					
					// stop iteration    
					if ( stepMax == step ) { break; }
				}
			}
		} catch (IOException e) {		}				
		fs.end();	
	}
	
	private static String setVizNodeId () {
		return  "node { "
				+ "size: 10px;"
				+ "fill-color: black;"
				+ "text-alignment: at-right; "
				+ "text-color: black; "
				+ "text-background-mode: plain; "
				+ "text-background-color: white; "
				+ "}";
	}
	
}
