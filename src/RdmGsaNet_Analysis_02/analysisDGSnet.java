package RdmGsaNet_Analysis_02;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.GraphParseException;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceFactory;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import RdmGsaNetAlgo.graphAnalysis;
import RdmGsaNetAlgo.graphIndicators;
import RdmGsaNetExport.*;
import RdmGsaNetViz.handleVizStype;
import RdmGsaNetViz.handleVizStype.palette;
import RdmGsaNetViz.handleVizStype.stylesheet;
import RdmGsaNet_Analysis_02.analysisMain;
import RdmGsaNet_Analysis_02.analysisLocal.nodeIndicators;

public  class analysisDGSnet extends analysisMain implements analysisDGS     {


	// CONSTANT
	private String dgsId ;
	
	private int degreeFreq ;
	
	// viz constants
	private static ViewPanel  view ;
	private int stepIncIm ;
	
	private int s1 = 0 , s2 = 0 ;
	
	// parameters of viz 
	private int setScale ; 
	private double sizeNode , sizeEdge ; 
	private String colorStaticNode , colorStaticEdge , colorBooleanNodeTrue , colorBooleanNodeFalse;
	private palette paletteColor ;
		
	protected static  boolean 	
	/* common boolean 		*/ 		run,  getImage, runViz;
	
	protected boolean 
	/* global compute boolean*/		computeFreqDegree, 
									computeFreqDegreeRel, 
									computeAverageDegree,
									computeStepNewNode, 
									computeNormalDegreeDistribution, 
									computeStepNewNodeRel, 
									computeNewSeedCount, 
									computeNewSeedCountRel,
									computeGlobalClustering,
									computeGlobalDensity;
	protected boolean
	/* local compute boolean*/		computeLocalClustering,
									computeLocalCloseness,
									computeLocalBetweenness;
	
	// set parameter analysis
	private boolean closenessNormalize , betweennessNormalize ;
// MAP FOR CHARTS
	// private map
	private Map <Integer , Integer >  	mapNetStepNodeCount = new HashMap< Integer , Integer > (),
										mapNetStepNodeCountRel = new HashMap< Integer , Integer > () ;
	
// --------------------------------------------------------------------------------------------------------------------------------------------------
	// COSTRUCTOR
	public analysisDGSnet ( String dgsId , boolean run ) {
		this.dgsId = dgsId;
		this.run = run ;
	} 
	
	// set parameters of analysis
	public void setParamAnalysis ( int degreeFreq , int stepIncIm ) {
		this.degreeFreq = degreeFreq ;
		this.stepIncIm = stepIncIm ;	
	}
	
	// setup viz parameters 
	public void setParamVizNet ( int setScale , double sizeNode , double sizeEdge , 
			String colorStaticNode , String colorStaticEdge , 
			String colorBooleanNodeTrue ,String colorBooleanNodeFalse, palette paletteColor ) {
		this.setScale = setScale ; 
		this.sizeNode = sizeNode ;
		this.sizeEdge = sizeEdge ;
		this.colorStaticNode = colorStaticNode ;
		this.colorStaticEdge = colorStaticEdge ;
		this.colorBooleanNodeTrue = colorBooleanNodeTrue ;
		this.colorBooleanNodeFalse = colorBooleanNodeFalse ;
		this.paletteColor = paletteColor ;
	}
	
	// set parameters of analysis
	public void setParamAnalysisLocal ( boolean closenessNormalize , boolean betweennessNormalize ) {
			this.closenessNormalize = closenessNormalize;
			this.betweennessNormalize = betweennessNormalize ;			
		}
		
	public void setWhichLocalAnalysis ( boolean runViz , boolean getImage , 
			boolean computeLocalClustering , boolean computeLocalCloseness , boolean computeLocalBetweenness ) {
		this.runViz = runViz ;
		this.getImage = getImage ;
		this.computeLocalClustering = computeLocalClustering ;	
		this.computeLocalCloseness = computeLocalCloseness ;
		this.computeLocalBetweenness = computeLocalBetweenness ;
	}	
	
