package RdmGsaNet_Analysis_pr02;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.print.attribute.HashAttributeSet;

import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.GraphParseException;
import org.graphstream.stream.file.FileSourceFactory;
import org.graphstream.ui.view.Viewer;


import RdmGsaNetExport.expImage;
import RdmGsaNetExport.expValues;
import RdmGsaNetExport.handleNameFile;
import RdmGsaNetViz.handleVizStype;
import RdmGsaNetViz.handleVizStype.palette;
import RdmGsaNetViz.handleVizStype.stylesheet;
import RdmGsaNet_Analysis_pr02.analysisMultiSim;


public class analysisDGSmultiSim extends analysisMain implements analysisDGS {

	// COSTANTS 
	// private static Map< Double , Double > mapStepClustering = new HashMap();
	protected static Map mapNetStepNodeCountRel = new HashMap() ;

	
	private String analysisId;
	private static File extF  ; 
	
	boolean run ;
	static boolean 	computeClustering ,
					computeDensity , 
					computeAverageDegree ,
					computeNewNodeRel ,
					computeSeedCountRel ,
					computeDensityRegularGraph ,
					computeGlobalCorrelationDegreeInh ,
					computeGlobalCorrelationSeedInh ;

	protected enum layerToAnalyze { gs , net , multiLayer }
	protected static layerToAnalyze layerToAnalyze ;
	
	protected enum typeMultiSim { probability , test }
	protected static typeMultiSim typeMultiSim ;
	
	protected enum typeIndicator { clustering  , density }
	
	
	protected static typeIndicator typeIndicator;
	
	// COSTRUCTOR 
	public analysisDGSmultiSim( String analysisId , boolean run ) {
		this.analysisId = analysisId;
		this.run = run ;	
	}
	
	public void  setParamAnalysisGlobal () { }
	
	public void  setParamAnalysisLocal () { }
	
	public void setWhichGlobalAnalysisNet ( 
			boolean computeClustering , boolean computeDensity , boolean computeAverageDegree ,
			boolean computeNewNodeRel , boolean computeSeedCountRel , boolean computeDensityRegularGraph 
			) {
		this.computeClustering = computeClustering ;
		this.computeDensity = computeDensity ;
		this.computeAverageDegree= computeAverageDegree ;
		this.computeNewNodeRel = computeNewNodeRel ;
		this.computeSeedCountRel = computeSeedCountRel ;
		this.computeDensityRegularGraph = computeDensityRegularGraph ;
	}
	
	public void setWhichGlobalAnalysisMultiLayer ( 
			boolean computeGlobalCorrelationDegreeInh , boolean computeGlobalCorrelationSeedInh
			) {
		this.computeGlobalCorrelationDegreeInh = computeGlobalCorrelationDegreeInh ;
		this.computeGlobalCorrelationSeedInh = computeGlobalCorrelationSeedInh ;
	}
	
	public void setWhichGlobalCorrelation ( ) {}
	
	public void setWhichGlobalAnalysisGs ( 	) {}
	
	public void setWhichGlobalAnalysis ( 
			layerToAnalyze layerToAnalyze , typeMultiSim typeMultiSim   
			) {
		this.layerToAnalyze = layerToAnalyze ;	
		this.typeMultiSim = typeMultiSim ;	
	}

	public void setWhichLocalAnalysis ( ) {	}
	
	
// --------------------------------------------------------------------------------------------------------------------------------------------------	

