package breakGrid;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class breakGridInterpolationAttribute {

	protected enum interpolation { linear }
	protected interpolation typeInerpolation ;
	protected Graph graph0 , graph1 ;
	protected String attribute ;
	
	public breakGridInterpolationAttribute ( Graph graph0 , Node node1 , interpolation  typeInterpolation , String attribute ) {
	
		this.typeInerpolation = typeInterpolation ;
		this.attribute = attribute ;
	}
	
	public void computeInterpolation () {
		
		
		
		
	}
}
