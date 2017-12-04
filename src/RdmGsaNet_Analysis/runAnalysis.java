package RdmGsaNet_Analysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import RdmGsaNetAlgo.*;
import RdmGsaNetAlgo.stroredDataAnalysis.analysisType;
import RdmGsaNetExport.*;
import RdmGsaNetExport.expChart.typeChart;

public class runAnalysis {
	
//	private static String folderExp1 = "D:\\Dropbox\\Dropbox\\JAVA\\RdmGsaNet_Export\\";				// forder LITIS
//	private static String folderExp2 = "C:\\Users\\frenz\\Dropbox\\JAVA\\RdmGsaNet_Export\\" ; 			// private folder
	
//	private static String[] folderExpList  = new  String [] { folderExp1, folderExp2 } ;
	private static String folderExp = "D:\\Dropbox\\Dropbox\\JAVA\\RdmGsaNet_Export\\graph\\export_02\\";
	
	private static String nameFileExp = "export_Sim_0_Size_400_Da_0.6_Di_0.2_F_0.05_K_0.05";
	
	// export chart 
//	private static String folderChart1 = "D:\\Dropbox\\Dropbox\\JAVA\\RdmGsaNet_chart\\";
//	private static String folderChart2 = "C:\\Users\\frenz\\Dropbox\\JAVA\\test\\";
	
//	private static String[] folderChartList  = new  String [] { folderChart1, folderChart2} ;
	private static String folderChart = "D:\\Dropbox\\Dropbox\\JAVA\\RdmGsaNet_Export\\chart\\chart_02\\" ;
	
	private static String nameFileChartAverage = "Time Varing Morphogen Averange";
	private static String nameFileChartMax = "Time Varing Morphogen max";
	private static String nameFileChartMin = "Time Varing Morphogen min";
	
	// SPAC analysis
	private static String nameFileChartSPACAverage = "Time Varing SPAC Averange";
	private static String nameFileChartSPACAssAverage = "Time Varing SPAC absolute Averange";

	public static void main(String[] args) throws IOException  {	
	
// CREATE MAPS
		// create map of val
//		Map<Integer,ArrayList<Double>> mapStepMorpAve = stroredDataAnalysis.getMapStepValMorp(folderExp, nameFileExp, stroredDataAnalysis.analysisType.average ) ;	 
//		Map<Integer,ArrayList<Double>> mapStepMorpMax = stroredDataAnalysis.getMapStepValMorp(folderExp, nameFileExp, stroredDataAnalysis.analysisType.max ) ;	 
//		Map<Integer,ArrayList<Double>> mapStepMorpMin = stroredDataAnalysis.getMapStepValMorp(folderExp, nameFileExp, stroredDataAnalysis.analysisType.min ) ;
		
		// create mapStepSPACAverage
		Map< Integer, ArrayList< Double >> mapStepSPACAverage = stroredDataAnalysis. getMapStepSPACAverage(folderExp, nameFileExp, 250 ) ;
//		Map< Integer, ArrayList< Double >> mapStepSPACAssAverage = stroredDataAnalysis. getMapStepSPACAverage(folderExp, nameFileExp, 250 ) ;
//		System.out.println(mapStepSPACAverage);
		
		// create map test
//		Map<Integer,ArrayList<Double>> mapTest = new HashMap<Integer, ArrayList<Double>>();	ArrayList<Double> testArray = new ArrayList<>(); testArray.add(2.5);		testArray.add(10.5);	mapTest.put(1, testArray);

// CREATE CHARTS 
//		createChartVal(true, mapStepMorpAve, mapStepMorpMax, mapStepMorpMin);
		
		// SPAC chart
		createChart(true, mapStepSPACAverage , nameFileChartSPACAverage );	
//		createChart(true, mapStepSPACAssAverage , nameFileChartSPACAssAverage );	
	}
	
	private static void createMapVal ( boolean createMap ) throws IOException {
		if ( createMap == true) {
			// create map of values at each step from folder
			Map<Integer,ArrayList<Double>> mapStepMorpAve = stroredDataAnalysis.getMapStepValMorp(folderExp, nameFileExp, stroredDataAnalysis.analysisType.average ) ;	 
			Map<Integer,ArrayList<Double>> mapStepMorpMax = stroredDataAnalysis.getMapStepValMorp(folderExp, nameFileExp, stroredDataAnalysis.analysisType.max ) ;	 
			Map<Integer,ArrayList<Double>> mapStepMorpMin = stroredDataAnalysis.getMapStepValMorp(folderExp, nameFileExp, stroredDataAnalysis.analysisType.min ) ;
		}
	}
	
	private static void createChartVal ( boolean createChart , Map mapStepMorpAve, Map mapStepMorpMax , Map mapStepMorpMin ) throws IOException {
		
		if ( createChart == true ) { 
			// average chart
			expChart xyChartAverage = new expChart(typeChart.XYchart , "Time Varing Morphogen Averange", "Step (t)" , "morp (%)" , 800, 600 ,	mapStepMorpAve );
			xyChartAverage.setVisible(true);
			xyChartAverage.saveChart(true,  folderChart, nameFileChartAverage );
				
			// max chart
			expChart xyChartMax = new expChart(typeChart.XYchart , "Time Varing Morphogen Max", "Step (t)" , "morp (%)" , 800, 600 ,	mapStepMorpMax );
			xyChartMax.setVisible(true);
			xyChartMax.saveChart(true,  folderChart, nameFileChartMax );
							
			// min chart
			expChart xyChartMin = new expChart(typeChart.XYchart , "Time Varing Morphogen Min", "Step (t)" , "morp (%)" , 800, 600 ,	mapStepMorpMin );
			xyChartMin.setVisible(true);
			xyChartMin.saveChart(true,  folderChart, nameFileChartMin );		
		}
	}
	
	
	private static void createChart ( boolean createChart , Map map , String nameChart) throws IOException {
		if ( createChart == true ) { 
			
			// average chart
			expChart xyChartSPACAverage = new expChart(typeChart.XYchart , nameChart, "Step (t)" , "SPAC (%)" , 800, 600 ,	map );
			xyChartSPACAverage.setVisible(true);
			xyChartSPACAverage.saveChart(true,  folderChart, nameChart );
		}
	}	
	
	// non funziona !
	private static String setFolder ( String[] folderExpList ) {
		
		try	 											{ return folderExp = folderExpList[0] ; }
		catch (java.lang.NullPointerException e) 		{ 
			for ( int i = 1 ; i <= folderExpList.length ; i++ ) {
				return folderExp = folderExpList[i] ;			
			}
		}
		return folderExp;
	}
}
