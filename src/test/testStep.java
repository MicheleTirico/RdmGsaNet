package test;

public class testStep {

	public static void main(String[] args) {

		double stepTest = 1 ;
		for ( int x = 0 ; x <= 100 ; x++ ) {
			if (  x / stepTest - Math.floor(x / stepTest ) <= 1 / ( 2 * stepTest ) )
				System.out.println( x +" "+ x / stepTest ) ;
			
			
		//	System.out.println( x +" "+ x / stepTest +" "+ isInteger ( x / stepTest ) ) ;
				
		}

	}
	
	
	
		

}
