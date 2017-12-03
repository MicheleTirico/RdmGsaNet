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
	
	private static String folderExp = "C:\\Users\\frenz\\Dropbox\\JAVA\\RdmGsaNet_Export\\" ; 
	private static String nameFileExp = RdmGsaNet_pr08.main.getNameFileExp();
	
	// export chart 
	private static String folderChart = "C:\\Users\\frenz\\Dropbox\\JAVA\\test\\";
	
	private static String nameFileChartAverage = "testAverage";
	private static String nameFileChartMax = "testMax";
	private static String nameFileChartMin = "testMin";

	static Map<Integer,ArrayList<Double>> mapStepMorpAve = new HashMap<Integer,ArrayList<Double>>() ;	 
	static Map<Integer,ArrayList<Double>> mapStepMorpMax = new HashMap<Integer,ArrayList<Double>>() ;
	static Map<Integer,ArrayList<Double>> mapStepMorpMin = new HashMap<Integer,ArrayList<Double>>() ;
	
	public static void main(String[] args) throws IOException  {	
	
// CREATE MAPS
		// create map of val
		createMapVal (false);
		
		// create mapIdSpac
		createIdSPAC(true);
		
		// create map test
		Map<Integer,ArrayList<Double>> mapTest = new HashMap<Integer, ArrayList<Double>>();	ArrayList<Double> testArray = new ArrayList<>(); testArray.add(2.5);		testArray.add(10.5);	mapTest.put(1, testArray);

		// CREATE CHARTS
		createChartVal(false);
		
		// SPAC chart
	}
	

	private static void createMapVal ( boolean createMap ) throws IOException {
		if ( createMap == true) {
			// create map of values at each step from folder
			Map<Integer,ArrayList<Double>> mapStepMorpAve = stroredDataAnalysis.getMapStepValMorp(folderExp, nameFileExp, stroredDataAnalysis.analysisType.average ) ;	 
			Map<Integer,ArrayList<Double>> mapStepMorpMax = stroredDataAnalysis.getMapStepValMorp(folderExp, nameFileExp, stroredDataAnalysis.analysisType.max ) ;	 
			Map<Integer,ArrayList<Double>> mapStepMorpMin = stroredDataAnalysis.getMapStepValMorp(folderExp, nameFileExp, stroredDataAnalysis.analysisType.min ) ;
		}
	}
	
	private static void createChartVal ( boolean createChart ) throws IOException {
		
		if ( createChart == true ) { 
			// average chart
			expChart xyChartAverage = new expChart(typeChart.XYchart , "Time Varing Morphogen Averange", "Step (t)" , "morp (%)" , 800, 600 ,	mapStepMorpAve );
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
	}
	
	private static void createIdSPAC ( boolean createSPAC ) throws IOException {
		if ( createSPAC == true ) {
			
			
//			Map<String , ArrayList<Double>> mapIdSPAC = stroredDataAnalysis.getMapIdSPAC( folderExp, nameFileExp, 45 );
//			System.out.println(mapIdSPAC);
			
			Map< Integer, ArrayList< Double >> mapStepSPACstat = stroredDataAnalysis. getMapStepSPACstat(folderExp, nameFileExp, analysisType.average) ;
//			System.out.println(mapStepSPACstat);
			
		}
	}

	

}


