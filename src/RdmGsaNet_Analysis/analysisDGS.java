package RdmGsaNet_Analysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;
import org.graphstream.stream.GraphParseException;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceDGS;
import org.graphstream.stream.file.FileSourceFactory;

import RdmGsaNetAlgo.morpAnalysis;
import RdmGsaNetAlgo.morpAnalysis.analysisType;
import RdmGsaNetExport.expGraph;

public class analysisDGS {
	
	private static FileSource fs;

	
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
		ArrayList<Double> incList = new ArrayList<Double>();
		for ( double n = 1 ; n * stepInc <= stepMax ; n++ ) {	incList.add( n * stepInc );	}						//	System.out.println(incList);
				
		// import start graph
		try 																										
			{	graph.read(pathStart);	} 
		catch (ElementNotFoundException | GraphParseException | org.graphstream.graph.IdAlreadyInUseException e) 	
			{	
				//e.printStackTrace();	
			}
				
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
						
			// identify step visualization  
			if ( stepMax == step ) {
				System.out.println("final step " + graph.getStep());
				System.out.println();	
				break;
				}
			}
		} catch (IOException e) {		}				
		fs.end();	
	}
// PRIVATE METHODS ----------------------------------------------------------------------------------------------------------------------------------

	public Object analysisDGS;

	
}
