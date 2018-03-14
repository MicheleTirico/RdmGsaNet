package RdmGsaNetExport;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class expTime {
	
	public static long getTimeMethod ( long startTime ) {
		
		
		
		
		
		return  ( System.nanoTime() - startTime  )   ;	
	}
	
	public static  long setStartTime ( ) {
		
		return  System.nanoTime();		
	}

}
