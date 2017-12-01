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

import javax.swing.SwingUtilities;

import java.util.Optional;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import RdmGsaNetAlgo.morpAnalysis.morphogen;
import RdmGsaNetAlgo.morpSpatialAutoCor.distanceMatrixType;
import RdmGsaNetExport.expChart;


import RdmGsaNetExport.expGraph;
import RdmGsaNetExport.expValues;

public class morpAnalysisRun {
	
	
	private static int 	step0 = 149 , 
						step1 = 150 ;
	
	private static String folderExp = "D:\\Dropbox\\Dropbox\\JAVA\\RdmGsaNet_Export\\" ; 
	private static String nameFileExp = RdmGsaNet_pr08.main.getNameFileExp();
	private static String nameFileExpStep0 = nameFileExp + "_step_" + step0;
	private static String nameFileExpStep1 = nameFileExp + "_step_" + step1;
	
	// export chart 
	private static String folderChart = "C:\\Users\\Michele TIRICO\\Desktop\\prove\\";
	private static String nameFileChart = "test";
	
	public static void main(String[] args) throws IOException  {	
	
		// create map of values at each step from folder
		Map<Integer,ArrayList<Double>> mapStepMorpMean = valAnalysis.getMapStepValMorp(folderExp, nameFileExp, valAnalysis.analysisType.average ) ;	 
		
		Map<Integer,ArrayList<Double>> mapTest = new HashMap<Integer, ArrayList<Double>>();
		
		ArrayList<Double> testArray = new ArrayList<>();
		testArray.add(2.5);
		testArray.add(10.5);
		mapTest.put(1, testArray);
		
		
		// set title and size
		expChart xyChart = new expChart("Time Varing Morphogen Averange", 800, 600 , mapStepMorpMean );
		
	
		
		xyChart.setLayoutFrame("gino");
		
		xyChart.setVisible(true);
		
		xyChart.saveChart(false,  folderChart, nameFileChart );
		
		
		/*
		// create immages
		expChart2 chart = new expChart2(expChart2.chartType.line ,
						folderChart, nameFileChart, 
						expChart2.fileType.png ,
						expChart2.morp.activator);
				
		chart.setDatasetFromMap(mapStepMorpMean, "gino");
		chart.setDimension(600, 480);
*/
	}
	
	public static void valAnalysisAverage ( ) throws IOException {}

	

	

}
