package RdmGsaNet_Analysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.stream.GraphParseException;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceDGS;
import org.graphstream.stream.file.FileSourceFactory;

import RdmGsaNetAlgo.morpAnalysis;
import RdmGsaNetAlgo.morpAnalysis.analysisType;
import scala.annotation.meta.setter;

public class analysisDGS {
	
	private static FileSource 	fs ;
	private static int 			stepMax, stepInc ;
	private static String 		pathStart , pathStep; 
	
	private static Graph gsGraph = analysisMain.gsGraph;
	private static Graph netGraph = analysisMain.netGraph; 
	
	
	// boolean to choice which charts to create
	private static boolean computeDegree;
	
	// degree parameters chart
	private static int degreeFreq ;
	private static int degreeFreqGs ;
	private static int degreeFreqNet ;
	
	public static enum runMethods { onlyOneStatAnalysis , allAnalysis }
	public static runMethods method;

	static Graph graphAnalysis ;
	public analysisDGS ( boolean computeDegree ) {
		analysisDGS.computeDegree = computeDegree;
	}

	// method to import start graph
	public static void readStartDGS ( Graph graph , String pathStart ) throws IOException {

		FileSourceDGS source = new FileSourceDGS();
		
		source.addSink( graph );
		source.begin(pathStart);
		
		while( source.nextEvents() ){	}
		source.end();
	}
	
