package RdmGsaNet_Analysis_pr02;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import scala.collection.parallel.ParIterableLike.Foreach;


public class analysisDGSmultiSim extends analysisMain implements analysisDGS {

	// COSTANTS 

	
	
	// COSTRUCTOR 
	public analysisDGSmultiSim() {
		// TODO Auto-generated constructor stub
	}
	
	public void  setParamAnalysisGlobal () {}
	
	public void  setParamAnalysisLocal () {}
	
	public void setWhichGlobalAnalysis ( ) {	}
	public void setWhichLocalAnalysis ( ) {	}
	
	
// --------------------------------------------------------------------------------------------------------------------------------------------------	

	 public void computeGlobalMultiSim (int stepMax, int stepInc , String folderMultiSim  ) {
		 /*
		 File path = new File(folderMultiSim );
		 String[] pathStartArr = null , pathStepArr ; 
		 int thread = 1 ;
		
		 File [] files = path.listFiles();							//   System.out.println(files[1]);
		
		 ArrayList<File> fileArray = new ArrayList<File>(Arrays.asList(files));
		 
		 fileArray.forEach(  s -> System.out.print("\n" + s) );

		 for (int i = 0; i < files.length; i++){					//	System.out.println(files[i]);
		
		   }
	 */

	 String test = getlayerGsStartInFolder( folder) ;
	 System.out.println(test);

	 
	 
	 
	 
	 }
	
	
	
	@Override
	public void computeGlobalStat(int stepMax, int stepInc, String[] pathStartArr, String[] pathStepArr, int thread)
			throws IOException, InterruptedException {
		
		
		
		
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
