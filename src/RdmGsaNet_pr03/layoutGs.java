package RdmGsaNet_pr03;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

public interface layoutGs {
	
	// declaration of static parameters
	public Graph GsGraph = new SingleGraph("GsGraph");
	
//-------------------------------------------------------------------------------------------------------
	// parameters of GsLayer
	
	// type layout
	public enum typeLayout { grid, random, gis }

	
	//  enumerate distribution of morhogens
	public enum disMorp {homogeneus, random }
	
//-------------------------------------------------------------------------------------------------------
	// set and get
	// Grid layout
//	public void setGsGridLayout (String x) { x = typeLayout; }
//	public int getGsGridLayout () { return typeLayout; }
	
	// distribution Morphogens
//	public void setDisMorp (String x) { x = disMorp; }
//	public int getdisMorp () { return disMorp; }
	
	

	

//-------------------------------------------------------------------------------------------------------------	
	// methods
	public static void setMorp(Graph graph) {}	// method to define morphogens dstribution
		
	public static void showValueMorp(Graph graph) {}

	public static void setupTypelayout(typeLayout grid) {
		typeLayout x = grid;
		
		switch (x) {
		case grid :
			System.out.println("grid");
			
		
			break;
	
		case gis :
			System.out.println("gis");
			break;

		case random :
			System.out.println("random");
			break;
		}
		
		
		
	}
	
}
		
		/*
		final class gino {
			typeLayout type;
			
			
			public gino (typeLayout type) {
				this.type = type;	
				testami();
			}
		
			private void testami () {
				switch (type) {
				case grid :
					System.out.println("grid");
					
				
					break;
			
				case gis :
					System.out.println("gis");
					break;
		
				case random :
					System.out.println("random");
					break;
				
				
			}
		}
		}
		
		
	}
	
//	public static void setupTypelayout(typeLayout type) {} 
}
*/
