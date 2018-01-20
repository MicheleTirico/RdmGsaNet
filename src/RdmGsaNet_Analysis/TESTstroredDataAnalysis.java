package RdmGsaNet_Analysis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import RdmGsaNetAlgo.graphAnalysis;
import RdmGsaNetAlgo.morpSignProCor;
import RdmGsaNetAlgo.graphAnalysis.morphogen;
import RdmGsaNetExport.expGraph;
import RdmGsaNetExport.expValues;

/* storedDataAnalysis
 * class to 
 * 
 */
public class TESTstroredDataAnalysis {
	
	public enum analysisType { average , max , min }
	
	private static String folderMap = "D:\\Dropbox\\Dropbox\\JAVA\\RdmGsaNet_Export\\values\\";
	
	private static String nameMapStepValMorp = "nameMapStepValMorp";
	private static String nameMapIdSPACfolder = "nameMapStepValMorp";
	private static String nameMapIdSPACassfolder = "nameMapStepValMorp";
	private static String nameMapStepSPACAverage = "nameMapStepValMorp";
	private static String nameMapStepSPACAssAverage = "nameMapStepValMorp";
	
	public static void storedMapStepValMorp (	String folderGr , String nameFileGr , String folderMap , 
												String nameMapAve , String nameMapMax, String nameMapMin,  
												Boolean createMapMax , Boolean createMapMin, Boolean createMapAve ) throws IOException {
		
		Map< Integer , ArrayList<Double>> mapStepValMorpAve = new HashMap< Integer , ArrayList < Double >> ( ) ;
		Map< Integer , ArrayList<Double>> mapStepValMorpMax = new HashMap< Integer , ArrayList < Double >> ( ) ;
		Map< Integer , ArrayList<Double>> mapStepValMorpMin = new HashMap< Integer , ArrayList < Double >> ( ) ;
		 
		// iter dossier
		File path = new File(folderGr);
		File [] files = path.listFiles();	
		
		for (int i = 1; i < files.length; i++){       
			if (files[i].isFile()){ 												//	System.out.println(files[i]);
				String nameFileExpStep = nameFileGr+"_step_"+i;						//	System.out.println(nameFileExpStep)
				
				Graph graph = expGraph.getGraphDgs(folderGr, nameFileExpStep);
				Map<String, ArrayList<Double>> mapIdMorp = expValues.getMapIdGsMorp(graph);    
				
				ArrayList<Double> arrAct = new ArrayList<Double>();
				ArrayList<Double> arrInh = new ArrayList<Double>();

				for (Entry<String, ArrayList<Double>> entry : mapIdMorp.entrySet()) {
					
					ArrayList<Double> arrMorp = new ArrayList<Double>();
					arrMorp = entry.getValue();										//	System.out.println(arrMorp);
					
					double act = arrMorp.get(0);
					double inh = arrMorp.get(1);
					
					arrAct.add(act);
					arrInh.add(inh);
				}																	//	System.out.println(arrAct);
				
				double 	actVal = 0, 
						inhVal = 0 ;
				
				// set average val
				actVal = arrAct.stream().mapToDouble(val -> val).average().getAsDouble();
				inhVal = arrInh.stream().mapToDouble(val -> val).average().getAsDouble();

				mapStepValMorpAve.put( i, new ArrayList<Double>(Arrays.asList( actVal , inhVal ) ) ) ;
				expValues.writeMap(true, mapStepValMorpAve, folderMap, nameMapAve); System.out.println("mapAve stored");
				
				// set max val
				actVal = arrAct.stream().mapToDouble(val -> val).max().getAsDouble();
				inhVal = arrInh.stream().mapToDouble(val -> val).max().getAsDouble();

				mapStepValMorpMax.put( i, new ArrayList<Double>(Arrays.asList( actVal , inhVal ) ) ) ;
				expValues.writeMap(true, mapStepValMorpMax, folderMap, nameMapMax); System.out.println("mapMax stored");
				
				// set min val
				actVal = arrAct.stream().mapToDouble(val -> val).min().getAsDouble();
				inhVal = arrInh.stream().mapToDouble(val -> val).min().getAsDouble();

				mapStepValMorpMin.put( i, new ArrayList<Double>(Arrays.asList( actVal , inhVal ) ) ) ;
				expValues.writeMap(true, mapStepValMorpMin, folderMap, nameMapMin); System.out.println("mapMin stored");
			}
		}			
	}
	
