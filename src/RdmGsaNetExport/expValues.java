package RdmGsaNetExport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class expValues {
	
	// method to export in a folder generic map after simulation
	public static void writeMap ( Boolean writeRun , Map map , String folderMap, String nameMap ) throws IOException {
		if ( writeRun ) {
			
			String path = folderMap + nameMap; 
			File file = new File(path);
			
			FileOutputStream fos = new FileOutputStream(file) ; 
			ObjectOutputStream oos = new ObjectOutputStream(fos) ;
			
			oos.writeObject(map) ;
			oos.flush(); 
		}
	}
	
	// method to read generic map in folder
	public static Map readMap ( Boolean readRun , String folderExp, String nameFileExp ) throws IOException, ClassNotFoundException  {
		
		Map map = new HashMap();
		
		if ( readRun ) {
			String path = folderExp + nameFileExp; 
			File file = new File(path);
			
			FileInputStream fis = new FileInputStream(file) ;
			ObjectInputStream ois = new ObjectInputStream(fis) ;
			
			map = (Map)ois.readObject(); 
			ois.close();
		}
		return map ;
	}
	
	/*	method to create a map with id of and list of morphogens of each nodes
	 * 		map < string, list > = map < id , morphogen >	 			*/
	public static Map<String, ArrayList<Double>> getMapIdGsMorp ( Graph graph ) {
		
		Map<String, ArrayList<Double>> mapIdGsMorp = new HashMap<String, ArrayList<Double>>();
		
		for ( Node n : graph.getEachNode()) {
			
			String idNode = n.getId();
			double act = n.getAttribute("gsAct") ;
			double inh = n.getAttribute("gsInh") ;

			ArrayList<Double> morp = new ArrayList<Double> ();
				
			morp.add(act) ;
			morp.add(inh) ;
			
			mapIdGsMorp.put(idNode, morp) ;	
		}	
		return mapIdGsMorp ;
	}
}
