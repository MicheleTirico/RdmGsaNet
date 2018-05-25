package RdmGsaNet_exportData;

import java.io.IOException;
import java.util.ArrayList;

import org.graphstream.algorithm.generator.lcf.DyckGraphGenerator;
import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;
import org.graphstream.stream.GraphParseException;
import org.graphstream.stream.file.FileSourceFactory;

import RdmGsaNetAlgo.graphIndicators;
import RdmGsaNetExport.handleNameFile;
import RdmGsaNet_Analysis_02.analysisDGS;
import RdmGsaNet_exportData.exportData_csv.typeMultiLineIndicator;
import RdmGsaNet_exportData.exportData_csv.typeSimpleIndicator;


public class exportData_computeIndicator extends exportData_main { 
	
	private static String 	pathToStore = handle.getParent(folder) , 
							pathDataMain = folder ;

	protected static String pathAnalysis = folderMain + "\\analysis\\" ;
	
	public static void main ( String[] args ) throws IOException {
		
	//	handleNameFile.createNewGenericFolder(folderMain , "analysis" );
		runSimpleIndicators(false);		
		runMultiLineIndicators(true);	 
		runMultiSim(false) ;
	} 
	
	public static void runMultiSim( boolean run ) throws IOException {
		
		exportData_csv.computeMultiSim( run , 5, 2500, typeSimpleIndicator.alfaIndex, pathToStore , folder );
		exportData_csv.computeMultiSim( run , 5, 2500, typeSimpleIndicator.averageClustering, pathToStore , folder );
		exportData_csv.computeMultiSim( run , 5, 2500, typeSimpleIndicator.averageDegree, pathToStore , folder );
		exportData_csv.computeMultiSim( run , 5, 2500, typeSimpleIndicator.density, pathToStore , folder );
	//	exportData_csv.computeMultiSim( run , 5, 2500, typeSimpleIndicator.diameter, pathToStore , folder );
	//	exportData_csv.computeMultiSim( run , 5, 2500, typeSimpleIndicator.gammaIndex, pathToStore , folder );
	//	exportData_csv.computeMultiSim( run , 5, 2500, typeSimpleIndicator.organicRatio, pathToStore , folder );
		exportData_csv.computeMultiSim( run , 5, 2500, typeSimpleIndicator.totEdges, pathToStore , folder );
		exportData_csv.computeMultiSim( run , 5, 2500, typeSimpleIndicator.totNodes, pathToStore , folder );
	}
	
	public static void runMultiLineIndicators ( boolean run ) throws IOException {
		exportData_csv.computeMultiLineIndicator(run, 5,  2500, typeMultiLineIndicator.degreeDistribution, pathAnalysis , folder , 10 );
		exportData_csv.computeMultiLineIndicator(run, 5,  2500, typeMultiLineIndicator.normalDegreeDistribution, pathAnalysis , folder , 10 );
	}
	
			
	public static void runSimpleIndicators ( boolean run ) throws IOException {
		exportData_csv.computeSimpleIndicator( run , 5, 2500, typeSimpleIndicator.alfaIndex, pathToStore , folder );
		exportData_csv.computeSimpleIndicator( run , 5, 2500, typeSimpleIndicator.averageClustering, pathToStore , folder );		
		exportData_csv.computeSimpleIndicator( run , 5, 2500, typeSimpleIndicator.averageDegree, pathToStore , folder );
		exportData_csv.computeSimpleIndicator( run , 5, 2500, typeSimpleIndicator.density, pathToStore , folder );		
		exportData_csv.computeSimpleIndicator( run , 5, 2500, typeSimpleIndicator.totEdges, pathToStore , folder );	
		exportData_csv.computeSimpleIndicator( run , 5, 2500, typeSimpleIndicator.totNodes, pathToStore , folder );
		exportData_csv.computeSimpleIndicator( run , 5, 2500, typeSimpleIndicator.organicRatio, pathToStore , folder );
	}
		
}
