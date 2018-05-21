package RdmGsaNet_dataAnalysis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import RdmGsaNetExport.expChart;
import RdmGsaNetExport.expValues;
import RdmGsaNetExport.handleNameFile;
import RdmGsaNetExport.expChart.typeChart;
import RdmGsaNet_Analysis_02.analysisDGSnet;

public class dataAnalysisMain {
	
	
	// HANDLE FILE OBJECT
	protected static handleNameFile handle ;
		
	// inizialize dataAnalysis
	protected static dataAnalysisGlobal dataAnalysisClustering 	 		= new dataAnalysisGlobal(true) ,
										dataAnalysisDensity 			= new dataAnalysisGlobal(true) ,
										dataAnalysisAverageDegree 		= new dataAnalysisGlobal(true) ,
										dataAnalysisNewNodeRel 			= new dataAnalysisGlobal(true) ,
										dataAnalysisSeedCountRel		= new dataAnalysisGlobal(true) ,
										dataAnalysisDensityRegularGraph = new dataAnalysisGlobal(true) ; 
	
	// Maps to create chart
	static Map  mapStepProbClustering = new HashMap( ) ,
				mapStepProbDensity  = new HashMap( ) ,
				mapStepProbAverageDegree  = new HashMap( ) ,
				mapStepProbNewNodeRel  = new HashMap( ) ,
				mapStepProbSeedCountRel	 = new HashMap( ) ,
				mapDensityRegularGraph = new HashMap() ;


 	// CREATE CHARTS
	static expChart xyChart = null ;

	// folder
	protected static String pathMain = "D:\\ownCloud\\RdmGsaNet_exp\\vf_seedBirt_DynamicRadius\\circle_8\\mazes\\multiSimAnalysis\\" ,
							pathMapToAnalyze = pathMain + "mapToAnalyze\\" ,
							
							folderChart = "chart\\" ,
							
							folderClustering = "netClustering\\",
							folderDensity = "netDensity\\",
							folderAverageDegree = "netAverageDegree\\" ,
							folderNewNodeRel = "netNewNodeRel\\" ,
							folderSeedCountRel = "netSeedCountRel\\" ,
							folderDensityRegularGraph = "netDensityRegularGraph\\" ,
										
							pathClustering = pathMapToAnalyze + folderClustering ,
							pathDensity  = pathMapToAnalyze + folderDensity ,
							pathAverageDegree = pathMapToAnalyze + folderAverageDegree ,
							pathNewNodeRel =  pathMapToAnalyze + folderNewNodeRel ,
							pathSeedCountRel =  pathMapToAnalyze + folderSeedCountRel ,
							pathDensityRegularGraph = pathMapToAnalyze + folderDensityRegularGraph 
							;
	
	public static void main ( String[] args ) throws ClassNotFoundException, IOException {
		
		// setup handle name file 
				handle = new handleNameFile( 
						/* handle file 						*/ true , 
						/* set folder 						*/ pathMain ,
						/* create new folder ? 				*/ false , 
						/*  manual name file (no in main )	*/ folderChart
					);		
		
		dataAnalysisClustering.computeAnalysis( pathClustering , mapStepProbClustering);
		dataAnalysisDensity.computeAnalysis(pathDensity, mapStepProbDensity);
		dataAnalysisAverageDegree.computeAnalysis(pathAverageDegree , mapStepProbAverageDegree);
		dataAnalysisNewNodeRel.computeAnalysis(pathNewNodeRel, mapStepProbNewNodeRel);
		dataAnalysisSeedCountRel.computeAnalysis(pathSeedCountRel, mapStepProbSeedCountRel);
		dataAnalysisDensityRegularGraph.computeAnalysis(pathDensityRegularGraph, mapDensityRegularGraph);
			
		
		createCharts(
				/* store chart ? 										*/ true , 
				/* create chart clustering								*/ true , 
				/* create chart density									*/ true , 
				/* create chart average Degree							*/ true ,
				/* create chart New Node Rel							*/ true ,
				/* create chart Seed Count Rel							*/ true ,
				/* create chart DensityRegularGraph						*/ true 
				);
	}
		private static void createCharts ( boolean storeCharts , 
				boolean createChartClustering , boolean createChartDensity , boolean createChartAverageDegree ,
				boolean createChartNewNodeRel , boolean createChartSeedCountRel , boolean createChartDensityRegularGraph
			 ) 	throws IOException {
		
			// exit method 
			if ( !dataAnalysisGlobal.run  ) return ;
			
			try {
				if ( createChartClustering ) {
					xyChart = new expChart(typeChart.XYchartSer_xy , "clustering Net", "Step (t)" , " average clustering (n)" , 800, 600 ,	mapStepProbClustering );
					xyChart.setVisible(true);
					xyChart.saveChart( true ,  pathMain + folderChart , "mapProbClustering" );	
					return ; 
					
					
				}
			}catch (Exception e) {
				// TODO: handle exception
			}
			try {
				if ( createChartDensity ) {
					xyChart = new expChart(typeChart.XYchartSer_xy , "density  Net", "Step (t)" , "  density (n)" , 800, 600 ,	mapStepProbDensity );
					xyChart.setVisible(true);
					xyChart.saveChart(false ,  pathMain + folderChart , "ProbDensity" );	
				}
			}catch (Exception e) {
				// TODO: handle exception
			}
			try {	
				if ( createChartAverageDegree ) {
						xyChart = new expChart(typeChart.XYchartSer_xy , "average degree Net", "Step (t)" , " average degree (n)" , 800, 600 ,	mapStepProbAverageDegree );
						xyChart.setVisible(true);
						xyChart.saveChart(false ,  pathMain + folderChart , "ProbAverageDegree" );	
				}
			}catch (Exception e) {
				// TODO: handle exception
			}
			try {			
				if ( createChartNewNodeRel ) {
					xyChart = new expChart(typeChart.XYchartSer_xy , "new node rel Net", "Step (t)" , " average degree (n)" , 800, 600 ,	mapStepProbNewNodeRel );
					xyChart.setVisible(true);
					xyChart.saveChart(false ,  pathMain + folderChart , "ProbNewNodeRel" );	
				}
			}catch (Exception e) {
				// TODO: handle exception
			}
			try {			
				if ( createChartSeedCountRel) {
					xyChart = new expChart(typeChart.XYchartSer_xy , "seed rel Net", "Step (t)" , " average degree (n)" , 800, 600 ,	mapStepProbSeedCountRel );
					xyChart.setVisible(true);
					xyChart.saveChart(false ,  pathMain + folderChart , "ProbSeedCountRel" );	
				}
			}catch (Exception e) {
				// TODO: handle exception
			}
			try {	
				if ( createChartDensityRegularGraph) {
					xyChart = new expChart(typeChart.XYchartSer_xy , "density regularg grid Net", "Step (t)" , " regular grid (n)" , 800, 600 ,	mapDensityRegularGraph );
					xyChart.setVisible(true);
					xyChart.saveChart(false ,  pathMain + folderChart , "DensityRegularGraph" );	
				}
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
}
