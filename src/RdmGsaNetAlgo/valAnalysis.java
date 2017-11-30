package RdmGsaNetAlgo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.graphstream.graph.Graph;

import RdmGsaNetExport.expGraph;
import RdmGsaNetExport.expValues;

public class valAnalysis {
	
	public enum analysisType { average , max , min }

	
	public static Map<Double,ArrayList<Double>>  getMapStepValMorp ( String dossierExp , String nameFileExp ,analysisType type ) throws IOException {
		
		Map< Double , ArrayList<Double>> mapStepValMorp = new HashMap< Double , ArrayList < Double >> ( ) ;
		
		// iter dossier
		File path = new File(dossierExp);
		File [] files = path.listFiles();
		
		
		for (int i = 1; i < files.length; i++){       
			if (files[i].isFile()){ //	System.out.println(files[i]);
				String nameFileExpStep = nameFileExp+"_step_"+i;	//	System.out.println(nameFileExpStep)
				
				Graph graph = expGraph.getGRaphDgs(dossierExp, nameFileExpStep);
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
				
				mapStepValMorp.put((double) i, arrayVal) ;  
				}	//	System.out.println(mapStepMeanMorp);
		    }
		return mapStepValMorp;
		}
	


}
