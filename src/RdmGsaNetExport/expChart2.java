package RdmGsaNetExport;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class expChart2 {
	
	public enum morp { activator, inhibitor }
	public enum chartType { line , test }
	public enum fileType { png , jpeg }
	
	public  chartType typeChart ;
	public  fileType typeFile ;
	public morp typeMorp ;
	public int width , height ;
	
	public expChart2 ( chartType typeChart , String dossier, String nameFile , fileType typeFile , morp typeMorp ) {
		this.typeChart = typeChart ;
		this.typeFile = typeFile ;
		this.typeMorp = typeMorp ;
		
		String typeFileStr = "" ;
		if 			( typeFile == typeFile.png  ) 	{ 	typeFileStr = ".png" ;	} 
		else {
				if 	( typeFile == typeFile.jpeg ) 	{	typeFileStr = ".jpeg" ;
			}
		}
		
		String path = dossier + nameFile + typeFileStr ;
	}
	
	public static void setDatasetFromMap ( Map<Integer,ArrayList<Double> > map , String rowKey ) {
		
		DefaultCategoryDataset lineChartDatasetAct = new DefaultCategoryDataset(); 
		DefaultCategoryDataset lineChartDatasetInh = new DefaultCategoryDataset(); 
		
		for (Entry<Integer,ArrayList<Double>> entry : map.entrySet() ) {
			
			int step = entry.getKey();
			int columnKey = step;
			ArrayList<Double> arrMorp = entry.getValue() ;
			
			double actVal = arrMorp.get(0) ;
			double inhVal = arrMorp.get(1) ;
			
			lineChartDatasetAct.addValue(actVal, rowKey, (Comparable) columnKey);
			lineChartDatasetInh.addValue(inhVal, rowKey, (Comparable) columnKey);
		}
	}
	
	public void setDimension ( int width , int height) {
		this.width = width; 
		this.height = height ;
	}
	
	public static void createChart ( ) {		
		
	}
	
	public static void saveChart ( ) {
		
	}
	
	

}
