package RdmGsaNet_pr08;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import com.sun.org.apache.xml.internal.security.utils.SignerOutputStream;

public class gsAlgoDiffusion  {
			
		// variables
	gsAlgo.diffusionType type;
	gsAlgo.morphogen morp;
		
	public static String typeDiff ;
	private static  double 	sideValSt ,			
							cornerValSt ;
		
	public enum weightType { matrix , length }
	private static weightType typeWeig;
			
	// constructor ( define type of diffusion and morphogen )
	public gsAlgoDiffusion (gsAlgo.diffusionType type,  gsAlgo.morphogen morp ) {
		this.type = type;
		this.morp = morp;
	}
		
	// this method return the value of diffusion. 
	public static double gsComputeDiffusion( gsAlgo.diffusionType type , double speed , Graph graph, String morp, String id, Map mapMorp ) {
		double diffusion = 0 ;			
			
		switch (type) {
			case fick: {
				diffusion = speed * fick ( graph, morp, id, mapMorp ) ; 			} break ;	//				System.out.println("diffFick");
			
				
			// diffusion not yet implemented 
			case perimeter : { 
				diffusion = perimeter ( ) ;  										} break ;	//				System.out.println("diffPer");
			
			case weight : { 
				diffusion = speed * weight ( typeWeig, graph, morp, id, mapMorp ) ;	} break ;	//				System.out.println("diffWeig");
					
		}																						//		System.out.println(diffusion);
		typeDiff = type.toString();
		return diffusion;
	}

//------------------------------------------------------------------------------------------------------		
	// methods to define diffusion type
		
	// classical diffusion, equilibrium of mass
	private static double fick (  Graph graph, String morp , String id , Map mapMorp ) {
			
		// local variables
		double 	diffusion ,
				d0 ,
				d0Neig  ,
				sumNeig = 0 ;
		int degree  ;
			
		// iterator
		Node n = graph.getNode(id);
		Iterator<Node> iter = n.getNeighborNodeIterator() ;		//	System.out.println("id " + n);
		 
		// list mapMorp
		ArrayList d0list = (ArrayList) mapMorp.get(id) ;		//	System.out.println(list0);
			
		// define morhogen 
		int morpInt;
		if (morp == "gsAct")	{	morpInt = 0 ;	}
		else 					{	morpInt = 1 ;	}			//	System.out.println(morp);
			
		// degree
		degree = n.getInDegree(); 	//	System.out.println(id + " degree " + degree);
			
		// count d0
		d0 = (double) d0list.get(morpInt);		//	System.out.println(mapMorp0);		System.out.println("d0 " + d0); 
		
		// count d0Neig
		while (iter.hasNext()) {
				 
			Node neig = iter.next() ;
			ArrayList<Double> neigList0 = (ArrayList) mapMorp.get(neig.getId()) ;
				
			d0Neig =  neigList0.get(morpInt);	//	System.out.println("d0Neig " + d0Neig);
			sumNeig = sumNeig + d0Neig;			//	System.out.println(neig.getId() + "  " +neigList0);	String a = neig.getId() ;		System.out.println(a);
		}										//	System.out.println("sumNeig " + sumNeig);
			
		// compute fick's diffusion
		diffusion =  - degree * d0 + sumNeig ;		//	System.out.println(diffusion);
		return diffusion ;
	}
				
//----------------------------------------------------------------------------------------------------------------------------------
		
	// diffusion not defined
	private static double perimeter (  ) {
		double diffusion = 0;
		return diffusion ;		
		}

	// diffusion not defined
	private static double weight ( weightType typeWeig , Graph graph, String morp , String id , Map mapMorp) {
		double diffusion = 0;
			
		switch (typeWeig) {
			case matrix : { 
//				System.out.println("matrix");
				diffusion = getDiffusionWeightMatrix (graph, morp, id, mapMorp);
				} break;
			case length : { 
//				System.out.println("length");
				diffusion = 0 ;}
		}
		return diffusion ;
	}

	private static double getDiffusionWeightMatrix (Graph graph, String morp , String id , Map mapMorp ) {

	
		// local variables
		double 	dif = 0 ,
				morp0 , 		// value of morphogen in node of analysis 
				morpS ,			// morphogen of side neighbors
				morpC ,			// morphogen of corner neighbors
				sumMorpS = 0 ,	// sum of values of side neighbors 
				sumMorpC = 0,	// sum of values of corner neighbors
				
				sideVal = sideValSt ,
				cornerVal = cornerValSt;
		
		Node n = graph.getNode(id);
		int degree = n.getInDegree();	
		
		// list mapMorp = two values, an activator and an inhibitor for node n mapMorp
		ArrayList morp0list = (ArrayList) mapMorp.get(id) ;									//	System.out.println(morp0list);
	
		// define morhogen 
		int morpInt;
		if (morp == "gsAct")	{	morpInt = 0 ;	}
		else 					{	morpInt = 1 ;	}			
					
		// value of morhogen in node
		morp0 = (double) morp0list.get(morpInt);											//		System.out.println(morpInt + " " + morp0);
		
		ArrayList<String> listNeig = new ArrayList<String>();
		ArrayList<String> listNeigSide = new ArrayList<String>();
		ArrayList<String> listNeigCorner = new ArrayList<String>();

		Iterator<Node> iter = n.getNeighborNodeIterator() ;	
		
		while ( iter.hasNext()) {
			Node neig = iter.next();
			String idNeig = neig.getId();
		
			Edge e = n.getEdgeFrom(neig) ;
			double len = e.getAttribute("length");											//	System.out.println(len);
			
			if ( len ==  1 ) 	{ listNeigSide.add(idNeig); 	}
			else 				{ listNeigCorner.add(idNeig); 	}
			listNeig.add(idNeig);
		}																					//		System.out.println(listNeig);//		System.out.println(listNeigSide);//		System.out.println(listNeigCorner);//		System.out.println(degree) ;
		
		double coefSide = 0 , coefCorner = 0  ;
	
		switch (degree) { 
		case 5: {
			coefSide =  2.0/7 ;
			coefCorner =  1.0/14 ;
		} ; break ;
		case 3 : {
			coefSide = 4.0/9 ;
			coefCorner = 1.0/9 ;	
		} ; break;
		case 8 : {
			coefSide =  1.0/5 ;
			coefCorner = 1.0/20 ;											//			System.out.println(coefCorner + " " + coefSide);
		} ; break;
	}																		//		System.out.println(degree ) ; //		System.out.println(coefCorner + " " + coefSide);
		for ( String idNeigSt : listNeig ) {
			Node idNeigNd = graph.getNode(idNeigSt);						//			System.out.println(idNeigSt);
		
			double morpNeig = idNeigNd.getAttribute(morp) ;					//			System.out.println(morpNeig);
			
			if ( listNeigSide.contains(idNeigSt))	{ sumMorpS = sumMorpS + morpNeig ; }
			else 									{ sumMorpC = sumMorpC + morpNeig ; }
		}	
		
		dif =  - morp0 + ( coefSide * sumMorpS + coefCorner * sumMorpC ) ;	//		System.out.println(n.getId() + "  " + dif);
		return dif;
	}		

	public static void setLaplacianMatrix ( double sideVal, double cornerVal ) {
		sideValSt =  sideVal ;
		cornerValSt = cornerVal ;	
	}

	public static void setWeightType(weightType type) {
		typeWeig = type;
		}
	
// GET METHODS ----------------------------------------------------------------------------------------------------------------------------
	public static String getDiffusionType() { return typeDiff; }
}