	 public void computeGlobalMultiSim (int stepMax, int stepInc , String folderMultiSim  ) throws IOException, InterruptedException {
				
		 if ( run == false ) 
				return ; 
			
		 File path = new File(folderMultiSim );
		 String[] pathStartArr = null , pathStepArr ; 
		 int thread = 1 ;
		 
		 File [] files = path.listFiles();							
		
		 ArrayList<File> fileArray = new ArrayList<File>(Arrays.asList(files));
		
//		 fileArray.forEach(  s -> System.out.print("\n" + s.getName()) );
		 Map <Double , ArrayList <Double>> mapToStore = new HashMap < Double , ArrayList <Double>>();
		 for ( File f : fileArray ) {
			 
			 extF = f ;
			 String s = f.getAbsolutePath();	 // System.out.println(s);
	
			 System.out.println(f.getName());
			 String localPathStepGs = handle.getCompletePathInFolder(folderCommonFiles,  "layerGs_step") ;
			 String localPathStepNet = handle.getCompletePathInFolder(s+"\\",  "layerNet_step") ;
			 String localPathStartGs = handle.getCompletePathInFolder(folderCommonFiles,  "layerGs_start") ;
			 String localPathStartNet = handle.getCompletePathInFolder(folderCommonFiles,  "layerNet_start") ;
			
			 String[] localPathStartArr = {localPathStartGs,localPathStartNet} ;
			 String[] localPathStepArr = {localPathStepGs,localPathStepNet} ;
			 
			 handleNameFile.createNewGenericFolder(folderMultiSim + "\\multiSimAnalysis\\", analysisMultiSim.nameFolderMap);
			 
			 if ( layerToAnalyze == layerToAnalyze.net ) {
 
				 if ( computeClustering ) 
					 handleNameFile.createNewGenericFolder(folderMultiSim + "\\multiSimAnalysis\\" + analysisMultiSim.nameFolderMap + "\\", "netClustering");
				
				 if ( computeDensity )
					 handleNameFile.createNewGenericFolder(folderMultiSim + "\\multiSimAnalysis\\" + analysisMultiSim.nameFolderMap + "\\", "netDensity");
				
				 if ( computeAverageDegree )
					 handleNameFile.createNewGenericFolder(folderMultiSim + "\\multiSimAnalysis\\" + analysisMultiSim.nameFolderMap + "\\", "netAverageDegree");
				 
				 if ( computeNewNodeRel )
					 handleNameFile.createNewGenericFolder(folderMultiSim + "\\multiSimAnalysis\\" + analysisMultiSim.nameFolderMap + "\\", "netNewNodeRel");
				
				 if ( computeSeedCountRel )
					 handleNameFile.createNewGenericFolder(folderMultiSim + "\\multiSimAnalysis\\" + analysisMultiSim.nameFolderMap + "\\", "netSeedCountRel");
				
				 if ( computeDensityRegularGraph )
					 handleNameFile.createNewGenericFolder(folderMultiSim + "\\multiSimAnalysis\\" + analysisMultiSim.nameFolderMap + "\\", "netDensityRegularGraph");
			 }

			 else if ( layerToAnalyze == layerToAnalyze.multiLayer ) {
				 if ( computeGlobalCorrelationDegreeInh )
					 handleNameFile.createNewGenericFolder(folderMultiSim + "\\multiSimAnalysis\\" + analysisMultiSim.nameFolderMap + "\\", "multiLayerGlobalCorrelationDegreeInh");
				 
				 if ( computeGlobalCorrelationSeedInh )
					 handleNameFile.createNewGenericFolder(folderMultiSim + "\\multiSimAnalysis\\" + analysisMultiSim.nameFolderMap + "\\", "multiLayerGlobalCorrelationSeedInh");
			 }
			 
			 else if ( layerToAnalyze == layerToAnalyze.gs ) {
				 
			 }
			 computeGlobalStat(stepMax, stepInc, localPathStartArr, localPathStepArr, thread);
		}
	 }
	
	
	
