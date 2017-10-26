package RdmGsaNet_pr05;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class viz extends setupGs {
	
	// show layer
	public void GsViz() {
		Graph graph = GsGraph; 
		graph.display();
	}
	
	// show list of ind and morphogens
	public void GsMorpViz() {
		Graph graph = GsGraph;
		for (Node n:graph.getEachNode()) { 
			double act, inh;
			act = n.getAttribute("GsAct");
			inh = n.getAttribute("GsInh");
			System.out.println("id node "+n.getId()+" GsAct = "+act);
			System.out.println("id node "+n.getId()+" GsInh = "+inh);
			}
		}
		

}
