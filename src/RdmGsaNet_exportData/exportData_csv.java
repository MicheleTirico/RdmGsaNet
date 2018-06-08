package RdmGsaNet_exportData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Edge;
import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.GraphParseException;
import org.graphstream.stream.file.FileSourceFactory;


import RdmGsaNetAlgo.graphAnalysis;
import RdmGsaNetAlgo.graphIndicators;
import RdmGsaNetAlgo.graphToolkit;
import RdmGsaNetExport.expCsv;
import RdmGsaNetExport.expGraph;
import RdmGsaNetExport.handleNameFile;
import RdmGsaNet_Analysis_02.analysisDGS;
import RdmGsaNet_Analysis_02.analysisMultiSim;
import RdmGsaNet_exportData.exportData_csv.layerToAnalyze;
import RdmGsaNet_exportData.exportData_image_01.layer;
import RdmGsaNet_staticBuckets_03.bucketSet;

public class exportData_csv extends exportData_main {
	
	static String 	pathStart = pathStartNet ,	 
					pathStep = pathStepNet ; 
	
	protected enum typeSimpleIndicator {	density  ,	averageDegree , diameter , averageShortpath , gammaIndex , alfaIndex , organicRatio , averageClustering , totNodes, totEdges ,
											buchetActiveCount , vectorActiveCount }	
	protected static typeSimpleIndicator typeIndicator ;	
	
	protected enum typeMultiLineIndicator {	degreeDistribution , normalDegreeDistribution }	
	protected static typeMultiLineIndicator typeMultiLineIndicator ;	
	

	protected enum typeMultiLayerIndicator { correlationVectorNode   }	
	protected static typeMultiLayerIndicator typeMultiLayerIndicator;	
	
	protected enum layerToAnalyze { vecGraph, netGraph , seedGraph , gsGraph }
	protected static layerToAnalyze layerToAnalyze ;
	
	protected static Graph graph , graph1 , graph2 ;								
	
	public static void computeMultiLineIndicator (  boolean run , int 	stepInc , int stepMax , typeMultiLineIndicator typeIndicator , String pathToStore , String pathDataMain ,
			int numLine ) throws IOException {
		
		if ( !run)
			return ;
		
		graph = new SingleGraph ("graph") ;
		 
		String 	header =  "step" ,
				nameFile = "multiLine_" + typeIndicator.toString() ;
		
		for ( int x = 1 ; x <= numLine ; x++ )
			header = header +";" + x ;
			
		handleNameFile.createNewGenericFolder(pathDataMain , "analysis" );
		pathToStore = pathDataMain + "\\analysis\\"  ;
		
		FileWriter fileWriter = new FileWriter( pathToStore + nameFile + ".csv" , true );		
		expCsv.addCsv_header( fileWriter, header ) ;
		
		// create list of step to create images
		ArrayList<Double> incList = analysisDGS.getListStepToAnalyze(stepInc, stepMax);						//	System.out.println(incList);
				
		// set file Source for file step
		fs = FileSourceFactory.sourceFor(pathStep);
		fs.addSink(graph);
		
		// import start graph
		try 														{	graph.read(pathStart);		} 
		catch (	ElementNotFoundException | 
				GraphParseException | 
				org.graphstream.graph.IdAlreadyInUseException e) 	{	/*e.printStackTrace();*/	}
		 
		// set file Source for file step
		fs = FileSourceFactory.sourceFor(pathStep);
		fs.addSink(graph);
		
		// import file step
		try {
			fs.begin(pathStep);
			while ( fs.nextStep()) {
				double step = graph.getStep();							//	System.out.println(step);
				if ( incList.contains(step)) {
					// add methods to run for each step in incList				
					System.out.println("----------------step " + step + " ----------------" );					
					
					double val = 0 ;
					ArrayList<String> listVal = new ArrayList<String>(Arrays.asList( Double.toString(step) ) ) ;
					switch (typeIndicator) { 
				
					case degreeDistribution : {
						Map<Double , Double> map = new TreeMap<Double , Double>(graphAnalysis.getMapFrequencyDegree(graph, numLine, false) ) ;
					//	Map<Double , Double> map = new TreeMap<Double , Double>(graphAnalysis.getMapFrequencyDegree_02(graph, numLine, false) ) ;
						
					//	System.out.println(map);
						
						for ( double d : map.keySet() )
							listVal.add(Double.toString(map.get(d))) ;
						
					} break ; 
					
					case normalDegreeDistribution : {
						Map<Double , Double> map = new TreeMap<Double , Double>(graphAnalysis.getMapFrequencyDegree(graph, numLine, true) ) ;
						
						for ( double d : map.keySet() )
							listVal.add(Double.toString(map.get(d))) ;
					}break ;
								 
						
					}
					if (listVal.size() <= numLine ) 
						while(listVal.size() <= numLine )
							listVal.add("0.0");
					
				//	System.out.println(listVal);
					expCsv.writeLine(fileWriter, listVal , ';' ) ;
				
					// stop iteration    			
					if ( stepMax == step )  
						break; 
				}			
			}
	
		} catch (IOException e) {		}				
	
		fs.end();	
		fileWriter.close();
	}
		
