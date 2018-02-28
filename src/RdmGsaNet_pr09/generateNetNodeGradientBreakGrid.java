package RdmGsaNet_pr09;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNetAlgo.gsAlgoToolkit;

	public class generateNetNodeGradientBreakGrid extends generateNetNodeGradient implements generateNetNode_Inter {
		
		protected boolean controlSeed ;

		protected enum interpolation { linear} 
		protected interpolation typeInterpolation ;
		
		// COSTRUTOR
		public generateNetNodeGradientBreakGrid (int numberMaxSeed, layoutSeed setLayoutSeed , rule rule, String morp , double prob 
				 , boolean stillAlive , interpolation typeInterpolation 
				) {
			this.numberMaxSeed = numberMaxSeed ;
			this.setLayoutSeed = setLayoutSeed ;
			this.rule = rule ;
			this.morp = morp ;
			this.prob = prob ;
			this.stillAlive = stillAlive ;
			this.typeInterpolation  = typeInterpolation  ;
		}

		@Override
		public void generateNodeRule(int step) {

			// set seed nodes ( only first step )
			setSeedNodes(step, numberMaxSeed, setLayoutSeed);
					
			// CREATE LIST OF SEEDGRAD 
			ArrayList<String> listNodeSeedGrad = gsAlgoToolkit.getListStringNodeAttribute(netGraph, "seedGrad" , 1 );			//		System.out.println("number of seed " + listNodeSeedGrad.size() + " " + listNodeSeedGrad);

			for ( String idNode : listNodeSeedGrad ) {
				
				Node nGs = gsGraph.getNode(idNode);
				Node nNet = netGraph.getNode(idNode);
				
				int nGsDegree  = nGs.getDegree() ;			//			System.out.println("degree gs , id " + nGs.getId() + " " + nGsDegree);
				int nNetDegree = nNet.getDegree() ;			//			System.out.println("degree net, id " + nNet.getId() + " " + nNetDegree);
					
				// list of neig
				ArrayList <String> listNeigGsStr  = gsAlgoToolkit.getListNeighborStr ( gsGraph,  idNode) ;		//	System.out.println("listNeigGsString of node " + nGs.getId() + " " + listNeigGsStr);
				ArrayList <String> listNeigNetStr = gsAlgoToolkit.getListNeighborStr ( netGraph, idNode) ;		//	System.out.println("listNeigNetString of node " + nNet.getId() + " "  + listNeigNetStr);
				
				ArrayList <String> listNeigGsStrSeed = new ArrayList<String>();
				ArrayList <String> listNeigGsStrNotSeed = new ArrayList<String>();//			System.out.println(netGraph.getNodeSet() ) ;
			
				
				double valInter = computeInterpolation(null, null, idNode, morp);
				
			}
		
			
		}

		@Override
		public void removeNodeRule(int step) {
			// TODO Auto-generated method stub
			
		}
		
		
		

// COMPUTE INTERPOLATION ----------------------------------------------------------------------------------------------------------------------------
		private double computeInterpolation ( Graph graph0 , Graph graph1, String idNode , String attribute ) {
			
			Node n0 = graph0.getNode(idNode);
			Node n1 = graph1.getNode(idNode);
			
			double [] n0Coordinate = GraphPosLengthUtils.nodePosition(n0) ;
			double [] n1Coordinate = GraphPosLengthUtils.nodePosition(n1) ;
		
			String posRispFather = gsAlgoToolkit.getPosRelRispFather( n1 , n0 ) ;
			
			ArrayList<String> listVertex = new ArrayList<String>(4);
			
			
			
				
			
			
			int[] aldo = gsAlgoToolkit.getCoordinateOfNodeStr ( idNode );
			
	
			return 0 ;
			
		}
		
		
		
	}
