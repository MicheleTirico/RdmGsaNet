package RdmGsaNet_exportData;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.tools.ant.taskdefs.condition.HasFreeSpace;
import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.GraphParseException;
import org.graphstream.stream.file.FileSourceFactory;

import RdmGsaNetAlgo.graphIndicators;
import RdmGsaNetExport.expCsv;
import RdmGsaNetExport.expGraph;
import RdmGsaNet_Analysis_02.analysisDGS;
import scala.Char;

public class exportData_csv extends exportData_main {
	
	static String 	pathStart = pathStartNet ,	 
					pathStep = pathStepNet ; 
	
	static int 	stepInc = 5 , 
				stepMax = 50 ;
	
	protected static Graph graph = new SingleGraph ("graph") ;
	
	protected static Map <Double , Double > mapStepAverageDegree = new HashMap <Double , Double > () ,
											mapStepDensity = new HashMap <Double , Double > () ;
	
	static String density_header = "step;val" ,
			path = folderMain , 
			nameFile = "test" ;
	



	
	
	public static void main ( String[] args ) throws IOException {
		
		FileWriter fileWriter = new FileWriter( path + nameFile + ".csv" , true );
		fileWriter.append(density_header.toString());
		
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
				
					double averageDegree ;
					
					
	
					double density = Toolkit.density(graph);
					
					System.out.println(density) ;
					fileWriter.append( String.valueOf(density));
					
					
					
//					mapStepDensity.put(step, density);
					
					
//					double gammaIndex = graphIndicators.getGammaIndex(graph, true) ;
//					double test = graphIndicators.getAlfaIndex(graph, true) ;
//					System.out.println(graph  +" "+ test);
		
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
