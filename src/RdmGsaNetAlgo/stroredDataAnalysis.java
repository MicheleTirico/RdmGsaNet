package RdmGsaNetAlgo;

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

import RdmGsaNetExport.expGraph;
import RdmGsaNetExport.expValues;

public class stroredDataAnalysis {
	
	public enum analysisType { average , max , min }
	
	public static Map<Integer,ArrayList<Double>>  getMapStepValMorp ( String folderExp , String nameFileExp , analysisType type ) throws IOException {
		
		Map< Integer , ArrayList<Double>> mapStepValMorp = new HashMap< Integer , ArrayList < Double >> ( ) ;
		
		// iter dossier
		File path = new File(folderExp);
		File [] files = path.listFiles();	
		
		for (int i = 1; i < files.length; i++){       
			if (files[i].isFile()){ //	System.out.println(files[i]);
				String nameFileExpStep = nameFileExp+"_step_"+i;	//	System.out.println(nameFileExpStep)
				
				Graph graph = expGraph.getGRaphDgs(folderExp, nameFileExpStep);
				Map<String, ArrayList<Double>> mapIdMorp = expValues.getMapIdGsMorp(graph);    
				
				ArrayList<Double> arrAct = new ArrayList<Double>();
				ArrayList<Double> arrInh = new ArrayList<Double>();

				for (Entry<String, ArrayList<Double>> entry : mapIdMorp.entrySet()) {
					
					ArrayList<Double> arrMorp = new ArrayList<Double>();
					arrMorp = entry.getValue();		//	System.out.println(arrMorp);
					
					double act = arrMorp.get(0);
					double inh = arrMorp.get(1);
					
					arrAct.add(act);
					arrInh.add(inh);
				}		//				System.out.println(arrAct);
				
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
					
				ArrayList <Double > arrayVal = new ArrayList<Double>() ;
				arrayVal.add(actVal);
				arrayVal.add(inhVal);
				
				mapStepValMorp.put( i, arrayVal) ;  
				}	//	System.out.println(mapStepValMorp);
		    }
		return mapStepValMorp;
	}
	
	public static Map<String, ArrayList<Double>> getMapIdSPAC ( String folderExp , String nameFileExp , int step ) throws IOException {
			
		File path = new File(folderExp);
		File [] files = path.listFiles();	
		double maxStep = files.length;
		int x  = 1;
		
		if ( step <= 1) {	System.out.println("can not compute SPAC at step <= 1 ");
							java.lang.System.exit(1) ;	
		}
		if ( step > maxStep ) {	System.out.println("can not compute SPAC at step > " + maxStep );
								java.lang.System.exit(1) ;
		}	
		
		Map< String , ArrayList<Double>> mapIdSPAC = new HashMap< String , ArrayList < Double >> ( ) ;
	
		String nameFileExpStep0 = nameFileExp+"_step_"+(step-1) ;		//	System.out.println(nameFileExpStep)
		String nameFileExpStep1 = nameFileExp+"_step_"+step 	;		//	System.out.println(nameFileExpStep)
					
		Graph graph0 = expGraph.getGRaphDgs(folderExp, nameFileExpStep0);
		Graph graph1 = expGraph.getGRaphDgs(folderExp, nameFileExpStep0);
				
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
		return mapIdSPAC ;
		}
	
	public static Map< Integer, ArrayList< Double >> getMapStepSPACstat ( String folderExp , String nameFileExp , analysisType type ) throws IOException {
		
		Map< Integer, ArrayList< Double >>  mapStepSPACstat = new HashMap < Integer, ArrayList< Double >> () ;
		
			for ( int i = 2 ; i < 4; i++ ) {
				Map<String, ArrayList<Double>>  mapIdSpac = getMapIdSPAC( folderExp, nameFileExp, i );
			 
				ArrayList< Double > arrMorp = new ArrayList<Double>();
				
				for (Entry<String, ArrayList<Double>> entry : mapIdSpac.entrySet()) {
					arrMorp.addAll(entry.getValue());
					
					
					
					
				}
				System.out.println();
			
		}
		
		return mapStepSPACstat ;
	}
	
	
	
}
		



