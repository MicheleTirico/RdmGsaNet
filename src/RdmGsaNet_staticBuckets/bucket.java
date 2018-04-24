package RdmGsaNet_staticBuckets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Node;

public class bucket extends abstractBuckets  { 	
	
	private double 	sizeBucketX , sizeBucketY ,
					posX , posY ;
	
	private String id ;
	
	public void setSizeBucket ( String id , double sizeBucketX , double sizeBucketY ) {
		
		this.id = id ;
		this.sizeBucketX = sizeBucketX ;
		this.sizeBucketY = sizeBucketY ;
	}
	
	public bucket ( String id , double posX , double posY ) {
		
	}
	
	public bucket getBuket ( Node node ) {
	
		return null;
	}
	
	public bucket createBuket ( Node node ) {
		
		return null;
	}
	
	public void setBuketInSet (  Map < bucket , ArrayList<Node> > buckets ) {
		
	
	}
	
	private ArrayList <Node> getListNode ( bucket bucket ) {
		
		ArrayList <Node> list = new ArrayList <Node>  () ;
		Map map = buckets ; 
				
				
				
 		return list ;
	}
	

}
