package RdmGsaNetExport;

import java.awt.BorderLayout;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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


public class expChart extends JFrame  {
	
	public enum morp { activator, inhibitor }
	public enum typeChart { XYchart2Morp,  XYchartMultipleLine , XYchartSingleLine , XYchartSer_xy }
	
	public typeChart type; 

	public morp typeMorp ;
	
	public int width , height ;
	 
	JFreeChart chart ;
	
	String chartTitle ;
    String xAxisLabel ;
    String yAxisLabel ;
  
//	 COSTRUCTOR  
	public expChart (	typeChart type ,
						String chartTitle, String xAxisLabel , String yAxisLabel ,
						int width , int height , 
						Map map  ) {
		
		super ( chartTitle ) ; 
		
		this.width = width; 
		this.height = height ;
	
		try {	
			JPanel chartPanel = null ;
			switch ( type ) {
			case XYchart2Morp : 		{ chartPanel = createChartPanelXY2Morp ( chartTitle , xAxisLabel , yAxisLabel , map ); 			break ; }
	
			case XYchartMultipleLine : 	{ chartPanel = createChartPanelXYMultipleLine ( chartTitle , xAxisLabel , yAxisLabel , map ) ; 	break ; }	
			
			case XYchartSingleLine :	{ chartPanel =  createChartPanelXYSingleLine ( chartTitle , xAxisLabel , yAxisLabel , map ) ; 	break ; }
				
			case XYchartSer_xy : 		{ chartPanel =  createChartPanelXY_ser_xy ( chartTitle , xAxisLabel , yAxisLabel , map ) ; 	break ; }
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
	
	private JPanel createChartPanelXYSingleLine ( String chartTitle , String xAxisLabel , String yAxisLabel ,  Map<Double, Double> map ) {
		
		this.xAxisLabel = xAxisLabel ;
	    this.yAxisLabel = yAxisLabel;
	    
	    XYDataset dataset = createDatasetSingleLine(map);
	
	    chart = ChartFactory.createXYLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset);
	    
		return new ChartPanel(chart);
	}
	
	private JPanel createChartPanelXY_ser_xy ( String chartTitle , String xAxisLabel , String yAxisLabel ,  Map<Double, Map<Double,Double>> map ) {
		this.xAxisLabel = xAxisLabel ;
	    this.yAxisLabel = yAxisLabel; 
	    
	    // covert map to mapChart
	  //  Map <Double, ArrayList<Double>> mapChart = getSortedMap( map );	//        System.out.println(mapChart);
	    
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
			
			for ( Entry<Double, Double> entry : map.entrySet()) {
				double xVal = entry.getKey();
				double yVal = entry.getValue() ;
				ser.add(xVal, yVal);
			}
			dataset.addSeries(ser);		
		}
		return dataset;	 
	}
	
	private JPanel createChartPanelXYMultipleLine ( String chartTitle , String xAxisLabel , String yAxisLabel ,  Map<Double, Map<Double,Double>> map ) {
		
		this.xAxisLabel = xAxisLabel ;
	    this.yAxisLabel = yAxisLabel;
	    
	    // covert map to mapChart
	    Map <Double, ArrayList<Double>> mapChart = getSortedMap( map );	//     System.out.println(mapChart);
	    
	    XYDataset dataset = createDatasetMultipleLine ( mapChart );
	
	    chart = ChartFactory.createXYLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset);
	    
		return new ChartPanel(chart);
	}
	
	// create dataset of values ( which is a map java collection ) 
	private XYDataset createDatasetMultipleLine (  Map<Double, ArrayList<Double>> mapChart  ) 	{
	
		// Get random key
		Object[] keys = mapChart.keySet().toArray();
		Random rand = new Random();
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
	
	// create dataset single line 
	private XYDataset createDatasetSingleLine (  Map <Double, Double > map   ) {
		
		// create dataset
		XYSeriesCollection dataset = new XYSeriesCollection();

 		// create series
		XYSeries ser = new XYSeries("serSingle") ;
		
		for (Entry<Double, Double> entry : map.entrySet() ) {
			
			double xPosition =  entry.getKey();
			
			double YVal = entry.getValue() ;
	
			ser.add(xPosition,YVal);
		}	
		// set series in dataset
		dataset.addSeries(ser);		
		
	    return dataset;	
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
	
	// save chart ( jpeg format ) in a folder
	public void saveChart ( boolean saveImage ,  String folder, String nameChart ) throws IOException {

		if (saveImage  ) {
			// define folder 
			String path = folder + nameChart + ".jpeg"  ;		
			File lineChartFile = new File ( path );  
	   
			OutputStream stream = new FileOutputStream( path ) ;
		
			// save image
			try {
	//			System.out.println(lineChartFile +"\n"+ chart +"\n"+ width +"\n"+ height );
				System.out.println(lineChartFile) ; 
				
				
				ChartUtilities.writeChartAsJPEG(stream, chart, width, height);
			}
			catch (java.util.ConcurrentModificationException e) {
		
			}
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
