package RdmGsaNet_Analysis_02;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import RdmGsaNetAlgo.graphAnalysis;
import RdmGsaNetAlgo.graphAnalysis.analysisType;
import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNet_mainSim.layerGs;
import RdmGsaNet_mainSim.layerNet;


public interface analysisDGS  {

	public void computeGlobalStat ( int stepMax , int stepInc , String[] pathStartArr , String[] pathStepArr  , int thread) throws IOException, InterruptedException ;
	
	public void computeLocalStat  ( int stepMax , int stepInc , String[] pathStartArr , String[] pathStepArr  , int thread ) throws IOException, InterruptedException ;
	
	public static Graph returnGraphAnalysis ( String dgsId ) {
	
		Graph graphAnalysis = new SingleGraph("graphAnalysis") ;
		
		if ( dgsId == "dgsGs" ) 				
			graphAnalysis = layerGs.getGraph() ;	
		if ( dgsId == "dgsNet" )				
			graphAnalysis = layerNet.getGraph() ;
		
		return graphAnalysis ;
	}

//GLOBAL STAT METHODS  -----------------------------------------------------------------------------------------------------------------------------

//degree -------------------------------------------------------------------------------------------------------------------------------------------
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
	
	//seed grad stat -----------------------------------------------------------------------------------------------------------------------------------
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
	 
//morp stat ----------------------------------------------------------------------------------------------------------------------------------------
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
	
//COMPUTE NEW NODES --------------------------------------------------------------------------------------------------------------------------------	
	// compute % new nodes vs size graph
	public static void computeStepNewNodeRel ( Graph graph , double step , Map mapStepNewNodeRel , Map<Integer , Integer >  mapNetStepNodeCountRel ,  int  s  ) {
	
		mapNetStepNodeCountRel.put(s, graph.getNodeCount()); //	System.out.println(mapNetStepNodeCountRel);
		try {
			mapStepNewNodeRel.put(step,(double) (graph.getNodeCount() - mapNetStepNodeCountRel.get(s-1)) / graph.getNodeCount());//	
		} catch (java.lang.NullPointerException e) {			}
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

	// global density regular grid
	public static void computeGlobalDensityRegularGraph ( Graph graph, double step , Map mapStepGlobalDensityGrid , int regularGraphDegree ) {
		
		int nodeCount = graph.getNodeCount() ;
		
		double sumDegree = 0.0 ;
		for ( Node n : graph.getEachNode() ) {
			double degree = n.getDegree();
			sumDegree = sumDegree + degree ;
		}
		double densityRegularGraph = sumDegree / ( nodeCount * regularGraphDegree) ;
		
		mapStepGlobalDensityGrid.put(step, densityRegularGraph) ;				//	System.out.println(mapStepGlobalDensityGrid);
	}
	
//LOCAL STAT METHODS -------------------------------------------------------------------------------------------------------------------------------
	// return map of step with map of nodes and clustering
	public static void computeMapLocalClustering ( Graph graph , double step , Map mapStepMapNodeClustering ) {
		Map map = graphAnalysis.getMapNodeClustering(graph);
		mapStepMapNodeClustering.put(step, map);
	}
	
	public static void computeLocalClustering ( Graph graph , double step ) {
		
	}
	
// GET METHODS --------------------------------------------------------------------------------------------------------------------------------------

// CORRELATION METHODS ------------------------------------------------------------------------------------------------------------------------------
	
	// global correlation 
	public static void computeGlobalCorrelation2 ( Graph graph0 , Graph graph1 , String atr0 , String atr1 , double step , int depth , Map mapGlobalCorrelation , boolean isCorrect ) {

		Collection<Node> nodeSet0 = graph0.getNodeSet();	//	System.out.println(nodeSet0.size());
		Collection<Node> nodeSet1 = graph1.getNodeSet();	//	System.out.println(nodeSet1.size());
		
		ArrayList<String> idSet0 = new ArrayList<String>();
		ArrayList<String> idSet1 = new ArrayList<String>();
	
		for ( Node n : nodeSet0) 
			idSet0.add(n.getId());
		
		for ( Node n : nodeSet1) 
			idSet1.add(n.getId());
		
		Map<Node , Double > map0 = new HashMap<Node, Double>();
		Map<Node , Double > map1 = new HashMap<Node, Double>();

		for ( Node n : graph0.getEachNode()) {
			if ( idSet1.contains(n.getId()))	 	
				map0.put(n, n.getAttribute(atr0)) ;
		}
		
		for ( Node n : graph1.getEachNode())  {
			if ( idSet0.contains(n.getId()))
				try {
				map1.put(n, (double) n.getAttribute(atr1)) ;
				}
			catch (java.lang.ClassCastException e) {
				int atr =  n.getAttribute(atr1);
				double val = 0.0 ;
				if ( atr == 1 )
					val = 1.0;
				else if ( atr == 0)
					val = 0.0 ;
				map1.put(n,  val) ;
			}
				}
		double globalCor = graphAnalysis.getGlobalCorrelation(map0, map1 , isCorrect);		//	System.out.println(globalCor);
		
		mapGlobalCorrelation.put(step, globalCor);

	}
	
	
	public static void computeGlobalCorrelation3 ( Map map0 , Map map1 , Map mapGlobalCorrelation , double step , boolean isSampling ) {
		
	}
	
	
	
	
	
	public static void computeGlobalCorrelation ( Graph graph0 , Graph graph1 , String attr0 , String attr1 , boolean  normVal0 , boolean normVal1 , Map mapGlobalCorrelation , double step  , boolean isSampling ) {
		
		double 	covariance = 0 ,
				aveVal0 , 
				aveVal1 ,
				stDev0 ,
				stDev1 ;
		
		ArrayList<Double> 	listVal0 = new ArrayList<Double>(), 
							listVal1 = new ArrayList<Double>() ,
							listVal0Common = new ArrayList<Double>() ,
							listVal1Common = new ArrayList<Double>();
		
		Map <String , Double> 	mapIdAttr0 , 
								mapIdAttr1 ;
		
		mapIdAttr0 = gsAlgoToolkit.getMapIdAttr(graph0, attr0, normVal0 ) ;
		mapIdAttr1 = gsAlgoToolkit.getMapIdAttr( graph1 , attr1 , normVal1) ;
		
	//	System.out.println(mapIdAttr0);
	//	System.out.println(mapIdAttr1);
		
		for ( String id : mapIdAttr0.keySet()) 
			listVal0.add(mapIdAttr0.get(id));
		
		for ( String id : mapIdAttr1.keySet()) 
			listVal1.add(mapIdAttr1.get(id));															//		
	
//		System.out.println(listVal0.size());
//		System.out.println(listVal1.size());
			
		ArrayList<String> listIdCommon = gsAlgoToolkit.getListIdCommon(mapIdAttr0, mapIdAttr1);			//		System.out.println(listIdCommon.size());
		
		for(String id : listIdCommon) {
			listVal0Common.add(mapIdAttr0.get(id));
			listVal1Common.add(mapIdAttr1.get(id));
		}
		
//		System.out.println(listVal0Common.size());
//		System.out.println(listVal1Common.size());
		
		aveVal0 = listVal0Common.stream().mapToDouble(val->val).average().getAsDouble() ;						//			System.out.println("aveVal0 " + aveVal0);
		aveVal1 = listVal1Common.stream().mapToDouble(val->val).average().getAsDouble() ;						//			
//		System.out.println("max1 " + listVal1Common.stream().mapToDouble(val->val).max().getAsDouble() );
//		System.out.println("aveVal1 " + aveVal1);
		
		stDev0 = gsAlgoToolkit.getStandarDeviation( isSampling , listVal0Common);								//		System.out.println("stDev0 " + stDev0);	
		stDev1 = gsAlgoToolkit.getStandarDeviation( isSampling , listVal1Common);										//		System.out.println("stDev1 " + stDev1);
		//		System.out.println("covariance " + covariance);
		for ( String s : listIdCommon ) {
			
			double val0 = mapIdAttr0.get(s);															//				
	//		System.out.println(s + " " + val0);
	//		System.out.println("aveVal0 " + aveVal0);
			
			double val1 = mapIdAttr1.get(s);															//				System.out.println(s + " " +  val1);	
		
			
			covariance = covariance + ( ( val0 - aveVal0) * ( val1 - aveVal1) );
	//		System.out.println(( val0 - aveVal0) * ( val1 - aveVal1));
		}	
//		System.out.println("covariance " + covariance);
		
		double numberVal = 0 ;
		
		
		if ( isSampling ) 
			numberVal = listIdCommon.size() - 1 ; 
		else if ( !isSampling )
			numberVal = listIdCommon.size();
		
//		System.out.println("numberVal " + numberVal);
		covariance = covariance / numberVal ;
//	System.out.println("covariance " + covariance);
		mapGlobalCorrelation.put(step,  covariance / ( stDev0 * stDev1 ) );
	//	System.out.println(mapGlobalCorrelation);
	}
}
