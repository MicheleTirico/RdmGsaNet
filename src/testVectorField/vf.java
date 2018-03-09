package testVectorField;

public class vf {

	vf_int vfI ; 
	
	public enum vfType {		spatial   , 		temporal  			}
	vfType type ; 
	
	public vf ( vfType type ) {
		this.type = type ; 
		
		switch (type) {
		case spatial:
			vfI = new vf_s() ;
			break;

		case temporal :
			vfI = new vf_t () ;
			break ; 
		}
		
		 
		
	}
	
	public void run ( ) {
		vfI.test();
		
	}
	
	
	
	
	
	
	

}