	@Override
	public void computeGlobalStat(int stepMax, int stepInc, String[] pathStartArr, String[] pathStepArr, int thread)
			throws IOException, InterruptedException {

		String pathStartGs = pathStartArr[0];
		String pathStepGs  = pathStepArr[0];

		String pathStartNet = pathStartArr[1];
		String pathStepNet = pathStepArr[1];
		
		if ( pathStepNet == null | pathStepGs == null | pathStartNet == null | pathStartGs == null )
			return ; 
		
		Map 	mapLocalStepClustering = new HashMap() , 
				mapLocalStepDensity = new HashMap() ,
				mapLocalAverageDegree = new HashMap(),
				mapNewNodeRel  = new HashMap(),
				mapSeedCountRel  = new HashMap() , 
				mapDensityRegularGraph = new HashMap() ,
				mapGlobalCorrelationDegreeInh = new HashMap() ,
				mapGlobalCorrelationSeedInh = new HashMap()   ;
	
		switch ( layerToAnalyze ) {
			case gs: {

			} break;
			 
			case net :	{
				computeGlobalStatNet ( "dgsNet" ,  stepMax,  stepInc, pathStartNet,  pathStepNet , 
						mapLocalStepClustering , mapLocalStepDensity , mapLocalAverageDegree ,
						mapNewNodeRel , mapSeedCountRel  , mapDensityRegularGraph ) ;
				
				if ( computeClustering ) 
					expValues.writeMap(true, mapLocalStepClustering, folderMultiSim + "\\multiSimAnalysis\\" + analysisMultiSim.nameFolderMap + "\\netClustering\\" , "netClustering_" + extF.getName() ); 
				
				if ( computeDensity ) 
					expValues.writeMap(true, mapLocalStepDensity, folderMultiSim + "\\multiSimAnalysis\\" + analysisMultiSim.nameFolderMap + "\\netDensity\\" , "netDensity_" + extF.getName() );
				
				if ( computeAverageDegree )
					expValues.writeMap(true, mapLocalAverageDegree , folderMultiSim + "\\multiSimAnalysis\\" + analysisMultiSim.nameFolderMap + "\\netAverageDegree\\" , "netAverageDegree_" + extF.getName() );
			
				if ( computeNewNodeRel )
					expValues.writeMap(true, mapNewNodeRel , folderMultiSim + "\\multiSimAnalysis\\" + analysisMultiSim.nameFolderMap + "\\netNewNodeRel\\" , "netNewNodeRel_" + extF.getName() );

				if ( computeSeedCountRel )
					expValues.writeMap(true, mapSeedCountRel , folderMultiSim + "\\multiSimAnalysis\\" + analysisMultiSim.nameFolderMap + "\\netSeedCountRel\\" , "netSeedCountRel_" + extF.getName() );

				if ( computeDensityRegularGraph )
					expValues.writeMap(true, mapDensityRegularGraph , folderMultiSim + "\\multiSimAnalysis\\" + analysisMultiSim.nameFolderMap + "\\netDensityRegularGraph\\" , "netSDensityRegularGraph_" + extF.getName() );
			} break;

			case multiLayer : {
				computeGlobalStatMultiLayer ( "dgsMultiLayer" ,  stepMax,  stepInc, pathStartNet,  pathStepNet , pathStartGs,  pathStepGs , 
						mapGlobalCorrelationDegreeInh , mapGlobalCorrelationSeedInh  ) ;
				
				if ( computeGlobalCorrelationDegreeInh )
					expValues.writeMap(true, mapGlobalCorrelationDegreeInh , folderMultiSim + "\\multiSimAnalysis\\" + analysisMultiSim.nameFolderMap + "\\multiLayerGlobalCorrelationDegreeInh\\" , "multiLayerGlobalCorrelationDegreeInh_" + extF.getName() );		
				
				if ( computeGlobalCorrelationSeedInh )
					expValues.writeMap(true, mapGlobalCorrelationSeedInh , folderMultiSim + "\\multiSimAnalysis\\" + analysisMultiSim.nameFolderMap + "\\multiLayerGlobalCorrelationSeedInh\\" , "multiLayerGlobalCorrelationSeedInh_" + extF.getName() );		
		
			
			} break ;
		}
	}

