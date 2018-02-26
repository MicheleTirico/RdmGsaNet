package RdmGsaNet_Analysis_pr02;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import RdmGsaNetExport.expValues;
import RdmGsaNetExport.handleNameFile;
import RdmGsaNetViz.handleVizStype;
import RdmGsaNetViz.handleVizStype.palette;
import RdmGsaNetViz.handleVizStype.stylesheet;
import RdmGsaNet_Analysis.analysisDGSCombinedLayer;
import RdmGsaNet_Analysis_pr02.analysisDGSmultiLayer.correlationValGs;
import RdmGsaNet_Analysis_pr02.analysisDGSmultiLayer.correlationValNet;

public class analysisMultiLayer extends analysisMain  {
	
	protected static Map	mapGlobalCorrelation = new HashMap () ; 
			
	private static analysisDGSmultiLayer combinedAnalysis = new analysisDGSmultiLayer ( 
			/* run								*/	true ,
			/* gsViz							*/ 	true ,
			/* netViz							*/	true ,	
			/* compute Global correlation 		*/	false
			);
		
	public static void main ( String[ ] args ) throws IOException, InterruptedException {
	
		combinedAnalysis.setParametersCorrelation(correlationValGs.gsInh, correlationValNet.degree, 1 );
		
		combinedAnalysis.setParamVizNet(
				/* setScale					*/ 100 ,
				/* sizeNode, 				*/ 3.0 ,
				/* sizeEdge, 				*/ 0.1 ,
				/* colorStaticNode, 		*/ "white" ,
				/* colorStaticEdge, 		*/ "gray" ,
				/* colorBooleanNodeTrue, 	*/ "red" ,
				/* colorBooleanNodeFalse	*/ "white"
				);
		
		combinedAnalysis.setParamVizGs(
				/* sizeNode, 				*/ 4.0 ,
				/* sizeEdge, 				*/ 0.1 ,
				/* palette color 			*/ palette.red
				);

		
		combinedAnalysis.computeGlobalStat (5000 , 5 , pathStart , pathStep , 10 );
				
	}
	

		
}