	public static Map<Integer,ArrayList<Double>>  getMapStepValMorp ( Boolean storedMap, String folderExp , String nameFileExp , analysisType type ) throws IOException {
		
		Map< Integer , ArrayList<Double>> mapStepValMorp = new HashMap< Integer , ArrayList < Double >> ( ) ;
		 
		// iter dossier
		File path = new File(folderExp);
		File [] files = path.listFiles();	
		
		for (int i = 1; i < files.length; i++){       
			if (files[i].isFile()){ //	System.out.println(files[i]);
				String nameFileExpStep = nameFileExp+"_step_"+ i;							//	System.out.println(nameFileExpStep)
				
				Graph graph = expGraph.getGraphDgs(folderExp, nameFileExpStep);
				Map<String, ArrayList<Double>> mapIdMorp = expValues.getMapIdGsMorp(graph);    
				
				ArrayList<Double> arrAct = new ArrayList<Double>();
				ArrayList<Double> arrInh = new ArrayList<Double>();

				for (Entry<String, ArrayList<Double>> entry : mapIdMorp.entrySet()) {
					
					ArrayList<Double> arrMorp = new ArrayList<Double>();
					arrMorp = entry.getValue();												//	System.out.println(arrMorp);
					
					double act = arrMorp.get(0);
					double inh = arrMorp.get(1);
					
					arrAct.add(act);
					arrInh.add(inh);
				}																			//	System.out.println(arrAct);
				
				double 	actVal = 0, 
						inhVal = 0 ;
				
				switch (type) {
					case average: {
						actVal = arrAct.stream().mapToDouble(val -> val).average().getAsDouble();
						inhVal = arrInh.stream().mapToDouble(val -> val).average().getAsDouble();
						break;
					}
					case max : {
						actVal = arrAct.stream().mapToDouble(val -> val).max().getAsDouble();
						inhVal = arrInh.stream().mapToDouble(val -> val).max().getAsDouble();
						break;
					}
					case min : {
						actVal = arrAct.stream().mapToDouble(val -> val).min().getAsDouble();
						inhVal = arrInh.stream().mapToDouble(val -> val).min().getAsDouble();
						break;
					}
				}
					
				mapStepValMorp.put( i, new ArrayList<Double>(Arrays.asList( actVal , inhVal ) )) ;  
				}	//	System.out.println(mapStepValMorp);
		    }
		
		if ( storedMap == true ) { expValues.writeMap(true, mapStepValMorp, folderMap, nameMapStepValMorp); }
		return mapStepValMorp;
	}
	
