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
	
//	public  chartType typeChart ;

	public morp typeMorp ;
	
	int width , height ;
	
	JFreeChart chart ;
	
	String chartTitle ;
    String xAxisLabel ;
    String yAxisLabel ;
  
    Map<Integer, ArrayList<Double>> map ;

	
	// costructor 
	public expChart ( String chartTitle, int width , int height , Map<Integer, ArrayList<Double>> map ) {
		
		super ( chartTitle ) ;
		
		this.width = width; 
		this.height = height ;
		
		JPanel chartPanel = createChartPanelXY( chartTitle , map );
		add(chartPanel, BorderLayout.CENTER);
		
	}

	
	public void setLayoutFrame( String title  ) {	
				
		setSize( width , height );
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	public void setLayoutChart( String chartTitle, String xAxisLabel , String yAxisLabel ) {
		
		this.chartTitle = chartTitle;
		this.xAxisLabel = xAxisLabel;
		this.yAxisLabel = yAxisLabel;
	}
	
	
	private JPanel createChartPanelXY(  String chartTitle, Map<Integer, ArrayList<Double>> map ) {
		this.chartTitle = chartTitle;
        String xAxisLabel = "X";
        String yAxisLabel = "Y";
    
		XYDataset dataset = createDataset( map );
        
        chart = ChartFactory.createXYLineChart(chartTitle,
                xAxisLabel, yAxisLabel, dataset);
      
		return new ChartPanel(chart);
	}

	
	
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
		/*
		
		// add values in series
		
		serAct.add(1.0, 2.0);
		serAct.add(2.0, 3.0);
		serAct.add(3.0, 2.5);
		serAct.add(3.5, 2.8);
		serAct.add(4.2, 6.0);
		
		serInh.add(2.0, 1.0);
		serInh.add(2.5, 2.4);
		serInh.add(3.2, 1.2);
		serInh.add(3.9, 2.8);
		serInh.add(4.6, 3.0);
		
		*/
		// set series in dataset
		dataset.addSeries(serAct);
		
		dataset.addSeries(serInh);
	 
	    return dataset;
		
		
	}


	
	public void saveChart ( boolean saveImage ,  String folder, String nameChart ) throws IOException {

		if (saveImage = true ) {
			// aggiustare le eccezioni
		
			// define folder 
			String path = folder + nameChart +".jpeg" ;		
			File lineChartFile = new File( path );  
	    
			// save image
			ChartUtilities.saveChartAsJPEG(lineChartFile, chart , width , height ) ;
		}
	}


}
