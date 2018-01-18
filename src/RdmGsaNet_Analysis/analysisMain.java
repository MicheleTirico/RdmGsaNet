package RdmGsaNet_Analysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import RdmGsaNetAlgo.morpAnalysis.analysisType;
import RdmGsaNetExport.expChart;
import RdmGsaNetExport.expGraph;
import RdmGsaNetExport.expChart.typeChart;
import RdmGsaNetViz.graphViz;
import RdmGsaNet_pr08.setupNetSeed;

public class analysisMain {
		
	private static String fileType = ".dgs" ;
	private static String folder = "C:\\Users\\frenz\\ownCloud\\RdmGsaNet_exp\\test_gradient\\dgs\\";
	
// START FILES
	// GS graph
	private static String nameStartGs = "layerGsStart_Size_50_Da_0.2_Di_0.1_F_0.03_K_0.062"  ;
	private static String folderStartGs = folder;
	private static String pathStartGs = folderStartGs + nameStartGs + fileType ;
	
	// NET graph
	private static String nameStartNet = "layerNetStart_Size_50_Da_0.2_Di_0.1_F_0.03_K_0.062"  ;
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
	private static String folderChart = "C:\\Users\\frenz\\ownCloud\\RdmGsaNet_exp\\test_gradient\\chart\\" ;
	
	private static String nameFileChartMax = nameStartGs + "max" ;
	private static String nameFileChartMin = nameStartGs + "min" ;
	private static String nameFileChartAve = nameStartGs + "ave" ;
	
// GRAPHS
	protected static Graph gsGraph = new SingleGraph( "gsGraph"); 
	protected static Graph netGraph = new SingleGraph( "netGraph"); 
		
// CREATE CHARTS
	private static analysisChart chart = new analysisChart();
	
// ANALYSIS DGS
	private static analysisDGS dgsGs = new analysisDGS(false // compute degree ?
			
			);
	public static analysisDGS dgsNet = new analysisDGS(true);
	
// MAP
	private static Map<Double, Double> mapStepGsActMax = new HashMap<Double , Double > ();
	private static Map<Double, Double> mapStepGsInhMax = new HashMap<Double , Double > ();
	
	private static Map<Double, Double> mapStepGsActMin = new HashMap<Double , Double > ();
	private static Map<Double, Double> mapStepGsInhMin = new HashMap<Double , Double > ();
	
	private static Map<Double, Double> mapStepGsActAve = new HashMap<Double , Double > ();
	private static Map<Double, Double> mapStepGsInhAve = new HashMap<Double , Double > ();
	
