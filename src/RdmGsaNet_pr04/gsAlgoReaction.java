package RdmGsaNet_pr04;




public class gsAlgoReaction implements gsAlgo {
	


	
	public void gsAlgoInit( morp x , interaction y ) {
		final String interaction = reactionType ;
		
		
		

	
	
		
	}

	@Override
	public void gsAlgoComp( interaction x ) {
		
		
			
			switch (reactionType) {
			case "ai2":
				reactionAi2 ();
				break;

			case "linear" :
				reactionLinear() ;
				break;
			}
		
	
		
	}

	@Override
	public void getValueMorp(morp x) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getValueInter(interaction x) {
		// TODO Auto-generated method stub
		
	}

	
	
	
	// method to compute reaction 
	private void reactionAi2 () {
		double Rai2 ;
		

	}
	
	private void reactionLinear () {
		
	}
	


}
