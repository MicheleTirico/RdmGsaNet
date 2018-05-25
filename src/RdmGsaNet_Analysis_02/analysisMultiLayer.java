package RdmGsaNet_Analysis_02;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import RdmGsaNetExport.expValues;
import RdmGsaNetExport.handleNameFile;
import RdmGsaNetViz.handleVizStype;
import RdmGsaNetViz.handleVizStype.palette;
import RdmGsaNetViz.handleVizStype.stylesheet;
import RdmGsaNet_Analysis_02.analysisDGSmultiLayer.correlationValGs;
import RdmGsaNet_Analysis_02.analysisDGSmultiLayer.correlationValNet;

public class analysisMultiLayer extends analysisMain  {
	
	protected static Map	mapGlobalCorrelation = new HashMap () ; 
		
	private static analysisDGSmultiLayer combinedAnalysis = new analysisDGSmultiLayer ( 
			/* run								*/	true ,
			/* gsViz							*/ 	true ,
			/* netViz							*/	true,	
			/* vecViz							*/	true,		
			/* SeedViz							*/	true ,	
			/* compute Global correlation 		*/	false
			);
		
	
	public static void main ( String[ ] args ) throws IOException, InterruptedException { 
	
		combinedAnalysis.setParametersCorrelation(correlationValGs.gsInh, correlationValNet.degree, 1 );
		
		combinedAnalysis.setParamVizNet(
				/* setScale					*/ 50 ,
				/* sizeNode, 				*/ 3.0 ,
				/* sizeEdge, 				*/ .5 ,
				/* colorStaticNode, 		*/ "black" ,
				/* colorStaticEdge, 		*/ "black" ,				
				/* colorBooleanNodeTrue, 	*/ "red" ,
				/* colorBooleanNodeFalse	*/ "black"
				);
		
		
		combinedAnalysis.setParamVizGs(
				/* sizeNode, 				*/ 6.0 ,
				/* sizeEdge, 				*/ 0.1 ,
				/* palette color 			*/ palette.red
				);
		
		combinedAnalysis.setParamVizVec(
				/* sizeNodeVec				*/ 0.1, 
				/* sizeEdgeVec				*/ 0.01
				);
		
		combinedAnalysis.setParamVizSeed(
				/* sizeNode seed				*/ 5, 
				/* sizeEdge seed				*/ 5
				);

		
		combinedAnalysis.computeGlobalStat (1600 , 5 , pathStart , pathStep , 5 );
				
	}
	

		
}
