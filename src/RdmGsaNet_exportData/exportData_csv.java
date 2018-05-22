package RdmGsaNet_exportData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.tools.ant.taskdefs.condition.HasFreeSpace;
import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.GraphParseException;
import org.graphstream.stream.file.FileSourceFactory;

import RdmGsaNetAlgo.graphAnalysis;
import RdmGsaNetAlgo.graphIndicators;
import RdmGsaNetExport.expCsv;
import RdmGsaNetExport.expGraph;
import RdmGsaNetExport.handleNameFile;
import RdmGsaNet_Analysis_02.analysisDGS;
import RdmGsaNet_Analysis_02.analysisMultiSim;
import graphstream_dev_toolkit.toolkit;
import scala.Char;

public class exportData_csv extends exportData_main {
	
	static String 	pathStart = pathStartNet ,	 
					pathStep = pathStepNet ; 
	
	protected enum typeSimpleIndicator {	density  ,	averageDegree , diameter , averageShortpath , gammaIndex , alfaIndex , organicRatio , averageClustering }	
	protected static typeSimpleIndicator typeIndicator ;	
	
	protected enum typeMultiLineIndicator {	degreeDistribution , normalDegreeDistribution}	
	protected static typeMultiLineIndicator typeMultiLineIndicator ;	
	
	
	protected static Graph graph ;								
	