	public static void computeSimpleIndicator ( boolean run , int 	stepInc , int stepMax , typeSimpleIndicator typeIndicator , String pathToStore , String pathDataMain ,
			layerToAnalyze layer ) throws IOException {
		
		if ( !run)
			return ;
		
		String[] path = getPath(pathDataMain, layer );
		
		pathStep = path[1] ;
		pathStart = path[0] ;
		
		graph = new SingleGraph ("graph") ;
		 
		String 	header = "step;" + typeIndicator.toString() ,
				nameFile = "simpleInd_" + typeIndicator.toString() ;
		
		handleNameFile.createNewGenericFolder(pathDataMain , "analysis" );
		pathToStore = pathDataMain + "\\analysis\\"  ;
		
		FileWriter fileWriter = new FileWriter( pathToStore + nameFile + ".csv" , true );		
		expCsv.addCsv_header( fileWriter, header ) ;
		
		// create list of step to create images
		ArrayList<Double> incList = analysisDGS.getListStepToAnalyze(stepInc, stepMax);						//	System.out.println(incList);
			
		// set file Source for file step
		fs = FileSourceFactory.sourceFor(pathStep);
		fs.addSink(graph);
		
		// import start graph
		try 														{	graph.read(pathStart);		} 
		catch (	ElementNotFoundException | 
				GraphParseException | 
				org.graphstream.graph.IdAlreadyInUseException e) 	{	/*e.printStackTrace();*/	}
		
		// set file Source for file step
		fs = FileSourceFactory.sourceFor(pathStep);
		fs.addSink(graph);
		
		// import file step
		try {
			fs.begin(pathStep);
			while ( fs.nextStep()) {
				double step = graph.getStep();							//	System.out.println(step);
				if ( incList.contains(step)) {
					// add methods to run for each step in incList				
					System.out.println("----------------step " + step + " ----------------" );					
					
					double val = getValIndicator ( graph , typeIndicator) ;
					
					expCsv.writeLine(fileWriter, Arrays.asList( Double.toString(step) , Double.toString(val) ) , ';' ) ;
				
					// stop iteration    			
					if ( stepMax == step )  
						break; 
				}			
			}
	
		} catch (IOException e) {		}				
	
		fs.end();	
		fileWriter.close();
	}
	