	// return map for a defined step.
	// SPAC = Signal Processing Auto Correlation = act1  * act0
	// map / key = (string) idGs , values, (list of double) [ SPACactivator, SPACinhibiter ] 
	public static Map<String, ArrayList<Double>> getMapIdSPACfolder ( Boolean storedMap,  String folderExp , String nameFileExp , int step ) throws IOException {
			
		File path = new File(folderExp);
		File [] files = path.listFiles();	
		double maxStep = files.length;
		
		if ( step <= 1) {	System.out.println("can not compute SPAC at step <= 1 ");
							java.lang.System.exit(1) ;	
		} 
//		if ( step > maxStep ) {	System.out.println("can not compute SPAC at step > " + maxStep );
//								java.lang.System.exit(1) ;		}
			
		
		Map< String , ArrayList<Double>> mapIdSPAC = new HashMap< String , ArrayList < Double >> ( ) ;
	
		String nameFileExpStep0 = nameFileExp+"_step_"+(step-1) ;		//	System.out.println(nameFileExpStep)
		String nameFileExpStep1 = nameFileExp+"_step_"+step 	;		//	System.out.println(nameFileExpStep)
					
		Graph graph0 = expGraph.getGraphDgs(folderExp, nameFileExpStep0);
		Graph graph1 = expGraph.getGraphDgs(folderExp, nameFileExpStep1);
		
		Map<String, Double> mapIdSPACact = morpSignProCor.getMapIdSPACval(graph0, graph1,  graphAnalysis.morphogen.activator ) ;
		Map<String, Double> mapIdSPACinh = morpSignProCor.getMapIdSPACval(graph0, graph1,  graphAnalysis.morphogen.inhibitor ) ;
		
		for ( Node n1 : graph1.getEachNode()) {
			
			String key = n1.getId();
		
			mapIdSPAC.put(key, new ArrayList<Double>(Arrays.asList( mapIdSPACact.get(key) , mapIdSPACinh.get(key))));
		
		}
		/*
			for ( Node n1 : graph1.getEachNode()) {
			double act1 = n1.getAttribute("gsAct") ;
			double inh1 = n1.getAttribute("gsInh") ;
					
			Node n0 = graph0.getNode(n1.getId());
			double act0 = n0.getAttribute("gsAct");
			double inh0 = n0.getAttribute("gsInh");
				
			double SPACact = act1 * act0 ;
			double SPACinh = inh1 * inh0 ;
					
			n1.setAttribute("SPACact", SPACact);
			n1.setAttribute("SPACinh", SPACinh);
					
			String key = n1.getId();
			ArrayList<Double> arrSPAC = new ArrayList<Double> () ;
			arrSPAC.add(SPACact);
			arrSPAC.add(SPACinh);
					
			mapIdSPAC.put(key, arrSPAC);
			}
		
		*/
		if ( storedMap == true ) { expValues.writeMap(true, mapIdSPAC, folderMap, nameMapIdSPACfolder); }
		return mapIdSPAC ;
		}

	public static Map<String, ArrayList<Double>> getMapIdSPACassfolder ( Boolean storedMap,  String folderExp , String nameFileExp , int step ) throws IOException {
		
		File path = new File(folderExp);
		File [] files = path.listFiles();	
		double maxStep = files.length;
		
		if ( step <= 2) {	System.out.println("can not compute SPAC at step <= 2 ");
							java.lang.System.exit(1) ;	
		} 
//		if ( step > maxStep ) {	System.out.println("can not compute SPAC at step > " + maxStep );
//								java.lang.System.exit(1) ;		}
			
		
		Map< String , ArrayList<Double>> mapIdSPACass = new HashMap< String , ArrayList < Double >> ( ) ;
	
		String nameFileExpStep2 = nameFileExp+"_step_"+(step-2) ;		//	System.out.println(nameFileExpStep)
		String nameFileExpStep1 = nameFileExp+"_step_"+(step-1) ;		//	System.out.println(nameFileExpStep)
		String nameFileExpStep0 = nameFileExp+"_step_"+step 	;		//	System.out.println(nameFileExpStep)
		
		Graph graph2 = expGraph.getGraphDgs(folderExp, nameFileExpStep2);
		Graph graph1 = expGraph.getGraphDgs(folderExp, nameFileExpStep1);
		Graph graph0 = expGraph.getGraphDgs(folderExp, nameFileExpStep0);
		
		Map<String, Double> mapIdSPACassAct = morpSignProCor.getMapSPACassVal(graph0, graph1, graph2,  graphAnalysis.morphogen.activator);
		Map<String, Double> mapIdSPACassInh = morpSignProCor.getMapSPACassVal(graph0, graph1, graph2,  graphAnalysis.morphogen.inhibitor ) ;
		
		
		for ( Node n1 : graph1.getEachNode()) {
			
			String key = n1.getId();
			mapIdSPACass.put(key, new ArrayList<Double>(Arrays.asList( mapIdSPACassAct.get(key) , mapIdSPACassInh.get(key)))) ;
		}	
		
		if ( storedMap == true ) { expValues.writeMap(true, mapIdSPACass, folderMap, nameMapIdSPACassfolder); }
		return mapIdSPACass ;	
	}
	