	/* method to read graph at each step and run methods
	 * we should defined in while loop which method to be run   */
	public static void readStepDGS  ( 	Graph graph , String morp ,
										int stepMax , int stepInc ,
										String pathStart , String pathStep , 
										analysisType type , Map <Double , Double > mapStepVal ) throws IOException {
		
		// create list of step to create images
		ArrayList<Double> incList = getListStepAnalysis(stepInc, stepMax);						//	System.out.println(incList);
				
		// import start graph
		try 																										{	graph.read(pathStart);		} 
		catch (ElementNotFoundException | GraphParseException | org.graphstream.graph.IdAlreadyInUseException e) 	{	/*e.printStackTrace();*/	}
				
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
			
					double val = morpAnalysis.getAttributeStatistic(graph, morp, type );
					mapStepVal.put(step, val);		//	System.out.println(val);					
				}
			}
		} catch (IOException e) {		}				
		fs.end();	
	}
	
	public  void computeMultipleStat ( 	Graph graph ,	String morp , 
										int stepMax , int stepInc ,
										String pathStart , String pathStep 
										) throws IOException { 
		
		
		graphAnalysis = checkDgsGraph(this);
	
		// create list of step to create images
		ArrayList<Double> incList = getListStepAnalysis(stepInc, stepMax);						//	System.out.println(incList);

		// import start graph
		try 																										{	graphAnalysis.read(pathStart);		} 
		catch (ElementNotFoundException | GraphParseException | org.graphstream.graph.IdAlreadyInUseException e) 	{	/*e.printStackTrace();*/	}
		
		// set file Source for file step
		fs = FileSourceFactory.sourceFor(pathStep);
		fs.addSink(graphAnalysis);

		// import file step
		try {
			fs.begin(pathStep);
			while ( fs.nextStep()) {

				double step = graphAnalysis.getStep();							//	System.out.println(step);

				if ( incList.contains(step)) {
					// add methods to run for each step in incList
					System.out.println("----------------step " + step + " ----------------" );
	
					int nodeCount = graphAnalysis.getNodeCount();
					System.out.println("nodeCount " + graphAnalysis + " " + nodeCount);
					
					
					if ( computeDegree == true ) {
						computeFreqDegree( graphAnalysis , degreeFreq);
					}
							
					// stop iteration    
					if ( stepMax == step ) { break; }
				}
			}
		} catch (IOException e) {		}				
		fs.end();	
	}
	
	public static  Graph  checkDgsGraph ( analysisDGS dgsGraph ) {
		
		boolean test = dgsGraph.equals(analysisMain.dgsGs);
		Graph graph = null ;
		
		if (test == true ) {	// 	System.out.println("gs");
			graph = gsGraph;
			analysisDGS.degreeFreq = degreeFreq ;
		}
		else {					//	System.out.println("net");
			graph = netGraph;
		}		
		return graph;
	}
	
	public static Map<Double, Double> getMapStepStatFromDGS  ( 	Graph graph , String morp ,
												int stepMax , int stepInc ,
												String pathStart , String pathStep ,
												analysisType type
												) throws IOException {
		
		Map<Double, Double> map = new HashMap<Double, Double>() ;

		// create list of step to create images
		ArrayList<Double> incList = getListStepAnalysis(stepInc, stepMax);						//	System.out.println(incList);

		// import start graph
		try 																										{	graph.read(pathStart);		} 
		catch (ElementNotFoundException | GraphParseException | org.graphstream.graph.IdAlreadyInUseException e) 	{	/*e.printStackTrace();*/	}

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

					double val = morpAnalysis.getAttributeStatistic(graph, morp, type );
					map.put(step, val);		//	System.out.println(val);	
					
				}
			}
		} catch (IOException e) {		}				
		fs.end();	
		
		return map;
	}

	public static void computeAllStatFromDGS  ( Graph graph , 
												int stepMax , int stepInc ,
												String pathStart , String pathStep ,
												Map<Double, ArrayList<Double>> mapStepMax , Map<Double, ArrayList<Double>> mapStepMin , Map<Double, ArrayList<Double>> mapStepAve 
												) throws IOException {

		// create list of step to create images
		ArrayList<Double> incList = getListStepAnalysis(stepInc, stepMax);						//	System.out.println(incList);

		// import start graph
		try 																										{	graph.read(pathStart);		} 
		catch (ElementNotFoundException | GraphParseException | org.graphstream.graph.IdAlreadyInUseException e) 	{	/*e.printStackTrace();*/	}

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

					Map mapAct = new HashMap<>();
					Map mapInh = new HashMap<>();
					double val ; 
					
					// get Max map step
					val = morpAnalysis.getAttributeStatistic(graph, "gsAct", analysisType.max );
					mapAct.put(step, val);		//	System.out.println(val);	
					
					val = morpAnalysis.getAttributeStatistic(graph, "gsInh", analysisType.max );
					mapInh.put(step, val);		//	System.out.println(val);	
					
					mapStepMax = getMapStepMorp(mapStepMax, mapAct, mapInh);
					
					// get Min map step
					val = morpAnalysis.getAttributeStatistic(graph, "gsAct", analysisType.min );
					mapAct.put(step, val);		//	System.out.println(val);	
					
					val = morpAnalysis.getAttributeStatistic(graph, "gsInh", analysisType.min );
					mapInh.put(step, val);		//	System.out.println(val);	
					
					mapStepMin = getMapStepMorp(mapStepMin, mapAct, mapInh);
					
					// get Ave map step
					val = morpAnalysis.getAttributeStatistic(graph, "gsAct", analysisType.average );
					mapAct.put(step, val);		//	System.out.println(val);	
					
					val = morpAnalysis.getAttributeStatistic(graph, "gsInh", analysisType.average );
					mapInh.put(step, val);		//	System.out.println(val);	
					
					mapStepAve = getMapStepMorp(mapStepAve, mapAct, mapInh);
					
					// stop iteration    
					if ( stepMax == step ) { break; }
				}
			}
		} catch (IOException e) {		}				
		fs.end();	
}
	
	public static void computeFrequencyNodeFromDGS (	Graph graph , String attribute , int nFreq ,
														int stepMax , int stepInc ,
														String pathStart , String pathStep ,
														Map mapStepfrequency ) throws IOException {
		// create list of step to create images
		ArrayList<Double> incList = getListStepAnalysis(stepInc, stepMax);											//	System.out.println(incList);

		// import start graph
		try 																										{	graph.read(pathStart);		} 
		catch (ElementNotFoundException | GraphParseException | org.graphstream.graph.IdAlreadyInUseException e) 	{	/*e.printStackTrace();*/	}

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

					Map <Double, Double> mapAss = morpAnalysis.getMapFrequencyAss(graph, attribute, nFreq);
					mapStepfrequency.put(step, mapAss);
							
					// stop iteration    
					if ( stepMax == step ) { break; }
				}
			}
		} catch (IOException e) {		}				
		fs.end();	
	}

// PRIVATE METHODS ----------------------------------------------------------------------------------------------------------------------------------
	private static void computeFreqDegree ( Graph graph , int degreeFreq ) {
		
//		System.out.println(graph + " " + degreeFreq );
		int nodeCount = graph.getNodeCount();
		System.out.println("nodeCount " + graph + " " + nodeCount);

	}
	

	private static ArrayList<Double> getListStepAnalysis ( double stepInc , double stepMax ) {
		
		ArrayList<Double> list = new ArrayList<Double>();
		for ( double n = 1 ; n * stepInc <= stepMax ; n++ ) {	
			list.add( n * stepInc );	
		}	
		return list;		
	}
	
	private static Map getMapStepMorp (Map mapMorp , Map<Double, Double> mapAct , Map<Double, Double> mapInh ) {
		
		for ( java.util.Map.Entry<Double, Double> entry : mapAct.entrySet()) {
					
			ArrayList<Double> morp = new ArrayList<>();
			morp.add(mapAct.get(entry.getKey()));
			morp.add(mapInh.get(entry.getKey()));
					
			mapMorp.put(entry.getKey(), morp);
		}		
		return mapMorp;			
	}
}
