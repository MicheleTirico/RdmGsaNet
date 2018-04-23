package RdmGsaNet_seedBirth;

import java.util.ArrayList;
import java.util.Map;

import org.graphstream.graph.Node;

public interface seedBirt_inter {
		
	
	
	
	public void test ( ) ; 
	
	public  ArrayList<String> getListIdToSplit  ( double probSplit  , double percBirth , int numMaxNewSeed ) ;
	
	
	public Map <Node , Node > createNewSeed ( ArrayList<String> listIdToSplit , double dist ) ;
	
	public void connectNewSeed ( Map <Node , Node > mapNewNodeFather ) ;

}
