package RdmGsaNet_Analysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

import RdmGsaNetAlgo.graphAnalysis.analysisType;
import RdmGsaNetExport.expChart;
import RdmGsaNetExport.expGraph;
import RdmGsaNetExport.expChart.typeChart;
import RdmGsaNetViz.graphViz;
import RdmGsaNetViz.setupViz;
import RdmGsaNet_pr08.setupNetSeed;
import javafx.scene.chart.XYChart;

public class OLDanalysisMain {
		
	private static String fileType = ".dgs" ;
	private static String folder = "D:\\ownCloud\\RdmGsaNet_exp\\test_gradient_2\\maxStep_3000_generateNetNodeGradient_generateNetEdgeNear_prob_0.15_00\\";
	
// START FILES
	// GS graph
	private static String nameStartGs = "layerGs_start_setupGsGrid_grid8_size_50_Da_0.2_Di_0.1_f_0.03_k_0.062_diff_weight"  ;
	private static String folderStartGs = folder;
	private static String pathStartGs = folderStartGs + nameStartGs + fileType ;
	
	// NET graph
	private static String nameStartNet = "layerNet_start_setupNetSeed"  ;
	private static String folderStartNet = folder;
	private static String pathStartNet = folderStartNet + nameStartNet + fileType ;
	
// STEP FILES	
	// GS graph
	private static String nameStepGs = "layerGs_step_setupGsGrid_grid8_size_50_Da_0.2_Di_0.1_f_0.03_k_0.062_diff_weight"; 
	private static String folderStepGs = folder;
	private static String pathStepGs = folderStepGs + nameStepGs + fileType ;
	
	// NET graph
	private static String nameStepNet = "layerNet_step_setupNetSeed"; 
	private static String folderStepNet = folder;
	private static String pathStepNet = folderStepNet + nameStepNet + fileType ;
	
		
// EXPORT IMAGES
	private static String folderIm =  "C:\\Users\\frenz\\ownCloud\\RdmGsaNet_exp\\dgs\\Da_0.2_Di_0.1_02\\image\\";
	private static String nameIm = nameStepGs + "_step_";
	
// EXPORT CHARTS
	private static String folderChart = "D:\\ownCloud\\RdmGsaNet_exp\\test_gradient_2\\maxStep_3000_generateNetNodeGradient_generateNetEdgeNear_prob_0.15_00\\chart\\" ;
	
	private static String nameFileChartMax = nameStartGs + "max" ;
	private static String nameFileChartMin = nameStartGs + "min" ;
	private static String nameFileChartAve = nameStartGs + "ave" ;
	
// GRAPHS
	protected static Graph gsGraph = new SingleGraph( "gsGraph"); 
	protected static Graph netGraph = new SingleGraph( "netGraph"); 
		
// CREATE CHARTS
	private static analysisChart chart = new analysisChart();
	
// ANALYSIS DGS
	public static OLDanalysisDGS dgsGs = new OLDanalysisDGS(
			"dgsGs" , 		// id analysis dgs Gs
			true ,		// run
			false ,		// run all
			false , 	// compute degree ?
			false ,		// compute distribution of morphogens (max)
			false,		// compute distribution of morphogens (min)					
			false ,		// compute distribution of morphogens (average)		
			false
			);
	
	public static OLDanalysisDGS dgsNet = new OLDanalysisDGS( 
			"dgsNet" , 	// id analysis dgs Net
			true,		// run
			true,		// run all
			true , 		// compute degree ?
			false ,		// compute distribution of morphogens (max)
			false,		// compute distribution of morphogens (min)					
			false,		// compute distribution of morphogens (average)
			true		// compute distribution of new nodes
			);
	
// MAP
	private static Map<Double, Double> 	mapStepGsActMax = new HashMap<Double , Double > () ,
										mapStepGsInhMax = new HashMap<Double , Double > () ,
										mapStepGsActMin = new HashMap<Double , Double > () , 
										mapStepGsInhMin = new HashMap<Double , Double > () , 
										mapStepGsActAve = new HashMap<Double , Double > () , 
										mapStepGsInhAve = new HashMap<Double , Double > () ;
	
	private static Map<Double, ArrayList<Double>>  	mapStepMaxMorp = new HashMap(), 
													mapStepMinMorp = new HashMap(), 
													mapStepAveMorp = new HashMap() ,
													mapFreqDegreeGs = new HashMap() ,			
													mapFreqDegreeNet = new HashMap() ,
													mapStepNewNode = new HashMap() ;
	
	public static void main(String[] args) throws IOException, InterruptedException {
	
		dgsGs.setParamAnalysis(
			/* morp analysis  		*/ "gsAct"
			/* frequency degree		*/ , 5);
		dgsNet.setParamAnalysis(
			/* morp analysis  		*/ "gsAct"
			/* frequency degree		*/ , 8 );
					
		dgsGs.computeMultipleStat( 3000, 5 , true , pathStartGs, pathStepGs, 
								mapFreqDegreeGs , mapStepMaxMorp , mapStepMinMorp , mapStepAveMorp , mapStepNewNode);	
		
		dgsNet.computeMultipleStat( 1000, 5 , true ,  pathStartNet, pathStepNet , 
									mapFreqDegreeNet , mapStepMaxMorp , mapStepMinMorp , mapStepAveMorp , mapStepNewNode );
		
		createCharts(true, false , false , false );  
		

	//	dgsNet.TESTviz ( 100, 5 , pathStartNet, pathStepNet );
		
	}

// PRIVATE METHODS ----------------------------------------------------------------------------------------------------------------------------------
	private static void createCharts ( boolean createChartDegree , boolean createChartMax , boolean createChartMin, boolean createChartAve ) throws IOException {
		
		expChart xyChart = null ;
		
		if ( createChartDegree == true ) {	
			xyChart = new expChart(typeChart.XYchartMultipleLine , "frequency degree Net", "Step (t)" , " freq degree (n)" , 800, 600 ,	mapFreqDegreeGs );
			xyChart.setVisible(true);
			xyChart.saveChart(true,  folderChart, "frequency degree Net" );	
		}
		xyChart = null ;
		
		if ( createChartMax == true ) {
			 xyChart = new expChart(typeChart.XYchart2Morp , "max", "Step (t)" , "morp (%)" , 800, 600 ,	mapStepMaxMorp );
			xyChart.setVisible(true);
			xyChart.saveChart(true,  folderChart, "max" );			
		}
		xyChart = null ;
		
		if ( createChartMin == true ) {
			xyChart = new expChart(typeChart.XYchart2Morp , "min", "Step (t)" , "morp (%)" , 800, 600 ,	mapStepMinMorp );
			xyChart.setVisible(true);
			xyChart.saveChart(true,  folderChart, "min" );			
		}
		xyChart = null ;
		
		if ( createChartAve == true ) {
			 xyChart = new expChart(typeChart.XYchart2Morp , "ave", "Step (t)" , "morp (%)" , 800, 600 ,	mapStepAveMorp );
			xyChart.setVisible(true);
			xyChart.saveChart(true,  folderChart, "ave" );			
		}
		xyChart = null ;		
	}
	
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
	
	public static OLDanalysisDGS getNameObj ( ) { return dgsNet	;}
}
