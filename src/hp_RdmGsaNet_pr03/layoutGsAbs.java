package hp_RdmGsaNet_pr03;

import java.util.Random;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public  class layoutGsAbs extends startValuesGs implements layoutGs {

	static String distribution, homogeneus, random;
	int size;
	
	public void setMorp (Graph graph) {
		switch (distribution) {		
		case "homogeneus" :
			System.out.println("distribution homogeneus");
			float x = 0;
			for (Node n:graph.getEachNode()) { n.setAttribute("GsAct" , x  ) ;}
			for (Node n:graph.getEachNode()) { n.setAttribute("GsInh" , x  ) ;}
			break;
		case "random" :
			System.out.println("distribution random");
			Random act = new Random(getRandomSeedAct());
			Random inh = new Random(getRandomSeedInh());
			for (Node n:graph.getEachNode()) { n.setAttribute("GsAct" , act.nextFloat()   ) ;}
			for (Node n:graph.getEachNode()) { n.setAttribute("GsInh" , inh.nextFloat()   ) ;}
			break;
			}
		}
	
	public void showValueMorp(Graph graph) {
		for (Node n:graph.getEachNode()) { 
			float act, inh;
			act = n.getAttribute("GsAct");
			inh = n.getAttribute("GsInh");
			System.out.println("id node "+n.getId()+" GsAct = "+act);
			System.out.println("id node "+n.getId()+" GsInh = "+inh);
			}
		}
	
	public static void showGraph(Graph graph) {
		graph.display();	
	}

	public void setupTypelayout() {
		// TODO Auto-generated method stub
		
	}
	
	
}

	
		