	// not simple to implement
	public void computeLocalStat(int stepMax, int stepInc, String[] pathStartArr, String[] pathStepArr, int thread)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
	}

	// PRIVATE METHODS ------------------------------------------------------------------------------------------------------------------------------
	private static Map computeGlobalStatGs ( String dgsId , int stepMax, int stepInc, String pathStart, String pathStep , typeIndicator typeIndicator ) {//		System.out.println( "layer "+ layerToAnalyze );		System.out.println("indic " + typeIndicator );
		Map mapToStore = new  HashMap () ;
		
		return mapToStore ;
	}
	
	
	private static void computeGlobalStatMultiLayer (String dgsId , int stepMax, int stepInc, String pathStartNet, String pathStepNet , String pathStartGs, String pathStepGs  ,
			 Map mapGlobalCorrelationDegreeInh , Map mapGlobalCorrelationSeedInh
			) throws IOException {
		
		Graph gsGraph = new SingleGraph("gsGraph");
		Graph netGraph = new SingleGraph("netGraph");
		
		// create list of step to create images
		ArrayList<Double> incList = analysisDGS.getListStepToAnalyze(stepInc, stepMax);						//	System.out.println(incList);
		
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
					
					for ( Node n : netGraph.getEachNode() ) 
						n.addAttribute("degree",  (double) n.getDegree());	
					
					if ( computeGlobalCorrelationDegreeInh ) {
					//	analysisDGS.computeGlobalCorrelation2(gsGraph, netGraph, "gsInh", "degree" , step , 1 ,  mapGlobalCorrelationDegreeInh , false);
						analysisDGS.computeGlobalCorrelation(gsGraph, netGraph, "gsInh", "degree", true, true , mapGlobalCorrelationDegreeInh, step , true );
				//		System.out.println(mapGlobalCorrelationDegreeInh);
					}
					
					if ( computeGlobalCorrelationSeedInh ) {
//						analysisDGS.computeGlobalCorrelation2(gsGraph, netGraph, "gsInh", "seedGrad" , step , 1 ,  mapGlobalCorrelationSeedInh , false);
						analysisDGS.computeGlobalCorrelation(gsGraph, netGraph, "gsInh", "seedGrad", true, false , mapGlobalCorrelationSeedInh, step , true );
				//		System.out.println(mapGlobalCorrelationSeedInh);
					}
					
					
					
					// stop iteration    
					if ( stepMax == step ) { break; }
				}
			}
		} catch (IOException e) {		}				
		gsFs.end() ;	
		netFs.end() ;	
	}
	
	
	private static void computeGlobalStatNet (String dgsId , int stepMax, int stepInc, String pathStart, String pathStep , 
			Map mapLocalStepClustering  , Map mapLocalStepDensity , Map mapLocalAverageDegree , 
			Map mapNewNodeRel , Map mapSeedCountRel , Map mapDensityRegularGraph
			) throws IOException {
	
	
		// get graph through dgsId of graph
		Graph graph = new SingleGraph(dgsId);
		handleVizStype netViz  = null ;
		
		// create list of step to create images
		ArrayList<Double> incList = analysisDGS.getListStepToAnalyze(stepInc, stepMax);						//	System.out.println(incList);

		// import start graph
		try 														{	graph.read(pathStart);		} 
		catch (	ElementNotFoundException | 
				GraphParseException | 
				org.graphstream.graph.IdAlreadyInUseException |
				java.lang.NullPointerException 					e) 	{	// e.printStackTrace();	
			}
		
		// set file Source for file step
		try {
			fs = FileSourceFactory.sourceFor(pathStep);
			fs.addSink(graph);
		} catch ( java.lang.NullPointerException e) { // e.printStackTrace();	
			return ;
		}
		int s = 0 ;
		// import file step
		try {
			fs.begin(pathStep);
			while ( fs.nextStep()) {
				double step = graph.getStep();							//	System.out.println(step);
				
				if ( incList.contains(step)) {
					// add methods to run for each step in incList
					System.out.println("----------------step " + step + " ----------------" );				
					
					if ( computeClustering )
						analysisDGS.computeGlobalClustering(graph, step,  mapLocalStepClustering );
				
					if ( computeDensity ) 
						analysisDGS.computeGlobalDensity(graph, step, mapLocalStepDensity);
					
					if ( computeAverageDegree )
						analysisDGS.computeAverageDegree(graph, step, mapLocalAverageDegree);
					
					if ( computeNewNodeRel ) {
						analysisDGS.computeStepNewNodeRel(graph, step, mapNewNodeRel, mapNetStepNodeCountRel, s);
						s++;
						}
						
					if ( computeSeedCountRel )
						analysisDGS.computeStepCountNewSeed(graph, step, mapSeedCountRel, true);
					
					if ( computeDensityRegularGraph )
						analysisDGS.computeGlobalDensityRegularGraph(graph, step, mapDensityRegularGraph, 8);
					}
					// stop iteration    
					if ( stepMax == step ) { break; }
				}
		} 
		catch (IOException | 
				org.graphstream.graph.IdAlreadyInUseException |
				java.lang.NullPointerException e) { 	}
	
		fs.end();	
	}
}
