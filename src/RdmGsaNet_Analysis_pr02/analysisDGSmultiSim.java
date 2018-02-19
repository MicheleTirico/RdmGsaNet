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

	private static int s2 = 0 ;
	private String analysisId;
	private static File extF  ; 
	
	boolean run ;
	static boolean 	computeClustering ,
					computeDensity , 
					computeAverageDegree ,
					computeNewNodeRel ,
					computeSeedCountRel	;

	protected enum layerToAnalyze { gs , net , both }
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
	
	public void setWhichGlobalAnalysis ( layerToAnalyze layerToAnalyze , typeMultiSim typeMultiSim  , 
			boolean computeClustering , boolean computeDensity , boolean computeAverageDegree ,
			boolean computeNewNodeRel , boolean computeSeedCountRel) {
		
		this.layerToAnalyze = layerToAnalyze ;	
		this.typeMultiSim = typeMultiSim ;
		this.computeClustering = computeClustering ;
		this.computeDensity = computeDensity ;
		this.computeAverageDegree= computeAverageDegree ;
		this.computeNewNodeRel = computeNewNodeRel ;
		this.computeSeedCountRel = computeSeedCountRel ;
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
			 String localPathStepGs = handle.getCompletePathInFolder(s+"\\",  "layerGs_step") ;
			 String localPathStepNet = handle.getCompletePathInFolder(s+"\\",  "layerNet_step") ;
			 String localPathStartGs = handle.getCompletePathInFolder(s+"\\",  "layerGs_start") ;
			 String localPathStartNet = handle.getCompletePathInFolder(s+"\\",  "layerNet_start") ;
			 
			 String[] localPathStartArr = {localPathStartGs,localPathStartNet} ;
			 String[] localPathStepArr = {localPathStepGs,localPathStepNet} ;
			 
			 handleNameFile.createNewGenericFolder(folderMultiSim + "\\multiSimAnalysis\\", analysisMultiSim.nameFolderMap);
			 
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
				mapSeedCountRel  = new HashMap();
	
		switch ( layerToAnalyze ) {
			case gs: {

			} break;
			 
			case net :	{
				computeGlobalStatNet ( "dgsNet" ,  stepMax,  stepInc, pathStartNet,  pathStepNet , 
						mapLocalStepClustering , mapLocalStepDensity , mapLocalAverageDegree ,
						mapNewNodeRel , mapSeedCountRel  ) ;

			} break;

	
			case both : {
			
			} break ;
		}
	//	System.out.println(mapLocalStepClustering); 
	//	System.out.println(mapLocalStepDensity); 
		if ( computeClustering ) 
			expValues.writeMap(true, mapLocalStepClustering, folderMultiSim + "\\multiSimAnalysis\\" + analysisMultiSim.nameFolderMap + "\\netClustering\\" , "netClustering_" + extF.getName() ); 
		
		if ( computeDensity ) 
			expValues.writeMap(true, mapLocalStepDensity, folderMultiSim + "\\multiSimAnalysis\\" + analysisMultiSim.nameFolderMap + "\\netDensity\\" , "netDensity_" + extF.getName() );
		
		if ( computeAverageDegree )
			expValues.writeMap(true, mapLocalAverageDegree , folderMultiSim + "\\multiSimAnalysis\\" + analysisMultiSim.nameFolderMap + "\\netAverageDegree\\" , "netAverageDegree_" + extF.getName() );
	
		if ( computeNewNodeRel )
			expValues.writeMap(true, mapNewNodeRel , folderMultiSim + "\\multiSimAnalysis\\" + analysisMultiSim.nameFolderMap + "\\netNewNodeRel\\" , "netNewNodeRel_" + extF.getName() );

		if ( computeSeedCountRel)
			expValues.writeMap(true, mapSeedCountRel , folderMultiSim + "\\multiSimAnalysis\\" + analysisMultiSim.nameFolderMap + "\\netSeedCountRel\\" , "netSeedCountRel_" + extF.getName() );

		
		
		// create map to store	

	//	ArrayList <Double> arr = new ArrayList<Double>( mapLocalStepClustering.values() ) ;

		// ,System.out.println(arr);
		// store map methods 
		
		
	}

	// not simple to implement
	public void computeLocalStat(int stepMax, int stepInc, String[] pathStartArr, String[] pathStepArr, int thread)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
	}
	
	private static void setVal ( File i , String[] pathStartArr ) {
		String s = i.toString() ;
	//	pathStartArr[i.toString()];
	}

	// PRIVATE METHODS ------------------------------------------------------------------------------------------------------------------------------
	private static Map computeGlobalStatGs ( String dgsId , int stepMax, int stepInc, String pathStart, String pathStep , typeIndicator typeIndicator ) {//		System.out.println( "layer "+ layerToAnalyze );		System.out.println("indic " + typeIndicator );
		Map mapToStore = new  HashMap () ;
		
		return mapToStore ;
	}
	
	private static void computeGlobalStatNet (String dgsId , int stepMax, int stepInc, String pathStart, String pathStep , 
			Map mapLocalStepClustering  , Map mapLocalStepDensity , Map mapLocalAverageDegree , 
			Map mapNewNodeRel , Map mapSeedCountRel 
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
						analysisDGS.computeStepNewNodeRel(graph, step, mapNewNodeRel, mapNetStepNodeCountRel, s2);
						}
						
					if ( computeSeedCountRel )
						analysisDGS.computeStepCountNewSeed(graph, step, mapSeedCountRel, true);
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
