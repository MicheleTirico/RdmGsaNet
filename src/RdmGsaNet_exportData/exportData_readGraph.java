package RdmGsaNet_exportData;

import java.io.IOException;
import java.util.ArrayList;

import org.graphstream.algorithm.generator.lcf.DyckGraphGenerator;
import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;
import org.graphstream.stream.GraphParseException;
import org.graphstream.stream.file.FileSourceFactory;

import RdmGsaNetAlgo.graphIndicators;
import RdmGsaNet_Analysis_02.analysisDGS;

public class exportData_readGraph extends exportData_main {
	
	public exportData_readGraph (  ) {

	}
	
	public void computeIndicators ( int stepMax , int stepInc, String[] pathStartArr, String[] pathStepArr  , int thread ) throws IOException {
		
		String pathStart = pathStartArr[0];
		String pathStep = pathStepArr[0];
		System.out.println(pathStepNet);
		
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
				
					double gammaIndex = graphIndicators.getGammaIndex(graph, true) ;
					double test = graphIndicators.getAlfaIndex(graph, true) ;
					System.out.println(graph  +" "+ test);
		
					// stop iteration    			
					if ( stepMax == step )  
						break; 
			
				}
		
			}
	
		} catch (IOException e) {		}				
	
		fs.end();	
	}
			
		
		
			
	

}
