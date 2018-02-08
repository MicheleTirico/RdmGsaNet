package RdmGsaNet_pr09;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

	public class gsAlgo { 
		
		private static Graph gsGraph = layerGs.getGraph();
		private static Map<String, ArrayList<Double>> mapMorp0 = simulation.getmapMorp0(); 
		private static Map<String, ArrayList<Double>> mapMorp1 = simulation.getmapMorp1(); 
		
		private static boolean storeGsValues = layerGs.storeGsValues ;
		
		
		// declare enum type
		public enum morphogen {activator, inhibitor }
//		public enum interaction {reaction , diffusion, ext }
		
		public enum reactionType {ai2, linear }
		public enum diffusionType {fick, perimeter, weight }
		public enum extType {gsModel, test}
	
		// declare variables
		private static reactionType r ;
		private static diffusionType d ;
		private static extType e ;
		private static double Da  ;
		private static double Di  ;
		private static double feed ;
		private static double kill ;
		
		private static double[] arrVal = new double[4];
		// handle NaN
		private static boolean handleNaN ;
		private static double setIfNaN ;
		
		// handle minVal
		private static double minVal ;
		private static boolean handleMinMaxVal ;
		
		// handle MaxVal
		private static double maxVal ;
		
		// set parameters in constructor
		public gsAlgo (	reactionType r, diffusionType d, extType e, 
						double Da, double Di, double feed ,  double kill, 
						boolean handleNaN , double setIfNaN, 
						boolean handleMinMaxVal , 
						double minVal , double maxVal ) {
			this.r = r ;
			this.d = d ;
			this.e = e ;
			this.Da = Da ;
			this.Di = Di ;
			this.kill = kill ;
			this.feed = feed ;
			this.handleNaN = handleNaN ;
			this.setIfNaN = setIfNaN ;
			this.handleMinMaxVal = handleMinMaxVal ;
			this.minVal = minVal ;
			this.maxVal = maxVal ; 
			
			setValues(Da, Di, feed, kill);
		}
		
		public static void gsAlgoMain (boolean print ) throws InterruptedException {	
	
			//declare iterator to go over each node
			Iterator<Node> iterNode = gsGraph.iterator();

			// start iterator loop
			while (iterNode.hasNext()) {
					
				Node n = iterNode.next();
				String id = n.getId();	//	System.out.println("node id " + id);
				
				// create list (act , inh ) for each node of values before the calcule of each step.
				ArrayList<Double> ArList0 =  (ArrayList<Double>) mapMorp0.get(n.getId()) ; 
				
				// values before calcule
				double act0 = ArList0.get(0);
				double inh0 = ArList0.get(1);
					
				// compute reaction
				double reaction = gsAlgoReaction.gsComputeReaction(r, act0, inh0);
				
				// compute diffusion
				double diffusionAct = gsAlgoDiffusion.gsComputeDiffusion(d , Da , gsGraph, "gsAct", id, mapMorp0 ) ;
				double diffusionInh = gsAlgoDiffusion.gsComputeDiffusion(d , Di , gsGraph, "gsInh", id, mapMorp0 ) ;
					
				// compute external values
				double extFeed = gsAlgoExt.gsComputeExt(e, morphogen.activator , feed, kill , act0 , inh0 )  ;
				double extKill = gsAlgoExt.gsComputeExt(e, morphogen.inhibitor , feed, kill , act0 , inh0 )  ;
					
//				System.out.println("Da " + Da);
//				System.out.println("Di " + Di);
//				System.out.println("feed " + feed);
//				System.out.println("kill " + kill);
					
//				System.out.println();
//				System.out.println("reaction " + reaction);
//				System.out.println("diffusionAct " + diffusionAct);
//				System.out.println("diffusionInh " + diffusionInh);
//				System.out.println("extfeed " + extFeed);
//				System.out.println("extkill " + extKill);
				
				// compute new act and inh values for each node  
				double act1 = act0 + ( diffusionAct - reaction + extFeed ) ;
				double inh1 = inh0 + ( diffusionInh + reaction - extKill ) ;
				
				if ( handleNaN == true ) { 
					if ( Double.isNaN(act1) ) 	{	act1 = setIfNaN ;	}
					if ( Double.isNaN(inh1) )	{	inh1 = setIfNaN ;	}
				}
				
				if ( handleMinMaxVal == true ) { 
					if ( act1 < minVal )  	{	act1 = minVal ;	}
					if ( act1 >= 1  )  		{	act1 = maxVal ;	}
					
					if ( inh1 < minVal )	{	inh1 = minVal ;	}
					if ( inh1 >= 1  )  		{	inh1 = maxVal ;	}
				}
		
				if ( storeGsValues ) {
					n.setAttribute("gsRea", reaction);
					n.setAttribute("gsDifAct", diffusionAct);
					n.setAttribute("gsDifInh", diffusionInh);
					n.setAttribute("gsExtFeed", extFeed);
					n.setAttribute("gsExtKill", extKill);
				}
					
				// create a list of values act an inh
				ArrayList<Double> ArList1 = new ArrayList<Double>() ;
				
				// add act and inh in list
				ArList1.add( act1 );
				ArList1.add( inh1 );
			
				// update list in map
				mapMorp1.put(n.getId(), ArList1 );		
			}		
								
			// after each step, we update map1 in gs graph
			for ( Node n : gsGraph.getEachNode() ) {
					
				ArrayList ArList1 = (ArrayList) mapMorp1.get(n.getId()) ; 
					
				double act1 = (double) ArList1.get(0);
				double inh1 = (double) ArList1.get(1);
					
				n.setAttribute( "gsAct", act1);
				n.setAttribute( "gsInh", inh1);			
			}
			if (print ) {	printGsAlgo ();		}
		}
		
		// print results
		private static void printGsAlgo () {
 			System.out.println("mapMorp1 " + mapMorp1); //			System.out.println("mapMorp0 " + mapMorp0);	
		}
		
// GET METHODS ----------------------------------------------------------------------------------------
	
	public static  double getDa()  { return arrVal[0] ; } 
	public static double getDi()   { return arrVal[1] ; } 	
	public static double getFeed() { return arrVal[2] ; } 
	public static double getKill() { return arrVal[3] ; } 
		
	public static String getDiffusionType () { return d.toString();}
	
	// set val in an array in order to return the values
	public static void setValues ( double Da, double Di, double feed ,  double kill  ) { 
		arrVal[0]= Da;
		arrVal[1]= Di;
		arrVal[2]= feed;
		arrVal[3]= kill;
	}
		
	public static double[] getvaluesGsAlgo ( ) { return arrVal ; }	
}
	
	