	public static void computeMultiSim ( boolean run , int stepInc , int stepMax , typeSimpleIndicator typeIndicator , String pathToStore , String pathDataMain , 
			layerToAnalyze layer ) throws IOException {
		
		if ( run == false )
			return ;	
		
		String 	header = "step" ,
				nameFile = "multiSim_" + typeIndicator.toString() ;
		
		File extF  ; 
		File path = new File(folderMain) ;
		File [] files = path.listFiles();									
		ArrayList<File> fileArray = new ArrayList<File>(Arrays.asList(files));

		 Map <Double , ArrayList <Double>> mapToStore = new TreeMap < Double , ArrayList <Double>>();
		
		 for ( File f : fileArray ) {
			 
			 extF = f ;			 
			 String s = f.getAbsolutePath();	 // 	 
	
			 System.out.println(f);
			 String pathStep = null, pathStart = null  ;
					 
			 switch (layer) {
				case netGraph: {
					 pathStep = handleNameFile.getCompletePathInFolder(s+"\\",  "layerNet_step") ; 
					 pathStart = handleNameFile.getCompletePathInFolder(folderCommonFiles,  "layerNet_start") ;
				} break;
				
				case vecGraph : {
					 pathStep = handleNameFile.getCompletePathInFolder(folderCommonFiles,  "layerVec_step") ; 
					 pathStart = handleNameFile.getCompletePathInFolder(folderCommonFiles,  "layerVec_start") ;
				} break ;
				
				case gsGraph : {
					 pathStep = handleNameFile.getCompletePathInFolder(folderCommonFiles,  "layerGs_step") ; 
					 pathStart = handleNameFile.getCompletePathInFolder(folderCommonFiles,  "layerGs_start") ;
				} break ;
				
				case seedGraph : {
					pathStep = handleNameFile.getCompletePathInFolder(s+"\\",  "layerSeed_step") ; 
					pathStart = handleNameFile.getCompletePathInFolder(folderCommonFiles,  "layerSeed_start") ;	
				} break ;
			}
			 	 
			 handleNameFile.createNewGenericFolder(folderMain , "multiSimAnalysis" );
			 
			 graph = new SingleGraph ("graph") ; 	
			 
			// create list of step to create images
			ArrayList<Double> incList = analysisDGS.getListStepToAnalyze(stepInc, stepMax);						//	System.out.println(incList);
					
			// set file Source for file step
			try {
				fs = FileSourceFactory.sourceFor(pathStep);
				fs.addSink(graph);
			} catch ( java.lang.NullPointerException e) { // e.printStackTrace();	
				continue ;
			}
			
			// import start graph
			try 														{	graph.read(pathStart);		} 
			catch (	ElementNotFoundException | 
					GraphParseException | 
					org.graphstream.graph.IdAlreadyInUseException e) 	{	/*e.printStackTrace();*/	}
			
			// set file Source for file step
			fs = FileSourceFactory.sourceFor(pathStep);
			fs.addSink(graph);
			
			// import file step			
			try {
				fs.begin(pathStep);
				while ( fs.nextStep()) {
					double step = graph.getStep();							//	System.out.println(step);
					if ( incList.contains(step)) {
						// add methods to run for each step in incList				
						System.out.println("----------------step " + step + " ----------------" );					
						
						double val = getValIndicator ( graph , typeIndicator) ;				//	System.out.println(val);		//	System.out.println(mapToStore.get(step));
						ArrayList <Double> arr = new ArrayList <Double> () ;
						
						if ( mapToStore.get(step) != null )
							arr = mapToStore.get(step) ;
						
						arr.add(val);
						mapToStore.put(step, arr) ;
						
											
						// stop iteration    			
						if ( stepMax == step )  
							break; 
					}			
				}
		
			} catch (IOException e) {		}				
			fs.end();			
		 }
		
		 String pathFile = folderMain + "\\multiSimAnalysis\\"  + nameFile + ".csv" ; 
		 FileWriter fileWriter = new FileWriter( pathFile , true );		
		
		 for ( int i = 1 ; i < fileArray.size() - 1 ; i++ )
			 header = header + ";" + i ;

		 expCsv.addCsv_header( fileWriter, header ) ;
		 
		 for ( Double step : mapToStore.keySet()) {		//	 System.out.println(mapToStore);

			ArrayList<String> line = new ArrayList<String> (Arrays.asList(Double.toString(step)));
			mapToStore.get(step).forEach( (val) -> line.add(Double.toString(val)) ) ;		//	System.out.println(line);
			expCsv.writeLine(fileWriter, line , ';' ) ;	
		 }		
		 
		 fileWriter.close();		
	}

