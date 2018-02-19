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

public class dataAnalysisGlobal extends dataAnalysisMain {
	
	protected static boolean run ;
	private double  prob = 0.0 ,
					stepInt = 0.0 ;
	
	
	public dataAnalysisGlobal( boolean run ) {
		this.run = run ;
	}

	public Map getMapComputed ( String folder ) throws ClassNotFoundException, IOException {
		
		Map toReturn = new HashMap() ;
		if ( run == false ) 	return toReturn; 
			
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
			
		 File path = new File( folder );
		
		 File [] files = path.listFiles();							
		
		 ArrayList<File> fileArray = new ArrayList<File>(Arrays.asList(files));
	
		 for ( File f : fileArray ) {
			 Map<Double, Double > map = expValues.readMap(run, folder, f.getName());
			 mapToUpdate.put(prob, map);
			 prob++ ;
			}
	
		 }

}
