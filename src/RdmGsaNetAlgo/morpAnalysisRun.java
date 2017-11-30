package RdmGsaNetAlgo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import RdmGsaNetAlgo.morpAnalysis.morphogen;
import RdmGsaNetAlgo.morpSpatialAutoCor.distanceMatrixType;
import RdmGsaNetExport.expGraph;
import RdmGsaNetExport.expValues;

public class morpAnalysisRun {
	
	
	private static int 	step0 = 149 , 
						step1 = 150 ;
	
	private static String dossierExp = "D:\\Dropbox\\Dropbox\\JAVA\\RdmGsaNet_Export\\" ; 
	private static String nameFileExp = RdmGsaNet_pr08.main.getNameFileExp();
	private static String nameFileExpStep0 = nameFileExp + "_step_" + step0;
	private static String nameFileExpStep1 = nameFileExp + "_step_" + step1;
	
//	private static Graph graphStep0 = new SingleGraph("graphStep0") ;
//	private static Graph graphStep1 = new SingleGraph("graphStep1") ;
	
	
	

	
	
	public static void main(String[] args) throws IOException {
		
		valAnalysisAverage();
		
		

		


	}
	
	public static void valAnalysisAverage ( ) throws IOException {
		 Map<Double,ArrayList<Double>> mapStepMorpMean = valAnalysis.getMapStepValMorp(dossierExp, nameFileExp, valAnalysis.analysisType.average ) ;	 
	}
	
	
	

	

}
