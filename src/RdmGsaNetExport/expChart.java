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

import javafx.scene.chart.XYChart;


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
  
    Map<Integer, ArrayList<Double>> map ;
	
	// costructor 
	public expChart (	typeChart type ,
						String chartTitle, String xAxisLabel , String yAxisLabel ,
						int width , int height , 
						Map<Integer, ArrayList<Double>> map  ) {
		
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
	
	private JPanel createChartPanelXY( String chartTitle, String xAxisLabel , String yAxisLabel ,  Map<Integer, ArrayList<Double>> map ) {
		
        this.xAxisLabel = xAxisLabel ;
        this.yAxisLabel = yAxisLabel;
    
		XYDataset dataset = createDataset( map );
        
        chart = ChartFactory.createXYLineChart(chartTitle,
                xAxisLabel, yAxisLabel, dataset);
      
		return new ChartPanel(chart);
	}
	
	// create dataset of values ( which is a map java collection ) 
	private XYDataset createDataset(  Map<Integer, ArrayList<Double>> map   ) {
		
		// create dataset
		XYSeriesCollection dataset = new XYSeriesCollection();

		// create series
		XYSeries serAct = new XYSeries("activator") ;
		XYSeries serInh = new XYSeries("inhibithor") ;
		
		for (Entry<Integer,ArrayList<Double>> entry : map.entrySet() ) {
			
			double x = (double) entry.getKey();
			ArrayList<Double> arrMorp = entry.getValue() ;
			double YactVal = arrMorp.get(0) ;
			double YInhVal = arrMorp.get(1) ;
		
			serAct.add(x,YactVal);
			serInh.add(x,YInhVal);		
		}
		// set series in dataset
		dataset.addSeries(serAct);	
		dataset.addSeries(serInh);
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
