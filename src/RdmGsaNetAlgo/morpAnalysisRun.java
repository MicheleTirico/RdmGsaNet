package RdmGsaNetAlgo;

import java.io.IOException;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import RdmGsaNetAlgo.morpAnalysis.morphogen;
import RdmGsaNetAlgo.morpSpatialAutoCor.distanceMatrixType;
import RdmGsaNetExport.expGraph;

public class morpAnalysisRun {
	
	private static int 	step0 = 149 , 
						step1 = 150 ;
	
	private static String dossierExp = "D:\\Dropbox\\Dropbox\\JAVA\\RdmGsaNet_Export\\" ; 
	private static String nameFileExp = RdmGsaNet_pr08.main.getNameFileExp();
	private static String nameFileExpStep0 = nameFileExp + "_step_" + step0;
	private static String nameFileExpStep1 = nameFileExp + "_step_" + step1;
	
	private static Graph graphStep0 = new SingleGraph("graphStep0") ;
	private static Graph graphStep1 = new SingleGraph("graphStep1") ;
	
	public static void AnaysisSPAC () throws IOException {
		
		
		// ITERATION FOR EACH FILE IN DOSSIER
			
		
		// Import graph
		expGraph.readGraphEachStepDgs(graphStep0, dossierExp, nameFileExpStep0);
		expGraph.readGraphEachStepDgs(graphStep1, dossierExp, nameFileExpStep1);
		
	}
	
	public static void main(String[] args) throws IOException {
		
		// Import graph
		expGraph.readGraphEachStepDgs(graphStep0, dossierExp, nameFileExpStep0);
		expGraph.readGraphEachStepDgs(graphStep1, dossierExp, nameFileExpStep1);
		
		// lisa
//		morpAnalysis.spatialAutoCorLisaLocalMoran(graphStep1, morphogen.activator, 3, distanceMatrixType.weight) ;
		
		// sp autocorrelation
		
		Map<String , Double> mapIdNodeActSpac = morpSignProCor.getMapSPACval(graphStep0, graphStep1, morphogen.activator);
		Map<String , Double> mapIdNodeInhSpac = morpSignProCor.getMapSPACval(graphStep0, graphStep1, morphogen.inhibitor);
		
		
		
		

	}
	

}