	public void setWhichGlobalAnalysis (boolean runViz, boolean getImage , 
			boolean computeFreqDegree , boolean computeFreqDegreeRel , boolean computeAverageDegree , 
			boolean computeStepNewNode, boolean computeStepNewNodeRel, boolean computeNormalDegreeDistribution ,
			boolean computeNewSeedCount , boolean computeNewSeedCountRel ,
			boolean computeGlobalClustering , boolean computeGlobalDensity ) {
		
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
		this.computeGlobalClustering = computeGlobalClustering  ;
		this.computeGlobalDensity = computeGlobalDensity ;
		
		if ( getImage ) {
			handle.createFolder(folder + "analysis\\", "image", false ) ;
		}		
	}
			
// COMPUTE MULTIPLE ANALYSIS --------------------------------------------------------------------------------------------------------------
	public void computeGlobalStat (int stepMax, int stepInc, String[] pathStartArr, String[] pathStepArr , int thread ) 
			throws IOException, InterruptedException  {
			
		if ( run == false  )  
			return ;  
		 
		String pathStart = pathStartArr[1];
		String pathStep = pathStepArr[1];

		// get graph through dgsId of graph
		graph = analysisDGS.returnGraphAnalysis(dgsId);
		handleVizStype netViz = new handleVizStype( graph ,stylesheet.manual , "seedGrad", 1) ;
		
		// run viz
		if ( runViz )  { 
			
			// setup net viz parameters
			netViz.setupIdViz(false, graph, 1 , "black");
			netViz.setupDefaultParam (graph, colorStaticNode, colorStaticEdge , sizeNode , sizeEdge );
			
			netViz.setupFixScaleManual( true, graph, setScale , 0);

			Viewer netViewer =  graph.display(false) ;	
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
					
					netViz.setupVizBooleanAtr(true, graph, colorBooleanNodeFalse ,colorBooleanNodeTrue , false, false ) ;
						
					if ( computeFreqDegree  ) 
						analysisDGS.computeFreqDegree( degreeFreq, graph , step , analysisGlobal.mapNetFreqDegree );	
					
					// return same result of degree distribution
					if ( computeFreqDegreeRel  )  
						analysisDGS.computeFreqDegreeRel( degreeFreq, graph , step , analysisGlobal.mapNetFreqDegreeRel );	
						
					if ( computeAverageDegree )
						analysisDGS.computeAverageDegree( graph, step, analysisGlobal.mapNetAverageDegree);
					
					if ( computeStepNewNode ) {
						mapNetStepNodeCount.put(s1, graph.getNodeCount());
						try {
							analysisGlobal.mapNetStepNewNode.put(step,(double) graph.getNodeCount() - mapNetStepNodeCount.get(s1-1));//		System.out.println(nodeCount0);
						} catch (java.lang.NullPointerException e) {			}
						s1++;
					}
					
					if ( computeStepNewNodeRel) { 
						mapNetStepNodeCountRel.put(s2, graph.getNodeCount());
						try {
							analysisGlobal.mapNetStepNewNodeRel.put(step,(double) ( graph.getNodeCount() - mapNetStepNodeCountRel.get(s2-1) ) /  graph.getNodeCount() );//		System.out.println(nodeCount0);
						} catch (java.lang.NullPointerException e) {			}
						s2++;	
					}
								
					if ( computeNormalDegreeDistribution) 	
						analysisDGS.computeStepNormalDegreeDistribution(graph, step, analysisGlobal.mapNetStepNormalDistributionDegree, true , 9 );
				
					if ( computeNewSeedCount ) 
						analysisDGS.computeStepCountNewSeed(graph, step, analysisGlobal.mapNetStepNewSeed, false);
					
					if ( computeNewSeedCountRel )
						analysisDGS.computeStepCountNewSeed(graph, step, analysisGlobal.mapNetStepNewSeedRel, true);
					
					if ( computeGlobalClustering  )
						analysisDGS.computeGlobalClustering(graph, step, analysisGlobal.mapNetStepGlobalClustering);
					
					if ( computeGlobalDensity )
						analysisDGS.computeGlobalDensity(graph, step, analysisGlobal.mapNetStepGlobalDensity);
					
					// run viz
					if ( runViz ) 	
						Thread.sleep( thread );
						
//					if ( getImage ) 	
//						if (  analysisDGS.getListStepToAnalyze( stepIncIm , stepMax).contains(step) ) 
				//			expImage.getImage(graph, folder + "analysis\\image\\" , "netImage" + step + ".png" );
//							expImage.getImageStep(graph, folder + "analysis\\image\\" , "netImage" + step + ".png", (int)step);
					Toolkit.computeLayout(graph);
					graph.write("D:\\ownCloud\\RdmGsaNet_exp\\test\\test.jpeg");					
					// stop iteration    
					if ( stepMax == step ) { break; }
				}
			}
		} catch (IOException e) {		}				
		fs.end();	
	}

	public void computeLocalStat (int stepMax, int stepInc, String[] pathStartArr, String[] pathStepArr , int thread   )
			throws IOException, InterruptedException {
		
		if ( run == false  )  
			return ; 
		
		String pathStart = pathStartArr[1];
		String pathStep = pathStepArr[1];
		
		
		// get graph through dgsId of graph
		graph = analysisDGS.returnGraphAnalysis(dgsId);
		
		// create list of step to create images
		ArrayList<Double> incList = analysisDGS.getListStepToAnalyze(stepInc, stepMax);						//	System.out.println(incList);
		
		// setup Viz
		handleVizStype netViz = null ;
		String indicator = null ;
		
		if 		( computeLocalClustering ) 
			indicator =  analysisLocal.getIndicator(nodeIndicators.clustering) ;
		
		else if ( computeLocalCloseness )
			indicator = analysisLocal.getIndicator(nodeIndicators.closeness ) ;
		
		else if ( computeLocalBetweenness )
			indicator = analysisLocal.getIndicator(nodeIndicators.betweenness ) ;
			
			
		
		Viewer netViewer = graph.display(false) ;
		
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
					
					netViz = new handleVizStype( graph , stylesheet.viz10Color , indicator , 1 )  ;
					netViz.setupDefaultParam (graph, "red", "black", sizeNode , sizeEdge );		
					netViz.setupFixScaleManual( true , graph, setScale, 0);	
					
					// create Map
					Map<Node, Double > mapToUpdate = new HashMap<>() ;			
					
					if 		( computeLocalClustering ) 
						mapToUpdate = graphIndicators.getMapNodeClustering(graph);
					
					else if ( computeLocalCloseness )
						mapToUpdate = graphIndicators.getMapCloseness(graph, indicator ,closenessNormalize);
					
					else if ( computeLocalBetweenness )
						mapToUpdate = graphIndicators.getMapBetweenness(graph, indicator , betweennessNormalize);
								
					// add value to attribute
					for ( Node n : mapToUpdate.keySet() ) 
						graph.getNode(n.getId()).addAttribute(indicator, mapToUpdate.get(n)) ;
					
					netViz.setupIdViz( false , graph , 1 , "black");
					netViz.setupLabelViz(false , graph, 0.1, "black", indicator);
					netViz.setupViz(true, true, paletteColor );

					/* doesn't work 
					if ( getImage ) 	
						if (  analysisDGS.getListStepToAnalyze( stepIncIm , stepMax).contains(step) ) 
							expImage.getImage(graph, "D:\\ownCloud\\RdmGsaNet_exp\\test\\" , "peppe" + step + ".png" );
			
					 	*/
				
					// System.out.println(mapToUpdate);
					
					Thread.sleep( thread );
//					RdmGsaNetExport.expValues.writeMap(true, mapToUpdate, "D:\\ownCloud\\RdmGsaNet_exp\\test\\", "nameMap"+ step );
					// stop iteration    
					if ( stepMax == step ) { break; }
				}
			
			}
			
			
		} catch (IOException e) {		}				
		fs.end();	
	
		
	}
}


