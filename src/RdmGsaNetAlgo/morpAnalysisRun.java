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
import RdmGsaNetExport.expChart.typeChart;
import RdmGsaNetExport.expGraph;
import RdmGsaNetExport.expValues;

public class morpAnalysisRun {
	
	private static int 	step0 = 149 , 
						step1 = 150 ;
	
	private static String folderExp = "C:\\Users\\frenz\\Dropbox\\JAVA\\RdmGsaNet_Export\\" ; 
	private static String nameFileExp = RdmGsaNet_pr08.main.getNameFileExp();
	
	private static String nameFileExpStep0 = nameFileExp + "_step_" + step0;
	private static String nameFileExpStep1 = nameFileExp + "_step_" + step1;
	
	// export chart 
	private static String folderChart = "C:\\Users\\frenz\\Dropbox\\JAVA\\test\\";
	private static String nameFileChartAverage = "testAverage";
	private static String nameFileChartMax = "testMax";
	private static String nameFileChartMin = "testMin";
	
	public static void main(String[] args) throws IOException  {	
	
		// create map of values at each step from folder
		Map<Integer,ArrayList<Double>> mapStepMorpAverage = stroredDataAnalysis.getMapStepValMorp(folderExp, nameFileExp, stroredDataAnalysis.analysisType.average ) ;	 
		Map<Integer,ArrayList<Double>> mapStepMorpMax = stroredDataAnalysis.getMapStepValMorp(folderExp, nameFileExp, stroredDataAnalysis.analysisType.max ) ;	 
		Map<Integer,ArrayList<Double>> mapStepMorpMin = stroredDataAnalysis.getMapStepValMorp(folderExp, nameFileExp, stroredDataAnalysis.analysisType.min ) ;	
		
		// create map of SPAC

		// create map test
		Map<Integer,ArrayList<Double>> mapTest = new HashMap<Integer, ArrayList<Double>>();	ArrayList<Double> testArray = new ArrayList<>(); testArray.add(2.5);		testArray.add(10.5);	mapTest.put(1, testArray);
		
		// create charts
		
		// average chart
		expChart xyChartAverage = new expChart(typeChart.XYchart , "Time Varing Morphogen Averange", "Step (t)" , "morp (%)" , 800, 600 ,	mapStepMorpAverage );
		xyChartAverage.setVisible(true);
		xyChartAverage.saveChart(false,  folderChart, nameFileChartAverage );
		
		// max chart
		expChart xyChartMax = new expChart(typeChart.XYchart , "Time Varing Morphogen Max", "Step (t)" , "morp (%)" , 800, 600 ,	mapStepMorpMax );
		xyChartMax.setVisible(true);
		xyChartMax.saveChart(true,  folderChart, nameFileChartMax );
				
				
		// min chart
		expChart xyChartMin = new expChart(typeChart.XYchart , "Time Varing Morphogen Min", "Step (t)" , "morp (%)" , 800, 600 ,	mapStepMorpMin );
		xyChartMin.setVisible(true);
		xyChartMin.saveChart(true,  folderChart, nameFileChartMin );
				
		
	}
	
	public static void valAnalysisAverage ( ) throws IOException {}

	

	

}
