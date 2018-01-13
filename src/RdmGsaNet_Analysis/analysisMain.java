package RdmGsaNet_Analysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.*;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry.Entry;

import RdmGsaNetAlgo.morpAnalysis.analysisType;
import RdmGsaNetExport.expChart;
import RdmGsaNetExport.expGraph;
import RdmGsaNetExport.expChart.typeChart;
import RdmGsaNetViz.graphViz;

public class analysisMain {
		
	private static String fileType = ".dgs" ;
	private static String folder = "C:\\Users\\Michele TIRICO\\ownCloud\\RdmGsaNet_exp\\Da_0.2_Di_0.1\\DGS\\";
	
// START FILES
	// GS graph
	private static String nameStartGs = "layerGsStart_Size_50_Da_0.2_Di_0.1_F_0.03_K_0.062"  ;
	private static String folderStartGs = folder;
	private static String pathStartGs = folderStartGs + nameStartGs + fileType ;
	
	// NET graph
	private static String nameStartNet = "layerNetStart_meanPoint_center_seedAct_1.0_seedInh_1.0"  ;
	private static String folderStartNet = folder;
	private static String pathStartNet = folderStartNet + nameStartNet + fileType ;
	
// STEP FILES	
	// GS graph
	private static String nameStepGs = "layerGsStep_Size_50_Da_0.2_Di_0.1_F_0.03_K_0.062"; 
	private static String folderStepGs = folder;
	private static String pathStepGs = folderStepGs + nameStepGs + fileType ;
	
	// NET graph
	private static String nameStepNet = "layerGsStep_Size_50_Da_0.2_Di_0.1_F_0.03_K_0.062"; 
	private static String folderStepNet = folder;
	private static String pathStepNet = folderStepNet + nameStepNet + fileType ;
	
		
// EXPORT IMAGES
	private static String folderIm =  "C:\\Users\\frenz\\ownCloud\\RdmGsaNet_exp\\dgs\\Da_0.2_Di_0.1_02\\image\\";
	private static String nameIm = nameStepGs + "_step_";
	
// EXPORT CHARTS
	private static String folderChart = "C:\\Users\\Michele TIRICO\\ownCloud\\RdmGsaNet_exp\\Da_0.2_Di_0.1\\charts\\" ;
	
	private static String nameFileChartMax = nameStartGs + "max" ;
	private static String nameFileChartMin = nameStartGs + "min" ;
	private static String nameFileChartAve = nameStartGs + "ave" ;
	

// GRAPHS
	private static Graph gsGraph = new SingleGraph( "gsGraph"); 
	private static Graph netGraph = new SingleGraph( "netGraph"); 
		
// CREATE CHARTS
	private static analysisChart chart = new analysisChart();
	
// ANALYSIS DGS
	private static analysisDGS dgsGs = new analysisDGS();
	private static analysisDGS dgsNet = new analysisDGS();
	
// MAP
	private static Map<Double, Double> mapStepGsActMax = new HashMap<Double , Double > ();
	private static Map<Double, Double> mapStepGsInhMax = new HashMap<Double , Double > ();
	
	private static Map<Double, Double> mapStepGsActMin = new HashMap<Double , Double > ();
	private static Map<Double, Double> mapStepGsInhMin = new HashMap<Double , Double > ();
	
	private static Map<Double, Double> mapStepGsActAve = new HashMap<Double , Double > ();
	private static Map<Double, Double> mapStepGsInhAve = new HashMap<Double , Double > ();
	
	public static void main(String[] args) throws IOException {
		
		// read files in a loop 
		dgsGs.readStartDGS(gsGraph, pathStartGs);
		dgsNet.readStartDGS(netGraph, pathStartNet);
		
		dgsGs.readStepDGS(gsGraph, "gsAct", 5000, 10 , pathStartGs, pathStepGs , analysisType.max , mapStepGsActMax );
		dgsGs.readStepDGS(gsGraph, "gsInh", 5000, 10 , pathStartGs, pathStepGs , analysisType.max , mapStepGsInhMax );
		
		dgsGs.readStepDGS(gsGraph, "gsAct", 5000, 10 , pathStartGs, pathStepGs , analysisType.min , mapStepGsActMin );
		dgsGs.readStepDGS(gsGraph, "gsInh", 5000, 10 , pathStartGs, pathStepGs , analysisType.min , mapStepGsInhMin );
		
		dgsGs.readStepDGS(gsGraph, "gsAct", 5000, 10 , pathStartGs, pathStepGs , analysisType.average , mapStepGsActAve );
		dgsGs.readStepDGS(gsGraph, "gsInh", 5000, 10 , pathStartGs, pathStepGs , analysisType.average , mapStepGsInhAve );
			
	//	System.out.println(mapStepGsActMax);

		Map<Double, ArrayList<Double>> mapStepMax = new HashMap<>();
		Map<Double, ArrayList<Double>> mapStepMin = new HashMap<>();
		Map<Double, ArrayList<Double>> mapStepAve = new HashMap<>();
		
		mapStepMax = getMapStepMorp(mapStepMax, mapStepGsActMax, mapStepGsInhMax);
		mapStepMin = getMapStepMorp(mapStepMin, mapStepGsActMin, mapStepGsInhMin);
		mapStepAve = getMapStepMorp(mapStepAve, mapStepGsActAve, mapStepGsInhAve);
		
		// create charts Max
		expChart xyChartMax = new expChart(typeChart.XYchart , "max", "Step (t)" , "morp (%)" , 800, 600 ,	mapStepMax );
		xyChartMax.setVisible(true);
		xyChartMax.saveChart(true,  folderChart, nameFileChartMax );
		
		// create charts Min
		expChart xyChartMin = new expChart(typeChart.XYchart , "min", "Step (t)" , "morp (%)" , 800, 600 ,	mapStepMin );
		xyChartMin.setVisible(true);
		xyChartMin.saveChart(true,  folderChart, nameFileChartMin );
				
		// create charts Ave
		expChart xyChartAve = new expChart(typeChart.XYchart , "Ave", "Step (t)" , "morp (%)" , 800, 600 ,	mapStepAve );
		xyChartAve.setVisible(true);
		xyChartAve.saveChart(true,  folderChart, nameFileChartAve );
		
//		chart.createChart();
		

		

	}

// PRIVATE METHODS ----------------------------------------------------------------------------------------------------------------------------------
	
	private static Map getMapStepMorp (Map mapMorp , Map<Double, Double> mapAct , Map<Double, Double> mapInh ) {
		
for ( java.util.Map.Entry<Double, Double> entry : mapAct.entrySet()) {
			
			ArrayList<Double> morp = new ArrayList<>();
			morp.add(mapAct.get(entry.getKey()));
			morp.add(mapInh.get(entry.getKey()));
			
			mapMorp.put(entry.getKey(), morp);
		}
		
		return mapMorp;
		
	}

}
