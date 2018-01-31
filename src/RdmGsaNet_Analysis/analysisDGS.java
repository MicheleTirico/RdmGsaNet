package RdmGsaNet_Analysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import RdmGsaNetAlgo.graphAnalysis;
import RdmGsaNetAlgo.graphAnalysis.analysisType;
import RdmGsaNet_pr08.*;

public interface analysisDGS  {
	
	static Graph gsGraph = layerGs.getGraph();
	static Graph netGraph = layerNet.getGraph();
	
	public void computeMultipleStat ( int stepMax , int stepInc ,String pathStart , String pathStep 	) throws IOException, InterruptedException ;
	
	public static Graph returnGraphAnalysis ( String dgsId ) {
		
		Graph graphAnalysis = new SingleGraph("graphAnalysis") ;
		
		if ( dgsId == "dgsGs" ) 				
			graphAnalysis = gsGraph ;	
		if ( dgsId == "dgsNet" )				
			graphAnalysis = netGraph ;
		
		return graphAnalysis ;
	}
	
// common methods -----------------------------------------------------------------------------------------------------------------------------------
	
	// compute frequency chart of degree
	public static void computeFreqDegree ( int degreeFreq , Graph graph , double step , Map mapFreqDegree ) {
			
		int nFreq = degreeFreq;
		String attribute = "degree";
			
		Map <Double, Double> mapDegree = graphAnalysis.getMapFrequencyDegree(graph, attribute, nFreq) ;
		mapFreqDegree.put(step, mapDegree);
	}
	
	// compute average degree
	public static void computeAverageDegree ( Graph graph , double step , Map mapStepAveDegree ) {
	
		double avDegree = Toolkit.averageDegree(graph);
		//System.out.println(avDegree);
		mapStepAveDegree.put(step, avDegree);
	}
	
	public static void computeStepNormalDegreeDistribution ( Graph graph , double step , Map mapStepNormalDistributionDegree , boolean setNumberLine , int numberLine ) {
	
		if ( setNumberLine ) {
			Map mapNormDegDist = graphAnalysis.getNormalDegreeDistribution(graph);
			double numberOfDegreeFreq = mapNormDegDist.size();
			double maxNewNullVal = numberLine - (double) numberOfDegreeFreq;	//			System.out.println("numberOfDegreeFreq " + numberOfDegreeFreq);			System.out.println("maxNewNullVal " + maxNewNullVal);		System.out.println(mapNormDegDist);
			
			for ( double x = numberOfDegreeFreq ; x <= maxNewNullVal + numberOfDegreeFreq ; x++ ) {
				mapNormDegDist.put(x  , 0.0 ) ;
			}	//	System.out.println(mapNormDegDist);
			mapStepNormalDistributionDegree.put(step, mapNormDegDist);
		} 
		else {
			Map mapNormDegDist = graphAnalysis.getNormalDegreeDistribution(graph);
			mapStepNormalDistributionDegree.put(step, mapNormDegDist);
		}	
	}
	
	// get list of step to do analysis
	public static ArrayList<Double> getListStepToAnalyze ( double stepInc , double stepMax ) {
			
		ArrayList<Double> list = new ArrayList<Double>();
		for ( double n = 1 ; n * stepInc <= stepMax ; n++ ) {	
			list.add( n * stepInc );	
		}	
		return list;		
	}
	
	// method to create 2 maps of statistical distribution of morphogen's values 
	public static void computeStepMorp ( Graph graph , double step , Map mapStepMorp , analysisType stat) {
		
		Map mapAct = new HashMap<>();
		Map mapInh = new HashMap<>();
		double val; 
		
		val = graphAnalysis.getAttributeStatistic(graph, "gsAct", stat );
		mapAct.put(step, val);		//	System.out.println(val);	
		
		val = graphAnalysis.getAttributeStatistic(graph, "gsInh", stat );
		mapInh.put(step, val);		//	System.out.println(val);	
		
		mapStepMorp = getMapStepMorp(mapStepMorp, mapAct, mapInh);		
	}

	// method to combine two maps of statistical morphogens  in only one map 
	public static Map getMapStepMorp (Map mapMorp , Map<Double, Double> mapAct , Map<Double, Double> mapInh ) {
		
		for ( java.util.Map.Entry<Double, Double> entry : mapAct.entrySet()) {
					
			ArrayList<Double> morp = new ArrayList<>();
			morp.add(mapAct.get(entry.getKey()));
			morp.add(mapInh.get(entry.getKey()));
					
			mapMorp.put(entry.getKey(), morp);
		}		
		return mapMorp;			
	}
	
	// implemented in analysisDGSnet
	public static void computeMapStepNewNode ( Graph graph , double step , Map mapStepNewNpode , ArrayList<Double> incList ) {

	}

}
