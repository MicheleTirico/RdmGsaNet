package RdmGsaNet_dataAnalysis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.orsoncharts.graphics3d.swing.ExportToSVGAction;

import RdmGsaNetExport.expValues;

public class dataAnalysisGlobal_02 extends dataAnalysisMain {
	
	protected static boolean run ;
	private double  prob = 0.0 ;
		
	
	public dataAnalysisGlobal_02( boolean run ) {
		this.run = run ;
	}

	public Map getMapComputed ( String folder ) throws ClassNotFoundException, IOException {
		
		Map toReturn = new HashMap() ;
		
		if ( run == false ) 	
			return toReturn; 
			
		File path = new File( folder );
		File [] files = path.listFiles();							
		
		ArrayList<File> fileArray = new ArrayList<File>(Arrays.asList(files));
	
		for ( File f : fileArray ) {
			Map<Double, Double > map = expValues.readMap(run, folder, f.getName());
			toReturn.put(prob, map);
			prob++ ;
		}
		return toReturn ;	 
	}
	
	public void computeAnalysis ( String folder , Map mapToUpdate) throws ClassNotFoundException, IOException {
	
		 if ( run == false ) 	
			 return ; 
		
		 try {
			 File path = new File( folder );	
			 File [] files = path.listFiles();								
			 ArrayList<File> fileArray = new ArrayList<File>(Arrays.asList(files));
		 
	
			 for ( File f : fileArray ) {														//	 System.out.println(f);
				 Map<Double, Double > map = expValues.readMap(run, folder, f.getName());
				 mapToUpdate.put(prob, map);													//	 System.out.println(map);
				 prob++ ;
			 }																					//	 System.out.println(mapToUpdate);
		 } catch ( java.lang.NullPointerException e ) {
		 return ;
	 }
	}
	
	
}
