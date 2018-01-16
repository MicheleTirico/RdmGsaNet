package RdmGsaNetExport;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
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
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import scala.collection.Set;
import scala.collection.generic.Sorted;


public class expChart extends JFrame  {
	
	public enum morp { activator, inhibitor }
	public enum typeChart { XYchart2Morp,  XYchartMultipleLine , test  }
	
	public typeChart type; 
//	public  chartType typeChart ;

	public morp typeMorp ;
	
	int width , height ;
	
	JFreeChart chart ;
	
	String chartTitle ;
    String xAxisLabel ;
    String yAxisLabel ;
  
	// COSTRUCTOR  
	public expChart (	typeChart type ,
						String chartTitle, String xAxisLabel , String yAxisLabel ,
						int width , int height , 
						Map map  ) {
		
		super ( chartTitle ) ;
		
		this.width = width; 
		this.height = height ;
		
		JPanel chartPanel = null ;
		switch ( type ) {
		case XYchart2Morp : 		{ chartPanel = createChartPanelXY2Morp ( chartTitle , xAxisLabel , yAxisLabel , map ); 	break ; }
	
		case XYchartMultipleLine : 	{ chartPanel = createChartPanelXYMultipleLine ( chartTitle , xAxisLabel , yAxisLabel , map ) ; break ; }	
		
		case test : 				{ 																					break ; }
		
		}
			
		add(chartPanel, BorderLayout.CENTER);
		setLayoutFrame () ;
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
	    Map <Double, ArrayList<Double>> mapChart = getSortedMap( map );	//     System.out.println(mapChart);
	    
	    XYDataset dataset = createDatasetMultipleLine ( mapChart );
	    
	//    XYDataset dataset = createDatasetMultipleLine ( map );

	    chart = ChartFactory.createXYLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset);
	    
		return new ChartPanel(chart);
	}
	
	// create dataset of values ( which is a map java collection ) 
	private XYDataset createDatasetMultipleLine2 (  Map<Double, ArrayList<Double>> mapChart  ) {
						
		// create dataset
		XYSeriesCollection dataset = new XYSeriesCollection();
	
		for ( Entry<Double, ArrayList<Double>> step : mapChart.entrySet() ) {
			
			System.out.println();
			
			String xStr = Double.toString(step.getKey()); 	
			XYSeries ser = new XYSeries(xStr) ;		
			
			//	System.out.println(xStr);
			ArrayList<Double> arr = step.getValue() ;
			System.out.println(arr);
			double xVal = step.getKey() ;
			
			for ( int freq = 0 ; freq < arr.size() ; freq++ ) {
			//	System.out.println(arr.get(x));
				
				
				double yVal = arr.get(freq) ;
				
				ser.add(xVal, yVal);
			}
			System.out.print(ser);		
			dataset.addSeries(ser);	
		}	
		
		return dataset;	 
	}
	
	private XYDataset createDatasetMultipleLine (  Map<Double, ArrayList<Double>> mapChart  ) {
	
		
		Object[] keys = mapChart.keySet().toArray();
		Random rand = new Random();

		// Get random key
		Double randKey = (Double) keys[ rand.nextInt(keys.length) ];
		
		ArrayList<Double> randList = mapChart.get(randKey);
		
		// create dataset
		XYSeriesCollection dataset = new XYSeriesCollection();
	
		for (  int freq = 0 ; freq < randList.size() ; freq++ ) {
			
			// create series
			XYSeries ser = new XYSeries(freq) ;
			
			for (Entry<Double, ArrayList<Double>> entry : mapChart.entrySet() ) {
				
				double xPosition =  entry.getKey();
				ArrayList<Double> arr = entry.getValue() ;
				double YVal = arr.get(freq) ;
		
				ser.add(xPosition,YVal);

			}
			// set series in dataset
			dataset.addSeries(ser);	
			
		}	
		return dataset;	 
	}
	
	private JPanel createChartPanelXY2Morp ( String chartTitle, String xAxisLabel , String yAxisLabel ,  Map map ) {
		
        this.xAxisLabel = xAxisLabel ;
        this.yAxisLabel = yAxisLabel;
    
		XYDataset dataset = createDataset2Morp ( map );
  
        chart = ChartFactory.createXYLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset);
      
		return new ChartPanel(chart);
	}
	
	// create dataset of values ( which is a map java collection ) 
	private XYDataset createDataset2Morp (  Map<Double, ArrayList<Double>> map   ) {
		
		// create dataset
		XYSeriesCollection dataset = new XYSeriesCollection();

 		// create series
		XYSeries serAct = new XYSeries("activator") ;
		XYSeries serInh = new XYSeries("inhibitor") ;
		
		for (Entry<Double, ArrayList<Double>> entry : map.entrySet() ) {
			
			double xPosition =  entry.getKey();
			ArrayList<Double> arrMorp = entry.getValue() ;
			double YactVal = arrMorp.get(0) ;
			double YInhVal = arrMorp.get(1) ;
		
			serAct.add(xPosition,YactVal);
			serInh.add(xPosition,YInhVal);		
		}
		
		// set series in dataset
		dataset.addSeries(serAct);	
		dataset.addSeries(serInh);
	    return dataset;	
	}
	
	// create dataset of values ( which is a map java collection ) 
	private XYDataset createDatasetMultipleLine3 (  Map<Double, Map < Double,  Double > > map   ) {
					
		// create dataset
		XYSeriesCollection dataset = new XYSeriesCollection();
	
		for ( Double step : map.keySet() ) {
			Map<Double, Double> mapInt = map.get(step);
				
			// create series	
			String xStr = Double.toString(step); 
			XYSeries ser = new XYSeries(xStr) ;
				
			double xPosition = step ;
				
			for (  Double freq : mapInt.keySet() ) {
				double yPosition = freq ;
				ser.add(xPosition, yPosition);
			}
			dataset.addSeries(ser);
		}		
		return dataset;	 
	}		

	// save chart ( jpeg format ) in a folder
	public void saveChart ( boolean saveImage ,  String folder, String nameChart ) throws IOException {

		if (saveImage == true ) {
			// aggiustare le eccezioni
		
			// define folder 
			String path = folder + nameChart +".jpeg" ;		
			File lineChartFile = new File( path );  
	    
			// save image
			ChartUtilities.saveChartAsJPEG(lineChartFile, chart , width , height ) ;
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
