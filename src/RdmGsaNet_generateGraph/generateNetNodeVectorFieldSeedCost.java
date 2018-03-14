package RdmGsaNet_generateGraph;

import java.io.IOException;
import java.util.ArrayList;

import org.graphstream.graph.Node;
import org.graphstream.stream.file.FileSinkDGS;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

import RdmGsaNetAlgo.graphToolkit;
import RdmGsaNetAlgo.gsAlgoToolkit;
import RdmGsaNetExport.expTime;
import RdmGsaNetExport.handleNameFile;
import RdmGsaNetAlgo.graphToolkit.element;
import RdmGsaNetAlgo.graphToolkit.elementTypeToReturn;
import RdmGsaNet_generateGraph.generateNetNode.interpolation;
import RdmGsaNet_generateGraph.generateNetNode.layoutSeed;
import RdmGsaNet_mainSim.main;

public class generateNetNodeVectorFieldSeedCost extends generateNetNodeVectorField implements generateNetNode_Inter {

	static FileSinkDGS fsd = new FileSinkDGS();
	private handleNameFile handle = main.getHandle();
	
	
	protected int startStep ;
	// protected ArrayList<Node> list;
	public generateNetNodeVectorFieldSeedCost(int numberMaxSeed, layoutSeed setLayoutSeed, interpolation typeInterpolation , int startStep , boolean createSeedGraph , boolean updateNetGraph ) {
		super(numberMaxSeed, setLayoutSeed, typeInterpolation , createSeedGraph , updateNetGraph );
		this.startStep = startStep ;
	}

	@Override
	public void generateNodeRule(int step) throws IOException {			//		System.out.println(super.getClass().getSimpleName());
		// set seed nodes 
		long startTime = expTime.setStartTime();
		
		generateNetNode.setSeedNodes(step, numberMaxSeed, setLayoutSeed);
		
		if ( createSeedGraph ) {
			if ( step == 1)	{				//		System.out.println("createSeedGraph step1" );
				ArrayList<String> list = new ArrayList<String> ( graphToolkit.getListElementAttribute( netGraph, element.node, elementTypeToReturn.string, "seedGrad", 1 ) ) ;
				for ( String id : list ) {
					Node n = netGraph.getNode(id) ;
					seedGraph.addNode( id );
					Node nodeSeed = seedGraph.getNode(id) ;
					gsAlgoToolkit.setNodeCoordinateFromNode(netGraph, seedGraph, n, nodeSeed);
					
					
					String pathStart = handle.getPathStartSeed();
					seedGraph.write(fsd, pathStart);	}
			
				
			}			
		}	
		
		if ( step <= startStep )
			return ; 
		
		ArrayList<Node> listNodeSeedGrad = new ArrayList<Node> () ;
		// CREATE LIST OF SEEDGRAD 
		if ( createSeedGraph ) 
			listNodeSeedGrad = graphToolkit.getListElement(seedGraph, element.node, elementTypeToReturn.element );					//	System.out.println("createSeedGraph" );
		else																													 	//	System.out.println("iter in netGraph" );
			listNodeSeedGrad =  gsAlgoToolkit.getListNodeAttribute(netGraph, "seedGrad" , 1 )  ;									//	System.out.println("number of seed " + listNodeSeedGrad.size() + " " + listNodeSeedGrad);
			
		int idNum = listNodeSeedGrad.size() * step  ;
		
		for ( Node nodeSeed : listNodeSeedGrad ) {																					//	System.out.println(nodeSeed.getAttributeKeySet());
			
			String idSeed = nodeSeed.getId() ;
				
			double[] nodeCoord = GraphPosLengthUtils.nodePosition(nodeSeed) ;
					
			double[] vector = getVector(vecGraph, nodeCoord, typeInterpolation.sumVectors) ;										//c'é 	System.out.println(idSeed  + " "  + vector[0] + " " + vector[1]);
					
			//	String idCouldAdded = Integer.toString(netGraph.getNodeCount() );
			String idCouldAdded = Integer.toString(idNum ); 	//	System.out.println(netGraph.getNodeCount());
			Node nodeCouldAdded = null ;							
			
			double 	xNewNode = generateNetNode.ceckCoordInGrid ( gsGraph , nodeCoord[0] + vector[0] ) ,
					yNewNode = generateNetNode.ceckCoordInGrid ( gsGraph , nodeCoord[1] + vector[1] ) ;
				
			
			if (updateNetGraph ) 
				generateNetNode.handleNewNodeCreation(netGraph, idCouldAdded, nodeSeed, xNewNode, yNewNode , true  )	;
			
			if ( createSeedGraph ) {
				nodeSeed.setAttribute( "xyz", xNewNode , yNewNode, 0 );
			}
				
				
				
			/*	
				seedGraph.addNode(idCouldAdded);
				Node nodeSeedGraphCouldAdded = seedGraph.getNode(idCouldAdded) ;
				nodeSeedGraphCouldAdded.setAttribute( "xyz", xNewNode , yNewNode, 0 );	
				nodeSeedGraphCouldAdded.addAttribute("father", nodeSeed.getId() );				//		System.out.println(nodeSeed.getId());	//		System.out.println(seedGraph.getNodeSet() ) ;
				seedGraph.removeNode(nodeSeed);
			}	
			idNum ++ ;
		*/
		}
	//	System.out.println("genNode " + expTime.getTimeMethod(startTime));
	}
	

	@Override
	public void removeNodeRule(int step) {
		// TODO Auto-generated method stub
		
	}
	
	
}
