package RdmGsaNet_Analysis_pr02;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.stream.GraphParseException;
import org.graphstream.stream.file.FileSourceFactory;
import org.graphstream.ui.view.Viewer;

import RdmGsaNetExport.handleNameFile;
import RdmGsaNetViz.handleVizStype;
import RdmGsaNetViz.handleVizStype.palette;
import RdmGsaNetViz.handleVizStype.stylesheet;
import scala.collection.parallel.ParIterableLike.Foreach;


public class analysisDGSmultiSim extends analysisMain implements analysisDGS {

	// COSTANTS 
	String analysisId;
	boolean run;

	protected enum layerToAnalyze { gs , net , both }
	protected layerToAnalyze layerToAnalyze ;
	
	protected enum typeIndicator { probability , test }
	protected typeIndicator typeIndicator;
	
	// COSTRUCTOR 
	public analysisDGSmultiSim( String analysisId , boolean run ) {
		this.analysisId = analysisId;
		this.run = run ;	
	}
	
	public void  setParamAnalysisGlobal () { }
	
	public void  setParamAnalysisLocal () { }
	
	public void setWhichGlobalAnalysis ( typeIndicator typeIndicator , layerToAnalyze layerToAnalyze  ) {
		this.typeIndicator = typeIndicator ;  
		this.layerToAnalyze = layerToAnalyze ;	
	}

	public void setWhichLocalAnalysis ( ) {	}
	
	
// --------------------------------------------------------------------------------------------------------------------------------------------------	

	 public void computeGlobalMultiSim (int stepMax, int stepInc , String folderMultiSim  ) throws IOException, InterruptedException {
				
		 if ( run == false ) 
				return ; 
			
		 File path = new File(folderMultiSim );
		 String[] pathStartArr = null , pathStepArr ; 
		 int thread = 1 ;
		
		 File [] files = path.listFiles();							
		
		 ArrayList<File> fileArray = new ArrayList<File>(Arrays.asList(files));
		
//		 fileArray.forEach(  s -> System.out.print("\n" + s.getName()) );

		 for ( File f : fileArray ) {
		
			 String s = f.getAbsolutePath();	 // System.out.println(s);
	
			 String localPathStepGs = handle.getCompletePathInFolder(s+"\\",  "layerGs_step") ;
			 String localPathStepNet = handle.getCompletePathInFolder(s+"\\",  "layerNet_step") ;
			 String localPathStartGs = handle.getCompletePathInFolder(s+"\\",  "layerGs_start") ;
			 String localPathStartNet = handle.getCompletePathInFolder(s+"\\",  "layerNet_start") ;
			 
			 String[] localPathStartArr = {localPathStartGs,localPathStartNet} ;
			 String[] localPathStepArr = {localPathStepGs,localPathStepNet} ;
		
			 computeGlobalStat(stepMax, stepInc, localPathStartArr, localPathStepArr, thread);
		}
	 }
	
	
	
	@Override
	public void computeGlobalStat(int stepMax, int stepInc, String[] pathStartArr, String[] pathStepArr, int thread)
			throws IOException, InterruptedException {

		String pathStartGs = pathStartArr[0];
		String pathStepGs  = pathStepArr[0];

		String pathStartNet = pathStartArr[1];
		String pathStepNet = pathStepArr[1];
		
		// create list of step to create images
		ArrayList<Double> incList = analysisDGS.getListStepToAnalyze(stepInc, stepMax);						//	System.out.println(incList);
		handleVizStype	netViz  = new handleVizStype( netGraph , stylesheet.manual , "seedGrad", 1) ,
				 		gsViz 	= new handleVizStype( gsGraph  , stylesheet.viz10Color , "gsInh", 1) ;
		
		netViz.setupFixScaleManual( true , netGraph , 50 , 0 );
		
		// read start path
		try {	
			gsGraph.read(pathStartGs);
			netGraph.read(pathStartNet);
		} 
		catch (	ElementNotFoundException | GraphParseException |org.graphstream.graph.IdAlreadyInUseException e) 	{	/*e.printStackTrace();*/	}
						
		// set file Source for file step
		gsFs = FileSourceFactory.sourceFor(pathStepGs);
		gsFs.addSink(gsGraph);
				
		netFs = FileSourceFactory.sourceFor(pathStepNet);
		netFs.addSink(netGraph);

		// import file step
		try {
			gsFs.begin(pathStepGs);
			netFs.begin(pathStepNet);
			while ( gsFs.nextStep() && netFs.nextStep()  ) {
						
				double step = gsGraph.getStep();							//	System.out.println(step);
						
				if ( incList.contains(step)) {
					// add methods to run for each step in incList
					System.out.println("----------------step " + step + " ----------------" );				
					
					Thread.sleep(thread);
					
					// stop iteration    
					if ( stepMax == step ) { break; }
				}
			}
		} catch (IOException e) {		}				
		gsFs.end() ;	
		netFs.end() ;	
	}

	// not simple to implement
	public void computeLocalStat(int stepMax, int stepInc, String[] pathStartArr, String[] pathStepArr, int thread)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
	}
	private static void setVal ( File i , String[] pathStartArr ) {
		String s = i.toString() ;
	//	pathStartArr[i.toString()];
	}

}
