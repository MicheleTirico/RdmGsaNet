package dynamicGraphSimplify;

import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public interface dynamicSymplify_inter {

	public void test ( );
	
	public void updateFatherAttribute ( int step , Map<String, String> mapFather ) ;
	
	public void handleGraphGenerator ( int step ) ;
	
//	public void setPivot ( boolean createPivot , double maxDistPivot ) ;
	
}