	public static void main(String[] args) throws IOException {
		
		dgsGs.setParamDegree(gsGraph , 100);
		dgsNet.setParamDegree(netGraph , 10);
		
		expChart xyChart = null;
		Map mapStepfrequency = null ;
		
		// read files in a loop 
		dgsGs.readStartDGS(gsGraph, pathStartGs);
		dgsNet.readStartDGS(netGraph, pathStartNet);
			
		Map<Double, ArrayList<Double>>  mapStepMax = new HashMap(), 
										mapStepMin = new HashMap(), 
										mapStepAve = new HashMap() ,
										mapDegree = new HashMap() ;
		
		dgsGs.computeMultipleStat(gsGraph, "gsAct", 10, 5, pathStartGs, pathStepGs);
		dgsNet.computeMultipleStat(netGraph, "gsAct", 10, 5, pathStartNet, pathStepNet);
		/*
		mapStepfrequency = new HashMap<>();
		
		dgsGs.computeFrequencyNodeFromDGS(gsGraph, "gsAct", 20 , 5000, 10, pathStartGs, pathStepGs, mapStepfrequency);
		
		// create charts stepFrequency
		xyChart = new expChart(typeChart.XYchartMultipleLine , "frequency of nodes Act", "Step (t)" , "number of nodes (n)" , 800, 600 ,	mapStepfrequency );
		xyChart.setVisible(true);
		xyChart.saveChart(true,  folderChart, "frequency Act" );
		
		mapStepfrequency = new HashMap<>() ;
		dgsGs.computeFrequencyNodeFromDGS(gsGraph, "gsInh", 20 , 5000, 10, pathStartGs, pathStepGs, mapStepfrequency);
		
		// create charts stepFrequency
		xyChart = new expChart(typeChart.XYchartMultipleLine , "frequency of nodes Inh", "Step (t)" , "number of nodes (n)" , 800, 600 ,	mapStepfrequency );
		xyChart.setVisible(true);
		xyChart.saveChart(true,  folderChart, "frequency Inh" );
		
		/*
		analysisDGS.computeAllStatFromDGS(gsGraph, 5000, 10 , pathStartGs, pathStepGs , mapStepMax, mapStepMin, mapStepAve);
		mapStepMax = getMapStepMorp(mapStepMax, mapStepGsActMax, mapStepGsInhMax);
		mapStepMin = getMapStepMorp(mapStepMin, mapStepGsActMin, mapStepGsInhMin);
		mapStepAve = getMapStepMorp(mapStepAve, mapStepGsActAve, mapStepGsInhAve);
				
		// create charts Max
		expChart xyChartMax = new expChart(typeChart.XYchart2Morp , "max", "Step (t)" , "morp (%)" , 800, 600 ,	mapStepMax );
		xyChartMax.setVisible(true);
		xyChartMax.saveChart(false,  folderChart, nameFileChartMax );
		
		// create charts Min
		expChart xyChartMin = new expChart(typeChart.XYchart2Morp , "min", "Step (t)" , "morp (%)" , 800, 600 ,	mapStepMin );
		xyChartMin.setVisible(true);
		xyChartMin.saveChart(false,  folderChart, nameFileChartMin );
				
		// create charts Ave
		expChart xyChartAve = new expChart(typeChart.XYchart2Morp , "Ave", "Step (t)" , "morp (%)" , 800, 600 ,	mapStepAve );
		xyChartAve.setVisible(true);
		xyChartAve.saveChart(false,  folderChart, nameFileChartAve );	
		*/
	}

// PRIVATE METHODS ----------------------------------------------------------------------------------------------------------------------------------
	
	private static Map getMapStepMorp (Map mapMorp , Map<Double, Double> mapAct , Map<Double, Double> mapInh ) {
		
		for ( Entry<Double, Double> entry : mapAct.entrySet()) {
			
			ArrayList<Double> morp = new ArrayList<>();
			morp.add(mapAct.get(entry.getKey()));
			morp.add(mapInh.get(entry.getKey()));
			
			mapMorp.put(entry.getKey(), morp);
		}
		return mapMorp;	
	}
	
	// not yet testing 
	private static Map<Double, ArrayList<Double>> getAveRel ( Map<Double, ArrayList<Double>> mapStepMax ,Map<Double, ArrayList<Double>> mapStepMin ,Map<Double, ArrayList<Double>> mapStepAve  ) {
		
		Map<Double, ArrayList<Double>> map = new HashMap<Double, ArrayList<Double>>();
		
		for ( Entry<Double, ArrayList<Double>> entry : mapStepAve.entrySet()) {
			
			double step = entry.getKey();
			ArrayList<Double> aveRelList = new  ArrayList<Double>() ;
			
			ArrayList<Double> arrMax = mapStepMax.get(step);
			ArrayList<Double> arrMin = mapStepMin.get(step);
			ArrayList<Double> listAve = mapStepAve.get(step);

			for (int morp = 0 ; morp <= 1 ; morp++ ) {

				double valMax = arrMax.get(morp);
				double valMin = arrMin.get(morp);
				double valAve = listAve.get(morp);
		
				double gap = valMax - valMin ;
				
				double AveRel = valAve / gap ;
				
				aveRelList.add(morp, AveRel);//				System.out.println(aveRelList);
			}	
			map.put(step, aveRelList);
		}
		return map;	
	}
	
	public static analysisDGS getNameObj ( ) { return dgsNet	;}
}
