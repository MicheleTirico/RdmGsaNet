package RdmGsaNetExport;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

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
	public enum typeChart { XYchart, test }
	
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
		case XYchart : 	{ 	chartPanel = createChartPanelXY( chartTitle , xAxisLabel, yAxisLabel, map ); 
							break ; }
		case test : 	{ 
							break ; }
		}
			
		add(chartPanel, BorderLayout.CENTER);
		setLayoutFrame () ;
	}

	private void setLayoutFrame( ) {	
				
		setSize( width , height );
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);	
	}
	
	private JPanel createChartPanelXY( String chartTitle, String xAxisLabel , String yAxisLabel ,  Map map ) {
		
        this.xAxisLabel = xAxisLabel ;
        this.yAxisLabel = yAxisLabel;
    
		XYDataset dataset = createDataset ( map );
        
        chart = ChartFactory.createXYLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset);
      
		return new ChartPanel(chart);
	}
	
	// create dataset of values ( which is a map java collection ) 
	private XYDataset createDataset(  Map<Double, ArrayList<Double>> map   ) {
		
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
		private XYDataset createDatasetMap (  Map<Double, Map < Double,  Double > > map   ) {
				
			
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
}