	public static void computeMultiLayerIndicators ( boolean run , int stepInc , int stepMax , typeMultiLayerIndicator typeIndicator , 
			String pathToStore , String pathDataMain , layerToAnalyze layer1 , layerToAnalyze layer2 ) throws IOException {
		
		if ( run == false )
			return ;	
		
		String[] path1 = getPath(pathDataMain, layer1);	//	System.out.println(path1[0] + "\n" + path1[1]);		
		String[] path2 = getPath(pathDataMain, layer2);	//	System.out.println(path2[0] + "\n" + path2[1]);
		
		graph1 = new SingleGraph ("graph1") ;
		graph2 = new SingleGraph ("graph2") ;
		
		// create list of step to create images
		ArrayList<Double> incList = analysisDGS.getListStepToAnalyze(stepInc, stepMax);						//	System.out.println(incList);
				
		// set file Source for file step
		fs1 = FileSourceFactory.sourceFor(path1[1]);
		fs1.addSink(graph1);
		fs2 = FileSourceFactory.sourceFor(path2[1]);
		fs2.addSink(graph2);
		
		// import start graph
		try 														{	graph1.read(path1[0]);	graph2.read(path2[0]);		} 
		catch (	ElementNotFoundException | 
				GraphParseException | 
				org.graphstream.graph.IdAlreadyInUseException e) 	{	/*e.printStackTrace();*/	}
		 
		// set file Source for file step
		fs1 = FileSourceFactory.sourceFor(path1[1]);
		fs1.addSink(graph1);
		
		fs2 = FileSourceFactory.sourceFor(path2[1]);
		fs2.addSink(graph2);
		
		// import file step
		try {
			fs1.begin(path1[1]);
			fs2.begin(path2[1]);
			while ( fs1.nextStep() && fs2.nextStep()) {
				double step = graph1.getStep();							//	System.out.println(step);
				if ( incList.contains(step)) {
					// add methods to run for each step in incList				
					System.out.println("----------------step " + step + " ----------------" );					
					
					double val = 0 ;
					ArrayList<String> listVal = new ArrayList<String>(Arrays.asList( Double.toString(step) ) ) ;
					
					double buchetAct = getValIndicator(graph1, typeSimpleIndicator.buchetActiveCount) ;
					double vectorAct = getValIndicator(graph2, typeSimpleIndicator.vectorActiveCount) ;
					
				//	System.out.println(buchetAct +" "+ vectorAct);
					// stop iteration    			
					if ( stepMax == step )  
						break; 
				}			
			}
	
		} catch (IOException e) {		}		
		
		graph2.display(false) ;
		fs1.end();	
		fs2.end();	
	}	
	
// private methods ----------------------------------------------------------------------------------------------------------------------------------	
	private static String[] getPath ( String pathDataMain , layerToAnalyze layer ) {
		
		String[] path = new String[2] ;
			
		switch (layer) {
			case gsGraph: {
				path[0] = pathStartGs ;
				path[1] = pathStepGs ;
			}break;
			
			case netGraph: {
				path[0] = pathStartNet ;
				path[1] = pathStepNet ;
			}break;
			
			case seedGraph: {
				path[0] = pathStartSeed ;
				path[1] = pathStepSeed ;
			}break;
			
			case vecGraph: {
				path[0] = pathStartVec ;
				path[1] = pathStepVec ;
			}break;
		}	
		return path;
	}
			
	private static double getValMultiLayer ( Graph graph1 , Graph graph2 , typeMultiLayerIndicator typeIndicator ) {
		double val = 0 ;
		switch (typeIndicator) {
		case correlationVectorNode : {
			
		}
		
		}
		return val ; 
	}

	private static double getValIndicator ( Graph graph , typeSimpleIndicator typeIndicator   ) {
		double val = 0 ;
		
		switch (typeIndicator) {
		
			case density: 
				val = Toolkit.density(graph);									
				break;
				
			case averageDegree : 
				val = Toolkit.averageDegree(graph);
				break ;
			
			case diameter : 
				val = Toolkit.diameter(graph);
				break ;
			
			case alfaIndex :
				val = graphIndicators.getAlfaIndex(graph);
				break ;
				
			case gammaIndex :
				val = graphIndicators.getGammaIndex(graph, true) ;
				break;
				
			case organicRatio :
				val = graphIndicators.getOrganicRatio(graph) ;
				break ;
				
			case averageShortpath :
				break;
		
			case averageClustering :
				val = Toolkit.averageClusteringCoefficient(graph);
				break ;			
				
			case totEdges :
				val = graph.getEdgeCount() ;
				break ;
				
			case totNodes :
				val = graph.getNodeCount() ;
				break ;
			
			case buchetActiveCount : {	
				bucketSet bs = new bucketSet(true, graph, 50, 50, 50, 50);
				bs.createBuketSet();
				val = bs.getBucketsCount() ;
				bs.removeAllBuchets(bs);	//		System.out.println(val);
			} break ;
			
			case vectorActiveCount : {
				int vectocActCount = 0 ;				
				for ( Node n : graph.getEachNode() ) 
					if ( (double) n.getAttribute("inten") > 0.0000000001 ) 
						vectocActCount++ ;			
				val = vectocActCount / 2 ;	//				
			} break ;
		}
		
		return val ;
	}
	
}
 