	public static void computeMultiLineIndicator (  boolean run , int 	stepInc , int stepMax , typeMultiLineIndicator typeIndicator , String pathToStore , String pathDataMain , int numLine ) throws IOException {
		
		if ( !run)
			return ;
		
		graph = new SingleGraph ("graph") ;
		 
		String 	header =  "step" ,
				nameFile = typeIndicator.toString() ;
		
		for ( int x = 1 ; x <= numLine ; x++ )
			header = header +";" + x ;
			
		FileWriter fileWriter = new FileWriter( pathToStore + nameFile + ".csv" , true );		
		expCsv.addCsv_header( fileWriter, header ) ;
		
		// create list of step to create images
		ArrayList<Double> incList = analysisDGS.getListStepToAnalyze(stepInc, stepMax);						//	System.out.println(incList);
				
		// set file Source for file step
		fs = FileSourceFactory.sourceFor(pathStep);
		fs.addSink(graph);
		
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
					
					double val = 0 ;
					ArrayList<String> listVal = new ArrayList<String>(Arrays.asList( Double.toString(step) ) ) ;
					switch (typeIndicator) { 
				
					case degreeDistribution : {
						int[] degreeDistr = Toolkit.degreeDistribution(graph) ;
						for ( int d : degreeDistr) 
							listVal.add( Integer.toString(d)) ;	
					} break ; 
					
					case normalDegreeDistribution : {
						Map<Double , Double> map = new HashMap<Double , Double>(graphAnalysis.getNormalDegreeDistribution (graph) ) ;
						for ( double d : map.keySet() )
							listVal.add(Double.toString(map.get(d))) ;
					}break ;
								 
						
					}
					expCsv.writeLine(fileWriter, listVal , ';' ) ;
				
					// stop iteration    			
					if ( stepMax == step )  
						break; 
				}			
			}
	
		} catch (IOException e) {		}				
	
		fs.end();	
		fileWriter.close();
	}
	
	public static void computeSimpleIndicator ( boolean run , int 	stepInc , int stepMax , typeSimpleIndicator typeIndicator , String pathToStore , String pathDataMain ) throws IOException {
		
		if ( !run)
			return ;
		
		graph = new SingleGraph ("graph") ;
		 
		String 	header = "step;" + typeIndicator.toString() ,
				nameFile = typeIndicator.toString() ;
		
		FileWriter fileWriter = new FileWriter( pathToStore + nameFile + ".csv" , true );		
		expCsv.addCsv_header( fileWriter, header ) ;
		
		// create list of step to create images
		ArrayList<Double> incList = analysisDGS.getListStepToAnalyze(stepInc, stepMax);						//	System.out.println(incList);
				
		// set file Source for file step
		fs = FileSourceFactory.sourceFor(pathStep);
		fs.addSink(graph);
		
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
					
					double val = 0 ;
					
					switch (typeIndicator) {
					
					case density: 
						val = Toolkit.density(graph);									
						break;
						
					case averageDegree : 
						val = Toolkit.averageDegree(graph);
						break ;
					
					case diameter : 
						val = Toolkit.diameter(graph);
						break ;
					
					case alfaIndex :
						val = graphIndicators.getAlfaIndex(graph);
						break ;
						
					case gammaIndex :
						val = graphIndicators.getGammaIndex(graph, true) ;
						break;
						
					case organicRatio :
						val = graphIndicators.getOrganicRatio(graph) ;
						break ;
						
					case averageShortpath :
						break;
				
					case averageClustering :
						Toolkit.averageClusteringCoefficient(graph);
						break ;						
						
					}
					expCsv.writeLine(fileWriter, Arrays.asList( Double.toString(step) , Double.toString(val) ) , ';' ) ;
				
					// stop iteration    			
					if ( stepMax == step )  
						break; 
				}			
			}
	
		} catch (IOException e) {		}				
	
		fs.end();	
		fileWriter.close();
	}
	
	public static void computeMultiSim ( boolean run , int stepInc , int stepMax , typeSimpleIndicator typeIndicator , String pathToStore , String pathDataMain ) throws IOException {
		
		
		if ( run == false )
			return ;
		
		String 	header = "step;" + typeIndicator.toString() ,
				nameFile = typeIndicator.toString() ;
		
		File extF  ; 
		File path = new File(folderMain) ;
		File [] files = path.listFiles();							
		
		 ArrayList<File> fileArray = new ArrayList<File>(Arrays.asList(files));
		
//		 fileArray.forEach(  s -> System.out.print("\n" + s.getName()) );
		 Map <Double , ArrayList <Double>> mapToStore = new HashMap < Double , ArrayList <Double>>();
		 
		 for ( File f : fileArray ) {
			 
			 extF = f ;
			 
			 String s = f.getAbsolutePath();	 // 	 
			 System.out.println(f);
			
			 String pathStepGs = handle.getCompletePathInFolder(folderCommonFiles,  "layerGs_step") ;
			 String pathStepNet = handle.getCompletePathInFolder(s+"\\",  "layerNet_step") ; 
			 String pathStartGs = handle.getCompletePathInFolder(folderCommonFiles,  "layerGs_start") ;
			 String pathStartNet = handle.getCompletePathInFolder(folderCommonFiles,  "layerNet_start") ;
		
			 handleNameFile.createNewGenericFolder(folderMain , "multiSimAnalysis" );
			 
			 graph = new SingleGraph ("graph") ; 

			 String pathFile = folderMain + "\\multiSimAnalysis\\"  + nameFile + ".csv" ;
			 FileWriter fileWriter = new FileWriter( pathFile , true );		
			 expCsv.addCsv_header( fileWriter, header ) ;
		
			// create list of step to create images
			ArrayList<Double> incList = analysisDGS.getListStepToAnalyze(stepInc, stepMax);						//	System.out.println(incList);
					
			// set file Source for file step
			fs = FileSourceFactory.sourceFor(pathStep);
			fs.addSink(graph);
			
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
						
						double val = 0 ;
						expCsv.writeColumn(pathFile, Arrays.asList( Double.toString(step) , Double.toString(val) ) , ';'  );
						
						expCsv.writeLine(fileWriter, Arrays.asList( Double.toString(step) , Double.toString(val) ) , ';' ) ;
					
						// stop iteration    			
						if ( stepMax == step )  
							break; 
					}			
				}
		
			} catch (IOException e) {		}				
		
			fs.end();	
			fileWriter.close();
		 }
	}
	
}
