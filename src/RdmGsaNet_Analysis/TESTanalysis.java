package RdmGsaNet_Analysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import RdmGsaNetAlgo.*;
import RdmGsaNetExport.*;
import RdmGsaNetExport.expChart.typeChart;
import RdmGsaNet_Analysis.TESTstroredDataAnalysis.analysisType;

public class TESTanalysis {
	
	// export map
	private static String folderMap 	= "C:\\Users\\Michele TIRICO\\Desktop\\prove\\"; 
	
	private static String nameMapAve 	= "mapStepMorpAve";
	private static String nameMapMax 	= "mapStepMorpMax";
	private static String nameMapMin 	= "mapStepMorpMin";
	
	private static String folderGr 		= "D:\\Dropbox\\Dropbox\\JAVA\\RdmGsaNet_Export\\graph\\export_03\\";
	private static String nameFileGr 	= "export_Sim_0_Size_200_Da_0.6_Di_0.2_F_0.05_K_0.05";
	
	// export chart 
	private static String folderChart = "D:\\Dropbox\\Dropbox\\JAVA\\RdmGsaNet_Export\\chart\\chart_03\\" ;
	
	private static String nameFileChartAverage = "Time Varing Morphogen Averange";
	private static String nameFileChartMax = "Time Varing Morphogen max";
	private static String nameFileChartMin = "Time Varing Morphogen min";
	
	// SPAC analysis
	private static String nameFileChartSPACAverage = "Time Varing SPAC Averange";
	private static String nameFileChartSPACAssAverage = "Time Varing SPAC absolute Averange";

	public static void main(String[] args) throws IOException, ClassNotFoundException  {	
	
// CREATE MAPS
	
		// create map of val
		TESTstroredDataAnalysis.storedMapStepValMorp(folderGr, nameFileGr, folderMap, nameMapAve, nameMapMax, nameMapMin, true, true, true );
		
		// get map of val
		Map<Integer,ArrayList<Double>> mapStepMorpAve = expValues.readMap(true, folderMap, nameMapAve) ;
		Map<Integer,ArrayList<Double>> mapStepMorpMax = expValues.readMap(true, folderMap, nameMapMax) ;
		Map<Integer,ArrayList<Double>> mapStepMorpMin = expValues.readMap(true, folderMap, nameMapMin) ;

		// create mapStepSPACAverage
		Map< Integer, ArrayList< Double >> mapStepSPACAverage = TESTstroredDataAnalysis. getMapStepSPACAverage(true, folderGr, nameFileGr, 500 ) ;
		Map< Integer, ArrayList< Double >> mapStepSPACAssAverage = TESTstroredDataAnalysis. getMapStepSPACAverage(true, folderGr, nameFileGr, 500 ) ;
		
// CREATE CHARTS 	
		createChart(true, mapStepMorpAve , nameFileChartAverage );	
		createChart(true, mapStepMorpMax , nameFileChartMax );	
		createChart(true, mapStepMorpMin , nameFileChartMin );	
	
		createChart(true, mapStepSPACAverage , nameFileChartSPACAverage );	
		createChart(true, mapStepSPACAssAverage , nameFileChartSPACAssAverage );	
		
		// create map test
//		Map<Integer,ArrayList<Double>> mapTest = new HashMap<Integer, ArrayList<Double>>();	ArrayList<Double> testArray = new ArrayList<>(); testArray.add(2.5);		testArray.add(10.5);	mapTest.put(1, testArray);


//		createChartVal(true, mapStepMorpAve, mapStepMorpMax, mapStepMorpMin);
		
	}
	
	private static void createMapVal ( boolean createMap ) throws IOException {
		if ( createMap == true) {
			// create map of values at each step from folder
//			Map<Integer,ArrayList<Double>> mapStepMorpAve = stroredDataAnalysis.getMapStepValMorp( true, folderExp, nameFileExp, stroredDataAnalysis.analysisType.average ) ;	 
//			Map<Integer,ArrayList<Double>> mapStepMorpMax = stroredDataAnalysis.getMapStepValMorp( true, folderExp, nameFileExp, stroredDataAnalysis.analysisType.max ) ;	 
//			Map<Integer,ArrayList<Double>> mapStepMorpMin = stroredDataAnalysis.getMapStepValMorp( true, folderExp, nameFileExp, stroredDataAnalysis.analysisType.min ) ;
		}
	}
	
	private static void createChartVal ( boolean createChart , Map mapStepMorpAve, Map mapStepMorpMax , Map mapStepMorpMin ) throws IOException {
		
		if ( createChart == true ) { 
			// average chart
			expChart xyChartAverage = new expChart(typeChart.XYchart2Morp , "Time Varing Morphogen Averange", "Step (t)" , "morp (%)" , 800, 600 ,	mapStepMorpAve );
			xyChartAverage.setVisible(true);
			xyChartAverage.saveChart(true,  folderChart, nameFileChartAverage );
				
			// max chart
			expChart xyChartMax = new expChart(typeChart.XYchart2Morp , "Time Varing Morphogen Max", "Step (t)" , "morp (%)" , 800, 600 ,	mapStepMorpMax );
			xyChartMax.setVisible(true);
			xyChartMax.saveChart(true,  folderChart, nameFileChartMax );
							
			// min chart
			expChart xyChartMin = new expChart(typeChart.XYchart2Morp , "Time Varing Morphogen Min", "Step (t)" , "morp (%)" , 800, 600 ,	mapStepMorpMin );
			xyChartMin.setVisible(true);
			xyChartMin.saveChart(true,  folderChart, nameFileChartMin );		
		}
	}
	
	private static void createChart ( boolean createChart , Map map , String nameChart) throws IOException {
		if ( createChart == true ) { 
			
			// average chart
			expChart xyChartSPACAverage = new expChart(typeChart.XYchart2Morp , nameChart, "Step (t)" , "SPAC (%)" , 800, 600 ,	map );
			xyChartSPACAverage.setVisible(true);
			xyChartSPACAverage.saveChart(true,  folderChart, nameChart );
		}
	}	
	
	// non funziona !
	private static String setFolder ( String[] folderExpList ) {
		
		try	 											{ return folderGr = folderExpList[0] ; }
		catch (java.lang.NullPointerException e) 		{ 
			for ( int i = 1 ; i <= folderExpList.length ; i++ ) {
				return folderGr = folderExpList[i] ;			
			}
		}
		return folderGr;
	}
}