	public static Map< Integer, ArrayList< Double >> getMapStepSPACAverage ( Boolean storedMap, String folderExp , String nameFileExp , int maxStep) throws IOException {
		
		Map< Integer, ArrayList< Double >>  mapStepSPACAverage = new HashMap < Integer, ArrayList< Double >> () ;
		
		for ( int step = 2 ; step <= maxStep ; step++) {
			
			double sumStepSPACact = 0 ;
			double sumStepSPACinh = 0 ;
			
			Map<String, ArrayList<Double>> mapIdSPAC = getMapIdSPACfolder(false , folderExp, nameFileExp, step ) ;
			
			int keyCount = mapIdSPAC.size() ;
			
			double sumSPACact = 0 ;
			double sumSPACinh = 0 ;
			
			for ( Entry <String , ArrayList<Double>> entry : mapIdSPAC.entrySet()) {
				
				ArrayList<Double> arrMorp = new ArrayList<Double>() ;
				arrMorp = entry.getValue() ;
				
				sumSPACact = sumSPACact + arrMorp.get(0) ;			
				sumSPACinh = sumSPACinh + arrMorp.get(1) ;		
				}
			
			sumStepSPACact = sumStepSPACact + ( sumSPACact / keyCount) ;
			sumStepSPACinh = sumStepSPACinh + ( sumSPACinh / keyCount) ;
			
			ArrayList<Double> arrSpac = new ArrayList<Double>();
			arrSpac.add(sumStepSPACact);
			arrSpac.add(sumStepSPACinh);
			
			mapStepSPACAverage.put(step, arrSpac ) ;
		}
		if ( storedMap == true ) { expValues.writeMap(true, mapStepSPACAverage, folderMap, nameMapStepSPACAverage); }
		return mapStepSPACAverage ;
	}
	
	public static Map< Integer, ArrayList< Double >> getMapStepSPACAssAverage ( Boolean storedMap, String folderExp , String nameFileExp , int maxStep) throws IOException {
		
		Map< Integer, ArrayList< Double >>  mapStepSPACAssAverage = new HashMap < Integer, ArrayList< Double >> () ;
		
		for ( int step = 3 ; step <= maxStep ; step++) {
			
			double sumStepSPACassAct = 0 ;
			double sumStepSPACassInh = 0 ;
			
			Map<String, ArrayList<Double>> mapIdSPAC = getMapIdSPACassfolder(false, folderExp, nameFileExp, step ) ;
			
			int keyCount = mapIdSPAC.size() ;
			
			double sumSPACact = 0 ;
			double sumSPACinh = 0 ;
			
			for ( Entry <String , ArrayList<Double>> entry : mapIdSPAC.entrySet()) {
				
				ArrayList<Double> arrMorp = new ArrayList<Double>() ;
				arrMorp = entry.getValue() ;
				
				sumSPACact = sumSPACact + arrMorp.get(0) ;			
				sumSPACinh = sumSPACinh + arrMorp.get(1) ;		
				}
			
			sumStepSPACassAct = sumStepSPACassAct + ( sumSPACact / keyCount) ;
			sumStepSPACassInh = sumStepSPACassInh + ( sumSPACinh / keyCount) ;
			
			ArrayList<Double> arrSpac = new ArrayList<Double>();
			arrSpac.add(sumStepSPACassAct);
			arrSpac.add(sumStepSPACassInh);
			
			mapStepSPACAssAverage.put(step, arrSpac ) ;
		}
		if ( storedMap == true ) { expValues.writeMap(true, mapStepSPACAssAverage, folderMap, nameMapStepSPACAssAverage); }
		return mapStepSPACAssAverage ;
	}

}
		



