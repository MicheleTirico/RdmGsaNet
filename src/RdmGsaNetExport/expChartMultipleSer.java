package RdmGsaNetExport;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import java.util.HashMap;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class expChartMultipleSer extends JFrame  {
	

	public enum typeChart { XYchartSer_xy }
	
	public typeChart type; 


	
	public int width , height ;
	 
	JFreeChart chart ;
	
	String chartTitle ;
    String xAxisLabel ;
    String yAxisLabel ;
  
//	 COSTRUCTOR  
	public expChartMultipleSer (	typeChart type ,
						String chartTitle, String xAxisLabel , String yAxisLabel ,
						int width , int height , 
						Map map  ) {
		
		super ( chartTitle ) ; 
		
		this.width = width; 
		this.height = height ;
	
		try {	
			JPanel chartPanel = null ;
			switch ( type ) {
	
			case XYchartSer_xy : 	{ chartPanel = createChartPanelXYMultipleLine ( chartTitle , xAxisLabel , yAxisLabel , map ) ; 	break ; }	
			

				
			}
			
			add(chartPanel, BorderLayout.CENTER);
			setLayoutFrame () ;
			
		} catch (java.lang.IllegalArgumentException e) {	System.exit(100);		} // exit if map is empty 
	}

	private void setLayoutFrame( ) {	
				
		setSize( width , height );
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);	
	}
	
		
	private JPanel createChartPanelXYMultipleLine ( String chartTitle , String xAxisLabel , String yAxisLabel ,  Map<Double, Map<Double,Double>> map ) {
		
		this.xAxisLabel = xAxisLabel ;
	    this.yAxisLabel = yAxisLabel;
	    
	    // covert map to mapChart
	    Map <Double, ArrayList<Double>> mapChart = getSortedMap( map );	//        System.out.println(mapChart);
	    
	    XYDataset dataset = createDatasetMultipleLine_ser_xy ( map );
	
	    chart = ChartFactory.createXYLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset);
	    
		return new ChartPanel(chart);
	}
	
	// create dataset of values ( which is a map java collection ) 
	private XYDataset createDatasetMultipleLine_ser_xy (  Map<Double, Map<Double,Double>> mapChart  ) 	{
		 
		// create dataset
		XYSeriesCollection dataset = new XYSeriesCollection();
	
		for ( double serName = 0.0 ; serName < mapChart.size() ; serName++ ) {
			
			// create series
			XYSeries ser = new XYSeries(serName) ;
			Map<Double,Double> map = mapChart.get(serName) ;
			System.out.println(map);
			for ( Entry<Double, Double> entry : map.entrySet()) {
				double xVal = entry.getKey();
				double yVal = entry.getValue() ;
				ser.add(xVal, yVal);
			}
			
		}
		return dataset;	 
	}
		
	// save chart ( jpeg format ) in a folder
	public void saveChart ( boolean saveImage ,  String folder, String nameChart ) throws IOException {

		if (saveImage  ) {
	
		
			// define folder 
			String path = folder + nameChart +".jpeg" ;		
			File lineChartFile = new File( path );  
	    
			// save image
			try {
				ChartUtilities.saveChartAsJPEG(lineChartFile, chart , width , height ) ;
			} catch (java.util.ConcurrentModificationException e) { 		}
		}
	}
		
	private static  Map <Double, ArrayList<Double>> getSortedMap ( Map<Double, Map<Double,Double>> map ) {
		
		Map <Double, ArrayList<Double>> mapChart = new HashMap<Double, ArrayList<Double>>();
		
		// covert map to sortedMap 
	    Map<Double, Map<Double,Double>> sortedMapVal = new HashMap<Double, Map<Double,Double>>(); 
	  
	    for ( Double step : map.keySet()) {
	    	Map mapToSort = map.get(step);
	    	Map<Double , Double > sotedMapValue = new TreeMap<Double, Double>(mapToSort); //	System.out.println(sotedMapValue);
	    	sortedMapVal.put(step, sotedMapValue);
	    }
	    
	    Map<Double, Map<Double,Double>> sortedMap = new TreeMap<Double, Map<Double,Double>>(sortedMapVal); //  System.out.println(sortedMap);
 
	    for ( Double step : sortedMap.keySet()) {					//	System.out.println(step);
	    	Map<Double, Double> mapFreq = sortedMap.get(step);
	    	ArrayList<Double> listNodeCount = new ArrayList<Double>();
	    	
	    	for ( Double freq : mapFreq.keySet() ) {	 			//	System.out.println(freq);
	    		double nodeCount = mapFreq.get(freq);				// 	System.out.println(nodeCount);
	    		listNodeCount.add(nodeCount);
	    	}
	    	mapChart.put(step, listNodeCount);
	    }
	    return mapChart; 
	}
}
