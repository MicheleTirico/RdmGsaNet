package RdmGsaNet_Analysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import RdmGsaNetAlgo.graphAnalysis;
import RdmGsaNetAlgo.graphAnalysis.analysisType;
import RdmGsaNet_pr08.*;

public interface analysisDGS  {
		
	public void computeGlobalStat ( int stepMax , int stepInc ,String pathStart , String pathStep , int thread	) throws IOException, InterruptedException ;
	
	public void computeLocalStat ( int stepMax , int stepInc ,String pathStart , String pathStep  , int thread	) throws IOException, InterruptedException ;
	
	public static Graph returnGraphAnalysis ( String dgsId ) {
		
		Graph graphAnalysis = new SingleGraph("graphAnalysis") ;
		
		if ( dgsId == "dgsGs" ) 				
			graphAnalysis = layerGs.getGraph() ;	
		if ( dgsId == "dgsNet" )				
			graphAnalysis = layerNet.getGraph(); ;
		
		return graphAnalysis ;
	}
	
// GLOBAL STAT METHODS  -----------------------------------------------------------------------------------------------------------------------------
	
// degree -------------------------------------------------------------------------------------------------------------------------------------------
	// compute frequency chart of degree
	public static void computeFreqDegree ( int degreeFreq , Graph graph , double step , Map mapFreqDegree ) {
			
		int nFreq = degreeFreq;
		String attribute = "degree";
			
		Map <Double, Double> mapDegree = graphAnalysis.getMapFrequencyDegree(graph, nFreq, false) ;
		mapFreqDegree.put(step, mapDegree);
	}
	
	// compute frequency chart of degree REL .  return the same result of normal degree distribution
	public static void computeFreqDegreeRel ( int degreeFreq , Graph graph , double step , Map mapFreqDegreeRel ) {
			
		int nFreq = degreeFreq;
		String attribute = "degree";
			
		Map <Double, Double> mapDegree = graphAnalysis.getMapFrequencyDegree(graph, nFreq, true) ;
		mapFreqDegreeRel.put(step, mapDegree);
	}
				
	// compute average degree
	public static void computeAverageDegree ( Graph graph , double step , Map mapStepAveDegree ) {
	
		double avDegree = Toolkit.averageDegree(graph);
		mapStepAveDegree.put(step, avDegree);
	}
	
	//compute normal degree distribution of each step. return the same result of freq degree rel
	public static void computeStepNormalDegreeDistribution ( Graph graph , double step , Map mapStepNormalDistributionDegree , boolean setNumberLine , int numberLine ) {
	
		if ( setNumberLine ) {
			Map mapNormDegDist = graphAnalysis.getNormalDegreeDistribution(graph);
			double numberOfDegreeFreq = mapNormDegDist.size();
			double maxNewNullVal = numberLine - (double) numberOfDegreeFreq;	//			System.out.println("numberOfDegreeFreq " + numberOfDegreeFreq);			System.out.println("maxNewNullVal " + maxNewNullVal);		System.out.println(mapNormDegDist);
			
			for ( double x = numberOfDegreeFreq ; x <= maxNewNullVal + numberOfDegreeFreq ; x++ ) {
				mapNormDegDist.put(x  , 0.0 ) ;
			}	
			mapStepNormalDistributionDegree.put(step, mapNormDegDist);
		} 
		else {
			Map mapNormDegDist = graphAnalysis.getNormalDegreeDistribution(graph);
			mapStepNormalDistributionDegree.put(step, mapNormDegDist);
		}	
	}
	
// seed grad stat -----------------------------------------------------------------------------------------------------------------------------------
	public static void computeStepCountNewSeed ( Graph graph , double step , Map mapStepNewSeed , boolean isRel ) {
	
		double count = graphAnalysis.getAttributeCount(graph , step , "seedGrad" ) ;
		
		if ( isRel == false )
			mapStepNewSeed.put(step, count) ;
		
		else if ( isRel ) {
			double countRel = count / graph.getNodeCount() ;
			mapStepNewSeed.put(step, countRel) ;
		}		
	}

	public static void computeStepAveSeed ( Graph graph , double step , Map mapStepAveSeed ) {		}
		
// morp stat ----------------------------------------------------------------------------------------------------------------------------------------
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
	
	public static void  computeGsActivedNodes ( Graph graph , double step , Map mapGsActivedNodes ) {
		
	}
	
// COMPUTE NEW NODES --------------------------------------------------------------------------------------------------------------------------------	
	// compute % new nodes vs size graph
	public static void computeStepNewNodeRel ( Graph graph , double step , Map mapStepNewNodeRel , Map<Integer , Integer >  mapNetStepNodeCountRel ,  int  s  ) {

		mapNetStepNodeCountRel.put(s, graph.getNodeCount());
		try {
			mapStepNewNodeRel.put(step,(double) (graph.getNodeCount() - mapNetStepNodeCountRel.get(s-1)) / graph.getNodeCount());//	
		} catch (java.lang.NullPointerException e) {			}
		s++;
	}
	
	// implemented in analysisDGSnet
	public static void computeStepNewNode ( Graph graph , double step , Map mapStepNewNode , Map<Integer , Integer >  mapNetStepNodeCount ,  int  s  ) {

		mapNetStepNodeCount.put(s, graph.getNodeCount());
		try {
			mapStepNewNode.put(step,(double) (graph.getNodeCount() - mapNetStepNodeCount.get(s-1)) );//	
		} catch (java.lang.NullPointerException e) {			}
		s++;
	}

	
//---------------------------------------------------------------------------------------------------------------------------------------------------
	// get list of step to do analysis
	public static ArrayList<Double> getListStepToAnalyze ( double stepInc , double stepMax ) {
			
		ArrayList<Double> list = new ArrayList<Double>();
		for ( double n = 1 ; n * stepInc <= stepMax ; n++ ) {	
			list.add( n * stepInc );	
		}	
		return list;		
	}
	
// LOCAL STAT METHODS -------------------------------------------------------------------------------------------------------------------------------
	// return map of step with map of nodes and clustering
	public static void computeLocalClustering ( Graph graph , double step , Map mapStepMapNodeClustering ) {
		Map map =graphAnalysis.getMapNodeClustering(graph);
		mapStepMapNodeClustering.put(step, map);
	}

	// global (average) clustering
	public static void computeGlobalClustering ( Graph graph, double step , Map mapStepGlobalClustering ) {
		double clustering = Toolkit.averageClusteringCoefficient(graph);
		mapStepGlobalClustering.put(step, clustering);
	}

	// global density 
	public static void computeGlobalDensity ( Graph graph, double step , Map mapStepGlobalDensity ) {
		double density = Toolkit.density(graph);
		mapStepGlobalDensity.put(step, density);
	}

	void computeLocalStat(int stepMax, int stepInc, String pathStart, String pathStep)
			throws IOException, InterruptedException;


}

